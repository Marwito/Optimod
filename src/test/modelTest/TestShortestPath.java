package test.modelTest;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import junit.framework.TestCase;
import model.fileLoader.MapLoaderXML;
import model.graph.EdgeI;
import model.map.MapI;
import model.map.ShortestPath;

public class TestShortestPath extends TestCase {

	/** --- INIT AND VARIABLES --- **/
	private ShortestPath shortestPath; // shortest path from node 0 to 2
	private MapI map;
	// (should be 0->1->3->4->2)

	
	@Override
	/**
	 * This method creates a new map and a the shortest path in the map.
	 * 
	 */
	protected void setUp() throws Exception {
		// Creating the map
		try {
			map = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// Creating the shortest path
		shortestPath = new ShortestPath(map, map.getNodeById(0), map.getNodeById(2));
	}
	// end init

	@Test
	/**
	 * Testing that the constructor of the shortest path computed the correct
	 * path in the map between the starting node and the ending node and that
	 * the other informations, computed by the constructor, are correct.
	 */
	public void testConstructorComputationsGood() {
		Iterator<EdgeI> pathIterator = this.shortestPath.getPathIterator();
		EdgeI nextEdge;
		// Checking the computed path is correct
		// 0->1
		nextEdge = pathIterator.next();
		assertEquals(nextEdge.getStartNode(), map.getNodeById(0));
		assertEquals(nextEdge.getEndNode(), map.getNodeById(1));
		// 1->3
		nextEdge = pathIterator.next();
		assertEquals(nextEdge.getStartNode(), map.getNodeById(1));
		assertEquals(nextEdge.getEndNode(), map.getNodeById(3));
		// 3->4
		nextEdge = pathIterator.next();
		assertEquals(nextEdge.getStartNode(), map.getNodeById(3));
		assertEquals(nextEdge.getEndNode(), map.getNodeById(4));
		// 4->2
		nextEdge = pathIterator.next();
		assertEquals(nextEdge.getStartNode(), map.getNodeById(4));
		assertEquals(nextEdge.getEndNode(), map.getNodeById(2));

		// Checking the computed travel time is correct (we know it should be
		// 40)
		assertEquals(this.shortestPath.getTravelTime(), 40);
	}

	@Test
	/**
	 * Testing that equals() returns true if two shortest paths are the same.
	 */
	public void testEqualsOverrideGood() throws Exception {
		// Charging map for A
		MapI mapA = null;
		// Creating the map
		try {
			mapA = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		ShortestPath shortestPathA = new ShortestPath(mapA, mapA.getNodeById(0), mapA.getNodeById(2));
		// Charging map for B
		MapI mapB = null;
		// Creating the map
		try {
			mapB = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		ShortestPath shortestPathB = new ShortestPath(mapB, mapB.getNodeById(0), mapB.getNodeById(2));
		;
		// Checking the two paths are equals
		assertEquals(shortestPathA, shortestPathB);
	}

	@Test
	/**
	 * Testing that equals() returns false if two shortest paths are not the same
	 * (two edges are inverted).
	 */
	public void testEqualsOverrideBad2InvertedEdges() throws Exception {
		// Charging map for A
		MapI mapA = null;
		// Creating the map
		try {
			mapA = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		ShortestPath shortestPathA = new ShortestPath(mapA, mapA.getNodeById(0), mapA.getNodeById(2));
		// Charging map for B
		MapI mapB = null;
		// Creating the map
		try {
			mapB = new MapLoaderXML().loadMap("maps/plan3x3.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		ShortestPath shortestPathB = new ShortestPath(mapB, mapB.getNodeById(0), mapB.getNodeById(2));
		;
		// Checking the two paths are equals
		assertNotEquals(shortestPathA, shortestPathB);
	}
}
