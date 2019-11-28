package model.delivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import model.computer.ComputeDeliveryJourney;
import model.computer.Computer;
import model.graph.EdgeI;
import model.map.MapI;
import model.map.ShortestPathDelivery;

/**
 * Represents the computed delivery journey
 * 
 * @author Marwan
 *
 */
public class DeliveryJourney implements DeliveryJourneyI {

	private DeliveryRequestI request; // the answered request
	private List<ShortestPathDelivery> journey;
	private Date journeyArrivalTime; // time when the tour ends
	private java.util.Map<DeliveryI, TimeFrameWait> timeFrames;
	private MapI map;
	// The computer used to compute the journey (i.e the list of shortest paths)
	private Computer journeyComputer = new ComputeDeliveryJourney();

	/**
	 * @param request
	 *            the request to which the delivery journey answers
	 * @param map
	 *            the map on which we want to compute the journey
	 * @throws Exception
	 *             if the journey could not be computed
	 */
	public DeliveryJourney(DeliveryRequestI request, MapI map, int maxWaitingTimeForConstructor) throws Exception {
		this.request = request;
		this.map = map;
		timeFrames = new HashMap<DeliveryI, TimeFrameWait>();

		computeJourney(maxWaitingTimeForConstructor);
		computeTimeWindows();

		List<DeliveryI> orderedDeliveries = new LinkedList<>();
		DeliveryI currentDelivery = null;
		for (ShortestPathDelivery shortestPath : journey) {
			currentDelivery = request.getDeliveryByNode(shortestPath.getEndNode());
			if (currentDelivery != null) {
				orderedDeliveries.add(currentDelivery);
			}
		}
		request.setDeliveries(orderedDeliveries);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void addDelivery(DeliveryI newDelivery, DeliveryI previousDelivery) throws Exception {
		int previousDeliveryPosition = request.getDeliveries().indexOf(previousDelivery);
		addDelivery(newDelivery, previousDeliveryPosition + 1);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void addDelivery(DeliveryI newDelivery, int position) throws Exception {
		ShortestPathDelivery shortestPathToDelete = null;
		ShortestPathDelivery firstNewShortestPath = null;
		ShortestPathDelivery secondNewShortestPath = null;
		DeliveryI previousDelivery = null;
		DeliveryI nextDelivery = null;

		// If this new delivery is to be the first.
		if (position == 0) {
			shortestPathToDelete = journey.get(0);
			nextDelivery = shortestPathToDelete.getDeliveryEnd();
			previousDelivery = new Delivery(1, null, null, request.getStoreAddress());
		} else if (position == request.getDeliveries().size()) {
			shortestPathToDelete = journey.get(journey.size() - 1);
			nextDelivery = new Delivery(1, null, null, request.getStoreAddress());
			previousDelivery = shortestPathToDelete.getDeliveryStart();
		} else {
			shortestPathToDelete = journey.get(position);
			nextDelivery = shortestPathToDelete.getDeliveryEnd();
			previousDelivery = shortestPathToDelete.getDeliveryStart();
		}

		firstNewShortestPath = new ShortestPathDelivery(map, previousDelivery, newDelivery);
		secondNewShortestPath = new ShortestPathDelivery(map, newDelivery, nextDelivery);

		journey.remove(position);

		journey.add(position, firstNewShortestPath);
		journey.add(position + 1, secondNewShortestPath);

		request.addDelivery(position, newDelivery);

		computeTimeWindows();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void removeDelivery(DeliveryI removedDelivery) throws Exception {
		ShortestPathDelivery firstShortestPathToDelete = null;
		ShortestPathDelivery secondShortestPathToDelete = null;
		int insertionPosition = 0;

		ShortestPathDelivery currentShortestPath = null;
		for (int index = 0; index < journey.size(); index++) {
			currentShortestPath = journey.get(index);

			if (currentShortestPath.getEndNode() == removedDelivery.getAddress()) {
				firstShortestPathToDelete = currentShortestPath;
				insertionPosition = index;
			} else if (currentShortestPath.getStartNode() == removedDelivery.getAddress()) {
				secondShortestPathToDelete = currentShortestPath;
			}
		}

		if (firstShortestPathToDelete == null || secondShortestPathToDelete == null) {
			throw new Exception("Delivery cannot be removed because " + "it isn't in the DeliveryJourney");
		}

		DeliveryI firstNode = firstShortestPathToDelete.getDeliveryStart();
		DeliveryI secondNode = secondShortestPathToDelete.getDeliveryEnd();

		ShortestPathDelivery newShortestPath = new ShortestPathDelivery(map, firstNode, secondNode);

		journey.remove(firstShortestPathToDelete);
		journey.remove(secondShortestPathToDelete);

		journey.add(insertionPosition, newShortestPath);

		request.removeDelivery(removedDelivery);

		computeTimeWindows();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void swapDeliveries(DeliveryI firstDelivery, DeliveryI secondDelivery) throws Exception {
		if (firstDelivery == null || secondDelivery == null)
			throw new Exception("Can't swap deliveries if one is null.");

		int firstDeliveryPosition = request.getDeliveries().indexOf(firstDelivery);
		int secondDeliveryPosition = request.getDeliveries().indexOf(secondDelivery);

		this.removeDelivery(firstDelivery);
		this.removeDelivery(secondDelivery);

		this.addDelivery(firstDelivery, secondDeliveryPosition);
		this.addDelivery(secondDelivery, firstDeliveryPosition);

		computeTimeWindows();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void updateDelivery(DeliveryI delivery, int deliveryDuration, Date minTime, Date maxTime) throws Exception {
		request.updateDelivery(delivery, deliveryDuration, minTime, maxTime);

		computeTimeWindows();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Iterator<EdgeI> edgeIterator() {
		List<EdgeI> journeyEdges = new ArrayList<EdgeI>();
		for (ShortestPathDelivery currentPath : journey) {
			Iterator<EdgeI> edgesOfCurrentPath = currentPath.getPathIterator();
			while (edgesOfCurrentPath.hasNext()) {
				journeyEdges.add(edgesOfCurrentPath.next());
			}
		}

		// Returning immutable
		List<EdgeI> immutableList = Collections.unmodifiableList(journeyEdges);
		return immutableList.iterator();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<ShortestPathDelivery> getJourney() {
		return journey;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Iterator<ShortestPathDelivery> shortestPathIterator() {
		List<ShortestPathDelivery> immutableShortestPaths = Collections.unmodifiableList(this.journey);
		return immutableShortestPaths.iterator();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Date getArrivalTime(DeliveryI delivery) {
		return this.timeFrames.get(delivery).getStartingTime();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Date getDepartureTime(DeliveryI delivery) {
		return this.timeFrames.get(delivery).getEndingTime();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public int getWaitingTime(DeliveryI delivery) {
		return this.timeFrames.get(delivery).getWaitingTime();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public DeliveryI getPreviousDelivery(DeliveryI currentDelivery) throws Exception {
		if (journey.get(0).getEndNode() == currentDelivery.getAddress()) {
			return null;
		}
		for (ShortestPathDelivery shortestPath : journey) {
			if (shortestPath.getEndNode() == currentDelivery.getAddress()) {
				return request.getDeliveryByNode(shortestPath.getStartNode());
			}
		}
		throw new Exception("The given delivery is neither the first nor has it a previous delivery.");
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public DeliveryI getNextDelivery(DeliveryI currentDelivery) throws Exception {
		if (journey.get(journey.size() - 1).getStartNode() == currentDelivery.getAddress()) {
			return null;
		}
		for (ShortestPathDelivery shortestPath : journey) {
			if (shortestPath.getStartNode() == currentDelivery.getAddress()) {
				return request.getDeliveryByNode(shortestPath.getEndNode());
			}
		}
		throw new Exception("The given delivery is neither the first nor has it a next delivery.");
	}

	@Override
	public DeliveryI getDeliveryById(int nodeId) {
		return request.getDeliveryByNodeId(nodeId);
	}

	@Override
	public DeliveryRequestI getRequest() {
		return request;
	}

	@Override
	/**
	 * Returns the time when the delivery journey ends.
	 * 
	 * @return the time when the delivery journey ends
	 * @throws Exception
	 */
	public Date getDeliveryJourneyArrivalTime() {
		return this.journeyArrivalTime;
	}

	/**
	 * Computes the journey (i.e the succession of shortest paths), the arrival
	 * time of the computed journey and the time frames of the deliveries of the
	 * computed journey.
	 * 
	 * @param maxWaitingTime
	 *            the max time to wait for the computation (in milliseconds)
	 * @throws Exception
	 *             if the journey could not be computed
	 */
	// TODO: remove system.out if all works (care, do not remove before the end
	// of project, it is useful for tests)
	public void computeJourney(int maxWaitingTime) throws Exception {
		// Computing the paths of the journey
		List<ShortestPathDelivery> computedPathJourney = journeyComputer.computeDeliveryJourney(this.request, this.map,
				maxWaitingTime);
		journey = computedPathJourney;

	}

	/**
	 * Computes the time windows for the current journey. PRECONDITION: the
	 * journey must be not null
	 * 
	 * @throws Exception
	 *             thrown if a time window of a delivery in the journey could
	 *             not be respected
	 */
	private void computeTimeWindows() throws Exception {
		assert (this.journey != null);
		// For each delivery, computing the starting date and the ending date
		Iterator<ShortestPathDelivery> shortestPathsIterator = this.journey.iterator();
		Date previousDepartureTime = new Date(request.getDepartureTime().getTime());
		Date arrivalTime = null;
		DeliveryI currentEstimatedDelivery = null;
		while (shortestPathsIterator.hasNext()) {
			ShortestPathDelivery currentSortestPath = shortestPathsIterator.next();
			currentEstimatedDelivery = currentSortestPath.getDeliveryEnd();
			arrivalTime = new Date(previousDepartureTime.getTime() + currentSortestPath.getTravelTime() * 1000);
			int waitingTime = 0; // in seconds
			if (currentEstimatedDelivery.hasMinTime()
					&& currentEstimatedDelivery.getMinTime().getTime() > arrivalTime.getTime()) {
				waitingTime = (int) ((currentEstimatedDelivery.getMinTime().getTime() - arrivalTime.getTime()) / 1000);
			}
			Date departureTime = new Date(
					arrivalTime.getTime() + waitingTime * 1000 + currentEstimatedDelivery.getDeliveryDuration() * 1000);

			this.timeFrames.put(currentEstimatedDelivery, new TimeFrameWait(arrivalTime, departureTime, waitingTime));

			previousDepartureTime = departureTime;

			// Checking that the arrival time and departure time respect the min
			// time and max time of the current estimated delivery
			if (shortestPathsIterator.hasNext()) { // if not store
				if (currentEstimatedDelivery.hasMaxTime()) {
					if (currentEstimatedDelivery.getMaxTime().getTime() < this.timeFrames.get(currentEstimatedDelivery)
							.getEndingTime().getTime()) {

					}
				}
			}
		}

		// Getting the 'fake' delivery associated to the store, removing from
		// the time frames and getting the arrival time
		this.timeFrames.remove(currentEstimatedDelivery);
		this.journeyArrivalTime = arrivalTime;

	}
}
