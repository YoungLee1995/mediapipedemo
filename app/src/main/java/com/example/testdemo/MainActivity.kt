package com.example.testdemo

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.testdemo.databinding.ActivityMainBinding
import com.google.mediapipe.components.PermissionHelper
import com.google.mediapipe.framework.TextureFrame
import com.google.mediapipe.solutioncore.CameraInput
import com.google.mediapipe.solutioncore.SolutionGlSurfaceView
import com.google.mediapipe.solutions.hands.HandLandmark
import com.google.mediapipe.solutions.hands.Hands
import com.google.mediapipe.solutions.hands.HandsOptions
import com.google.mediapipe.solutions.hands.HandsResult

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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        //启动功能
        setupLiveDemoUiComponents()
    }

    private fun initView() {
        // 获取权限
        PermissionHelper.checkAndRequestCameraPermissions(this)
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

        //动态绘制键盘View
        binding.keyboardLayout.removeAllViewsInLayout()
        for (j in 0..2) {
            for (i in 0..6) {
                val textView = TextView(this)
                textView.x = 20f + (j * 320).toFloat()
                textView.y = (i * 220).toFloat()
                val params = FrameLayout.LayoutParams(300, 200)
                textView.layoutParams = params
                textView.text = "$i"
                textView.gravity = Gravity.CENTER
                textView.textSize = 20f
                textView.typeface = Typeface.defaultFromStyle(Typeface.BOLD)
                textView.setTextColor(ContextCompat.getColor(this, R.color.white))
                textView.setBackgroundResource(R.drawable.keyboard_btn_bg)
                binding.keyboardLayout.addView(textView)
            }
        }
        binding.keyboardLayout.requestLayout()
    }

    /*override fun onResume() {
        super.onResume()
        if (PermissionHelper.cameraPermissionsGranted(this)) {
            setupLiveDemoUiComponents()
        }
    }*/

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    private fun logWristLandmark(result: HandsResult, showPixelValues: Boolean) {
        if (result.multiHandLandmarks().isEmpty()) {
            return
        }
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
        if (result.multiHandWorldLandmarks().isEmpty()) {
            return
        }
        //multiHandLandmarks  像素坐标  multiHandWorldLandmarks  真实坐标
        //L.v(Gson().toJson(result.multiHandLandmarks()))
        val wristWorldLandmark =
            result.multiHandWorldLandmarks()[0].landmarkList[HandLandmark.INDEX_FINGER_TIP]

        L.v(
            String.format(
                "WorldLandmarks-------INDEX_FINGER_TIP:x=%f m, y=%f m, z=%f m",
                wristWorldLandmark.x, wristWorldLandmark.y, wristWorldLandmark.z
            )
        )
        /*Log.i(
            TAG, String.format(
                "MediaPipe Hand wrist world coordinates (in meters with the origin at the hand's"
                        + " approximate geometric center): x=%f m, y=%f m, z=%f m",
                wristWorldLandmark.x, wristWorldLandmark.y, wristWorldLandmark.z
            )
        )*/


    }
}