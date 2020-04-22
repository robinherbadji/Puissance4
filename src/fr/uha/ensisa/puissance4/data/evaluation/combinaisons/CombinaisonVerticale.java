package fr.uha.ensisa.puissance4.data.evaluation.combinaisons;

import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * {@link Combinaison} de 4 {@link Case} de direction verticale de bas en haut : |
 * <br>
 * <br>
 * <i>Note : Il y a 21 {@link CombinaisonVerticale} différentes dans une
 * {@link fr.uha.ensisa.puissance4.data.Grille Grille}</i>
 * 
 * @author Robin
 * @author Yassine
 * @see Combinaison
 * @see CombinaisonHorizontale
 * @see CombinaisonObliqueGaucheDroite
 * @see CombinaisonObliqueDroiteGauche
 * @see fr.uha.ensisa.puissance4.data.Grille Grille
 */
public class CombinaisonVerticale extends Combinaison {

	public CombinaisonVerticale(Case symbCourant) {
		super(Constantes.DIRECTION_VERTICALE, symbCourant);
	}

	public CombinaisonVerticale(Case symbCourant, int[] coord) {
		super(Constantes.DIRECTION_VERTICALE, symbCourant, coord);
	}

	@Override
	public double getScore() {
		double score = 0;
		// Combinaison parfaite
		if (getNbCurrent() == 4) {
			score = Constantes.SCORE_4;
		}
		// Combinaisons possibles
		if (getNbCurrent() == 3 && combinaison.get(3) == Case.V) {
			score = Constantes.SCORE_3;
		}
		if (getNbCurrent() == 2 && combinaison.get(2) == Case.V) {
			score = Constantes.SCORE_2;
		}
		if (combinaison.get(0) == symbCourant && combinaison.get(1) == Case.V) {
			score = Constantes.SCORE_1;
		}
		// System.out.println("Vertical score : "+score);
		return score;
	}

	@Override
	public String getNomDirection() {
		return "Vertical";
	}

	@Override
	public int[] getCoordVideDans3() {
		int[] coord = new int[2];
		coord[0] = coordonnees[0] + this.getIndexVideDans3(); // Ligne
		coord[1] = coordonnees[1]; // Colonne
		return coord;
	}

}
