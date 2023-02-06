package com.example.testdemo.keyboard.datastruct;

import com.google.mediapipe.formats.proto.LandmarkProto;

import java.util.List;

enum GratitudeTag {
    FLAT(1),
    RISING(2),
    DESCENDING(3);


    private int code;
    private GratitudeTag(int code){
        this.code = code;
    }

    public int getCode(){
        return code;
    }

}

public class HandMark {
    public HandMark() {
        for(int i=0;i<jointPoint.length;i++){
            Position p=new Position();
            jointPoint[i]=p;
        }
    }

    public Position[] jointPoint =new Position[21];
    public int ID;
    public double timestamp=0.0;
    public boolean historyMoveSign =false;
    public boolean historyPushSign =false;
    public boolean historyFOnKSign =false;
    public int historyKey =0;
    public double gratitude2lastp=0.0;
    public double gratitude2last5p=0.0; //使用最小二乘法得到当前5帧的线性拟合后的梯度
    public GratitudeTag gratitudeTag= GratitudeTag.FLAT;

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
