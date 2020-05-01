package fr.uha.ensisa.puissance4.jeu.algosIA.minimax;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.jeu.algosIA.Algorithm;

public class NegamaxThread extends Algorithm {
	private int nbNoeuds;

	public NegamaxThread(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
		this.nbNoeuds = 0;
	}

	
	@SuppressWarnings("serial")
	public class NegamaxRecursive extends RecursiveTask<Double> {
		private int limite_threads = 1000;		
		
		private List<NegamaxRecursive> listNega = new ArrayList<NegamaxRecursive>();
		private Grille grille;
		private int profondeur;
		private double score;
		
		public NegamaxRecursive(Grille grille, int profondeur, int nbNoeuds) {
			NegamaxThread.this.nbNoeuds = 0;
			this.grille = grille;
			this.profondeur = profondeur;
			this.score = Constantes.SCORE_MIN_NON_DEFINI;
		}
		
		public NegamaxRecursive(Grille grille, int profondeur) {
			NegamaxThread.this.nbNoeuds ++;
			this.grille = grille;
			this.profondeur = profondeur;
			this.score = Constantes.SCORE_MIN_NON_DEFINI;
		}

		@Override
		protected Double compute() {
			// Si on dépasse un seuil de Threads, on passe en mono Thread			
			if (NegamaxThread.this.nbNoeuds >= this.limite_threads) {
				this.score = -negamax_mono_thread(this.grille, profondeur);
			}
			else {
				this.score = -negamax_multi_thread(this.grille, profondeur);
			}
			return score;
		}
		
		private double negamax_mono_thread(Grille state, int profondeur) {
			int etatPartie = state.getEtatPartie(symboleMax, profondeur);
			int etatPartieAdverse = state.getEtatPartie(symboleMin, profondeur);
			if ((etatPartie != Constantes.PARTIE_EN_COURS) || (etatPartieAdverse != Constantes.PARTIE_EN_COURS) || (profondeur - tourDepart >= levelIA)) {
				if ((profondeur - tourDepart) % 2 == 1) {
					return -state.evaluer(symboleMax);
				}
				else {
					return state.evaluer(symboleMax);
				}
			}
			
			int tourRef = profondeur;
			double utility = Constantes.SCORE_MIN_NON_DEFINI;
			for (int col : classementColonnes()) {
				Grille grille_next = state.clone();
				if (grille_next.isCoupPossible(col)) {
					if ((profondeur - tourDepart) % 2 == 1) {
						grille_next.ajouterCoup(col, symboleMin);
					}
					else {
						grille_next.ajouterCoup(col, symboleMax);
					}			
					utility = Math.max(utility, -negamax_mono_thread(grille_next, tourRef+1));
				}
			}
			return utility;	
		}
		
		
		private double negamax_multi_thread(Grille state, int profondeur) {
			int etatPartie = state.getEtatPartie(symboleMax, profondeur);
			int etatPartieAdverse = state.getEtatPartie(symboleMin, profondeur);
			if ((etatPartie != Constantes.PARTIE_EN_COURS) || (etatPartieAdverse != Constantes.PARTIE_EN_COURS) || (profondeur - tourDepart >= levelIA)) {
				if ((profondeur - tourDepart) % 2 == 1) {
					return -state.evaluer(symboleMax);
				}
				else {
					return state.evaluer(symboleMax);
				}
			}
			
			int tourRef = profondeur;
			for (int col : classementColonnes()) {
				Grille grille_next = state.clone();
				if (grille_next.isCoupPossible(col)) {
					if ((profondeur - tourDepart) % 2 == 1) {
						grille_next.ajouterCoup(col, symboleMin);
					}
					else {
						grille_next.ajouterCoup(col, symboleMax);
					}
					
					NegamaxRecursive nega = new NegamaxRecursive(grille_next, tourRef+1);
					listNega.add(nega);
					nega.fork();
				}
			}
			
			double utility = Constantes.SCORE_MIN_NON_DEFINI;
			for (NegamaxRecursive nega : listNega) {
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
		//int coup = Constantes.COUP_NON_DEFINI;
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
					NegamaxRecursive nega = new NegamaxRecursive(grille_next, tourExplore, 0);
					ForkJoinPool pool = new ForkJoinPool(processeurs);
					pool.invoke(nega);
					eval_grille_next = nega.getScore();
				}
				
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
	

}
