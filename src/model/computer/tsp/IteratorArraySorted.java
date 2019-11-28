package model.computer.tsp;

import java.util.Arrays;
import java.util.Iterator;

public class IteratorArraySorted implements Iterator<Integer>{
	private SommetCout[] candidats;
	private int nbCandidats;

	/**
	 * Create a counter for 
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 */
	public IteratorArraySorted(SommetCout[] nonVus){
		SommetCout [] tab = new SommetCout[nonVus.length];
		for (int i= 0 ; i<nonVus.length ; i++)
			tab[i] = nonVus[i];
		

		Arrays.sort(tab);

		candidats = tab; 
		nbCandidats = nonVus.length;
	}
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	public boolean hasNext() {
		return nbCandidats > 0;
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public Integer next() {
		return candidats[--nbCandidats].getSommet();
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public void remove() {}

}