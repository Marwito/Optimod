package model.graph;

/**
 * Represents each intersection of roads, associated with coordinates.
 * 
 * @author jerome, Adrien, Arthur
 */
public class Node implements NodeI {
	private int id;
	private int x;
	private int y;

	/**
	 * Creates a node with specified coordinates and a specified id
	 * 
	 * @param aX
	 *            the X coordinate of the node
	 * @param aY
	 *            the Y coordinate of the node
	 * @param aId
	 *            the id of the node
	 */
	public Node(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getX() {
		return x;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getY() {
		return y;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getId() {
		return id;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		return "<noeud id=\"" + this.id + "\" x=\"" + this.x + "\" y=\"" + this.y + "\"/>";
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
		if (!(other instanceof Node))
			return false;
		// Comparing the map with the other map
		Node otherNode = (Node) other;
		return otherNode.getX() == this.getX() && otherNode.getY() == this.getY() && otherNode.getId() == this.getId();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return this.getId();
	}
}
