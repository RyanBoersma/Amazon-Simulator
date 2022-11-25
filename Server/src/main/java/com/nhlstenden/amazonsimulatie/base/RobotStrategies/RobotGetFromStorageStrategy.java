package com.nhlstenden.amazonsimulatie.base.RobotStrategies;

import com.nhlstenden.amazonsimulatie.base.CBS;
import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.GraphVertexGroup;
import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.models.Road;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.StorageUnit;

public class RobotGetFromStorageStrategy implements RobotStrategy {
	private final MapState mapState;
	private final CBS cbs;
	private Road road;
	private StorageUnit storageUnit;

	public RobotGetFromStorageStrategy(MapState mapState, CBS cbs, Robot robot) {
		this.cbs = cbs;
		this.mapState = mapState;

		for (Road road : mapState.getSinkingRoads()) {
			for (StorageUnit storageUnit : road.getAvailableSinks()) {
				this.storageUnit = storageUnit;

				GraphVertex targetVertex = storageUnit.getVertex();
				robot.setTargetVertex(targetVertex);

				this.road = road;
				
				break;
			}

			if (this.storageUnit != null)
				break;
		}

		road.reserveSink(this.storageUnit);
	}

	@Override
	public void tick(Robot robot) {
		Path path = robot.getPath();

		if (path != null && path.getDepth() > 0) {
			robot.setVertex(path.getVertices().get(0));
			robot.setBattery(robot.getBattery() - 1);
		}

		if (robot.hasReachedTarget()) {
			storageUnit.setRobot(robot);
			robot.setStorageUnit(storageUnit);

			mapState.setStorePlacesLeft(mapState.getStorePlacesLeft()+1);

			GraphVertexGroup storageVertices = mapState.getFreeStorageVertices();
			storageVertices.free(robot.getVertex());

			RobotStrategy strategy = new RobotPutIntoTruckStrategy(mapState, cbs, robot, road, storageUnit);
			robot.setStrategy(strategy);
		}
	}
}
