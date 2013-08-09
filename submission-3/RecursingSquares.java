/*
 *
 *  Christopher Lirette                     clirett
 *
 *  RecursingSqaures.java
 *
 *  This program recursively draws boxes within boxes, switching color for
 *  a few prime numbers...
 *
 *  THIS CODE IS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
 *  CODE WRITTEN BY OTHER STUDENTS. Christopher Lirette
 *
 */

import java.util.*;

public class RecursingSquares {

    // This is the recursive method that draws a square that's the size of the
    // canvas and calls itself with a smaller canvas size.
    public static void recursingSquares (double length) {

        if (length < 2.00) return;
    
        // This changes the colors for multiples of three prime numbers. 
        // As you can see this changes the look dramatically
        if (length % 17.0 == 0) StdDraw.setPenColor(StdDraw.BLUE);
        else if (length % 23.0 == 0) StdDraw.setPenColor(StdDraw.GREEN);
        else if (length % 11.0 == 0) StdDraw.setPenColor(StdDraw.BOOK_RED);
        else StdDraw.setPenColor();
        StdDraw.square( 500, 500, length);
        recursingSquares( length - 2.0);
    }

    public static void main (String[] args) {
    StdDraw.setCanvasSize(600, 600);    
    StdDraw.setXscale(0, 1000 );
    StdDraw.setYscale(0, 1000);
    
    StdDraw.show(0);
    recursingSquares(500.0);
    StdDraw.show(0);
    }
}
