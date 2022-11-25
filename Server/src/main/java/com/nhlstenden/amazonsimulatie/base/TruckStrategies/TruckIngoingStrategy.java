package com.nhlstenden.amazonsimulatie.base.TruckStrategies;

import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.models.Road;
import com.nhlstenden.amazonsimulatie.models.Truck;

public class TruckIngoingStrategy implements TruckStrategy {
	private final Road road;
	private final Path path;
	private int pathIndex;

	public TruckIngoingStrategy(Road road) {
		this.road = road;
		this.path = road.getIngoingPath();

		this.road.setIdle(false);

		this.pathIndex = 0;
	}

	@Override
	public void tick(Truck truck) {
		GraphVertex vertex = path.getVertices().get(pathIndex);
		truck.setVertex(vertex);
		truck.setVertexFacing(vertex.getVertexFacing());

		if (vertex != path.getEndVertex())
			pathIndex++;
		else {
			TruckStrategy strategy = new TruckAwaitStrategy(road);
			truck.setStrategy(strategy);
		}
	}
}
