package com.example.testdemo.keyboard.taprules;

import com.example.testdemo.keyboard.datastruct.HandMarks;
import com.example.testdemo.keyboard.datastruct.KeyShape;
import com.example.testdemo.keyboard.datastruct.Position;
import com.example.testdemo.keyboard.datastruct.TestKeyBoard;
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
        //统计近40帧手指是否都位于当前帧所在按键内
        for(int i=0;i<20;i++){
            int num=hmSize-20+i;
            if(handMarks.historyKey.get(i)==handMarks.historyKey.getLast()&&handMarks.historyFOnKSign.get(i)){
                hmSize++;
            }
        }

        return hmSize>15&&handMarks.historyFOnKSign.getLast();
    }

    public static boolean isKeyPushed(HandMarks handMarks){
        boolean[] signList=new boolean[12];
        int totalSign=0;
        for(int i=1;i<signList.length;i++){
            int num=i*10;
            if(handMarks.markList.getLast().jointPoint[8].getLocation_z()-handMarks.markList.get(num).jointPoint[8].getLocation_z()>4.5){
                signList[i]=true;
            }
            if(handMarks.historyMoveSign.get(num)){
                int index=0;
                while(num+index<signList.length&&handMarks.historyMoveSign.get(num+index)){
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
            handMarks.historyMoveSign.removeLast();
            handMarks.historyMoveSign.addLast(true);
        }
        boolean isFingerOnKey=FingerDetect.isFingerOnKey(handMarks);
        boolean isKeyPushed=handMarks.historyMoveSign.getLast() && !handMarks.historyMoveSign.get(handMarks.historyMoveSign.size() - 2)&&isFingerOnKey;
        if(isKeyPushed){
            handMarks.historyPushSign.removeLast();
            handMarks.historyPushSign.addLast(true);
        }

        return isKeyPushed;
    }
}


