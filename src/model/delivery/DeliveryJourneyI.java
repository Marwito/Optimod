/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.delivery;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.delivery.DeliveryI;
import model.graph.EdgeI;
import model.map.ShortestPathDelivery;
/**
 * An interface for the DeliveryJourney class
 */
public interface DeliveryJourneyI {
	/**
	 * Returns an iterator over the edges of the journey, ordered in the order
	 * of travel of the journey.
	 * 
	 * @return an iterator over the edges of the journey
	 */
	public Iterator<EdgeI> edgeIterator();

	/**
	 * Returns an iterator over the ShortestPathDelivery of the journey, ordered
	 * in the order of travel of the journey.
	 * 
	 * @return an iterator over the ShortestPathDelivery of the journey
	 */
	public Iterator<ShortestPathDelivery> shortestPathIterator();

	/**
	 * Returns the arrival time of a delivery in the computed journey.
	 * 
	 * @param delivery
	 *            the delivery of which we want the arrival time
	 * @return the arrival time of the delivery ; null if the delivery was not
	 *         in the request
	 */
	public Date getArrivalTime(DeliveryI delivery);

	/**
	 * Returns the departure time of a delivery in the computed journey.
	 * 
	 * @param delivery
	 *            the delivery of which we want the departure time
	 * @return the departure time of the delivery ; null if the delivery was not
	 *         in the request
	 */
	public Date getDepartureTime(DeliveryI delivery);

	/**
	 * Returns the waiting time of a delivery in the computed journey.
	 * 
	 * @param delivery
	 *            the delivery of which we want the waiting time
	 * @return the waiting time of the delivery ; null if the delivery was not
	 *         in the request
	 */
	public int getWaitingTime(DeliveryI delivery);

	/**
	 * Returns, in the computed delivery journey, the delivery preceding the
	 * given delivery, if there is one.
	 * 
	 * @param currentDelivery
	 *            the given delivery
	 * @return the delivery preceding the given delivery ; null if the given
	 *         delivery is not in the journey
	 * @throws Exception When the given delivery is neither the first nor has it a previous delivery
	 */
	DeliveryI getPreviousDelivery(DeliveryI currentDelivery) throws Exception;

	/**
	 * Returns the time when the delivery journey ends.
	 * 
	 * @return the time when the delivery journey ends
	 */
	Date getDeliveryJourneyArrivalTime();
	
	/**
	 * Adds a new delivery after another one given as an input parameter
	 * @param newDelivery The new delivery to be added
	 * @param previousDelivery The previous delivery after which the new delivery will be inserted
	 * @throws Exception When called methods inside throw an exception
	 */
	void addDelivery(DeliveryI newDelivery, DeliveryI previousDelivery) throws Exception;
    
	/**
	 * Removes a delivery
     * @param removedDelivery the delivery to be deleted
     * @throws Exception When the delivery cannot be removed because it is not in the DeliveryJourney
     */
	void removeDelivery(DeliveryI removedDelivery) throws Exception;
    /**
     * Swap 2 neighboring deliveries
     * @param firstDelivery 
     * @param secondDelivery
     * @throws Exception When the swap can't be done if one of the two deliveries is null
     */
	void swapDeliveries(DeliveryI firstDelivery, DeliveryI secondDelivery) throws Exception;
    /**
     * Updates details of a delivery
     * @param delivery the delivery 
     * @param deliveryDuration 
     * @param minTime minimal time of the time window
     * @param maxTime maximal time of the time window
     * @throws Exception When there would be an exception while updating the delivery or calculating the time window
     */
	void updateDelivery(DeliveryI delivery, int deliveryDuration, Date minTime, Date maxTime) throws Exception;
	/**
	 * Returns a delivery 
	 * @param deliveryId The Id of the searched delivery 
	 * @return The delivery whose Id equals deliveryId
	 */
	DeliveryI getDeliveryById(int deliveryId);
	/**
	 * Returns the request of deliveries to which the delivery journey answers
	 * @return the request of deliveries
	 */
	DeliveryRequestI getRequest();
	/**
	 * Returns, in the computed delivery journey, the delivery that follows the
	 * given delivery, if there is one.
	 * @param currentDelivery
	 *            the given delivery
	 * @return the delivery preceding the given delivery ; null if the given
	 *         delivery is not in the journey
	 * @throws Exception The given delivery is neither the first nor has it a next delivery
	 */
	DeliveryI getNextDelivery(DeliveryI currentDelivery) throws Exception;
	
	/**
	 * Adds a new delivery at a specific position in the list of the ShortestPathDelivery
	 * @param newDelivery The new delivery to be added
	 * @param position The index in the list of ShortestPathDelivery where to insert the new delivery
	 * @throws Exception When called methods inside would throw an exception
	 */
	void addDelivery(DeliveryI newDelivery, int position) throws Exception;
	
    /**
     * Returns the list of the deliveries' paths of a delivery
     * @return List of paths 
     */
	List<ShortestPathDelivery> getJourney();
}