package model.computer.tsp;

import java.util.ArrayList;
import java.util.Iterator;

public class TSP1 extends TemplateTSP {
	
	/**
	  * {@inheritDoc}
	  */
	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int coutVus, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage) {
		return new IteratorSeq(nonVus, sommetCrt);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	protected double bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		return 0;
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	protected int coutSuivant(Integer sommetCourant, Integer sommetSuivant,int coutVus, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage){
		return coutVus + cout[sommetCourant][sommetSuivant] + duree[sommetSuivant]; 
	}
}
