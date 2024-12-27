package com.samsung.android.biometrics.app.setting.fingerprint;

public interface HbmProvider {
    default int getCurrentAlpha() {
        return 0;
    }

    default boolean isEnabledHbm() {
        return true;
    }

    void turnOffCalibrationLightSource();

    void turnOffHBM();

    void turnOffLightSource();

    void turnOnCalibrationLightSource();

    void turnOnHBM();

    void turnOnLightSource();

    default void destroyHbmProvider() {}

    default void initHbmProvider() {}

    default void onConfigurationInfoChanged() {}

    default void onRotationChanged() {}
}
