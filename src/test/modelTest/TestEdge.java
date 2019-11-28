package test.modelTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

import org.junit.Test;

import model.graph.Edge;
import model.graph.EdgeI;
import model.graph.Node;
import model.graph.NodeI;

/**
 * 
 * @author Arthur
 * @see model.graph.Edge Tests for the Edge class
 */
public class TestEdge {

	@Test
	/**
	 * Testing if getStartNode() returns the desired Node. The desired Node is
	 * the start Node of an edge.
	 */
	public void getStartNodeGood() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 1000, 10, "aRoadName");
			assertSame(edge.getStartNode(), startNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getEndNode() returns the desired Node. The desired Node is the
	 * end Node of an edge.
	 */
	public void getEndNodeGood() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 1000, 10, "aRoadName");
			assertSame(edge.getEndNode(), endNode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getLength() returns the desired length. The desired length is
	 * the length of an edge.
	 */
	public void getLengthGood() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 1000, 10, "aRoadName");
			assertEquals(edge.getLength(), 0.001, 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getAverageTravelTimeMin() returns the correct time. The test
	 * is made for an integer precision calculation.
	 */
	public void getAverageTravelTimeMinGoodT1() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 1000, 2.8, "aRoadName");
			assertEquals(edge.getAverageTravelTimeMin(), 6);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getAverageTravelTimeMin() returns the correct time. The test
	 * is made for a double precision calculation.
	 */
	public void getAverageTravelTimeMinGoodT2() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 1000, 2.2, "aRoadName");
			assertEquals(edge.getAverageTravelTimeMin(), 8);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getAverageTravelTime() returns the correct time. The test is
	 * made for an integer precision calculation.
	 */
	public void getAverageTravelTimeGoodT1() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 1000, 2.2, "aRoadName");
			assertEquals(edge.getAverageTravelTime(), 455);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Second test to test if getAverageTravelTime() returns the correct time.
	 * The test is made for a double precision calculation.
	 */
	public void getAverageTravelTimeGoodT2() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 800, 10, "aRoadName");
			assertEquals(edge.getAverageTravelTime(), 80);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getAverageSpeed() returns the correct average speed.
	 */
	public void getAverageSpeedGood() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 800, 10, "aRoadName");
			assertEquals(edge.getAverageSpeed(), 0.01, 800);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getAverageSpeed() returns the correct average speed, for a
	 * double precision speed.
	 */
	public void getAverageAverageSpeedGoodLittle() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		EdgeI edge;
		try {
			edge = new Edge(startNode, endNode, 1000, 1, "aRoadName");
			assertEquals(edge.getAverageSpeed(), 0.01, 1);
			System.out.println(edge);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getRoadNameGood() returns the desired road name.
	 */
	public void getRoadNameGood() {
		NodeI startNode = new Node(10, 10, 1);
		NodeI endNode = new Node(20, 20, 2);
		try {
			EdgeI edge = new Edge(startNode, endNode, 800, 10, "aRoadName");
			assertEquals("aRoadName", edge.getRoadName());
			;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that equals() return true if two edges are equals.
	 */
	public void testEqualsOverrideGood() {
		NodeI startNode1 = new Node(10, 10, 1);
		NodeI endNode1 = new Node(20, 20, 2);
		EdgeI edge1;
		EdgeI edge2;
		try {
			edge1 = new Edge(startNode1, endNode1, 800, 10, "aRoadName");
			NodeI startNode2 = new Node(10, 10, 1);
			NodeI endNode2 = new Node(20, 20, 2);
			edge2 = new Edge(startNode2, endNode2, 800, 10, "aRoadName");
			assertEquals(edge1, edge2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that equals() return false if two edges are not equals, if they
	 * have a different ending node.
	 */
	public void testEqualsOverrideBadEndNode() {
		NodeI startNode1 = new Node(10, 10, 1);
		NodeI endNode1 = new Node(20, 20, 2);
		EdgeI edge1;
		EdgeI edge2;
		try {
			edge1 = new Edge(startNode1, endNode1, 800, 10, "aRoadName");
			NodeI startNode2 = new Node(10, 10, 1);
			NodeI endNode2 = new Node(20, 20, 3);
			edge2 = new Edge(startNode2, endNode2, 800, 10, "aRoadName");
			assertNotEquals(edge1, edge2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
