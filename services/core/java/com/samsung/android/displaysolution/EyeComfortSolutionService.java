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
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SemHqmManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import com.android.server.display.DisplayPowerController2;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public class EyeComfortSolutionService {
    public static final String[] ECSS_KEYS = {"BOPR_AVG_VALUE_BLF_ON", "BOPR_AVG_VALUE_BLF_OFF"};
    public int BOPR_MAX_VALUE;
    public boolean mAdaptiveBlueLightFilterSupported;
    public boolean mColorOnPixelRatioSupported;
    public final Context mContext;
    public boolean mEnvironmentAdaptiveDisplaySupported;
    public boolean mGlareReductionSupported;
    public ECSControlHandler mHandler;
    public HandlerThread mHandlerThread;
    public SemMdnieManager mMdnieManager;
    public float mPlatformBrightnessValue;
    public SemHqmManager mSemHqmManager;
    public SensorManager mSensorManager;
    public SettingsObserver mSettingsObserver;
    public boolean mUseEyeComfortSolutionServiceConfig;
    public String NIGHT_DIM_MODE_NODE = "/sys/class/lcd/panel/night_dim";
    public String NIGHT_DIM_MODE_SUB_NODE = "/sys/class/lcd/panel1/night_dim";
    public String ANTI_GLARE_MODE_NODE = "/sys/class/mdnie/mdnie/anti_glare";
    public String ANTI_GLARE_MODE_SUB_NODE = "/sys/class/mdnie/mdnie1/anti_glare";
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public final int MORNING_BLF_INTERVAL_A = 2;
    public final int MORNING_BLF_INTERVAL_B = 5;
    public final int NIGHT_BLF_INTERVAL = 10;
    public final int HIGH_DAY_BLF = 6000;
    public final int RANGE_DAY_ONLY_BLF = 2000;
    public final int RANGE_DAY_BLF = 800;
    public final int RANGE_BOPR_BLF = 1200;
    public final int MAX_TOTAL_STEP = 90;
    public final int MAX_BOPR_STEP = 54;
    public final int BED_TRANSITION_TIME = 360;
    public final int WAKEUP_TRANSITION_TIME_A = 180;
    public final int WAKEUP_TRANSITION_TIME_B = 180;
    public final int DEFAULT_BED_TIME = 0;
    public final int DEFAULT_WAKEUP_TIME = 360;
    public final int MSG_GET_SYSTEM_SERVICES = 1;
    public final int MSG_GET_BOPR_SENSOR_VALUE_ON = 2;
    public final int MSG_GET_BOPR_SENSOR_VALUE_OFF = 3;
    public final int MSG_GET_BOPR_SENSOR_VALUE_FOR_BIGDATA_ON = 4;
    public final int MSG_GET_BOPR_SENSOR_VALUE_FOR_BIGDATA_OFF = 5;
    public final int MSG_SET_BLUE_LIGHT_FILTER_DAY = 6;
    public final int MSG_NIGHT_MODE_CUSTOM_TIME_CHANGED = 7;
    public int GET_SYSTEM_SERVICES_MILLIS = 4000;
    public int ACTION_GET_BOPR_SENSOR_VALUE_DEBOUNCE_MILLIS = 10000;
    public int ACTION_GET_BOPR_SENSOR_VALUE_FOR_BIGDATA_DEBOUNCE_MILLIS = 10000;
    public int bopr_current_value = 0;
    public int bopr_cumulative_count = 0;
    public int bopr_cumulative_value_for_bigdata_blf_on = 0;
    public int bopr_cumulative_count_for_bigdata_blf_on = 0;
    public int bopr_average_value_for_bigdata_blf_on = 0;
    public int bopr_cumulative_value_for_bigdata_blf_off = 0;
    public int bopr_cumulative_count_for_bigdata_blf_off = 0;
    public int bopr_average_value_for_bigdata_blf_off = 0;
    public float app_weighting_factor = 1.0f;
    public float cal_value_sum = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
    public int prevTotalStep = -1;
    public int BOPR_VALUE_STEP = 0;
    public int mLastDayBlueLightFilterIndex = 0;
    public int mLastBoprBlueLightFilterIndex = 0;
    public int mLastTotalBlueLightFilterIndex = 0;
    public int mNightDimFontConfigValue = 0;
    public int mNightDimFontConfigValuePrev = -1;
    public int mBlfIndexEadOffset = 0;
    public int defaultBedTime = 0;
    public int defaultWakeupTime = 360;
    public int mBedtime_sunday = -1;
    public int mBedtime_monday = -1;
    public int mBedtime_tuesday = -1;
    public int mBedtime_wednesday = -1;
    public int mBedtime_thursday = -1;
    public int mBedtime_friday = -1;
    public int mBedtime_saturday = -1;
    public int mWakeupTime_sunday = -1;
    public int mWakeupTime_monday = -1;
    public int mWakeupTime_tuesday = -1;
    public int mWakeupTime_wednesday = -1;
    public int mWakeupTime_thursday = -1;
    public int mWakeupTime_friday = -1;
    public int mWakeupTime_saturday = -1;
    public Sensor mBoprSensor = null;
    public boolean mWorkingCondition = false;
    public boolean mScreenOn = false;
    public boolean mBlueLightFilterModeEnabled = false;
    public boolean mBlueLightFilterAdaptiveModeEnabled = false;
    public boolean mBlueLightFilterAntiGlareEnabled = false;
    public boolean mNightDimModeEnabled = false;
    public boolean mDefaultThemeEnabled = false;
    public boolean mCurrentStateSleep = false;
    public boolean mCurrentStateWakeup = false;
    public boolean mBlueLightFilterEnableTime = false;
    public boolean mBlueLightFilterScheduledTime = false;
    public boolean mBlueLightFilterCustomAlwaysOn = false;
    public SensorEventListener mBoprSensorListener = new SensorEventListener() { // from class: com.samsung.android.displaysolution.EyeComfortSolutionService.1
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            long uptimeMillis = SystemClock.uptimeMillis();
            EyeComfortSolutionService.this.bopr_current_value = (int) sensorEvent.values[2];
            EyeComfortSolutionService.this.bopr_cumulative_count++;
            if (EyeComfortSolutionService.this.mBlueLightFilterEnableTime) {
                EyeComfortSolutionService eyeComfortSolutionService = EyeComfortSolutionService.this;
                eyeComfortSolutionService.blue_light_filter_setting_bopr(eyeComfortSolutionService.bopr_current_value, EyeComfortSolutionService.this.bopr_cumulative_count);
            }
            EyeComfortSolutionService.this.mSensorManager.unregisterListener(EyeComfortSolutionService.this.mBoprSensorListener);
            if (EyeComfortSolutionService.this.BOPR_VALUE_STEP >= 54 || !EyeComfortSolutionService.this.mBlueLightFilterEnableTime) {
                return;
            }
            EyeComfortSolutionService.this.mHandler.removeMessages(2);
            EyeComfortSolutionService.this.mHandler.sendEmptyMessageAtTime(2, uptimeMillis + EyeComfortSolutionService.this.ACTION_GET_BOPR_SENSOR_VALUE_DEBOUNCE_MILLIS);
        }
    };
    public SensorEventListener mBoprSensorForBigDataListener = new SensorEventListener() { // from class: com.samsung.android.displaysolution.EyeComfortSolutionService.2
        @Override // android.hardware.SensorEventListener
        public void onAccuracyChanged(Sensor sensor, int i) {
        }

        @Override // android.hardware.SensorEventListener
        public void onSensorChanged(SensorEvent sensorEvent) {
            long uptimeMillis = SystemClock.uptimeMillis();
            if (EyeComfortSolutionService.this.mBlueLightFilterModeEnabled) {
                EyeComfortSolutionService eyeComfortSolutionService = EyeComfortSolutionService.this;
                eyeComfortSolutionService.bopr_cumulative_value_for_bigdata_blf_on = ((int) sensorEvent.values[2]) + eyeComfortSolutionService.bopr_cumulative_value_for_bigdata_blf_on;
                EyeComfortSolutionService.this.bopr_cumulative_count_for_bigdata_blf_on++;
                EyeComfortSolutionService eyeComfortSolutionService2 = EyeComfortSolutionService.this;
                eyeComfortSolutionService2.bopr_average_value_for_bigdata_blf_on = eyeComfortSolutionService2.bopr_cumulative_value_for_bigdata_blf_on / EyeComfortSolutionService.this.bopr_cumulative_count_for_bigdata_blf_on;
            } else if (!EyeComfortSolutionService.this.mBlueLightFilterModeEnabled) {
                EyeComfortSolutionService eyeComfortSolutionService3 = EyeComfortSolutionService.this;
                eyeComfortSolutionService3.bopr_cumulative_value_for_bigdata_blf_off = ((int) sensorEvent.values[2]) + eyeComfortSolutionService3.bopr_cumulative_value_for_bigdata_blf_off;
                EyeComfortSolutionService.this.bopr_cumulative_count_for_bigdata_blf_off++;
                EyeComfortSolutionService eyeComfortSolutionService4 = EyeComfortSolutionService.this;
                eyeComfortSolutionService4.bopr_average_value_for_bigdata_blf_off = eyeComfortSolutionService4.bopr_cumulative_value_for_bigdata_blf_off / EyeComfortSolutionService.this.bopr_cumulative_count_for_bigdata_blf_off;
            }
            EyeComfortSolutionService.this.mSensorManager.unregisterListener(EyeComfortSolutionService.this.mBoprSensorForBigDataListener);
            EyeComfortSolutionService.this.mHandler.removeMessages(4);
            EyeComfortSolutionService.this.mHandler.sendEmptyMessageAtTime(4, uptimeMillis + EyeComfortSolutionService.this.ACTION_GET_BOPR_SENSOR_VALUE_FOR_BIGDATA_DEBOUNCE_MILLIS);
        }
    };

    public EyeComfortSolutionService(Context context) {
        this.BOPR_MAX_VALUE = 0;
        this.mUseEyeComfortSolutionServiceConfig = false;
        this.mAdaptiveBlueLightFilterSupported = false;
        this.mColorOnPixelRatioSupported = false;
        this.mEnvironmentAdaptiveDisplaySupported = false;
        this.mGlareReductionSupported = false;
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("EyeComfortSolutionServiceThread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new ECSControlHandler(this.mHandlerThread.getLooper());
        this.mUseEyeComfortSolutionServiceConfig = context.getResources().getBoolean(R.bool.config_enableNetworkLocationOverlay);
        this.BOPR_MAX_VALUE = context.getResources().getInteger(R.integer.config_attentiveWarningDuration);
        this.mEnvironmentAdaptiveDisplaySupported = false;
        this.mGlareReductionSupported = false;
        Slog.i("EyeComfortSolutionService", "mEnvironmentAdaptiveDisplaySupported " + this.mEnvironmentAdaptiveDisplaySupported + " , mGlareReductionSupported : " + this.mGlareReductionSupported);
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
        ContentResolver contentResolver = context.getContentResolver();
        long uptimeMillis = SystemClock.uptimeMillis();
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_night_dim"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_adaptive_mode"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_anti_glare"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("current_sec_active_themepackage"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_type"), false, this.mSettingsObserver, -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.TIME_TICK");
        intentFilter.addAction("android.intent.action.TIME_SET");
        intentFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
        intentFilter.addAction("com.sec.android.intent.action.HQM_UPDATE_REQ");
        context.registerReceiverAsUser(new ScreenWatchingReceiver(), UserHandle.ALL, intentFilter, null, null);
        this.mHandler.removeMessages(1);
        this.mHandler.sendEmptyMessageAtTime(1, uptimeMillis + this.GET_SYSTEM_SERVICES_MILLIS);
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0) > 0) {
            this.mAdaptiveBlueLightFilterSupported = true;
        }
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0) == 1) {
            this.mColorOnPixelRatioSupported = true;
        }
        Slog.i("EyeComfortSolutionService", "A_BLF : " + this.mAdaptiveBlueLightFilterSupported + " , COPR_IP : " + this.mColorOnPixelRatioSupported);
        SystemProperties.set("sys.eyecomfortsolution.ecson", "false");
        if (this.mUseEyeComfortSolutionServiceConfig) {
            SystemProperties.set("sys.eyecomfortsolution.ecson", "true");
        }
        Slog.i("EyeComfortSolutionService", "EyeComfortSolutionService Enabled");
    }

    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            long uptimeMillis = SystemClock.uptimeMillis();
            Slog.i("EyeComfortSolutionService", "action  :  " + action);
            if ("android.intent.action.BOOT_COMPLETED".equals(action) || "android.intent.action.USER_SWITCHED".equals(action)) {
                EyeComfortSolutionService.this.setting_is_changed();
                EyeComfortSolutionService.this.bopr_for_bigdata_data_reset();
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                EyeComfortSolutionService.this.mScreenOn = true;
                if (EyeComfortSolutionService.this.mBlueLightFilterModeEnabled && EyeComfortSolutionService.this.mBlueLightFilterAdaptiveModeEnabled && EyeComfortSolutionService.this.mBlueLightFilterEnableTime) {
                    EyeComfortSolutionService.this.mHandler.removeMessages(2);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessageAtTime(2, EyeComfortSolutionService.this.ACTION_GET_BOPR_SENSOR_VALUE_DEBOUNCE_MILLIS + uptimeMillis);
                }
                EyeComfortSolutionService.this.mHandler.removeMessages(4);
                EyeComfortSolutionService.this.mHandler.sendEmptyMessageAtTime(4, uptimeMillis + EyeComfortSolutionService.this.ACTION_GET_BOPR_SENSOR_VALUE_FOR_BIGDATA_DEBOUNCE_MILLIS);
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                EyeComfortSolutionService.this.mScreenOn = false;
                if (EyeComfortSolutionService.this.mBlueLightFilterModeEnabled && EyeComfortSolutionService.this.mBlueLightFilterAdaptiveModeEnabled && EyeComfortSolutionService.this.mBlueLightFilterEnableTime) {
                    EyeComfortSolutionService.this.mHandler.removeMessages(2);
                    EyeComfortSolutionService.this.mHandler.removeMessages(3);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessage(3);
                }
                EyeComfortSolutionService.this.mHandler.removeMessages(4);
                EyeComfortSolutionService.this.mHandler.removeMessages(5);
                EyeComfortSolutionService.this.mHandler.sendEmptyMessage(5);
                return;
            }
            if ("android.intent.action.USER_PRESENT".equals(action)) {
                return;
            }
            if ("android.intent.action.TIME_TICK".equals(action)) {
                if (EyeComfortSolutionService.this.mBlueLightFilterModeEnabled && EyeComfortSolutionService.this.mBlueLightFilterAdaptiveModeEnabled) {
                    EyeComfortSolutionService.this.mHandler.removeMessages(6);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessage(6);
                    return;
                }
                return;
            }
            if ("android.intent.action.TIME_SET".equals(action)) {
                if (EyeComfortSolutionService.this.mBlueLightFilterModeEnabled && EyeComfortSolutionService.this.mBlueLightFilterAdaptiveModeEnabled) {
                    EyeComfortSolutionService.this.mHandler.removeMessages(6);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessage(6);
                    return;
                }
                return;
            }
            if ("android.intent.action.TIMEZONE_CHANGED".equals(action)) {
                if (EyeComfortSolutionService.this.mBlueLightFilterModeEnabled && EyeComfortSolutionService.this.mBlueLightFilterAdaptiveModeEnabled) {
                    EyeComfortSolutionService.this.mHandler.removeMessages(6);
                    EyeComfortSolutionService.this.mHandler.sendEmptyMessage(6);
                    return;
                }
                return;
            }
            if ("com.sec.android.intent.action.HQM_UPDATE_REQ".equals(action)) {
                EyeComfortSolutionService eyeComfortSolutionService = EyeComfortSolutionService.this;
                eyeComfortSolutionService.mSemHqmManager = (SemHqmManager) eyeComfortSolutionService.mContext.getSystemService("HqmManagerService");
                if (EyeComfortSolutionService.this.mSemHqmManager != null) {
                    EyeComfortSolutionService.this.sendBigDatatoHQM();
                }
            }
        }
    }

    public final void blue_light_filter_setting_bopr(int i, int i2) {
        this.mContext.getContentResolver();
        float f = getting_platform_brightness_value();
        float pow = (float) (Math.pow(r6 / 256.0f, 2.200000047683716d) * this.app_weighting_factor * f);
        this.cal_value_sum += pow;
        Slog.v("EyeComfortSolutionService", "count : " + i2 + " , bopr_value : " + i + ", platform_value : " + f + " , app_weighting : " + this.app_weighting_factor + " , cal_value : " + pow + " , sum_value : " + this.cal_value_sum + " , BOPR_MAX_VALUE : " + this.BOPR_MAX_VALUE);
        if (this.BOPR_VALUE_STEP < 54) {
            for (int i3 = 0; i3 < 54; i3++) {
                float f2 = this.cal_value_sum;
                int i4 = this.BOPR_MAX_VALUE / 54;
                int i5 = this.BOPR_VALUE_STEP;
                if (f2 >= i4 * (i5 + 1) && i5 == i3) {
                    this.mLastBoprBlueLightFilterIndex = i5 + 1;
                    Slog.i("EyeComfortSolutionService", "Change BLF step by B-opr, BLF dayIndex : [" + this.mLastDayBlueLightFilterIndex + "] , boprIndex : [" + this.mLastBoprBlueLightFilterIndex + "]");
                    int i6 = this.mLastDayBlueLightFilterIndex + this.mLastBoprBlueLightFilterIndex;
                    this.mLastTotalBlueLightFilterIndex = i6;
                    setBlueLightFilterMode(true, i6);
                    this.BOPR_VALUE_STEP = this.BOPR_VALUE_STEP + 1;
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:78:0x01f4, code lost:
    
        if (r2 < 36) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0223, code lost:
    
        if (r2 < 36) goto L113;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void blue_light_filter_setting_day(boolean z) {
        String str;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        long uptimeMillis = SystemClock.uptimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EE", Locale.ENGLISH);
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("mm", Locale.getDefault());
        String format = simpleDateFormat.format(new Date());
        String format2 = simpleDateFormat2.format(new Date());
        String format3 = simpleDateFormat3.format(new Date());
        int parseInt = (Integer.parseInt(format2) * 60) + Integer.parseInt(format3);
        if (z) {
            int i9 = this.defaultBedTime;
            int i10 = this.defaultWakeupTime;
            if (i9 >= i10) {
                if (parseInt > i10 && parseInt <= i9) {
                    this.mCurrentStateWakeup = true;
                    this.mCurrentStateSleep = false;
                } else {
                    this.mCurrentStateWakeup = false;
                    this.mCurrentStateSleep = true;
                }
            } else if (parseInt > i9 && parseInt <= i10) {
                this.mCurrentStateWakeup = false;
                this.mCurrentStateSleep = true;
            } else {
                this.mCurrentStateWakeup = true;
                this.mCurrentStateSleep = false;
            }
            if (this.mCurrentStateWakeup) {
                StringBuilder sb = new StringBuilder();
                sb.append("Week : ");
                sb.append(format);
                sb.append(" , Hour : ");
                sb.append(format2);
                sb.append(" , Minute : ");
                sb.append(format3);
                sb.append(" , TimeValue : ");
                sb.append(parseInt);
                sb.append(" , wakeup state, defaultBedTime : ");
                sb.append(this.defaultBedTime);
                sb.append(" , start sleep : ");
                sb.append(this.defaultBedTime - 360);
                Slog.i("EyeComfortSolutionService", sb.toString());
                int i11 = this.defaultBedTime;
                if (i11 - 360 >= 0) {
                    str = " , TimeValue : ";
                    if (i11 - 360 >= 0) {
                        if (parseInt >= i11 - 360 && parseInt < i11) {
                            this.mBlueLightFilterEnableTime = true;
                            if (parseInt == i11 - 360 && this.mScreenOn) {
                                this.mHandler.removeMessages(2);
                                this.mHandler.sendEmptyMessageAtTime(2, uptimeMillis + this.ACTION_GET_BOPR_SENSOR_VALUE_DEBOUNCE_MILLIS);
                            }
                            i7 = (parseInt - (this.defaultBedTime - 360)) / 10;
                            i8 = 1;
                            i = i7 + i8;
                            Slog.i("EyeComfortSolutionService", "currentWakeupStep : " + i);
                        } else if (parseInt == i11) {
                            this.mBlueLightFilterEnableTime = false;
                            updateSleepWakeupTime("NextWakeupTime");
                            i = 36;
                            Slog.i("EyeComfortSolutionService", "currentWakeupStep : " + i);
                        } else {
                            this.mBlueLightFilterEnableTime = false;
                        }
                    }
                    i = 0;
                    Slog.i("EyeComfortSolutionService", "currentWakeupStep : " + i);
                } else if (parseInt < (i11 + 1440) - 360 || parseInt >= 1440) {
                    str = " , TimeValue : ";
                    i8 = 1;
                    if (parseInt >= 0 && parseInt < i11) {
                        this.mBlueLightFilterEnableTime = true;
                        i7 = (360 - (i11 - parseInt)) / 10;
                        i = i7 + i8;
                        Slog.i("EyeComfortSolutionService", "currentWakeupStep : " + i);
                    } else if (parseInt == i11) {
                        this.mBlueLightFilterEnableTime = false;
                        updateSleepWakeupTime("WakeupTime");
                        i = 36;
                        Slog.i("EyeComfortSolutionService", "currentWakeupStep : " + i);
                    } else {
                        this.mBlueLightFilterEnableTime = false;
                        i = 0;
                        Slog.i("EyeComfortSolutionService", "currentWakeupStep : " + i);
                    }
                } else {
                    this.mBlueLightFilterEnableTime = true;
                    if (parseInt == (i11 + 1440) - 360 && this.mScreenOn) {
                        this.mHandler.removeMessages(2);
                        str = " , TimeValue : ";
                        this.mHandler.sendEmptyMessageAtTime(2, uptimeMillis + this.ACTION_GET_BOPR_SENSOR_VALUE_DEBOUNCE_MILLIS);
                    } else {
                        str = " , TimeValue : ";
                    }
                    i7 = (parseInt - ((this.defaultBedTime + 1440) - 360)) / 10;
                    i8 = 1;
                    i = i7 + i8;
                    Slog.i("EyeComfortSolutionService", "currentWakeupStep : " + i);
                }
            } else {
                str = " , TimeValue : ";
                i = 0;
            }
            if (this.mCurrentStateSleep) {
                boolean z2 = this.mColorOnPixelRatioSupported;
                if (z2) {
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Week : ");
                    sb2.append(format);
                    sb2.append(" , Hour : ");
                    sb2.append(format2);
                    sb2.append(" , Minute : ");
                    sb2.append(format3);
                    sb2.append(str);
                    sb2.append(parseInt);
                    sb2.append(" , sleep state, defaultWakeupTime : ");
                    sb2.append(this.defaultWakeupTime);
                    sb2.append(" , start wakeup : ");
                    sb2.append(this.defaultWakeupTime - 180);
                    Slog.i("EyeComfortSolutionService", sb2.toString());
                    int i12 = this.defaultWakeupTime;
                    if (i12 - 180 >= 0) {
                        if (i12 - 180 >= 0) {
                            if (parseInt < i12 - 180 || parseInt >= i12) {
                                if (parseInt == i12) {
                                    Slog.i("EyeComfortSolutionService", "reset blue_light_filter_data_reset");
                                    blue_light_filter_data_reset();
                                    i5 = 0;
                                    this.mBlueLightFilterEnableTime = false;
                                    updateSleepWakeupTime("BedTime");
                                } else {
                                    this.mBlueLightFilterEnableTime = false;
                                    i5 = this.mLastTotalBlueLightFilterIndex;
                                }
                                i2 = i5;
                            } else {
                                this.mBlueLightFilterEnableTime = false;
                                i6 = (parseInt - (i12 - 180)) / 2;
                                i5 = 89 - i6;
                                i2 = i5;
                            }
                        }
                        i2 = 0;
                    } else if (parseInt < (i12 + 1440) - 180 || parseInt >= 1440) {
                        if (parseInt >= 0 && parseInt < i12) {
                            this.mBlueLightFilterEnableTime = false;
                            i5 = 89 - ((180 - (i12 - parseInt)) / 2);
                        } else if (parseInt == i12) {
                            Slog.i("EyeComfortSolutionService", "reset blue_light_filter_data_reset");
                            blue_light_filter_data_reset();
                            i5 = 0;
                            this.mBlueLightFilterEnableTime = false;
                            updateSleepWakeupTime("BedTime");
                        } else {
                            this.mBlueLightFilterEnableTime = false;
                            i5 = this.mLastTotalBlueLightFilterIndex;
                        }
                        i2 = i5;
                    } else {
                        this.mBlueLightFilterEnableTime = false;
                        i6 = (parseInt - ((i12 + 1440) - 180)) / 2;
                        i5 = 89 - i6;
                        i2 = i5;
                    }
                } else {
                    String str2 = str;
                    if (!z2) {
                        StringBuilder sb3 = new StringBuilder();
                        sb3.append("Week : ");
                        sb3.append(format);
                        sb3.append(" , Hour : ");
                        sb3.append(format2);
                        sb3.append(" , Minute : ");
                        sb3.append(format3);
                        sb3.append(str2);
                        sb3.append(parseInt);
                        sb3.append(" , sleep state, defaultWakeupTime : ");
                        sb3.append(this.defaultWakeupTime);
                        sb3.append(" , start wakeup : ");
                        sb3.append(this.defaultWakeupTime - 180);
                        Slog.i("EyeComfortSolutionService", sb3.toString());
                        int i13 = this.defaultWakeupTime;
                        if (i13 - 180 >= 0) {
                            i3 = 0;
                            if (i13 - 180 >= 0) {
                                if (parseInt >= i13 - 180 && parseInt < i13) {
                                    this.mBlueLightFilterEnableTime = false;
                                    i4 = (parseInt - (i13 - 180)) / 5;
                                    i5 = 35 - i4;
                                    i2 = i5;
                                } else if (parseInt == i13) {
                                    this.mBlueLightFilterEnableTime = false;
                                    updateSleepWakeupTime("BedTime");
                                    i2 = i3;
                                } else {
                                    this.mBlueLightFilterEnableTime = false;
                                    i2 = 36;
                                }
                            }
                        } else if (parseInt < (i13 + 1440) - 180 || parseInt >= 1440) {
                            i3 = 0;
                            if (parseInt >= 0 && parseInt < i13) {
                                this.mBlueLightFilterEnableTime = false;
                                i5 = 35 - ((180 - (i13 - parseInt)) / 5);
                                i2 = i5;
                            } else if (parseInt == i13) {
                                this.mBlueLightFilterEnableTime = false;
                                updateSleepWakeupTime("BedTime");
                                i2 = i3;
                            } else {
                                this.mBlueLightFilterEnableTime = false;
                                i2 = 36;
                            }
                        } else {
                            this.mBlueLightFilterEnableTime = false;
                            i4 = (parseInt - ((i13 + 1440) - 180)) / 5;
                            i5 = 35 - i4;
                            i2 = i5;
                        }
                    }
                    i2 = 0;
                }
                Slog.i("EyeComfortSolutionService", "currentSleepStep : " + i2);
            } else {
                i2 = 0;
            }
            if (this.mCurrentStateWakeup) {
                if (this.prevTotalStep != i) {
                    this.prevTotalStep = i;
                    this.mLastDayBlueLightFilterIndex = i;
                    Slog.i("EyeComfortSolutionService", "Change BLF step by mCurrentStateWakeup, BLF dayIndex : [" + this.mLastDayBlueLightFilterIndex + "] , boprIndex : [" + this.mLastBoprBlueLightFilterIndex + "]");
                    int i14 = this.mLastDayBlueLightFilterIndex + this.mLastBoprBlueLightFilterIndex;
                    this.mLastTotalBlueLightFilterIndex = i14;
                    setBlueLightFilterMode(true, i14);
                    return;
                }
                return;
            }
            if (this.mCurrentStateSleep) {
                boolean z3 = this.mColorOnPixelRatioSupported;
                if (z3) {
                    if (this.prevTotalStep == -1) {
                        if (i2 > 36) {
                            i2 = 36;
                        }
                        this.prevTotalStep = i2;
                        this.mLastTotalBlueLightFilterIndex = i2;
                        Slog.i("EyeComfortSolutionService", "Change BLF step by mCurrentStateSleep_1, BLF totalIndex : [" + this.mLastTotalBlueLightFilterIndex + "]");
                        setBlueLightFilterMode(true, this.mLastTotalBlueLightFilterIndex);
                    }
                    if (this.prevTotalStep != i2) {
                        this.prevTotalStep = i2;
                        if (i2 < this.mLastTotalBlueLightFilterIndex) {
                            this.mLastTotalBlueLightFilterIndex = i2;
                            Slog.i("EyeComfortSolutionService", "Change BLF step by mCurrentStateSleep_2, BLF totalIndex : [" + this.mLastTotalBlueLightFilterIndex + "]");
                            setBlueLightFilterMode(true, this.mLastTotalBlueLightFilterIndex);
                            return;
                        }
                        return;
                    }
                    return;
                }
                if (z3 || this.prevTotalStep == i2) {
                    return;
                }
                this.prevTotalStep = i2;
                this.mLastDayBlueLightFilterIndex = i2;
                Slog.i("EyeComfortSolutionService", "Change BLF step by mCurrentStateWakeup, BLF dayIndex : [" + this.mLastDayBlueLightFilterIndex + "] , boprIndex : [" + this.mLastBoprBlueLightFilterIndex + "]");
                int i15 = this.mLastDayBlueLightFilterIndex + this.mLastBoprBlueLightFilterIndex;
                this.mLastTotalBlueLightFilterIndex = i15;
                setBlueLightFilterMode(true, i15);
                return;
            }
            return;
        }
        this.prevTotalStep = -1;
        this.mLastDayBlueLightFilterIndex = 0;
        this.mLastBoprBlueLightFilterIndex = 0;
        Slog.d("EyeComfortSolutionService", "set ead offset to 0");
        this.mBlfIndexEadOffset = 0;
        if (this.mBlueLightFilterModeEnabled || !this.mBlueLightFilterAdaptiveModeEnabled) {
            return;
        }
        int i16 = this.mLastDayBlueLightFilterIndex + this.mLastBoprBlueLightFilterIndex;
        this.mLastTotalBlueLightFilterIndex = i16;
        setBlueLightFilterMode(false, i16);
    }

    public final void setBlueLightFilterMode(boolean z, int i) {
        SemMdnieManager semMdnieManager;
        if (this.mAdaptiveBlueLightFilterSupported) {
            boolean z2 = this.mColorOnPixelRatioSupported;
            if (z2) {
                Slog.i("EyeComfortSolutionService", "BLF Adaptive Mode Enable : " + z + " , targetIndex : [" + i + " + 11] = " + (6000.0d - (i * 22.22222222222222d)) + " , ead offset : " + this.mBlfIndexEadOffset);
            } else if (!z2) {
                Slog.i("EyeComfortSolutionService", "BLF Adaptive Mode Enable : " + z + " , targetIndex : [" + i + " + 11] = " + (6000.0d - (i * 55.55555555555556d)) + " , ead offset : " + this.mBlfIndexEadOffset);
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
                boolean z4 = this.mColorOnPixelRatioSupported;
                int i3 = z4 ? 101 : !z4 ? 47 : 0;
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

    public final float getting_platform_brightness_value() {
        float f = Settings.System.getInt(this.mContext.getContentResolver(), "screen_brightness", 0);
        this.mPlatformBrightnessValue = f;
        return f;
    }

    public void setEyeComfortWeightingFactorValue(float f) {
        this.app_weighting_factor = f;
    }

    public final void blue_light_filter_data_reset() {
        this.BOPR_VALUE_STEP = 0;
        this.bopr_cumulative_count = 0;
        this.cal_value_sum = DisplayPowerController2.RATE_FROM_DOZE_TO_ON;
        this.mLastDayBlueLightFilterIndex = 0;
        this.mLastBoprBlueLightFilterIndex = 0;
        this.mLastTotalBlueLightFilterIndex = 0;
    }

    public final void bopr_for_bigdata_data_reset() {
        this.bopr_cumulative_value_for_bigdata_blf_on = 0;
        this.bopr_cumulative_count_for_bigdata_blf_on = 0;
        this.bopr_average_value_for_bigdata_blf_on = 0;
        this.bopr_cumulative_value_for_bigdata_blf_off = 0;
        this.bopr_cumulative_count_for_bigdata_blf_off = 0;
        this.bopr_average_value_for_bigdata_blf_off = 0;
    }

    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            EyeComfortSolutionService.this.setting_is_changed();
        }
    }

    public final void updateNightDimSettings(int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.mBlueLightFilterModeEnabled && this.mNightDimModeEnabled) {
            if (this.mBlueLightFilterAdaptiveModeEnabled) {
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
            } else if (this.mBlueLightFilterScheduledTime || this.mBlueLightFilterCustomAlwaysOn) {
                if (this.mDefaultThemeEnabled) {
                    this.mNightDimFontConfigValue = 75;
                } else {
                    this.mNightDimFontConfigValue = 32;
                }
            } else {
                this.mNightDimFontConfigValue = 0;
            }
        } else {
            this.mNightDimFontConfigValue = 0;
        }
        Slog.d("EyeComfortSolutionService", "updateNightDimSettings index : " + i + " , mNightDimFontConfigValue : " + this.mNightDimFontConfigValue);
        if (this.mNightDimFontConfigValue != this.mNightDimFontConfigValuePrev) {
            Slog.d("EyeComfortSolutionService", "updateConfiguration (" + this.mNightDimFontConfigValuePrev + ") -> (" + this.mNightDimFontConfigValue + ")");
            int i2 = this.mNightDimFontConfigValue;
            this.mNightDimFontConfigValuePrev = i2;
            Settings.Secure.putIntForUser(contentResolver, "enhanced_comfort_font_value", i2, -2);
            final IActivityManager service = ActivityManager.getService();
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() { // from class: com.samsung.android.displaysolution.EyeComfortSolutionService$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    EyeComfortSolutionService.this.lambda$updateNightDimSettings$0(service);
                }
            }, 0L);
        }
        if (this.mBlueLightFilterModeEnabled && this.mNightDimModeEnabled) {
            if (this.mBlueLightFilterAdaptiveModeEnabled) {
                if (i == 0) {
                    sysfsWrite(this.NIGHT_DIM_MODE_NODE, 0);
                    if (new File(this.NIGHT_DIM_MODE_SUB_NODE).exists()) {
                        sysfsWrite(this.NIGHT_DIM_MODE_SUB_NODE, 0);
                    }
                    Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 0 (CLUT Off index)");
                    return;
                }
                sysfsWrite(this.NIGHT_DIM_MODE_NODE, 1);
                if (new File(this.NIGHT_DIM_MODE_SUB_NODE).exists()) {
                    sysfsWrite(this.NIGHT_DIM_MODE_SUB_NODE, 1);
                }
                Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 1 (CLUT On)");
                return;
            }
            if (this.mBlueLightFilterScheduledTime || this.mBlueLightFilterCustomAlwaysOn) {
                sysfsWrite(this.NIGHT_DIM_MODE_NODE, 1);
                if (new File(this.NIGHT_DIM_MODE_SUB_NODE).exists()) {
                    sysfsWrite(this.NIGHT_DIM_MODE_SUB_NODE, 1);
                }
                Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 1 (CLUT On)");
                return;
            }
            sysfsWrite(this.NIGHT_DIM_MODE_NODE, 0);
            if (new File(this.NIGHT_DIM_MODE_SUB_NODE).exists()) {
                sysfsWrite(this.NIGHT_DIM_MODE_SUB_NODE, 0);
            }
            Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 0 (CLUT Off setting)");
            return;
        }
        sysfsWrite(this.NIGHT_DIM_MODE_NODE, 0);
        if (new File(this.NIGHT_DIM_MODE_SUB_NODE).exists()) {
            sysfsWrite(this.NIGHT_DIM_MODE_SUB_NODE, 0);
        }
        Slog.d("EyeComfortSolutionService", "NIGHT_DIM Mode : 0 (CLUT Off setting)");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$updateNightDimSettings$0(IActivityManager iActivityManager) {
        try {
            Configuration globalConfiguration = iActivityManager.getGlobalConfiguration();
            globalConfiguration.nightDim = this.mNightDimFontConfigValue;
            iActivityManager.updateConfiguration(globalConfiguration);
        } catch (Exception e) {
            Slog.e("EyeComfortSolutionService", "cannot update Configuration : " + e.getMessage());
        }
    }

    public final void updateAntiGlareSettings() {
        if (this.mBlueLightFilterModeEnabled && this.mGlareReductionSupported) {
            if (this.mBlueLightFilterAdaptiveModeEnabled) {
                sysfsWrite(this.ANTI_GLARE_MODE_NODE, 1);
                if (new File(this.ANTI_GLARE_MODE_SUB_NODE).exists()) {
                    sysfsWrite(this.ANTI_GLARE_MODE_SUB_NODE, 1);
                }
                Slog.d("EyeComfortSolutionService", "ANTI_GLARE Mode : 1");
                return;
            }
            if (this.mBlueLightFilterAntiGlareEnabled) {
                if (this.mBlueLightFilterScheduledTime || this.mBlueLightFilterCustomAlwaysOn) {
                    sysfsWrite(this.ANTI_GLARE_MODE_NODE, 1);
                    if (new File(this.ANTI_GLARE_MODE_SUB_NODE).exists()) {
                        sysfsWrite(this.ANTI_GLARE_MODE_SUB_NODE, 1);
                    }
                    Slog.d("EyeComfortSolutionService", "ANTI_GLARE Mode : 1");
                    return;
                }
                sysfsWrite(this.ANTI_GLARE_MODE_NODE, 0);
                if (new File(this.ANTI_GLARE_MODE_SUB_NODE).exists()) {
                    sysfsWrite(this.ANTI_GLARE_MODE_SUB_NODE, 0);
                }
                Slog.d("EyeComfortSolutionService", "ANTI_GLARE Mode : 0");
                return;
            }
            sysfsWrite(this.ANTI_GLARE_MODE_NODE, 0);
            if (new File(this.ANTI_GLARE_MODE_SUB_NODE).exists()) {
                sysfsWrite(this.ANTI_GLARE_MODE_SUB_NODE, 0);
            }
            Slog.d("EyeComfortSolutionService", "ANTI_GLARE Mode : 0");
            return;
        }
        sysfsWrite(this.ANTI_GLARE_MODE_NODE, 0);
        if (new File(this.ANTI_GLARE_MODE_SUB_NODE).exists()) {
            sysfsWrite(this.ANTI_GLARE_MODE_SUB_NODE, 0);
        }
        Slog.d("EyeComfortSolutionService", "ANTI_GLARE Mode : 0");
    }

    public final void setting_is_changed() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        long uptimeMillis = SystemClock.uptimeMillis();
        this.mBlueLightFilterModeEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter", 0, -2) == 1;
        this.mNightDimModeEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter_night_dim", 0, -2) == 1;
        this.mBlueLightFilterAdaptiveModeEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter_adaptive_mode", 0, -2) == 1;
        this.mBlueLightFilterAntiGlareEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter_anti_glare", 0, -2) == 1;
        this.mDefaultThemeEnabled = Settings.System.getStringForUser(contentResolver, "current_sec_active_themepackage", -2) == null;
        this.mBlueLightFilterCustomAlwaysOn = Settings.System.getIntForUser(contentResolver, "blue_light_filter_type", 0, -2) == 0;
        Slog.d("EyeComfortSolutionService", "mBlueLightFilterModeEnabled : " + this.mBlueLightFilterModeEnabled + " , mNightDimModeEnabled : " + this.mNightDimModeEnabled + " , mBlueLightFilterAdaptiveModeEnabled : " + this.mBlueLightFilterAdaptiveModeEnabled + " , mDefaultThemeEnabled : " + this.mDefaultThemeEnabled + " , mBlueLightFilterCustomAlwaysOn : " + this.mBlueLightFilterCustomAlwaysOn);
        if (this.mBlueLightFilterModeEnabled && this.mBlueLightFilterAdaptiveModeEnabled) {
            updateSleepWakeupTime("All");
            blue_light_filter_setting_day(true);
            if (this.mScreenOn && this.mBlueLightFilterEnableTime) {
                this.mHandler.removeMessages(2);
                this.mHandler.sendEmptyMessageAtTime(2, uptimeMillis + this.ACTION_GET_BOPR_SENSOR_VALUE_DEBOUNCE_MILLIS);
            }
        } else {
            blue_light_filter_setting_day(false);
            this.mHandler.removeMessages(2);
            this.mHandler.removeMessages(3);
            this.mHandler.sendEmptyMessage(3);
            Slog.i("EyeComfortSolutionService", "reset blue_light_filter_data_reset");
            blue_light_filter_data_reset();
        }
        updateNightDimSettings(this.mLastTotalBlueLightFilterIndex);
        updateAntiGlareSettings();
    }

    public final class ECSControlHandler extends Handler {
        public ECSControlHandler(Looper looper) {
            super(looper, null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean z = false;
            switch (message.what) {
                case 1:
                    EyeComfortSolutionService.this.getSystemServices();
                    break;
                case 2:
                    EyeComfortSolutionService.this.getBoprSensorValue(true);
                    break;
                case 3:
                    EyeComfortSolutionService.this.getBoprSensorValue(false);
                    break;
                case 4:
                    EyeComfortSolutionService.this.getBoprSensorValueForBigData(true);
                    break;
                case 5:
                    EyeComfortSolutionService.this.getBoprSensorValueForBigData(false);
                    break;
                case 6:
                    EyeComfortSolutionService eyeComfortSolutionService = EyeComfortSolutionService.this;
                    if (eyeComfortSolutionService.mBlueLightFilterModeEnabled && EyeComfortSolutionService.this.mBlueLightFilterAdaptiveModeEnabled) {
                        z = true;
                    }
                    eyeComfortSolutionService.blue_light_filter_setting_day(z);
                    break;
                case 7:
                    EyeComfortSolutionService.this.updateNightDimSettings(-1);
                    EyeComfortSolutionService.this.updateAntiGlareSettings();
                    break;
            }
        }
    }

    public final void getBoprSensorValue(boolean z) {
        if (z) {
            SensorManager sensorManager = this.mSensorManager;
            if (sensorManager == null || !this.mColorOnPixelRatioSupported) {
                return;
            }
            sensorManager.registerListener(this.mBoprSensorListener, this.mBoprSensor, 3, this.mHandler);
            return;
        }
        SensorManager sensorManager2 = this.mSensorManager;
        if (sensorManager2 == null || !this.mColorOnPixelRatioSupported) {
            return;
        }
        sensorManager2.unregisterListener(this.mBoprSensorListener);
    }

    public final void getBoprSensorValueForBigData(boolean z) {
        if (z) {
            SensorManager sensorManager = this.mSensorManager;
            if (sensorManager == null || !this.mColorOnPixelRatioSupported) {
                return;
            }
            sensorManager.registerListener(this.mBoprSensorForBigDataListener, this.mBoprSensor, 3, this.mHandler);
            return;
        }
        SensorManager sensorManager2 = this.mSensorManager;
        if (sensorManager2 == null || !this.mColorOnPixelRatioSupported) {
            return;
        }
        sensorManager2.unregisterListener(this.mBoprSensorForBigDataListener);
    }

    public final void getSystemServices() {
        this.mSensorManager = (SensorManager) this.mContext.getSystemService("sensor");
        Slog.v("EyeComfortSolutionService", "mSensorManager : " + this.mSensorManager);
        SensorManager sensorManager = this.mSensorManager;
        if (sensorManager != null) {
            this.mBoprSensor = sensorManager.getDefaultSensor(65587);
        }
        if (this.mSensorManager == null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageAtTime(1, uptimeMillis + this.GET_SYSTEM_SERVICES_MILLIS);
            Slog.v("EyeComfortSolutionService", "Failure to register all of the services system.");
            return;
        }
        this.mWorkingCondition = true;
        Slog.v("EyeComfortSolutionService", "Success to register all of the services system.");
    }

    public final void sendBigDatatoHQM() {
        if (this.bopr_cumulative_count_for_bigdata_blf_on <= 60) {
            this.bopr_average_value_for_bigdata_blf_on = 0;
        }
        if (this.bopr_cumulative_count_for_bigdata_blf_off <= 60) {
            this.bopr_average_value_for_bigdata_blf_off = 0;
        }
        String[] strArr = {Integer.toString(this.bopr_average_value_for_bigdata_blf_on), Integer.toString(this.bopr_average_value_for_bigdata_blf_off)};
        SemHqmManager semHqmManager = this.mSemHqmManager;
        if (semHqmManager != null) {
            if (this.bopr_average_value_for_bigdata_blf_on != 0 || this.bopr_average_value_for_bigdata_blf_off != 0) {
                String[] strArr2 = ECSS_KEYS;
                semHqmManager.sendHWParamToHQM(0, "Display", "ECSS", "sm", "0.0", "sec", "", JsonParseData(strArr2, strArr, strArr2.length), "");
                Slog.v("EyeComfortSolutionService", "BigDatatoHQM sendData : " + JsonParseData(strArr2, strArr, strArr2.length));
            }
            bopr_for_bigdata_data_reset();
        }
    }

    public final String JsonParseData(String[] strArr, String[] strArr2, int i) {
        JSONObject jSONObject;
        JSONException e;
        try {
            jSONObject = new JSONObject();
            for (int i2 = 0; i2 < i; i2++) {
                try {
                    jSONObject.put(strArr[i2], strArr2[i2]);
                } catch (JSONException e2) {
                    e = e2;
                    e.printStackTrace();
                    return jSONObject.toString().replaceAll("\\{", "").replaceAll("\\}", "");
                }
            }
        } catch (JSONException e3) {
            jSONObject = null;
            e = e3;
        }
        return jSONObject.toString().replaceAll("\\{", "").replaceAll("\\}", "");
    }

    public void updateRubinSleepPattern(String str, long j, long j2, float f) {
        if ("SUNDAY".equals(str)) {
            if (f > 0.5f) {
                this.mBedtime_saturday = ((int) j) / 60000;
                this.mWakeupTime_sunday = ((int) j2) / 60000;
            } else {
                this.mBedtime_saturday = -1;
                this.mWakeupTime_sunday = -1;
            }
            Slog.v("EyeComfortSolutionService", "SUNDAY(" + f + ") , BedTime : " + this.mBedtime_saturday + " , WakeupTime : " + this.mWakeupTime_sunday);
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
            Slog.v("EyeComfortSolutionService", "MONDAY(" + f + ") , BedTime : " + this.mBedtime_sunday + " , WakeupTime : " + this.mWakeupTime_monday);
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
            Slog.v("EyeComfortSolutionService", "TUESDAY(" + f + ") , BedTime : " + this.mBedtime_monday + " , WakeupTime : " + this.mWakeupTime_tuesday);
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
            Slog.v("EyeComfortSolutionService", "WEDNESDAY(" + f + ") , BedTime : " + this.mBedtime_tuesday + " , WakeupTime : " + this.mWakeupTime_wednesday);
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
            Slog.v("EyeComfortSolutionService", "THURSDAY(" + f + ") , BedTime : " + this.mBedtime_wednesday + " , WakeupTime : " + this.mWakeupTime_thursday);
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
            Slog.v("EyeComfortSolutionService", "FRIDAY(" + f + ") , BedTime : " + this.mBedtime_thursday + " , WakeupTime : " + this.mWakeupTime_friday);
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
            Slog.v("EyeComfortSolutionService", "SATURDAY(" + f + ") , BedTime : " + this.mBedtime_friday + " , WakeupTime : " + this.mWakeupTime_saturday);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:146:0x0085, code lost:
    
        if (r4.getBoolean("isEnabledInSupportedApps") != false) goto L18;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x00e7  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0125  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateSleepWakeupTime(String str) {
        boolean z;
        Locale locale;
        Bundle call;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH", Locale.getDefault());
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm", Locale.getDefault());
        String format = simpleDateFormat.format(new Date());
        String format2 = simpleDateFormat2.format(new Date());
        Integer.parseInt(format);
        Integer.parseInt(format2);
        try {
            call = this.mContext.getContentResolver().call(Uri.parse("content://com.samsung.android.rubin.state"), "getRubinState", "com.samsung.android.bluelightfilter", (Bundle) null);
        } catch (Exception unused) {
        }
        if (call != null) {
            z = true;
            try {
                if (call.containsKey("isDeviceRubinSupported") && call.getBoolean("isDeviceRubinSupported")) {
                    Slog.v("EyeComfortSolutionService", "D-Runestone isDeviceRubinSupported : " + call.getBoolean("isDeviceRubinSupported"));
                } else {
                    if ("OK".equalsIgnoreCase(call.getString("currentRubinState"))) {
                    }
                    z = false;
                    Slog.v("EyeComfortSolutionService", "O-Runestone currentRubinState : " + call.getString("currentRubinState") + " , isEnabledInSupportedApps : " + call.getBoolean("isEnabledInSupportedApps"));
                }
                Slog.v("EyeComfortSolutionService", "mRubinDataValid : " + z);
            } catch (Exception unused2) {
            }
            locale = Locale.ENGLISH;
            if (!new SimpleDateFormat("EE", locale).format(new Date()).equals("Sun")) {
                if (this.mBedtime_sunday != -1 && this.mWakeupTime_sunday != -1 && z) {
                    if ("BedTime".equals(str)) {
                        this.defaultBedTime = this.mBedtime_sunday;
                    } else if ("WakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_sunday;
                    } else if ("NextWakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_monday;
                    } else {
                        this.defaultBedTime = this.mBedtime_sunday;
                        this.defaultWakeupTime = this.mWakeupTime_sunday;
                    }
                } else {
                    this.defaultBedTime = 0;
                    this.defaultWakeupTime = 360;
                }
            } else if (new SimpleDateFormat("EE", locale).format(new Date()).equals("Mon")) {
                if (this.mBedtime_monday != -1 && this.mWakeupTime_monday != -1 && z) {
                    if ("BedTime".equals(str)) {
                        this.defaultBedTime = this.mBedtime_monday;
                    } else if ("WakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_monday;
                    } else if ("NextWakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_tuesday;
                    } else {
                        this.defaultBedTime = this.mBedtime_monday;
                        this.defaultWakeupTime = this.mWakeupTime_monday;
                    }
                } else {
                    this.defaultBedTime = 0;
                    this.defaultWakeupTime = 360;
                }
            } else if (new SimpleDateFormat("EE", locale).format(new Date()).equals("Tue")) {
                if (this.mBedtime_tuesday != -1 && this.mWakeupTime_tuesday != -1 && z) {
                    if ("BedTime".equals(str)) {
                        this.defaultBedTime = this.mBedtime_tuesday;
                    } else if ("WakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_tuesday;
                    } else if ("NextWakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_wednesday;
                    } else {
                        this.defaultBedTime = this.mBedtime_tuesday;
                        this.defaultWakeupTime = this.mWakeupTime_tuesday;
                    }
                } else {
                    this.defaultBedTime = 0;
                    this.defaultWakeupTime = 360;
                }
            } else if (new SimpleDateFormat("EE", locale).format(new Date()).equals("Wed")) {
                if (this.mBedtime_wednesday != -1 && this.mWakeupTime_wednesday != -1 && z) {
                    if ("BedTime".equals(str)) {
                        this.defaultBedTime = this.mBedtime_wednesday;
                    } else if ("WakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_wednesday;
                    } else if ("NextWakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_thursday;
                    } else {
                        this.defaultBedTime = this.mBedtime_wednesday;
                        this.defaultWakeupTime = this.mWakeupTime_wednesday;
                    }
                } else {
                    this.defaultBedTime = 0;
                    this.defaultWakeupTime = 360;
                }
            } else if (new SimpleDateFormat("EE", locale).format(new Date()).equals("Thu")) {
                if (this.mBedtime_thursday != -1 && this.mWakeupTime_thursday != -1 && z) {
                    if ("BedTime".equals(str)) {
                        this.defaultBedTime = this.mBedtime_thursday;
                    } else if ("WakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_thursday;
                    } else if ("NextWakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_friday;
                    } else {
                        this.defaultBedTime = this.mBedtime_thursday;
                        this.defaultWakeupTime = this.mWakeupTime_thursday;
                    }
                } else {
                    this.defaultBedTime = 0;
                    this.defaultWakeupTime = 360;
                }
            } else if (new SimpleDateFormat("EE", locale).format(new Date()).equals("Fri")) {
                if (this.mBedtime_friday != -1 && this.mWakeupTime_friday != -1 && z) {
                    if ("BedTime".equals(str)) {
                        this.defaultBedTime = this.mBedtime_friday;
                    } else if ("WakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_friday;
                    } else if ("NextWakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_saturday;
                    } else {
                        this.defaultBedTime = this.mBedtime_friday;
                        this.defaultWakeupTime = this.mWakeupTime_friday;
                    }
                } else {
                    this.defaultBedTime = 0;
                    this.defaultWakeupTime = 360;
                }
            } else if (new SimpleDateFormat("EE", locale).format(new Date()).equals("Sat")) {
                if (this.mBedtime_saturday != -1 && this.mWakeupTime_saturday != -1 && z) {
                    if ("BedTime".equals(str)) {
                        this.defaultBedTime = this.mBedtime_saturday;
                    } else if ("WakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_saturday;
                    } else if ("NextWakeupTime".equals(str)) {
                        this.defaultWakeupTime = this.mWakeupTime_sunday;
                    } else {
                        this.defaultBedTime = this.mBedtime_saturday;
                        this.defaultWakeupTime = this.mWakeupTime_saturday;
                    }
                } else {
                    this.defaultBedTime = 0;
                    this.defaultWakeupTime = 360;
                }
            }
            Slog.v("EyeComfortSolutionService", "updateSleepWakeupTime. type : " + str + " , defaultBedTime : " + this.defaultBedTime + " , defaultWakeupTime : " + this.defaultWakeupTime);
        }
        z = false;
        locale = Locale.ENGLISH;
        if (!new SimpleDateFormat("EE", locale).format(new Date()).equals("Sun")) {
        }
        Slog.v("EyeComfortSolutionService", "updateSleepWakeupTime. type : " + str + " , defaultBedTime : " + this.defaultBedTime + " , defaultWakeupTime : " + this.defaultWakeupTime);
    }

    public void setBlfEnableTimeBySchedule(boolean z, int i) {
        this.mBlueLightFilterScheduledTime = z;
        Slog.d("EyeComfortSolutionService", "setBlueLightFilterModeEnabledTime - mBlueLightFilterScheduledTime: " + this.mBlueLightFilterScheduledTime + " , index: " + i);
        ECSControlHandler eCSControlHandler = this.mHandler;
        eCSControlHandler.sendMessage(Message.obtain(eCSControlHandler, 7));
    }

    public boolean isBlueLightFilterScheduledTime() {
        Slog.d("EyeComfortSolutionService", "mBlueLightFilterAdaptiveModeEnabled: " + this.mBlueLightFilterAdaptiveModeEnabled + " , mBlueLightFilterScheduledTime: " + this.mBlueLightFilterScheduledTime);
        return !this.mBlueLightFilterAdaptiveModeEnabled && this.mBlueLightFilterScheduledTime;
    }

    public void setEadIndexOffset(int i) {
        Slog.d("EyeComfortSolutionService", "setEadIndexOffset(" + i + ")");
        if (i < 0) {
            i = 0;
        }
        this.mBlfIndexEadOffset = i;
        if (this.mBlueLightFilterModeEnabled && this.mBlueLightFilterAdaptiveModeEnabled) {
            setBlueLightFilterMode(true, this.mLastTotalBlueLightFilterIndex);
        }
    }

    public int getBlfAdaptiveCurrentIndex() {
        return this.mLastTotalBlueLightFilterIndex;
    }

    public static boolean sysfsWrite(String str, int i) {
        FileOutputStream fileOutputStream;
        IOException e;
        File file = new File(str);
        if (file.exists()) {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                    return false;
                }
            } catch (IOException e3) {
                fileOutputStream = null;
                e = e3;
            }
            try {
                fileOutputStream.write(Integer.toString(i).getBytes());
                fileOutputStream.close();
                return true;
            } catch (IOException e4) {
                e = e4;
                e.printStackTrace();
                try {
                    fileOutputStream.close();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }
}
