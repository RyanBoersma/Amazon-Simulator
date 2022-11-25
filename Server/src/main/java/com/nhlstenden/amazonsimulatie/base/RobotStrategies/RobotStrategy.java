package com.nhlstenden.amazonsimulatie.base.RobotStrategies;

import com.nhlstenden.amazonsimulatie.models.Robot;

public interface RobotStrategy {
	/**
	 * Executes a strategy for a robot
	 * @param robot to execute strategy on
	 */
	public void tick(Robot robot);
}
