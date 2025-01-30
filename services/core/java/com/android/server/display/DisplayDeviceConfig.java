package com.android.server.display;

import android.R;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.hardware.display.DisplayManagerInternal;
import android.hardware.input.HostUsiVersion;
import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import android.os.Environment;
import android.text.TextUtils;
import android.util.MathUtils;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.Spline;
import android.view.SurfaceControl;
import com.android.internal.display.BrightnessSynchronizer;
import com.android.server.display.DensityMapping;
import com.android.server.display.config.AutoBrightness;
import com.android.server.display.config.BlockingZoneConfig;
import com.android.server.display.config.BrightnessThresholds;
import com.android.server.display.config.BrightnessThrottlingMap;
import com.android.server.display.config.BrightnessThrottlingPoint;
import com.android.server.display.config.Density;
import com.android.server.display.config.DisplayBrightnessPoint;
import com.android.server.display.config.DisplayConfiguration;
import com.android.server.display.config.DisplayQuirks;
import com.android.server.display.config.HbmTiming;
import com.android.server.display.config.HighBrightnessMode;
import com.android.server.display.config.IntegerArray;
import com.android.server.display.config.NitsMap;
import com.android.server.display.config.Point;
import com.android.server.display.config.RefreshRateConfigs;
import com.android.server.display.config.RefreshRateRange;
import com.android.server.display.config.RefreshRateThrottlingMap;
import com.android.server.display.config.RefreshRateThrottlingPoint;
import com.android.server.display.config.RefreshRateZone;
import com.android.server.display.config.SensorDetails;
import com.android.server.display.config.ThermalStatus;
import com.android.server.display.config.ThermalThrottling;
import com.android.server.display.config.ThresholdPoint;
import com.android.server.display.config.UsiVersion;
import com.android.server.display.config.XmlParser;
import com.android.server.power.PowerManagerUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class DisplayDeviceConfig {
    static final float HDR_PERCENT_OF_SCREEN_REQUIRED_DEFAULT = 0.5f;
    public float[] mAmbientBrighteningLevels;
    public float[] mAmbientBrighteningLevelsIdle;
    public float[] mAmbientBrighteningPercentages;
    public float[] mAmbientBrighteningPercentagesIdle;
    public float[] mAmbientDarkeningLevels;
    public float[] mAmbientDarkeningLevelsIdle;
    public float[] mAmbientDarkeningPercentages;
    public float[] mAmbientDarkeningPercentagesIdle;
    public boolean mAutoBrightnessAvailable;
    public long mAutoBrightnessBrighteningLightDebounce;
    public long mAutoBrightnessDarkeningLightDebounce;
    public float[] mBacklight;
    public Spline mBacklightToBrightnessSpline;
    public Spline mBacklightToNitsSpline;
    public float[] mBrightness;
    public float[] mBrightnessLevelsLux;
    public float[] mBrightnessLevelsNits;
    public Spline mBrightnessToBacklightSpline;
    public final Context mContext;
    public boolean mDdcAutoBrightnessAvailable;
    public int mDefaultHighBlockingZoneRefreshRate;
    public int mDefaultLowBlockingZoneRefreshRate;
    public int mDefaultPeakRefreshRate;
    public int mDefaultRefreshRate;
    public int mDefaultRefreshRateInHbmHdr;
    public int mDefaultRefreshRateInHbmSunlight;
    public DensityMapping mDensityMapping;
    public HighBrightnessModeData mHbmData;
    public int[] mHighAmbientBrightnessThresholds;
    public int[] mHighDisplayBrightnessThresholds;
    public HostUsiVersion mHostUsiVersion;
    public int mInterpolationType;
    public boolean mIsCoverDisplay;
    public boolean mIsFirstDisplay;
    public boolean mIsHighBrightnessModeEnabled;
    public boolean mIsSimpleMappingStrategy;
    public String mLoadedFrom;
    public int[] mLowAmbientBrightnessThresholds;
    public int[] mLowDisplayBrightnessThresholds;
    public String mName;
    public float[] mNits;
    public Spline mNitsToBacklightSpline;
    public List mQuirks;
    public float[] mRawBacklight;
    public float[] mRawNits;
    public final Map mRefreshRateThrottlingMap;
    public final Map mRefreshRateZoneProfiles;
    public float[] mScreenBrighteningLevels;
    public float[] mScreenBrighteningLevelsIdle;
    public float[] mScreenBrighteningPercentages;
    public float[] mScreenBrighteningPercentagesIdle;
    public float[] mScreenDarkeningLevels;
    public float[] mScreenDarkeningLevelsIdle;
    public float[] mScreenDarkeningPercentages;
    public float[] mScreenDarkeningPercentagesIdle;
    public int[] mScreenOffBrightnessSensorValueToLux;
    public Spline mSdrToHdrSpline;
    public final HashMap mThermalBrightnessThrottlingDataMapByThrottlingId;
    public boolean mUseSurfaceControlBrightness;
    public static final int[] DEFAULT_BRIGHTNESS_THRESHOLDS = new int[0];
    public static final float[] DEFAULT_AMBIENT_THRESHOLD_LEVELS = {DisplayPowerController2.RATE_FROM_DOZE_TO_ON};
    public static final float[] DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS = {100.0f};
    public static final float[] DEFAULT_AMBIENT_DARKENING_THRESHOLDS = {200.0f};
    public static final float[] DEFAULT_SCREEN_THRESHOLD_LEVELS = {DisplayPowerController2.RATE_FROM_DOZE_TO_ON};
    public static final float[] DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS = {100.0f};
    public static final float[] DEFAULT_SCREEN_DARKENING_THRESHOLDS = {200.0f};
    public final SensorData mAmbientLightSensor = new SensorData();
    public final SensorData mScreenOffBrightnessSensor = new SensorData();
    public final SensorData mProximitySensor = new SensorData();
    public final List mRefreshRateLimitations = new ArrayList(2);
    public float mBacklightMinimum = Float.NaN;
    public float mBacklightMaximum = Float.NaN;
    public float mBrightnessDefault = Float.NaN;
    public float mBrightnessRampFastDecrease = Float.NaN;
    public float mBrightnessRampFastIncrease = Float.NaN;
    public float mBrightnessRampSlowDecrease = Float.NaN;
    public float mBrightnessRampSlowIncrease = Float.NaN;
    public long mBrightnessRampDecreaseMaxMillis = 0;
    public long mBrightnessRampIncreaseMaxMillis = 0;
    public int mAmbientHorizonLong = 2500;
    public int mAmbientHorizonShort = 600;
    public float mScreenBrighteningMinThreshold = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mScreenBrighteningMinThresholdIdle = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mScreenDarkeningMinThreshold = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mScreenDarkeningMinThresholdIdle = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mAmbientLuxBrighteningMinThreshold = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mAmbientLuxBrighteningMinThresholdIdle = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mAmbientLuxDarkeningMinThreshold = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public float mAmbientLuxDarkeningMinThresholdIdle = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;

    public DisplayDeviceConfig(Context context) {
        float[] fArr = DEFAULT_SCREEN_THRESHOLD_LEVELS;
        this.mScreenBrighteningLevels = fArr;
        float[] fArr2 = DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS;
        this.mScreenBrighteningPercentages = fArr2;
        this.mScreenDarkeningLevels = fArr;
        float[] fArr3 = DEFAULT_SCREEN_DARKENING_THRESHOLDS;
        this.mScreenDarkeningPercentages = fArr3;
        this.mScreenBrighteningLevelsIdle = fArr;
        this.mScreenBrighteningPercentagesIdle = fArr2;
        this.mScreenDarkeningLevelsIdle = fArr;
        this.mScreenDarkeningPercentagesIdle = fArr3;
        float[] fArr4 = DEFAULT_AMBIENT_THRESHOLD_LEVELS;
        this.mAmbientBrighteningLevels = fArr4;
        float[] fArr5 = DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS;
        this.mAmbientBrighteningPercentages = fArr5;
        this.mAmbientDarkeningLevels = fArr4;
        float[] fArr6 = DEFAULT_AMBIENT_DARKENING_THRESHOLDS;
        this.mAmbientDarkeningPercentages = fArr6;
        this.mAmbientBrighteningLevelsIdle = fArr4;
        this.mAmbientBrighteningPercentagesIdle = fArr5;
        this.mAmbientDarkeningLevelsIdle = fArr4;
        this.mAmbientDarkeningPercentagesIdle = fArr6;
        this.mIsHighBrightnessModeEnabled = false;
        this.mLoadedFrom = null;
        this.mIsSimpleMappingStrategy = false;
        this.mAutoBrightnessBrighteningLightDebounce = -1L;
        this.mAutoBrightnessDarkeningLightDebounce = -1L;
        this.mAutoBrightnessAvailable = false;
        this.mDdcAutoBrightnessAvailable = true;
        this.mDefaultPeakRefreshRate = 0;
        this.mDefaultRefreshRate = 60;
        this.mDefaultRefreshRateInHbmHdr = 0;
        this.mDefaultRefreshRateInHbmSunlight = 0;
        this.mDefaultHighBlockingZoneRefreshRate = 0;
        this.mDefaultLowBlockingZoneRefreshRate = 60;
        this.mRefreshRateZoneProfiles = new HashMap();
        int[] iArr = DEFAULT_BRIGHTNESS_THRESHOLDS;
        this.mLowDisplayBrightnessThresholds = iArr;
        this.mLowAmbientBrightnessThresholds = iArr;
        this.mHighDisplayBrightnessThresholds = iArr;
        this.mHighAmbientBrightnessThresholds = iArr;
        this.mThermalBrightnessThrottlingDataMapByThrottlingId = new HashMap();
        this.mRefreshRateThrottlingMap = new HashMap();
        this.mContext = context;
    }

    public static DisplayDeviceConfig create(Context context, boolean z, boolean z2, boolean z3) {
        DisplayDeviceConfig configFromPmValues;
        if (z2) {
            configFromPmValues = getConfigFromGlobalXml(context, z);
        } else {
            configFromPmValues = getConfigFromPmValues(context);
        }
        configFromPmValues.copyUninitializedValuesFromSecondaryConfig(loadDefaultConfigurationXml(context));
        if (z3) {
            configFromPmValues.convertBacklightArrayForSurfaceControl();
        }
        return configFromPmValues;
    }

    public static DisplayDeviceConfig create(Context context, boolean z) {
        if (z) {
            return getConfigFromGlobalXml(context);
        }
        return getConfigFromPmValues(context);
    }

    public static DisplayConfiguration loadDefaultConfigurationXml(Context context) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(Environment.buildPath(Environment.getProductDirectory(), new String[]{"etc", "displayconfig", "default.xml"}));
        arrayList.add(Environment.buildPath(Environment.getVendorDirectory(), new String[]{"etc", "displayconfig", "default.xml"}));
        String uiModeTypeString = Configuration.getUiModeTypeString(context.getResources().getInteger(R.integer.config_displayWhiteBalanceTransitionTimeIncrease));
        if (uiModeTypeString != null) {
            arrayList.add(Environment.buildPath(Environment.getRootDirectory(), new String[]{"etc", "displayconfig", String.format("default_%s.xml", uiModeTypeString)}));
        }
        arrayList.add(Environment.buildPath(Environment.getRootDirectory(), new String[]{"etc", "displayconfig", "default.xml"}));
        File firstExistingFile = getFirstExistingFile(arrayList);
        DisplayConfiguration displayConfiguration = null;
        if (firstExistingFile == null) {
            return null;
        }
        try {
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(firstExistingFile));
            try {
                displayConfiguration = XmlParser.read(bufferedInputStream);
                if (displayConfiguration == null) {
                    Slog.i("DisplayDeviceConfig", "Default DisplayDeviceConfig file is null");
                }
                bufferedInputStream.close();
            } catch (Throwable th) {
                try {
                    bufferedInputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException | DatatypeConfigurationException | XmlPullParserException e) {
            Slog.e("DisplayDeviceConfig", "Encountered an error while reading/parsing display config file: " + firstExistingFile, e);
        }
        return displayConfiguration;
    }

    public static File getFirstExistingFile(Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            File file = (File) it.next();
            if (file.exists() && file.isFile()) {
                return file;
            }
        }
        return null;
    }

    public String getName() {
        return this.mName;
    }

    public float[] getNits() {
        return this.mNits;
    }

    public float getBacklightFromBrightness(float f) {
        return this.mBrightnessToBacklightSpline.interpolate(f);
    }

    public float getNitsFromBacklight(float f) {
        if (this.mBacklightToNitsSpline == null) {
            if (this.mIsSimpleMappingStrategy) {
                return -1.0f;
            }
            Slog.wtf("DisplayDeviceConfig", "requesting nits when no mapping exists.");
            return -1.0f;
        }
        return this.mBacklightToNitsSpline.interpolate(Math.max(f, this.mBacklightMinimum));
    }

    public boolean hasSdrToHdrRatioSpline() {
        return this.mSdrToHdrSpline != null;
    }

    public float getHdrBrightnessFromSdr(float f, float f2) {
        if (this.mSdrToHdrSpline == null) {
            return -1.0f;
        }
        float nitsFromBacklight = getNitsFromBacklight(getBacklightFromBrightness(f));
        if (nitsFromBacklight == -1.0f) {
            return -1.0f;
        }
        float interpolate = this.mSdrToHdrSpline.interpolate(nitsFromBacklight);
        Spline spline = this.mNitsToBacklightSpline;
        if (spline == null) {
            return -1.0f;
        }
        return this.mBacklightToBrightnessSpline.interpolate(Math.max(this.mBacklightMinimum, Math.min(this.mBacklightMaximum, spline.interpolate(interpolate))));
    }

    public float[] getBrightness() {
        return this.mBrightness;
    }

    public float getBrightnessDefault() {
        return this.mBrightnessDefault;
    }

    public float getBrightnessRampFastDecrease() {
        return this.mBrightnessRampFastDecrease;
    }

    public float getBrightnessRampFastIncrease() {
        return this.mBrightnessRampFastIncrease;
    }

    public float getBrightnessRampSlowDecrease() {
        return this.mBrightnessRampSlowDecrease;
    }

    public float getBrightnessRampSlowIncrease() {
        return this.mBrightnessRampSlowIncrease;
    }

    public long getBrightnessRampDecreaseMaxMillis() {
        return this.mBrightnessRampDecreaseMaxMillis;
    }

    public long getBrightnessRampIncreaseMaxMillis() {
        return this.mBrightnessRampIncreaseMaxMillis;
    }

    public int getAmbientHorizonLong() {
        return this.mAmbientHorizonLong;
    }

    public int getAmbientHorizonShort() {
        return this.mAmbientHorizonShort;
    }

    public SensorData getAmbientLightSensor() {
        return this.mAmbientLightSensor;
    }

    public SensorData getScreenOffBrightnessSensor() {
        return this.mScreenOffBrightnessSensor;
    }

    public SensorData getProximitySensor() {
        return this.mProximitySensor;
    }

    public boolean isAutoBrightnessAvailable() {
        return this.mAutoBrightnessAvailable;
    }

    public boolean isFirstDisplay() {
        return this.mIsFirstDisplay;
    }

    public boolean hasQuirk(String str) {
        List list = this.mQuirks;
        return list != null && list.contains(str);
    }

    public HighBrightnessModeData getHighBrightnessModeData() {
        if (!this.mIsHighBrightnessModeEnabled || this.mHbmData == null) {
            return null;
        }
        HighBrightnessModeData highBrightnessModeData = new HighBrightnessModeData();
        this.mHbmData.copyTo(highBrightnessModeData);
        return highBrightnessModeData;
    }

    public List getRefreshRateLimitations() {
        return this.mRefreshRateLimitations;
    }

    public DensityMapping getDensityMapping() {
        return this.mDensityMapping;
    }

    public HashMap getThermalBrightnessThrottlingDataMapByThrottlingId() {
        return this.mThermalBrightnessThrottlingDataMapByThrottlingId;
    }

    public SparseArray getThermalRefreshRateThrottlingData(String str) {
        if (str == null) {
            str = "default";
        }
        return (SparseArray) this.mRefreshRateThrottlingMap.get(str);
    }

    public long getAutoBrightnessDarkeningLightDebounce() {
        return this.mAutoBrightnessDarkeningLightDebounce;
    }

    public long getAutoBrightnessBrighteningLightDebounce() {
        return this.mAutoBrightnessBrighteningLightDebounce;
    }

    public float[] getAutoBrightnessBrighteningLevelsLux() {
        return this.mBrightnessLevelsLux;
    }

    public float[] getAutoBrightnessBrighteningLevelsNits() {
        return this.mBrightnessLevelsNits;
    }

    public int getDefaultPeakRefreshRate() {
        return this.mDefaultPeakRefreshRate;
    }

    public int getDefaultRefreshRate() {
        return this.mDefaultRefreshRate;
    }

    public int getDefaultRefreshRateInHbmHdr() {
        return this.mDefaultRefreshRateInHbmHdr;
    }

    public int getDefaultRefreshRateInHbmSunlight() {
        return this.mDefaultRefreshRateInHbmSunlight;
    }

    public int getDefaultHighBlockingZoneRefreshRate() {
        return this.mDefaultHighBlockingZoneRefreshRate;
    }

    public int getDefaultLowBlockingZoneRefreshRate() {
        return this.mDefaultLowBlockingZoneRefreshRate;
    }

    public SurfaceControl.RefreshRateRange getRefreshRange(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return (SurfaceControl.RefreshRateRange) this.mRefreshRateZoneProfiles.get(str);
    }

    public Map getRefreshRangeProfiles() {
        return this.mRefreshRateZoneProfiles;
    }

    public int[] getLowDisplayBrightnessThresholds() {
        return this.mLowDisplayBrightnessThresholds;
    }

    public int[] getLowAmbientBrightnessThresholds() {
        return this.mLowAmbientBrightnessThresholds;
    }

    public int[] getHighDisplayBrightnessThresholds() {
        return this.mHighDisplayBrightnessThresholds;
    }

    public int[] getHighAmbientBrightnessThresholds() {
        return this.mHighAmbientBrightnessThresholds;
    }

    public int[] getScreenOffBrightnessSensorValueToLux() {
        return this.mScreenOffBrightnessSensorValueToLux;
    }

    public HostUsiVersion getHostUsiVersion() {
        return this.mHostUsiVersion;
    }

    public String toString() {
        return "DisplayDeviceConfig{mLoadedFrom=" + this.mLoadedFrom + ", mBacklightMinimum=" + this.mBacklightMinimum + ", mBacklightMaximum=" + this.mBacklightMaximum + ", mBrightnessDefault=" + this.mBrightnessDefault + ", mQuirks=" + this.mQuirks + ", isHbmEnabled=" + this.mIsHighBrightnessModeEnabled + ", mHbmData=" + this.mHbmData + ", mSdrToHdrSpline=" + this.mSdrToHdrSpline + ", mThermalBrightnessThrottlingDataMapByThrottlingId=" + this.mThermalBrightnessThrottlingDataMapByThrottlingId + "\n, mBrightnessRampFastDecrease=" + this.mBrightnessRampFastDecrease + ", mBrightnessRampFastIncrease=" + this.mBrightnessRampFastIncrease + ", mBrightnessRampSlowDecrease=" + this.mBrightnessRampSlowDecrease + ", mBrightnessRampSlowIncrease=" + this.mBrightnessRampSlowIncrease + ", mBrightnessRampDecreaseMaxMillis=" + this.mBrightnessRampDecreaseMaxMillis + ", mBrightnessRampIncreaseMaxMillis=" + this.mBrightnessRampIncreaseMaxMillis + "\n, mAmbientHorizonLong=" + this.mAmbientHorizonLong + ", mAmbientHorizonShort=" + this.mAmbientHorizonShort + "\n, mScreenDarkeningMinThreshold=" + this.mScreenDarkeningMinThreshold + ", mScreenDarkeningMinThresholdIdle=" + this.mScreenDarkeningMinThresholdIdle + ", mScreenBrighteningMinThreshold=" + this.mScreenBrighteningMinThreshold + ", mScreenBrighteningMinThresholdIdle=" + this.mScreenBrighteningMinThresholdIdle + ", mAmbientLuxDarkeningMinThreshold=" + this.mAmbientLuxDarkeningMinThreshold + ", mAmbientLuxDarkeningMinThresholdIdle=" + this.mAmbientLuxDarkeningMinThresholdIdle + ", mAmbientLuxBrighteningMinThreshold=" + this.mAmbientLuxBrighteningMinThreshold + ", mAmbientLuxBrighteningMinThresholdIdle=" + this.mAmbientLuxBrighteningMinThresholdIdle + "\n, mScreenBrighteningLevels=" + Arrays.toString(this.mScreenBrighteningLevels) + ", mScreenBrighteningPercentages=" + Arrays.toString(this.mScreenBrighteningPercentages) + ", mScreenDarkeningLevels=" + Arrays.toString(this.mScreenDarkeningLevels) + ", mScreenDarkeningPercentages=" + Arrays.toString(this.mScreenDarkeningPercentages) + ", mAmbientBrighteningLevels=" + Arrays.toString(this.mAmbientBrighteningLevels) + ", mAmbientBrighteningPercentages=" + Arrays.toString(this.mAmbientBrighteningPercentages) + ", mAmbientDarkeningLevels=" + Arrays.toString(this.mAmbientDarkeningLevels) + ", mAmbientDarkeningPercentages=" + Arrays.toString(this.mAmbientDarkeningPercentages) + "\n, mAmbientBrighteningLevelsIdle=" + Arrays.toString(this.mAmbientBrighteningLevelsIdle) + ", mAmbientBrighteningPercentagesIdle=" + Arrays.toString(this.mAmbientBrighteningPercentagesIdle) + ", mAmbientDarkeningLevelsIdle=" + Arrays.toString(this.mAmbientDarkeningLevelsIdle) + ", mAmbientDarkeningPercentagesIdle=" + Arrays.toString(this.mAmbientDarkeningPercentagesIdle) + ", mScreenBrighteningLevelsIdle=" + Arrays.toString(this.mScreenBrighteningLevelsIdle) + ", mScreenBrighteningPercentagesIdle=" + Arrays.toString(this.mScreenBrighteningPercentagesIdle) + ", mScreenDarkeningLevelsIdle=" + Arrays.toString(this.mScreenDarkeningLevelsIdle) + ", mScreenDarkeningPercentagesIdle=" + Arrays.toString(this.mScreenDarkeningPercentagesIdle) + "\n, mAmbientLightSensor=" + this.mAmbientLightSensor + ", mScreenOffBrightnessSensor=" + this.mScreenOffBrightnessSensor + ", mProximitySensor=" + this.mProximitySensor + ", mRefreshRateLimitations= " + Arrays.toString(this.mRefreshRateLimitations.toArray()) + ", mDensityMapping= " + this.mDensityMapping + ", mAutoBrightnessBrighteningLightDebounce= " + this.mAutoBrightnessBrighteningLightDebounce + ", mAutoBrightnessDarkeningLightDebounce= " + this.mAutoBrightnessDarkeningLightDebounce + ", mBrightnessLevelsLux= " + Arrays.toString(this.mBrightnessLevelsLux) + ", mBrightnessLevelsNits= " + Arrays.toString(this.mBrightnessLevelsNits) + ", mDdcAutoBrightnessAvailable= " + this.mDdcAutoBrightnessAvailable + ", mAutoBrightnessAvailable= " + this.mAutoBrightnessAvailable + "\n, mDefaultLowBlockingZoneRefreshRate= " + this.mDefaultLowBlockingZoneRefreshRate + ", mDefaultHighBlockingZoneRefreshRate= " + this.mDefaultHighBlockingZoneRefreshRate + ", mDefaultPeakRefreshRate= " + this.mDefaultPeakRefreshRate + ", mDefaultRefreshRate= " + this.mDefaultRefreshRate + ", mRefreshRateZoneProfiles= " + this.mRefreshRateZoneProfiles + ", mDefaultRefreshRateInHbmHdr= " + this.mDefaultRefreshRateInHbmHdr + ", mDefaultRefreshRateInHbmSunlight= " + this.mDefaultRefreshRateInHbmSunlight + ", mRefreshRateThrottlingMap= " + this.mRefreshRateThrottlingMap + "\n, mLowDisplayBrightnessThresholds= " + Arrays.toString(this.mLowDisplayBrightnessThresholds) + ", mLowAmbientBrightnessThresholds= " + Arrays.toString(this.mLowAmbientBrightnessThresholds) + ", mHighDisplayBrightnessThresholds= " + Arrays.toString(this.mHighDisplayBrightnessThresholds) + ", mHighAmbientBrightnessThresholds= " + Arrays.toString(this.mHighAmbientBrightnessThresholds) + "\n, mScreenOffBrightnessSensorValueToLux=" + Arrays.toString(this.mScreenOffBrightnessSensorValueToLux) + "\n, mUsiVersion= " + this.mHostUsiVersion + ", mUseSurfaceControlBrightness=" + this.mUseSurfaceControlBrightness + "}";
    }

    public static DisplayDeviceConfig getConfigFromGlobalXml(Context context) {
        DisplayDeviceConfig displayDeviceConfig = new DisplayDeviceConfig(context);
        displayDeviceConfig.initFromGlobalXml();
        return displayDeviceConfig;
    }

    public static DisplayDeviceConfig getConfigFromGlobalXml(Context context, boolean z) {
        DisplayDeviceConfig displayDeviceConfig = new DisplayDeviceConfig(context);
        displayDeviceConfig.setFirstDisplay(z);
        displayDeviceConfig.initFromGlobalXml();
        return displayDeviceConfig;
    }

    public static DisplayDeviceConfig getConfigFromPmValues(Context context) {
        DisplayDeviceConfig displayDeviceConfig = new DisplayDeviceConfig(context);
        displayDeviceConfig.initFromDefaultValues();
        return displayDeviceConfig;
    }

    public boolean initFromFile(File file) {
        BufferedInputStream bufferedInputStream;
        if (!file.exists()) {
            return false;
        }
        if (!file.isFile()) {
            Slog.e("DisplayDeviceConfig", "Display configuration is not a file: " + file + ", skipping");
            return false;
        }
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        } catch (IOException | DatatypeConfigurationException | XmlPullParserException e) {
            Slog.e("DisplayDeviceConfig", "Encountered an error while reading/parsing display config file: " + file, e);
        }
        try {
            DisplayConfiguration read = XmlParser.read(bufferedInputStream);
            if (read != null) {
                loadName(read);
                loadDensityMapping(read);
                loadBrightnessDefaultFromDdcXml(read);
                loadBrightnessConstraintsFromConfigXml();
                loadBrightnessMap(read);
                loadThermalThrottlingConfig(read);
                loadHighBrightnessModeData(read);
                loadQuirks(read);
                loadBrightnessRamps(read);
                loadAmbientLightSensorFromDdc(read);
                loadScreenOffBrightnessSensorFromDdc(read);
                loadProxSensorFromDdc(read);
                loadAmbientHorizonFromDdc(read);
                loadBrightnessChangeThresholds(read);
                loadAutoBrightnessConfigValues(read);
                loadRefreshRateSetting(read);
                loadScreenOffBrightnessSensorValueToLuxFromDdc(read);
                loadUsiVersion(read);
            } else {
                Slog.w("DisplayDeviceConfig", "DisplayDeviceConfig file is null");
            }
            bufferedInputStream.close();
            this.mLoadedFrom = file.toString();
            return true;
        } catch (Throwable th) {
            try {
                bufferedInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    public final void initFromGlobalXml() {
        loadBrightnessDefaultFromConfigXml();
        loadBrightnessConstraintsFromConfigXml();
        loadBrightnessMapFromConfigXml();
        loadBrightnessRampsFromConfigXml();
        loadAmbientLightSensorFromConfigXml();
        loadBrightnessChangeThresholdsFromXml();
        setProxSensorUnspecified();
        loadAutoBrightnessConfigsFromConfigXml();
        loadAutoBrightnessAvailableFromConfigXml();
        loadRefreshRateSetting(null);
        this.mLoadedFrom = "<config.xml>";
        loadHighBrightnessModeDataFromConfigXml();
    }

    public final void initFromDefaultValues() {
        this.mLoadedFrom = "Static values";
        this.mBacklightMinimum = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mBacklightMaximum = 1.0f;
        this.mBrightnessDefault = HDR_PERCENT_OF_SCREEN_REQUIRED_DEFAULT;
        this.mBrightnessRampFastDecrease = 1.0f;
        this.mBrightnessRampFastIncrease = 1.0f;
        this.mBrightnessRampSlowDecrease = 1.0f;
        this.mBrightnessRampSlowIncrease = 1.0f;
        this.mBrightnessRampDecreaseMaxMillis = 0L;
        this.mBrightnessRampIncreaseMaxMillis = 0L;
        setSimpleMappingStrategyValues();
        loadAmbientLightSensorFromConfigXml();
        setProxSensorUnspecified();
        loadAutoBrightnessAvailableFromConfigXml();
    }

    public final void copyUninitializedValuesFromSecondaryConfig(DisplayConfiguration displayConfiguration) {
        if (displayConfiguration != null && this.mDensityMapping == null) {
            loadDensityMapping(displayConfiguration);
        }
    }

    public final void loadName(DisplayConfiguration displayConfiguration) {
        this.mName = displayConfiguration.getName();
    }

    public final void loadDensityMapping(DisplayConfiguration displayConfiguration) {
        if (displayConfiguration.getDensityMapping() == null) {
            return;
        }
        List density = displayConfiguration.getDensityMapping().getDensity();
        DensityMapping.Entry[] entryArr = new DensityMapping.Entry[density.size()];
        for (int i = 0; i < density.size(); i++) {
            Density density2 = (Density) density.get(i);
            entryArr[i] = new DensityMapping.Entry(density2.getWidth().intValue(), density2.getHeight().intValue(), density2.getDensity().intValue());
        }
        this.mDensityMapping = DensityMapping.createByOwning(entryArr);
    }

    public final void loadBrightnessDefaultFromDdcXml(DisplayConfiguration displayConfiguration) {
        if (displayConfiguration != null) {
            BigDecimal screenBrightnessDefault = displayConfiguration.getScreenBrightnessDefault();
            if (screenBrightnessDefault != null) {
                this.mBrightnessDefault = screenBrightnessDefault.floatValue();
            } else {
                loadBrightnessDefaultFromConfigXml();
            }
        }
    }

    public final void loadBrightnessDefaultFromConfigXml() {
        float f = this.mContext.getResources().getFloat(R.dimen.config_pictureInPictureExpandedHorizontalHeight);
        if (f == -2.0f) {
            this.mBrightnessDefault = BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(R.integer.config_screen_rotation_fade_in));
        } else {
            this.mBrightnessDefault = f;
        }
    }

    public final void loadBrightnessConstraintsFromConfigXml() {
        float f = this.mContext.getResources().getFloat(R.dimen.config_pictureInPictureMaxAspectRatio);
        float max = Math.max(IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT, Resources.getSystem().getInteger(!this.mIsCoverDisplay ? R.integer.config_screenTimeoutOverride : R.integer.config_defaultNotificationLedOff)) / 255.0f;
        if (f == -2.0f || max == -2.0f) {
            this.mBacklightMinimum = BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(R.integer.config_screen_rotation_fade_out));
            this.mBacklightMaximum = max;
        } else {
            this.mBacklightMinimum = f;
            this.mBacklightMaximum = max;
        }
    }

    public final void loadBrightnessMap(DisplayConfiguration displayConfiguration) {
        NitsMap screenBrightnessMap = displayConfiguration.getScreenBrightnessMap();
        if (screenBrightnessMap == null) {
            loadBrightnessMapFromConfigXml();
            return;
        }
        List<Point> point = screenBrightnessMap.getPoint();
        int size = point.size();
        float[] fArr = new float[size];
        float[] fArr2 = new float[size];
        this.mInterpolationType = convertInterpolationType(screenBrightnessMap.getInterpolation());
        int i = 0;
        for (Point point2 : point) {
            fArr[i] = point2.getNits().floatValue();
            float floatValue = point2.getValue().floatValue();
            fArr2[i] = floatValue;
            if (i > 0) {
                int i2 = i - 1;
                if (fArr[i] < fArr[i2]) {
                    Slog.e("DisplayDeviceConfig", "screenBrightnessMap must be non-decreasing, ignoring rest  of configuration. Nits: " + fArr[i] + " < " + fArr[i2]);
                    return;
                }
                if (floatValue < fArr2[i2]) {
                    Slog.e("DisplayDeviceConfig", "screenBrightnessMap must be non-decreasing, ignoring rest  of configuration. Value: " + fArr2[i] + " < " + fArr2[i2]);
                    return;
                }
            }
            i++;
        }
        this.mRawNits = fArr;
        this.mRawBacklight = fArr2;
        constrainNitsAndBacklightArrays();
    }

    public final Spline loadSdrHdrMapFromConfigXml() {
        float[] floatArray = getFloatArray(this.mContext.getResources().obtainTypedArray(17236299), -1.0f);
        float[] floatArray2 = getFloatArray(this.mContext.getResources().obtainTypedArray(17236298), -1.0f);
        if (this.mIsCoverDisplay) {
            float[] floatArray3 = getFloatArray(this.mContext.getResources().obtainTypedArray(R.array.config_screenDarkeningThresholds), -1.0f);
            float[] floatArray4 = getFloatArray(this.mContext.getResources().obtainTypedArray(R.array.config_screenBrightnessNits), -1.0f);
            if (floatArray3.length != 0 && floatArray4.length != 0) {
                Slog.d("DisplayDeviceConfig", "loadSdrHdrMapFromConfigXml: use seperate config for cover display");
                floatArray = (float[]) floatArray3.clone();
                floatArray2 = (float[]) floatArray4.clone();
            }
        }
        if (floatArray.length == 0 && floatArray2.length == 0) {
            Slog.d("DisplayDeviceConfig", "loadSdrHdrMapFromConfigXml: not support");
            return null;
        }
        if (floatArray.length != floatArray2.length) {
            Slog.w("DisplayDeviceConfig", "loadSdrHdrMapFromConfigXml: wrong length: " + floatArray.length + ", " + floatArray2.length);
            return null;
        }
        for (int i = 0; i < floatArray.length; i++) {
            if (i > 0) {
                float f = floatArray[i];
                int i2 = i - 1;
                if (f < floatArray[i2]) {
                    Slog.e("DisplayDeviceConfig", "loadSdrHdrMapFromConfigXml: sdrHdrRatioMap must be non-decreasing, ignoring rest  of configuration. sdr nits: " + floatArray[i] + " < " + floatArray[i2]);
                    return null;
                }
                if (f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON || floatArray2[i] < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                    Slog.w("DisplayDeviceConfig", "loadSdrHdrMapFromConfigXml: invalid value: [" + i + "] " + floatArray[i] + ", " + floatArray2[i]);
                    return null;
                }
            }
        }
        return Spline.createLinearSpline(floatArray, floatArray2);
    }

    public final void loadThermalThrottlingConfig(DisplayConfiguration displayConfiguration) {
        ThermalThrottling thermalThrottling = displayConfiguration.getThermalThrottling();
        if (thermalThrottling == null) {
            Slog.i("DisplayDeviceConfig", "No thermal throttling config found");
        } else {
            loadThermalBrightnessThrottlingMaps(thermalThrottling);
            loadThermalRefreshRateThrottlingMap(thermalThrottling);
        }
    }

    public final void loadThermalBrightnessThrottlingMaps(ThermalThrottling thermalThrottling) {
        boolean z;
        List<BrightnessThrottlingMap> brightnessThrottlingMap = thermalThrottling.getBrightnessThrottlingMap();
        if (brightnessThrottlingMap == null || brightnessThrottlingMap.isEmpty()) {
            Slog.i("DisplayDeviceConfig", "No brightness throttling map found");
            return;
        }
        for (BrightnessThrottlingMap brightnessThrottlingMap2 : brightnessThrottlingMap) {
            List brightnessThrottlingPoint = brightnessThrottlingMap2.getBrightnessThrottlingPoint();
            ArrayList arrayList = new ArrayList(brightnessThrottlingPoint.size());
            Iterator it = brightnessThrottlingPoint.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                }
                BrightnessThrottlingPoint brightnessThrottlingPoint2 = (BrightnessThrottlingPoint) it.next();
                ThermalStatus thermalStatus = brightnessThrottlingPoint2.getThermalStatus();
                if (!thermalStatusIsValid(thermalStatus)) {
                    z = true;
                    break;
                }
                arrayList.add(new ThermalBrightnessThrottlingData.ThrottlingLevel(convertThermalStatus(thermalStatus), brightnessThrottlingPoint2.getBrightness().floatValue()));
            }
            if (!z) {
                String id = brightnessThrottlingMap2.getId() == null ? "default" : brightnessThrottlingMap2.getId();
                if (this.mThermalBrightnessThrottlingDataMapByThrottlingId.containsKey(id)) {
                    throw new RuntimeException("Brightness throttling data with ID " + id + " already exists");
                }
                this.mThermalBrightnessThrottlingDataMapByThrottlingId.put(id, ThermalBrightnessThrottlingData.create(arrayList));
            }
        }
    }

    public final void loadThermalRefreshRateThrottlingMap(ThermalThrottling thermalThrottling) {
        List<RefreshRateThrottlingMap> refreshRateThrottlingMap = thermalThrottling.getRefreshRateThrottlingMap();
        if (refreshRateThrottlingMap == null || refreshRateThrottlingMap.isEmpty()) {
            Slog.w("DisplayDeviceConfig", "RefreshRateThrottling: map not found");
            return;
        }
        for (RefreshRateThrottlingMap refreshRateThrottlingMap2 : refreshRateThrottlingMap) {
            List<RefreshRateThrottlingPoint> refreshRateThrottlingPoint = refreshRateThrottlingMap2.getRefreshRateThrottlingPoint();
            String id = refreshRateThrottlingMap2.getId() == null ? "default" : refreshRateThrottlingMap2.getId();
            if (refreshRateThrottlingPoint == null || refreshRateThrottlingPoint.isEmpty()) {
                Slog.w("DisplayDeviceConfig", "RefreshRateThrottling: points not found for mapId=" + id);
            } else if (this.mRefreshRateThrottlingMap.containsKey(id)) {
                Slog.wtf("DisplayDeviceConfig", "RefreshRateThrottling: map already exists, mapId=" + id);
            } else {
                SparseArray sparseArray = new SparseArray();
                for (RefreshRateThrottlingPoint refreshRateThrottlingPoint2 : refreshRateThrottlingPoint) {
                    ThermalStatus thermalStatus = refreshRateThrottlingPoint2.getThermalStatus();
                    if (!thermalStatusIsValid(thermalStatus)) {
                        Slog.wtf("DisplayDeviceConfig", "RefreshRateThrottling: Invalid thermalStatus=" + thermalStatus.getRawName() + ",mapId=" + id);
                    } else {
                        int convertThermalStatus = convertThermalStatus(thermalStatus);
                        if (sparseArray.contains(convertThermalStatus)) {
                            Slog.wtf("DisplayDeviceConfig", "RefreshRateThrottling: thermalStatus=" + thermalStatus.getRawName() + " is already in the map, mapId=" + id);
                        } else {
                            sparseArray.put(convertThermalStatus, new SurfaceControl.RefreshRateRange(refreshRateThrottlingPoint2.getRefreshRateRange().getMinimum().floatValue(), refreshRateThrottlingPoint2.getRefreshRateRange().getMaximum().floatValue()));
                        }
                    }
                }
                if (sparseArray.size() == 0) {
                    Slog.w("DisplayDeviceConfig", "RefreshRateThrottling: no valid throttling points found for map, mapId=" + id);
                } else {
                    this.mRefreshRateThrottlingMap.put(id, sparseArray);
                }
            }
        }
    }

    public final void loadRefreshRateSetting(DisplayConfiguration displayConfiguration) {
        RefreshRateConfigs refreshRate = displayConfiguration == null ? null : displayConfiguration.getRefreshRate();
        BlockingZoneConfig lowerBlockingZoneConfigs = refreshRate == null ? null : refreshRate.getLowerBlockingZoneConfigs();
        BlockingZoneConfig higherBlockingZoneConfigs = refreshRate != null ? refreshRate.getHigherBlockingZoneConfigs() : null;
        loadPeakDefaultRefreshRate(refreshRate);
        loadDefaultRefreshRate(refreshRate);
        loadDefaultRefreshRateInHbm(refreshRate);
        loadLowerRefreshRateBlockingZones(lowerBlockingZoneConfigs);
        loadHigherRefreshRateBlockingZones(higherBlockingZoneConfigs);
        loadRefreshRateZoneProfiles(refreshRate);
    }

    public final void loadPeakDefaultRefreshRate(RefreshRateConfigs refreshRateConfigs) {
        if (refreshRateConfigs == null || refreshRateConfigs.getDefaultPeakRefreshRate() == null) {
            this.mDefaultPeakRefreshRate = this.mContext.getResources().getInteger(R.integer.config_displayWhiteBalanceColorTemperatureMin);
        } else {
            this.mDefaultPeakRefreshRate = refreshRateConfigs.getDefaultPeakRefreshRate().intValue();
        }
    }

    public final void loadDefaultRefreshRate(RefreshRateConfigs refreshRateConfigs) {
        if (refreshRateConfigs == null || refreshRateConfigs.getDefaultRefreshRate() == null) {
            this.mDefaultRefreshRate = this.mContext.getResources().getInteger(R.integer.config_displayWhiteBalanceDecreaseDebounce);
        } else {
            this.mDefaultRefreshRate = refreshRateConfigs.getDefaultRefreshRate().intValue();
        }
    }

    public final void loadRefreshRateZoneProfiles(RefreshRateConfigs refreshRateConfigs) {
        if (refreshRateConfigs == null) {
            return;
        }
        for (RefreshRateZone refreshRateZone : refreshRateConfigs.getRefreshRateZoneProfiles().getRefreshRateZoneProfile()) {
            RefreshRateRange refreshRateRange = refreshRateZone.getRefreshRateRange();
            this.mRefreshRateZoneProfiles.put(refreshRateZone.getId(), new SurfaceControl.RefreshRateRange(refreshRateRange.getMinimum().floatValue(), refreshRateRange.getMaximum().floatValue()));
        }
    }

    public final void loadDefaultRefreshRateInHbm(RefreshRateConfigs refreshRateConfigs) {
        if (refreshRateConfigs != null && refreshRateConfigs.getDefaultRefreshRateInHbmHdr() != null) {
            this.mDefaultRefreshRateInHbmHdr = refreshRateConfigs.getDefaultRefreshRateInHbmHdr().intValue();
        } else {
            this.mDefaultRefreshRateInHbmHdr = this.mContext.getResources().getInteger(R.integer.config_displayWhiteBalanceDisplayNominalWhiteCct);
        }
        if (refreshRateConfigs != null && refreshRateConfigs.getDefaultRefreshRateInHbmSunlight() != null) {
            this.mDefaultRefreshRateInHbmSunlight = refreshRateConfigs.getDefaultRefreshRateInHbmSunlight().intValue();
        } else {
            this.mDefaultRefreshRateInHbmSunlight = this.mContext.getResources().getInteger(R.integer.config_displayWhiteBalanceIncreaseDebounce);
        }
    }

    public final void loadLowerRefreshRateBlockingZones(BlockingZoneConfig blockingZoneConfig) {
        loadLowerBlockingZoneDefaultRefreshRate(blockingZoneConfig);
        loadLowerBrightnessThresholds(blockingZoneConfig);
    }

    public final void loadHigherRefreshRateBlockingZones(BlockingZoneConfig blockingZoneConfig) {
        loadHigherBlockingZoneDefaultRefreshRate(blockingZoneConfig);
        loadHigherBrightnessThresholds(blockingZoneConfig);
    }

    public final void loadHigherBlockingZoneDefaultRefreshRate(BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig == null) {
            this.mDefaultHighBlockingZoneRefreshRate = this.mContext.getResources().getInteger(R.integer.config_letterboxDefaultPositionForBookModeReachability);
        } else {
            this.mDefaultHighBlockingZoneRefreshRate = blockingZoneConfig.getDefaultRefreshRate().intValue();
        }
    }

    public final void loadLowerBlockingZoneDefaultRefreshRate(BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig == null) {
            this.mDefaultLowBlockingZoneRefreshRate = this.mContext.getResources().getInteger(R.integer.config_displayWhiteBalanceTransitionTime);
        } else {
            this.mDefaultLowBlockingZoneRefreshRate = blockingZoneConfig.getDefaultRefreshRate().intValue();
        }
    }

    public final void loadLowerBrightnessThresholds(BlockingZoneConfig blockingZoneConfig) {
        if (blockingZoneConfig == null) {
            this.mLowDisplayBrightnessThresholds = this.mContext.getResources().getIntArray(R.array.config_requestVibrationParamsForUsages);
            int[] intArray = this.mContext.getResources().getIntArray(R.array.config_minimumBrightnessCurveLux);
            this.mLowAmbientBrightnessThresholds = intArray;
            int[] iArr = this.mLowDisplayBrightnessThresholds;
            if (iArr == null || intArray == null || iArr.length != intArray.length) {
                throw new RuntimeException("display low brightness threshold array and ambient brightness threshold array have different length: mLowDisplayBrightnessThresholds=" + Arrays.toString(this.mLowDisplayBrightnessThresholds) + ", mLowAmbientBrightnessThresholds=" + Arrays.toString(this.mLowAmbientBrightnessThresholds));
            }
            return;
        }
        List displayBrightnessPoint = blockingZoneConfig.getBlockingZoneThreshold().getDisplayBrightnessPoint();
        int size = displayBrightnessPoint.size();
        this.mLowDisplayBrightnessThresholds = new int[size];
        this.mLowAmbientBrightnessThresholds = new int[size];
        for (int i = 0; i < size; i++) {
            this.mLowDisplayBrightnessThresholds[i] = (int) ((DisplayBrightnessPoint) displayBrightnessPoint.get(i)).getNits().floatValue();
            this.mLowAmbientBrightnessThresholds[i] = ((DisplayBrightnessPoint) displayBrightnessPoint.get(i)).getLux().intValue();
        }
    }

    public final void loadHigherBrightnessThresholds(BlockingZoneConfig blockingZoneConfig) {
        int[] iArr;
        if (blockingZoneConfig == null) {
            this.mHighDisplayBrightnessThresholds = this.mContext.getResources().getIntArray(R.array.resolver_target_actions_pin);
            int[] intArray = this.mContext.getResources().getIntArray(R.array.required_apps_managed_user);
            this.mHighAmbientBrightnessThresholds = intArray;
            if (intArray == null || (iArr = this.mHighDisplayBrightnessThresholds) == null || intArray.length != iArr.length) {
                throw new RuntimeException("display high brightness threshold array and ambient brightness threshold array have different length: mHighDisplayBrightnessThresholds=" + Arrays.toString(this.mHighDisplayBrightnessThresholds) + ", mHighAmbientBrightnessThresholds=" + Arrays.toString(this.mHighAmbientBrightnessThresholds));
            }
            return;
        }
        List displayBrightnessPoint = blockingZoneConfig.getBlockingZoneThreshold().getDisplayBrightnessPoint();
        int size = displayBrightnessPoint.size();
        this.mHighDisplayBrightnessThresholds = new int[size];
        this.mHighAmbientBrightnessThresholds = new int[size];
        for (int i = 0; i < size; i++) {
            this.mHighDisplayBrightnessThresholds[i] = (int) ((DisplayBrightnessPoint) displayBrightnessPoint.get(i)).getNits().floatValue();
            this.mHighAmbientBrightnessThresholds[i] = ((DisplayBrightnessPoint) displayBrightnessPoint.get(i)).getLux().intValue();
        }
    }

    public final void loadAutoBrightnessConfigValues(DisplayConfiguration displayConfiguration) {
        AutoBrightness autoBrightness = displayConfiguration.getAutoBrightness();
        loadAutoBrightnessBrighteningLightDebounce(autoBrightness);
        loadAutoBrightnessDarkeningLightDebounce(autoBrightness);
        loadAutoBrightnessDisplayBrightnessMapping(autoBrightness);
        loadEnableAutoBrightness(autoBrightness);
    }

    public final void loadAutoBrightnessBrighteningLightDebounce(AutoBrightness autoBrightness) {
        if (autoBrightness == null || autoBrightness.getBrighteningLightDebounceMillis() == null) {
            this.mAutoBrightnessBrighteningLightDebounce = this.mContext.getResources().getInteger(R.integer.config_bluetooth_tx_cur_ma);
        } else {
            this.mAutoBrightnessBrighteningLightDebounce = autoBrightness.getBrighteningLightDebounceMillis().intValue();
        }
    }

    public final void loadAutoBrightnessDarkeningLightDebounce(AutoBrightness autoBrightness) {
        if (autoBrightness == null || autoBrightness.getDarkeningLightDebounceMillis() == null) {
            this.mAutoBrightnessDarkeningLightDebounce = this.mContext.getResources().getInteger(R.integer.config_brightness_ramp_rate_fast);
        } else {
            this.mAutoBrightnessDarkeningLightDebounce = autoBrightness.getDarkeningLightDebounceMillis().intValue();
        }
    }

    public final void loadAutoBrightnessDisplayBrightnessMapping(AutoBrightness autoBrightness) {
        if (autoBrightness == null || autoBrightness.getDisplayBrightnessMapping() == null) {
            this.mBrightnessLevelsNits = getFloatArray(this.mContext.getResources().obtainTypedArray(R.array.config_networkNotifySwitches), DisplayPowerController2.RATE_FROM_DOZE_TO_ON);
            this.mBrightnessLevelsLux = getLuxLevels(this.mContext.getResources().getIntArray(R.array.config_notificationFallbackVibePattern));
            return;
        }
        int size = autoBrightness.getDisplayBrightnessMapping().getDisplayBrightnessPoint().size();
        this.mBrightnessLevelsNits = new float[size];
        this.mBrightnessLevelsLux = new float[size + 1];
        int i = 0;
        while (i < size) {
            this.mBrightnessLevelsNits[i] = ((DisplayBrightnessPoint) autoBrightness.getDisplayBrightnessMapping().getDisplayBrightnessPoint().get(i)).getNits().floatValue();
            int i2 = i + 1;
            this.mBrightnessLevelsLux[i2] = ((DisplayBrightnessPoint) autoBrightness.getDisplayBrightnessMapping().getDisplayBrightnessPoint().get(i)).getLux().floatValue();
            i = i2;
        }
    }

    public final void loadAutoBrightnessAvailableFromConfigXml() {
        if (this.mIsCoverDisplay) {
            this.mAutoBrightnessAvailable = this.mContext.getResources().getBoolean(R.bool.config_cecSystemAudioModeMutingEnabled_allowed);
        } else {
            this.mAutoBrightnessAvailable = this.mContext.getResources().getBoolean(R.bool.config_autoPowerModeUseMotionSensor);
        }
    }

    public final void loadBrightnessMapFromConfigXml() {
        Resources resources = this.mContext.getResources();
        float[] floatArray = BrightnessMappingStrategy.getFloatArray(resources.obtainTypedArray(17236294));
        int[] intArray = resources.getIntArray(17236293);
        int length = intArray.length;
        float[] fArr = new float[length];
        for (int i = 0; i < intArray.length; i++) {
            fArr[i] = BrightnessSynchronizer.brightnessIntToFloat(intArray[i]);
        }
        if (length == 0 || floatArray.length == 0) {
            setSimpleMappingStrategyValues();
            return;
        }
        Slog.d("DisplayDeviceConfig", "backlight min=" + intArray[0] + " backlight max=" + intArray[intArray.length - 1]);
        this.mRawNits = floatArray;
        this.mRawBacklight = fArr;
        adjustBacklightArrayBoundary();
        constrainNitsAndBacklightArrays();
    }

    public final void setSimpleMappingStrategyValues() {
        this.mNits = null;
        this.mBacklight = null;
        float[] fArr = {DisplayPowerController2.RATE_FROM_DOZE_TO_ON, this.mBacklightMaximum};
        this.mBrightnessToBacklightSpline = Spline.createSpline(fArr, fArr);
        this.mBacklightToBrightnessSpline = Spline.createSpline(fArr, fArr);
        this.mIsSimpleMappingStrategy = true;
    }

    public final void adjustBacklightArrayBoundary() {
        if (BrightnessSynchronizer.floatEquals(this.mRawBacklight[0], this.mBacklightMinimum) && this.mRawBacklight[0] != this.mBacklightMinimum) {
            Slog.d("DisplayDeviceConfig", "adjust backlight min boundary : " + this.mRawBacklight[0] + " " + this.mBacklightMinimum);
            this.mRawBacklight[0] = this.mBacklightMinimum;
        }
        if (BrightnessSynchronizer.floatEquals(this.mRawBacklight[r0.length - 1], this.mBacklightMaximum)) {
            if (this.mRawBacklight[r0.length - 1] != this.mBacklightMaximum) {
                StringBuilder sb = new StringBuilder();
                sb.append("adjust backlight max boundary : ");
                sb.append(this.mRawBacklight[r1.length - 1]);
                sb.append(" ");
                sb.append(this.mBacklightMaximum);
                Slog.d("DisplayDeviceConfig", sb.toString());
                this.mRawBacklight[r0.length - 1] = this.mBacklightMaximum;
            }
        }
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
                        f = rawBacklightToNits(i4, f6);
                    } else if (z2) {
                        f6 = MathUtils.min(f6, f7);
                        f = rawBacklightToNits(i4 - 1, f6);
                    } else {
                        f = this.mRawNits[i4];
                    }
                    fArr3[i3] = f6;
                    fArr2[i3] = f;
                    i4++;
                    z = z2;
                }
                int i5 = i3 + 1;
                this.mBacklight = Arrays.copyOf(fArr3, i5);
                this.mNits = Arrays.copyOf(fArr2, i5);
                createBacklightConversionSplines();
                return;
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Min or max values are invalid; raw min=");
        sb.append(this.mRawBacklight[0]);
        sb.append("; raw max=");
        float[] fArr6 = this.mRawBacklight;
        sb.append(fArr6[fArr6.length - 1]);
        sb.append("; backlight min=");
        sb.append(this.mBacklightMinimum);
        sb.append("; backlight max=");
        sb.append(this.mBacklightMaximum);
        throw new IllegalStateException(sb.toString());
    }

    public final float rawBacklightToNits(int i, float f) {
        float[] fArr = this.mRawBacklight;
        float f2 = fArr[i];
        int i2 = i + 1;
        float f3 = fArr[i2];
        float[] fArr2 = this.mRawNits;
        return MathUtils.map(f2, f3, fArr2[i], fArr2[i2], f);
    }

    public final void createBacklightConversionSplines() {
        float[] fArr;
        Spline createSpline;
        Spline createSpline2;
        Spline createSpline3;
        Spline createSpline4;
        this.mBrightness = new float[this.mBacklight.length];
        int i = 0;
        while (true) {
            fArr = this.mBrightness;
            if (i >= fArr.length) {
                break;
            }
            float[] fArr2 = this.mBacklight;
            fArr[i] = MathUtils.map(fArr2[0], fArr2[fArr2.length - 1], this.mBacklightMinimum, this.mBacklightMaximum, fArr2[i]);
            i++;
        }
        if (this.mInterpolationType == 1) {
            createSpline = Spline.createLinearSpline(fArr, this.mBacklight);
        } else {
            createSpline = Spline.createSpline(fArr, this.mBacklight);
        }
        this.mBrightnessToBacklightSpline = createSpline;
        if (this.mInterpolationType == 1) {
            createSpline2 = Spline.createLinearSpline(this.mBacklight, this.mBrightness);
        } else {
            createSpline2 = Spline.createSpline(this.mBacklight, this.mBrightness);
        }
        this.mBacklightToBrightnessSpline = createSpline2;
        if (this.mInterpolationType == 1) {
            createSpline3 = Spline.createLinearSpline(this.mBacklight, this.mNits);
        } else {
            createSpline3 = Spline.createSpline(this.mBacklight, this.mNits);
        }
        this.mBacklightToNitsSpline = createSpline3;
        if (this.mInterpolationType == 1) {
            createSpline4 = Spline.createLinearSpline(this.mNits, this.mBacklight);
        } else {
            createSpline4 = Spline.createSpline(this.mNits, this.mBacklight);
        }
        this.mNitsToBacklightSpline = createSpline4;
    }

    public final void loadQuirks(DisplayConfiguration displayConfiguration) {
        DisplayQuirks quirks = displayConfiguration.getQuirks();
        if (quirks != null) {
            this.mQuirks = new ArrayList(quirks.getQuirk());
        }
    }

    public final void loadHighBrightnessModeData(DisplayConfiguration displayConfiguration) {
        HighBrightnessMode highBrightnessMode = displayConfiguration.getHighBrightnessMode();
        if (highBrightnessMode != null) {
            this.mIsHighBrightnessModeEnabled = highBrightnessMode.getEnabled();
            HighBrightnessModeData highBrightnessModeData = new HighBrightnessModeData();
            this.mHbmData = highBrightnessModeData;
            highBrightnessModeData.minimumLux = highBrightnessMode.getMinimumLux_all().floatValue();
            float floatValue = highBrightnessMode.getTransitionPoint_all().floatValue();
            if (floatValue >= this.mBacklightMaximum) {
                throw new IllegalArgumentException("HBM transition point invalid. " + this.mHbmData.transitionPoint + " is not less than " + this.mBacklightMaximum);
            }
            this.mHbmData.transitionPoint = this.mBacklightToBrightnessSpline.interpolate(floatValue);
            HbmTiming timing_all = highBrightnessMode.getTiming_all();
            this.mHbmData.timeWindowMillis = timing_all.getTimeWindowSecs_all().longValue() * 1000;
            this.mHbmData.timeMaxMillis = timing_all.getTimeMaxSecs_all().longValue() * 1000;
            this.mHbmData.timeMinMillis = timing_all.getTimeMinSecs_all().longValue() * 1000;
            this.mHbmData.allowInLowPowerMode = highBrightnessMode.getAllowInLowPowerMode_all();
            RefreshRateRange refreshRate_all = highBrightnessMode.getRefreshRate_all();
            if (refreshRate_all != null) {
                this.mRefreshRateLimitations.add(new DisplayManagerInternal.RefreshRateLimitation(1, refreshRate_all.getMinimum().floatValue(), refreshRate_all.getMaximum().floatValue()));
            }
            BigDecimal minimumHdrPercentOfScreen_all = highBrightnessMode.getMinimumHdrPercentOfScreen_all();
            if (minimumHdrPercentOfScreen_all != null) {
                this.mHbmData.minimumHdrPercentOfScreen = minimumHdrPercentOfScreen_all.floatValue();
                float f = this.mHbmData.minimumHdrPercentOfScreen;
                if (f > 1.0f || f < DisplayPowerController2.RATE_FROM_DOZE_TO_ON) {
                    Slog.w("DisplayDeviceConfig", "Invalid minimum HDR percent of screen: " + String.valueOf(this.mHbmData.minimumHdrPercentOfScreen));
                    this.mHbmData.minimumHdrPercentOfScreen = HDR_PERCENT_OF_SCREEN_REQUIRED_DEFAULT;
                }
            } else {
                this.mHbmData.minimumHdrPercentOfScreen = HDR_PERCENT_OF_SCREEN_REQUIRED_DEFAULT;
            }
            this.mSdrToHdrSpline = loadSdrHdrMapFromConfigXml();
            return;
        }
        loadHighBrightnessModeDataFromConfigXml();
    }

    public final void loadBrightnessRamps(DisplayConfiguration displayConfiguration) {
        BigDecimal screenBrightnessRampFastDecrease = displayConfiguration.getScreenBrightnessRampFastDecrease();
        BigDecimal screenBrightnessRampFastIncrease = displayConfiguration.getScreenBrightnessRampFastIncrease();
        BigDecimal screenBrightnessRampSlowDecrease = displayConfiguration.getScreenBrightnessRampSlowDecrease();
        BigDecimal screenBrightnessRampSlowIncrease = displayConfiguration.getScreenBrightnessRampSlowIncrease();
        if (screenBrightnessRampFastDecrease != null && screenBrightnessRampFastIncrease != null && screenBrightnessRampSlowDecrease != null && screenBrightnessRampSlowIncrease != null) {
            this.mBrightnessRampFastDecrease = screenBrightnessRampFastDecrease.floatValue();
            this.mBrightnessRampFastIncrease = screenBrightnessRampFastIncrease.floatValue();
            this.mBrightnessRampSlowDecrease = screenBrightnessRampSlowDecrease.floatValue();
            this.mBrightnessRampSlowIncrease = screenBrightnessRampSlowIncrease.floatValue();
        } else {
            if (screenBrightnessRampFastDecrease != null || screenBrightnessRampFastIncrease != null || screenBrightnessRampSlowDecrease != null || screenBrightnessRampSlowIncrease != null) {
                Slog.w("DisplayDeviceConfig", "Per display brightness ramp values ignored because not all values are present in display device config");
            }
            loadBrightnessRampsFromConfigXml();
        }
        if (displayConfiguration.getScreenBrightnessRampIncreaseMaxMillis() != null) {
            this.mBrightnessRampIncreaseMaxMillis = r0.intValue();
        }
        if (displayConfiguration.getScreenBrightnessRampDecreaseMaxMillis() != null) {
            this.mBrightnessRampDecreaseMaxMillis = r5.intValue();
        }
    }

    public final void loadBrightnessRampsFromConfigXml() {
        this.mBrightnessRampFastIncrease = BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(R.integer.config_datause_threshold_bytes));
        float brightnessIntToFloat = BrightnessSynchronizer.brightnessIntToFloat(this.mContext.getResources().getInteger(R.integer.config_datause_throttle_kbitsps));
        this.mBrightnessRampSlowIncrease = brightnessIntToFloat;
        this.mBrightnessRampFastDecrease = this.mBrightnessRampFastIncrease;
        this.mBrightnessRampSlowDecrease = brightnessIntToFloat;
    }

    public final void loadAmbientLightSensorFromConfigXml() {
        SensorData sensorData = this.mAmbientLightSensor;
        sensorData.name = "";
        sensorData.type = this.mContext.getResources().getString(R.string.demo_restarting_message);
    }

    public final void loadAutoBrightnessConfigsFromConfigXml() {
        loadAutoBrightnessDisplayBrightnessMapping(null);
    }

    public final void loadAmbientLightSensorFromDdc(DisplayConfiguration displayConfiguration) {
        SensorDetails lightSensor = displayConfiguration.getLightSensor();
        if (lightSensor != null) {
            this.mAmbientLightSensor.type = lightSensor.getType();
            this.mAmbientLightSensor.name = lightSensor.getName();
            RefreshRateRange refreshRate = lightSensor.getRefreshRate();
            if (refreshRate != null) {
                this.mAmbientLightSensor.minRefreshRate = refreshRate.getMinimum().floatValue();
                this.mAmbientLightSensor.maxRefreshRate = refreshRate.getMaximum().floatValue();
                return;
            }
            return;
        }
        loadAmbientLightSensorFromConfigXml();
    }

    public final void setProxSensorUnspecified() {
        SensorData sensorData = this.mProximitySensor;
        sensorData.name = null;
        sensorData.type = null;
    }

    public final void loadScreenOffBrightnessSensorFromDdc(DisplayConfiguration displayConfiguration) {
        SensorDetails screenOffBrightnessSensor = displayConfiguration.getScreenOffBrightnessSensor();
        if (screenOffBrightnessSensor != null) {
            this.mScreenOffBrightnessSensor.type = screenOffBrightnessSensor.getType();
            this.mScreenOffBrightnessSensor.name = screenOffBrightnessSensor.getName();
        }
    }

    public final void loadProxSensorFromDdc(DisplayConfiguration displayConfiguration) {
        SensorDetails proxSensor = displayConfiguration.getProxSensor();
        if (proxSensor != null) {
            this.mProximitySensor.name = proxSensor.getName();
            this.mProximitySensor.type = proxSensor.getType();
            RefreshRateRange refreshRate = proxSensor.getRefreshRate();
            if (refreshRate != null) {
                this.mProximitySensor.minRefreshRate = refreshRate.getMinimum().floatValue();
                this.mProximitySensor.maxRefreshRate = refreshRate.getMaximum().floatValue();
                return;
            }
            return;
        }
        setProxSensorUnspecified();
    }

    public final void loadBrightnessChangeThresholdsFromXml() {
        loadBrightnessChangeThresholds(null);
    }

    public final void loadBrightnessChangeThresholds(DisplayConfiguration displayConfiguration) {
        loadDisplayBrightnessThresholds(displayConfiguration);
        loadAmbientBrightnessThresholds(displayConfiguration);
        loadDisplayBrightnessThresholdsIdle(displayConfiguration);
        loadAmbientBrightnessThresholdsIdle(displayConfiguration);
    }

    public final void loadDisplayBrightnessThresholds(DisplayConfiguration displayConfiguration) {
        BrightnessThresholds brightnessThresholds;
        BrightnessThresholds brightnessThresholds2;
        if (displayConfiguration == null || displayConfiguration.getDisplayBrightnessChangeThresholds() == null) {
            brightnessThresholds = null;
            brightnessThresholds2 = null;
        } else {
            BrightnessThresholds brighteningThresholds = displayConfiguration.getDisplayBrightnessChangeThresholds().getBrighteningThresholds();
            brightnessThresholds = displayConfiguration.getDisplayBrightnessChangeThresholds().getDarkeningThresholds();
            brightnessThresholds2 = brighteningThresholds;
        }
        float[] fArr = DEFAULT_SCREEN_THRESHOLD_LEVELS;
        Pair brightnessLevelAndPercentage = getBrightnessLevelAndPercentage(brightnessThresholds2, 17236297, 17236292, fArr, DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS, true);
        this.mScreenBrighteningLevels = (float[]) brightnessLevelAndPercentage.first;
        this.mScreenBrighteningPercentages = (float[]) brightnessLevelAndPercentage.second;
        Pair brightnessLevelAndPercentage2 = getBrightnessLevelAndPercentage(brightnessThresholds, 17236297, 17236296, fArr, DEFAULT_SCREEN_DARKENING_THRESHOLDS, true);
        this.mScreenDarkeningLevels = (float[]) brightnessLevelAndPercentage2.first;
        this.mScreenDarkeningPercentages = (float[]) brightnessLevelAndPercentage2.second;
        if (brightnessThresholds2 != null && brightnessThresholds2.getMinimum() != null) {
            this.mScreenBrighteningMinThreshold = brightnessThresholds2.getMinimum().floatValue();
        }
        if (brightnessThresholds == null || brightnessThresholds.getMinimum() == null) {
            return;
        }
        this.mScreenDarkeningMinThreshold = brightnessThresholds.getMinimum().floatValue();
    }

    public final void loadAmbientBrightnessThresholds(DisplayConfiguration displayConfiguration) {
        BrightnessThresholds brightnessThresholds;
        BrightnessThresholds brightnessThresholds2;
        if (displayConfiguration == null || displayConfiguration.getAmbientBrightnessChangeThresholds() == null) {
            brightnessThresholds = null;
            brightnessThresholds2 = null;
        } else {
            BrightnessThresholds brighteningThresholds = displayConfiguration.getAmbientBrightnessChangeThresholds().getBrighteningThresholds();
            brightnessThresholds = displayConfiguration.getAmbientBrightnessChangeThresholds().getDarkeningThresholds();
            brightnessThresholds2 = brighteningThresholds;
        }
        float[] fArr = DEFAULT_AMBIENT_THRESHOLD_LEVELS;
        Pair brightnessLevelAndPercentage = getBrightnessLevelAndPercentage(brightnessThresholds2, R.array.config_maskBuiltInDisplayCutoutArray, R.array.config_mainBuiltInDisplayWaterfallCutout, fArr, DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS);
        this.mAmbientBrighteningLevels = (float[]) brightnessLevelAndPercentage.first;
        this.mAmbientBrighteningPercentages = (float[]) brightnessLevelAndPercentage.second;
        Pair brightnessLevelAndPercentage2 = getBrightnessLevelAndPercentage(brightnessThresholds, R.array.config_maskBuiltInDisplayCutoutArray, R.array.config_mappedColorModes, fArr, DEFAULT_AMBIENT_DARKENING_THRESHOLDS);
        this.mAmbientDarkeningLevels = (float[]) brightnessLevelAndPercentage2.first;
        this.mAmbientDarkeningPercentages = (float[]) brightnessLevelAndPercentage2.second;
        if (brightnessThresholds2 != null && brightnessThresholds2.getMinimum() != null) {
            this.mAmbientLuxBrighteningMinThreshold = brightnessThresholds2.getMinimum().floatValue();
        }
        if (brightnessThresholds == null || brightnessThresholds.getMinimum() == null) {
            return;
        }
        this.mAmbientLuxDarkeningMinThreshold = brightnessThresholds.getMinimum().floatValue();
    }

    public final void loadDisplayBrightnessThresholdsIdle(DisplayConfiguration displayConfiguration) {
        BrightnessThresholds brightnessThresholds;
        BrightnessThresholds brightnessThresholds2;
        if (displayConfiguration == null || displayConfiguration.getDisplayBrightnessChangeThresholdsIdle() == null) {
            brightnessThresholds = null;
            brightnessThresholds2 = null;
        } else {
            BrightnessThresholds brighteningThresholds = displayConfiguration.getDisplayBrightnessChangeThresholdsIdle().getBrighteningThresholds();
            brightnessThresholds = displayConfiguration.getDisplayBrightnessChangeThresholdsIdle().getDarkeningThresholds();
            brightnessThresholds2 = brighteningThresholds;
        }
        float[] fArr = DEFAULT_SCREEN_THRESHOLD_LEVELS;
        Pair brightnessLevelAndPercentage = getBrightnessLevelAndPercentage(brightnessThresholds2, 17236297, 17236292, fArr, DEFAULT_SCREEN_BRIGHTENING_THRESHOLDS, true);
        this.mScreenBrighteningLevelsIdle = (float[]) brightnessLevelAndPercentage.first;
        this.mScreenBrighteningPercentagesIdle = (float[]) brightnessLevelAndPercentage.second;
        Pair brightnessLevelAndPercentage2 = getBrightnessLevelAndPercentage(brightnessThresholds, 17236297, 17236296, fArr, DEFAULT_SCREEN_DARKENING_THRESHOLDS, true);
        this.mScreenDarkeningLevelsIdle = (float[]) brightnessLevelAndPercentage2.first;
        this.mScreenDarkeningPercentagesIdle = (float[]) brightnessLevelAndPercentage2.second;
        if (brightnessThresholds2 != null && brightnessThresholds2.getMinimum() != null) {
            this.mScreenBrighteningMinThresholdIdle = brightnessThresholds2.getMinimum().floatValue();
        }
        if (brightnessThresholds == null || brightnessThresholds.getMinimum() == null) {
            return;
        }
        this.mScreenDarkeningMinThresholdIdle = brightnessThresholds.getMinimum().floatValue();
    }

    public final void loadAmbientBrightnessThresholdsIdle(DisplayConfiguration displayConfiguration) {
        BrightnessThresholds brightnessThresholds;
        BrightnessThresholds brightnessThresholds2;
        if (displayConfiguration == null || displayConfiguration.getAmbientBrightnessChangeThresholdsIdle() == null) {
            brightnessThresholds = null;
            brightnessThresholds2 = null;
        } else {
            BrightnessThresholds brighteningThresholds = displayConfiguration.getAmbientBrightnessChangeThresholdsIdle().getBrighteningThresholds();
            brightnessThresholds = displayConfiguration.getAmbientBrightnessChangeThresholdsIdle().getDarkeningThresholds();
            brightnessThresholds2 = brighteningThresholds;
        }
        float[] fArr = DEFAULT_AMBIENT_THRESHOLD_LEVELS;
        Pair brightnessLevelAndPercentage = getBrightnessLevelAndPercentage(brightnessThresholds2, R.array.config_maskBuiltInDisplayCutoutArray, R.array.config_mainBuiltInDisplayWaterfallCutout, fArr, DEFAULT_AMBIENT_BRIGHTENING_THRESHOLDS);
        this.mAmbientBrighteningLevelsIdle = (float[]) brightnessLevelAndPercentage.first;
        this.mAmbientBrighteningPercentagesIdle = (float[]) brightnessLevelAndPercentage.second;
        Pair brightnessLevelAndPercentage2 = getBrightnessLevelAndPercentage(brightnessThresholds, R.array.config_maskBuiltInDisplayCutoutArray, R.array.config_mappedColorModes, fArr, DEFAULT_AMBIENT_DARKENING_THRESHOLDS);
        this.mAmbientDarkeningLevelsIdle = (float[]) brightnessLevelAndPercentage2.first;
        this.mAmbientDarkeningPercentagesIdle = (float[]) brightnessLevelAndPercentage2.second;
        if (brightnessThresholds2 != null && brightnessThresholds2.getMinimum() != null) {
            this.mAmbientLuxBrighteningMinThresholdIdle = brightnessThresholds2.getMinimum().floatValue();
        }
        if (brightnessThresholds == null || brightnessThresholds.getMinimum() == null) {
            return;
        }
        this.mAmbientLuxDarkeningMinThresholdIdle = brightnessThresholds.getMinimum().floatValue();
    }

    public final Pair getBrightnessLevelAndPercentage(BrightnessThresholds brightnessThresholds, int i, int i2, float[] fArr, float[] fArr2) {
        return getBrightnessLevelAndPercentage(brightnessThresholds, i, i2, fArr, fArr2, false);
    }

    public final Pair getBrightnessLevelAndPercentage(BrightnessThresholds brightnessThresholds, int i, int i2, float[] fArr, float[] fArr2, boolean z) {
        int i3 = 0;
        if (brightnessThresholds != null && brightnessThresholds.getBrightnessThresholdPoints() != null && brightnessThresholds.getBrightnessThresholdPoints().getBrightnessThresholdPoint().size() != 0) {
            List<ThresholdPoint> brightnessThresholdPoint = brightnessThresholds.getBrightnessThresholdPoints().getBrightnessThresholdPoint();
            int size = brightnessThresholdPoint.size();
            float[] fArr3 = new float[size];
            float[] fArr4 = new float[size];
            for (ThresholdPoint thresholdPoint : brightnessThresholdPoint) {
                fArr3[i3] = thresholdPoint.getThreshold().floatValue();
                fArr4[i3] = thresholdPoint.getPercentage().floatValue();
                i3++;
            }
            return new Pair(fArr3, fArr4);
        }
        int[] intArray = this.mContext.getResources().getIntArray(i);
        int length = (intArray == null || intArray.length == 0) ? 1 : intArray.length + 1;
        int[] intArray2 = this.mContext.getResources().getIntArray(i2);
        boolean z2 = intArray2 == null || intArray2.length == 0;
        if (z2 && length == 1) {
            return new Pair(fArr, fArr2);
        }
        if (z2 || intArray2.length != length) {
            throw new IllegalArgumentException("Brightness threshold arrays do not align in length");
        }
        float[] fArr5 = new float[length];
        for (int i4 = 1; i4 < length; i4++) {
            fArr5[i4] = intArray[i4 - 1];
        }
        if (z) {
            fArr5 = constraintInRangeIfNeeded(fArr5);
        }
        float[] fArr6 = new float[length];
        while (i3 < intArray2.length) {
            fArr6[i3] = intArray2[i3] / 10.0f;
            i3++;
        }
        return new Pair(fArr5, fArr6);
    }

    public final float[] constraintInRangeIfNeeded(float[] fArr) {
        if (isAllInRange(fArr, DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f)) {
            return fArr;
        }
        Slog.w("DisplayDeviceConfig", "Detected screen thresholdLevels on a deprecated brightness scale");
        float[] fArr2 = new float[fArr.length];
        for (int i = 0; fArr.length > i; i++) {
            fArr2[i] = fArr[i] / 255.0f;
        }
        return fArr2;
    }

    public final boolean isAllInRange(float[] fArr, float f, float f2) {
        for (float f3 : fArr) {
            if (f3 < f || f3 > f2) {
                return false;
            }
        }
        return true;
    }

    /* renamed from: com.android.server.display.DisplayDeviceConfig$1 */
    public abstract /* synthetic */ class AbstractC12091 {
        public static final /* synthetic */ int[] $SwitchMap$com$android$server$display$config$ThermalStatus;

        static {
            int[] iArr = new int[ThermalStatus.values().length];
            $SwitchMap$com$android$server$display$config$ThermalStatus = iArr;
            try {
                iArr[ThermalStatus.none.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[ThermalStatus.light.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[ThermalStatus.moderate.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[ThermalStatus.severe.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[ThermalStatus.critical.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[ThermalStatus.emergency.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$android$server$display$config$ThermalStatus[ThermalStatus.shutdown.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public final boolean thermalStatusIsValid(ThermalStatus thermalStatus) {
        if (thermalStatus == null) {
            return false;
        }
        switch (AbstractC12091.$SwitchMap$com$android$server$display$config$ThermalStatus[thermalStatus.ordinal()]) {
        }
        return false;
    }

    public static int convertThermalStatus(ThermalStatus thermalStatus) {
        if (thermalStatus == null) {
            return 0;
        }
        switch (AbstractC12091.$SwitchMap$com$android$server$display$config$ThermalStatus[thermalStatus.ordinal()]) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            default:
                Slog.wtf("DisplayDeviceConfig", "Unexpected Thermal Status: " + thermalStatus);
                break;
        }
        return 0;
    }

    public final int convertInterpolationType(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        if ("linear".equals(str)) {
            return 1;
        }
        Slog.wtf("DisplayDeviceConfig", "Unexpected Interpolation Type: " + str);
        return 0;
    }

    public final void loadAmbientHorizonFromDdc(DisplayConfiguration displayConfiguration) {
        BigInteger ambientLightHorizonLong = displayConfiguration.getAmbientLightHorizonLong();
        if (ambientLightHorizonLong != null) {
            this.mAmbientHorizonLong = ambientLightHorizonLong.intValue();
        }
        BigInteger ambientLightHorizonShort = displayConfiguration.getAmbientLightHorizonShort();
        if (ambientLightHorizonShort != null) {
            this.mAmbientHorizonShort = ambientLightHorizonShort.intValue();
        }
    }

    public final void setFirstDisplay(boolean z) {
        this.mIsFirstDisplay = z;
        this.mIsCoverDisplay = PowerManagerUtil.SEC_FEATURE_FLIP_COVER_DISPLAY && !z;
    }

    public final void convertBacklightArrayForSurfaceControl() {
        this.mUseSurfaceControlBrightness = true;
        int i = 0;
        if (this.mIsSimpleMappingStrategy) {
            float[] fArr = {DisplayPowerController2.RATE_FROM_DOZE_TO_ON, this.mBacklightMaximum};
            float[] fArr2 = {DisplayPowerController2.RATE_FROM_DOZE_TO_ON, 1.0f};
            this.mBrightnessToBacklightSpline = Spline.createSpline(fArr, fArr2);
            this.mBacklightToBrightnessSpline = Spline.createSpline(fArr2, fArr);
            return;
        }
        while (true) {
            float[] fArr3 = this.mBacklight;
            if (i < fArr3.length) {
                fArr3[i] = fArr3[i] / this.mBacklightMaximum;
                i++;
            } else {
                createBacklightConversionSplines();
                return;
            }
        }
    }

    public final void loadHighBrightnessModeDataFromConfigXml() {
        boolean z = this.mBacklightMaximum > 1.0f;
        this.mIsHighBrightnessModeEnabled = z;
        if (z) {
            this.mHbmData = new HighBrightnessModeData(this.mContext.getResources().getInteger(R.integer.config_brightness_ramp_rate_slow), 1.0f, 0L, 0L, 0L, true, HDR_PERCENT_OF_SCREEN_REQUIRED_DEFAULT);
            this.mSdrToHdrSpline = loadSdrHdrMapFromConfigXml();
        }
    }

    public static float[] getFloatArray(TypedArray typedArray, float f) {
        int length = typedArray.length();
        float[] fArr = new float[length];
        for (int i = 0; i < length; i++) {
            fArr[i] = typedArray.getFloat(i, f);
        }
        typedArray.recycle();
        return fArr;
    }

    public static float[] getLuxLevels(int[] iArr) {
        float[] fArr = new float[iArr.length + 1];
        int i = 0;
        while (i < iArr.length) {
            int i2 = i + 1;
            fArr[i2] = iArr[i];
            i = i2;
        }
        return fArr;
    }

    public final void loadEnableAutoBrightness(AutoBrightness autoBrightness) {
        this.mDdcAutoBrightnessAvailable = true;
        if (autoBrightness != null) {
            this.mDdcAutoBrightnessAvailable = autoBrightness.getEnabled();
        }
        this.mAutoBrightnessAvailable = this.mContext.getResources().getBoolean(R.bool.config_autoPowerModeUseMotionSensor) && this.mDdcAutoBrightnessAvailable;
    }

    public final void loadScreenOffBrightnessSensorValueToLuxFromDdc(DisplayConfiguration displayConfiguration) {
        IntegerArray screenOffBrightnessSensorValueToLux = displayConfiguration.getScreenOffBrightnessSensorValueToLux();
        if (screenOffBrightnessSensorValueToLux == null) {
            return;
        }
        List item = screenOffBrightnessSensorValueToLux.getItem();
        this.mScreenOffBrightnessSensorValueToLux = new int[item.size()];
        for (int i = 0; i < item.size(); i++) {
            this.mScreenOffBrightnessSensorValueToLux[i] = ((BigInteger) item.get(i)).intValue();
        }
    }

    public final void loadUsiVersion(DisplayConfiguration displayConfiguration) {
        UsiVersion usiVersion = displayConfiguration.getUsiVersion();
        this.mHostUsiVersion = usiVersion != null ? new HostUsiVersion(usiVersion.getMajorVersion().intValue(), usiVersion.getMinorVersion().intValue()) : null;
    }

    public class SensorData {
        public String name;
        public String type;
        public float minRefreshRate = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        public float maxRefreshRate = Float.POSITIVE_INFINITY;

        public String toString() {
            return "Sensor{type: " + this.type + ", name: " + this.name + ", refreshRateRange: [" + this.minRefreshRate + ", " + this.maxRefreshRate + "]} ";
        }

        public boolean matches(String str, String str2) {
            boolean z = !TextUtils.isEmpty(str);
            boolean z2 = !TextUtils.isEmpty(str2);
            return (z || z2) && (!z || str.equals(this.name)) && (!z2 || str2.equals(this.type));
        }
    }

    public class HighBrightnessModeData {
        public boolean allowInLowPowerMode;
        public float minimumHdrPercentOfScreen;
        public float minimumLux;
        public long timeMaxMillis;
        public long timeMinMillis;
        public long timeWindowMillis;
        public float transitionPoint;

        public HighBrightnessModeData() {
        }

        public HighBrightnessModeData(float f, float f2, long j, long j2, long j3, boolean z, float f3) {
            this.minimumLux = f;
            this.transitionPoint = f2;
            this.timeWindowMillis = j;
            this.timeMaxMillis = j2;
            this.timeMinMillis = j3;
            this.allowInLowPowerMode = z;
            this.minimumHdrPercentOfScreen = f3;
        }

        public void copyTo(HighBrightnessModeData highBrightnessModeData) {
            highBrightnessModeData.minimumLux = this.minimumLux;
            highBrightnessModeData.timeWindowMillis = this.timeWindowMillis;
            highBrightnessModeData.timeMaxMillis = this.timeMaxMillis;
            highBrightnessModeData.timeMinMillis = this.timeMinMillis;
            highBrightnessModeData.transitionPoint = this.transitionPoint;
            highBrightnessModeData.allowInLowPowerMode = this.allowInLowPowerMode;
            highBrightnessModeData.minimumHdrPercentOfScreen = this.minimumHdrPercentOfScreen;
        }

        public String toString() {
            return "HBM{minLux: " + this.minimumLux + ", transition: " + this.transitionPoint + ", timeWindow: " + this.timeWindowMillis + "ms, timeMax: " + this.timeMaxMillis + "ms, timeMin: " + this.timeMinMillis + "ms, allowInLowPowerMode: " + this.allowInLowPowerMode + ", minimumHdrPercentOfScreen: " + this.minimumHdrPercentOfScreen + "} ";
        }
    }

    public class ThermalBrightnessThrottlingData {
        public List throttlingLevels;

        public class ThrottlingLevel {
            public float brightness;
            public int thermalStatus;

            public ThrottlingLevel(int i, float f) {
                this.thermalStatus = i;
                this.brightness = f;
            }

            public String toString() {
                return "[" + this.thermalStatus + "," + this.brightness + "]";
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof ThrottlingLevel)) {
                    return false;
                }
                ThrottlingLevel throttlingLevel = (ThrottlingLevel) obj;
                return throttlingLevel.thermalStatus == this.thermalStatus && throttlingLevel.brightness == this.brightness;
            }

            public int hashCode() {
                return ((this.thermalStatus + 31) * 31) + Float.hashCode(this.brightness);
            }
        }

        public static ThermalBrightnessThrottlingData create(List list) {
            if (list == null || list.size() == 0) {
                Slog.e("DisplayDeviceConfig", "BrightnessThrottlingData received null or empty throttling levels");
                return null;
            }
            ThrottlingLevel throttlingLevel = (ThrottlingLevel) list.get(0);
            int size = list.size();
            int i = 1;
            while (i < size) {
                ThrottlingLevel throttlingLevel2 = (ThrottlingLevel) list.get(i);
                if (throttlingLevel2.thermalStatus <= throttlingLevel.thermalStatus) {
                    Slog.e("DisplayDeviceConfig", "brightnessThrottlingMap must be strictly increasing, ignoring configuration. ThermalStatus " + throttlingLevel2.thermalStatus + " <= " + throttlingLevel.thermalStatus);
                    return null;
                }
                if (throttlingLevel2.brightness >= throttlingLevel.brightness) {
                    Slog.e("DisplayDeviceConfig", "brightnessThrottlingMap must be strictly decreasing, ignoring configuration. Brightness " + throttlingLevel2.brightness + " >= " + throttlingLevel2.brightness);
                    return null;
                }
                i++;
                throttlingLevel = throttlingLevel2;
            }
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ThrottlingLevel throttlingLevel3 = (ThrottlingLevel) it.next();
                if (throttlingLevel3.brightness > 1.0f) {
                    Slog.e("DisplayDeviceConfig", "brightnessThrottlingMap contains a brightness value exceeding system max. Brightness " + throttlingLevel3.brightness + " > 1.0");
                    return null;
                }
            }
            return new ThermalBrightnessThrottlingData(list);
        }

        public String toString() {
            return "ThermalBrightnessThrottlingData{throttlingLevels:" + this.throttlingLevels + "} ";
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof ThermalBrightnessThrottlingData) {
                return this.throttlingLevels.equals(((ThermalBrightnessThrottlingData) obj).throttlingLevels);
            }
            return false;
        }

        public int hashCode() {
            return this.throttlingLevels.hashCode();
        }

        public ThermalBrightnessThrottlingData(List list) {
            this.throttlingLevels = new ArrayList(list.size());
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ThrottlingLevel throttlingLevel = (ThrottlingLevel) it.next();
                this.throttlingLevels.add(new ThrottlingLevel(throttlingLevel.thermalStatus, throttlingLevel.brightness));
            }
        }
    }
}
