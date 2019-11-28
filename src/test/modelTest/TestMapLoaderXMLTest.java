package test.modelTest;

import static org.junit.Assert.*;

import org.junit.Test;

import model.fileLoader.MapLoaderXML;
import model.graph.Edge;
import model.graph.EdgeI;
import model.graph.Node;
import model.map.Map;

public class TestMapLoaderXMLTest {
	@Test
	/**
	 * Checking that mapLoaderXML loads a map from a XML file correctly
	 */
	public void testLoadMap() {
		// Charging the map from the XML file
		MapLoaderXML mapLoaderXML = new MapLoaderXML();
		Map mapLoaded = new Map();
		try {
			mapLoaded = mapLoaderXML.loadMap("maps/plan3x3.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}

		// Constructing the same map artificially
		Map mapCopy = new Map();
		mapCopy.addNode(new Node(0, 134, 193));
		mapCopy.addNode(new Node(1, 195, 291));
		mapCopy.addNode(new Node(2, 140, 420));
		mapCopy.addNode(new Node(3, 132, 470));
		mapCopy.addNode(new Node(4, 128, 660));
		mapCopy.addNode(new Node(5, 263, 81));
		mapCopy.addNode(new Node(6, 244, 277));
		mapCopy.addNode(new Node(7, 244, 345));
		mapCopy.addNode(new Node(8, 300, 506));

		try {
			mapCopy.addEdge(new Edge(mapCopy.getNodeById(0), mapCopy.getNodeById(1), (double) 9234 / 10, (double) 41 / 10, "v0"));
			mapCopy.addEdge(new Edge(mapCopy.getNodeById(0), mapCopy.getNodeById(5), (double) 13666 / 10, (double) 47 / 10, "h0"));
			mapCopy.addEdge(new Edge(mapCopy.getNodeById(1), mapCopy.getNodeById(0), (double) 9234 / 10, (double) 46 / 10, "v0"));
			mapCopy.addEdge(new Edge(mapCopy.getNodeById(1), mapCopy.getNodeById(2), (double) 11218 / 10, (double) 39 / 10, "v0"));
			mapCopy.addEdge(new Edge(mapCopy.getNodeById(1), mapCopy.getNodeById(6), (double) 4076 / 10, (double) 40 / 10, "h1"));
			mapCopy.addEdge(new Edge(mapCopy.getNodeById(2), mapCopy.getNodeById(1), (double) 11218 / 10, (double) 38 / 10, "v0"));
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Checking that the map loaded correspond to the desired map
		assertEquals(mapLoaded, mapCopy);
	}

	@Test
	/**
	 * Checking that mapLoaderXML loads a map correctly from a XML file which
	 * contains speed lower than 1 meter/second.
	 */
	public void testLoadMapDoublePrecisionSpeed() {
		// Charging the map from the XML file
		MapLoaderXML mapLoaderXML = new MapLoaderXML();
		Map mapLoaded = new Map();
		try {
			mapLoaded = mapLoaderXML.loadMap("maps/plan3x3-LowSpeedAndLength.xml");
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		EdgeI testedEdge = mapLoaded.edgeIterator().next();
		assertNotEquals(testedEdge.getAverageSpeed(), 0.0);
		assertNotEquals(testedEdge.getLength(), 0.0);
	}
}