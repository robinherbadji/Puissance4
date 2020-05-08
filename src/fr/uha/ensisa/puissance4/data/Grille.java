package fr.uha.ensisa.puissance4.data;

import fr.uha.ensisa.puissance4.data.evaluation.strategies.Strategie;
import fr.uha.ensisa.puissance4.data.evaluation.strategies.StrategieAmelioree;
import fr.uha.ensisa.puissance4.data.evaluation.strategies.StrategieOddEven;
import fr.uha.ensisa.puissance4.data.evaluation.strategies.StrategieSimple;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * Décrit la grille de jeu du puissance 4
 *
 */
public class Grille {

	private Case[][] grille;

	public Grille() {
		grille = new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for (int i = 0; i < Constantes.NB_COLONNES; i++) {
			for (int j = 0; j < Constantes.NB_LIGNES; j++) {
				grille[i][j] = Case.V;
			}
		}
	}

	/**
	 * Constructeur permettant de créer une copie de la grille donnée en argument
	 * 
	 * @param original
	 * @author Yassine
	 * @author Robin
	 */
	private Grille(Grille original) {
		grille = new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for (int i = 0; i < Constantes.NB_COLONNES; i++) {
			for (int j = 0; j < Constantes.NB_LIGNES; j++) {
				grille[i][j] = original.getCase(j, i);
			}
		}
	}

	/**
	 * Renvoie le contenu de la case aux coordonnées données en argument
	 * 
	 * @param ligne
	 * @param colonne
	 * @return
	 */
	public Case getCase(int ligne, int colonne) {
		try {
			return grille[colonne][ligne];
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public void addCase(int ligne, int colonne, Case symbole) {
		grille[colonne][ligne] = symbole;
	}

	/**
	 * Indique s'il y a encore de la place dans la colonne indiquée
	 * 
	 * @param colonne
	 * @return
	 */
	public boolean isCoupPossible(int colonne) {
		if (colonne >= 0 && colonne < Constantes.NB_COLONNES) {
			return grille[colonne][Constantes.NB_LIGNES - 1] == Case.V;
		} else {
			return false;
		}
	}

	/**
	 * Ajoute le symbole indiqué dans la colonne indiquée ce qui permet de jouer ce
	 * coup
	 * 
	 * @param colonne
	 * @param symbole
	 */
	public void ajouterCoup(int colonne, Case symbole) {
		for (int j = 0; j < Constantes.NB_LIGNES; j++) {
			if (grille[colonne][j] == Case.V) {
				grille[colonne][j] = symbole;
				break;
			}
		}
	}

	/**
	 * Renvoie l'état de la partie
	 * 
	 * @param symboleJoueurCourant
	 * @param tour
	 * @return
	 */
	public int getEtatPartie(Case symboleJoueurCourant, int tour) {
		int victoire;
		if (symboleJoueurCourant == Constantes.SYMBOLE_J1) {
			victoire = Constantes.VICTOIRE_JOUEUR_1;
		} else {
			victoire = Constantes.VICTOIRE_JOUEUR_2;
		}
		int nbAlignes = 0;
		// Vérification alignement horizontaux
		for (int i = 0; i < Constantes.NB_LIGNES; i++) {
			for (int j = 0; j < Constantes.NB_COLONNES; j++) {
				if (grille[j][i] == symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes = 0;
				if (nbAlignes == 4) {
					return victoire;
				}
			}
			nbAlignes = 0;
		}
		// Vérification alignement verticaux
		for (int j = 0; j < Constantes.NB_COLONNES; j++) {
			for (int i = 0; i < Constantes.NB_LIGNES; i++) {
				if (grille[j][i] == symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes = 0;
				if (nbAlignes == 4) {
					return victoire;
				}
			}
			nbAlignes = 0;
		}

		// Vérification alignement diagonaux (bas-gauche vers haut-droit)
		for (int i = 0; i < Constantes.NB_LIGNES - 3; i++)
			for (int j = 0; j < Constantes.NB_COLONNES - 3; j++) {
				for (int x = 0; i + x < Constantes.NB_LIGNES && j + x < Constantes.NB_COLONNES; x++) {
					if (grille[j + x][i + x] == symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes = 0;
					if (nbAlignes == 4) {
						return victoire;
					}
				}
				nbAlignes = 0;
			}

		// Vérification alignement diagonaux (bas-droite vers haut-gauche)
		for (int i = 0; i < Constantes.NB_LIGNES - 3; i++)
			for (int j = Constantes.NB_COLONNES - 1; j >= 3; j--) {
				for (int x = 0; i + x < Constantes.NB_LIGNES && j - x >= 0; x++) {
					if (grille[j - x][i + x] == symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes = 0;
					if (nbAlignes == 4) {
						return victoire;
					}
				}
				nbAlignes = 0;
			}

		if (tour == Constantes.NB_TOUR_MAX) {
			return Constantes.MATCH_NUL;
		}

		return Constantes.PARTIE_EN_COURS;
	}

	///////////////////////////////////////////////////////////////////////////
	// EVALUATION
	///////////////////////////////////////////////////////////////////////////

	// VERSION 5
	/**
	 * Donne un score à la grille en fonction du joueur courant et d'une {@link fr.uha.ensisa.puissance4.data.evaluation.strategies.Strategie Strategie}
	 * 
	 * @param symboleJoueurCourant
	 * @return
	 */	
	public double evaluer(Case symboleJoueurCourant, int type_strategie) {
		Strategie strategie = null;
		switch (type_strategie) {
		case Constantes.IA_STRATEGIE_AMELIOREE:
			strategie = new StrategieAmelioree(this, symboleJoueurCourant);
			break;
		case Constantes.IA_STRATEGIE_ODD_EVEN:
			strategie = new StrategieOddEven(this, symboleJoueurCourant);
			break;
		default:
			strategie = new StrategieSimple(this, symboleJoueurCourant);
			break;
		}
		return strategie.getUtility();
	}

	

	/**
	 * Renvoie true si la grille est uniquement remplie sur la colonne du milieu
	 * 
	 * @return
	 */
	public boolean isSeulementRemplieMilieu() {
		for (int i = 0; i < Constantes.NB_LIGNES; i++) {
			for (int j = 0; j < Constantes.NB_COLONNES / 2; j++) {
				if (this.getCase(i, j) != Case.V || this.getCase(i, this.getSymetrique(j)) != Case.V) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Renvoie true si la grille est verticalement symétrique
	 * 
	 * @return
	 */
	public boolean isSymetrique() {
		for (int i = 0; i < Constantes.NB_LIGNES; i++) {
			for (int j = 0; j < Constantes.NB_COLONNES / 2; j++) {
				if (this.getCase(i, j) != Case.V && this.getCase(i, this.getSymetrique(j)) == Case.V) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Renvoie le symétrique à la colonne de gauche par rapport à l'axe vertical
	 * passant par le milieu de la grille
	 * 
	 * @param colonne
	 * @return le symétrique de la colonne
	 */
	public int getSymetrique(int colonne) {
		return Constantes.NB_COLONNES - 1 - colonne;
	}

	/**
	 * Renvoie true si l'IA courante peut gagner à son prochain coup
	 * 
	 * @param symboleMax
	 * @param tour
	 * @return
	 */
	public boolean isIAGagnante(Case symboleMax, int tour) {
		int etatIA = getEtatPartie(symboleMax, tour);
		if (etatIA != Constantes.PARTIE_EN_COURS) {
			return true;
		}
		return false;
	}

	/**
	 * Renvoie la première colonne gagnante pour l'adversaire à son prochain tour
	 * 
	 * @param symboleMin
	 * @param tour
	 * @return
	 */
	public int isAdversaireGagnant(Case symboleMin, int tour) {
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille grille_next = this.clone();
			if (grille_next.isCoupPossible(col)) {
				grille_next.ajouterCoup(col, symboleMin);
				if (grille_next.getEtatPartie(symboleMin, tour) != Constantes.PARTIE_EN_COURS) {
					return col;
				}
			}
		}
		return Constantes.COUP_NON_DEFINI;
	}

	/**
	 * Clone la grille
	 */
	public Grille clone() {
		Grille copy = new Grille(this);
		return copy;
	}

}
