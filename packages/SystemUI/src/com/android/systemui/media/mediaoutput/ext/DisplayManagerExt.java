package com.android.systemui.media.mediaoutput.ext;

import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplay;
import android.hardware.display.SemWifiDisplayStatus;

public final class DisplayManagerExt {
    public static final DisplayManagerExt INSTANCE = new DisplayManagerExt();

    private DisplayManagerExt() {
    }

    public static SemWifiDisplay getActiveDisplay(DisplayManager displayManager) {
        SemWifiDisplayStatus semGetWifiDisplayStatus = displayManager.semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus != null) {
            return semGetWifiDisplayStatus.getActiveDisplay();
        }
        return null;
    }
}
