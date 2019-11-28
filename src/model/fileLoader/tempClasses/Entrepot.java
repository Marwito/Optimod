package model.fileLoader.tempClasses;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "entrepot")
public class Entrepot {
	private int adresse;
	private String heureDepart;

	@XmlAttribute(name = "adresse", required = true)
	public int getAdresse() {
		return adresse;
	}

	public void setAdresse(int adresse) {
		this.adresse = adresse;
	}

	@XmlAttribute(name = "heureDepart", required = true)
	public String getHeureDepart() {
		return heureDepart;
	}

	public void setHeureDepart(String heureDepart) {
		this.heureDepart = heureDepart;
	}
}
