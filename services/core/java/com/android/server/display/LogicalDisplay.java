package com.android.server.display;

import android.graphics.Point;
import android.graphics.Rect;
import android.util.ArraySet;
import android.util.Size;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayEventReceiver;
import android.view.DisplayInfo;
import android.view.SurfaceControl;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.display.mode.SyntheticModeManager;

import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.rune.CoreRune;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class LogicalDisplay {
    public static final DisplayInfo EMPTY_DISPLAY_INFO = new DisplayInfo();
    public final boolean mAlwaysRotateDisplayDeviceEnabled;
    public final DisplayInfo mBaseDisplayInfo;
    public DisplayModeDirector.DesiredDisplayModeSpecs mDesiredDisplayModeSpecs;
    public int mDevicePosition;
    public boolean mDirty;
    public int mDisplayGroupId;
    public String mDisplayGroupName;
    public final int mDisplayId;
    public DisplayOffloadSessionImpl mDisplayOffloadSession;
    public int mDisplayOffsetX;
    public int mDisplayOffsetY;
    public final Point mDisplayPosition;
    public boolean mDisplayScalingDisabled;
    public DisplayEventReceiver.FrameRateOverride[] mFrameRateOverrides;
    public boolean mHasContent;
    public final DisplayInfoProxy mInfo;
    public final boolean mIsAnisotropyCorrectionEnabled;
    public boolean mIsEnabled;
    public boolean mIsInTransition;
    public final int mLayerStack;
    public SurfaceControl.RefreshRateRange mLayoutLimitedRefreshRate;
    public int mLeadDisplayId;
    public DisplayInfo mOverrideDisplayInfo;
    public ArraySet mPendingFrameRateOverrideUids;
    public String mPowerThrottlingDataId;
    public DisplayDevice mPrimaryDisplayDevice;
    public DisplayDeviceInfo mPrimaryDisplayDeviceInfo;
    public int mRefreshRateMode;
    public int mRequestedColorMode;
    public boolean mRequestedMinimalPostProcessing;
    public final Rect mTempDisplayRect;
    public final SparseArray mTempFrameRateOverride;
    public final Rect mTempLayerStackRect;
    public String mThermalBrightnessThrottlingDataId;
    public SparseArray mThermalRefreshRateThrottling;
    public int[] mUserDisabledHdrTypes;

    public LogicalDisplay(int i, int i2, DisplayDevice displayDevice, boolean z, boolean z2) {
        DisplayInfo displayInfo = new DisplayInfo();
        this.mBaseDisplayInfo = displayInfo;
        this.mLeadDisplayId = -1;
        this.mDisplayGroupId = -1;
        this.mRefreshRateMode = 0;
        DisplayInfoProxy displayInfoProxy = new DisplayInfoProxy();
        displayInfoProxy.mInfo = null;
        this.mInfo = displayInfoProxy;
        this.mUserDisabledHdrTypes = new int[0];
        this.mDesiredDisplayModeSpecs = new DisplayModeDirector.DesiredDisplayModeSpecs();
        this.mDisplayPosition = new Point();
        this.mTempLayerStackRect = new Rect();
        this.mTempDisplayRect = new Rect();
        this.mDevicePosition = -1;
        this.mDirty = false;
        this.mThermalRefreshRateThrottling = new SparseArray();
        this.mDisplayId = i;
        this.mLayerStack = i2;
        this.mPrimaryDisplayDevice = displayDevice;
        this.mPendingFrameRateOverrideUids = new ArraySet();
        this.mTempFrameRateOverride = new SparseArray();
        this.mIsEnabled = true;
        this.mIsInTransition = false;
        this.mThermalBrightnessThrottlingDataId = "default";
        this.mPowerThrottlingDataId = "default";
        displayInfo.thermalBrightnessThrottlingDataId = "default";
        this.mIsAnisotropyCorrectionEnabled = z;
        this.mAlwaysRotateDisplayDeviceEnabled = z2;
    }

    public static Rect getMaskingInsets(DisplayDeviceInfo displayDeviceInfo) {
        DisplayCutout displayCutout;
        return ((displayDeviceInfo.flags & 2048) == 0
                        || (displayCutout = displayDeviceInfo.displayCutout) == null)
                ? new Rect()
                : displayCutout.getSafeInsets();
    }

    public final void dumpLocked(PrintWriter printWriter) {
        StringBuilder m =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                        .m(
                                                                BinaryTransparencyService$$ExternalSyntheticOutline0
                                                                        .m(
                                                                                new StringBuilder(
                                                                                        "mDisplayId="),
                                                                                this.mDisplayId,
                                                                                printWriter,
                                                                                "mIsEnabled="),
                                                                this.mIsEnabled,
                                                                printWriter,
                                                                "mIsInTransition="),
                                                this.mIsInTransition,
                                                printWriter,
                                                "mLayerStack="),
                                        this.mLayerStack,
                                        printWriter,
                                        "mPosition="),
                                this.mDevicePosition,
                                printWriter,
                                "mHasContent="),
                        this.mHasContent,
                        printWriter,
                        "mDesiredDisplayModeSpecs={");
        m.append(this.mDesiredDisplayModeSpecs);
        m.append("}");
        printWriter.println(m.toString());
        StringBuilder m2 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("mRequestedColorMode="),
                        this.mRequestedColorMode,
                        printWriter,
                        "mDisplayOffset=(");
        m2.append(this.mDisplayOffsetX);
        m2.append(", ");
        m2.append(this.mDisplayOffsetY);
        m2.append(")");
        printWriter.println(m2.toString());
        StringBuilder m3 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("mDisplayScalingDisabled="),
                        this.mDisplayScalingDisabled,
                        printWriter,
                        "mPrimaryDisplayDevice=");
        DisplayDevice displayDevice = this.mPrimaryDisplayDevice;
        StringBuilder m4 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        printWriter,
                        displayDevice != null
                                ? displayDevice.getDisplayDeviceInfoLocked().name
                                : "null",
                        "mBaseDisplayInfo=",
                        m3);
        m4.append(this.mBaseDisplayInfo);
        printWriter.println(m4.toString());
        printWriter.println("mOverrideDisplayInfo=" + this.mOverrideDisplayInfo);
        StringBuilder m5 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("mRequestedMinimalPostProcessing="),
                        this.mRequestedMinimalPostProcessing,
                        printWriter,
                        "mFrameRateOverrides=");
        m5.append(Arrays.toString(this.mFrameRateOverrides));
        printWriter.println(m5.toString());
        printWriter.println("mPendingFrameRateOverrideUids=" + this.mPendingFrameRateOverrideUids);
        StringBuilder m6 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                printWriter,
                                this.mThermalBrightnessThrottlingDataId,
                                "mLeadDisplayId=",
                                BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                        printWriter,
                                        this.mDisplayGroupName,
                                        "mThermalBrightnessThrottlingDataId=",
                                        new StringBuilder("mDisplayGroupName="))),
                        this.mLeadDisplayId,
                        printWriter,
                        "mLayoutLimitedRefreshRate=");
        m6.append(this.mLayoutLimitedRefreshRate);
        printWriter.println(m6.toString());
        printWriter.println("mThermalRefreshRateThrottling=" + this.mThermalRefreshRateThrottling);
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("mPowerThrottlingDataId="),
                this.mPowerThrottlingDataId,
                printWriter);
    }

    public final DisplayInfo getDisplayInfoLocked() {
        DisplayInfoProxy displayInfoProxy = this.mInfo;
        if (displayInfoProxy.mInfo == null) {
            DisplayInfo displayInfo = new DisplayInfo();
            DisplayInfo displayInfo2 = this.mBaseDisplayInfo;
            DisplayInfo displayInfo3 = this.mOverrideDisplayInfo;
            displayInfo.copyFrom(displayInfo2);
            if (displayInfo3 != null) {
                displayInfo.appWidth = displayInfo3.appWidth;
                displayInfo.appHeight = displayInfo3.appHeight;
                displayInfo.smallestNominalAppWidth = displayInfo3.smallestNominalAppWidth;
                displayInfo.smallestNominalAppHeight = displayInfo3.smallestNominalAppHeight;
                displayInfo.largestNominalAppWidth = displayInfo3.largestNominalAppWidth;
                displayInfo.largestNominalAppHeight = displayInfo3.largestNominalAppHeight;
                displayInfo.logicalWidth = displayInfo3.logicalWidth;
                displayInfo.logicalHeight = displayInfo3.logicalHeight;
                displayInfo.physicalXDpi = displayInfo3.physicalXDpi;
                displayInfo.physicalYDpi = displayInfo3.physicalYDpi;
                displayInfo.rotation = displayInfo3.rotation;
                displayInfo.displayCutout = displayInfo3.displayCutout;
                displayInfo.logicalDensityDpi = displayInfo3.logicalDensityDpi;
                displayInfo.roundedCorners = displayInfo3.roundedCorners;
                displayInfo.displayShape = displayInfo3.displayShape;
            }
            if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
                displayInfo.refreshRateMode = this.mRefreshRateMode;
            }
            displayInfoProxy.set(displayInfo);
        }
        return displayInfoProxy.mInfo;
    }

    public final DisplayDevice setPrimaryDisplayDeviceLocked(DisplayDevice displayDevice) {
        DisplayDevice displayDevice2 = this.mPrimaryDisplayDevice;
        this.mPrimaryDisplayDevice = displayDevice;
        this.mPrimaryDisplayDeviceInfo = null;
        this.mBaseDisplayInfo.copyFrom(EMPTY_DISPLAY_INFO);
        this.mInfo.set(null);
        return displayDevice2;
    }

    public final void setUserDisabledHdrTypes(int[] iArr) {
        if (this.mUserDisabledHdrTypes != iArr) {
            this.mUserDisabledHdrTypes = iArr;
            this.mBaseDisplayInfo.userDisabledHdrTypes = iArr;
            this.mInfo.set(null);
        }
    }

    public final String toString() {
        StringWriter stringWriter = new StringWriter();
        dumpLocked(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public final void updateLocked(
            DisplayDeviceRepository displayDeviceRepository,
            SyntheticModeManager syntheticModeManager) {
        DisplayDevice displayDevice = this.mPrimaryDisplayDevice;
        if (displayDevice == null) {
            return;
        }
        if (!((ArrayList) displayDeviceRepository.mDisplayDevices).contains(displayDevice)) {
            setPrimaryDisplayDeviceLocked(null);
            return;
        }
        DisplayDeviceInfo displayDeviceInfoLocked =
                this.mPrimaryDisplayDevice.getDisplayDeviceInfoLocked();
        DisplayDeviceConfig displayDeviceConfig =
                this.mPrimaryDisplayDevice.getDisplayDeviceConfig();
        if (!Objects.equals(this.mPrimaryDisplayDeviceInfo, displayDeviceInfoLocked)
                || this.mDirty) {
            boolean z = CoreRune.MD_DEX_WIRELESS;
            int i = this.mDisplayId;
            if (z && i == 2) {
                Iterator it = ((ArrayList) displayDeviceRepository.mDisplayDevices).iterator();
                while (true) {
                    if (it.hasNext()) {
                        if ((((DisplayDevice) it.next()).getDisplayDeviceInfoLocked().flags
                                        & 67108864)
                                != 0) {
                            displayDeviceInfoLocked.flags &= -536870913;
                            break;
                        }
                    } else {
                        displayDeviceInfoLocked.flags |= 536870912;
                        break;
                    }
                }
            }
            DisplayInfo displayInfo = this.mBaseDisplayInfo;
            displayInfo.layerStack = this.mLayerStack;
            displayInfo.flags = 0;
            displayInfo.removeMode = 0;
            int i2 = displayDeviceInfoLocked.flags;
            if ((i2 & 8) != 0) {
                displayInfo.flags = 1;
            }
            if ((i2 & 4) != 0) {
                displayInfo.flags |= 2;
            }
            if ((i2 & 16) != 0) {
                displayInfo.flags |= 4;
                displayInfo.removeMode = 1;
            }
            if ((i2 & 1024) != 0) {
                displayInfo.removeMode = 1;
            }
            if (i != 0) {
                if (i == 1) {
                    displayInfo.flags |= 8;
                } else if ((i2 & 64) != 0) {
                    displayInfo.flags |= 8;
                }
            }
            if ((i2 & 256) != 0) {
                displayInfo.flags |= 16;
            }
            if ((i2 & 512) != 0) {
                displayInfo.flags |= 32;
            }
            if ((i2 & 4096) != 0) {
                displayInfo.flags |= 64;
            }
            if ((i2 & 8192) != 0) {
                displayInfo.flags |= 128;
            }
            if ((i2 & EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION) != 0) {
                displayInfo.flags |= 256;
            }
            if ((i2 & 32768) != 0) {
                displayInfo.flags |= 512;
            }
            if ((i2 & 2) != 0) {
                displayInfo.flags |=
                        EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION;
            }
            if ((65536 & i2) != 0) {
                displayInfo.flags |= 1024;
            }
            if ((131072 & i2) != 0) {
                displayInfo.flags |= 2048;
            }
            if ((i2 & 524288) != 0) {
                displayInfo.flags |= 4096;
            }
            Rect maskingInsets = getMaskingInsets(displayDeviceInfoLocked);
            int i3 = (displayDeviceInfoLocked.width - maskingInsets.left) - maskingInsets.right;
            int i4 = (displayDeviceInfoLocked.height - maskingInsets.top) - maskingInsets.bottom;
            if (this.mIsAnisotropyCorrectionEnabled && displayDeviceInfoLocked.type == 2) {
                float f = displayDeviceInfoLocked.xDpi;
                if (f > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    float f2 = displayDeviceInfoLocked.yDpi;
                    if (f2 > FullScreenMagnificationGestureHandler.MAX_SCALE) {
                        if (f > f2 * 1.025f) {
                            i4 = (int) (((i4 * f) / f2) + 0.5d);
                        } else if (1.025f * f < f2) {
                            i3 = (int) (((i3 * f2) / f) + 0.5d);
                        }
                    }
                }
            }
            DisplayInfo displayInfo2 = this.mBaseDisplayInfo;
            displayInfo2.type = displayDeviceInfoLocked.type;
            displayInfo2.address = displayDeviceInfoLocked.address;
            displayInfo2.deviceProductInfo = displayDeviceInfoLocked.deviceProductInfo;
            displayInfo2.name = displayDeviceInfoLocked.name;
            displayInfo2.uniqueId = displayDeviceInfoLocked.uniqueId;
            displayInfo2.appWidth = i3;
            displayInfo2.appHeight = i4;
            displayInfo2.logicalWidth = i3;
            displayInfo2.logicalHeight = i4;
            displayInfo2.rotation = 0;
            displayInfo2.modeId = displayDeviceInfoLocked.modeId;
            displayInfo2.renderFrameRate = displayDeviceInfoLocked.renderFrameRate;
            displayInfo2.defaultModeId = displayDeviceInfoLocked.defaultModeId;
            displayInfo2.userPreferredModeId = displayDeviceInfoLocked.userPreferredModeId;
            Display.Mode[] modeArr = displayDeviceInfoLocked.supportedModes;
            displayInfo2.supportedModes = (Display.Mode[]) Arrays.copyOf(modeArr, modeArr.length);
            DisplayInfo displayInfo3 = this.mBaseDisplayInfo;
            Display.Mode[] modeArr2 = displayInfo3.supportedModes;
            syntheticModeManager.getClass();
            if ((displayDeviceConfig.mVrrSupportEnabled || CoreRune.FW_VRR_DISCRETE)
                    && syntheticModeManager.mSynthetic60HzModesEnabled) {
                ArrayList arrayList = new ArrayList();
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                int i5 = 0;
                for (Display.Mode mode : modeArr2) {
                    if (mode.getRefreshRate() > 60.01f) {
                        arrayList.add(mode);
                    }
                    if (mode.getModeId() > i5) {
                        i5 = mode.getModeId();
                    }
                    if (Math.abs((mode.getVsyncRate() / 60.0f) - Math.round(r15)) < 0.01f) {
                        linkedHashMap.put(
                                new Size(mode.getPhysicalWidth(), mode.getPhysicalHeight()),
                                mode.getSupportedHdrTypes());
                    }
                }
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                    Size size = (Size) entry.getKey();
                    i5 += 2;
                    arrayList.add(
                            new Display.Mode(
                                    i5 + 1,
                                    size.getWidth(),
                                    size.getHeight(),
                                    60.0f,
                                    60.0f,
                                    true,
                                    new float[0],
                                    (int[]) entry.getValue()));
                }
                modeArr2 = (Display.Mode[]) arrayList.toArray(new Display.Mode[arrayList.size()]);
            }
            displayInfo3.appsSupportedModes = modeArr2;
            DisplayInfo displayInfo4 = this.mBaseDisplayInfo;
            displayInfo4.colorMode = displayDeviceInfoLocked.colorMode;
            int[] iArr = displayDeviceInfoLocked.supportedColorModes;
            displayInfo4.supportedColorModes = Arrays.copyOf(iArr, iArr.length);
            DisplayInfo displayInfo5 = this.mBaseDisplayInfo;
            displayInfo5.hdrCapabilities = displayDeviceInfoLocked.hdrCapabilities;
            displayInfo5.userDisabledHdrTypes = this.mUserDisabledHdrTypes;
            displayInfo5.minimalPostProcessingSupported =
                    displayDeviceInfoLocked.allmSupported
                            || displayDeviceInfoLocked.gameContentTypeSupported;
            displayInfo5.logicalDensityDpi = displayDeviceInfoLocked.densityDpi;
            displayInfo5.physicalXDpi = displayDeviceInfoLocked.xDpi;
            displayInfo5.physicalYDpi = displayDeviceInfoLocked.yDpi;
            displayInfo5.appVsyncOffsetNanos = displayDeviceInfoLocked.appVsyncOffsetNanos;
            displayInfo5.presentationDeadlineNanos =
                    displayDeviceInfoLocked.presentationDeadlineNanos;
            displayInfo5.state = displayDeviceInfoLocked.state;
            displayInfo5.committedState = displayDeviceInfoLocked.committedState;
            displayInfo5.smallestNominalAppWidth = i3;
            displayInfo5.smallestNominalAppHeight = i4;
            displayInfo5.largestNominalAppWidth = i3;
            displayInfo5.largestNominalAppHeight = i4;
            displayInfo5.ownerUid = displayDeviceInfoLocked.ownerUid;
            displayInfo5.ownerPackageName = displayDeviceInfoLocked.ownerPackageName;
            displayInfo5.displayCutout =
                    (displayDeviceInfoLocked.flags & 2048) != 0
                            ? null
                            : displayDeviceInfoLocked.displayCutout;
            displayInfo5.displayId = i;
            displayInfo5.displayGroupId = this.mDisplayGroupId;
            this.mTempFrameRateOverride.clear();
            DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr =
                    this.mFrameRateOverrides;
            if (frameRateOverrideArr != null) {
                for (DisplayEventReceiver.FrameRateOverride frameRateOverride :
                        frameRateOverrideArr) {
                    this.mTempFrameRateOverride.put(
                            frameRateOverride.uid, Float.valueOf(frameRateOverride.frameRateHz));
                }
            }
            DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr2 =
                    displayDeviceInfoLocked.frameRateOverrides;
            this.mFrameRateOverrides = frameRateOverrideArr2;
            if (frameRateOverrideArr2 != null) {
                for (DisplayEventReceiver.FrameRateOverride frameRateOverride2 :
                        frameRateOverrideArr2) {
                    float floatValue =
                            ((Float)
                                            this.mTempFrameRateOverride.get(
                                                    frameRateOverride2.uid,
                                                    Float.valueOf(
                                                            FullScreenMagnificationGestureHandler
                                                                    .MAX_SCALE)))
                                    .floatValue();
                    if (floatValue == FullScreenMagnificationGestureHandler.MAX_SCALE
                            || frameRateOverride2.frameRateHz != floatValue) {
                        this.mTempFrameRateOverride.put(
                                frameRateOverride2.uid,
                                Float.valueOf(frameRateOverride2.frameRateHz));
                    } else {
                        this.mTempFrameRateOverride.delete(frameRateOverride2.uid);
                    }
                }
            }
            for (int i6 = 0; i6 < this.mTempFrameRateOverride.size(); i6++) {
                this.mPendingFrameRateOverrideUids.add(
                        Integer.valueOf(this.mTempFrameRateOverride.keyAt(i6)));
            }
            DisplayInfo displayInfo6 = this.mBaseDisplayInfo;
            displayInfo6.brightnessMinimum = FullScreenMagnificationGestureHandler.MAX_SCALE;
            displayInfo6.brightnessMaximum = displayDeviceInfoLocked.brightnessMaximum;
            displayInfo6.brightnessDefault = displayDeviceInfoLocked.brightnessDefault;
            displayInfo6.hdrSdrRatio = displayDeviceInfoLocked.hdrSdrRatio;
            displayInfo6.roundedCorners = displayDeviceInfoLocked.roundedCorners;
            displayInfo6.installOrientation = displayDeviceInfoLocked.installOrientation;
            displayInfo6.displayShape = displayDeviceInfoLocked.displayShape;
            if (this.mDevicePosition == 1) {
                displayInfo6.flags |= 8200;
                displayInfo6.removeMode = 1;
            }
            displayInfo6.layoutLimitedRefreshRate = this.mLayoutLimitedRefreshRate;
            displayInfo6.thermalRefreshRateThrottling = this.mThermalRefreshRateThrottling;
            displayInfo6.thermalBrightnessThrottlingDataId =
                    this.mThermalBrightnessThrottlingDataId;
            this.mPrimaryDisplayDeviceInfo = displayDeviceInfoLocked;
            this.mInfo.set(null);
            this.mDirty = false;
            if (CoreRune.SYSFW_APP_SPEG && (displayDeviceInfoLocked.flags & 1073741824) != 0) {
                this.mBaseDisplayInfo.flags |= 32768;
            }
            int i7 = displayDeviceInfoLocked.flags;
            if ((8388608 & i7) != 0) {
                this.mBaseDisplayInfo.flags |= 262144;
            }
            if ((4194304 & i7) != 0) {
                this.mBaseDisplayInfo.flags |= 524288;
            }
            if ((i7 & 2097152) != 0) {
                DisplayInfo displayInfo7 = this.mBaseDisplayInfo;
                displayInfo7.flags = 2097152 | displayInfo7.flags;
            }
            if (CoreRune.BAIDU_CARLIFE && (Integer.MIN_VALUE & i7) != 0) {
                this.mBaseDisplayInfo.flags |= 1048576;
            }
            if ((i7 & 33554432) != 0) {
                DisplayInfo displayInfo8 = this.mBaseDisplayInfo;
                displayInfo8.flags = 33554432 | displayInfo8.flags;
            }
            if ((i7 & 268435456) != 0) {
                DisplayInfo displayInfo9 = this.mBaseDisplayInfo;
                displayInfo9.flags = 268435456 | displayInfo9.flags;
                int i8 = displayDeviceInfoLocked.rotation;
                if (i8 == 1 || i8 == 3) {
                    displayInfo9.rotation = 1;
                }
                displayInfo9.refreshRateMode = 0;
            }
            if ((i7 & 536870912) != 0) {
                DisplayInfo displayInfo10 = this.mBaseDisplayInfo;
                displayInfo10.flags = 536870912 | displayInfo10.flags;
            }
            if ((i7 & 134217728) != 0) {
                DisplayInfo displayInfo11 = this.mBaseDisplayInfo;
                displayInfo11.flags = 134217728 | displayInfo11.flags;
            }
            if ((i7 & 67108864) != 0) {
                this.mBaseDisplayInfo.flags |= 67108864;
            }
        }
    }
}
