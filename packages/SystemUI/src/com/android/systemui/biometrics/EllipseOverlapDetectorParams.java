package com.android.systemui.biometrics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
