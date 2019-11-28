
package test.modelTest;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;

import model.graph.Edge;
import model.graph.EdgeI;
import model.graph.Node;
import model.graph.NodeI;
import model.map.Map;

import org.junit.Test;

public class TestMap {

	@Test
	/**
	 * Testing that the default constructor of the class Map creates an empty Map.
	 */
	public void testEmptyMap() {
		Map map = new Map();
		assertFalse(map.edgeIterator().hasNext());
		assertFalse(map.nodeIterator().hasNext());
	}

	@Test
	/**
	 * Testing that the edgeIterator method returns all the edges and only them.
	 */
	public void testEdgeIterator() throws Exception {
		Set<EdgeI> edgesInMap = new HashSet<EdgeI>();
		Map map = createMapNodesAndEdges(new HashSet<NodeI>(), edgesInMap);
		assertTrue(iteratorEquals(map.edgeIterator(), edgesInMap));
	}

	@Test
	/**
	 * Testing that the nodeIterator method returns all the nodes and only them.
	 */
	public void testNodeIterator() throws Exception {
		Set<NodeI> nodesInMap = new HashSet<NodeI>();
		Map map = createMapNodesAndEdges(nodesInMap, new HashSet<EdgeI>());
		assertTrue(iteratorEquals(map.nodeIterator(), nodesInMap));
	}

	@Test
	/**
	 * Testing that the full constructor of the class Map creates a Map with all the desired
	 * edges and nodes.
	 */
	public void testFullMapConstructorGood() throws Exception {
		Set<NodeI> nodesInMap = new HashSet<NodeI>();
		Set<EdgeI> edgesInMap = new HashSet<EdgeI>();
		createNodesAndEdges(nodesInMap, edgesInMap);

		// Creating the map
		Map map = new Map(nodesInMap, edgesInMap);
		// Checking the map is correct
		assertTrue(iteratorEquals(map.nodeIterator(), nodesInMap));
		assertTrue(iteratorEquals(map.edgeIterator(), edgesInMap));
	}

	@Test
	/**
	 * Testing that the full constructor of the class Map creates a Map with all the desired
	 * edges and nodes (missing node scenario).
	 */
	public void testFullMapConstructorBadNode() throws Exception {
		Set<NodeI> nodesInMap = new HashSet<NodeI>();
		Set<EdgeI> edgesInMap = new HashSet<EdgeI>();
		createNodesAndEdges(nodesInMap, edgesInMap);
		// Creating the map
		Map map = new Map(nodesInMap, edgesInMap);
		// Removing the first node of the map
		Set<NodeI> nodesNotInMap = new HashSet<NodeI>(nodesInMap);
		if (!nodesInMap.iterator().hasNext())
			throw new Exception("nodesInMap is empty");
		else
			nodesNotInMap.remove(nodesInMap.iterator().next());
		// Checking the map does not contain this node anymore
		assertFalse(iteratorEquals(map.nodeIterator(), nodesNotInMap));
		assertTrue(iteratorEquals(map.nodeIterator(), nodesInMap));
		assertTrue(iteratorEquals(map.edgeIterator(), edgesInMap));
	}

	@Test
	/**
	 * Testing that the full constructor of the class Map creates a Map with all the desired
	 * edges and nodes (missing edge scenario).
	 */
	public void testFullMapConstructorBadEdge() throws Exception {
		Set<NodeI> nodesInMap = new HashSet<NodeI>();
		Set<EdgeI> edgesInMap = new HashSet<EdgeI>();
		createNodesAndEdges(nodesInMap, edgesInMap);
		// Creating the map
		Map map = new Map(nodesInMap, edgesInMap);
		// Removing the first edge of the map
		Set<EdgeI> edgesNotInMap = new HashSet<EdgeI>(edgesInMap);
		if (!edgesInMap.iterator().hasNext())
			throw new Exception("edgesInMap is empty");
		else
			edgesNotInMap.remove(edgesInMap.iterator().next());
		// Checking the map does not contain this edge anymore
		assertFalse(iteratorEquals(map.edgeIterator(), edgesNotInMap));
		assertTrue(iteratorEquals(map.nodeIterator(), nodesInMap));
		assertTrue(iteratorEquals(map.edgeIterator(), edgesInMap));
	}

	@Test
	/**
	 * Testing the Dijkstra algorithm with a created graph example.
	 */
	public void testDijkstra() {
		// Creating nodes
		NodeI node1 = new Node(1, 10, 20);
		NodeI node2 = new Node(2, 15, 25);
		NodeI node3 = new Node(3, 20, 30);
		NodeI node4 = new Node(4, 25, 35);

		Set<NodeI> listNodes = new HashSet<NodeI>();
		listNodes.add(node1);
		listNodes.add(node2);
		listNodes.add(node3);
		listNodes.add(node4);
		// Creating edges
		EdgeI edgeN1toN2 = null;
		EdgeI edgeN1toN3 = null;
		EdgeI edgeN2toN3 = null;
		EdgeI edgeN2toN4 = null;
		EdgeI edgeN3toN2 = null;
		EdgeI edgeN3toN4 = null;
		EdgeI edgeN4toN2 = null;
		EdgeI edgeN4toN3 = null;
		try {
			edgeN1toN2 = new Edge(node1, node2, 800, 10, "roadN1toN2");
			edgeN1toN3 = new Edge(node1, node3, 200, 10, "roadN1toN3");
			edgeN2toN3 = new Edge(node2, node3, 400, 10, "roadN2toN3");
			edgeN2toN4 = new Edge(node2, node4, 100, 10, "roadN2toN4");
			edgeN3toN2 = new Edge(node3, node2, 300, 10, "roadN3toN2");
			edgeN3toN4 = new Edge(node3, node4, 200, 10, "roadN3toN4");
			edgeN4toN2 = new Edge(node4, node2, 200, 10, "roadN4toN2");
			edgeN4toN3 = new Edge(node4, node3, 500, 10, "roadN4toN3");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Set<EdgeI> listEdges = new HashSet<EdgeI>();
		listEdges.add(edgeN1toN2);
		listEdges.add(edgeN1toN3);
		listEdges.add(edgeN2toN3);
		listEdges.add(edgeN2toN4);
		listEdges.add(edgeN3toN2);
		listEdges.add(edgeN3toN4);
		listEdges.add(edgeN4toN2);
		listEdges.add(edgeN4toN3);

		// Creating the map
		Map map = new Map(listNodes, listEdges);

		/* Checking Dijkstra sends the wanted datas */
		java.util.Map<NodeI, Entry<EdgeI, Double>> resDijkstra = map.dijkstra(node1);
		// For node 1
		assertEquals(resDijkstra.get(node1), null);
		// Entry is null getValue have no sence.
		// assertEquals(resDijkstra.get(node1).getValue(), 0, 0.001);
		// For node 2
		assertEquals(resDijkstra.get(node2).getKey(), edgeN3toN2);
		assertEquals(resDijkstra.get(node2).getValue(), 50, 0.001);
		// For node 3
		assertEquals(resDijkstra.get(node3).getKey(), edgeN1toN3);
		assertEquals(resDijkstra.get(node3).getValue(), 20, 0.001);
		// For node 4
		assertEquals(resDijkstra.get(node4).getKey(), edgeN3toN4);
		assertEquals(resDijkstra.get(node4).getValue(), 40, 0.001);
	}

	@Test
	/**
	 * Testing that getSize() returns the desired size of the map.
	 */
	public void testGetSize() {
		Set<NodeI> nodesInMap = new HashSet<NodeI>();
		Set<EdgeI> edgesInMap = new HashSet<EdgeI>();
		nodesInMap.add(new Node(1, 0, 0));
		nodesInMap.add(new Node(2, 10, 0));
		nodesInMap.add(new Node(3, 0, 20));
		nodesInMap.add(new Node(4, 10, 20));

		// Creating the map
		Map map = new Map(nodesInMap, edgesInMap);
		// Checking the map is correct
		assertEquals(map.getSize().getWidth(), 0.001, 10);
		assertEquals(map.getSize().getHeight(), 0.001, 20);
	}

	@Test
	/**
	 * Testing that getNodeById() returns the desired node.
	 */
	public void testGetNodeById() {
		Set<NodeI> nodesInMap = new HashSet<NodeI>();
		Set<EdgeI> edgesInMap = new HashSet<EdgeI>();
		NodeI node1 = new Node(1, 0, 0);
		NodeI node2 = new Node(2, 10, 0);
		NodeI node3 = new Node(3, 0, 20);
		NodeI node4 = new Node(4, 10, 20);
		nodesInMap.add(node1);
		nodesInMap.add(node2);
		nodesInMap.add(node3);
		nodesInMap.add(node4);

		// Creating the map
		Map map = new Map(nodesInMap, edgesInMap);
		// Checking the map is correct
		assertSame(map.getNodeById(2), node2);
	}

	@Test
	/**
	 * Testing that addNode() adds a node properly to a Map.
	 */
	public void testaddNode() {
		Set<NodeI> nodesInMap = new HashSet<NodeI>();
		Set<EdgeI> edgesInMap = new HashSet<EdgeI>();
		NodeI node1 = new Node(1, 0, 0);

		// Creating the map
		Map map = new Map(new HashSet<NodeI>(nodesInMap), new HashSet<EdgeI>(edgesInMap));
		// Checking the map is correct
		assertSame(map.getNodeById(1), null);
		map.addNode(node1);
		assertSame(map.getNodeById(1), node1);
	}

	@Test
	/**
	 * Testing that addEdge() adds an edge properly to a Node.
	 */
	public void testaddEdge() throws Throwable {
		Set<NodeI> nodesInMap = new HashSet<NodeI>();
		Set<EdgeI> edgesInMap = new HashSet<EdgeI>();

		// Creating the map
		Map map = createMapNodesAndEdges(nodesInMap, edgesInMap);
		// Checking the map is correct
		assertTrue(iteratorEquals(map.edgeIterator(), edgesInMap));

		// Getting the 2 first nodes
		Iterator<NodeI> nodeIterator = map.nodeIterator();
		NodeI startNode = null;
		NodeI endNode = null;
		if (nodeIterator.hasNext()) { // getting start node
			startNode = nodeIterator.next();
		} else {
			throw new Throwable("Need a start node");
		}
		if (nodeIterator.hasNext()) { // getting ending node
			endNode = nodeIterator.next();
		} else {
			throw new Throwable("Need an ending node");
		}
		// adding the edge and checking the map is correct
		EdgeI edgeAdded = new Edge(startNode, endNode, 1000, 10, "aRoadName");
		map.addEdge(edgeAdded);
		assertFalse(iteratorEquals(map.edgeIterator(), edgesInMap));

		edgesInMap.add(edgeAdded);
		assertTrue(iteratorEquals(map.edgeIterator(), edgesInMap));
	}

	@Test
	/**
	 * Testing that equals() return true if two maps are the same.
	 */
	public void testEqualsOverrideGood() throws Exception {
		// Creating a map A
		Set<NodeI> nodesInMapA = new HashSet<NodeI>();
		Set<EdgeI> edgesInMapA = new HashSet<EdgeI>();
		createNodesAndEdges(nodesInMapA, edgesInMapA);
		Map mapA = new Map(nodesInMapA, edgesInMapA);
		// Creating a map B
		Set<NodeI> nodesInMapB = new HashSet<NodeI>();
		Set<EdgeI> edgesInMapB = new HashSet<EdgeI>();
		createNodesAndEdges(nodesInMapB, edgesInMapB);
		Map mapB = new Map(nodesInMapB, edgesInMapB);
		// Checking the two map are equals
		assertTrue(mapA.equals(mapB));
	}

	/****************** -- PRIVATE -- ******************/

	/**
	 * Tests if an iterator (without duplicates) contains a set of given
	 * objects. Return false if the iterator contains duplicated objects or if
	 * the iterator contains an object which is not in the given set of objects.
	 * 
	 * @param it The tested iterator
	 * @param objects The set of objects
	 * @return true if the iterator contains exactly the set of object
	 * @throws Exception
	 * 
	 */
	private <T> boolean iteratorEquals(Iterator<T> it, Set<T> objects) throws Exception {
		Set<T> itWithoutDuplicate = new HashSet<T>();
		while (it.hasNext()) {
			if (!itWithoutDuplicate.add(it.next()))
				throw new Exception("The given iterator 'contains' duplicates");
		}
		return objects.equals(itWithoutDuplicate);
	}

	/**
	 * Factory method for a map and a set of associated nodes and edges.
	 * 
	 * @param nodes Reference to store the created nodes
	 * @param edges Reference to store the created edges
	 */
	private Map createMapNodesAndEdges(Set<NodeI> nodes, Set<EdgeI> edges) {
		createNodesAndEdges(nodes, edges);
		return new Map(nodes, edges);
	}

	/**
	 * Empty some node sets and edge sets and then fill them logically (the nodes
	 * of the edges are in the node set).
	 * 
	 * @param nodes Reference for the nodes to create
	 * @param edges Reference for the edges to create
	 */
	private void createNodesAndEdges(Set<NodeI> nodes, Set<EdgeI> edges) {
		// Nodes
		for (NodeI n : nodes) {
			nodes.remove(n);
		}
		NodeI startNode1 = new Node(10, 20, 1);
		NodeI endNode1 = new Node(40, 50, 2);
		NodeI startNode2 = new Node(15, 25, 3);
		NodeI endNode2 = new Node(45, 55, 4);
		nodes.add(startNode1);
		nodes.add(endNode1);
		nodes.add(startNode2);
		nodes.add(endNode2);

		// Edges
		for (EdgeI e : edges) {
			edges.remove(e);
		}
		EdgeI edge1;
		EdgeI edge2;
		try {
			edge1 = new Edge(startNode1, endNode1, 500, 10, "edge1");
			edge2 = new Edge(startNode2, endNode2, 100, 8, "edge2");
			edges.add(edge1);
			edges.add(edge2);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}