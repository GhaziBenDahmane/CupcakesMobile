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
public class Cart {

    private Integer id_cart;
    private Product products;

    public Cart(Integer id_cart, Product products) {
        this.id_cart = id_cart;
        this.products = products;
    }
    
    public Cart(Product products) {
        
        this.products = products;
    }


    public Integer getId_cart() {
        return id_cart;
    }

    public void setId_cart(Integer id_cart) {
        this.id_cart = id_cart;
    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "Cart{" + "id_cart=" + id_cart + ", products=" + products + '}';
    }

}
