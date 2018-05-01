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

    /*      public static boolean isUsername(String text) {



        if (text.matches("^[A-Za-z0-9]+$+") ) {

            return true;

        } 

            return false;

    }

          public static boolean DateNullCS(String date){

            if(date == ""){

                return true ;

            }

              return false;

          }

      public static boolean adresse(String text) {



        if (text.matches("^[A-Z a-z 0-9]+$")) {

            return true;

        }

            return false;

    }*/
     
     public static boolean isValidDate(Date date) {

        return date.getTime() >= new Date().getTime();

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

           /*       public static boolean isTel(String text) {



        if (text.matches("^[0-9]+$")&& text.length()==8) {

            return true;

        } else {

            return false;

        }

    }



     private static final String EMAIL_PATTERN

            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"

            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

                      private static Pattern pattern = Pattern.compile(EMAIL_PATTERN);

                          private static final String pwd=  "^[A-Za-z0-9]+$";

                                private static Pattern pattern1 = Pattern.compile(pwd);

     public static boolean valiemail(final String hex) {

        matcher = pattern.matcher(hex);

        return matcher.matches();

    }

      public static boolean validPasswor(final String hex) {

        matcher = pattern1.matcher(hex);

        return matcher.matches();

    } */
    
}
