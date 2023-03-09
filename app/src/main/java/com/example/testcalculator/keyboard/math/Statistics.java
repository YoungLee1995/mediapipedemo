package com.example.testcalculator.keyboard.math;

import com.example.testcalculator.keyboard.datastruct.HandMarks;

import java.util.ArrayList;

public class Statistics {
    public static double[] zeroMean(double[] A) {
        int sizeA = A.length;
        double[] B = new double[sizeA];
        for (int i = 0; i < sizeA; i++) {
            B[i] = A[i];
        }
        double sum = 0.0;
        for (double b : B) {
            sum += b;
        }
        double mean = sum / sizeA;
        for (int i = 0; i < sizeA; i++) {
            B[i] -= mean;
        }
        return B;
    }
    //后续应该将每一帧自卷积存储在handmarks中减少计算量
    public static double selfConv(HandMarks h, int n){
        double result=0.0;
        int size=h.graList.size();
        for (int i=0;i<21;i++){
            double[] waveX=new double[n];
            double[] waveY=new double[n];
            double[] waveZ=new double[n];
            for (int time=size-n;time<size;time++){
                waveX[n+time-size]=h.graList.get(time).jointPoint[i].getLocation_x();
                waveY[n+time-size]=h.graList.get(time).jointPoint[i].getLocation_y();
                waveZ[n+time-size]=h.graList.get(time).jointPoint[i].getLocation_z();
            }
            result+=aveCorrelation(waveX,waveX)+aveCorrelation(waveY,waveY)+aveCorrelation(waveZ,waveZ);
        }
        return result;
    }

    public static double[] lastNZeroMean(HandMarks handMarks, int n, int id) {
        double[] result = new double[n];
        int xyzId=0;
        int realId=id;
        while (realId>20){
            xyzId++;
            realId-=21;
        }
        int markSize = handMarks.markList.size();
        if (n > markSize) {
            throw new IllegalArgumentException("n is larger than the size of ArrayList A.");
        }
        double[] B = new double[n];
        for (int i = 0; i < n; i++) {
            B[i] = handMarks.markList.get(markSize - n + i).jointPoint[realId].location[xyzId];
        }
        double sum = 0;
        for (double b : B) {
            sum += b;
        }
        double mean = sum / n;
        for (int i = 0; i < n; i++) {
            B[i] -= mean;
        }
        return B;
    }


    public static double correlationCalc(double[] A, double[] B) {
        int n = A.length;
        double sumA = 0.0;
        double sumB = 0.0;
        double sumASquared = 0.0;
        double sumBSquared = 0.0;
        double sumAB = 0.0;

        for (int i = 0; i < n; i++) {
            sumA += A[i];
            sumB += B[i];
            sumASquared += A[i] * A[i];
            sumBSquared += B[i] * B[i];
            sumAB += A[i] * B[i];
        }

        double numerator = n * sumAB - sumA * sumB;
        double denominator = Math.sqrt((n * sumASquared - sumA * sumA) * (n * sumBSquared - sumB * sumB));

        if (denominator == 0.0) {
            return 0.0;
        } else {
            return numerator / denominator;
        }
    }

    public static double aveCorrelation(double[] A, double[] B) {
        // 计算平均值
        double sumA = 0.0;
        double sumB = 0.0;
        for (int i = 0; i < A.length; i++) {
            sumA += A[i];
            sumB += B[i];
        }
        double meanA = sumA / A.length;
        double meanB = sumB / B.length;

        // 将数组转换为零均值数组
        double[] zeroMeanA = new double[A.length];
        double[] zeroMeanB = new double[B.length];
        for (int i = 0; i < A.length; i++) {
            zeroMeanA[i] = A[i] - meanA;
            zeroMeanB[i] = B[i] - meanB;
        }

        // 计算相关系数
        double numerator = 0.0;
        double denominator1 = 0.0;
        double denominator2 = 0.0;
        for (int i = 0; i < A.length; i++) {
            numerator += zeroMeanA[i] * zeroMeanB[i];
            denominator1 += Math.pow(zeroMeanA[i], 2);
            denominator2 += Math.pow(zeroMeanB[i], 2);
        }
        double correlation = numerator / (Math.sqrt(denominator1) * Math.sqrt(denominator2));

        return correlation;
    }
}
