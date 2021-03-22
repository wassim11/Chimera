/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.users;

import slowlifejava.gui.users.ConfimerCompteController;
import entities.users.Client;
import entities.users.Coach;
import services.users.ClientService;
import services.users.CoachService;
import utils.users.Mailing;
import utils.users.NavigationEntreInterfaces;
import static utils.users.PatternEmail.validate;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author zaefdfyjhlk
 */
public class InscriptionController implements Initializable {
    @FXML
    private TextField nom_txt;
    @FXML
    private PasswordField mdp_txt;
    @FXML
    private TextField domaine_txt;
    @FXML
    private PasswordField confirm_txt;
    @FXML
    private TextField email_txt;
    @FXML
    private TextField prenom_txt;
    @FXML
    private DatePicker bday_dtp;
    @FXML
    private ImageView import_btn;
    @FXML
    private ImageView image_vi;
    @FXML
    private RadioButton coach_rb;
    @FXML
    private RadioButton membre_rb;
    @FXML
    private RadioButton homme_rb;
    @FXML
    private RadioButton femme_rb;
    @FXML
    private ToggleGroup type_tg;
    @FXML
    private ToggleGroup genre_tg;
    private FileChooser filechooser;
    private File file;
    private String filePath;
    @FXML
    private Button confirm_btn;
    @FXML
    private Button back_btn;
    private Label domain_lbl;
    private int code=1000;
    private int type;
    private String genre = "";
    private Date bday;
    @FXML
    private ImageView domain_ph;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        domaine_txt.setVisible(false);
        domain_ph.setVisible(false);
    }

    private boolean validateInputs() {
        if (nom_txt.getText().isEmpty()){
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            alert1.setTitle("Erreur");
            alert1.setContentText("Veuillez saisir votre nom");
            alert1.setHeaderText(null);
            alert1.show();
            return false;
        } else if ((prenom_txt.getText().isEmpty())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez saisir votre prenom");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
            } else if ((email_txt.getText().isEmpty())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez saisir votre email");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
            } else if ((confirm_txt.getText().isEmpty())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez confirmer votre mot de passe");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
            } else if ((mdp_txt.getText().isEmpty())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez saisir votre mot de passe");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }else if (!(mdp_txt.getText().equals(confirm_txt.getText()))) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez vérifier votre mot de passe");
            alert2.setHeaderText(null);
            alert2.show();
            return false;

        } else if (mdp_txt.getText().length() > 15) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Mot de passe de doit pas dépasser les 15 caractères");
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
        } else if (!(membre_rb.isSelected()) && !(coach_rb.isSelected())) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez Choisir un type");
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
        } else if ((coach_rb.isSelected()) && domaine_txt.getText().isEmpty()) {
            Alert alert2 = new Alert(Alert.AlertType.WARNING);
            alert2.setTitle("Erreur");
            alert2.setContentText("Veuillez entrer votre domaine");
            alert2.setHeaderText(null);
            alert2.show();
            return false;
        }

        return true;
    }

    @FXML
    private void EnvoyerCodeConfirmation(ActionEvent event) throws IOException {
        if (validateInputs()) {
            Time sentTime = new Time(System.currentTimeMillis());
            bday = (Date.valueOf(bday_dtp.getValue()));
            if (homme_rb.isSelected()) {
                genre = "Homme";
            } else if (femme_rb.isSelected()) {
                genre = "Femme";
            }
            String toEmail = email_txt.getText();
            String subject = "Confirmation Code";
            Random random = new Random();
            code =code+ random.nextInt(8999);
            String body = "Bonjour Mme/Mr " + nom_txt.getText() + " vous avez vous inscrire dans notre Application SLOWLIFE, merci de confirmer votre inscription."
                    + " \n Voici votre code de confirmation: " + code;
            Mailing m = new Mailing();
            m.sendEmail(toEmail, subject, body);
            if (validateInputs()) {
                if (membre_rb.isSelected()) {
                    type = 2;
                } else if (coach_rb.isSelected()) {
                    type = 3;
                }
                sendCode();
            }
        }
    }
    private void sendCode() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/users/ConfimerCompte.fxml"));
            Parent root = (Parent) loader.load();
            ConfimerCompteController cc = loader.getController();
            cc.getCode(code);
            cc.getInformation(nom_txt.getText(), prenom_txt.getText(), email_txt.getText(), mdp_txt.getText(), filePath,domaine_txt.getText(), type, bday, genre);
            System.out.println(domaine_txt.getText());
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
        NavigationEntreInterfaces nav = new NavigationEntreInterfaces();
        nav.navigate(event, "confirmer inscription", "/gui/users/AccueilPage.fxml");
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
    private void HideDomaine(ActionEvent event) {
        domaine_txt.setVisible(false);
        domain_ph.setVisible(false);
    }

    @FXML
    private void DisplayDomaine(ActionEvent event) {
        domaine_txt.setVisible(true);
        domain_ph.setVisible(true);
    }


}
