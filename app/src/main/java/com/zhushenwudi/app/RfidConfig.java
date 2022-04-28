package com.zhushenwudi.app;

import top.keepempty.sph.library.SerialPortConfig;

class RfidConfig extends SerialPortConfig {
    private RfidConfig() {
        setMode(0);
        setPath("dev/ttyS1");
        setBaudRate(19200);
        setDataBits(8);
        setParity('n');
        setStopBits(1);
    }

    private static class LazyHolder {
        static final RfidConfig instance = new RfidConfig();
    }

    public static RfidConfig getInstance() {
        return LazyHolder.instance;
    }
}