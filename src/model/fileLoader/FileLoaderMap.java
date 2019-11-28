package model.fileLoader;

import model.map.Map;

/**
 * A FileLoaderMap can load a map from a file.
 */
public interface FileLoaderMap {

	/**
	 * Loads a map out of a file.
	 * 
	 * @param fileName
	 *            The name of the file from which the map is loaded
	 * @return the loaded map
	 * @throws Throwable
	 *             If the file loaded does not have the correct format
	 */
	public Map loadMap(String fileName) throws Throwable;
}
