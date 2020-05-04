package fr.uha.ensisa.puissance4.jeu.algosIA.alphabeta;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.jeu.algosIA.Algorithm;
import fr.uha.ensisa.puissance4.util.Constantes;

/**
 * Classe implémentant l'algorithme Alpha-Beta
 */
public class AlphaBeta extends Algorithm {

	public AlphaBeta(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
	}

	@Override
	public int choisirCoup() {
		// Stratégie de départ
		// int coup = Constantes.COUP_NON_DEFINI;
		int coup = coupDepartPartie();
		if (coup != Constantes.COUP_NON_DEFINI) {
			return coup;
		}

		// Blocage de l'adversaire s'il gagne au prochain coup
		int victoireAdversaire = grilleDepart.isAdversaireGagnant(symboleMin, tourDepart);
		if (victoireAdversaire != Constantes.COUP_NON_DEFINI) {
			return victoireAdversaire;
		}

		// Choisir cette colonne ferait directement gagner l'adversaire
		int colonneDefaite = Constantes.COUP_NON_DEFINI;
		double utility = Constantes.SCORE_MIN_NON_DEFINI;
		for (int col : this.classementColonnes()) {
			Grille grille_next = grilleDepart.clone();
			if (grille_next.isCoupPossible(col)) {
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = this.tourDepart + 1;

				// Si l'IA peut gagner en un coup : jouer ce coup
				if (grille_next.isIAGagnante(symboleMax, tourDepart)) {
					return col;
				}

				// Si l'adversaire peut gagner après que l'on a joué : éviter ce coup
				double eval_grille_next = Constantes.SCORE_MIN_NON_DEFINI;
				victoireAdversaire = grille_next.isAdversaireGagnant(symboleMin, tourDepart);
				if (victoireAdversaire != Constantes.COUP_NON_DEFINI) {
					colonneDefaite = col;
				} else {
					eval_grille_next = min_value(grille_next, Constantes.SCORE_MIN_NON_DEFINI,
							Constantes.SCORE_MAX_NON_DEFINI);
				}

				System.out.println("COLONNE " + (col + 1) + " : " + eval_grille_next);
				if (eval_grille_next > utility) {
					utility = eval_grille_next;
					coup = col;
				}
			}
		}

		// On a perdu il faut bien renvoyer quelque chose ...
		if (coup == Constantes.COUP_NON_DEFINI) {
			coup = colonneDefaite;
		}
		return coup;
	}

	private double min_value(Grille state, double alpha, double beta) {
		// On regarde si l'IA a gagné, ou match nul ou la profondeur max de l'IA est
		// atteinte
		int etatPartie = state.getEtatPartie(symboleMax, tourExplore);
		if ((etatPartie != Constantes.PARTIE_EN_COURS) || (this.tourExplore - this.tourDepart >= this.levelIA)) {
			return state.evaluer(symboleMax);
		}

		// Parcours des successeurs
		double utility = Constantes.SCORE_MAX_NON_DEFINI;
		int tourRef = tourExplore;
		for (int col : this.classementColonnes()) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {
				grille_next.ajouterCoup(col, symboleMin);
				tourExplore = tourRef + 1;
				utility = Math.min(utility, max_value(grille_next, alpha, beta));
				beta = Math.min(beta, utility);
				if (alpha >= beta) {
					break;
				}
			}
		}
		return utility;
	}

	private double max_value(Grille state, double alpha, double beta) {
		// On regarde si l'adversaire a gagné, ou match nul ou la profondeur max de l'IA
		// est atteinte
		int etatPartieAdverse = state.getEtatPartie(symboleMin, tourExplore);
		if ((etatPartieAdverse != Constantes.PARTIE_EN_COURS) || (this.tourExplore - this.tourDepart >= this.levelIA)) {
			return state.evaluer(symboleMax);
		}

		// Parcours des successeurs
		double utility = Constantes.SCORE_MIN_NON_DEFINI;
		int tourRef = tourExplore;
		for (int col : this.classementColonnes()) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {
				grille_next.ajouterCoup(col, symboleMax);
				tourExplore = tourRef + 1;
				utility = Math.max(utility, min_value(grille_next, alpha, beta));
				alpha = Math.max(alpha, utility);
				if (alpha >= beta) {
					break;
				}
			}
		}
		return utility;
	}

}
