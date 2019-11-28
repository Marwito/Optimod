/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.delivery;

import java.util.Date;

import model.graph.NodeI;

/**
 * This class is an interface of the Delivery class
 */
public interface DeliveryI {
	/**
	 * Returns the duration of the delivery (in seconds).
	 * 
	 * @return the duration of the delivery (in seconds)
	 */
	public int getDeliveryDuration();

	/**
	 * Returns the duration of the delivery (in minutes).
	 * 
	 * @return the duration of the delivery (in minutes)
	 */
	public int getDurationInMin();

	/**
	 * Returns the max starting time of the delivery.
	 * 
	 * @return the max starting time of the delivery
	 */
	public Date getMaxTime();

	/**
	 * Returns the min starting time of the delivery.
	 * 
	 * @return the min starting time of the delivery
	 */
	public Date getMinTime();

	/**
	 * Returns the Node corresponding to the location of the delivery.
	 * 
	 * @return the Node corresponding to the location of the delivery
	 */
	public NodeI getAddress();

	/**
	 * Returns true if this delivery has a min starting.
	 * 
	 * @return true if this delivery has a min starting time
	 */
	public boolean hasMinTime();

	/**
	 * Returns true if this delivery has a max starting.
	 * 
	 * @return true if this delivery has a max starting time
	 */
	public boolean hasMaxTime();

	/**
	 * Returns true if this delivery has a min starting time and a max starting
	 * time.
	 * 
	 * @return true if this delivery has a min starting time and a max starting
	 *         time
	 */
	public boolean hasTimeframe();

	/**
	 * Changes the duration of the delivery.
	 * 
	 * @param deliveryDuration
	 *            the new duration for the delivery
	 * @throws Exception When the duration of a delivery is negative
	 */
	void setDeliveryDuration(int deliveryDuration) throws Exception;

	/**
	 * Changes the time frame of the delivery.
	 * 
	 * @param minTime
	 *            the new min time for the delivery
	 * @param maxTime
	 *            the new max time for the delivery
	 * @throws Exception
	 *             if minTime > maxTime
	 */
	void setMinMaxTime(Date minTime, Date maxTime) throws Exception;
}
