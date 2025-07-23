package com.android.internal.widget.remotecompose.core.operations.utilities;

/* loaded from: classes6.dex */
public class ColorUtils {
    public static int RC_COLOR = 62;

    public static int createColor(int i, int i2, int i3, int i4) {
        return (i << 16) | (i4 << 24) | (i2 << 8) | i3;
    }

    boolean isRCColor(long j) {
        return (j & 63) == 62;
    }

    long packRCColor(int i, int i2) {
        return (i << 32) | (i2 << 8) | RC_COLOR;
    }

    int getID(long j) {
        if (isRCColor(j)) {
            return (int) ((j & (-256)) >> 8);
        }
        return -1;
    }

    public int getDefaultColor(long j) {
        if (isRCColor(j) || (255 & j) == 0) {
            return (int) (j >> 32);
        }
        return 0;
    }
}
