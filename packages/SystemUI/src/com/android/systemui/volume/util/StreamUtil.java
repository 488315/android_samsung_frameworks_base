package com.android.systemui.volume.util;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StreamUtil {
    static {
        new StreamUtil();
    }

    private StreamUtil() {
    }

    public static final int getMusicStream(boolean z) {
        return z ? 21 : 3;
    }
}
