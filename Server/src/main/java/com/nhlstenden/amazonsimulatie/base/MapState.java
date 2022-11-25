package com.nhlstenden.amazonsimulatie.base;

import java.util.*;

import com.nhlstenden.amazonsimulatie.models.*;

public class MapState {
	private final Set<Model> models;
	private final Set<NetworkObject> networkObjects;
	private final Set<Robot> robots;
	private final Set<StorageUnit> storageUnits;
	private final Set<Truck> trucks;
	private final Set<SimulationStatus> simulationStatuses;
	private final Set<Road> roads;

	private final MapData mapData;

	private final TimeExpandedGraph timeExpandedGraph;

	private final GraphVertexGroup freeStorageVertices;
	private final GraphVertexGroup freeChargeVertices;

	private int storePlacesLeft;
	private final Set<StorageUnit> stored;

	public MapState(MapData mapData) {
		this.mapData = mapData;

		timeExpandedGraph = new TimeExpandedGraph(mapData.getGraph(), 5);

		freeStorageVertices = new GraphVertexGroup(this.mapData.getStorageVertices());
		freeChargeVertices = new GraphVertexGroup(this.mapData.getChargeVertices());

		models = new HashSet<Model>();
		networkObjects = new HashSet<NetworkObject>();
		robots = new HashSet<Robot>();
		trucks = new HashSet<Truck>();
		storageUnits = new HashSet<StorageUnit>();
		simulationStatuses = new HashSet<SimulationStatus>();
		roads = new HashSet<Road>();

		this.stored = new HashSet<StorageUnit>();

		for (MapData.Road road : mapData.getRoads()) {
			Road roadModel = new Road(road.getVertex(), road.getIngoingPath(), road.getOutgoingPath());
			registerRoad(roadModel);
		}

		storePlacesLeft = 20;
	}

	public int getStorePlacesLeft() {
		return storePlacesLeft;
	}

	public void setStorePlacesLeft(int storePlacesLeft) {
		this.storePlacesLeft = storePlacesLeft;
	}

	public Set<StorageUnit> getStored() {
		return stored;
	}

	public void resetModels() {
		this.models.clear();
		this.robots.clear();
		this.storageUnits.clear();
		this.trucks.clear();
		this.simulationStatuses.clear();
	}

	public Set<Road> getRoads() {
		return roads;
	}

	public Set<SimulationStatus> getSimulationStatuses() {
		return simulationStatuses;
	}

	public Set<Truck> getTrucks() {
		return trucks;
	}

	public Set<StorageUnit> getStorageUnits() {
		return storageUnits;
	}

	public Set<Robot> getRobots() {
		return robots;
	}

	public Set<Model> getModels() {
		return models;
	}
	
	public Set<NetworkObject> getNetworkObjects() {
		return networkObjects;
	}

	public MapData getMapData() {
		return mapData;
	}

	public TimeExpandedGraph getTimeExpandedGraph() {
		return timeExpandedGraph;
	}

	public List<Road> getSourcingRoads() {
		List<Road> sourcingRoads = new ArrayList<Road>();
	
		for (Road road : roads) {
			if (!road.areSourcesDone())
				sourcingRoads.add(road);
		}

		return sourcingRoads;
	}

	public List<Road> getSinkingRoads() {
		List<Road> sinkingRoads = new ArrayList<Road>();
	
		for (Road road : roads) {
			if (!road.areSinksDone())
				sinkingRoads.add(road);
		}

		return sinkingRoads;
	}

	public boolean hasSourcingRoad() {
		for (Road road : roads) {
			if (road.hasAvailableSources())
				return true;
		}

		return false;
	}

	public boolean hasSinkingRoad() {
		for (Road road : roads) {
			if (road.hasAvailableSinks())
				return true;
		}

		return false;
	}

	public GraphVertexGroup getFreeStorageVertices() {
		return freeStorageVertices;
	}

	public GraphVertexGroup getFreeChargeVertices() {
		return freeChargeVertices;
	}

	public void registerModel(Model model) {
		models.add(model);
	}

	public void registerNetworkObject(NetworkObject networkObject) {
		registerModel(networkObject);
		networkObjects.add(networkObject);
	}

	public void registerSimulationStatus(SimulationStatus simulationStatus) {
		registerNetworkObject(simulationStatus);
		simulationStatuses.add(simulationStatus);
	}

	public void registerRobot(Robot robot) {
		registerNetworkObject(robot);
		robots.add(robot);
	}

	public void registerStorageUnit(StorageUnit storageUnit) {
		registerNetworkObject(storageUnit);
		storageUnits.add(storageUnit);
	}

	public void registerTruck(Truck truck) {
		registerNetworkObject(truck);
		trucks.add(truck);
	}

	public void registerRoad(Road road) {
		registerModel(road);
		roads.add(road);
	}
}
