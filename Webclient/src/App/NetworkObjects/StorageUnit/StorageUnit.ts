import { Object3D } from "three";
import { Loaders } from "../../../Lib/Loaders";
import NetworkObject3D from "../NetworkObject3D/NetworkObject3D";
import { StorageUnitDTO } from "./StorageUnitDTO";
import StorageUnitModel from "./StorageUnitModel";

export default class StorageUnit extends NetworkObject3D {
	constructor() {
		super();

		StorageUnitModel.then((model) => {
			this.model.add(model.clone());
		});
	}

	updateFromDTO(dto: StorageUnitDTO): void {
		super.updateFromDTO(dto);
	}
}