/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.centre;
//import Entities.Centre;
import javafx.scene.layout.HBox;
import slowlifejava.entities.centre.Rendezvous;
//import Entities.Service;
//import Entities.StatRDV;
//import Entities.Utilisateur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
//import gui.centre.FXMLDocumentController;
//import utils.MaConnexion;
import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import slowlifejava.entities.centre.Centre;
import slowlifejava.entities.centre.Service;
import entities.users.Utilisateur;
import slowlifejava.entities.centre.StatRDV;
import slowlifejava.utils.SlowlifeDB;


/**
 *
 * @author amira
 */
public class ServiceRendezvous {
    
    
    Connection cnx;

    public Connection getCnx() {
        return cnx;
    }

    public ServiceRendezvous() {
        cnx = SlowlifeDB.getInstance().getConnection();
    }

    
    
    
    public void ajouterRendezvous(Rendezvous r) {
        try {
            Statement stm = cnx.createStatement();

            String query = "INSERT INTO rendezvous (Id_centre,Id_utilisateur,Id_service,date,temps)"
                    + "VALUES ('" + r.getCentre().getId() + "','" + r.getClient().getId() + "','" + r.getService().getId() + "','" + r.getDate() + "','" + r.getTemps() + "')";

            stm.executeUpdate(query);
            //System.out.println("rdv ajouté");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    
    
    public  ObservableList<Rendezvous> afficherRendezvous() throws SQLException {
        
            Statement stm = cnx.createStatement();
             

            String query = "SELECT * From rendezvous";

            ResultSet rst = stm.executeQuery(query);
             
             ObservableList<Rendezvous> listrdv = FXCollections.observableArrayList();
           
            while(rst.next()){
                
                Rendezvous r = new Rendezvous();
                r.setId(rst.getInt("id"));
                
                int idc= rst.getInt("id_centre");
                ServiceCentre sc= new ServiceCentre();
                Centre c = new Centre();
                c=sc.afficherCentreDetail(idc);
                r.setCentre(c);
                
                int idu=rst.getInt("id_utilisateur");
                ServiceUtilisateur su= new ServiceUtilisateur();
                Utilisateur u = new Utilisateur();
                u= su.SelectUtilisateur(idu);
                r.setClient(u);
                
                int ids= rst.getInt("id_service");
                ServiceService ss= new ServiceService();
                Service s = new Service();
                s = ss.SelectService(ids);
                r.setService(s);
                
                r.setTemps(rst.getString("temps"));
                r.setDate(rst.getDate("Date"));
                
                listrdv.add(r);
            }

        
        return listrdv;

    }

    
     public void supprimerRendezvous(int id) {
        try {
            Statement stm = cnx.createStatement();

            String query = "Delete FROM rendezvous WHERE id='" + id + "' ";

            stm.executeUpdate(query);
            //System.out.println("centre supprime");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
     
        public void modifierRendezvous(Rendezvous r,int idd) {
        try {
            
                Statement stm = cnx.createStatement();

             String query = "UPDATE Rendezvous SET Id_centre= '" + r.getCentre().getId() + "' , Id_utilisateur='" + r.getClient().getId() + "' , Id_service='" + r.getService().getId() + "', date='" + r.getDate() +"', temps='" + r.getTemps() + "' WHERE id='" + idd + "'";
             
              stm.executeUpdate(query);
           

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        
        
 
        
        
            public Rendezvous afficherRendezvousSelected(int idd) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From Rendezvous WHERE id='" + idd + "'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Rendezvous> rdv= FXCollections.observableArrayList();
            
            while(rst.next()){
                 Rendezvous r = new Rendezvous();
                r.setId(rst.getInt("id"));
                
                int idc= rst.getInt("id_centre");
                ServiceCentre sc= new ServiceCentre();
                Centre c = new Centre();
                c=sc.afficherCentreDetail(idc);
                r.setCentre(c);
                
                int idu=rst.getInt("id_utilisateur");
                ServiceUtilisateur su= new ServiceUtilisateur();
                Utilisateur u = new Utilisateur();
                u= su.SelectUtilisateur(idu);
                r.setClient(u);
                
                int ids= rst.getInt("id_service");
                ServiceService ss= new ServiceService();
                Service s = new Service();
                s = ss.SelectService(ids);
                r.setService(s);

                r.setTemps(rst.getString("temps"));
                r.setDate(rst.getDate("Date"));
                rdv.add(r);
            }

        
        return rdv.get(0);

    }  
            
            
    public Boolean TestRendezvousSelected(int idut,String t,String ldt) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From Rendezvous WHERE id_utilisateur='" + idut + "' and temps ='" + t + "' and date ='" + ldt + "'";

            ResultSet rst = stm.executeQuery(query);
            
            

        
        return rst.first();

    }  
        
        
        
     public  ObservableList<StatRDV> StatRendezvous() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "select date, COUNT(*) nbr from rendezvous  GROUP by date Limit 8;";

            ResultSet rst = stm.executeQuery(query);

             ObservableList<StatRDV> listrdv = FXCollections.observableArrayList();
             
            while(rst.next()){
               
                StatRDV r=new StatRDV();
                r.setNbr(rst.getInt("nbr"));
                r.setD(rst.getString("Date"));
               
                listrdv.add(r);
            }
            
              return listrdv;
            
     }
     
     
        public  ObservableList<Rendezvous> afficherRendezvousTriDate() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From rendezvous order by date";

            ResultSet rst = stm.executeQuery(query);

             ObservableList<Rendezvous> listrdv = FXCollections.observableArrayList();
           
            while(rst.next()){
                Rendezvous r = new Rendezvous();
                r.setId(rst.getInt("id"));
                
                int idc= rst.getInt("id_centre");
                ServiceCentre sc= new ServiceCentre();
                Centre c = new Centre();
                c=sc.afficherCentreDetail(idc);
                r.setCentre(c);
                
                int idu=rst.getInt("id_utilisateur");
                ServiceUtilisateur su= new ServiceUtilisateur();
                Utilisateur u = new Utilisateur();
                u= su.SelectUtilisateur(idu);
                r.setClient(u);
                
                int ids= rst.getInt("id_service");
                ServiceService ss= new ServiceService();
                Service s = new Service();
                s = ss.SelectService(ids);
                r.setService(s);

                r.setTemps(rst.getString("temps"));
                r.setDate(rst.getDate("Date"));
                listrdv.add(r);
            }

        
        return listrdv;

    }
        
        
        
        
          public  ObservableList<Rendezvous> afficherUpcomingRendezvous(LocalDateTime l) throws SQLException {
        
            Statement stm = cnx.createStatement();
            String today=l.toString().substring(0, 11);
            String end=l.plusDays(3).toString().substring(0, 11);

            String query = "SELECT * from rendezvous where date BETWEEN '" + today + "' and '" + end + "'";

            ResultSet rst = stm.executeQuery(query);

             ObservableList<Rendezvous> listrdv = FXCollections.observableArrayList();
           
            while(rst.next()){
                Rendezvous r = new Rendezvous();
                r.setId(rst.getInt("id"));
                
                int idc= rst.getInt("id_centre");
                ServiceCentre sc= new ServiceCentre();
                Centre c = new Centre();
                c=sc.afficherCentreDetail(idc);
                r.setCentre(c);
                
                int idu=rst.getInt("id_utilisateur");
                ServiceUtilisateur su= new ServiceUtilisateur();
                Utilisateur u = new Utilisateur();
                u= su.SelectUtilisateur(idu);
                r.setClient(u);
                
                int ids= rst.getInt("id_service");
                ServiceService ss= new ServiceService();
                Service s = new Service();
                s = ss.SelectService(ids);
                r.setService(s);

                r.setTemps(rst.getString("temps"));
                r.setDate(rst.getDate("Date"));
                listrdv.add(r);
            }

        
        return listrdv;

    }
        
        
        
        
           public  ObservableList<Rendezvous> afficherRendezvousTriClient() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "select r.*,c.nom from rendezvous r,utilisateur c where r.id_utilisateur=c.id order by nom ";

            ResultSet rst = stm.executeQuery(query);

             ObservableList<Rendezvous> listrdv = FXCollections.observableArrayList();
           
            while(rst.next()){
                Rendezvous r = new Rendezvous();
                r.setId(rst.getInt("id"));
                
                int idc= rst.getInt("id_centre");
                ServiceCentre sc= new ServiceCentre();
                Centre c = new Centre();
                c=sc.afficherCentreDetail(idc);
                r.setCentre(c);
                
                int idu=rst.getInt("id_utilisateur");
                ServiceUtilisateur su= new ServiceUtilisateur();
                Utilisateur u = new Utilisateur();
                u= su.SelectUtilisateur(idu);
                r.setClient(u);
                
                int ids= rst.getInt("id_service");
                ServiceService ss= new ServiceService();
                Service s = new Service();
                s = ss.SelectService(ids);
                r.setService(s);

                r.setTemps(rst.getString("temps"));
                r.setDate(rst.getDate("Date"));
                listrdv.add(r);
            }

        
        return listrdv;

    }
           
           
           
           public  ObservableList<Rendezvous> afficherRendezvousTriCentre() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "select r.*, c.nom from rendezvous r,centre c where r.id_centre=c.id order by nom ";

            ResultSet rst = stm.executeQuery(query);

             ObservableList<Rendezvous> listrdv = FXCollections.observableArrayList();
           
            while(rst.next()){
                Rendezvous r = new Rendezvous();
                r.setId(rst.getInt("id"));
                
                int idc= rst.getInt("id_centre");
                ServiceCentre sc= new ServiceCentre();
                Centre c = new Centre();
                c=sc.afficherCentreDetail(idc);
                r.setCentre(c);
                
                int idu=rst.getInt("id_utilisateur");
                ServiceUtilisateur su= new ServiceUtilisateur();
                Utilisateur u = new Utilisateur();
                u= su.SelectUtilisateur(idu);
                r.setClient(u);
                
                int ids= rst.getInt("id_service");
                ServiceService ss= new ServiceService();
                Service s = new Service();
                s = ss.SelectService(ids);
                r.setService(s);

                r.setTemps(rst.getString("temps"));
                r.setDate(rst.getDate("Date"));
                listrdv.add(r);
            }

        
        return listrdv;

    }
           
        public void supprimerRendezvousDate(LocalDateTime l) {
        try {
            Statement stm = cnx.createStatement();
             String today=l.toString().substring(0, 11);
            String query = "Delete FROM rendezvous WHERE date <'" + today + "' ";

            stm.executeUpdate(query);
            //System.out.println("centre supprime");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
        
    
    
}
