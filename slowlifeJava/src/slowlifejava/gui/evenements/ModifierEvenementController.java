/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.evenements;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import slowlifejava.tests.Slowlife;
import slowlifejava.entities.evenements.Activite;
import slowlifejava.entities.evenements.Evenement;
import slowlifejava.services.evenements.EvenementService;

/**
 * FXML Controller class
 *
 * @author Ahmed Ezzine
 */
public class ModifierEvenementController implements Initializable {

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
    
    private Evenement e;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void modifierEvent(MouseEvent event) throws IOException {
        if (controleDeSaisie()) {
        e.setNom(nomEvent.getText());
        e.setType(typeEvent.getText());
        e.setLieu(lieuEvent.getText());
        e.setDescription(descEvent.getText());
        e.setDateDeb(Date.valueOf(dateDebEvent.getValue()));
        e.setDateFin(Date.valueOf(dateFin.getValue()));
        System.out.println(e.getIdEvent());
        System.out.println(e.getNom());
        new EvenementService().modifier(e);
        Parent root = FXMLLoader.load(getClass().getResource("AfficherEvenements.fxml"));
       /* Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
        Slowlife.stage.setScene(scene);*/
       ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
          Alert a = new Alert(Alert.AlertType.INFORMATION);
             a.setAlertType(Alert.AlertType.INFORMATION);
             a.setHeaderText("Valider");
             a.setContentText("les informations ont été modifiées avec succès");
             a.show();
        }else{
           Alert a = new Alert(Alert.AlertType.ERROR);
             a.setAlertType(Alert.AlertType.ERROR);
             a.setHeaderText("erreur!");
             a.setContentText("verifier les champs!");
             a.show();
        }
    }

    @FXML
    private void retourEvent(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AfficherEvenements.fxml"));
        /*Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
         scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
        Slowlife.stage.setScene(scene);*/
        ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
    }
    
    public void setData(Evenement ev) {
        e=ev;
        nomEvent.setText(e.getNom());
        descEvent.setText(e.getDescription());
        lieuEvent.setText(e.getLieu());
        dateDebEvent.setValue(e.getDateDeb().toLocalDate());
        dateFin.setValue(e.getDateFin().toLocalDate());
        typeEvent.setText(e.getType());
        System.out.println(e.getIdEvent());
    }

    void setData(Activite a) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
        private boolean controleDeSaisie() {
        if (nomEvent.getText().length()<2)
            return false;
        if (typeEvent.getText().length()<3)
            return false;
        if (lieuEvent.getText().length()<5)
            return false;
        if (descEvent.getText().length()<5)
            return false;
        if (dateDebEvent.getValue().compareTo(LocalDate.now())<=0)
            return false;
        if(dateDebEvent.getValue().compareTo(dateFin.getValue())>0)
            return false;
        return true;
    }
    
}
