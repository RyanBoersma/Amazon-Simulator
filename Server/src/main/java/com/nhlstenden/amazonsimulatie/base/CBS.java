package com.nhlstenden.amazonsimulatie.base;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class CBS {
	private TimeExpandedPathFinder timeExtendedPathFinder;
	private List<CBSAgent> agents;
	private CBSSolution solution;

	public CBS(TimeExpandedPathFinder timeExtendedPathFinder) {
		this.timeExtendedPathFinder = timeExtendedPathFinder;

		agents = new ArrayList<CBSAgent>();
	}

	/**
	 * Adds an agent to the solver
	 * @param agent to add
	 */
	public void addAgent(CBSAgent agent) {
		this.agents.add(agent);
	}

	/**
	 * Removes an agent from the solver
	 * @param agent to remove
	 */
	public void removeAgent(CBSAgent agent) {
		this.agents.remove(agent);
	}

	/**
	 * Returns the found solution
	 * @return The solution
	 */
	public CBSSolution getSolution() {
		return solution;
	}

	/**
	 * Solves for all agents
	 */
	public void solve() {
		int attemptsLeft = 25;

		this.solution = null;

		Comparator<CBSSolution> solutionComparator = new Comparator<CBSSolution>() {
			@Override
			public int compare(CBSSolution s1, CBSSolution s2) {
				return s1.getCost() - s2.getCost();
			}
		};

		PriorityQueue<CBSSolution> openSolutions = new PriorityQueue<CBSSolution>(1, solutionComparator);

		CBSSolution initalSolution = new CBSSolution(agents);
		initalSolution.solve(timeExtendedPathFinder);
		openSolutions.add(initalSolution);
	
		while (openSolutions.size() > 0) {
			CBSSolution solution = openSolutions.poll();

			if (attemptsLeft > 0) {
				CBSCollision collision = solution.getFirstCollision();

				if (collision != null) {
					CBSSolution solutionA = solution.mutate(collision.getAgentA(), collision.getVertex());
					CBSSolution solutionB = solution.mutate(collision.getAgentB(), collision.getVertex());

					solutionA.solve(timeExtendedPathFinder);
					solutionB.solve(timeExtendedPathFinder);

					openSolutions.add(solutionA);
					openSolutions.add(solutionB);

					openSolutions.remove(solution);

					attemptsLeft--;
				} else {
					this.solution = solution;
					
					break;
				}
			} else {
				CBSSolution bestSolution = null;
				int bestCollisionCount = Integer.MAX_VALUE;

				for (CBSSolution openSolution : openSolutions) {
					int collisionCount = openSolution.getCollisionCount();
					if (collisionCount < bestCollisionCount) {
						bestSolution = openSolution;
						bestCollisionCount = collisionCount;
					}
				}
				
				this.solution = bestSolution;
				break;
			}
		}
	}
}
