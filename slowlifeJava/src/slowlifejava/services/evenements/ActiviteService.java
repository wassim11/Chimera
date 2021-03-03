/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package slowlifejava.services.evenements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import slowlifejava.entities.evenements.Activite;
import slowlifejava.utils.SlowlifeDB;

/**
 *
 * @author Ahmed Ezzine
 */
public class ActiviteService {
    
    private Connection cnx;
    
    public ActiviteService() {
        cnx = SlowlifeDB.getInstance().getConnection();
    }
    
    public void ajouter(Activite a) {
        try {
            String request="INSERT INTO activite(idEvent,nom,type,Description,duree) VALUES(?,?,?,?,?)";
            PreparedStatement pre=cnx.prepareStatement(request);
            
            pre.setLong(1, a.getIdEvent());
            pre.setString(2, a.getNom());
            pre.setString(3, a.getType());
            pre.setString(4, a.getDescription());            
            pre.setLong(5, a.getDuree());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    public ArrayList<Activite> getAll(){
        ArrayList<Activite> activites = new ArrayList<>();
        try {
            String request="SELECT a.id, a.idEvent, a.nom, a.type, a.Description, a.duree, e.nom FROM activite a INNER JOIN evenement e WHERE a.idEvent = e.id";
            Statement ste=cnx.createStatement();
            ResultSet result = ste.executeQuery(request);
            while (result.next()) {
                Activite a = new Activite();
                a.setIdActivite(result.getLong("a.id"));
                a.setIdEvent(result.getLong("a.idEvent"));
                a.setNom(result.getString("a.nom"));
                a.setType(result.getString("a.type"));
                a.setDescription(result.getString("a.Description"));
                a.setDuree(result.getInt("a.duree"));
                a.setNomEvent(result.getString("e.nom"));
                activites.add(a);
            }
        } catch(SQLException ex) {
            System.out.println(ex);
        }
        return activites;
    }
        
    public void supprimer(Activite a) {
        try {
            String request="DELETE FROM activite WHERE id = ?";
            PreparedStatement pre = cnx.prepareStatement(request);
            pre.setLong(1, a.getIdActivite());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
            
    public void modifier (Activite a){
        try {
            String request="UPDATE activite SET idEvent = ? , nom = ? , type = ? , Description = ? , duree = ? WHERE id= ?";
            PreparedStatement pre=cnx.prepareStatement(request);
            pre.setLong(1, a.getIdEvent());
            pre.setString(2, a.getNom());
            pre.setString(3, a.getType());            
            pre.setString(4, a.getDescription());
            pre.setLong(5, a.getDuree());
            pre.setLong(6,a.getIdActivite());
            pre.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
}