package fr.uha.ensisa.puissance4.data;

import fr.uha.ensisa.puissance4.ui.Controlleur;
import fr.uha.ensisa.puissance4.util.Constantes;

public class Humain extends Joueur {

	public Humain(String nom, int order) {
		super(nom, order);		
	}

	@Override
	public int getType() {
		return Constantes.JOUEUR_HUMAN;
	}

	@Override
	public String getTypeNom() {
		return "Humain";
	}


	@Override
	public int joue(Grille grille, Controlleur itface, int tour) {
		return (itface.getHumanCoup(this.getNom())-1);
	}

}
