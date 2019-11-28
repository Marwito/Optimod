package model.map;

import java.awt.Dimension;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import model.graph.EdgeI;
import model.graph.NodeI;

/**
 * This class stands for a set of nodes and edges, and is consequently a kind of
 * graph.
 * 
 * @author Adrien, Arthur
 *
 */
public class Map implements MapI {

	private Set<NodeI> nodes = new HashSet<NodeI>();
	private Set<EdgeI> edges = new HashSet<EdgeI>();
	// A set of already computed shortest path with Dijkstra for all the nodes
	// of the map. Allow not to recalculate already computed Dijkstra.
	private java.util.Map<NodeI, java.util.Map<NodeI, Entry<EdgeI, Double>>> alreadyComputedDijkstras = new HashMap<>();

	/**
	 * Constructor Creates an empty map.
	 */
	public Map() {
	}

	/**
	 * Constructor Creates a map with a given set of nodes and edges
	 * 
	 * @param listNodes
	 *            the set of nodes of the created map (by copy)
	 * @param listEdges
	 *            the set of edges of the created map (by copy) TODO:
	 *            precondition: the node in the edges are in the node list
	 */
	public Map(Set<NodeI> listNodes, Set<EdgeI> listEdges) {
		nodes = new HashSet<NodeI>(listNodes);
		edges = new HashSet<EdgeI>(listEdges);
	}

	/**
	 * Creates a map with a given set of nodes
	 * 
	 * @param listNodes
	 *            the set of nodes of the created map
	 */
	public Map(Set<NodeI> listNodes) {
		nodes = new HashSet<NodeI>(listNodes);
	}

	/**
	 * {@inheritDoc}
	 */
	public java.util.Map<NodeI, Entry<EdgeI, Double>> dijkstra(NodeI nodeBegin) {
		// Checking the Dijkstra was not already computed for this node
		if (this.alreadyComputedDijkstras.containsKey(nodeBegin)) {
			return this.alreadyComputedDijkstras.get(nodeBegin);
		} else {
			Dijkstra dijkstra = new Dijkstra(edges);
			dijkstra.execute(nodeBegin);
			// Putting the result in the computed Dijkstras of this map
			this.alreadyComputedDijkstras.put(nodeBegin, dijkstra.getResult());
			return this.alreadyComputedDijkstras.get(nodeBegin);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public NodeI getNodeById(int id) {
		NodeI returnValue = null;
		for (NodeI node : nodes) {
			if (node.getId() == id) {
				returnValue = node;
			}
		}

		return returnValue;
	}

	/**
	 * {@inheritDoc}
	 */
	public Dimension getSize() {
		int minX = 0, minY = 0, maxX = 0, maxY = 0;
		for (NodeI node : nodes) {
			if (minX > node.getX())
				minX = node.getX();
			if (minY > node.getY())
				minY = node.getY();
			if (maxX < node.getX())
				maxX = node.getX();
			if (maxY < node.getY())
				maxY = node.getY();
		}
		return new Dimension(maxX - minX, maxY - minY);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addNode(NodeI node) {
		nodes.add(node);
	}

	/**
	 * {@inheritDoc}
	 */
	public void addEdge(EdgeI edge) throws Exception {
		for (EdgeI currentEdge : edges) {
			if (currentEdge.getStartNode() == edge.getStartNode() && currentEdge.getEndNode() == edge.getEndNode()) {
				throw new Exception("There musn't be two edges with the same start node and end node.");
			}
		}
		edges.add(edge);
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<NodeI> nodeIterator() {
		Set<NodeI> immutableNodeSet = Collections.unmodifiableSet(nodes);
		return immutableNodeSet.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterator<EdgeI> edgeIterator() {
		Set<EdgeI> immutableEdgesSet = Collections.unmodifiableSet(edges);
		return immutableEdgesSet.iterator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		String mapString = "<reseau>" + "\r\n";

		for (NodeI node : this.nodes) {
			mapString += node + "\r\n";
		}

		for (EdgeI edge : this.edges) {
			mapString += edge + "\r\n";
		}

		mapString += "</reseau>" + "\r\n";

		return mapString;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Map))
			return false;
		// Comparing the map with the other map
		Map otherMap = (Map) other;
		if (!this.nodes.equals(otherMap.nodes)) {
			return false;
		} else if (!this.edges.equals(otherMap.edges)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		Iterator<NodeI> nodeIterator = this.nodeIterator();
		Iterator<EdgeI> edgeIterator = this.edgeIterator();
		int hashCode = 0;
		if (nodeIterator.hasNext()) {
			hashCode = nodeIterator.next().hashCode();
		}
		if (edgeIterator.hasNext()) {
			hashCode += edgeIterator.next().hashCode();
		}
		return hashCode;
	}
}
