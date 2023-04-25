package com.example.testcalculator.keyboard.datastruct;

import com.example.testcalculator.keyboard.headers.Enums;
import com.google.mediapipe.formats.proto.LandmarkProto;

import java.util.List;



public class HandMark {
    public HandMark() {
        for(int i=0;i<jointPoint.length;i++){
            Position p=new Position();
            jointPoint[i]=p;
        }
    }

    public Position[] jointPoint =new Position[21];
    public int ID;
    public long timestamp=0;
    public boolean historyMoveSign =false;
    public boolean historyPushSign =false;
    public Enums.tapSign historySign=Enums.tapSign.noSignal;
    public boolean historyFOnKSign =false;
    Enums.tapSign opSign= Enums.tapSign.noSignal;
    public int historyKey =99999;
    public double gratitude2lastp=0.0;
    public double gratitude2last5p=0.0; //使用最小二乘法得到当前5帧的线性拟合后的梯度
    public Enums.GratitudeTag gratitudeTag= Enums.GratitudeTag.FLAT;
    public Integer keyId=null;

    //将landmark转换为handmark
    public static HandMark lm2hm(int width,int pixelWidth,
                                 int height,List<LandmarkProto.Landmark> l,
                                 List<LandmarkProto.NormalizedLandmark> nl,
                                 long timestamp){
        HandMark result=new HandMark();
        for(int i=0;i<l.size();i++){
            Position position=new Position();
            position.setLocation_x(l.get(i).getX()*1000);
            position.setLocation_y(l.get(i).getY()*1000);
            position.setLocation_z(l.get(i).getZ()*1000);
            position.setPixel_x(nl.get(i).getX()*width-(width-pixelWidth)/2);
            position.setPixel_y(nl.get(i).getY()*height);
            result.jointPoint[i]=position;
        }
        result.timestamp = timestamp;
        return result;
    }
}
