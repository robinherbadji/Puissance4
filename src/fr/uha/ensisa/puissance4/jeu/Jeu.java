package fr.uha.ensisa.puissance4.jeu;

import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.ui.Controlleur;
import fr.uha.ensisa.puissance4.ui.graphique.controlleur.ControlleurFXML;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.application.Platform;

public class Jeu extends Thread{
	
	private Partie partie;
	private Controlleur controlleur;
	
	
	public Jeu(Joueur joueur1, Joueur joueur2, ControlleurFXML controlleurFXML)
	{
		this.partie = new Partie(joueur1, joueur2);
		this.controlleur = controlleurFXML;
	}
	
	
	public Jeu(Joueur joueur1, Joueur joueur2, Console console)
	{
		this.partie = new Partie(joueur1, joueur2);
		this.controlleur = console;
	}
	
	
	public Partie getPartie() {
		return this.partie;		
	}
	
	
	public void run() {
		if (controlleur instanceof Console) {
			this.runConsole();
		}
		if (controlleur instanceof ControlleurFXML) {
			this.runFXMLControlleur();
		}
	}
	
	
	public void runConsole() {
		controlleur.lancementPartie(partie.getJoueur1(), partie.getJoueur2());		
		while(!partie.isPartieFinie())
		{
			controlleur.lancementTour(partie.getTour(), partie.getJoueurCourant(), partie.getGrille());
			
			long tempsReflexion=System.currentTimeMillis();
			int coup= partie.getJoueurCourant().joue(partie.getGrille(), controlleur, partie.getTour());
			tempsReflexion=System.currentTimeMillis()-tempsReflexion;
			controlleur.afficherCoup(partie.getJoueurCourant(), coup, tempsReflexion);
			if(!partie.jouerCoup(coup, tempsReflexion))
			{
				System.out.println("COUP INVALIDE : Recommencez !");
			}			
		}
		
		controlleur.afficherFinPartie(partie);
	}
	
	
	public void runFXMLControlleur() {
		Platform.runLater(() -> controlleur.lancementPartie(partie.getJoueur1(), partie.getJoueur2()));
		
		// TESTS /////////////////
		/*
		Platform.runLater(() -> controlleur.lancementTour(partie.getTour(), partie.getJoueurCourant(), partie.getGrille()));
		
		int coup = 5;
		if(!partie.jouerCoup(coup, 20))
		{
			System.out.println("COUP INVALIDE : Recommencez !");
		}
		else {
			Platform.runLater(() -> ((ControlleurFXML)controlleur).afficher_grille(partie.getGrille(), partie.getJoueurCourant(), coup));
		}
		*/
		
		/*
		long tempsReflexion=System.currentTimeMillis();
		int coup = -1;
		
		
		if (partie.getJoueurCourant().getType() == Constantes.JOUEUR_HUMAN) {
			while (coup <= -1) {
			//while (!((ControlleurFXML)controlleur).hasJoueurClicked()) {
				//coup = partie.getJoueurCourant().joue(partie.getGrille(), Platform.runLater(() -> ((ControlleurFXML) controlleur).getControlleurFXML()), partie.getTour());
				coup = partie.getJoueurCourant().joue(partie.getGrille(), controlleur, partie.getTour());

				//System.out.println(coup);
			}
		}
		else {
			coup = partie.getJoueurCourant().joue(partie.getGrille(), controlleur, partie.getTour());
		}
		*/
	
		
		
		/////////////////////////////////////////
		
		
		while(!partie.isPartieFinie() && !controlleur.interrupted())
		{
			Platform.runLater(() -> controlleur.lancementTour(partie.getTour(), partie.getJoueurCourant(), partie.getGrille()));
			
			long tempsDebut = System.currentTimeMillis();
			
			final int coup = partie.getJoueurCourant().joue(partie.getGrille(), controlleur, partie.getTour());
			((ControlleurFXML)controlleur).resetBouton();
			
			System.out.println("Selectioné : "+coup);
			final long tempsReflexion=System.currentTimeMillis()-tempsDebut;
			
			
			Platform.runLater(() -> ((ControlleurFXML)controlleur).jouerCoupGraphique(coup, tempsReflexion));
			Platform.runLater(() -> ((ControlleurFXML)controlleur).afficherCoup(partie.getJoueurCourant(), coup, tempsReflexion));
			
			/*
			Platform.runLater(() -> ((ControlleurFXML)controlleur).afficherCoup(partie.getJoueurCourant(), c, t));
			
			if(!partie.jouerCoup(coup, tempsReflexion))
			{
				System.out.println("COUP INVALIDE : Recommencez !");
			}
			else {
				Platform.runLater(() -> ((ControlleurFXML)controlleur).afficherGrille(partie.getGrille()));
			}*/
			
		}
		
		
		
		Platform.runLater(() -> controlleur.afficherFinPartie(partie));
		
	}
	

}
