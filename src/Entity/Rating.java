/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

/**
 *
 * @author Arshavin
 */
public class Rating {

    private Integer id_rating;
    private Double rate;
    private Integer Votes;
    private Integer note;
    private Product products;

    public Rating() {
    }

    public Rating(Integer note) {
        this.note = note;
    }
    
    
            

    public Integer getId_rating() {
        return id_rating;
    }

    public void setId_rating(Integer id_rating) {
        this.id_rating = id_rating;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getVotes() {
        return Votes;
    }

    public void setVotes(Integer Votes) {
        this.Votes = Votes;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
    }

    public Product getProducts() {
        return products;
    }

    public void setProducts(Product products) {
        this.products = products;
    }
    
    
    

    @Override
    public String toString() {
        return "Rating{" + "id_rating=" + id_rating + ", rate=" + rate + '}';
    }

}
