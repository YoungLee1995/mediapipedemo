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
    public enum tapSign {
        noSignal,
        f1Tap,
        f2Tap,
        f3Tap,
        f5Tap,


        /*int code=0;
        tapSign(int code){
            this.code = code;
        }

        public int getCode(){
            return code;
        }
        public void setCode(int code){
            this.code = code;
        }*/

    }


}
