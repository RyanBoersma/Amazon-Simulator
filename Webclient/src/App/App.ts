import * as THREE from "three";
import { Object3D, PCFSoftShadowMap, Scene, Vector3, WebGLRenderer } from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import { Input } from "../Lib/Input";
import { EffectComposer } from "three/examples/jsm/postprocessing/EffectComposer";
import { RenderPass } from "three/examples/jsm/postprocessing/RenderPass";
import { SAOPass } from "three/examples/jsm/postprocessing/SAOPass";
import Robot from "./NetworkObjects/Robot/Robot";
import SimulationStatus from "./NetworkObjects/SimulationStatus/SimulationStatus";
import StorageUnit from "./NetworkObjects/StorageUnit/StorageUnit";
import { ShaderPass } from "three/examples/jsm/postprocessing/ShaderPass";
import Truck from "./NetworkObjects/Truck/Truck";
import { Loaders } from "../Lib/Loaders";
import GammaCorrectionShader from "./Shaders/GammaCorrectionShader";
import Player from "./Player";
import NetworkObject from "./NetworkObjects/NetworkObject/NetworkObject";
import { NetworkObjectDTO } from "./NetworkObjects/NetworkObject/NetworkObjectDTO";
import { DTOMapper } from "./DTOMapper";
import NetworkObject3D from "./NetworkObjects/NetworkObject3D/NetworkObject3D";

// Honestly. This file is a mess, and should be split into different parts:
// Creating the scene
// Rendering
// HTML UI stuff
// Websocket stuff
// Additionally, App should *really* be class, instead of a namespace.

export namespace App {
	const renderer = new WebGLRenderer({
		antialias: true,
		powerPreference: "high-performance",
		canvas: document.querySelector("canvas"),
	});
	const composer = new EffectComposer(renderer);
	const scene = new Scene();

	const networkObjects: Map<string, NetworkObject> = new Map();

	const player = new Player(new Vector3(0, 0, 0));
	let frameProgress = 0;

	let socket: WebSocket | undefined;

	const renderTarget = new THREE.WebGLRenderTarget(1024, 1024, { format: THREE.RGBFormat });
	const warehouseCamera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.01, 1000);

	export function startApplication(startTime: number) {
		document.getElementById("btnRestartSimulation").onclick = () => {
			if (socket && socket.readyState == socket.OPEN) {
				socket.send(JSON.stringify({
					command: "restartSimulation",
				}));
			}
		};

		document.getElementById("checkboxHappyMode").addEventListener("click", () => {
			NetworkObject3D.happyMode = !NetworkObject3D.happyMode;
		});

		Input.keyPressed.addListener((key) => {
			if (!Input.getKeyDown("Control"))
				return;

			if (key == "y") {
				const uiContainer = document.getElementById("uiContainer");				
				uiContainer.classList.toggle("invisible");

				onWindowResize();
			}
		}, undefined);

		initalizeCallbacks();
		initializeRenderer();
		loadScene();
		setupWebsocket();
	
		let lastTime = startTime;
	
		function render(time: number): void {
			const dt = (time - lastTime) / 1000;
			lastTime = time;

			frameProgress = Math.min(frameProgress + (dt * 4), 1);

			player.update(dt);

			networkObjects.forEach((networkObject) => {
				networkObject.update(dt, frameProgress);
			});

			composer.render(); 
			/** @ts-ignore */
			renderer.setRenderTarget(renderTarget)
			renderer.render(scene, warehouseCamera);

			Input.update();

			requestAnimationFrame(render);
		}
	
		requestAnimationFrame(render);
	}

	function initalizeCallbacks() {
		window.addEventListener("resize", onWindowResize, false);
	}

	function initializeRenderer() {
		onWindowResize();

		renderer.shadowMap.enabled = true;
		renderer.shadowMap.type = PCFSoftShadowMap; 	
		
		composer.addPass(new RenderPass(scene, player.camera));
		const saoPass = new SAOPass(scene, player.camera, false);
		composer.addPass(saoPass);

		saoPass.params.saoBias = .04
    saoPass.params.saoIntensity = .002
    saoPass.params.saoScale = 2 
    saoPass.params.saoKernelRadius = 20
    saoPass.params.saoBlurRadius = 2
		saoPass.params.saoMinResolution = 0
		
		const gammaCorrection = new ShaderPass( GammaCorrectionShader );
		composer.addPass(gammaCorrection);
	}

	function setupWebsocket() {
		socket = new WebSocket("ws://" + window.location.hostname + ":8080/connectToSimulation");

		socket.addEventListener("open", (event) => {
			document.getElementById("websocketStatus").innerHTML = "CONNECTED";
		})

		socket.addEventListener("error", (event) => {
			
		});

		socket.addEventListener("close", (event) => {
			document.getElementById("websocketStatus").innerHTML = "DISCONNECTED";

			networkObjects.forEach((networkObject) => {
				networkObject.detach(scene);
			});

			setTimeout(() => {
				setupWebsocket();
			}, 500);
		});

		socket.addEventListener("message", (event) => {
			const serverCommand: {
				serverCommandType: "CREATE" | "UPDATE" | "DESTROY",
				simulationObjectDTOs: Array<NetworkObjectDTO>
			} = JSON.parse(event.data);

			if (serverCommand.serverCommandType == "CREATE" || serverCommand.serverCommandType == "UPDATE") {
				frameProgress = 0; 
				networkObjects.forEach((networkObject) => {
					networkObject.updateNetwork();
				});

				serverCommand.simulationObjectDTOs.forEach((dto) => {
					let networkObject = networkObjects.get(dto.UUID);
					
					if (!networkObject) {
						networkObject = DTOMapper.mapDTOToNetworkObject(dto);
	
						if (networkObject) {
							networkObjects.set(dto.UUID, networkObject);
							networkObject.attach(scene);
						}
					}
	
					if (networkObject)
						networkObject.updateFromDTO(dto);
				});
			} else if (serverCommand.serverCommandType == "DESTROY") {
				serverCommand.simulationObjectDTOs.forEach((dto) => {
					let networkObject = networkObjects.get(dto.UUID);

					if (networkObject) {
						networkObjects.delete(dto.UUID);
						networkObject.detach(scene);	
					}
				});
			}
		});
	}

	function loadScene() {
		warehouseCamera.position.set(0, 2, 0);
		warehouseCamera.lookAt(0, 0, - 3);

		scene.add(player);
	
		var terrain: Object3D;
	
		const loader = new GLTFLoader();
		loader.load(
			"/public/terrain.glb",
			function (gltf) {
				terrain = gltf.scene;
				terrain.castShadow = true;
				terrain.receiveShadow = true;

				gltf.scene.traverse((object) => {
					object.receiveShadow = true;
					object.castShadow = true;
				})

				scene.add(terrain);
			},
		);

		Loaders.CubeTextureLoader.load([
			'/public/Textures/Skybox/Side.png',
			'/public/Textures/Skybox/Side.png',
			'/public/Textures/Skybox/Top.png',
			'/public/Textures/Skybox/Bottom.png',
			'/public/Textures/Skybox/Side.png',
			'/public/Textures/Skybox/Side.png',
		], (texture) => {
			scene.background = texture;
		}, () => {}, (error) => {
			console.log(error);
		});
	
		const light2 = new THREE.DirectionalLight(0xffffff, 0.3);
		light2.position.set(20, 20, 20);
	
		const light = new THREE.DirectionalLight(0xffffff, 1);
		light.position.set(20, 20, 20);
		light.castShadow = true;
		light.shadow.mapSize.width = 8192;
		light.shadow.mapSize.height = 8192;
		light.shadow.camera.near = 0.1;
		light.shadow.camera.far = 1000;
		light.shadow.camera.left = -100;
		light.shadow.camera.right = 100;
		light.shadow.camera.top = 100;
		light.shadow.camera.bottom = -100;
		light.shadow.normalBias = 0.2;
	
		scene.add(light);
		scene.add(light2);
	
		const ambientLight = new THREE.AmbientLight(0xffffff, 0.4);
		scene.add(ambientLight);

		const planelikeGeometry = new THREE.PlaneGeometry( 1.5, 1.5);
		/** @ts-ignore */
		const plane = new THREE.Mesh( planelikeGeometry, new THREE.MeshBasicMaterial( { map: renderTarget } ) );
		plane.position.set(3.125, 1.4, -3.75);
		scene.add(plane);
	}

	function onWindowResize() {
		const canvas = renderer.domElement;

		const width = canvas.clientWidth;
		const height = canvas.clientHeight;

		if (canvas.width !== width ||canvas.height !== height) {
			renderer.setSize(width, height, false);
			composer.setSize(width, height);

			player.camera.aspect = width / height;
			player.camera.updateProjectionMatrix();
		}
	}
}