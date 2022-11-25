package com.nhlstenden.amazonsimulatie.controllers;

import java.util.*;

import com.nhlstenden.amazonsimulatie.base.*;
import com.nhlstenden.amazonsimulatie.base.GraphVertex.GraphVertexFacing;
import com.nhlstenden.amazonsimulatie.models.*;
import com.nhlstenden.amazonsimulatie.services.*;

public class SimulationController {
	private final Set<Listener> listeners;

	private RobotService robotService;
	private StorageUnitService storageUnitService;
	private TruckService truckService;
	private SimulationStatusService simulationStatusService;
	private RoadService roadService;

	private MapState mapState;

	public SimulationController(double targetTickRate) {
		super();

		listeners = new HashSet<>();
	}

	public void start() {
		MapDataLoader mapDataLoader = new MapDataLoader();
		MapData mapData = mapDataLoader.load();

		mapState = new MapState(mapData);

		robotService = new RobotService(mapState);
		storageUnitService = new StorageUnitService(mapState);
		truckService = new TruckService(mapState);
		simulationStatusService = new SimulationStatusService(mapState);
		roadService = new RoadService(mapState);

		mapState.registerSimulationStatus(new SimulationStatus());

		for (int i = 0; i < 10; i++) {
			Robot robot = new Robot(mapData.getChargeVertices().get(i), GraphVertexFacing.RIGHT);
			mapState.registerRobot(robot);
		}
	}

	/**
	 * Resets the simulation
	 */
	public void reset() {
		mapState.resetModels();

		invokeReset();
	}

	/**
	 * Ticks the simulation
	 */
	public void tick() {
		Set<NetworkObject> changedNetworkObjects = new HashSet<NetworkObject>();
		
		changedNetworkObjects.addAll(robotService.tick(mapState.getRobots()));
		changedNetworkObjects.addAll(storageUnitService.tick(mapState.getStorageUnits()));
		changedNetworkObjects.addAll(truckService.tick(mapState.getTrucks()));
		changedNetworkObjects.addAll(simulationStatusService.tick(mapState.getSimulationStatuses()));
		roadService.tick(mapState.getRoads());

		invokeNetworkObjectsChanged(changedNetworkObjects);
	}

	public void addListener(Listener listener) {
		listeners.add(listener);
	}

	public void removeListener(Listener listener) {
		listeners.remove(listener);
	}

	public void invokeReset() {
		for (Listener listener : listeners)
			listener.onReset();
	}

	private void invokeNetworkObjectsChanged(Set<NetworkObject> changedNetworkObjects) {
		for (Listener listener : listeners)
			listener.onNetworkObjectsChanged(changedNetworkObjects);
	}

	public Set<NetworkObject> getAllNetworkObjects() {
		return mapState.getNetworkObjects();
	}

	public interface Listener {
		void onReset();
		void onNetworkObjectsChanged(Set<NetworkObject> changedNetworkObjects);
	}
}