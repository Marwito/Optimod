package test.modelTest;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;

import junit.framework.TestCase;
import model.delivery.Delivery;
import model.delivery.DeliveryI;
import model.delivery.DeliveryRequestI;
import model.fileLoader.DeliveryRLoaderXML;
import model.fileLoader.MapLoaderXML;
import model.map.MapI;
import model.map.ShortestPathDelivery;

public class TestShortestPathDelivery extends TestCase {

	/** --- INIT AND VARIABLES --- **/
	private ShortestPathDelivery shortestPath;
	private MapI map;
	private DeliveryI deliveryStart;
	private DeliveryI deliveryEnd;

	@Override
	/**
	 * This method creates a new map and a the ShortestPathDelivery in the map.
	 * 
	 */
	protected void setUp() throws Exception {
		// Creating the map
		try {
			map = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// Creating the deliveries
		DeliveryRequestI deliveryRequestForDeliveries = null;
		try {
			deliveryRequestForDeliveries = new DeliveryRLoaderXML()
					.loadDeliveryRequest("deliveryrequests/livraisonsForTest6Nodes.xml", this.map);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Iterator<DeliveryI> deliveryIterator = deliveryRequestForDeliveries.deliveryIterator();
		deliveryStart = deliveryIterator.next();
		deliveryEnd = deliveryIterator.next();

		// Creating the shortest path
		shortestPath = new ShortestPathDelivery(map, deliveryStart, deliveryEnd);
	}
	// end init

	@Test
	/**
	 * Testing that getDeliveryStart() returns the desired delivery.
	 */
	public void testGetStartingDeliveryGood() throws Exception {
		assertEquals(this.deliveryStart, this.shortestPath.getDeliveryStart());
	}

	@Test
	/**
	 * Testing that getDeliveryEnd() returns the desired delivery.
	 */
	public void testGetEndingDeliveryGood() throws Exception {
		assertEquals(this.deliveryEnd, this.shortestPath.getDeliveryEnd());
	}

	@Test
	/**
	 * Testing that getTotalNeededTime() returns the desired time.
	 */
	public void testGetTravelTimeGood() throws Exception {
		assertEquals(this.shortestPath.getTravelTime() + this.shortestPath.getDeliveryStart().getDeliveryDuration()
				+ this.shortestPath.getDeliveryEnd().getDeliveryDuration(), this.shortestPath.getTotalNeededTime());
	}

	@Test
	/**
	 * Testing that equals() returns true if two ShortestPathDelivery are the
	 * same.
	 */
	public void testEqualsOverrideGood() throws Exception {
		// Charging map for A
		MapI mapA = null;
		// Creating the map
		try {
			mapA = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		DeliveryRequestI deliveryRequestForDeliveriesA = null;
		try {
			deliveryRequestForDeliveriesA = new DeliveryRLoaderXML()
					.loadDeliveryRequest("deliveryrequests/livraisonsForTest6Nodes.xml", this.map);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Iterator<DeliveryI> deliveryIterator = deliveryRequestForDeliveriesA.deliveryIterator();
		DeliveryI deliveryStartA = deliveryIterator.next();
		DeliveryI deliveryEndA = deliveryIterator.next();

		ShortestPathDelivery shortestPathA = new ShortestPathDelivery(mapA, deliveryStartA, deliveryEndA);
		// Charging map for B
		MapI mapB = null;
		// Creating the map
		try {
			mapB = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		DeliveryRequestI deliveryRequestForDeliveriesB = null;
		try {
			deliveryRequestForDeliveriesB = new DeliveryRLoaderXML()
					.loadDeliveryRequest("deliveryrequests/livraisonsForTest6Nodes.xml", this.map);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		deliveryIterator = deliveryRequestForDeliveriesB.deliveryIterator();
		DeliveryI deliveryStartB = deliveryIterator.next();
		DeliveryI deliveryEndB = deliveryIterator.next();

		ShortestPathDelivery shortestPathB = new ShortestPathDelivery(mapB, deliveryStartB, deliveryEndB);
		;
		// Checking the two paths are equals
		assertEquals(shortestPathA, shortestPathB);
	}

	@Test
	/**
	 * Testing that equals() returns false if two sShortestPathDelivery are not
	 * the same (two different delivery end).
	 */
	public void testEqualsOverrideBadDeliveryEnd() throws Exception {
		// Charging map for A
		MapI mapA = null;
		// Creating the map
		try {
			mapA = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		DeliveryRequestI deliveryRequestForDeliveriesA = null;
		try {
			deliveryRequestForDeliveriesA = new DeliveryRLoaderXML()
					.loadDeliveryRequest("deliveryrequests/livraisonsForTest6Nodes.xml", this.map);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		Iterator<DeliveryI> deliveryIterator = deliveryRequestForDeliveriesA.deliveryIterator();
		DeliveryI deliveryStartA = deliveryIterator.next();
		DeliveryI deliveryEndA = deliveryIterator.next();

		ShortestPathDelivery shortestPathA = new ShortestPathDelivery(mapA, deliveryStartA, deliveryEndA);
		// Charging map for B
		MapI mapB = null;
		// Creating the map
		try {
			mapB = new MapLoaderXML().loadMap("maps/planTest6Nodes.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		DeliveryRequestI deliveryRequestForDeliveriesB = null;
		try {
			deliveryRequestForDeliveriesB = new DeliveryRLoaderXML()
					.loadDeliveryRequest("deliveryrequests/livraisonsForTest6Nodes.xml", this.map);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		deliveryIterator = deliveryRequestForDeliveriesB.deliveryIterator();
		DeliveryI deliveryStartB = deliveryIterator.next();
		DeliveryI deliveryEndB = new Delivery(deliveryStartA.getDeliveryDuration(), deliveryStartA.getMinTime(),
				deliveryStartA.getMaxTime(), deliveryEndA.getAddress());

		ShortestPathDelivery shortestPathB = new ShortestPathDelivery(mapB, deliveryStartB, deliveryEndB);
		;
		// Checking the two paths are equals
		assertNotEquals(shortestPathA, shortestPathB);
	}

}
