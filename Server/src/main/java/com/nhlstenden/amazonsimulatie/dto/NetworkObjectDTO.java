package com.nhlstenden.amazonsimulatie.dto;

import com.nhlstenden.amazonsimulatie.models.NetworkObject;

public class NetworkObjectDTO {
	private final String UUID;
	private final String type;

	public NetworkObjectDTO(NetworkObject networkObject) {
		this.UUID = networkObject.getUUID();
		this.type = networkObject.getType();
	}
}
