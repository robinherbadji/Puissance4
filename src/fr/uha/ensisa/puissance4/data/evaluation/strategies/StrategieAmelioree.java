package fr.uha.ensisa.puissance4.data.evaluation.strategies;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.evaluation.Strategie;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.Combinaison;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonHorizontale;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonObliqueDroiteGauche;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonObliqueGaucheDroite;
import fr.uha.ensisa.puissance4.data.evaluation.combinaisons.CombinaisonVerticale;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class StrategieAmelioree extends Strategie {

	public StrategieAmelioree(Grille grille, Case symboleJoueurCourant, int tourDepart, int tourExplore) {
		super(grille, symboleJoueurCourant, tourDepart, tourExplore);
	}

	public double getUtility() {
		double attaque = Constantes.COEF_IA * evaluerJoueur(symboleJoueurCourant);
		double defense = Constantes.COEF_OTHER * evaluerJoueur(symboleJoueurAdverse);
		double utility = attaque - defense;
		//System.out.println("Attaque : "+attaque+" / Défense : "+defense);
		return utility;
	}
	
	
	private double evaluerJoueur(Case symboleJoueur) {
		double score = 0;
		
		//Vérification alignement horizontaux
		// Parcourt des 6 lignes
		for(int i=0; i < Constantes.NB_LIGNES; i++)
		{
			// Parcours de 4 possiblilités
			for(int j=0; j < Constantes.NB_COLONNES-Constantes.NB_JETONS_VICTOIRE+1; j++)
			{
				Combinaison combinaison = new CombinaisonHorizontale(symboleJoueur);
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i, j+k);
					combinaison.addSymbole(symbole);
					//System.out.println("ligne : "+j+" et colonne : "+(i+k));
				}
				combinaison.addBonus(grille.getCase(i, j+Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				//System.out.println(combinaison.getNbCombinaison());
				//System.out.println("colonne : "+j+" et ligne : "+i+" / "+combinaison.toString());
				//System.out.println(combinaison.toString());
			}
		}
		
		//Vérification alignement verticaux
		//Parcours des 3 lignes
		for(int i=0;i<Constantes.NB_LIGNES-Constantes.NB_JETONS_VICTOIRE+1;i++) {
			
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				Combinaison combinaison = new CombinaisonVerticale(symboleJoueur);
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i+k, j);
					combinaison.addSymbole(symbole);
				}
				combinaison.addBonus(grille.getCase(i+Constantes.NB_JETONS_VICTOIRE, j));
				score += combinaison.getScore();
				//System.out.println(combinaison.getNbCombinaison());
				//System.out.println("colonne : "+j+" et ligne : "+i);
				//System.out.println(combinaison.toString());
			}
		}
		
		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		//Parcours des 3 lignes
		for(int i=0;i<Constantes.NB_LIGNES-Constantes.NB_JETONS_VICTOIRE+1;i++) {
			// Parcours des 4 colonnes
			for(int j=0; j < Constantes.NB_COLONNES-Constantes.NB_JETONS_VICTOIRE+1; j++) {				
				Combinaison combinaison = new CombinaisonObliqueGaucheDroite(symboleJoueur);
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i+k, j+k);
					combinaison.addSymbole(symbole);
				}
				combinaison.addBonus(grille.getCase(i+Constantes.NB_JETONS_VICTOIRE, j+Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				//System.out.println("colonne : "+j+" et ligne : "+i+" / "+combinaison.toString());
				//System.out.println(combinaison.getNbCombinaison());
				//System.out.println("colonne : "+j+" et ligne : "+i);
				//System.out.println(combinaison.toString());
			}
		}
		

		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		// Parcours des 3 lignes possibles
		for(int i=0;i<Constantes.NB_LIGNES-Constantes.NB_JETONS_VICTOIRE+1;i++)
			// Parcours des 4 colonnes en sens inverse
			for(int j=Constantes.NB_COLONNES-1;j>=Constantes.NB_COLONNES-Constantes.NB_JETONS_VICTOIRE;j--)
			{
				Combinaison combinaison = new CombinaisonObliqueDroiteGauche(symboleJoueur);
				for (int k=0; k<Constantes.NB_JETONS_VICTOIRE; k++) {
					Case symbole = grille.getCase(i+k, j-k);
					combinaison.addSymbole(symbole);
				}
				combinaison.addBonus(grille.getCase(i+Constantes.NB_JETONS_VICTOIRE, j-Constantes.NB_JETONS_VICTOIRE));
				score += combinaison.getScore();
				//System.out.println("colonne : "+j+" et ligne : "+i+" / "+combinaison.toString());
				//System.out.println(combinaison.getNbCombinaison());
				//System.out.println("colonne : "+j+" et ligne : "+i);
				//System.out.println(combinaison.toString());
			}		
		return score; 
	}
	
}
