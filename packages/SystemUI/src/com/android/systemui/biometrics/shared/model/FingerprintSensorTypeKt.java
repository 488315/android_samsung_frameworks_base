package com.android.systemui.biometrics.shared.model;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

public abstract class FingerprintSensorTypeKt {
    public static final FingerprintSensorType toSensorType(int i) {
        if (i == 0) {
            return FingerprintSensorType.UNKNOWN;
        }
        if (i == 1) {
            return FingerprintSensorType.REAR;
        }
        if (i == 2) {
            return FingerprintSensorType.UDFPS_ULTRASONIC;
        }
        if (i == 3) {
            return FingerprintSensorType.UDFPS_OPTICAL;
        }
        if (i == 4) {
            return FingerprintSensorType.POWER_BUTTON;
        }
        if (i == 5) {
            return FingerprintSensorType.HOME_BUTTON;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid SensorType value: "));
    }
}
