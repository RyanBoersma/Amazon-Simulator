package com.nhlstenden.amazonsimulatie.base;

import java.util.Set;

import com.nhlstenden.amazonsimulatie.dto.NetworkObjectDTO;

public class ServerCommand {
	public enum ServerCommandType {
		CREATE,
		UPDATE,
		DESTROY,
	}

	private ServerCommandType serverCommandType;
	private Set<NetworkObjectDTO> simulationObjectDTOs;

	public ServerCommand(ServerCommandType serverCommandType, Set<NetworkObjectDTO> simulationObjectDTOs) {
		this.serverCommandType = serverCommandType;
		this.simulationObjectDTOs = simulationObjectDTOs;
	}
}
