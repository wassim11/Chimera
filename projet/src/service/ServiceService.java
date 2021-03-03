/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entities.Service;
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
import utils.MaConnexion;

/**
 *
 * @author amira
 */
public class ServiceService {
    
    
      
    Connection cnx;

    public ServiceService() {
        cnx = MaConnexion.getInstance().getCnx();
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
    
}
