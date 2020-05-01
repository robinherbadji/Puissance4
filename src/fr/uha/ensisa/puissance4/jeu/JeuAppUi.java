package fr.uha.ensisa.puissance4.jeu;

import fr.uha.ensisa.puissance4.data.Humain;
import fr.uha.ensisa.puissance4.data.IA;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.ui.sample.Controller;
import fr.uha.ensisa.puissance4.ui.sample.UIConnect4;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.application.Platform;

public class JeuAppUi extends Thread{

    /*
    private Partie partie;
    private Controller controller;
    private Console console;

    public JeuAppUi(Joueur joueur1, Joueur joueur2, Controller controller)
    {
        this.partie=new Partie(joueur1, joueur2);
        this.controller=controller;
    }

    public JeuAppUi(Joueur joueur1, Joueur joueur2, Controller controller, Console console)
    {
        this.partie=new Partie(joueur1, joueur2);
        this.controller=controller;
        this.console = console;
    }

    public void run()
    {
        //Console Lancement Partie
        console.lancementPartie(partie.getJoueur1(), partie.getJoueur2());
        //Controller Lancement Partie
        controller.lancementPartie(partie.getJoueur1(), partie.getJoueur2());

        /*
        while(!partie.isPartieFinie())
        {
            console.lancementTour(partie.getTour(), partie.getJoueurCourant(), partie.getGrille());

            long tempsReflexion=System.currentTimeMillis();
            int coup= partie.getJoueurCourant().joue(partie.getGrille(), console, partie.getTour());
            tempsReflexion=System.currentTimeMillis()-tempsReflexion;
            console.afficherCoup(partie.getJoueurCourant(), coup, tempsReflexion);
            if(!partie.jouerCoup(coup, tempsReflexion))
            {
                System.out.println("COUP INVALIDE : Recommencez !");
            }
        }
        console.closeScanner();
        console.afficherFinPartie(partie);


    }*/



    private Partie partie;
    private Controller controller;
    private Console console;

    public JeuAppUi(Joueur joueur1, Joueur joueur2, Controller controller)
    {
        this.partie=new Partie(joueur1, joueur2);
        this.controller=controller;
    }

    public JeuAppUi(Joueur joueur1, Joueur joueur2, Controller controller, Console console)
    {
        this.partie=new Partie(joueur1, joueur2);
        this.controller=controller;
        this.console = console;
    }

    public void run()
    {
        while(!partie.isPartieFinie() && !controller.interrupted())
        {

            if(controller.coupJoue){
                long tempsReflexion=System.currentTimeMillis();

                System.out.println("C'est au tour de : "+partie.getJoueurCourant().getNom());

                int coup= partie.getJoueurCourant().joue(partie.getGrille(), controller, partie.getTour());
                tempsReflexion=System.currentTimeMillis()-tempsReflexion;
                System.out.println(coup);

                if(!partie.jouerCoup(coup, tempsReflexion))
                {
                    System.out.println("COUP INVALIDE : Recommencez !");
                }else{
                    if(partie.getJoueurCourant().getOrder()==Constantes.JOUEUR_2){
                        Runnable addJetonYThread = () -> {
                            controller.addJetonYass(coup+1);
                        };
                        addJetonYThread.run();
                    }
                    else{
                        Runnable addJetonRThread = () -> {
                            controller.addJetonBadji(coup+1);
                        };
                        addJetonRThread.run();
                    }

                }
                controller.coupJoue =false;
                if(partie.getJoueurCourant().getType()==Constantes.JOUEUR_IA){
                    controller.coupJoue =true;
                }
            }
        }
        Platform.runLater(() -> controller.afficherFinPartie(partie.getEtatPartie()));

        System.out.println("fin partie");

    }



}
