package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.display.SemDlnaDevice;
import android.hardware.display.SemWifiDisplay;
import android.hardware.display.SemWifiDisplayStatus;
import android.view.Display;
import com.android.systemui.basic.util.LogWrapper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DisplayManagerWrapper {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public int displayCurrentVolume;
    public DisplayManagerWrapper$registerDisplayVolumeListener$1 displayVolumeListener;
    public final LogWrapper logWrapper;
    public final Lazy volumeManager$delegate = LazyKt__LazyJVMKt.lazy(new DisplayManagerWrapper$$ExternalSyntheticLambda0());
    public int minSmartViewVol = -1;
    public int maxSmartViewVol = -1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DisplayManagerWrapper(Context context, LogWrapper logWrapper) {
        this.context = context;
        this.logWrapper = logWrapper;
    }

    public final int getDisplayMaxVolume() {
        if (this.maxSmartViewVol == -1) {
            SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
            Context context = this.context;
            systemServiceExtension.getClass();
            this.maxSmartViewVol = ((Integer) SystemServiceExtension.getDisplayManager(context).semGetWifiDisplayConfiguration("mivo")).intValue();
        }
        return this.maxSmartViewVol;
    }

    public final Display getFrontCameraDisplay() {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        Display[] displays = SystemServiceExtension.getDisplayManager(context).getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY");
        if (displays.length == 0) {
            return null;
        }
        return displays[0];
    }

    public final Display getFrontSubDisplay() {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        Display[] displays = SystemServiceExtension.getDisplayManager(context).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
        if (displays.length <= 1) {
            displays = null;
        }
        if (displays != null) {
            return displays[1];
        }
        return null;
    }

    public final String getSmartViewDeviceName() {
        SemDlnaDevice semGetActiveDlnaDevice;
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        SemWifiDisplayStatus semGetWifiDisplayStatus = SystemServiceExtension.getDisplayManager(context).semGetWifiDisplayStatus();
        if (semGetWifiDisplayStatus != null) {
            if (semGetWifiDisplayStatus.getActiveDisplayState() != 2) {
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
        if (SystemServiceExtension.getDisplayManager(this.context).semGetActiveDlnaState() == 1 && (semGetActiveDlnaDevice = SystemServiceExtension.getDisplayManager(this.context).semGetActiveDlnaDevice()) != null) {
            return semGetActiveDlnaDevice.getDeviceName();
        }
        return null;
    }

    public final boolean isValidPlayerType() {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        SemDlnaDevice semGetActiveDlnaDevice = SystemServiceExtension.getDisplayManager(context).semGetActiveDlnaDevice();
        if (semGetActiveDlnaDevice == null) {
            return false;
        }
        int dlnaType = semGetActiveDlnaDevice.getDlnaType();
        return dlnaType == 0 || dlnaType == 2 || dlnaType == 3;
    }

    public final void toggleWifiDisplayMute() {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        if (SystemServiceExtension.getDisplayManager(context).semGetWifiDisplayConfiguration("muvo") instanceof Boolean) {
            SystemServiceExtension.getDisplayManager(this.context).semSetWifiDisplayConfiguration("mkev", !((Boolean) r0).booleanValue());
        }
    }
}
