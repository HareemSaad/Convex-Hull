package com.convex_hull;

import java.util.Arrays;
import java.util.Stack;

public class GrahamScan {

    public int area_height = 10;
    public int area_width = 10;

    public GrahamScan(int height, int width) {
        this.area_height = height;
        this.area_width = width;
    }

    public void convexHullWithPrint(Point[] points) {
        this.checkLimits(points);

        Point start = this.getLowestPoint(points);

        System.out.println("Original Point: " + Point.toString(start));

        sortPointsByPolarAngle(points, start);

        // start the convex hull with the leftmost point
        Stack<Point> convexHull = new Stack<>();
        convexHull.push(start);
        convexHull.push(points[1]);

        for (int i = 1; i < points.length - 1; i++) {
            Point point_to_add = points[i + 1];
            int top = convexHull.size() - 1;
            if (Point.ccw(convexHull.elementAt(top - 1), convexHull.elementAt(top), point_to_add)) {
                convexHull.push(point_to_add);
            } else {
                convexHull.pop();
                i--;
            }
        }
        
        // print the convex hull stack
        System.out.println("Convex Hull: " + Point.toString(convexHull));
    }

    public Stack<Point> convexHull(Point[] points) {
        this.checkLimits(points);

        Point start = this.getLowestPoint(points);

        sortPointsByPolarAngle(points, start);

        // start the convex hull with the leftmost point
        Stack<Point> convexHull = new Stack<>();
        convexHull.push(start);
        convexHull.push(points[1]);

        for (int i = 2; i < points.length; i++) {
            Point point_to_add = points[i];

            // Ensure there are at least 2 points in the stack before checking ccw
            while (convexHull.size() > 1 && !Point.ccw(convexHull.elementAt(convexHull.size() - 2), convexHull.peek(), point_to_add)) {
                convexHull.pop(); // Remove the top point if it makes a right turn
            }

            convexHull.push(point_to_add); // Add the current point to the hull
        }

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

    private Point getLowestPoint(Point[] points) {
        Point lowest = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i].y < lowest.y) {
                lowest = points[i];
            }
        }
        return lowest;
    }

    private void sortPointsByPolarAngle(Point[] points, Point start) {
        Arrays.sort(points, (p1, p2) -> {
            double angle1 = Math.atan2(p1.y - start.y, p1.x - start.x);
            double angle2 = Math.atan2(p2.y - start.y, p2.x - start.x);
            return Double.compare(angle1, angle2);
        });
    }
    
    public static void main(String[] args) {
        GrahamScan convexHull = new GrahamScan(10, 10);

        Point[] points = convexHull.generateRandomPoints(10);

        // print the points
        System.out.print("Points: ");
        Point.printPoints(points);

        long startTime = System.nanoTime();
        System.out.println("\n\nConvex Hull: " + Point.toString(convexHull.convexHull(points)));
        long endTime = System.nanoTime();

        System.out.println("Time taken: " + (endTime - startTime) + " ns");
    }
}