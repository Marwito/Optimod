package model.delivery;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.graph.NodeI;

/**
 * This class implements a delivery A delivery has a duration, a beginning time,
 * an ending time and an address.
 */
public class Delivery implements DeliveryI {
	private int deliveryDuration = 0;
	private Date minTime;
	private Date maxTime;
	private NodeI adress;

	/**
	 * 
	 * @param deliveryDuration
	 *            The duration of the delivery (in seconds)
	 * @param minTime
	 *            The min starting time for the delivery (null if not needed)
	 * @param maxTime
	 *            The max starting time for the delivery (null if not needed)
	 * @param adress
	 *            The Node corresponding to the location of the delivery
	 * @throws Exception
	 *             if maxTime < minTime or if the delivery duration is negative.
	 */
	public Delivery(int deliveryDuration, Date minTime, Date maxTime, NodeI adress) throws Exception {
		super();
		this.setDeliveryDuration(deliveryDuration);
		this.setMinMaxTime(minTime, maxTime);
		this.setAdress(adress);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setDeliveryDuration(int deliveryDuration) throws Exception {
		if (deliveryDuration > 0) {
			this.deliveryDuration = deliveryDuration;
		} else {
			throw new Exception(
					"The deliveryDuration of the delivery at node " + adress.getId() + " must not be negative.");
		}
	}

	@Override
	public void setMinMaxTime(Date minTime, Date maxTime) throws Exception {
		if (minTime != null && maxTime != null && maxTime.before(minTime)) {
			throw new Exception("The time window of the delivery at node " + adress.getId() + " is not valid.");
		} else
			if (minTime != null && maxTime != null && deliveryDuration > maxTime.getTime() - minTime.getTime() / 1000) {
			throw new Exception(
					"The time window of the delivery at node " + adress.getId() + " is smaller than the duration.");
		} else if ((minTime == null && maxTime != null) || (minTime != null && maxTime == null)) {
			throw new Exception("The time window must have both a minimal value " + "and a maximal one.");
		} else {
			this.minTime = minTime;
			this.maxTime = maxTime;
		}
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Date getMinTime() {
		return minTime;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Date getMaxTime() {
		return maxTime;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean hasTimeframe() {
		return (this.getMinTime() != null && this.getMaxTime() != null);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean hasMinTime() {
		return (this.getMinTime() != null);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean hasMaxTime() {
		return (this.getMaxTime() != null);
	}

	/**
	 * 
	 * {@inheritDoc}
	 */
	public NodeI getAddress() {
		return adress;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getDurationInMin() {
		return getDeliveryDuration() / 60;
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
		if (!(other instanceof Delivery))
			return false;
		// Comparing the map with the other map
		Delivery otherDelivery = (Delivery) other;
		// Comparing minTime (which could be null)
		boolean minTimeEquals = false;
		// the two are null
		if (otherDelivery.getMinTime() == null && this.getMinTime() == null) {
			minTimeEquals = true;
		}
		// none is null
		else if (otherDelivery.getMinTime() != null && this.getMinTime() != null) {
			// Formating the min times to get just second precision on the
			// comparison (and not millisecond precision)
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
			String minTimFormatedOtherDelivery = formatter.format(otherDelivery.getMinTime());
			String minTimeFormatedCurrentDelivery = formatter.format(this.getMinTime());
			minTimeEquals = minTimFormatedOtherDelivery.equals(minTimeFormatedCurrentDelivery);
		} else { // one is null only
			minTimeEquals = false;
		}
		// Comparing maxTime (which could be null)
		boolean maxTimeEquals = false;
		if (otherDelivery.getMaxTime() == null && this.getMaxTime() == null) {
			maxTimeEquals = true;
		} else if (otherDelivery.getMaxTime() != null && this.getMaxTime() != null) {
			// Formating the max times to get just second precision on the
			// comparison (and not millisecond precision)
			SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
			String maxTimFormatedOtherDelivery = formatter.format(otherDelivery.getMaxTime());
			String maxTimeFormatedCurrentDelivery = formatter.format(this.getMaxTime());
			maxTimeEquals = maxTimFormatedOtherDelivery.equals(maxTimeFormatedCurrentDelivery);
		} else {
			maxTimeEquals = false;
		}

		// Comparing all the informations
		return otherDelivery.getDeliveryDuration() == this.getDeliveryDuration() && minTimeEquals && maxTimeEquals
				&& otherDelivery.getAddress().equals(this.getAddress());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int hashCode() {
		return this.getAddress().hashCode();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String toString() {
		String stringRes = "<";
		stringRes += "Address: " + this.getAddress() + " | ";
		stringRes += "Duration: " + this.getDeliveryDuration() + " | ";
		stringRes += "MinTime: " + this.getMinTime() + " | ";
		stringRes += "MaxTime: " + this.getMaxTime();
		return stringRes + ">";
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getDeliveryDuration() {
		return deliveryDuration;
	}

	// ---- METHODES PRIVEES ----
	/**
	 * Set an address for a given delivery
	 * 
	 * @param adress
	 *            A node as an input
	 * @throws Exception
	 *             When the input parameter 'adress' is null
	 */
	private void setAdress(NodeI adress) throws Exception {
		if (adress == null) {
			throw new Exception("It is impossible to create a delivery with a null node.");
		} else {
			this.adress = adress;
		}
	}

}
