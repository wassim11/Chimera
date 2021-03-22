/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.users;

import com.jfoenix.controls.JFXComboBox;
import entities.users.Client;
import entities.users.Coach;
import entities.users.Utilisateur;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import services.users.ClientService;
import services.users.CoachService;
import services.users.UserService;
import utils.users.Mailing;
import utils.users.NavigationEntreInterfaces;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class ListeUtilisateursController implements Initializable {

    @FXML
    private TableView<Utilisateur> list_tbl;
    @FXML
    private TableColumn<Utilisateur, String> nom_col;
    @FXML
    private TableColumn<Utilisateur, String> prenom_col;
    @FXML
    private TableColumn<Utilisateur, String> email_col;
    @FXML
    private TableColumn<Utilisateur, LocalDate> bday_col;
    @FXML
    private TableColumn<Utilisateur, String> genre_col;
    UserService us = new UserService();
    CoachService chs = new CoachService();
    ClientService cs = new ClientService();
    @FXML
    private RadioButton tous_rb;
    @FXML
    private ToggleGroup filtre_tg;
    @FXML
    private RadioButton simple_rb;
    @FXML
    private RadioButton coachs_rb;
    @FXML
    private TableColumn<?, ?> domain_col;
    @FXML
    private TextField rech_txt;
    @FXML
    private ImageView rech_btn;
    @FXML
    private Button delete_btn;
    @FXML
    private TableColumn<Utilisateur, String> colImage;
    @FXML
    private JFXComboBox<String> domain_cmb;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Afficher();
        domain_cmb.setVisible(false);
    

        // TODO
    }

    @FXML
    private void OnSelect(MouseEvent event) {

    }

    @FXML
    private void OnSupp(ActionEvent event) throws IOException {
        Utilisateur user = list_tbl.getSelectionModel().getSelectedItems().get(0);
        us.supprimerUser(user.getId());
        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("ListUtilisateurs.fxml"));
            Scene sceneview = new Scene(tableview);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(sceneview);
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String toEmail = user.getEmail();
        String subject = "Suppression de compte";
        String body = "Bonjour Mme/mr " + user.getNom() + "\n"
                + "Nous sommes désolés de vous informer que votre compte est supprimé par decision de notre administration à cause de contenu inapproprié. \n"
                + "Cordialement";
        Mailing m = new Mailing();
        m.sendEmail(toEmail, subject, body);

        //Afficher();
    }

    public void Afficher() {
        domain_col.setVisible(false);

        ObservableList<Utilisateur> userList = FXCollections.observableArrayList();
        for (Utilisateur u : us.displayUsers()) {
            userList.add(u);
        }
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        bday_col.setCellValueFactory(new PropertyValueFactory<>("bday"));
        genre_col.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colImage.setCellValueFactory(new PropertyValueFactory<>("photo"));
        Callback<TableColumn<Utilisateur, String>, TableCell<Utilisateur, String>> cellImage
                = new Callback<TableColumn<Utilisateur, String>, TableCell<Utilisateur, String>>() {
            @Override
            public TableCell call(final TableColumn<Utilisateur, String> param) {
                final TableCell<Utilisateur, String> cell = new TableCell<Utilisateur, String>() {
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            if (item.isEmpty()) {
                                System.out.println("empty item");
                            } else {
                                ImageView imagev = new ImageView();

                                InputStream stream;
                                try {
                                    stream = new FileInputStream(item);
                                    Image image = new Image(stream);
                                    imagev.setImage(image);
                                    imagev.setFitHeight(75);
                                    imagev.setFitWidth(75);
                                    setGraphic(imagev);
                                    setText(null);
                                } catch (FileNotFoundException ex) {
                                    System.out.println(ex.getMessage());
                                }
                            }
                        }
                    }
                };
                return cell;
            }
        };

        colImage.setCellFactory(cellImage);
        list_tbl.setItems(userList);

    }

    @FXML
    private void AfficherTous(ActionEvent event) {
        Afficher();
        domain_cmb.setVisible(false);

    }

    @FXML
    private void AfficherClient(ActionEvent event) {
        domain_col.setVisible(false);
        domain_cmb.setVisible(false);

        ObservableList<Utilisateur> userList = FXCollections.observableArrayList();
        for (Client clt : cs.displayClients()) {
            userList.add(clt);
        }
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        bday_col.setCellValueFactory(new PropertyValueFactory<>("bday"));
        genre_col.setCellValueFactory(new PropertyValueFactory<>("genre"));
        //photo_col.setCellValueFactory(new PropertyValueFactory<>("photo"));
        list_tbl.setItems(userList);
    }

    @FXML
    private void AfficherCoach(ActionEvent event) throws SQLException {
        domain_col.setVisible(true);
        ObservableList<Utilisateur> userList = FXCollections.observableArrayList();
        for (Coach ch : chs.displayCoachs()) {
            userList.add(ch);
        }
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        bday_col.setCellValueFactory(new PropertyValueFactory<>("bday"));
        genre_col.setCellValueFactory(new PropertyValueFactory<>("genre"));
        //photo_col.setCellValueFactory(new PropertyValueFactory<>("photo"));
        domain_col.setCellValueFactory(new PropertyValueFactory<>("Domaine"));
        list_tbl.setItems(userList);
        domain_cmb.setVisible(true);
        remplirCmb();
    }
    

    @FXML
    private void Rechercher(MouseEvent event) {
        ObservableList<Utilisateur> userList = FXCollections.observableArrayList();
        for (Utilisateur u : us.RechercheUsers(rech_txt.getText())) {
            userList.add(u);
        }
        //colImage.setCellValueFactory(new PropertyValueFactory<>("photo"));
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        bday_col.setCellValueFactory(new PropertyValueFactory<>("bday"));
        genre_col.setCellValueFactory(new PropertyValueFactory<>("genre"));
        //photo_col.setCellValueFactory(new PropertyValueFactory<>("photo"));
        list_tbl.setItems(userList);
    }

    private void OnSupp(MouseEvent event) {
        Utilisateur user = list_tbl.getSelectionModel().getSelectedItems().get(0);
        us.supprimerUser(user.getId());
        try {
            javafx.scene.Parent tableview = FXMLLoader.load(getClass().getResource("ListUtilisateurs.fxml"));
            Scene sceneview = new Scene(tableview);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(sceneview);
            window.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        String toEmail = user.getEmail();
        String subject = "Suppression de compte";
        String body = "Bonjour Mme/mr " + user.getNom() + "\n"
                + "Nous sommes désolés de vous informer que votre compte est supprimé par decision de notre administration à cause de contenu inapproprié. \n"
                + "Cordialement";
        Mailing m = new Mailing();
        m.sendEmail(toEmail, subject, body);

    }

    public void remplirCmb() throws SQLException {
        ObservableList<String> list = us.coachByDomain();
        domain_cmb.setItems(list);
    

    }
    public void rechercheDomain(){
        String domaineSelected=domain_cmb.getValue();
         ObservableList<Utilisateur> userList = FXCollections.observableArrayList();
        for (Coach u : us.RechercheByDomain(domaineSelected)) {
            userList.add(u);
        }
        //colImage.setCellValueFactory(new PropertyValueFactory<>("photo"));
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email_col.setCellValueFactory(new PropertyValueFactory<>("email"));
        bday_col.setCellValueFactory(new PropertyValueFactory<>("bday"));
        genre_col.setCellValueFactory(new PropertyValueFactory<>("genre"));
        //photo_col.setCellValueFactory(new PropertyValueFactory<>("photo"));
        list_tbl.setItems(userList);
    }

    @FXML
    private void RecherchDom(ActionEvent event) {
        rechercheDomain();
    }

}
