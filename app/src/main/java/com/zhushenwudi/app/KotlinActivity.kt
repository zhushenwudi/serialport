package com.zhushenwudi.app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import top.keepempty.sph.library.SerialPortFinder

class KotlinActivity : AppCompatActivity() {

    private var yourChoice = -1

    companion object {
        private const val TAG = "MainActivity"
    }

    private val balanceApi by lazy {
        BalanceHelper({
            Log.d(TAG, "balance: $it")
        }, {
            Log.e(TAG, "balance: $it")
        })
    }

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

        findViewById<Button>(R.id.btnChoose).setOnClickListener {
            showSingleChoiceDialog()
        }
    }

    private fun showSingleChoiceDialog() {
        val items = SerialPortFinder().getAllDevicesPath()
        yourChoice = -1
        val singleChoiceDialog: AlertDialog.Builder = AlertDialog.Builder(this)
        singleChoiceDialog.setTitle("选择串口地址")
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0) { _, which ->
            yourChoice = which
        }
        singleChoiceDialog.setPositiveButton("确定") { _, _ ->
            if (yourChoice != -1) {
                Toast.makeText(this, "你选择了" + items[yourChoice], Toast.LENGTH_SHORT).show()
            }
        }
        singleChoiceDialog.show()
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