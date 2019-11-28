package model.map;

import java.awt.Dimension;
import java.util.Iterator;
import java.util.Map.Entry;

import model.graph.EdgeI;
import model.graph.NodeI;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Interface that ensure some can iterate over the nodes and the edges of the
 * map which implements this interface.
 * 
 * @author jerome, Arthur
 */
public interface MapI {

	/**
	 * Returns a const iterator over the nodes of the Map.
	 * 
	 * @return a const iterator over the nodes of the Map
	 */
	public Iterator<NodeI> nodeIterator();

	/**
	 * Returns a const iterator over the edges of the Map.
	 * 
	 * @return a const iterator over the edges of the Map
	 */
	public Iterator<EdgeI> edgeIterator();

	/**
	 * Returns the dimension of the map (difference beetween xMax and xMin, yMax
	 * and yMin)
	 * 
	 * @return the dimension of the map
	 */
	public Dimension getSize();

	/**
	 * 
	 * @param adresse
	 * @return
	 * @throws Throwable
	 */
	public NodeI getNodeById(int adresse);

	/**
	 * Adds a node to the map.
	 * 
	 * @param postcondition:
	 *            the node is added
	 */
	public void addNode(NodeI node);

	/**
	 * Adds an edge to the map.
	 * 
	 * @param edge
	 *            the edge to add
	 * @throws Exception
	 */
	public void addEdge(EdgeI edge) throws Exception;

	/**
	 * Calculates the shortest path in the map, starting from a given node.
	 * 
	 * @param nodeBegin
	 *            the node for which we want the shortest paths
	 * @return an HashMap<Node, Edge> ; for each node is associated his
	 *         predecessor (the starting node of the associated edge) TODO:
	 *         POSTCONDITION: the entry for the nodeBegin is null (because it
	 *         would have no sense)
	 */
	public java.util.Map<NodeI, Entry<EdgeI, Double>> dijkstra(NodeI nodeBegin);

}
