package fr.uha.ensisa.puissance4.data.evaluation.strategies;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * Classe qui permet de définir une stratégie pour le calcul des évaluations d'une {@link fr.uha.ensisa.puissance4.data.Grille Grille}
 * @author Robin
 * @author Yassine
 * @see StrategieSimple
 * @see StrategieAmelioree
 */
public abstract class Strategie {

	protected Grille grille;
	protected Case symboleJoueurCourant;
	protected Case symboleJoueurAdverse;

	public Strategie(Grille grille, Case symboleJoueurCourant) {
		this.grille = grille;
		this.symboleJoueurCourant = symboleJoueurCourant;
		this.symboleJoueurAdverse = getSymboleAdverse(symboleJoueurCourant);
	}

	/**
	 * Renvoie un score de l'évaluation de la grille en fonction du joueur courant
	 * 
	 * @return
	 */
	public abstract double getUtility();

	/**
	 * Renvoie l'autre symbole que celui passé en paramètre
	 * 
	 * @param symboleJoueurCourant
	 * @return
	 */
	private Case getSymboleAdverse(Case symboleJoueurCourant) {
		Case symboleJoueurAdverse = Constantes.SYMBOLE_J1;
		if (symboleJoueurCourant == Constantes.SYMBOLE_J1) {
			symboleJoueurAdverse = Constantes.SYMBOLE_J2;
		}
		return symboleJoueurAdverse;
	}

}
