package test.modelTest;

import static org.junit.Assert.*;

import java.util.Date;

import model.delivery.Delivery;
import model.delivery.DeliveryI;
import model.graph.Node;

import org.junit.Test;

public class TestDelivery {

	@Test
	/**
	 * Testing if getDuration() returns the desired duration.
	 */
	public void getDuration() {
		DeliveryI delivery;
		try {
			delivery = new Delivery(120, new Date(), new Date(), new Node(1, 0, 0));
			assertEquals(delivery.getDeliveryDuration(), 120);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getDurationInMin() returns the desired duration.
	 */
	public void getDurationInMin() {
		DeliveryI delivery;
		try {
			delivery = new Delivery(120, new Date(), new Date(), new Node(1, 0, 0));
			assertEquals(delivery.getDurationInMin(), 2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getMinTime() returns the desired date.
	 */
	public void getMinTime() {
		Date currentTime = new Date();
		DeliveryI delivery;
		try {
			delivery = new Delivery(120, currentTime, null, new Node(1, 0, 0));
			assertEquals(delivery.getMinTime(), currentTime);
			assertTrue(delivery.hasMinTime());
			assertFalse(delivery.hasMaxTime());
			assertFalse(delivery.hasTimeframe());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getMaxTime() returns the desired date.
	 */
	public void getMaxTime() {
		Date currentTime = new Date();
		Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
		DeliveryI delivery;
		try {
			delivery = new Delivery(120, null, currentTimePlus2Min, new Node(1, 0, 0));
			assertEquals(delivery.getMaxTime(), currentTimePlus2Min);
			assertFalse(delivery.hasMinTime());
			assertTrue(delivery.hasMaxTime());
			assertFalse(delivery.hasTimeframe());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	/**
	 * Testing if we can get the delivery time frame.
	 */
	public void hasDeliveryTimeFrame() {
		Date currentTime = new Date();
		Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
		DeliveryI delivery;
		try {
			delivery = new Delivery(120, currentTime, currentTimePlus2Min, new Node(1, 0, 0));
			assertEquals(delivery.getMinTime(), currentTime);
			assertEquals(delivery.getMaxTime(), currentTimePlus2Min);
			assertTrue(delivery.hasMinTime());
			assertTrue(delivery.hasMaxTime());
			assertTrue(delivery.hasTimeframe());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing if getAddress() returns the desired address.
	 */
	public void getAddress() {
		Date currentTime = new Date();
		Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
		DeliveryI delivery;
		try {
			delivery = new Delivery(120, currentTime, currentTimePlus2Min, new Node(1, 0, 0));
			assertEquals(delivery.getAddress(), new Node(1, 0, 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that if two edges are equals, a function returns true.
	 */
	public void testEqualsOverrideGood() {
		Date currentTime = new Date();
		Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
		DeliveryI deliveryA;
		DeliveryI deliveryB;
		try {
			deliveryA = new Delivery(120, currentTime, currentTimePlus2Min, new Node(1, 0, 0));
			deliveryB = new Delivery(120, currentTime, currentTimePlus2Min, new Node(1, 0, 0));
			assertEquals(deliveryA, deliveryB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that if two edges are not equals, a function returns false. 
	 */
	public void testEqualsOverrideBadMinTime() {
		Date currentTime = new Date();
		Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
		Date currentTimePlus3Min = new Date(currentTime.getTime() + 1000 * 60 * 3);
		DeliveryI deliveryA;
		DeliveryI deliveryB;
		try {
			deliveryA = new Delivery(120, currentTime, currentTimePlus2Min, new Node(1, 0, 0));
			deliveryB = new Delivery(120, currentTime, currentTimePlus3Min, new Node(1, 0, 0));
			assertNotEquals(deliveryA, deliveryB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that if one of the values minimal times is 'null', the function returns false. 
	 */
	public void testEqualsOverrideBadMinTimeOneNull() {
		Date currentTime = new Date();
		Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
		DeliveryI deliveryA;
		DeliveryI deliveryB;
		try {
			deliveryA = new Delivery(120, currentTime, currentTimePlus2Min, new Node(1, 0, 0));
			deliveryB = new Delivery(120, null, currentTimePlus2Min, new Node(1, 0, 0));
			assertNotEquals(deliveryA, deliveryB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that if both of the two values minimal times are 'null',  a function returns false.
	 */
	public void testEqualsOverrideGoodMinTimeTwoNull() {
		Date currentTime = new Date();
		Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
		DeliveryI deliveryA;
		DeliveryI deliveryB;
		try {
			deliveryA = new Delivery(120, null, currentTimePlus2Min, new Node(1, 0, 0));
			deliveryB = new Delivery(120, null, currentTimePlus2Min, new Node(1, 0, 0));
			assertEquals(deliveryA, deliveryB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that if one of the two maximal times is 'null', a function returns false.
	 */
	public void testEqualsOverrideBadMaxTimeOneNull() {
		Date currentTime = new Date();
		Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
		DeliveryI deliveryA;
		DeliveryI deliveryB;
		try {
			deliveryA = new Delivery(120, currentTime, currentTimePlus2Min, new Node(1, 0, 0));
			deliveryB = new Delivery(120, currentTime, null, new Node(1, 0, 0));
			assertNotEquals(deliveryA, deliveryB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that if both of the two maximal times are 'null', a function returns false. 
	 */
	public void testEqualsOverrideGoodMaxTimeTwoNull() {
		Date currentTime = new Date();
		DeliveryI deliveryA;
		DeliveryI deliveryB;
		try {
			deliveryA = new Delivery(120, currentTime, null, new Node(1, 0, 0));
			deliveryB = new Delivery(120, currentTime, null, new Node(1, 0, 0));
			assertEquals(deliveryA, deliveryB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that if
	 *  > both minimal times are 'null' &&
	 *  > both maximal times are 'null's
	 *  a function returns false.
	 *  
	 */
	public void testEqualsOverrideGoodMaxMinTimeAllNull() {
		DeliveryI deliveryA;
		DeliveryI deliveryB;
		try {
			deliveryA = new Delivery(120, null, null, new Node(1, 0, 0));
			deliveryB = new Delivery(120, null, null, new Node(1, 0, 0));
			assertEquals(deliveryA, deliveryB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
