package fr.uha.ensisa.puissance4.jeu.algosIA.alphabeta;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.jeu.algosIA.Algorithm;
import fr.uha.ensisa.puissance4.util.Constantes;

public class NegamaxAlphaThread extends Algorithm {
	private int nbNoeuds;

	public NegamaxAlphaThread(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
		this.nbNoeuds = 0;
	}

	@SuppressWarnings("serial")
	public class NegamaxAlphaRecursive extends RecursiveTask<Double> {
		private int limite_threads = 10;

		private List<NegamaxAlphaRecursive> listNega = new ArrayList<NegamaxAlphaRecursive>();
		private Grille grille;
		private int profondeur;
		private double score;
		private double alpha;
		private double beta;

		public NegamaxAlphaRecursive(Grille grille, double alpha, double beta, int profondeur, int nbNoeuds) {
			NegamaxAlphaThread.this.nbNoeuds = 0;
			this.grille = grille;
			this.alpha = alpha;
			this.beta = beta;
			this.profondeur = profondeur;
			this.score = Constantes.SCORE_MIN_NON_DEFINI;
		}

		public NegamaxAlphaRecursive(Grille grille, double alpha, double beta, int profondeur) {
			NegamaxAlphaThread.this.nbNoeuds++;
			this.grille = grille;
			this.alpha = alpha;
			this.beta = beta;
			this.profondeur = profondeur;
			this.score = Constantes.SCORE_MIN_NON_DEFINI;
		}

		@Override
		protected Double compute() {
			// Si on dépasse un seuil de Threads, on passe en mono Thread
			if (NegamaxAlphaThread.this.nbNoeuds >= this.limite_threads) {
				this.score = -alpha_negamax_mono_thread(this.grille, alpha, beta, profondeur);
			} else {
				this.score = -alpha_negamax_multi_thread(this.grille, alpha, beta, profondeur);
			}

			return score;
		}

		private double alpha_negamax_mono_thread(Grille state, double alpha, double beta, int profondeur) {
			int etatPartie = state.getEtatPartie(symboleMax, profondeur);
			int etatPartieAdverse = state.getEtatPartie(symboleMin, profondeur);
			if ((etatPartie != Constantes.PARTIE_EN_COURS) || (etatPartieAdverse != Constantes.PARTIE_EN_COURS)
					|| (profondeur - tourDepart >= levelIA)) {
				if ((profondeur - tourDepart) % 2 == 1) {
					return -state.evaluer(symboleMax);
				} else {
					return state.evaluer(symboleMax);
				}
			}

			double utility = Constantes.SCORE_MIN_NON_DEFINI;
			int tourRef = profondeur;
			for (int col : classementColonnes()) {
				Grille grille_next = state.clone();
				if (grille_next.isCoupPossible(col)) {
					if ((profondeur - tourDepart) % 2 == 1) {
						grille_next.ajouterCoup(col, symboleMin);
					} else {
						grille_next.ajouterCoup(col, symboleMax);
					}
					utility = Math.max(utility, -alpha_negamax_mono_thread(grille_next, -beta, -alpha, tourRef + 1));
					alpha = Math.max(alpha, utility);
					if (alpha >= beta) {
						break;
					}
				}
			}
			return utility;
		}

		private double alpha_negamax_multi_thread(Grille state, double alpha, double beta, int profondeur) {
			int etatPartie = state.getEtatPartie(symboleMax, profondeur);
			int etatPartieAdverse = state.getEtatPartie(symboleMin, profondeur);
			if ((etatPartie != Constantes.PARTIE_EN_COURS) || (etatPartieAdverse != Constantes.PARTIE_EN_COURS)
					|| (profondeur - tourDepart >= levelIA)) {
				if ((profondeur - tourDepart) % 2 == 1) {
					return -state.evaluer(symboleMax);
				} else {
					return state.evaluer(symboleMax);
				}
			}

			int tourRef = profondeur;
			for (int col : classementColonnes()) {
				Grille grille_next = state.clone();
				if (grille_next.isCoupPossible(col)) {
					if ((profondeur - tourDepart) % 2 == 1) {
						grille_next.ajouterCoup(col, symboleMin);
					} else {
						grille_next.ajouterCoup(col, symboleMax);
					}

					NegamaxAlphaRecursive nega = new NegamaxAlphaRecursive(grille_next, -beta, -alpha, tourRef + 1);
					listNega.add(nega);
					nega.fork();
				}
			}
			double utility = Constantes.SCORE_MIN_NON_DEFINI;
			for (NegamaxAlphaRecursive nega : listNega) {
				utility = Math.max(utility, nega.join());
			}
			return utility;
		}

		public double getScore() {
			return this.score;
		}

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
			System.out.println("Blocage Victoire Adverse");
			return victoireAdversaire;
		}

		// Choisir cette colonne ferait directement gagner l'adversaire
		int colonneDefaite = Constantes.COUP_NON_DEFINI;
		int processeurs = Runtime.getRuntime().availableProcessors();
		double utility_max = Constantes.SCORE_MIN_NON_DEFINI;
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
					NegamaxAlphaRecursive nega = new NegamaxAlphaRecursive(grille_next, Constantes.SCORE_MIN_NON_DEFINI,
							Constantes.SCORE_MAX_NON_DEFINI, tourExplore, 0);
					ForkJoinPool pool = new ForkJoinPool(processeurs);
					pool.invoke(nega);
					eval_grille_next = nega.getScore();
				}

				//System.out.println("COLONNE " + (col + 1) + " : " + eval_grille_next);
				if (eval_grille_next > utility_max) {
					utility_max = eval_grille_next;
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

}
