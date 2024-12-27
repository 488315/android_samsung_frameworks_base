package com.android.internal.widget.remotecompose.core.operations.utilities;

/* loaded from: classes5.dex */
public class ColorUtils {
    public static int RC_COLOR = 62;

    long packRCColor(int defaultARGB, int id) {
        long l = defaultARGB;
        return (l << 32) | (id << 8) | RC_COLOR;
    }

    boolean isRCColor(long color) {
        return (63 & color) == 62;
    }

    int getID(long color) {
        if (isRCColor(color)) {
            return (int) (((-256) & color) >> 8);
        }
        return -1;
    }

    public int getDefaultColor(long color) {
        if (isRCColor(color)) {
            return (int) (color >> 32);
        }
        if ((255 & color) == 0) {
            return (int) (color >> 32);
        }
        return 0;
    }
}
