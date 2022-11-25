package com.nhlstenden.amazonsimulatie.base;

import java.util.LinkedList;
import java.util.List;

public class Path {
	private LinkedList<GraphVertex> vertices;
	private GraphVertex endVertex;

	public Path() {
		vertices = new LinkedList<GraphVertex>();
	}

	/**
	 * Prepends a vertex to the path
	 * @param vertex to prepend
	 */
	public void prependVertex(GraphVertex vertex) {
		if (vertices.size() == 0)
			endVertex = vertex;

		vertices.addFirst(vertex);
	}

	/**
	 * Appends a vertex to the path
	 * @param vertex to append
	 */
	public void appendVertex(GraphVertex vertex) {
		vertices.addLast(vertex);

		endVertex = vertex;
	}

	/**
	 * Returns list of all vertices in the path
	 * @return List of all vertices
	 */
	public List<GraphVertex> getVertices() {
		return vertices;
	}

	/**
	 * Returns depth (length) of the path
	 * @return depth (lenght) of the path
	 */
	public int getDepth() {
		return this.vertices.size();
	}

	/**
	 * Returns the inverse of the path
	 * For an inverse path, the order of vertices is flipped
	 * @return inverse path
	 */
	public Path getInversePath() {
		Path path = new Path();

		for (GraphVertex vertex : getVertices())
			path.prependVertex(vertex);

		return path;
	}

	/**
	 * Returns the last vertex of the path
	 * @return last vertex of the path
	 */
	public GraphVertex getEndVertex() {
		return endVertex;
	}
}
