import NetworkObject from "../NetworkObject/NetworkObject";
import { SimulationStatusDTO } from "./SimulationStatusDTO";

export default class SimulationStatus extends NetworkObject {
	private _frame: number;
	public get frame() {
		return this._frame;
	}
	
	updateFromDTO(dto: SimulationStatusDTO): void {
		super.updateFromDTO(dto);

		this._frame = dto.frame;
	}
}