package com.nhlstenden.amazonsimulatie.base;

public class TimeExpandedPathDataElement {
	public int minimumDistanceFromRoot;
	public TimeExpandedGraphVertex lastVertex;
	
	public TimeExpandedPathDataElement(int minimumDistanceFromRoot, TimeExpandedGraphVertex lastVertex) {
		this.minimumDistanceFromRoot = minimumDistanceFromRoot;
		this.lastVertex = lastVertex;
	}
}
