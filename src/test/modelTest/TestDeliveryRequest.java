package test.modelTest;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import model.delivery.Delivery;
import model.delivery.DeliveryI;
import model.delivery.DeliveryRequest;
import model.delivery.DeliveryRequestI;
import model.graph.Node;
import model.graph.NodeI;

public class TestDeliveryRequest extends TestCase {

	/** --- INIT AND VARIABLES --- **/
	private Date currentTime = new Date();
	private Date currentTimePlus2Min = new Date(currentTime.getTime() + 1000 * 60 * 2);
	private Date currentTimePlus10Min = new Date(currentTime.getTime() + 1000 * 60 * 10);
	private DeliveryI delivery1;
	private DeliveryI delivery2;
	private DeliveryI delivery3;
	private NodeI storeAddress = new Node(0, 10, 10);
	private Date departureTime = new Date(currentTime.getTime());
	private List<DeliveryI> deliveries = new LinkedList<DeliveryI>();

	private DeliveryRequestI deliveryRequest;

	/**
	  * This method creates a new Delivery Request with variables defined above.
	  */
	@Override
	protected void setUp() throws Exception {
		delivery1 = new Delivery(120, currentTimePlus2Min, currentTimePlus10Min, new Node(1, 0, 0));
		delivery2 = new Delivery(120, currentTimePlus2Min, currentTimePlus10Min, new Node(2, 1, 1));
		delivery3 = new Delivery(120, currentTimePlus2Min, currentTimePlus10Min, new Node(3, 2, 2));

		deliveries.add(delivery1);
		deliveries.add(delivery2);
		deliveries.add(delivery3);
		deliveryRequest = new DeliveryRequest(storeAddress, departureTime, deliveries);
	}
	// end init

	@Test
	/**
	 * Testing if deliveryIterator() returns the deliveries of the delivery request.
	 */
	public void testDeliveryIterator() throws Exception {
		assertTrue(iteratorEquals(deliveryRequest.deliveryIterator(), this.deliveries));
	}

	@Test
	/**
	 * Testing if getStoreAddress() returns the address of the store.
	 */
	public void testGetStoreAddress() {
		assertEquals(deliveryRequest.getStoreAddress(), this.storeAddress);
	}

	@Test
	/**
	 * Testing if getDepartureTime() returns the address of the store.
	 */
	public void testGetDepartureTime() {
		assertEquals(deliveryRequest.getDepartureTime(), this.departureTime);
	}

	@Test
	/**
	 * Testing that equals() return true if two delivery requests are equals.
	 */
	public void testEqualsOverrideGood() {
		// Creating delivery request A
		DeliveryRequestI deliveryRequestA = this.deliveryRequest;
		// Creating delivery request B
		DeliveryI delivery1B;
		DeliveryI delivery2B;
		DeliveryI delivery3B;
		try {
			delivery1B = new Delivery(120, currentTimePlus2Min, currentTimePlus10Min, new Node(1, 0, 0));
			delivery2B = new Delivery(120, currentTimePlus2Min, currentTimePlus10Min, new Node(2, 1, 1));
			delivery3B = new Delivery(120, currentTimePlus2Min, currentTimePlus10Min, new Node(3, 2, 2));
			NodeI storeAddressB = new Node(0, 10, 10);
			Date departureTimeB = new Date(currentTime.getTime());
			List<DeliveryI> deliveriesB = new LinkedList<DeliveryI>();
			deliveriesB.add(delivery1B);
			deliveriesB.add(delivery2B);
			deliveriesB.add(delivery3B);
			DeliveryRequestI deliveryRequestB = new DeliveryRequest(storeAddressB, departureTimeB, deliveriesB);
			// Checking that the two delivery request are equals
			assertEquals(deliveryRequestA, deliveryRequestB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	/**
	 * Testing that equals() return false if two delivery requests are not
	 * equals if one of the two contains an additional delivery.
	 */
	public void testEqualsOverrideBadMissingDelivery() {
		// Creating delivery request A
		DeliveryRequestI deliveryRequestA = this.deliveryRequest;
		// Creating delivery request B
		DeliveryI delivery1B;
		DeliveryI delivery2B;
		try {
			delivery1B = new Delivery(120, currentTimePlus2Min, currentTimePlus10Min, new Node(1, 0, 0));
			delivery2B = new Delivery(120, currentTimePlus2Min, currentTimePlus10Min, new Node(2, 1, 1));		NodeI storeAddressB = new Node(0, 10, 10);
			Date departureTimeB = new Date(currentTime.getTime());
			List<DeliveryI> deliveriesB = new LinkedList<DeliveryI>();
			deliveriesB.add(delivery1B);
			deliveriesB.add(delivery2B);
			DeliveryRequestI deliveryRequestB = new DeliveryRequest(storeAddressB, departureTimeB, deliveriesB);
			// Checking that the two delivery request are equals
			assertNotEquals(deliveryRequestA, deliveryRequestB);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/****************** -- PRIVATE -- ******************/

	/**
	 * Tests if an iterator (without duplicates) contains a set of given
	 * objects. Return false if the iterator contains duplicated objects or if
	 * the iterator contains an object which is not in the given set of objects.
	 * 
	 * @param it The tested iterator
	 * @param objects The set of objects
	 * @return true if the iterator contains exactly the set of object
	 * @throws Exception
	 * 
	 */
	private <T> boolean iteratorEquals(Iterator<T> it, List<T> objects) throws Exception {
		List<T> itWithoutDuplicate = new LinkedList<T>();
		while (it.hasNext()) {
			if (!itWithoutDuplicate.add(it.next()))
				throw new Exception("The given iterator 'contains' duplicates");
		}
		return objects.equals(itWithoutDuplicate);
	}
}
