package com.nhlstenden.amazonsimulatie.base.RobotStrategies;

import com.nhlstenden.amazonsimulatie.base.CBS;
import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.GraphVertexGroup;
import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.models.Robot;

public class RobotChargeStrategy implements RobotStrategy {
	private CBS cbs;
	private MapState mapState;

	public RobotChargeStrategy(MapState mapState, CBS cbs, Robot robot) {
		this.cbs = cbs;
		
		GraphVertexGroup chargeVertices = mapState.getFreeChargeVertices();

		GraphVertex targetVertex = chargeVertices.getClosest(robot.getVertex());

		robot.setTargetVertex(targetVertex);
		chargeVertices.reserve(targetVertex);

		this.mapState = mapState;
	}

	@Override
	public void tick(Robot robot) {
		Path path = robot.getPath();

		if (path != null && path.getDepth() > 0) {
			robot.setVertex(path.getVertices().get(0));
		}

		if (robot.hasReachedTarget()) {
			robot.setBattery(robot.getBattery() + 5);

			if (robot.isBatteryFull()) {
				GraphVertexGroup chargeVertices = mapState.getFreeChargeVertices();
				chargeVertices.free(robot.getTargetVertex());

				RobotStrategy strategy = new RobotIdleStrategy(mapState, cbs);
				robot.setStrategy(strategy);
			}
		}
	}
}
