package android.view;

import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.display.DeviceProductInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.SparseArray;
import android.util.proto.ProtoOutputStream;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.SurfaceControl;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.display.feature.flags.Flags;
import com.samsung.android.rune.CoreRune;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

/* loaded from: classes4.dex */
public final class DisplayInfo implements Parcelable {
    public static final Parcelable.Creator<DisplayInfo> CREATOR = new Parcelable.Creator<DisplayInfo>() { // from class: android.view.DisplayInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayInfo createFromParcel(Parcel parcel) {
            return new DisplayInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayInfo[] newArray(int i) {
            return new DisplayInfo[i];
        }
    };
    public DisplayAddress address;
    public int appHeight;
    public long appVsyncOffsetNanos;
    public int appWidth;
    public Display.Mode[] appsSupportedModes;
    public float brightnessDefault;
    public float brightnessDim;
    public float brightnessMaximum;
    public float brightnessMinimum;
    public boolean canHostTasks;
    public int colorMode;
    public int committedState;
    public int defaultModeId;
    public DeviceProductInfo deviceProductInfo;
    public DisplayCutout displayCutout;
    public int displayGroupId;
    public int displayId;
    public DisplayShape displayShape;
    public int flags;
    public FrameRateCategoryRate frameRateCategoryRate;
    public boolean hasArrSupport;
    public Display.HdrCapabilities hdrCapabilities;
    public float hdrSdrRatio;
    public int installOrientation;
    public boolean isForceSdr;
    public int largestNominalAppHeight;
    public int largestNominalAppWidth;
    public int layerStack;
    public SurfaceControl.RefreshRateRange layoutLimitedRefreshRate;
    public int logicalDensityDpi;
    public int logicalHeight;
    public int logicalWidth;
    public boolean minimalPostProcessingSupported;
    public int modeId;
    public String name;
    public String ownerPackageName;
    public int ownerUid;
    public float physicalXDpi;
    public float physicalYDpi;
    public long presentationDeadlineNanos;
    public int refreshRateMode;
    public float refreshRateOverride;
    public int removeMode;
    public float renderFrameRate;
    public int rotation;
    public RoundedCorners roundedCorners;
    public int smallestNominalAppHeight;
    public int smallestNominalAppWidth;
    public int state;
    public int[] supportedColorModes;
    public Display.Mode[] supportedModes;
    public float[] supportedRefreshRates;
    public String thermalBrightnessThrottlingDataId;
    public SparseArray<SurfaceControl.RefreshRateRange> thermalRefreshRateThrottling;
    public int type;
    public String uniqueId;
    public int[] userDisabledHdrTypes;
    public int userPreferredModeId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public int hashCode() {
        return 0;
    }

    public DisplayInfo() {
        this.supportedRefreshRates = new float[0];
        this.refreshRateMode = 0;
        this.userPreferredModeId = -1;
        this.supportedModes = Display.Mode.EMPTY_ARRAY;
        this.appsSupportedModes = Display.Mode.EMPTY_ARRAY;
        this.supportedColorModes = new int[]{0};
        this.userDisabledHdrTypes = new int[0];
        this.removeMode = 0;
        this.hdrSdrRatio = Float.NaN;
        this.thermalRefreshRateThrottling = new SparseArray<>();
    }

    public DisplayInfo(DisplayInfo displayInfo) {
        this.supportedRefreshRates = new float[0];
        this.refreshRateMode = 0;
        this.userPreferredModeId = -1;
        this.supportedModes = Display.Mode.EMPTY_ARRAY;
        this.appsSupportedModes = Display.Mode.EMPTY_ARRAY;
        this.supportedColorModes = new int[]{0};
        this.userDisabledHdrTypes = new int[0];
        this.removeMode = 0;
        this.hdrSdrRatio = Float.NaN;
        this.thermalRefreshRateThrottling = new SparseArray<>();
        copyFrom(displayInfo);
    }

    private DisplayInfo(Parcel parcel) {
        this.supportedRefreshRates = new float[0];
        this.refreshRateMode = 0;
        this.userPreferredModeId = -1;
        this.supportedModes = Display.Mode.EMPTY_ARRAY;
        this.appsSupportedModes = Display.Mode.EMPTY_ARRAY;
        this.supportedColorModes = new int[]{0};
        this.userDisabledHdrTypes = new int[0];
        this.removeMode = 0;
        this.hdrSdrRatio = Float.NaN;
        this.thermalRefreshRateThrottling = new SparseArray<>();
        readFromParcel(parcel);
    }

    public boolean equals(Object obj) {
        return (obj instanceof DisplayInfo) && equals((DisplayInfo) obj);
    }

    public boolean equals(DisplayInfo displayInfo) {
        return equals(displayInfo, false);
    }

    public boolean equals(DisplayInfo displayInfo, boolean z) {
        boolean z2 = displayInfo != null && this.layerStack == displayInfo.layerStack && this.flags == displayInfo.flags && this.type == displayInfo.type && this.displayId == displayInfo.displayId && this.displayGroupId == displayInfo.displayGroupId && Objects.equals(this.address, displayInfo.address) && Objects.equals(this.deviceProductInfo, displayInfo.deviceProductInfo) && Objects.equals(this.uniqueId, displayInfo.uniqueId) && this.appWidth == displayInfo.appWidth && this.appHeight == displayInfo.appHeight && this.smallestNominalAppWidth == displayInfo.smallestNominalAppWidth && this.smallestNominalAppHeight == displayInfo.smallestNominalAppHeight && this.largestNominalAppWidth == displayInfo.largestNominalAppWidth && this.largestNominalAppHeight == displayInfo.largestNominalAppHeight && this.logicalWidth == displayInfo.logicalWidth && this.logicalHeight == displayInfo.logicalHeight && Objects.equals(this.displayCutout, displayInfo.displayCutout) && this.rotation == displayInfo.rotation && this.hasArrSupport == displayInfo.hasArrSupport && Objects.equals(this.frameRateCategoryRate, displayInfo.frameRateCategoryRate) && Arrays.equals(this.supportedRefreshRates, displayInfo.supportedRefreshRates) && this.defaultModeId == displayInfo.defaultModeId && (!CoreRune.FW_VRR_REFRESH_RATE_MODE || this.refreshRateMode == displayInfo.refreshRateMode) && this.userPreferredModeId == displayInfo.userPreferredModeId && Arrays.equals(this.supportedModes, displayInfo.supportedModes) && Arrays.equals(this.appsSupportedModes, displayInfo.appsSupportedModes) && this.colorMode == displayInfo.colorMode && Arrays.equals(this.supportedColorModes, displayInfo.supportedColorModes) && Objects.equals(this.hdrCapabilities, displayInfo.hdrCapabilities) && this.isForceSdr == displayInfo.isForceSdr && Arrays.equals(this.userDisabledHdrTypes, displayInfo.userDisabledHdrTypes) && this.minimalPostProcessingSupported == displayInfo.minimalPostProcessingSupported && this.logicalDensityDpi == displayInfo.logicalDensityDpi && this.physicalXDpi == displayInfo.physicalXDpi && this.physicalYDpi == displayInfo.physicalYDpi && this.state == displayInfo.state && this.ownerUid == displayInfo.ownerUid && Objects.equals(this.ownerPackageName, displayInfo.ownerPackageName) && this.removeMode == displayInfo.removeMode && this.brightnessMinimum == displayInfo.brightnessMinimum && this.brightnessMaximum == displayInfo.brightnessMaximum && this.brightnessDefault == displayInfo.brightnessDefault && this.brightnessDim == displayInfo.brightnessDim && Objects.equals(this.roundedCorners, displayInfo.roundedCorners) && this.installOrientation == displayInfo.installOrientation && Objects.equals(this.displayShape, displayInfo.displayShape) && Objects.equals(this.layoutLimitedRefreshRate, displayInfo.layoutLimitedRefreshRate) && BrightnessSynchronizer.floatEquals(this.hdrSdrRatio, displayInfo.hdrSdrRatio) && this.thermalRefreshRateThrottling.contentEquals(displayInfo.thermalRefreshRateThrottling) && Objects.equals(this.thermalBrightnessThrottlingDataId, displayInfo.thermalBrightnessThrottlingDataId) && this.canHostTasks == displayInfo.canHostTasks;
        if (!Flags.committedStateSeparateEvent()) {
            z2 = z2 && this.committedState == displayInfo.committedState;
        }
        return !z ? z2 && getRefreshRate() == displayInfo.getRefreshRate() && this.appVsyncOffsetNanos == displayInfo.appVsyncOffsetNanos && this.presentationDeadlineNanos == displayInfo.presentationDeadlineNanos && this.modeId == displayInfo.modeId && this.committedState == displayInfo.committedState : z2;
    }

    public void copyFrom(DisplayInfo displayInfo) {
        this.layerStack = displayInfo.layerStack;
        this.flags = displayInfo.flags;
        this.type = displayInfo.type;
        this.displayId = displayInfo.displayId;
        this.displayGroupId = displayInfo.displayGroupId;
        this.address = displayInfo.address;
        this.deviceProductInfo = displayInfo.deviceProductInfo;
        this.name = displayInfo.name;
        this.uniqueId = displayInfo.uniqueId;
        this.appWidth = displayInfo.appWidth;
        this.appHeight = displayInfo.appHeight;
        this.smallestNominalAppWidth = displayInfo.smallestNominalAppWidth;
        this.smallestNominalAppHeight = displayInfo.smallestNominalAppHeight;
        this.largestNominalAppWidth = displayInfo.largestNominalAppWidth;
        this.largestNominalAppHeight = displayInfo.largestNominalAppHeight;
        this.logicalWidth = displayInfo.logicalWidth;
        this.logicalHeight = displayInfo.logicalHeight;
        this.displayCutout = displayInfo.displayCutout;
        this.rotation = displayInfo.rotation;
        this.modeId = displayInfo.modeId;
        this.renderFrameRate = displayInfo.renderFrameRate;
        this.hasArrSupport = displayInfo.hasArrSupport;
        this.frameRateCategoryRate = displayInfo.frameRateCategoryRate;
        float[] fArr = displayInfo.supportedRefreshRates;
        this.supportedRefreshRates = Arrays.copyOf(fArr, fArr.length);
        this.defaultModeId = displayInfo.defaultModeId;
        if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
            this.refreshRateMode = displayInfo.refreshRateMode;
        }
        this.userPreferredModeId = displayInfo.userPreferredModeId;
        Display.Mode[] modeArr = displayInfo.supportedModes;
        this.supportedModes = (Display.Mode[]) Arrays.copyOf(modeArr, modeArr.length);
        Display.Mode[] modeArr2 = displayInfo.appsSupportedModes;
        this.appsSupportedModes = (Display.Mode[]) Arrays.copyOf(modeArr2, modeArr2.length);
        this.colorMode = displayInfo.colorMode;
        int[] iArr = displayInfo.supportedColorModes;
        this.supportedColorModes = Arrays.copyOf(iArr, iArr.length);
        this.hdrCapabilities = displayInfo.hdrCapabilities;
        this.isForceSdr = displayInfo.isForceSdr;
        this.userDisabledHdrTypes = displayInfo.userDisabledHdrTypes;
        this.minimalPostProcessingSupported = displayInfo.minimalPostProcessingSupported;
        this.logicalDensityDpi = displayInfo.logicalDensityDpi;
        this.physicalXDpi = displayInfo.physicalXDpi;
        this.physicalYDpi = displayInfo.physicalYDpi;
        this.appVsyncOffsetNanos = displayInfo.appVsyncOffsetNanos;
        this.presentationDeadlineNanos = displayInfo.presentationDeadlineNanos;
        this.state = displayInfo.state;
        this.committedState = displayInfo.committedState;
        this.ownerUid = displayInfo.ownerUid;
        this.ownerPackageName = displayInfo.ownerPackageName;
        this.removeMode = displayInfo.removeMode;
        this.refreshRateOverride = displayInfo.refreshRateOverride;
        this.brightnessMinimum = displayInfo.brightnessMinimum;
        this.brightnessMaximum = displayInfo.brightnessMaximum;
        this.brightnessDefault = displayInfo.brightnessDefault;
        this.brightnessDim = displayInfo.brightnessDim;
        this.roundedCorners = displayInfo.roundedCorners;
        this.installOrientation = displayInfo.installOrientation;
        this.displayShape = displayInfo.displayShape;
        this.layoutLimitedRefreshRate = displayInfo.layoutLimitedRefreshRate;
        this.hdrSdrRatio = displayInfo.hdrSdrRatio;
        this.thermalRefreshRateThrottling = displayInfo.thermalRefreshRateThrottling;
        this.thermalBrightnessThrottlingDataId = displayInfo.thermalBrightnessThrottlingDataId;
        this.canHostTasks = displayInfo.canHostTasks;
    }

    public void readFromParcel(Parcel parcel) {
        this.layerStack = parcel.readInt();
        this.flags = parcel.readInt();
        this.type = parcel.readInt();
        this.displayId = parcel.readInt();
        this.displayGroupId = parcel.readInt();
        this.address = (DisplayAddress) parcel.readParcelable(null, DisplayAddress.class);
        this.deviceProductInfo = (DeviceProductInfo) parcel.readParcelable(null, DeviceProductInfo.class);
        this.name = parcel.readString8();
        this.appWidth = parcel.readInt();
        this.appHeight = parcel.readInt();
        this.smallestNominalAppWidth = parcel.readInt();
        this.smallestNominalAppHeight = parcel.readInt();
        this.largestNominalAppWidth = parcel.readInt();
        this.largestNominalAppHeight = parcel.readInt();
        this.logicalWidth = parcel.readInt();
        this.logicalHeight = parcel.readInt();
        this.displayCutout = DisplayCutout.ParcelableWrapper.readCutoutFromParcel(parcel);
        this.rotation = parcel.readInt();
        this.modeId = parcel.readInt();
        this.renderFrameRate = parcel.readFloat();
        this.hasArrSupport = parcel.readBoolean();
        this.frameRateCategoryRate = (FrameRateCategoryRate) parcel.readParcelable(null, FrameRateCategoryRate.class);
        int readInt = parcel.readInt();
        this.supportedRefreshRates = new float[readInt];
        for (int i = 0; i < readInt; i++) {
            this.supportedRefreshRates[i] = parcel.readFloat();
        }
        this.defaultModeId = parcel.readInt();
        if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
            this.refreshRateMode = parcel.readInt();
        }
        this.userPreferredModeId = parcel.readInt();
        int readInt2 = parcel.readInt();
        this.supportedModes = new Display.Mode[readInt2];
        for (int i2 = 0; i2 < readInt2; i2++) {
            this.supportedModes[i2] = Display.Mode.CREATOR.createFromParcel(parcel);
        }
        int readInt3 = parcel.readInt();
        this.appsSupportedModes = new Display.Mode[readInt3];
        for (int i3 = 0; i3 < readInt3; i3++) {
            this.appsSupportedModes[i3] = Display.Mode.CREATOR.createFromParcel(parcel);
        }
        this.colorMode = parcel.readInt();
        int readInt4 = parcel.readInt();
        this.supportedColorModes = new int[readInt4];
        for (int i4 = 0; i4 < readInt4; i4++) {
            this.supportedColorModes[i4] = parcel.readInt();
        }
        this.hdrCapabilities = (Display.HdrCapabilities) parcel.readParcelable(null, Display.HdrCapabilities.class);
        this.isForceSdr = parcel.readBoolean();
        this.minimalPostProcessingSupported = parcel.readBoolean();
        this.logicalDensityDpi = parcel.readInt();
        this.physicalXDpi = parcel.readFloat();
        this.physicalYDpi = parcel.readFloat();
        this.appVsyncOffsetNanos = parcel.readLong();
        this.presentationDeadlineNanos = parcel.readLong();
        this.state = parcel.readInt();
        this.committedState = parcel.readInt();
        this.ownerUid = parcel.readInt();
        this.ownerPackageName = parcel.readString8();
        this.uniqueId = parcel.readString8();
        this.removeMode = parcel.readInt();
        this.refreshRateOverride = parcel.readFloat();
        this.brightnessMinimum = parcel.readFloat();
        this.brightnessMaximum = parcel.readFloat();
        this.brightnessDefault = parcel.readFloat();
        this.brightnessDim = parcel.readFloat();
        this.roundedCorners = (RoundedCorners) parcel.readTypedObject(RoundedCorners.CREATOR);
        int readInt5 = parcel.readInt();
        this.userDisabledHdrTypes = new int[readInt5];
        for (int i5 = 0; i5 < readInt5; i5++) {
            this.userDisabledHdrTypes[i5] = parcel.readInt();
        }
        this.installOrientation = parcel.readInt();
        this.displayShape = (DisplayShape) parcel.readTypedObject(DisplayShape.CREATOR);
        this.layoutLimitedRefreshRate = (SurfaceControl.RefreshRateRange) parcel.readTypedObject(SurfaceControl.RefreshRateRange.CREATOR);
        this.hdrSdrRatio = parcel.readFloat();
        this.thermalRefreshRateThrottling = parcel.readSparseArray(null, SurfaceControl.RefreshRateRange.class);
        this.thermalBrightnessThrottlingDataId = parcel.readString8();
        this.canHostTasks = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.layerStack);
        parcel.writeInt(this.flags);
        parcel.writeInt(this.type);
        parcel.writeInt(this.displayId);
        parcel.writeInt(this.displayGroupId);
        parcel.writeParcelable(this.address, i);
        parcel.writeParcelable(this.deviceProductInfo, i);
        parcel.writeString8(this.name);
        parcel.writeInt(this.appWidth);
        parcel.writeInt(this.appHeight);
        parcel.writeInt(this.smallestNominalAppWidth);
        parcel.writeInt(this.smallestNominalAppHeight);
        parcel.writeInt(this.largestNominalAppWidth);
        parcel.writeInt(this.largestNominalAppHeight);
        parcel.writeInt(this.logicalWidth);
        parcel.writeInt(this.logicalHeight);
        DisplayCutout.ParcelableWrapper.writeCutoutToParcel(this.displayCutout, parcel, i);
        parcel.writeInt(this.rotation);
        parcel.writeInt(this.modeId);
        parcel.writeFloat(this.renderFrameRate);
        parcel.writeBoolean(this.hasArrSupport);
        parcel.writeParcelable(this.frameRateCategoryRate, i);
        parcel.writeInt(this.supportedRefreshRates.length);
        int i2 = 0;
        for (float f : this.supportedRefreshRates) {
            parcel.writeFloat(f);
        }
        parcel.writeInt(this.defaultModeId);
        if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
            parcel.writeInt(this.refreshRateMode);
        }
        parcel.writeInt(this.userPreferredModeId);
        parcel.writeInt(this.supportedModes.length);
        int i3 = 0;
        while (true) {
            Display.Mode[] modeArr = this.supportedModes;
            if (i3 >= modeArr.length) {
                break;
            }
            modeArr[i3].writeToParcel(parcel, i);
            i3++;
        }
        parcel.writeInt(this.appsSupportedModes.length);
        int i4 = 0;
        while (true) {
            Display.Mode[] modeArr2 = this.appsSupportedModes;
            if (i4 >= modeArr2.length) {
                break;
            }
            modeArr2[i4].writeToParcel(parcel, i);
            i4++;
        }
        parcel.writeInt(this.colorMode);
        parcel.writeInt(this.supportedColorModes.length);
        int i5 = 0;
        while (true) {
            int[] iArr = this.supportedColorModes;
            if (i5 >= iArr.length) {
                break;
            }
            parcel.writeInt(iArr[i5]);
            i5++;
        }
        parcel.writeParcelable(this.hdrCapabilities, i);
        parcel.writeBoolean(this.isForceSdr);
        parcel.writeBoolean(this.minimalPostProcessingSupported);
        parcel.writeInt(this.logicalDensityDpi);
        parcel.writeFloat(this.physicalXDpi);
        parcel.writeFloat(this.physicalYDpi);
        parcel.writeLong(this.appVsyncOffsetNanos);
        parcel.writeLong(this.presentationDeadlineNanos);
        parcel.writeInt(this.state);
        parcel.writeInt(this.committedState);
        parcel.writeInt(this.ownerUid);
        parcel.writeString8(this.ownerPackageName);
        parcel.writeString8(this.uniqueId);
        parcel.writeInt(this.removeMode);
        parcel.writeFloat(this.refreshRateOverride);
        parcel.writeFloat(this.brightnessMinimum);
        parcel.writeFloat(this.brightnessMaximum);
        parcel.writeFloat(this.brightnessDefault);
        parcel.writeFloat(this.brightnessDim);
        parcel.writeTypedObject(this.roundedCorners, i);
        parcel.writeInt(this.userDisabledHdrTypes.length);
        while (true) {
            int[] iArr2 = this.userDisabledHdrTypes;
            if (i2 < iArr2.length) {
                parcel.writeInt(iArr2[i2]);
                i2++;
            } else {
                parcel.writeInt(this.installOrientation);
                parcel.writeTypedObject(this.displayShape, i);
                parcel.writeTypedObject(this.layoutLimitedRefreshRate, i);
                parcel.writeFloat(this.hdrSdrRatio);
                parcel.writeSparseArray(this.thermalRefreshRateThrottling);
                parcel.writeString8(this.thermalBrightnessThrottlingDataId);
                parcel.writeBoolean(this.canHostTasks);
                return;
            }
        }
    }

    public float getRefreshRate() {
        float f = this.refreshRateOverride;
        if (f > 0.0f) {
            return f;
        }
        float f2 = this.renderFrameRate;
        if (f2 > 0.0f) {
            return f2;
        }
        if (this.supportedModes.length == 0) {
            return 0.0f;
        }
        return getMode().getRefreshRate();
    }

    public Display.Mode getMode() {
        return findMode(this.modeId);
    }

    public Display.Mode getDefaultMode() {
        return findMode(this.defaultModeId);
    }

    private Display.Mode findMode(int i) {
        int i2 = 0;
        while (true) {
            Display.Mode[] modeArr = this.supportedModes;
            if (i2 < modeArr.length) {
                if (modeArr[i2].getModeId() == i) {
                    return this.supportedModes[i2];
                }
                i2++;
            } else {
                throw new IllegalStateException("Unable to locate mode id=" + i + ",supportedModes=" + Arrays.toString(this.supportedModes));
            }
        }
    }

    public Display.Mode findDefaultModeByRefreshRate(float f) {
        Display.Mode[] modeArr = this.appsSupportedModes;
        Display.Mode defaultMode = getDefaultMode();
        for (int i = 0; i < modeArr.length; i++) {
            if (modeArr[i].matches(defaultMode.getPhysicalWidth(), defaultMode.getPhysicalHeight(), f)) {
                return modeArr[i];
            }
        }
        return null;
    }

    public float[] getDefaultRefreshRates() {
        float[] fArr = this.supportedRefreshRates;
        if (fArr.length == 0) {
            return getDefaultRefreshRatesLegacy();
        }
        return Arrays.copyOf(fArr, fArr.length);
    }

    public float[] getDefaultRefreshRatesLegacy() {
        return getDefaultRefreshRatesLegacy(this.appsSupportedModes);
    }

    public float[] getDefaultRefreshRatesLegacy(Display.Mode[] modeArr) {
        ArraySet arraySet = new ArraySet();
        Display.Mode defaultMode = getDefaultMode();
        int i = 0;
        for (Display.Mode mode : modeArr) {
            if (mode.getPhysicalWidth() == defaultMode.getPhysicalWidth() && mode.getPhysicalHeight() == defaultMode.getPhysicalHeight()) {
                arraySet.add(Float.valueOf(mode.getRefreshRate()));
            }
        }
        float[] fArr = new float[arraySet.size()];
        Iterator it = arraySet.iterator();
        while (it.hasNext()) {
            fArr[i] = ((Float) it.next()).floatValue();
            i++;
        }
        return fArr;
    }

    public void getAppMetrics(DisplayMetrics displayMetrics) {
        getAppMetrics(displayMetrics, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, null);
    }

    public void getAppMetrics(DisplayMetrics displayMetrics, DisplayAdjustments displayAdjustments) {
        getMetricsWithSize(displayMetrics, displayAdjustments.getCompatibilityInfo(), displayAdjustments.getConfiguration(), this.appWidth, this.appHeight);
    }

    public void getAppMetrics(DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo, Configuration configuration) {
        getMetricsWithSize(displayMetrics, compatibilityInfo, configuration, this.appWidth, this.appHeight);
    }

    public void getLogicalMetrics(DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo, Configuration configuration) {
        getMetricsWithSize(displayMetrics, compatibilityInfo, configuration, this.logicalWidth, this.logicalHeight);
    }

    public void getMaxBoundsMetrics(DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo, Configuration configuration) {
        Rect maxBounds = configuration.windowConfiguration.getMaxBounds();
        getMetricsWithSize(displayMetrics, compatibilityInfo, null, maxBounds.width(), maxBounds.height());
    }

    public int getNaturalWidth() {
        int i = this.rotation;
        return (i == 0 || i == 2) ? this.logicalWidth : this.logicalHeight;
    }

    public int getNaturalHeight() {
        int i = this.rotation;
        return (i == 0 || i == 2) ? this.logicalHeight : this.logicalWidth;
    }

    public boolean isHdr() {
        Display.HdrCapabilities hdrCapabilities = this.hdrCapabilities;
        int[] supportedHdrTypes = hdrCapabilities != null ? hdrCapabilities.getSupportedHdrTypes() : null;
        return supportedHdrTypes != null && supportedHdrTypes.length > 0;
    }

    public boolean isWideColorGamut() {
        for (int i : this.supportedColorModes) {
            if (i == 6 || i > 7) {
                return true;
            }
        }
        return false;
    }

    public boolean hasAccess(int i) {
        return Display.hasAccess(i, this.flags, this.ownerUid, this.displayId);
    }

    private void getMetricsWithSize(DisplayMetrics displayMetrics, CompatibilityInfo compatibilityInfo, Configuration configuration, int i, int i2) {
        int i3 = this.logicalDensityDpi;
        displayMetrics.noncompatDensityDpi = i3;
        displayMetrics.densityDpi = i3;
        float f = this.logicalDensityDpi * 0.00625f;
        displayMetrics.noncompatDensity = f;
        displayMetrics.density = f;
        float f2 = displayMetrics.density;
        displayMetrics.noncompatScaledDensity = f2;
        displayMetrics.scaledDensity = f2;
        float f3 = this.physicalXDpi;
        displayMetrics.noncompatXdpi = f3;
        displayMetrics.xdpi = f3;
        float f4 = this.physicalYDpi;
        displayMetrics.noncompatYdpi = f4;
        displayMetrics.ydpi = f4;
        Rect appBounds = configuration != null ? configuration.windowConfiguration.getAppBounds() : null;
        if (appBounds != null) {
            i = appBounds.width();
        }
        if (appBounds != null) {
            i2 = appBounds.height();
        }
        displayMetrics.widthPixels = i;
        displayMetrics.noncompatWidthPixels = i;
        displayMetrics.heightPixels = i2;
        displayMetrics.noncompatHeightPixels = i2;
        compatibilityInfo.applyDisplayMetricsIfNeeded(displayMetrics, configuration != null && appBounds == null);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("DisplayInfo{\"");
        sb.append(this.name);
        sb.append("\", displayId ");
        sb.append(this.displayId);
        sb.append(", displayGroupId ");
        sb.append(this.displayGroupId);
        sb.append(flagsToString(this.flags));
        sb.append(", real ");
        sb.append(this.logicalWidth);
        sb.append(" x ");
        sb.append(this.logicalHeight);
        sb.append(", largest app ");
        sb.append(this.largestNominalAppWidth);
        sb.append(" x ");
        sb.append(this.largestNominalAppHeight);
        sb.append(", smallest app ");
        sb.append(this.smallestNominalAppWidth);
        sb.append(" x ");
        sb.append(this.smallestNominalAppHeight);
        sb.append(", appVsyncOff ");
        sb.append(this.appVsyncOffsetNanos);
        sb.append(", presDeadline ");
        sb.append(this.presentationDeadlineNanos);
        sb.append(", mode ");
        sb.append(this.modeId);
        sb.append(", renderFrameRate ");
        sb.append(this.renderFrameRate);
        sb.append(", hasArrSupport ");
        sb.append(this.hasArrSupport);
        sb.append(", frameRateCategoryRate ");
        sb.append(this.frameRateCategoryRate);
        sb.append(", supportedRefreshRates ");
        sb.append(Arrays.toString(this.supportedRefreshRates));
        sb.append(", defaultMode ");
        sb.append(this.defaultModeId);
        if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
            sb.append(", ");
            sb.append(Settings.Secure.refreshRateModeToString(this.refreshRateMode));
        }
        sb.append(", userPreferredModeId ");
        sb.append(this.userPreferredModeId);
        sb.append(", supportedModes ");
        sb.append(Arrays.toString(this.supportedModes));
        sb.append(", appsSupportedModes ");
        sb.append(Arrays.toString(this.appsSupportedModes));
        sb.append(", hdrCapabilities ");
        sb.append(this.hdrCapabilities);
        sb.append(", isForceSdr ");
        sb.append(this.isForceSdr);
        sb.append(", userDisabledHdrTypes ");
        sb.append(Arrays.toString(this.userDisabledHdrTypes));
        sb.append(", minimalPostProcessingSupported ");
        sb.append(this.minimalPostProcessingSupported);
        sb.append(", rotation ");
        sb.append(this.rotation);
        sb.append(", state ");
        sb.append(Display.stateToString(this.state));
        sb.append(", committedState ");
        sb.append(Display.stateToString(this.committedState));
        if (Process.myUid() != 1000) {
            sb.append("}");
            return sb.toString();
        }
        sb.append(", type ");
        sb.append(Display.typeToString(this.type));
        sb.append(", uniqueId \"");
        sb.append(this.uniqueId);
        sb.append("\", app ");
        sb.append(this.appWidth);
        sb.append(" x ");
        sb.append(this.appHeight);
        sb.append(", density ");
        sb.append(this.logicalDensityDpi);
        sb.append(" (");
        sb.append(this.physicalXDpi);
        sb.append(" x ");
        sb.append(this.physicalYDpi);
        sb.append(") dpi, layerStack ");
        sb.append(this.layerStack);
        sb.append(", colorMode ");
        sb.append(this.colorMode);
        sb.append(", supportedColorModes ");
        sb.append(Arrays.toString(this.supportedColorModes));
        if (this.address != null) {
            sb.append(", address ");
            sb.append(this.address);
        }
        sb.append(", deviceProductInfo ");
        sb.append(this.deviceProductInfo);
        if (this.ownerUid != 0 || this.ownerPackageName != null) {
            sb.append(", owner ");
            sb.append(this.ownerPackageName);
            sb.append(" (uid ");
            sb.append(this.ownerUid);
            sb.append(NavigationBarInflaterView.KEY_CODE_END);
        }
        sb.append(", removeMode ");
        sb.append(this.removeMode);
        sb.append(", refreshRateOverride ");
        sb.append(this.refreshRateOverride);
        sb.append(", brightnessMinimum ");
        sb.append(this.brightnessMinimum);
        sb.append(", brightnessMaximum ");
        sb.append(this.brightnessMaximum);
        sb.append(", brightnessDefault ");
        sb.append(this.brightnessDefault);
        sb.append(", brightnessDim ");
        sb.append(this.brightnessDim);
        sb.append(", installOrientation ");
        sb.append(Surface.rotationToString(this.installOrientation));
        sb.append(", layoutLimitedRefreshRate ");
        sb.append(this.layoutLimitedRefreshRate);
        sb.append(", hdrSdrRatio ");
        if (Float.isNaN(this.hdrSdrRatio)) {
            sb.append("not_available");
        } else {
            sb.append(this.hdrSdrRatio);
        }
        sb.append(", thermalRefreshRateThrottling ");
        sb.append(this.thermalRefreshRateThrottling);
        sb.append(", thermalBrightnessThrottlingDataId ");
        sb.append(this.thermalBrightnessThrottlingDataId);
        sb.append(", canHostTasks ");
        sb.append(this.canHostTasks);
        sb.append("}");
        return sb.toString();
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.logicalWidth);
        protoOutputStream.write(1120986464258L, this.logicalHeight);
        protoOutputStream.write(1120986464259L, this.appWidth);
        protoOutputStream.write(1120986464260L, this.appHeight);
        protoOutputStream.write(1138166333445L, this.name);
        protoOutputStream.write(1120986464262L, this.flags);
        protoOutputStream.write(1120986464264L, this.type);
        DisplayCutout displayCutout = this.displayCutout;
        if (displayCutout != null) {
            displayCutout.dumpDebug(protoOutputStream, 1146756268039L);
        }
        protoOutputStream.end(start);
    }

    private static String flagsToString(int i) {
        StringBuilder sb = new StringBuilder();
        if ((i & 2) != 0) {
            sb.append(", FLAG_SECURE");
        }
        if ((i & 1) != 0) {
            sb.append(", FLAG_SUPPORTS_PROTECTED_BUFFERS");
        }
        if ((i & 4) != 0) {
            sb.append(", FLAG_PRIVATE");
        }
        if ((i & 8) != 0) {
            sb.append(", FLAG_PRESENTATION");
        }
        if ((1073741824 & i) != 0) {
            sb.append(", FLAG_SCALING_DISABLED");
        }
        if ((i & 16) != 0) {
            sb.append(", FLAG_ROUND");
        }
        if ((i & 32) != 0) {
            sb.append(", FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD");
        }
        if ((i & 64) != 0) {
            sb.append(", FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS");
        }
        if ((i & 128) != 0) {
            sb.append(", FLAG_TRUSTED");
        }
        if ((i & 256) != 0) {
            sb.append(", FLAG_OWN_DISPLAY_GROUP");
        }
        if ((i & 512) != 0) {
            sb.append(", FLAG_ALWAYS_UNLOCKED");
        }
        if ((i & 1024) != 0) {
            sb.append(", FLAG_TOUCH_FEEDBACK_DISABLED");
        }
        if ((i & 2048) != 0) {
            sb.append(", FLAG_OWN_FOCUS");
        }
        if ((524288 & i) != 0) {
            sb.append(", FLAG_VIEW_COVER_DISPLAY");
        }
        if ((2097152 & i) != 0) {
            sb.append(", FLAG_REMOTE_APP_DISPLAY");
        }
        if ((131072 & i) != 0) {
            sb.append(", FLAG_EXTERNAL_DEX_HOSTING");
        }
        if (CoreRune.SYSFW_APP_SPEG && (65536 & i) != 0) {
            sb.append(", FLAG_SPEG_DISPLAY");
        }
        if ((33554432 & i) != 0) {
            sb.append(", FLAG_HIDDEN_SPACE_DISPLAY");
        }
        if ((67108864 & i) != 0) {
            sb.append(", FLAG_WIRELESS_DEX_DISPLAY");
        }
        if ((134217728 & i) != 0) {
            sb.append(", FLAG_PC_DEX_DISPLAY");
        }
        if ((268435456 & i) != 0) {
            sb.append(", FLAG_WIFI_DISPLAY");
        }
        if ((536870912 & i) != 0) {
            sb.append(", FLAG_NO_LOCK_PRESENTATION");
        }
        if (CoreRune.BAIDU_CARLIFE && (1048576 & i) != 0) {
            sb.append(", FLAG_CARLIFE_DISPLAY");
        }
        if ((i & 8192) != 0) {
            sb.append(", FLAG_REAR_DISPLAY");
        }
        if ((i & 32768) != 0) {
            sb.append(", FLAG_ALLOWS_CONTENT_MODE_SWITCH");
        }
        return sb.toString();
    }
}
