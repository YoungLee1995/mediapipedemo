package com.example.testcalculator.keyboard.method2Outside;

import android.util.Log;

import com.example.testcalculator.keyboard.datastruct.HandMarks;
import com.example.testcalculator.keyboard.datastruct.KeyShape;
import com.example.testcalculator.keyboard.datastruct.Position;
import com.example.testcalculator.keyboard.datastruct.TestKeyBoard;
import com.example.testcalculator.keyboard.headers.Enums;
import com.example.testcalculator.keyboard.math.Distance;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FingerDetect {
    public static int onWhichKey(TestKeyBoard testKeyBoard, Position finger1_position) {
        double distance = Double.MAX_VALUE;
        int id = 99999;
        Position newKeyCenter = new Position();
        for (int i = 0; i < testKeyBoard.testKeyMap.size(); i++) {
            Position keyCenter = Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).position;
            KeyShape keyShape = Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).keyShape;
            double x = keyCenter.getPixel_x();
            double y = keyCenter.getPixel_y();
            double width = keyShape.getKey_width();
            double height = keyShape.getKey_height();
            newKeyCenter.setPixel_x(x + width / 2.0);
            newKeyCenter.setPixel_y(y + height / 2.0);
            double d = Distance.pixelDistance(finger1_position, newKeyCenter);
            int nearId = Objects.requireNonNull(testKeyBoard.testKeyMap.get(i)).getId();
            if(nearId<27){
                if (d < distance && isFingerOnKey(finger1_position, keyCenter, keyShape)) {
                    id = nearId;
                    //Log.v("距离id",d+":"+newKeyCenter.getPixel_x());
                    distance = d;
                }
            }
        }

        return id;
    }

    public static int onWhichKey(ArrayList<float[]> testKeyBoard, Position finger1_position) {
        double distance = Double.MAX_VALUE;
        int id = 99999;
        Position newKeyCenter = new Position();
        for (int i = 0; i < testKeyBoard.size(); i++) {
            double keyX = (testKeyBoard.get(i)[0]+testKeyBoard.get(i)[2]+
                    testKeyBoard.get(i)[4]+testKeyBoard.get(i)[6])/4.0;
            double keyY = (testKeyBoard.get(i)[1]+testKeyBoard.get(i)[3]+
                    testKeyBoard.get(i)[5]+testKeyBoard.get(i)[7])/4.0;
            double keyWidth=(-testKeyBoard.get(i)[0]+testKeyBoard.get(i)[2]-
                    testKeyBoard.get(i)[4]+testKeyBoard.get(i)[6])/2.0;
            double keyHeight = (-testKeyBoard.get(i)[1]+testKeyBoard.get(i)[3]-
                    testKeyBoard.get(i)[5]+testKeyBoard.get(i)[7])/4.0;
            Position keyCenter = new Position(keyX,keyY,0.0);
            KeyShape keyShape = new KeyShape(keyWidth,keyHeight,0.0);
            double x = keyCenter.getPixel_x();
            double y = keyCenter.getPixel_y();
            double width = keyShape.getKey_width();
            double height = keyShape.getKey_height();
            newKeyCenter.setPixel_x(x + width / 2.0);
            newKeyCenter.setPixel_y(y + height / 2.0);
            double d = Distance.pixelDistance(finger1_position, newKeyCenter);
            int nearId = i;
            if(nearId<27){
                if (d < distance && isFingerOnKey(finger1_position, keyCenter, keyShape)) {
                    id = nearId;
                    //Log.v("距离id",d+":"+newKeyCenter.getPixel_x());
                    distance = d;
                }
            }
        }

        return id;
    }

    public static boolean isFingerOnKey(TestKeyBoard testKeyBoard, Position finger1_position, int id) {
        //此处确定是否落入键盘的标准
        Position keyCenter = Objects.requireNonNull(testKeyBoard.testKeyMap.get(id)).getPosition();
        KeyShape keyShape = Objects.requireNonNull(testKeyBoard.testKeyMap.get(id)).getKeyShape();
        double x = keyCenter.getPixel_x();
        double y = keyCenter.getPixel_y();
        double width = keyShape.getKey_width();
        double height = keyShape.getKey_height();
        double pixelX = x + width / 2.0;
        double pixelY = y + height / 2.0;
        return (finger1_position.getPixel_x() - pixelX) < keyShape.getKey_width() / 2.0 &&
                (finger1_position.getPixel_y() - pixelY) < keyShape.getKey_height() / 2.0;
    }

    public static boolean isFingerOnKey(Position finger1_position, Position keyCenter, KeyShape keyShape ) {
        //此处确定是否落入键盘的标准
        double x = keyCenter.getPixel_x();
        double y = keyCenter.getPixel_y();
        double width = keyShape.getKey_width();
        double height = keyShape.getKey_height();
        double pixelX = x + width / 2.0;
        double pixelY = y + height / 2.0;
        return (finger1_position.getPixel_x() - pixelX) < keyShape.getKey_width() / 2.0 &&
                (finger1_position.getPixel_y() - pixelY) < keyShape.getKey_height() / 2.0;
    }

    public static boolean isFingerOnKey(HandMarks handMarks) {
        int totalSign = 0;
        int hmSize = handMarks.markList.size();
        if (hmSize < 119) return false;
        //统计近20帧手指是否大部分位于同一个按键内
        for (int i = 0; i < 20; i++) {
            int num = hmSize - 20 + i;
            if (handMarks.markList.get(num).historyKey == handMarks.markList.getLast().historyKey && handMarks.markList.get(i).historyFOnKSign) {
                totalSign++;
            }
        }

        return totalSign > 10 && handMarks.markList.getLast().historyMoveSign;
    }

    public static int[] pushedKey(TestKeyBoard testKeyBoard, HandMarks handMarks, int fingerId, int picNum) {
        Map<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> onKeyList=new ArrayList<>();

        int size=handMarks.markList.size();
        handMarks.markList.getLast().keyId=onWhichKey(testKeyBoard,handMarks.markList.getLast().jointPoint[fingerId]);
        for (int i=0;i<picNum;i++){
            int id=handMarks.markList.get(size-picNum+i).keyId;
            onKeyList.add(id);
        }
        for (int i = 0; i < onKeyList.size(); i++) {
            int num = onKeyList.get(i);
            if(num<100){
                if (map.containsKey(num)) {
                    map.put(num, map.get(num) + 1);
                } else {
                    map.put(num, 1);
                }
            }
        }

        int maxNum = 0;
        int maxCount = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            if (count > maxCount) {
                maxNum = num;
                maxCount = count;
            }
        }

        return new int[]{maxNum, maxCount};
    }

    public static int[] pushedKey(ArrayList<float[]> testKeyBoard, HandMarks handMarks, int fingerId, int picNum) {
        Map<Integer, Integer> map = new HashMap<>();
        ArrayList<Integer> onKeyList=new ArrayList<>();

        int size=handMarks.markList.size();
        handMarks.markList.getLast().keyId=onWhichKey(testKeyBoard,handMarks.markList.getLast().jointPoint[fingerId]);
        for (int i=0;i<picNum;i++){
            int id=handMarks.markList.get(size-picNum+i).keyId;
            onKeyList.add(id);
        }
        for (int i = 0; i < onKeyList.size(); i++) {
            int num = onKeyList.get(i);
            if(num<100){
                if (map.containsKey(num)) {
                    map.put(num, map.get(num) + 1);
                } else {
                    map.put(num, 1);
                }
            }
        }

        int maxNum = 0;
        int maxCount = 0;

        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int num = entry.getKey();
            int count = entry.getValue();
            if (count > maxCount) {
                maxNum = num;
                maxCount = count;
            }
        }

        return new int[]{maxNum, maxCount};
    }

    public static boolean isKeyPushed(HandMarks handMarks) {
        boolean[] signList = new boolean[12];
        int totalSign = 0;
        for (int i = 1; i < signList.length; i++) {
            int num = i * 10;
            if (handMarks.markList.getLast().jointPoint[8].getLocation_z() - handMarks.markList.get(num).jointPoint[8].getLocation_z() > 4.5) {
                signList[i] = true;
            }
            if (handMarks.markList.get(num).historyMoveSign) {
                int index = 0;
                while (num + index < signList.length && handMarks.markList.get(num + index).historyMoveSign) {
                    index++;
                }
                if (handMarks.markList.getLast().jointPoint[8].getLocation_z() - handMarks.markList.get(num + index).jointPoint[8].getLocation_z() > -0.5) {
                    signList[i] = true;
                }
            }
        }
        for (int i = 1; i < signList.length; i++) {
            if (signList[i]) {
                totalSign++;
            }
        }
        if (totalSign / 10.0 > 0.7) {
            handMarks.markList.getLast().historyMoveSign = true;
        }
        //boolean isFingerOnKey=FingerDetect.isFingerOnKey(handMarks);
        boolean isKeyPushed = (handMarks.markList.getLast().gratitudeTag == Enums.GratitudeTag.RISING);//handMarks.markList.getLast().historyMoveSign && !handMarks.markList.get(handMarks.markList.size() - 2).historyMoveSig&&&&isFingerOnKey
        //&&(handMarks.markList.get(handMarks.markList.size() - 2).gratitudeTag== Enums.GratitudeTag.RISING)
        if (isKeyPushed) {
            handMarks.markList.getLast().historyPushSign = true;
        }

        return isKeyPushed;
    }
}


