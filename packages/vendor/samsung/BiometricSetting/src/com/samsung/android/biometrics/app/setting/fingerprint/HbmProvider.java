package com.samsung.android.biometrics.app.setting.fingerprint;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
/* loaded from: classes.dex */
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
