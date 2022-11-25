import { App } from "./App";
import NetworkObject from "./NetworkObjects/NetworkObject/NetworkObject";
import { NetworkObjectDTO } from "./NetworkObjects/NetworkObject/NetworkObjectDTO";
import Robot from "./NetworkObjects/Robot/Robot";
import SimulationStatus from "./NetworkObjects/SimulationStatus/SimulationStatus";
import StorageUnit from "./NetworkObjects/StorageUnit/StorageUnit";
import Truck from "./NetworkObjects/Truck/Truck";

type ModelConstructor = { new(): NetworkObject }

export namespace DTOMapper {
	const dtoMapper: Map<string, ModelConstructor> = new Map();

	dtoMapper.set("Robot", Robot);
	dtoMapper.set("SimulationStatus", SimulationStatus);
	dtoMapper.set("StorageUnit", StorageUnit);
	dtoMapper.set("Truck", Truck);

	export function mapDTOToNetworkObject(dto: NetworkObjectDTO): NetworkObject | undefined {
		const modelConstructor = dtoMapper.get(dto.type);
		
		if (modelConstructor) {
			const networkObject = new modelConstructor();
			networkObject.updateFromDTO(dto);

			return networkObject;
		} else {
			console.warn(`Server sent unimplemented networkobject type: ${dto.type}`);

			return undefined;
		}
	}
}