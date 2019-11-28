package model.map;

import model.delivery.DeliveryI;

/**
 * This class represents for a given map, the shortest path between two
 * deliveries to perform in the map.
 *
 */
public class ShortestPathDelivery extends ShortestPath {
	DeliveryI deliveryStart;
	DeliveryI deliveryEnd;

	/**
	 * Creates a shortest path between two given deliveries, for a given map.
	 * The path is automatically computed between the given starting node and
	 * the given ending node.
	 * 
	 * @param map
	 *            the map related to the shortest path
	 * @param deliveryStart
	 *            the starting delivery
	 * @param deliveryEnd
	 *            the ending delivery PRECONDITION: The node of the starting
	 *            delivery and the node of the ending delivery are in the map
	 * @throws Exception
	 *             an exception is thrown if there is no possible path between
	 *             the two deliveries
	 */
	public ShortestPathDelivery(MapI map, DeliveryI deliveryStart, DeliveryI deliveryEnd) throws Exception {
		super(map, deliveryStart.getAddress(), deliveryEnd.getAddress());
		this.deliveryStart = deliveryStart;
		this.deliveryEnd = deliveryEnd;
	}

	/**
	 * Returns the starting delivery of the path.
	 * 
	 * @return the starting delivery of the path
	 */
	public DeliveryI getDeliveryStart() {
		return this.deliveryStart;
	}

	/**
	 * Returns the ending delivery of the path.
	 * 
	 * @return the ending delivery of the path
	 */
	public DeliveryI getDeliveryEnd() {
		return this.deliveryEnd;
	}

	/**
	 * Returns the total time to travel the path from the starting delivery to
	 * the ending delivery (in seconds), taking into account the duration to
	 * effectively deliver the two deliveries (i.e the duration of the
	 * deliveries).
	 * 
	 * @return the total travel time to travel the path (in seconds)
	 */
	public int getTotalNeededTime() {
		return this.getTravelTime() + this.deliveryStart.getDeliveryDuration() + this.deliveryEnd.getDeliveryDuration();
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
		if (!(other instanceof ShortestPathDelivery))
			return false;
		ShortestPathDelivery otherShortestPath = (ShortestPathDelivery) other;
		return super.equals(otherShortestPath) && this.getDeliveryStart().equals(otherShortestPath.getDeliveryStart())
				&& this.getDeliveryEnd().equals(otherShortestPath.getDeliveryEnd());
	}

}
