/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.evenements;

import slowlifejava.entities.evenements.Evenement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import slowlifejava.utils.SlowlifeDB;

/**
 *
 * @author Ahmed Ezzine
 */
public class EvenementService {
    private Connection cnx;
    
    public EvenementService() {
        cnx = SlowlifeDB.getInstance().getConnection();
    }
    
    public void ajouter(Evenement e) {
        try {
            String request="INSERT INTO evenement(nom,lieu,type,Description,date_debut,date_fin) VALUES(?,?,?,?,?,?)";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setString(1, e.getNom());
            pre.setString(2, e.getLieu());
            pre.setString(3, e.getType());            
            pre.setString(4, e.getDescription());
            pre.setDate(5, e.getDateDeb());
            pre.setDate(6, e.getDateFin());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public ArrayList<Evenement> getAll(){
        ArrayList<Evenement> evenements = new ArrayList<>();
        try {
            String request="SELECT * FROM evenement";
            Statement ste=cnx.createStatement();
            ResultSet result = ste.executeQuery(request);
            while (result.next()) {
                Evenement e = new Evenement();
                e.setIdEvent(result.getLong("id"));
                e.setNom(result.getString("nom"));
                e.setLieu(result.getString("lieu"));
                e.setType(result.getString("type"));
                e.setDescription(result.getString("Description"));
                e.setDateDeb(result.getDate("date_debut"));
                e.setDateFin(result.getDate("date_fin"));
                evenements.add(e);
            }
        } catch(SQLException ex) {
            System.out.println(ex);
        }
        return evenements;
    }
    
    public void supprimer(Evenement e) {
        try {
            String request="DELETE FROM evenement WHERE id = ?";
            PreparedStatement pre = cnx.prepareStatement(request);
            pre.setLong(1, e.getIdEvent());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void modifier (Evenement e){
        try {
            String request="UPDATE evenement SET nom = ? , lieu = ? , type = ? , Description = ? , date_debut = ? , date_fin = ? WHERE id = ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setString(1, e.getNom());
            pre.setString(2, e.getLieu());
            pre.setString(3, e.getType());            
            pre.setString(4, e.getDescription());
            pre.setDate(5, e.getDateDeb());
            pre.setDate(6, e.getDateFin());
            pre.setLong(7, e.getIdEvent());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
