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

public class StrategieOddEven extends Strategie {

	public StrategieOddEven(Grille grille, Case symboleJoueurCourant) {
		super(grille, symboleJoueurCourant);
	}

	@Override
	public double getUtility() {
		double attaque = Constantes.COEF_IA * evaluerJoueur(symboleJoueurCourant);
		double defense = Constantes.COEF_OTHER * evaluerJoueur(symboleJoueurAdverse);
		double utility = attaque - defense;
		return utility;
	}
	
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
		//for (int i = 0; i < Constantes.NB_LIGNES - 1; i++) {
		// Le joueur 1 doit sécuriser la victoire sur les lignes paires (0,2,4)
		if (symboleJoueur == Case.X) {
			for (int i = 0; i < Constantes.NB_LIGNES - 1; i+=2) {
				for (int j = 0; j < Constantes.NB_COLONNES; j++) {
					if (grille.getCase(i, j) == Case.V) {
						int[] coordVide = new int[2]; // Ne pas confondre coordVide et Covid, cela peut prêter à confusion ces temps-ci ^^
						coordVide[0] = i;
						coordVide[1] = j;
						if (groupe3.contains(coordVide)) {
							score += Constantes.SCORE_ODD_EVEN;
						}
					}
				}
			}
		}
		// Le joueur 2 doit sécuriser la victoire sur les lignes impaires (1,3,5)
		else {
			for (int i = 1; i < Constantes.NB_LIGNES - 1; i+=2) {
				for (int j = 0; j < Constantes.NB_COLONNES; j++) {
					if (grille.getCase(i, j) == Case.V) {
						int[] coordVide = new int[2];
						coordVide[0] = i;
						coordVide[1] = j;
						if (groupe3.contains(coordVide)) {
							score += Constantes.SCORE_ODD_EVEN;
						}
					}
				}
			}
		}
		return score;
	}
}
