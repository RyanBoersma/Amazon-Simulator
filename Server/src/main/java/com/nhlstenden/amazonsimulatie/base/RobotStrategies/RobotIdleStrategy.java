package com.nhlstenden.amazonsimulatie.base.RobotStrategies;

import com.nhlstenden.amazonsimulatie.base.CBS;
import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.models.Robot;

public class RobotIdleStrategy implements RobotStrategy {
	private MapState mapState;
	private CBS cbs;
		
	public RobotIdleStrategy(MapState mapState, CBS cbs) {
		this.mapState = mapState;
		this.cbs = cbs;
	}

	@Override
	public void tick(Robot robot) {
		RobotStrategy strategy = null;

		if (robot.isBatteryEmpty())
			strategy = new RobotChargeStrategy(mapState, cbs, robot);
		else if (mapState.hasSourcingRoad())
			strategy = new RobotGetFromTruckStrategy(mapState, cbs, robot);
		else if (mapState.hasSinkingRoad())
			strategy = new RobotGetFromStorageStrategy(mapState, cbs, robot);
		else
			strategy = new RobotChargeStrategy(mapState, cbs, robot);
		robot.setStrategy(strategy);
	}
}
