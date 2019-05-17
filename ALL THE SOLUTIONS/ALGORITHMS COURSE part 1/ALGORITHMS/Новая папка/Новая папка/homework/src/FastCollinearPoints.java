import java.util.Arrays;

import edu.princeton.cs.algs4.In;
// import edu.princeton.cs.algs4.StdDraw;
// import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private final LineSegment[] lineSegments;
    private int numberOfSegments;
    
   public FastCollinearPoints(Point[] points) {
       // finds all line segments containing 4 points
       if (points == null)
           throw new IllegalArgumentException("array of points is null");
       Point[] pointsUsed = new Point[points.length];
       Point[] pointsSort = new Point[points.length];
       for (int i = 0; i < points.length; i++) {
           if (points[i] == null)
               throw new IllegalArgumentException("point is null");
           for (int j = i+1; j < points.length; j++) {
               if (points[j] != null && points[i].compareTo(points[j]) == 0)
                   throw new IllegalArgumentException("duplicate points are exist");
           }
           pointsUsed[i] = points[i];
           pointsSort[i] = points[i];
       }
       
       int size = pointsUsed.length;
       
       Point[] pointsMin = new Point[size];
       Point[] pointsMax = new Point[size];
       double[] slopes = new double[size];
       int indexOfPoints = 0;
       
       double currentSlope = Double.NEGATIVE_INFINITY;
       Point currentP1 = null;
       Point currentP2 = null;
       boolean slopeExist = false;
       
       for (int i = 0; i < pointsUsed.length; i++) {
            Point invPoint = pointsUsed[i];
            Arrays.sort(pointsSort, invPoint.slopeOrder());
            
            // StdOut.println("-----------------------------");
            // for (int k = 0; k < pointsSort.length; k++) {
            //     double slope = invPoint.slopeTo(pointsSort[k]);
            //     StdOut.println(" slope = " + slope);
            // }
            // StdOut.println();
             
            currentSlope = Double.NEGATIVE_INFINITY;
            for (int j = 0; j < pointsSort.length-2; j++) {    
                 slopeExist = false;
                 double slope1 = invPoint.slopeTo(pointsSort[j]);
                 double slope2 = invPoint.slopeTo(pointsSort[j+1]);
                 double slope3 = invPoint.slopeTo(pointsSort[j+2]);
                 if (slope1 == slope2 && slope2 == slope3) { 
                     Point p1, p2;
                     p1 = null;
                     p2 = null;                                  
                     
                     if (invPoint.compareTo(pointsSort[j]) < 0 &&
                             invPoint.compareTo(pointsSort[j+1]) < 0 &&
                             invPoint.compareTo(pointsSort[j+2]) < 0) {
                         p1 = invPoint;
                     }
                     if (pointsSort[j].compareTo(invPoint) < 0 &&
                             pointsSort[j].compareTo(pointsSort[j+1]) < 0 &&
                             pointsSort[j].compareTo(pointsSort[j+2]) < 0) {
                         p1 = pointsSort[j];
                     }
                     if (pointsSort[j+1].compareTo(invPoint) < 0 &&
                             pointsSort[j+1].compareTo(pointsSort[j]) < 0 &&
                             pointsSort[j+1].compareTo(pointsSort[j+2]) < 0) {
                         p1 = pointsSort[j+1];
                     }
                     if (pointsSort[j+2].compareTo(invPoint) < 0 &&
                             pointsSort[j+2].compareTo(pointsSort[j]) < 0 &&
                             pointsSort[j+2].compareTo(pointsSort[j+1]) < 0) {
                         p1 = pointsSort[j+2];
                     }      

                     
                     if (invPoint.compareTo(pointsSort[j]) > 0 &&
                             invPoint.compareTo(pointsSort[j+1]) > 0 &&
                             invPoint.compareTo(pointsSort[j+2]) > 0) {
                         p2 = invPoint;
                     }
                     if (pointsSort[j].compareTo(invPoint) > 0 &&
                             pointsSort[j].compareTo(pointsSort[j+1]) > 0 &&
                             pointsSort[j].compareTo(pointsSort[j+2]) > 0) {
                         p2 = pointsSort[j];
                     }
                     if (pointsSort[j+1].compareTo(invPoint) > 0 &&
                             pointsSort[j+1].compareTo(pointsSort[j]) > 0 &&
                             pointsSort[j+1].compareTo(pointsSort[j+2]) > 0) {
                         p2 = pointsSort[j+1];
                     }
                     if (pointsSort[j+2].compareTo(invPoint) > 0 &&
                             pointsSort[j+2].compareTo(pointsSort[j]) > 0 &&
                             pointsSort[j+2].compareTo(pointsSort[j+1]) > 0) {
                         p2 = pointsSort[j+2];
                     }   
                     
                     if (currentSlope == slope1) {
                         if (currentP1.compareTo(p1) < 0) {
                             p1 = currentP1;
                         } 
                         if (currentP2.compareTo(p2) > 0) {
                             p2 = currentP2;
                         }   
                     }
                     else {
                         for (int k = 0; k < indexOfPoints; k++) {
                             if (slopes[k] == slope1) {
                                 if (pointsMin[k].compareTo(p1) == 0) {
                                     slopeExist = true;
                                 }
                             }
                         }
                         currentSlope = Double.NEGATIVE_INFINITY;
                     }
                    

                     if (p1 != null && p2 != null && !slopeExist) {
                         pointsMin[indexOfPoints] = p1;
                         pointsMax[indexOfPoints] = p2;
                         slopes[indexOfPoints] = slope1;
                         indexOfPoints++;
                         currentSlope = slope1; 
                         currentP1 = p1;
                         currentP2 = p2;
                     }
                 }       
            }
       }
       
       Point p1, p2;
       lineSegments = new LineSegment[indexOfPoints];
       numberOfSegments = 0;
       for (int i = 0; i < indexOfPoints; i++) {
           p1 = pointsMin[i];
           p2 = pointsMax[i];
           if (p1 != null && p2 != null) {
               LineSegment line = new LineSegment(p1, p2);
               lineSegments[numberOfSegments] = line;
               numberOfSegments++;
           }
       }
       
   }
   public int numberOfSegments() {
       // the number of line segments
       return numberOfSegments;
   }
   public LineSegment[] segments() {
       // the line segments
       LineSegment[] copy = lineSegments.clone();
       return copy;
   }
   
   public static void main(String[] args) {

       // read the n points from a file
       In in = new In(args[0]);
       int n = in.readInt();
       Point[] points = new Point[n];
       for (int i = 0; i < n; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
       }

       // draw the points
       // StdDraw.enableDoubleBuffering();
       // StdDraw.setXscale(0, 32768);
       // StdDraw.setYscale(0, 32768);
       // for (Point p : points) {
       //    p.draw();
       // }
       // StdDraw.show();

       // print and draw the line segments
       FastCollinearPoints collinear = new FastCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           if (segment != null) {
               StdOut.println(segment);
               // segment.draw();
           }
       }
       // StdDraw.show();
   }
}
