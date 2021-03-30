/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.RecetteSuivi.Recette;

import slowlifejava.services.RecetteSuivi.ServiceIngredientRecette;
//import Utils.RecetteSuivi.Mailing;
//import Utils.RecetteSuivi.SMS;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import slowlifejava.entities.RecetteSuivi.IngredientRecette;
import slowlifejava.entities.RecetteSuivi.Recette;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Yassine
 */
public class EnvoyerRecetteController implements Initializable {

    @FXML
    private JFXTextField numtel;
    @FXML
    private JFXRadioButton rbMail;
    @FXML
    private JFXRadioButton rbSms;
    @FXML
    private JFXButton btnenvoyer;
    public static int count;
    private static EnvoyerRecetteController instance;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        count=1;
        numtel.setVisible(false);// TODO
    }    

     public EnvoyerRecetteController() {
        instance = this;
    }

    public static EnvoyerRecetteController getInstance() {
        return instance;
    }
    @FXML
    private void mail(ActionEvent event) {
        rbSms.setSelected(false);
        numtel.setVisible(false);
    }

    @FXML
    private void sms(ActionEvent event) {
        rbMail.setSelected(false);
        numtel.setVisible(true);
    }

    @FXML
    private void envoyer() throws SQLException {
     if(rbSms.isSelected() && !numtel.getText().isEmpty())
     {
     count=0;
 //    SMS sms = new SMS();
     ServiceIngredientRecette SIR = new ServiceIngredientRecette();
     List<IngredientRecette> LIR=SIR.readAll(new Recette( RecetteController.id));      
     String msg ="*******\nBonjour votre recette '"+LIR.get(0).getRct().getNomRecette()+"' est composé de "+LIR.size()+" Ingredients:\n";
     for(IngredientRecette IR : LIR)
     {
         msg+=IR.getQuantite()+IR.getIng().getUnite().split(" ")[1]+" "+IR.getIng().getNom()+"\n";
     }
   //  sms.sendSms(Integer.parseInt(numtel.getText()), msg);
     closePage();
     }
     if(rbMail.isSelected())
     {
      count=0;
      //Mailing.sendEmail(toEmail, subject, body);
      closePage();
      }
      else
      {
            
      }
    }
    public void closePage(){
        Stage stage = (Stage) btnenvoyer.getScene().getWindow();
        stage.close();
    }
    
}
