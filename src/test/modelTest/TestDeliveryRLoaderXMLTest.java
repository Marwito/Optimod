package test.modelTest;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import model.delivery.Delivery;
import model.delivery.DeliveryI;
import model.delivery.DeliveryRequest;
import model.delivery.DeliveryRequestI;
import model.fileLoader.DeliveryRLoaderXML;
import model.fileLoader.MapLoaderXML;
import model.graph.NodeI;
import model.map.Map;

public class TestDeliveryRLoaderXMLTest {
	@Test
	/**
	 * Checking that DeliveryRLoaderXML loads a delivery request from a XML
	 * File will be correctly for deliveries without time frames
	 */
	public void testLoadDeliveryRequestWithoutTimeFrame() throws Throwable {
		// First , loading the map file
		MapLoaderXML mapLoaderXML = new MapLoaderXML();
		Map map = new Map();
		map = mapLoaderXML.loadMap("maps/plan3x3.xml");
		// Then, loading the delivery request
		DeliveryRLoaderXML deliveryRLoaderXML = new DeliveryRLoaderXML();

		DeliveryRequestI deliveryRequest = null;
		try {
			deliveryRequest = deliveryRLoaderXML.loadDeliveryRequest("deliveryrequests/livraisonsForTest6Nodes.xml",
					map);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// Checking the deliveries loaded has no time frame, as expected
		Iterator<DeliveryI> deliveryIterator = deliveryRequest.deliveryIterator();
		while (deliveryIterator.hasNext()) {
			DeliveryI currentDelivery = deliveryIterator.next();
			assertFalse(currentDelivery.hasMinTime() || currentDelivery.hasMaxTime());
		}
		
		Thread.sleep(1); // ensure that there should be no comparison on
		// milliseconds in time frames

		// Creating the expected deliveries
		NodeI storeAddress = map.getNodeById(0);
		Date departureTime = parseTime("8:0:0");
		DeliveryI delivery1 = new Delivery(120, null, null, map.getNodeById(2));
		DeliveryI delivery2 = new Delivery(240, null, null, map.getNodeById(3));
		DeliveryI delivery3 = new Delivery(60, null, null, map.getNodeById(5));
		List<DeliveryI> deliveriesCopy = new LinkedList<DeliveryI>();
		deliveriesCopy.add(delivery1);
		deliveriesCopy.add(delivery2);
		deliveriesCopy.add(delivery3);
		DeliveryRequestI deliveryRequestCopy = new DeliveryRequest(storeAddress, departureTime, deliveriesCopy);

		// Checking the delivery request loaded corresponds to the expected delivery request
		assertEquals(deliveryRequest, deliveryRequestCopy);

		// Checking hours of the loaded delivery request (had an error of parsing before)
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(deliveryRequest.getDepartureTime());
		assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 8);
		assertEquals(calendar.get(Calendar.MINUTE), 0);
		assertEquals(calendar.get(Calendar.SECOND), 0);
	}

	@Test
	/**
	 * Checking that DeliveryRLoaderXML loads a delivery request from a XML
	 * File will be correctly for deliveries with time frames
	 */
	public void testLoadDeliveryRequestWithAndWithoutTimeFrame() throws Throwable {
		// First , loading the map file
		MapLoaderXML mapLoaderXML = new MapLoaderXML();
		Map map = new Map();
		try {
			map = mapLoaderXML.loadMap("maps/plan5x5.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// Then, loading the delivery request
		DeliveryRLoaderXML deliveryRLoaderXML = new DeliveryRLoaderXML();

		DeliveryRequestI deliveryRequest = null;
		deliveryRequest = deliveryRLoaderXML.loadDeliveryRequest("deliveryrequests/livraisons5x5-9-TW_reduction.xml",
				map);

		Thread.sleep(1); // ensure that there should be no comparison on
							// milliseconds in time frames

		// Creating the expected deliveries
		NodeI storeAddress = map.getNodeById(6);
		Date departureTime = parseTime("8:0:0");
		DeliveryI delivery1 = new Delivery(300, parseTime("10:0:0"), parseTime("12:0:0"), map.getNodeById(23));
		DeliveryI delivery2 = new Delivery(300, null, null, map.getNodeById(9));
		DeliveryI delivery3 = new Delivery(900, parseTime("12:0:0"), parseTime("14:0:0"), map.getNodeById(22));
		List<DeliveryI> deliveriesCopy = new LinkedList<DeliveryI>();
		deliveriesCopy.add(delivery1);
		deliveriesCopy.add(delivery2);
		deliveriesCopy.add(delivery3);
		DeliveryRequestI deliveryRequestCopy = new DeliveryRequest(storeAddress, departureTime, deliveriesCopy);

		// Checking the delivery request loaded corresponds to the expected
		// delivery request
		assertEquals(deliveryRequest, deliveryRequestCopy);

		// Checking hours of the loaded delivery request (had an error of
		// parsing before)
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(deliveryRequest.getDepartureTime());
		assertEquals(calendar.get(Calendar.HOUR_OF_DAY), 8);
		assertEquals(calendar.get(Calendar.MINUTE), 0);
		assertEquals(calendar.get(Calendar.SECOND), 0);

	}
	
	/**
	 * This methods receives a String which resembles a Date
	 * It transforms this String into a Date Object
	 * 
	 * @param timeString This string should equal a date
	 * @return An actual date as an object Date is returned
	 */
	private Date parseTime(String timeString) {
		if (timeString == null)
			return null;

		Date date = new Date();

		List<String> timeComponents = Arrays.asList(timeString.split("\\s*:\\s*"));

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeComponents.get(0)));
		cal.set(Calendar.MINUTE, Integer.parseInt(timeComponents.get(1)));
		cal.set(Calendar.SECOND, Integer.parseInt(timeComponents.get(2)));
		date = cal.getTime();

		return date;
	}
}
