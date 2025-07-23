package android.view;

import android.app.ActivityThread;
import android.content.res.CompatibilityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.ColorSpace;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.OverlayProperties;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DeviceProductInfo;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.hardware.graphics.common.DisplayDecorationSupport;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.security.Credentials;
import android.util.ArraySet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import com.samsung.android.hardware.display.RefreshRateConfig;
import com.samsung.android.rune.CoreRune;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public final class Display {
    private static final int CACHED_APP_SIZE_DURATION_MILLIS = 20;
    public static final int CARLIFE_DISPLAY_GROUP = 4;
    public static final int COLOR_MODE_ADOBE_RGB = 8;
    public static final int COLOR_MODE_BT601_525 = 3;
    public static final int COLOR_MODE_BT601_525_UNADJUSTED = 4;
    public static final int COLOR_MODE_BT601_625 = 1;
    public static final int COLOR_MODE_BT601_625_UNADJUSTED = 2;
    public static final int COLOR_MODE_BT709 = 5;
    public static final int COLOR_MODE_DCI_P3 = 6;
    public static final int COLOR_MODE_DEFAULT = 0;
    public static final int COLOR_MODE_DISPLAY_P3 = 9;
    public static final int COLOR_MODE_INVALID = -1;
    public static final int COLOR_MODE_SRGB = 7;
    private static final boolean DEBUG = false;
    public static final int DEFAULT_DISPLAY = 0;
    public static final int DEFAULT_DISPLAY_GROUP = 0;
    public static final int DEX_DISPLAY = 2;
    public static final int DEX_DISPLAY_GROUP = 2;
    public static final int DISPLAY_MODE_ID_FOR_FRAME_RATE_OVERRIDE = 255;
    public static final int EXTRA_BUILT_IN_DISPLAY = 1;
    public static final int FLAG_ALLOWS_CONTENT_MODE_SWITCH = 32768;
    public static final int FLAG_ALWAYS_UNLOCKED = 512;
    public static int FLAG_BACKGROUND_DISPLAY = Integer.MIN_VALUE;
    public static final int FLAG_CAN_SHOW_WITH_INSECURE_KEYGUARD = 32;
    public static final int FLAG_CARLIFE_DISPLAY = 1048576;
    public static final int FLAG_EXTERNAL_DEX_HOSTING = 131072;
    public static final int FLAG_EXTRA_BUILT_IN_DISPLAY = 262144;
    public static final int FLAG_HIDDEN_SPACE_DISPLAY = 33554432;
    public static final int FLAG_NO_LOCK_PRESENTATION = 536870912;
    public static final int FLAG_OWN_DISPLAY_GROUP = 256;
    public static final int FLAG_OWN_FOCUS = 2048;
    public static final int FLAG_PC_DEX_DISPLAY = 134217728;
    public static final int FLAG_PRESENTATION = 8;
    public static final int FLAG_PRIVATE = 4;
    public static final int FLAG_REAR = 8192;
    public static final int FLAG_REMOTE_APP_DISPLAY = 2097152;
    public static final int FLAG_ROTATES_WITH_CONTENT = 16384;
    public static final int FLAG_ROUND = 16;
    public static final int FLAG_SCALING_DISABLED = 1073741824;
    public static final int FLAG_SECURE = 2;
    public static final int FLAG_SHOULD_SHOW_SYSTEM_DECORATIONS = 64;
    public static final int FLAG_SPEG_DISPLAY = 65536;
    public static final int FLAG_STEAL_TOP_FOCUS_DISABLED = 4096;
    public static final int FLAG_SUPPORTS_PROTECTED_BUFFERS = 1;
    public static final int FLAG_TOUCH_FEEDBACK_DISABLED = 1024;
    public static final int FLAG_TRUSTED = 128;
    public static final int FLAG_VIEW_COVER_DISPLAY = 524288;
    public static final int FLAG_WIFI_DISPLAY = 268435456;
    public static final int FLAG_WIRELESS_DEX_DISPLAY = 67108864;
    public static final int FRAME_RATE_CATEGORY_HIGH = 1;
    public static final int FRAME_RATE_CATEGORY_NORMAL = 0;
    public static final int INVALID_DISPLAY = -1;
    public static final int INVALID_DISPLAY_GROUP = -1;
    public static final int INVALID_DISPLAY_HEIGHT = -1;
    public static final float INVALID_DISPLAY_REFRESH_RATE = 0.0f;
    public static final int INVALID_DISPLAY_WIDTH = -1;
    public static final int MAX_STATIC_DISPLAY = 4;
    public static final int MAX_STATIC_DISPLAY_GROUP = 4;
    public static final int REMOVE_MODE_DESTROY_CONTENT = 1;
    public static final int REMOVE_MODE_MOVE_CONTENT_TO_PRIMARY = 0;
    public static final int SEM_TYPE_EXTERNAL = 2;
    public static final int STATE_DOZE = 3;
    public static final int STATE_DOZE_SUSPEND = 4;
    public static final int STATE_OFF = 1;
    public static final int STATE_ON = 2;
    public static final int STATE_ON_SUSPEND = 6;
    public static final int STATE_REASON_DEFAULT_POLICY = 1;
    public static final int STATE_REASON_DRAW_WAKE_LOCK = 2;
    public static final int STATE_REASON_DREAM_MANAGER = 5;
    public static final int STATE_REASON_KEY = 6;
    public static final int STATE_REASON_MOTION = 7;
    public static final int STATE_REASON_OFFLOAD = 3;
    public static final int STATE_REASON_TILT = 4;
    public static final int STATE_REASON_UNKNOWN = 0;
    public static final int STATE_UNKNOWN = 0;
    public static final int STATE_VR = 5;
    private static final String TAG = "Display";
    public static final int TYPE_EXTERNAL = 2;
    public static final int TYPE_INTERNAL = 1;
    public static final int TYPE_MAX = 5;
    public static final int TYPE_OVERLAY = 4;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_VIRTUAL = 5;
    public static final int TYPE_WIFI = 3;
    public static final int VIEW_COVER_DISPLAY = 4;
    private int mCachedAppHeightCompat;
    private int mCachedAppWidthCompat;
    private DisplayAdjustments mDisplayAdjustments;
    private final int mDisplayId;
    private DisplayInfo mDisplayInfo;
    private final int mFlags;
    private final DisplayManagerGlobal mGlobal;
    private ArrayList<HdrSdrRatioListenerWrapper> mHdrSdrRatioListeners;
    private boolean mIsValid;
    private long mLastCachedAppSizeUpdate;
    private final Object mLock;
    private final String mOwnerPackageName;
    private final int mOwnerUid;
    private boolean mRefreshRateChangesRegistered;
    private final Resources mResources;
    private final DisplayMetrics mTempMetrics;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ColorMode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface FrameRateCategory {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StateReason {
    }

    public static boolean isActiveState(int i) {
        return i == 2 || i == 5;
    }

    public static boolean isDozeState(int i) {
        return i == 3 || i == 4;
    }

    public static boolean isHeightValid(int i) {
        return i > 0;
    }

    public static boolean isOffState(int i) {
        return i == 1;
    }

    public static boolean isOnState(int i) {
        return i == 2 || i == 5 || i == 6;
    }

    public static boolean isRefreshRateValid(float f) {
        return f > 0.0f;
    }

    public static boolean isSuspendedState(int i) {
        return i == 1 || i == 4 || i == 6;
    }

    public static boolean isWidthValid(int i) {
        return i > 0;
    }

    @Deprecated
    public int getPixelFormat() {
        return 1;
    }

    public Display(DisplayManagerGlobal displayManagerGlobal, int i, DisplayInfo displayInfo, DisplayAdjustments displayAdjustments) {
        this(displayManagerGlobal, i, displayInfo, displayAdjustments, null);
    }

    public Display(DisplayManagerGlobal displayManagerGlobal, int i, DisplayInfo displayInfo, Resources resources) {
        this(displayManagerGlobal, i, displayInfo, null, resources);
    }

    private Display(DisplayManagerGlobal displayManagerGlobal, int i, DisplayInfo displayInfo, DisplayAdjustments displayAdjustments, Resources resources) {
        DisplayAdjustments displayAdjustments2;
        this.mLock = new Object();
        this.mTempMetrics = new DisplayMetrics();
        this.mHdrSdrRatioListeners = new ArrayList<>();
        this.mGlobal = displayManagerGlobal;
        this.mDisplayId = i;
        this.mDisplayInfo = displayInfo;
        this.mResources = resources;
        if (resources != null) {
            displayAdjustments2 = new DisplayAdjustments(resources.getConfiguration());
        } else {
            displayAdjustments2 = displayAdjustments != null ? new DisplayAdjustments(displayAdjustments) : new DisplayAdjustments();
        }
        this.mDisplayAdjustments = displayAdjustments2;
        this.mIsValid = true;
        this.mFlags = displayInfo.flags;
        this.mType = displayInfo.type;
        this.mOwnerUid = displayInfo.ownerUid;
        this.mOwnerPackageName = displayInfo.ownerPackageName;
    }

    public int getDisplayId() {
        return this.mDisplayId;
    }

    public String getUniqueId() {
        return this.mDisplayInfo.uniqueId;
    }

    public boolean isValid() {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            z = this.mIsValid;
        }
        return z;
    }

    public boolean getDisplayInfo(DisplayInfo displayInfo) {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            displayInfo.copyFrom(this.mDisplayInfo);
            z = this.mIsValid;
        }
        return z;
    }

    public int getLayerStack() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mDisplayInfo.layerStack;
        }
        return i;
    }

    public int getFlags() {
        int i = this.mType;
        if (i == 2 || i == 4 || i == 3) {
            synchronized (this.mLock) {
                updateDisplayInfoLocked();
            }
        }
        boolean z = (this.mFlags & 131072) != 0;
        boolean isExternalDesktopDisplay = DisplayManager.isExternalDesktopDisplay(this.mDisplayInfo);
        if (!z && isExternalDesktopDisplay) {
            return this.mFlags | 131072;
        }
        if (z && !isExternalDesktopDisplay) {
            return this.mFlags & (-131073);
        }
        return this.mFlags;
    }

    public int getType() {
        return this.mType;
    }

    public int semGetType() {
        return getType();
    }

    public DisplayAddress getAddress() {
        DisplayAddress displayAddress;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            displayAddress = this.mDisplayInfo.address;
        }
        return displayAddress;
    }

    public int getOwnerUid() {
        return this.mOwnerUid;
    }

    public String getOwnerPackageName() {
        return this.mOwnerPackageName;
    }

    public DisplayAdjustments getDisplayAdjustments() {
        Resources resources = this.mResources;
        if (resources != null) {
            DisplayAdjustments displayAdjustments = resources.getDisplayAdjustments();
            if (!this.mDisplayAdjustments.equals(displayAdjustments)) {
                this.mDisplayAdjustments = new DisplayAdjustments(displayAdjustments);
            }
        }
        return this.mDisplayAdjustments;
    }

    public String getName() {
        String str;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            str = this.mDisplayInfo.name;
        }
        return str;
    }

    public float getBrightnessDefault() {
        float f;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            f = this.mDisplayInfo.brightnessDefault;
        }
        return f;
    }

    public BrightnessInfo getBrightnessInfo() {
        return this.mGlobal.getBrightnessInfo(this.mDisplayId);
    }

    @Deprecated
    public void getSize(Point point) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
            point.x = this.mTempMetrics.widthPixels;
            point.y = this.mTempMetrics.heightPixels;
        }
    }

    @Deprecated
    public void getRectSize(Rect rect) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
            rect.set(0, 0, this.mTempMetrics.widthPixels, this.mTempMetrics.heightPixels);
        }
    }

    public void getCurrentSizeRange(Point point, Point point2) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            point.x = this.mDisplayInfo.smallestNominalAppWidth;
            point.y = this.mDisplayInfo.smallestNominalAppHeight;
            point2.x = this.mDisplayInfo.largestNominalAppWidth;
            point2.y = this.mDisplayInfo.largestNominalAppHeight;
        }
    }

    public int getMaximumSizeDimension() {
        int max;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            max = Math.max(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
        }
        return max;
    }

    public float getMinSizeDimensionDp() {
        float deriveDimension;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics);
            deriveDimension = TypedValue.deriveDimension(1, Math.min(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight), this.mTempMetrics);
        }
        return deriveDimension;
    }

    @Deprecated
    public int getWidth() {
        int i;
        synchronized (this.mLock) {
            updateCachedAppSizeIfNeededLocked();
            i = this.mCachedAppWidthCompat;
        }
        return i;
    }

    @Deprecated
    public int getHeight() {
        int i;
        synchronized (this.mLock) {
            updateCachedAppSizeIfNeededLocked();
            i = this.mCachedAppHeightCompat;
        }
        return i;
    }

    public int getRotation() {
        int localRotation;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            localRotation = getLocalRotation();
        }
        return localRotation;
    }

    public int getInstallOrientation() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mDisplayInfo.installOrientation;
        }
        return i;
    }

    @Deprecated
    public int getOrientation() {
        return getRotation();
    }

    public int getRealRotation() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mDisplayInfo.rotation;
        }
        return i;
    }

    public DisplayCutout getCutout() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (this.mResources == null) {
                return this.mDisplayInfo.displayCutout;
            }
            DisplayCutout displayCutout = this.mDisplayInfo.displayCutout;
            if (displayCutout == null) {
                return null;
            }
            int localRotation = getLocalRotation();
            if (localRotation == this.mDisplayInfo.rotation) {
                return displayCutout;
            }
            return displayCutout.getRotated(this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight, this.mDisplayInfo.rotation, localRotation);
        }
    }

    public RoundedCorner getRoundedCorner(int i) {
        RoundedCorner roundedCorner;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            RoundedCorners roundedCorners = this.mDisplayInfo.roundedCorners;
            int localRotation = getLocalRotation();
            if (roundedCorners != null && localRotation != this.mDisplayInfo.rotation) {
                roundedCorners.rotate(localRotation, this.mDisplayInfo.logicalWidth, this.mDisplayInfo.logicalHeight);
            }
            roundedCorner = roundedCorners == null ? null : roundedCorners.getRoundedCorner(i);
        }
        return roundedCorner;
    }

    public DisplayShape getShape() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            DisplayShape displayShape = this.mDisplayInfo.displayShape;
            int localRotation = getLocalRotation();
            if (displayShape == null || localRotation == this.mDisplayInfo.rotation) {
                return displayShape;
            }
            return displayShape.setRotation(localRotation);
        }
    }

    public float getRefreshRate() {
        float refreshRate;
        synchronized (this.mLock) {
            if (!this.mRefreshRateChangesRegistered) {
                DisplayManagerGlobal.getInstance().registerForRefreshRateChanges();
                this.mRefreshRateChangesRegistered = true;
            }
            updateDisplayInfoLocked();
            refreshRate = this.mDisplayInfo.getRefreshRate();
        }
        return refreshRate;
    }

    public float[] getSupportedRefreshRates() {
        float[] defaultRefreshRates;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            defaultRefreshRates = this.mDisplayInfo.getDefaultRefreshRates();
            Objects.requireNonNull(defaultRefreshRates);
        }
        return defaultRefreshRates;
    }

    public float[] getSupportedRefreshRatesLegacy() {
        float[] defaultRefreshRatesLegacy;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            defaultRefreshRatesLegacy = this.mDisplayInfo.getDefaultRefreshRatesLegacy();
            Objects.requireNonNull(defaultRefreshRatesLegacy);
        }
        return defaultRefreshRatesLegacy;
    }

    public Mode getMode() {
        Mode mode;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            mode = this.mDisplayInfo.getMode();
        }
        return mode;
    }

    public Mode getDefaultMode() {
        Mode defaultMode;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            defaultMode = this.mDisplayInfo.getDefaultMode();
        }
        return defaultMode;
    }

    public Mode[] getSupportedModes() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
                return filteredSupportedModes(this.mDisplayInfo.appsSupportedModes);
            }
            Mode[] modeArr = this.mDisplayInfo.appsSupportedModes;
            return (Mode[]) Arrays.copyOf(modeArr, modeArr.length);
        }
    }

    public Mode[] getSystemSupportedModes() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (CoreRune.FW_VRR_REFRESH_RATE_MODE) {
                return filteredSupportedModes(this.mDisplayInfo.supportedModes);
            }
            Mode[] modeArr = this.mDisplayInfo.supportedModes;
            return (Mode[]) Arrays.copyOf(modeArr, modeArr.length);
        }
    }

    private Mode[] filteredSupportedModes(Mode[] modeArr) {
        if (this.mType != 1) {
            return (Mode[]) Arrays.copyOf(modeArr, modeArr.length);
        }
        final RefreshRateConfig refreshRateConfig = RefreshRateConfig.getInstance(getDisplayId());
        final Mode mode = this.mDisplayInfo.getMode();
        Stream stream = Arrays.stream(modeArr);
        if (this.mDisplayInfo.refreshRateMode == 0) {
            if (refreshRateConfig.isSwitchable()) {
                return (Mode[]) stream.filter(new Predicate() { // from class: android.view.Display$$ExternalSyntheticLambda0
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj) {
                        return Display.lambda$filteredSupportedModes$0(RefreshRateConfig.this, mode, (Display.Mode) obj);
                    }
                }).toArray(new IntFunction() { // from class: android.view.Display$$ExternalSyntheticLambda1
                    @Override // java.util.function.IntFunction
                    public final Object apply(int i) {
                        return Display.lambda$filteredSupportedModes$1(i);
                    }
                });
            }
            return (Mode[]) stream.filter(new Predicate() { // from class: android.view.Display$$ExternalSyntheticLambda2
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Display.lambda$filteredSupportedModes$2(RefreshRateConfig.this, (Display.Mode) obj);
                }
            }).toArray(new IntFunction() { // from class: android.view.Display$$ExternalSyntheticLambda3
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return Display.lambda$filteredSupportedModes$3(i);
                }
            });
        }
        if (this.mDisplayInfo.refreshRateMode == 2) {
            return (Mode[]) stream.filter(new Predicate() { // from class: android.view.Display$$ExternalSyntheticLambda4
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Display.lambda$filteredSupportedModes$4(RefreshRateConfig.this, mode, (Display.Mode) obj);
                }
            }).toArray(new IntFunction() { // from class: android.view.Display$$ExternalSyntheticLambda5
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return Display.lambda$filteredSupportedModes$5(i);
                }
            });
        }
        if (this.mDisplayInfo.refreshRateMode == 1) {
            return (Mode[]) stream.filter(new Predicate() { // from class: android.view.Display$$ExternalSyntheticLambda6
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return Display.lambda$filteredSupportedModes$6(RefreshRateConfig.this, (Display.Mode) obj);
                }
            }).toArray(new IntFunction() { // from class: android.view.Display$$ExternalSyntheticLambda7
                @Override // java.util.function.IntFunction
                public final Object apply(int i) {
                    return Display.lambda$filteredSupportedModes$7(i);
                }
            });
        }
        return (Mode[]) Arrays.copyOf(modeArr, modeArr.length);
    }

    static /* synthetic */ boolean lambda$filteredSupportedModes$0(RefreshRateConfig refreshRateConfig, Mode mode, Mode mode2) {
        return ((int) mode2.getRefreshRate()) == refreshRateConfig.getNormalSpeedRefreshRates().max() && mode2.equalsExceptRefreshRate(mode);
    }

    static /* synthetic */ Mode[] lambda$filteredSupportedModes$1(int i) {
        return new Mode[i];
    }

    static /* synthetic */ boolean lambda$filteredSupportedModes$2(RefreshRateConfig refreshRateConfig, Mode mode) {
        return ((int) mode.getRefreshRate()) >= refreshRateConfig.getNormalSpeedRefreshRates().min() && ((int) mode.getRefreshRate()) <= refreshRateConfig.getNormalSpeedRefreshRates().max();
    }

    static /* synthetic */ Mode[] lambda$filteredSupportedModes$3(int i) {
        return new Mode[i];
    }

    static /* synthetic */ boolean lambda$filteredSupportedModes$4(RefreshRateConfig refreshRateConfig, Mode mode, Mode mode2) {
        return ((int) mode2.getRefreshRate()) == refreshRateConfig.getHighSpeedRefreshRates().max() && mode2.equalsExceptRefreshRate(mode);
    }

    static /* synthetic */ Mode[] lambda$filteredSupportedModes$5(int i) {
        return new Mode[i];
    }

    static /* synthetic */ boolean lambda$filteredSupportedModes$6(RefreshRateConfig refreshRateConfig, Mode mode) {
        return ((int) mode.getRefreshRate()) >= refreshRateConfig.getHighSpeedRefreshRates().min() && ((int) mode.getRefreshRate()) <= refreshRateConfig.getHighSpeedRefreshRates().max();
    }

    static /* synthetic */ Mode[] lambda$filteredSupportedModes$7(int i) {
        return new Mode[i];
    }

    public boolean hasArrSupport() {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            z = this.mDisplayInfo.hasArrSupport;
        }
        return z;
    }

    public float getSuggestedFrameRate(int i) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (i == 1) {
                return this.mDisplayInfo.frameRateCategoryRate.getHigh();
            }
            if (i == 0) {
                return this.mDisplayInfo.frameRateCategoryRate.getNormal();
            }
            throw new IllegalArgumentException("Invalid FrameRateCategory provided");
        }
    }

    public boolean isMinimalPostProcessingSupported() {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            z = this.mDisplayInfo.minimalPostProcessingSupported;
        }
        return z;
    }

    public void requestColorMode(int i) {
        this.mGlobal.requestColorMode(this.mDisplayId, i);
    }

    public int getColorMode() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mDisplayInfo.colorMode;
        }
        return i;
    }

    public int getRemoveMode() {
        return this.mDisplayInfo.removeMode;
    }

    public Mode getSystemPreferredDisplayMode() {
        return this.mGlobal.getSystemPreferredDisplayMode(getDisplayId());
    }

    public HdrCapabilities getHdrCapabilities() {
        int[] iArr;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (this.mDisplayInfo.hdrCapabilities != null && !this.mDisplayInfo.isForceSdr) {
                if (this.mDisplayInfo.userDisabledHdrTypes.length == 0) {
                    int[] supportedHdrTypes = getMode().getSupportedHdrTypes();
                    iArr = Arrays.copyOf(supportedHdrTypes, supportedHdrTypes.length);
                } else {
                    ArraySet arraySet = new ArraySet();
                    int i = 0;
                    for (int i2 : getMode().getSupportedHdrTypes()) {
                        if (!contains(this.mDisplayInfo.userDisabledHdrTypes, i2)) {
                            arraySet.add(Integer.valueOf(i2));
                        }
                    }
                    int[] iArr2 = new int[arraySet.size()];
                    Iterator it = arraySet.iterator();
                    while (it.hasNext()) {
                        iArr2[i] = ((Integer) it.next()).intValue();
                        i++;
                    }
                    iArr = iArr2;
                }
                return new HdrCapabilities(iArr, this.mDisplayInfo.hdrCapabilities.mMaxLuminance, this.mDisplayInfo.hdrCapabilities.mMaxAverageLuminance, this.mDisplayInfo.hdrCapabilities.mMinLuminance);
            }
            return null;
        }
    }

    private boolean contains(int[] iArr, int i) {
        for (int i2 : iArr) {
            Integer.valueOf(i2).getClass();
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    public int[] getReportedHdrTypes() {
        int[] supportedHdrTypes;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            supportedHdrTypes = this.mDisplayInfo.getMode().getSupportedHdrTypes();
        }
        return supportedHdrTypes;
    }

    public boolean isHdr() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            HdrCapabilities hdrCapabilities = getHdrCapabilities();
            if (hdrCapabilities == null) {
                return false;
            }
            return hdrCapabilities.getSupportedHdrTypes().length != 0;
        }
    }

    public boolean isHdrSdrRatioAvailable() {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            z = !Float.isNaN(this.mDisplayInfo.hdrSdrRatio);
        }
        return z;
    }

    public float getHdrSdrRatio() {
        float f;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            f = Float.isNaN(this.mDisplayInfo.hdrSdrRatio) ? 1.0f : this.mDisplayInfo.hdrSdrRatio;
        }
        return f;
    }

    private int findHdrSdrRatioListenerLocked(Consumer<Display> consumer) {
        for (int i = 0; i < this.mHdrSdrRatioListeners.size(); i++) {
            if (this.mHdrSdrRatioListeners.get(i).mListener == consumer) {
                return i;
            }
        }
        return -1;
    }

    public void registerHdrSdrRatioChangedListener(Executor executor, Consumer<Display> consumer) {
        HdrSdrRatioListenerWrapper hdrSdrRatioListenerWrapper;
        if (!isHdrSdrRatioAvailable()) {
            throw new IllegalStateException("HDR/SDR ratio changed not available");
        }
        synchronized (this.mLock) {
            hdrSdrRatioListenerWrapper = null;
            byte b = 0;
            if (findHdrSdrRatioListenerLocked(consumer) == -1) {
                HdrSdrRatioListenerWrapper hdrSdrRatioListenerWrapper2 = new HdrSdrRatioListenerWrapper(consumer);
                this.mHdrSdrRatioListeners.add(hdrSdrRatioListenerWrapper2);
                hdrSdrRatioListenerWrapper = hdrSdrRatioListenerWrapper2;
            }
        }
        if (hdrSdrRatioListenerWrapper != null) {
            this.mGlobal.registerDisplayListener((DisplayManager.DisplayListener) hdrSdrRatioListenerWrapper, executor, 18L, ActivityThread.currentPackageName(), true);
        }
    }

    public void unregisterHdrSdrRatioChangedListener(Consumer<Display> consumer) {
        HdrSdrRatioListenerWrapper remove;
        synchronized (this.mLock) {
            int findHdrSdrRatioListenerLocked = findHdrSdrRatioListenerLocked(consumer);
            remove = findHdrSdrRatioListenerLocked != -1 ? this.mHdrSdrRatioListeners.remove(findHdrSdrRatioListenerLocked) : null;
        }
        if (remove != null) {
            this.mGlobal.unregisterDisplayListener(remove);
        }
    }

    public float getHighestHdrSdrRatio() {
        return this.mGlobal.getHighestHdrSdrRatio(this.mDisplayId);
    }

    public void setUserPreferredDisplayMode(Mode mode) {
        this.mGlobal.setUserPreferredDisplayMode(this.mDisplayId, new Mode(mode.getPhysicalWidth(), mode.getPhysicalHeight(), mode.getRefreshRate()));
    }

    public void clearUserPreferredDisplayMode() {
        this.mGlobal.setUserPreferredDisplayMode(this.mDisplayId, null);
    }

    public Mode getUserPreferredDisplayMode() {
        return this.mGlobal.getUserPreferredDisplayMode(this.mDisplayId);
    }

    public boolean isWideColorGamut() {
        boolean isWideColorGamut;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            isWideColorGamut = this.mDisplayInfo.isWideColorGamut();
        }
        return isWideColorGamut;
    }

    public ColorSpace getPreferredWideGamutColorSpace() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (!this.mDisplayInfo.isWideColorGamut()) {
                return null;
            }
            return this.mGlobal.getPreferredWideGamutColorSpace();
        }
    }

    public OverlayProperties getOverlaySupport() {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (this.mDisplayInfo.type != 1 && this.mDisplayInfo.type != 2) {
                return OverlayProperties.getDefault();
            }
            return this.mGlobal.getOverlaySupport();
        }
    }

    public int[] getSupportedColorModes() {
        int[] copyOf;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            int[] iArr = this.mDisplayInfo.supportedColorModes;
            copyOf = Arrays.copyOf(iArr, iArr.length);
        }
        return copyOf;
    }

    public ColorSpace[] getSupportedWideColorGamut() {
        synchronized (this.mLock) {
            ColorSpace[] colorSpaceArr = new ColorSpace[0];
            updateDisplayInfoLocked();
            if (!isWideColorGamut()) {
                return colorSpaceArr;
            }
            int[] supportedColorModes = getSupportedColorModes();
            ArrayList arrayList = new ArrayList();
            for (int i : supportedColorModes) {
                if (i == 6) {
                    arrayList.add(ColorSpace.get(ColorSpace.Named.DCI_P3));
                } else if (i == 9) {
                    arrayList.add(ColorSpace.get(ColorSpace.Named.DISPLAY_P3));
                }
            }
            return (ColorSpace[]) arrayList.toArray(colorSpaceArr);
        }
    }

    public long getAppVsyncOffsetNanos() {
        long j;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            j = this.mDisplayInfo.appVsyncOffsetNanos;
        }
        return j;
    }

    public long getPresentationDeadlineNanos() {
        long j;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            j = this.mDisplayInfo.presentationDeadlineNanos;
        }
        return j;
    }

    public DeviceProductInfo getDeviceProductInfo() {
        DeviceProductInfo deviceProductInfo;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            deviceProductInfo = this.mDisplayInfo.deviceProductInfo;
        }
        return deviceProductInfo;
    }

    @Deprecated
    public void getMetrics(DisplayMetrics displayMetrics) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(displayMetrics, getDisplayAdjustments());
        }
    }

    @Deprecated
    public void getRealSize(Point point) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (shouldReportMaxBounds()) {
                Rect maxBounds = this.mResources.getConfiguration().windowConfiguration.getMaxBounds();
                point.x = maxBounds.width();
                point.y = maxBounds.height();
            } else {
                point.x = this.mDisplayInfo.logicalWidth;
                point.y = this.mDisplayInfo.logicalHeight;
                int localRotation = getLocalRotation();
                if (localRotation != this.mDisplayInfo.rotation) {
                    adjustSize(point, this.mDisplayInfo.rotation, localRotation);
                }
            }
        }
    }

    @Deprecated
    public void getRealMetrics(DisplayMetrics displayMetrics) {
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            if (shouldReportMaxBounds()) {
                this.mDisplayInfo.getMaxBoundsMetrics(displayMetrics, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, this.mResources.getConfiguration());
                return;
            }
            this.mDisplayInfo.getLogicalMetrics(displayMetrics, CompatibilityInfo.DEFAULT_COMPATIBILITY_INFO, null);
            int localRotation = getLocalRotation();
            if (localRotation != this.mDisplayInfo.rotation) {
                adjustMetrics(displayMetrics, this.mDisplayInfo.rotation, localRotation);
            }
        }
    }

    private boolean shouldReportMaxBounds() {
        Configuration configuration;
        Resources resources = this.mResources;
        return (resources == null || (configuration = resources.getConfiguration()) == null || configuration.windowConfiguration.getMaxBounds().isEmpty()) ? false : true;
    }

    public int getState() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mIsValid ? this.mDisplayInfo.state : 0;
        }
        return i;
    }

    public int getCommittedState() {
        int i;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            i = this.mIsValid ? this.mDisplayInfo.committedState : 0;
        }
        return i;
    }

    public boolean canHostTasks() {
        boolean z;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            z = this.mIsValid && this.mDisplayInfo.canHostTasks;
        }
        return z;
    }

    public boolean hasAccess(int i) {
        return hasAccess(i, this.mFlags, this.mOwnerUid, this.mDisplayId);
    }

    public static boolean hasAccess(int i, int i2, int i3, int i4) {
        return (i2 & 4) == 0 || i == i3 || i == 1000 || i == 0 || DisplayManagerGlobal.getInstance().isUidPresentOnDisplay(i, i4);
    }

    public boolean isPublicPresentation() {
        return (this.mFlags & 12) == 8;
    }

    public boolean isTrusted() {
        return (this.mFlags & 128) == 128;
    }

    public boolean canStealTopFocus() {
        return (this.mFlags & 4096) == 0;
    }

    private void updateDisplayInfoLocked() {
        DisplayInfo displayInfo = this.mGlobal.getDisplayInfo(this.mDisplayId);
        if (displayInfo == null) {
            if (this.mIsValid) {
                this.mIsValid = false;
            }
        } else {
            this.mDisplayInfo = displayInfo;
            if (!this.mIsValid) {
                this.mIsValid = true;
            }
        }
        if (CoreRune.FW_VRR_FOLD && this.mDisplayId == 0) {
            RefreshRateConfig.updateFoldStateIfNeeded(this.mDisplayInfo.address);
        }
    }

    private void updateCachedAppSizeIfNeededLocked() {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis > this.mLastCachedAppSizeUpdate + 20) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
            this.mCachedAppWidthCompat = this.mTempMetrics.widthPixels;
            this.mCachedAppHeightCompat = this.mTempMetrics.heightPixels;
            this.mLastCachedAppSizeUpdate = uptimeMillis;
        }
    }

    private static boolean noFlip(int i, int i2) {
        return ((i - i2) + 4) % 2 == 0;
    }

    private void adjustSize(Point point, int i, int i2) {
        if (noFlip(i, i2)) {
            return;
        }
        int i3 = point.x;
        point.x = point.y;
        point.y = i3;
    }

    private void adjustMetrics(DisplayMetrics displayMetrics, int i, int i2) {
        if (noFlip(i, i2)) {
            return;
        }
        int i3 = displayMetrics.widthPixels;
        displayMetrics.widthPixels = displayMetrics.heightPixels;
        displayMetrics.heightPixels = i3;
        int i4 = displayMetrics.noncompatWidthPixels;
        displayMetrics.noncompatWidthPixels = displayMetrics.noncompatHeightPixels;
        displayMetrics.noncompatHeightPixels = i4;
    }

    private int getLocalRotation() {
        Resources resources = this.mResources;
        if (resources == null) {
            return this.mDisplayInfo.rotation;
        }
        int displayRotation = resources.getConfiguration().windowConfiguration.getDisplayRotation();
        return displayRotation != -1 ? displayRotation : this.mDisplayInfo.rotation;
    }

    public String toString() {
        String str;
        synchronized (this.mLock) {
            updateDisplayInfoLocked();
            this.mDisplayInfo.getAppMetrics(this.mTempMetrics, getDisplayAdjustments());
            str = "Display id " + this.mDisplayId + ": " + this.mDisplayInfo + ", " + this.mTempMetrics + ", isValid=" + this.mIsValid;
        }
        return str;
    }

    public static String typeToString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "INTERNAL";
        }
        if (i == 2) {
            return "EXTERNAL";
        }
        if (i == 3) {
            return "WIFI";
        }
        if (i == 4) {
            return "OVERLAY";
        }
        if (i == 5) {
            return "VIRTUAL";
        }
        return Integer.toString(i);
    }

    public static String stateToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "OFF";
            case 2:
                return "ON";
            case 3:
                return "DOZE";
            case 4:
                return "DOZE_SUSPEND";
            case 5:
                return "VR";
            case 6:
                return "ON_SUSPEND";
            default:
                return Integer.toString(i);
        }
    }

    public static String stateReasonToString(int i) {
        switch (i) {
            case 0:
                return "UNKNOWN";
            case 1:
                return "DEFAULT_POLICY";
            case 2:
                return "DRAW_WAKE_LOCK";
            case 3:
                return "OFFLOAD";
            case 4:
                return "TILT";
            case 5:
                return "DREAM_MANAGER";
            case 6:
                return Credentials.EXTRA_PUBLIC_KEY;
            case 7:
                return "MOTION";
            default:
                return Integer.toString(i);
        }
    }

    public DisplayDecorationSupport getDisplayDecorationSupport() {
        return this.mGlobal.getDisplayDecorationSupport(this.mDisplayId);
    }

    public static final class Mode implements Parcelable {
        public static final int INVALID_MODE_ID = -1;
        private final float[] mAlternativeRefreshRates;
        private final int mHeight;
        private final boolean mIsSynthetic;
        private final int mModeId;
        private final float mPeakRefreshRate;
        private final int[] mSupportedHdrTypes;
        private final float mVsyncRate;
        private final int mWidth;
        public static final Mode[] EMPTY_ARRAY = new Mode[0];
        public static final Parcelable.Creator<Mode> CREATOR = new Parcelable.Creator<Mode>() { // from class: android.view.Display.Mode.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Mode createFromParcel(Parcel parcel) {
                return new Mode(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Mode[] newArray(int i) {
                return new Mode[i];
            }
        };

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Mode(int i, int i2, float f) {
            this(-1, i, i2, f, f, new float[0], new int[0]);
        }

        public Mode(int i, int i2, int i3, float f) {
            this(i, i2, i3, f, f, new float[0], new int[0]);
        }

        public Mode(int i, int i2, int i3, float f, float[] fArr, int[] iArr) {
            this(i, i2, i3, f, f, fArr, iArr);
        }

        public Mode(int i, int i2, int i3, float f, float f2, float[] fArr, int[] iArr) {
            this(i, i2, i3, f, f2, false, fArr, iArr);
        }

        public Mode(int i, int i2, int i3, float f, float f2, boolean z, float[] fArr, int[] iArr) {
            this.mModeId = i;
            this.mWidth = i2;
            this.mHeight = i3;
            this.mPeakRefreshRate = f;
            this.mVsyncRate = f2;
            this.mIsSynthetic = z;
            float[] copyOf = Arrays.copyOf(fArr, fArr.length);
            this.mAlternativeRefreshRates = copyOf;
            Arrays.sort(copyOf);
            int[] copyOf2 = Arrays.copyOf(iArr, iArr.length);
            this.mSupportedHdrTypes = copyOf2;
            Arrays.sort(copyOf2);
        }

        public int getModeId() {
            return this.mModeId;
        }

        public int getPhysicalWidth() {
            return this.mWidth;
        }

        public int getPhysicalHeight() {
            return this.mHeight;
        }

        public float getRefreshRate() {
            return this.mPeakRefreshRate;
        }

        public float getVsyncRate() {
            return this.mVsyncRate;
        }

        public boolean isSynthetic() {
            return this.mIsSynthetic;
        }

        public float[] getAlternativeRefreshRates() {
            if (CoreRune.FW_VRR_POLICY) {
                if (RefreshRateConfig.getInstance(CoreRune.FW_VRR_FOLD && RefreshRateConfig.isInExtraDisplay()).isSwitchable()) {
                    return new float[0];
                }
            }
            float[] fArr = this.mAlternativeRefreshRates;
            return Arrays.copyOf(fArr, fArr.length);
        }

        public int[] getSupportedHdrTypes() {
            int[] iArr = this.mSupportedHdrTypes;
            return Arrays.copyOf(iArr, iArr.length);
        }

        public boolean matches(int i, int i2, float f) {
            return this.mWidth == i && this.mHeight == i2 && Float.floatToIntBits(this.mPeakRefreshRate) == Float.floatToIntBits(f);
        }

        public boolean matchesIfValid(int i, int i2, float f) {
            if ((Display.isWidthValid(i) || Display.isHeightValid(i2) || Display.isRefreshRateValid(f)) && Display.isWidthValid(i) == Display.isHeightValid(i2)) {
                return (!Display.isWidthValid(i) || this.mWidth == i) && (!Display.isHeightValid(i2) || this.mHeight == i2) && (!Display.isRefreshRateValid(f) || Float.floatToIntBits(this.mPeakRefreshRate) == Float.floatToIntBits(f));
            }
            return false;
        }

        public boolean equalsExceptRefreshRate(Mode mode) {
            return this.mWidth == mode.mWidth && this.mHeight == mode.mHeight;
        }

        public boolean isRefreshRateSet() {
            return this.mPeakRefreshRate != 0.0f;
        }

        public boolean isResolutionSet() {
            return (this.mWidth == -1 || this.mHeight == -1) ? false : true;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Mode)) {
                return false;
            }
            Mode mode = (Mode) obj;
            return this.mModeId == mode.mModeId && matches(mode.mWidth, mode.mHeight, mode.mPeakRefreshRate) && Arrays.equals(this.mAlternativeRefreshRates, mode.mAlternativeRefreshRates) && Arrays.equals(this.mSupportedHdrTypes, mode.mSupportedHdrTypes);
        }

        public int hashCode() {
            return ((((((((((((this.mModeId + 17) * 17) + this.mWidth) * 17) + this.mHeight) * 17) + Float.floatToIntBits(this.mPeakRefreshRate)) * 17) + Float.floatToIntBits(this.mVsyncRate)) * 17) + Arrays.hashCode(this.mAlternativeRefreshRates)) * 17) + Arrays.hashCode(this.mSupportedHdrTypes);
        }

        public String toString() {
            return "{id=" + this.mModeId + ", width=" + this.mWidth + ", height=" + this.mHeight + ", fps=" + this.mPeakRefreshRate + ", vsync=" + this.mVsyncRate + ", synthetic=" + this.mIsSynthetic + ", alternativeRefreshRates=" + Arrays.toString(this.mAlternativeRefreshRates) + ", supportedHdrTypes=" + Arrays.toString(this.mSupportedHdrTypes) + "}";
        }

        private Mode(Parcel parcel) {
            this(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readBoolean(), parcel.createFloatArray(), parcel.createIntArray());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mModeId);
            parcel.writeInt(this.mWidth);
            parcel.writeInt(this.mHeight);
            parcel.writeFloat(this.mPeakRefreshRate);
            parcel.writeFloat(this.mVsyncRate);
            parcel.writeBoolean(this.mIsSynthetic);
            parcel.writeFloatArray(this.mAlternativeRefreshRates);
            parcel.writeIntArray(this.mSupportedHdrTypes);
        }

        public static final class Builder {
            private int mWidth = -1;
            private int mHeight = -1;
            private float mRefreshRate = 0.0f;

            public Builder setResolution(int i, int i2) {
                if (i > 0 && i2 > 0) {
                    this.mWidth = i;
                    this.mHeight = i2;
                }
                return this;
            }

            public Builder setRefreshRate(float f) {
                if (f > 0.0f) {
                    this.mRefreshRate = f;
                }
                return this;
            }

            public Mode build() {
                return new Mode(this.mWidth, this.mHeight, this.mRefreshRate);
            }
        }
    }

    public static final class HdrCapabilities implements Parcelable {
        public static final int HDR_TYPE_DOLBY_VISION = 1;
        public static final int HDR_TYPE_HDR10 = 2;
        public static final int HDR_TYPE_HDR10_PLUS = 4;
        public static final int HDR_TYPE_HLG = 3;
        public static final int HDR_TYPE_INVALID = -1;
        public static final float INVALID_LUMINANCE = -1.0f;
        private float mMaxAverageLuminance;
        private float mMaxLuminance;
        private float mMinLuminance;
        private int[] mSupportedHdrTypes;
        public static final int[] HDR_TYPES = {1, 2, 3, 4};
        public static final Parcelable.Creator<HdrCapabilities> CREATOR = new Parcelable.Creator<HdrCapabilities>() { // from class: android.view.Display.HdrCapabilities.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HdrCapabilities createFromParcel(Parcel parcel) {
                return new HdrCapabilities(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public HdrCapabilities[] newArray(int i) {
                return new HdrCapabilities[i];
            }
        };

        @Retention(RetentionPolicy.SOURCE)
        public @interface HdrType {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public HdrCapabilities() {
            this.mSupportedHdrTypes = new int[0];
            this.mMaxLuminance = -1.0f;
            this.mMaxAverageLuminance = -1.0f;
            this.mMinLuminance = -1.0f;
        }

        public HdrCapabilities(int[] iArr, float f, float f2, float f3) {
            this.mMaxLuminance = -1.0f;
            this.mMaxAverageLuminance = -1.0f;
            this.mMinLuminance = -1.0f;
            this.mSupportedHdrTypes = iArr;
            Arrays.sort(iArr);
            this.mMaxLuminance = f;
            this.mMaxAverageLuminance = f2;
            this.mMinLuminance = f3;
        }

        @Deprecated
        public int[] getSupportedHdrTypes() {
            int[] iArr = this.mSupportedHdrTypes;
            return Arrays.copyOf(iArr, iArr.length);
        }

        public float getDesiredMaxLuminance() {
            return this.mMaxLuminance;
        }

        public float getDesiredMaxAverageLuminance() {
            return this.mMaxAverageLuminance;
        }

        public float getDesiredMinLuminance() {
            return this.mMinLuminance;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof HdrCapabilities)) {
                return false;
            }
            HdrCapabilities hdrCapabilities = (HdrCapabilities) obj;
            return Arrays.equals(this.mSupportedHdrTypes, hdrCapabilities.mSupportedHdrTypes) && this.mMaxLuminance == hdrCapabilities.mMaxLuminance && this.mMaxAverageLuminance == hdrCapabilities.mMaxAverageLuminance && this.mMinLuminance == hdrCapabilities.mMinLuminance;
        }

        public int hashCode() {
            return ((((((391 + Arrays.hashCode(this.mSupportedHdrTypes)) * 17) + Float.floatToIntBits(this.mMaxLuminance)) * 17) + Float.floatToIntBits(this.mMaxAverageLuminance)) * 17) + Float.floatToIntBits(this.mMinLuminance);
        }

        private HdrCapabilities(Parcel parcel) {
            this.mSupportedHdrTypes = new int[0];
            this.mMaxLuminance = -1.0f;
            this.mMaxAverageLuminance = -1.0f;
            this.mMinLuminance = -1.0f;
            readFromParcel(parcel);
        }

        public void readFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            this.mSupportedHdrTypes = new int[readInt];
            for (int i = 0; i < readInt; i++) {
                this.mSupportedHdrTypes[i] = parcel.readInt();
            }
            this.mMaxLuminance = parcel.readFloat();
            this.mMaxAverageLuminance = parcel.readFloat();
            this.mMinLuminance = parcel.readFloat();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.mSupportedHdrTypes.length);
            int i2 = 0;
            while (true) {
                int[] iArr = this.mSupportedHdrTypes;
                if (i2 < iArr.length) {
                    parcel.writeInt(iArr[i2]);
                    i2++;
                } else {
                    parcel.writeFloat(this.mMaxLuminance);
                    parcel.writeFloat(this.mMaxAverageLuminance);
                    parcel.writeFloat(this.mMinLuminance);
                    return;
                }
            }
        }

        public String toString() {
            return "HdrCapabilities{mSupportedHdrTypes=" + Arrays.toString(this.mSupportedHdrTypes) + ", mMaxLuminance=" + this.mMaxLuminance + ", mMaxAverageLuminance=" + this.mMaxAverageLuminance + ", mMinLuminance=" + this.mMinLuminance + '}';
        }

        public static String hdrTypeToString(int i) {
            if (i == 1) {
                return "HDR_TYPE_DOLBY_VISION";
            }
            if (i == 2) {
                return "HDR_TYPE_HDR10";
            }
            if (i == 3) {
                return "HDR_TYPE_HLG";
            }
            if (i == 4) {
                return "HDR_TYPE_HDR10_PLUS";
            }
            return "HDR_TYPE_INVALID";
        }
    }

    private class HdrSdrRatioListenerWrapper implements DisplayManager.DisplayListener {
        float mLastReportedRatio;
        Consumer<Display> mListener;

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        private HdrSdrRatioListenerWrapper(Consumer<Display> consumer) {
            this.mLastReportedRatio = 1.0f;
            this.mListener = consumer;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            if (i == Display.this.getDisplayId()) {
                float hdrSdrRatio = Display.this.getHdrSdrRatio();
                if (hdrSdrRatio != this.mLastReportedRatio) {
                    this.mLastReportedRatio = hdrSdrRatio;
                    this.mListener.accept(Display.this);
                }
            }
        }
    }
}
