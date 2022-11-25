package com.nhlstenden.amazonsimulatie.base.TruckStrategies;

import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.models.Road;
import com.nhlstenden.amazonsimulatie.models.Truck;

public class TruckOutgoingStrategy implements TruckStrategy {
	private final Road road;
	private final Path path;
	private int pathIndex;

	public TruckOutgoingStrategy(Road road) {
		this.road = road;
		this.path = road.getOutgoingPath();
		this.pathIndex = 0;	
	}

	@Override
	public void tick(Truck truck) {
		GraphVertex vertex = path.getVertices().get(pathIndex);
		truck.setVertex(vertex);
		truck.setVertexFacing(vertex.getVertexFacing());

		if (vertex != path.getEndVertex())
			pathIndex++;
		else
			this.road.setIdle(true);
	}
}
