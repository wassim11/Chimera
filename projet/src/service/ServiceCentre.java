/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import Entities.Centre;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
public class ServiceCentre {

    Connection cnx;

    public ServiceCentre() {
        cnx = MaConnexion.getInstance().getCnx();
    }

    
    
    
    public void ajouterCentre(Centre c) {
        try {
            Statement stm = cnx.createStatement();

            String query = "INSERT INTO centre (nom,description,lieu,image,type)"
                    + "VALUES ('" + c.getNom() + "','" + c.getDescription() + "','" + c.getLieu() + "','" + c.getImage() + "','" + c.getType() + "')";

            stm.executeUpdate(query);
            //System.out.println("centre ajouté");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    
    
    
    
    public ObservableList<Centre> afficherCentre() throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres;

    }
    
    
    
     public void supprimerCentre(int id) {
        try {
            Statement stm = cnx.createStatement();

            String query = "Delete FROM centre WHERE id='" + id + "' ";

            stm.executeUpdate(query);
            //System.out.println("centre supprime");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
     
     
     public void modifierCentre(Centre c,int idd) {
        try {
            

//            String query = "UPDATE centre SET nom=? , description=? , lieu=? , image=? WHERE id=?";
//              PreparedStatement stm = cnx.prepareStatement(query);
//              String n=c.getNom(), d=c.getDescription(), l=c.getLieu() , i=c.getImage();
//              stm.setString(1, n);
//              stm.setString(2, d);
//              stm.setString(3, l);
//              stm.setString(4, i);
//              stm.setInt(5, idd);
//            stm.executeUpdate(query);
                //System.out.println("in");
                Statement stm = cnx.createStatement();

             String query = "UPDATE centre SET nom= '" + c.getNom() + "' , description='" + c.getDescription() + "' , lieu='" + c.getLieu() + "' , image='" + c.getImage() + "' , type='" + c.getType() + "' WHERE id='" + idd + "'";
             
              stm.executeUpdate(query);
           

        } catch (SQLException ex) {
            Logger.getLogger(ServiceCentre.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
     
     
     
      public Centre afficherCentreDetail(int idd) throws SQLException {
        
            Statement stm = cnx.createStatement();

            String query = "SELECT * From centre WHERE id='" + idd + "'";

            ResultSet rst = stm.executeQuery(query);

            ObservableList<Centre> centres= FXCollections.observableArrayList();
            
            while(rst.next()){
                Centre c = new Centre();
                c.setId(rst.getInt("id"));
                c.setNom(rst.getString("nom"));
                c.setDescription(rst.getString("description"));
                c.setLieu(rst.getString("lieu"));
                c.setImage(rst.getString("image"));
                c.setType(rst.getString("type"));
                centres.add(c);
            }

        
        return centres.get(0);

    }

}
