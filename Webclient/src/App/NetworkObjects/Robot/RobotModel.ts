import { Object3D } from "three";
import { Loaders } from "../../../Lib/Loaders";

const RobotModel = new Promise<Object3D>((resolve, reject) => {
	Loaders.GLTFLoader.load("/public/robot.glb", 
	async (gltf) => {
		const model = gltf.scene;

		model.traverse((object) => {
			object.receiveShadow = true;
			object.castShadow = true;
		})

		model.scale.set(0.125, 0.125, 0.125);

		model.castShadow = true;
		model.receiveShadow = true;

		resolve(model);
	}, 
	() => {}, 
	(error) => {
		console.error("Couldn't load robot model: " + error);
		reject(error);
	});
});

export default RobotModel;