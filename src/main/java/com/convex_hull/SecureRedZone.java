package com.convex_hull;

import java.util.List;

public class SecureRedZone {

    public double radiationThreshold;
    public int area_height = 10;
    public int area_width = 10;

    public SecureRedZone(double radiationThreshold, int height, int width) {
        if (radiationThreshold < 0 || radiationThreshold > 1) {
            throw new IllegalArgumentException("Radiation threshold must be between 0 and 1");
        }
        this.radiationThreshold = radiationThreshold;
        this.area_height = height;
        this.area_width = width;
    }

    public Point[] generateRandomPoints(int number_of_points) {
        // pick random points within the area
        Point[] points = new Point[number_of_points];
        for (int i = 0; i < number_of_points; i++) {
            // generate a random double point between 0 and area_width
            double x = Math.random() * this.area_width;
            double y = Math.random() * this.area_height;
            double radiationLevel = Math.random();
            points[i] = new Point(x, y, radiationLevel);
        }
        return points;
    }

    // private method to remove any points with radiation levels below the threshold
    private Point[] removeLowRadiationPoints(Point[] points) {
        int count = 0;
        for (Point point : points) {
            if (point.radiationLevel >= this.radiationThreshold) {
                count++;
            }
        }
        Point[] newPoints = new Point[count];
        int index = 0;
        for (Point point : points) {
            if (point.radiationLevel >= this.radiationThreshold) {
                newPoints[index] = point;
                index++;
            }
        }
        return newPoints;
    }

    // public method to run the Jarvis March algorithm
    public List<Point> runJarvisMarch(Point[] points) {
        Point[] filteredPoints = this.removeLowRadiationPoints(points);
        JarvisMarch jarvisMarch = new JarvisMarch(this.area_height, this.area_width);
        return jarvisMarch.convexHull(filteredPoints);
    }

    // public method to run the Graham Scan algorithm
    public List<Point> runGrahamScan(Point[] points) {
        Point[] filteredPoints = this.removeLowRadiationPoints(points);
        GrahamScan grahamScan = new GrahamScan(this.area_height, this.area_width);
        return grahamScan.convexHull(filteredPoints);
    }

    public static void main(String[] args) {
        SecureRedZone redZone = new SecureRedZone(0.5, 100, 100);
        Point[] points = redZone.generateRandomPoints(100);

        System.out.println("Points: " + Point.toString(points) + "\n");

        // run both algorithms with time logging and input/output logging
        System.out.println("Running Jarvis March");
        long startTime = System.nanoTime();
        List<Point> jarvisHull = redZone.runJarvisMarch(points);
        long endTime = System.nanoTime();
        System.out.println("Time taken (Jarvis-March): " + (endTime - startTime) + " ns");
        System.out.println("Jarvis Hull: " + Point.toString(jarvisHull) + "\n");


        System.out.println("Running Graham Scan");
        startTime = System.nanoTime();
        List<Point> grahamHull = redZone.runGrahamScan(points);
        endTime = System.nanoTime();
        System.out.println("Time taken (Graham-Scan): " + (endTime - startTime) + " ns");
        System.out.println("Graham Hull: " + Point.toString(grahamHull) + "\n");
    }

}
