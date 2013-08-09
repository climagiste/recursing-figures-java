
/*
 *
 *  Christopher Lirette                     clirett
 *
 *  RayKochTriangle.java
 *
 *  THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
 *  CODE WRITTEN BY OTHER STUDENTS. _Your_Name_Here_
 *
 */

import java.util.*;
import java.awt.*;

public class RayKochTriangle {
        
    private static double size;
    private static int counter;
    private static int triangleSides;
    private static double originX;
    private static double originY;

    public static double kochShiftX( double startX, double startY, double endX, double endY ) {
        return (startX + startX + endX) / 2 + Math.sqrt(3)/6 * (startY - endY);
    }

    public static double kochShiftY( double startX, double startY, double endX, double endY ) {
        return (startY + startY + endY) / 2 + Math.sqrt(3)/6 * (startX - endX);
    }

    public static void kochCurve( double startX, double startY, double endX, double endY, double penRadius, int depth ){
      
        double x = Math.round( startX +  ( endX - startX ) / 3 );
        double y = Math.round( startY + ( endY - startY ) / 3 );

        StdDraw.line( startX, startY, x, y );
        double x1 = Math.round( kochShiftX( startX, startY, endX, endY ) );
        double y1 = Math.round( kochShiftY( startX, startY, endX, endY ) );
        StdDraw.line( x, y, x1, y1 );

        double x2 = Math.round( endX - ( endX - startX ) / 3 );
        double y2 = Math.round( endY - ( endY - startY ) / 3 );
        
        StdDraw.line( x1, y1, x2, y2 );

        StdDraw.line( x, y, endX, endY );
        if ( depth >= 1 ) {
            depth -= 1;
            kochCurve( startX, startY, x, y, penRadius, depth );
            kochCurve( x, y, x1, y1, penRadius, depth );
            kochCurve( x1, y1, x2, y2, penRadius, depth );
            kochCurve( x2, y2, endX, endY, penRadius, depth );
        } else {
            return;
        }
        
    }

    public static void triangleLine (double length, double x, double y, double delta, double penRadius) {

        StdDraw.setPenRadius(penRadius);
        System.out.println("x : " + x + "; y : " + y);
        
        double newX = Math.round( shiftAngleX( length, x, delta ) );
        double newY = Math.round( shiftAngleY( length, y, delta ) );
        
            kochCurve(  x, y, newX, newY, penRadius, 3 );

        delta -= Math.toRadians(240);

        counter++;
        
        if (counter < 3 ){
            triangleLine(length, newX, newY,  delta , penRadius);
     }    
    }
    
    public static double shiftAngleX ( double length, double x, double theta ) {
         x = x - length * Math.cos ( theta );
        return x;
    }

    public static double shiftAngleY ( double length, double y, double theta ) {
         y = y + length * Math.sin ( theta );
        return y;
    }

    public static void main (String[] args) {
        
        StdDraw.setScale( 0, 500 );

        StdDraw.setPenRadius(0.005);
        size = 500;
        double length = 450; 
        double height = ( Math.sqrt(3) * length ) / 2;
        originY = ((size - height) / 2);
        originX = ((size - length) / 2);

        // call triangle
        triangleLine( length, originX, originY, Math.toRadians(120), .001); 

    }
}
