package com.samsung.android.displaysolution;

import android.R;
import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SemHqmManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.NandswapManager$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;

import com.samsung.android.displayaiqe.DisplayAiqeManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class EyeComfortSolutionService {
    public final int ACTION_GET_BOPR_VALUE_DEBOUNCE_MILLIS;
    public final int BOPR_MAX_VALUE;
    public int BOPR_VALUE_STEP;
    public final int GET_SYSTEM_SERVICES_MILLIS;
    public float app_weighting_factor;
    public int bopr_cumulative_count;
    public float cal_value_sum;
    public int defaultBedTime;
    public int defaultWakeupTime;
    public final boolean mAdaptiveBlueLightFilterSupported;
    public int mBedtime_friday;
    public int mBedtime_monday;
    public int mBedtime_saturday;
    public int mBedtime_sunday;
    public int mBedtime_thursday;
    public int mBedtime_tuesday;
    public int mBedtime_wednesday;
    public int mBlfIndexEadOffset;
    public boolean mBlueLightFilterAdaptiveModeEnabled;
    public boolean mBlueLightFilterCustomAlwaysOn;
    public boolean mBlueLightFilterEnableTime;
    public boolean mBlueLightFilterModeEnabled;
    public boolean mBlueLightFilterScheduledTime;
    public Sensor mBoprSensor;
    public final AnonymousClass1 mBoprSensorListener;
    public final boolean mColorOnPixelRatioSupported;
    public final Context mContext;
    public boolean mCurrentStateSleep;
    public boolean mCurrentStateWakeup;
    public boolean mDefaultThemeEnabled;
    public DisplayAiqeManager mDisplayAiqeManager;
    public final boolean mEnvironmentAdaptiveDisplaySupported;
    public final ECSControlHandler mHandler;
    public int mLastBoprBlueLightFilterIndex;
    public int mLastDayBlueLightFilterIndex;
    public int mLastTotalBlueLightFilterIndex;
    public SemMdnieManager mMdnieManager;
    public final boolean mNaturalGammaScreenModeSupported;
    public int mNightDimFontConfigValue;
    public int mNightDimFontConfigValuePrev;
    public boolean mNightDimModeEnabled;
    public int mPrevClutValue;
    public String mPrevmDNIeMode = null;
    public int mScreenModeSetting;
    public boolean mScreenOn;
    public SemHqmManager mSemHqmManager;
    public SensorManager mSensorManager;
    public final boolean mSupportAPmDNIe;
    public int mWakeupTime_friday;
    public int mWakeupTime_monday;
    public int mWakeupTime_saturday;
    public int mWakeupTime_sunday;
    public int mWakeupTime_thursday;
    public int mWakeupTime_tuesday;
    public int mWakeupTime_wednesday;
    public int prevTotalStep;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ECSControlHandler extends Handler {
        public ECSControlHandler(Looper looper) {
            super(looper, null);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            EyeComfortSolutionService eyeComfortSolutionService = EyeComfortSolutionService.this;
            if (i != 1) {
                if (i == 2) {
                    EyeComfortSolutionService.m1136$$Nest$mgetBoprValue(
                            eyeComfortSolutionService, true);
                    return;
                }
                if (i == 3) {
                    EyeComfortSolutionService.m1136$$Nest$mgetBoprValue(
                            eyeComfortSolutionService, false);
                    return;
                } else if (i == 4) {
                    eyeComfortSolutionService.blue_light_filter_setting_day(
                            eyeComfortSolutionService.mBlueLightFilterModeEnabled
                                    && eyeComfortSolutionService
                                            .mBlueLightFilterAdaptiveModeEnabled);
                    return;
                } else {
                    if (i != 5) {
                        return;
                    }
                    eyeComfortSolutionService.updateNightDimSettings(-1);
                    return;
                }
            }
            boolean z = eyeComfortSolutionService.mSupportAPmDNIe;
            int i2 = eyeComfortSolutionService.GET_SYSTEM_SERVICES_MILLIS;
            ECSControlHandler eCSControlHandler = eyeComfortSolutionService.mHandler;
            if (z) {
                eyeComfortSolutionService.mDisplayAiqeManager =
                        (DisplayAiqeManager)
                                eyeComfortSolutionService.mContext.getSystemService("display_aiqe");
                Slog.v(
                        "EyeComfortSolutionService",
                        "mDisplayAiqeManager : " + eyeComfortSolutionService.mDisplayAiqeManager);
                if (eyeComfortSolutionService.mDisplayAiqeManager != null) {
                    Slog.v(
                            "EyeComfortSolutionService",
                            "Success to register all of the services system.");
                    return;
                }
                long uptimeMillis = SystemClock.uptimeMillis();
                eCSControlHandler.removeMessages(1);
                eCSControlHandler.sendEmptyMessageAtTime(1, uptimeMillis + i2);
                Slog.v(
                        "EyeComfortSolutionService",
                        "Failure to register all of the services system.");
                return;
            }
            eyeComfortSolutionService.mSensorManager =
                    (SensorManager) eyeComfortSolutionService.mContext.getSystemService("sensor");
            Slog.v(
                    "EyeComfortSolutionService",
                    "mSensorManager : " + eyeComfortSolutionService.mSensorManager);
            SensorManager sensorManager = eyeComfortSolutionService.mSensorManager;
            if (sensorManager != null) {
                eyeComfortSolutionService.mBoprSensor = sensorManager.getDefaultSensor(65587);
            }
            if (eyeComfortSolutionService.mSensorManager != null) {
                Slog.v(
                        "EyeComfortSolutionService",
                        "Success to register all of the services system.");
                return;
            }
            long uptimeMillis2 = SystemClock.uptimeMillis();
            eCSControlHandler.removeMessages(1);
            eCSControlHandler.sendEmptyMessageAtTime(1, uptimeMillis2 + i2);
            Slog.v("EyeComfortSolutionService", "Failure to register all of the services system.");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            long uptimeMillis = SystemClock.uptimeMillis();
            Slog.i("EyeComfortSolutionService", "action  :  " + action);
            if ("android.intent.action.BOOT_COMPLETED".equals(action)
                    || "android.intent.action.USER_SWITCHED".equals(action)) {
                EyeComfortSolutionService.m1137$$Nest$msetting_is_changed(
                        EyeComfortSolutionService.this);
                EyeComfortSolutionService.this.getClass();
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                EyeComfortSolutionService eyeComfortSolutionService =
                        EyeComfortSolutionService.this;
                eyeComfortSolutionService.mScreenOn = true;
                if (eyeComfortSolutionService.mBlueLightFilterModeEnabled
                        && eyeComfortSolutionService.mBlueLightFilterAdaptiveModeEnabled
                        && eyeComfortSolutionService.mBlueLightFilterEnableTime) {
                    eyeComfortSolutionService.mHandler.removeMessages(2);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessageAtTime(
                            2, uptimeMillis + r5.ACTION_GET_BOPR_VALUE_DEBOUNCE_MILLIS);
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                EyeComfortSolutionService eyeComfortSolutionService2 =
                        EyeComfortSolutionService.this;
                eyeComfortSolutionService2.mScreenOn = false;
                if (eyeComfortSolutionService2.mBlueLightFilterModeEnabled
                        && eyeComfortSolutionService2.mBlueLightFilterAdaptiveModeEnabled
                        && eyeComfortSolutionService2.mBlueLightFilterEnableTime) {
                    eyeComfortSolutionService2.mHandler.removeMessages(2);
                    EyeComfortSolutionService.this.mHandler.removeMessages(3);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessage(3);
                    return;
                }
                return;
            }
            if ("android.intent.action.USER_PRESENT".equals(action)) {
                return;
            }
            if ("android.intent.action.TIME_TICK".equals(action)) {
                EyeComfortSolutionService eyeComfortSolutionService3 =
                        EyeComfortSolutionService.this;
                if (eyeComfortSolutionService3.mBlueLightFilterModeEnabled
                        && eyeComfortSolutionService3.mBlueLightFilterAdaptiveModeEnabled) {
                    eyeComfortSolutionService3.mHandler.removeMessages(4);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessage(4);
                    return;
                }
                return;
            }
            if ("android.intent.action.TIME_SET".equals(action)) {
                EyeComfortSolutionService eyeComfortSolutionService4 =
                        EyeComfortSolutionService.this;
                if (eyeComfortSolutionService4.mBlueLightFilterModeEnabled
                        && eyeComfortSolutionService4.mBlueLightFilterAdaptiveModeEnabled) {
                    eyeComfortSolutionService4.mHandler.removeMessages(4);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessage(4);
                    return;
                }
                return;
            }
            if ("android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                EyeComfortSolutionService eyeComfortSolutionService5 =
                        EyeComfortSolutionService.this;
                if (eyeComfortSolutionService5.mBlueLightFilterModeEnabled
                        && eyeComfortSolutionService5.mBlueLightFilterAdaptiveModeEnabled) {
                    eyeComfortSolutionService5.mHandler.removeMessages(4);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessage(4);
                    return;
                }
                return;
            }
            if ("com.sec.android.intent.action.HQM_UPDATE_REQ".equals(action)) {
                EyeComfortSolutionService eyeComfortSolutionService6 =
                        EyeComfortSolutionService.this;
                eyeComfortSolutionService6.mSemHqmManager =
                        (SemHqmManager)
                                eyeComfortSolutionService6.mContext.getSystemService(
                                        "HqmManagerService");
                if (EyeComfortSolutionService.this.mSemHqmManager != null) {
                    Integer.toString(0);
                    Integer.toString(0);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(ECSControlHandler eCSControlHandler) {
            super(eCSControlHandler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            EyeComfortSolutionService.m1137$$Nest$msetting_is_changed(
                    EyeComfortSolutionService.this);
        }
    }

    /* renamed from: -$$Nest$mgetBoprValue, reason: not valid java name */
    public static void m1136$$Nest$mgetBoprValue(
            EyeComfortSolutionService eyeComfortSolutionService, boolean z) {
        SensorManager sensorManager;
        boolean z2 = eyeComfortSolutionService.mColorOnPixelRatioSupported;
        boolean z3 = eyeComfortSolutionService.mSupportAPmDNIe;
        if (!z) {
            if (z3 || (sensorManager = eyeComfortSolutionService.mSensorManager) == null || !z2) {
                return;
            }
            sensorManager.unregisterListener(eyeComfortSolutionService.mBoprSensorListener);
            return;
        }
        ECSControlHandler eCSControlHandler = eyeComfortSolutionService.mHandler;
        if (!z3) {
            SensorManager sensorManager2 = eyeComfortSolutionService.mSensorManager;
            if (sensorManager2 == null || !z2) {
                return;
            }
            sensorManager2.registerListener(
                    eyeComfortSolutionService.mBoprSensorListener,
                    eyeComfortSolutionService.mBoprSensor,
                    3,
                    eCSControlHandler);
            return;
        }
        long uptimeMillis = SystemClock.uptimeMillis();
        int coprValue = eyeComfortSolutionService.mDisplayAiqeManager.getCoprValue();
        int i = eyeComfortSolutionService.bopr_cumulative_count + 1;
        eyeComfortSolutionService.bopr_cumulative_count = i;
        if (eyeComfortSolutionService.mBlueLightFilterEnableTime) {
            eyeComfortSolutionService.blue_light_filter_setting_bopr(coprValue, i);
        }
        if (eyeComfortSolutionService.BOPR_VALUE_STEP >= 54
                || !eyeComfortSolutionService.mBlueLightFilterEnableTime) {
            return;
        }
        eCSControlHandler.removeMessages(2);
        eCSControlHandler.sendEmptyMessageAtTime(
                2, uptimeMillis + eyeComfortSolutionService.ACTION_GET_BOPR_VALUE_DEBOUNCE_MILLIS);
    }

    /* renamed from: -$$Nest$msetting_is_changed, reason: not valid java name */
    public static void m1137$$Nest$msetting_is_changed(
            EyeComfortSolutionService eyeComfortSolutionService) {
        ContentResolver contentResolver = eyeComfortSolutionService.mContext.getContentResolver();
        long uptimeMillis = SystemClock.uptimeMillis();
        eyeComfortSolutionService.mBlueLightFilterModeEnabled =
                Settings.System.getIntForUser(contentResolver, "blue_light_filter", 0, -2) == 1;
        eyeComfortSolutionService.mNightDimModeEnabled =
                Settings.System.getIntForUser(contentResolver, "blue_light_filter_night_dim", 0, -2)
                        == 1;
        eyeComfortSolutionService.mBlueLightFilterAdaptiveModeEnabled =
                Settings.System.getIntForUser(
                                contentResolver, "blue_light_filter_adaptive_mode", 0, -2)
                        == 1;
        eyeComfortSolutionService.mDefaultThemeEnabled =
                Settings.System.getStringForUser(
                                contentResolver, "current_sec_active_themepackage", -2)
                        == null;
        eyeComfortSolutionService.mBlueLightFilterCustomAlwaysOn =
                Settings.System.getIntForUser(contentResolver, "blue_light_filter_type", 0, -2)
                        == 0;
        eyeComfortSolutionService.mScreenModeSetting =
                Settings.System.getIntForUser(
                        eyeComfortSolutionService.mContext.getContentResolver(),
                        "screen_mode_setting",
                        4,
                        -2);
        StringBuilder sb = new StringBuilder("mBlueLightFilterModeEnabled : ");
        sb.append(eyeComfortSolutionService.mBlueLightFilterModeEnabled);
        sb.append(" , mNightDimModeEnabled : ");
        sb.append(eyeComfortSolutionService.mNightDimModeEnabled);
        sb.append(" , mBlueLightFilterAdaptiveModeEnabled : ");
        sb.append(eyeComfortSolutionService.mBlueLightFilterAdaptiveModeEnabled);
        sb.append(" , mDefaultThemeEnabled : ");
        sb.append(eyeComfortSolutionService.mDefaultThemeEnabled);
        sb.append(" , mBlueLightFilterCustomAlwaysOn : ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "EyeComfortSolutionService",
                sb,
                eyeComfortSolutionService.mBlueLightFilterCustomAlwaysOn);
        boolean z = eyeComfortSolutionService.mBlueLightFilterModeEnabled;
        ECSControlHandler eCSControlHandler = eyeComfortSolutionService.mHandler;
        if (z && eyeComfortSolutionService.mBlueLightFilterAdaptiveModeEnabled) {
            eyeComfortSolutionService.updateSleepWakeupTime("All");
            eyeComfortSolutionService.blue_light_filter_setting_day(true);
            if (eyeComfortSolutionService.mScreenOn
                    && eyeComfortSolutionService.mBlueLightFilterEnableTime) {
                eCSControlHandler.removeMessages(2);
                eCSControlHandler.sendEmptyMessageAtTime(
                        2,
                        uptimeMillis
                                + eyeComfortSolutionService.ACTION_GET_BOPR_VALUE_DEBOUNCE_MILLIS);
            }
        } else {
            eyeComfortSolutionService.blue_light_filter_setting_day(false);
            eCSControlHandler.removeMessages(2);
            eCSControlHandler.removeMessages(3);
            eCSControlHandler.sendEmptyMessage(3);
            Slog.i("EyeComfortSolutionService", "reset blue_light_filter_data_reset");
            eyeComfortSolutionService.BOPR_VALUE_STEP = 0;
            eyeComfortSolutionService.bopr_cumulative_count = 0;
            eyeComfortSolutionService.cal_value_sum =
                    FullScreenMagnificationGestureHandler.MAX_SCALE;
            eyeComfortSolutionService.mLastDayBlueLightFilterIndex = 0;
            eyeComfortSolutionService.mLastBoprBlueLightFilterIndex = 0;
            eyeComfortSolutionService.mLastTotalBlueLightFilterIndex = 0;
        }
        eyeComfortSolutionService.updateNightDimSettings(
                eyeComfortSolutionService.mLastTotalBlueLightFilterIndex);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.samsung.android.displaysolution.EyeComfortSolutionService$1] */
    public EyeComfortSolutionService(Context context) {
        "eng".equals(Build.TYPE);
        this.GET_SYSTEM_SERVICES_MILLIS = 4000;
        this.ACTION_GET_BOPR_VALUE_DEBOUNCE_MILLIS = 10000;
        this.BOPR_MAX_VALUE = 0;
        this.bopr_cumulative_count = 0;
        this.mScreenModeSetting = 4;
        this.mPrevClutValue = -1;
        this.app_weighting_factor = 1.0f;
        this.cal_value_sum = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.prevTotalStep = -1;
        this.BOPR_VALUE_STEP = 0;
        this.mLastDayBlueLightFilterIndex = 0;
        this.mLastBoprBlueLightFilterIndex = 0;
        this.mLastTotalBlueLightFilterIndex = 0;
        this.mNightDimFontConfigValue = 0;
        this.mNightDimFontConfigValuePrev = -1;
        this.mBlfIndexEadOffset = 0;
        this.defaultBedTime = 0;
        this.defaultWakeupTime = 360;
        this.mBedtime_sunday = -1;
        this.mBedtime_monday = -1;
        this.mBedtime_tuesday = -1;
        this.mBedtime_wednesday = -1;
        this.mBedtime_thursday = -1;
        this.mBedtime_friday = -1;
        this.mBedtime_saturday = -1;
        this.mWakeupTime_sunday = -1;
        this.mWakeupTime_monday = -1;
        this.mWakeupTime_tuesday = -1;
        this.mWakeupTime_wednesday = -1;
        this.mWakeupTime_thursday = -1;
        this.mWakeupTime_friday = -1;
        this.mWakeupTime_saturday = -1;
        this.mBoprSensor = null;
        this.mSupportAPmDNIe = false;
        this.mScreenOn = false;
        this.mBlueLightFilterModeEnabled = false;
        this.mBlueLightFilterAdaptiveModeEnabled = false;
        this.mNightDimModeEnabled = false;
        this.mDefaultThemeEnabled = false;
        this.mCurrentStateSleep = false;
        this.mCurrentStateWakeup = false;
        this.mBlueLightFilterEnableTime = false;
        this.mAdaptiveBlueLightFilterSupported = false;
        this.mColorOnPixelRatioSupported = false;
        this.mBlueLightFilterScheduledTime = false;
        this.mBlueLightFilterCustomAlwaysOn = false;
        this.mEnvironmentAdaptiveDisplaySupported = false;
        this.mNaturalGammaScreenModeSupported = false;
        this.mBoprSensorListener =
                new SensorEventListener() { // from class:
                                            // com.samsung.android.displaysolution.EyeComfortSolutionService.1
                    @Override // android.hardware.SensorEventListener
                    public final void onAccuracyChanged(Sensor sensor, int i) {}

                    @Override // android.hardware.SensorEventListener
                    public final void onSensorChanged(SensorEvent sensorEvent) {
                        long uptimeMillis = SystemClock.uptimeMillis();
                        EyeComfortSolutionService eyeComfortSolutionService =
                                EyeComfortSolutionService.this;
                        int i = (int) sensorEvent.values[2];
                        eyeComfortSolutionService.getClass();
                        int i2 = eyeComfortSolutionService.bopr_cumulative_count + 1;
                        eyeComfortSolutionService.bopr_cumulative_count = i2;
                        if (eyeComfortSolutionService.mBlueLightFilterEnableTime) {
                            eyeComfortSolutionService.blue_light_filter_setting_bopr(i, i2);
                        }
                        EyeComfortSolutionService eyeComfortSolutionService2 =
                                EyeComfortSolutionService.this;
                        eyeComfortSolutionService2.mSensorManager.unregisterListener(
                                eyeComfortSolutionService2.mBoprSensorListener);
                        EyeComfortSolutionService eyeComfortSolutionService3 =
                                EyeComfortSolutionService.this;
                        if (eyeComfortSolutionService3.BOPR_VALUE_STEP >= 54
                                || !eyeComfortSolutionService3.mBlueLightFilterEnableTime) {
                            return;
                        }
                        eyeComfortSolutionService3.mHandler.removeMessages(2);
                        EyeComfortSolutionService.this.mHandler.sendEmptyMessageAtTime(
                                2, uptimeMillis + r6.ACTION_GET_BOPR_VALUE_DEBOUNCE_MILLIS);
                    }
                };
        this.mContext = context;
        ECSControlHandler eCSControlHandler =
                new ECSControlHandler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                        "EyeComfortSolutionServiceThread")
                                .getLooper());
        this.mHandler = eCSControlHandler;
        boolean z =
                context.getResources().getBoolean(R.bool.config_goToSleepOnButtonPressTheaterMode);
        this.BOPR_MAX_VALUE =
                context.getResources().getInteger(R.integer.config_attentiveWarningDuration);
        this.mEnvironmentAdaptiveDisplaySupported = true;
        Slog.i("EyeComfortSolutionService", "mEnvironmentAdaptiveDisplaySupported true");
        SettingsObserver settingsObserver = new SettingsObserver(eCSControlHandler);
        ContentResolver contentResolver = context.getContentResolver();
        long uptimeMillis = SystemClock.uptimeMillis();
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("blue_light_filter"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("blue_light_filter_night_dim"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("blue_light_filter_adaptive_mode"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("current_sec_active_themepackage"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("blue_light_filter_type"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("screen_mode_setting"), false, settingsObserver, -2);
        IntentFilter intentFilter = new IntentFilter();
        ActivityManagerService$$ExternalSyntheticOutline0.m(
                intentFilter,
                "android.intent.action.BOOT_COMPLETED",
                "android.intent.action.USER_SWITCHED",
                "android.intent.action.SCREEN_ON",
                "android.intent.action.SCREEN_OFF");
        ActivityManagerService$$ExternalSyntheticOutline0.m(
                intentFilter,
                "android.intent.action.USER_PRESENT",
                "android.intent.action.TIME_TICK",
                "android.intent.action.TIME_SET",
                "android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
        context.registerReceiverAsUser(
                new ScreenWatchingReceiver(), UserHandle.ALL, intentFilter, null, null);
        eCSControlHandler.removeMessages(1);
        eCSControlHandler.sendEmptyMessageAtTime(1, 4000 + uptimeMillis);
        if (SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0)
                > 0) {
            this.mAdaptiveBlueLightFilterSupported = true;
        }
        if (SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0)
                == 1) {
            this.mColorOnPixelRatioSupported = true;
        }
        if (SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NATURAL_MODE_TYPE", 0)
                == 1) {
            this.mNaturalGammaScreenModeSupported = true;
        }
        if (!"DDI"
                .equals(
                        SemFloatingFeature.getInstance()
                                .getString("SEC_FLOATING_FEATURE_LCD_CONFIG_HW_MDNIE"))) {
            this.mSupportAPmDNIe = true;
        }
        Slog.i(
                "EyeComfortSolutionService",
                "A_BLF : "
                        + this.mAdaptiveBlueLightFilterSupported
                        + " , COPR_IP : "
                        + this.mColorOnPixelRatioSupported);
        SystemProperties.set("sys.eyecomfortsolution.ecson", "false");
        if (z) {
            SystemProperties.set("sys.eyecomfortsolution.ecson", "true");
        }
        Slog.i("EyeComfortSolutionService", "EyeComfortSolutionService Enabled");
    }

    public static void sysfsWrite(int i, String str) {
        File file = new File(str);
        if (!file.exists()) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(Integer.toString(i).getBytes());
                    fileOutputStream2.close();
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
        } catch (IOException e4) {
            e = e4;
        }
    }

    public final void blue_light_filter_setting_bopr(int i, int i2) {
        this.mContext.getContentResolver();
        float f =
                Settings.System.getInt(this.mContext.getContentResolver(), "screen_brightness", 0);
        float pow =
                (float) (Math.pow(r8 / 256.0f, 2.200000047683716d) * this.app_weighting_factor * f);
        this.cal_value_sum += pow;
        StringBuilder sb = new StringBuilder("count : ");
        sb.append(i2);
        sb.append(" , bopr_value : ");
        sb.append(i);
        sb.append(", platform_value : ");
        sb.append(f);
        sb.append(" , app_weighting : ");
        sb.append(this.app_weighting_factor);
        sb.append(" , cal_value : ");
        sb.append(pow);
        sb.append(" , sum_value : ");
        sb.append(this.cal_value_sum);
        sb.append(" , BOPR_MAX_VALUE : ");
        GmsAlarmManager$$ExternalSyntheticOutline0.m(
                sb, this.BOPR_MAX_VALUE, "EyeComfortSolutionService");
        if (this.BOPR_VALUE_STEP < 54) {
            for (int i3 = 0; i3 < 54; i3++) {
                float f2 = this.cal_value_sum;
                int i4 = this.BOPR_VALUE_STEP;
                int i5 = i4 + 1;
                if (f2 >= (r8 / 54) * i5 && i4 == i3) {
                    this.mLastBoprBlueLightFilterIndex = i5;
                    StringBuilder sb2 =
                            new StringBuilder("Change BLF step by B-opr, BLF dayIndex : [");
                    sb2.append(this.mLastDayBlueLightFilterIndex);
                    sb2.append("] , boprIndex : [");
                    CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                            sb2,
                            this.mLastBoprBlueLightFilterIndex,
                            "]",
                            "EyeComfortSolutionService");
                    int i6 = this.mLastDayBlueLightFilterIndex + this.mLastBoprBlueLightFilterIndex;
                    this.mLastTotalBlueLightFilterIndex = i6;
                    setBlueLightFilterMode(i6, true);
                    this.BOPR_VALUE_STEP++;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:80:0x01a3, code lost:

       if (r9 < 36) goto L75;
    */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01d3, code lost:

       if (r9 < 36) goto L75;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void blue_light_filter_setting_day(boolean r18) {
        /*
            Method dump skipped, instructions count: 782
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.displaysolution.EyeComfortSolutionService.blue_light_filter_setting_day(boolean):void");
    }

    public final void setBlfEnableTimeBySchedule(boolean z, int i) {
        this.mBlueLightFilterScheduledTime = z;
        Slog.d(
                "EyeComfortSolutionService",
                "setBlueLightFilterModeEnabledTime - mBlueLightFilterScheduledTime: "
                        + this.mBlueLightFilterScheduledTime
                        + " , index: "
                        + i);
        ECSControlHandler eCSControlHandler = this.mHandler;
        eCSControlHandler.sendMessage(Message.obtain(eCSControlHandler, 5));
    }

    public final void setBlueLightFilterMode(int i, boolean z) {
        SemMdnieManager semMdnieManager;
        if (this.mAdaptiveBlueLightFilterSupported) {
            boolean z2 = this.mColorOnPixelRatioSupported;
            if (z2) {
                StringBuilder m =
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                i,
                                "BLF Adaptive Mode Enable : ",
                                " , targetIndex : [",
                                " + 11] = ",
                                z);
                m.append(6000.0d - (i * 22.22222222222222d));
                m.append(" , ead offset : ");
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        m, this.mBlfIndexEadOffset, "EyeComfortSolutionService");
            } else if (!z2) {
                StringBuilder m2 =
                        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                                i,
                                "BLF Adaptive Mode Enable : ",
                                " , targetIndex : [",
                                " + 11] = ",
                                z);
                m2.append(6000.0d - (i * 55.55555555555556d));
                m2.append(" , ead offset : ");
                SystemServiceManager$$ExternalSyntheticOutline0.m(
                        m2, this.mBlfIndexEadOffset, "EyeComfortSolutionService");
            }
            this.mMdnieManager = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
            Slog.v("EyeComfortSolutionService", "mMdnieManager : " + this.mMdnieManager);
            boolean z3 = this.mEnvironmentAdaptiveDisplaySupported;
            if (!z3) {
                SemMdnieManager semMdnieManager2 = this.mMdnieManager;
                if (semMdnieManager2 != null) {
                    int i2 = i + 11;
                    semMdnieManager2.setNightModeStep(i2);
                    this.mMdnieManager.setNightMode(z, i2);
                }
            } else if (z3 && (semMdnieManager = this.mMdnieManager) != null) {
                int i3 = z2 ? 101 : !z2 ? 47 : 0;
                int i4 = this.mBlfIndexEadOffset + i + 11;
                if (i4 <= i3) {
                    i3 = i4;
                }
                semMdnieManager.setNightModeStep(i3);
                this.mMdnieManager.setNightMode(z, i3);
            }
            updateNightDimSettings(i);
        }
    }

    public final void setmDNIeModeState(String str) {
        if (!this.mNaturalGammaScreenModeSupported
                || str == null
                || str.equals(this.mPrevmDNIeMode)) {
            return;
        }
        this.mPrevmDNIeMode = str;
        if (str.contains("NATURAL_DS") || (!str.contains("HBM_MODE") && str.contains("EAD_MODE"))) {
            if (this.mPrevClutValue != 2) {
                sysfsWrite(2, "/sys/class/lcd/panel/night_dim");
                if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
                    sysfsWrite(2, "/sys/class/lcd/panel1/night_dim");
                }
                this.mPrevClutValue = 2;
                Slog.d("EyeComfortSolutionService", "NIGHT_DIM_Mode : 2 (Natural Gamma On)");
                return;
            }
            return;
        }
        if (str.contains("HBM_MODE") || !str.contains("BLF_MODE")) {
            if (this.mPrevClutValue != 0) {
                sysfsWrite(0, "/sys/class/lcd/panel/night_dim");
                if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
                    sysfsWrite(0, "/sys/class/lcd/panel1/night_dim");
                }
                this.mPrevClutValue = 0;
                Slog.d("EyeComfortSolutionService", "NIGHT_DIM_Mode : 0 (CLUT Off)");
                return;
            }
            return;
        }
        if (!this.mNightDimModeEnabled) {
            if (this.mPrevClutValue != 0) {
                sysfsWrite(0, "/sys/class/lcd/panel/night_dim");
                if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
                    sysfsWrite(0, "/sys/class/lcd/panel1/night_dim");
                }
                this.mPrevClutValue = 0;
                Slog.d("EyeComfortSolutionService", "NIGHT_DIM_Mode : 0 (CLUT off)");
                return;
            }
            return;
        }
        int i = this.mScreenModeSetting;
        if (i == 4) {
            if (this.mPrevClutValue != 1) {
                sysfsWrite(1, "/sys/class/lcd/panel/night_dim");
                if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
                    sysfsWrite(1, "/sys/class/lcd/panel1/night_dim");
                }
                this.mPrevClutValue = 1;
                Slog.d("EyeComfortSolutionService", "NIGHT_DIM_Mode : 1 (E.C Vivid)");
                return;
            }
            return;
        }
        if (i != 3 || this.mPrevClutValue == 3) {
            return;
        }
        sysfsWrite(3, "/sys/class/lcd/panel/night_dim");
        if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
            sysfsWrite(3, "/sys/class/lcd/panel1/night_dim");
        }
        this.mPrevClutValue = 3;
        Slog.d("EyeComfortSolutionService", "NIGHT_DIM_Mode : 3 (E.C Natural)");
    }

    public final void updateNightDimSettings(int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (!this.mBlueLightFilterModeEnabled || !this.mNightDimModeEnabled) {
            this.mNightDimFontConfigValue = 0;
        } else if (this.mBlueLightFilterAdaptiveModeEnabled) {
            if (!this.mDefaultThemeEnabled) {
                this.mNightDimFontConfigValue = 32;
            } else if (i == 0) {
                this.mNightDimFontConfigValue = 32;
            } else if (i > 0 && i <= 11) {
                this.mNightDimFontConfigValue = 38;
            } else if (i > 11 && i <= 22) {
                this.mNightDimFontConfigValue = 44;
            } else if (i > 22 && i <= 34) {
                this.mNightDimFontConfigValue = 50;
            } else if (i > 34 && i <= 45) {
                this.mNightDimFontConfigValue = 56;
            } else if (i > 45 && i <= 56) {
                this.mNightDimFontConfigValue = 62;
            } else if (i > 56 && i <= 68) {
                this.mNightDimFontConfigValue = 68;
            } else if (i > 68) {
                this.mNightDimFontConfigValue = 75;
            }
        } else if (!this.mBlueLightFilterScheduledTime && !this.mBlueLightFilterCustomAlwaysOn) {
            this.mNightDimFontConfigValue = 0;
        } else if (this.mDefaultThemeEnabled) {
            this.mNightDimFontConfigValue = 75;
        } else {
            this.mNightDimFontConfigValue = 32;
        }
        DeviceIdleController$$ExternalSyntheticOutline0.m(
                BatteryService$$ExternalSyntheticOutline0.m(
                        i, "updateNightDimSettings index : ", " , mNightDimFontConfigValue : "),
                this.mNightDimFontConfigValue,
                "EyeComfortSolutionService");
        if (this.mNightDimFontConfigValue != this.mNightDimFontConfigValuePrev) {
            StringBuilder sb = new StringBuilder("updateConfiguration (");
            sb.append(this.mNightDimFontConfigValuePrev);
            sb.append(") -> (");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    sb, this.mNightDimFontConfigValue, ")", "EyeComfortSolutionService");
            int i2 = this.mNightDimFontConfigValue;
            this.mNightDimFontConfigValuePrev = i2;
            Settings.Secure.putIntForUser(contentResolver, "enhanced_comfort_font_value", i2, -2);
            final IActivityManager service = ActivityManager.getService();
            new Handler(Looper.getMainLooper())
                    .postDelayed(
                            new Runnable() { // from class:
                                             // com.samsung.android.displaysolution.EyeComfortSolutionService$$ExternalSyntheticLambda0
                                @Override // java.lang.Runnable
                                public final void run() {
                                    EyeComfortSolutionService eyeComfortSolutionService =
                                            EyeComfortSolutionService.this;
                                    IActivityManager iActivityManager = service;
                                    eyeComfortSolutionService.getClass();
                                    try {
                                        Configuration globalConfiguration =
                                                iActivityManager.getGlobalConfiguration();
                                        globalConfiguration.nightDim =
                                                eyeComfortSolutionService.mNightDimFontConfigValue;
                                        iActivityManager.updateConfiguration(globalConfiguration);
                                    } catch (Exception e) {
                                        NandswapManager$$ExternalSyntheticOutline0.m(
                                                e,
                                                new StringBuilder("cannot update Configuration : "),
                                                "EyeComfortSolutionService");
                                    }
                                }
                            },
                            0L);
        }
        if (this.mNaturalGammaScreenModeSupported) {
            return;
        }
        if (!this.mBlueLightFilterModeEnabled || !this.mNightDimModeEnabled) {
            sysfsWrite(0, "/sys/class/lcd/panel/night_dim");
            if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
                sysfsWrite(0, "/sys/class/lcd/panel1/night_dim");
            }
            Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 0 (CLUT Off setting)");
            return;
        }
        if (this.mBlueLightFilterAdaptiveModeEnabled) {
            if (i == 0) {
                sysfsWrite(0, "/sys/class/lcd/panel/night_dim");
                if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
                    sysfsWrite(0, "/sys/class/lcd/panel1/night_dim");
                }
                Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 0 (CLUT Off index)");
                return;
            }
            sysfsWrite(1, "/sys/class/lcd/panel/night_dim");
            if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
                sysfsWrite(1, "/sys/class/lcd/panel1/night_dim");
            }
            Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 1 (CLUT On)");
            return;
        }
        if (this.mBlueLightFilterScheduledTime || this.mBlueLightFilterCustomAlwaysOn) {
            sysfsWrite(1, "/sys/class/lcd/panel/night_dim");
            if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
                sysfsWrite(1, "/sys/class/lcd/panel1/night_dim");
            }
            Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 1 (CLUT On)");
            return;
        }
        sysfsWrite(0, "/sys/class/lcd/panel/night_dim");
        if (new File("/sys/class/lcd/panel1/night_dim").exists()) {
            sysfsWrite(0, "/sys/class/lcd/panel1/night_dim");
        }
        Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 0 (CLUT Off setting)");
    }

    public final void updateRubinSleepPattern(String str, long j, long j2, float f) {
        if ("SUNDAY".equals(str)) {
            if (f > 0.5f) {
                this.mBedtime_saturday = ((int) j) / 60000;
                this.mWakeupTime_sunday = ((int) j2) / 60000;
            } else {
                this.mBedtime_saturday = -1;
                this.mWakeupTime_sunday = -1;
            }
            StringBuilder sb = new StringBuilder("SUNDAY(");
            sb.append(f);
            sb.append(") , BedTime : ");
            sb.append(this.mBedtime_saturday);
            sb.append(" , WakeupTime : ");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb, this.mWakeupTime_sunday, "EyeComfortSolutionService");
            return;
        }
        if ("MONDAY".equals(str)) {
            if (f > 0.5f) {
                this.mBedtime_sunday = ((int) j) / 60000;
                this.mWakeupTime_monday = ((int) j2) / 60000;
            } else {
                this.mBedtime_sunday = -1;
                this.mWakeupTime_monday = -1;
            }
            StringBuilder sb2 = new StringBuilder("MONDAY(");
            sb2.append(f);
            sb2.append(") , BedTime : ");
            sb2.append(this.mBedtime_sunday);
            sb2.append(" , WakeupTime : ");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb2, this.mWakeupTime_monday, "EyeComfortSolutionService");
            return;
        }
        if ("TUESDAY".equals(str)) {
            if (f > 0.5f) {
                this.mBedtime_monday = ((int) j) / 60000;
                this.mWakeupTime_tuesday = ((int) j2) / 60000;
            } else {
                this.mBedtime_monday = -1;
                this.mWakeupTime_tuesday = -1;
            }
            StringBuilder sb3 = new StringBuilder("TUESDAY(");
            sb3.append(f);
            sb3.append(") , BedTime : ");
            sb3.append(this.mBedtime_monday);
            sb3.append(" , WakeupTime : ");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb3, this.mWakeupTime_tuesday, "EyeComfortSolutionService");
            return;
        }
        if ("WEDNESDAY".equals(str)) {
            if (f > 0.5f) {
                this.mBedtime_tuesday = ((int) j) / 60000;
                this.mWakeupTime_wednesday = ((int) j2) / 60000;
            } else {
                this.mBedtime_tuesday = -1;
                this.mWakeupTime_wednesday = -1;
            }
            StringBuilder sb4 = new StringBuilder("WEDNESDAY(");
            sb4.append(f);
            sb4.append(") , BedTime : ");
            sb4.append(this.mBedtime_tuesday);
            sb4.append(" , WakeupTime : ");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb4, this.mWakeupTime_wednesday, "EyeComfortSolutionService");
            return;
        }
        if ("THURSDAY".equals(str)) {
            if (f > 0.5f) {
                this.mBedtime_wednesday = ((int) j) / 60000;
                this.mWakeupTime_thursday = ((int) j2) / 60000;
            } else {
                this.mBedtime_wednesday = -1;
                this.mWakeupTime_thursday = -1;
            }
            StringBuilder sb5 = new StringBuilder("THURSDAY(");
            sb5.append(f);
            sb5.append(") , BedTime : ");
            sb5.append(this.mBedtime_wednesday);
            sb5.append(" , WakeupTime : ");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb5, this.mWakeupTime_thursday, "EyeComfortSolutionService");
            return;
        }
        if ("FRIDAY".equals(str)) {
            if (f > 0.5f) {
                this.mBedtime_thursday = ((int) j) / 60000;
                this.mWakeupTime_friday = ((int) j2) / 60000;
            } else {
                this.mBedtime_thursday = -1;
                this.mWakeupTime_friday = -1;
            }
            StringBuilder sb6 = new StringBuilder("FRIDAY(");
            sb6.append(f);
            sb6.append(") , BedTime : ");
            sb6.append(this.mBedtime_thursday);
            sb6.append(" , WakeupTime : ");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb6, this.mWakeupTime_friday, "EyeComfortSolutionService");
            return;
        }
        if ("SATURDAY".equals(str)) {
            if (f > 0.5f) {
                this.mBedtime_friday = ((int) j) / 60000;
                this.mWakeupTime_saturday = ((int) j2) / 60000;
            } else {
                this.mBedtime_friday = -1;
                this.mWakeupTime_saturday = -1;
            }
            StringBuilder sb7 = new StringBuilder("SATURDAY(");
            sb7.append(f);
            sb7.append(") , BedTime : ");
            sb7.append(this.mBedtime_friday);
            sb7.append(" , WakeupTime : ");
            GmsAlarmManager$$ExternalSyntheticOutline0.m(
                    sb7, this.mWakeupTime_saturday, "EyeComfortSolutionService");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x0087, code lost:

       if (r7.getBoolean("isEnabledInSupportedApps") != false) goto L18;
    */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x011c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateSleepWakeupTime(java.lang.String r13) {
        /*
            Method dump skipped, instructions count: 804
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.displaysolution.EyeComfortSolutionService.updateSleepWakeupTime(java.lang.String):void");
    }
}
