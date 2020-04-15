package fr.uha.ensisa.puissance4.data.evaluation.combinaisons;

import java.util.ArrayList;

import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class CombinaisonVerticale extends Combinaison{

	public CombinaisonVerticale(ArrayList<Case> combinaison, Case symbCourant) {
		super(Constantes.DIRECTION_VERTICALE, combinaison, symbCourant);
	}
	
	public CombinaisonVerticale(Case symbCourant) {
		super(Constantes.DIRECTION_VERTICALE, symbCourant);
	}
	
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
		//System.out.println("Vertical score : "+score);
		return score;
	}
	

	@Override
	public String getNomDirection() {
		return "Vertical";
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
