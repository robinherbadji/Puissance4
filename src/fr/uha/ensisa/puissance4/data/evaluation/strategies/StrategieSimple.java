package fr.uha.ensisa.puissance4.data.evaluation.strategies;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonHorizontale;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonObliqueDroiteGauche;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonObliqueGaucheDroite;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonVerticale;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * Stratégie simple pour le calcul des évaluations d'une
 * {@link fr.uha.ensisa.puissance4.data.Grille Grille}
 * <p>
 * La stratégie consiste à extraire toutes les
 * {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison
 * Combinaison} de la {@link fr.uha.ensisa.puissance4.data.Grille Grille}.
 * Ensuite il ne reste plus qu'à appeler la méthode
 * {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison#getScore()
 * getScore()} sur chacune des {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison
 * Combinaison} afin d'en déduire le score total pour le joueur courant<br/>
 * Ensuite
 * </p>
 * 
 * @author Robin
 * @author Yassine
 * @see Strategie
 * @see StrategieAmelioree
 */
public class StrategieSimple extends Strategie {

	public StrategieSimple(Grille grille, Case symboleJoueurCourant) {
		super(grille, symboleJoueurCourant);
	}

	@Override
	public double getUtility() {
		double attaque = Constantes.COEF_IA * evaluerJoueur(symboleJoueurCourant);
		double defense = Constantes.COEF_OTHER * evaluerJoueur(symboleJoueurAdverse);
		double utility = attaque - defense;
		return utility;
	}

	/*
	public double[] getUtility_tab() {
		double[] tab_utility = new double[3];
		tab_utility[0] = Constantes.COEF_IA * evaluerJoueur(symboleJoueurCourant);
		tab_utility[1] = Constantes.COEF_OTHER * evaluerJoueur(symboleJoueurAdverse);
		double utility = Constantes.COEF_IA * evaluerJoueur(symboleJoueurCourant)
				- Constantes.COEF_OTHER * evaluerJoueur(symboleJoueurAdverse);
		tab_utility[2] = utility;
		System.out.println("Attaque : " + tab_utility[0] + " / Défense : " + tab_utility[1]);
		return tab_utility;
	}
	*/

	/**
	 * Renvoie le score d'un joueur en additionnant le score de chaque
	 * {@link fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison
	 * Combinaison} composant la {@link fr.uha.ensisa.puissance4.data.Grille Grille}
	 * 
	 * @param symboleJoueur
	 * @return
	 */
	private double evaluerJoueur(Case symboleJoueur) {
		double score = 0;

		// Vérification alignement horizontaux
		// Parcourt des 6 lignes
		for (int i = 0; i < Constantes.NB_LIGNES; i++) {
			// Parcours de 4 possiblilités
			for (int j = 0; j < Constantes.NB_COLONNES - Constantes.NB_JETONS_VICTOIRE + 1; j++) {
				Combinaison combinaison = new CombinaisonHorizontale(symboleJoueur);
				for (int k = 0; k < Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i, j + k);
					combinaison.addSymbole(symbole);
					// System.out.println("ligne : "+j+" et colonne : "+(i+k));
				}
				combinaison.addBonus(grille.getCase(i, j + Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
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
				Combinaison combinaison = new CombinaisonVerticale(symboleJoueur);
				for (int k = 0; k < Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i + k, j);
					combinaison.addSymbole(symbole);
				}
				combinaison.addBonus(grille.getCase(i + Constantes.NB_JETONS_VICTOIRE, j));
				score += combinaison.getScore();
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
				Combinaison combinaison = new CombinaisonObliqueGaucheDroite(symboleJoueur);
				for (int k = 0; k < Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i + k, j + k);
					combinaison.addSymbole(symbole);
				}
				combinaison
						.addBonus(grille.getCase(i + Constantes.NB_JETONS_VICTOIRE, j + Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				// System.out.println("colonne : "+j+" et ligne : "+i+" /
				// "+combinaison.toString());
				// System.out.println(combinaison.getNbCombinaison());
				// System.out.println("colonne : "+j+" et ligne : "+i);
				// System.out.println(combinaison.toString());
			}
		}

		// Vérification alignement diagonaux (bas-droite vers haut-gauche)
		// Parcours des 3 lignes possibles
		for (int i = 0; i < Constantes.NB_LIGNES - Constantes.NB_JETONS_VICTOIRE + 1; i++)
			// Parcours des 4 colonnes en sens inverse
			for (int j = Constantes.NB_COLONNES - 1; j >= Constantes.NB_COLONNES - Constantes.NB_JETONS_VICTOIRE; j--) {
				Combinaison combinaison = new CombinaisonObliqueDroiteGauche(symboleJoueur);
				for (int k = 0; k < Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i + k, j - k);
					combinaison.addSymbole(symbole);
				}
				combinaison
						.addBonus(grille.getCase(i + Constantes.NB_JETONS_VICTOIRE, j - Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				// System.out.println("colonne : "+j+" et ligne : "+i+" /
				// "+combinaison.toString());
				// System.out.println(combinaison.getNbCombinaison());
				// System.out.println("colonne : "+j+" et ligne : "+i);
				// System.out.println(combinaison.toString());
			}
		return score;
	}

}
