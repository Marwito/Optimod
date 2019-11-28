package model.computer.tsp;

/**
 * cette objet lie un sommet à son cout.
 * @author Adrien (je suis dislexique excuser les fotes d'ortographe)
 *
 */
public class SommetCout implements Comparable<SommetCout> {
	private Integer sommet ; 
	private double coutSommet; 

	
	/**
	 * cree un objet qui contien un sommet et son cout 
	 * @param sommet : sommet 
	 * @param coutSommet : cout+Band pour si on ajoute sommet au chemain courant.
	 */
	public SommetCout (Integer sommet,double coutSommet){
		this.sommet = sommet;
		this.coutSommet = coutSommet; 
	}
	

	/**
	  * {@inheritDoc}
	  */
	@Override
	public int hashCode(){
	    return (int) (coutSommet*2);
	  }

	/**
	  * {@inheritDoc}
	  */
	public int compareTo(SommetCout another)  {
	    return  (((SommetCout) another).hashCode()-this.hashCode());    
	}
	
	/**
	 * 
	 * @return le sommet
	 */
	public Integer getSommet() {
		return sommet;
	}

	/**
	 * 
	 * @return le Cout lier a ce Sommet
	 */
	public double getCoutSommet() {
		return coutSommet;
	}
}
