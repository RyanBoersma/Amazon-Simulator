import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;

import com.nhlstenden.amazonsimulatie.base.Graph;
import com.nhlstenden.amazonsimulatie.base.GraphEdge;
import com.nhlstenden.amazonsimulatie.base.GraphVertex;

public class GraphTests {
	private Graph graph;

	private GraphVertex mockVertexA;
	private GraphVertex mockVertexB;

	private GraphEdge mockEdgeA;

	@Before
	public void init() throws Exception {
		graph = new Graph();

		mockVertexA = mock(GraphVertex.class);
		mockVertexB = mock(GraphVertex.class);

		mockEdgeA = mock(GraphEdge.class);
		when(mockEdgeA.getFrom()).thenReturn(mockVertexB);

		graph.addVertex(mockVertexA);
		graph.addVertex(mockVertexB);

		graph.addEdge(mockEdgeA);
	}

	@Test
	public void testGetVertices() throws Exception {
		List<GraphVertex> vertices = graph.getVertices();

		assertEquals(mockVertexA, vertices.get(0));
		assertEquals(mockVertexB, vertices.get(1));
	}
	
	@Test
	public void testAddVertex() throws Exception {
		GraphVertex mockVertexC = mock(GraphVertex.class);
		graph.addVertex(mockVertexC);

		assertEquals(3, graph.getVertices().size());
	}

	@Test
	public void testGetEdges() throws Exception {
		assertEquals(1, graph.getEdges().size());
	}

	@Test
	public void testAddEdge() throws Exception {
		graph.addEdge(mock(GraphEdge.class));

		assertEquals(2, graph.getEdges().size());
	}

	@Test
	public void getEdgesLookup() throws Exception {
		Map<GraphVertex, List<GraphEdge>> edgesLookup = graph.getEdgesLookup();
		assertEquals(edgesLookup.get(mockVertexB).get(0), mockEdgeA);
	}
}