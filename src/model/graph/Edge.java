package model.graph;

/**
 * An implementation for the EdgeI interface.
 * 
 * @see model.graph.EdgeI
 * @author Adrien, Arthur
 *
 */
public class Edge implements EdgeI {
	private NodeI startNode;
	private NodeI endNode;
	private double length;
	private double averageSpeed;
	private String roadName;

	/**
	 * Creates an edge with a specified starting node, ending node, length and
	 * average speed
	 * 
	 * @param aStartNode
	 *            The starting node
	 * @param aEndNode
	 *            The ending node
	 * @param aLength
	 *            The length of the edge (in meters)
	 * @param aAverageSpeed
	 *            The average speed of the edge (in meters/s)
	 */
	public Edge(NodeI startNode, NodeI endNode, double length, double averageSpeed, String roadName) throws Exception {
		this.setStartNode(startNode);
		this.setEndNode(endNode);
		this.setLength(length);
		this.setAverageSpeed(averageSpeed);
		this.setRoadName(roadName);
	}

	/**
	 * {@inheritDoc}
	 */
	public NodeI getStartNode() {
		return startNode;
	}

	/**
	 * {@inheritDoc}
	 */
	public NodeI getEndNode() {
		return endNode;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getAverageTravelTimeMin() {
		return (int) Math.round(((length / averageSpeed) / 60));
	}

	/**
	 * {@inheritDoc}
	 */
	public int getAverageTravelTime() {
		return (int) Math.round(((length / averageSpeed)));
	}

	/**
	 * {@inheritDoc}
	 */
	public double getLength() {
		return length;
	}

	/**
	 * {@inheritDoc}
	 */
	public double getAverageSpeed() {
		return averageSpeed;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getRoadName() {
		return roadName;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "<troncon destination=\"" + this.endNode.getId() + "\" longueur=\"" + this.length + "\" nomRue=\""
				+ this.roadName + "\" origine=\"" + this.startNode.getId() + "\" vitesse=\"" + this.averageSpeed
				+ "\"/>";
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
		if (!(other instanceof Edge))
			return false;
		// Comparing the map with the other map
		Edge otherEdge = (Edge) other;
		return otherEdge.getStartNode().equals(this.getStartNode()) && otherEdge.getEndNode().equals(this.getEndNode())
				&& otherEdge.getLength() == this.getLength() && otherEdge.getRoadName().equals(this.getRoadName())
				&& otherEdge.getAverageSpeed() == this.getAverageSpeed();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return this.getStartNode().hashCode() + this.getEndNode().hashCode();
	}

	// ---- PRIVATE METHODS ----
	private void setStartNode(NodeI startNode) throws Exception {
		if (startNode == null) {
			throw new Exception("It is impossible to create an edge with unexisting nodes.");
		} else {
			this.startNode = startNode;
		}
	}

	private void setEndNode(NodeI endNode) throws Exception {
		if (endNode == null) {
			throw new Exception("It is impossible to create an edge with unexisting nodes.");
		} else {
			this.endNode = endNode;
		}
	}

	private void setLength(double length) throws Exception {
		if (length <= 0) {
			throw new Exception("The length can't be lower than or equal to 0.");
		} else {
			this.length = length;
		}
	}

	private void setAverageSpeed(double averageSpeed) throws Exception {
		if (averageSpeed <= 0) {
			throw new Exception("The average speed can't be lower than or equal to 0.");
		} else {
			this.averageSpeed = averageSpeed;
		}
	}

	private void setRoadName(String roadName) {
		this.roadName = roadName;
	}
}
