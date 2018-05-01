/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import com.codename1.ui.TextField;
import com.codename1.ui.spinner.Picker;
import java.util.Date;

/**
 *
 * @author haffe
 */
public class Event {
    
    private int id;
    private String title;
    private int nbPerson;
    private Date startDate;
    private Date endDate;
    private int nbTable;
    private int band;
    private String status;
    private double cost;

    public Event() {
    }

    public Event(int id,String title, int nbPerson, Date startDate, Date endDate, int nbTable, int band, String status, double cost) {
        this.title = title;
        this.nbPerson = nbPerson;
        this.startDate = startDate;
        this.endDate = endDate;
        this.nbTable = nbTable;
        this.band = band;
        this.status = status;
        this.cost = cost;
    }

   
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(int nbPerson) {
        this.nbPerson = nbPerson;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNbTable() {
        return nbTable;
    }

    public void setNbTable(int nbTable) {
        this.nbTable = nbTable;
    }

    public int getBand() {
        return band;
    }

    public void setBand(int band) {
        this.band = band;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Event{" + "id=" + id + ", title=" + title + ", nbPerson=" + nbPerson + ", startDate=" + startDate + ", endDate=" + endDate + ", nbTable=" + nbTable + ", band=" + band + ", status=" + status + ", cost=" + cost + '}';
    }
    
}
