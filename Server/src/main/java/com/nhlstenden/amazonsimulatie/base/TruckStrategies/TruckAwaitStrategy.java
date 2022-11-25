package com.nhlstenden.amazonsimulatie.base.TruckStrategies;

import com.nhlstenden.amazonsimulatie.models.Road;
import com.nhlstenden.amazonsimulatie.models.Truck;

public class TruckAwaitStrategy implements TruckStrategy {
	private final Road road; 
	
	public TruckAwaitStrategy(Road road) {
		this.road = road;

		road.supply();
	}

	@Override
	public void tick(Truck truck) {
		if (road.isDone()) {
			TruckStrategy strategy = new TruckOutgoingStrategy(road);
			truck.setStrategy(strategy); 
		}
	}
}
