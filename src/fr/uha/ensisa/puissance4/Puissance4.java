package fr.uha.ensisa.puissance4;

import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.ui.sample.UIConnect4;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.application.Application;

public abstract class Puissance4 {

	public static void main(String[] args) {

		int mode = Constantes.MODE_INTERFACE_GRAPHIQUE;
		//Indique la bonne interface et la lance dans un thread différent
		switch(mode)
		{
			case Constantes.MODE_INTERFACE_GRAPHIQUE : Application.launch(UIConnect4.class,args);
				break;
			case Constantes.MODE_CONSOLE : Console console = new Console();
				console.start();
				break;
			default : break;
		}

	}

}
