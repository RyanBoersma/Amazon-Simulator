package com.nhlstenden.amazonsimulatie.services;

import java.util.Set;

import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.models.SimulationStatus;

public class SimulationStatusService extends Service<SimulationStatus> {
	public SimulationStatusService(MapState mapState) {
		super(mapState);
	}

	@Override
	public Set<SimulationStatus> tick(Set<SimulationStatus> models) {
		for (SimulationStatus simulationStatus : models) {
			simulationStatus.setFrame(simulationStatus.getFrame() + 1);
		}
		
		return models;
	}
}
