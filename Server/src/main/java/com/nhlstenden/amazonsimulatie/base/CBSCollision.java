package com.nhlstenden.amazonsimulatie.base;

public class CBSCollision {
	private TimeExpandedGraphVertex vertex;
	private CBSAgent agentA;
	private CBSAgent agentB;
	
	public CBSCollision(TimeExpandedGraphVertex vertex, CBSAgent agentA, CBSAgent agentB) {
		this.vertex = vertex;
		this.agentA = agentA;
		this.agentB = agentB;
	}

	/**
	 * Returns the vertex associated with the collision
	 * @return vertex associated with the collision
	 */
	public TimeExpandedGraphVertex getVertex() {
		return vertex;
	}

	/**
	 * Returns the first agent associated with the collision
	 * @return first agent associated with the collision
	 */
	public CBSAgent getAgentA() {
		return agentA;
	}

	/**
	 * Returns the sceond agent associated with the collision
	 * @return second agent associated with the collision
	 */
	public CBSAgent getAgentB() {
		return agentB;
	}
}
