package com.example.testdemo.keyboard;

import java.util.LinkedList;

public class HandMarks {
    public LinkedList<HandMark> markList =new LinkedList<HandMark>();
    public LinkedList<Boolean> historyMoveSign =new LinkedList<>();
    public LinkedList<Boolean> historyPushSign =new LinkedList<>();
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void pushback(HandMark h, Boolean b){
        this.markList.add(h);
        this.historyMoveSign.addLast(b);
        this.historyPushSign.addLast(b);
    }
    public void pushFront(HandMark h,Boolean b){
        this.markList.addFirst(h);
        this.historyMoveSign.addFirst(b);
        this.historyPushSign.addFirst(b);
    }
    public void poplast(){
        this.markList.removeLast();
        this.historyMoveSign.removeLast();
        this.historyPushSign.removeLast();
    }
    public void popfront(){
        this.markList.removeFirst();   //移除队列第一个元素
        this.historyMoveSign.removeFirst();
        this.historyPushSign.removeFirst();
    }
}
