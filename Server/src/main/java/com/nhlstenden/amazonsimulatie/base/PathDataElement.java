package com.nhlstenden.amazonsimulatie.base;

public class PathDataElement {
	private int minimumDistanceFromRoot;
	private GraphVertex lastVertex;

	public PathDataElement(int minimumDistanceFromRoot, GraphVertex lastVertex) {
		this.minimumDistanceFromRoot = minimumDistanceFromRoot;
		this.lastVertex = lastVertex;
	}

	/**
	 * Returns the minimum distance from root
	 * @return the minimum distance from root
	 */
	public int getMinimumDistanceFromRoot() {
		return minimumDistanceFromRoot;
	}

	/**
	 * Sets the minimum distance from root
	 * @param minimumDistanceFromRoot
	 */
	public void setMinimumDistanceFromRoot(int minimumDistanceFromRoot) {
		this.minimumDistanceFromRoot = minimumDistanceFromRoot;
	}

	/**
	 * Retursn the last vertex
	 * @return the last vertex
	 */
	public GraphVertex getLastVertex() {
		return lastVertex;
	}

	/**
	 * Sets the last vertex
	 * @param lastVertex
	 */
	public void setLastVertex(GraphVertex lastVertex) {
		this.lastVertex = lastVertex;
	}
}