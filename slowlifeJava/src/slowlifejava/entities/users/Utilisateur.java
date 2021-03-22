/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities.users;

import java.time.LocalDate;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author zaefdfyjhlk
 */
public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private String photo;
    //Type : 1: Admin 2: Client 3: Coach
    private int type;
    private String genre;
    private Date bday;

    public Utilisateur() {
    }
    

    public Utilisateur(int id, String nom, String prenom, String email, String mdp, String photo, int type, String genre, Date bday) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.photo = photo;
        this.type = type;
        this.genre = genre;
        this.bday = bday;
    }

    public Utilisateur(String nom, String prenom, String email, String mdp, String photo, int type, String genre, Date bday) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.photo = photo;
        this.type = type;
        this.genre = genre;
        this.bday = bday;
    }

    public Utilisateur(String nom, String prenom, String email, String mdp, String photo, int type, String genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.photo = photo;
        this.type = type;
        this.genre = genre;
    }

    public Utilisateur(String nom, String prenom, String email, String mdp, String photo, String genre) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.photo = photo;
        this.genre = genre;
    }

    public Utilisateur(String nom, String prenom, String email, String photo, int type, String genre, Date bday) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.type = type;
        this.genre = genre;
        this.bday = bday;
    }

    public Utilisateur(int id, String nom, String prenom, String email, String photo, int type, String genre, Date bday) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.photo = photo;
        this.type = type;
        this.genre = genre;
        this.bday = bday;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getBday() {
        return bday;
    }

    public void setBday(Date bday) {
        this.bday = bday;
    }

    
    @Override
    public String toString() {
        return "Utilisateur{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", mdp=" + mdp + ", photo=" + photo + ", type=" + type + ", genre=" + genre + ", bday=" + bday + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Utilisateur other = (Utilisateur) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
    
}
