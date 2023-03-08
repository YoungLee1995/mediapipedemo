package com.example.testcalculator.keyboard.math;

public class TriangleFunc {
    public static double getAngle(double a, double b, double c) {
        double cosA = (b*b + c*c - a*a) / (2*b*c);
        return Math.acos(cosA);
    }

}
