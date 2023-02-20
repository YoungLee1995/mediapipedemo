package com.example.testcalculator.keyboard.datastruct;

import java.util.LinkedList;

public class HandMarks {
    public LinkedList<HandMark> markList =new LinkedList<HandMark>();
    public boolean outputPermitTag =true;   //可输出标记，在输出一次后置false，在检测到下降沿之后置true
    public int lastOutputFrame=0;
    public int ID;

    public void pushback(HandMark h){
        this.markList.add(h);
    }
    public void poplast(){
        this.markList.removeLast();
    }
    public void popfront(){
        this.markList.removeFirst();
    }
}
