package com.android.systemui.biometrics.shared.model;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class SensorStrengthKt {
    public static final SensorStrength toSensorStrength(int i) {
        if (i == 0) {
            return SensorStrength.CONVENIENCE;
        }
        if (i == 1) {
            return SensorStrength.WEAK;
        }
        if (i == 2) {
            return SensorStrength.STRONG;
        }
        throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Invalid SensorStrength value: "));
    }
}
