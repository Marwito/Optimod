package model.fileLoader.tempClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "livraison")
public class Livraison {
	private int adresse;
	private String debutPlage;
	private int duree;
	private String finPlage;

	@XmlAttribute(name = "adresse", required = true)
	public int getAdresse() {
		return adresse;
	}

	public void setAdresse(int adresse) {
		this.adresse = adresse;
	}

	@XmlAttribute(name = "debutPlage", required = false)public String getDebutPlage() {
		return debutPlage;
	}

	public void setDebutPlage(String debutPlage) {
		this.debutPlage = debutPlage;
	}

	@XmlAttribute(name = "duree", required = true)
	public int getDuree() {
		return duree;
	}

	public void setDuree(int duree) {
		this.duree = duree;
	}

	@XmlAttribute(name = "finPlage", required = false)
	public String getFinPlage() {
		return finPlage;
	}

	public void setFinPlage(String finPlage) {
		this.finPlage = finPlage;
	}
}
