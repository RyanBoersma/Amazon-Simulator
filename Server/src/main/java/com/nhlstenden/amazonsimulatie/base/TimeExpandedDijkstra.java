package com.nhlstenden.amazonsimulatie.base;

import java.util.*;

public class TimeExpandedDijkstra implements TimeExpandedPathFinder {
	private TimeExpandedGraph timeExpandedGraph;

	public TimeExpandedDijkstra(TimeExpandedGraph timeExpandedGraph) {
		this.timeExpandedGraph = timeExpandedGraph;
	}

	public TimeExpandedPath findPath(GraphVertex startVertex, GraphVertex targetVertex, Set<TimeExpandedGraphVertex> constraints) {
		int maxDepth = timeExpandedGraph.getDepth();

		Set<TimeExpandedGraphVertex> timeExpandedUnsettled = new HashSet<TimeExpandedGraphVertex>();
		Map<TimeExpandedGraphVertex, TimeExpandedPathDataElement> timeExpandedPathData = new HashMap<TimeExpandedGraphVertex, TimeExpandedPathDataElement>();

		TimeExpandedGraphVertex timeExpandedStart = timeExpandedGraph.getRootTimeExpandedVertex(startVertex);

		timeExpandedUnsettled.add(timeExpandedStart);
		timeExpandedPathData.put(timeExpandedStart, new TimeExpandedPathDataElement(0, null));

		TimeExpandedGraphVertex endTimeExpandedGraphVertex = null;
		int endCost = Integer.MAX_VALUE;

		while (timeExpandedUnsettled.size() > 0) {
			TimeExpandedGraphVertex shortestDistancePoint = null;
			int shortestDistance = Integer.MAX_VALUE;

			for (TimeExpandedGraphVertex point : timeExpandedUnsettled) {
				int minimumDistanceFromRoot = timeExpandedPathData.get(point).minimumDistanceFromRoot;

				if (minimumDistanceFromRoot < shortestDistance) {
					shortestDistancePoint = point;
					shortestDistance = minimumDistanceFromRoot;
				}
			}

			if (shortestDistance >= maxDepth-1)
				break;

			TimeExpandedGraphVertex currentVertex = shortestDistancePoint;

			TimeExpandedPathDataElement currentPathDataElement = timeExpandedPathData.get(currentVertex);

			List<TimeExpandedGraphVertex> neighbours = timeExpandedGraph.getEdges().get(currentVertex);

			if (neighbours != null) {
				for (TimeExpandedGraphVertex neighbour : neighbours) {
					if (constraints.contains(neighbour))
						continue;

					int minimumDistanceFromRoot = currentPathDataElement.minimumDistanceFromRoot + 1;
	
					TimeExpandedPathDataElement neighbourPathDataElement = timeExpandedPathData.get(neighbour);
					int neighbourMinimumDistanceFromRoot = (neighbourPathDataElement != null) ? neighbourPathDataElement.minimumDistanceFromRoot : Integer.MAX_VALUE;
	
					if (endCost < neighbourMinimumDistanceFromRoot)
						continue;

					if (minimumDistanceFromRoot < neighbourMinimumDistanceFromRoot) {
						if (neighbourPathDataElement == null) {
							neighbourPathDataElement = new TimeExpandedPathDataElement(Integer.MAX_VALUE, null);
							timeExpandedPathData.put(neighbour, neighbourPathDataElement);
						}
	
						neighbourPathDataElement.lastVertex = currentVertex;
						neighbourPathDataElement.minimumDistanceFromRoot = minimumDistanceFromRoot;
	
						if (neighbour.getVertex() == targetVertex) {
							endTimeExpandedGraphVertex = neighbour;
							endCost = minimumDistanceFromRoot;
						}

						timeExpandedUnsettled.add(neighbour);
					}
				}

				timeExpandedUnsettled.remove(currentVertex);
			}
		}

		
		TimeExpandedGraphVertex nextTimeExpandedGraphVertex = endTimeExpandedGraphVertex;

		Path pathTail = new Path();
		if (nextTimeExpandedGraphVertex == null) {
			Graph graph = timeExpandedGraph.getGraph();

			Map<GraphVertex, TimeExpandedGraphVertex> vertexOriginLookup = new HashMap<>();

			Set<GraphVertex> unsettled = new HashSet<>();
			Map<GraphVertex, PathDataElement> pathData = new HashMap<>();			

			for (TimeExpandedGraphVertex timeExpandedGraphVertex : timeExpandedUnsettled) {
				if (timeExpandedGraphVertex.isAuxiliary())
					continue;

				GraphVertex vertex = timeExpandedGraphVertex.getVertex();
				TimeExpandedPathDataElement timeExpandedPathDataElement = timeExpandedPathData.get(timeExpandedGraphVertex);

				unsettled.add(vertex);

				PathDataElement pathDataElement = new PathDataElement(timeExpandedPathDataElement.minimumDistanceFromRoot, null);
				pathData.put(vertex, pathDataElement);

				vertexOriginLookup.put(vertex, timeExpandedGraphVertex);
			}

			if (unsettled.size() == 0) {
				unsettled.add(startVertex);
				PathDataElement pathDataElement = new PathDataElement(0, null);
				pathData.put(startVertex, pathDataElement);
			}

			while (unsettled.size() > 0) {
				GraphVertex shortestDistanceVertex = null;
				int shortestDistance = Integer.MAX_VALUE;

				for (GraphVertex vertex : unsettled) {
					int minimumDistanceFromRoot = pathData.get(vertex).getMinimumDistanceFromRoot();

					if (minimumDistanceFromRoot < shortestDistance) {
						shortestDistanceVertex = vertex;
						shortestDistance = minimumDistanceFromRoot;
					}
				}

				GraphVertex currentVertex = shortestDistanceVertex;
				int currentDistance = shortestDistance;

				if (currentVertex == targetVertex) {
					break;
				}

				List<GraphEdge> edges = graph.getEdgesLookup().get(currentVertex);

				if (edges == null)
					continue;

				for (GraphEdge edge : edges) {
					GraphVertex neighbourVertex = edge.getTo();

					int minimumDistanceFromRoot = currentDistance + 1;

					PathDataElement neighbourPathDataElement = pathData.get(neighbourVertex);
					int neighbourMinimumDistanceFromRoot = (neighbourPathDataElement != null) ? neighbourPathDataElement.getMinimumDistanceFromRoot() : Integer.MAX_VALUE;

					if (minimumDistanceFromRoot < neighbourMinimumDistanceFromRoot) {
						if (neighbourPathDataElement == null) { 
							neighbourPathDataElement = new PathDataElement(Integer.MAX_VALUE, null);
							pathData.put(neighbourVertex, neighbourPathDataElement);
						}

						neighbourPathDataElement.setMinimumDistanceFromRoot(minimumDistanceFromRoot);
						neighbourPathDataElement.setLastVertex(currentVertex); 

						unsettled.add(neighbourVertex);
					}
				}

				unsettled.remove(currentVertex);
			}

			GraphVertex vertex = targetVertex;

			while (vertex != null) {
				PathDataElement pathDataElement = pathData.get(vertex);
				GraphVertex nextVertex = pathDataElement.getLastVertex();	

				if (nextVertex != null) {
					pathTail.prependVertex(vertex);
				} else {
					nextTimeExpandedGraphVertex = vertexOriginLookup.get(vertex);
				}

				vertex = nextVertex;
			}
		}

		TimeExpandedPath path = new TimeExpandedPath(pathTail);
		while (nextTimeExpandedGraphVertex != null) {
			TimeExpandedPathDataElement pathDataElement = timeExpandedPathData.get(nextTimeExpandedGraphVertex);
			
			if (pathDataElement.lastVertex != null)
				path.prependVertex(nextTimeExpandedGraphVertex);

			nextTimeExpandedGraphVertex = pathDataElement.lastVertex;
		}

		return path;
	}
}