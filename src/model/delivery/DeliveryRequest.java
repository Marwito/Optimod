package model.delivery;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import model.graph.NodeI;

/**
 * This class implements the methods of the delivery request interface.
 * A delivery request stands for a set of deliveries and starts from a store, at
 * a given moment called the "departure time". A delivery request can also
 * compute a delivery journey for the demanded deliveries.
 * 
 * @author Marwan
 *
 */
public class DeliveryRequest implements DeliveryRequestI {
	private NodeI storeAddress;
	private Date departureTime;
	private List<DeliveryI> deliveries;

	/**
	 * Creates a DeliveryRequest with a given address for the store, a given
	 * departure time and a given set of deliveries
	 * 
	 * @param storeAddress
	 *            The address of the store
	 * @param departureTime
	 *            The departure time (precision max: seconds)
	 * @param deliveries
	 *            The deliveries to achieve
	 */
	public DeliveryRequest(NodeI storeAddress, Date departureTime, List<DeliveryI> deliveries) {
		this.storeAddress = storeAddress;
		this.departureTime = departureTime;
		this.deliveries = new LinkedList<DeliveryI>(deliveries);
	}

	/**
	 * Creates an empty delivery request.
	 */
	public DeliveryRequest() {
		this.storeAddress = null;
		this.departureTime = null;
		this.deliveries = new LinkedList<DeliveryI>();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Iterator<DeliveryI> deliveryIterator() {
		List<DeliveryI> immutableDeliverySet = Collections.unmodifiableList(deliveries);
		return immutableDeliverySet.iterator();
	}

	/**
	 * Returns the address of the store.
	 * 
	 * @return The address of the store
	 */
	public NodeI getStoreAddress() {
		return storeAddress;
	}

	/**
	 * Returns the departure time.
	 * 
	 * @return The departure time
	 */
	public Date getDepartureTime() {
		return departureTime;
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
		if (!(other instanceof DeliveryRequest))
			return false;
		// Comparing the map with the other map
		DeliveryRequest otherRequest = (DeliveryRequest) other;
		// Formating the departure times to get just second precision on the
		// comparison (and not millisecond precision)
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		String departureFormatedOtherRequest = formatter.format(otherRequest.getDepartureTime());
		String departureFormatedCurrentRequest = formatter.format(this.getDepartureTime());
		// Checking the shortest paths are equals
		return otherRequest.getStoreAddress().equals(this.getStoreAddress())
				&& departureFormatedOtherRequest.equals(departureFormatedCurrentRequest)
				&& otherRequest.deliveries.equals(this.deliveries);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return this.getStoreAddress().hashCode();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		String stringRes = "(\n";
		stringRes += "Store address: " + this.getStoreAddress() + "\n";
		stringRes += "Departure time: " + this.getDepartureTime() + "\n";
		stringRes += "Deliveries:\n";
		for (DeliveryI delivery : this.deliveries) {
			stringRes += "\t" + delivery + "\n";
		}
		return stringRes + ")";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void addDelivery(int position, DeliveryI delivery) {
		deliveries.add(position, delivery);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void removeDelivery(DeliveryI delivery) {
		deliveries.remove(delivery);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public DeliveryI getDeliveryByNode(NodeI node) {
		for (DeliveryI delivery : deliveries) {
			if (delivery.getAddress() == node) {
				return delivery;
			}
		}
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void updateDelivery(DeliveryI delivery, int deliveryDuration, Date minTime, Date maxTime) throws Exception {
		for (DeliveryI currentDelivery : deliveries) {
			if (currentDelivery == delivery) {
				currentDelivery.setDeliveryDuration(deliveryDuration);
				currentDelivery.setMinMaxTime(minTime, maxTime);
			}
		}
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public DeliveryI getDeliveryByNodeId(int id) {
		for (DeliveryI delivery : deliveries) {
			if (delivery.getAddress().getId() == id) {
				return delivery;
			}
		}
		return null;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<DeliveryI> getDeliveries() {
		return deliveries;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setDeliveries(List<DeliveryI> deliveries) {
		this.deliveries = deliveries;
	}
}
