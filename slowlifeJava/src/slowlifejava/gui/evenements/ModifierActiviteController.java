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
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
public class ModifierActiviteController implements Initializable {

    @FXML
    private TextArea descAct;
    @FXML
    private TextField nomAct;
    @FXML
    private TextField typeAct;
    @FXML
    private ComboBox<Evenement> idEventAct;
    @FXML
    private Spinner<Integer> dureeAct;

    private Activite a;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idEventAct.getItems().addAll(new EvenementService().getAll());
    }    

    @FXML
    private void modifierActivite(MouseEvent event) throws IOException{
        if (controleDeSaisie()) {
        a.setIdEvent(idEventAct.getSelectionModel().getSelectedItem().getIdEvent());
        a.setNom(nomAct.getText());
        a.setType(typeAct.getText());
        a.setDescription(descAct.getText());
        a.setDuree(dureeAct.getValue());
        System.out.println(a.getIdEvent());
        System.out.println(a.getNom());
        new ActiviteService().modifier(a);
        Parent root = FXMLLoader.load(getClass().getResource("AfficherActivites.fxml"));
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
    private void retour(MouseEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("AfficherActivites.fxml"));
        /*Scene scene=new Scene(root, Slowlife.stage.getScene().getWidth(), Slowlife.stage.getScene().getHeight());
        scene.getStylesheets().add("/slowlifejava/utils/buttons.css");
        Slowlife.stage.setScene(scene);*/
        ((BorderPane)Slowlife.stage.getScene().lookup("#bp")).setCenter(root);
        
    }
    
    public void setData(Activite ac) {
        a=ac;
        Evenement ev = idEventAct.getItems().stream().filter(e->e.getIdEvent()==a.getIdEvent()).findFirst().get();//recuperation de l'evenement associe à l'activite
        idEventAct.getSelectionModel().select(ev);//selection de l'evenement associe à l'activite
        nomAct.setText(a.getNom());
        typeAct.setText(a.getType());
        descAct.setText(a.getDescription());
        dureeAct.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(5, Integer.MAX_VALUE, a.getDuree()));//duree men activite a
    }
    
        private boolean controleDeSaisie() {
            
        if (nomAct.getText().length()<2)
            return false;
        if (typeAct.getText().length()<3)
            return false;
        if (descAct.getText().length()<5)
            return false;
        if (dureeAct.getValue()<5)
            return false;
        return true;
    }
    
}
