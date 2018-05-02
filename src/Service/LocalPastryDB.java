/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import Entity.Pastry;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author haffe
 */
public class LocalPastryDB {
        public static Database db ;
public void createSQLiteDB() {
        try {
            db = Database.openOrCreate("Cupcake");

            db.execute("create table if not "
                    + "exists pastry (id_pastry INTEGER PRIMARY KEY  ,address TEXT,nb_table TEXT ,lat DOUBLE, lon DOUBLE );");
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public void insertInSQLiteDB(Pastry p, Double lat , Double lon) throws IOException {
       
            db.execute("insert into pastry ( id_pastry,address, nb_table,lat,lon) values ( '" + p.getId() + "', '" + p.getAdress() + "','"+p.getNbTable()+"', '" + lat + "', '" + lon + "' );");
        
    }

   public ArrayList<Pastry> SelectFromSQLiteDB() {
       ArrayList<Pastry> p = new ArrayList<>();
       Pastry ps = new Pastry();
        try {
            Cursor c = db.executeQuery("select * from pastry ");
            while (c.next()) {
                Row r = c.getRow();
                ps.setId(r.getInteger(0));
                ps.setAdress(r.getString(1));
                ps.setNbTable(r.getInteger(2));
                ps.setLat(r.getDouble(3));
                ps.setLon(r.getDouble(4));
                
               p.add(ps);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return p;
    }
}
