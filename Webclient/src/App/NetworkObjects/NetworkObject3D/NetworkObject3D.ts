import { Object3D, Scene } from "three";
import NetworkObject from "../NetworkObject/NetworkObject";
import { NetworkObject3DDTO } from "./NetworkObject3DDTO";
const lerp = require('lerp')

export default class NetworkObject3D extends NetworkObject {
	protected model: Object3D;

	private oldX: number;
	private oldY: number;
	private oldZ: number;

	private newX: number;
	private newY: number;
	private newZ: number;

	public static happyMode = false;

	constructor() {
		super();

		this.model = new Object3D();
		
		this.model.castShadow = true;
		this.model.receiveShadow = true;
	}

	public updateFromDTO(dto: NetworkObject3DDTO) {
		super.updateFromDTO(dto);

		this.oldX = this.newX;
		this.oldY = this.newY; 
		this.oldZ = this.newZ;

		this.newX = dto.X;
		this.newY = dto.Y;
		this.newZ = dto.Z;

		this.model.rotation.x = dto.rotationX;
		this.model.rotation.y = dto.rotationY;
		this.model.rotation.z = dto.rotationZ;
	};

	public attach(scene: Scene): void {
		scene.add(this.model);
	}

	public detach(scene: Scene): void {
		scene.remove(this.model);
	}

	public update(dt: number, frameProgress: number) {
		this.model.position.x = lerp(this.oldX, this.newX, frameProgress);		
		
		if (!NetworkObject3D.happyMode)
			this.model.position.y = lerp(this.oldY, this.newY, frameProgress);
		else
			this.model.position.y = lerp(this.oldY + (Math.sin(frameProgress * Math.PI) / 4), this.newY, frameProgress);
		
			this.model.position.z = lerp(this.oldZ, this.newZ, frameProgress);
	}
}