package com.android.systemui.biometrics.shared.model;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
