package com.samsung.android.biometrics.app.setting.fingerprint;

/* loaded from: classes.dex */
public interface UdfpsWindowCallback {
    void onUserCancel(int i);

    default void onVisualEffectFinished() {
    }

    default void onSensorIconVisibilityChanged(int i) {
    }
}
