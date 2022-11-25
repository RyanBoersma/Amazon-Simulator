package com.nhlstenden.amazonsimulatie.dto;

import com.nhlstenden.amazonsimulatie.models.SimulationStatus;

public class SimulationStatusDTO extends NetworkObjectDTO {
	private final double frame;

	public SimulationStatusDTO(SimulationStatus simulationStatus) {
		super(simulationStatus);
		
		frame = simulationStatus.getFrame();
	}
}
