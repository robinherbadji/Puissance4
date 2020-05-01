package fr.uha.ensisa.puissance4.ui.sample;

import java.net.URL;
import java.util.ResourceBundle;

import fr.uha.ensisa.puissance4.data.Humain;
import fr.uha.ensisa.puissance4.data.IA;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Controller extends Thread {

    public boolean coupJoue = false;
    UIConnect4 ui;
    int c1=0,c2=0,c3=0,c4=0,c5=0,c6=0,c7=0;
    final int stepX=75;
    final int stepY=76;
    final int margeY=460;
    final int margeX=210;

    @FXML
    Label turnId;
    private int humanCoup;
    private String nomJoueurY;
    private String nomJoueurR;

    @FXML
    public void handleStartButton(ActionEvent actionEvent) {
    }

    @FXML
    public void handleButton(ActionEvent actionEvent) {

    }

    @FXML
    public void handleCheckButton(ActionEvent actionEvent) {
    }

    @FXML
    public void handleButtonClose(ActionEvent actionEvent) {
        System.exit(0);
    }

    public int getHumanCoup() {
        return humanCoup;
    }

    public void lancementPartie(Joueur joueur1, Joueur joueur2) {
        Label label = new Label("Draw");
        label.setLayoutX(315);
        label.setLayoutY(610);
        label.setFont(Font.font("Cambria", 26));
        UIConnect4.panel.getChildren().add(label);
    }

    public void setMainApp(UIConnect4 gui) {
        this.ui = gui;
    }

    public void addJetonYass(int colonne) {
        URL imageURL = getClass().getResource("@../resources/Yass.png");
        final Image image = new Image(imageURL.toExternalForm());
        final ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        imageView.setLayoutX(margeX+stepX*(colonne-1));
        switch (colonne) {
            case 1:  	imageView.setLayoutY(margeY-c1*stepY);
                c1++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 2:  	imageView.setLayoutY(margeY-c2*stepY);
                c2++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 3:  	imageView.setLayoutY(margeY-c3*stepY);
                c3++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 4:  	imageView.setLayoutY(margeY-c4*stepY);
                c4++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 5:  	imageView.setLayoutY(margeY-c5*stepY);
                c5++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 6:  	imageView.setLayoutY(margeY-c6*stepY);
                c6++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 7:	imageView.setLayoutY(margeY-c7*stepY);
                c7++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            default:
                break;
        }
    }

    public void addJetonBadji(int colonne) {
        URL imageURL = getClass().getResource("@../resources/Badji.png");
        final Image image = new Image(imageURL.toExternalForm());
        final ImageView imageView = new ImageView(image);
        imageView.setFitWidth(70);
        imageView.setFitHeight(70);
        imageView.setLayoutX(margeX+stepX*(colonne-1));
        switch (colonne) {
            case 1:  	imageView.setLayoutY(margeY-c1*stepY);
                c1++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 2:  	imageView.setLayoutY(margeY-c2*stepY);
                c2++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 3:  	imageView.setLayoutY(margeY-c3*stepY);
                c3++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 4:  	imageView.setLayoutY(margeY-c4*stepY);
                c4++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 5:  	imageView.setLayoutY(margeY-c5*stepY);
                c5++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 6:  	imageView.setLayoutY(margeY-c6*stepY);
                c6++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            case 7:	imageView.setLayoutY(margeY-c7*stepY);
                c7++;
                UIConnect4.rootLayout.getChildren().add(imageView);
                break;
            default:
                break;
        }
    }

    public void afficherFinPartie(int etatPartie) {
        if(etatPartie==Constantes.MATCH_NUL){
            Label label = new Label("");
            label.setLayoutX(300);
            label.setLayoutY(10);
            label.setFont(Font.font("Georgia", 38));
            UIConnect4.panel.getChildren().add(label);
        }
        if(etatPartie==Constantes.VICTOIRE_JOUEUR_1){
            Label label = new Label(nomJoueurY+"a gagné");
            label.setLayoutX(320);
            label.setLayoutY(650);
            label.setFont(Font.font("Georgia", 38));

            UIConnect4.panel.getChildren().add(label);

        }
        if(etatPartie==Constantes.VICTOIRE_JOUEUR_2){
            Label label = new Label(nomJoueurR+"a gagné");
            label.setLayoutX(320);
            label.setLayoutY(650);
            label.setFont(Font.font("Georgia", 38));
            UIConnect4.panel.getChildren().add(label);
        }
    }
}





