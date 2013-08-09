/*
 *
 *  Christopher Lirette                     clirett
 *
 *  KochTriangle.java
 *
 *  This program draws first some Brownian noise, then a distorted Koch 
 *  triangle, then two regular Koch triangles.
 *
 *  The Brownian noise was generated by mashing together several
 *  the algorithms for Brownian noise and AnimatedHTrees found on
 *  the Princeton Algorithms website. These were adapted to learn
 *  the basics of recursive drawing. The Koch Triangle, on the other
 *  hand, was the fruit of relearning trigonometry, reading the
 *  Wikipedia entry for Koch curves, and endless trial-and-error.
 *  In the course of discovering this algorithm, I uncovered many
 *  strange distortions, one of which frames the more "perfect"
 *  versions of the Koch triangle.
 *
 *  THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
 *  CODE WRITTEN BY OTHER STUDENTS. Christopher Lirette
 *
 */

import java.util.*;
import java.awt.*;

public class KochTriangle {
        
    // My own formula for an excellent green
    private static Color VERT = new Color( 163, 211, 156 );
    
    // To end recursion and to identify types of triangles used
    private static int counter;
    private static boolean field = false, emblem = false;

    // This shifts the middle angle of the Koch curve, pulled out
    // so that I could see it better while debugging
    public static double kochShiftX( double startX, double startY, double endX, double endY ) {
        double shift;
        if (field) {
            shift = ((startX + endX) / 2) - ((startY - endY) * Math.sqrt(3)/6);
        } else {
            shift = ((startX + endX) / 2) - (startY - endY) * Math.sqrt(3)/6;
        }
        return shift;
    }

    public static double kochShiftY( double startX, double startY, double endX, double endY ) {
        double shift;
        if (field) {
            shift = (startY + endY) / 2 + ((startX - endX) + Math.sqrt(3)/6);
        } else {
            shift = (startY + endY) / 2 + (startX - endX) * Math.sqrt(3)/6;
        }
        return shift;
    }

    // This is the famous Koch curve, which takes in two points
    // and divides it into four sections, the middle two jut out
    // to form another equilateral triangle based on the line it
    // divides
    public static void kochCurve( double startX, double startY, double endX, double endY, int depth ){
        
        // To provide some animation
        StdDraw.show(1);
        colorLayer( depth );
        setOptionalKochColors( depth );

        // This series computes the vertices for the divided line
        // We use the starting points to keep these computations
        // flexible (and to limit variables). Each line call connects
        // a line to the next vertex
        double x = Math.round( startX +  ( endX - startX ) / 3 );
        double y = Math.round( startY + ( endY - startY ) / 3 );
        StdDraw.line( startX, startY, x, y );
        
        double x1 = Math.round( kochShiftX( startX, startY, endX, endY ) );
        double y1 = Math.round( kochShiftY( startX, startY, endX, endY ) );
        
        StdDraw.line( x, y, x1, y1 );
        double x2 = Math.round( endX - ( endX - startX ) / 3 );
        double y2 = Math.round( endY - ( endY - startY ) / 3 );
        
        StdDraw.line( x1, y1, x2, y2 );

        StdDraw.line( x2, y2, endX, endY );

        // This keeps the recursion going according the depth
        if ( depth >= 1 ) {
            depth -= 1;
            kochCurve( startX, startY, x, y, depth );
            kochCurve( x, y, x1, y1, depth );
            kochCurve( x1, y1, x2, y2, depth );
            kochCurve( x2, y2, endX, endY, depth );

        // This returns when we're done
        } else {
            return;
        }
        
    }
    // This allows me to change colors for different shapes
    public static void setOptionalKochColors ( int N ) {
        if (field) {
            StdDraw.setPenColor( VERT );
            StdDraw.setPenRadius( 0.004);
        } else if (emblem) {
            if ( N == 0 ) {
                StdDraw.setPenColor(StdDraw.WHITE);
                StdDraw.setPenRadius(0.003);
            }
            else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.setPenRadius();
            }
        }
    }

    // This allows me to draw only the last layer to keep the
    // Koch triangle clean
    public static void colorLayer ( int N ) {
        switch ( N ) {
            case 4: StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    StdDraw.setPenRadius(0.01);
                    break;
            case 3: StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    break;
            case 2: StdDraw.setPenColor(StdDraw.DARK_GRAY);
                    break;
            case 1: StdDraw.setPenColor(StdDraw.LIGHT_GRAY);
                    StdDraw.setPenRadius(0.009);
                    break;
            case 0: StdDraw.setPenColor(StdDraw.WHITE);
                     StdDraw.setPenRadius(0.004);
                    break;
        }
    }

    // Here's the function that makes the shape triangular
    // at first
    public static void triangleLine (double length, double x, double y, double delta) {

        // A bit of an animation
        StdDraw.show(1);
        
        // We compute the end points with the angle (60 degrees)
        // and the length
        double newX = Math.round( shiftAngleX( length, x, delta ) );
        double newY = Math.round( shiftAngleY( length, y, delta ) );
        
        // This makes each line of the triangle a Koch curve
        kochCurve(  newX, newY, x, y, 4 );

        // This rotates the line at each vertex
        delta -= Math.toRadians(240);

        // Keeping track of how big a triangle is, continuing if
        // we're still in the realm of three main sides
        counter++;
        if (counter < 3 ){
            triangleLine(length, newX, newY,  delta); 
        }    
    }
    
    // These compute the X and Y end points, pulled out to practice my math
    public static double shiftAngleX ( double length, double x, double theta ) {
        x = x - length * Math.cos ( theta );
        return x;
    }

    public static double shiftAngleY ( double length, double y, double theta ) {
        y = y + length * Math.sin ( theta );
        return y;
    }

    // This generates some Brown noise
    public static void brownianStatic(int n, double x, double y, double size, double trigAngle, double trailingFactor) {

        // sets variables and conditions to stop
        if (n == 0) return;
        
        // These change the variation in the branches and angles
        double s = Math.pow( 2, 2 * 1.5);
        double angle = Math.toRadians(45);
        double branchAngle = Math.toRadians(12);
        double variation = .1;

        // This gives some starting coordinates and trails
        double x0 = ( x - size / 2 ) + Math.cos(trigAngle) * trailingFactor;
        double  x1 = x + size/ 2;
        double y0 = ( y - size / 2 ) + Math.sin(trigAngle) * trailingFactor; 
        StdDraw.setPenRadius( .0005 *  Math.pow( n, 1.2 ) );
            
        // Draw each branch
        branch( x0, y0, x,  y, variation, s);

        // Recurse a few times to make it noisy
        brownianStatic(n-1, x0, y0, size/2, trigAngle + angle - branchAngle, trailingFactor * .75);
        brownianStatic(n-1, x1, y0, size/2, trigAngle + angle + branchAngle, trailingFactor * .65);
        brownianStatic(n-1, x0, y0, size/2, trigAngle + angle, trailingFactor * -0.65); 
    }

    // This is the actual drawn branches of Brownian noise; notice
    // how they almost look like Lichtenberg figures in the base case
    public static void branch (double startX, double startY, double endX, double endY, double variation, double s) {

        // Here is the base drawing case
        if ( Math.abs ( endX - startX ) < .01 ) {
            StdDraw.setPenColor( StdDraw.LIGHT_GRAY );
            StdDraw.line ( startX, startY, endX, endY );
            return;
        }

        // This alters some values for more variation
        double midX = ( startX + endX ) / 2;
        double midY = ( startY + endY ) / 2;
        midY = midY + Math.random(); 
        
        // RECURSE!
        branch( startX, startY, midX, midY, variation / s, s );
        branch( midX, midY, endX, endY, variation / s, s );
    }
    
    // This is the main class which puts all these shapes into a lovely
    // picture
    public static void main (String[] args) {
        double size = 1000;
        StdDraw.setCanvasSize( 800, 800 );

        StdDraw.clear(StdDraw.DARK_GRAY);
        
        int brownianDepth = 8;
        double trigAngle = Math.PI / 2;
        brownianStatic(brownianDepth, .5, .5, .2, trigAngle, .02);
        
        StdDraw.setScale( 0, 1000 );
        double length = 700; 
        double height = ( Math.sqrt(3) * length ) / 2;
        
        double y = ((size - height) / 2) + height / 3;
        double x = ((size - length) / 2);
        
        // call koch
        field = true;
        triangleLine( length, x, y, Math.toRadians(120)); 
        
        // reset counter to do it again
        counter = 0;

        // crazy Koch
        field = false;
        triangleLine( length, x, y, Math.toRadians(120)); 
        counter = 0;

        // small Koch
        emblem = true;
        triangleLine( length / 3, 20 + x + size / 3, y + size / 3, Math.toRadians(-120)); 

    }
}
