import { Vector2 } from "three";
import Reactor from "./Reactor";

enum KeyState {
	Pressed,
	Down,
	Released,
	Up,
}

export namespace Input {
	const keyStates: Map<string, KeyState> = new Map();

	let mouseAttached: boolean = false;
	const mouseMovement: Vector2 = new Vector2(0, 0);

	export const keyPressed = new Reactor<string>();
	export const keyReleased = new Reactor<string>();

	export function getKey(key: string) {
		const keystate = keyStates.get(key);
		return keystate == KeyState.Pressed || keystate == KeyState.Down;
	}

	export function getKeyPressed(key: string) {
		return keyStates.get(key) == KeyState.Pressed;
	}

	export function getKeyDown(key: string) {
		return keyStates.get(key) == KeyState.Down;
	}

	export function getKeyReleased(key: string) {
		return keyStates.get(key) == KeyState.Released;
	}

	export function getKeyUp(key: string) {
		return keyStates.get(key) == KeyState.Up;
	}

	export function isMouseAttached(): boolean {
		return mouseAttached;
	}

	export function getMouseMovement(): Vector2 {
		return mouseMovement;
	}

	export function update() {
		keyStates.forEach((value, key) => {
			if (value == KeyState.Pressed)
				keyStates.set(key, KeyState.Down);

			if (value == KeyState.Released)
				keyStates.set(key, KeyState.Up);
		})

		mouseMovement.set(0, 0);
	}

	function onKeyPressed(event: KeyboardEvent) : void {
		if (event.repeat)
			return;

		if (event.isComposing)
			return;

		keyStates.set(event.key, KeyState.Pressed);
		keyPressed.invoke(event.key);
	}

	function onKeyReleased(event: KeyboardEvent) : void {
		if (event.repeat)
			return;

		if (event.isComposing)
			return;

		keyStates.delete(event.key);
		keyReleased.invoke(event.key);
	}

	function onMouseDown(event: MouseEvent): void {
		if (event.button == 0)
			mouseAttached = true;
	}

	function onMouseUp(event: MouseEvent): void { 
		if (event.button == 0)
			mouseAttached = false;
	}

	function onMouseMove(event: MouseEvent): void {
		if (mouseAttached)
			mouseMovement.set(event.movementX, event.movementY);
	}

	window.addEventListener("keydown", onKeyPressed, false);
	window.addEventListener("keyup", onKeyReleased, false);

	window.addEventListener("mousedown", onMouseDown, false);
	window.addEventListener("mouseup", onMouseUp, false);
	window.addEventListener("mousemove", onMouseMove, false);
}