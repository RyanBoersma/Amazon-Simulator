import { Object3D } from "three/src/core/Object3D";
import { Loaders } from "../../../Lib/Loaders";

const TruckModel = new Promise<Object3D>((resolve, reject) => {
	Loaders.GLTFLoader.load("/public/truck.glb", 
	async (gltf) => {
		const model = gltf.scene;

		model.traverse((object) => {
			object.receiveShadow = true;
			object.castShadow = true;
		})

		model.scale.set(0.25, 0.25, 0.25);

		model.castShadow = true;
		model.receiveShadow = true;

		resolve(model);
	}, 
	() => {}, 
	(error) => {
		console.error("Couldn't load truck model: " + error);
		reject(error);
	});
});

export default TruckModel;