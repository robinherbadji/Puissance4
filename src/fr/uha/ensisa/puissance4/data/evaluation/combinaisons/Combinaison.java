package fr.uha.ensisa.puissance4.data.evaluation.combinaisons;

import java.util.ArrayList;
import java.util.Collections;

import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public abstract class Combinaison {
	private static int nbCombinaison = 0;
	
	//private Case[][] grille;
	protected ArrayList<Case> combinaison;
	protected Case bonus;
	protected int direction;
	protected Case symbCourant;
	protected Case symbAdverse;	
	
	public Combinaison(int direction, ArrayList<Case> combinaison, Case symbCourant) {
		nbCombinaison ++;
		//this.grille = grille;
		this.direction = direction;
		this.combinaison = combinaison;
		this.bonus = null;
		this.symbCourant = symbCourant;
		this.symbAdverse = getSymboleAdverse(symbCourant);
	}

	public Combinaison(int direction, Case symbCourant) {
		nbCombinaison ++;
		//this.grille = grille;
		this.direction = direction;
		this.combinaison = new ArrayList<Case>();
		this.bonus = null;
		this.symbCourant = symbCourant;
		this.symbAdverse = getSymboleAdverse(symbCourant);
	}
	
	
	public int getNbCombinaison() {
		return nbCombinaison;
	}	
	public void setNbCombinaison(int i) {
		nbCombinaison = i;
	}
	public void resetNbCombinaison() {
		nbCombinaison = 0;
	}
	
	public void addSymbole(Case symbole) {
		this.combinaison.add(symbole);
	}
	
	public void addBonus(Case symbole) {
		this.bonus = symbole;
	}
	
	//public abstract double getScore();
	
	
	private Case getSymboleAdverse(Case symboleJoueurCourant) {
		Case symboleJoueurAdverse=Constantes.SYMBOLE_J1;
		if(symboleJoueurCourant==Constantes.SYMBOLE_J1)
		{
			symboleJoueurAdverse=Constantes.SYMBOLE_J2;
		}
		return symboleJoueurAdverse;
	}
	
	public int getNbVides() {
		return Collections.frequency(combinaison, Case.V);
	}
	
	public int getNbCurrent() {
		return Collections.frequency(combinaison, symbCourant);
	}
	
	public int getNbOther() {
		return Collections.frequency(combinaison, symbAdverse);
	}
	
	public abstract String getNomDirection();
	
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append(this.getNomDirection());
		str.append(" [");
		for (Case symbole : combinaison) {
			str.append(symbole);
			str.append(", ");
		}
		str.append(bonus);
		str.append("]");
		return str.toString();		
	}
	
	public double getScore() {
		double score = 0;
		// Combinaison parfaite
		if (getNbCurrent() == 4) {
			score = Constantes.SCORE_4;
		}
		// Combinaison possible
		if (getNbCurrent() == 3 && getNbVides() == 1) {
			score = Constantes.SCORE_3;
			if (this.combinaison.get(0)==Case.V && bonus==Case.V) {
				score += Constantes.SCORE_BONUS;
			}
		}
		if (getNbCurrent() == 2 && getNbVides() == 2) {
			score = Constantes.SCORE_2;
		}
		if (getNbCurrent() == 1 && getNbVides() == 3) {
			score = Constantes.SCORE_1;
		}
		return score;
	}
}
