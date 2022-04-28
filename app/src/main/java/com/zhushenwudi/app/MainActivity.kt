package com.zhushenwudi.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "MainActivity"
    }

    // kotlin 写法
    private val balanceApi by lazy {
        BalanceHelper({
            Log.d(TAG, "balance: $it")
        }, {
            Log.e(TAG, "balance: $it")
        })
    }

    // java 写法
    private val rfidApi by lazy {
        RfidHelper().setCallback(object : RfidCallback {
            override fun callback(result: String) {
                Log.d(TAG, "rfid: $result")
            }

            override fun message(message: String) {
                Log.e(TAG, "rfid: $message")
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        balanceApi.start()
        rfidApi.start()
    }

    override fun onDestroy() {
        if (balanceApi.isRunning()) {
            balanceApi.stop()
        }
        if (rfidApi.isRunning) {
            rfidApi.stop()
        }
        super.onDestroy()
    }
}