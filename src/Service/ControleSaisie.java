/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;

import com.codename1.util.regex.RE;
import java.util.Date;


/**
 *
 * @author LENOVO
 */
public class ControleSaisie {
    
  

    public static boolean isString(String text) {

        RE r = new RE("^[a-zA-Z]+$");

        if (r.match(text)) {

            return true;

        } 

            return false;

    }

     public static boolean isNull(String text){

         if(text == ""){

             return true; //null

         }

         return false ;//n'est pas vide

     }

   
     public static boolean isValidDate(Date date) {

        return date.getTime() >= new Date().getTime();

    }
      public static boolean isValidDate2(Date date) {

        return date.getTime() >= new Date().getTime()+2.628e+9;

    }


          public static boolean isNumberTable(String text) {


       // RE r = new RE("^[0-9]+$");
        if ( Integer.parseInt(text)>0 && Integer.parseInt(text)<16) {

            return true;

        } else {

            return false;

        }

    }
          public static boolean isNumberPerson(String text) {


       // RE r = new RE("^[0-9]+$");
        if ( Integer.parseInt(text)>0 && Integer.parseInt(text)<5) {

            return true;

        } else {

            return false;

        }

    }

         public static boolean isNumber(String text) {

        if (Integer.parseInt(text) > 0 && Integer.parseInt(text) < 100) {

            return true;

        } else {

            return false;

        }

    }
    
}
