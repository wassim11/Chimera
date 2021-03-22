/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.evenements;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import slowlifejava.tests.Slowlife;
import slowlifejava.entities.evenements.Activite;
import slowlifejava.entities.evenements.Evenement;
import slowlifejava.services.evenements.ActiviteService;
import slowlifejava.services.evenements.EvenementService;

/**
 * FXML Controller class
 *
 * @author Ahmed Ezzine
 */
public class AjouterActiviteController implements Initializable {

    @FXML
    private TextField nomActivite;
    @FXML
    private TextField typeActivite;
    @FXML
    private TextArea descActivite;
    @FXML
    private Spinner<Integer> dureeSpinner;
    @FXML
    private ComboBox<Evenement> evenement;
    
    private ActiviteService activiteS;
    private EvenementService evenementS;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        activiteS = new ActiviteService();
        evenementS=new EvenementService();
        evenement.getItems().addAll(evenementS.getAll());
        dureeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));
    }    

    @FXML
    private void retour(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AfficherActivites.fxml"));
        Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        Slowlife.stage.setScene(scene);

    }

    @FXML
    private void ajouterActivite(MouseEvent event) throws IOException {
        Activite a = new Activite();
        a.setIdEvent(evenement.getSelectionModel().getSelectedItem().getIdEvent());
        a.setNom(nomActivite.getText());
        a.setType(typeActivite.getText());
        a.setDescription(descActivite.getText());
        a.setDuree(dureeSpinner.getValue());
        activiteS.ajouter(a);
        Parent root = FXMLLoader.load(getClass().getResource("AfficherActivites.fxml"));
        Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        Slowlife.stage.setScene(scene);
    }
    
    
    
}
