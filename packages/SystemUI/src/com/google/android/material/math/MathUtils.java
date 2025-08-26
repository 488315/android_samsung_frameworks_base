package com.google.android.material.math;

/* loaded from: classes4.dex */
public final class MathUtils {
    private MathUtils() {
    }

    public static float dist(float f, float f2, float f3, float f4) {
        return (float) Math.hypot(f3 - f, f4 - f2);
    }

    public static float distanceToFurthestCorner(float f, float f2, float f3, float f4) {
        float fDist = dist(f, f2, 0.0f, 0.0f);
        float fDist2 = dist(f, f2, f3, 0.0f);
        float fDist3 = dist(f, f2, f3, f4);
        float fDist4 = dist(f, f2, 0.0f, f4);
        return (fDist <= fDist2 || fDist <= fDist3 || fDist <= fDist4) ? (fDist2 <= fDist3 || fDist2 <= fDist4) ? fDist3 > fDist4 ? fDist3 : fDist4 : fDist2 : fDist;
    }

    public static float lerp(float f, float f2, float f3) {
        return (f3 * f2) + ((1.0f - f3) * f);
    }
}
