package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.nhlstenden.amazonsimulatie.base.GraphVertex.GraphVertexFacing;
import com.nhlstenden.amazonsimulatie.base.TruckStrategies.TruckStrategy;

public class Truck extends VertexAlignedObject {
	private final UUID uuid;

	private TruckStrategy strategy;

	private final List<StorageUnit> sources;
	private final List<StorageUnit> sinks;

	public Truck(Road road) {
		super(road.getIngoingPath().getEndVertex(), GraphVertexFacing.UP);

		this.sources = new ArrayList<>();
		this.sinks = new ArrayList<>();

		uuid = UUID.randomUUID();
	}

	/**
	 * Gets the strategy
	 * @return strategy
	 */
	public TruckStrategy getStrategy() {
		return strategy;
	}

	/**
	 * Sets the strategy
	 * @param strategy
	 */
	public void setStrategy(TruckStrategy strategy) {
		this.strategy = strategy;
	}

	@Override
	public String getType() {
		return Truck.class.getSimpleName();
	}

	/**
	 * Clears the sources and sinks
	 */
	public void clearSupplies() {
		this.sources.clear();
		this.sinks.clear();
	}

	/**
	 * Adds sources
	 * @param sources
	 */
	public void addSources(List<StorageUnit> sources) {
		for (StorageUnit source : sources)
			this.sources.add(source);
	}

	/**
	 * Adds sinks
	 * @param sinks
	 */
	public void addSinks(List<StorageUnit> sinks) {
		for (StorageUnit sink : sinks)
			this.sinks.add(sink);
	}

	/**
	 * Returns sources
	 * @return
	 */
	public List<StorageUnit> getSources() {
		return sources;
	}

	/**
	 * Returns sinks
	 * @return
	 */
	public List<StorageUnit> getSinks() {
		return sinks;
	}

	@Override
	public String getUUID() {
		return uuid.toString();
	}
}