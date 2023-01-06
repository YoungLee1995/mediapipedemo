package com.example.testdemo

import android.graphics.RectF
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.testdemo.databinding.KeyBoardAtBinding
import com.open.androidtvwidget.keyboard.SoftKey
import com.open.androidtvwidget.keyboard.SoftKeyBoardListener

/**
 * 软键盘页面
 */
class KeyBoardAt : AppCompatActivity() {
    var mOldSoftKey: SoftKey? = null
    private lateinit var binding: KeyBoardAtBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = KeyBoardAtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.skbContainer.setSkbLayout(R.xml.sbd_qwerty)
        binding.skbContainer.isFocusable = true
        binding.skbContainer.isFocusableInTouchMode = true
        // 设置属性(默认是不移动的选中边框)
        setSkbContainerMove()
        //
        binding.skbContainer.setSelectSofkKeyFront(true) // 设置选中边框最前面.

        // 监听键盘事件.
        /*binding.skbContainer.setOnSoftKeyBoardListener(object : SoftKeyBoardListener {
            override fun onCommitText(softKey: SoftKey) {
                if (binding.skbContainer.getSkbLayoutId() == R.xml.skb_t9_keys) {
                    onCommitT9Text(softKey)
                } else {
                    var keyCode = softKey.keyCode
                    val keyLabel = softKey.keyLabel
                    if (!TextUtils.isEmpty(keyLabel)) { // 输入文字.
                        input_tv.setText(input_tv.getText().toString() + softKey.keyLabel)
                    } else { // 自定义按键，这些都是你自己在XML中设置的keycode.
                        keyCode = softKey.keyCode
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            val text: String = input_tv.getText().toString()
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(applicationContext, "文本已空", Toast.LENGTH_LONG).show()
                            } else {
                                input_tv.setText(text.substring(0, text.length - 1))
                            }
                        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                            finish()
                        } else if (keyCode == 66) {
                            Toast.makeText(applicationContext, "回车", Toast.LENGTH_LONG).show()
                        } else if (keyCode == 250) { //切换键盘
                            // 这里只是测试，你可以写自己其它的数字键盘或者其它键盘
                            setSkbContainerOther()
                            binding.skbContainer.setSkbLayout(R.xml.sbd_number)
                        }
                    }
                }
            }

            override fun onBack(key: SoftKey) {
                finish()
            }

            override fun onDelete(key: SoftKey) {
                val text: String = input_tv.getText().toString()
                input_tv.setText(text.substring(0, text.length - 1))
            }
        })*/
        // DEMO（测试键盘失去焦点和获取焦点)
        binding.skbContainer.setOnFocusChangeListener { v, hasFocus ->
            /*OPENLOG.D("hasFocus:$hasFocus")
            if (hasFocus) {
                if (mOldSoftKey != null) skbContainer.setKeySelected(mOldSoftKey) else skbContainer.setDefualtSelectKey(
                    0,
                    0
                )
            } else {
                mOldSoftKey = skbContainer.getSelectKey()
                skbContainer.setKeySelected(null)
            }*/
        }
    }

    private fun setSkbContainerMove() {
        mOldSoftKey = null
        binding.skbContainer.setMoveSoftKey(true) // 设置是否移动按键边框.
        val rectf = RectF(5f,
            5f,
            5f,
            5f)
        binding.skbContainer.setSoftKeySelectPadding(rectf) // 设置移动边框相差的间距.
        binding.skbContainer.setMoveDuration(200) // 设置移动边框的时间(默认:300)
        binding.skbContainer.setSelectSofkKeyFront(true) // 设置选中边框在最前面.
    }
}