package fr.uha.ensisa.puissance4.data.evaluation.combinaisons;

import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * {@link Combinaison} de 4 {@link Case} de direction horizontale de gauche à droite
 * : _ <br>
 * <br>
 * <i>Note : Il y a 24 {@link CombinaisonHorizontale} différentes dans une
 * {@link fr.uha.ensisa.puissance4.data.Grille Grille}</i>
 * 
 * @author Robin
 * @author Yassine
 * @see Combinaison
 * @see CombinaisonVerticale
 * @see CombinaisonObliqueGaucheDroite
 * @see CombinaisonObliqueDroiteGauche
 * @see fr.uha.ensisa.puissance4.data.Grille Grille
 */
public class CombinaisonHorizontale extends Combinaison {

	public CombinaisonHorizontale(Case symbCourant) {
		super(Constantes.DIRECTION_HORIZONTALE, symbCourant);
	}

	public CombinaisonHorizontale(Case symbCourant, int[] coord) {
		super(Constantes.DIRECTION_HORIZONTALE, symbCourant, coord);
	}

	@Override
	public String getNomDirection() {
		return "Horizontal";
	}

	@Override
	public int[] getCoordVideDans3() {
		int[] coord = new int[2];
		coord[0] = coordonnees[0]; // Ligne
		coord[1] = coordonnees[1] + this.getIndexVideDans3(); // Colonne
		return coord;
	}

	@Override
	public double getScore() {
		double score = 0;
		// Combinaison parfaite
		if (getNbCurrent() == 4) {
			score = Constantes.SCORE_4;
		}
		// Combinaisons possibles
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
		// System.out.println("Horizontal score : "+score);
		return score;
	}

}
