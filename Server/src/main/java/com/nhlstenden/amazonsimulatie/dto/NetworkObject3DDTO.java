package com.nhlstenden.amazonsimulatie.dto;

import com.nhlstenden.amazonsimulatie.models.Object3D;

public class NetworkObject3DDTO extends NetworkObjectDTO {
	private final double X;
	private final double Y;
	private final double Z;

	private final double rotationX;
	private final double rotationY;
	private final double rotationZ;

	public NetworkObject3DDTO(Object3D networkObject3D) {
		super(networkObject3D);

		this.X = networkObject3D.getX();
		this.Y = networkObject3D.getY();
		this.Z = networkObject3D.getZ();

		this.rotationX = networkObject3D.getRotationX();
		this.rotationY = networkObject3D.getRotationY();
		this.rotationZ = networkObject3D.getRotationZ();
	}
}
