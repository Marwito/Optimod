package model.computer.tsp;

import java.util.Collection;
import java.util.Iterator;

public class IteratorSeq implements Iterator<Integer> {

	private Integer[] candidats;
	private int nbCandidats;

	/**
	 * Create a counter for 
	 * Cree un iterateur pour iterer sur l'ensemble des sommets de nonVus
	 * @param nonVus
	 * @param sommetCrt
	 */
	public IteratorSeq(Collection<Integer> nonVus, int sommetCrt){
		this.candidats = new Integer[nonVus.size()];
		nbCandidats = 0;
		for (Integer s : nonVus){
			candidats[nbCandidats++] = s;
		}
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
		return candidats[--nbCandidats];
	}

	/**
	  * {@inheritDoc}
	  */
	@Override
	public void remove() {}

}
