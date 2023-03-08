package com.example.testcalculator.keyboard.headers;

public class Enums {
    public static enum GratitudeTag {
        FLAT(1),
        RISING(2),
        DESCENDING(3);


        int code;
        GratitudeTag(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }

    }
    public static enum tapSign {
        noSignal(0),
        f1Tap(1),
        f2Tap(2),
        f3Tap(3),
        f5Tap(5);


        int code=0;
        tapSign(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }
        public void setCode(int code){
            this.code = code;
        }

    }


}
