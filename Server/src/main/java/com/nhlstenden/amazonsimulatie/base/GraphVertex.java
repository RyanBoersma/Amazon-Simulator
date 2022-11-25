package com.nhlstenden.amazonsimulatie.base;

public class GraphVertex {
	public static enum GraphVertexFacing {
		UP(Math.PI/2), 
		DOWN(-Math.PI/2), 
		LEFT(Math.PI), 
		RIGHT(0);

		private final double rotation;
		
		private GraphVertexFacing(double rotation) {
			this.rotation = rotation;
		}

		public double getRotation() {
			return this.rotation;
		}
	}

	private final double x;
	private final double y;
	private final double z;

	private final GraphVertexFacing vertexFacing;

	public GraphVertex(double x, double y, double z, GraphVertexFacing vertexFacing) {
		this.x = x;
		this.y = y;
		this.z = z;

		this.vertexFacing = vertexFacing;
	}

	/**
	 * Returns the x position of the vertex
	 * @return the x position of the vertex
	 */
	public double getX() {
		return x;
	}

	/**
	 * Returns the y position of the vertex
	 * @return the y position of the vertex
	 */
	public double getY() {
		return y;
	}

	/**
	 * Returns the z position of the vertex
	 * @return the z position of the vertex
	 */
	public double getZ() {
		return z;
	}

	/**
	 * Returns the facing of the vertex
	 * @return the facing of the vertex
	 */
	public GraphVertexFacing getVertexFacing() {
		return this.vertexFacing;
	}
}
