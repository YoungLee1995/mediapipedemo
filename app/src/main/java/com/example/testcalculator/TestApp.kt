package com.example.testcalculator

import androidx.multidex.MultiDexApplication
import com.example.testcalculator.utils.log.LogcatUtils
import com.example.testcalculator.utils.ResUIUtils
import com.example.testcalculator.utils.log.L

/**
 * App启动入口
 */
class TestApp : MultiDexApplication() {
    companion object {
        lateinit var instance: TestApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        ResUIUtils.init(instance)
        L.setDebug(true)
        LogcatUtils.newInstance().init(this)
    }
}