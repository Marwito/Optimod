package model.computer.tsp;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * implémantiationd l'Interface TSP permet de calculer un TSP en prenant en compte le plages de livrésons
 * optimisation du bound statyque en . 
 * @author Adrien (je suis dislexique excuser les fotes d'ortographe)
 *
 */
public class TSP4 extends TemplateTSP {
	private int[] minCoutsEntrant; 
	private int[] minCoutsSortant; 
	private int[][] cout; 
	/**
	  * {@inheritDoc}
	  */
	@Override
	protected Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int coutVus, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage){
		return new IteratorSeq(nonVus, sommetCrt);
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
			if (debutPlage [sommetSuivant]+ duree[sommetSuivant] > finPlage [sommetSuivant] ){// plage depasse
				return Integer.MAX_VALUE;
			}
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
