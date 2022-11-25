import { Object3D } from "three/src/core/Object3D";
import { Loaders } from "../../../Lib/Loaders";

const StorageUnitModel = new Promise<Object3D>((resolve, reject) => {
	Loaders.GLTFLoader.load("/public/storageUnit.glb", 
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
		console.error("Couldn't load storageUnit model: " + error);
		reject(error);
	});
});

export default StorageUnitModel;