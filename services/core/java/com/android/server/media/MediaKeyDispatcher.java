package com.android.server.media;

/* loaded from: classes2.dex */
public abstract class MediaKeyDispatcher {
    public static boolean isDoubleTapOverridden(int i) {
        return (i & 2) != 0;
    }

    public static boolean isSingleTapOverridden(int i) {
        return (i & 1) != 0;
    }

    public static boolean isTripleTapOverridden(int i) {
        return (i & 4) != 0;
    }
}
