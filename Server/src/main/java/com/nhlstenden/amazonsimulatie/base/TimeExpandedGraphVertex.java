package com.nhlstenden.amazonsimulatie.base;

public class TimeExpandedGraphVertex {
	private final GraphVertex vertex;
	private final int time;
	private boolean auxiliary;

	public TimeExpandedGraphVertex(GraphVertex vertex, int time, boolean auxiliary) {
		this.vertex = vertex;
		this.time = time;
		this.auxiliary = auxiliary;
	}

	/**
	 * Returns the vertex associated with the time expanded vertex
	 * @return the vertex associated with the time expanded vertex
	 */
	public GraphVertex getVertex() {
		return vertex;
	}

	/**
	 * Returns the time of the time expanded vertex
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Returns if the vertex is auxiliary
	 * @return true if auxiliary, false otherwise
	 */
	public boolean isAuxiliary() {
		return auxiliary;
	}
}