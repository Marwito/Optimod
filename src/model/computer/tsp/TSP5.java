package model.computer.tsp;

import java.util.ArrayList;
import java.util.Iterator;

public class TSP5 extends TemplateTSP {
	private int[] minCoutsEntrant; 
	private int[] minCoutsSortant; 
	private int[][] cout; 
	/**
	  * {@inheritDoc}
	  */
	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int coutVus, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage) {
		SommetCout[] sommetCouts = new SommetCout[nonVus.size()]; 
		int i = 0; 
		for (Integer sommetSuivant : nonVus ){
			sommetCouts[i] = new SommetCout(sommetSuivant, coutSuivant( sommetCrt,  sommetSuivant, coutVus, cout,  duree,  debutPlage , finPlage)+bound( sommetCrt,  nonVus,  cout,  duree));
			i++; 
		}
		
		return new IteratorArraySorted(sommetCouts);
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	protected double bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree) {
		double borneInf =0; 
		if (cout != this.cout){
			mAJCout(cout);
		}
		for (Integer sommet :nonVus){
			// duree de livrésion du sommet non vue 
			borneInf += duree[sommet];
			
			
			borneInf+= (minCoutsEntrant[sommet]+minCoutsSortant[sommet])/2.0;
			

			
		}
		return borneInf;
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	protected int coutSuivant(Integer sommetCourant, Integer sommetSuivant,int coutVus, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage){
		if (coutVus + cout[sommetCourant][sommetSuivant] + duree[sommetSuivant] > finPlage [sommetSuivant] ){// plage depasse
			return Integer.MAX_VALUE;
		}
		if (coutVus + cout[sommetCourant][sommetSuivant] < debutPlage [sommetSuivant] ){// plage depasse
			return debutPlage [sommetSuivant] + duree[sommetSuivant];
		}
		return coutVus + cout[sommetCourant][sommetSuivant] + duree[sommetSuivant]; 
	}
	
	/**
	 * met a jour minCoutsEntrant, minCoutsSortant 
	 * 
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i <
	 *            nbSommets et 0 <= j < nbSommets
	 */
	private void mAJCout(int[][] cout){
		this.cout = cout;
		minCoutsEntrant = new int[cout.length];
		minCoutsSortant =  new int[cout.length];
		for (int i = 0; i<cout.length ; i++){
			// duree de livrésion du sommet non vue 
		
			
			// cout de la plus petite arret entrante

			int coutMinArretEntrant = Integer.MAX_VALUE;
			for (int j = 0; j<cout.length ; j++){
				if (cout[j][i] <coutMinArretEntrant ) {
					coutMinArretEntrant = cout[j][i];
					
				}
			}
			minCoutsEntrant[i]= coutMinArretEntrant;
			
			// cout de la plus petite arret Sortante

			int coutMinArretSortant = Integer.MAX_VALUE;
			for (int j = 0; j<cout.length ; j++){
				if (cout[i][j] <coutMinArretSortant ) {
					coutMinArretSortant = cout[i][j];
					
				}
			}	
			minCoutsSortant[i]= coutMinArretSortant;
		}
	}
}
