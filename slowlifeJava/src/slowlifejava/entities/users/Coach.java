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
public class Coach extends Utilisateur{
    private String domaine;

    public Coach() {
    }
    

    public Coach(String domaine) {
        this.domaine = domaine;
    }

    public Coach(String domaine, int id, String nom, String prenom, String email, String mdp, String photo, int type, String genre, Date bday) {
        super(id, nom, prenom, email, mdp, photo, type, genre, bday);
        this.domaine = domaine;
    }

    public Coach(String domaine, String nom, String prenom, String email, String mdp, String photo, int type, String genre, Date bday) {
        super(nom, prenom, email, mdp, photo, type, genre, bday);
        this.domaine = domaine;
    }

    public Coach(String domaine, String nom, String prenom, String email, String mdp, String photo, int type, String genre) {
        super(nom, prenom, email, mdp, photo, type, genre);
        this.domaine = domaine;
    }

    public Coach(String domaine, String nom, String prenom, String email, String mdp, String photo, String genre) {
        super(nom, prenom, email, mdp, photo, genre);
        this.domaine = domaine;
    }

    public Coach(String domaine, String nom, String prenom, String email, String photo, int type, String genre, Date bday) {
        super(nom, prenom, email, photo, type, genre, bday);
        this.domaine = domaine;
    }

    public Coach(String domaine, int id, String nom, String prenom, String email, String photo, int type, String genre, Date bday) {
        super(id, nom, prenom, email, photo, type, genre, bday);
        this.domaine = domaine;
    }
    public String getDomaine() {
        return domaine;
    }

    public void setDomaine(String domaine) {
        this.domaine = domaine;
    }

    @Override
    public String toString() {
        return "Coach{"+ super.toString() + "domaine=" + domaine + '}';
    }

    
    
    
}
