/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.users;

import entities.users.Utilisateur;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import slowlifejava.services.users.UserService;
import slowlifejava.utils.users.NavigationEntreInterfaces;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class SidebarController implements Initializable {

    @FXML
    public BorderPane bp;
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
    private Button user_btn;
    @FXML
    private AnchorPane ap;
    @FXML
    private ImageView user_image;
    @FXML
    private Button profile_btn;
    @FXML
    private Button logout;
    UserService us = new UserService();
    @FXML
    private HBox user_hb;
    @FXML
    private Button statistic_btn;
    @FXML
    private HBox statistic_hb;
    @FXML
    private Button mdp_btn;
  
    //3 sous menu
    @FXML
    private ImageView image1;
    @FXML
    private Label label1;
    @FXML
    private ImageView image2;
    @FXML
    private Label label2;
    @FXML
    private ImageView image3;
    @FXML
    private Label label3;
    
    private Utilisateur user;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Utilisateur log = us.getUserlogged();
            InputStream stream;
            try {
                stream = new FileInputStream(log.getPhoto());
                Image image = new Image(stream);
                user_image.setImage(image);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }
            if (log.getType() == 1) {
                user_hb.setVisible(true);
                statistic_hb.setVisible(true);
            } else {
                user_hb.setVisible(false);
                statistic_hb.setVisible(false);

            }
        } catch (SQLException ex) {
            Logger.getLogger(SidebarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public BorderPane getBp() {
        return bp;
    }

    public void setBp(BorderPane bp) {
        this.bp = bp;
    }

    @FXML
    private void SMRegime(ActionEvent event) throws SQLException {
        /*
        1 admin
        2 client
        3 coach
        */
        user=us.getUserlogged();
        if(user.getType()==1){
        image1.setImage(new Image("slowlifejava/gui/RecetteSuivi/sousmenu/recipe.png"));
        label1.setText("Recette");
        image1.setOnMouseClicked(evenement->{
        LoadPage("/slowlifejava/gui/RecetteSuivi/Recette/RecetteBack");
        
        });
        }
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
    private void gererUser(ActionEvent event) throws IOException {
        LoadPage("/slowlifejava/gui/users/ListeUtilisateurs");
    }

    private void LoadPage(String page) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource(page + ".fxml"));
        } catch (IOException ex) {
            System.out.println("error de transition " + ex);
        }
        bp.setCenter(root);
    }

    @FXML
    private void GererProfile(ActionEvent event) throws SQLException {
        LoadPage("/slowlifejava/gui/users/ProfileAdmin");
    }

    @FXML
    private void logout(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "Login", "/slowlifejava/gui/users/Login.fxml");
        UserService us = new UserService();
        us.loggedOut();
    }

    @FXML
    private void statistic(ActionEvent event) {
        LoadPage("/slowlifejava/gui/users/Statistique");
    }

    @FXML
    private void mdp(ActionEvent event) {
        LoadPage("/slowlifejava/gui/users/ChangerMDP");
    }

}
