package com.nhlstenden.amazonsimulatie.base.TruckStrategies;

import com.nhlstenden.amazonsimulatie.models.Truck;

public interface TruckStrategy {
	/**
	 * Executes a strategy for a truck
	 * @param truck to execute strategy on
	 */
	public void tick(Truck truck);
}
