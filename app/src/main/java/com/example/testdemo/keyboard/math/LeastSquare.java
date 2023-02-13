package com.example.testdemo.keyboard.math;

import java.util.LinkedList;

public class LeastSquare {
    public static double LeastSquareA(LinkedList<Long> x,LinkedList<Double> y)
    {
        double sigmaxy=0.0;
        double sigmax=0.0;
        double sigmay=0.0;
        double sigmaxx=0.0;
        int n=x.size();
        while(!x.isEmpty()&&!y.isEmpty()){
            double x0=x.pollFirst();
            double y0=y.pollFirst();
            sigmaxy=sigmaxy+x0*y0;
            sigmax=sigmax+x0;
            sigmaxx=sigmax+x0*x0;
            sigmay=sigmay+y0;
        }
        double a=(n*sigmaxy-sigmax*sigmay)/(n*sigmaxx-sigmax*sigmax);
        return a;
    }
    public static double LeastSquareB(LinkedList<Double> x,LinkedList<Double> y)
    {
        double sigmaxy=0.0;
        double sigmax=0.0;
        double sigmay=0.0;
        double sigmaxx=0.0;
        int n=x.size();
        while(!x.isEmpty()&&!y.isEmpty()){
            double x0=x.pollFirst();
            double y0=y.pollFirst();
            sigmaxy=sigmaxy+x0*y0;
            sigmax=sigmax+x0;
            sigmaxx=sigmax+x0*x0;
            sigmay=sigmay+y0;
        }
        double b=(sigmaxx*sigmay-sigmax*sigmaxy)/(n*sigmaxx-sigmax*sigmax);
        return b;
    }
}
