/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.users;

import entities.users.Utilisateur;
import services.users.UserService;
import utils.users.BCrypt;
import utils.users.NavigationEntreInterfaces;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class LoginController implements Initializable {

    @FXML
    private Button oublie_btn;
    @FXML
    private Button login_btn;
    @FXML
    private TextField login_txt;
    @FXML
    private PasswordField mdp_login_txt;
    @FXML
    private Button back_btn;
    @FXML
    private Button ggl_btn;
    @FXML
    private Button twitter_btn;
    @FXML
    private Button fb_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void OnLogin(ActionEvent event) throws IOException {

        try {
            if (validateInputs()) {
                UserService us = new UserService();
                String email = login_txt.getText();
                String mdp = mdp_login_txt.getText();
                Utilisateur u = us.searchByPseudoPassU(email, mdp);
                if (u != null && BCrypt.checkpw(mdp_login_txt.getText(), u.getMdp())) {
                    us.loggedIn(u);
                    NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
                    nav.navigate(event, "Sidebar", "/gui/users/Sidebar.fxml");
                    System.out.println(u.getPhoto());
                } else {
                    Alert alert2 = new Alert(Alert.AlertType.WARNING);
                    alert2.setTitle("Erreur");
                    alert2.setContentText("Veuillez vérifier votre email ou mot de passe");
                    alert2.setHeaderText(null);
                    alert2.show();
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean validateInputs() throws SQLException {

        if (login_txt.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez saisir votre email");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
        }
        if (mdp_login_txt.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez saisir votre mot de passe");
            alert1.setHeaderText("Controle de saisie");
            alert1.show();
            return false;
        }
        return true;

    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "Accueil", "/gui/users/AccueilPage.fxml");
    }

    @FXML
    private void OnOublie(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "Accueil", "/gui/users/RecupererMDP.fxml");

    }

    @FXML
    private void gglLogin(ActionEvent event) {
    }

    @FXML
    private void twitterLogin(ActionEvent event) {
    }

    @FXML
    private void fbLogin(ActionEvent event) {

    }
}
