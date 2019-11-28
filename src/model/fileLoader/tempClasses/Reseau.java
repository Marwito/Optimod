package model.fileLoader.tempClasses;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reseau")
public class Reseau {
	private List<Noeud> noeuds = new ArrayList<Noeud>();
	private List<Troncon> troncons = new ArrayList<Troncon>();

	public Reseau() {
	}

	public Reseau(List<Noeud> noeuds, List<Troncon> troncons) {
		this.noeuds = noeuds;
		this.troncons = troncons;
	}

	@XmlElement(name = "noeud")
	public List<Noeud> getNoeuds() {
		return noeuds;
	}

	public void setNoeuds(List<Noeud> noeuds) {
		this.noeuds = noeuds;
	}

	@XmlElement(name = "troncon")
	public List<Troncon> getTroncons() {
		return troncons;
	}

	public void setTroncons(List<Troncon> troncons) {
		this.troncons = troncons;
	}

	public String toString() {
		String reseauString = "<reseau>" + "\r\n";

		for (Noeud noeud : noeuds) {
			reseauString += "\t" + noeud.toString() + "\r\n";
		}

		for (Troncon troncon : troncons) {
			reseauString += "\t" + troncon.toString() + "\r\n";
		}

		reseauString += "</reseau>" + "\r\n";

		return reseauString;
	}
}
