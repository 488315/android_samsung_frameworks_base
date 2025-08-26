package com.android.systemui.volume.util;

/* loaded from: classes3.dex */
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
