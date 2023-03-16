package com.example.testcalculator.bean

//屏幕分辨率实体类
data class ScreenPixelSize(var screenWidth: Int, var screenHeight: Int)

data class SignData(
    var signs: MutableList<SignBean> = mutableListOf()
)

data class SignBean(
    var id: Int = 0,
    var ids: MutableList<Int> = mutableListOf(),
    var moves: MutableList<WaveBean> = mutableListOf()
)

data class WaveBean(
    var wave: MutableList<Double> = mutableListOf()
)