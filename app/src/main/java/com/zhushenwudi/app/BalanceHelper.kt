package com.zhushenwudi.app

import android.util.Log
import kotlinx.coroutines.*
import top.keepempty.sph.library.SerialListener
import top.keepempty.sph.library.SerialPortHelper
import java.util.*
import java.util.concurrent.atomic.AtomicBoolean

class BalanceHelper(
    private val callback: (message: String) -> Unit,
    private val message: (str: String) -> Unit
) : SerialListener {
    private var serialPortHelper: SerialPortHelper? = null
    private var isHandle = AtomicBoolean(false)
    private var sendScope: CoroutineScope? = null

    fun start() {
        Log.d(TAG, START_LOG)
        try {
            val config = MySerialPortConfig.getInnerBalanceConfig()
            serialPortHelper = SerialPortHelper(MAX_SIZE, this, config)
            if (serialPortHelper?.openDevice() == false) {
                Log.d(TAG, OPEN_BALANCE_FAILED)
                return
            }

            // 无限循环发送读取命令
            sendScope = CoroutineScope(Dispatchers.IO)
            sendScope?.launch {
                while(sendScope?.isActive == true) {
                    if (isHandle.get()) {
                        delay(100)
                    } else {
                        // 没有在处理数据
                        serialPortHelper?.sendHex(SEND_STR)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        Log.d(TAG, STOP_LOG)
        try {
            if (serialPortHelper?.isOpenDevice() == true) {
                serialPortHelper?.closeDevice()
                serialPortHelper = null
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (sendScope != null) {
            sendScope?.cancel()
            sendScope = null
        }
    }

    fun isRunning(): Boolean {
        return serialPortHelper != null
    }

    companion object {
        const val TAG = "balance"
        const val MAX_SIZE = 16
        const val SEND_STR = "1B70" // 读数
        const val START_LOG = "balance start"
        const val STOP_LOG = "balance stop"
        const val OPEN_BALANCE_FAILED = "电子秤打开失败"
    }

    override fun onNewData(data: ByteArray?) {
        if (isHandle.get()) {
            return
        }
        isHandle.set(true)
        val result = Arrays.toString(data)
        callback(result)
        isHandle.set(false)
    }

    override fun onRunError(e: Exception?) {
        message(e.toString())
        stop()
    }
}