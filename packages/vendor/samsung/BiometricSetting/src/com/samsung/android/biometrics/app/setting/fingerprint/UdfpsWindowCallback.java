package com.samsung.android.biometrics.app.setting.fingerprint;

public interface UdfpsWindowCallback {
    void onSensorIconVisibilityChanged(int i);

    void onUserCancel(int i);

    default void onVisualEffectFinished() {}
}
