package com.zhushenwudi.app;

import android.util.Log;
import androidx.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;
import top.keepempty.sph.library.DataConversion;
import top.keepempty.sph.library.SerialListener;
import top.keepempty.sph.library.SerialPortHelper;

class RfidHelper {
    private final String TAG = "rfid";
    private final String RFID_CANT_OPEN = "RFID_CANT_OPEN";
    private final String START_LOG = "rfid start";
    private final String STOP_LOG = "rfid stop";
    private final String SEND_STRING = "050001869095";

    private SerialPortHelper serialPortHelper = null;
    private final AtomicBoolean isHandle = new AtomicBoolean(false);
    private RfidCallback callback;

    private void openDevice() {
        // 打开设备
        if (!isRunning()) {
            serialPortHelper = new SerialPortHelper(15, new SerialListener() {
                @Override
                public void onNewData(@Nullable byte[] data) {
                    if (isHandle.get()) {
                        return;
                    }
                    isHandle.set(true);
                    String result = DataConversion.INSTANCE.encodeHexString(data);
                    if (callback != null) {
                        callback.callback(result);
                    }
                    isHandle.set(false);
                }

                @Override
                public void onRunError(@Nullable Exception e) {
                    if (callback != null && e != null && e.getMessage() != null) {
                        callback.message(e.getMessage());
                    }
                    stop();
                }
            }, RfidConfig.getInstance(), false);
            if (!serialPortHelper.openDevice() && callback != null) {
                callback.message(RFID_CANT_OPEN);
            }
        }
    }

    void start() {
        Log.e(TAG, START_LOG);
        openDevice();
        new Thread(() -> {
            while (serialPortHelper != null) {
                if (isHandle.get()) {
                    sleep();
                } else {
                    serialPortHelper.sendHex(SEND_STRING);
                }
            }
        }).start();
    }

    void stop() {
        callback = null;
        if (serialPortHelper != null) {
            if (serialPortHelper.isOpenDevice()) {
                serialPortHelper.closeDevice();
                serialPortHelper = null;
            }
        }
        Log.e(TAG, STOP_LOG);
    }

    RfidHelper setCallback(RfidCallback callback) {
        this.callback = callback;
        return this;
    }

    boolean isRunning() {
        return serialPortHelper != null;
    }
    
    private void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

