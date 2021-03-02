/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.entities.evenements;

/**
 *
 * @author Ahmed Ezzine
 */
public class Activite {
    private long idActivite ;
    private long idEvent ;
    private String nom ;
    private String type ;
    private String description ;
    private long duree ;
    
    private String nomEvent;

    public Activite(long idActivite, long idEvent, String nom, String type, String description, long duree) {
        this.idActivite = idActivite;
        this.idEvent = idEvent;
        this.nom = nom;
        this.type = type;
        this.description = description;
        this.duree = duree;
    }

    public Activite() {
        super();
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public long getIdActivite() {
        return idActivite;
    }

    public void setIdActivite(long idActivite) {
        this.idActivite = idActivite;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDuree() {
        return duree;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }

    public String getNomEvent() {
        return nomEvent;
    }

    public void setNomEvent(String nomEvent) {
        this.nomEvent = nomEvent;
    }
}
