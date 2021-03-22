/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.users;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class ListeUtilisateursController implements Initializable {

    @FXML
    private TableView<?> list_tbl;
    @FXML
    private TableColumn<?, ?> colImage;
    @FXML
    private TableColumn<?, ?> nom_col;
    @FXML
    private TableColumn<?, ?> prenom_col;
    @FXML
    private TableColumn<?, ?> email_col;
    @FXML
    private TableColumn<?, ?> bday_col;
    @FXML
    private TableColumn<?, ?> genre_col;
    @FXML
    private TableColumn<?, ?> domain_col;
    @FXML
    private ImageView rech_btn;
    @FXML
    private JFXRadioButton tous_rb;
    @FXML
    private ToggleGroup filtre_tg;
    @FXML
    private JFXRadioButton coachs_rb;
    @FXML
    private JFXRadioButton simple_rb;
    @FXML
    private JFXTextField rech_txt;
    @FXML
    private JFXComboBox<?> domain_cmb;
    @FXML
    private Button delete_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void OnSelect(MouseEvent event) {
    }

    @FXML
    private void Rechercher(MouseEvent event) {
    }

    @FXML
    private void AfficherTous(ActionEvent event) {
    }

    @FXML
    private void AfficherCoach(ActionEvent event) {
    }

    @FXML
    private void AfficherClient(ActionEvent event) {
    }

    @FXML
    private void RecherchDom(ActionEvent event) {
    }

    @FXML
    private void OnSupp(ActionEvent event) {
    }
    
}
