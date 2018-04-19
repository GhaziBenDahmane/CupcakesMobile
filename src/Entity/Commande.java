/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author Arshavin
 */
public class Commande {

    private Integer id_command;
    private Integer quantity;
    private Double price;

    public Integer getId_command() {
        return id_command;
    }

    public void setId_command(Integer id_command) {
        this.id_command = id_command;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Commande{" + "id_command=" + id_command + ", quantity=" + quantity + ", price=" + price + '}';
    }

}
