/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.entities.evenements;

import java.sql.Date;

/**
 *
 * @author Ahmed Ezzine
 */
public class Evenement {
    private long idEvent ;
    private String nom ;
    private String type ;
    private Date dateDeb ;
    private Date dateFin ;
    private String lieu ;
    private String description ;
    
    public Evenement() {
        super();
    }

    public Evenement(long idEvent, String nom, String type, Date dateDeb, Date dateFin, String lieu, String description) {
        this.idEvent = idEvent;
        this.nom = nom;
        this.type = type;
        this.dateDeb = dateDeb;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.description = description;
    }

    public long getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(long idEvent) {
        this.idEvent = idEvent;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDateDeb() {
        return dateDeb;
    }

    public void setDateDeb(Date dateDeb) {
        this.dateDeb = dateDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

