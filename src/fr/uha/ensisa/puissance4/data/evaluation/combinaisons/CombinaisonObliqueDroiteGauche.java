package fr.uha.ensisa.puissance4.data.evaluation.combinaisons;

import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * {@link Combinaison} de 4 {@link Case} de direction oblique de bas en haut de
 * droite à gauche : \ <br/>
 * <br/>
 * <i>Note : Il y a 12 {@link CombinaisonObliqueDroiteGauche} différentes dans
 * une {@link fr.uha.ensisa.puissance4.data.Grille Grille}</i>
 * 
 * @author Robin
 * @author Yassine
 * @see Combinaison
 * @see CombinaisonHorizontale
 * @see CombinaisonVerticale
 * @see CombinaisonObliqueGaucheDroite
 * @see fr.uha.ensisa.puissance4.data.Grille Grille
 */
public class CombinaisonObliqueDroiteGauche extends Combinaison {

	public CombinaisonObliqueDroiteGauche(Case symbCourant) {
		super(Constantes.DIRECTION_OBLIQUE_DROITE_GAUCHE, symbCourant);
	}

	public CombinaisonObliqueDroiteGauche(Case symbCourant, int[] coord) {
		super(Constantes.DIRECTION_OBLIQUE_DROITE_GAUCHE, symbCourant, coord);
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
		// System.out.println("Oblique Droite Gauche score : "+score);
		return score;
	}

	@Override
	public String getNomDirection() {
		return "Oblique Droite-Gauche";
	}

	@Override
	public int[] getCoordVideDans3() {
		int[] coord = new int[2];
		coord[0] = coordonnees[0] + this.getIndexVideDans3(); // Ligne
		coord[1] = coordonnees[1] - this.getIndexVideDans3(); // Colonne
		return coord;
	}

}
