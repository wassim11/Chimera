/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.util.Date;

/**
 *
 * @author amira
 */
public class Rendezvous {
    
    private int id ,id_centre,id_client, id_service;
    private String temps;
    
    Date date;
     public void setTemps(String temps) {
        this.temps = temps;
    }

    public int getId() {
        return id;
    }

    public int getId_centre() {
        return id_centre;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId_service() {
        return id_service;
    }

   

    public void setId(int id) {
        this.id = id;
    }

    public void setId_centre(int id_centre) {
        this.id_centre = id_centre;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setId_service(int id_service) {
        this.id_service = id_service;
    }

   

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Rendezvous{" + "id=" + id + ", id_centre=" + id_centre + ", id_client=" + id_client + ", id_service=" + id_service + ", date=" + date + "} \n";
    }

    public String getTemps() {
        return temps;
    }
    
    
    
    
}
