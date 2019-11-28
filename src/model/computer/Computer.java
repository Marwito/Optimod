package model.computer;

import java.util.List;

import model.delivery.DeliveryRequestI;
import model.map.MapI;
import model.map.ShortestPathDelivery;

/**
 * Interface that defines all the functionalities that should be implemented by
 * a computer of a delivery journey.
 */
public interface Computer {
	static final int Infinite = Integer.MAX_VALUE / 100;

	/**
	 * This method calculates the faster journey between the deliveries of a
	 * delivery request, for a given map
	 * 
	 * @param request
	 *            the request of deliveries
	 * @param map
	 *            the map on which we want to compute a journey
	 * @param maxWaitingTime
	 *            the max waiting time for the computation (in ms)
	 * @return the computed delivery journey
	 * @throws Exception
	 *             if it took too much time or was impossible to compute
	 */
	public List<ShortestPathDelivery> computeDeliveryJourney(DeliveryRequestI request, MapI map, int maxWaitingTime)
			throws Exception;
}
