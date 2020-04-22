package fr.uha.ensisa.puissance4.data.evaluation.combinaisons;

import java.util.ArrayList;
import java.util.Collections;

import fr.uha.ensisa.puissance4.util.Constantes.Case;

/**
 * Liste de 4 {@link Case} consécutives dans une des 4 directions possibles<br>
 * <br>
 * <i>Note : Il y a 69 {@link Combinaison} différentes dans une
 * {@link fr.uha.ensisa.puissance4.data.Grille Grille}</i>
 * 
 * @author Robin
 * @author Yassine
 * @see CombinaisonHorizontale
 * @see CombinaisonVerticale
 * @see CombinaisonObliqueGaucheDroite
 * @see CombinaisonObliqueDroiteGauche
 * @see fr.uha.ensisa.puissance4.data.Grille Grille
 */
public abstract class Combinaison {
	private static int nbCombinaison = 0;

	protected ArrayList<Case> combinaison;
	/**
	 * 5ème {@link Case} de la combinaison permettant d'attribuer un score bonus aux
	 * {@link Combinaison} de 3 {@link Case} gagnantes entre 2 {@link Case} vides.
	 */
	protected Case bonus;
	protected int direction;
	protected int[] coordonnees;
	protected Case symbCourant;

	public Combinaison(int direction, Case symbCourant) {
		nbCombinaison++;
		this.direction = direction;
		this.combinaison = new ArrayList<Case>();
		this.bonus = null;
		this.symbCourant = symbCourant;
	}

	public Combinaison(int direction, Case symbCourant, int[] coord) {
		nbCombinaison++;
		this.direction = direction;
		this.combinaison = new ArrayList<Case>();
		this.bonus = null;
		this.symbCourant = symbCourant;
		this.coordonnees = coord;
	}

	/**
	 * Renvoie la direction de la combinaison
	 * 
	 * @return
	 */
	public abstract String getNomDirection();

	/**
	 * Renvoie les coordonnées de l'emplacement vide dans la combinaison
	 * 
	 * @return
	 */
	public abstract int[] getCoordVideDans3();

	public int getNbCombinaison() {
		return nbCombinaison;
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

	/**
	 * Ajout des coordonnées associées à la combinaison
	 * 
	 * @param coord
	 */
	public void addCoord(int[] coord) {
		this.coordonnees = coord;
	}

	/**
	 * Renvoie true si la combinaison a 3 jetons gagnants et 1 case vide
	 * 
	 * @return
	 */
	public boolean has3JetonsGagnants() {
		if (getNbCurrent() == 3 && getNbVides() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * Renvoie l'indice de la case vide dans une combinaison
	 * 
	 * @return
	 */
	public int getIndexVideDans3() {
		int idx = -1;
		if (this.has3JetonsGagnants()) {
			idx = combinaison.indexOf(Case.V);
		}
		return idx;
	}

	/**
	 * Renvoie le nombre de cases vides dans la combinaison
	 * 
	 * @return
	 */
	public int getNbVides() {
		return Collections.frequency(combinaison, Case.V);
	}

	/**
	 * Renvoie le nombre de cases remplies dans la combinaison par le joueur courant
	 * 
	 * @return
	 */
	public int getNbCurrent() {
		return Collections.frequency(combinaison, symbCourant);
	}

	/**
	 * Renvoie le score associé à la combinaison
	 * 
	 * @return
	 */
	public abstract double getScore();

	/**
	 * Renvoie le score associé à la combinaison
	 * 
	 * @return
	 */
	/*
	 * public double getScore() { double score = 0; // Combinaison parfaite if
	 * (getNbCurrent() == 4) { score = Constantes.SCORE_4; } // Combinaison possible
	 * if (getNbCurrent() == 3 && getNbVides() == 1) { score = Constantes.SCORE_3;
	 * if (this.combinaison.get(0)==Case.V && bonus==Case.V) { score +=
	 * Constantes.SCORE_BONUS; } } if (getNbCurrent() == 2 && getNbVides() == 2) {
	 * score = Constantes.SCORE_2; } if (getNbCurrent() == 1 && getNbVides() == 3) {
	 * score = Constantes.SCORE_1; } return score; }
	 */

	/**
	 * Présentation textuelle d'un combinaison
	 */
	@Override
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

}
