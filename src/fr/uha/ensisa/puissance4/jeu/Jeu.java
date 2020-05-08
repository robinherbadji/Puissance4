package fr.uha.ensisa.puissance4.jeu;

import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.ui.Controlleur;
import fr.uha.ensisa.puissance4.ui.graphique.controlleur.ControlleurFXML;
import javafx.application.Platform;

public class Jeu extends Thread {

	private Partie partie;
	private Controlleur controlleur;

	public Jeu(Joueur joueur1, Joueur joueur2, Console console) {
		this.partie = new Partie(joueur1, joueur2);
		this.controlleur = console;
	}

	public Jeu(Joueur joueur1, Joueur joueur2, ControlleurFXML controlleurFXML) {
		this.partie = new Partie(joueur1, joueur2);
		this.controlleur = controlleurFXML;
	}

	
	public Partie getPartie() {
		return this.partie;
	}

	public void run() {
		if (controlleur instanceof Console) {
			this.runConsole();
		}

		if (controlleur instanceof ControlleurFXML) {
			try {
				this.runFXMLControlleur();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void runConsole() {
		controlleur.lancementPartie(partie.getJoueur1(), partie.getJoueur2());
		while (!partie.isPartieFinie()) {
			controlleur.lancementTour(partie.getTour(), partie.getJoueurCourant(), partie.getGrille());

			long tempsReflexion = System.currentTimeMillis();
			int coup = partie.getJoueurCourant().joue(partie.getGrille(), controlleur, partie.getTour());
			tempsReflexion = System.currentTimeMillis() - tempsReflexion;
			controlleur.afficherCoup(partie.getJoueurCourant(), coup, tempsReflexion);
			if (!partie.jouerCoup(coup, tempsReflexion)) {
				System.out.println("COUP INVALIDE : Recommencez !");
			}
		}

		controlleur.afficherFinPartie(partie);
	}

	public synchronized void runFXMLControlleur() throws InterruptedException {
		Platform.runLater(() -> controlleur.lancementPartie(partie.getJoueur1(), partie.getJoueur2()));
		while (!partie.isPartieFinie() && !((ControlleurFXML)controlleur).interruptJeu) {
			try {
				Platform.runLater(() -> controlleur.lancementTour(partie.getTour(), partie.getJoueurCourant(),
						partie.getGrille()));
				wait(25);
				
				long tempsDebut = System.currentTimeMillis();				
				((ControlleurFXML) controlleur).resetBouton();
				int coup = partie.getJoueurCourant().joue(partie.getGrille(), controlleur, partie.getTour());
				((ControlleurFXML) controlleur).resetBouton();
				
				
				if (((ControlleurFXML)controlleur).interruptJeu) {
					Thread.currentThread().interrupt();
					return;
				}

				final long tempsReflexion = System.currentTimeMillis() - tempsDebut;
				wait(25);
				Platform.runLater(() -> ((ControlleurFXML) controlleur).afficherCoup(partie.getJoueurCourant(), coup,
						tempsReflexion));
				Platform.runLater(() -> ((ControlleurFXML) controlleur).jouerCoupGraphique(coup, tempsReflexion));
				wait(25);

			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				((ControlleurFXML)controlleur).interruptJeu = false;
				return;
			}
		}
		Platform.runLater(() -> controlleur.afficherFinPartie(partie));
	}

}
