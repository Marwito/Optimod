package model.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a path, which is a succession of edges with a starting
 * node and an ending node.
 * 
 * @author Arthur
 *
 */
public class Path {
	private List<EdgeI> path;
	private int travelTime;
	private NodeI startNode;
	private NodeI endNode;

	/**
	 * Creates a path with the given ordered list of edges. The starting node of
	 * the path is the starting node of the first edge, and the ending node is
	 * the ending node of the last edge.
	 * 
	 * @param edges
	 *            the path, taken in the given order TODO: POSTCONDITION: the
	 *            edges 'create' a path (=follow each other) (with cofoja)
	 */
	public Path(List<EdgeI> edges) {
		startNode = edges.get(0).getStartNode();
		endNode = edges.get(edges.size() - 1).getEndNode();
		changePath(edges);
	}

	/**
	 * Creates an empty path
	 */
	public Path() {
		this.path = new ArrayList<EdgeI>();
		this.travelTime = 0;
		this.startNode = null;
		this.endNode = null;
	}

	/**
	 * Returns a const iterator over edges, which represents the path.
	 * 
	 * @return a const iterator over edges, which represents the path
	 */
	public Iterator<EdgeI> getPathIterator() {
		List<EdgeI> immutableEdgeList = Collections.unmodifiableList(path);
		return immutableEdgeList.iterator();
	}

	/**
	 * Returns a list of edges of a delivery.
	 * 
	 * @return a path of edges.
	 */
	public List<EdgeI> getPath() {

		return path;
	}

	/**
	 * Changes the path between the starting node and the ending node.
	 * 
	 * @param newPath
	 *            the list of new edges of which the path is composed. TODO:
	 *            POSTCONDTION: the new edges create a path (=follow each other)
	 *            from the starting node, till the ending node. (with cofoja)
	 */
	public void changePath(List<EdgeI> newPath) {
		// A path can be created empty
		if (startNode == null && endNode == null) {
			this.startNode = newPath.get(0).getStartNode();
			this.endNode = newPath.get(newPath.size() - 1).getEndNode();
		}
		// Assert for start node and end node
		assert(newPath.get(0).getStartNode().equals(this.getStartNode())
				&& newPath.get(newPath.size() - 1).getEndNode().equals(this.getEndNode()));

		path = new ArrayList<EdgeI>(newPath);
		// Computing the travel time
		travelTime = 0;
		for (EdgeI e : newPath) {
			travelTime += e.getAverageTravelTime();
		}
	}

	/**
	 * Returns the total time to travel the path from the start to the end (in
	 * seconds)
	 * 
	 * @return the total travel time to travel the path (in seconds)
	 */
	public int getTravelTime() {
		return travelTime;
	}

	/**
	 * Returns the starting node of the path.
	 * 
	 * @return the starting node of the path
	 */
	public NodeI getStartNode() {
		return startNode;
	}

	/**
	 * Returns the ending node of the path.
	 * 
	 * @return the ending node of the path
	 */
	public NodeI getEndNode() {
		return endNode;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		String returnedString = "(\n\tTotal travel time: " + travelTime + "\n";
		returnedString += "\tPath: < ";
		for (EdgeI edge : path) {
			returnedString += edge + ", ";
		}
		returnedString.substring(0, returnedString.length() - 2);
		returnedString += " > \n)";
		return returnedString;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Path))
			return false;
		// Comparing the map with the other map
		Path otherPath = (Path) other;
		return otherPath.path.equals(this.path) && otherPath.getTravelTime() == this.getTravelTime()
				&& otherPath.getStartNode().equals(this.getStartNode())
				&& otherPath.getEndNode().equals(this.getEndNode());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return this.getStartNode().getId() + this.getEndNode().getId();
	}
}