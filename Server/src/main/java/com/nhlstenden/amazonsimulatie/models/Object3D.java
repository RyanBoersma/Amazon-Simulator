package com.nhlstenden.amazonsimulatie.models;

public interface Object3D extends NetworkObject {
	/**
	 * Returns the X position
	 * @return X position
	 */
	public abstract double getX();
	
	/**
	 * Returns the Y position
	 * @return Y position
	 */
	public abstract double getY();
	
	/**
	 * Returns the Z position
	 * @return Z position
	 */
	public abstract double getZ();


	/**
	 * Returns the X rotation
	 * @return X rotation
	 */
	public abstract double getRotationX();
	
	/**
	 * Returns the Y rotation
	 * @return Y rotation
	 */
	public abstract double getRotationY();
	
	/**
	 * Returns the Z rotation
	 * @return Z rotation
	 */
	public abstract double getRotationZ();
}