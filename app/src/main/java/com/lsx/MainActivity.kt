package com.lsx

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.Gson
import com.shouzhong.scanner.Result.TYPE_ID_CARD_FRONT
import com.shouzhong.scanner.Result.TYPE_LICENSE_PLATE
import kotlinx.android.synthetic.main.activity_main.*

/**
 *
 * 扫描身份证或者车牌
 * @author win_lsx
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    @SuppressLint("SetTextI18n")
     fun initView() {
        val viewFinder = ViewFinder(this)
        scannerView.setViewFinder(viewFinder)
        scannerView.setSaveBmp(false)
        // 启用身份证识别器（这里只支持2代身份证）
        scannerView.setEnableIdCard(true)
        scannerView.setEnableIdCard2(true)
        // 启用车牌识别
        scannerView.setEnableLicensePlate(true)
        scannerView.setCallback {
            if (it.type == TYPE_ID_CARD_FRONT) {
                val mIDCard =  Gson().fromJson(it.data,IDCard::class.java)
                Log.v("lsx", "：$mIDCard")
            } else if (it.type == TYPE_LICENSE_PLATE) {
                val cardNumber = it.data
            }
            tvResult.text = "识别结果：\n$it"
            Log.v("lsx", "识别结果：\n$it")
            scannerView.restartPreviewAfterDelay(2000)
        }
    }


    override fun onResume() {
        super.onResume()
        scannerView.onResume()
    }

    override fun onPause() {
        super.onPause()
        scannerView.onPause()
    }
}

