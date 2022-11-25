package com.nhlstenden.amazonsimulatie.views;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.nhlstenden.amazonsimulatie.base.ClientCommand;
import com.nhlstenden.amazonsimulatie.base.ServerCommand;
import com.nhlstenden.amazonsimulatie.base.ServerCommand.ServerCommandType;
import com.nhlstenden.amazonsimulatie.controllers.SimulationController;
import com.nhlstenden.amazonsimulatie.dto.*;
import com.nhlstenden.amazonsimulatie.models.*;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public class DefaultWebSocketView implements SimulationController.Listener {
	private final WebSocketSession session;
	private final SimulationController simulationController;

	private final DTOMapper dtoMapper;

	public DefaultWebSocketView(WebSocketSession session, SimulationController simulationController) {
		this.session = session;
		this.simulationController = simulationController;

		dtoMapper = new DTOMapper();

		simulationController.addListener(this);
		start(simulationController.getAllNetworkObjects());
	}

	private void start(Set<NetworkObject> models) {
		Set<NetworkObject> networkObjects = simulationController.getAllNetworkObjects();

		Set<NetworkObjectDTO> dtos = dtoMapper.mapNetworkObjects(networkObjects);
		sendDTOPackage(ServerCommandType.CREATE, dtos);
	}

	@Override
	public void onReset() {
		Set<NetworkObject> networkObjects = simulationController.getAllNetworkObjects();

		Set<NetworkObjectDTO> dtos = dtoMapper.mapNetworkObjects(networkObjects);
		sendDTOPackage(ServerCommandType.DESTROY, dtos);
	}

	@Override
	public void onNetworkObjectsChanged(Set<NetworkObject> changedNetworkObjects) {
		Set<NetworkObjectDTO> dtos = dtoMapper.mapNetworkObjects(changedNetworkObjects);
		sendDTOPackage(ServerCommandType.UPDATE, dtos);
	}

	private void sendDTOPackage(ServerCommandType serverCommandType, Set<NetworkObjectDTO> dtos) {
		try {
			ServerCommand serverCommand = new ServerCommand(serverCommandType, dtos);
			Gson gson = new Gson();
			String str = gson.toJson(serverCommand);
			
			if (this.session.isOpen())
				this.session.sendMessage(new TextMessage(str));
		} catch (Exception e) {
			// System.out.println(e.getStackTrace());
		}
	}

	public void executeClientCommand(ClientCommand clientCommand) {
		if (clientCommand.getCommand().equals("restartSimulation")) {
			simulationController.reset();
			simulationController.start();
		}
	}

	public void close() {
		simulationController.removeListener(this);
	}

	private class DTOMapper {
		private Map<Class<?>, Class<?>> dtos = new HashMap<>();

		public DTOMapper() {
			this.dtos.put(NetworkObject.class, NetworkObjectDTO.class);
			this.dtos.put(SimulationStatus.class, SimulationStatusDTO.class);
			this.dtos.put(Object3D.class, NetworkObject3DDTO.class);
			this.dtos.put(Robot.class, RobotDTO.class);
			this.dtos.put(StorageUnit.class, StorageUnitDTO.class);
			this.dtos.put(Truck.class, TruckDTO.class);
		}

		public NetworkObjectDTO mapNetworkObject(NetworkObject networkObject) {
			Class<?> type = networkObject.getClass();
			Class<?> cls = this.dtos.get(type);
			
			try {
				NetworkObjectDTO dto = (NetworkObjectDTO) cls.getConstructor(type).newInstance(networkObject);
				return dto;
			} catch (Exception e) {
				e.printStackTrace();
			}

			return null;
		}

		public Set<NetworkObjectDTO> mapNetworkObjects(Set<NetworkObject> networkObjects) {
			Set<NetworkObjectDTO> dtos = new HashSet<>();
			
			for (NetworkObject networkObject : networkObjects)
				dtos.add(mapNetworkObject(networkObject));

			return dtos;
		}
	}
}