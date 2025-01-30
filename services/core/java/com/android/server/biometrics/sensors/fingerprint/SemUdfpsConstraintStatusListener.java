package com.android.server.biometrics.sensors.fingerprint;

/* loaded from: classes.dex */
public interface SemUdfpsConstraintStatusListener {
    void onOneHandModeEnabled();

    default void onWirelessPowerEnabled() {
    }
}
