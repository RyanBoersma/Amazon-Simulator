package com.nhlstenden.amazonsimulatie.base;

import java.util.*;

public class Dijkstra implements PathFinder {
	private Graph graph;

	public Dijkstra(Graph graph) {
		this.graph = graph;
	}

	@Override
	public Path findPath(GraphVertex start, GraphVertex targetVertex) {
		Set<GraphVertex> unsettled = new HashSet<>();
		Map<GraphVertex, PathDataElement> pathData = new HashMap<>();

		unsettled.add(start);
		pathData.put(start, new PathDataElement(0, null));

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

		Path path = new Path();
		GraphVertex nextVertex = targetVertex;

		while (nextVertex != null) {
			path.prependVertex(nextVertex);

			PathDataElement pathDataElement = pathData.get(nextVertex);
			nextVertex = pathDataElement.getLastVertex();
		}

		return path;
	}
}
