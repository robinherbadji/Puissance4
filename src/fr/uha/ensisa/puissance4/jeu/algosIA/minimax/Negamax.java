package fr.uha.ensisa.puissance4.jeu.algosIA.minimax;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.jeu.algosIA.Algorithm;
import fr.uha.ensisa.puissance4.util.Constantes;

public class Negamax extends Algorithm {

	public Negamax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
	}

	@Override
	public int choisirCoup() {
		// Stratégie de départ
		int coup = Constantes.COUP_NON_DEFINI;

		// Blocage de l'adversaire s'il gagne au prochain coup
		int victoireAdversaire = grilleDepart.isAdversaireGagnant(symboleMin, tourDepart);
		if (victoireAdversaire != Constantes.COUP_NON_DEFINI) {
			System.out.println("Blocage Victoire Adverse");
			return victoireAdversaire;
		}

		// Choisir cette colonne ferait directement gagner l'adversaire
		int colonneDefaite = Constantes.COUP_NON_DEFINI;

		double utility_max = Constantes.SCORE_MIN_NON_DEFINI;
		for (int col : this.classementColonnes()) {
			Grille grille_next = grilleDepart.clone();
			if (grille_next.isCoupPossible(col)) {
				grille_next.ajouterCoup(col, symboleMax);
				// tourExplore = this.tourDepart + 1;

				// Si l'IA peut gagner en un coup : jouer ce coup
				if (grille_next.isIAGagnante(symboleMax, tourDepart)) {
					System.out.println("C'est gagné");
					return col;
				}

				// Si l'adversaire peut gagner après que l'on a joué : éviter ce coup
				double eval_grille_next = Constantes.SCORE_MIN_NON_DEFINI;
				victoireAdversaire = grille_next.isAdversaireGagnant(symboleMin, tourDepart);
				if (victoireAdversaire != Constantes.COUP_NON_DEFINI) {
					System.out.println("IA perd si elle met dans la colonne suivante : ");
					colonneDefaite = col;
				} else {
					eval_grille_next = -negamax(grille_next, this.tourDepart + 1);
					// eval_grille_next = -min_value(grille_next, this.tourDepart + 1);
				}

				// double eval_grille_next = min_value(grille_next);
				System.out.println("COLONNE " + (col + 1) + " : " + eval_grille_next);
				if (eval_grille_next > utility_max) {
					utility_max = eval_grille_next;
					coup = col;
				}
			}
		}
		// On a perdu il faut bien renvoyer quelque chose ...
		if (coup == Constantes.COUP_NON_DEFINI) {
			System.out.println("Bon ben là plus aucune chance");
			coup = colonneDefaite;
		}
		return coup;
	}
	
	
	private double negamax(Grille state, int profondeur) {
		int etatPartie = state.getEtatPartie(symboleMax, profondeur);
		int etatPartieAdverse = state.getEtatPartie(symboleMin, profondeur);
		if ((etatPartie != Constantes.PARTIE_EN_COURS) || (etatPartieAdverse != Constantes.PARTIE_EN_COURS) || (profondeur - this.tourDepart >= this.levelIA)) {
			if ((profondeur - tourDepart) % 2 == 1) {
				return -state.evaluer(symboleMax);
			}
			else {
				return state.evaluer(symboleMax);
			}
		}
		
		double utility = Constantes.SCORE_MIN_NON_DEFINI;
		int tourRef = profondeur;
		for (int col : this.classementColonnes()) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {
				if ((profondeur - tourDepart) % 2 == 1) {
					grille_next.ajouterCoup(col, symboleMin);
				}
				else {
					grille_next.ajouterCoup(col, symboleMax);
				}
				utility = Math.max(utility, -negamax(grille_next, tourRef+1));
			}
		}
		return utility;		
	}
	


}
