package com.facebook.rebound;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class OrigamiValueConverter {
    public static double frictionFromOrigamiValue(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        return ((d - 8.0d) * 3.0d) + 25.0d;
    }

    public static double tensionFromOrigamiValue(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        return ((d - 30.0d) * 3.62d) + 194.0d;
    }
}
