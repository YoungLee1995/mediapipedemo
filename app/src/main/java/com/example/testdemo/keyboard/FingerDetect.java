package com.example.testdemo.keyboard;

import java.util.Objects;

public class FingerDetect {

    public static int pushedKey(Position finger1_position){
        TestKeyBoard testKeyBoard=new TestKeyBoard();
        testKeyBoard.Init();
        double distance=Double.MAX_VALUE;
        int id=0;
        for(int i=0;i<testKeyBoard.testKeyMap.size();i++){
            Position keyCenter = Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).getPosition();
            double x=keyCenter.getPixel_x();
            double y=keyCenter.getPixel_y();
            keyCenter.setPixel_x(x+250);
            keyCenter.setPixel_y(y+250);
            double d=Distance.pixelDistanceMax(finger1_position, keyCenter);
            if(d<distance){
                id= Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).getId();
                distance=d;
            }
        }

        return id;
    }

    public static boolean isFingerOnKey(Position finger1_position, int id){
        TestKeyBoard testKeyBoard=new TestKeyBoard();
        testKeyBoard.Init();
        //此处确定是否落入键盘的标准
        Position keyCenter = Objects.requireNonNull(testKeyBoard.testKeyMap.get(id)).getPosition();
        double x=keyCenter.getPixel_x();
        double y=keyCenter.getPixel_y();
        keyCenter.setPixel_x(x+250);
        keyCenter.setPixel_y(y+250);
        return Distance.pixelDistanceMax(finger1_position, keyCenter) < 200;
    }

    public static boolean isFingerOnKey(HandMarks handMarks){
        int totalSign=0;
        int hmSize=handMarks.markList.size();
        if(hmSize<119) return false;
        //统计近40帧手指是否都位于当前帧所在按键内
        for(int i=0;i<40;i++){
            int num=hmSize-40+i;
            if(handMarks.historyKey.get(i)==handMarks.historyKey.getLast()&&handMarks.historyFOnKSign.get(i)){
                hmSize++;
            }
        }

        return hmSize>35&&handMarks.historyFOnKSign.getLast();
    }

    public static boolean isKeyPushed(HandMarks handMarks){
        boolean[] signList=new boolean[12];
        int totalSign=0;
        for(int i=1;i<signList.length;i++){
            int num=i*10;
            if(handMarks.markList.getLast().jointPoint[8].getLocation_z()-handMarks.markList.get(num).jointPoint[8].getLocation_z()>2.5){
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


