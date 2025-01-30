package com.android.systemui.monet.contrast;

import com.android.systemui.monet.utils.ColorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Contrast {
    private Contrast() {
    }

    public static double ratioOfTones(double d, double d2) {
        double yFromLstar = ColorUtils.yFromLstar(d);
        double yFromLstar2 = ColorUtils.yFromLstar(d2);
        double max = Math.max(yFromLstar, yFromLstar2);
        if (max != yFromLstar2) {
            yFromLstar = yFromLstar2;
        }
        return (max + 5.0d) / (yFromLstar + 5.0d);
    }
}
