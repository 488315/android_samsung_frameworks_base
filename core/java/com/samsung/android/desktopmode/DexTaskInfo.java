package com.samsung.android.desktopmode;

/* loaded from: classes5.dex */
public class DexTaskInfo {
    public static final int FLAG_FIXED_ORIENTATION = 4;
    public static final int FLAG_IN_MULTI_WINDOW_MODE = 1;
    public static final int FLAG_RESIZEABLE_TO_FULLSCREEN = 2;
    private final int mFlags;

    public DexTaskInfo(int flags) {
        this.mFlags = flags;
    }

    public boolean isInMultiWindowMode() {
        return (this.mFlags & 1) != 0;
    }

    public boolean isResizableToFullscreen() {
        return (this.mFlags & 2) != 0;
    }

    public boolean isFixedOrientation() {
        return (this.mFlags & 4) != 0;
    }
}
