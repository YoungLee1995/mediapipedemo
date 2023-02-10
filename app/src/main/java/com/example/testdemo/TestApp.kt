package com.example.testdemo

import androidx.multidex.MultiDexApplication
import com.example.testdemo.utils.log.LogcatUtils
import com.example.testdemo.utils.ResUIUtils
import com.example.testdemo.utils.log.L

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