package com.example.testdemo.keyboard.taprules;

import android.util.Log;

import com.example.testdemo.keyboard.datastruct.HandMarks;
import com.example.testdemo.keyboard.datastruct.KeyShape;
import com.example.testdemo.keyboard.datastruct.Position;
import com.example.testdemo.keyboard.datastruct.TestKeyBoard;
import com.example.testdemo.keyboard.headers.Enums;
import com.example.testdemo.keyboard.math.Distance;

import java.util.Objects;

public class FingerDetect {
    public static int pushedKey(TestKeyBoard testKeyBoard, Position finger1_position){
        double distance=Double.MAX_VALUE;
        int id=0;
        Position newKeyCenter = new Position();
        for(int i=0;i<testKeyBoard.testKeyMap.size();i++){
            Position keyCenter = Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).position;
            KeyShape keyShape = Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).keyShape;
            double x=keyCenter.getPixel_x();
            double y=keyCenter.getPixel_y();
            double width=keyShape.getKey_width();
            double height=keyShape.getKey_height();
            newKeyCenter.setPixel_x(x+width/2.0);
            newKeyCenter.setPixel_y(y+height/2.0);
            double d= Distance.pixelDistance(finger1_position, newKeyCenter);
            if(d<distance){
                id= Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).getId();
                //Log.v("距离id",d+":"+newKeyCenter.getPixel_x());
                distance=d;
            }
        }

        return id;
    }

    public static boolean isFingerOnKey(TestKeyBoard testKeyBoard,Position finger1_position, int id){
        //此处确定是否落入键盘的标准
        Position keyCenter = Objects.requireNonNull(testKeyBoard.testKeyMap.get(id)).getPosition();
        KeyShape keyShape=Objects.requireNonNull(testKeyBoard.testKeyMap.get(id)).getKeyShape();
        double x=keyCenter.getPixel_x();
        double y=keyCenter.getPixel_y();
        double width=keyShape.getKey_width();
        double height=keyShape.getKey_height();
        double pixelX = x+width/2.0;
        double pixelY = y+height/2.0;
        return (finger1_position.getPixel_x()-pixelX)<keyShape.getKey_width()/2.0&&
                (finger1_position.getPixel_y()-pixelY)<keyShape.getKey_height()/2.0;
    }

    public static boolean isFingerOnKey(HandMarks handMarks){
        int totalSign=0;
        int hmSize=handMarks.markList.size();
        if(hmSize<119) return false;
        //统计近20帧手指是否都位于当前帧所在按键内
        for(int i=0;i<20;i++){
            int num=hmSize-20+i;
            if(handMarks.markList.get(num).historyKey==handMarks.markList.getLast().historyKey&&handMarks.markList.get(i).historyFOnKSign){
                totalSign++;
            }
        }

        return totalSign>15&&handMarks.markList.getLast().historyMoveSign;
    }

    public static boolean isKeyPushed(HandMarks handMarks){
        boolean[] signList=new boolean[12];
        int totalSign=0;
        for(int i=1;i<signList.length;i++){
            int num=i*10;
            if(handMarks.markList.getLast().jointPoint[8].getLocation_z()-handMarks.markList.get(num).jointPoint[8].getLocation_z()>4.5){
                signList[i]=true;
            }
            if(handMarks.markList.get(num).historyMoveSign){
                int index=0;
                while(num+index<signList.length&&handMarks.markList.get(num+index).historyMoveSign){
                    index++;
                }
                if(handMarks.markList.getLast().jointPoint[8].getLocation_z()-handMarks.markList.get(num+index).jointPoint[8].getLocation_z()>-0.5){
                    signList[i]=true;
                }
            }
        }
        for(int i=1;i<signList.length;i++){
            if(signList[i]){
                totalSign++;
            }
        }
        if(totalSign/10.0>0.7){
            handMarks.markList.getLast().historyMoveSign=true;
        }
        //boolean isFingerOnKey=FingerDetect.isFingerOnKey(handMarks);
        boolean isKeyPushed=(handMarks.markList.getLast().gratitudeTag== Enums.GratitudeTag.RISING);//handMarks.markList.getLast().historyMoveSign && !handMarks.markList.get(handMarks.markList.size() - 2).historyMoveSig&&&&isFingerOnKey
        //&&(handMarks.markList.get(handMarks.markList.size() - 2).gratitudeTag== Enums.GratitudeTag.RISING)
        if(isKeyPushed){
            handMarks.markList.getLast().historyPushSign=true;
        }

        return isKeyPushed;
    }
}


