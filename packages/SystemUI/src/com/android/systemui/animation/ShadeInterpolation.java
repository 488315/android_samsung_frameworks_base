package com.android.systemui.animation;

import android.util.MathUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ShadeInterpolation {
    public static final ShadeInterpolation INSTANCE = new ShadeInterpolation();

    private ShadeInterpolation() {
    }

    public static final float getContentAlpha(float f) {
        float constrainedMap = MathUtils.constrainedMap(0.0f, 1.0f, 0.3f, 1.0f, f);
        INSTANCE.getClass();
        return interpolateEaseInOut(constrainedMap);
    }

    public static final float getNotifContentAlpha(float f) {
        float constrainedMap = MathUtils.constrainedMap(0.0f, 1.0f, 0.45f, 1.0f, f);
        INSTANCE.getClass();
        return interpolateEaseInOut(constrainedMap);
    }

    public static final float getNotificationScrimAlpha(float f) {
        float constrainedMap = MathUtils.constrainedMap(0.0f, 1.0f, 0.0f, 0.5f, f);
        INSTANCE.getClass();
        return interpolateEaseInOut(constrainedMap);
    }

    public static float interpolateEaseInOut(float f) {
        float f2 = (f * 1.2f) - 0.2f;
        if (f2 <= 0.0f) {
            return 0.0f;
        }
        float f3 = 1.0f - f2;
        double d = 1.0f;
        return (float) (d - ((d - Math.cos((3.14159f * f3) * f3)) * 0.5f));
    }
}
