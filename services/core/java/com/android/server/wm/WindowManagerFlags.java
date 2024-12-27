package com.android.server.wm;

import com.android.window.flags.Flags;

public final class WindowManagerFlags {
    public final boolean mWallpaperOffsetAsync = Flags.wallpaperOffsetAsync();
    public final boolean mAllowsScreenSizeDecoupledFromStatusBarAndCutout =
            Flags.allowsScreenSizeDecoupledFromStatusBarAndCutout();
    public final boolean mInsetsDecoupledConfiguration = Flags.insetsDecoupledConfiguration();
    public final boolean mRespectNonTopVisibleFixedOrientation =
            Flags.respectNonTopVisibleFixedOrientation();
}
