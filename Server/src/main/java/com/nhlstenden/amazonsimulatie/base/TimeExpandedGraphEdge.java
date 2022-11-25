package com.nhlstenden.amazonsimulatie.base;

public class TimeExpandedGraphEdge {
	private final TimeExpandedGraphVertex from;
	private final TimeExpandedGraphVertex to;

	public TimeExpandedGraphEdge(TimeExpandedGraphVertex from, TimeExpandedGraphVertex to) {
		this.from = from;
		this.to = to;
	}

	/**
	 * Returns the time expanded vertex the edge came from
	 * @return the time expadned vertex the edge came from
	 */
	public TimeExpandedGraphVertex getFrom() {
		return from;
	}

	/**
	 * Returns the time expanded vertex the edge goes to
	 * @return the time expadned vertex the edge goes to
	 */
	public TimeExpandedGraphVertex getTo() {
		return to;
	}
}
