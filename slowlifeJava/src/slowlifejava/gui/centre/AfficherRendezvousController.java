/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.centre;

//import javax.mail.*;
//import javax.mail.internet.*;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import slowlifejava.entities.centre.Rendezvous;
import slowlifejava.services.centre.ServiceRendezvous;


/**
 * FXML Controller class
 *
 * @author amira
 */
public class AfficherRendezvousController implements Initializable {

    private TableColumn<Rendezvous, Integer> colidrdv;
    private TableColumn<Rendezvous, Integer> colidutrdv;
    private TableColumn<Rendezvous, Integer> colidcentrerdv;
    private TableColumn<Rendezvous, Integer> colidservicerdv;
    @FXML
    private TableColumn<Rendezvous, Date> coldaterdv;
    @FXML
    private TableColumn<Rendezvous, String> coltempsrdv;
    @FXML
    private TableView<Rendezvous> tableRDV;
    @FXML
    private TableColumn<Rendezvous, String> colclientrdv;
    @FXML
    private TableColumn<Rendezvous, String> colmailrdv;
    @FXML
    private TableColumn<Rendezvous, String> colcentrerdv;
    @FXML
    private TableColumn<Rendezvous, String> colservicerdv;
    @FXML
    private TextField rechercherrdv;
    @FXML
    private ComboBox<String> cbtrier;
    @FXML
    private TableView<Rendezvous> tableUpComingRDV;
    @FXML
    private TableColumn<Rendezvous, String> colclientupcoming;
    @FXML
    private TableColumn<Rendezvous, String> colcentreupcoming;
    @FXML
    private TableColumn<Rendezvous, Date> coldateupcoming;
    @FXML
    private Button btnEnvoyerMail;
    @FXML
    private Button btnrefresh;

    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        try {
//            cbtrier.getItems().addAll("Date","Nom Client","Nom Centre");
//            showRendezvous();
//            showUpcomingRendezvous();
//        } catch (SQLException ex) {
//            Logger.getLogger(AfficherRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }    
    
    /*
     public void showRendezvous() throws SQLException
    {
        ServiceRendezvous sr =new ServiceRendezvous();
       
        ObservableList<Rendezvous> lr= sr.afficherRendezvous();
      
        
        
                //******************************************************************************
FilteredList<Rendezvous> filteredData = new FilteredList<>(lr, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        rechercherrdv.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getCentre().getNom()).toLowerCase().contains(lowerCaseFilter)) 
                    return true;
                    // Filter matches name.
                 else if (String.valueOf(myObject.getClient().getNom()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                }
                return false; // Does not match.
            });
        });

        
        
        

           coldaterdv.setCellValueFactory(new PropertyValueFactory<Rendezvous,Date>("date"));
        coltempsrdv.setCellValueFactory(new PropertyValueFactory<Rendezvous,String>("temps"));
        
              colservicerdv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getService().getNom());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
        
        
      colcentrerdv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getCentre().getNom());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
        
        
        
                colmailrdv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getClient().getEmail());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
        
        
        
        colclientrdv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getClient().getNom() + " " + p.getValue().getClient().getPrenom());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
        
        
        
        
        
        tableRDV.setItems(filteredData);
        
    } 

    @FXML
    private void TrierRdv(ActionEvent event) throws SQLException {
        rechercherrdv.setText("");
        ServiceRendezvous sr =new ServiceRendezvous();
         ObservableList<Rendezvous> lr= sr.afficherRendezvous();
        if (cbtrier.getValue().compareTo("Date")==0)
             lr= sr.afficherRendezvousTriDate();
        else if (cbtrier.getValue().compareTo("Nom Centre")==0)
             lr= sr.afficherRendezvousTriCentre();
        else if (cbtrier.getValue().compareTo("Nom Client")==0)
        
              lr= sr.afficherRendezvousTriClient();
        
               
                //******************************************************************************
          FilteredList<Rendezvous> filteredData = new FilteredList<>(lr, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        rechercherrdv.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(myObject -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name field in your object with filter.
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(myObject.getCentre().getNom()).toLowerCase().contains(lowerCaseFilter)) 
                    return true;
                    // Filter matches name.
                 else if (String.valueOf(myObject.getClient().getNom()).toLowerCase().contains(lowerCaseFilter)) {
                    return true;

                }
                return false; // Does not match.
            });
        });

        
        
        

           coldaterdv.setCellValueFactory(new PropertyValueFactory<Rendezvous,Date>("date"));
        coltempsrdv.setCellValueFactory(new PropertyValueFactory<Rendezvous,String>("temps"));
        
              colservicerdv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getService().getNom());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
        
        
      colcentrerdv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getCentre().getNom());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
        
        
        
                colmailrdv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getClient().getEmail());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
        
        
        
        colclientrdv.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getClient().getNom() + " " + p.getValue().getClient().getPrenom());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
        
        
        
        
        
        tableRDV.setItems(filteredData);
        
    }
    
    
    
    
    
    
      public void showUpcomingRendezvous() throws SQLException
    {
        ServiceRendezvous sr =new ServiceRendezvous();
        LocalDateTime ldt;
        ldt = LocalDateTime.now();
        java.sql.Timestamp.valueOf(ldt);
        
        System.out.println(java.sql.Timestamp.valueOf(ldt));
        System.out.println(java.sql.Timestamp.valueOf(ldt.plusDays(3)));
        
       
        ObservableList<Rendezvous> lr= sr.afficherUpcomingRendezvous(ldt);
              colclientupcoming.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getClient().getNom() + " " + p.getValue().getClient().getPrenom());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
              });
              
              
                   colcentreupcoming.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Rendezvous, String>, ObservableValue<String>>() {

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Rendezvous, String> p) {
        if (p.getValue() != null) {
            return new SimpleStringProperty(p.getValue().getCentre().getNom());
        } else {
            return new SimpleStringProperty("<no name>");
        }
    }
});
          
         coldateupcoming.setCellValueFactory(new PropertyValueFactory<Rendezvous,Date>("date"));
              
          tableUpComingRDV.setItems(lr);  
          
          
          
          
          
          btnEnvoyerMail.setOnAction(event->{  
          
                                                       String USER_NAME = "amirakarchoud111";  // GMail user name (just the part before "@gmail.com")
                                                       String PASSWORD = "esprit123"; // GMail password
                                                       String from = USER_NAME;
                                                       String pass = PASSWORD;
                                                       
                                                       String RECIPIENT="";
                                                       for (Rendezvous rr:lr)
                                                       {RECIPIENT=rr.getClient().getEmail();
                                                          String[] to = { RECIPIENT }; // list of recipient email addresses
                                                               String subject = "Rappel Rendezvous";
                                                               String body = "Vous avez un rendez vous chez: "+rr.getCentre().getNom()+" le : "+rr.getDate()+" a : "+rr.getTemps()+" \n l'adresse du centre : "+rr.getCentre().getLieu();

                                                               sendFromGMail(from, pass, to, subject, body);
                                                       }
                                                       
                                                              
          
          
                                });
          
              
    }
      
    
      
      
   
      
       private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
        Properties props = System.getProperties();
        String host = "smtp.gmail.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            message.setFrom(new InternetAddress(from));
            InternetAddress[] toAddress = new InternetAddress[to.length];

            // To get the array of addresses
            for( int i = 0; i < to.length; i++ ) {
                toAddress[i] = new InternetAddress(to[i]);
            }

            for( int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            message.setSubject(subject);
            message.setText(body);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    @FXML
    private void RefreshRdv(ActionEvent event) throws SQLException {
         ServiceRendezvous sr =new ServiceRendezvous();
         rechercherrdv.setText("");
         cbtrier.setValue("");
         
         LocalDateTime ldt;
        ldt = LocalDateTime.now();
         sr.supprimerRendezvousDate(ldt);
         showRendezvous();
    }
*/
      
    
}
