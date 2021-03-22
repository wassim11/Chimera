/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;


import entities.users.Client;
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
public class ClientService {
     public void ajouterClient(Client clt){
        int typecl =2;
        int enabled=0;
        String hachedMdp = BCrypt.hashpw(clt.getMdp(), BCrypt.gensalt());
        try{
            String req= "INSERT INTO utilisateur (nom,prenom,email,mdp,type,bday,genre,photo)"
                    + "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setString(1, clt.getNom());
            pst.setString(2, clt.getPrenom());
            pst.setString(3, clt.getEmail());
            pst.setString(4, hachedMdp);
            pst.setInt(5, typecl);
            pst.setDate(6, clt.getBday());
            pst.setString(7, clt.getGenre());
            pst.setString(8, clt.getPhoto());
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted>0){
                System.out.println("Client Inseré");
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void supprimerClient(Client clt){
        try{
            String req= "DELETE FROM utilisateur WHERE id=?";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setInt(1, clt.getId());
            int rowsDeleted= pst.executeUpdate();
            if(rowsDeleted>0){
                System.out.println("Client Supprimé");
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void updateClient(Client clt){
        try{
            String req="UPDATE utilisateur SET nom=?,prenom=?,email=?,bday=?,genre=?,photo=? WHERE id=?";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setString(1,clt.getNom());
            pst.setString(2,clt.getPrenom());
            pst.setString(3,clt.getEmail());
            //pst.setString(4,clt.getMdp());
            pst.setDate(4,clt.getBday());
            pst.setString(5,clt.getGenre());
            pst.setString(6,clt.getPhoto());
            pst.setInt(7,clt.getId());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client modifié");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public List<Client> displayClients(){
        List<Client> clientList=new ArrayList<>();
        try{
            String req= "SELECT * FROM utilisateur WHERE type=2 ORDER BY bday";
            Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
            ResultSet rs= st.executeQuery(req);
            while (rs.next()){
                Client clt = new Client();
                clt.setId(rs.getInt("id"));
                clt.setNom(rs.getString("nom"));
                clt.setPrenom(rs.getString("prenom"));
                clt.setEmail(rs.getString("email"));
                clt.setPhoto(rs.getString("photo"));
                clt.setGenre(rs.getString("genre"));
                clt.setBday(rs.getDate("bday"));
                clientList.add(clt);
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return clientList;
    }
    
}
