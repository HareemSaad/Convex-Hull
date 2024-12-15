package com.convex_hull;

import java.util.List;
import java.util.Stack;

public class Point {
    public double x;
    public double y;
    public double radiationLevel;
    public double angle_with_start = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.radiationLevel = 0;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        this.radiationLevel = 0;
    }

    public Point(double x, double y, double radiationLevel) {
        this.x = x;
        this.y = y;
        this.radiationLevel = radiationLevel;
    }

    public void setAngle(Point start) {
        this.angle_with_start = Math.toDegrees(Math.atan2(this.y - start.y, this.x - start.x));
    }

    // false => clockwise or collinear, true => counter-clockwise
    public static boolean ccw(Point p1, Point p2, Point p3) {
        double val = ((p2.y - p1.y) * (p3.x - p2.x)) - ((p2.x - p1.x) * (p3.y - p2.y));
        // String direction = val <= 0 ? "ccw" : "cw";
        // System.out.println("(" + p1.x + ", " + p1.y + ") (" + p2.x + ", " + p2.y + ") (" + p3.x + ", " + p3.y + ") -- " + direction + " -- " + val);   
        return val <= 0;
    }

    /**
     * Returns the orientation of the triplet (p, q, r).
     * @return -1 if counterclockwise, 1 if clockwise, 0 if collinear
     */
    public static int orientation(Point p1, Point p2, Point p3) {
        double val = ((p2.y - p1.y) * (p3.x - p2.x)) - ((p2.x - p1.x) * (p3.y - p2.y));
        if (val < 0) return -1; // Counterclockwise
        if (val > 0) return 1;  // Clockwise
        return 0;               // Collinear
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    // print a points array
    public static void printPoints(Point[] points) {
        for (Point point : points) {
            System.out.print("(" + point.x + ", " + point.y + "), ");
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
            return_string += "(" + point.x + ", " + point.y + "), ";
        }
        return return_string;
    }

    public static String toString(Stack<Point> points) {
        String return_string = "";
        for (Point point : points) {
            return_string += "(" + point.x + ", " + point.y + "), ";
        }
        return return_string;
    }

    public static String toString(List<Point> points) {
        String return_string = "";
        for (Point point : points) {
            return_string += "(" + point.x + ", " + point.y + "), ";
        }
        return return_string;
    }
}