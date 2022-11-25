package com.nhlstenden.amazonsimulatie.services;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.nhlstenden.amazonsimulatie.base.CBS;
import com.nhlstenden.amazonsimulatie.base.CBSAgent;
import com.nhlstenden.amazonsimulatie.base.CBSSolution;
import com.nhlstenden.amazonsimulatie.base.MapState;
import com.nhlstenden.amazonsimulatie.base.Path;
import com.nhlstenden.amazonsimulatie.base.TimeExpandedDijkstra;
import com.nhlstenden.amazonsimulatie.base.TimeExpandedPathFinder;
import com.nhlstenden.amazonsimulatie.base.RobotStrategies.RobotIdleStrategy;
import com.nhlstenden.amazonsimulatie.base.RobotStrategies.RobotStrategy;
import com.nhlstenden.amazonsimulatie.models.Robot;

public class RobotService extends Service<Robot> {
	private CBS cbs;

	private Map<Robot, CBSAgent> agents;

	public RobotService(MapState mapState) {
		super(mapState);

		TimeExpandedPathFinder timeExpandedPathFinder = new TimeExpandedDijkstra(mapState.getTimeExpandedGraph());
		cbs = new CBS(timeExpandedPathFinder);
		
		agents = new HashMap<>();
	}

	@Override
	public Set<Robot> tick(Set<Robot> robots) {
		for (Robot robot : robots) {
			CBSAgent agent = agents.get(robot);

			if (agent == null) {
				agent = new CBSAgent(robot.getVertex(), robot.getTargetVertex());
				agents.put(robot, agent);

				cbs.addAgent(agent);
			}

			agent.setStartVertex(robot.getVertex());
			agent.setTargetVertex(robot.getTargetVertex());
		}

		cbs.solve();

		for (Robot robot : robots) {
			CBSAgent agent = agents.get(robot);
			CBSSolution solution = cbs.getSolution();

			Path path = solution.getAgentPath(agent).getPath();
			robot.setPath(path);

			RobotStrategy strategy = robot.getStrategy();

			if (strategy == null) { 
				RobotStrategy newStrategy = new RobotIdleStrategy(mapState, cbs);
				robot.setStrategy(newStrategy);

				strategy = newStrategy;
			}

			strategy.tick(robot);
		}

		return robots;
	}
}
