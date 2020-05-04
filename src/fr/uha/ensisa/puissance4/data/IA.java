package fr.uha.ensisa.puissance4.data;

import fr.uha.ensisa.puissance4.jeu.algosIA.Algorithm;
import fr.uha.ensisa.puissance4.jeu.algosIA.alphabeta.AlphaBeta;
import fr.uha.ensisa.puissance4.jeu.algosIA.alphabeta.NegamaxAlphaThread;
import fr.uha.ensisa.puissance4.jeu.algosIA.minimax.Minimax;
import fr.uha.ensisa.puissance4.jeu.algosIA.minimax.NegamaxThread;
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
		Algorithm iA = null;
		switch (algoIA) {
		case Constantes.IA_MINIMAX:
			iA = new Minimax(levelIA, grille, this, tour);
			break;
		case Constantes.IA_ALPHABETA:
			iA = new AlphaBeta(levelIA, grille, this, tour); // Le + rapide des AlphaBeta
			break;
		case Constantes.IA_NEGAMAX_THREAD:
			iA = new NegamaxThread(levelIA, grille, this, tour); // Le + rapide des Minimax
			break;
		case Constantes.IA_NEGAMAX_ALPHA_THREAD:
			iA = new NegamaxAlphaThread(levelIA, grille, this, tour);
			break;
		default:
			break;			
		}
		
		return iA.choisirCoup();
	}


}
