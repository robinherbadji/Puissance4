package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;


/**
	Classe implémentant l'algorithme Minimax
*/
public class Minimax extends Algorithm {
	private int tourExplore;
	

	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
	}

	@Override
	public int choisirCoup() {
		int coup = Constantes.COUP_NON_DEFINI;
		double utility_max = -10000;
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			
			Grille grille_next = grilleDepart.clone();
			if (grille_next.isCoupPossible(col)) {
				
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = this.tourDepart + 1;
				
			
				double eval_grille_next = min_value(grille_next);
				System.out.println("colonne "+(col+1)+" : "+eval_grille_next);
				if (eval_grille_next > utility_max) {
					utility_max = eval_grille_next;
					coup = col;
					//System.out.println("on a trouuvé mieux");
				}
			}			
		}
		
		return coup;
	}
	
	
	private double min_value(Grille state) {
		int etatPartie = state.getEtatPartie(symboleMax, tourExplore); // L'etat partie se fait tjrs sur symboleMax (pas sûr mais pour etatPartie ca cahnge pas grand chose)
		if (etatPartie != Constantes.PARTIE_EN_COURS || (this.tourExplore - this.tourDepart) >= this.levelIA) {
			//System.out.println("On est dans Min Value");
			//System.out.println(("profondeur : "+ (this.tourExplore - this.tourDepart)));
			return state.evaluer(symboleMax);
		}
		
		//double utility = Constantes.SCORE_MAX_NON_DEFINI; // tourne à l'infini si décommenté
		double utility = 100000; // tend vers +inf
		
		// Parcours des successeurs
		int tourRef = tourExplore;
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {
				
				grille_next.ajouterCoup(col, symboleMin);
				tourExplore = tourRef + 1;
				
				double eval_grille_next = max_value(grille_next);
				if (eval_grille_next < utility) {
					utility = eval_grille_next;
				}
			}
		}
		return utility;		
	}
	
	private double max_value(Grille state) {
		int etatPartie = state.getEtatPartie(symboleMax, tourExplore);
		if (etatPartie != Constantes.PARTIE_EN_COURS || (this.tourExplore - this.tourDepart) >= this.levelIA) {
			//System.out.println("On est dans Max Value");
			return state.evaluer(symboleMax);
		}
		
		//double utility = Constantes.SCORE_MIN_NON_DEFINI; // tourne à l'infini si décommenté
		double utility = -100000; // tend vers -inf
		
		// Parcours des successeurs
		int tourRef = tourExplore;
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {
				
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = tourRef + 1;
				
				double eval_grille_next = max_value(grille_next);
				if (eval_grille_next > utility) {
					utility = eval_grille_next;
				}
			}
		}
		return utility;		
	}
	

	
	
	

}
