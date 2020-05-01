package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public abstract class Algorithm {

	protected int levelIA;
	protected Grille grilleDepart;
	protected int tourDepart;
	protected int tourMax;
	protected int tourExplore;

	// protected Joueur joueurActuel;

	/**
	 * Symbole adversaire
	 */
	protected Case symboleMin;
	/**
	 * Symbole joueur courant
	 */
	protected Case symboleMax;

	/**
	 * Instanciation de l'algorithme de Jeu
	 * 
	 * @param levelIA
	 * @param grilleDepart
	 * @param joueurActuel
	 * @param tour
	 */
	public Algorithm(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		this.levelIA = levelIA;
		this.grilleDepart = grilleDepart;
		this.tourDepart = tour;
		this.tourMax = Math.min(tourDepart + levelIA, Constantes.NB_TOUR_MAX);
		// this.joueurActuel = joueurActuel;
		if (joueurActuel.getOrder() == Constantes.JOUEUR_1) {
			symboleMax = Constantes.SYMBOLE_J1;
			symboleMin = Constantes.SYMBOLE_J2;
		} else {
			symboleMax = Constantes.SYMBOLE_J2;
			symboleMin = Constantes.SYMBOLE_J1;
		}
	}

	/**
	 * Lancement de l'algorithme de Jeu
	 * 
	 * @return le coup choisi pour la grille indiqué lors de l'instantiation de
	 *         l'algo
	 */
	public abstract int choisirCoup();

	/**
	 * Renvoie un tableau d'indices des colonnes ordonné avec un parcours allant du
	 * centre vers l'extérieur, alternativement à gauche puis à droite.</br>
	 * </br>
	 * <i>Ici avec 7 colonnes, cette méthode renverra donc {3,2,4,1,5,0,6}</i>
	 * 
	 * @return
	 */
	protected int[] classementColonnes() {
		int[] colonnes = new int[Constantes.NB_COLONNES];
		for (int i = 0; i < Constantes.NB_COLONNES; i++) {
			colonnes[i] = Constantes.NB_COLONNES / 2 + (1 - 2 * (i % 2)) * (i + 1) / 2;
		}
		return colonnes;
	}

	/**
	 * Renvoie la dernière colonne du tableau classé selon
	 * {@link #classementColonnes()}</br>
	 * </br>
	 * <i>Ici avec 7 colonnes, cette méthode renverra donc 6</i>
	 * 
	 * @return
	 */
	protected int lastColonne() {
		return Constantes.NB_COLONNES / 2 - 1;
	}

	/**
	 * Renvoie la colonne centrale pour les
	 * {@link fr.uha.ensisa.puissance4.util.Constantes#STRATEGIE_DEPART
	 * STRATEGIE_DEPART} premiers tours de la partie
	 * 
	 * @return
	 */
	protected int coupDepartPartie() {
		if (tourDepart <= Constantes.NB_TOURS_DEPART && grilleDepart.isSeulementRemplieMilieu()) {
			return (Constantes.NB_COLONNES / 2);
		}
		return Constantes.COUP_NON_DEFINI;
	}

}
