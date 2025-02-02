package com.samsung.android.biometrics.app.setting;

/* loaded from: classes.dex */
public interface ClientCallback {
    default void onClientStarted() {
    }

    default void onClientFinished(SysUiClient sysUiClient) {
    }
}
