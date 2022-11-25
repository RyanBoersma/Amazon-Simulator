import NetworkObject3D from "../NetworkObject3D/NetworkObject3D";
import { RobotDTO } from "./RobotDTO";
import RobotModel from "./RobotModel";

export default class Robot extends NetworkObject3D {
	constructor() {
		super();

		RobotModel.then((model) => {
			this.model.add(model.clone());
		});
	}

	updateFromDTO(dto: RobotDTO): void {
		super.updateFromDTO(dto);
	}
}