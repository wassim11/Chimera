/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author Nadhir Boussetta
 */
public class Reclamation { 
    private int id;
    private String sujet;
    private String description;
    private String etat;
    private int id_utilisateur;

    public Reclamation() {
    }

    public Reclamation(int id, String sujet, String description, String etat, int id_utilisateur) {
        this.id = id;
        this.sujet = sujet;
        this.description = description;
        this.etat = etat;
        this.id_utilisateur = id_utilisateur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSujet() {
        return sujet;
    }

    public void setSujet(String sujet) {
        this.sujet = sujet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public int getId_utilisateur() {
        return id_utilisateur;
    }

    public void setId_utilisateur(int id_utilisateur) {
        this.id_utilisateur = id_utilisateur;
    }

    public Reclamation(String sujet, String description, String etat, int id_utilisateur) {
        this.sujet = sujet;
        this.description = description;
        this.etat = etat;
        this.id_utilisateur = id_utilisateur;
    }

    @Override
    public String toString() {
        return "Reclamation{" + "id=" + id + ", sujet=" + sujet + ", description=" + description + ", etat=" + etat + ", id_utilisateur=" + id_utilisateur + '}';
    }

    
}
