package com.samsung.android.biometrics.app.setting.fingerprint;

/* loaded from: classes.dex */
public interface HbmProvider {
    default int getCurrentAlpha() {
        return 0;
    }

    default boolean isEnabledHbm() {
        return true;
    }

    void turnOffHBM();

    void turnOffLightSource();

    void turnOnHBM();

    void turnOnLightSource();

    default void destroyHbmProvider() {
    }

    default void initHbmProvider() {
    }

    default void onConfigurationInfoChanged() {
    }

    default void onRotationChanged() {
    }

    default void turnOffCalibrationLightSource() {
    }

    default void turnOnCalibrationLightSource() {
    }
}
