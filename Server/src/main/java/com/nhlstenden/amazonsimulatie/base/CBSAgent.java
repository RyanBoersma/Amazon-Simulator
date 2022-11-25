package com.nhlstenden.amazonsimulatie.base;

public class CBSAgent {
	private GraphVertex startVertex;
	private GraphVertex targetVertex;

	public CBSAgent(GraphVertex startVertex, GraphVertex targetVertex) {
		this.startVertex = startVertex;
		this.targetVertex = targetVertex;
	}

	/**
	 * Returns the start vertex of the agent
	 * @return the start vertex of the agent
	 */
	public GraphVertex getStartVertex() {
		return startVertex;
	}

	/**
	 * Sets the start vertex of the agent
	 * @param startVertex of the agent
	 */
	public void setStartVertex(GraphVertex startVertex) {
		this.startVertex = startVertex;
	}

	/**
	 * Returns the target vertex of the agent
	 * @return the target vertex of the agent
	 */
	public GraphVertex getTargetVertex() {
		return targetVertex;
	}

	/**
	 * Sets the target vertex of the agent
	 * @param targetVertex of the agent
	 */
	public void setTargetVertex(GraphVertex targetVertex) {
		this.targetVertex = targetVertex;
	}
}
