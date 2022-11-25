import { Scene } from "three/src/scenes/Scene";
import { NetworkObjectDTO } from "./NetworkObjectDTO";

export default class NetworkObject {
	private _UUID: string;
	public get UUID() {
		return this._UUID;
	}

	public attach(scene: Scene): void { }
	public detach(scene: Scene): void { }
	public updateNetwork() : void { }
	public update(deltaTime: number, frameProgress: number) : void { }

	public updateFromDTO(dto: NetworkObjectDTO) {
		this._UUID = dto.UUID;
	};
}