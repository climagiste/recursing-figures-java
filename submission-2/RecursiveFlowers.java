/*
 *
 *  Christopher Lirette                     clirett
 *
 *  RecursiveFlowers.java
 *
 *  This program draws several flowers by mapping a curve 360 degrees at
 *  a center pivot point.                     
 *
 *  The basic algorithm here is based on the Rose.java file from 
 *  the Java: Introduction to Computer Science textbook website, 
 *  introcs.cs.princeton.edu. This program helped me learn to manipulate
 *  the scale of an image, as well as the way one can easily transform
 *  one drawing to another by changing certain key parameters. Right now,
 *  the program draws one certain flower (an "orchid"), but it could be
 *  modified to draw different flowers with different shapes by requesting
 *  the user's input, and storing it in the flowerName variable.
 *
 *  THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
 *  CODE WRITTEN BY OTHER STUDENTS. Christopher Lirette_
 *
 */

import java.awt.*;

public class RecursiveFlowers {

    // My own private green.
    private static Color VERT = new Color(163, 211, 156);
    private static int counter;

    // This method draws the flower. It also calls the flowerName to change
    // the parameters/draw new shapes, which indirectly recurses on flower
    public static void flower ( int N, double t, double x0, double y0, String flowerName ) {

        // This is the stop condition, which counts the number of stacks 
        // before popping them off. I've limited it this way, because this
        // program becomes quickly capable of overflowing, and it gives me
        // more flexibility with types of flowers drawn
        if ( counter == 3000 )  return;
        
        // The math behind drawing a polar equation is best explained here:
        // http://jwilson.coe.uga.edu/EMT668/EMAT6680.2003.fall/Shiver/assignment11/PolarGraphs.htm
        double theta = Math.toRadians(t);
        double r = Math.sin( N * theta );
        double x1 = r * Math.cos( theta );
        double y1 = r * Math.sin( theta );
        StdDraw.line( x0, y0, x1, y1 );
        x0 = x1;
        y0 = y1;
        t += 0.99;
        counter += 1;
        
        // These if conditions create different drawings, calling specific
        // functions for each flower
        if (flowerName == "insideyellow") {
            insideyellow(N, t, x0, y0, flowerName);
        }
        if (flowerName == "orchid") {
            orchid(N, t, x0, y0, flowerName);
        }
    }

    // This draws the inside of a flower 
    public static void insideyellow(int N, double t, double x1, double y1, String flowerName) {

        // Set out colors
        StdDraw.setPenColor(StdDraw.YELLOW);
        
        // Pass in these parameters for the "first" flower image
        if ( t <= 540) {
            StdDraw.setPenColor(StdDraw.YELLOW);
            flower ( N, t, x1 / .2, y1 / .2, flowerName );
        } else {
            return;
        }
    }

    // This method calls the orchid
    public static void orchid(int N, double t, double x1, double y1, String flowerName ) {
        
        // draw this until 2000, then revert to the standard parameters
        if ( counter == 2000 ) return;

        // for the first 360, it is basically a 12 sided flower
        if ( t < 360 ) {
                StdDraw.setPenRadius(0.002);
                StdDraw.setPenColor(StdDraw.BOOK_RED);
                flower(N, t, x1 / 0.1, y1 / 0.1, flowerName);
        // then it adds something
        } else if ( t < 720 ) {
                StdDraw.setPenColor(Color.MAGENTA);
                flower(N - 1, 0, 7.5, 7.5, flowerName);
        }
}

    public static void main( String[] args) {
        StdDraw.setXscale( -10, +10 );
        StdDraw.setYscale( -10, + 10 );

        StdDraw.clear(StdDraw.PINK);
        double x0 = 0, y0 = 0, t = 0;
        flower ( 6, t, x0, y0, "orchid" );
        flower ( 6, .53, x0, y0, "insideyellow" );
    }
}
