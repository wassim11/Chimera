/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.users;

import utils.users.NavigationEntreInterfaces;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class AccueilPageController implements Initializable {

    @FXML
    private Button log_btn;
    @FXML
    private Button cmpt_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    

    @FXML
    private void pageInscription(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav= new NavigationEntreInterfaces();
            nav.navigate(event, "Inscription", "/gui/users/Inscription.fxml");
    }

    @FXML
    private void pageConnexion(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav= new NavigationEntreInterfaces();
            nav.navigate(event, "Login", "/gui/users/Login.fxml");
    }
    
}
