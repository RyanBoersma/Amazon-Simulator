package com.nhlstenden.amazonsimulatie.base;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GraphVertexGroup {
	private final Set<GraphVertex> vertices;

	public GraphVertexGroup(List<GraphVertex> vertices) {
		this.vertices = new HashSet<GraphVertex>(vertices);
	}

	/**
	 * Returns all vertices in the group
	 * @return all vertices in the group
	 */
	public Set<GraphVertex> getVertices() {
		return vertices;
	}

	/**
	 * Frees a vertex in the group
	 * @param vertex to free
	 */
	public void free(GraphVertex vertex) {
		vertices.add(vertex);
	}

	/**
	 * Reserves a vertex in the group
	 * @param vertex to reserve
	 */
	public void reserve(GraphVertex vertex) {
		vertices.remove(vertex);
	}

	/**
	 * Returns the closest vertex to another vertex using Manhattan distance
	 * @param vertex to get closest vertex to
	 * @return closest vertex
	 */
	public GraphVertex getClosest(GraphVertex vertex) {
		GraphVertex closestVertex = null;
		double minDistance = Double.MAX_VALUE;
		
		for (GraphVertex otherVertex : vertices) {
			double dx = Math.abs(vertex.getX() - otherVertex.getX()); 
			double dy = Math.abs(vertex.getY() - otherVertex.getY());
			double dz = Math.abs(vertex.getZ() - otherVertex.getZ());

			// Euclidian distance
			//double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

			// Squared Euclidian distance
			//double distance = dx * dx + dy * dy + dz * dz;

			// Manhattan distance
			double distance = dx + dy + dz;

			if (distance < minDistance) {
				closestVertex = otherVertex;
				minDistance = distance;
			}
		}
		
		return closestVertex;
	}
}
