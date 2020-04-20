package fr.uha.ensisa.puissance4.data.evaluation;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public abstract class Strategie {
	protected Grille grille;
	protected Case symboleJoueurCourant;
	protected Case symboleJoueurAdverse;
	protected int tourExplore;
	protected int tourDepart;
	
	
	public Strategie(Grille grille, Case symboleJoueurCourant) {
		this.grille = grille;
		this.symboleJoueurCourant = symboleJoueurCourant;
		this.symboleJoueurAdverse = getSymboleAdverse(symboleJoueurCourant);
	}
	
	
	public Strategie(Grille grille, Case symboleJoueurCourant, int tourDepart, int tourExplore) {
		this.grille = grille;
		this.symboleJoueurCourant = symboleJoueurCourant;
		this.symboleJoueurAdverse = getSymboleAdverse(symboleJoueurCourant);
		this.tourDepart = tourDepart;
		this.tourExplore = tourExplore;
	}
	
	public abstract double getUtility();
	
	private Case getSymboleAdverse(Case symboleJoueurCourant) {
		Case symboleJoueurAdverse=Constantes.SYMBOLE_J1;
		if(symboleJoueurCourant==Constantes.SYMBOLE_J1)
		{
			symboleJoueurAdverse=Constantes.SYMBOLE_J2;
		}
		return symboleJoueurAdverse;
	}
	
	
}

