package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.display.SemDlnaDevice;
import android.hardware.display.SemWifiDisplay;
import android.hardware.display.SemWifiDisplayStatus;
import android.view.Display;
import com.android.systemui.basic.util.LogWrapper;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class DisplayManagerWrapper {
    public final Context context;
    public int displayCurrentVolume;
    public final LogWrapper logWrapper;
    public int minSmartViewVol = -1;
    public int maxSmartViewVol = -1;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DisplayManagerWrapper(Context context, LogWrapper logWrapper) {
        this.context = context;
        this.logWrapper = logWrapper;
    }

    public final Display getFrontCameraDisplay() {
        SystemServiceExtension.INSTANCE.getClass();
        Display[] displays = SystemServiceExtension.getDisplayManager(this.context).getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
        if (displays.length == 0) {
            return null;
        }
        return displays[0];
    }

    public final Display getFrontSubDisplay() {
        SystemServiceExtension.INSTANCE.getClass();
        Display[] displays = SystemServiceExtension.getDisplayManager(this.context).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (!(displays.length > 1)) {
            displays = null;
        }
        if (displays != null) {
            return displays[1];
        }
        return null;
    }

    public final String getSmartViewDeviceName() {
        SemDlnaDevice semGetActiveDlnaDevice;
        SystemServiceExtension.INSTANCE.getClass();
        Context context = this.context;
        SemWifiDisplayStatus semGetWifiDisplayStatus = SystemServiceExtension.getDisplayManager(context).semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus != null) {
            if (!(semGetWifiDisplayStatus.getActiveDisplayState() == 2)) {
                semGetWifiDisplayStatus = null;
            }
            if (semGetWifiDisplayStatus != null) {
                SemWifiDisplay activeDisplay = semGetWifiDisplayStatus.getActiveDisplay();
                if (activeDisplay != null) {
                    return activeDisplay.getDeviceName();
                }
                return null;
            }
        }
        if (SystemServiceExtension.getDisplayManager(context).semGetActiveDlnaState() != 1 || (semGetActiveDlnaDevice = SystemServiceExtension.getDisplayManager(context).semGetActiveDlnaDevice()) == null) {
            return null;
        }
        return semGetActiveDlnaDevice.getDeviceName();
    }

    public final boolean isValidPlayerType() {
        int dlnaType;
        SystemServiceExtension.INSTANCE.getClass();
        SemDlnaDevice semGetActiveDlnaDevice = SystemServiceExtension.getDisplayManager(this.context).semGetActiveDlnaDevice();
        return semGetActiveDlnaDevice != null && ((dlnaType = semGetActiveDlnaDevice.getDlnaType()) == 0 || dlnaType == 2 || dlnaType == 3);
    }

    public final void toggleWifiDisplayMute() {
        SystemServiceExtension.INSTANCE.getClass();
        Context context = this.context;
        if (SystemServiceExtension.getDisplayManager(context).semGetWifiDisplayConfiguration("muvo") instanceof Boolean) {
            SystemServiceExtension.getDisplayManager(context).semSetWifiDisplayConfiguration("mkev", !((Boolean) r0).booleanValue());
        }
    }
}
