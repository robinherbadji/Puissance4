package fr.uha.ensisa.puissance4.data.combinaisons;

import java.util.ArrayList;

import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class CombinaisonObliqueDroiteGauche extends Combinaison{

	public CombinaisonObliqueDroiteGauche(ArrayList<Case> combinaison, Case symbCourant) {
		super(Constantes.DIRECTION_OBLIQUE_DROITE_GAUCHE, combinaison, symbCourant);
	}
	
	public CombinaisonObliqueDroiteGauche(Case symbCourant) {
		super(Constantes.DIRECTION_OBLIQUE_DROITE_GAUCHE, symbCourant);
	}

	/*
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
			if (this.combinaison.get(0)==Case.V && bonus==Case.V) {
				score += Constantes.SCORE_BONUS;
			}
		}
		if (getNbCurrent() == 2 && getNbVides() == 2) {
			score = Constantes.SCORE_2;
		}
		if (getNbCurrent() == 1 && getNbVides() == 3) {
			score = Constantes.SCORE_1;
		}
		
		return score;
	}
	*/
	
	
	@Override
	public String getNomDirection() {
		return "Oblique Droite-Gauche";
	}

	
	
	/*
	public ArrayList<Combinaison> extractionCombinaison() {
		
		// Parcourt des 6 lignes
		for(int i=0; i < Constantes.NB_LIGNES; i++)
		{
			// Parcours des 4 colonnes possibles
			for(int j=0; j < Constantes.NB_COLONNES-Constantes.NB_JETONS_VICTOIRE+1; j++)
			{
				ArrayList<Case> combinaison = new ArrayList<Case>();
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					combinaison.add(getCase(i, j + k));
				}
				Case bonus = null;
				if (j + Constantes.NB_JETONS_VICTOIRE <= Constantes.NB_COLONNES) {
					bonus = getCase(i, j + Constantes.NB_JETONS_VICTOIRE);
				}
				
				score += this.getScore(combinaison, );
			}
			nbAlignes=0;
		}
	}
	
	public Case next() {
		
	}
	*/
}
