import { NetworkObjectDTO } from "../NetworkObject/NetworkObjectDTO";

export type SimulationStatusDTO = {
	frame: number;
} & NetworkObjectDTO;