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
		noeudsExplores = 0;
		int coup = Constantes.COUP_NON_DEFINI;
		//double utility = -Constantes.NB_COLONNES*Constantes.NB_LIGNES;
		double utility = -100000;
		double alpha = -100000;
		double beta = 100000;
		for (int col : this.classementColonnes()) {
		//for (int col = 0; col < Constantes.NB_COLONNES; col++) {			
			Grille grille_next = grilleDepart.clone();
			noeudsExplores ++;
			if (grille_next.isCoupPossible(col)) {				
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = this.tourDepart + 1;
				
				double eval_grille_next = min(grille_next, alpha, beta);
				System.out.println("colonne "+(col+1)+" : "+eval_grille_next);
				if (eval_grille_next > utility) {
					utility = eval_grille_next;
					coup = col;
				}
			}			
		}
		
		return coup;
	}
	
	
	private double min(Grille state, double alpha, double beta) {
		if (this.tourDepart == Constantes.NB_COLONNES * Constantes.NB_LIGNES) {
			return 0;
		}
		noeudsExplores ++;
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille victoire_j2 = grilleDepart.clone();
			if (grilleDepart.isCoupPossible(col)) {
				victoire_j2.ajouterCoup(col, symboleMin);
				int etatPartie = victoire_j2.getEtatPartie(symboleMin, tourExplore+1);
				if (etatPartie == Constantes.VICTOIRE_JOUEUR_1 || etatPartie == Constantes.VICTOIRE_JOUEUR_2) {
					//System.out.println(Constantes.VICTOIRE_JOUEUR_2);
					return (Constantes.NB_COLONNES * Constantes.NB_LIGNES +1 - tourExplore)/2;
				}
			}
		}
		
		if (this.tourExplore - this.tourDepart >= this.levelIA) {
			return 0;
		}
		
		double max = (Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore -1)/2;
		if (beta > max) {
			beta = max;
			if (alpha >= beta) {
				return beta;
			}
		}
		
		int tourRef = tourExplore;
		for (int col : this.classementColonnes()) {
			Grille grille_next = state.clone();
			
			//System.out.println("Noeuds :"+noeudsExplores);
			if (grille_next.isCoupPossible(col)) {				
				grille_next.ajouterCoup(col, symboleMin);
				tourExplore = tourRef + 1;
				double score = -max(grille_next, -alpha, -beta);
				
				if(score >= beta) return score;
			    if(score > alpha) alpha = score;
			}
		}		
		return alpha;
	}
	
	
	private double max(Grille state, double alpha, double beta) {
		if (this.tourDepart == Constantes.NB_COLONNES * Constantes.NB_LIGNES) {
			return 0;
		}
		noeudsExplores ++;
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille victoire_j1 = grilleDepart.clone();
			if (grilleDepart.isCoupPossible(col)) {
				victoire_j1.ajouterCoup(col, symboleMax);
				int etatPartie = victoire_j1.getEtatPartie(symboleMax, tourExplore+1);
				if (etatPartie == Constantes.VICTOIRE_JOUEUR_1 || etatPartie == Constantes.VICTOIRE_JOUEUR_2) {
					//System.out.println(Constantes.VICTOIRE_JOUEUR_1);
					return (Constantes.NB_COLONNES * Constantes.NB_LIGNES +1 - tourExplore)/2;
				}
			}
		}
		
		if (this.tourExplore - this.tourDepart >= this.levelIA) {
			return 0;
		}
		
		double max = (Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore -1)/2;
		if (beta > max) {
			beta = max;
			if (alpha >= beta) {
				return beta;
			}
		}
		
		int tourRef = tourExplore;
		for (int col : this.classementColonnes()) {
			Grille grille_next = state.clone();
			
			if (grille_next.isCoupPossible(col)) {				
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = tourRef + 1;
				double score = -min(grille_next, -alpha, -beta);
				
				if(score >= beta) return score;
			    if(score > alpha) alpha = score;
			}
		}		
		return alpha;
	}
	
	/*
	private double negamax(Grille state, double alpha, double beta) {
		if (this.tourDepart == Constantes.NB_COLONNES * Constantes.NB_LIGNES) {
			return 0;
		}
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille victoire_j1 = grilleDepart.clone();
			Grille victoire_j2 = grilleDepart.clone();
			if (grilleDepart.isCoupPossible(col)) {
				victoire_j1.ajouterCoup(col, symboleMax);
				if (victoire_j1.getEtatPartie(symboleMax, tourExplore+1) == Constantes.VICTOIRE_JOUEUR_1) {
					return (Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore)/2;
				}
				victoire_j2.ajouterCoup(col, symboleMin);
				if (victoire_j1.getEtatPartie(symboleMin, tourExplore+1) == Constantes.VICTOIRE_JOUEUR_2) {
					return (Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore)/2;
				}
			}
		}
		
		double max = (Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore -1)/2;
		if (beta > max) {
			beta = max;
			if (alpha >= beta) {
				return beta;
			}
		}
		
		int tourRef = tourExplore;
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {				
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = tourRef + 1;
				double score = -negamax(grille_next, -alpha, -beta);
				
				if(score >= beta) return score;
			    if(score > alpha) alpha = score;
			}
		}
		
		return alpha;
	}
	*/
	

/*
	
	@Override
	public int choisirCoup() {
		if (tourDepart <= 2) {
			return 3;
		}
		int coup = Constantes.COUP_NON_DEFINI;
		//double utility = -Constantes.NB_COLONNES*Constantes.NB_LIGNES;
		double utility = -100000;
		for (int col = 0; col < Constantes.NB_COLONNES; col++) {			
			Grille grille_next = grilleDepart.clone();
			if (grille_next.isCoupPossible(col)) {
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = this.tourDepart + 1;
				double beta = 100000;			
				double eval_grille_next = min_value(grille_next, utility, beta);
				System.out.println("colonne "+(col+1)+" : "+eval_grille_next);
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
		if (etatPartie != Constantes.PARTIE_EN_COURS) {
			//System.out.println("MIN : JE SUIS PASSé PAR Là : "+(this.tourExplore - this.tourDepart));
			
		}
		if (etatPartie == Constantes.VICTOIRE_JOUEUR_1 || etatPartie == Constantes.VICTOIRE_JOUEUR_2) {
			//System.out.println("MIN: VICTOIRE !");
			return -(Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore)/2;
			//return state.evaluer(symboleMin);
			//return 10000;
		}
		
		if (this.tourExplore - this.tourDepart >= this.levelIA) {
			return state.evaluer(symboleMin, tourExplore);
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
				utility = Math.min(utility, max_value(grille_next, alpha, beta));
				beta = Math.min(beta, utility);
				if (alpha >= beta) {
					return utility;
				}
				
			}
		}
		return utility;
	}
	
	private double max_value(Grille state, double alpha, double beta) {
		int etatPartie = state.getEtatPartie(symboleMax, tourExplore);
		if (etatPartie != Constantes.PARTIE_EN_COURS) {
			//System.out.println("MAX : JE SUIS PASSé PAR Là : "+(this.tourExplore - this.tourDepart));			
		}
		
		if (etatPartie == Constantes.VICTOIRE_JOUEUR_1 || etatPartie == Constantes.VICTOIRE_JOUEUR_2) {
			//System.out.println("MIN: VICTOIRE !");
			return (Constantes.NB_COLONNES * Constantes.NB_LIGNES - tourExplore)/2;
			//return 10000;
			//return state.evaluer(symboleMax);
		}
		
		
		if (this.tourExplore - this.tourDepart >= this.levelIA) {
			//System.out.println("On est dans Max Value");
			//if ((this.tourExplore - this.tourDepart) != this.levelIA) System.out.println(etatPartie);
			return state.evaluer(symboleMax, tourExplore);
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
				utility = Math.max(utility, min_value(grille_next, alpha, beta));
				alpha = Math.max(alpha, utility);
				if (alpha >= beta) {
					return utility;
				}
				
			}
		}
		return utility;		
	}
	*/
	
}
