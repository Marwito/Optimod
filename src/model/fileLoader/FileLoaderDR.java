package model.fileLoader;

import model.delivery.DeliveryRequestI;
import model.map.MapI;

/**
 * This interface provides the function for loading a delivery request from an
 * XML file, for a given map.
 *
 */
public interface FileLoaderDR {

	/**
	 * Loads a delivery request out of a file.
	 * 
	 * @param fileName
	 *            the name of the file that will be loaded (file including the
	 *            deliveries)
	 * @param map
	 *            the map upon which the delivery request is loaded
	 * @return the loaded delivery request
	 * @throws Exception
	 *             If the file loaded does not have the correct format
	 */
	public DeliveryRequestI loadDeliveryRequest(String fileName, MapI map) throws Throwable;
}
