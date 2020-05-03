package fr.uha.ensisa.puissance4.data;

import fr.uha.ensisa.puissance4.jeu.algosIA.Algorithm;
import fr.uha.ensisa.puissance4.jeu.algosIA.alphabeta.AlphaBeta;
import fr.uha.ensisa.puissance4.jeu.algosIA.alphabeta.NegamaxAlphaThread;
import fr.uha.ensisa.puissance4.jeu.algosIA.minimax.Minimax;
import fr.uha.ensisa.puissance4.jeu.algosIA.minimax.NegamaxThread;
import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.ui.Controlleur;
import fr.uha.ensisa.puissance4.util.Constantes;

/**
 Classe permettant de gérer l'IA comme joueur	
*/
public class IA extends Joueur {
	
	private int algoIA;
	private int levelIA;
	
	
	public IA(String nom, int order, int algoIA, int levelIA) {
		super(nom, order);
		this.algoIA=algoIA;
		this.levelIA=levelIA;
	}

	@Override
	public int getType() {
		return Constantes.JOUEUR_IA;
	}
	
	public int getAlgoIA()
	{
		return algoIA;
	}

	@Override
	public String getTypeNom() {
		return "IA";
	}
	

	@Override
	public int joue(Grille grille, Controlleur itface, int tour) {
		itface.reflexionIA(this.getNom());
		Algorithm iA;
		if(algoIA==Constantes.IA_MINIMAX)
		{
			//iA = new Minimax(levelIA, grille, this, tour);
			iA = new NegamaxThread(levelIA, grille, this, tour); // Algorithme le plus rapide			
		}
		else
		{
			iA = new AlphaBeta(levelIA, grille, this, tour); // Algorithme le plus rapide
			//iA = new NegamaxAlphaThread(levelIA, grille, this, tour);			
		}
		return iA.choisirCoup();
	}
	
	/*
	@Override
	public int joue(Grille grille, Console console, int tour) {
		console.reflexionIA(this.getNom());
		Algorithm iA;
		if(algoIA==Constantes.IA_MINIMAX)
		{
			//iA = new Minimax(levelIA, grille, this, tour);
			iA = new NegamaxThread(levelIA, grille, this, tour); // Algorithme le plus rapide
			
		}
		else
		{
			iA = new AlphaBeta(levelIA, grille, this, tour); // Algorithme le plus rapide
			//iA = new NegamaxAlphaThread(levelIA, grille, this, tour);			
		}
		
		return iA.choisirCoup();
	}
	*/


}
