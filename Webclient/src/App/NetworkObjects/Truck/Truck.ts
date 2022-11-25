import NetworkObject3D from "../NetworkObject3D/NetworkObject3D";
import { TruckDTO } from "./TruckDTO";
import TruckModel from "./TruckModel";

export default class Truck extends NetworkObject3D {
	constructor() {
		super();

		TruckModel.then((model) => {
			this.model.add(model.clone());
		});
	}

	updateFromDTO(dto: TruckDTO): void {
		super.updateFromDTO(dto);
	}
}