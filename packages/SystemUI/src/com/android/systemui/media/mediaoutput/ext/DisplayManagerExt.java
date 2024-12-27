package com.android.systemui.media.mediaoutput.ext;

import android.hardware.display.DisplayManager;
import android.hardware.display.SemWifiDisplay;
import android.hardware.display.SemWifiDisplayStatus;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
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
