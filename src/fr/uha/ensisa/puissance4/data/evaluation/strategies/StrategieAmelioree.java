package fr.uha.ensisa.puissance4.data.evaluation.strategies;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonHorizontale;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonObliqueDroiteGauche;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonObliqueGaucheDroite;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonVerticale;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Groupe3Combinaisons;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * Stratégie améliorée pour le calcul des évaluations d'une
 * {@link fr.uha.ensisa.puissance4.data.Grille Grille}
 * <p>
 * La stratégie consiste à extraire toutes les
 * {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison
 * Combinaison} de la {@link fr.uha.ensisa.puissance4.data.Grille Grille}.
 * Ensuite il ne reste plus qu'à appeler la méthode
 * {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison#getScore()
 * getScore()} sur chacune des
 * {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison
 * Combinaison} afin d'en déduire le score total pour le joueur courant. En plus
 * de cela, cette stratégie ajoute le score issue de la classe
 * {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Groupe3Combinaisons
 * Groupe3Combinaisons}<br>
 * </p>
 * 
 * @author Robin
 * @author Yassine
 * @see Strategie
 * @see StrategieAmelioree
 */
public class StrategieAmelioree extends Strategie {
	/*
	protected int tourExplore;
	protected int tourDepart;
	
	public StrategieAmelioree(Grille grille, Case symboleJoueurCourant, int tourDepart, int tourExplore) {
		super(grille, symboleJoueurCourant);
		this.tourDepart = tourDepart;
		this.tourExplore = tourExplore;
	}
	*/
	public StrategieAmelioree(Grille grille, Case symboleJoueurCourant) {
		super(grille, symboleJoueurCourant);
	}

	public double getUtility() {
		double attaque = Constantes.COEF_IA * evaluerJoueur(symboleJoueurCourant);
		double defense = Constantes.COEF_OTHER * evaluerJoueur(symboleJoueurAdverse);
		double utility = attaque - defense;
		return utility;
	}
	
	/**
	 * Renvoie le même score que
	 * {@link StrategieSimple#evaluerJoueur(Case symboleJoueur)} en ajoutant le
	 * score de la stratégie issue de
	 * {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Groupe3Combinaisons
	 * Groupe3Combinaisons}
	 * 
	 * @param symboleJoueur
	 * @return
	 */
	private double evaluerJoueur(Case symboleJoueur) {
		Groupe3Combinaisons groupe3 = new Groupe3Combinaisons();
		double score = 0;

		// Vérification alignement horizontaux
		// Parcourt des 6 lignes
		for (int i = 0; i < Constantes.NB_LIGNES; i++) {
			// Parcours de 4 possiblilités
			for (int j = 0; j < Constantes.NB_COLONNES - Constantes.NB_JETONS_VICTOIRE + 1; j++) {
				int[] coord = { i, j };
				Combinaison combinaison = new CombinaisonHorizontale(symboleJoueur, coord);
				// Combinaison combinaison = new CombinaisonHorizontale(symboleJoueur);
				for (int k = 0; k < Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i, j + k);
					combinaison.addSymbole(symbole);
					// System.out.println("ligne : "+j+" et colonne : "+(i+k));
				}
				combinaison.addBonus(grille.getCase(i, j + Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				if (combinaison.has3JetonsGagnants()) {
					groupe3.add(coord);
				}
				// System.out.println(combinaison.getNbCombinaison());
				// System.out.println("colonne : "+j+" et ligne : "+i+" /
				// "+combinaison.toString());
				// System.out.println(combinaison.toString());
			}
		}

		// Vérification alignement verticaux
		// Parcours des 3 lignes
		for (int i = 0; i < Constantes.NB_LIGNES - Constantes.NB_JETONS_VICTOIRE + 1; i++) {
			for (int j = 0; j < Constantes.NB_COLONNES; j++) {
				int[] coord = { i, j };
				Combinaison combinaison = new CombinaisonVerticale(symboleJoueur, coord);
				// Combinaison combinaison = new CombinaisonVerticale(symboleJoueur);
				for (int k = 0; k < Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i + k, j);
					combinaison.addSymbole(symbole);
				}
				combinaison.addBonus(grille.getCase(i + Constantes.NB_JETONS_VICTOIRE, j));
				score += combinaison.getScore();
				if (combinaison.has3JetonsGagnants()) {
					groupe3.add(coord);
				}
				// System.out.println(combinaison.getNbCombinaison());
				// System.out.println("colonne : "+j+" et ligne : "+i);
				// System.out.println(combinaison.toString());
			}
		}

		// Vérification alignement diagonaux (bas-gauche vers haut-droit)
		// Parcours des 3 lignes
		for (int i = 0; i < Constantes.NB_LIGNES - Constantes.NB_JETONS_VICTOIRE + 1; i++) {
			// Parcours des 4 colonnes
			for (int j = 0; j < Constantes.NB_COLONNES - Constantes.NB_JETONS_VICTOIRE + 1; j++) {
				int[] coord = { i, j };
				Combinaison combinaison = new CombinaisonObliqueGaucheDroite(symboleJoueur, coord);

				// Combinaison combinaison = new CombinaisonObliqueGaucheDroite(symboleJoueur);
				for (int k = 0; k < Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i + k, j + k);
					combinaison.addSymbole(symbole);
				}
				combinaison
						.addBonus(grille.getCase(i + Constantes.NB_JETONS_VICTOIRE, j + Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				if (combinaison.has3JetonsGagnants()) {
					groupe3.add(coord);
				}
				// System.out.println("colonne : "+j+" et ligne : "+i+" /
				// "+combinaison.toString());
				// System.out.println(combinaison.getNbCombinaison());
				// System.out.println("colonne : "+j+" et ligne : "+i);
				// System.out.println(combinaison.toString());
			}
		}

		// Vérification alignement diagonaux (bas-droite vers haut-gauche)
		// Parcours des 3 lignes possibles
		for (int i = 0; i < Constantes.NB_LIGNES - Constantes.NB_JETONS_VICTOIRE + 1; i++) {
			// Parcours des 4 colonnes en sens inverse
			for (int j = Constantes.NB_COLONNES - 1; j >= Constantes.NB_COLONNES - Constantes.NB_JETONS_VICTOIRE; j--) {
				int[] coord = { i, j };
				Combinaison combinaison = new CombinaisonObliqueDroiteGauche(symboleJoueur, coord);
				// Combinaison combinaison = new CombinaisonVerticale(symboleJoueur, coord);
				// combinaison.addCoord(coord);
				for (int k = 0; k < Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i + k, j - k);
					combinaison.addSymbole(symbole);
				}
				combinaison
						.addBonus(grille.getCase(i + Constantes.NB_JETONS_VICTOIRE, j - Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				if (combinaison.has3JetonsGagnants()) {
					groupe3.add(coord);
				}
				// System.out.println("colonne : "+j+" et ligne : "+i+" /
				// "+combinaison.toString());
				// System.out.println(combinaison.getNbCombinaison());
				// System.out.println("colonne : "+j+" et ligne : "+i);
				// System.out.println(combinaison.toString());
			}
		}

		// Stratégie:
		for (int i = 0; i < Constantes.NB_LIGNES - 1; i++) {
			for (int j = 0; j < Constantes.NB_COLONNES; j++) {
				if ((grille.getCase(i, j) == Case.V) && (grille.getCase(i + 1, j) == Case.V)) {
					int[] coordVide1 = new int[2];
					coordVide1[0] = i;
					coordVide1[1] = j;
					int[] coordVide2 = new int[2];
					coordVide2[0] = i + 1;
					coordVide2[1] = j;
					if (groupe3.contains(coordVide1) && groupe3.contains(coordVide2)) {
						score += Constantes.SCORE_STRATEGIE;
					}
				}
			}
		}
		return score;
	}

}
