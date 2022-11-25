package com.nhlstenden.amazonsimulatie.base;

import java.util.LinkedList;
import java.util.List;

public class TimeExpandedPath {
	private LinkedList<TimeExpandedGraphVertex> vertices;
	private Path pathTail;

	public TimeExpandedPath(Path pathTail) {
		vertices = new LinkedList<TimeExpandedGraphVertex>();
		this.pathTail = pathTail;
	}

	/**
	 * Prepends a time expanded vertex
	 * @param vertex to prepend
	 */
	public void prependVertex(TimeExpandedGraphVertex vertex) {
		vertices.addFirst(vertex);
	}

	/**
	 * Appends a time expanded vertex
	 * @param vertex to append
	 */
	public void appendVertex(TimeExpandedGraphVertex vertex) {
		vertices.addLast(vertex);
	}

	/**
	 * Returns all time expanded vertices
	 * @return all time expanded vertices
	 */
	public List<TimeExpandedGraphVertex> getVertices() {
		return vertices;
	}

	/**
	 * Returns the cost of the path
	 * @return cost of the path
	 */
	public int getCost() {
		if (this.vertices.size() == 0)
			return Integer.MAX_VALUE;
		return this.vertices.size();
	}

	/**
	 * Returns the depth of the path
	 * @return depth of the path
	 */
	public int getDepth() {
		return this.vertices.size();
	}

	/**
	 * Returns the non-time expanded path variant of this path
	 * @return the non-time expanded path variant of this path
	 */
	public Path getPath() {
		Path path = new Path();

		for (TimeExpandedGraphVertex timeExpandedGraphVertex : getVertices()) {
			if (timeExpandedGraphVertex.isAuxiliary())
				continue;

			path.appendVertex(timeExpandedGraphVertex.getVertex());
		}

		for (GraphVertex vertex : pathTail.getVertices()) {
			path.appendVertex(vertex);
		}

		return path;
	}
}
