/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.users;

import com.jfoenix.controls.JFXTextField;
import entities.users.Admin;
import entities.users.Client;
import entities.users.Coach;
import entities.users.Utilisateur;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import slowlifejava.services.users.AdminService;
import slowlifejava.services.users.ClientService;
import slowlifejava.services.users.CoachService;
import slowlifejava.services.users.UserService;
import slowlifejava.utils.SlowlifeDB;
import slowlifejava.utils.users.Mailing;

import slowlifejava.utils.users.NavigationEntreInterfaces;
import static slowlifejava.utils.users.PatternEmail.validate;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class ProfileAdminController implements Initializable {

    @FXML
    private Button modif_btn;
    @FXML
    private TextField nom_txt;
    @FXML
    private TextField prenom_txt;
    @FXML
    private TextField email_txt;
    @FXML
    private DatePicker bday_dtp;
    @FXML
    private ImageView image_vi;
    @FXML
    private RadioButton homme_rb;
    @FXML
    private RadioButton femme_rb;
    @FXML
    private ImageView import_btn;
    @FXML
    private Button supp_btn;

    private BorderPane bp;
    UserService us = new UserService();
    private FileChooser filechooser;
    private File file;
    private String filePath;
    @FXML
    private ImageView admin_iv;
    @FXML
    private Label admin_lbl;
    @FXML
    private ToggleGroup genre_tg1;
    @FXML
    private JFXTextField domaine_txt;
    @FXML
    private ImageView domain_ph;
    @FXML
    private ToggleGroup genre_tg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        try {
            Utilisateur log;
            log = us.getUserlogged();
            String dom = "";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(log.getBday().toString(), formatter);
            switch (log.getType()) {
                case 1:
                    admin_iv.setVisible(true);
                    admin_lbl.setVisible(true);
                    domain_ph.setVisible(false);
                    domaine_txt.setVisible(false);
                    break;
                case 2:
                    admin_iv.setVisible(false);
                    admin_lbl.setVisible(false);
                    domain_ph.setVisible(false);
                    domaine_txt.setVisible(false);
                    break;
                case 3:
                    admin_iv.setVisible(false);
                    admin_lbl.setVisible(false);
                    domain_ph.setVisible(true);
                    domaine_txt.setVisible(true);
                    break;
                default:
                    break;
            }
            nom_txt.setText(log.getNom());
            prenom_txt.setText(log.getPrenom());
            email_txt.setText(log.getEmail());
            bday_dtp.setValue(localDate);
            if ((log.getGenre()).equals("Homme")) {
                homme_rb.setSelected(true);

            } else if ((log.getGenre()).equals("Femme")) {
                femme_rb.setSelected(true);
                if (homme_rb.isSelected()) {
                    femme_rb.setSelected(false);
                } else if (femme_rb.isSelected()) {
                    homme_rb.setSelected(false);
                }
            }

            if (log.getType() == 3) {
                String req2 = "SELECT domaine FROM utilisateur WHERE id=" + log.getId();
                Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
                ResultSet rs = st.executeQuery(req2);
                while (rs.next()) {
                    dom = rs.getString("domaine");
                }
                domaine_txt.setText(dom);
            }
            InputStream stream;
            try {
                stream = new FileInputStream(log.getPhoto());
                Image image = new Image(stream);
                image_vi.setImage(image);
            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    private void OnModif(ActionEvent event) throws SQLException {
        Utilisateur user = us.getUserlogged();
        int id_log = user.getId();
        String genre = "";
        LocalDate bday = bday_dtp.getValue();
        if (homme_rb.isSelected()) {
            genre = "Homme";
            femme_rb.setSelected(false);
        } else if (femme_rb.isSelected()) {
            genre = "Femme";
            homme_rb.setSelected(false);
        }
        //verifier userlogged. getEmail if == email.txt rien faire else envoyer mail au  nouvel et ancien email
        if (!(email_txt.getText().equals(user.getEmail()))) {
            Mailing m = new Mailing();
            String subject = "Changement d'Email";
            String body1 = "Bonjour Mme/mr " + user.getNom() + "\n"
                    + "Vous avez changé votre adresse email, vous receverez donc un mail dans la nouvelle adresse. \n"
                    + "Cordialement";
            String body2 = "Bonjour Mme/mr " + user.getNom() + "\n"
                    + "Vous avez changé votre adresse email, et ceci est la nouvelle adresse. \n"
                    + "Cordialement";
        }
        if (validateInputs()) {

            switch (user.getType()) {
                case 1:
                    Admin ad = new Admin(id_log, nom_txt.getText(), prenom_txt.getText(), email_txt.getText(), filePath, 2, genre, (Date.valueOf(bday)));
                    AdminService as = new AdminService();
                    as.updateAdmin(ad);
                    break;
                case 2:
                    Client clt = new Client(id_log, nom_txt.getText(), prenom_txt.getText(), email_txt.getText(), filePath, 2, genre, (Date.valueOf(bday)));
                    ClientService cs = new ClientService();
                    cs.updateClient(clt);
                    break;
                case 3:
                    Coach ch = new Coach(domaine_txt.getText(), id_log, nom_txt.getText(), prenom_txt.getText(), email_txt.getText(), filePath, 2, genre, (Date.valueOf(bday)));
                    CoachService chs = new CoachService();
                    chs.updateCoach(ch);
                    break;
                default:
                    break;
            }

        }
    }

    @FXML
    private void chooseImage(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        filechooser = new FileChooser();
        filechooser.setTitle("Open Image");
        this.file = filechooser.showOpenDialog(stage);
        String userDirectoryString = System.getProperty("user.home") + "\\Images";
        File userDirectory = new File(userDirectoryString);
        if (!(userDirectory.canRead())) {
            userDirectory = new File("c:/");
        }
        filechooser.setInitialDirectory(userDirectory);
        this.file = filechooser.showOpenDialog(stage);
        try {
            BufferedImage bi = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bi, null);
            image_vi.setImage(image);
            filePath = file.getAbsolutePath();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }



    @FXML
    private void supprimerCompte(ActionEvent event) throws SQLException, IOException {
        Utilisateur user = us.getUserlogged();
        int id_log = user.getId();
        String toEmail = user.getEmail();
        String subject = "Désactivation Compte";
        String body = "Bonjour Mmr/Mr " + user.getNom() + "\n"
                + "Vous avez choisi de désactiver votre compte.\n"
                + "Si un jour vous changeriez d'avis nous serons très heureux de vous avoir parmi nous encore une fois \n"
                + "A très bientot.";
        Mailing m = new Mailing();
        m.sendEmail(toEmail, subject, body);
        us.supprimerUser(user.getId());
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "Login", "/gui/users/Login.fxml");
        UserService us = new UserService();
        us.loggedOut();
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

    private boolean validateInputs() {
        if (nom_txt.getText().isEmpty()) {
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez saisir votre nom");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if (prenom_txt.getText().isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez saisir votre prenom");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if (email_txt.getText().isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez saisir votre email");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if (!(validate(email_txt.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez vérifier votre email");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        } else if (bday_dtp.getValue().isAfter(LocalDate.now())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez vérifier votre date de naissance");
            alert2.setHeaderText(null);
            alert2.show();
            return false;

        } else if (!(homme_rb.isSelected()) && !(femme_rb.isSelected())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez entrer votre genre");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }

        return true;
    }

    @FXML
    private void homme(ActionEvent event) {

        femme_rb.setSelected(false);

    }

    @FXML
    private void femme(ActionEvent event) {
        homme_rb.setSelected(false);
    }

}
