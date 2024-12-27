package com.android.server.display;

import android.hardware.display.DeviceProductInfo;
import android.view.Display;
import android.view.DisplayAddress;
import android.view.DisplayCutout;
import android.view.DisplayEventReceiver;
import android.view.DisplayShape;
import android.view.RoundedCorners;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;
import java.util.Arrays;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class DisplayDeviceInfo {
    public DisplayAddress address;
    public boolean allmSupported;
    public long appVsyncOffsetNanos;
    public float brightnessDefault;
    public float brightnessMaximum;
    public int colorMode;
    public int defaultModeId;
    public int densityDpi;
    public DeviceProductInfo deviceProductInfo;
    public DisplayCutout displayCutout;
    public DisplayShape displayShape;
    public int flags;
    public boolean gameContentTypeSupported;
    public Display.HdrCapabilities hdrCapabilities;
    public int height;
    public int modeId;
    public String name;
    public String ownerPackageName;
    public int ownerUid;
    public long presentationDeadlineNanos;
    public float renderFrameRate;
    public RoundedCorners roundedCorners;
    public int touch;
    public int type;
    public String uniqueId;
    public int width;
    public float xDpi;
    public float yDpi;
    public int userPreferredModeId = -1;
    public Display.Mode[] supportedModes = Display.Mode.EMPTY_ARRAY;
    public int[] supportedColorModes = {0};
    public int rotation = 0;
    public int state = 2;
    public int committedState = 0;
    public DisplayEventReceiver.FrameRateOverride[] frameRateOverrides = new DisplayEventReceiver.FrameRateOverride[0];
    public float hdrSdrRatio = Float.NaN;
    public int installOrientation = 0;

    public final int diff(DisplayDeviceInfo displayDeviceInfo) {
        int i = this.state != displayDeviceInfo.state ? 2 : 0;
        if (this.committedState != displayDeviceInfo.committedState) {
            i |= 4;
        }
        if (this.colorMode != displayDeviceInfo.colorMode) {
            i |= 8;
        }
        if (!BrightnessSynchronizer.floatEquals(this.hdrSdrRatio, displayDeviceInfo.hdrSdrRatio)) {
            i |= 16;
        }
        if (this.rotation != displayDeviceInfo.rotation) {
            i |= 32;
        }
        if (this.renderFrameRate != displayDeviceInfo.renderFrameRate || this.presentationDeadlineNanos != displayDeviceInfo.presentationDeadlineNanos || this.appVsyncOffsetNanos != displayDeviceInfo.appVsyncOffsetNanos) {
            i |= 64;
        }
        if (this.modeId != displayDeviceInfo.modeId) {
            i |= 128;
        }
        return (Objects.equals(this.name, displayDeviceInfo.name) && Objects.equals(this.uniqueId, displayDeviceInfo.uniqueId) && this.width == displayDeviceInfo.width && this.height == displayDeviceInfo.height && this.defaultModeId == displayDeviceInfo.defaultModeId && this.userPreferredModeId == displayDeviceInfo.userPreferredModeId && Arrays.equals(this.supportedModes, displayDeviceInfo.supportedModes) && Arrays.equals(this.supportedColorModes, displayDeviceInfo.supportedColorModes) && Objects.equals(this.hdrCapabilities, displayDeviceInfo.hdrCapabilities) && this.allmSupported == displayDeviceInfo.allmSupported && this.gameContentTypeSupported == displayDeviceInfo.gameContentTypeSupported && this.densityDpi == displayDeviceInfo.densityDpi && this.xDpi == displayDeviceInfo.xDpi && this.yDpi == displayDeviceInfo.yDpi && this.flags == displayDeviceInfo.flags && Objects.equals(this.displayCutout, displayDeviceInfo.displayCutout) && this.touch == displayDeviceInfo.touch && this.type == displayDeviceInfo.type && Objects.equals(this.address, displayDeviceInfo.address) && Objects.equals(this.deviceProductInfo, displayDeviceInfo.deviceProductInfo) && this.ownerUid == displayDeviceInfo.ownerUid && Objects.equals(this.ownerPackageName, displayDeviceInfo.ownerPackageName) && Arrays.equals(this.frameRateOverrides, displayDeviceInfo.frameRateOverrides) && BrightnessSynchronizer.floatEquals(FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE) && BrightnessSynchronizer.floatEquals(this.brightnessMaximum, displayDeviceInfo.brightnessMaximum) && BrightnessSynchronizer.floatEquals(this.brightnessDefault, displayDeviceInfo.brightnessDefault) && Objects.equals(this.roundedCorners, displayDeviceInfo.roundedCorners) && this.installOrientation == displayDeviceInfo.installOrientation && Objects.equals(this.displayShape, displayDeviceInfo.displayShape)) ? i : i | 1;
    }

    public final boolean equals(Object obj) {
        DisplayDeviceInfo displayDeviceInfo;
        return (obj instanceof DisplayDeviceInfo) && (displayDeviceInfo = (DisplayDeviceInfo) obj) != null && diff(displayDeviceInfo) == 0;
    }

    public final int hashCode() {
        return 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DisplayDeviceInfo{\"");
        sb.append(this.name);
        sb.append("\": uniqueId=\"");
        sb.append(this.uniqueId);
        sb.append("\", ");
        sb.append(this.width);
        sb.append(" x ");
        sb.append(this.height);
        sb.append(", modeId ");
        sb.append(this.modeId);
        sb.append(", renderFrameRate ");
        sb.append(this.renderFrameRate);
        sb.append(", defaultModeId ");
        sb.append(this.defaultModeId);
        sb.append(", userPreferredModeId ");
        sb.append(this.userPreferredModeId);
        sb.append(", supportedModes ");
        sb.append(Arrays.toString(this.supportedModes));
        sb.append(", colorMode ");
        sb.append(this.colorMode);
        sb.append(", supportedColorModes ");
        sb.append(Arrays.toString(this.supportedColorModes));
        sb.append(", hdrCapabilities ");
        sb.append(this.hdrCapabilities);
        sb.append(", allmSupported ");
        sb.append(this.allmSupported);
        sb.append(", gameContentTypeSupported ");
        sb.append(this.gameContentTypeSupported);
        sb.append(", density ");
        sb.append(this.densityDpi);
        sb.append(", ");
        sb.append(this.xDpi);
        sb.append(" x ");
        sb.append(this.yDpi);
        sb.append(" dpi, appVsyncOff ");
        sb.append(this.appVsyncOffsetNanos);
        sb.append(", presDeadline ");
        sb.append(this.presentationDeadlineNanos);
        if (this.displayCutout != null) {
            sb.append(", cutout ");
            sb.append(this.displayCutout);
        }
        sb.append(", touch ");
        int i = this.touch;
        sb.append(i != 0 ? i != 1 ? i != 2 ? i != 3 ? Integer.toString(i) : "VIRTUAL" : "EXTERNAL" : "INTERNAL" : "NONE");
        sb.append(", rotation ");
        sb.append(this.rotation);
        sb.append(", type ");
        sb.append(Display.typeToString(this.type));
        if (this.address != null) {
            sb.append(", address ");
            sb.append(this.address);
        }
        sb.append(", deviceProductInfo ");
        sb.append(this.deviceProductInfo);
        sb.append(", state ");
        sb.append(Display.stateToString(this.state));
        sb.append(", committedState ");
        sb.append(Display.stateToString(this.committedState));
        if (this.ownerUid != 0 || this.ownerPackageName != null) {
            sb.append(", owner ");
            sb.append(this.ownerPackageName);
            sb.append(" (uid ");
            sb.append(this.ownerUid);
            sb.append(")");
        }
        sb.append(", frameRateOverride ");
        for (DisplayEventReceiver.FrameRateOverride frameRateOverride : this.frameRateOverrides) {
            sb.append(frameRateOverride);
            sb.append(" ");
        }
        sb.append(", brightnessMinimum 0.0, brightnessMaximum ");
        sb.append(this.brightnessMaximum);
        sb.append(", brightnessDefault ");
        sb.append(this.brightnessDefault);
        sb.append(", hdrSdrRatio ");
        sb.append(this.hdrSdrRatio);
        if (this.roundedCorners != null) {
            sb.append(", roundedCorners ");
            sb.append(this.roundedCorners);
        }
        int i2 = this.flags;
        StringBuilder sb2 = new StringBuilder();
        if ((i2 & 1) != 0) {
            sb2.append(", FLAG_ALLOWED_TO_BE_DEFAULT_DISPLAY");
        }
        if ((i2 & 2) != 0) {
            sb2.append(", FLAG_ROTATES_WITH_CONTENT");
        }
        if ((i2 & 4) != 0) {
            sb2.append(", FLAG_SECURE");
        }
        if ((i2 & 8) != 0) {
            sb2.append(", FLAG_SUPPORTS_PROTECTED_BUFFERS");
        }
        if ((i2 & 16) != 0) {
            sb2.append(", FLAG_PRIVATE");
        }
        if ((i2 & 32) != 0) {
            sb2.append(", FLAG_NEVER_BLANK");
        }
        if ((i2 & 64) != 0) {
            sb2.append(", FLAG_PRESENTATION");
        }
        if ((i2 & 128) != 0) {
            sb2.append(", FLAG_OWN_CONTENT_ONLY");
        }
        if ((i2 & 256) != 0) {
            sb2.append(", FLAG_ROUND");
        }
        if ((i2 & 512) != 0) {
            sb2.append(", FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD");
        }
        if ((i2 & 1024) != 0) {
            sb2.append(", FLAG_DESTROY_CONTENT_ON_REMOVAL");
        }
        if ((i2 & 2048) != 0) {
            sb2.append(", FLAG_MASK_DISPLAY_CUTOUT");
        }
        if ((i2 & 4096) != 0) {
            sb2.append(", FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS");
        }
        if ((i2 & 8192) != 0) {
            sb2.append(", FLAG_TRUSTED");
        }
        if ((i2 & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) != 0) {
            sb2.append(", FLAG_OWN_DISPLAY_GROUP");
        }
        if ((32768 & i2) != 0) {
            sb2.append(", FLAG_ALWAYS_UNLOCKED");
        }
        if ((65536 & i2) != 0) {
            sb2.append(", FLAG_TOUCH_FEEDBACK_DISABLED");
        }
        if ((131072 & i2) != 0) {
            sb2.append(", FLAG_OWN_FOCUS");
        }
        if ((524288 & i2) != 0) {
            sb2.append(", FLAG_STEAL_TOP_FOCUS_DISABLED");
        }
        if ((67108864 & i2) != 0) {
            sb2.append(", FLAG_WIRELESS_DEX_DISPLAY");
        }
        if ((134217728 & i2) != 0) {
            sb2.append(", FLAG_PC_DEX_DISPLAY");
        }
        if ((268435456 & i2) != 0) {
            sb2.append(", FLAG_WIFI_DISPLAY");
        }
        if ((536870912 & i2) != 0) {
            sb2.append(", FLAG_NO_LOCK_PRESENTATION");
        }
        if ((33554432 & i2) != 0) {
            sb2.append(", FLAG_HIDDEN_SPACE_DISPLAY");
        }
        if (CoreRune.SYSFW_APP_SPEG && (1073741824 & i2) != 0) {
            sb2.append(", FLAG_SPEG_DISPLAY");
        }
        if (CoreRune.BAIDU_CARLIFE && (Integer.MIN_VALUE & i2) != 0) {
            sb2.append(", FLAG_CARLIFE_DISPLAY");
        }
        if ((8388608 & i2) != 0) {
            sb2.append(", FLAG_EXTRA_BUILT_IN_DISPLAY");
        }
        if ((4194304 & i2) != 0) {
            sb2.append(", FLAG_VIEW_COVER_DISPLAY");
        }
        if ((1048576 & i2) != 0) {
            sb2.append(", FLAG_DESKTOP_DISPLAY");
        }
        if ((i2 & 2097152) != 0) {
            sb2.append(", FLAG_REMOTE_APP_DISPLAY");
        }
        sb.append(sb2.toString());
        sb.append(", installOrientation ");
        sb.append(this.installOrientation);
        if (this.displayShape != null) {
            sb.append(", displayShape ");
            sb.append(this.displayShape);
        }
        sb.append("}");
        return sb.toString();
    }
}
