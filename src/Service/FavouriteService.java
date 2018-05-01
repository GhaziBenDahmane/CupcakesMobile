/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Cart;
import Entity.Product;
import Entity.Promotion;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Arshavin
 */
public class FavouriteService {

    public static Database db;

    public void createSQLiteDB() {
        try {
            db = Database.openOrCreate("Cupcake");

            db.execute("create table if not "
                    + "exists products (id_product INTEGER PRIMARY KEY  ,name TEXT,category TEXT , description TEXT, price DOUBLE, id_promotion INTEGER , photo1 TEXT);");
            db.execute("create table if not "
                    + "exists promotion (id INTEGER PRIMARY KEY AUTOINCREMENT , discount DOUBLE);");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void insertProductInSQLiteDB(Product product) {
        try {
            db.execute("insert into products ( id_product,name, category,description,price,id_promotion,photo1) values ( '" + product.getId() + "', '" + product.getName() + "', '" + product.getType() + "', '" + product.getDescription() + "', '" + product.getPrice() + "', '" + product.getPromotion().getId_promotion() + "', '" + product.getImage() + "' );");
            System.out.println(product);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void insertPromotionInSQLiteDB(Promotion promotion) {
        try {
            db.execute("insert into promotion ( discount) values ( '" + promotion.getDiscount() + "'); ");
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public Promotion SelectPromotionFromSQLiteDB(int id) {
        Promotion promotion = new Promotion();
        try {
            Cursor c = db.executeQuery("select * from promotion where id=" + id);
            while (c.next()) {
                Row r = c.getRow();
                promotion.setId_promotion(r.getInteger(0));
                promotion.setDiscount(r.getDouble(1));
                // cart.set r.getString(1);

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return promotion;
    }

    public ArrayList SelectProductFromSQLiteDB() {
        ArrayList<Product> products = new ArrayList<>();
        
        try {
            Cursor c = db.executeQuery("select * from products");
            while (c.next()) {
                Product product =new Product();
                Row r = c.getRow();
                product.setId(r.getInteger(0));
                product.setPrice(r.getDouble(4));
                product.setName(r.getString(1));
                product.setDescription(r.getString(3));
                product.setType(r.getString(2));
                Promotion promotion;
                promotion = SelectPromotionFromSQLiteDB(r.getInteger(5));
                product.setPromotion(promotion);
                product.setImage(r.getString(6));
                products.add(product);
                System.out.println("product favourite "+products);

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return products;
    }

    public void deleteProductFromFavouriteInSQLiteDB(int id) {

        try {
            db.execute("delete from products where id_product=" + id);
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    public void closeDB() {
        try {
            db.close();
        } catch (IOException ex) {
        }
    }

}
