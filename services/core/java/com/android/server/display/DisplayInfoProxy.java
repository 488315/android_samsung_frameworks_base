package com.android.server.display;

import android.hardware.display.DisplayManagerGlobal;
import android.view.DisplayInfo;

public final class DisplayInfoProxy {
    public DisplayInfo mInfo;

    public final void set(DisplayInfo displayInfo) {
        this.mInfo = displayInfo;
        DisplayManagerGlobal.invalidateLocalDisplayInfoCaches();
    }
}
