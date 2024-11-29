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

    public int x_limit = 10;
    public int y_limit = 10;
    public int point_limit = 10;

    public ConvexHull(int x_limit, int y_limit, int point_limit) {
        this.x_limit = x_limit;
        this.y_limit = y_limit;
        this.point_limit = point_limit;
    }

    public void convexHull(Point[] points) {
        // find the leftmost point
        Point start = points[0];
        for (int i = 1; i < points.length; i++) {
            if (points[i].y < start.y) {
                start = points[i];
            }
        }

        System.out.println("Original Point: " + Point.toString(start));

        for (Point point : points) {
            point.setAngle(start);
        }

        // sort array based on point's angle_with_start
        Arrays.sort(points, (Point p1, Point p2) -> Double.compare(p1.angle_with_start, p2.angle_with_start));

        // print the sorted array
        System.out.println("Sorted Points: " + Point.toString(points));

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

    public static void main(String[] args) {
        ConvexHull convexHull = new ConvexHull(10, 10, 10);

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

        convexHull.convexHull(points);

    }
}