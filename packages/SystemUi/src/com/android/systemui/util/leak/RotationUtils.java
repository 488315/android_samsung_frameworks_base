package com.android.systemui.util.leak;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.media.AbstractC0000x2c234b15;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class RotationUtils {
    public static int getExactRotation(Context context) {
        int rotation = context.getDisplay().getRotation();
        if (rotation == 1) {
            return 1;
        }
        if (rotation == 3) {
            return 3;
        }
        return rotation == 2 ? 2 : 0;
    }

    public static Resources getResourcesForRotation(int i, Context context) {
        int i2 = 1;
        if (i != 0) {
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        throw new IllegalArgumentException(AbstractC0000x2c234b15.m0m("Unknown rotation: ", i));
                    }
                }
            }
            i2 = 2;
        }
        Configuration configuration = new Configuration(context.getResources().getConfiguration());
        configuration.orientation = i2;
        return context.createConfigurationContext(configuration).getResources();
    }

    public static int getRotation(Context context) {
        int rotation = context.getDisplay().getRotation();
        if (rotation == 1) {
            return 1;
        }
        return rotation == 3 ? 3 : 0;
    }
}
