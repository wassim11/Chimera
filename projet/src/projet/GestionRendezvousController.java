/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projet;

import Entities.Centre;
import Entities.Rendezvous;
import Entities.ServiceOffert;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import service.ServiceCentre;
import service.ServiceRendezvous;
import service.ServiceServiceCentre;

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
    
    int idUtilisateur=1;

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
            GridPane.setHalignment(tempLabel, HPos.CENTER);
            gpMain.add(tempLabel, i, 0);
        }       
    }
    
    
    
    
    
        private BorderPane createCell(Label label, LocalDateTime ldt) {

        BorderPane cell = new BorderPane();
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
        ComboBox<String> cbnomcentre = new ComboBox<>(); 
        ComboBox<String> cbnomservice = new ComboBox<>();
         ComboBox<String> cbtemps = new ComboBox<>();
        Label nomcentre = new Label("Nom du Centre:");
        Label nomservice = new Label("Service du Centre:");
        Label prixs = new Label("Prix du service:");
        Label prixService = new Label("");
        Label space = new Label("                 ");
        Label space2 = new Label("                 ");
        Label lbtemps= new Label("Temps du Rendez vous:");
        cbtemps.getItems().addAll("9:00","10:00","11:00","12:00","14:00","15:00","16:00");

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
     

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setContent(expContent);
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            ServiceRendezvous srdv= new ServiceRendezvous();
            
       //     r.setId_centre();
            if(cbnomcentre.getValue() !=null && cbnomservice.getValue()!= null && cbtemps.getValue()!= null && (ldt.isAfter(LocalDateTime.now())))
            {
             //   createNoteForDate(textArea.getText(), ldt);//servicerdv.java
                Rendezvous r= new Rendezvous();
            r.setDate(java.sql.Timestamp.valueOf(ldt));
            r.setId_centre(Centre.iddet);
            r.setId_client(idUtilisateur);
            r.setId_service(ServiceOffert.idselected);
            r.setTemps(cbtemps.getValue()+":00");
            srdv.ajouterRendezvous(r);
                loadMonth(ldt);
            }  
           
        } 
    }
    
    
    
         
         
         
         
         
         
    
      private List<HBox> loadRendezvousForDate(LocalDateTime ldt) 
    {  
        List<HBox> tempList = new ArrayList();
           ServiceRendezvous srdv = new ServiceRendezvous();
           
           try{
            Date d= java.sql.Timestamp.valueOf(ldt);
        System.out.println(ldt);
        System.out.println(d);
        String dd=d.toString();
        dd=dd.substring(0, 10);
        System.out.println(dd);
        
        String query = "Select * FROM Rendezvous WHERE date = '" + dd + "' and id_utilisateur='" + idUtilisateur + "'";
        
        Statement stm = srdv.getCnx().createStatement();
             
        
            
            ResultSet rst = stm.executeQuery(query);
            //System.out.println("ici 1");
            while(rst.next())
            { System.out.println(rst.getString("id_centre"));

                Button b= new Button();
                b.setText(rst.getString("temps"));
                
                int tempNote = rst.getInt("id");
                Label tempLabel = new Label(""+tempNote);
                 // System.out.println("ici 3");
                Label label = new Label(rst.getString("id_centre"));
                b.setOnMouseClicked(e -> {deleteRendezvous(ldt, tempNote); loadMonth(ldt);});
                tempLabel.setOnMouseClicked(e -> {try {
                    editRendezvous(ldt, tempNote);
                } catch (SQLException ex) {
                    Logger.getLogger(GestionRendezvousController.class.getName()).log(Level.SEVERE, null, ex);
                }
                 loadMonth(ldt);});
                 
                HBox hbox = new HBox();
                hbox.getChildren().add(b);
                
                //System.out.println("ici 4");
                hbox.getChildren().add(tempLabel);
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
              
         Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Modifier");
        alert.setHeaderText("Modifier un Rendezvous");
        alert.setContentText("Saisir les donnees");
        
        //***************selected rdv************************
         ServiceRendezvous srdv= new ServiceRendezvous();
      Rendezvous r=srdv.afficherRendezvousSelected(idd);
      Centre.iddet=r.getId_centre();
       ServiceOffert.idselected=r.getId_service();
        
        
                     //contenu du alert
        ComboBox<String> cbnomcentre = new ComboBox<>(); 
        ComboBox<String> cbnomservice = new ComboBox<>();
         ComboBox<String> cbtemps = new ComboBox<>();
        Label nomcentre = new Label("Nom du Centre:");
        Label nomservice = new Label("Service du Centre:");
        Label prixs = new Label("Prix du service:");
        Label prixService = new Label("");
        Label space = new Label("                 ");
        Label space2 = new Label("                 ");
        Label lbtemps= new Label("Temps du Rendez vous:");
        cbtemps.getItems().addAll("9:00","10:00","11:00","12:00","14:00","15:00","16:00");

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
      
      for (Centre c:lc)
          if (c.getId()==r.getId_centre())
              cbnomcentre.setValue(c.getNom());
              
       ServiceServiceCentre ssc =new ServiceServiceCentre(); 
        ObservableList<ServiceOffert> lcc= ssc.afficherServiceOffertCentre(Centre.iddet);
                 for (ServiceOffert soo:lcc)
              { 
                  if (r.getId_service()==soo.getId())
              {ServiceOffert.idselected=soo.getId();
              prixService.setText(""+soo.getPrix());
                cbnomservice.setValue(soo.getNom());
              }
              }
                  cbtemps.setValue(r.getTemps());
      
      
      //***********************************************************    
   
      
 //changer prix service ****************     
      cbnomservice.setOnAction(event->{ 
          prixService.setText("");
          for (ServiceOffert so:lcc)
          {
              if (so.getNom().equals(cbnomservice.getValue()))
              {ServiceOffert.idselected=so.getId();
              prixService.setText(""+so.getPrix());
              System.out.println(so.getPrix());
              }
          }
          
          });
     

        GridPane expContent = new GridPane();
        
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
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
            //ServiceRendezvous srdv= new ServiceRendezvous();
            
            if(cbnomcentre.getValue() !=null && cbnomservice.getValue()!= null && cbtemps.getValue()!= null)
            {
                Rendezvous rr= new Rendezvous();
            rr.setDate(java.sql.Timestamp.valueOf(ldt));
            rr.setId_centre(Centre.iddet);
            rr.setId_client(idUtilisateur);
            rr.setId_service(ServiceOffert.idselected);
            rr.setTemps(cbtemps.getValue()+":00");
            srdv.modifierRendezvous(rr,idd);
                loadMonth(ldt);
            }            
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
    
}
