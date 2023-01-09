package com.example.testdemo.keyboard;

import java.util.Objects;

public class FingerDetect {

    public static int pushedKey(Position finger1_position){
        TestKeyBoard testKeyBoard=new TestKeyBoard();
        testKeyBoard.Init();
        double distance=Double.MAX_VALUE;
        int id=0;
        for(int i=0;i<testKeyBoard.testKeyMap.size();i++){
            double d=Distance.pixel_distance(finger1_position, Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).getPosition());
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
        return Distance.pixelDistanceL1(finger1_position, Objects.requireNonNull(testKeyBoard.testKeyMap.get(id)).getPosition()) < 300;
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

        int historyWeightSign;
        boolean historyPushed;
        return handMarks.historyMoveSign.getLast() && !handMarks.historyMoveSign.get(handMarks.historyMoveSign.size() - 2);
    }
}


