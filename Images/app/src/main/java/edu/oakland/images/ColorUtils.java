package edu.oakland.images;

import android.graphics.Color;

/**
 * Created by brandon on 1/17/15.
 */
public class ColorUtils {

   public static String whatColor(int color) {
       int r, g, b;
       r = Color.red(color) / 10;
       g = Color.green(color) / 10;
       b = Color.blue(color) / 10;

       if( r==g && g==b && b==0 ) {
           return "black";
       }

       if( r==g && g==b && b==25 ) {
           return "white";
       }

       if( r==g && g==b ) {
           return "grey";
       }

       if( (r==25 || r==12) && g==b && b==0 ) {
           return "red";
       }

       if( (g==25 || g==12) && r==b && b==0 ) {
           return "green";
       }

       if( (b==25 || b==12) && r==g && g==0 ) {
           return "blue";
       }

       if( r==25 && g==25 && b==0 ) {
           return "yellow";
       }

       if( r>=20 && g>=20 && b<100 ) {
           return "yellow";
       }

       if( r==25 && g==0 && b==25 ) {
           return "magenta";
       }

       if( r==0 && g==25 && b==25 ) {
           return "aqua";
       }

       if( r==12 && g==12 && b==0 ) {
           return "olive";
       }

       if( r==0 && g==12 && b==12 ) {
           return "teal";
       }

       if( r==12 && g==0 && b==12 ) {
           return "purple";
       }

       if( r==12 && g==b && b==0 ) {
           return "maroon";
       }

       if( r>g && b==0 ) {
           return "orange";
       }

       if( b>r && r>g ) {
           return "violet";
       }

       if( b>g && g>r && r<=2 ) {
           return "sky blue";
       }

       if( b>g && g>r ) {
           return "light blue";
       }

       return "nothing";
   }
}
