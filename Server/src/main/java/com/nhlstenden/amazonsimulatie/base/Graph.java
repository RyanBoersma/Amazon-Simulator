package com.nhlstenden.amazonsimulatie.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graph {
	private final List<GraphVertex> vertices;
	private Map<GraphVertex, List<GraphEdge>> edgesLookup;
	private List<GraphEdge> edges;

	public Graph() {
		vertices = new ArrayList<>();
		edgesLookup = new HashMap<>();
		edges = new ArrayList<>();
	}

	/**
	 * Adds a vertex
	 * @param vertex to add
	 */
	public void addVertex(GraphVertex vertex) {
		vertices.add(vertex);
	}

	/**
	 * Adds an edge
	 * @param edge to add
	 */
	public void addEdge(GraphEdge edge) {
		GraphVertex from = edge.getFrom();

		if (edgesLookup.get(from) == null)
			edgesLookup.put(from, new ArrayList<>());

		edgesLookup.get(from).add(edge);
		edges.add(edge);
	}

	/**
	 * Gets the vertices
	 * @return List of vertices
	 */
	public List<GraphVertex> getVertices() {
		return vertices;
	}

	/**
	 * Returns lookup map for vertices to edges
	 * @return Map of vertex to list of its edgesx
	 */
	public Map<GraphVertex, List<GraphEdge>> getEdgesLookup() {
		return edgesLookup;
	}

	/**
	 * Returns all the edges
	 * @return list of all edges
	 */
	public List<GraphEdge> getEdges() {
		return edges;
	}
}
