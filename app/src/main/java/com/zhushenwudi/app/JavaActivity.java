package com.zhushenwudi.app;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JavaActivity extends AppCompatActivity {
    private final String TAG = "JavaActivity";
    private RfidHelper rfidApi = null;
    private BalanceHelper balanceApi = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rfidApi = new RfidHelper().setCallback(new RfidCallback() {
            @Override
            public void callback(@NonNull String result) {
                Log.d(TAG, "rfid: " + result);
            }

            @Override
            public void message(@NonNull String message) {
                Log.e(TAG, "rfid: " + message);
            }
        });

        balanceApi = new BalanceHelper(result -> {
            Log.d(TAG, "balance: " + result);
            return null;
        }, message -> {
            Log.e(TAG, "balance: " + message);
            return null;
        });

        rfidApi.start();
        balanceApi.start();
    }

    @Override
    protected void onDestroy() {
        if (rfidApi != null) {
            if (rfidApi.isRunning()) {
                rfidApi.stop();
            }
            rfidApi = null;
        }
        if (balanceApi != null) {
            if (balanceApi.isRunning()) {
                balanceApi.stop();
            }
            balanceApi = null;
        }
        super.onDestroy();
    }
}
