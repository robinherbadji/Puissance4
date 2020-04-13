package fr.uha.ensisa.puissance4.data;


public class Noeud {
	protected Noeud parent;
	protected int profondeur;
	
	public Noeud() {
		this.parent = null;
		this.profondeur = 0;
	}
	
	public Noeud(Noeud parent, int profondeur) {
		this.parent = parent;
		this.profondeur = profondeur;
	}
	
	public Noeud getParent() {
		return parent;
	}
	
	public int getProfondeur() {
		return profondeur;
	}
}
