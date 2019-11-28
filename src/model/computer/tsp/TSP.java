package model.computer.tsp;

public interface TSP {
		
	/**
	 * @return true si chercheSolution() s'est terminee parce que la limite de temps avait ete atteinte, avant d'avoir pu explorer tout l'espace de recherche,
	 */
	public Boolean getTempsLimiteAtteint();
	
	/**
	 * Cherche un circuit de duree minimale passant par chaque sommet (compris entre 0 et nbSommets-1)
	 * @param tpsLimite : limite (en millisecondes) sur le temps d'execution de chercheSolution
	 * @param nbSommets : nombre de sommets du graphe
	 * @param cout : cout[i][j] = duree pour aller de i a j, avec 0 <= i < nbSommets et 0 <= j < nbSommets
	 * @param duree : duree[i] = duree pour visiter le sommet i, avec 0 <= i < nbSommets
	 * @param debutPlage : debutPlage[i] = temps relatife a partire du quel la plage horaire la plage horaire du sommet
	 * 			commence (en particulier debutPlage[0] = 0; debutPlage[i] = x <=> plage horaire  de i commence
	 * 			x secondes aprés le début de la tournée) 0 <= i < nbSommets
	 * @param finPlage : finPlage[i] temps relatife a partire du quel la plage horaire la plage horaire du sommet
	 * 			se etmine (en particulier finPlage[0] = infini ; finPlage[i] = x <=> plage horaire  de i fini 
	 * 			x secondes aprés le début de la tournée) 0 <= i < nbSommets
	 */
	public void chercheSolution(int tpsLimite, int nbSommets, int[][] cout, int[] duree, int[] debutPlage ,int[] finPlage);
	
	/**
	 * @param i
	 * @return le sommet visite en i-eme position dans la solution calculee par chercheSolution
	 */
	public Integer getMeilleureSolution(int i);
	
	/** 
	 * @return la duree de la solution calculee par chercheSolution
	 */
	public int getCoutMeilleureSolution();
	
	/** 
	 * @return le nombre d'appel récurcife a la fonctione BandB. 
	 */
	public long getNbappel();
}
