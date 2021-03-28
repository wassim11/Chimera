/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui;

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
    private Label label3;     private ImageView sousmenu1;
    private ImageView sousmenu2;
    private ImageView sousmenu3;
    private ImageView sousmenu4;
    private ImageView sousmenu5;
    
    private  Utilisateur log;
    private String pdp_fb;
    private String code;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            log = us.getUserlogged();
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
        if(log.getType()==1){
        //image1.setImage(new Image("slowlifejava/gui/RecetteSuivi/sousmenu/recipe.png"));
//        label1.setText("Recette");
//        image1.setOnMouseClicked(evenement->{
        LoadPage("/slowlifejava/gui/RecetteSuivi/Recette/RecetteBack");
        
        
        }
    }

    @FXML
    private void SMPublication(ActionEvent event) {
    }

    @FXML
    private void SMEvenement(ActionEvent event) {
    }

    @FXML
    private void SMCentres(ActionEvent event) throws FileNotFoundException {
        int i=1; //a changer
        InputStream stream;
              if (i==1)
              {stream = new FileInputStream("src/gui/centre/imageSideBar/centre.png");
                Image image = new Image(stream);
                 sousmenu1.setImage(image);
                 stream = new FileInputStream("src/gui/centre/image/serviceIcon.png");
                image = new Image(stream);
                 sousmenu2.setImage(image);
                 stream = new FileInputStream("src/gui/centre/image/RdvIcon.png");
                image = new Image(stream);
                 sousmenu3.setImage(image);
                 stream = new FileInputStream("src/gui/centre/image/staticon.png");
                image = new Image(stream);
                 sousmenu4.setImage(image);
//                 stream = new FileInputStream("src/gui/centre/image/c.png");
//                image = new Image(stream);
//                 sousmenu5.setImage(image);
                 sousmenu1.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/FXMLDocument");});
                 sousmenu2.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/gestionService");});
                 sousmenu3.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/AfficherRendezvous");});
                 sousmenu4.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/StatCentreService");});
                 
              }
              else if (i==2)
              {stream = new FileInputStream("src/gui/centre/imageSideBar/centre.png");
                Image image = new Image(stream);
                 sousmenu1.setImage(image);
                 
                 stream = new FileInputStream("src/gui/centre/image/RdvIcon.png");
                image = new Image(stream);
                 sousmenu2.setImage(image);
                 stream = new FileInputStream("src/gui/centre/image/staticon.png");
                image = new Image(stream);
                 sousmenu3.setImage(image);
//                 stream = new FileInputStream("src/gui/centre/image/c.png");
//                image = new Image(stream);
//                 sousmenu4.setImage(image);
                 sousmenu1.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/FrontC");});
                 sousmenu2.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/GestionRendezvous");});
                 sousmenu3.setOnMouseClicked(eventt->{LoadPage("/slowlifejava/gui/centre/StatPrixService");});
            
              }
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
    public void getPdp(String passedpdp) {
        this.pdp_fb = passedpdp;
    }
    public void getText(String passedCode){
        this.code=passedCode;
    }

}
