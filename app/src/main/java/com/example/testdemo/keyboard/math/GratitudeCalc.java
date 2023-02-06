package com.example.testdemo.keyboard.math;

import com.example.testdemo.keyboard.datastruct.HandMark;
import com.example.testdemo.keyboard.datastruct.HandMarks;

//基于眼镜坐标系
public class GratitudeCalc {
    public static double gratitudeOf2frame(HandMark h1,HandMark h2,int pointId,double time){
        if(pointId>20||pointId<0){
            return 0.0;
        }
        double z1=h1.jointPoint[pointId].getLocation_z();
        double z2=h2.jointPoint[pointId].getLocation_z();

        return (z2-z1)/time;
    }

    public static double gratitudeOf5frame(HandMarks h, int pointId){
        if(pointId>20||pointId<0){
            return 0.0;
        }

        return 0.0;
    }
}
