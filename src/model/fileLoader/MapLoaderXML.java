package model.fileLoader;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import model.fileLoader.tempClasses.Noeud;
import model.fileLoader.tempClasses.Reseau;
import model.fileLoader.tempClasses.Troncon;
import model.graph.Edge;
import model.graph.Node;
import model.graph.NodeI;
import model.map.Map;

/**
 * A FileLoaderDR can load a map from a xml file.
 */
public class MapLoaderXML implements FileLoaderMap {

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Map loadMap(String fileName) throws Throwable {
		File file = new File(fileName);
		JAXBContext jaxbContext;
		Reseau reseau = new Reseau();

		jaxbContext = JAXBContext.newInstance(Reseau.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		reseau = (Reseau) jaxbUnmarshaller.unmarshal(file);

		// Casting the Noeud into NodeI
		List<Noeud> importedNoeuds = reseau.getNoeuds();
		Set<NodeI> importedNodeI = new HashSet<NodeI>();
		for (Noeud noeud : importedNoeuds) {
			// Check if we are going to create a node with the same id twice.
			for (NodeI node : importedNodeI) {
				if (node.getId() == noeud.getId()) {
					throw new Exception("There musn't be two nodes with id = " + noeud.getId());
				}
			}
			importedNodeI.add(new Node(noeud.getId(), noeud.getX(), noeud.getY()));
		}

		Map map = new Map(importedNodeI);

		// Adding the edges in the map
		List<Troncon> importedTroncons = reseau.getTroncons();
		for (Troncon troncon : importedTroncons) {
			map.addEdge(new Edge(map.getNodeById(troncon.getOrigine()), map.getNodeById(troncon.getDestination()),
					(double) troncon.getLongueur() / 10, (double) troncon.getVitesse() / 10, troncon.getNomRue()));
		}

		return map;
	}
}
