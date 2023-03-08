package com.example.testcalculator.keyboard.math;

import com.example.testcalculator.keyboard.datastruct.HandMarks;

import java.util.ArrayList;

public class Statistics {
    public static Double[] zeroMean(Double[] A) {
        int sizeA = A.length;
        Double[] B = new Double[sizeA];
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

    public static Double[] lastNZeroMean(HandMarks handMarks, int n, int id) {
        Double[] result = new Double[n];
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
        Double[] B = new Double[n];
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


    public static double correlationCalc(Double[] A, Double[] B) {
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

    public static double aveCorrelation(Double[] A, Double[] B) {
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
        Double[] zeroMeanA = new Double[A.length];
        Double[] zeroMeanB = new Double[B.length];
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
