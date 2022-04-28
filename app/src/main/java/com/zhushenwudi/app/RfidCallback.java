package com.zhushenwudi.app;

import androidx.annotation.Keep;
import org.jetbrains.annotations.NotNull;

@Keep
public interface RfidCallback {
    void callback(@NotNull String result);

    void message(@NotNull String message);
}