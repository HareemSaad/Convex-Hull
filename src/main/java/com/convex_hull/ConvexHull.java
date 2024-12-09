package com.convex_hull;

import java.util.Arrays;
import java.util.Stack;

public class ConvexHull {

    public int area_height = 10;
    public int area_width = 10;

    public ConvexHull(int height, int width) {
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
        for (Point point : points) {
            point.setAngle(start);
        }

        // sort array based on point's angle_with_start
        Arrays.sort(points, (Point p1, Point p2) -> Double.compare(p1.angle_with_start, p2.angle_with_start));
    }
    
    public static void main(String[] args) {
        ConvexHull convexHull = new ConvexHull(10000, 10000 );

        Point[] points = convexHull.generateRandomPoints(100);

        // print the points
        System.out.print("Points: ");
        Point.printPoints(points);

        System.out.println("\n\nConvex Hull: " + Point.toString(convexHull.convexHull(points)));

    }
}