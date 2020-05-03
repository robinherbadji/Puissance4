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
			try {
				this.runFXMLControlleur();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	
	
	public synchronized void runFXMLControlleur() throws InterruptedException {
		Platform.runLater(() -> controlleur.lancementPartie(partie.getJoueur1(), partie.getJoueur2()));
		
		//while(!partie.isPartieFinie() && !controlleur.interrupted())
		while(!partie.isPartieFinie())
		{
			Platform.runLater(() -> controlleur.lancementTour(partie.getTour(), partie.getJoueurCourant(), partie.getGrille()));
			wait(10);
			
			long tempsDebut = System.currentTimeMillis();
			//long tempsReflexion = System.currentTimeMillis();
			
			System.out.println("Joueur Courant RUN " + partie.getJoueurCourant().getNom());
			
			//Platform.runLater(() -> ((ControlleurFXML)controlleur).jouerTour(partie.getGrille(), controlleur, partie.getTour()));
					
			((ControlleurFXML)controlleur).resetBouton();
			int coup = partie.getJoueurCourant().joue(partie.getGrille(), controlleur, partie.getTour());
			((ControlleurFXML)controlleur).resetBouton();
			
			
			//tempsReflexion=System.currentTimeMillis()-tempsReflexion;
			final long tempsReflexion=System.currentTimeMillis()-tempsDebut;
			wait(10);
			Platform.runLater(() -> ((ControlleurFXML)controlleur).afficherCoup(partie.getJoueurCourant(), coup, tempsReflexion));
			Platform.runLater(() -> ((ControlleurFXML)controlleur).jouerCoupGraphique(coup, tempsReflexion));
			wait(10);
			System.out.println("Run etat :"+partie.getEtatPartie());			
		}		
		
		Platform.runLater(() -> controlleur.afficherFinPartie(partie));		
	}
	

}
