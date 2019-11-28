package model.graph;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * An edge of the map, corresponding to a possible travelling path between two
 * nodes. An edge is oriented (i.e it crossing the edge is only possible from
 * the starting node to the ending node).
 * 
 * @see model.NodeI, model.map.Map
 * @author Jerome, Arthur
 */
public interface EdgeI {

	/**
	 * Returns the starting node of the edge.
	 * 
	 * @return The starting node
	 */
	NodeI getStartNode();

	/**
	 * Returns the ending node of the edge.
	 * 
	 * @return The ending node
	 */
	NodeI getEndNode();

	/**
	 * Returns the average required time to cross the edge from the starting
	 * node to the ending node.
	 * 
	 * @return The average travel time (in seconds)
	 */
	int getAverageTravelTime();

	/**
	 * Returns the average required time to cross the edge from the starting
	 * node to the ending node.
	 * 
	 * @return The average travel time (in minutes)
	 */
	int getAverageTravelTimeMin();

	/**
	 * Returns the length of the edge.
	 * 
	 * @return The length of the edge (in meters)
	 */
	double getLength();

	/**
	 * Returns the average traveling speed of the edge.
	 * 
	 * @return The average traveling speed (in m/s)
	 */
	double getAverageSpeed();

	/**
	 * Returns the name of the road for this edge.
	 * 
	 * @return The name of the road for this edge
	 */
	public String getRoadName();

}
