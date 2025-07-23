package com.android.systemui.biometrics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class EllipseOverlapDetectorParams {
    public final float minOverlap;
    public final int stepSize;
    public final float targetSize;

    public EllipseOverlapDetectorParams(float f, float f2, int i) {
        this.minOverlap = f;
        this.targetSize = f2;
        this.stepSize = i;
    }
}
