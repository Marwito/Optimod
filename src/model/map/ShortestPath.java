package model.map;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;

import model.graph.EdgeI;
import model.graph.NodeI;
import model.graph.Path;

/**
 * This class represents for a given map, the shortest path between two nodes.
 * The path is composed of a list of edges, which is ordered and corresponds to
 * the the shortest path from the starting node to the ending node.
 *
 */
public class ShortestPath extends Path {
	private MapI map;

	/**
	 * Creates a shortest path for a given map. The path is automatically
	 * computed between the given starting node and the given ending node.
	 * 
	 * @param map
	 *            the map related to the shortest path
	 * @param startingNode
	 *            the starting node, in the map, of the shortest path
	 * @param endingNode
	 *            the ending node, in the map, of the shortest path
	 *            PRECONDITION: Starting and ending node are in the map
	 * @throws Exception
	 *             an exception is thrown if there is no possible path between
	 *             the starting node and the ending node
	 */
	public ShortestPath(MapI map, NodeI startingNode, NodeI endingNode) throws Exception {
		// Precondition: Starting and ending node are in the map
		assert(map.getNodeById(startingNode.getId()) != null
				&& map.getNodeById(startingNode.getId()).equals(startingNode)
				&& map.getNodeById(endingNode.getId()) != null
				&& map.getNodeById(endingNode.getId()).equals(endingNode));
		this.map = map;

		// Computing the shortest path between the starting node and the ending
		// node from the dijkstra computed by the map.
		java.util.Map<NodeI, Entry<EdgeI, Double>> resDijkstra = map.dijkstra(startingNode);

		// Checking if a path is possible from the starting node to the ending
		// node, in the map
		if (!resDijkstra.containsKey(endingNode)) {
			throw new Exception("A path from " + startingNode + "to " + endingNode
					+ "does not exist.\n Cannot construct the shortest path between those two nodes.");
		}
		// reconstructing the path using the precedence tab
		List<EdgeI> pathFromStartToGoal = new ArrayList<EdgeI>();
		Entry<EdgeI, Double> precedingEntry = resDijkstra.get(endingNode);
		do {
			EdgeI precedingGoalEdge = precedingEntry.getKey();
			// inserting the edge at last position and searching for more
			pathFromStartToGoal.add(pathFromStartToGoal.size(), precedingGoalEdge);
			precedingEntry = resDijkstra.get(precedingGoalEdge.getStartNode());
		} while (precedingEntry != null); // If we got all the path, we can stop

		// Putting the edges from start to end (reverse)
		Collections.reverse(pathFromStartToGoal);

		// Finally, we can set the new shortest path
		this.changePath(pathFromStartToGoal);
	}

	/**
	 * Returns the map related to this shortest path.
	 * 
	 * @return The map related to this shortest path
	 */
	public MapI getMap() {
		return this.map;
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
		if (!(other instanceof ShortestPath))
			return false;
		ShortestPath otherShortestPath = (ShortestPath) other;
		return super.equals(otherShortestPath) && this.getMap().equals(otherShortestPath.getMap());
	}

}
