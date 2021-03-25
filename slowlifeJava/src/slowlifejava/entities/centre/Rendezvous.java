/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.entities.centre;


import entities.users.Utilisateur;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author amira
 */
public class Rendezvous {
    
    private int id;
    private String temps;
    private Centre centre;
    private Utilisateur client;
    private Service service;

    public Centre getCentre() {
        return centre;
    }

    public Utilisateur getClient() {
        return client;
    }

    public Service getService() {
        return service;
    }

    public void setCentre(Centre centre) {
        this.centre = centre;
    }

    public void setClient(Utilisateur client) {
        this.client = client;
    }

    public void setService(Service service) {
        this.service = service;
    }
   
    
    
    Date date;
     public void setTemps(String temps) {
        this.temps = temps;
    }

    public int getId() {
        return id;
    }

    
   

    public void setId(int id) {
        this.id = id;
    }

  
   

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

   
    public String getTemps() {
        return temps;
    }
    
    
    
    
}
