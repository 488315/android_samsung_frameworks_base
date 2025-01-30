package com.google.android.material.math;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MathUtils {
    private MathUtils() {
    }

    public static float distanceToFurthestCorner(float f, float f2, float f3, float f4) {
        double d = 0.0f - f;
        double d2 = 0.0f - f2;
        float hypot = (float) Math.hypot(d, d2);
        double d3 = f3 - f;
        float hypot2 = (float) Math.hypot(d3, d2);
        double d4 = f4 - f2;
        float hypot3 = (float) Math.hypot(d3, d4);
        float hypot4 = (float) Math.hypot(d, d4);
        return (hypot <= hypot2 || hypot <= hypot3 || hypot <= hypot4) ? (hypot2 <= hypot3 || hypot2 <= hypot4) ? hypot3 > hypot4 ? hypot3 : hypot4 : hypot2 : hypot;
    }
}
