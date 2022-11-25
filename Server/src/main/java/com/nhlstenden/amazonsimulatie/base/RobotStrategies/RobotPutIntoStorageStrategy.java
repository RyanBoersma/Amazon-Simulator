package com.nhlstenden.amazonsimulatie.base.RobotStrategies;

import com.nhlstenden.amazonsimulatie.base.CBS;
import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.GraphVertexGroup;
import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.StorageUnit;

public class RobotPutIntoStorageStrategy implements RobotStrategy {
	private CBS cbs;
	private MapState mapState;

	public RobotPutIntoStorageStrategy(MapState mapState, CBS cbs, Robot robot) {
		this.cbs = cbs;

		GraphVertexGroup storageVertices = mapState.getFreeStorageVertices();
		GraphVertex targetVertex = storageVertices.getClosest(robot.getVertex());

		robot.setTargetVertex(targetVertex);
		storageVertices.reserve(targetVertex);

		this.mapState = mapState;
	}

	@Override
	public void tick(Robot robot) {
		Path path = robot.getPath();

		if (path != null && path.getDepth() > 0) {
			robot.setVertex(path.getVertices().get(0));
			robot.setBattery(robot.getBattery() - 1);
		}

		if (robot.hasReachedTarget()) {
			StorageUnit storageUnit = robot.getStorageUnit();

			storageUnit.setVertex(robot.getVertex());

			storageUnit.setRobot(null);
			robot.setStorageUnit(null);

			mapState.getStored().add(storageUnit);

			RobotStrategy strategy = new RobotIdleStrategy(mapState, cbs);
			robot.setStrategy(strategy);
		}
	}
}