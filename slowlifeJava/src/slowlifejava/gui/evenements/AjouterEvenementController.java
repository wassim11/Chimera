/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.evenements;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import slowlifejava.Slowlife;
import slowlifejava.entities.evenements.Evenement;
import slowlifejava.services.evenements.EvenementService;

/**
 * FXML Controller class
 *
 * @author Ahmed Ezzine
 */
public class AjouterEvenementController implements Initializable {

    @FXML
    private TextField nomEvent;
    @FXML
    private TextArea descEvent;
    @FXML
    private TextField lieuEvent;
    @FXML
    private DatePicker dateDebEvent;
    @FXML
    private DatePicker dateFin;
    @FXML
    private TextField typeEvent;

    private EvenementService evenementS;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        evenementS = new EvenementService();
    }    

    @FXML
    private void ajouterEvent(MouseEvent event) throws IOException {
        Evenement e = new Evenement();
        e.setNom(nomEvent.getText());
        e.setType(typeEvent.getText());
        e.setLieu(lieuEvent.getText());
        e.setDescription(descEvent.getText());
        e.setDateDeb(Date.valueOf(dateDebEvent.getValue()));
        e.setDateFin(Date.valueOf(dateFin.getValue()));
        evenementS.ajouter(e);
        Parent root = FXMLLoader.load(getClass().getResource("AfficherEvenements.fxml"));
        Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        Slowlife.stage.setScene(scene);
    }

    @FXML
    private void retourEvent(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AfficherEvenements.fxml"));
        Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        Slowlife.stage.setScene(scene);
    }
    
}
