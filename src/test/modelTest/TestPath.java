package test.modelTest;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import junit.framework.TestCase;
import model.graph.Edge;
import model.graph.EdgeI;
import model.graph.Node;
import model.graph.NodeI;
import model.graph.Path;

public class TestPath extends TestCase {

	/** --- INIT AND VARIABLES --- **/
	private NodeI node1 = new Node(1, 10, 20);
	private NodeI node2 = new Node(2, 15, 25);
	private NodeI node3 = new Node(3, 20, 30);
	private NodeI node4 = new Node(4, 25, 35);

	private EdgeI edgeN1toN2;
	private EdgeI edgeN2toN4;
	private EdgeI edgeN3toN1;
	private EdgeI edgeN4toN3;

	private List<EdgeI> pathEdges = new LinkedList<EdgeI>();

	private Path path;
	
	@Override
	/**
	 *  This method creates new paths between the nodes defined above.
	 */
	protected void setUp() throws Exception {
		edgeN1toN2 = new Edge(node1, node2, 102, 10, "roadN1toN2");
		edgeN2toN4 = new Edge(node2, node4, 204, 10, "roadN2toN4");
		edgeN3toN1 = new Edge(node3, node1, 301, 10, "roadN3toN1");
		edgeN4toN3 = new Edge(node4, node3, 403, 10, "roadN4toN3");
		
		pathEdges.add(edgeN1toN2);
		pathEdges.add(edgeN2toN4);
		pathEdges.add(edgeN4toN3);
		pathEdges.add(edgeN3toN1);
		path = new Path(pathEdges);
	}
	// end init

	@Test
	/**
	 * Testing if edgeIterator() returns the edges of the path, in the right order.
	 */
	public void testEdgeIteratorGood() {
		Iterator<EdgeI> edgesPath = path.getPathIterator();
		assertEquals(edgeN1toN2, edgesPath.next());
		assertEquals(edgeN2toN4, edgesPath.next());
		assertEquals(edgeN4toN3, edgesPath.next());
		assertEquals(edgeN3toN1, edgesPath.next());
	}

	@Test
	/**
	 * Testing if changePath() changes correctly the path.
	 */
	public void testChangePathGood() {
		Path pathTest = new Path(pathEdges);
		Iterator<EdgeI> edgesPath = pathTest.getPathIterator();
		assertEquals(edgeN1toN2, edgesPath.next());
		assertEquals(edgeN2toN4, edgesPath.next());
		assertEquals(edgeN4toN3, edgesPath.next());
		assertEquals(edgeN3toN1, edgesPath.next());
		// Changing the path
		List<EdgeI> newEdges = new LinkedList<EdgeI>();
		EdgeI edgeN1toN2T = null;
		EdgeI edgeN2toN3T = null;
		EdgeI edgeN3toN1T = null;
		try {
			edgeN1toN2T = new Edge(node1, node2, 102, 10, "roadN1toN2");
			edgeN2toN3T = new Edge(node2, node3, 102, 10, "roadN2toN3");
			edgeN3toN1T = new Edge(node3, node1, 301, 10, "roadN3toN1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		newEdges.add(edgeN1toN2T);
		newEdges.add(edgeN2toN3T);
		newEdges.add(edgeN3toN1T);
		pathTest.changePath(newEdges);
		// Checking the path is correctly changed
		edgesPath = pathTest.getPathIterator();
		assertEquals(edgeN1toN2T, edgesPath.next());
		assertEquals(edgeN2toN3T, edgesPath.next());
		assertEquals(edgeN3toN1T, edgesPath.next());
		// Cheking the travel time is correct
		assertEquals(pathTest.getTravelTime(), edgeN1toN2T.getAverageTravelTime() + edgeN2toN3T.getAverageTravelTime()
				+ edgeN3toN1T.getAverageTravelTime());
	}

	@Test
	/**
	 * Testing if changePath() changes the path correctly if the path is an empty path.
	 */
	public void testChangePathForEmptyPathGood() {
		Path pathTest = new Path();
		// Changing the path
		List<EdgeI> newEdges = new LinkedList<EdgeI>();
		EdgeI edgeN1toN2T = null;
		EdgeI edgeN2toN3T = null;
		EdgeI edgeN3toN1T = null;
		try {
			edgeN1toN2T = new Edge(node1, node2, 102, 10, "roadN1toN2");
			edgeN2toN3T = new Edge(node2, node3, 102, 10, "roadN2toN3");
			edgeN3toN1T = new Edge(node3, node1, 301, 10, "roadN3toN1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		newEdges.add(edgeN1toN2T);
		newEdges.add(edgeN2toN3T);
		newEdges.add(edgeN3toN1T);
		pathTest.changePath(newEdges);
		// Checking the path is correctly set
		Iterator<EdgeI> edgesPath = pathTest.getPathIterator();
		edgesPath = pathTest.getPathIterator();
		assertEquals(edgeN1toN2T, edgesPath.next());
		assertEquals(edgeN2toN3T, edgesPath.next());
		assertEquals(edgeN3toN1T, edgesPath.next());
		// Cheking the travel time is correct
		assertEquals(pathTest.getTravelTime(), edgeN1toN2T.getAverageTravelTime() + edgeN2toN3T.getAverageTravelTime()
				+ edgeN3toN1T.getAverageTravelTime());
		// Checking starting and ending nodes
		assertEquals(pathTest.getStartNode(), edgeN1toN2T.getStartNode());
		assertEquals(pathTest.getEndNode(), edgeN3toN1T.getEndNode());
	}

	@Test
	/**
	 * Testing if changePath() has an assertion error if the starting node changes.
	 */
	public void testChangePathBadStartNode() {
		Path pathTest = new Path(pathEdges);
		Iterator<EdgeI> edgesPath = pathTest.getPathIterator();
		assertEquals(edgeN1toN2, edgesPath.next());
		assertEquals(edgeN2toN4, edgesPath.next());
		assertEquals(edgeN4toN3, edgesPath.next());
		assertEquals(edgeN3toN1, edgesPath.next());
		// Changing the path
		List<EdgeI> newEdges = new LinkedList<EdgeI>();
		EdgeI edgeN1toN2T = null;
		EdgeI edgeN2toN3T = null;
		EdgeI edgeN3toN1T = null;
		try {
			edgeN1toN2T = new Edge(node1, node2, 102, 10, "roadN1toN2");
			edgeN2toN3T = new Edge(node2, node3, 102, 10, "roadN2toN3");
			edgeN3toN1T = new Edge(node3, node1, 301, 10, "roadN3toN1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		newEdges.add(edgeN2toN3T);
		newEdges.add(edgeN1toN2T);
		newEdges.add(edgeN3toN1T);
		boolean assertionErrorHappened = false;
		try {
			pathTest.changePath(newEdges); // should throw assertion error
		} catch (AssertionError assertionError) {
			assertionErrorHappened = true;
		}
		if (!assertionErrorHappened) {
			fail("Assertion error was expected");
		}

	}

	@Test
	/**
	 * Testing if getStartNode() returns the correct starting node.
	 */
	public void testGetStartNodeGood() {
		assertEquals(path.getStartNode(), edgeN1toN2.getStartNode());
	}

	@Test
	/**
	 * Testing if getEndNode() returns the correct ending node.
	 */
	public void testGetEndtNodeGood() {
		assertEquals(path.getEndNode(), edgeN3toN1.getEndNode());
	}

	@Test
	/**
	 * Testing if getTravelTime() returns the correct travel time.
	 */
	public void testGetTravelTimeGood() {
		assertEquals(path.getTravelTime(), edgeN1toN2.getAverageTravelTime() + edgeN2toN4.getAverageTravelTime()
				+ edgeN4toN3.getAverageTravelTime() + edgeN3toN1.getAverageTravelTime());
	}

	@Test
	/**
	 * Testing that equals() returns true if two path are equals.
	 */
	public void testEqualsOverrideGood() {
		// Creating pathA
		NodeI node1A = new Node(1, 10, 20);
		NodeI node2A = new Node(2, 15, 25);
		NodeI node3A = new Node(3, 20, 30);
		NodeI node4A = new Node(4, 25, 35);

		EdgeI edgeN1toN2A = null;
		EdgeI edgeN2toN4A = null;
		EdgeI edgeN3toN1A = null;
		EdgeI edgeN4toN3A = null;
		try {
			edgeN1toN2A = new Edge(node1A, node2A, 102, 10, "roadN1toN2");
			edgeN2toN4A = new Edge(node2A, node4A, 204, 10, "roadN2toN4");
			edgeN3toN1A = new Edge(node3A, node1A, 301, 10, "roadN3toN1");
			edgeN4toN3A = new Edge(node4A, node3A, 403, 10, "roadN4toN3");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<EdgeI> pathEdgesA = new LinkedList<EdgeI>();

		pathEdgesA.add(edgeN1toN2A);
		pathEdgesA.add(edgeN2toN4A);
		pathEdgesA.add(edgeN4toN3A);
		pathEdgesA.add(edgeN3toN1A);

		@SuppressWarnings("unused")
		Path pathA = new Path(pathEdgesA);

		// Creating pathB
		NodeI node1B = new Node(1, 10, 20);
		NodeI node2B = new Node(2, 15, 25);
		NodeI node3B = new Node(3, 20, 30);
		NodeI node4B = new Node(4, 25, 35);

		EdgeI edgeN1toN2B = null;
		EdgeI edgeN2toN4B = null;
		EdgeI edgeN3toN1B = null;
		EdgeI edgeN4toN3B = null;
		try {
			edgeN1toN2B = new Edge(node1B, node2B, 102, 10, "roadN1toN2");
			edgeN2toN4B = new Edge(node2B, node4B, 204, 10, "roadN2toN4");
			edgeN3toN1B = new Edge(node3B, node1B, 301, 10, "roadN3toN1");
			edgeN4toN3B = new Edge(node4B, node3B, 403, 10, "roadN4toN3");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<EdgeI> pathEdgesB = new LinkedList<EdgeI>();

		pathEdgesB.add(edgeN1toN2B);
		pathEdgesB.add(edgeN2toN4B);
		pathEdgesB.add(edgeN4toN3B);
		pathEdgesB.add(edgeN3toN1B);

		@SuppressWarnings("unused")
		Path pathB = new Path(pathEdgesB);

		// Checking the two paths are equals
		assertEquals(pathEdgesA, pathEdgesB);
	}

	@Test
	/**
	 * Testing that equals() returns false if two path are not equals (two edges
	 * are inverted).
	 */
	public void testEqualsOverrideBad2InvertedEdges() {
		// Creating pathA
		NodeI node1A = new Node(1, 10, 20);
		NodeI node2A = new Node(2, 15, 25);
		NodeI node3A = new Node(3, 20, 30);
		NodeI node4A = new Node(4, 25, 35);

		EdgeI edgeN1toN2A = null;
		EdgeI edgeN2toN4A = null;
		EdgeI edgeN3toN1A = null;
		EdgeI edgeN4toN3A = null;
		try {
			edgeN1toN2A = new Edge(node1A, node2A, 102, 10, "roadN1toN2");
			edgeN2toN4A = new Edge(node2A, node4A, 204, 10, "roadN2toN4");
			edgeN3toN1A = new Edge(node3A, node1A, 301, 10, "roadN3toN1");
			edgeN4toN3A = new Edge(node4A, node3A, 403, 10, "roadN4toN3");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<EdgeI> pathEdgesA = new LinkedList<EdgeI>();

		pathEdgesA.add(edgeN1toN2A);
		pathEdgesA.add(edgeN2toN4A);
		pathEdgesA.add(edgeN4toN3A);
		pathEdgesA.add(edgeN3toN1A);

		@SuppressWarnings("unused")
		Path pathA = new Path(pathEdgesA);

		// Creating pathB
		NodeI node1B = new Node(1, 10, 20);
		NodeI node2B = new Node(2, 15, 25);
		NodeI node3B = new Node(3, 20, 30);
		NodeI node4B = new Node(4, 25, 35);

		EdgeI edgeN1toN2B = null;
		EdgeI edgeN2toN4B = null;
		EdgeI edgeN3toN1B = null;
		EdgeI edgeN4toN3B = null;
		try {
			edgeN1toN2B = new Edge(node1B, node2B, 102, 10, "roadN1toN2");
			edgeN2toN4B = new Edge(node2B, node4B, 204, 10, "roadN2toN4");
			edgeN3toN1B = new Edge(node3B, node1B, 301, 10, "roadN3toN1");
			edgeN4toN3B = new Edge(node4B, node3B, 403, 10, "roadN4toN3");
		} catch (Exception e) {
			e.printStackTrace();
		}

		List<EdgeI> pathEdgesB = new LinkedList<EdgeI>();

		pathEdgesB.add(edgeN1toN2B);
		pathEdgesB.add(edgeN2toN4B);
		pathEdgesB.add(edgeN3toN1B);
		pathEdgesB.add(edgeN4toN3B);

		@SuppressWarnings("unused")
		Path pathB = new Path(pathEdgesB);

		// Checking the two paths are equals
		assertNotEquals(pathEdgesA, pathEdgesB);
	}

}
