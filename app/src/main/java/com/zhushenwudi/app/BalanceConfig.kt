package com.zhushenwudi.app

import top.keepempty.sph.library.SerialPortConfig

object BalanceConfig: SerialPortConfig(){
    fun getBalanceConfig(): BalanceConfig {
        mode = 0
        path = "dev/ttyS3"
        baudRate = 9600
        dataBits = 8
        parity = 'n'
        stopBits = 1
        return this
    }
}