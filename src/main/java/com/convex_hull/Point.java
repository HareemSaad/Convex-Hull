package com.convex_hull;

import java.util.Stack;

public class Point {
    public double x;
    public double y;
    public double angle_with_start = 0;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y) {
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