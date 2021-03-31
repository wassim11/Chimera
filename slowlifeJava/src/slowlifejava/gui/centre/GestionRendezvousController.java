/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.gui.centre;

//import Entities.Centre;
//import Entities.Rendezvous;
//import Entities.Service;
//import Entities.ServiceOffert;
//import Entities.Utilisateur;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTimePicker;
import entities.users.Utilisateur;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import slowlifejava.entities.centre.Centre;
import slowlifejava.entities.centre.Rendezvous;
import slowlifejava.entities.centre.Service;
import slowlifejava.entities.centre.ServiceOffert;
import slowlifejava.services.centre.ServiceCentre;
import slowlifejava.services.centre.ServiceRendezvous;
import slowlifejava.services.centre.ServiceService;
import slowlifejava.services.centre.ServiceServiceCentre;
import slowlifejava.services.users.UserService;
//import service.ServiceCentre;
//import service.ServiceRendezvous;
//import service.ServiceService;
//import service.ServiceServiceCentre;
//import service.ServiceUtilisateur;


/**
 * FXML Controller class
 *
 * @author amira
 */
public class GestionRendezvousController implements Initializable {

    @FXML
    private Button btnPreviousMonth;
    @FXML
    private Label lblMonthYear;
    @FXML
    private Button btnNextMonth;
    @FXML
    private GridPane gpMain;
    
    LocalDateTime ldtControl;
    
    int idUtilisateur=5;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ldtControl = LocalDateTime.now();        
        
            loadMonth(ldtControl);
        
    }   
    
    
    int getColumn(LocalDateTime ldt)
    {
        int i = 0;
        while(ldt.getDayOfWeek() != DayOfWeek.SUNDAY)
        {
            i++;
            ldt = ldt.plusDays(1);
        }
        
        return i;
    }
    
    
    
    
    private void loadMonth(LocalDateTime ldt) 
    {
        if(gpMain.getChildren().size() > 0)
        { 
            gpMain.getChildren().clear();
        }
        
        loadGridPaneFirstRow();
        lblMonthYear.setText(ldt.getMonth() + " " + ldt.getYear());
        
        LocalDateTime ldtIterator = ldt.minusDays(ldt.getDayOfMonth() - 1);   
        ldtIterator.format(DateTimeFormatter.ISO_DATE);
        
        int control = getColumn(ldtIterator);
        int control2 = 0;
        int i = 0;
        while(ldtIterator.getMonth() == ldt.getMonth())
        {
            if( i == 0 || i == 1 && control2 <= control)
            {
                i = 1;
                control2++;
            }
            else 
            {
                i = ((control2 - (control + 1)) / 7) + 2;
                control2++;
            }
            
            Label tempLabel = new Label(Integer.toString(ldtIterator.getDayOfMonth()));
            
            
            gpMain.add(createCell(tempLabel, ldtIterator), ldtIterator.getDayOfWeek().getValue() - 1, i);
            
            ldtIterator = ldtIterator.plusDays(1);
        }        
    }
    
    private void loadGridPaneFirstRow()
    {
        String[] string = {"Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday", "Sunday"};
        for(int i = 0; i < string.length; i++)
        {
            Label tempLabel = new Label(string[i]);
            tempLabel.setStyle("-fx-text-fill: #2f963c;");
            GridPane.setHalignment(tempLabel, HPos.CENTER);
            gpMain.add(tempLabel, i, 0);
        }       
    }
    
    
    
    
    
        private BorderPane createCell(Label label, LocalDateTime ldt) {

        BorderPane cell = new BorderPane();
        cell.setStyle("    -fx-border-color: #2e8b57;\n" +
                      "    -fx-border-width: 0.5px;");
        label.setOnMouseClicked(e -> {
            try {
                addNewRendezvous(ldt);
            } catch (SQLException ex) {
                Logger.getLogger(GestionRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        VBox vbox = new VBox();
       vbox.getChildren().addAll(loadRendezvousForDate(ldt));//load->servicerdv.java
        BorderPane.setAlignment(vbox, Pos.CENTER);
        cell.setCenter(vbox);

        BorderPane.setAlignment(label, Pos.TOP_RIGHT);
        cell.setTop(label);
        cell.getStyleClass().add("cell");
        
        return cell;
    }
        
        
        
        
        
        
         private void addNewRendezvous(LocalDateTime ldt) throws SQLException
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Nouveau Rendezvous");
        alert.setHeaderText("Prendre un rendez-vous");
        alert.setContentText("Saisir les donnees");
        
        //contenu du alert
        JFXComboBox<String> cbnomcentre = new JFXComboBox<>(); 
        JFXComboBox<String> cbnomservice = new JFXComboBox<>();
        cbnomcentre.setStyle("-jfx-focus-color:#ffb845;-jfx-unfocus-color:#39c94c;");
        cbnomservice.setStyle("-jfx-focus-color:#ffb845;-jfx-unfocus-color:#39c94c;");
        // ComboBox<String> cbtemps = new ComboBox<>();
        Label nomcentre = new Label("Nom du Centre:");
        Label nomservice = new Label("Service du Centre:");
        Label prixs = new Label("Prix du service:");
        Label prixService = new Label("");
        Label space = new Label("                 ");
        Label space2 = new Label("                 ");
        Label lbtemps= new Label("Temps du Rendez vous:");
        //cbtemps.getItems().addAll("9:00","10:00","11:00","12:00","14:00","15:00","16:00");
        JFXTimePicker tp =new JFXTimePicker();
        

//combo box nom centre**********************
       ServiceCentre sc = new ServiceCentre ();
       ObservableList<Centre> lc= sc.afficherCentre();
          for (Centre c:lc)
              cbnomcentre.getItems().add(c.getNom());
         // cbnomcentre.getStyleClass().add("combob");
//combo box nom service **********************
        //cbnomservice.getStyleClass().add("combob");
      cbnomcentre.setOnAction(event->{ServiceServiceCentre ssc =new ServiceServiceCentre();
                                        
                                       for (Centre c:lc)
                                           if(c.getNom().equals(cbnomcentre.getValue()))
              {Centre.iddet=c.getId();
                  //System.out.println(Centre.iddet);
              }
            try { cbnomservice.getItems().clear();
                
                ObservableList<ServiceOffert> lcc= ssc.afficherServiceOffertCentre(Centre.iddet);
                 for (ServiceOffert so:lcc)
              {cbnomservice.getItems().add(so.getNom());
              
              
              }
            } catch (SQLException ex) {
                Logger.getLogger(GestionRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                                      });
      
 //changer prix service ****************     
      cbnomservice.setOnAction(event->{ 
                              ServiceServiceCentre ssc =new ServiceServiceCentre();
                                  //System.out.println("ici 1");      
            try {prixService.setText("");
                //System.out.println(cbnomservice.getValue());
            //System.out.println("ici 2"); 
                ObservableList<ServiceOffert> lcc= ssc.afficherServiceOffertCentre(Centre.iddet);
                //System.out.println("ici 3"); 
                 for (ServiceOffert so:lcc)
              { //System.out.println(so.getNom());
                  if (so.getNom().equals(cbnomservice.getValue()))
              {ServiceOffert.idselected=so.getId();
              prixService.setText(""+so.getPrix());
                  System.out.println(so.getPrix());
              }
              }
                 //System.out.println("ici 4"); 
            } catch (SQLException ex) {
                Logger.getLogger(GestionRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
            }
          
          });

        GridPane expContent = new GridPane();
        
       lbtemps.setStyle("-fx-text-fill: green;");
       nomcentre.setStyle("-fx-text-fill: green;");
      nomservice.setStyle("-fx-text-fill: green;");         
        prixs.setStyle("-fx-text-fill: green;");  
        
        cbnomcentre.setMaxWidth(180);
        cbnomservice.setMaxWidth(180);
         expContent.add(nomcentre, 0, 1);
         expContent.add(cbnomcentre, 0, 2);
         expContent.add(nomservice, 0, 3);
         expContent.add(cbnomservice, 0, 4);
          expContent.add(space, 1, 3);
          expContent.add(space2, 1, 4);
         expContent.add(prixs, 2, 3);
         expContent.add(prixService, 2, 4);
         expContent.add(lbtemps, 0, 6);
         //expContent.add(cbtemps, 0, 7);
           expContent.add(tp, 0, 7);

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setContent(expContent);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {      System.out.println(tp.getValue());
            ServiceRendezvous srdv= new ServiceRendezvous();
            
       //     r.setId_centre();
            if(cbnomcentre.getValue() !=null && cbnomservice.getValue()!= null && tp.getValue()!= null && (ldt.isAfter(LocalDateTime.now())))
            {  Date d= java.sql.Timestamp.valueOf(ldt);
        String dd=d.toString();
        dd=dd.substring(0, 10);
                if (!srdv.TestRendezvousSelected(idUtilisateur, tp.getValue()+":00", dd))
                { //   createNoteForDate(textArea.getText(), ldt);//servicerdv.java
                Rendezvous r= new Rendezvous();
            r.setDate(java.sql.Timestamp.valueOf(ldt));
          
            

                Centre c = new Centre();
                c=sc.afficherCentreDetail(Centre.iddet);
                r.setCentre(c);

                UserService su= new UserService();
                Utilisateur u = new Utilisateur();
                u= su.getUserById(idUtilisateur);
                r.setClient(u);

                ServiceService ss= new ServiceService();
                Service s = new Service();
                s = ss.SelectService(ServiceOffert.idselected);
                r.setService(s);
            
            
            r.setTemps(tp.getValue()+":00");
            srdv.ajouterRendezvous(r);
                loadMonth(ldt);
                }
                else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Error");
        alert1.setHeaderText("Error");
        alert1.setContentText("vous avez deja un Rendezvous a cette heure!");
        alert1.show();
        
                }
            }  
            else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR);
        alert1.setTitle("Error");
        alert1.setHeaderText("Error");
        alert1.setContentText("Verifier les champs et la date!");
        alert1.show();
        
                }
           
        } 
    }
    
    
    
         
         
         
         
         
         
    
      private List<HBox> loadRendezvousForDate(LocalDateTime ldt) 
    {  
        List<HBox> tempList = new ArrayList();
           ServiceRendezvous srdv = new ServiceRendezvous();
           
           try{
            Date d= java.sql.Timestamp.valueOf(ldt);
        String dd=d.toString();
        dd=dd.substring(0, 10);
        
        String query = "Select * FROM Rendezvous WHERE date = '" + dd + "' and id_utilisateur='" + idUtilisateur + "'";
        
        Statement stm = srdv.getCnx().createStatement();
             
        
            
            ResultSet rst = stm.executeQuery(query);
            String[] tabColor= {"143,235,168","250, 217, 172","157, 236, 250","250, 172, 224"};
            int i=0;
            while(rst.next())
            {   ServiceCentre s1 =new ServiceCentre();
            Centre cc1 = new Centre();
            cc1 =s1.afficherCentreDetail(rst.getInt("id_centre"));
                int tempNote = rst.getInt("id");
                String tmp1=rst.getString("temps").substring(0,5);
                Label tempLabel = new Label(rst.getString("temps"));
                Button b = new Button(tmp1+" : "+cc1.getNom());
                b.setStyle("-fx-border-radius: 5; -fx-background-color: rgb("+tabColor[i]+");-fx-padding: 1px 8px;-fx-font-size: 9px;");
                if (i+1>=tabColor.length)
                    i=0;
                else 
                    i++;
                           
                b.setOnMouseClicked(e -> {try {
                    editRendezvous(ldt, tempNote);
                } catch (SQLException ex) {
                    Logger.getLogger(GestionRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
                }
                 loadMonth(ldt);});
                 
                HBox hbox = new HBox();
                hbox.getChildren().add(b);
                //hbox.getChildren().add(tempLabel);
                tempList.add(hbox);
            }
            //System.out.println("ici 5");
        } catch (SQLException ex) {
            Logger.getLogger(ServiceRendezvous.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return tempList;
    }
    
    
    
    
    
      
      
      private void deleteRendezvous(LocalDateTime ldt, int idd)
    {
        if(deleteConfirmation().get() == ButtonType.OK)
        {
             ServiceRendezvous srdv= new ServiceRendezvous();
             srdv.supprimerRendezvous(idd);
             
        }
      }
      
      
      
      
      
      
      
      
      
      
         private  Optional<ButtonType> deleteConfirmation()
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete");
        alert.setHeaderText("Annuler Rendezvous");
        alert.setContentText("Etes-vous sur ?");
        Optional<ButtonType> result = alert.showAndWait();
        
        ObservableList<String> optionList = FXCollections.observableArrayList();
        return result;
    }

    
    
         
         
         
         
          private void editRendezvous(LocalDateTime ldt, int idd) throws SQLException
    {
            //  JFXDialog alert = new JFXDialog();
         Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Modifier");
        alert.setHeaderText("Modifier un Rendezvous");
        alert.setContentText("Saisir les donnees");
//alert.setContent(new Label("Modifier"));
        
        //***************selected rdv************************
         ServiceRendezvous srdv= new ServiceRendezvous();
      Rendezvous r=srdv.afficherRendezvousSelected(idd);
      Centre.iddet=r.getCentre().getId();
       ServiceOffert.idselected=r.getService().getId();
        
        
                     //contenu du alert
        JFXComboBox<String> cbnomcentre = new JFXComboBox<>(); 
        JFXComboBox<String> cbnomservice = new JFXComboBox<>();
        cbnomcentre.setStyle("-jfx-focus-color:#ffb845;-jfx-unfocus-color:#39c94c;");
        cbnomservice.setStyle("-jfx-focus-color:#ffb845;-jfx-unfocus-color:#39c94c;");
         //ComboBox<String> cbtemps = new ComboBox<>();
          JFXTimePicker cbtemps =new JFXTimePicker();
        Label nomcentre = new Label("Nom du Centre:");
        Label nomservice = new Label("Service du Centre:");
        Label prixs = new Label("Prix du service:");
        Label prixService = new Label("");
        Label space = new Label("                 ");
        Label space2 = new Label("                 ");
        Label lbtemps= new Label("Temps du Rendez vous:");
        //cbtemps.getItems().addAll("9:00","10:00","11:00","12:00","14:00","15:00","16:00");

//combo box nom centre**********************
       ServiceCentre sc = new ServiceCentre ();
       ObservableList<Centre> lc= sc.afficherCentre();
          for (Centre c:lc)
              cbnomcentre.getItems().add(c.getNom());
//combo box nom service **********************
      cbnomcentre.setOnAction(event->{ServiceServiceCentre ssc =new ServiceServiceCentre();
                                        
                                       for (Centre c:lc)
                                           if(c.getNom().equals(cbnomcentre.getValue()))
              {Centre.iddet=c.getId();
                  //System.out.println(Centre.iddet);
              }
            try { cbnomservice.getItems().clear();
                
                ObservableList<ServiceOffert> lcc= ssc.afficherServiceOffertCentre(Centre.iddet);
                 for (ServiceOffert so:lcc)
              {cbnomservice.getItems().add(so.getNom());
              
              
              }
            } catch (SQLException ex) {
                Logger.getLogger(GestionRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
                                      });
      
   //***************set combobox ***************************
      
//      for (Centre c:lc)
//          if (c.getId()==r.getCentre())
              cbnomcentre.setValue(r.getCentre().getNom());
              
       ServiceServiceCentre ssc =new ServiceServiceCentre(); 
       for (Centre c:lc)
                                           if(c.getNom().equals(cbnomcentre.getValue()))
              {Centre.iddet=c.getId();
                  //System.out.println(Centre.iddet);
              }
            try { cbnomservice.getItems().clear();
                
                ObservableList<ServiceOffert> lcc= ssc.afficherServiceOffertCentre(Centre.iddet);
                 for (ServiceOffert so:lcc)
              {cbnomservice.getItems().add(so.getNom());
              
              
              }
            } catch (SQLException ex) {
                Logger.getLogger(GestionRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
            }
        ObservableList<ServiceOffert> lcc= ssc.afficherServiceOffertCentre(Centre.iddet);
                 for (ServiceOffert soo:lcc)
              { 
                  if (r.getService().getId()==soo.getId())
              {ServiceOffert.idselected=soo.getId();
              prixService.setText(""+soo.getPrix());
                cbnomservice.setValue(soo.getNom());
              }
              }
                 String tmp1=r.getTemps().substring(0,5);
                  cbtemps.setValue(LocalTime.parse(tmp1));
      
      
      //***********************************************************    
   
      
 //changer prix service ****************     
      cbnomservice.setOnAction(event->{ 
             try {
                 ObservableList<ServiceOffert> lcc1= ssc.afficherServiceOffertCentre(Centre.iddet);
                 prixService.setText("");
                 for (ServiceOffert so:lcc1)
                 {
                     if (so.getNom().equals(cbnomservice.getValue()))
                     {ServiceOffert.idselected=so.getId();
                     prixService.setText(""+so.getPrix());
                     System.out.println(so.getPrix());
                     }
                 }  } catch (SQLException ex) {
                 Logger.getLogger(GestionRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
             }
          
          });
     
                alert.setWidth(800);
        GridPane expContent = new GridPane();
        
               lbtemps.setStyle("-fx-text-fill: green;");
       nomcentre.setStyle("-fx-text-fill: green;");
      nomservice.setStyle("-fx-text-fill: green;");         
        prixs.setStyle("-fx-text-fill: green;");  
        
        cbnomcentre.setMaxWidth(180);
        cbnomservice.setMaxWidth(180);
         expContent.add(nomcentre, 0, 1);
         expContent.add(cbnomcentre, 0, 2);
         expContent.add(nomservice, 0, 3);
         expContent.add(cbnomservice, 0, 4);
          expContent.add(space, 1, 3);
          expContent.add(space2, 1, 4);
         expContent.add(prixs, 2, 3);
         expContent.add(prixService, 2, 4);
         expContent.add(lbtemps, 0, 6);
         expContent.add(cbtemps, 0, 7);
     

        alert.getDialogPane().setContent(expContent);

        ButtonType buttonModifier = new ButtonType("Modifier");
        ButtonType buttonPdf = new ButtonType("telecharger");
//       Image img = new Image("src/image/download.png");
//      ImageView view = new ImageView(img);
//      buttonPdf.setGraphic(view);
        
        ButtonType buttonSupprimer = new ButtonType("Supprimer");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonModifier,buttonSupprimer,buttonPdf, buttonTypeCancel);
        
        

        Optional<ButtonType> result = alert.showAndWait();
       
        if (result.get() == buttonModifier)
        {
            //ServiceRendezvous srdv= new ServiceRendezvous();
            
            if(cbnomcentre.getValue() !=null && cbnomservice.getValue()!= null && cbtemps.getValue()!= null)
            {
                Rendezvous rr= new Rendezvous();
            rr.setDate(java.sql.Timestamp.valueOf(ldt));
            
                Centre c = new Centre();
                c=sc.afficherCentreDetail(Centre.iddet);
                rr.setCentre(c);
                
           
                UserService su= new UserService();
                Utilisateur u = new Utilisateur();
                u= su.getUserById(idUtilisateur);
                rr.setClient(u);
                
              
                ServiceService ss= new ServiceService();
                Service s = new Service();
                s = ss.SelectService(ServiceOffert.idselected);
                rr.setService(s);
            
            rr.setTemps(cbtemps.getValue()+":00");
            srdv.modifierRendezvous(rr,idd);
                loadMonth(ldt);
            }            
        } 
        if (result.get() == buttonSupprimer)
        {
            deleteRendezvous(ldt, idd); loadMonth(ldt);
        }
        if (result.get() == buttonPdf)
        {
             Rendezvous rr= new Rendezvous();
            rr.setDate(java.sql.Timestamp.valueOf(ldt));
            
                Centre c;
                c=sc.afficherCentreDetail(Centre.iddet);
                rr.setCentre(c);
                
           
                UserService su= new UserService();
                Utilisateur u ;
                u= su.getUserById(idUtilisateur);
                rr.setClient(u);
                
              
                ServiceService ss= new ServiceService();
                Service s ;
                s = ss.SelectService(ServiceOffert.idselected);
                rr.setService(s);
            
            rr.setTemps(cbtemps.getValue()+":00"); 
            telecharger(rr);
        }
          
       }
         
         
         
         
         

    @FXML
    private void handleBTNMonthChange(ActionEvent event) {
         if(((Button)event.getSource()).getId().equals("leftButtonWithImage"))
        {
            ldtControl = ldtControl.minusMonths(1);
            loadMonth(ldtControl);
        }
        else if(((Button)event.getSource()).getId().equals("rightButtonWithImage"))
        {
            ldtControl = ldtControl.plusMonths(1);
            loadMonth(ldtControl);
        }
    }
    
    
    
    private void telecharger(Rendezvous r)
    {
         try{
        
        System.out.println("Creating");
        Random rand = new Random(); 
         int upperbound = 999;
      int int_random = rand.nextInt(upperbound); 
        String fileName = "Rendezvous"+int_random+".pdf"; // name of our file


        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();

        doc.addPage(page);

        PDPageContentStream content = new PDPageContentStream(doc, page);
        
        
        //images
        String imageName = "src/image/centre2.png";
        PDImageXObject pdImage = PDImageXObject.createFromFile(imageName, doc);
        content.drawImage(pdImage, 20, 730);   
        imageName = "src/image/RdvIcon.png";
        pdImage = PDImageXObject.createFromFile(imageName, doc);
        content.drawImage(pdImage, 520, 730);
        imageName = "src/image/logoV.png";
        pdImage = PDImageXObject.createFromFile(imageName, doc);
        content.drawImage(pdImage, 20, 20);
        
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 26);
        content.setNonStrokingColor(222, 149, 22);
        content.moveTextPositionByAmount(220, 750);
        content.drawString("Rendez-vous");
        content.endText();
        
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 15);
        content.moveTextPositionByAmount(80, 700);
        content.setNonStrokingColor(100, 209, 94);
        content.drawString("Nom et prenom :     ");
        content.setNonStrokingColor(Color.black);
        content.drawString(r.getClient().getPrenom()+" "+r.getClient().getNom());
        content.endText();
        
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 15);
        content.moveTextPositionByAmount(80,650);
        content.setNonStrokingColor(100, 209, 94);
        content.drawString("Nom du centre:   ");
        content.setNonStrokingColor(Color.black);
         content.drawString(r.getCentre().getNom());
        content.endText();
        
        String dd=r.getDate().toString();
        dd=dd.substring(0, 10);
        content.beginText();
        content.moveTextPositionByAmount(80,600);
        content.setNonStrokingColor(100, 209, 94);
        content.drawString("Date :      ");
        content.setNonStrokingColor(Color.black);
        content.drawString(dd);
        content.endText();
        
        
        content.beginText();
        content.moveTextPositionByAmount(80,550);
        content.setNonStrokingColor(100, 209, 94);
        content.drawString("Heure :      ");
        content.setNonStrokingColor(Color.black);
        content.drawString(r.getTemps());
        content.endText();
        
        
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 10);
        content.moveTextPositionByAmount(80,50);
        content.drawString("Veuillez vous présenter au centre avant au moins 10 minutes de votre rendez-vous");
        content.endText();
        content.beginText();
        content.setFont(PDType1Font.HELVETICA, 10);
        content.moveTextPositionByAmount(80,40);
        content.drawString("Merci d'avoir choisi SlowLife");
        content.endText();
        
        
        content.close();
        doc.save(fileName);
        doc.close();
        
        System.out.println("your file created in : "+ System.getProperty("user.dir"));

        }
        catch(IOException e){
        
        System.out.println(e.getMessage());
        
        }

        
    }
    
}
