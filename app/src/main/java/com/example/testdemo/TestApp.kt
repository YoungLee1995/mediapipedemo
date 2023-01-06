package com.example.testdemo

import androidx.multidex.MultiDexApplication

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
    }
}