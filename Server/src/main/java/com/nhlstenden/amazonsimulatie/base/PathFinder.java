package com.nhlstenden.amazonsimulatie.base;

public interface PathFinder {
	/**
	 * Finds a path between two vertices
	 * @param start vertex
	 * @param target vertex
	 * @return a path between the vertices
	 */
	public Path findPath(GraphVertex start, GraphVertex target); 
}
