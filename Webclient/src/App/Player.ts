import { Object3D, PerspectiveCamera, Vector3 } from "three";
import { Input } from "../Lib/Input";

export default class Player extends Object3D {
	private _camera: PerspectiveCamera;
	get camera(): PerspectiveCamera { 
		return this._camera;
	}

	private velocity: Vector3 = new Vector3(0, 0, 0);
	private speed: number = 20;

	private pitch: number = 0;
	private yaw: number = 0;

	constructor(position: Vector3) {
		super();

		this.position.copy(position);
		
		this._camera = new PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.01, 1000);
		this._camera.lookAt(0, 0, 0);
		this.add(this._camera);
	}
	
	update(dt: number) {
		const input = this.processInput();

		this.applyMovementInput(input.movementInput, dt);
		this.applyRotationInput(input.rotationInput);
		this.applyVelocity(dt);
		this.applyFriction();
	}	

	private processInput(): {movementInput: Vector3, rotationInput: Vector3} {
		const movement = new Vector3(0, 0, 0); 
		const rotation = new Vector3(0, 0, 0);

		const forward = new Vector3( 
			Math.sin(this.yaw + Math.PI),
			0,
			Math.cos(this.yaw + Math.PI)
		);

		const right = new Vector3(
			Math.sin(this.yaw + Math.PI * 0.5),
			0,
			Math.cos(this.yaw + Math.PI * 0.5)
		);

		// Ty
		if (Input.getKey(" "))
			movement.y++;
		if (Input.getKey("Shift"))
			movement.y--;

		// Tz
		if (Input.getKey("s"))
			movement.sub(forward);
		if (Input.getKey("w"))
			movement.add(forward);

		// Tx
		if (Input.getKey("d"))
			movement.add(right);
		if (Input.getKey("a"))
			movement.sub(right);

		const mouseMovement = Input.getMouseMovement();
		
		rotation.x += mouseMovement.x / 200;
		rotation.y += mouseMovement.y / 200;

		return {
			movementInput: movement, 
			rotationInput: rotation
		};
	}

	private applyMovementInput(input: Vector3, dt: number) {
		const movement = input.clone().multiplyScalar(this.speed);
		const deltaMovement = movement.multiplyScalar(dt);

		this.velocity.add(deltaMovement);
	}

	private applyRotationInput(input: Vector3) {
		this.pitch -= input.y;
		this.yaw -= input.x; 
	
		this.rotation.set(0, 0, 0);
		this.rotateY(this.yaw);
		this.rotateX(this.pitch);
	}

	private applyVelocity(dt: number) {
		const deltaVelocity = this.velocity.clone().multiplyScalar(dt);

		this.position.add(deltaVelocity);
	}

	private applyFriction() {
		this.velocity.multiplyScalar(0.95);
	}
}