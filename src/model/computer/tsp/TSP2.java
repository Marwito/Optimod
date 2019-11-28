package model.computer.tsp;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * implémantiationd l'Interface TSP permet de calculer un TSP en prenant en compte le plages de livrésons 
 * @author Adrien (je suis dislexique excuser les fotes d'ortographe)
 *
 */
public class TSP2 extends TemplateTSP {
	
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
		
		return 0;
	}
	/**
	  * {@inheritDoc}
	  */
	@Override
	protected int coutSuivant(Integer sommetCourant, Integer sommetSuivant,int coutVus, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage){
		if (coutVus + cout[sommetCourant][sommetSuivant] + duree[sommetSuivant] > finPlage [sommetSuivant] ){// plage depasse
			//System.out.println("dépaasement de plage");
			return Integer.MAX_VALUE;
		}
		if (coutVus + cout[sommetCourant][sommetSuivant] < debutPlage [sommetSuivant] ){// plage depasse
			return debutPlage [sommetSuivant] + duree[sommetSuivant];
		}
		return coutVus + cout[sommetCourant][sommetSuivant] + duree[sommetSuivant]; 
	}
}
