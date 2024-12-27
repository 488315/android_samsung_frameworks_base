package com.android.server.display;

import android.hardware.display.DisplayManagerGlobal;
import android.view.DisplayInfo;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayInfoProxy {
    public DisplayInfo mInfo;

    public final void set(DisplayInfo displayInfo) {
        this.mInfo = displayInfo;
        DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
    }
}
