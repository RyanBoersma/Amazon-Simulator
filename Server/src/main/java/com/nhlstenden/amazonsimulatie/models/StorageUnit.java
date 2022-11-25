package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

public class StorageUnit extends VertexAlignedObject {
	private final UUID uuid;

	private Robot robot;

	public StorageUnit() {
		super(null, null);

		uuid = UUID.randomUUID();
	}

	/**
	 * Returns the carrying robot
	 * @return robot
	 */
	public Robot getRobot() {
		return robot;
	}

	/**
	 * Sets the carrying robot
	 * @param robot
	 */
	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	@Override
	public String getUUID() {
		return this.uuid.toString();
	}

	@Override
	public String getType() {
		return StorageUnit.class.getSimpleName();
	}
}