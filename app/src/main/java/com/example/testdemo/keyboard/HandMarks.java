package com.example.testdemo.keyboard;

import java.util.LinkedList;

public class HandMarks {
    public LinkedList<HandMark> markList =new LinkedList<HandMark>();
    public LinkedList<Boolean> historyMoveSign =new LinkedList<>();
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void push(HandMark h,Boolean b){
        this.markList.add(h);
        this.historyMoveSign.add(b);
    }
    public void pushFront(HandMark h,Boolean b){
        this.markList.addFirst(h);
        this.historyMoveSign.addFirst(b);
    }
    public void pop(){
        this.markList.pop();
        this.historyMoveSign.pop();
    }
    public void popfront(){
        this.markList.poll();   //移除队列第一个元素
        this.historyMoveSign.poll();
    }
}
