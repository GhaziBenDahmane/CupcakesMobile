/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author asus
 */
public class Formation {

    int id;
    String nom;
    String video;
    Date start_date_formation;
    Date end_date_formation;
    String status;

    public Formation() {
    }

    public Formation(int id, String nom, String video, String status) {
        this.id = id;
        this.nom = nom;
        this.video = video;
        
     
        this.status = status;
    }

    public Formation(String nom, String video, String status) {
        this.nom = nom;
        this.video = video;
        
        this.status = status;
    }

    public Date getStart_date_formation() {
        return start_date_formation;
    }

    public void setStart_date_formation(Date start_date_formation) {
        this.start_date_formation = start_date_formation;
    }

    public Date getEnd_date_formation() {
        return end_date_formation;
    }

    public void setEnd_date_formation(Date end_date_formation) {
        this.end_date_formation = end_date_formation;
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

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /*@Override
    public String toString() {
        return "Formation{" + "id=" + id + ", nom=" + nom + ", video=" + video + ", status=" + status + '}';
    }*/

   @Override
    public String toString() {
        return "Formation{" + "id=" + id + ", nom=" + nom + ", video=" + video + ", start_date_formation=" + start_date_formation + ", end_date_formation=" + end_date_formation + ", status=" + status + '}';
    }
    
    

}
