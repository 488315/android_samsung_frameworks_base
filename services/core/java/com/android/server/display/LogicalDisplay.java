package com.android.server.display;

import android.graphics.Point;
import android.graphics.Rect;
import android.p005os.IInstalld;
import android.util.ArraySet;
import android.util.SparseArray;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DisplayEventReceiver;
import android.view.DisplayInfo;
import android.view.SurfaceControl;
import com.android.server.display.mode.DisplayModeDirector;
import com.android.server.wm.utils.InsetUtils;
import com.samsung.android.rune.CoreRune;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class LogicalDisplay {
    public static final DisplayInfo EMPTY_DISPLAY_INFO = new DisplayInfo();
    public final DisplayInfo mBaseDisplayInfo;
    public DisplayModeDirector.DesiredDisplayModeSpecs mDesiredDisplayModeSpecs;
    public int mDevicePosition;
    public boolean mDirty;
    public int mDisplayGroupId;
    public String mDisplayGroupName;
    public final int mDisplayId;
    public int mDisplayOffsetX;
    public int mDisplayOffsetY;
    public final Point mDisplayPosition;
    public boolean mDisplayScalingDisabled;
    public boolean mDualSwitchApplied;
    public DisplayEventReceiver.FrameRateOverride[] mFrameRateOverrides;
    public boolean mHasContent;
    public final DisplayInfoProxy mInfo;
    public boolean mIsEnabled;
    public boolean mIsInTransition;
    public final int mLayerStack;
    public SurfaceControl.RefreshRateRange mLayoutLimitedRefreshRate;
    public int mLeadDisplayId;
    public boolean mMaskingCutout;
    public boolean mNeedToUpdateBaseDisplayInfo;
    public DisplayDevice mOldDisplayDevice;
    public DisplayInfo mOverrideDisplayInfo;
    public ArraySet mPendingFrameRateOverrideUids;
    public DisplayDevice mPrimaryDisplayDevice;
    public DisplayDeviceInfo mPrimaryDisplayDeviceInfo;
    public int mRequestedColorMode;
    public boolean mRequestedMinimalPostProcessing;
    public final Rect mTempDisplayRect;
    public final SparseArray mTempFrameRateOverride;
    public final Rect mTempLayerStackRect;
    public String mThermalBrightnessThrottlingDataId;
    public SparseArray mThermalRefreshRateThrottling;
    public int[] mUserDisabledHdrTypes;

    public LogicalDisplay(int i, int i2, DisplayDevice displayDevice) {
        DisplayInfo displayInfo = new DisplayInfo();
        this.mBaseDisplayInfo = displayInfo;
        this.mLeadDisplayId = -1;
        this.mDisplayGroupId = -1;
        this.mInfo = new DisplayInfoProxy(null);
        this.mOldDisplayDevice = null;
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
        displayInfo.thermalBrightnessThrottlingDataId = "default";
    }

    public void setDevicePositionLocked(int i) {
        if (this.mDevicePosition != i) {
            this.mDevicePosition = i;
            this.mDirty = true;
        }
    }

    public int getDisplayIdLocked() {
        return this.mDisplayId;
    }

    public DisplayDevice getPrimaryDisplayDeviceLocked() {
        return this.mPrimaryDisplayDevice;
    }

    public DisplayInfo getDisplayInfoLocked() {
        if (this.mInfo.get() == null) {
            DisplayInfo displayInfo = new DisplayInfo();
            displayInfo.copyFrom(this.mBaseDisplayInfo);
            DisplayInfo displayInfo2 = this.mOverrideDisplayInfo;
            if (displayInfo2 != null) {
                displayInfo.appWidth = displayInfo2.appWidth;
                displayInfo.appHeight = displayInfo2.appHeight;
                displayInfo.smallestNominalAppWidth = displayInfo2.smallestNominalAppWidth;
                displayInfo.smallestNominalAppHeight = displayInfo2.smallestNominalAppHeight;
                displayInfo.largestNominalAppWidth = displayInfo2.largestNominalAppWidth;
                displayInfo.largestNominalAppHeight = displayInfo2.largestNominalAppHeight;
                displayInfo.logicalWidth = displayInfo2.logicalWidth;
                displayInfo.logicalHeight = displayInfo2.logicalHeight;
                displayInfo.physicalXDpi = displayInfo2.physicalXDpi;
                displayInfo.physicalYDpi = displayInfo2.physicalYDpi;
                displayInfo.rotation = displayInfo2.rotation;
                displayInfo.displayCutout = displayInfo2.displayCutout;
                displayInfo.logicalDensityDpi = displayInfo2.logicalDensityDpi;
                displayInfo.roundedCorners = displayInfo2.roundedCorners;
                displayInfo.displayShape = displayInfo2.displayShape;
            }
            this.mInfo.set(displayInfo);
        }
        return this.mInfo.get();
    }

    public DisplayEventReceiver.FrameRateOverride[] getFrameRateOverrides() {
        return this.mFrameRateOverrides;
    }

    public ArraySet getPendingFrameRateOverrideUids() {
        return this.mPendingFrameRateOverrideUids;
    }

    public void clearPendingFrameRateOverrideUids() {
        this.mPendingFrameRateOverrideUids = new ArraySet();
    }

    public void getNonOverrideDisplayInfoLocked(DisplayInfo displayInfo) {
        displayInfo.copyFrom(this.mBaseDisplayInfo);
    }

    public boolean setDisplayInfoOverrideFromWindowManagerLocked(DisplayInfo displayInfo) {
        if (displayInfo != null) {
            DisplayInfo displayInfo2 = this.mOverrideDisplayInfo;
            if (displayInfo2 == null) {
                this.mOverrideDisplayInfo = new DisplayInfo(displayInfo);
                this.mInfo.set(null);
                return true;
            }
            if (displayInfo2.equals(displayInfo)) {
                return false;
            }
            this.mOverrideDisplayInfo.copyFrom(displayInfo);
            this.mInfo.set(null);
            return true;
        }
        if (this.mOverrideDisplayInfo == null) {
            return false;
        }
        this.mOverrideDisplayInfo = null;
        this.mInfo.set(null);
        return true;
    }

    public boolean isValidLocked() {
        return this.mPrimaryDisplayDevice != null;
    }

    public boolean isDirtyLocked() {
        return this.mDirty;
    }

    public void updateDisplayGroupIdLocked(int i) {
        if (i != this.mDisplayGroupId) {
            this.mDisplayGroupId = i;
            this.mDirty = true;
        }
    }

    public void updateLayoutLimitedRefreshRateLocked(SurfaceControl.RefreshRateRange refreshRateRange) {
        if (Objects.equals(refreshRateRange, this.mLayoutLimitedRefreshRate)) {
            return;
        }
        this.mLayoutLimitedRefreshRate = refreshRateRange;
        this.mDirty = true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateThermalRefreshRateThrottling(SparseArray sparseArray) {
        if (sparseArray == null) {
            sparseArray = new SparseArray();
        }
        if (this.mThermalRefreshRateThrottling.contentEquals(sparseArray)) {
            return;
        }
        this.mThermalRefreshRateThrottling = sparseArray;
        this.mDirty = true;
    }

    public void updateLocked(DisplayDeviceRepository displayDeviceRepository) {
        DisplayDevice displayDevice = this.mPrimaryDisplayDevice;
        if (displayDevice == null) {
            return;
        }
        if (!displayDeviceRepository.containsLocked(displayDevice)) {
            setPrimaryDisplayDeviceLocked(null);
            return;
        }
        DisplayDeviceInfo displayDeviceInfoLocked = this.mPrimaryDisplayDevice.getDisplayDeviceInfoLocked();
        if (!Objects.equals(this.mPrimaryDisplayDeviceInfo, displayDeviceInfoLocked) || this.mDirty) {
            if (CoreRune.MD_DEX_WIRELESS && this.mDisplayId == 2) {
                if (displayDeviceRepository.hasDisplayDeviceForWirelessDexLocked()) {
                    displayDeviceInfoLocked.flags &= -536870913;
                } else {
                    displayDeviceInfoLocked.flags |= 536870912;
                }
            }
            DisplayInfo displayInfo = this.mBaseDisplayInfo;
            displayInfo.layerStack = this.mLayerStack;
            displayInfo.flags = 0;
            displayInfo.removeMode = 0;
            int i = displayDeviceInfoLocked.flags;
            if ((i & 8) != 0) {
                displayInfo.flags = 0 | 1;
            }
            if ((i & 4) != 0) {
                displayInfo.flags = 2 | displayInfo.flags;
            }
            if ((i & 16) != 0) {
                displayInfo.flags |= 4;
                displayInfo.removeMode = 1;
            }
            if ((i & 1024) != 0) {
                displayInfo.removeMode = 1;
            }
            int i2 = this.mDisplayId;
            if (i2 != 0) {
                if (i2 == 1) {
                    displayInfo.flags |= 8;
                } else if ((i & 64) != 0) {
                    displayInfo.flags |= 8;
                }
            }
            if ((i & 256) != 0) {
                displayInfo.flags |= 16;
            }
            if ((i & 512) != 0) {
                displayInfo.flags |= 32;
            }
            if ((i & IInstalld.FLAG_USE_QUOTA) != 0) {
                displayInfo.flags |= 64;
            }
            if ((i & IInstalld.FLAG_FORCE) != 0) {
                displayInfo.flags |= 128;
            }
            if ((i & 16384) != 0) {
                displayInfo.flags |= 256;
            }
            if ((i & 32768) != 0) {
                displayInfo.flags |= 512;
            }
            if ((65536 & i) != 0) {
                displayInfo.flags |= 1024;
            }
            if ((131072 & i) != 0) {
                displayInfo.flags |= IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES;
            }
            if ((i & 524288) != 0) {
                displayInfo.flags |= IInstalld.FLAG_USE_QUOTA;
            }
            Rect maskingInsets = getMaskingInsets(displayDeviceInfoLocked);
            int i3 = (displayDeviceInfoLocked.width - maskingInsets.left) - maskingInsets.right;
            int i4 = (displayDeviceInfoLocked.height - maskingInsets.top) - maskingInsets.bottom;
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
            Display.Mode[] modeArr = displayDeviceInfoLocked.supportedModes;
            displayInfo2.supportedModes = (Display.Mode[]) Arrays.copyOf(modeArr, modeArr.length);
            DisplayInfo displayInfo3 = this.mBaseDisplayInfo;
            displayInfo3.colorMode = displayDeviceInfoLocked.colorMode;
            int[] iArr = displayDeviceInfoLocked.supportedColorModes;
            displayInfo3.supportedColorModes = Arrays.copyOf(iArr, iArr.length);
            DisplayInfo displayInfo4 = this.mBaseDisplayInfo;
            displayInfo4.hdrCapabilities = displayDeviceInfoLocked.hdrCapabilities;
            displayInfo4.userDisabledHdrTypes = this.mUserDisabledHdrTypes;
            displayInfo4.minimalPostProcessingSupported = displayDeviceInfoLocked.allmSupported || displayDeviceInfoLocked.gameContentTypeSupported;
            displayInfo4.logicalDensityDpi = displayDeviceInfoLocked.densityDpi;
            displayInfo4.physicalXDpi = displayDeviceInfoLocked.xDpi;
            displayInfo4.physicalYDpi = displayDeviceInfoLocked.yDpi;
            displayInfo4.appVsyncOffsetNanos = displayDeviceInfoLocked.appVsyncOffsetNanos;
            displayInfo4.presentationDeadlineNanos = displayDeviceInfoLocked.presentationDeadlineNanos;
            displayInfo4.state = displayDeviceInfoLocked.state;
            displayInfo4.committedState = displayDeviceInfoLocked.committedState;
            displayInfo4.smallestNominalAppWidth = i3;
            displayInfo4.smallestNominalAppHeight = i4;
            displayInfo4.largestNominalAppWidth = i3;
            displayInfo4.largestNominalAppHeight = i4;
            displayInfo4.ownerUid = displayDeviceInfoLocked.ownerUid;
            displayInfo4.ownerPackageName = displayDeviceInfoLocked.ownerPackageName;
            displayInfo4.displayCutout = (displayDeviceInfoLocked.flags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0 ? null : displayDeviceInfoLocked.displayCutout;
            displayInfo4.displayId = this.mDisplayId;
            displayInfo4.displayGroupId = this.mDisplayGroupId;
            updateFrameRateOverrides(displayDeviceInfoLocked);
            DisplayInfo displayInfo5 = this.mBaseDisplayInfo;
            displayInfo5.brightnessMinimum = displayDeviceInfoLocked.brightnessMinimum;
            displayInfo5.brightnessMaximum = displayDeviceInfoLocked.brightnessMaximum;
            displayInfo5.brightnessDefault = displayDeviceInfoLocked.brightnessDefault;
            displayInfo5.hdrSdrRatio = displayDeviceInfoLocked.hdrSdrRatio;
            displayInfo5.roundedCorners = displayDeviceInfoLocked.roundedCorners;
            displayInfo5.installOrientation = displayDeviceInfoLocked.installOrientation;
            displayInfo5.displayShape = displayDeviceInfoLocked.displayShape;
            if (this.mDevicePosition == 1) {
                displayInfo5.flags = displayInfo5.flags | IInstalld.FLAG_FORCE | 8;
                displayInfo5.removeMode = 1;
            }
            displayInfo5.layoutLimitedRefreshRate = this.mLayoutLimitedRefreshRate;
            displayInfo5.thermalRefreshRateThrottling = this.mThermalRefreshRateThrottling;
            displayInfo5.thermalBrightnessThrottlingDataId = this.mThermalBrightnessThrottlingDataId;
            this.mPrimaryDisplayDeviceInfo = displayDeviceInfoLocked;
            this.mInfo.set(null);
            this.mDirty = false;
            if (CoreRune.SYSFW_APP_SPEG && (displayDeviceInfoLocked.flags & 1073741824) != 0) {
                this.mBaseDisplayInfo.flags |= 32768;
            }
            int i5 = displayDeviceInfoLocked.flags;
            if ((2097152 & i5) != 0) {
                this.mBaseDisplayInfo.flags |= 33554432;
            }
            if ((8388608 & i5) != 0) {
                this.mBaseDisplayInfo.flags |= 524288;
            }
            if ((16777216 & i5) != 0) {
                this.mBaseDisplayInfo.flags |= 262144;
            }
            if (CoreRune.BAIDU_CARLIFE && (Integer.MIN_VALUE & i5) != 0) {
                this.mBaseDisplayInfo.flags |= 1048576;
            }
            if ((4194304 & i5) != 0) {
                this.mBaseDisplayInfo.flags |= 16384;
            }
            if ((i5 & 268435456) != 0) {
                DisplayInfo displayInfo6 = this.mBaseDisplayInfo;
                displayInfo6.flags = 268435456 | displayInfo6.flags;
                int i6 = displayDeviceInfoLocked.rotation;
                if (i6 == 1 || i6 == 3) {
                    displayInfo6.rotation = 1;
                }
                displayInfo6.refreshRateMode = 0;
            }
            if ((i5 & 536870912) != 0) {
                this.mBaseDisplayInfo.flags |= 536870912;
            }
            if ((i5 & 134217728) != 0) {
                DisplayInfo displayInfo7 = this.mBaseDisplayInfo;
                displayInfo7.flags = 134217728 | displayInfo7.flags;
            }
            if ((i5 & 67108864) != 0) {
                this.mBaseDisplayInfo.flags |= 67108864;
            }
        }
    }

    public final void updateFrameRateOverrides(DisplayDeviceInfo displayDeviceInfo) {
        this.mTempFrameRateOverride.clear();
        DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr = this.mFrameRateOverrides;
        if (frameRateOverrideArr != null) {
            for (DisplayEventReceiver.FrameRateOverride frameRateOverride : frameRateOverrideArr) {
                this.mTempFrameRateOverride.put(frameRateOverride.uid, Float.valueOf(frameRateOverride.frameRateHz));
            }
        }
        DisplayEventReceiver.FrameRateOverride[] frameRateOverrideArr2 = displayDeviceInfo.frameRateOverrides;
        this.mFrameRateOverrides = frameRateOverrideArr2;
        if (frameRateOverrideArr2 != null) {
            for (DisplayEventReceiver.FrameRateOverride frameRateOverride2 : frameRateOverrideArr2) {
                float floatValue = ((Float) this.mTempFrameRateOverride.get(frameRateOverride2.uid, Float.valueOf(DisplayPowerController2.RATE_FROM_DOZE_TO_ON))).floatValue();
                if (floatValue == DisplayPowerController2.RATE_FROM_DOZE_TO_ON || frameRateOverride2.frameRateHz != floatValue) {
                    this.mTempFrameRateOverride.put(frameRateOverride2.uid, Float.valueOf(frameRateOverride2.frameRateHz));
                } else {
                    this.mTempFrameRateOverride.delete(frameRateOverride2.uid);
                }
            }
        }
        for (int i = 0; i < this.mTempFrameRateOverride.size(); i++) {
            this.mPendingFrameRateOverrideUids.add(Integer.valueOf(this.mTempFrameRateOverride.keyAt(i)));
        }
    }

    public Rect getInsets() {
        return getMaskingInsets(this.mPrimaryDisplayDeviceInfo);
    }

    public static Rect getMaskingInsets(DisplayDeviceInfo displayDeviceInfo) {
        DisplayCutout displayCutout;
        if (((displayDeviceInfo.flags & IInstalld.FLAG_FREE_CACHE_DEFY_TARGET_FREE_BYTES) != 0) && (displayCutout = displayDeviceInfo.displayCutout) != null) {
            return displayCutout.getSafeInsets();
        }
        return new Rect();
    }

    public Point getDisplayPosition() {
        return new Point(this.mDisplayPosition);
    }

    public void configureDisplayLocked(SurfaceControl.Transaction transaction, DisplayDevice displayDevice, boolean z) {
        int i;
        int i2;
        if (getDisplayIdLocked() == 4) {
            z = false;
        }
        displayDevice.setLayerStackLocked(transaction, z ? -1 : this.mLayerStack, this.mDisplayId);
        displayDevice.setDisplayFlagsLocked(transaction, (!isEnabledLocked() || displayDevice.getDisplayDeviceInfoLocked().touch == 0) ? 0 : 1);
        if (displayDevice == this.mPrimaryDisplayDevice) {
            displayDevice.setDesiredDisplayModeSpecsLocked(this.mDesiredDisplayModeSpecs);
            displayDevice.setRequestedColorModeLocked(this.mRequestedColorMode);
        } else {
            displayDevice.setDesiredDisplayModeSpecsLocked(new DisplayModeDirector.DesiredDisplayModeSpecs());
            displayDevice.setRequestedColorModeLocked(0);
        }
        displayDevice.setAutoLowLatencyModeLocked(this.mRequestedMinimalPostProcessing);
        displayDevice.setGameContentTypeLocked(this.mRequestedMinimalPostProcessing);
        DisplayInfo displayInfoLocked = getDisplayInfoLocked();
        DisplayDeviceInfo displayDeviceInfoLocked = displayDevice.getDisplayDeviceInfoLocked();
        this.mTempLayerStackRect.set(0, 0, displayInfoLocked.logicalWidth, displayInfoLocked.logicalHeight);
        int i3 = (displayDeviceInfoLocked.flags & 2) != 0 ? displayInfoLocked.rotation : 0;
        if (CoreRune.MD_DEX_EMULATOR && this.mDualSwitchApplied) {
            i3 = 1;
        }
        int i4 = (i3 + displayDeviceInfoLocked.rotation) % 4;
        boolean z2 = i4 == 1 || i4 == 3;
        int i5 = z2 ? displayDeviceInfoLocked.height : displayDeviceInfoLocked.width;
        int i6 = z2 ? displayDeviceInfoLocked.width : displayDeviceInfoLocked.height;
        Rect maskingInsets = getMaskingInsets(displayDeviceInfoLocked);
        InsetUtils.rotateInsets(maskingInsets, i4);
        int i7 = i5 - (maskingInsets.left + maskingInsets.right);
        int i8 = i6 - (maskingInsets.top + maskingInsets.bottom);
        if ((displayInfoLocked.flags & 1073741824) != 0 || this.mDisplayScalingDisabled) {
            i = displayInfoLocked.logicalWidth;
            i2 = displayInfoLocked.logicalHeight;
        } else {
            int i9 = displayInfoLocked.logicalHeight;
            int i10 = i7 * i9;
            int i11 = displayInfoLocked.logicalWidth;
            if (i10 < i8 * i11) {
                i2 = (i9 * i7) / i11;
                i = i7;
            } else {
                i = (i11 * i8) / i9;
                i2 = i8;
            }
        }
        int i12 = (i8 - i2) / 2;
        int i13 = (i7 - i) / 2;
        this.mTempDisplayRect.set(i13, i12, i + i13, i2 + i12);
        this.mTempDisplayRect.offset(maskingInsets.left, maskingInsets.top);
        if (i4 == 0) {
            this.mTempDisplayRect.offset(this.mDisplayOffsetX, this.mDisplayOffsetY);
        } else if (i4 == 1) {
            this.mTempDisplayRect.offset(this.mDisplayOffsetY, -this.mDisplayOffsetX);
        } else if (i4 == 2) {
            this.mTempDisplayRect.offset(-this.mDisplayOffsetX, -this.mDisplayOffsetY);
        } else {
            this.mTempDisplayRect.offset(-this.mDisplayOffsetY, this.mDisplayOffsetX);
        }
        if ((displayDeviceInfoLocked.flags & 4194304) != 0) {
            this.mTempDisplayRect.set(0, 0, displayInfoLocked.logicalWidth, displayInfoLocked.logicalHeight);
        }
        Point point = this.mDisplayPosition;
        Rect rect = this.mTempDisplayRect;
        point.set(rect.left, rect.top);
        displayDevice.setProjectionLocked(transaction, i4, this.mTempLayerStackRect, this.mTempDisplayRect);
    }

    public boolean hasContentLocked() {
        return this.mHasContent;
    }

    public void setHasContentLocked(boolean z) {
        this.mHasContent = z;
    }

    public void setDesiredDisplayModeSpecsLocked(DisplayModeDirector.DesiredDisplayModeSpecs desiredDisplayModeSpecs) {
        this.mDesiredDisplayModeSpecs = desiredDisplayModeSpecs;
    }

    public DisplayModeDirector.DesiredDisplayModeSpecs getDesiredDisplayModeSpecsLocked() {
        return this.mDesiredDisplayModeSpecs;
    }

    public void setRequestedColorModeLocked(int i) {
        this.mRequestedColorMode = i;
    }

    public boolean getRequestedMinimalPostProcessingLocked() {
        return this.mRequestedMinimalPostProcessing;
    }

    public void setRequestedMinimalPostProcessingLocked(boolean z) {
        this.mRequestedMinimalPostProcessing = z;
    }

    public int getRequestedColorModeLocked() {
        return this.mRequestedColorMode;
    }

    public int getDisplayOffsetXLocked() {
        return this.mDisplayOffsetX;
    }

    public int getDisplayOffsetYLocked() {
        return this.mDisplayOffsetY;
    }

    public void setDisplayOffsetsLocked(int i, int i2) {
        this.mDisplayOffsetX = i;
        this.mDisplayOffsetY = i2;
    }

    public boolean isDisplayScalingDisabled() {
        return this.mDisplayScalingDisabled;
    }

    public void setDisplayScalingDisabledLocked(boolean z) {
        this.mDisplayScalingDisabled = z;
    }

    public void setUserDisabledHdrTypes(int[] iArr) {
        if (this.mUserDisabledHdrTypes != iArr) {
            this.mUserDisabledHdrTypes = iArr;
            this.mBaseDisplayInfo.userDisabledHdrTypes = iArr;
            this.mInfo.set(null);
        }
    }

    public void swapDisplaysLocked(LogicalDisplay logicalDisplay) {
        setPrimaryDisplayDeviceLocked(logicalDisplay.setPrimaryDisplayDeviceLocked(this.mPrimaryDisplayDevice));
    }

    public DisplayDevice setPrimaryDisplayDeviceLocked(DisplayDevice displayDevice) {
        DisplayDevice displayDevice2 = this.mPrimaryDisplayDevice;
        this.mPrimaryDisplayDevice = displayDevice;
        this.mPrimaryDisplayDeviceInfo = null;
        this.mBaseDisplayInfo.copyFrom(EMPTY_DISPLAY_INFO);
        this.mInfo.set(null);
        return displayDevice2;
    }

    public boolean isEnabledLocked() {
        return this.mIsEnabled;
    }

    public void setEnabledLocked(boolean z) {
        this.mIsEnabled = z;
    }

    public boolean isInTransitionLocked() {
        return this.mIsInTransition;
    }

    public void setIsInTransitionLocked(boolean z) {
        this.mIsInTransition = z;
    }

    public void setThermalBrightnessThrottlingDataIdLocked(String str) {
        if (Objects.equals(str, this.mThermalBrightnessThrottlingDataId)) {
            return;
        }
        this.mThermalBrightnessThrottlingDataId = str;
        this.mDirty = true;
    }

    public void setLeadDisplayLocked(int i) {
        int i2 = this.mDisplayId;
        if (i2 == this.mLeadDisplayId || i2 == i) {
            return;
        }
        this.mLeadDisplayId = i;
    }

    public int getLeadDisplayIdLocked() {
        return this.mLeadDisplayId;
    }

    public void setDisplayGroupNameLocked(String str) {
        this.mDisplayGroupName = str;
    }

    public String getDisplayGroupNameLocked() {
        return this.mDisplayGroupName;
    }

    public void dumpLocked(PrintWriter printWriter) {
        printWriter.println("mDisplayId=" + this.mDisplayId);
        printWriter.println("mIsEnabled=" + this.mIsEnabled);
        printWriter.println("mIsInTransition=" + this.mIsInTransition);
        printWriter.println("mLayerStack=" + this.mLayerStack);
        printWriter.println("mPosition=" + this.mDevicePosition);
        printWriter.println("mHasContent=" + this.mHasContent);
        printWriter.println("mDesiredDisplayModeSpecs={" + this.mDesiredDisplayModeSpecs + "}");
        StringBuilder sb = new StringBuilder();
        sb.append("mRequestedColorMode=");
        sb.append(this.mRequestedColorMode);
        printWriter.println(sb.toString());
        printWriter.println("mDisplayOffset=(" + this.mDisplayOffsetX + ", " + this.mDisplayOffsetY + ")");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("mDisplayScalingDisabled=");
        sb2.append(this.mDisplayScalingDisabled);
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append("mPrimaryDisplayDevice=");
        DisplayDevice displayDevice = this.mPrimaryDisplayDevice;
        sb3.append(displayDevice != null ? displayDevice.getNameLocked() : "null");
        printWriter.println(sb3.toString());
        printWriter.println("mBaseDisplayInfo=" + this.mBaseDisplayInfo);
        printWriter.println("mOverrideDisplayInfo=" + this.mOverrideDisplayInfo);
        printWriter.println("mRequestedMinimalPostProcessing=" + this.mRequestedMinimalPostProcessing);
        printWriter.println("mFrameRateOverrides=" + Arrays.toString(this.mFrameRateOverrides));
        printWriter.println("mPendingFrameRateOverrideUids=" + this.mPendingFrameRateOverrideUids);
        printWriter.println("mDisplayGroupName=" + this.mDisplayGroupName);
        printWriter.println("mThermalBrightnessThrottlingDataId=" + this.mThermalBrightnessThrottlingDataId);
        printWriter.println("mLeadDisplayId=" + this.mLeadDisplayId);
        printWriter.println("mLayoutLimitedRefreshRate=" + this.mLayoutLimitedRefreshRate);
        printWriter.println("mThermalRefreshRateThrottling=" + this.mThermalRefreshRateThrottling);
        if (CoreRune.MD_DEX_EMULATOR && this.mDualSwitchApplied) {
            printWriter.println("mDualSwitchApplied=true");
        }
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        dumpLocked(new PrintWriter(stringWriter));
        return stringWriter.toString();
    }

    public void setMaskingCutout(boolean z) {
        this.mMaskingCutout = z;
        this.mNeedToUpdateBaseDisplayInfo = true;
    }
}
