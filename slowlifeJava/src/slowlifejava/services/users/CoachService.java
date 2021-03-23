/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.services.users;

import entities.users.Coach;
import slowlifejava.utils.users.BCrypt;

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
public class CoachService {
    public void ajouterCoach(Coach ch){
        int typech =3;
        int enabled=0;
        String hachedMdp = BCrypt.hashpw(ch.getMdp(), BCrypt.gensalt());
        try{
            String req= "INSERT INTO utilisateur (nom,prenom,email,mdp,type,bday,genre,photo,domaine)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setString(1, ch.getNom());
            pst.setString(2, ch.getPrenom());
            pst.setString(3, ch.getEmail());
            pst.setString(4, hachedMdp);
            pst.setInt(5, typech);
            pst.setDate(6,ch.getBday());
            pst.setString(7, ch.getGenre());
            pst.setString(8, ch.getPhoto());
            pst.setString(9, ch.getDomaine());
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted>0){
                System.out.println("Coach Inseré");
            }
            
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void supprimerCoach(Coach ch){
        try{
            String req= "DELETE FROM utilisateur WHERE id=?";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setInt(1, ch.getId());
            int rowsDeleted= pst.executeUpdate();
            if(rowsDeleted>0){
                System.out.println("Coach Supprimé");
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public void updateCoach(Coach ch){
        try{
            String req="UPDATE utilisateur SET nom=?,prenom=?,email=?,bday=?,genre=?,photo=?,domaine=? WHERE id=?";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setString(1,ch.getNom());
            pst.setString(2,ch.getPrenom());
            pst.setString(3,ch.getEmail());
            //pst.setString(4,ch.getMdp());
            pst.setDate(4, ch.getBday());
            pst.setString(5,ch.getGenre());
            pst.setString(6,ch.getPhoto());
            pst.setString(7,ch.getDomaine());
            pst.setInt(8,ch.getId());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Coach modifié");
            }
        }catch(SQLException ex){
            System.out.println(ex.getMessage());
        }
    }
    public List<Coach> displayCoachs(){
        List<Coach> coachList=new ArrayList<>();
        try{
            String req= "SELECT * FROM utilisateur WHERE type=3 ORDER BY bday";
            Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
            ResultSet rs= st.executeQuery(req);
            while (rs.next()){
                Coach ch = new Coach();
                ch.setId(rs.getInt("id"));
                ch.setNom(rs.getString("nom"));
                ch.setPrenom(rs.getString("prenom"));
                ch.setEmail(rs.getString("email"));
                ch.setPhoto(rs.getString("photo"));
                ch.setGenre(rs.getString("genre"));
                ch.setBday(rs.getDate("bday"));
                ch.setDomaine(rs.getString("domaine"));
                coachList.add(ch);
            }
        }catch (SQLException ex){
            System.out.println(ex.getMessage());
        }
        return coachList;
    }
}
