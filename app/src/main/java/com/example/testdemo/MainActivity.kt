package com.example.testdemo

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Typeface
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.testdemo.bean.ScreenPixelSize
import com.example.testdemo.databinding.ActivityMainBinding
import com.example.testdemo.keyboard.*
import com.example.testdemo.utils.ResUIUtils
import com.google.mediapipe.framework.TextureFrame
import com.google.mediapipe.solutioncore.CameraInput
import com.google.mediapipe.solutioncore.SolutionGlSurfaceView
import com.google.mediapipe.solutions.hands.HandLandmark
import com.google.mediapipe.solutions.hands.Hands
import com.google.mediapipe.solutions.hands.HandsOptions
import com.google.mediapipe.solutions.hands.HandsResult
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import java.lang.Runnable
import kotlin.time.Duration.Companion.seconds


/**
 * 摄像头展示界面
 */
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // 实时相机演示UI和相机组件。
    private lateinit var cameraInput: CameraInput
    private var glSurfaceView: SolutionGlSurfaceView<HandsResult>? = null
    private var hands: Hands? = null
    private val TAG = "MainActivity"
    private lateinit var keyboard:TestKeyBoard
    private val keyContent:StringBuffer = StringBuffer()
    private val keyBoardViewMap = HashMap<Int,TextView>()
    private var animation: AnimatorSet?=null
    private var selectView :TextView?=null

    private val handsView = mutableListOf<HandGestureView>()
    private var pixelWidth:Int = 0
    private var pixelHeight:Int = 0
    private var screenSize :ScreenPixelSize?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        screenSize = ResUIUtils.getScreenPixelSize(this)
        initView()
        ///启动功能
        initData()
        //setupLiveDemoUiComponents()
        //find_camera_resolution()
    }

    private fun initView() {
        /*binding.btnOpen.setOnClickListener {
            startActivity(Intent(this,KeyBoardAt::class.java))
        }*/
        /*binding.btnOpen.post {
            val location = IntArray(2)
            binding.btnOpen.getLocationOnScreen(location)
            val x = location[0] // view 距离 屏幕左边的距离（即 x 轴方向）
            val y = location[1] // view 距离 屏幕顶边的距离（即 y 轴方向）
            Log.v("多少坐标", "$x-----$y")

            Log.v("多少坐标2", "${binding.btnOpen.x}-----${binding.btnOpen.y}")

            Log.v("多少坐标top", "${binding.btnOpen.top}")
            Log.v("多少坐标left", "${binding.btnOpen.left}")
            Log.v("多少坐标right", "${binding.btnOpen.right}")
            Log.v("多少坐标bottom", "${binding.btnOpen.bottom}")

            Log.v("多少", "${ResUIUtils.dip2px(100f)}")
        }*/
        keyContent.setLength(0)
        keyboard = TestKeyBoard()
        keyboard.Init()
        //动态绘制键盘View
        binding.keyboardLayout.removeAllViewsInLayout()
        keyBoardViewMap.clear()
        for ((key,keyboard) in keyboard.testKeyMap){
            val textView = TextView(this)
            textView.x = (keyboard.position.pixel_x).toFloat()
            textView.y = (keyboard.position.pixel_y).toFloat()
            val params = FrameLayout.LayoutParams(keyboard.keyShape.key_width.toInt(), keyboard.keyShape.key_height.toInt())
            textView.layoutParams = params
            textView.text = "$key"
            textView.gravity = Gravity.CENTER
            textView.textSize = 20f
            textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
            textView.setTextColor(ContextCompat.getColor(this, R.color.white))
            textView.setBackgroundResource(R.drawable.keyboard_btn_bg)
            binding.keyboardLayout.addView(textView)
            //把按钮的View对象存储起来
            keyBoardViewMap[key] = textView
        }
        binding.keyboardLayout.requestLayout()

        binding.btnOpen.setOnClickListener {
            keyContent.setLength(0)
            binding.keyboardContent.text = keyContent.toString()
            //把当前选中的View重置回去
            recoverySelectView()
            selectView = null
        }

        /*binding.keyboardLayout.post {
            pixelWidth = binding.keyboardLayout.width
            pixelHeight = binding.keyboardLayout.height
            Log.v("多少111","$pixelWidth==$pixelHeight")
        }*/

        binding.keyboardLayout.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= 16) {
                    binding.keyboardLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                } else {
                    binding.keyboardLayout.viewTreeObserver.removeGlobalOnLayoutListener(this)
                }
                pixelWidth = binding.keyboardLayout.width
                pixelHeight = binding.keyboardLayout.height
            }
        })
        binding.flHandSpot.removeAllViewsInLayout()
        handsView.clear()
        for (i in 0..20){
            val handGestureView = HandGestureView(this)
            binding.flHandSpot.addView(handGestureView)
            handsView.add(handGestureView)
        }
    }

    private fun initData(){
        XXPermissions.with(this)
            // 申请单个权限
            .permission(Permission.CAMERA)
            // 设置权限请求拦截器（局部设置）
            //.interceptor(new PermissionInterceptor())
            // 设置不触发错误检测机制（局部设置）
            //.unchecked()
            .request(object : OnPermissionCallback {
                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
                    if (!allGranted) {
                        Toast.makeText(this@MainActivity,"获取部分权限成功，但部分权限未正常授予",Toast.LENGTH_SHORT).show()
                        return
                    }
                    setupLiveDemoUiComponents()
                }

                override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
                    if (doNotAskAgain) {
                        Toast.makeText(this@MainActivity,"被永久拒绝授权,请手动授予相机权限",Toast.LENGTH_SHORT).show()
                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
                        XXPermissions.startPermissionActivity(this@MainActivity, permissions)
                    } else {
                        Toast.makeText(this@MainActivity,"获取相机权限失败",Toast.LENGTH_SHORT).show()
                    }
                }
            })
    }

    /**设置带有摄像头输入的实时演示的UI组件*/
    private fun setupLiveDemoUiComponents() {
        //清除数据
        //stopCurrentPipeline()
        //启动摄像头输入功能
        setupStreamingModePipeline()
    }

    private fun stopCurrentPipeline() {
        /*if (cameraInput != null) {
            cameraInput.setNewFrameListener(null)
            cameraInput.close()
        }*/
        if (glSurfaceView != null) {
            glSurfaceView?.visibility = View.GONE
        }
        if (hands != null) {
            hands?.close()
        }
    }

    /** 设置流模式的核心工作流  */
    private fun setupStreamingModePipeline() {
        // 在流模式下初始化新的MediaPipe Hands解决方案实例。
        hands = Hands(
            this,
            HandsOptions.builder()
                .setStaticImageMode(false)
                .setMaxNumHands(1)
                .setRunOnGpu(true)
                .build()
        )
        hands?.setErrorListener { message: String, e: RuntimeException? ->
            Log.e(
                TAG,
                "MediaPipe Hands error:$message"
            )
        }
        // 初始化新的CameraInput实例并将其连接到MediaPipe Hands Solution。
        cameraInput = CameraInput(this)
        cameraInput.setNewFrameListener { textureFrame: TextureFrame? ->
            hands?.send(
                textureFrame
            )
        }

        // 使用ResultGlRenderer<HandsResult>实例初始化新的GlSurfaceView
        // 它提供了运行用户定义的OpenGL渲染代码的接口。
        glSurfaceView = hands?.glMajorVersion?.let {
            SolutionGlSurfaceView<HandsResult>(
                this,
                hands?.glContext,
                it
            )
        }
        /*val params = ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        params.width = pixelWidth
        params.height = pixelHeight
        glSurfaceView?.layoutParams = params*/

        glSurfaceView?.setSolutionResultRenderer(HandsResultGlRenderer())
        glSurfaceView?.setRenderInputImage(true)
        hands?.setResultListener { handsResult: HandsResult ->
            logWristLandmark(handsResult,  /*showPixelValues=*/false)
            //请求GL渲染
            glSurfaceView?.setRenderData(handsResult)
            glSurfaceView?.requestRender()
        }

        //连接gl曲面视图后可运行以启动相机。
        glSurfaceView?.post(Runnable { this.startCamera() })

        // 更新预览布局。
        val frameLayout = findViewById<FrameLayout>(R.id.preview_display_layout)
        frameLayout.removeAllViewsInLayout()
        frameLayout.addView(glSurfaceView)
        glSurfaceView?.visibility = View.VISIBLE
        frameLayout.requestLayout()
    }

    private fun startCamera() {
        cameraInput.start(
            this,
            hands!!.glContext,
            CameraInput.CameraFacing.BACK,
            glSurfaceView!!.width,
            glSurfaceView!!.height
        )
    }
    val handMarks = HandMarks()
    val optimizedMarks = OptimizedMarks()
    private fun logWristLandmark(result: HandsResult, showPixelValues: Boolean) {
        /*if (result.multiHandLandmarks().isEmpty()) {
            return
        }*/
        //val wristLandmark = result.multiHandLandmarks()[0].landmarkList[HandLandmark.WRIST]
        // 对于位图，显示像素值。对于纹理输入，显示规格化坐标。
        /*if (showPixelValues) {
            val width = result.inputBitmap().width
            val height = result.inputBitmap().height
            Log.i(
                TAG, String.format(
                    "MediaPipe Hand wrist coordinates (pixel values): x=%f, y=%f",
                    wristLandmark.x * width, wristLandmark.y * height
                )
            )
        } else {
            Log.i(
                TAG, String.format(
                    "MediaPipe Hand wrist normalized coordinates (value range: [0, 1]): x=%f, y=%f",
                    wristLandmark.x, wristLandmark.y
                )
            )
        }*/
        if (result.multiHandWorldLandmarks().isEmpty()||result.multiHandLandmarks().isEmpty()) {
            binding.flHandSpot.post{
                binding.flHandSpot.visibility = View.GONE
            }
            return
        }

        //multiHandLandmarks  像素坐标  multiHandWorldLandmarks  真实坐标
        //L.v(Gson().toJson(result.multiHandLandmarks()))
        //获取真实坐标
        val landmark = result.multiHandWorldLandmarks()[0].landmarkList
        //获取像素坐标
        val normalizedLandmark = result.multiHandLandmarks()[0].landmarkList

        if(landmark.isEmpty()&&normalizedLandmark.isEmpty()){
            binding.flHandSpot.post{
                binding.flHandSpot.visibility = View.GONE
            }
            return
        }
        binding.flHandSpot.post {
            if(binding.flHandSpot.visibility == View.GONE){
                binding.flHandSpot.visibility = View.VISIBLE
            }
            //因为输出的图像宽高比是4:3的  所以需要按照高度转换一下得到图像原始宽度
            val widthSize = (pixelHeight*(3.0/4.0)).toFloat()
            Log.v("多少","$pixelHeight===$widthSize===${screenSize?.screenWidth}")
            for ((index,item) in normalizedLandmark.withIndex()){
                //原始宽度减去手机屏幕宽度再除以2  得到左右多出多少间距  再减去自定义圆点的半径  即23
                val pixelX = (item.x*widthSize-(widthSize-screenSize?.screenWidth!!)/2.0)-23.0
                val pixelY = item.y*pixelHeight-23
                val view = handsView[index]
                if(index==0){
                    Log.v("多少","$pixelX")
                }
                view.x = pixelX.toFloat()
                view.y = pixelY
            }
        }

        //L.v("長度---${landmark.size}----${normalizedLandmark.size}")
        //坐标转换成handMark
        val hand= normalizedLandmark[HandLandmark.WRIST]
        L.v("${hand.x}-----${hand.y}")
        val handMark = HandMark.lm2hm(landmark, normalizedLandmark)

        //判断是否满足120帧
        if (optimizedMarks.aveOptMarks.markList.size < 120) {
            //计算各信号值，存储前120帧数据
            val position = handMark.jointPoint[8]
            val intKeyId= FingerDetect.pushedKey(position)
            val isFingerOnKey = FingerDetect.isFingerOnKey(position,intKeyId)
            optimizedMarks.pushback(handMark,intKeyId,false,false,isFingerOnKey)
        } else {
            //满足120帧  先删除第一针
            val position = optimizedMarks.aveOptMarks.markList.last.jointPoint[8]
            val intKeyId= FingerDetect.pushedKey(position)
            val isFingerOnKey = FingerDetect.isFingerOnKey(position,intKeyId)
            optimizedMarks.popfront()
            optimizedMarks.pushback(handMark,intKeyId,false,false,isFingerOnKey)
            val isKeyPushed = FingerDetect.isKeyPushed(optimizedMarks.aveOptMarks)
            if(isKeyPushed){
                //L.v("当前出发的ID=========${keyboard.testKeyMap[intKeyId]?.id}")
                if(keyContent.isEmpty()){
                    keyContent.append(keyboard.testKeyMap[intKeyId]?.id)
                }else{
                    keyContent.append(",${keyboard.testKeyMap[intKeyId]?.id}")
                }
                binding.keyboardContent.post {
                    keyBoardViewMap[intKeyId]?.let { keyView->
                        if(selectView!=null){
                            //重置之前的View为原始状态
                            recoverySelectView()
                        }
                        //设置选中的View效果
                        selectView = keyView
                        keyView.setBackgroundResource(R.drawable.keyboard_btn_press_bg)
                        if(animation!=null){
                            //取消之前沒有完成的动画
                            animation?.cancel()
                        }
                        val objectAnimationX = ObjectAnimator.ofFloat(keyView, "scaleX", 1f,0.9f)
                        val objectAnimationY = ObjectAnimator.ofFloat(keyView, "scaleY", 1f,0.9f)
                        animation = AnimatorSet()
                        animation?.play(objectAnimationX)?.with(objectAnimationY)
                        animation?.duration = 200
                        animation?.addListener(object :AnimatorListener{

                            override fun onAnimationStart(animation: Animator) {
                            }

                            override fun onAnimationEnd(animation: Animator) {
                                lifecycleScope.coroutineContext.cancelChildren()
                                lifecycleScope.launch {
                                    flow<Boolean> {
                                        delay(1.seconds)
                                        emit(true)
                                    }.collect{
                                        //取消缩放效果 恢复原始状态
                                        recoverySelectView()
                                        selectView = null
                                    }
                                }
                            }

                            override fun onAnimationCancel(animation: Animator) {
                            }

                            override fun onAnimationRepeat(animation: Animator) {
                            }

                        })
                        animation?.start()
                    }
                    binding.keyboardContent.text = keyContent.toString()
                }
            }
        }

        /*val wristWorldLandmark =
            result.multiHandWorldLandmarks()[0].landmarkList[HandLandmark.INDEX_FINGER_TIP]
*/
        /*L.v(
            String.format(
                "WorldLandmarks-------INDEX_FINGER_TIP: z=%f m", wristWorldLandmark.z
            )
        )*/
        /*Log.i(
            TAG, String.format(
                "MediaPipe Hand wrist world coordinates (in meters with the origin at the hand's"
                        + " approximate geometric center): x=%f m, y=%f m, z=%f m",
                wristWorldLandmark.x, wristWorldLandmark.y, wristWorldLandmark.z
            )
        )*/
    }
    private fun recoverySelectView(){
        selectView?.let {
            it.setBackgroundResource(R.drawable.keyboard_btn_bg)
            val objectAnimationX = ObjectAnimator.ofFloat(it, "scaleX", 0.9f,1f)
            val objectAnimationY = ObjectAnimator.ofFloat(it, "scaleY", 0.9f,1f)
            val animationSet = AnimatorSet()
            animationSet.play(objectAnimationX)?.with(objectAnimationY)
            animationSet.duration = 200
            animationSet.start()
        }
    }

    private var mPreviewScale:Float= 0f
    private var mPreviewWidth:Int = 0
    private var mPreviewHeight:Int = 0
    fun find_camera_resolution() {
        val mCamera: Camera = Camera.open()
        val params: Camera.Parameters = mCamera.getParameters()
        val vSizes : List<Camera.Size> = params.getSupportedPreviewSizes()

        //获取和屏幕比列相近的 一个尺寸
        val previewSize: Camera.Size = getSuitableSize(vSizes)
//重新定义 宽和高 （宽始终小于高）
        //重新定义 宽和高 （宽始终小于高）
        val previewWidth = Math.min(previewSize.width, previewSize.height)
        val previewHeight = Math.max(previewSize.width, previewSize.height)

        //获取 最终 我们需要预览的大小比列
        mPreviewScale = previewWidth * 1f / previewHeight;
        //算出 最终 宽高
        if (mPreviewWidth > previewWidth)
            mPreviewHeight = (mPreviewWidth / mPreviewScale).toInt()
        else if (mPreviewWidth < previewWidth)
            mPreviewHeight = (mPreviewHeight * mPreviewScale).toInt()



        Log.v("多少","$mPreviewWidth-----$mPreviewHeight")
        //parameters.setPreviewSize(mPreviewWidth, mPreviewHeight); // 设置预览图像大小


        //val widths = IntArray(params.getSupportedPreviewSizes().size)
        //val heights = IntArray(params.getSupportedPictureSizes().size)
        //var mSize: Camera.Size?
        /*for (size in sizes) {
            *//*Toast.makeText(
                this,
                "Available resolution:" + widths.size + "" + size.height,
                Toast.LENGTH_LONG
            ).show()
            mSize = size*//*
            Log.v("多少","宽：${size.width}====高：${size.height}")
        }*/
    }

    private fun getSuitableSize(sizes : List<Camera.Size>):Camera.Size{
        var minDelta = Int.MAX_VALUE // 最小的差值，初始值应该设置大点保证之后的计算中会被重置
        var index = 0 // 最小的差值对应的索引坐标
        for (i in 0 until sizes.size){
            val previewSize = sizes[i]
            Log.v(
                TAG,
                "SupportedPreviewSize, width: " + previewSize.width.toString() + ", height: " + previewSize.height
            )
            // 找到一个与设置的分辨率差值最小的相机支持的分辨率大小
            if ((previewSize.width * mPreviewScale).toInt() === previewSize.height){
                val delta: Int = Math.abs(mPreviewWidth - previewSize.width)
                if (delta == 0) {
                    return previewSize
                }
                if (minDelta > delta){
                    minDelta = delta
                    index = i
                }
            }
        }
        return sizes[index] // 默认返回与设置的分辨率最接近的预览尺寸
    }
}