/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.centre;

//import Entities.Service;
//import Entities.StatPrixS;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import slowlifejava.entities.centre.Service;
import slowlifejava.entities.centre.StatPrixS;
import slowlifejava.utils.SlowlifeDB;


/**
 *
 * @author amira
 */
public class ServiceService {
    
    
      
    Connection cnx;

    public ServiceService() {
        cnx = SlowlifeDB.getInstance().getConnection();
    }

    
    
    
    public void ajouterService(Service s) {
        try {
            Statement stm = cnx.createStatement();

            String query = "INSERT INTO Service (nom,description,type)"
                    + "VALUES ('" + s.getNom() + "','" + s.getDescription() + "','" + s.getType() + "')";

            stm.executeUpdate(query);
            //System.out.println("service ajouté");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    
    
    public ObservableList<Service> afficherService() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From service";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Service> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                Service s = new Service();
                s.setId(rst.getInt("id"));
                s.setNom(rst.getString("nom"));
                s.setDescription(rst.getString("description"));
            
                s.setType(rst.getString("type"));
                
                services.add(s);
            }

        
        return services;

    }
    
    
     public void supprimerService(int id) {
        try {
            Statement stm = cnx.createStatement();

            String query = "Delete FROM service WHERE id='" + id + "' ";

            stm.executeUpdate(query);
            //System.out.println("centre supprime");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
      public void modifierService(Service s,int idd) {
        try {
            
                Statement stm = cnx.createStatement();

             String query = "UPDATE Service SET nom= '" + s.getNom() + "' , description='" + s.getDescription() + "' , type='" + s.getType() + "' WHERE id='" + idd + "'";
             
              stm.executeUpdate(query);
           

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
      
      
       public ObservableList<Service> afficherServicetriNom() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From service order by nom";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Service> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                Service s = new Service();
                s.setId(rst.getInt("id"));
                s.setNom(rst.getString("nom"));
                s.setDescription(rst.getString("description"));
            
                s.setType(rst.getString("type"));
                
                services.add(s);
            }

        
        return services;

    }
       
       
         
       public ObservableList<Service> afficherServicetriType() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From service order by type";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Service> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                Service s = new Service();
                s.setId(rst.getInt("id"));
                s.setNom(rst.getString("nom"));
                s.setDescription(rst.getString("description"));
            
                s.setType(rst.getString("type"));
                
                services.add(s);
            }

        
        return services;

    }
       
       
             public Service RechercherServiceNom(String nom) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From Service WHERE nom='" + nom + "'";

            ResultSet rst = stm.executeQuery(query);

            
            ObservableList<Service> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                Service s = new Service();
                s.setId(rst.getInt("id"));
                s.setNom(rst.getString("nom"));
                s.setDescription(rst.getString("description"));
            
                s.setType(rst.getString("type"));
                
                services.add(s);
            }
        
        return services.get(0);

    }
             
             
            public Service SelectService(int idd) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From Service WHERE id='" + idd + "'";

            ResultSet rst = stm.executeQuery(query);

            
            ObservableList<Service> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                Service s = new Service();
                s.setId(rst.getInt("id"));
                s.setNom(rst.getString("nom"));
                s.setDescription(rst.getString("description"));
            
                s.setType(rst.getString("type"));
                
                services.add(s);
            }
        
        return services.get(0);

    }
            
            
          public ObservableList<Service> RechercherServiceType(String t) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From service where type='" + t + "'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Service> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                Service s = new Service();
                s.setId(rst.getInt("id"));
                s.setNom(rst.getString("nom"));
                s.setDescription(rst.getString("description"));
            
                s.setType(rst.getString("type"));
                
                services.add(s);
            }

        
        return services;

    }
          
           public ObservableList<StatPrixS> StatPrixService(String t) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "select s.nom,c.nom nn,sc.prix from service s, centre_service sc, centre c where c.id=sc.id_centre and s.id=sc.id_service and s.nom='" + t + "'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<StatPrixS> services = FXCollections.observableArrayList();
            
            while(rst.next()){
                StatPrixS s = new StatPrixS();
                s.setPrix(rst.getFloat("prix"));
                s.setCentre(rst.getString("nn"));
                
                services.add(s);
            }

        
        return services;

    }
    
    
    
}
