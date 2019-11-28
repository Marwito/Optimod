package model.fileLoader.tempClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "troncon")
public class Troncon {
	private int destination;
	private int longueur;
	private String nomRue;
	private int origine;
	private int vitesse;

	public Troncon() {
	}

	public Troncon(int destination, int longueur, String nomRue, int origine, int vitesse) {
		this.destination = destination;
		this.longueur = longueur;
		this.nomRue = nomRue;
		this.origine = origine;
		this.vitesse = vitesse;
	}

	@XmlAttribute(name = "destination", required = true)
	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	@XmlAttribute(name = "longueur", required = true)
	public int getLongueur() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur = longueur;
	}

	@XmlAttribute(name = "nomRue", required = true)
	public String getNomRue() {
		return nomRue;
	}

	public void setNomRue(String nomRue) {
		this.nomRue = nomRue;
	}

	@XmlAttribute(name = "origine", required = true)
	public int getOrigine() {
		return origine;
	}

	public void setOrigine(int origine) {
		this.origine = origine;
	}

	@XmlAttribute(name = "vitesse", required = true)
	public int getVitesse() {
		return vitesse;
	}

	public void setVitesse(int vitesse) {
		this.vitesse = vitesse;
	}

	public String toString() {
		return "<troncon destination=\"" + this.destination + "\" longueur=\"" + this.longueur + "\" nomRue=\""
				+ this.nomRue + "\" origine=\"" + this.origine + "\" vitesse=\"" + this.vitesse + "\"/>";
	}
}
