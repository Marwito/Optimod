package model.computer.tsp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * implémantiationd l'Interface TSP permet de calculer un TSP en prenant en compte le plages de livrésons
 * optimisation du bound dynamique. 
 * @author Adrien (je suis dislexique excuser les fotes d'ortographe)
 *
 */
public class TSP3 extends TemplateTSP {
	
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
		double borneInf =0; 
		for (Integer sommet :nonVus){
			// duree de livrésion du sommet non vue 
			borneInf += duree[sommet];
		
			
			// cout de la plus petite arret entrante
			ArrayList<Integer> nonVueEtOrigine = new ArrayList<>(nonVus);
			nonVueEtOrigine.add(sommetCourant);
			int coutMinArretEntrant = Integer.MAX_VALUE;
			for (Integer temp :nonVueEtOrigine){
				if (cout[temp][sommet] <coutMinArretEntrant ) {
					coutMinArretEntrant = cout[temp][sommet];
					
				}
			}
			
			// cout de la plus petite arret Sortante
			ArrayList<Integer> nonVueEtCur = new ArrayList<>(nonVus);
			nonVueEtCur.add(0);
			int coutMinArretSortant = Integer.MAX_VALUE;
			for (Integer temp :nonVueEtCur){
				if (cout[sommet][temp] <coutMinArretSortant ) {
					coutMinArretSortant = cout[sommet][temp];
					
				}
			}
			
			
			borneInf+= (coutMinArretSortant+coutMinArretEntrant)/2.0;
			

			
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
}
