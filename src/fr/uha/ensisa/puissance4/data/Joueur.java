package fr.uha.ensisa.puissance4.data;

import fr.uha.ensisa.puissance4.ui.Controlleur;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public abstract class Joueur {
	/**
	 * Nom du joueur
	 */
	protected String nom;
	/**
	 * Indique si le joueur est le joueur 1 ou 2
	 */
	protected int order;
	/**
	 * Chemin de l'icône du joueur
	 */
	protected String icone_path;
	
	/**
	 * Constructeur spécifique à l'utilisation Console
	 * 
	 * @param nom
	 * @param order
	 */
	protected Joueur(String nom, int order)
	{
		this.nom = nom;
		this.order = order;
	}
	
	/**
	 * Constructeur spécifique à l'utilisation GUI
	 * 
	 * @param nom
	 * @param order
	 * @param icone_path
	 */
	protected Joueur(String nom, int order, String icone_path)
	{
		this.nom = nom;
		this.order = order;
		this.icone_path = icone_path;
	}
	
	/**
	 * Indique si le joueur est une IA ou un humain
	 * @return
	 */
	public abstract int getType();
	
	/**
	 * Renvoie "Humain" ou "IA" à des fins d'affichage
	 * @return
	 */
	public abstract String getTypeNom();
	
	/**
	 * Renvoie si le joueur est le joueur 1 ou 2
	 * @return Constantes.JOUEUR_1 ou Constantes.JOUEUR_2
	 */
	public int getOrder()
	{
		return order;
	}
	
	/**
	 * Renvoie le nom du joueur
	 * @return String nom
	 */
	public String getNom()
	{
		return nom;
	}
	
	/**
	 * Renvoie le symbole utilisé par le joueur (X ou O)
	 * @return Case symbole
	 */
	public Case getSymbole()
	{
		if(getOrder()==Constantes.JOUEUR_1)
		{
			return Constantes.SYMBOLE_J1;
		}
		else
		{
			return Constantes.SYMBOLE_J2;
		}
	}
	
	public String getIconePath() {
		return this.icone_path;
	}

	/**
	 * Fais jouer un tour au joueur
	 * @param grille
	 * @param controlleur
	 * @param tour
	 * @return
	 */
	public abstract int joue(Grille grille, Controlleur controlleur, int tour);
	

}
