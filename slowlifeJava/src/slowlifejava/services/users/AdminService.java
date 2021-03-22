/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;

import entities.users.Admin;
import utils.users.BCrypt;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import slowlifejava.utils.SlowlifeDB;

/**
 *
 * @author zaefdfyjhlk
 */
public class AdminService {
    public void ajouterAdmin(Admin ad){
        int typea =1;
        int enabled=0;
        String hachedMdp = BCrypt.hashpw(ad.getMdp(), BCrypt.gensalt());
        try{
            String req= "INSERT INTO utilisateur (nom,prenom,email,mdp,type,bday,genre,photo,enabled)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setString(1, ad.getNom());
            pst.setString(2, ad.getPrenom());
            pst.setString(3, ad.getEmail());
            pst.setString(4, hachedMdp);
            pst.setInt(5, typea);
            pst.setDate(6, ad.getBday());
            pst.setString(7, ad.getGenre());
            pst.setString(8, ad.getPhoto());
            pst.setInt(9, enabled);
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted>0){
                System.out.println("Admin Inseré");
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void supprimerAdmin(Admin ad){
        try{
            String req= "DELETE FROM utilisateur WHERE id=?";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setInt(1, ad.getId());
            int rowsDeleted= pst.executeUpdate();
            if(rowsDeleted>0){
                System.out.println("Admin Supprimé");
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void updateAdmin(Admin ad){
        try{
            String req="UPDATE utilisateur SET nom=?,prenom=?,email=?,mdp=?,bday=?,genre=?,photo=? WHERE id=?";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setString(1,ad.getNom());
            pst.setString(2,ad.getPrenom());
            pst.setString(3,ad.getEmail());
            pst.setString(4,ad.getMdp());
            pst.setDate(5, ad.getBday());
            pst.setString(6,ad.getGenre());
            pst.setString(7,ad.getPhoto());
            pst.setInt(8,ad.getId());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Admin modifié");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public List<Admin> displayAdmins(){
        List<Admin> adminList=new ArrayList<>();
        try{
            String req= "SELECT * FROM utilisateur WHERE type=1";
            Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
            ResultSet rs= st.executeQuery(req);
            while (rs.next()){
                Admin ad = new Admin();
                ad.setId(rs.getInt("id"));
                ad.setNom(rs.getString("nom"));
                ad.setPrenom(rs.getString("prenom"));
                ad.setEmail(rs.getString("email"));
                ad.setPhoto(rs.getString("photo"));
                ad.setGenre(rs.getString("genre"));
                ad.setBday(rs.getDate("bday"));
                adminList.add(ad);
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return adminList;
    }
    
    
    
}
