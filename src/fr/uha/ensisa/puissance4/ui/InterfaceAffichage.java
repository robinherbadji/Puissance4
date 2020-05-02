package fr.uha.ensisa.puissance4.ui;

import fr.uha.ensisa.puissance4.data.Joueur;

public abstract class InterfaceAffichage extends Thread {
	
	public abstract void lancementPartie(Joueur joueur1, Joueur joueur2);
	
	
}
