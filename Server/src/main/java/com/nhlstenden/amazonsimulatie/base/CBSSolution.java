package com.nhlstenden.amazonsimulatie.base;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CBSSolution {
	private List<CBSAgent> agents;
	private Map<CBSAgent, TimeExpandedPath> paths;
	private Map<CBSAgent, Set<TimeExpandedGraphVertex>> constraints;

	public CBSSolution(List<CBSAgent> agents) {
		this.agents = agents;
		paths = new HashMap<CBSAgent, TimeExpandedPath>();
		constraints = new HashMap<CBSAgent, Set<TimeExpandedGraphVertex>>();

		for (CBSAgent agent : this.agents) {
			Set<TimeExpandedGraphVertex> agentConstraints = new HashSet<TimeExpandedGraphVertex>();
			constraints.put(agent, agentConstraints);
		}
	}

	public CBSSolution(List<CBSAgent> agents, Map<CBSAgent, Set<TimeExpandedGraphVertex>> constraints) {
		this.agents = agents;
		paths = new HashMap<CBSAgent, TimeExpandedPath>();
		this.constraints = constraints;
	}

	/**
	 * Solves the solution
	 * @param timeExpandedPathFinder to use
	 * @return true if a solution was found. False otherwise
	 */
	public boolean solve(TimeExpandedPathFinder timeExpandedPathFinder) {
		for (CBSAgent agent : agents) {
			Set<TimeExpandedGraphVertex> agentConstraints = constraints.get(agent);

			GraphVertex startVertex = agent.getStartVertex();
			GraphVertex targetVertex = agent.getTargetVertex();
			if (targetVertex == null)
				targetVertex = startVertex;

			TimeExpandedPath path = timeExpandedPathFinder.findPath(startVertex, targetVertex, agentConstraints);
			paths.put(agent, path);
		}

		return true;
	}

	/**
	 * Return the total cost of the solution
	 * @return total cost of the solution
	 */
	public int getCost() {
		int totalCost = 0;

		for (TimeExpandedPath path : paths.values())
			totalCost += path.getCost();
		
		return totalCost;
	}

	/**
	 * Returns the first found collision in the solution
	 * @return first found collision in the solution
	 */
	public CBSCollision getFirstCollision() {
		CBSCollision collision = null;

		Map<TimeExpandedGraphVertex, CBSAgent> takenVertices = new HashMap<TimeExpandedGraphVertex, CBSAgent>();
		
		for (CBSAgent agent : paths.keySet()) {
			TimeExpandedPath path = paths.get(agent);

			for (TimeExpandedGraphVertex vertex : path.getVertices()) {
				if (takenVertices.containsKey(vertex)) {
					CBSAgent otherAgent = takenVertices.get(vertex);
					collision = new CBSCollision(vertex, agent, otherAgent);

					break;
				}

				takenVertices.put(vertex, agent);
			}

			if (collision != null)
				break;
		}

		return collision;
	}

	/**
	 * Returns the amount of collisions in the solution
	 * @return the amount of collisions in the solution
	 */
	public int getCollisionCount() {
		int collisionCount = 0;

		Set<TimeExpandedGraphVertex> takenVertices = new HashSet<TimeExpandedGraphVertex>();
		
		for (CBSAgent agent : paths.keySet()) {
			TimeExpandedPath path = paths.get(agent);

			for (TimeExpandedGraphVertex vertex : path.getVertices()) {
				if (takenVertices.contains(vertex))
					collisionCount++;
				else
					takenVertices.add(vertex);
			}
		}

		return collisionCount;
	}
	
	/**
	 * Mutates the solution so a agent will fulfull a constraint
	 * @param agent to fulfill a constrant
	 * @param constraint to fulfill
	 * @return new mutated solution
	 */
	public CBSSolution mutate(CBSAgent agent, TimeExpandedGraphVertex constraint) {
		Map<CBSAgent, Set<TimeExpandedGraphVertex>> newConstraints = new HashMap<CBSAgent, Set<TimeExpandedGraphVertex>>();

		for (CBSAgent cAgent : constraints.keySet()) {
			Set<TimeExpandedGraphVertex> oldAgentConstraints = constraints.get(cAgent);
			Set<TimeExpandedGraphVertex> newAgentConstraints = new HashSet<TimeExpandedGraphVertex>(oldAgentConstraints);

			newConstraints.put(cAgent, newAgentConstraints);
		}

		newConstraints.get(agent).add(constraint);

		return new CBSSolution(agents, newConstraints);
	}

	/**
	 * Returns lookup for all agents to a path
	 * @return lookup for all agents to a path
	 */
	public Map<CBSAgent, TimeExpandedPath> getPaths() {
		return paths;
	}

	/**
	 * Returns path for an agent
	 * @param agent to get path for
	 * @return path for agent
	 */
	public TimeExpandedPath getAgentPath(CBSAgent agent) {
		return paths.get(agent);
	}
} 
