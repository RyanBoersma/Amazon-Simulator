import { GLTFLoader as _GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import { CubeTextureLoader as _CubeTextureLoader } from "three";

export namespace Loaders {
	export const GLTFLoader = new _GLTFLoader();
	export const CubeTextureLoader = new _CubeTextureLoader;
}