package com.nhlstenden.amazonsimulatie.base;

import java.util.ArrayList;
import java.util.List;

public class MapData {
	public class Road {
		private final GraphVertex vertex;
		private final Path ingoingPath;
		private final Path outgoingPath;

		public Road(GraphVertex vertex, Path ingoingPath, Path outgoingPath) {
			this.vertex = vertex;
			this.ingoingPath = ingoingPath;
			this.outgoingPath = outgoingPath;
		}

		public GraphVertex getVertex() {
			return vertex;
		}

		public Path getIngoingPath() {
			return ingoingPath;
		}

		public Path getOutgoingPath() {
			return outgoingPath;
		}
	}

	private Graph graph;

	private List<Road> roads;

	private List<GraphVertex> dockVertices;
	private List<GraphVertex> storageVertices;
	private List<GraphVertex> chargeVertices;

	public MapData() {
		graph = new Graph();

		roads = new ArrayList<Road>();

		dockVertices = new ArrayList<GraphVertex>();
		storageVertices = new ArrayList<GraphVertex>();
		chargeVertices = new ArrayList<GraphVertex>();
	}

	/**
	 * Returns all roads
	 * @return all roads
	 */
	public List<Road> getRoads() {
		return roads;
	}

	/**
	 * Adds a path vertex
	 * @param vertex to add
	 */
	public void addPathVertex(GraphVertex vertex) {
		graph.addVertex(vertex);
	}

	/**
	 * Adds a path dock
	 * @param vertex to add
	 */
	public void addDockVertex(GraphVertex vertex) {
		graph.addVertex(vertex);
		dockVertices.add(vertex);
	}

	/**
	 * Adds a path storage
	 * @param vertex to add
	 */
	public void addStorageVertex(GraphVertex vertex) {
		graph.addVertex(vertex);
		storageVertices.add(vertex);
	}

	/**
	 * Adds a path charge
	 * @param vertex to add
	 */
	public void addChargeVertex(GraphVertex vertex) {
		graph.addVertex(vertex);
		chargeVertices.add(vertex);
	}

	/**
	 * Adds an edge
	 * @param edge to add
	 */
	public void addEdge(GraphEdge edge) {
		graph.addEdge(edge);
	}

	/**
	 * Returns the graph
	 * @return graph
	 */
	public Graph getGraph() {
		return graph;
	}

	/**
	 * Returns all dock vertices
	 * @return all dock vertices
	 */
	public List<GraphVertex> getDockVertices() {
		return dockVertices;
	}

	/**
	 * Returns all storage vertices
	 * @return all storage vertices
	 */
	public List<GraphVertex> getStorageVertices() {
		return storageVertices;
	}

	/**
	 * Returns all charge vertices
	 * @return all charge vertices
	 */
	public List<GraphVertex> getChargeVertices() {
		return chargeVertices;
	}
}
