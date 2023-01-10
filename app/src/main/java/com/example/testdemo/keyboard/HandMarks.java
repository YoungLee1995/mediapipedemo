package com.example.testdemo.keyboard;

import java.util.LinkedList;

public class HandMarks {
    public LinkedList<HandMark> markList =new LinkedList<HandMark>();
    public LinkedList<Boolean> historyMoveSign =new LinkedList<>();
    public LinkedList<Boolean> historyPushSign =new LinkedList<>();
    public LinkedList<Boolean> historyFOnKSign =new LinkedList<>();
    public LinkedList<Integer> historyKey =new LinkedList<>();
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void pushback(HandMark h){
        this.markList.add(h);
        this.historyMoveSign.addLast(false);
        this.historyPushSign.addLast(false);
        this.historyFOnKSign.addLast(false);
        this.historyKey.addLast(null);
    }
    public void pushback(HandMark h,int currentId){
        this.markList.add(h);
        this.historyMoveSign.addLast(false);
        this.historyPushSign.addLast(false);
        this.historyFOnKSign.addLast(false);
        this.historyKey.addLast(currentId);
    }
    public void pushback(HandMark h, Boolean b1, Boolean b2, Boolean b3){
        this.markList.add(h);
        this.historyMoveSign.addLast(b1);
        this.historyPushSign.addLast(b2);
        this.historyFOnKSign.addLast(b3);
        this.historyKey.addLast(null);
    }
    public void pushback(HandMark h,int currentId, Boolean b1, Boolean b2, Boolean b3){
        this.markList.add(h);
        this.historyMoveSign.addLast(b1);
        this.historyPushSign.addLast(b2);
        this.historyFOnKSign.addLast(b3);
        this.historyKey.addLast(currentId);
    }
/*    public void pushFront(HandMark h){
        this.markList.addFirst(h);
        this.historyMoveSign.addFirst(false);
        this.historyPushSign.addFirst(false);
        this.historyFOnKSign.addFirst(false);
    }
    public void pushFront(HandMark h, Boolean b1, Boolean b2, Boolean b3){
        this.markList.add(h);
        this.historyMoveSign.addFirst(b1);
        this.historyPushSign.addFirst(b2);
        this.historyFOnKSign.addFirst(b3);
    }*/
    public void poplast(){
        this.markList.removeLast();
        this.historyMoveSign.removeLast();
        this.historyPushSign.removeLast();
        this.historyFOnKSign.removeLast();
        this.historyKey.removeLast();
    }
    public void popfront(){
        this.markList.removeFirst();   //移除队列第一个元素
        this.historyMoveSign.removeFirst();
        this.historyPushSign.removeFirst();
        this.historyFOnKSign.removeFirst();
        this.historyKey.removeFirst();
    }
}
