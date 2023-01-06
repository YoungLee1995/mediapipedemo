package com.example.testdemo.keyboard;

public class OptimizedMarks {
    public OptimizedMarks() {
    }
    public OptimizedMarks(HandMarks originMarks) {
        this.originMarks = originMarks;

        HandMark h0=MoveAverage.calc2Average(originMarks.markList.getFirst(),originMarks.markList.get(1));
        this.aveOptMarks.push(h0,false);
        this.aveOptMarks.push(h0,false);
        for(int i=3;i<originMarks.markList.size();i++){
            HandMark h=MoveAverage.calc4Average(originMarks.markList.get(i),originMarks.markList.get(i-1),
                    originMarks.markList.get(i-2),originMarks.markList.get(i-3));
            this.aveOptMarks.push(h,false);
        }
        h0=MoveAverage.calc2Average(originMarks.markList.getLast(),originMarks.markList.get(originMarks.markList.size()-2));
        this.aveOptMarks.push(h0,false);
    }

    public void pushfront(HandMark handMark){
        this.originMarks.push(handMark,false);
        int listSize=originMarks.markList.size();
        if(listSize>3){
            HandMark h=MoveAverage.calc4Average(originMarks.markList.get(listSize),originMarks.markList.get(listSize-1),
                    originMarks.markList.get(listSize-2),originMarks.markList.get(listSize-3));
            this.aveOptMarks.push(h,false);
        }else{
            this.aveOptMarks.push(handMark,false);
        }

    }

    public void popfront(){
        this.originMarks.popfront();
        this.aveOptMarks.popfront();
    }
    public void popback(){
        this.aveOptMarks.pop();
        this.originMarks.pop();
    }


    public HandMarks originMarks;
    public HandMarks aveOptMarks;
    public HandMarks multipOptMarks;
}
