package com.samsung.server.wallpaper;

import android.hardware.display.DisplayManager;
import android.view.Display;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class VirtualDisplayMode {
    public final DisplayManager mDisplayManager;

    public VirtualDisplayMode(DisplayManager displayManager) {
        this.mDisplayManager = displayManager;
    }

    public final boolean isVirtualWallpaperDisplay(int i) {
        Display[] displays =
                this.mDisplayManager.getDisplays(
                        "com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
        if (displays.length > 0) {
            for (Display display : displays) {
                if (i == display.getDisplayId()) {
                    return true;
                }
            }
        }
        return false;
    }
}
