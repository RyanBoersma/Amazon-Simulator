package com.nhlstenden.amazonsimulatie.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.Path;

public class Road implements Model {
	private final GraphVertex vertex;
	private final Path ingoingPath;
	private final Path outgoingPath;
	private Truck truck;
	private boolean idle;

	private List<StorageUnit> sources;
	private List<StorageUnit> sinks;

	private Set<StorageUnit> availableSources;
	private Set<StorageUnit> availableSinks;

	private Set<StorageUnit> completedSources;
	private Set<StorageUnit> completedSinks;

	public Road(GraphVertex vertex, Path ingoingPath, Path outgoingPath) {
		this.vertex = vertex;
		this.ingoingPath = ingoingPath;
		this.outgoingPath = outgoingPath;

		sources = new ArrayList<>();
		sinks = new ArrayList<>();

		availableSources = new HashSet<>();
		availableSinks = new HashSet<>();

		completedSources = new HashSet<>();
		completedSinks = new HashSet<>();

		setIdle(true);
	}

	/**
	 * Returns if the road is idle
	 * @return true if idle, false otherwise
	 */
	public boolean isIdle() {
		return idle;
	}

	/**
	 * Sets the idle state
	 * @param idle
	 */
	public void setIdle(boolean idle) {
		this.idle = idle;
	}

	/**
	 * Returns the vertex
	 * @return the vertex
	 */
	public GraphVertex getVertex() {
		return vertex;
	}

	/**
	 * Returns the occupying truck
	 * @return occupying truck
	 */
	public Truck getTruck() {
		return truck;
	}

	/**
	 * Sets the occupying truck
	 * @param truck
	 */
	public void setTruck(Truck truck) {
		this.truck = truck;
	}

	/**
	 * Returns if the road has a truck
	 * @return true if there's a truck, false otherwise
	 */
	public boolean hasTruck() {
		return truck != null;
	}

	/**
	 * Returns the ingoing path
	 * @return ingoing path
	 */
	public Path getIngoingPath() {
		return this.ingoingPath;
	}
	
	/**
	 * Retursn the outgoing path
	 * @return outgoing path
	 */
	public Path getOutgoingPath() {
		return this.outgoingPath;
	}

	/**
	 * Supplies the road with the sources and sinks from the truck
	 */
	public void supply() {
		this.sources = new ArrayList<>(truck.getSources());
		this.sinks = new ArrayList<>(truck.getSinks());

		this.availableSources = new HashSet<>(truck.getSources());
		this.availableSinks = new HashSet<>(truck.getSinks());

		this.completedSources.clear();
		this.completedSinks.clear();
	}

	/**
	 * Returns the available sources
	 * @return set of available sources
	 */
	public Set<StorageUnit> getAvailableSources() {
		return availableSources;
	}

	/**
	 * Returns the available sinks
	 * @return set of available sinks
	 */
	public Set<StorageUnit> getAvailableSinks() {
		return availableSinks;
	}

	/**
	 * Reserves a source storageunit
	 * @param storageUnit
	 */
	public void reserveSource(StorageUnit storageUnit) {
		availableSources.remove(storageUnit);
	}

	/**
	 * Reserves a sink storageunit
	 * @param storageUnit
	 */
	public void reserveSink(StorageUnit storageUnit) {
		availableSinks.remove(storageUnit);
	}

	/**
	 * Competes a source storageunit
	 * @param storageUnit
	 */
	public void completeSource(StorageUnit storageUnit) {
		completedSources.add(storageUnit);
	}

	/**
	 * Completes a sink storageunit
	 * @param storageUnit
	 */
	public void completeSink(StorageUnit storageUnit) {
		completedSinks.add(storageUnit);
	}

	/**
	 * Returns if there's sources available
	 * @return true if there's sources available. False otherwise
	 */
	public boolean hasAvailableSources() {
		return availableSources.size() > 0;
	}

	/**
	 * Returns if there's sinks available
	 * @return true if there's sinks available. False otherwise
	 */
	public boolean hasAvailableSinks() {
		return availableSinks.size() > 0;
	}

	/**
	 * Returns if the sources are done
	 * @return true if the sources are done. False otherwise
	 */
	public boolean areSourcesDone() {
		if (truck == null)
			return true;

		return completedSources.size() == sources.size();
	}

	/**
	 * Returns if the sinks are done
	 * @return true if the sinks are done. False otherwise
	 */
	public boolean areSinksDone() {
		if (truck == null)
			return true;

		return completedSinks.size() == sinks.size();
	}

	/**
	 * Returns if both source and sinks are done
	 * @returntrue if the sources and sinks are done. False otherwise
	 */
	public boolean isDone() {
		return areSourcesDone() && areSinksDone();
	}
}
