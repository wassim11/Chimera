/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package slowlifejava.entities.RecetteSuivi;

import entities.users.Utilisateur;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Yassine
 */
public class SuiviRegime {
    private int idSuivi;
    private Date dateSuivi;
    private int poid;
    private Float taille;
    private int heursActivite;
    private int consommationEau;
    private float IMC;
    private Utilisateur user;

    public SuiviRegime() {
    }

    public SuiviRegime(int idSuivi) {
        this.idSuivi = idSuivi;
    }

    public SuiviRegime(int poid, Float taille, int heursActivite, int consommationEau) {
        this.poid = poid;
        this.taille = taille;
        this.heursActivite = heursActivite;
        this.consommationEau = consommationEau;
    }

    public SuiviRegime(int idSuivi, int poid, Float taille, int heursActivite, int consommationEau, float IMC) {
        this.idSuivi = idSuivi;
        this.poid = poid;
        this.taille = taille;
        this.heursActivite = heursActivite;
        this.consommationEau = consommationEau;
        this.IMC = IMC;
    }

    public SuiviRegime(Date dateSuivi, int poid, Float taille, int heursActivite, int consommationEau, float IMC) {
        this.dateSuivi = dateSuivi;
        this.poid = poid;
        this.taille = taille;
        this.heursActivite = heursActivite;
        this.consommationEau = consommationEau;
        this.IMC = IMC;
    }

    public SuiviRegime(int idSuivi, Date dateSuivi, int poid, Float taille, int heursActivite, int consommationEau, float IMC) {
        this.idSuivi = idSuivi;
        this.dateSuivi = dateSuivi;
        this.poid = poid;
        this.taille = taille;
        this.heursActivite = heursActivite;
        this.consommationEau = consommationEau;
        this.IMC = IMC;
    }

    public SuiviRegime(Date dateSuivi) {
        this.dateSuivi = dateSuivi;
    }
    
    public SuiviRegime(int idSuivi, Date dateSuivi, int poid, Float taille, int heursActivite, int consommationEau,float IMC, Utilisateur idUser) {
       
        this.idSuivi = idSuivi;
        this.dateSuivi = dateSuivi;
        this.poid = poid;
        this.taille = taille;
        this.heursActivite = heursActivite;
        this.consommationEau = consommationEau;
        this.IMC=IMC;
        this.user=idUser;
    }

    public SuiviRegime(int idSuivi, Date dateSuivi, int poid, Float taille, int heursActivite, int consommationEau, Utilisateur user) {
        this.idSuivi = idSuivi;
        this.dateSuivi = dateSuivi;
        this.poid = poid;
        this.taille = taille;
        this.heursActivite = heursActivite;
        this.consommationEau = consommationEau;
        this.user = user;
    }


    public SuiviRegime(Date dateSuivi, int poid, Float taille, int heursActivite, int consommationEau, Utilisateur idUser) {
        this.dateSuivi = dateSuivi;
        this.poid = poid;
        this.taille = taille;
        this.heursActivite = heursActivite;
        this.consommationEau = consommationEau;
        this.user = idUser;
    }

    public SuiviRegime(SuiviRegime readone) {
        this.poid=readone.getPoid();
        this.taille=readone.getTaille();
        this.consommationEau=readone.getConsommationEau();
        this.heursActivite=readone.getHeursActivite();
//To change body of generated methods, choose Tools | Templates.
    }

    public float getIMC() {
        return IMC;
    }

    public void setIMC(float IMC) {
        this.IMC = IMC;
    }

    public int getIdSuivi() {
        return idSuivi;
    }

    public Date getDateSuivi() {
        return dateSuivi;
    }

    public int getPoid() {
        return poid;
    }

    public Float getTaille() {
        return taille;
    }
    public int getHeursActivite() {
        return heursActivite;
    }

    public int getConsommationEau() {
        return consommationEau;
    }

    public Utilisateur getUser() {
        return user;
    }

    public void setIdSuivi(int idSuivi) {
        this.idSuivi = idSuivi;
    }

    public void setDateSuivi(Date dateSuivi) {
        this.dateSuivi = dateSuivi;
    }

    public void setPoid(int poid) {
        this.poid = poid;
    }

    public void setTaille(Float taille) {
        this.taille = taille;
    }


    public void setHeursActivite(int heursActivite) {
        this.heursActivite = heursActivite;
    }

    public void setConsommationEau(int consommationEau) {
        this.consommationEau = consommationEau;
    }

    public void User(Utilisateur idUser) {
        this.user = idUser;
    }

    @Override
    public String toString() {
        return "SuiviRegime{" + "idSuivi=" + idSuivi + ", dateSuivi=" + dateSuivi + ", poid=" + poid + ", taille=" + taille + ", heursActivite=" + heursActivite + ", consommationEau=" + consommationEau + ", IMC=" + IMC + ", user=" + user + '}';
    }

    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SuiviRegime other = (SuiviRegime) obj;
        return Objects.equals(this.dateSuivi, other.dateSuivi);
    }

}
