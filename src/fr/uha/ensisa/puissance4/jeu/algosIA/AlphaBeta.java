package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;

/**
	Classe implémentant l'algorithme Alpha-Beta
*/
public class AlphaBeta extends Algorithm {
	
	private int noeudsExplores = 0;
	
	public AlphaBeta(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);

	}
	
	
	@Override
	public int choisirCoup() {
		/*
		if (tourDepart <= 2) {
			return 3;
		}
		*/
		int coup = Constantes.COUP_NON_DEFINI;
		//coup = 3;
		/*
		double utility = Constantes.SCORE_MIN_NON_DEFINI;
		double beta = Constantes.SCORE_MAX_NON_DEFINI;
		*/
		
		double utility = -100000;
		double beta = 100000;
		
		for (int col : this.classementColonnes()) {
		//for (int col = 0; col < Constantes.NB_COLONNES; col++) {			
			Grille grille_next = grilleDepart.clone();
			if (grille_next.isCoupPossible(col)) {
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = this.tourDepart + 1;
							
				double eval_grille_next = min_value(grille_next, utility, beta);
				System.out.println("COLONNE "+(col+1)+" : "+eval_grille_next);
				//System.out.println(utility);
				if (eval_grille_next > utility) {
					utility = eval_grille_next;
					coup = col;
				}
			}
		}
		return coup;
	}
	
	
	
	private double min_value(Grille state, double alpha, double beta) {
		int etatPartie = state.getEtatPartie(symboleMax, tourExplore);
		int etatPartieAdverse = state.getEtatPartie(symboleMin, tourExplore);
		if ((etatPartie != Constantes.PARTIE_EN_COURS) || (etatPartieAdverse != Constantes.PARTIE_EN_COURS) || 
				(this.tourExplore - this.tourDepart >= this.levelIA)) {
			//System.out.println("MIN : JE SUIS PASSé PAR Là : "+(this.tourExplore - this.tourDepart));
			return state.evaluer(symboleMax);			
		}
		
		
		/*
		// Victoire du joueur courant
		if (etatPartie == Constantes.VICTOIRE_JOUEUR_1 || etatPartie == Constantes.VICTOIRE_JOUEUR_2) {
			//System.out.println("MIN: VICTOIRE !");
			return -(Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore)/2;
			//return state.evaluer(symboleMin);
			//return 10000;
		}
		*/		
		
		//double utility = Constantes.SCORE_MAX_NON_DEFINI; // tourne à l'infini si décommenté
		double utility = 100000; // tend vers +inf
		
		// Parcours des successeurs
		int tourRef = tourExplore;
		for (int col : this.classementColonnes()) {
		//for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {
				grille_next.ajouterCoup(col, symboleMin);
				tourExplore = tourRef + 1;
				double eval_grille_next = max_value(grille_next, alpha, beta);
				System.out.println("colonne min "+(col+1)+" : "+eval_grille_next);
				utility = Math.min(utility, max_value(grille_next, alpha, beta));
				beta = Math.min(beta, utility);
				if (alpha >= beta) {
					System.out.println("Coupure : " + utility + " alpha : " + alpha);
					return utility;
				}
				
			}
		}
		return utility;
	}
	
	private double max_value(Grille state, double alpha, double beta) {
		int etatPartie = state.getEtatPartie(symboleMax, tourExplore);
		int etatPartieAdverse = state.getEtatPartie(symboleMin, tourExplore);
		if ((etatPartie != Constantes.PARTIE_EN_COURS) || (etatPartieAdverse != Constantes.PARTIE_EN_COURS) || 
				(this.tourExplore - this.tourDepart >= this.levelIA)) {
			//System.out.println("MAX : JE SUIS PASSé PAR Là : "+(this.tourExplore - this.tourDepart));
			return state.evaluer(symboleMax);
		}
		
		
		/*
		// Victoire du joueur courant
		if (etatPartie == Constantes.VICTOIRE_JOUEUR_1 || etatPartie == Constantes.VICTOIRE_JOUEUR_2) {
			//System.out.println("MIN: VICTOIRE !");
			return (Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore)/2;
			//return 10000;
			//return state.evaluer(symboleMax);
		}
		*/
		
			
		//double utility = Constantes.SCORE_MIN_NON_DEFINI; // tourne à l'infini si décommenté
		double utility = -100000; // tend vers -inf
		
		// Parcours des successeurs
		int tourRef = tourExplore;
		for (int col : this.classementColonnes()) {
		//for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {				
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = tourRef + 1;				
				utility = Math.max(utility, min_value(grille_next, alpha, beta));
				alpha = Math.max(alpha, utility);
				if (alpha >= beta) {
					return utility;
				}
				
			}
		}
		return utility;		
	}
	
	
}
