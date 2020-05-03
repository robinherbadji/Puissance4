package fr.uha.ensisa.puissance4.ui;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;

public abstract class Controlleur extends Thread {
	
	public Controlleur(String name) {
		super(name);
	}
	
	public abstract void lancementPartie(Joueur joueur1, Joueur joueur2);
	public abstract void lancementTour(int tour, Joueur joueurCourant, Grille grille);
	public abstract void afficherCoup(Joueur joueurCourant, int coup, long t);
	public abstract void afficherFinPartie(Partie partie);
	public abstract int getHumanCoup(String nom);
	public abstract void reflexionIA(String nom);
	//protected abstract void afficheGrille(Grille grille);
	
	
	protected String timeToString(long t) {
		String s = "";
		if (t > 3600000) {
			long h = t / 3600000;
			s += h + "h ";
			t -= h * 3600000;
		}
		if (t > 60000) {
			long m = t / 60000;
			s += m + "m ";
			t -= m * 60000;
		}
		if (t > 1000) {
			long sec = t / 1000;
			s += sec + "s ";
			t -= sec * 1000;
		}
		if (t > 0) {
			s += t + "ms";
		}
		return s;
	}
	
}
