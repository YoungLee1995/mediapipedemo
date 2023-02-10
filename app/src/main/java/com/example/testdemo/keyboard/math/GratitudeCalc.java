package com.example.testdemo.keyboard.math;

import static com.example.testdemo.keyboard.headers.ConstPredefinedKt.doubleZero;
import com.example.testdemo.keyboard.datastruct.HandMark;
import com.example.testdemo.keyboard.datastruct.HandMarks;
import com.example.testdemo.keyboard.headers.Enums;

import java.util.LinkedList;

//基于眼镜坐标系
public class GratitudeCalc {
    public static double gratitudeOf2frame(HandMark h1,HandMark h2,int pointId){
        if(pointId>20||pointId<0||h2.timestamp- h1.timestamp<doubleZero){
            return 0.0;
        }

        double z1=h1.jointPoint[pointId].getLocation_z();
        double z2=h2.jointPoint[pointId].getLocation_z();


        return (z2-z1)/(h2.timestamp- h1.timestamp);
    }
    //最小二乘法求近n帧拟合出的线性函数梯度，HandMarks长度不能小于n
    public static double gratitudeOfNframe(HandMarks h, int pointId,int n){
        if(pointId>20||pointId<0){
            return 0.0;
        }
        LinkedList<Double> timeStamps=new LinkedList<>();
        LinkedList<Double> deepthOfPoint=new LinkedList<>();
        int HMsize=h.markList.size();
        for(int i=0;i<n;i++){
            timeStamps.addLast(h.markList.get(HMsize-6+i).timestamp);
            deepthOfPoint.addLast(h.markList.get(HMsize-6+i).jointPoint[pointId].getLocation_z());
        }

        return LeastSquare.LeastSquareA(timeStamps,deepthOfPoint);
    }

    //不仅要返回梯度tag，还应该将HandMarks里面对应的梯度值修改为计算得到的梯度值。单位mm/ms
    public static Enums.GratitudeTag gratitudeTagCalc(HandMarks h, int pointId){
        Enums.GratitudeTag result=Enums.GratitudeTag.FLAT;
        double criticalG=10.0/33.0;
        h.markList.getLast().gratitude2lastp=GratitudeCalc.gratitudeOfNframe(h,pointId,2);
        if(GratitudeCalc.gratitudeOfNframe(h,pointId,2)>criticalG){
            result=Enums.GratitudeTag.RISING;
        }
        if(GratitudeCalc.gratitudeOfNframe(h,pointId,3)>criticalG){
            result=Enums.GratitudeTag.RISING;
            h.markList.get(h.markList.size()-2).gratitudeTag=result;
        }
        if(GratitudeCalc.gratitudeOfNframe(h,pointId,4)>criticalG){
            result=Enums.GratitudeTag.RISING;
            h.markList.get(h.markList.size()-2).gratitudeTag=result;
            h.markList.get(h.markList.size()-3).gratitudeTag=result;
        }
        if(GratitudeCalc.gratitudeOfNframe(h,pointId,2)<-1*criticalG){
            result=Enums.GratitudeTag.DESCENDING;
        }
        if(GratitudeCalc.gratitudeOfNframe(h,pointId,3)<-1*criticalG){
            result=Enums.GratitudeTag.DESCENDING;
            h.markList.get(h.markList.size()-2).gratitudeTag=result;
        }
        if(GratitudeCalc.gratitudeOfNframe(h,pointId,4)<-1*criticalG){
            result=Enums.GratitudeTag.DESCENDING;
            h.markList.get(h.markList.size()-2).gratitudeTag=result;
            h.markList.get(h.markList.size()-3).gratitudeTag=result;
        }
        h.markList.getLast().gratitudeTag=result;
        return result;
    }
}
