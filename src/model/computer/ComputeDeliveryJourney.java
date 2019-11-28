package model.computer;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import model.computer.tsp.TSP;
import model.computer.tsp.TSP4;
import model.delivery.Delivery;
import model.delivery.DeliveryI;
import model.delivery.DeliveryRequestI;
import model.graph.NodeI;
import model.map.MapI;
import model.map.ShortestPathDelivery;

/**
 * A computer of a delivery journey is the implementation of the computation of
 * the delivery journey, for a given delivery request.
 *
 */
public class ComputeDeliveryJourney implements Computer {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public List<ShortestPathDelivery> computeDeliveryJourney(DeliveryRequestI request, MapI map, int maxWaitingTime)
			throws Exception {
		// Computing the shortest paths
		java.util.Map<DeliveryI, Set<ShortestPathDelivery>> shortestPaths = computeShortestPaths(request, map);

		NodeI origin = request.getStoreAddress();
		NodeI nodes[] = new NodeI[shortestPaths.size()];
		DeliveryI deliveries[] = new DeliveryI[shortestPaths.size()];
		nodes[0] = origin; // for TSOn first node has to be the origin
		// creating a 'fake' delivery for the store
		deliveries[0] = new Delivery(1, null, null, request.getStoreAddress());
		int nodesCounter = 1;
		int deliveryCounter = 1;
		for (java.util.Map.Entry<DeliveryI, Set<ShortestPathDelivery>> entry : shortestPaths.entrySet()) {
			DeliveryI delivery = entry.getKey();
			NodeI node = delivery.getAddress();
			if (!node.equals(origin)) {
				nodes[nodesCounter++] = node;
				deliveries[deliveryCounter++] = delivery;
			}
		}

		// travel time
		int[][] costs = creatCosteMatrix(origin, shortestPaths, deliveries);
		// duration of the delivery
		int[] duration = new int[nodes.length];
		// the max time after which we can arrive to the delivery node (in
		// seconds)
		int[] beginWindow = new int[nodes.length];
		// the min time after which we can arrive to the delivery node (in
		// seconds)
		int[] endWindow = new int[nodes.length];
		Date deliveryTime = request.getDepartureTime();
		for (int i = 1; i < deliveries.length; i++) {
			DeliveryI currentEstimatedDelivery = deliveries[i];
			duration[i] = currentEstimatedDelivery.getDeliveryDuration();
			// min time is not mandatory
			if (currentEstimatedDelivery.hasMinTime()) {
				beginWindow[i] = (int) ((currentEstimatedDelivery.getMinTime().getTime() - deliveryTime.getTime())
						/ 1000);
				if (beginWindow[i] < 0) {
					throw new Exception("Can not compute delivery journey: one min time < departure time");
				}
			} else {
				beginWindow[i] = 0;
			}
			// max time is not mandatory
			if (currentEstimatedDelivery.hasMaxTime()) {
				endWindow[i] = (int) ((currentEstimatedDelivery.getMaxTime().getTime() - deliveryTime.getTime())
						/ 1000);
				if (beginWindow[i] < 0) {
					throw new Exception("Can not compute delivery journey: one max time < departure time");
				}
			} else {
				endWindow[i] = Computer.Infinite;
			}
		}
		// particular situation: the duration, begin window, end window for the
		// store
		duration[0] = 0;
		beginWindow[0] = 0;
		endWindow[0] = Computer.Infinite;

		// Computing the solution
		TSP tsp = new TSP4();
		tsp.chercheSolution(maxWaitingTime, nodes.length, costs, duration, beginWindow, endWindow);
		// Testing if no solution was found
		if (tsp.getMeilleureSolution(0) == null) {
			throw new ComputeDeliveryJourneyException(maxWaitingTime);
		}
		// If there was a solution, constructing the solution
		LinkedList<ShortestPathDelivery> lDelivery = new LinkedList<ShortestPathDelivery>();
		for (int i = 0; i + 1 < nodes.length; i++) {
			lDelivery.add(getShortestPathFrom(shortestPaths, deliveries[(int) tsp.getMeilleureSolution(i)],
					deliveries[(int) tsp.getMeilleureSolution(i + 1)]));
		}
		lDelivery.add(getShortestPathFrom(shortestPaths, deliveries[(int) tsp.getMeilleureSolution(nodes.length - 1)],
				deliveries[(int) tsp.getMeilleureSolution(0)]));
		return lDelivery;
	}

	/**
	 * This method creates the cost matrix for the deliveries of the delivery
	 * request.
	 * 
	 * @param origin
	 *            the origin of the future delivery journey
	 * @param shortestPaths
	 *            the shortest paths between all the deliveries and the store
	 *            node
	 * @param deliveries
	 *            the deliveries of the delivery request
	 * @return creates the cost matrix for the deliveries of the delivery
	 *         request
	 */
	public int[][] creatCosteMatrix(NodeI origin, java.util.Map<DeliveryI, Set<ShortestPathDelivery>> shortestPaths,
			DeliveryI[] deliveries) {
		// creating the cost tab
		int infinite = Computer.Infinite;
		int costs[][] = new int[deliveries.length][deliveries.length];
		for (int i = 0; i < deliveries.length; i++) {
			for (int j = 0; j < deliveries.length; j++) {
				// getting the cost of the shortest path from i to j
				int cost = infinite;
				if (i != j) {
					// Searching if there is a path
					for (ShortestPathDelivery sPath : shortestPaths.get(deliveries[i])) {
						if (sPath.getEndNode().equals(deliveries[j].getAddress())) {
							cost = sPath.getTravelTime();
							break;
						}
					}
				}
				costs[i][j] = cost;
			}
		}
		return costs;
	}

	/**
	 * Returns the shortest path between two deliveries of the delivery request.
	 * 
	 * @param shortestPaths
	 *            the list of all the computed shortest paths between the
	 *            deliveries of the delivery reques
	 * @param deliveryStart
	 *            the starting delivery for the shortest path to compute
	 * @param deliveryEnd
	 *            the starting delivery for the shortest path to compute
	 * @return the computed shortest path
	 */
	private ShortestPathDelivery getShortestPathFrom(java.util.Map<DeliveryI, Set<ShortestPathDelivery>> shortestPaths,
			DeliveryI deliveryStart, DeliveryI deliveryEnd) {
		for (ShortestPathDelivery shortestPath : shortestPaths.get(deliveryStart)) {
			if (shortestPath.getDeliveryEnd().equals(deliveryEnd)) {
				return shortestPath;
			}
		}
		return null;
	}

	/**
	 * Compute, for each delivery of the request, the shortest paths from the
	 * node of this delivery to the other deliveries, in the given map.
	 * 
	 * @param map
	 *            the map on which we want to compute a journey (and therefore
	 *            the shortest paths between the deliveries)
	 * @return for each delivery, the shortest paths to the other deliveries -->
	 *         N.b: the 'path' to the starting address is not in the set, since
	 *         it is the start of all paths. PRECONDITION: The map contains all
	 *         the nodes of the delivery request (i.e addresses of the requests)
	 * @throws Exception
	 *             An exception is thrown if one of the shortest path could not
	 *             be computed
	 */
	private java.util.Map<DeliveryI, Set<ShortestPathDelivery>> computeShortestPaths(DeliveryRequestI request, MapI map)
			throws Exception {
		java.util.Map<DeliveryI, Set<ShortestPathDelivery>> shortestPaths = new HashMap<DeliveryI, Set<ShortestPathDelivery>>();

		// For each delivery and the store
		Set<DeliveryI> deliveryNodesAndStore = new HashSet<DeliveryI>(request.getDeliveries());

		// Creating a 'fake' delivery to add the store to the list of paths to
		// compute
		deliveryNodesAndStore.add(new Delivery(1, null, null, request.getStoreAddress()));

		// For each delivery and the store , we compute the shortest
		// paths from this delivery to the other deliveries and to the store
		// node
		for (DeliveryI nodeDeliveryStart : deliveryNodesAndStore) {
			Set<ShortestPathDelivery> deliveryStartShortestPaths = new HashSet<ShortestPathDelivery>();
			for (DeliveryI delivGoal : deliveryNodesAndStore) {
				if (delivGoal != nodeDeliveryStart) {
					deliveryStartShortestPaths.add(new ShortestPathDelivery(map, nodeDeliveryStart, delivGoal));
				}
			}

			// Adding the shortest path list of this delivery to the map
			shortestPaths.put(nodeDeliveryStart, deliveryStartShortestPaths);
		}

		return shortestPaths;
	}
}