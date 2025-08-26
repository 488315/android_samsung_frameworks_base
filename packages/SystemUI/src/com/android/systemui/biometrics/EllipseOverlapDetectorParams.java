package com.android.systemui.biometrics;

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
