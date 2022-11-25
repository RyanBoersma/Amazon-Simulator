package com.nhlstenden.amazonsimulatie.models;

import java.util.UUID;

import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.base.GraphVertex.GraphVertexFacing;
import com.nhlstenden.amazonsimulatie.base.RobotStrategies.RobotStrategy;

public class Robot extends VertexAlignedObject {
	private final UUID uuid;

	private StorageUnit storageUnit;

	private final int maxBattery = 150;
	private int battery;
	private RobotStrategy strategy;
	private GraphVertex targetVertex;

	private Path path;

	public Robot(GraphVertex vertex, GraphVertexFacing vertexFacing) {
		super(vertex, vertexFacing);

		setBattery(maxBattery);

		uuid = UUID.randomUUID();
	}

	/**
	 * Returns the path
	 * @return path
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Sets the path
	 * @param path
	 */
	public void setPath(Path path) {
		this.path = path;
	}

	/**
	 * Returns if the target vertex was reached
	 * @return true if target vertex was reached. False otherwise
	 */
	public boolean hasReachedTarget() {
		return getVertex() == targetVertex;
	}

	/**
	 * Returns the target vertex
	 * @return target vertex
	 */
	public GraphVertex getTargetVertex() {
		return targetVertex;
	}

	/**
	 * Sets the targetVertex
	 * @param targetVertex
	 */
	public void setTargetVertex(GraphVertex targetVertex) {
		this.targetVertex = targetVertex;
	}

	/**
	 * Returns the carrying storageUnit
	 * @return storageUnit
	 */
	public StorageUnit getStorageUnit() {
		return storageUnit;
	}

	/**
	 * Sets the carrying storageUnit
	 * @param storageUnit
	 */
	public void setStorageUnit(StorageUnit storageUnit) {
		this.storageUnit = storageUnit;
	}

	/**
	 * Returns the strategy
	 * @return strategy
	 */
	public RobotStrategy getStrategy() {
		return strategy;
	}

	/**
	 * Sets the strategy
	 * @param strategy
	 */
	public void setStrategy(RobotStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Sets the battery
	 * @param battery
	 */
	public void setBattery(int battery) {
		this.battery = Math.max(0, Math.min(maxBattery, battery));
	}

	/**
	 * Gets the battery
	 * @return battery
	 */
	public int getBattery() {
		return battery;
	}

	/**
	 * Returns if the battery is full
	 * @return true if the battery is full. False otherwise
	 */
	public boolean isBatteryFull() {
		return battery == maxBattery;
	}

	/**
	 * Returns if the battery is empty
	 * @return true if the battery is empty. False otherwise
	 */
	public boolean isBatteryEmpty() {
		return battery == 0;
	}

	@Override
	public String getType() {
		return Robot.class.getSimpleName();
	}

	@Override
	public String getUUID() {
		return uuid.toString();
	}
}