package fr.uha.ensisa.puissance4.data.evaluation.combinaisons;

import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * {@link Combinaison} de 4 {@link Case} de direction oblique de bas en haut de
 * gauche à droite: / <br>
 * <br>
 * <i>Note : Il y a 12 {@link CombinaisonObliqueGaucheDroite} différentes dans
 * une {@link fr.uha.ensisa.puissance4.data.Grille Grille}</i>
 * 
 * @author Robin
 * @author Yassine
 * @see Combinaison
 * @see CombinaisonHorizontale
 * @see CombinaisonVerticale
 * @see CombinaisonObliqueDroiteGauche
 * @see fr.uha.ensisa.puissance4.data.Grille Grille
 */
public class CombinaisonObliqueGaucheDroite extends Combinaison {

	public CombinaisonObliqueGaucheDroite(Case symbCourant) {
		super(Constantes.DIRECTION_OBLIQUE_GAUCHE_DROITE, symbCourant);
	}

	public CombinaisonObliqueGaucheDroite(Case symbCourant, int[] coord) {
		super(Constantes.DIRECTION_OBLIQUE_GAUCHE_DROITE, symbCourant, coord);
	}

	@Override
	public double getScore() {
		double score = 0;
		// Combinaison parfaite
		if (getNbCurrent() == 4) {
			score = Constantes.SCORE_4;
		}
		// Combinaison possible
		if (getNbCurrent() == 3 && getNbVides() == 1) {
			score = Constantes.SCORE_3;
			// Bonus si les 3 jetons sont entre 2 cases vides
			if (this.combinaison.get(0) == Case.V && bonus == Case.V) {
				score += Constantes.SCORE_BONUS;
			}
		}
		if (getNbCurrent() == 2 && getNbVides() == 2) {
			score = Constantes.SCORE_2;
		}
		if (getNbCurrent() == 1 && getNbVides() == 3) {
			score = Constantes.SCORE_1;
		}
		// System.out.println("Oblique Gauche Droite score : "+score);
		return score;
	}

	@Override
	public String getNomDirection() {
		return "Oblique Gauche-Droite";
	}

	@Override
	public int[] getCoordVideDans3() {
		int[] coord = new int[2];
		coord[0] = coordonnees[0] + this.getIndexVideDans3(); // Ligne
		coord[1] = coordonnees[1] + this.getIndexVideDans3(); // Colonne
		return coord;
	}

}
