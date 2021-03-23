/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.users;

import entities.users.Client;
import entities.users.Coach;
import slowlifejava.services.users.ClientService;
import slowlifejava.services.users.CoachService;
import slowlifejava.utils.users.NavigationEntreInterfaces;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class ConfimerCompteController implements Initializable {

    @FXML
    private TextField codet_txt;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String genre;
    private String image;
    private String domaine;
    private int type;
    private Date bday;
    private int code;
    @FXML
    private Button confirmer_txt;
    @FXML
    private Button back_btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        getInformation(prenom, prenom, email, mdp, image, domaine, type, bday, genre);
        getCode(code);
        // TODO
    }

    @FXML
    private void confirmerCode(ActionEvent event) throws IOException {
        if (codet_txt.getText().equals(String.valueOf(code))) {
            System.out.println("Code Confirmé");
            inscri();
            NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
            nav.navigate(event, "confirmer inscription", "/gui/users/Login.fxml");

        } else {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur Code");
            alert1.setContentText("Veuillez vérifier le code");
            alert1.setHeaderText(null);
            alert1.show();
        }
    }

    public void getInformation(String passedNom, String passedPrenom, String passedEmail, String passedMdp, String passedImage, String passedDomaine, int passedType, Date passedBday, String passedGenre) {
        this.nom = passedNom;
        this.prenom = passedPrenom;
        this.email = passedEmail;
        this.mdp = passedMdp;
        this.image = passedImage;
        this.domaine = passedDomaine;
        this.genre = passedGenre;
        this.bday = passedBday;
        this.type = passedType;
    }
    public void getCode(int passedCode) {
        this.code = passedCode;
    }
    public void inscri() {
        if (type == 2) {
            Client clt = new Client(nom, prenom, email, mdp, image, type, genre, bday);
            ClientService cs = new ClientService();
            cs.ajouterClient(clt);
        } else if (type == 3) {
            Coach ch = new Coach(domaine, nom, prenom, email, mdp, image, type, genre, bday);
            CoachService chs = new CoachService();
            chs.ajouterCoach(ch);
        }
    }


    @FXML
    private void back(ActionEvent event) {
    }

}
