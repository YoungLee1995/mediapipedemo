package com.example.testdemo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat

class HandGestureView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null)  : View(context,attrs) {
    private var framePaint: Paint = Paint()
    private var circlePaint: Paint = Paint()
    private var startX:Float = 25f
    private var startY:Float = 25f
    init{
        //初始化画笔
        //抗锯齿
        framePaint.isAntiAlias = true
        //设置颜色
        framePaint.color = ContextCompat.getColor(context,R.color.purple_500)
        //样式  描边
        framePaint.style = Paint.Style.STROKE
        framePaint.strokeWidth = 6f

        //抗锯齿
        circlePaint.isAntiAlias = true
        //设置颜色
        circlePaint.color = ContextCompat.getColor(context,R.color.red)
        //样式  描边
        circlePaint.style = Paint.Style.FILL
    }

    /*override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        width=getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        height=getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        //该方法是系统自带的  撑满当前容器的宽高
        //measureChildren(width!!, height!!)
        setMeasuredDimension(width!!,height!!)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
    }*/
   /* override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        this.x = 20f
        this.y = 30f
    }*/

    override fun onDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        //画边框
        canvas?.drawCircle(startX,startY,23f,framePaint)
        //画内圆
        canvas?.drawCircle(startX,startY,20f,circlePaint)
    }
}