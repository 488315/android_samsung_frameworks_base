package com.android.server.display;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.hardware.biometrics.face.V1_0.OptionalBool$$ExternalSyntheticOutline0;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.input.HostUsiVersion;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.util.MathUtils;
import android.util.Slog;
import android.util.Spline;
import android.view.SurfaceControl;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeapdumpWatcher$$ExternalSyntheticOutline0;
import com.android.server.VaultKeeperService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.display.config.AutoBrightness;
import com.android.server.display.config.AutoBrightnessSettingName;
import com.android.server.display.config.BlockingZoneConfig;
import com.android.server.display.config.BlockingZoneThreshold;
import com.android.server.display.config.BrightnessLimitMap;
import com.android.server.display.config.Density;
import com.android.server.display.config.DisplayBrightnessMappingConfig;
import com.android.server.display.config.DisplayBrightnessPoint;
import com.android.server.display.config.DisplayConfiguration;
import com.android.server.display.config.EvenDimmerBrightnessData;
import com.android.server.display.config.HbmTiming;
import com.android.server.display.config.HdrBrightnessData;
import com.android.server.display.config.HighBrightnessMode;
import com.android.server.display.config.HysteresisLevels;
import com.android.server.display.config.IdleScreenRefreshRateTimeout;
import com.android.server.display.config.IdleScreenRefreshRateTimeoutLuxThresholdPoint;
import com.android.server.display.config.IdleScreenRefreshRateTimeoutLuxThresholds;
import com.android.server.display.config.LuxThrottling;
import com.android.server.display.config.NonNegativeFloatToFloatPoint;
import com.android.server.display.config.PowerThrottlingConfig;
import com.android.server.display.config.PowerThrottlingMap;
import com.android.server.display.config.PowerThrottlingPoint;
import com.android.server.display.config.PredefinedBrightnessLimitNames;
import com.android.server.display.config.RefreshRateConfigs;
import com.android.server.display.config.RefreshRateData;
import com.android.server.display.config.RefreshRateRange;
import com.android.server.display.config.RefreshRateZone;
import com.android.server.display.config.RefreshRateZoneProfiles;
import com.android.server.display.config.SecHysteresisLevels;
import com.android.server.display.config.SensorData;
import com.android.server.display.config.ThermalStatus;
import com.android.server.display.feature.DisplayManagerFlags;
import com.android.server.display.utils.DebugUtils;
import com.android.server.display.utils.DeviceConfigParsingUtils;
import com.android.server.power.PowerManagerUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class DisplayDeviceConfig {
    static final float BRIGHTNESS_DEFAULT = 0.5f;
    public static final boolean DEBUG = DebugUtils.isDebuggable("DisplayDeviceConfig");
    public static final float[] DEFAULT_BRIGHTNESS_THRESHOLDS = new float[0];
    static final float HDR_PERCENT_OF_SCREEN_REQUIRED_DEFAULT = 0.5f;
    public HysteresisLevels mAmbientBrightnessHysteresis;
    public HysteresisLevels mAmbientBrightnessIdleHysteresis;
    public SecHysteresisLevels mAmbientBrightnessThresholdsTouchHigh;
    public SecHysteresisLevels mAmbientBrightnessThresholdsTouchLow;
    public SensorData mAmbientLightSensor;
    public boolean mAutoBrightnessAvailable;
    public long mAutoBrightnessBrighteningLightDebounce;
    public long mAutoBrightnessBrighteningLightDebounceIdle;
    public long mAutoBrightnessDarkeningLightDebounce;
    public long mAutoBrightnessDarkeningLightDebounceIdle;
    public float[] mBacklight;
    public Spline mBacklightToBrightnessSpline;
    public Spline mBacklightToNitsSpline;
    public float[] mBrightness;
    public float mBrightnessCapForWearBedtimeMode;
    public Spline mBrightnessToBacklightSpline;
    public final Context mContext;
    public boolean mDdcAutoBrightnessAvailable;
    public int mDefaultHighBlockingZoneRefreshRate;
    public int mDefaultLowBlockingZoneRefreshRate;
    public DensityMapping mDensityMapping;
    public DisplayBrightnessMappingConfig mDisplayBrightnessMapping;
    public EvenDimmerBrightnessData mEvenDimmerBrightnessData;
    public final DisplayManagerFlags mFlags;
    public HighBrightnessModeData mHbmData;
    public HdrBrightnessData mHdrBrightnessData;
    public float[] mHighAmbientBrightnessThresholds;
    public String mHighBlockingZoneThermalMapId;
    public float[] mHighDisplayBrightnessThresholds;
    public HostUsiVersion mHostUsiVersion;
    public List mIdleScreenRefreshRateTimeoutLuxThresholds;
    public int mInterpolationType;
    public boolean mIsCoverDisplay;
    public boolean mIsFirstDisplay;
    public boolean mIsHighBrightnessModeEnabled;
    public boolean mIsSimpleMappingStrategy;
    public String mLoadedFrom;
    public float[] mLowAmbientBrightnessThresholds;
    public String mLowBlockingZoneThermalMapId;
    public float[] mLowDisplayBrightnessThresholds;
    public final Map mLuxThrottlingData;
    public String mName;
    public float[] mNits;
    public Spline mNitsToBacklightSpline;
    public PowerThrottlingConfigData mPowerThrottlingConfigData;
    public final Map mPowerThrottlingDataMapByThrottlingId;
    public SensorData mProximitySensor;
    public List mQuirks;
    public float[] mRawBacklight;
    public float[] mRawNits;
    public RefreshRateData mRefreshRateData;
    public final Map mRefreshRateThrottlingMap;
    public final Map mRefreshRateZoneProfiles;
    public HysteresisLevels mScreenBrightnessHysteresis;
    public HysteresisLevels mScreenBrightnessIdleHysteresis;
    public SensorData mScreenOffBrightnessSensor;
    public int[] mScreenOffBrightnessSensorValueToLux;
    public Spline mSdrToHdrSpline;
    public SensorData mTempSensor;
    public final Map mThermalBrightnessThrottlingDataMapByThrottlingId;
    public boolean mUseSurfaceControlBrightness;
    public boolean mVrrSupportEnabled;
    public final List mRefreshRateLimitations = new ArrayList(2);
    public float mBacklightMinimum = Float.NaN;
    public float mBacklightMaximum = Float.NaN;
    public float mBrightnessDefault = Float.NaN;
    public float mBrightnessRampFastDecrease = Float.NaN;
    public float mBrightnessRampFastIncrease = Float.NaN;
    public float mBrightnessRampSlowDecrease = Float.NaN;
    public float mBrightnessRampSlowIncrease = Float.NaN;
    public float mBrightnessRampSlowDecreaseIdle = Float.NaN;
    public float mBrightnessRampSlowIncreaseIdle = Float.NaN;
    public long mBrightnessRampDecreaseMaxMillis = 0;
    public long mBrightnessRampIncreaseMaxMillis = 0;
    public long mBrightnessRampDecreaseMaxIdleMillis = 0;
    public long mBrightnessRampIncreaseMaxIdleMillis = 0;
    public int mAmbientHorizonLong = 2500;
    public int mAmbientHorizonShort = 600;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BrightnessLimitMapType {
        public static final /* synthetic */ BrightnessLimitMapType[] $VALUES;
        public static final BrightnessLimitMapType ADAPTIVE;
        public static final BrightnessLimitMapType DEFAULT;

        static {
            BrightnessLimitMapType brightnessLimitMapType =
                    new BrightnessLimitMapType("DEFAULT", 0);
            DEFAULT = brightnessLimitMapType;
            BrightnessLimitMapType brightnessLimitMapType2 =
                    new BrightnessLimitMapType("ADAPTIVE", 1);
            ADAPTIVE = brightnessLimitMapType2;
            $VALUES =
                    new BrightnessLimitMapType[] {brightnessLimitMapType, brightnessLimitMapType2};
        }

        public static BrightnessLimitMapType valueOf(String str) {
            return (BrightnessLimitMapType) Enum.valueOf(BrightnessLimitMapType.class, str);
        }

        public static BrightnessLimitMapType[] values() {
            return (BrightnessLimitMapType[]) $VALUES.clone();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HighBrightnessModeData {
        public boolean allowInLowPowerMode;
        public float minimumHdrPercentOfScreen;
        public float minimumLux;
        public long timeMaxMillis;
        public long timeMinMillis;
        public long timeWindowMillis;
        public float transitionPoint;

        public final String toString() {
            return "HBM{minLux: "
                    + this.minimumLux
                    + ", transition: "
                    + this.transitionPoint
                    + ", timeWindow: "
                    + this.timeWindowMillis
                    + "ms, timeMax: "
                    + this.timeMaxMillis
                    + "ms, timeMin: "
                    + this.timeMinMillis
                    + "ms, allowInLowPowerMode: "
                    + this.allowInLowPowerMode
                    + ", minimumHdrPercentOfScreen: "
                    + this.minimumHdrPercentOfScreen
                    + "} ";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PowerThrottlingConfigData {
        public final float brightnessLowestCapAllowed;
        public final int pollingWindowMillis;

        public PowerThrottlingConfigData(float f, int i) {
            this.brightnessLowestCapAllowed = f;
            this.pollingWindowMillis = i;
        }

        public final String toString() {
            StringBuilder sb =
                    new StringBuilder("PowerThrottlingConfigData{brightnessLowestCapAllowed: ");
            sb.append(this.brightnessLowestCapAllowed);
            sb.append(", pollingWindowMillis: ");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.pollingWindowMillis, sb, "} ");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PowerThrottlingData {
        public final List throttlingLevels;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ThrottlingLevel {
            public final float powerQuotaMilliWatts;
            public final int thermalStatus;

            public ThrottlingLevel(float f, int i) {
                this.thermalStatus = i;
                this.powerQuotaMilliWatts = f;
            }

            public final boolean equals(Object obj) {
                if (!(obj instanceof ThrottlingLevel)) {
                    return false;
                }
                ThrottlingLevel throttlingLevel = (ThrottlingLevel) obj;
                return throttlingLevel.thermalStatus == this.thermalStatus
                        && throttlingLevel.powerQuotaMilliWatts == this.powerQuotaMilliWatts;
            }

            public final int hashCode() {
                return Float.hashCode(this.powerQuotaMilliWatts) + ((this.thermalStatus + 31) * 31);
            }

            public final String toString() {
                return "[" + this.thermalStatus + "," + this.powerQuotaMilliWatts + "]";
            }
        }

        public PowerThrottlingData(List list) {
            this.throttlingLevels = new ArrayList(list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ThrottlingLevel throttlingLevel = (ThrottlingLevel) it.next();
                this.throttlingLevels.add(
                        new ThrottlingLevel(
                                throttlingLevel.powerQuotaMilliWatts,
                                throttlingLevel.thermalStatus));
            }
        }

        public static PowerThrottlingData create(List list) {
            if (list == null || list.size() == 0) {
                Slog.e(
                        "DisplayDeviceConfig",
                        "PowerThrottlingData received null or empty throttling levels");
                return null;
            }
            ThrottlingLevel throttlingLevel = (ThrottlingLevel) list.get(0);
            int size = list.size();
            int i = 1;
            while (i < size) {
                ThrottlingLevel throttlingLevel2 = (ThrottlingLevel) list.get(i);
                if (throttlingLevel2.thermalStatus <= throttlingLevel.thermalStatus) {
                    StringBuilder sb =
                            new StringBuilder(
                                    "powerThrottlingMap must be strictly increasing, ignoring"
                                        + " configuration. ThermalStatus ");
                    sb.append(throttlingLevel2.thermalStatus);
                    sb.append(" <= ");
                    VaultKeeperService$$ExternalSyntheticOutline0.m(
                            sb, throttlingLevel.thermalStatus, "DisplayDeviceConfig");
                    return null;
                }
                float f = throttlingLevel2.powerQuotaMilliWatts;
                float f2 = throttlingLevel.powerQuotaMilliWatts;
                if (f >= f2) {
                    Slog.e(
                            "DisplayDeviceConfig",
                            "powerThrottlingMap must be strictly decreasing, ignoring"
                                + " configuration. powerQuotaMilliWatts "
                                    + f
                                    + " >= "
                                    + f2);
                    return null;
                }
                i++;
                throttlingLevel = throttlingLevel2;
            }
            return new PowerThrottlingData(list);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof PowerThrottlingData)) {
                return false;
            }
            return ((ArrayList) this.throttlingLevels)
                    .equals(((PowerThrottlingData) obj).throttlingLevels);
        }

        public final int hashCode() {
            return ((ArrayList) this.throttlingLevels).hashCode();
        }

        public final String toString() {
            return "PowerThrottlingData{throttlingLevels:" + this.throttlingLevels + "} ";
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ThermalBrightnessThrottlingData {
        public final List throttlingLevels;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class ThrottlingLevel {
            public final float brightness;
            public final int thermalStatus;

            public ThrottlingLevel(float f, int i) {
                this.thermalStatus = i;
                this.brightness = f;
            }

            public final boolean equals(Object obj) {
                if (!(obj instanceof ThrottlingLevel)) {
                    return false;
                }
                ThrottlingLevel throttlingLevel = (ThrottlingLevel) obj;
                return throttlingLevel.thermalStatus == this.thermalStatus
                        && throttlingLevel.brightness == this.brightness;
            }

            public final int hashCode() {
                return Float.hashCode(this.brightness) + ((this.thermalStatus + 31) * 31);
            }

            public final String toString() {
                return "[" + this.thermalStatus + "," + this.brightness + "]";
            }
        }

        public ThermalBrightnessThrottlingData(List list) {
            this.throttlingLevels = new ArrayList(list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ThrottlingLevel throttlingLevel = (ThrottlingLevel) it.next();
                this.throttlingLevels.add(
                        new ThrottlingLevel(
                                throttlingLevel.brightness, throttlingLevel.thermalStatus));
            }
        }

        public static ThermalBrightnessThrottlingData create(List list) {
            if (list == null || list.size() == 0) {
                Slog.e(
                        "DisplayDeviceConfig",
                        "BrightnessThrottlingData received null or empty throttling levels");
                return null;
            }
            ThrottlingLevel throttlingLevel = (ThrottlingLevel) list.get(0);
            int size = list.size();
            int i = 1;
            while (i < size) {
                ThrottlingLevel throttlingLevel2 = (ThrottlingLevel) list.get(i);
                if (throttlingLevel2.thermalStatus <= throttlingLevel.thermalStatus) {
                    StringBuilder sb =
                            new StringBuilder(
                                    "brightnessThrottlingMap must be strictly increasing, ignoring"
                                        + " configuration. ThermalStatus ");
                    sb.append(throttlingLevel2.thermalStatus);
                    sb.append(" <= ");
                    VaultKeeperService$$ExternalSyntheticOutline0.m(
                            sb, throttlingLevel.thermalStatus, "DisplayDeviceConfig");
                    return null;
                }
                float f = throttlingLevel.brightness;
                float f2 = throttlingLevel2.brightness;
                if (f2 >= f) {
                    Slog.e(
                            "DisplayDeviceConfig",
                            "brightnessThrottlingMap must be strictly decreasing, ignoring"
                                + " configuration. Brightness "
                                    + f2
                                    + " >= "
                                    + f2);
                    return null;
                }
                i++;
                throttlingLevel = throttlingLevel2;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ThrottlingLevel throttlingLevel3 = (ThrottlingLevel) it.next();
                if (throttlingLevel3.brightness > 1.0f) {
                    Slog.e(
                            "DisplayDeviceConfig",
                            "brightnessThrottlingMap contains a brightness value exceeding system"
                                + " max. Brightness "
                                    + throttlingLevel3.brightness
                                    + " > 1.0");
                    return null;
                }
            }
            return new ThermalBrightnessThrottlingData(list);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ThermalBrightnessThrottlingData)) {
                return false;
            }
            return ((ArrayList) this.throttlingLevels)
                    .equals(((ThermalBrightnessThrottlingData) obj).throttlingLevels);
        }

        public final int hashCode() {
            return ((ArrayList) this.throttlingLevels).hashCode();
        }

        public final String toString() {
            return "ThermalBrightnessThrottlingData{throttlingLevels:"
                    + this.throttlingLevels
                    + "} ";
        }
    }

    public DisplayDeviceConfig(Context context, DisplayManagerFlags displayManagerFlags) {
        float[] fArr = HysteresisLevels.DEFAULT_SCREEN_THRESHOLD_LEVELS;
        float[] fArr2 = HysteresisLevels.DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS;
        float[] fArr3 = HysteresisLevels.DEFAULT_SCREEN_DARKENING_THRESHOLDS;
        this.mScreenBrightnessHysteresis =
                HysteresisLevels.createHysteresisLevels(
                        null, 17236307, 17236302, 17236306, fArr, fArr2, fArr3, true);
        this.mScreenBrightnessIdleHysteresis =
                HysteresisLevels.createHysteresisLevels(
                        null, 17236307, 17236302, 17236306, fArr, fArr2, fArr3, true);
        float[] fArr4 = HysteresisLevels.DEFAULT_AMBIENT_THRESHOLD_LEVELS;
        float[] fArr5 = HysteresisLevels.DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS;
        float[] fArr6 = HysteresisLevels.DEFAULT_AMBIENT_DARKENING_THRESHOLDS;
        this.mAmbientBrightnessHysteresis =
                HysteresisLevels.createHysteresisLevels(
                        null,
                        R.array.config_mainBuiltInDisplayCutoutSideOverride,
                        R.array.config_longPressVibePattern,
                        R.array.config_lteDbmThresholds,
                        fArr4,
                        fArr5,
                        fArr6,
                        false);
        this.mAmbientBrightnessIdleHysteresis =
                HysteresisLevels.createHysteresisLevels(
                        null,
                        R.array.config_mainBuiltInDisplayCutoutSideOverride,
                        R.array.config_longPressVibePattern,
                        R.array.config_lteDbmThresholds,
                        fArr4,
                        fArr5,
                        fArr6,
                        false);
        this.mAmbientBrightnessThresholdsTouchHigh = null;
        this.mAmbientBrightnessThresholdsTouchLow = null;
        this.mIsHighBrightnessModeEnabled = false;
        this.mLoadedFrom = null;
        this.mIsSimpleMappingStrategy = false;
        this.mAutoBrightnessBrighteningLightDebounce = -1L;
        this.mAutoBrightnessDarkeningLightDebounce = -1L;
        this.mAutoBrightnessBrighteningLightDebounceIdle = -1L;
        this.mAutoBrightnessDarkeningLightDebounceIdle = -1L;
        this.mAutoBrightnessAvailable = false;
        this.mDdcAutoBrightnessAvailable = true;
        this.mDefaultHighBlockingZoneRefreshRate = 0;
        this.mDefaultLowBlockingZoneRefreshRate = 60;
        this.mRefreshRateZoneProfiles = new HashMap();
        float[] fArr7 = DEFAULT_BRIGHTNESS_THRESHOLDS;
        this.mLowDisplayBrightnessThresholds = fArr7;
        this.mLowAmbientBrightnessThresholds = fArr7;
        this.mHighDisplayBrightnessThresholds = fArr7;
        this.mHighAmbientBrightnessThresholds = fArr7;
        this.mLowBlockingZoneThermalMapId = null;
        this.mHighBlockingZoneThermalMapId = null;
        this.mThermalBrightnessThrottlingDataMapByThrottlingId = new HashMap();
        this.mPowerThrottlingDataMapByThrottlingId = new HashMap();
        this.mRefreshRateThrottlingMap = new HashMap();
        this.mLuxThrottlingData = new HashMap();
        this.mIdleScreenRefreshRateTimeoutLuxThresholds = new ArrayList();
        this.mRefreshRateData = RefreshRateData.DEFAULT_REFRESH_RATE_DATA;
        this.mContext = context;
        this.mFlags = displayManagerFlags;
    }

    public static int convertThermalStatus(ThermalStatus thermalStatus) {
        if (thermalStatus == null) {
            return 0;
        }
        switch (thermalStatus) {
            case EF6:
                break;
            case EF15:
                break;
            case EF24:
                break;
            case EF33:
                break;
            case EF42:
                break;
            case EF51:
                break;
            case EF60:
                break;
            default:
                Slog.wtf("DisplayDeviceConfig", "Unexpected Thermal Status: " + thermalStatus);
                break;
        }
        return 0;
    }

    public static DisplayDeviceConfig getConfigFromPmValues(
            Context context, DisplayManagerFlags displayManagerFlags) {
        DisplayDeviceConfig displayDeviceConfig =
                new DisplayDeviceConfig(context, displayManagerFlags);
        displayDeviceConfig.mLoadedFrom = "Static values";
        displayDeviceConfig.mBacklightMinimum = FullScreenMagnificationGestureHandler.MAX_SCALE;
        displayDeviceConfig.mBacklightMaximum = 1.0f;
        displayDeviceConfig.mBrightnessDefault = 0.5f;
        displayDeviceConfig.mBrightnessRampFastDecrease = 1.0f;
        displayDeviceConfig.mBrightnessRampFastIncrease = 1.0f;
        displayDeviceConfig.mBrightnessRampSlowDecrease = 1.0f;
        displayDeviceConfig.mBrightnessRampSlowIncrease = 1.0f;
        displayDeviceConfig.mBrightnessRampSlowDecreaseIdle = 1.0f;
        displayDeviceConfig.mBrightnessRampSlowIncreaseIdle = 1.0f;
        displayDeviceConfig.mBrightnessRampDecreaseMaxMillis = 0L;
        displayDeviceConfig.mBrightnessRampIncreaseMaxMillis = 0L;
        displayDeviceConfig.mBrightnessRampDecreaseMaxIdleMillis = 0L;
        displayDeviceConfig.mBrightnessRampIncreaseMaxIdleMillis = 0L;
        displayDeviceConfig.mNits = null;
        displayDeviceConfig.mBacklight = null;
        float[] fArr = {FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f};
        displayDeviceConfig.mBrightnessToBacklightSpline = Spline.createSpline(fArr, fArr);
        displayDeviceConfig.mBacklightToBrightnessSpline = Spline.createSpline(fArr, fArr);
        displayDeviceConfig.mIsSimpleMappingStrategy = true;
        displayDeviceConfig.mAmbientLightSensor =
                new SensorData(
                        displayDeviceConfig
                                .mContext
                                .getResources()
                                .getString(
                                        R.string
                                                .display_rotation_camera_compat_toast_in_multi_window),
                        "");
        displayDeviceConfig.mProximitySensor = new SensorData();
        displayDeviceConfig.mTempSensor = new SensorData("SKIN", null);
        displayDeviceConfig.loadAutoBrightnessAvailableFromConfigXml();
        return displayDeviceConfig;
    }

    public static float[] getFloatArray(TypedArray typedArray) {
        int length = typedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = typedArray.getFloat(i, -1.0f);
        }
        typedArray.recycle();
        return fArr;
    }

    public final void constrainNitsAndBacklightArrays() {
        float f;
        float[] fArr = this.mRawBacklight;
        float f2 = fArr[0];
        float f3 = this.mBacklightMinimum;
        if (f2 <= f3) {
            float f4 = fArr[fArr.length - 1];
            float f5 = this.mBacklightMaximum;
            if (f4 >= f5 && f3 <= f5) {
                float[] fArr2 = new float[fArr.length];
                float[] fArr3 = new float[fArr.length];
                int i = 0;
                while (true) {
                    float[] fArr4 = this.mRawBacklight;
                    if (i >= fArr4.length - 1) {
                        i = 0;
                        break;
                    }
                    int i2 = i + 1;
                    if (fArr4[i2] > this.mBacklightMinimum) {
                        break;
                    } else {
                        i = i2;
                    }
                }
                boolean z = false;
                int i3 = 0;
                int i4 = i;
                while (true) {
                    float[] fArr5 = this.mRawBacklight;
                    if (i4 >= fArr5.length || z) {
                        break;
                    }
                    i3 = i4 - i;
                    float f6 = fArr5[i4];
                    float f7 = this.mBacklightMaximum;
                    boolean z2 = f6 >= f7 || i4 >= fArr5.length - 1;
                    if (i3 == 0) {
                        f6 = MathUtils.max(f6, this.mBacklightMinimum);
                        float[] fArr6 = this.mRawBacklight;
                        float f8 = fArr6[i4];
                        int i5 = i4 + 1;
                        float f9 = fArr6[i5];
                        float[] fArr7 = this.mRawNits;
                        f = MathUtils.map(f8, f9, fArr7[i4], fArr7[i5], f6);
                    } else if (z2) {
                        f6 = MathUtils.min(f6, f7);
                        int i6 = i4 - 1;
                        float[] fArr8 = this.mRawBacklight;
                        float f10 = fArr8[i6];
                        float f11 = fArr8[i4];
                        float[] fArr9 = this.mRawNits;
                        f = MathUtils.map(f10, f11, fArr9[i6], fArr9[i4], f6);
                    } else {
                        f = this.mRawNits[i4];
                    }
                    fArr3[i3] = f6;
                    fArr2[i3] = f;
                    i4++;
                    z = z2;
                }
                int i7 = i3 + 1;
                this.mBacklight = Arrays.copyOf(fArr3, i7);
                this.mNits = Arrays.copyOf(fArr2, i7);
                createBacklightConversionSplines();
                return;
            }
        }
        StringBuilder sb = new StringBuilder("Min or max values are invalid; raw min=");
        sb.append(this.mRawBacklight[0]);
        sb.append("; raw max=");
        float[] fArr10 = this.mRawBacklight;
        sb.append(fArr10[fArr10.length - 1]);
        sb.append("; backlight min=");
        sb.append(this.mBacklightMinimum);
        sb.append("; backlight max=");
        sb.append(this.mBacklightMaximum);
        throw new IllegalStateException(sb.toString());
    }

    public final void createBacklightConversionSplines() {
        float[] fArr;
        this.mBrightness = new float[this.mBacklight.length];
        int i = 0;
        while (true) {
            fArr = this.mBrightness;
            if (i >= fArr.length) {
                break;
            }
            float[] fArr2 = this.mBacklight;
            fArr[i] =
                    MathUtils.map(
                            fArr2[0],
                            fArr2[fArr2.length - 1],
                            this.mBacklightMinimum,
                            this.mBacklightMaximum,
                            fArr2[i]);
            i++;
        }
        this.mBrightnessToBacklightSpline =
                this.mInterpolationType == 1
                        ? Spline.createLinearSpline(fArr, this.mBacklight)
                        : Spline.createSpline(fArr, this.mBacklight);
        this.mBacklightToBrightnessSpline =
                this.mInterpolationType == 1
                        ? Spline.createLinearSpline(this.mBacklight, this.mBrightness)
                        : Spline.createSpline(this.mBacklight, this.mBrightness);
        this.mBacklightToNitsSpline =
                this.mInterpolationType == 1
                        ? Spline.createLinearSpline(this.mBacklight, this.mNits)
                        : Spline.createSpline(this.mBacklight, this.mNits);
        this.mNitsToBacklightSpline =
                this.mInterpolationType == 1
                        ? Spline.createLinearSpline(this.mNits, this.mBacklight)
                        : Spline.createSpline(this.mNits, this.mBacklight);
    }

    public final float[] getAutoBrightnessBrighteningLevels(int i, int i2) {
        DisplayBrightnessMappingConfig displayBrightnessMappingConfig =
                this.mDisplayBrightnessMapping;
        if (displayBrightnessMappingConfig == null) {
            return null;
        }
        float[] fArr =
                (float[])
                        ((HashMap) displayBrightnessMappingConfig.mBrightnessLevelsMap)
                                .get(
                                        DisplayBrightnessMappingConfig.autoBrightnessModeToString(i)
                                                + "_"
                                                + DisplayBrightnessMappingConfig
                                                        .autoBrightnessPresetToString(i2));
        if (fArr != null) {
            return fArr;
        }
        return (float[])
                ((HashMap) displayBrightnessMappingConfig.mBrightnessLevelsMap)
                        .get(
                                DisplayBrightnessMappingConfig.autoBrightnessModeToString(i)
                                        + "_"
                                        + AutoBrightnessSettingName.normal.getRawName());
    }

    public final float[] getAutoBrightnessBrighteningLevelsLux(int i, int i2) {
        DisplayBrightnessMappingConfig displayBrightnessMappingConfig =
                this.mDisplayBrightnessMapping;
        if (displayBrightnessMappingConfig == null) {
            return null;
        }
        float[] fArr =
                (float[])
                        ((HashMap) displayBrightnessMappingConfig.mBrightnessLevelsLuxMap)
                                .get(
                                        DisplayBrightnessMappingConfig.autoBrightnessModeToString(i)
                                                + "_"
                                                + DisplayBrightnessMappingConfig
                                                        .autoBrightnessPresetToString(i2));
        if (fArr != null) {
            return fArr;
        }
        return (float[])
                ((HashMap) displayBrightnessMappingConfig.mBrightnessLevelsLuxMap)
                        .get(
                                DisplayBrightnessMappingConfig.autoBrightnessModeToString(i)
                                        + "_"
                                        + AutoBrightnessSettingName.normal.getRawName());
    }

    public float[] getBacklight() {
        EvenDimmerBrightnessData evenDimmerBrightnessData = this.mEvenDimmerBrightnessData;
        return evenDimmerBrightnessData != null
                ? evenDimmerBrightnessData.mBacklight
                : this.mBacklight;
    }

    public final float getBacklightFromNits(float f) {
        EvenDimmerBrightnessData evenDimmerBrightnessData = this.mEvenDimmerBrightnessData;
        return evenDimmerBrightnessData != null
                ? evenDimmerBrightnessData.mNitsToBacklight.interpolate(f)
                : this.mNitsToBacklightSpline.interpolate(f);
    }

    public final float getBrightnessFromBacklight(float f) {
        EvenDimmerBrightnessData evenDimmerBrightnessData = this.mEvenDimmerBrightnessData;
        return evenDimmerBrightnessData != null
                ? evenDimmerBrightnessData.mBacklightToBrightness.interpolate(f)
                : this.mBacklightToBrightnessSpline.interpolate(f);
    }

    public final float getHdrBrightnessFromSdr(float f) {
        if (this.mSdrToHdrSpline == null) {
            return -1.0f;
        }
        EvenDimmerBrightnessData evenDimmerBrightnessData = this.mEvenDimmerBrightnessData;
        float interpolate =
                evenDimmerBrightnessData != null
                        ? evenDimmerBrightnessData.mBrightnessToBacklight.interpolate(f)
                        : this.mBrightnessToBacklightSpline.interpolate(f);
        float nitsFromBacklight = getNitsFromBacklight(interpolate);
        if (nitsFromBacklight == -1.0f) {
            return -1.0f;
        }
        float interpolate2 = this.mSdrToHdrSpline.interpolate(nitsFromBacklight);
        EvenDimmerBrightnessData evenDimmerBrightnessData2 = this.mEvenDimmerBrightnessData;
        if ((evenDimmerBrightnessData2 != null
                        ? evenDimmerBrightnessData2.mNitsToBacklight
                        : this.mNitsToBacklightSpline)
                == null) {
            return -1.0f;
        }
        float max =
                Math.max(
                        this.mBacklightMinimum,
                        Math.min(this.mBacklightMaximum, getBacklightFromNits(interpolate2)));
        float brightnessFromBacklight = getBrightnessFromBacklight(max);
        if (DEBUG) {
            Slog.d(
                    "DisplayDeviceConfig",
                    "getHdrBrightnessFromSdr: sdr brightness "
                            + f
                            + " backlight "
                            + interpolate
                            + " nits "
                            + nitsFromBacklight
                            + " hdrNits "
                            + interpolate2
                            + " hdrBacklight "
                            + max
                            + " hdrBrightness "
                            + brightnessFromBacklight);
        }
        return brightnessFromBacklight;
    }

    public final HighBrightnessModeData getHighBrightnessModeData() {
        HighBrightnessModeData highBrightnessModeData;
        if (!this.mIsHighBrightnessModeEnabled
                || (highBrightnessModeData = this.mHbmData) == null) {
            return null;
        }
        HighBrightnessModeData highBrightnessModeData2 = new HighBrightnessModeData();
        highBrightnessModeData2.minimumLux = highBrightnessModeData.minimumLux;
        highBrightnessModeData2.timeWindowMillis = highBrightnessModeData.timeWindowMillis;
        highBrightnessModeData2.timeMaxMillis = highBrightnessModeData.timeMaxMillis;
        highBrightnessModeData2.timeMinMillis = highBrightnessModeData.timeMinMillis;
        highBrightnessModeData2.transitionPoint = highBrightnessModeData.transitionPoint;
        highBrightnessModeData2.allowInLowPowerMode = highBrightnessModeData.allowInLowPowerMode;
        highBrightnessModeData2.minimumHdrPercentOfScreen =
                highBrightnessModeData.minimumHdrPercentOfScreen;
        return highBrightnessModeData2;
    }

    public final float getNitsFromBacklight(float f) {
        EvenDimmerBrightnessData evenDimmerBrightnessData = this.mEvenDimmerBrightnessData;
        if (evenDimmerBrightnessData != null) {
            if (evenDimmerBrightnessData.mBacklightToNits == null) {
                return -1.0f;
            }
            return this.mEvenDimmerBrightnessData.mBacklightToNits.interpolate(
                    Math.max(f, this.mBacklightMinimum));
        }
        if (this.mBacklightToNitsSpline != null) {
            return this.mBacklightToNitsSpline.interpolate(Math.max(f, this.mBacklightMinimum));
        }
        if (!this.mIsSimpleMappingStrategy) {
            Slog.wtf("DisplayDeviceConfig", "requesting nits when no mapping exists.");
        }
        return -1.0f;
    }

    public Map getRefreshRangeProfiles() {
        return this.mRefreshRateZoneProfiles;
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x0113 A[Catch: all -> 0x006f, TryCatch #2 {all -> 0x006f, blocks: (B:12:0x0033, B:14:0x0039, B:16:0x0044, B:17:0x004e, B:19:0x0059, B:21:0x0068, B:22:0x0072, B:24:0x0085, B:26:0x008b, B:27:0x0092, B:28:0x0099, B:30:0x00a6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ca, B:38:0x00e2, B:40:0x00ec, B:44:0x00f6, B:45:0x0101, B:47:0x0113, B:48:0x0119, B:50:0x011d, B:51:0x0123, B:54:0x0160, B:56:0x0164, B:57:0x0175, B:59:0x0181, B:60:0x019b, B:63:0x01a7, B:67:0x01a3, B:68:0x0188, B:69:0x0131, B:71:0x0135, B:72:0x013c, B:73:0x0149, B:75:0x014f, B:78:0x00fc, B:79:0x00c5, B:80:0x00ab, B:81:0x004b, B:82:0x01aa), top: B:11:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x011d A[Catch: all -> 0x006f, TryCatch #2 {all -> 0x006f, blocks: (B:12:0x0033, B:14:0x0039, B:16:0x0044, B:17:0x004e, B:19:0x0059, B:21:0x0068, B:22:0x0072, B:24:0x0085, B:26:0x008b, B:27:0x0092, B:28:0x0099, B:30:0x00a6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ca, B:38:0x00e2, B:40:0x00ec, B:44:0x00f6, B:45:0x0101, B:47:0x0113, B:48:0x0119, B:50:0x011d, B:51:0x0123, B:54:0x0160, B:56:0x0164, B:57:0x0175, B:59:0x0181, B:60:0x019b, B:63:0x01a7, B:67:0x01a3, B:68:0x0188, B:69:0x0131, B:71:0x0135, B:72:0x013c, B:73:0x0149, B:75:0x014f, B:78:0x00fc, B:79:0x00c5, B:80:0x00ab, B:81:0x004b, B:82:0x01aa), top: B:11:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0130  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0164 A[Catch: all -> 0x006f, TryCatch #2 {all -> 0x006f, blocks: (B:12:0x0033, B:14:0x0039, B:16:0x0044, B:17:0x004e, B:19:0x0059, B:21:0x0068, B:22:0x0072, B:24:0x0085, B:26:0x008b, B:27:0x0092, B:28:0x0099, B:30:0x00a6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ca, B:38:0x00e2, B:40:0x00ec, B:44:0x00f6, B:45:0x0101, B:47:0x0113, B:48:0x0119, B:50:0x011d, B:51:0x0123, B:54:0x0160, B:56:0x0164, B:57:0x0175, B:59:0x0181, B:60:0x019b, B:63:0x01a7, B:67:0x01a3, B:68:0x0188, B:69:0x0131, B:71:0x0135, B:72:0x013c, B:73:0x0149, B:75:0x014f, B:78:0x00fc, B:79:0x00c5, B:80:0x00ab, B:81:0x004b, B:82:0x01aa), top: B:11:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0181 A[Catch: all -> 0x006f, TryCatch #2 {all -> 0x006f, blocks: (B:12:0x0033, B:14:0x0039, B:16:0x0044, B:17:0x004e, B:19:0x0059, B:21:0x0068, B:22:0x0072, B:24:0x0085, B:26:0x008b, B:27:0x0092, B:28:0x0099, B:30:0x00a6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ca, B:38:0x00e2, B:40:0x00ec, B:44:0x00f6, B:45:0x0101, B:47:0x0113, B:48:0x0119, B:50:0x011d, B:51:0x0123, B:54:0x0160, B:56:0x0164, B:57:0x0175, B:59:0x0181, B:60:0x019b, B:63:0x01a7, B:67:0x01a3, B:68:0x0188, B:69:0x0131, B:71:0x0135, B:72:0x013c, B:73:0x0149, B:75:0x014f, B:78:0x00fc, B:79:0x00c5, B:80:0x00ab, B:81:0x004b, B:82:0x01aa), top: B:11:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:62:0x01a2  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01a3 A[Catch: all -> 0x006f, TryCatch #2 {all -> 0x006f, blocks: (B:12:0x0033, B:14:0x0039, B:16:0x0044, B:17:0x004e, B:19:0x0059, B:21:0x0068, B:22:0x0072, B:24:0x0085, B:26:0x008b, B:27:0x0092, B:28:0x0099, B:30:0x00a6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ca, B:38:0x00e2, B:40:0x00ec, B:44:0x00f6, B:45:0x0101, B:47:0x0113, B:48:0x0119, B:50:0x011d, B:51:0x0123, B:54:0x0160, B:56:0x0164, B:57:0x0175, B:59:0x0181, B:60:0x019b, B:63:0x01a7, B:67:0x01a3, B:68:0x0188, B:69:0x0131, B:71:0x0135, B:72:0x013c, B:73:0x0149, B:75:0x014f, B:78:0x00fc, B:79:0x00c5, B:80:0x00ab, B:81:0x004b, B:82:0x01aa), top: B:11:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0188 A[Catch: all -> 0x006f, TryCatch #2 {all -> 0x006f, blocks: (B:12:0x0033, B:14:0x0039, B:16:0x0044, B:17:0x004e, B:19:0x0059, B:21:0x0068, B:22:0x0072, B:24:0x0085, B:26:0x008b, B:27:0x0092, B:28:0x0099, B:30:0x00a6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ca, B:38:0x00e2, B:40:0x00ec, B:44:0x00f6, B:45:0x0101, B:47:0x0113, B:48:0x0119, B:50:0x011d, B:51:0x0123, B:54:0x0160, B:56:0x0164, B:57:0x0175, B:59:0x0181, B:60:0x019b, B:63:0x01a7, B:67:0x01a3, B:68:0x0188, B:69:0x0131, B:71:0x0135, B:72:0x013c, B:73:0x0149, B:75:0x014f, B:78:0x00fc, B:79:0x00c5, B:80:0x00ab, B:81:0x004b, B:82:0x01aa), top: B:11:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0131 A[Catch: all -> 0x006f, TryCatch #2 {all -> 0x006f, blocks: (B:12:0x0033, B:14:0x0039, B:16:0x0044, B:17:0x004e, B:19:0x0059, B:21:0x0068, B:22:0x0072, B:24:0x0085, B:26:0x008b, B:27:0x0092, B:28:0x0099, B:30:0x00a6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ca, B:38:0x00e2, B:40:0x00ec, B:44:0x00f6, B:45:0x0101, B:47:0x0113, B:48:0x0119, B:50:0x011d, B:51:0x0123, B:54:0x0160, B:56:0x0164, B:57:0x0175, B:59:0x0181, B:60:0x019b, B:63:0x01a7, B:67:0x01a3, B:68:0x0188, B:69:0x0131, B:71:0x0135, B:72:0x013c, B:73:0x0149, B:75:0x014f, B:78:0x00fc, B:79:0x00c5, B:80:0x00ab, B:81:0x004b, B:82:0x01aa), top: B:11:0x0033 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean initFromFile(java.io.File r10) {
        /*
            Method dump skipped, instructions count: 471
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.DisplayDeviceConfig.initFromFile(java.io.File):boolean");
    }

    public final void loadAutoBrightnessAvailableFromConfigXml() {
        if (this.mIsCoverDisplay) {
            this.mAutoBrightnessAvailable =
                    this.mContext
                            .getResources()
                            .getBoolean(R.bool.config_defaultAdasGnssLocationEnabled);
        } else {
            this.mAutoBrightnessAvailable =
                    this.mContext
                            .getResources()
                            .getBoolean(R.bool.config_batterySaverStickyBehaviourDisabled);
        }
    }

    public final void loadAutoBrightnessConfigValues(DisplayConfiguration displayConfiguration) {
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger bigInteger3;
        BigInteger bigInteger4;
        AutoBrightness autoBrightness = displayConfiguration.autoBrightness;
        if (autoBrightness == null
                || (bigInteger4 = autoBrightness.brighteningLightDebounceMillis) == null) {
            this.mAutoBrightnessBrighteningLightDebounce =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_burnInProtectionMaxRadius);
        } else {
            this.mAutoBrightnessBrighteningLightDebounce = bigInteger4.intValue();
        }
        if (autoBrightness == null
                || (bigInteger3 = autoBrightness.darkeningLightDebounceMillis) == null) {
            this.mAutoBrightnessDarkeningLightDebounce =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_burnInProtectionMaxVerticalOffset);
        } else {
            this.mAutoBrightnessDarkeningLightDebounce = bigInteger3.intValue();
        }
        if (autoBrightness == null
                || (bigInteger2 = autoBrightness.brighteningLightDebounceIdleMillis) == null) {
            this.mAutoBrightnessBrighteningLightDebounceIdle =
                    this.mAutoBrightnessBrighteningLightDebounce;
        } else {
            this.mAutoBrightnessBrighteningLightDebounceIdle = bigInteger2.intValue();
        }
        if (autoBrightness == null
                || (bigInteger = autoBrightness.darkeningLightDebounceIdleMillis) == null) {
            this.mAutoBrightnessDarkeningLightDebounceIdle =
                    this.mAutoBrightnessDarkeningLightDebounce;
        } else {
            this.mAutoBrightnessDarkeningLightDebounceIdle = bigInteger.intValue();
        }
        Context context = this.mContext;
        EvenDimmerBrightnessData evenDimmerBrightnessData = this.mEvenDimmerBrightnessData;
        this.mDisplayBrightnessMapping =
                new DisplayBrightnessMappingConfig(
                        context,
                        this.mFlags,
                        autoBrightness,
                        evenDimmerBrightnessData != null
                                ? evenDimmerBrightnessData.mBacklightToBrightness
                                : this.mBacklightToBrightnessSpline);
        this.mDdcAutoBrightnessAvailable = true;
        if (autoBrightness != null) {
            Boolean bool = autoBrightness.enabled;
            this.mDdcAutoBrightnessAvailable = bool == null ? false : bool.booleanValue();
        }
        this.mAutoBrightnessAvailable =
                this.mContext
                                .getResources()
                                .getBoolean(R.bool.config_batterySaverStickyBehaviourDisabled)
                        && this.mDdcAutoBrightnessAvailable;
    }

    public final void loadBrightnessChangeThresholds() {
        Resources resources = this.mContext.getResources();
        this.mScreenBrightnessHysteresis = null;
        this.mScreenBrightnessIdleHysteresis = null;
        this.mAmbientBrightnessHysteresis =
                SecHysteresisLevels.loadAmbientBrightnessConfig(resources);
        this.mAmbientBrightnessIdleHysteresis =
                SecHysteresisLevels.loadAmbientBrightnessConfig(resources);
        if (PowerManagerUtil.SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI) {
            this.mAmbientBrightnessThresholdsTouchHigh =
                    resources != null
                            ? new SecHysteresisLevels(
                                    resources.getIntArray(
                                            R.array
                                                    .config_face_acquire_vendor_biometricprompt_ignorelist),
                                    resources.getIntArray(
                                            R.array.config_face_acquire_vendor_enroll_ignorelist),
                                    resources.getIntArray(R.array.config_foldedDeviceStates),
                                    resources.getIntArray(R.array.config_fontManagerServiceCerts))
                            : null;
            this.mAmbientBrightnessThresholdsTouchLow =
                    resources != null
                            ? new SecHysteresisLevels(
                                    resources.getIntArray(
                                            R.array
                                                    .config_face_acquire_vendor_biometricprompt_ignorelist),
                                    resources.getIntArray(
                                            R.array.config_face_acquire_vendor_enroll_ignorelist),
                                    resources.getIntArray(R.array.config_forceQueryablePackages),
                                    resources.getIntArray(R.array.config_forceSlowJpegModeList))
                            : null;
        }
    }

    public final void loadBrightnessConstraintsFromConfigXml() {
        float f =
                this.mContext
                        .getResources()
                        .getFloat(R.dimen.conversation_badge_protrusion_group_expanded_face_pile);
        float max =
                Math.max(
                                IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT,
                                Resources.getSystem()
                                        .getInteger(
                                                !this.mIsCoverDisplay
                                                        ? R.integer
                                                                .config_vibratorControlServiceDumpSizeLimit
                                                        : R.integer
                                                                .config_defaultPowerStatsThrottlePeriodWifi))
                        / 255.0f;
        if (f == -2.0f || max == -2.0f) {
            this.mBacklightMinimum =
                    BrightnessSynchronizer.brightnessIntToFloat(
                            this.mContext
                                    .getResources()
                                    .getInteger(R.integer.config_wakeUpToLastStateTimeoutMillis));
            this.mBacklightMaximum = max;
        } else {
            this.mBacklightMinimum = f;
            this.mBacklightMaximum = max;
        }
    }

    public final void loadBrightnessDefaultFromConfigXml() {
        float f = this.mContext.getResources().getFloat(R.dimen.conversation_badge_protrusion);
        if (f == -2.0f) {
            this.mBrightnessDefault =
                    BrightnessSynchronizer.brightnessIntToFloat(
                            this.mContext
                                    .getResources()
                                    .getInteger(
                                            R.integer
                                                    .config_wait_for_datagram_sending_response_timeout_millis));
        } else {
            this.mBrightnessDefault = f;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadBrightnessMap(com.android.server.display.config.DisplayConfiguration r9) {
        /*
            r8 = this;
            com.android.server.display.config.NitsMap r9 = r9.screenBrightnessMap
            if (r9 != 0) goto L8
            r8.loadBrightnessMapFromConfigXml()
            return
        L8:
            java.util.List r0 = r9.point
            if (r0 != 0) goto L13
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r9.point = r0
        L13:
            java.util.List r0 = r9.point
            java.util.ArrayList r0 = (java.util.ArrayList) r0
            int r1 = r0.size()
            float[] r2 = new float[r1]
            float[] r1 = new float[r1]
            java.lang.String r9 = r9.interpolation
            boolean r3 = android.text.TextUtils.isEmpty(r9)
            r4 = 0
            java.lang.String r5 = "DisplayDeviceConfig"
            if (r3 == 0) goto L2c
        L2a:
            r9 = r4
            goto L49
        L2c:
            java.lang.String r3 = "linear"
            boolean r3 = r3.equals(r9)
            if (r3 == 0) goto L37
            r9 = 1
            goto L49
        L37:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r6 = "Unexpected Interpolation Type: "
            r3.<init>(r6)
            r3.append(r9)
            java.lang.String r9 = r3.toString()
            android.util.Slog.wtf(r5, r9)
            goto L2a
        L49:
            r8.mInterpolationType = r9
            java.util.Iterator r9 = r0.iterator()
        L4f:
            boolean r0 = r9.hasNext()
            if (r0 == 0) goto Lbc
            java.lang.Object r0 = r9.next()
            com.android.server.display.config.Point r0 = (com.android.server.display.config.Point) r0
            java.math.BigDecimal r3 = r0.nits
            float r3 = r3.floatValue()
            r2[r4] = r3
            java.math.BigDecimal r0 = r0.value
            float r0 = r0.floatValue()
            r1[r4] = r0
            if (r4 <= 0) goto Lb9
            r3 = r2[r4]
            int r6 = r4 + (-1)
            r7 = r2[r6]
            int r3 = (r3 > r7 ? 1 : (r3 == r7 ? 0 : -1))
            java.lang.String r7 = " < "
            if (r3 >= 0) goto L96
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "screenBrightnessMap must be non-decreasing, ignoring rest  of configuration. Nits: "
            r8.<init>(r9)
            r9 = r2[r4]
            r8.append(r9)
            r8.append(r7)
            r9 = r2[r6]
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.util.Slog.e(r5, r8)
            return
        L96:
            r3 = r1[r6]
            int r0 = (r0 > r3 ? 1 : (r0 == r3 ? 0 : -1))
            if (r0 >= 0) goto Lb9
            java.lang.StringBuilder r8 = new java.lang.StringBuilder
            java.lang.String r9 = "screenBrightnessMap must be non-decreasing, ignoring rest  of configuration. Value: "
            r8.<init>(r9)
            r9 = r1[r4]
            r8.append(r9)
            r8.append(r7)
            r9 = r1[r6]
            r8.append(r9)
            java.lang.String r8 = r8.toString()
            android.util.Slog.e(r5, r8)
            return
        Lb9:
            int r4 = r4 + 1
            goto L4f
        Lbc:
            r8.mRawNits = r2
            r8.mRawBacklight = r1
            r8.constrainNitsAndBacklightArrays()
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.DisplayDeviceConfig.loadBrightnessMap(com.android.server.display.config.DisplayConfiguration):void");
    }

    public final void loadBrightnessMapFromConfigXml() {
        Resources resources = this.mContext.getResources();
        float[] floatArray =
                BrightnessMappingStrategy.getFloatArray(resources.obtainTypedArray(17236304));
        int[] intArray = resources.getIntArray(17236303);
        int length = intArray.length;
        float[] fArr = new float[length];
        for (int i = 0; i < intArray.length; i++) {
            fArr[i] = BrightnessSynchronizer.brightnessIntToFloat(intArray[i]);
        }
        if (length == 0 || floatArray.length == 0) {
            this.mNits = null;
            this.mBacklight = null;
            float[] fArr2 = {
                FullScreenMagnificationGestureHandler.MAX_SCALE, this.mBacklightMaximum
            };
            this.mBrightnessToBacklightSpline = Spline.createSpline(fArr2, fArr2);
            this.mBacklightToBrightnessSpline = Spline.createSpline(fArr2, fArr2);
            this.mIsSimpleMappingStrategy = true;
            return;
        }
        StringBuilder sb = new StringBuilder("backlight min=");
        sb.append(intArray[0]);
        sb.append(" backlight max=");
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                sb, intArray[intArray.length - 1], "DisplayDeviceConfig");
        this.mRawNits = floatArray;
        this.mRawBacklight = fArr;
        if (BrightnessSynchronizer.floatEquals(fArr[0], this.mBacklightMinimum)
                && this.mRawBacklight[0] != this.mBacklightMinimum) {
            Slog.d(
                    "DisplayDeviceConfig",
                    "adjust backlight min boundary : "
                            + this.mRawBacklight[0]
                            + " "
                            + this.mBacklightMinimum);
            this.mRawBacklight[0] = this.mBacklightMinimum;
        }
        float[] fArr3 = this.mRawBacklight;
        if (BrightnessSynchronizer.floatEquals(fArr3[fArr3.length - 1], this.mBacklightMaximum)) {
            float[] fArr4 = this.mRawBacklight;
            if (fArr4[fArr4.length - 1] != this.mBacklightMaximum) {
                StringBuilder sb2 = new StringBuilder("adjust backlight max boundary : ");
                float[] fArr5 = this.mRawBacklight;
                sb2.append(fArr5[fArr5.length - 1]);
                sb2.append(" ");
                sb2.append(this.mBacklightMaximum);
                Slog.d("DisplayDeviceConfig", sb2.toString());
                float[] fArr6 = this.mRawBacklight;
                fArr6[fArr6.length - 1] = this.mBacklightMaximum;
            }
        }
        constrainNitsAndBacklightArrays();
    }

    public final void loadBrightnessRamps(DisplayConfiguration displayConfiguration) {
        BigDecimal bigDecimal = displayConfiguration.screenBrightnessRampFastDecrease;
        BigDecimal bigDecimal2 = displayConfiguration.screenBrightnessRampFastIncrease;
        BigDecimal bigDecimal3 = displayConfiguration.screenBrightnessRampSlowDecrease;
        BigDecimal bigDecimal4 = displayConfiguration.screenBrightnessRampSlowIncrease;
        if (bigDecimal == null
                || bigDecimal2 == null
                || bigDecimal3 == null
                || bigDecimal4 == null) {
            if (bigDecimal != null
                    || bigDecimal2 != null
                    || bigDecimal3 != null
                    || bigDecimal4 != null) {
                Slog.w(
                        "DisplayDeviceConfig",
                        "Per display brightness ramp values ignored because not all values are"
                            + " present in display device config");
            }
            loadBrightnessRampsFromConfigXml();
        } else {
            this.mBrightnessRampFastDecrease = bigDecimal.floatValue();
            this.mBrightnessRampFastIncrease = bigDecimal2.floatValue();
            this.mBrightnessRampSlowDecrease = bigDecimal3.floatValue();
            this.mBrightnessRampSlowIncrease = bigDecimal4.floatValue();
        }
        if (displayConfiguration.screenBrightnessRampIncreaseMaxMillis != null) {
            this.mBrightnessRampIncreaseMaxMillis = r0.intValue();
        }
        if (displayConfiguration.screenBrightnessRampDecreaseMaxMillis != null) {
            this.mBrightnessRampDecreaseMaxMillis = r0.intValue();
        }
        BigDecimal bigDecimal5 = displayConfiguration.screenBrightnessRampSlowDecreaseIdle;
        BigDecimal bigDecimal6 = displayConfiguration.screenBrightnessRampSlowIncreaseIdle;
        if (bigDecimal5 == null || bigDecimal6 == null) {
            if (bigDecimal5 != null || bigDecimal6 != null) {
                Slog.w(
                        "DisplayDeviceConfig",
                        "Per display idle brightness ramp values ignored because not all values are"
                            + " present in display device config");
            }
            this.mBrightnessRampSlowDecreaseIdle = this.mBrightnessRampSlowDecrease;
            this.mBrightnessRampSlowIncreaseIdle = this.mBrightnessRampSlowIncrease;
        } else {
            this.mBrightnessRampSlowDecreaseIdle = bigDecimal5.floatValue();
            this.mBrightnessRampSlowIncreaseIdle = bigDecimal6.floatValue();
        }
        if (displayConfiguration.screenBrightnessRampIncreaseMaxIdleMillis != null) {
            this.mBrightnessRampIncreaseMaxIdleMillis = r0.intValue();
        } else {
            this.mBrightnessRampIncreaseMaxIdleMillis = this.mBrightnessRampIncreaseMaxMillis;
        }
        if (displayConfiguration.screenBrightnessRampDecreaseMaxIdleMillis != null) {
            this.mBrightnessRampDecreaseMaxIdleMillis = r6.intValue();
        } else {
            this.mBrightnessRampDecreaseMaxIdleMillis = this.mBrightnessRampDecreaseMaxMillis;
        }
    }

    public final void loadBrightnessRampsFromConfigXml() {
        this.mBrightnessRampFastIncrease =
                BrightnessSynchronizer.brightnessIntToFloat(
                        this.mContext
                                .getResources()
                                .getInteger(R.integer.config_defaultDisplayDefaultColorMode));
        float brightnessIntToFloat =
                BrightnessSynchronizer.brightnessIntToFloat(
                        this.mContext
                                .getResources()
                                .getInteger(R.integer.config_defaultHapticFeedbackIntensity));
        this.mBrightnessRampSlowIncrease = brightnessIntToFloat;
        this.mBrightnessRampFastDecrease = this.mBrightnessRampFastIncrease;
        this.mBrightnessRampSlowDecrease = brightnessIntToFloat;
    }

    public final void loadDensityMapping(DisplayConfiguration displayConfiguration) {
        com.android.server.display.config.DensityMapping densityMapping =
                displayConfiguration.densityMapping;
        if (densityMapping == null) {
            return;
        }
        if (densityMapping.density == null) {
            densityMapping.density = new ArrayList();
        }
        ArrayList arrayList = (ArrayList) densityMapping.density;
        DensityMapping.Entry[] entryArr = new DensityMapping.Entry[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            Density density = (Density) arrayList.get(i);
            entryArr[i] =
                    new DensityMapping.Entry(
                            density.width.intValue(),
                            density.height.intValue(),
                            density.density.intValue());
        }
        this.mDensityMapping = new DensityMapping(entryArr);
    }

    public final void loadHighBrightnessModeData(DisplayConfiguration displayConfiguration) {
        HighBrightnessMode highBrightnessMode = displayConfiguration.highBrightnessMode;
        if (highBrightnessMode == null) {
            loadHighBrightnessModeDataFromConfigXml();
            return;
        }
        Boolean bool = highBrightnessMode.enabled;
        this.mIsHighBrightnessModeEnabled = bool == null ? false : bool.booleanValue();
        HighBrightnessModeData highBrightnessModeData = new HighBrightnessModeData();
        this.mHbmData = highBrightnessModeData;
        highBrightnessModeData.minimumLux = highBrightnessMode.minimumLux_all.floatValue();
        float floatValue = highBrightnessMode.transitionPoint_all.floatValue();
        if (floatValue >= this.mBacklightMaximum) {
            throw new IllegalArgumentException(
                    "HBM transition point invalid. "
                            + this.mHbmData.transitionPoint
                            + " is not less than "
                            + this.mBacklightMaximum);
        }
        this.mHbmData.transitionPoint = getBrightnessFromBacklight(floatValue);
        HbmTiming hbmTiming = highBrightnessMode.timing_all;
        this.mHbmData.timeWindowMillis = hbmTiming.timeWindowSecs_all.longValue() * 1000;
        this.mHbmData.timeMaxMillis = hbmTiming.timeMaxSecs_all.longValue() * 1000;
        this.mHbmData.timeMinMillis = hbmTiming.timeMinSecs_all.longValue() * 1000;
        HighBrightnessModeData highBrightnessModeData2 = this.mHbmData;
        Boolean bool2 = highBrightnessMode.allowInLowPowerMode_all;
        highBrightnessModeData2.allowInLowPowerMode = bool2 != null ? bool2.booleanValue() : false;
        RefreshRateRange refreshRateRange = highBrightnessMode.refreshRate_all;
        if (refreshRateRange != null) {
            ((ArrayList) this.mRefreshRateLimitations)
                    .add(
                            new DisplayManagerInternal.RefreshRateLimitation(
                                    1,
                                    refreshRateRange.minimum.floatValue(),
                                    refreshRateRange.maximum.floatValue()));
        }
        BigDecimal bigDecimal = highBrightnessMode.minimumHdrPercentOfScreen_all;
        if (bigDecimal != null) {
            this.mHbmData.minimumHdrPercentOfScreen = bigDecimal.floatValue();
            float f = this.mHbmData.minimumHdrPercentOfScreen;
            if (f > 1.0f || f < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                Slog.w(
                        "DisplayDeviceConfig",
                        "Invalid minimum HDR percent of screen: "
                                + String.valueOf(this.mHbmData.minimumHdrPercentOfScreen));
                this.mHbmData.minimumHdrPercentOfScreen = 0.5f;
            }
        } else {
            this.mHbmData.minimumHdrPercentOfScreen = 0.5f;
        }
        this.mSdrToHdrSpline = loadSdrHdrMapFromConfigXml();
    }

    public final void loadHighBrightnessModeDataFromConfigXml() {
        boolean z = this.mBacklightMaximum > 1.0f;
        this.mIsHighBrightnessModeEnabled = z;
        if (z) {
            float integer =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_burnInProtectionMinHorizontalOffset);
            HighBrightnessModeData highBrightnessModeData = new HighBrightnessModeData();
            highBrightnessModeData.minimumLux = integer;
            highBrightnessModeData.transitionPoint = 1.0f;
            highBrightnessModeData.timeWindowMillis = 0L;
            highBrightnessModeData.timeMaxMillis = 0L;
            highBrightnessModeData.timeMinMillis = 0L;
            highBrightnessModeData.allowInLowPowerMode = true;
            highBrightnessModeData.minimumHdrPercentOfScreen = 0.5f;
            this.mHbmData = highBrightnessModeData;
            this.mSdrToHdrSpline = loadSdrHdrMapFromConfigXml();
        }
    }

    public final void loadIdleScreenRefreshRateTimeoutConfigs(
            DisplayConfiguration displayConfiguration) {
        IdleScreenRefreshRateTimeout idleScreenRefreshRateTimeout;
        if (!this.mFlags.mIdleScreenRefreshRateTimeout.isEnabled()
                || displayConfiguration == null
                || (idleScreenRefreshRateTimeout =
                                displayConfiguration.idleScreenRefreshRateTimeout)
                        == null) {
            return;
        }
        IdleScreenRefreshRateTimeoutLuxThresholds idleScreenRefreshRateTimeoutLuxThresholds =
                idleScreenRefreshRateTimeout.luxThresholds;
        if (idleScreenRefreshRateTimeoutLuxThresholds != null) {
            if (idleScreenRefreshRateTimeoutLuxThresholds.point == null) {
                idleScreenRefreshRateTimeoutLuxThresholds.point = new ArrayList();
            }
            Iterator it = ((ArrayList) idleScreenRefreshRateTimeoutLuxThresholds.point).iterator();
            int i = -1;
            while (it.hasNext()) {
                IdleScreenRefreshRateTimeoutLuxThresholdPoint
                        idleScreenRefreshRateTimeoutLuxThresholdPoint =
                                (IdleScreenRefreshRateTimeoutLuxThresholdPoint) it.next();
                int intValue = idleScreenRefreshRateTimeoutLuxThresholdPoint.lux.intValue();
                if (i >= intValue) {
                    throw new RuntimeException(
                            "Lux values should be in ascending order in the idle screen refresh"
                                + " rate timeout config");
                }
                if (idleScreenRefreshRateTimeoutLuxThresholdPoint.timeout.intValue() < 0) {
                    throw new RuntimeException(
                            "The timeout value cannot be negative in idle screen refresh rate"
                                + " timeout config");
                }
                i = intValue;
            }
        }
        IdleScreenRefreshRateTimeoutLuxThresholds idleScreenRefreshRateTimeoutLuxThresholds2 =
                displayConfiguration.idleScreenRefreshRateTimeout.luxThresholds;
        if (idleScreenRefreshRateTimeoutLuxThresholds2.point == null) {
            idleScreenRefreshRateTimeoutLuxThresholds2.point = new ArrayList();
        }
        this.mIdleScreenRefreshRateTimeoutLuxThresholds =
                idleScreenRefreshRateTimeoutLuxThresholds2.point;
    }

    public final void loadLuxThrottling(DisplayConfiguration displayConfiguration) {
        LuxThrottling luxThrottling = displayConfiguration.luxThrottling;
        if (luxThrottling != null) {
            HighBrightnessMode highBrightnessMode = displayConfiguration.highBrightnessMode;
            float floatValue =
                    highBrightnessMode != null
                            ? highBrightnessMode.transitionPoint_all.floatValue()
                            : 1.0f;
            if (luxThrottling.brightnessLimitMap == null) {
                luxThrottling.brightnessLimitMap = new ArrayList();
            }
            Iterator it = ((ArrayList) luxThrottling.brightnessLimitMap).iterator();
            while (it.hasNext()) {
                BrightnessLimitMap brightnessLimitMap = (BrightnessLimitMap) it.next();
                PredefinedBrightnessLimitNames predefinedBrightnessLimitNames =
                        brightnessLimitMap.type;
                int ordinal = predefinedBrightnessLimitNames.ordinal();
                BrightnessLimitMapType brightnessLimitMapType =
                        ordinal != 0
                                ? ordinal != 1 ? null : BrightnessLimitMapType.ADAPTIVE
                                : BrightnessLimitMapType.DEFAULT;
                if (brightnessLimitMapType == null) {
                    Slog.wtf(
                            "DisplayDeviceConfig",
                            "Invalid NBM config: unsupported map type="
                                    + predefinedBrightnessLimitNames);
                } else if (((HashMap) this.mLuxThrottlingData)
                        .containsKey(brightnessLimitMapType)) {
                    Slog.wtf(
                            "DisplayDeviceConfig",
                            "Invalid NBM config: duplicate map type=" + brightnessLimitMapType);
                } else {
                    HashMap hashMap = new HashMap();
                    for (NonNegativeFloatToFloatPoint nonNegativeFloatToFloatPoint :
                            brightnessLimitMap.map.getPoint()) {
                        float floatValue2 = nonNegativeFloatToFloatPoint.first.floatValue();
                        float floatValue3 = nonNegativeFloatToFloatPoint.second.floatValue();
                        if (floatValue3 > floatValue) {
                            Slog.wtf(
                                    "DisplayDeviceConfig",
                                    "Invalid NBM config: maxBrightness is greater than"
                                        + " hbm.transitionPoint. type="
                                            + predefinedBrightnessLimitNames
                                            + "; lux="
                                            + floatValue2
                                            + "; maxBrightness="
                                            + floatValue3);
                        } else if (hashMap.containsKey(Float.valueOf(floatValue2))) {
                            Slog.wtf(
                                    "DisplayDeviceConfig",
                                    "Invalid NBM config: duplicate lux key. type="
                                            + predefinedBrightnessLimitNames
                                            + "; lux="
                                            + floatValue2);
                        } else {
                            hashMap.put(
                                    Float.valueOf(floatValue2),
                                    Float.valueOf(getBrightnessFromBacklight(floatValue3)));
                        }
                    }
                    if (!hashMap.isEmpty()) {
                        ((HashMap) this.mLuxThrottlingData).put(brightnessLimitMapType, hashMap);
                    }
                }
            }
        }
    }

    public final void loadPowerThrottlingConfigData(DisplayConfiguration displayConfiguration) {
        PowerThrottlingConfig powerThrottlingConfig = displayConfiguration.powerThrottlingConfig;
        if (powerThrottlingConfig == null) {
            return;
        }
        if (powerThrottlingConfig.powerThrottlingMap == null) {
            powerThrottlingConfig.powerThrottlingMap = new ArrayList();
        }
        List list = powerThrottlingConfig.powerThrottlingMap;
        if (list != null) {
            ArrayList arrayList = (ArrayList) list;
            if (!arrayList.isEmpty()) {
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    PowerThrottlingMap powerThrottlingMap = (PowerThrottlingMap) it.next();
                    if (powerThrottlingMap.powerThrottlingPoint == null) {
                        powerThrottlingMap.powerThrottlingPoint = new ArrayList();
                    }
                    ArrayList arrayList2 = (ArrayList) powerThrottlingMap.powerThrottlingPoint;
                    ArrayList arrayList3 = new ArrayList(arrayList2.size());
                    Iterator it2 = arrayList2.iterator();
                    while (true) {
                        if (it2.hasNext()) {
                            PowerThrottlingPoint powerThrottlingPoint =
                                    (PowerThrottlingPoint) it2.next();
                            ThermalStatus thermalStatus = powerThrottlingPoint.thermalStatus;
                            if (thermalStatus == null) {
                                break;
                            }
                            switch (thermalStatus) {
                                case EF6:
                                case EF15:
                                case EF24:
                                case EF33:
                                case EF42:
                                case EF51:
                                case EF60:
                                    arrayList3.add(
                                            new PowerThrottlingData.ThrottlingLevel(
                                                    powerThrottlingPoint.powerQuotaMilliWatts
                                                            .floatValue(),
                                                    convertThermalStatus(thermalStatus)));
                            }
                        } else {
                            String str = powerThrottlingMap.id;
                            if (str == null) {
                                str = "default";
                            }
                            if (((HashMap) this.mPowerThrottlingDataMapByThrottlingId)
                                    .containsKey(str)) {
                                throw new RuntimeException(
                                        XmlUtils$$ExternalSyntheticOutline0.m(
                                                "Power throttling data with ID ",
                                                str,
                                                " already exists"));
                            }
                            ((HashMap) this.mPowerThrottlingDataMapByThrottlingId)
                                    .put(str, PowerThrottlingData.create(arrayList3));
                        }
                    }
                }
                this.mPowerThrottlingConfigData =
                        new PowerThrottlingConfigData(
                                powerThrottlingConfig.brightnessLowestCapAllowed.floatValue(),
                                powerThrottlingConfig.pollingWindowMillis.intValue());
                return;
            }
        }
        Slog.i("DisplayDeviceConfig", "No power throttling map found");
    }

    public final void loadRefreshRateSetting(DisplayConfiguration displayConfiguration) {
        RefreshRateZoneProfiles refreshRateZoneProfiles;
        RefreshRateConfigs refreshRateConfigs =
                displayConfiguration == null ? null : displayConfiguration.refreshRate;
        BlockingZoneConfig blockingZoneConfig =
                refreshRateConfigs == null ? null : refreshRateConfigs.lowerBlockingZoneConfigs;
        BlockingZoneConfig blockingZoneConfig2 =
                refreshRateConfigs != null ? refreshRateConfigs.higherBlockingZoneConfigs : null;
        if (blockingZoneConfig != null) {
            this.mLowBlockingZoneThermalMapId = blockingZoneConfig.refreshRateThermalThrottlingId;
        }
        if (blockingZoneConfig == null) {
            this.mDefaultLowBlockingZoneRefreshRate =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_dozeWakeLockScreenDebounce);
        } else {
            this.mDefaultLowBlockingZoneRefreshRate =
                    blockingZoneConfig.defaultRefreshRate.intValue();
        }
        if (blockingZoneConfig == null) {
            int[] intArray =
                    this.mContext
                            .getResources()
                            .getIntArray(R.array.config_reduceBrightColorsCoefficients);
            int[] intArray2 =
                    this.mContext
                            .getResources()
                            .getIntArray(R.array.config_mainBuiltInDisplayWaterfallCutout);
            if (intArray == null || intArray2 == null || intArray.length != intArray2.length) {
                throw new RuntimeException(
                        "display low brightness threshold array and ambient brightness threshold"
                            + " array have different length: lowDisplayBrightnessThresholdsInt="
                                + Arrays.toString(intArray)
                                + ", lowAmbientBrightnessThresholdsInt="
                                + Arrays.toString(intArray2));
            }
            this.mLowDisplayBrightnessThresholds =
                    DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(intArray);
            this.mLowAmbientBrightnessThresholds =
                    DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(intArray2);
        } else {
            BlockingZoneThreshold blockingZoneThreshold = blockingZoneConfig.blockingZoneThreshold;
            if (blockingZoneThreshold.displayBrightnessPoint == null) {
                blockingZoneThreshold.displayBrightnessPoint = new ArrayList();
            }
            ArrayList arrayList = (ArrayList) blockingZoneThreshold.displayBrightnessPoint;
            int size = arrayList.size();
            this.mLowDisplayBrightnessThresholds = new float[size];
            this.mLowAmbientBrightnessThresholds = new float[size];
            for (int i = 0; i < size; i++) {
                float floatValue = ((DisplayBrightnessPoint) arrayList.get(i)).nits.floatValue();
                if (floatValue < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    this.mLowDisplayBrightnessThresholds[i] = floatValue;
                } else {
                    this.mLowDisplayBrightnessThresholds[i] =
                            getBrightnessFromBacklight(getBacklightFromNits(floatValue));
                }
                this.mLowAmbientBrightnessThresholds[i] =
                        ((DisplayBrightnessPoint) arrayList.get(i)).lux.floatValue();
            }
        }
        if (blockingZoneConfig2 != null) {
            this.mHighBlockingZoneThermalMapId = blockingZoneConfig2.refreshRateThermalThrottlingId;
        }
        if (blockingZoneConfig2 == null) {
            this.mDefaultHighBlockingZoneRefreshRate =
                    this.mContext
                            .getResources()
                            .getInteger(R.integer.config_lowPowerStandbyNonInteractiveTimeout);
        } else {
            this.mDefaultHighBlockingZoneRefreshRate =
                    blockingZoneConfig2.defaultRefreshRate.intValue();
        }
        if (blockingZoneConfig2 == null) {
            int[] intArray3 =
                    this.mContext.getResources().getIntArray(R.array.vendor_cross_profile_apps);
            int[] intArray4 =
                    this.mContext.getResources().getIntArray(R.array.unloggable_phone_numbers);
            if (intArray3 == null || intArray4 == null || intArray3.length != intArray4.length) {
                throw new RuntimeException(
                        "display high brightness threshold array and ambient brightness threshold"
                            + " array have different length: highDisplayBrightnessThresholdsInt="
                                + Arrays.toString(intArray3)
                                + ", highAmbientBrightnessThresholdsInt="
                                + Arrays.toString(intArray4));
            }
            this.mHighDisplayBrightnessThresholds =
                    DeviceConfigParsingUtils.displayBrightnessThresholdsIntToFloat(intArray3);
            this.mHighAmbientBrightnessThresholds =
                    DeviceConfigParsingUtils.ambientBrightnessThresholdsIntToFloat(intArray4);
        } else {
            BlockingZoneThreshold blockingZoneThreshold2 =
                    blockingZoneConfig2.blockingZoneThreshold;
            if (blockingZoneThreshold2.displayBrightnessPoint == null) {
                blockingZoneThreshold2.displayBrightnessPoint = new ArrayList();
            }
            ArrayList arrayList2 = (ArrayList) blockingZoneThreshold2.displayBrightnessPoint;
            int size2 = arrayList2.size();
            this.mHighDisplayBrightnessThresholds = new float[size2];
            this.mHighAmbientBrightnessThresholds = new float[size2];
            for (int i2 = 0; i2 < size2; i2++) {
                float floatValue2 = ((DisplayBrightnessPoint) arrayList2.get(i2)).nits.floatValue();
                if (floatValue2 < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    this.mHighDisplayBrightnessThresholds[i2] = floatValue2;
                } else {
                    this.mHighDisplayBrightnessThresholds[i2] =
                            getBrightnessFromBacklight(getBacklightFromNits(floatValue2));
                }
                this.mHighAmbientBrightnessThresholds[i2] =
                        ((DisplayBrightnessPoint) arrayList2.get(i2)).lux.floatValue();
            }
        }
        if (refreshRateConfigs == null
                || (refreshRateZoneProfiles = refreshRateConfigs.refreshRateZoneProfiles) == null) {
            return;
        }
        if (refreshRateZoneProfiles.refreshRateZoneProfile == null) {
            refreshRateZoneProfiles.refreshRateZoneProfile = new ArrayList();
        }
        Iterator it = ((ArrayList) refreshRateZoneProfiles.refreshRateZoneProfile).iterator();
        while (it.hasNext()) {
            RefreshRateZone refreshRateZone = (RefreshRateZone) it.next();
            RefreshRateRange refreshRateRange = refreshRateZone.refreshRateRange;
            ((HashMap) this.mRefreshRateZoneProfiles)
                    .put(
                            refreshRateZone.id,
                            new SurfaceControl.RefreshRateRange(
                                    refreshRateRange.minimum.floatValue(),
                                    refreshRateRange.maximum.floatValue()));
        }
    }

    public final Spline loadSdrHdrMapFromConfigXml() {
        float[] floatArray = getFloatArray(this.mContext.getResources().obtainTypedArray(17236309));
        float[] floatArray2 =
                getFloatArray(this.mContext.getResources().obtainTypedArray(17236308));
        if (this.mIsCoverDisplay) {
            float[] floatArray3 =
                    getFloatArray(
                            this.mContext
                                    .getResources()
                                    .obtainTypedArray(
                                            R.array.config_secondaryBuiltInDisplayWaterfallCutout));
            float[] floatArray4 =
                    getFloatArray(
                            this.mContext
                                    .getResources()
                                    .obtainTypedArray(
                                            R.array
                                                    .config_secondaryBuiltInDisplayCutoutSideOverride));
            if (floatArray3.length != 0 && floatArray4.length != 0) {
                Slog.d(
                        "DisplayDeviceConfig",
                        "loadSdrHdrMapFromConfigXml: use seperate config for cover display");
                floatArray = (float[]) floatArray3.clone();
                floatArray2 = (float[]) floatArray4.clone();
            }
        }
        if (floatArray.length == 0 && floatArray2.length == 0) {
            Slog.d("DisplayDeviceConfig", "loadSdrHdrMapFromConfigXml: not support");
            return null;
        }
        if (floatArray.length != floatArray2.length) {
            StringBuilder sb = new StringBuilder("loadSdrHdrMapFromConfigXml: wrong length: ");
            sb.append(floatArray.length);
            sb.append(", ");
            HeapdumpWatcher$$ExternalSyntheticOutline0.m(
                    sb, floatArray2.length, "DisplayDeviceConfig");
            return null;
        }
        for (int i = 0; i < floatArray.length; i++) {
            if (i > 0) {
                float f = floatArray[i];
                int i2 = i - 1;
                if (f < floatArray[i2]) {
                    Slog.e(
                            "DisplayDeviceConfig",
                            "loadSdrHdrMapFromConfigXml: sdrHdrRatioMap must be non-decreasing,"
                                + " ignoring rest  of configuration. sdr nits: "
                                    + floatArray[i]
                                    + " < "
                                    + floatArray[i2]);
                    return null;
                }
                if (f < FullScreenMagnificationGestureHandler.MAX_SCALE
                        || floatArray2[i] < FullScreenMagnificationGestureHandler.MAX_SCALE) {
                    StringBuilder m =
                            BatteryService$$ExternalSyntheticOutline0.m(
                                    i, "loadSdrHdrMapFromConfigXml: invalid value: [", "] ");
                    m.append(floatArray[i]);
                    m.append(", ");
                    m.append(floatArray2[i]);
                    Slog.w("DisplayDeviceConfig", m.toString());
                    return null;
                }
            }
        }
        return Spline.createLinearSpline(floatArray, floatArray2);
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void loadThermalThrottlingConfig(
            com.android.server.display.config.DisplayConfiguration r10) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.display.DisplayDeviceConfig.loadThermalThrottlingConfig(com.android.server.display.config.DisplayConfiguration):void");
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("DisplayDeviceConfig{mLoadedFrom=");
        sb.append(this.mLoadedFrom);
        sb.append(", mBacklightMinimum=");
        sb.append(this.mBacklightMinimum);
        sb.append(", mBacklightMaximum=");
        sb.append(this.mBacklightMaximum);
        sb.append(", mBrightnessDefault=");
        sb.append(this.mBrightnessDefault);
        sb.append(", mQuirks=");
        sb.append(this.mQuirks);
        sb.append(", mIsHighBrightnessModeEnabled=");
        sb.append(this.mIsHighBrightnessModeEnabled);
        sb.append("\nmLuxThrottlingData=");
        sb.append(this.mLuxThrottlingData);
        sb.append(", mHbmData=");
        sb.append(this.mHbmData);
        sb.append(", mSdrToHdrSpline=");
        sb.append(this.mSdrToHdrSpline);
        sb.append(", mThermalBrightnessThrottlingDataMapByThrottlingId=");
        sb.append(this.mThermalBrightnessThrottlingDataMapByThrottlingId);
        sb.append("\n, mPowerThrottlingDataMapByThrottlingId=");
        sb.append(this.mPowerThrottlingDataMapByThrottlingId);
        sb.append("\nmBrightnessRampFastDecrease=");
        sb.append(this.mBrightnessRampFastDecrease);
        sb.append(", mBrightnessRampFastIncrease=");
        sb.append(this.mBrightnessRampFastIncrease);
        sb.append(", mBrightnessRampSlowDecrease=");
        sb.append(this.mBrightnessRampSlowDecrease);
        sb.append(", mBrightnessRampSlowIncrease=");
        sb.append(this.mBrightnessRampSlowIncrease);
        sb.append(", mBrightnessRampSlowDecreaseIdle=");
        sb.append(this.mBrightnessRampSlowDecreaseIdle);
        sb.append(", mBrightnessRampSlowIncreaseIdle=");
        sb.append(this.mBrightnessRampSlowIncreaseIdle);
        sb.append(", mBrightnessRampDecreaseMaxMillis=");
        sb.append(this.mBrightnessRampDecreaseMaxMillis);
        sb.append(", mBrightnessRampIncreaseMaxMillis=");
        sb.append(this.mBrightnessRampIncreaseMaxMillis);
        sb.append(", mBrightnessRampDecreaseMaxIdleMillis=");
        sb.append(this.mBrightnessRampDecreaseMaxIdleMillis);
        sb.append(", mBrightnessRampIncreaseMaxIdleMillis=");
        sb.append(this.mBrightnessRampIncreaseMaxIdleMillis);
        sb.append("\nmAmbientHorizonLong=");
        sb.append(this.mAmbientHorizonLong);
        sb.append(", mAmbientHorizonShort=");
        sb.append(this.mAmbientHorizonShort);
        sb.append("\nmAmbientBrightnessHysteresis=");
        sb.append(this.mAmbientBrightnessHysteresis);
        sb.append("\nmAmbientIdleHysteresis=");
        sb.append(this.mAmbientBrightnessIdleHysteresis);
        sb.append("\nmScreenBrightnessHysteresis=");
        sb.append(this.mScreenBrightnessHysteresis);
        sb.append("\nmScreenBrightnessIdleHysteresis=");
        sb.append(this.mScreenBrightnessIdleHysteresis);
        sb.append("\nmAmbientLightSensor=");
        sb.append(this.mAmbientLightSensor);
        sb.append(", mScreenOffBrightnessSensor=");
        sb.append(this.mScreenOffBrightnessSensor);
        sb.append(", mProximitySensor=");
        sb.append(this.mProximitySensor);
        sb.append(", mTempSensor=");
        sb.append(this.mTempSensor);
        sb.append(", mRefreshRateLimitations= ");
        sb.append(Arrays.toString(((ArrayList) this.mRefreshRateLimitations).toArray()));
        sb.append(", mDensityMapping= ");
        sb.append(this.mDensityMapping);
        sb.append(", mAutoBrightnessBrighteningLightDebounce= ");
        sb.append(this.mAutoBrightnessBrighteningLightDebounce);
        sb.append(", mAutoBrightnessDarkeningLightDebounce= ");
        sb.append(this.mAutoBrightnessDarkeningLightDebounce);
        sb.append(", mAutoBrightnessBrighteningLightDebounceIdle= ");
        sb.append(this.mAutoBrightnessBrighteningLightDebounceIdle);
        sb.append(", mAutoBrightnessDarkeningLightDebounceIdle= ");
        sb.append(this.mAutoBrightnessDarkeningLightDebounceIdle);
        sb.append(", mDisplayBrightnessMapping= ");
        sb.append(this.mDisplayBrightnessMapping);
        sb.append(", mDdcAutoBrightnessAvailable= ");
        sb.append(this.mDdcAutoBrightnessAvailable);
        sb.append(", mAutoBrightnessAvailable= ");
        sb.append(this.mAutoBrightnessAvailable);
        sb.append("\nmDefaultLowBlockingZoneRefreshRate= ");
        sb.append(this.mDefaultLowBlockingZoneRefreshRate);
        sb.append(", mDefaultHighBlockingZoneRefreshRate= ");
        sb.append(this.mDefaultHighBlockingZoneRefreshRate);
        sb.append(", mRefreshRateData= ");
        sb.append(this.mRefreshRateData);
        sb.append(", mRefreshRateZoneProfiles= ");
        sb.append(this.mRefreshRateZoneProfiles);
        sb.append(", mRefreshRateThrottlingMap= ");
        sb.append(this.mRefreshRateThrottlingMap);
        sb.append(", mLowBlockingZoneThermalMapId= ");
        sb.append(this.mLowBlockingZoneThermalMapId);
        sb.append(", mHighBlockingZoneThermalMapId= ");
        sb.append(this.mHighBlockingZoneThermalMapId);
        sb.append("\nmLowDisplayBrightnessThresholds= ");
        sb.append(Arrays.toString(this.mLowDisplayBrightnessThresholds));
        sb.append(", mLowAmbientBrightnessThresholds= ");
        sb.append(Arrays.toString(this.mLowAmbientBrightnessThresholds));
        sb.append(", mHighDisplayBrightnessThresholds= ");
        sb.append(Arrays.toString(this.mHighDisplayBrightnessThresholds));
        sb.append(", mHighAmbientBrightnessThresholds= ");
        sb.append(Arrays.toString(this.mHighAmbientBrightnessThresholds));
        sb.append("\nmScreenOffBrightnessSensorValueToLux= ");
        sb.append(Arrays.toString(this.mScreenOffBrightnessSensorValueToLux));
        sb.append("\nmUsiVersion= ");
        sb.append(this.mHostUsiVersion);
        sb.append("\nmHdrBrightnessData= ");
        sb.append(this.mHdrBrightnessData);
        sb.append("\nmBrightnessCapForWearBedtimeMode= ");
        sb.append(this.mBrightnessCapForWearBedtimeMode);
        sb.append("\nmEvenDimmerBrightnessData:");
        EvenDimmerBrightnessData evenDimmerBrightnessData = this.mEvenDimmerBrightnessData;
        sb.append(evenDimmerBrightnessData != null ? evenDimmerBrightnessData.toString() : "null");
        sb.append("\nmVrrSupported= ");
        sb.append(this.mVrrSupportEnabled);
        sb.append("\n, mUseSurfaceControlBrightness=");
        return OptionalBool$$ExternalSyntheticOutline0.m(
                "}", sb, this.mUseSurfaceControlBrightness);
    }
}
