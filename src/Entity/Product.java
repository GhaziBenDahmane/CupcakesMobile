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
public class Product{

    private Integer id;
    private String name;
    private String type;
    private Integer barcode;
    private Double price;
    private Integer nb_view;
    private Integer nb_seller;
    private String Photo;
    private String Description;
    private Promotion promotion;
    private String image;

    public Product() {
    }

    public Product(Integer id, String name, String Type, Double price, Integer nb_view, Integer nb_seller, String Photo, String Description, Integer barcode, Promotion promotion) {
        this.id = id;
        this.name = name;
        this.type = Type;
        this.price = price;
        this.nb_view = nb_view;
        this.nb_seller = nb_seller;
        this.Photo = Photo;
        this.Description = Description;
        this.barcode = barcode;
        this.promotion = promotion;
    }

    public Product(Integer id, String name, String Type, Double price, Integer nb_view, Integer nb_seller, String Photo, String Description, Integer barcode, Promotion promotion, String Image) {
        this.id = id;
        this.name = name;
        this.type = Type;
        this.price = price;
        this.nb_view = nb_view;
        this.nb_seller = nb_seller;
        this.Photo = Photo;
        this.Description = Description;
        this.barcode = barcode;
        this.promotion = promotion;
        this.image = image;
    }

    public Product(String name, String Type, Double price, Integer nb_view, Integer nb_seller, String Photo, String Description, Integer barcode) {

        this.name = name;
        this.type = Type;
        this.price = price;
        this.nb_view = nb_view;
        this.nb_seller = nb_seller;
        this.Photo = Photo;
        this.Description = Description;
        this.barcode = barcode;

    }

    public Product(String name, String Type, Double price, Integer nb_view, Integer nb_seller, String Photo, String Description, Integer barcode, Promotion promotion) {

        this.name = name;
        this.type = Type;
        this.price = price;
        this.nb_view = nb_view;
        this.nb_seller = nb_seller;
        this.Photo = Photo;
        this.Description = Description;
        this.barcode = barcode;
        this.promotion = promotion;
    }

    public Product(String name, String Type, Double price, String Photo, String Description) {

        this.name = name;
        this.type = Type;
        this.price = price;
        this.Photo = Photo;
        this.Description = Description;
        this.nb_seller = 0;
        this.nb_view = 0;
    }

    public Product(Integer id, Integer barcode, String name, String type, Double price, String Description) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.type = type;
        this.price = price;
        this.Description = Description;

    }

    public Product(Integer id, Integer barcode, String name, String type, Double price, String Description, Promotion promotion) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.type = type;
        this.price = price;
        this.Description = Description;
        this.promotion = promotion;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBarcode() {
        return barcode;
    }

    public void setBarcode(Integer barcode) {
        this.barcode = barcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String Type) {
        this.type = Type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getNb_view() {
        return nb_view;
    }

    public void setNb_view(Integer nb_view) {
        this.nb_view = nb_view;
    }

    public Integer getNb_seller() {
        return nb_seller;
    }

    public void setNb_seller(Integer nb_seller) {
        this.nb_seller = nb_seller;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String Photo) {
        this.Photo = Photo;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", name=" + name + ", type=" + type + ", barcode=" + barcode + ", price=" + price + ", nb_view=" + nb_view + ", nb_seller=" + nb_seller + ", Photo=" + Photo + ", Description=" + Description + ", promotion=" + promotion + ", image=" + image + '}';
    }

}
