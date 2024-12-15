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

    public Point[] generateRandomPoints(int gridWidth, int gridHeight) {
        int totalSensors = gridWidth * gridHeight;
        Point[] points = new Point[totalSensors];
    
        // Grid spacing based on area dimensions
        double xSpacing = this.area_width / gridWidth;
        double ySpacing = this.area_height / gridHeight;
    
        // Single Hotspot center
        double hotspotX = Math.random() * this.area_width;  // X-coordinate of the hotspot
        double hotspotY = Math.random() * this.area_height; // Y-coordinate of the hotspot
    
        // Generate grid with radiation levels
        int index = 0;
        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                double x = i * xSpacing;
                double y = j * ySpacing;
    
                // Calculate radiation level influenced by the single hotspot
                double distance = Math.sqrt(Math.pow(hotspotX - x, 2) + Math.pow(hotspotY - y, 2));
                double radiationLevel = Math.exp(-distance / 10.0); // Gaussian-like falloff
    
                // Add background noise
                radiationLevel += 0.1 * Math.random();
    
                // Normalize radiation level to [0, 1]
                radiationLevel = Math.min(1.0, radiationLevel);
    
                points[index++] = new Point(x, y, radiationLevel);
            }
        }
    
        System.out.printf("Hotspot located at (%.2f, %.2f)\n", hotspotX, hotspotY); // Log the hotspot position
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

    // public method to only print the low radiation points
    public String printHighRadiationPoints(Point[] points) {
        Point[] filteredPoints = this.removeLowRadiationPoints(points);
        return Point.toString(filteredPoints);
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
        SecureRedZone redZone = new SecureRedZone(0.5, 50, 50);
        Point[] points = redZone.generateRandomPoints(redZone.area_height, redZone.area_width);

        // System.out.println("Points: " + Point.toString(points) + "\n");

        System.out.println("High Radiation Points" + redZone.printHighRadiationPoints(points) + "\n");

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
