package model.graph;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Represents each intersection of roads, associated with coordinates.
 * @author jerome, Adrien, Arthur
 */
public interface NodeI {
	
	/**
	 * Returns the X coordinate of the node.
	 * @return the X coordinate of the node
	 */
    int getX();
    
	/**
	 * Returns the Y coordinate of the node.
	 * @return the Y coordinate of the node
	 */
    int getY();
    
	/**
	 * Returns the id of the node.
	 * @return the id of the node
	 */
    int getId();
}
