/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.users;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class SidebarController implements Initializable {

    @FXML
    private BorderPane bp;
    @FXML
    private Button regime_btn;
    @FXML
    private Button pub_btn;
    @FXML
    private Button event_btn;
    @FXML
    private Button centre_btn;
    @FXML
    private Button feed_btn;
    @FXML
    private HBox user_hb;
    @FXML
    private Button user_btn;
    @FXML
    private HBox statistic_hb;
    @FXML
    private Button statistic_btn;
    @FXML
    private AnchorPane ap;
    @FXML
    private ImageView user_image;
    @FXML
    private Button profile_btn;
    @FXML
    private Button mdp_btn;
    @FXML
    private Button logout;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SMRegime(ActionEvent event) {
    }

    @FXML
    private void SMPublication(ActionEvent event) {
    }

    @FXML
    private void SMEvenement(ActionEvent event) {
    }

    @FXML
    private void SMCentres(ActionEvent event) {
    }

    @FXML
    private void SMFeedback(ActionEvent event) {
    }

    @FXML
    private void gererUser(ActionEvent event) {
    }

    @FXML
    private void statistic(ActionEvent event) {
    }

    @FXML
    private void GererProfile(ActionEvent event) {
    }

    @FXML
    private void mdp(ActionEvent event) {
    }

    @FXML
    private void logout(ActionEvent event) {
    }
    
}
