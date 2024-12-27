package com.samsung.android.biometrics.app.setting;

public interface ClientCallback {
    default void onClientFinished(SysUiClient sysUiClient) {}

    default void onClientStarted(SysUiClient sysUiClient) {}
}
