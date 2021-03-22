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
public class Admin extends Utilisateur{

    public Admin() {
        
    }

    public Admin(int id, String nom, String prenom, String email, String mdp, String photo, int type, String genre, Date bday) {
        super(id, nom, prenom, email, mdp, photo, type, genre, bday);
    }

    public Admin(String nom, String prenom, String email, String mdp, String photo, int type, String genre, Date bday) {
        super(nom, prenom, email, mdp, photo, type, genre, bday);
    }

    public Admin(String nom, String prenom, String email, String mdp, String photo, int type, String genre) {
        super(nom, prenom, email, mdp, photo, type, genre);
    }

    public Admin(String nom, String prenom, String email, String mdp, String photo, String genre) {
        super(nom, prenom, email, mdp, photo, genre);
    }
    

    public Admin(String nom, String prenom, String email, String photo, int type, String genre, Date bday) {
        super(nom, prenom, email, photo, type, genre, bday);
    }

    public Admin(int id, String nom, String prenom, String email, String photo, int type, String genre, Date bday) {
        super(id, nom, prenom, email, photo, type, genre, bday);
    }

    
    
    
    

    
    

    
    
    
}
