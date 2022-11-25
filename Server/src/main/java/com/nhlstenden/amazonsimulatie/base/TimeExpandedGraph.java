package com.nhlstenden.amazonsimulatie.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeExpandedGraph {
	private List<TimeExpandedGraphVertex> vertices;
	private Map<TimeExpandedGraphVertex, List<TimeExpandedGraphVertex>> edges;
	private int depth;

	private Map<GraphVertex, TimeExpandedGraphVertex> rootVertices;
	private Graph graph;

	public TimeExpandedGraph(Graph graph, int depth) {
		this.graph = graph;

		vertices = new ArrayList<TimeExpandedGraphVertex>();
		edges = new HashMap<TimeExpandedGraphVertex, List<TimeExpandedGraphVertex>>();
		this.depth = depth * 2 - 1;
		rootVertices = new HashMap<GraphVertex, TimeExpandedGraphVertex>();

		Map<GraphVertex, TimeExpandedGraphVertex> lastVertices = new HashMap<GraphVertex, TimeExpandedGraphVertex>();
		Map<GraphVertex, TimeExpandedGraphVertex> newVertices = new HashMap<GraphVertex, TimeExpandedGraphVertex>();
		
		for (int i = 0; i < this.depth; i++) {
			for (GraphVertex vertex : graph.getVertices()) {
				TimeExpandedGraphVertex timeExpandedVertex = new TimeExpandedGraphVertex(vertex, i, false);
				vertices.add(timeExpandedVertex);

				newVertices.put(vertex, timeExpandedVertex);

				if (i == 0)
					rootVertices.put(vertex, timeExpandedVertex);
			}

			if (i != 0) {
				for (GraphVertex vertex : graph.getVertices()) {
					TimeExpandedGraphVertex from = lastVertices.get(vertex);
					TimeExpandedGraphVertex to = newVertices.get(vertex);
					
					TimeExpandedGraphVertex auxiliary = new TimeExpandedGraphVertex(null, to.getTime(), true);
					vertices.add(auxiliary);

					if (edges.get(from) == null)
						edges.put(from, new ArrayList<TimeExpandedGraphVertex>());

					//getVertexEdges(from).add(auxiliary);
					//getVertexEdges(auxiliary).add(to);
				}

				for (int j = 0; j < graph.getEdges().size(); j += 2) {
					GraphEdge edge = graph.getEdges().get(j);

					GraphVertex vertexA = edge.getFrom();
					GraphVertex vertexB = edge.getTo();

					TimeExpandedGraphVertex lastVertexA = lastVertices.get(vertexA);
					TimeExpandedGraphVertex lastVertexB = lastVertices.get(vertexB);

					TimeExpandedGraphVertex newVertexA = newVertices.get(vertexA);
					TimeExpandedGraphVertex newVertexB = newVertices.get(vertexB);

					createEdge(lastVertexA, newVertexA, lastVertexB, newVertexB);
				}

				// for (GraphEdge edge : graph.getEdges()) {
				// 	GraphVertex vertexA = edge.getFrom();
				// 	GraphVertex vertexB = edge.getTo();

				// 	TimeExpandedGraphVertex lastVertexA = lastVertices.get(vertexA);
				// 	TimeExpandedGraphVertex lastVertexB = lastVertices.get(vertexB);

				// 	TimeExpandedGraphVertex newVertexA = newVertices.get(vertexA);
				// 	TimeExpandedGraphVertex newVertexB = newVertices.get(vertexB);

				// 	createEdge(lastVertexA, newVertexA, lastVertexB, newVertexB);
				// }
			}

			Map<GraphVertex, TimeExpandedGraphVertex> temp = newVertices;
			newVertices = lastVertices;
			lastVertices = temp;

			newVertices.clear();
		}
	}

	/**
	 * Adds an edge between 2 time expanded vertices
	 * @param aLast vertex A at t-1
	 * @param aNew vertex A at t
	 * @param bLast vertex B at t-1
	 * @param bNew vertex B at t
	 */
	private void createEdge(TimeExpandedGraphVertex aLast, TimeExpandedGraphVertex aNew, TimeExpandedGraphVertex bLast, TimeExpandedGraphVertex bNew) {
		TimeExpandedGraphVertex auxiliary = new TimeExpandedGraphVertex(null, aNew.getTime(), true);
		vertices.add(auxiliary);

		getVertexEdges(aLast).add(auxiliary);
		getVertexEdges(bLast).add(auxiliary);

		getVertexEdges(auxiliary).add(aNew);
		getVertexEdges(auxiliary).add(bNew);
	}

	/**
	 * Returns all neighbours for a time expanded vertex
	 * @param vertex
	 * @return list of neighbours
	 */
	private List<TimeExpandedGraphVertex> getVertexEdges(TimeExpandedGraphVertex vertex) {
		List<TimeExpandedGraphVertex> vertexEdges = edges.get(vertex);

		if (vertexEdges == null) {
			vertexEdges = new ArrayList<TimeExpandedGraphVertex>();
			edges.put(vertex, vertexEdges);
		}

		return vertexEdges;
	}

	/**
	 * Returns all time expanded vertices
	 * @return all time expanded vertices
	 */
	public List<TimeExpandedGraphVertex> getVertices() {
		return vertices;
	}

	/**
	 * Returns all edges
	 * @return all edges
	 */
	public Map<TimeExpandedGraphVertex, List<TimeExpandedGraphVertex>> getEdges() {
		return edges;
	}

	/**
	 * Returns the depth
	 * @return the depth
	 */
	public int getDepth() {
		return depth;
	}

	/**
	 * Returns the time expanded vertex for a root vertex
	 * @param vertex to find time expanded vertex for
	 * @return time expanded vertex for a root vertex
	 */
	public TimeExpandedGraphVertex getRootTimeExpandedVertex(GraphVertex vertex) {
		return rootVertices.get(vertex);
	}

	/**
	 * Returns the graph
	 * @return the graph
	 */
	public Graph getGraph() {
		return this.graph;
	}
}
