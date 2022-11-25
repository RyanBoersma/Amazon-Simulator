package com.nhlstenden.amazonsimulatie.base;

import java.util.*;

public interface TimeExpandedPathFinder {
	/**
	 * Finds a path in a time expanded graph
	 * @param start vertex
	 * @param target vertex
	 * @param constraints
	 * @return a time expanded path
	 */
	public TimeExpandedPath findPath(GraphVertex start, GraphVertex target, Set<TimeExpandedGraphVertex> constraints);
}
