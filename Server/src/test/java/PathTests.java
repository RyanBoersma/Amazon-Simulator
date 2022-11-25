import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.List;

import com.nhlstenden.amazonsimulatie.base.GraphVertex;
import com.nhlstenden.amazonsimulatie.base.Path;

public class PathTests {
	private Path path;

	private GraphVertex mockA;
	private GraphVertex mockB;

	@Before
	public void init() throws Exception {
		path = new Path();

		mockA = mock(GraphVertex.class);
		mockB = mock(GraphVertex.class);

		path.appendVertex(mockA);
		path.appendVertex(mockB);
	}
	
	@Test
	public void testGetVertices() throws Exception {
		List<GraphVertex> vertices = path.getVertices();

		assertEquals(mockA, vertices.get(0));
		assertEquals(mockB, vertices.get(1));
	}

	@Test
	public void testGetDepth() throws Exception {
		assertEquals(2, path.getDepth());
	}

	@Test
	public void testInitialCount() throws Exception {
		assertEquals(2, path.getDepth());
	}

	@Test
	public void testPrependVertex() throws Exception {
		GraphVertex mockVertex = mock(GraphVertex.class);

		path.prependVertex(mockVertex);
		assertEquals(3, path.getDepth());

		assertEquals(mockVertex, path.getVertices().get(0));
	}

	@Test
	public void testGetEndVertex() throws Exception {
		assertEquals(mockB, path.getEndVertex());
	}

	@Test
	public void testAppendVertex() throws Exception {
		GraphVertex mockVertex = mock(GraphVertex.class);

		path.appendVertex(mockVertex);
		assertEquals(3, path.getDepth());

		assertEquals(mockVertex, path.getEndVertex());
	}

	@Test
	public void testGetInversePath() throws Exception {
		path.appendVertex(mock(GraphVertex.class));
		
		Path inversePath = path.getInversePath();

		List<GraphVertex> origVertices = path.getVertices();
		List<GraphVertex> invsvertices = inversePath.getVertices();

		assertEquals(origVertices.get(0), invsvertices.get(2));
		assertEquals(origVertices.get(1), invsvertices.get(1));
		assertEquals(origVertices.get(2), invsvertices.get(0));
	}
}