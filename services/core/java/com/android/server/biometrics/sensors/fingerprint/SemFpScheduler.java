package com.android.server.biometrics.sensors.fingerprint;

import com.android.server.biometrics.SemBiometricFeature;
import com.android.server.biometrics.sensors.SemConcurrentBiometricScheduler;

/* loaded from: classes.dex */
public class SemFpScheduler extends SemConcurrentBiometricScheduler {
    public SemFpScheduler(String str, GestureAvailabilityDispatcher gestureAvailabilityDispatcher) {
        super(str, SemBiometricFeature.FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE ? 2 : 3, SemFpMainThread.get().getHandler(), gestureAvailabilityDispatcher);
    }
}
