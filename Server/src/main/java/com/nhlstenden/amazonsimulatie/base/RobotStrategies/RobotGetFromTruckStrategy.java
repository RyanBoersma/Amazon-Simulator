package com.nhlstenden.amazonsimulatie.base.RobotStrategies;

import com.nhlstenden.amazonsimulatie.base.CBS;
import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.models.Road;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.StorageUnit;

public class RobotGetFromTruckStrategy implements RobotStrategy {
	private CBS cbs;
	private MapState mapState;
	private Road road;
	private StorageUnit storageUnit;

	private int waitFrames = 1;

	public RobotGetFromTruckStrategy(MapState mapState, CBS cbs, Robot robot) {
		this.cbs = cbs;
		this.mapState = mapState;

		for (Road road : mapState.getSourcingRoads()) {
			for (StorageUnit storageUnit : road.getAvailableSources()) {
				this.storageUnit = storageUnit;

				GraphVertex targetVertex = road.getVertex();
				robot.setTargetVertex(targetVertex);

				this.road = road;
				
				break;
			}

			if (this.storageUnit != null)
				break;
		}

		road.reserveSource(this.storageUnit);
	}

	@Override
	public void tick(Robot robot) {
		Path path = robot.getPath();

		if (path != null && path.getDepth() > 0) {
			robot.setVertex(path.getVertices().get(0));
			robot.setBattery(robot.getBattery() - 1);
		}

		if (robot.hasReachedTarget()) {
			if (waitFrames == 0) {
				road.completeSource(storageUnit);

				storageUnit.setRobot(robot);
				robot.setStorageUnit(storageUnit);

				mapState.registerStorageUnit(storageUnit);

				RobotStrategy strategy = new RobotPutIntoStorageStrategy(mapState, cbs, robot);
				robot.setStrategy(strategy);
			} else {
				waitFrames--;
			}
		}
	}
}