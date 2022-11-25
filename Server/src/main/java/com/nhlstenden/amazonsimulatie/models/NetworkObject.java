package com.nhlstenden.amazonsimulatie.models;

public interface NetworkObject extends Model {
	/**
	 * Returns the string representation of the networkobject's UUID
	 * @return string representation of the networkobject's UUID
	 */
	public abstract String getUUID();

	/**
	 * Returns the networkobject's type
	 * @return the networkobject's type
	 */
	public abstract String getType();
}
