package com.android.server.wm;

import com.android.window.flags.Flags;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WindowManagerFlags {
    public final boolean mWallpaperOffsetAsync = Flags.wallpaperOffsetAsync();
    public final boolean mAllowsScreenSizeDecoupledFromStatusBarAndCutout =
            Flags.allowsScreenSizeDecoupledFromStatusBarAndCutout();
    public final boolean mInsetsDecoupledConfiguration = Flags.insetsDecoupledConfiguration();
    public final boolean mRespectNonTopVisibleFixedOrientation =
            Flags.respectNonTopVisibleFixedOrientation();
}
