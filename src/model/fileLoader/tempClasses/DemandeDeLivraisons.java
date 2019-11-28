package model.fileLoader.tempClasses;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "demandeDeLivraisons")
public class DemandeDeLivraisons {

	private List<Livraison> livraisons;
	private Entrepot entrepot;

	@XmlElement(name = "livraison")
	public List<Livraison> getLivraisons() {
		return livraisons;
	}

	public void setLivraisons(List<Livraison> livraisons) {
		this.livraisons = livraisons;
	}

	@XmlElement(name = "entrepot")
	public Entrepot getEntrepot() {
		return entrepot;
	}

	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}
}