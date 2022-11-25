package com.nhlstenden.amazonsimulatie.base;

public class GraphEdge {
	private final GraphVertex from;
	private final GraphVertex to;

	public GraphEdge(GraphVertex from, GraphVertex to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * Returns the vertex the edge came from
	 * @return Vertex the edge came from
	 */
	public GraphVertex getFrom() {
		return from;
	}

	/**
	 * Returns the vertex the edge goes to
	 * @return Vertex the edge goes to
	 */
	public GraphVertex getTo() {
		return to;
	}
}
