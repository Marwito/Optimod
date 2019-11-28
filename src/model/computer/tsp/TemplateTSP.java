package model.computer.tsp;

import java.util.ArrayList;
import java.util.Iterator;

public abstract class TemplateTSP implements TSP {

	private Integer[] meilleureSolution;
	private int coutMeilleureSolution = 0;
	private Boolean tempsLimiteAtteint;
	private long nbAppel;

	/**
	 * return le nombre d'itération de la methode branchAndBound.
	 * 
	 * @return le nombre d'itération de la methode branchAndBound.
	 */
	public long getNbappel() {
		return nbAppel;
	}

	/**
	 * This method returns the maximal waiting time
	 * 
	 * @return boolean
	 */
	public Boolean getTempsLimiteAtteint() {
		return tempsLimiteAtteint;
	}

	/**
	 * @param tpsLimite
	 * @param nbSommets
	 * @param cout
	 * @param duree
	 */
	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree, int[] debutPlage,
			int[] finPlage) {
		nbAppel = 0;
		tempsLimiteAtteint = false;
		coutMeilleureSolution = Integer.MAX_VALUE;
		meilleureSolution = new Integer[nbSommets];
		ArrayList<Integer> nonVus = new ArrayList<Integer>();
		for (int i = 1; i < nbSommets; i++)
			nonVus.add(i);
		ArrayList<Integer> vus = new ArrayList<Integer>(nbSommets);
		vus.add(0); // le premier sommet visite est 0
		branchAndBound(0, nonVus, vus, 0, cout, duree, System.currentTimeMillis(), tpsLimite, debutPlage, finPlage);
	}
	
	/**
	 * retourn le ieme sommet de la meilleur Solution 
	 * @param i 
	 * @return le ieme sommet de la meilleur Solution 
	 */
	public Integer getMeilleureSolution(int i) {
		if ((meilleureSolution == null) || (i < 0) || (i >= meilleureSolution.length))
			return null;
		return meilleureSolution[i];
	}
	

	/**
	 * retourne le cout de la meilleur solution 
	 * @return coutMeilleureSolution le cout de la meilleur solution 
	 */
	public int getCoutMeilleureSolution() {
		return coutMeilleureSolution;
	}

	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * 
	 * @param sommetCourant
	 * @param nonVus
	 *            : tableau des sommets restant a visiter
	 * @param cout
	 *            : cout[i][j] = duree pour aller de i a j, avec 0 <= i <
	 *            nbSommets et 0 <= j < nbSommets
	 * @param duree
	 *            : duree[i] = duree pour visiter le sommet i, avec 0 <= i <
	 *            nbSommets
	 * @return une borne inferieure du cout des permutations commencant par
	 *         sommetCourant, contenant chaque sommet de nonVus exactement une
	 *         fois et terminant par le sommet 0
	 */
	protected abstract double bound(Integer sommetCourant, ArrayList<Integer> nonVus, int[][] cout, int[] duree);

	/**
	 * Methode devant etre redefinie par les sous-classes de TemplateTSP
	 * 
	 * @param sommetCrt
	 * @param nonVus
	 *            : tableau des sommets restant a visiter
	 *            
	 * @param coutVus
	 * 			  : cout du chemain courant         
	 * @param cout
	 *            : cout[i][j] = duree pour aller de i a j, avec 0 <= i <
	 *            nbSommets et 0 <= j < nbSommets
	 * @param duree
	 *            : duree[i] = duree pour visiter le sommet i, avec 0 <= i <
	 *            nbSommets
	 * @param debutPlage 
	 * 			  : debutPlage[i] = temps relatife a partire du quel la plage horaire la plage horaire du sommet
	 * 			commence (en particulier debutPlage[0] = 0; debutPlage[i] = x <=> plage horaire  de i commence
	 * 			x secondes aprés le début de la tournée) 0 <= i < nbSommets
	 * @param finPlage
	 * 			  : finPlage[i] temps relatife a partire du quel la plage horaire la plage horaire du sommet
	 * 			se etmine (en particulier finPlage[0] = infini ; finPlage[i] = x <=> plage horaire  de i fini 
	 * 			x secondes aprés le début de la tournée) 0 <= i < nbSommets
	 * @return un iterateur permettant d'iterer sur tous les sommets de nonVus
	 */

	protected abstract Iterator<Integer> iterator(Integer sommetCrt, ArrayList<Integer> nonVus, int coutVus, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage);
	
	/**
	 * 
	 * @param sommetCourant
	 * @param sommetSuivant
	 * @param coutVus
	 * 			: cout du chemain courant 
	 * @param cout
	 *			: cout[i][j] = duree pour aller de i a j, avec 0 <= i <
	 *            nbSommets et 0 <= j < nbSommets
	 * @param duree
	 * 			: duree[i] = duree pour visiter le sommet i, avec 0 <= i <
	 *            nbSommets
	 * @param debutPlage 
	 * 			  : debutPlage[i] = temps relatife a partire du quel la plage horaire la plage horaire du sommet
	 * 			commence (en particulier debutPlage[0] = 0; debutPlage[i] = x <=> plage horaire  de i commence
	 * 			x secondes aprés le début de la tournée) 0 <= i < nbSommets
	 * @param finPlage
	 * 			  : finPlage[i] temps relatife a partire du quel la plage horaire la plage horaire du sommet
	 * 			se etmine (en particulier finPlage[0] = infini ; finPlage[i] = x <=> plage horaire  de i fini 
	 * 			x secondes aprés le début de la tournée) 0 <= i < nbSommets
	 * @return cout du chimain courant suivi de sommetSuivant
	 * 		
	 */
	protected abstract int coutSuivant(Integer sommetCourant, Integer sommetSuivant, int coutVus, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage); 
	

	/**
	 * Methode definissant le patron (template) d'une resolution par separation et evaluation (branch and bound) du TSP
	 * @param sommetCrt le dernier sommet visite
	 * @param nonVus la liste des sommets qui n'ont pas encore ete visites
	 * @param vus la liste des sommets visites (y compris sommetCrt)
	 * @param coutVus la somme des couts des arcs du chemin passant par tous les sommets de vus + la somme des duree des sommets de vus
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @param tpsDebut : moment ou la resolution a commence
	 * @param tpsLimite : limite de temps pour la resolution
	 * @param debutPlage 
	 * 			: debutPlage[i] = temps relatife a partire du quel la plage horaire la plage horaire du sommet
	 * 			commence (en particulier debutPlage[0] = 0; debutPlage[i] = x <=> plage horaire  de i commence
	 * 			x secondes aprés le début de la tournée) 0 <= i < nbSommets
	 * @param finPlage
	 * 			: finPlage[i] temps relatife a partire du quel la plage horaire la plage horaire du sommet
	 * 			se etmine (en particulier finPlage[0] = infini ; finPlage[i] = x <=> plage horaire  de i fini 
	 * 			x secondes aprés le début de la tournée) 0 <= i < nbSommets
	 */	
	 void branchAndBound(int sommetCrt, ArrayList<Integer> nonVus, ArrayList<Integer> vus, int coutVus, int[][] cout, int[] duree, long tpsDebut, int tpsLimite, int[] debutPlage ,int[] finPlage){
		 if (System.currentTimeMillis() - tpsDebut > tpsLimite){
			 tempsLimiteAtteint = true;
			 return;
		 }
		 nbAppel++;
	     if (nonVus.size() == 0){ // tous les sommets ont ete visites
	    	coutVus += cout[sommetCrt][0];
	    	if (coutVus < coutMeilleureSolution){ // on a trouve une solution meilleure que meilleureSolution
	    		vus.toArray(meilleureSolution);
	    		coutMeilleureSolution = coutVus;
	    	}
	    } else if (coutVus + bound(sommetCrt, nonVus, cout, duree) < coutMeilleureSolution){
	    	Iterator<Integer> it = iterator(sommetCrt, nonVus, coutVus, cout, duree, debutPlage , finPlage);
	        while (it.hasNext()){
	        	Integer prochainSommet = it.next();
	        	int coutsuivant = coutSuivant( sommetCrt,  prochainSommet,coutVus,cout, duree, debutPlage , finPlage); 
	        	if (coutsuivant < Integer.MAX_VALUE/100){
		        	vus.add(prochainSommet);
		        	nonVus.remove(prochainSommet);
		        	branchAndBound(prochainSommet, nonVus, vus, coutSuivant( sommetCrt,  prochainSommet,coutVus,cout, duree, debutPlage , finPlage), cout, duree, tpsDebut, tpsLimite,debutPlage,finPlage);
		        	vus.remove(prochainSommet);
		        	nonVus.add(prochainSommet);
	        	}
	        }	    
	    }
	}
}
