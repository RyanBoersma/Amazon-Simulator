package com.nhlstenden.amazonsimulatie.services;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.base.TruckStrategies.TruckIngoingStrategy;
import com.nhlstenden.amazonsimulatie.base.TruckStrategies.TruckStrategy;
import com.nhlstenden.amazonsimulatie.models.Road;
import com.nhlstenden.amazonsimulatie.models.StorageUnit;
import com.nhlstenden.amazonsimulatie.models.Truck;

public class RoadService extends Service<Road> {
	public RoadService(MapState mapState) {
		super(mapState);
	}

	@Override
	public Set<Road> tick(Set<Road> roads) {
		for (Road road : roads) {
			Truck truck = road.getTruck();

			if (truck == null) {
				truck = new Truck(road);
				road.setTruck(truck);
				
				mapState.registerTruck(truck);
			}

			if (road.isIdle()) {
				Random random = new Random();

				if (random.nextDouble() > 0.925f) {
					List<StorageUnit> sources = new ArrayList<StorageUnit>();
					List<StorageUnit> sinks = new ArrayList<StorageUnit>();

					int toSource = Math.min(2 + random.nextInt(5), mapState.getStorePlacesLeft());
					for (int i = 0; i < toSource; i++)
						sources.add(new StorageUnit());
					mapState.setStorePlacesLeft(mapState.getStorePlacesLeft() - toSource);

					int toSink = Math.min(2 + random.nextInt(5), mapState.getStored().size());
					for (int i = 0; i < toSink; i++) {
						int index = random.nextInt(mapState.getStored().size());
						Iterator<StorageUnit> iter = mapState.getStored().iterator();
						for (int j = 0; j < index; j++)
							iter.next();

						StorageUnit storageUnit = iter.next();
						sinks.add(storageUnit);

						mapState.getStored().remove(storageUnit);
					}

					truck.clearSupplies();
					truck.addSources(sources);
					truck.addSinks(sinks);

					TruckStrategy strategy = new TruckIngoingStrategy(road);
					truck.setStrategy(strategy);
				}
			}
		}
		
		return roads;
	}
}
