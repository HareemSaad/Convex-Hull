package com.convex_hull;

import java.util.ArrayList;
import java.util.List;

public class JarvisMarch {

    public int area_height = 10;
    public int area_width = 10;

    public JarvisMarch(int height, int width) {
        this.area_height = height;
        this.area_width = width;
    }

    public void convexHullWithPrint(Point[] points) {
        this.checkLimits(points);

        Point start = this.getMostLeftPoint(points);

        System.out.println("\n\nOriginal Point: " + Point.toString(start));

        // Initialize the convex hull with the leftmost point
        List<Point> convexHull = new ArrayList<>();
        Point current = start;

        do {
            convexHull.add(current);
            Point nextPoint = points[0]; // Assume the first point as the next hull point

            for (Point candidate : points) {
                // if its the first point, skip it
                if (candidate == current) continue;

                // Check if the candidate point is more counterclockwise than the current next point
                if ((nextPoint == current || Point.orientation(current, nextPoint, candidate) == -1 || (Point.orientation(current, nextPoint, candidate) == 0 && Point.distance(current, candidate) > Point.distance(current, nextPoint)))) {
                    nextPoint = candidate;
                }
            }

            current = nextPoint; // Move to the next point in the hull

            // Break if we return to the starting point
            if (current.equals(start)) {
                break;
            }
        } while (!current.equals(start)); // Stop when we return to the starting point
        
        // print the convex hull stack
        System.out.println("Convex Hull: " + Point.toString(convexHull));
    }

    public List<Point> convexHull(Point[] points) {
        this.checkLimits(points);

        Point start = this.getMostLeftPoint(points);

        // Initialize the convex hull with the leftmost point
        List<Point> convexHull = new ArrayList<>();
        Point current = start;

        do {
            convexHull.add(current);
            Point nextPoint = points[0]; // Assume the first point as the next hull point

            for (Point candidate : points) {
                // if its the first point, skip it
                if (candidate == current) continue;

                // Check if the candidate point is more counterclockwise than the current next point
                if ((nextPoint == current || Point.orientation(current, nextPoint, candidate) == -1 || (Point.orientation(current, nextPoint, candidate) == 0 && Point.distance(current, candidate) > Point.distance(current, nextPoint)))) {
                    nextPoint = candidate;
                }
            }

            current = nextPoint; // Move to the next point in the hull

            // Break if we return to the starting point
            if (current.equals(start)) {
                break;
            }
        } while (!current.equals(start)); // Stop when we return to the starting point
        
        return convexHull;
    }

    public Point[] generateRandomPoints(int number_of_points) {
        // pick random points within the area
        Point[] points = new Point[number_of_points];
        for (int i = 0; i < number_of_points; i++) {
            // generate a random double point between 0 and area_width
            double x = Math.random() * this.area_width;
            double y = Math.random() * this.area_height;
            points[i] = new Point(x, y);
        }
        return points;
    }

    private void checkLimits(Point[] points) {
        if (points.length < 3) {
            throw new IllegalArgumentException("Need at least 3 points to form a convex hull");
        }
    }

    private Point getMostLeftPoint(Point[] points) {
        Point lowest = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i].x < lowest.x) {
                lowest = points[i];
            }
        }
        return lowest;
    }
    
    public static void main(String[] args) {
        JarvisMarch convexHull = new JarvisMarch(1000, 1000 );

        Point[] points = convexHull.generateRandomPoints(100);

        // print the points
        System.out.print("Points: ");
        Point.printPoints(points);

        // convexHull.convexHullWithPrint(points);

        // print time before
        long startTime = System.nanoTime();
        System.out.println("\n\nConvex Hull: " + Point.toString(convexHull.convexHull(points)));
        // print time after
        long endTime = System.nanoTime();

        // print the time taken
        System.out.println("Time taken: " + (endTime - startTime) + " ns");
        System.out.println("Start Time: " + startTime + " End Time: " + endTime);

    }
}