package fr.uha.ensisa.puissance4.jeu;

import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.ui.InterfaceCommande;
import fr.uha.ensisa.puissance4.ui.graphique.controlleur.Controlleur;

public class Jeu extends Thread{
	
	private Partie partie;
	private Console console;
	private InterfaceCommande itface;
	
	public Jeu(Joueur joueur1, Joueur joueur2, Controlleur controlleur)
	{
		this.partie = new Partie(joueur1, joueur2);
		this.itface = controlleur;
	}
	
	public Jeu(Joueur joueur1, Joueur joueur2, Console console)
	{
		this.partie = new Partie(joueur1, joueur2);
		this.itface = console;
	}
	
	
	public void run()
	{
		itface.lancementPartie(partie.getJoueur1(), partie.getJoueur2());		
		while(!partie.isPartieFinie())
		{
			itface.lancementTour(partie.getTour(), partie.getJoueurCourant(), partie.getGrille());
			
			long tempsReflexion=System.currentTimeMillis();
			int coup= partie.getJoueurCourant().joue(partie.getGrille(), itface, partie.getTour());
			tempsReflexion=System.currentTimeMillis()-tempsReflexion;
			itface.afficherCoup(partie.getJoueurCourant(), coup, tempsReflexion);
			if(!partie.jouerCoup(coup, tempsReflexion))
			{
				System.out.println("COUP INVALIDE : Recommencez !");
			}
		}
		
		itface.afficherFinPartie(partie);
	}
	
	/*
	public void run()
	{
		console.lancementPartie(partie.getJoueur1(), partie.getJoueur2());		
		while(!partie.isPartieFinie())
		{
			console.lancementTour(partie.getTour(), partie.getJoueurCourant(), partie.getGrille());
			
			long tempsReflexion=System.currentTimeMillis();
			int coup= partie.getJoueurCourant().joue(partie.getGrille(), console, partie.getTour());
			tempsReflexion=System.currentTimeMillis()-tempsReflexion;
			console.afficherCoup(partie.getJoueurCourant(), coup, tempsReflexion);
			if(!partie.jouerCoup(coup, tempsReflexion))
			{
				System.out.println("COUP INVALIDE : Recommencez !");
			}
		}
		console.closeScanner();
		console.afficherFinPartie(partie);
	}
	*/
	

}
