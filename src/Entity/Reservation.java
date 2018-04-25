/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.util.Date;

/**
 *
 * @author LENOVO
 */
public class Reservation {
    private int id, nbTable,nbPerson;
    private Date dateReservation;

    public Reservation() {
    }

    public Reservation(int id, int nbTable, int nbPerson, Date dateReservation) {
        this.id = id;
        this.nbTable = nbTable;
        this.nbPerson = nbPerson;
        this.dateReservation = dateReservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNbTable() {
        return nbTable;
    }

    public void setNbTable(int nbTable) {
        this.nbTable = nbTable;
    }

    public int getNbPerson() {
        return nbPerson;
    }

    public void setNbPerson(int nbPerson) {
        this.nbPerson = nbPerson;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    @Override
    public String toString() {
        return "Reservation{" + "id=" + id + ", nbTable=" + nbTable + ", nbPerson=" + nbPerson + ", dateReservation=" + dateReservation + '}';
    }
    
}
