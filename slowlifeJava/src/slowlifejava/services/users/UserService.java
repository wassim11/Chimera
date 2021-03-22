/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.users;

import entities.users.Coach;
import entities.users.Utilisateur;
import java.sql.Date;
import utils.users.BCrypt;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import slowlifejava.utils.SlowlifeDB;

/**
 *
 * @author zaefdfyjhlk
 */
public class UserService {

    public void changePassword(String mdp, String email) throws SQLException {
        String hachedMdp = BCrypt.hashpw(mdp, BCrypt.gensalt());
        String req = "UPDATE utilisateur SET mdp = ?  WHERE email = ?";
        PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
        pst.setString(1, hachedMdp);
        pst.setString(2, email);
        int rowUpdated = pst.executeUpdate();
        if (rowUpdated > 0) {
            System.out.println("Mdp modifié");
        } else {
            System.out.println("ERR");
        }
    }

    public void setEnabled(Utilisateur u) throws SQLException {
        int enabled = 1;
        String req = "UPDATE utilisateur SET enabled=? WHERE id=?";
        PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
        pst.setInt(1, enabled);
        pst.setInt(2, u.getId());
        int rowsUpdated = pst.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Etat modifié");
        }
    }

    public Utilisateur getUserById(int id) throws SQLException {
        Utilisateur u = null;
        String req = "SELECT * FROM utilisateur WHERE id = ?";
        PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            u = new Utilisateur();
            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setType(rs.getInt("type"));
            u.setMdp(rs.getString("mdp"));
            u.setBday(rs.getDate("bday"));
            u.setGenre(rs.getString("genre"));
            u.setPhoto(rs.getString("photo"));

        }
        return u;
    }

    public Utilisateur searchByPseudoPassU(String email, String mdp) throws SQLException {
        Utilisateur u = null;
        String req = "SELECT * FROM utilisateur WHERE email = ?";
        PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            u = new Utilisateur();
            u = UserService.this.getUserById(rs.getInt("id"));
            if (BCrypt.checkpw(u.getMdp(), BCrypt.hashpw(mdp, BCrypt.gensalt()))) {
                return u;
            }
        }
        return u;
    }

    public boolean searchByPseudoPass(String email, String mdp) throws SQLException {
        //fos_user u = null;
        String hachedMdp = BCrypt.hashpw(mdp, BCrypt.gensalt());
        boolean log = false;
        String req = "SELECT * FROM utilisateur WHERE ((email = ? and mdp = ?))";
        PreparedStatement pst =SlowlifeDB.getInstance().getConnection().prepareStatement(req);
        pst.setString(1, email);
        pst.setString(2, hachedMdp);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            log = true;
        }
        return log;
    }

    public Utilisateur getUserByEmail(String email) throws SQLException {
        Utilisateur u = null;
        String req = "SELECT * FROM utilisateur WHERE email = ?";
        PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
        pst.setString(1, email);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            u = new Utilisateur();

            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setMdp(rs.getString("mdp"));
            u.setGenre(rs.getString("genre"));
            u.setPhoto(rs.getString("photo"));
            u.setType(rs.getInt("type"));
            u.setBday(rs.getDate("bday"));

            System.out.println("Utilisateur trouvé !");
            System.out.println(u);

        }
        return u;
    }

    public void supprimerUser(int id) {
        try {
            String req = "DELETE FROM utilisateur WHERE id=?";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setInt(1, id);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Utilisateur Supprimé");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Utilisateur> displayUsers() {
        List<Utilisateur> userList = new ArrayList<>();
        try {
            String req = "SELECT * FROM utilisateur ORDER BY bday";
            Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMdp(rs.getString("mdp"));
                u.setPhoto(rs.getString("photo"));
                u.setGenre(rs.getString("genre"));
                u.setType(rs.getInt("type"));
                u.setBday(rs.getDate("bday"));

                userList.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userList;
    }

    public List<Utilisateur> RechercheUsers(String rech) {
        List<Utilisateur> userList = new ArrayList<>();
        try {
            String req = "SELECT * FROM utilisateur WHERE nom LIKE '%" + rech + "%' OR prenom LIKE '%" + rech + "%' OR domaine LIKE '%" + rech + "%'  ";
            Statement st =SlowlifeDB.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMdp(rs.getString("mdp"));
                u.setPhoto(rs.getString("photo"));
                u.setGenre(rs.getString("genre"));
                u.setType(rs.getInt("type"));
                u.setBday(rs.getDate("bday"));

                userList.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userList;
    }

    public void loggedIn(Utilisateur u) {
        String hachedMdp = BCrypt.hashpw(u.getMdp(), BCrypt.gensalt());
        try {
            String req = "INSERT INTO logged (id,nom,prenom,email,mdp,type,bday,genre,photo)"
                    + "VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            pst.setInt(1, u.getId());
            pst.setString(2, u.getNom());
            pst.setString(3, u.getPrenom());
            pst.setString(4, u.getEmail());
            pst.setString(5, hachedMdp);
            pst.setInt(6, u.getType());
            pst.setDate(7, u.getBday());
            pst.setString(8, u.getGenre());
            pst.setString(9, u.getPhoto());
            //pst.setString(9, u.getDomaine());
            int rowsInserted = pst.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("user logged in");
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public Utilisateur getUserlogged() throws SQLException {
        Utilisateur u = null;
        String req = "SELECT * FROM logged ";
        Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            u = new Utilisateur();

            u.setId(rs.getInt("id"));
            u.setNom(rs.getString("nom"));
            u.setPrenom(rs.getString("prenom"));
            u.setEmail(rs.getString("email"));
            u.setMdp(rs.getString("mdp"));
            u.setGenre(rs.getString("genre"));
            u.setPhoto(rs.getString("photo"));
            u.setType(rs.getInt("type"));
            u.setBday(rs.getDate("bday"));
            System.out.println("Utilisateur trouvé !");
            System.out.println(u);
        }
        return u;
    }

    public void loggedOut() {
        try {
            String req = "DELETE FROM logged ";
            PreparedStatement pst = SlowlifeDB.getInstance().getConnection().prepareStatement(req);
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("user logged out");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ObservableList<String> coachByDomain() throws SQLException {
        ObservableList<String> domainList = FXCollections.observableArrayList();
        String req = "SELECT domaine from utilisateur WHERE domaine IS NOT NULL GROUP BY domaine";
        Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            String d = rs.getString("domaine");
            domainList.add(d);
        }
        return domainList;
    }

    public List<Coach> RechercheByDomain(String rech) {
        List<Coach> userList = new ArrayList<>();
        try {
            String req = "SELECT * FROM utilisateur WHERE domaine LIKE '" + rech + "'";
            Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Coach u = new Coach();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMdp(rs.getString("mdp"));
                u.setPhoto(rs.getString("photo"));
                u.setGenre(rs.getString("genre"));
                u.setType(rs.getInt("type"));
                u.setDomaine(rs.getString("domaine"));
                u.setBday(rs.getDate("bday"));
                userList.add(u);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return userList;
    }

    public Map nbUserByAge() throws SQLException {
        Map<Long, Integer> map = new HashMap<>();
        ArrayList<Long>years=new ArrayList<>();
        ArrayList<Integer>nbs=new ArrayList<>();
        String req = "SELECT YEAR(bday) AS year, COUNT(*) AS nb FROM utilisateur GROUP BY YEAR(bday)";
        Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            //map.put(rs.getLong("year"), rs.getInt("nb"));
            years.add(rs.getLong("year"));
            nbs.add(rs.getInt("nb"));
        }
        return map;
    }

    public Map nbUserByType() throws SQLException {
        Map<Long, Integer> map = new HashMap<>();
        String req = "SELECT type, COUNT(*) AS nb FROM utilisateur WHERE type != 1 GROUP BY type";
        Statement st = SlowlifeDB.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            map.put(rs.getLong("type"), rs.getInt("nb"));
        }
        return map;

    }
    public Map nbCoachByDomain() throws SQLException {
        Map<Long, Integer> map = new HashMap<>();
        String req = "SELECT domaine, COUNT(*) AS nb FROM utilisateur WHERE type =3 GROUP BY domaine";
        Statement st =SlowlifeDB.getInstance().getConnection().createStatement();
        ResultSet rs = st.executeQuery(req);
        while (rs.next()) {
            map.put(rs.getLong("domaine"), rs.getInt("nb"));
        }
        return map;

    }
    

}
