package model.delivery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.graph.EdgeI;
import model.map.ShortestPathDelivery;

/**
 * Generates the roadmap of a delivery journey
 * @author Benoit, Marwan
 *
 */
public class RoadMap {
	private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static final String nl = System.getProperty("line.separator");
	
	private DeliveryJourneyI deliveryJourney;
	
	/**
	 * @param deliveryJourney The computed delivery journey
	 * @throws Exception Throws an exception if the creation of the roadmap fails
	 */
	public RoadMap(DeliveryJourneyI deliveryJourney) throws Exception {
		this.deliveryJourney = deliveryJourney;
		createRoadMap();
	}
	
	/**
	 * Generates the roadmap.
	 * @throws Exception throws an exception if the delivery journey is null
	 */
	private void createRoadMap() throws Exception {
		if (deliveryJourney == null) {
			throw new Exception("Cannot create the Roadmap if the delivery journey is null.");
		}
		
		List<ShortestPathDelivery> journey = deliveryJourney.getJourney();
		DeliveryRequestI request = deliveryJourney.getRequest();

		String roadMapString = "";
		roadMapString += "-------------------------------------------------" + nl;
		roadMapString += "Delivery journey starts on : " + request.getDepartureTime() + nl;
		roadMapString += "-------------------------------------------------" + nl + nl;
		
		for (int i = 0; i < journey.size() - 1; i++) {
			roadMapString += deliveryInformationToString(journey, i);
		}
		// Last itinerary to Store :
		String storeString = "";
		storeString += "-------------------------------------------------" + nl;
		storeString += "Back to Store : " + nl;
		storeString += "-------------------------------------------------" + nl;
		storeString += "Address : " + request.getStoreAddress().getId() + nl;
		storeString += "Arrival Time : " + deliveryJourney.getDeliveryJourneyArrivalTime() + nl;
		storeString += "Itinerary : " + nl;
		storeString += itineraryToString(journey.get(journey.size() - 1).getPath()) + nl;
		
		roadMapString += storeString + nl;
		
		createFileFromString(roadMapString);
	}
	
	/**
	 * Constructs the string containing all details of each delivery to be written later into the text file
	 * @param journey List of paths of all deliveries
	 * @param index Index of the current delivery's path
	 * @return The String with all parameters of each delivery
	 */
	private String deliveryInformationToString(List<ShortestPathDelivery> journey, int index) {
		ShortestPathDelivery currentPath = journey.get(index);
		DeliveryI currentDelivery = currentPath.getDeliveryEnd();
		
		Date currentDeliveryDepartureTime = deliveryJourney.getDepartureTime(currentDelivery);
		Date currentDeliveryArrivalTime = deliveryJourney.getArrivalTime(currentDelivery);
		int currentDeliveryWaitingTime = deliveryJourney.getWaitingTime(currentDelivery);
		List<EdgeI> currentDeliveryItinerary = currentPath.getPath();
		
		String deliveryString = "";
		deliveryString += "*************************************************" + nl;
		deliveryString += "Delivery : " + (index + 1) + nl;
		deliveryString += "*************************************************" + nl;
		deliveryString += "Address : " + currentDelivery.getAddress().getId() + nl;
		deliveryString += "Minimum Time of the time window : " + dateToHourString(currentDelivery.getMinTime()) + nl;
		deliveryString += "Maximum Time of the time window : " + dateToHourString(currentDelivery.getMaxTime()) + nl;
		deliveryString += "Arrival Time : " + dateToHourString(currentDeliveryArrivalTime) + nl;
		deliveryString += "Waiting Time : " + currentDeliveryWaitingTime + nl;
		deliveryString += "Departure Time : " + dateToHourString(currentDeliveryDepartureTime) + nl;
		deliveryString += "Itinerary : " + nl;
		deliveryString += itineraryToString(currentDeliveryItinerary) + nl;
		
		return deliveryString;
	}
	/**
	 * Constructs the String containing the itinerary of each delivery's path
	 * @param itinerary List of edges of a delivert's path.
	 * @return The string containing roads and corresponding covered distances in a path
	 */
	private String itineraryToString(List<EdgeI> itinerary) {
		String itineraryString = "";
		
		int distance = 0;
		EdgeI currentEdge = itinerary.get(0);
		EdgeI previousEdge = currentEdge;
		
		for (int i = 0; i < itinerary.size(); i++) {
			currentEdge = itinerary.get(i);
			
			if (currentEdge.getRoadName().equals(previousEdge.getRoadName())) {
				distance += (int)previousEdge.getLength();
			}
			else {
				itineraryString += "Take the road " + previousEdge.getRoadName() + " on " +
						distance + " meters" + nl;
				distance = (int)currentEdge.getLength();
			}
			
			previousEdge = currentEdge;
		}
		itineraryString += "Take the road " + previousEdge.getRoadName() + " on " +
				distance + " meters" + nl;
	
		return itineraryString;
	}
	/**
	 * Converts a date to a string
	 * @param date.
	 * @return The string containing a date in a "HH:mm:ss" format
	 */
	private String dateToHourString(Date date) {
		if (date == null) {
			return "none";
		}
		return timeFormat.format(date);
	}
	/**
	 * Creates the text file and writes the roadmap string into it and closes the file at the end.
	 * @param roadMapString The string containing all details of 
	 */
	private void createFileFromString(String roadMapString) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter("./RoadMap.txt"));
		writer.write(roadMapString);
		writer.close();
	}
 }
