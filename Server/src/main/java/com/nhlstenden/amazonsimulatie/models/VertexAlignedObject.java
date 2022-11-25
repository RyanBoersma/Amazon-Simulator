package com.nhlstenden.amazonsimulatie.models;

import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.GraphVertex.GraphVertexFacing;

public abstract class VertexAlignedObject implements Object3D {
	private GraphVertex vertex;
	private GraphVertexFacing vertexFacing;

	public VertexAlignedObject(GraphVertex vertex, GraphVertexFacing graphVertexFacing) {
		setVertex(vertex);
		setVertexFacing(vertexFacing);
	}

	/**
	 * Returns the vertex facing
	 * @return the vertex facing
	 */
	public GraphVertexFacing getVertexFacing() {
		return vertexFacing;
	}

	/**
	 * Sets the vertexfacing
	 * @param vertexFacing
	 */
	public void setVertexFacing(GraphVertexFacing vertexFacing) {
		this.vertexFacing = vertexFacing;
	}

	/**
	 * Gets the vertex
	 * @return the vertex
	 */
	public GraphVertex getVertex() {
		return vertex;
	}

	/**
	 * Sets the vertex
	 * @param vertex
	 */
	public void setVertex(GraphVertex vertex) {
		this.vertex = vertex;
	}

	@Override
	public abstract String getUUID();

	@Override
	public abstract String getType();

	@Override
	public double getX() {
		GraphVertex vertex = getVertex();
		return vertex == null ? 0 : getVertex().getX();
	}

	@Override
	public double getY() {
		GraphVertex vertex = getVertex();
		return vertex == null ? 0 : getVertex().getY();
	}

	@Override
	public double getZ() {
		GraphVertex vertex = getVertex();
		return vertex == null ? 0 : vertex.getZ();
	}

	@Override
	public double getRotationX() {
		return 0;
	}

	@Override
	public double getRotationY() {
		GraphVertexFacing vertexFacing = getVertexFacing();
		return vertexFacing == null ? 0 : vertexFacing.getRotation();
	}

	@Override
	public double getRotationZ() {
		return 0;
	}
}
