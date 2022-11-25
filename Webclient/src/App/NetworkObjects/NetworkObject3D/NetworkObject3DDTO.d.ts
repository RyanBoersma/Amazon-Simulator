import { NetworkObjectDTO } from "../NetworkObject/NetworkObjectDTO";

export type NetworkObject3DDTO = {
	X: number;
	Y: number;
	Z: number;

	rotationX: number;
	rotationY: number;
	rotationZ: number;
} & NetworkObjectDTO