package com.example.testcalculator.keyboard.datastruct;

import java.util.LinkedList;

public class HandMarks {
    public LinkedList<HandMark> markList = new LinkedList<HandMark>();
    public LinkedList<HmGrat> graList = new LinkedList<HmGrat>();
    public LinkedList<HandPosture2D> postureList = new LinkedList<HandPosture2D>();
    public boolean outputPermitTag = true;   //可输出标记，在输出一次后置false，在检测到下降沿之后置true
    public int lastOutputFrame = 0;
    public int ID;

    public void pushback(HandMark h) {
        this.markList.add(h);
        HandPosture2D hp = new HandPosture2D(h);
        this.postureList.add(hp);
        if(this.markList.size()<1){
            HmGrat hg=new HmGrat();
            this.graList.add(hg);
        }else{
            HmGrat hg=new HmGrat(this.markList.getLast(),h);
            this.graList.add(hg);
        }
    }

    public void poplast() {
        this.markList.removeLast();
        this.postureList.removeLast();
        this.graList.removeLast();
    }

    public void popfront() {
        this.markList.removeFirst();
        this.postureList.removeFirst();
        this.graList.removeFirst();
    }
}
