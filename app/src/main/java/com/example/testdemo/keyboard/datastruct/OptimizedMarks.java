package com.example.testdemo.keyboard.datastruct;

import com.example.testdemo.keyboard.datastruct.HandMark;
import com.example.testdemo.keyboard.datastruct.HandMarks;
import com.example.testdemo.keyboard.math.MoveAverage;

public class OptimizedMarks {
    public OptimizedMarks() {
    }
    public OptimizedMarks(HandMarks originMarks) {
        this.originMarks = originMarks;

        HandMark h0= MoveAverage.calc2Average(originMarks.markList.getFirst(),originMarks.markList.get(1));
        this.aveOptMarks.pushback(originMarks.markList.getFirst(),originMarks.historyKey.getFirst(),originMarks.historyMoveSign.getFirst(),
                originMarks.historyPushSign.getFirst(),originMarks.historyFOnKSign.getFirst());
        this.aveOptMarks.pushback(h0,originMarks.historyMoveSign.get(1),false,false);
        for(int i=3;i<originMarks.markList.size();i++){
            HandMark h=MoveAverage.calc4Average(originMarks.markList.get(i),originMarks.markList.get(i-1),
                    originMarks.markList.get(i-2),originMarks.markList.get(i-3));
            this.aveOptMarks.pushback(h);
        }
        h0=MoveAverage.calc2Average(originMarks.markList.getLast(),originMarks.markList.get(originMarks.markList.size()-2));
        this.aveOptMarks.pushback(h0);
    }

    public void pushback(HandMark handMark){
        this.originMarks.pushback(handMark);
        int listSize=originMarks.markList.size();
        if(listSize>4){
            HandMark h=MoveAverage.calc4Average(originMarks.markList.get(listSize-1),originMarks.markList.get(listSize-2),
                    originMarks.markList.get(listSize-3),originMarks.markList.get(listSize-4));
            this.aveOptMarks.pushback(h);
        }else{
            this.aveOptMarks.pushback(handMark);
        }
    }
    public void pushback(HandMark handMark, int keyId, boolean b1, boolean b2,boolean b3){
        this.originMarks.pushback(handMark,keyId,b1,b2,b3);
        int listSize=originMarks.markList.size();
        if(listSize>4){
            HandMark h=MoveAverage.calc4Average(originMarks.markList.get(listSize-1),originMarks.markList.get(listSize-2),
                    originMarks.markList.get(listSize-3),originMarks.markList.get(listSize-4));
            this.aveOptMarks.pushback(h,keyId,b1,b2,b3);
        }else{
            this.aveOptMarks.pushback(handMark,keyId,b1,b2,b3);
        }
    }

    public void popfront(){
        this.originMarks.popfront();
        this.aveOptMarks.popfront();
    }
    public void popback(){
        this.aveOptMarks.poplast();
        this.originMarks.poplast();
    }


    public HandMarks originMarks = new HandMarks();
    public HandMarks aveOptMarks = new HandMarks();
    public HandMarks multipOptMarks = new HandMarks();
}
