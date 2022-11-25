type Handler<T> = (this: any, arg: T) => void

export type EventHandle = {}

export default class Reactor<T = void> {
	private listeners: Array<{func: (arg: T) => void, target: any}>;

	constructor() {
		this.listeners = new Array();
	}

	public addListener<H extends Handler<T>>(listener: (arg: T) => void, target: ThisParameterType<H>): EventHandle {
		const handle = {
			func: listener, 
			target: this
		}
		
		this.listeners.push(handle);

		return handle;
	}

	public removeListener(handle: EventHandle): boolean {
		const index = this.listeners.indexOf(handle as {func: (args: T) => void, target: any});

		if (index == -1)
			return false;

		this.listeners.splice(index, 1);

		return true;
	}

	public invoke(arg: T): void {
		this.listeners.forEach((listener) => {
			listener.func.call(listener.target, arg);
		})
	}
}