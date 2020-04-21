package fr.uha.ensisa.puissance4.data.evaluation.combinaisons;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Liste de toutes les {@link Combinaison} ayant 3 {@link Case} gagnantes et 1
 * {@link Case} vide
 * <p>
 * Cette classe permettra de mettre en place une stratégie gagnante qui consiste
 * à avoir 2 cases vides superposées, chacune complétant 2 combinaisons
 * différentes. Ainsi, aucune des actions de l'adversaire ne pourra l'empêcher
 * de perdre
 * </p>
 * 
 * @author Robin
 * @author Yassine
 * @see Combinaison
 * @see CombinaisonHorizontale
 * @see CombinaisonVerticale
 * @see CombinaisonObliqueGaucheDroite
 * @see CombinaisonObliqueDroiteGauche
 */
public class Groupe3Combinaisons {

	private ArrayList<int[]> coordTrous;

	public Groupe3Combinaisons() {
		this.coordTrous = new ArrayList<int[]>();
	}

	public void add(int[] coord) {
		this.coordTrous.add(coord);
	}

	public boolean contains(int[] coord) {
		Iterator<int[]> itr = this.coordTrous.iterator();
		while (itr.hasNext()) {
			int[] current = itr.next();
			if (current[0] == coord[0] && current[1] == coord[1]) {
				return true;
			}
		}
		return false;
	}

}
