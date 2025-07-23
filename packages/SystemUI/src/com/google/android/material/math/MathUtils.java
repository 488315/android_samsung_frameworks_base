package com.google.android.material.math;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MathUtils {
    private MathUtils() {
    }

    public static float dist(float f, float f2, float f3, float f4) {
        return (float) Math.hypot(f3 - f, f4 - f2);
    }

    public static float distanceToFurthestCorner(float f, float f2, float f3, float f4) {
        float dist = dist(f, f2, 0.0f, 0.0f);
        float dist2 = dist(f, f2, f3, 0.0f);
        float dist3 = dist(f, f2, f3, f4);
        float dist4 = dist(f, f2, 0.0f, f4);
        return (dist <= dist2 || dist <= dist3 || dist <= dist4) ? (dist2 <= dist3 || dist2 <= dist4) ? dist3 > dist4 ? dist3 : dist4 : dist2 : dist;
    }

    public static float lerp(float f, float f2, float f3) {
        return (f3 * f2) + ((1.0f - f3) * f);
    }
}
