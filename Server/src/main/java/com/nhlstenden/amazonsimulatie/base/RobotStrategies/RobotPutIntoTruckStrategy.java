package com.nhlstenden.amazonsimulatie.base.RobotStrategies;

import com.nhlstenden.amazonsimulatie.base.CBS;
import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.base.GraphVertex.GraphVertexFacing;
import com.nhlstenden.amazonsimulatie.models.Road;
import com.nhlstenden.amazonsimulatie.models.Robot;
import com.nhlstenden.amazonsimulatie.models.StorageUnit;

public class RobotPutIntoTruckStrategy implements RobotStrategy {
	private final static GraphVertex hideVertex = new GraphVertex(0, -10000, 0, GraphVertexFacing.DOWN);

	private CBS cbs;
	private MapState mapState;
	private Road road;
	private StorageUnit storageUnit;

	private int waitFrames = 1;

	public RobotPutIntoTruckStrategy(MapState mapState, CBS cbs, Robot robot, Road road, StorageUnit storageUnit) {
		this.mapState = mapState;
		this.cbs = cbs;
		this.road = road;
		this.storageUnit = storageUnit;

		robot.setTargetVertex(road.getVertex());
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
				road.completeSink(storageUnit);

				storageUnit.setRobot(null);
				robot.setStorageUnit(null);

				storageUnit.setVertex(hideVertex);

				RobotStrategy strategy = new RobotIdleStrategy(mapState, cbs);
				robot.setStrategy(strategy);
			} else {
				waitFrames--;
			}
		}
	}
}
