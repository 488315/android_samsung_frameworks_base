package com.facebook.rebound;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OrigamiValueConverter {
    public static double frictionFromOrigamiValue(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        return 25.0d + ((d - 8.0d) * 3.0d);
    }

    public static double tensionFromOrigamiValue(double d) {
        if (d == 0.0d) {
            return 0.0d;
        }
        return 194.0d + ((d - 30.0d) * 3.62d);
    }
}
