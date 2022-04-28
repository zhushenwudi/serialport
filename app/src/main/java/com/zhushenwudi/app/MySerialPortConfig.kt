package com.zhushenwudi.app

import top.keepempty.sph.library.SerialPortConfig

object MySerialPortConfig: SerialPortConfig(){
    fun getHFRfidConfig(): MySerialPortConfig {
        mode = 0
        path = "dev/ttyS1"
        baudRate = 19200
        dataBits = 8
        parity = 'n'
        stopBits = 1
        return this
    }

    fun getInnerBalanceConfig(): MySerialPortConfig {
        mode = 0
        path = "dev/ttyS3"
        baudRate = 9600
        dataBits = 8
        parity = 'n'
        stopBits = 1
        return this
    }
}