/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.users;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonObject;
import com.restfb.types.User;
import entities.users.Client;
import entities.users.Utilisateur;
import slowlifejava.services.users.UserService;
import slowlifejava.utils.users.BCrypt;
import slowlifejava.utils.users.NavigationEntreInterfaces;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import slowlifejava.gui.SidebarController;
import slowlifejava.services.users.ClientService;

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
    private String appId = "3706717389396251";
    private String appSecret = "4cf728c99a0cc12918b90d296733bca1";
    private String redirectUrl = "http://localhost/";
    private String state = "2707";
    private String rectidrectUrlEncode = "http%3A%2F%2Flocalhost%2F";
    private String authentification = "http://www.facebook.com/v8.0/dialog/oauth?client_id=" + appId + "&redirect_uri=" + rectidrectUrlEncode + "&state" + state;
    ;
    private String graph = "https://graph.facebook.com/v8.0/oauth/access_token?client_id=" + appId + "&redirect_uri=" + rectidrectUrlEncode + "&client_secret=" + appSecret + "&code=";
    public String profileUrl;
    private String prof;
    UserService us = new UserService();

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
                    nav.navigate(event, "Sidebar", "/slowlifejava/gui/Sidebar.fxml");
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
        nav.navigate(event, "Accueil", "/slowlifejava/gui/users/AccueilPage.fxml");
    }

    @FXML
    private void OnOublie(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "RecupérerMDP", "/slowlifejava/gui/users/RecupererMDP.fxml");

    }

    @FXML
    private void gglLogin(ActionEvent event) {
    }

    @FXML
    private void twitterLogin(ActionEvent event) {
    }

    @FXML
    private void fbLogin(ActionEvent event) {
        WebView webView = new WebView();
        WebEngine eg = webView.getEngine();
        eg.load(authentification);
        Pane wView = new Pane();
        wView.getChildren().add(webView);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(wView));
        stage.show();
        eg.locationProperty().addListener((obs, oldlocation, newlocation) -> {
            if (newlocation != null && newlocation.startsWith("http://localhost")) {
                try {
                    int codeOffset = newlocation.indexOf("code");
                    String code = newlocation.substring(codeOffset + "code=".length());
                    String graph1 = graph + code;
                    System.out.println(graph1);
                    DefaultFacebookClient facebookClient = new DefaultFacebookClient(Version.LATEST);
                    FacebookClient.AccessToken accessToken = facebookClient.obtainUserAccessToken(appId, appSecret, "http://localhost/", code);
                    String access_token = accessToken.getAccessToken();
                    FacebookClient fbClient = new DefaultFacebookClient(access_token, Version.LATEST);
                    fbClient.createClientWithAccessToken(access_token);
                    JsonObject profile_pic = fbClient.fetchObject("me/picture", JsonObject.class, Parameter.with("redirect", "false"));
                    String fields = "name,first_name,last_name,birthday,email,gender";
                    User user = fbClient.fetchObject("me", User.class, Parameter.with("fields", fields));
                    System.out.println(user);
                    System.out.println(user.getName());
                    System.out.println(user.getFirstName());
                    System.out.println(user.getLastName());
                    System.out.println(user.getBirthday());
                    System.out.println(user.getGender());
                    int si = profile_pic.toString().indexOf("url\":\"");
                    int ei = profile_pic.toString().indexOf("\",\"");
                    profileUrl = profile_pic.toString().substring(si + 6, ei);
                    System.out.println(profileUrl);
                    prof = profileUrl;

                    //Ajout de l'utilisateur(client seulement car le coach doit enter son domaine
                    if ((us.getUserByEmail(user.getEmail())) == null) {
                        //régler le genre
                        String genre = "";
                        if (user.getGender().equals("female")) {
                            genre = "Femme";
                        } else if (user.getGender().equals("male")) {
                            genre = "Homme";
                        }
                        //regler le bday
                        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
                        java.util.Date date;
                        try {
                            date = sdf1.parse(user.getBirthday());
                            java.sql.Date bday = new java.sql.Date(date.getTime());
                            System.out.println(bday);
                            System.out.println(user.getBirthday().getClass());
                            Client clt = new Client(user.getLastName(), user.getFirstName(), user.getEmail(), "C:\\Users\\zaefdfyjhlk\\Desktop\\slowlifeJava\\src\\slowlifejava\\gui\\users\\images\\fbUser.PNG", 2, genre, bday);
                            ClientService cs = new ClientService();
                            cs.ajouterClient(clt);
                            us.loggedIn(us.getUserByEmail(user.getEmail()));
                        } catch (ParseException ex) {
                            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        //fermer la page fb
                        stage.close();
                        //envoyer la pdp et afficher le side bar
//                        try {
//                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/slowlifejava/gui/users/Sidebar.fxml"));
//                            Parent root = (Parent) loader.load();
//                            SidebarController sb = loader.getController();
//                            sb.getPdp(profileUrl);
//                            Stage stage2 = new Stage();
//                            stage2.setScene(new Scene(root));
//                            stage2.show();
//                        } catch (IOException e) {
//                            System.out.println(e.getMessage());
//                        }
                        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
                        nav.navigate(event, "SideBar", "/slowlifejava/gui/Sidebar.fxml");
                    } else {
                        us.loggedIn(us.getUserByEmail(user.getEmail()));
                        stage.close();
//                        try {
//                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/slowlifejava/gui/Sidebar.fxml"));
//                            Parent root = (Parent) loader.load();
//                            SidebarController sb = loader.getController();
//                            sb.getPdp(profileUrl);
//                            Stage stage2 = new Stage();
//                            stage2.setScene(new Scene(root));
//                            stage2.show();
//                        } catch (IOException e) {
//                            System.out.println(e.getMessage());
//                        }
                        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
                        nav.navigate(event, "SideBar", "/slowlifejava/gui/Sidebar.fxml");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);

                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        );
    }

    private void sendPdp() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/slowlifejava/gui/Sidebar.fxml"));
            Parent root = (Parent) loader.load();
            SidebarController sb = loader.getController();
            System.out.println("controller ");
            sb.getPdp(prof);
            System.out.println("image passé");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
