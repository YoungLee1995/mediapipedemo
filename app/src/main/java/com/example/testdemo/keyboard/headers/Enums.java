package com.example.testdemo.keyboard.headers;

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


}
