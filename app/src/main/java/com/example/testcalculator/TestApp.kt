package com.example.testcalculator

import androidx.multidex.MultiDexApplication
import com.example.testcalculator.utils.log.LogcatUtils
import com.example.testcalculator.utils.ResUIUtils
import com.example.testcalculator.utils.log.L
import ruiyue.nxr.vst.NxrVstJni

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

        //获取客户端
        val service = NxrVstJni.getNativeService()
        NxrVstJni.stopVst(service)
        NxrVstJni.stopStreaming(service)
    }
}