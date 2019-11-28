/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.delivery;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import model.graph.NodeI;

/**
 * This is the interface for a delivery request.
 * A delivery request stands for a set of deliveries and starts from a store, at
 * a given moment called the "departure time". A delivery request can also
 * compute a delivery journey for the demanded deliveries.
 * 
 * @author Jerome, Arthur
 */
public interface DeliveryRequestI {

	/**
	 * Returns a constant iterator over the deliveries of the DeliveryRequest.
	 * 
	 * @return The deliveries of the request
	 */
	public Iterator<DeliveryI> deliveryIterator();

	/**
	 * Returns the address of the store.
	 * 
	 * @return The address of the store
	 */
	public NodeI getStoreAddress();

	/**
	 * Returns the departure time.
	 * 
	 * @return The departure time
	 */
	public Date getDepartureTime();

	/**
	 * Adds a delivery to the request.
	 * 
	 * @param position
	 *            the position in the list for the delivery
	 * @param delivery
	 *            the delivery to add
	 */
	public void addDelivery(int position, DeliveryI delivery);

	/**
	 * Remove a delivery from the request.
	 * 
	 * @param delivery
	 *            the delivery to remove
	 */
	void removeDelivery(DeliveryI delivery);

	/**
	 * Returns a delivery of the request, given the address of the delivery.
	 * 
	 * @param adress
	 *            the address of the desired delivery
	 * @return the delivery corresponding to the given address ; null if there
	 *         is no delivery corresponding to the given address in the delivery
	 *         request
	 */
	DeliveryI getDeliveryByNode(NodeI adress);

	/**
	 * Sets an immutable set containing all the deliveries of the request.
	 * 
	 * @param deliveries
	 *            an immutable set containing all the deliveries of the request
	 */
	void setDeliveries(List<DeliveryI> deliveries);

	/**
	 * Returns an immutable set containing all the deliveries of the request.
	 * 
	 * @return an immutable set containing all the deliveries of the request
	 */
	List<DeliveryI> getDeliveries();

	/**
	 * Change the informations of a specific delivery, in the request.
	 * 
	 * @param delivery
	 *            the delivery to update
	 * @param deliveryDuration
	 *            the new duration of the delivery
	 * @param minTime
	 *            the new min time of the delivery
	 * @param maxTime
	 *            the new max time of the delivery
	 * @throws Exception
	 *             if the delivery duration or the minimal or maximal time is
	 *             not set correctly
	 */
	void updateDelivery(DeliveryI delivery, int deliveryDuration, Date minTime, Date maxTime) throws Exception;

	/**
	 * Return a delivery by its id.
	 * 
	 * @param id
	 *            the id of the delivery
	 * @return the delivery that corresponds to the id
	 */
	DeliveryI getDeliveryByNodeId(int id);

}