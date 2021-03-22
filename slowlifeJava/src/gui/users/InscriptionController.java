/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.users;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class InscriptionController implements Initializable {

    @FXML
    private Button confirm_btn;
    @FXML
    private ImageView import_btn;
    @FXML
    private Button back_btn;
    @FXML
    private ImageView image_vi;
    @FXML
    private JFXTextField nom_txt;
    @FXML
    private JFXTextField prenom_txt;
    @FXML
    private JFXDatePicker bday_dtp;
    @FXML
    private JFXRadioButton homme_rb;
    @FXML
    private ToggleGroup genre_tg;
    @FXML
    private JFXRadioButton femme_rb;
    @FXML
    private JFXTextField email_txt;
    @FXML
    private JFXTextField domaine_txt;
    @FXML
    private JFXPasswordField mdp_txt;
    @FXML
    private JFXPasswordField confirm_txt;
    @FXML
    private JFXRadioButton coach_rb;
    @FXML
    private ToggleGroup type_tg;
    @FXML
    private JFXRadioButton membre_rb;
    @FXML
    private ImageView domain_ph;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void EnvoyerCodeConfirmation(ActionEvent event) {
    }

    @FXML
    private void chooseImage(MouseEvent event) {
    }

    @FXML
    private void back(ActionEvent event) {
    }

    @FXML
    private void DisplayDomaine(ActionEvent event) {
    }

    @FXML
    private void HideDomaine(ActionEvent event) {
    }
    
}
