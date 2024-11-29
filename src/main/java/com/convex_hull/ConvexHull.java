package com.convex_hull;

import java.util.Arrays;
import java.util.Stack;

public class ConvexHull {

    public static class Point {
        public double x;
        public double y;
        public double angle_with_start = 0;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void setAngle(Point start) {
            this.angle_with_start = Math.toDegrees(Math.atan2(this.y - start.y, this.x - start.x));
        }
        // false => clockwise, true => counter-clockwise or collinear
        public static boolean ccw(Point p1, Point p2, Point p3) {
            double val =  ((p2.y - p1.y) * (p3.x - p2.x)) - ((p2.x - p1.x) * (p3.y - p2.y));
            // String direction = val <= 0 ? "ccw" : "cw";
            // System.out.println("(" + p1.x + ", " + p1.y + ") (" + p2.x + ", " + p2.y + ") (" + p3.x + ", " + p3.y + ") -- " + direction + " -- " + val);   
            return val <= 0;
        }

        // print a points array
        public static void printPoints(Point[] points) {
            for (Point point : points) {
                System.out.print("(" + point.x + ", " + point.y + ") ");
            }
        }

        // implement print points for stack
        public static void printPoints(Stack<Point> points) {
            for (Point point : points) {
                System.out.print("(" + point.x + ", " + point.y + ") ");
            }
        }

        public static String toString(Point point) {
            return "(" + point.x + ", " + point.y + ")";
        }

        public static String toString(Point[] points) {
            String return_string = "";
            for (Point point : points) {
                return_string += "(" + point.x + ", " + point.y + ") ";
            }
            return return_string;
        }

        public static String toString(Stack<Point> points) {
            String return_string = "";
            for (Point point : points) {
                return_string += "(" + point.x + ", " + point.y + ") ";
            }
            return return_string;
        }
    }

    public int point_limit = 10;

    public ConvexHull(int point_limit) {
        this.point_limit = point_limit;
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

    private void checkLimits(Point[] points) {
        if (points.length < 3) {
            throw new IllegalArgumentException("Need at least 3 points to form a convex hull");
        }

        if (points.length > this.point_limit) {
            throw new IllegalArgumentException("Number of points exceeds the limit of " + this.point_limit);
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
        ConvexHull convexHull = new ConvexHull(10);

        Point[] points = new Point[9];
        points[0] = new Point(1, 1);
        points[1] = new Point(1, 3);
        points[2] = new Point(2, 1);
        points[3] = new Point(2, 2);
        points[4] = new Point(2, 3);
        points[5] = new Point(2, 4);
        points[6] = new Point(3, 1);
        points[7] = new Point(3, 2);
        points[8] = new Point(4, 2);

        System.out.println("Convex Hull: " + Point.toString(convexHull.convexHull(points)));

    }
}