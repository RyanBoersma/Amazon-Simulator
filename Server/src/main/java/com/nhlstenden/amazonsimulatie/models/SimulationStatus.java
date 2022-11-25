package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

public class SimulationStatus implements NetworkObject {
	private final UUID uuid;

	private double frame;

	public SimulationStatus() {
		uuid = UUID.randomUUID();

		setFrame(0);
	}

	/**
	 * Gets the frame
	 * @return frame
	 */
	public double getFrame() {
		return frame;
	}

	/**
	 * Sets the frame
	 * @param frame
	 */
	public void setFrame(double frame) {
		this.frame = frame;
	}

	@Override
	public String getUUID() {
		return uuid.toString();
	}

	@Override
	public String getType() {
		return SimulationStatus.class.getSimpleName();
	}
}
