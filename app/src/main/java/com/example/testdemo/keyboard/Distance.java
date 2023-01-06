package com.example.testdemo.keyboard;

public class Distance {
    public static double pixel_distance(Position p1,Position p2){
        double dx=p1.getPixel_x()-p2.getPixel_x();
        double dy=p1.getPixel_y()-p2.getPixel_y();
        double d=Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
        return d;
    }
    public static double pixelDistanceL1(Position p1, Position p2){
        double dx=p1.getPixel_x()-p2.getPixel_x();
        double dy=p1.getPixel_y()-p2.getPixel_y();
        double d=Math.abs(dx)+Math.abs(dy);
        return d;
    }
}