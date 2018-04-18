/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.sql.Date;

/**
 *
 * @author Arshavin
 */
public class Promotion {

    public Promotion(Date starting_date, Date ending_date) {
        this.starting_date = starting_date;
        this.ending_date = ending_date;
    }

    private Integer id_promotion;
    private Double Discount;
    private Date starting_date;
    private Date ending_date;

    public Promotion(Double Discount, Date starting_date, Date ending_date) {
        this.Discount = Discount;
        this.starting_date = starting_date;
        this.ending_date = ending_date;
    }

    public Promotion(Integer id_promotion, Double Discount, Date starting_date, Date ending_date) {
        this.id_promotion = id_promotion;
        this.Discount = Discount;
        this.starting_date = starting_date;
        this.ending_date = ending_date;
    }

    public Promotion() {
    }

    public Integer getId_promotion() {
        return id_promotion;
    }

    public void setId_promotion(Integer id_promotion) {
        this.id_promotion = id_promotion;
    }

    public Double getDiscount() {
        return Discount;
    }

    public void setDiscount(Double Discount) {
        this.Discount = Discount;
    }

    public Date getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(Date starting_date) {
        this.starting_date = starting_date;
    }

    public Date getEnding_date() {
        return ending_date;
    }

    public void setEnding_date(Date ending_date) {
        this.ending_date = ending_date;
    }

    @Override
    public String toString() {
        return "Promotion{" + "id_promotion=" + id_promotion + ", Discount=" + Discount + ", starting_date=" + starting_date + ", ending_date=" + ending_date + '}';
    }

}
