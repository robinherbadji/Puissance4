package fr.uha.ensisa.puissance4.jeu.algosIA.minimax;

import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.jeu.algosIA.Algorithm;
import fr.uha.ensisa.puissance4.util.Constantes;

public class MinimaxThread extends Algorithm {

	public MinimaxThread(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
	}
	
	
	// Création des Threads avec la Classe RecursiveTask -> Solution 1 (sur 2 au total)
	// Les 2 solutions ont des performances équivalentes
	@SuppressWarnings("serial")
	public class ExploreColonneRecursive extends RecursiveTask<Double> {
		double score;
		int colonneDefaite;
		int colonneVictoire;
		int colonne;

		public ExploreColonneRecursive(int colonne) {
			this.colonne = colonne;
			this.score = Constantes.SCORE_MIN_NON_DEFINI;
			this.colonneDefaite = Constantes.COUP_NON_DEFINI;
			this.colonneVictoire = Constantes.COUP_NON_DEFINI;
		}

		@Override
		protected Double compute() {
			Grille grille_next = grilleDepart.clone();
			if (grille_next.isCoupPossible(colonne)) {
				grille_next.ajouterCoup(colonne, symboleMax);
				tourExplore = tourDepart + 1;

				// Si l'IA peut gagner en un coup : jouer ce coup
				if (grille_next.isIAGagnante(symboleMax, tourDepart)) {
					System.out.println("C'est gagné");
					this.colonneVictoire = colonne;
					//return colonne;
				} else {
					// Si l'adversaire peut gagner après que l'on a joué : éviter ce coup
					//double score = Constantes.SCORE_MIN_NON_DEFINI;
					int victoireAdversaire = grille_next.isAdversaireGagnant(symboleMin, tourDepart);
					if (victoireAdversaire != Constantes.COUP_NON_DEFINI) {
						System.out.println("IA perd si elle met dans la colonne suivante : ");
						this.colonneDefaite = colonne;
					} else {
						this.score = min_value(grille_next, tourExplore);
					}

					//double eval_grille_next = min_value(grille_next);
					System.out.println("COLONNE " + (colonne + 1) + " : " + score);
				}
			}
			return this.score;
		}

		public int getColonne() {
			return this.colonne;
		}

		public int getColonneVictoire() {
			return this.colonneVictoire;
		}

		public int getColonneDefaite() {
			return this.colonneDefaite;
		}

	}

	/*
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

		int processeurs = Runtime.getRuntime().availableProcessors();
		// Choisir cette colonne ferait directement gagner l'adversaire		
		double utility_max = Constantes.SCORE_MIN_NON_DEFINI;
		ArrayList<ForkJoinPool> pools = new ArrayList<ForkJoinPool>();
		ArrayList<ExploreColonneRecursive> explorateurs = new ArrayList<ExploreColonneRecursive>();

		for (int col : this.classementColonnes()) {
			// Création de tous les Threads
			ExploreColonneRecursive explorateur = new ExploreColonneRecursive(col);
			explorateurs.add(explorateur);

			//Nous créons notre pool de thread pour nos tâches de fond
			ForkJoinPool pool = new ForkJoinPool(processeurs);
			pool.execute(explorateur);
			pools.add(pool);
		}

		int colonneDefaite = Constantes.COUP_NON_DEFINI;
		for (ExploreColonneRecursive explorateur : explorateurs) {
			double score = explorateur.join();
			if (explorateur.getColonneVictoire() != Constantes.COUP_NON_DEFINI) {
				return explorateur.getColonneVictoire();
			}
			if (explorateur.getColonneDefaite() != Constantes.COUP_NON_DEFINI) {
				colonneDefaite = explorateur.getColonneDefaite();
			}
			if (score > utility_max) {
				utility_max = score;
				coup = explorateur.getColonne();
			}
		}
		if (coup == Constantes.COUP_NON_DEFINI) {
			System.out.println("Bon ben là plus aucune chance");
			coup = colonneDefaite;
		}
		return coup;
	}
	*/
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	// Création des Threads avec interface Runnable Solution 2 (sur 2 au total)
	public class ExploreColonne implements Runnable {
		//Thread t;
		double score;
		int colonneDefaite;
		int colonneVictoire;
		int colonne;
		
		public ExploreColonne(int colonne) {
			this.colonne = colonne;
			this.score = Constantes.SCORE_MIN_NON_DEFINI;
			this.colonneDefaite = Constantes.COUP_NON_DEFINI;
			this.colonneVictoire = Constantes.COUP_NON_DEFINI;
		}
		
		@Override
		public void run() {
			Grille grille_next = grilleDepart.clone();
			if (grille_next.isCoupPossible(colonne)) {
				grille_next.ajouterCoup(colonne, symboleMax);
				tourExplore = tourDepart + 1;
				
				// Si l'IA peut gagner en un coup : jouer ce coup
				if (grille_next.isIAGagnante(symboleMax, tourDepart)) {
					System.out.println("C'est gagné");
					this.colonneVictoire = colonne;
					//return colonne;
				}
				else {
					// Si l'adversaire peut gagner après que l'on a joué : éviter ce coup
					//double score = Constantes.SCORE_MIN_NON_DEFINI;
					int victoireAdversaire = grille_next.isAdversaireGagnant(symboleMin, tourDepart);
					if (victoireAdversaire != Constantes.COUP_NON_DEFINI) {
						System.out.println("IA perd si elle met dans la colonne suivante : ");
						this.colonneDefaite = colonne;
					}
					else {
						this.score = min_value(grille_next, tourExplore);
					}
					
					//double eval_grille_next = min_value(grille_next);
					System.out.println("COLONNE "+(colonne+1)+" : "+score);
				}				
			}
		}
		
		public int getColonne() {
			return this.colonne;
		}
		
		public int getColonneVictoire() {
			return this.colonneVictoire;
		}
		
		public int getColonneDefaite() {
			return this.colonneDefaite;
		}
		
		public double getScore() {
			return this.score;
		}
	}
	
	
	// Méthode associée à la création de Threads via Runnable
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

		double utility_max = Constantes.SCORE_MIN_NON_DEFINI;
		ArrayList<Thread> threads = new ArrayList<Thread>();
		ArrayList<ExploreColonne> explorateurs = new ArrayList<ExploreColonne>();

		// double eval_grille_thread = Constantes.SCORE_MIN_NON_DEFINI;
		for (int col : this.classementColonnes()) {
			// Création de tous les Threads
			ExploreColonne explorateur = new ExploreColonne(col);
			explorateurs.add(explorateur);

			Thread thread = new Thread(explorateur);
			threads.add(thread);

			thread.start();

		}

		int i = 0;
		for (ExploreColonne explorateur : explorateurs) {
			// Thread thread = threadItr.next();
			// explorateur = explorateurItr.next();
			Thread thread = threads.get(i);

			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// thread.start();

			if (explorateur.getColonneVictoire() != Constantes.COUP_NON_DEFINI) {
				return explorateur.getColonneVictoire();
			}

			if (explorateur.getScore() > utility_max) {
				utility_max = explorateur.getScore();
				coup = explorateur.getColonne();
			}

			if (coup == Constantes.COUP_NON_DEFINI) {
				System.out.println("Bon ben là plus aucune chance");
				coup = explorateur.getColonneDefaite();
			}
			i++;
		}
		return coup;
	}
	
	
	private double min_value(Grille state, int profondeur) {
		// On regarde si l'IA a gagné, ou match nul ou la profondeur max de l'IA est atteinte
		int etatPartie = state.getEtatPartie(symboleMax, tourExplore);
		if ((etatPartie != Constantes.PARTIE_EN_COURS) || (this.tourExplore - this.tourDepart >= this.levelIA)) {
			return state.evaluer(symboleMax);
		}

		double utility = Constantes.SCORE_MAX_NON_DEFINI;
		
		// Parcours des successeurs
		int tourRef = profondeur;
		for (int col : this.classementColonnes()) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {
				//System.out.println("Prof : "+profondeur+"Depart : "+tourDepart);
				grille_next.ajouterCoup(col, symboleMin);
				//profondeur = tourRef + 1;
				double val = max_value(grille_next, tourRef + 1);
				//System.out.println("Val Min : "+ val);
				utility = Math.min(utility, val);
				//utility = Math.min(utility, max_value(grille_next, tourRef + 1));
			}
		}
		return utility;
	}
	
	private double max_value(Grille state, int profondeur) {
		// On regarde si l'adversaire a gagné, ou match nul ou la profondeur max de l'IA est atteinte
		int etatPartieAdverse = state.getEtatPartie(symboleMin, profondeur);
		if ((etatPartieAdverse != Constantes.PARTIE_EN_COURS) || (profondeur - this.tourDepart >= this.levelIA)) {
			return state.evaluer(symboleMax);
		}
		
		double utility = Constantes.SCORE_MIN_NON_DEFINI;
		
		// Parcours des successeurs
		int tourRef = profondeur;
		for (int col : this.classementColonnes()) {
			Grille grille_next = state.clone();
			if (grille_next.isCoupPossible(col)) {
				//System.out.println("Prof : "+profondeur+"Depart : "+tourDepart);
				grille_next.ajouterCoup(col, symboleMax);
				//profondeur = tourRef + 1;
				double val = min_value(grille_next, tourRef + 1);
				//System.out.println("Val Max : "+ val);
				utility = Math.max(utility, val);
				//utility = Math.max(utility, min_value(grille_next, tourRef + 1));
			}
		}
		return utility;
	}

}
