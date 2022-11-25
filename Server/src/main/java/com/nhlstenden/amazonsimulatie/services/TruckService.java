package com.nhlstenden.amazonsimulatie.services;

import java.util.HashSet;
import java.util.Set;

import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.base.TruckStrategies.TruckStrategy;
import com.nhlstenden.amazonsimulatie.models.Truck;

public class TruckService extends Service<Truck> {
	private Set<Truck> changedTrucks;
	
	public TruckService(MapState mapState) {
		super(mapState);

		changedTrucks = new HashSet<Truck>();
	}

	@Override
	public Set<Truck> tick(Set<Truck> trucks) {
		changedTrucks.clear();

		for (Truck truck : trucks) {
			TruckStrategy strategy = truck.getStrategy();

			if (strategy == null)
				continue;
			
			strategy.tick(truck);
			changedTrucks.add(truck);
		}

		return changedTrucks;
	}
}
