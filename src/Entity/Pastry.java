/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author haffe
 */
public class Pastry {
    private int id;
    private String adress ;
    private int nbTable;

    public Pastry(int id, String adress, int nbTable) {
        this.id = id;
        this.adress = adress;
        this.nbTable = nbTable;
    }

    @Override
    public String toString() {
        return "Pastry{" + "id=" + id + ", adress=" + adress + ", nbTable=" + nbTable + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getNbTable() {
        return nbTable;
    }

    public void setNbTable(int nbTable) {
        this.nbTable = nbTable;
    }
    
}
