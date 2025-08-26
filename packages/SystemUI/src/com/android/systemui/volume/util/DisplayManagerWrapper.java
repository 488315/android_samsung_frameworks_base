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

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getSmartViewDeviceName() {
        SemDlnaDevice semDlnaDeviceSemGetActiveDlnaDevice;
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        SemWifiDisplayStatus semWifiDisplayStatusSemGetWifiDisplayStatus = SystemServiceExtension.getDisplayManager(context).semGetWifiDisplayStatus();
        if (semWifiDisplayStatusSemGetWifiDisplayStatus != null) {
            if (semWifiDisplayStatusSemGetWifiDisplayStatus.getActiveDisplayState() != 2) {
                semWifiDisplayStatusSemGetWifiDisplayStatus = null;
            }
            if (semWifiDisplayStatusSemGetWifiDisplayStatus != null) {
                SemWifiDisplay activeDisplay = semWifiDisplayStatusSemGetWifiDisplayStatus.getActiveDisplay();
                if (activeDisplay != null) {
                    return activeDisplay.getDeviceName();
                }
            }
        } else if (SystemServiceExtension.getDisplayManager(this.context).semGetActiveDlnaState() == 1 && (semDlnaDeviceSemGetActiveDlnaDevice = SystemServiceExtension.getDisplayManager(this.context).semGetActiveDlnaDevice()) != null) {
            return semDlnaDeviceSemGetActiveDlnaDevice.getDeviceName();
        }
        return null;
    }

    public final boolean isValidPlayerType() {
        SystemServiceExtension systemServiceExtension = SystemServiceExtension.INSTANCE;
        Context context = this.context;
        systemServiceExtension.getClass();
        SemDlnaDevice semDlnaDeviceSemGetActiveDlnaDevice = SystemServiceExtension.getDisplayManager(context).semGetActiveDlnaDevice();
        if (semDlnaDeviceSemGetActiveDlnaDevice == null) {
            return false;
        }
        int dlnaType = semDlnaDeviceSemGetActiveDlnaDevice.getDlnaType();
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
