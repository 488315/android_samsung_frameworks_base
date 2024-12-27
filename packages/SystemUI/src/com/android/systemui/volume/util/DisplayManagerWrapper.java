package com.android.systemui.volume.util;

import android.content.Context;
import android.hardware.display.SemDlnaDevice;
import android.hardware.display.SemWifiDisplay;
import android.hardware.display.SemWifiDisplayStatus;
import android.view.Display;
import com.android.systemui.Dependency;
import com.android.systemui.audio.soundcraft.interfaces.volume.VolumeManager;
import com.android.systemui.basic.util.LogWrapper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class DisplayManagerWrapper {
    public final Context context;
    public int displayCurrentVolume;
    public DisplayManagerWrapper$registerDisplayVolumeListener$1 displayVolumeListener;
    public final LogWrapper logWrapper;
    public final Lazy volumeManager$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.volume.util.DisplayManagerWrapper$volumeManager$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return (VolumeManager) Dependency.sDependency.getDependencyInner(VolumeManager.class);
        }
    });
    public int minSmartViewVol = -1;
    public int maxSmartViewVol = -1;

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
        return (Display) ArraysKt___ArraysKt.firstOrNull(SystemServiceExtension.getDisplayManager(context).getDisplays("com.samsung.android.hardware.display.category.VIEW_COVER_DISPLAY"));
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
        if (SystemServiceExtension.getDisplayManager(this.context).semGetActiveDlnaState() != 1 || (semGetActiveDlnaDevice = SystemServiceExtension.getDisplayManager(this.context).semGetActiveDlnaDevice()) == null) {
            return null;
        }
        return semGetActiveDlnaDevice.getDeviceName();
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
