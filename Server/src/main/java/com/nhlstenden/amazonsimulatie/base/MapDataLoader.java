package com.nhlstenden.amazonsimulatie.base;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.nhlstenden.amazonsimulatie.base.GraphVertex.GraphVertexFacing;

public class MapDataLoader {
	class MapDataFormat {
		public List<VertexFormat> pathNodes; 
		public List<VertexFormat> dockNodes; 
		public List<VertexFormat> storageNodes; 
		public List<VertexFormat> chargeNodes; 

		public List<EdgeFormat> connections;
	} 

	class VertexFormat {
		public double x;
		public double y;
		public double z;
		public int id;
	}

	class EdgeFormat {
		public int from;
		public int to;
	}
	
	/**
	 * Loads and returns mapData
	 * @return mapData
	 */
	public MapData load() {
		try {
			Gson gson = new Gson();

			JsonReader reader = new JsonReader(new FileReader("graph.json"));
			MapDataFormat graphFormat = gson.fromJson(reader, MapDataFormat.class);

			MapData mapData = new MapData();
			
		
			Map<Integer, GraphVertex> vertexLookup = new HashMap<Integer, GraphVertex>();

			for (VertexFormat vertexFormat : graphFormat.pathNodes) {
				GraphVertex vertex = new GraphVertex(vertexFormat.x, vertexFormat.y, vertexFormat.z, GraphVertexFacing.DOWN);

				vertexLookup.put(vertexFormat.id, vertex);
				mapData.addPathVertex(vertex);
			}

			for (VertexFormat vertexFormat : graphFormat.dockNodes) {
				GraphVertex vertex = new GraphVertex(vertexFormat.x, vertexFormat.y, vertexFormat.z, GraphVertexFacing.DOWN);

				vertexLookup.put(vertexFormat.id, vertex);
				mapData.addDockVertex(vertex);
			}

			for (VertexFormat vertexFormat : graphFormat.storageNodes) {
				GraphVertex vertex = new GraphVertex(vertexFormat.x, vertexFormat.y, vertexFormat.z, GraphVertexFacing.DOWN);

				vertexLookup.put(vertexFormat.id, vertex);
				mapData.addStorageVertex(vertex);
			}

			for (VertexFormat vertexFormat : graphFormat.chargeNodes) {
				GraphVertex vertex = new GraphVertex(vertexFormat.x, vertexFormat.y, vertexFormat.z, GraphVertexFacing.DOWN);

				vertexLookup.put(vertexFormat.id, vertex);
				mapData.addChargeVertex(vertex);
			}

			for (EdgeFormat connection : graphFormat.connections) {
				GraphVertex from = vertexLookup.get(connection.from);
				GraphVertex to = vertexLookup.get(connection.to);
					
				GraphEdge edge = new GraphEdge(from, to);

				mapData.addEdge(edge);
			}

			{
				Path path = new Path();
				for (int i = 0; i < 11; i++)
					path.appendVertex(new GraphVertex(0.625, 0, 3.25 + i, GraphVertexFacing.DOWN));
				MapData.Road road = mapData.new Road(mapData.getDockVertices().get(0), path.getInversePath(), path);
				mapData.getRoads().add(road);
			}

			{
				Path path = new Path();
				for (int i = 0; i < 11; i++)
					path.appendVertex(new GraphVertex(0.625 + 1.25, 0, 3.25 + i, GraphVertexFacing.DOWN));
				MapData.Road road = mapData.new Road(mapData.getDockVertices().get(1), path.getInversePath(), path);
				mapData.getRoads().add(road);
			}

			{
				Path path = new Path();
				for (int i = 0; i < 11; i++)
					path.appendVertex(new GraphVertex(0.625 + 3.75, 0, 3.25 + i, GraphVertexFacing.DOWN));
				MapData.Road road = mapData.new Road(mapData.getDockVertices().get(2), path.getInversePath(), path);
				mapData.getRoads().add(road);
			}

			{
				Path path = new Path();
				for (int i = 0; i < 11; i++)
					path.appendVertex(new GraphVertex(0.625 + 5, 0, 3.25 + i, GraphVertexFacing.DOWN));
				MapData.Road road = mapData.new Road(mapData.getDockVertices().get(3), path.getInversePath(), path);
				mapData.getRoads().add(road);
			}

			return mapData;
		} catch (Exception e) {
			e.printStackTrace();

			return null;
		}
	}
}
