package model.fileLoader;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import model.delivery.Delivery;
import model.delivery.DeliveryI;
import model.delivery.DeliveryRequest;
import model.delivery.DeliveryRequestI;
import model.fileLoader.tempClasses.DemandeDeLivraisons;
import model.fileLoader.tempClasses.Entrepot;
import model.fileLoader.tempClasses.Livraison;
import model.graph.NodeI;
import model.map.MapI;

/**
 * This class implements the functions defined in the FileLoaderDR interface. It
 * provides the function to import a delivery request from an XML file for a given map.
 *
 */
public class DeliveryRLoaderXML implements FileLoaderDR {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public DeliveryRequestI loadDeliveryRequest(String fileName, MapI map) throws Throwable {
		File file = new File(fileName);
		JAXBContext jaxbContext;
		DemandeDeLivraisons demandeDeLivraisons = new DemandeDeLivraisons();

		try {
			jaxbContext = JAXBContext.newInstance(DemandeDeLivraisons.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			demandeDeLivraisons = (DemandeDeLivraisons) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			throw e; // Make the exception go up.
		}

		// Finding the node corresponding to the storeAdress
		// and translate HeureDepart into Date objectdepartureTime
		Entrepot entrepot = demandeDeLivraisons.getEntrepot();
		NodeI storeAdress = map.getNodeById(entrepot.getAdresse());

		Date departureTime = parseTime(entrepot.getHeureDepart()); // can throw
																	// a
																	// ParseException

		// Casting the Livraisons into Deliveries
		List<Livraison> livraisons = demandeDeLivraisons.getLivraisons();
		List<DeliveryI> deliveries = new LinkedList<DeliveryI>();
		for (Livraison livraison : livraisons) {
			int deliveryDuration = livraison.getDuree();
			Date minTime = parseTime(livraison.getDebutPlage());
			Date maxTime = parseTime(livraison.getFinPlage());
			NodeI adress = map.getNodeById(livraison.getAdresse());
			deliveries.add(new Delivery(deliveryDuration, minTime, maxTime, adress));
		}

		return new DeliveryRequest(storeAdress, departureTime, deliveries);
	}

	/**

	 * This method is given a date as a String. It returns a Date as a
	 * Date-Object. This method is public because it is used by several tests to
	 * parse the time.
	 * 
	 * @param timeString
	 *            The Date written as a String
	 * @return The date written as a Date-Object
	 */
	public static Date parseTime(String timeString) {
		if (timeString == null) {
			return null;
		}

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
