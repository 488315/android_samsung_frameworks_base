package com.samsung.android.hardware.display;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.FactoryTest;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;
import android.view.Display;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.ServiceKeeper$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList$$ExternalSyntheticOutline0;
import com.samsung.android.displayaiqe.DisplayAiqeManager;
import com.samsung.android.displaysolution.SemDisplaySolutionManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.ISemMdnieManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class SemMdnieManagerService extends ISemMdnieManager.Stub {
    public final String FP_FEATURE_SENSOR_IS_OPTICAL;
    public final int MDNIE_SUPPORT_FUNCTION;
    public final int NIGHT_MODE_MAX_INDEX;
    public final AfterimageCompensationService afterimageCompensation;
    public final boolean mAdaptiveBlueLightFilterSupported;
    public boolean mAlwaysOnDisplayEnabled;
    public int mBlueFilterIndex;
    public boolean mBlueLightFilterEnabled;
    public int mContentMode;
    public final Context mContext;
    public boolean mCurtainModeIsRunning;
    public final DisplayAiqeManager mDisplayAiqeManager;
    public final AnonymousClass1 mDisplayListener;
    public final DisplayManager mDisplayManager;
    public boolean mDisplayOn;
    public int mDisplayState;
    public int mDisplayStatePrev;
    public boolean mEnvironmentAdaptiveDisplayEnabled;
    public final boolean mEnvironmentAdaptiveDisplaySupported;
    public final Object mLock;
    public final boolean mMdnieWhiteRGBSupported;
    public final boolean mNaturalGammaScreenModeSupported;
    public boolean mNightDimModeEnabled;
    public final boolean mNightDimSupported;
    public boolean mNightMode;
    public boolean mNightModeBlock;
    public int mNightModeIndex;
    public final int[][][] mPresetAdjustWhiteRGB;
    public int mScreenMode;
    public int mScreenModeSetting;
    public final boolean mSupportAPmDNIe;
    public final boolean mSupportBlueFilter;
    public final boolean mSupportContentMode;
    public final boolean mSupportContentModeGame;
    public final boolean mSupportContentModeVideoEnhance;
    public final boolean mSupportLightNotificationMode;
    public final boolean mSupportScreeenReadingMode;
    public final boolean mSupportScreenMode;
    public final boolean mUseAfterimageCompensationServiceConfig;
    public int mVividnessIndex;
    public boolean mWorkBlueFilter;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!"android.intent.action.BOOT_COMPLETED".equals(action)) {
                if ("android.intent.action.SCREEN_ON".equals(action)) {
                    SemMdnieManagerService.this.mDisplayOn = true;
                    return;
                } else {
                    if ("android.intent.action.SCREEN_OFF".equals(action)) {
                        SemMdnieManagerService.this.mDisplayOn = false;
                        return;
                    }
                    return;
                }
            }
            DeviceIdleController$$ExternalSyntheticOutline0.m(new StringBuilder("BOOT_ON - mDNIe ScreenMode : "), SemMdnieManagerService.this.mScreenMode, "SemMdnieManagerService");
            SemMdnieManagerService semMdnieManagerService = SemMdnieManagerService.this;
            semMdnieManagerService.mScreenModeSetting = Settings.System.getIntForUser(semMdnieManagerService.mContext.getContentResolver(), "screen_mode_setting", 4, -2);
            SemMdnieManagerService semMdnieManagerService2 = SemMdnieManagerService.this;
            if (semMdnieManagerService2.mScreenModeSetting != 3) {
                semMdnieManagerService2.mScreenMode = 4;
            } else if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_WIDE_COLOR_GAMUT", false)) {
                SemMdnieManagerService.this.mScreenMode = 0;
            } else {
                SemMdnieManagerService.this.mScreenMode = 2;
            }
            SemMdnieManagerService semMdnieManagerService3 = SemMdnieManagerService.this;
            if ((semMdnieManagerService3.mSupportScreenMode || semMdnieManagerService3.mSupportScreeenReadingMode) && semMdnieManagerService3.checkScreenMode(semMdnieManagerService3.mScreenMode)) {
                synchronized (SemMdnieManagerService.this.mLock) {
                    try {
                        SemMdnieManagerService semMdnieManagerService4 = SemMdnieManagerService.this;
                        if (semMdnieManagerService4.mSupportAPmDNIe) {
                            Slog.i("SemMdnieManagerService", "AP setScreenMode : " + SemMdnieManagerService.this.mScreenMode);
                            SemMdnieManagerService semMdnieManagerService5 = SemMdnieManagerService.this;
                            semMdnieManagerService5.mDisplayAiqeManager.setScreenMode(semMdnieManagerService5.mScreenMode);
                            if (new File("/sys/class/sensors/light_sensor/screen_mode").exists()) {
                                SemMdnieManagerService.sysfsWrite(SemMdnieManagerService.this.mScreenMode, "/sys/class/sensors/light_sensor/screen_mode");
                            }
                        } else {
                            SemMdnieManagerService.sysfsWrite(semMdnieManagerService4.mScreenMode, "/sys/class/mdnie/mdnie/mode");
                            if (new File("/sys/class/mdnie/mdnie1/mode").exists()) {
                                SemMdnieManagerService.sysfsWrite(SemMdnieManagerService.this.mScreenMode, "/sys/class/mdnie/mdnie1/mode");
                            }
                            Slog.i("SemMdnieManagerService", "DDI setScreenMode : " + SemMdnieManagerService.this.mScreenMode);
                        }
                    } finally {
                    }
                }
            }
            if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDNESS", 0) > 0) {
                if (SemMdnieManagerService.this.mSupportAPmDNIe) {
                    SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("AP setVividnessMode : "), SemMdnieManagerService.this.mVividnessIndex, "SemMdnieManagerService");
                    SemMdnieManagerService semMdnieManagerService6 = SemMdnieManagerService.this;
                    semMdnieManagerService6.mDisplayAiqeManager.setVividnessMode(semMdnieManagerService6.mVividnessIndex);
                } else {
                    if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie/vividness")) {
                        SemMdnieManagerService.sysfsWrite(SemMdnieManagerService.this.mVividnessIndex, "/sys/class/mdnie/mdnie/vividness");
                    }
                    if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/vividness")) {
                        SemMdnieManagerService.sysfsWrite(SemMdnieManagerService.this.mVividnessIndex, "/sys/class/mdnie/mdnie1/vividness");
                    }
                    SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("DDI setVividnessMode : "), SemMdnieManagerService.this.mVividnessIndex, "SemMdnieManagerService");
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri AOD_SHOW_STATE_URI;
        public final Uri EAD_ENABLED_URI;
        public final Uri NIGHT_DIM_URI;
        public final Uri VIVIDNESS_STATE_URI;
        public final ContentResolver resolver;

        public SettingsObserver() {
            super(new Handler());
            this.resolver = SemMdnieManagerService.this.mContext.getContentResolver();
            this.NIGHT_DIM_URI = Settings.System.getUriFor("blue_light_filter_night_dim");
            this.EAD_ENABLED_URI = Settings.System.getUriFor("ead_enabled");
            this.AOD_SHOW_STATE_URI = Settings.System.getUriFor("aod_show_state");
            this.VIVIDNESS_STATE_URI = Settings.System.getUriFor("vividness_intensity");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (SemMdnieManagerService.this.FP_FEATURE_SENSOR_IS_OPTICAL.contains("optical")) {
                if (this.VIVIDNESS_STATE_URI.equals(uri)) {
                    Slog.i("SemMdnieManagerService", "VIVIDNESS_STATE_URI onChange");
                    if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDNESS", 0) > 0) {
                        SemMdnieManagerService.this.setting_is_changed();
                        if (SemMdnieManagerService.this.mSupportAPmDNIe) {
                            SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("AP setVividnessMode : "), SemMdnieManagerService.this.mVividnessIndex, "SemMdnieManagerService");
                            SemMdnieManagerService semMdnieManagerService = SemMdnieManagerService.this;
                            semMdnieManagerService.mDisplayAiqeManager.setVividnessMode(semMdnieManagerService.mVividnessIndex);
                        } else {
                            if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie/vividness")) {
                                SemMdnieManagerService.sysfsWrite(SemMdnieManagerService.this.mVividnessIndex, "/sys/class/mdnie/mdnie/vividness");
                            }
                            if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/vividness")) {
                                SemMdnieManagerService.sysfsWrite(SemMdnieManagerService.this.mVividnessIndex, "/sys/class/mdnie/mdnie1/vividness");
                            }
                            SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("DDI setVividnessMode : "), SemMdnieManagerService.this.mVividnessIndex, "SemMdnieManagerService");
                        }
                    }
                }
                SemMdnieManagerService.this.setting_is_changed();
                return;
            }
            SemMdnieManagerService.this.mAlwaysOnDisplayEnabled = Settings.System.getIntForUser(this.resolver, "aod_show_state", 0, -2) == 1;
            if (this.AOD_SHOW_STATE_URI.equals(uri)) {
                StringBuilder sb = new StringBuilder("AOD_SHOW_STATE_SETTINGS onChange. mAlwaysOnDisplayEnabled : ");
                sb.append(SemMdnieManagerService.this.mAlwaysOnDisplayEnabled);
                sb.append(" , mDisplayOn : ");
                sb.append(SemMdnieManagerService.this.mDisplayOn);
                sb.append(" , mDisplayState : ");
                SystemServiceManager$$ExternalSyntheticOutline0.m(sb, SemMdnieManagerService.this.mDisplayState, "SemMdnieManagerService");
                SemMdnieManagerService semMdnieManagerService2 = SemMdnieManagerService.this;
                if (semMdnieManagerService2.mAlwaysOnDisplayEnabled) {
                    semMdnieManagerService2.setNightDimOffMode(semMdnieManagerService2.mNightModeIndex, semMdnieManagerService2.mNightMode);
                    return;
                }
                if (semMdnieManagerService2.mDisplayOn) {
                    semMdnieManagerService2.setNightMode(semMdnieManagerService2.mNightMode, semMdnieManagerService2.mNightModeIndex);
                    return;
                }
                int i = semMdnieManagerService2.mDisplayState;
                if (i == 1) {
                    semMdnieManagerService2.setNightDimOffMode(semMdnieManagerService2.mNightModeIndex, semMdnieManagerService2.mNightMode);
                    return;
                } else {
                    if (i == 2) {
                        semMdnieManagerService2.setNightMode(semMdnieManagerService2.mNightMode, semMdnieManagerService2.mNightModeIndex);
                        return;
                    }
                    return;
                }
            }
            if (this.NIGHT_DIM_URI.equals(uri)) {
                Slog.i("SemMdnieManagerService", "BLUE_LIGHT_FILTER_NIGHT_DIM onChange");
                SemMdnieManagerService semMdnieManagerService3 = SemMdnieManagerService.this;
                semMdnieManagerService3.setNightMode(semMdnieManagerService3.mNightMode, semMdnieManagerService3.mNightModeIndex);
                return;
            }
            if (!this.EAD_ENABLED_URI.equals(uri)) {
                if (!this.VIVIDNESS_STATE_URI.equals(uri)) {
                    SemMdnieManagerService.this.setting_is_changed();
                    return;
                }
                Slog.i("SemMdnieManagerService", "VIVIDNESS_STATE_URI onChange");
                if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDNESS", 0) > 0) {
                    SemMdnieManagerService.this.setting_is_changed();
                    if (SemMdnieManagerService.this.mSupportAPmDNIe) {
                        SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("AP setVividnessMode : "), SemMdnieManagerService.this.mVividnessIndex, "SemMdnieManagerService");
                        SemMdnieManagerService semMdnieManagerService4 = SemMdnieManagerService.this;
                        semMdnieManagerService4.mDisplayAiqeManager.setVividnessMode(semMdnieManagerService4.mVividnessIndex);
                        return;
                    } else {
                        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie/vividness")) {
                            SemMdnieManagerService.sysfsWrite(SemMdnieManagerService.this.mVividnessIndex, "/sys/class/mdnie/mdnie/vividness");
                        }
                        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/vividness")) {
                            SemMdnieManagerService.sysfsWrite(SemMdnieManagerService.this.mVividnessIndex, "/sys/class/mdnie/mdnie1/vividness");
                        }
                        SystemServiceManager$$ExternalSyntheticOutline0.m(new StringBuilder("DDI setVividnessMode : "), SemMdnieManagerService.this.mVividnessIndex, "SemMdnieManagerService");
                        return;
                    }
                }
                return;
            }
            Slog.i("SemMdnieManagerService", "EAD_ENABLED_URI onChange");
            SemMdnieManagerService semMdnieManagerService5 = SemMdnieManagerService.this;
            if (semMdnieManagerService5.mEnvironmentAdaptiveDisplaySupported) {
                if (semMdnieManagerService5.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setEnvironmentAdaptiveDisplayMode : " + Settings.System.getIntForUser(SemMdnieManagerService.this.mContext.getContentResolver(), "ead_enabled", 0, -2));
                    SemMdnieManagerService semMdnieManagerService6 = SemMdnieManagerService.this;
                    semMdnieManagerService6.mDisplayAiqeManager.setEnvironmentAdaptiveDisplayMode(Settings.System.getIntForUser(semMdnieManagerService6.mContext.getContentResolver(), "ead_enabled", 0, -2));
                } else {
                    if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie/adaptive_mode")) {
                        SemMdnieManagerService.sysfsWrite(Settings.System.getIntForUser(SemMdnieManagerService.this.mContext.getContentResolver(), "ead_enabled", 0, -2), "/sys/class/mdnie/mdnie/adaptive_mode");
                    }
                    if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/adaptive_mode")) {
                        SemMdnieManagerService.sysfsWrite(Settings.System.getIntForUser(SemMdnieManagerService.this.mContext.getContentResolver(), "ead_enabled", 0, -2), "/sys/class/mdnie/mdnie1/adaptive_mode");
                    }
                    Slog.i("SemMdnieManagerService", "DDI setEnvironmentAdaptiveDisplayMode : " + Settings.System.getIntForUser(SemMdnieManagerService.this.mContext.getContentResolver(), "ead_enabled", 0, -2));
                }
                SemMdnieManagerService.this.setting_is_changed();
            }
        }
    }

    public SemMdnieManagerService(Context context) {
        boolean z;
        "eng".equals(Build.TYPE);
        this.mScreenMode = 4;
        this.mScreenModeSetting = 4;
        this.mContentMode = 0;
        this.afterimageCompensation = null;
        this.mLock = new Object();
        int intValue = Integer.valueOf("65303").intValue();
        this.MDNIE_SUPPORT_FUNCTION = intValue;
        this.FP_FEATURE_SENSOR_IS_OPTICAL = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_BIOAUTH_CONFIG_FINGERPRINT_FEATURES");
        this.mSupportAPmDNIe = false;
        this.mSupportContentMode = false;
        this.mSupportContentModeGame = false;
        this.mSupportContentModeVideoEnhance = false;
        this.mSupportScreenMode = false;
        this.mSupportScreeenReadingMode = false;
        this.mSupportBlueFilter = false;
        this.mSupportLightNotificationMode = false;
        this.mWorkBlueFilter = true;
        this.mNightModeBlock = true;
        this.mCurtainModeIsRunning = false;
        this.mNightMode = false;
        this.mAdaptiveBlueLightFilterSupported = false;
        this.mNaturalGammaScreenModeSupported = false;
        this.mNightDimSupported = false;
        this.mBlueLightFilterEnabled = false;
        this.mNightDimModeEnabled = false;
        this.mAlwaysOnDisplayEnabled = false;
        this.mDisplayOn = false;
        this.mMdnieWhiteRGBSupported = false;
        this.mEnvironmentAdaptiveDisplayEnabled = false;
        this.mEnvironmentAdaptiveDisplaySupported = false;
        this.mUseAfterimageCompensationServiceConfig = false;
        this.mBlueFilterIndex = 0;
        this.mDisplayStatePrev = -1;
        this.mDisplayState = 0;
        this.mNightModeIndex = 0;
        this.mVividnessIndex = 0;
        this.NIGHT_MODE_MAX_INDEX = 0;
        this.mPresetAdjustWhiteRGB = new int[][][]{new int[][]{new int[]{0, -6, -20}, new int[]{0, -3, -10}, new int[]{0, 0, 0}, new int[]{-7, -5, 0}, new int[]{-14, -10, 0}}, new int[][]{new int[]{0, -3, -20}, new int[]{0, 0, -10}, new int[]{-3, 0, -3}, new int[]{-7, -2, 0}, new int[]{-14, -7, 0}}, new int[][]{new int[]{0, 0, -20}, new int[]{-3, 0, -13}, new int[]{-6, 0, -6}, new int[]{-8, 0, -1}, new int[]{-14, -4, 0}}, new int[][]{new int[]{-3, 0, -23}, new int[]{-6, 0, -16}, new int[]{-9, 0, -9}, new int[]{-11, 0, -4}, new int[]{-14, -1, 0}}, new int[][]{new int[]{-6, 0, -26}, new int[]{-9, 0, -19}, new int[]{-12, 0, -12}, new int[]{-14, 0, -7}, new int[]{-16, 0, -2}}};
        DisplayManager.DisplayListener displayListener = new DisplayManager.DisplayListener() { // from class: com.samsung.android.hardware.display.SemMdnieManagerService.1
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                Display display;
                boolean isBlueLightFilterScheduledTime;
                ContentResolver contentResolver = SemMdnieManagerService.this.mContext.getContentResolver();
                SemMdnieManagerService semMdnieManagerService = SemMdnieManagerService.this;
                if (semMdnieManagerService.mDisplayManager == null || semMdnieManagerService.FP_FEATURE_SENSOR_IS_OPTICAL.contains("optical") || (display = SemMdnieManagerService.this.mDisplayManager.getDisplay(i)) == null || i != 0) {
                    return;
                }
                SemMdnieManagerService.this.mDisplayState = display.getState();
                SemMdnieManagerService semMdnieManagerService2 = SemMdnieManagerService.this;
                int i2 = semMdnieManagerService2.mDisplayState;
                if (i2 == 3 || i2 == 4 || i2 == 1) {
                    semMdnieManagerService2.mDisplayState = 1;
                } else {
                    semMdnieManagerService2.mDisplayState = 2;
                }
                int i3 = semMdnieManagerService2.mDisplayState;
                if (i3 != semMdnieManagerService2.mDisplayStatePrev) {
                    semMdnieManagerService2.mDisplayStatePrev = i3;
                    StringBuilder sb = new StringBuilder("DisplayListener onDisplayChanged. mAlwaysOnDisplayEnabled : ");
                    sb.append(SemMdnieManagerService.this.mAlwaysOnDisplayEnabled);
                    sb.append(" , mDisplayOn : ");
                    sb.append(SemMdnieManagerService.this.mDisplayOn);
                    sb.append(" , mDisplayState : ");
                    sb.append(SemMdnieManagerService.this.mDisplayState);
                    sb.append(" , mWorkBlueFilter : ");
                    sb.append(SemMdnieManagerService.this.mWorkBlueFilter);
                    sb.append(" , mNightModeBlock : ");
                    HeimdAllFsService$$ExternalSyntheticOutline0.m("SemMdnieManagerService", sb, SemMdnieManagerService.this.mNightModeBlock);
                    SemDisplaySolutionManager semDisplaySolutionManager = (SemDisplaySolutionManager) SemMdnieManagerService.this.mContext.getSystemService("DisplaySolution");
                    if (semDisplaySolutionManager == null) {
                        Slog.d("SemMdnieManagerService", "SemDisplaySolutionManager is null");
                        isBlueLightFilterScheduledTime = true;
                    } else {
                        isBlueLightFilterScheduledTime = semDisplaySolutionManager.isBlueLightFilterScheduledTime();
                    }
                    if (!SemMdnieManagerService.this.mNightMode && isBlueLightFilterScheduledTime && Settings.System.getIntForUser(contentResolver, "blue_light_filter", 0, -2) == 1) {
                        SemMdnieManagerService semMdnieManagerService3 = SemMdnieManagerService.this;
                        if (!semMdnieManagerService3.mNightModeBlock) {
                            semMdnieManagerService3.mNightMode = true;
                        }
                    }
                    SemMdnieManagerService semMdnieManagerService4 = SemMdnieManagerService.this;
                    if (semMdnieManagerService4.mDisplayState == 1) {
                        semMdnieManagerService4.setNightDimOffMode(semMdnieManagerService4.mNightModeIndex, semMdnieManagerService4.mNightMode);
                    } else {
                        semMdnieManagerService4.setNightMode(semMdnieManagerService4.mNightMode, semMdnieManagerService4.mNightModeIndex);
                    }
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
        this.mContext = context;
        ContentResolver contentResolver = context.getContentResolver();
        SettingsObserver settingsObserver = new SettingsObserver();
        this.mSupportContentMode = (intValue & 1) != 0;
        this.mSupportContentModeGame = (intValue & 2) != 0;
        this.mSupportContentModeVideoEnhance = (intValue & 4) != 0;
        this.mSupportScreenMode = (intValue & 16) != 0;
        this.mSupportScreeenReadingMode = (intValue & 32) != 0;
        this.mSupportBlueFilter = (intValue & 4096) != 0;
        this.mSupportLightNotificationMode = (intValue & 32768) != 0;
        this.mMdnieWhiteRGBSupported = true;
        this.mEnvironmentAdaptiveDisplaySupported = true;
        if (!"DDI".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LCD_CONFIG_HW_MDNIE"))) {
            this.mSupportAPmDNIe = true;
        }
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_mode_setting"), false, settingsObserver, -2);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_mode_automatic_setting"), false, settingsObserver, -2);
        contentResolver.registerContentObserver(Settings.System.getUriFor("sec_display_temperature_red"), false, settingsObserver, -2);
        contentResolver.registerContentObserver(Settings.System.getUriFor("sec_display_temperature_green"), false, settingsObserver, -2);
        contentResolver.registerContentObserver(Settings.System.getUriFor("sec_display_temperature_blue"), false, settingsObserver, -2);
        contentResolver.registerContentObserver(Settings.System.getUriFor("sec_display_preset_index"), false, settingsObserver, 0);
        contentResolver.registerContentObserver(Settings.System.getUriFor("vividness_intensity"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_night_dim"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("aod_show_state"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("ead_enabled"), false, settingsObserver, -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        if (!FactoryTest.isFactoryBinary()) {
            context.registerReceiver(new ScreenWatchingReceiver(), intentFilter);
        }
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0) > 0) {
            z = true;
            this.mAdaptiveBlueLightFilterSupported = true;
        } else {
            z = true;
        }
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NIGHT_DIM", 0) > 0) {
            this.mNightDimSupported = z;
        }
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NATURAL_MODE_TYPE", 0) == z) {
            this.mNaturalGammaScreenModeSupported = z;
        }
        boolean z2 = this.mAdaptiveBlueLightFilterSupported;
        if (z2 && this.mNightDimSupported) {
            this.NIGHT_MODE_MAX_INDEX = 306;
        } else if (!z2 || this.mNightDimSupported) {
            this.NIGHT_MODE_MAX_INDEX = 11;
        } else {
            this.NIGHT_MODE_MAX_INDEX = 102;
        }
        Slog.d("SemMdnieManagerService", "SemMdnieMdnieManager Night mode Index : " + this.NIGHT_MODE_MAX_INDEX);
        boolean z3 = context.getResources().getBoolean(R.bool.config_LTE_eri_for_network_name);
        this.mUseAfterimageCompensationServiceConfig = z3;
        if (z3 && !FactoryTest.isFactoryBinary()) {
            this.afterimageCompensation = new AfterimageCompensationService(context);
        }
        try {
            if (this.mDisplayManager == null) {
                DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
                this.mDisplayManager = displayManager;
                displayManager.registerDisplayListener(displayListener, null);
            }
            if (this.mDisplayAiqeManager == null) {
                this.mDisplayAiqeManager = (DisplayAiqeManager) context.getSystemService("display_aiqe");
            }
        } catch (Exception unused) {
            Slog.d("SemMdnieManagerService", "failed to registerProcessObserver");
        }
        setting_is_changed();
        if (this.mSupportAPmDNIe) {
            if (this.mNaturalGammaScreenModeSupported) {
                Slog.i("SemMdnieManagerService", "AP setNaturalMode : DS");
                this.mDisplayAiqeManager.setNaturalMode("DS");
            } else {
                Slog.i("SemMdnieManagerService", "AP setNaturalMode : DM");
                this.mDisplayAiqeManager.setNaturalMode("DM");
            }
            if (this.mEnvironmentAdaptiveDisplaySupported) {
                Slog.i("SemMdnieManagerService", "AP setEnvironmentAdaptiveDisplayMode : " + Settings.System.getIntForUser(this.mContext.getContentResolver(), "ead_enabled", 0, -2));
                this.mDisplayAiqeManager.setEnvironmentAdaptiveDisplayMode(Settings.System.getIntForUser(this.mContext.getContentResolver(), "ead_enabled", 0, -2));
            }
        }
        if (FactoryTest.isFactoryBinary()) {
            if (this.mSupportAPmDNIe) {
                Slog.i("SemMdnieManagerService", "AP setByPassMode : true");
                this.mDisplayAiqeManager.setByPassMode(true);
            } else {
                if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie/bypass")) {
                    sysfsWrite(1, "/sys/class/mdnie/mdnie/bypass");
                }
                if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/bypass")) {
                    sysfsWrite(1, "/sys/class/mdnie/mdnie1/bypass");
                }
                Slog.i("SemMdnieManagerService", "DDI setByPassMode : true");
            }
        }
        Slog.d("SemMdnieManagerService", "mMdnieWhiteRGBSupported - " + this.mMdnieWhiteRGBSupported + ", S_EAD - " + this.mEnvironmentAdaptiveDisplaySupported + ", SemMdnieMdnieManager AFC config : " + this.mUseAfterimageCompensationServiceConfig);
        Slog.i("SemMdnieManagerService", "SemMdnieMdnieManagerService Init Success");
    }

    public static boolean sysfsWrite(int i, String str) {
        FileOutputStream fileOutputStream;
        File file = new File(str);
        if (file.exists()) {
            FileOutputStream fileOutputStream2 = null;
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (IOException e2) {
                e = e2;
            }
            try {
                fileOutputStream.write(Integer.toString(i).getBytes(Charset.forName("UTF-8")));
                fileOutputStream.close();
                return true;
            } catch (IOException e3) {
                e = e3;
                fileOutputStream2 = fileOutputStream;
                e.printStackTrace();
                try {
                    fileOutputStream2.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    public static boolean sysfsWriteSting(String str, String str2) {
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                try {
                    fileOutputStream2.write(str2.getBytes(Charset.forName("UTF-8")));
                    fileOutputStream2.close();
                    return true;
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return false;
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (IOException e4) {
            e = e4;
        }
    }

    public static void sysfsWrite_AdaptiveArray(String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(iArr[0]));
        sb.append(" ");
        sb.append(Integer.toString(iArr[1]));
        sb.append(" ");
        String m = ProcessList$$ExternalSyntheticOutline0.m(sb, iArr[2]);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                try {
                    fileOutputStream2.write(m.getBytes(Charset.forName("UTF-8")));
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

    public static boolean sysfsWrite_CB(int i, String str, int[] iArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toString(i));
        sb.append(" ");
        BatteryService$$ExternalSyntheticOutline0.m(iArr[0], sb, " ");
        BatteryService$$ExternalSyntheticOutline0.m(iArr[1], sb, " ");
        BatteryService$$ExternalSyntheticOutline0.m(iArr[2], sb, " ");
        BatteryService$$ExternalSyntheticOutline0.m(iArr[3], sb, " ");
        BatteryService$$ExternalSyntheticOutline0.m(iArr[4], sb, " ");
        BatteryService$$ExternalSyntheticOutline0.m(iArr[5], sb, " ");
        BatteryService$$ExternalSyntheticOutline0.m(iArr[6], sb, " ");
        BatteryService$$ExternalSyntheticOutline0.m(iArr[7], sb, " ");
        String m = AudioChannelMask$$ExternalSyntheticOutline0.m(sb, iArr[8]);
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
                try {
                    fileOutputStream2.write(m.getBytes(Charset.forName("UTF-8")));
                    fileOutputStream2.close();
                    return true;
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                    return false;
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
                return false;
            }
        } catch (IOException e4) {
            e = e4;
        }
    }

    public final boolean afpcDataApply() {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "afpcDataApply");
        Slog.d("SemMdnieManagerService", "afpcDataApply");
        AfterimageCompensationService afterimageCompensationService = this.afterimageCompensation;
        if (afterimageCompensationService != null) {
            return afterimageCompensationService.afpcDataApply();
        }
        return false;
    }

    public final boolean afpcDataOff() {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "afpcDataOff");
        Slog.d("SemMdnieManagerService", "afpcDataOff");
        AfterimageCompensationService afterimageCompensationService = this.afterimageCompensation;
        if (afterimageCompensationService != null) {
            return afterimageCompensationService.afpcDataOff();
        }
        return false;
    }

    public final boolean afpcDataVerify() {
        Slog.d("SemMdnieManagerService", "afpcDataVerify");
        AfterimageCompensationService afterimageCompensationService = this.afterimageCompensation;
        if (afterimageCompensationService != null) {
            return afterimageCompensationService.afpcDataVerify();
        }
        return false;
    }

    public final boolean afpcDataWrite() {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "afpcDataWrite");
        Slog.d("SemMdnieManagerService", "afpcDataWrite");
        AfterimageCompensationService afterimageCompensationService = this.afterimageCompensation;
        if (afterimageCompensationService != null) {
            return afterimageCompensationService.afpcDataWrite();
        }
        return false;
    }

    public final boolean afpcWorkOff() {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "afpcWorkOff");
        Slog.d("SemMdnieManagerService", "afpcWorkOff");
        AfterimageCompensationService afterimageCompensationService = this.afterimageCompensation;
        if (afterimageCompensationService == null) {
            return false;
        }
        afterimageCompensationService.getClass();
        Slog.i("AfterimageCompensationService", "afpcWorkOff()");
        afterimageCompensationService.AfcStateCondition = false;
        return true;
    }

    public final boolean checkScreenMode(int i) {
        boolean z = false;
        if (this.mSupportScreenMode && (i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || (i == 5 && this.mSupportScreeenReadingMode))) {
            z = true;
        }
        if (this.mSupportScreeenReadingMode) {
            if (i == 5) {
                z = true;
            }
            if (i == 4) {
                return true;
            }
        }
        return z;
    }

    public final boolean disableNightMode() {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "disableNightMode");
        return setNightMode(false, this.mNightModeIndex);
    }

    public final boolean enableNightMode(int i) {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "enableNightMode");
        return setNightMode(true, i);
    }

    public final int getContentMode() {
        return this.mContentMode;
    }

    public final boolean getNightModeBlock() {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "getNightModeBlock");
        HeimdAllFsService$$ExternalSyntheticOutline0.m("SemMdnieManagerService", new StringBuilder("getNightModeBlock : "), this.mWorkBlueFilter);
        return this.mWorkBlueFilter;
    }

    public final int getNightModeStep() {
        HeimdAllFsService$$ExternalSyntheticOutline0.m("SemMdnieManagerService", new StringBuilder("getNightModeStep : "), this.mWorkBlueFilter);
        return this.mBlueFilterIndex;
    }

    public final int getScreenMode() {
        return this.mScreenMode;
    }

    public final int[] getSupportedContentMode() {
        Slog.i("SemMdnieManagerService", "MDNIE_SUPPORT_FUNCTION (" + this.MDNIE_SUPPORT_FUNCTION + "), mSupportContentMode (" + this.mSupportContentMode + ")");
        boolean z = this.mSupportContentMode;
        int[] iArr = new int[z ? 6 : 0];
        if (z) {
            iArr[0] = 0;
            iArr[1] = 1;
            iArr[2] = 4;
            iArr[3] = 6;
            iArr[4] = 8;
            iArr[5] = 9;
        }
        return iArr;
    }

    public final int[] getSupportedScreenMode() {
        boolean z = this.mSupportScreenMode;
        int[] iArr = new int[z ? this.mSupportScreeenReadingMode ? 5 : 4 : this.mSupportScreeenReadingMode ? 2 : 0];
        if (z) {
            iArr[0] = 0;
            iArr[1] = 1;
            iArr[2] = 2;
            iArr[3] = 4;
            if (this.mSupportScreeenReadingMode) {
                iArr[4] = 5;
            }
        } else if (this.mSupportScreeenReadingMode) {
            iArr[0] = 4;
            iArr[1] = 5;
        }
        return iArr;
    }

    public final boolean isContentModeSupported() {
        return this.mSupportContentMode;
    }

    public final boolean isNightModeSupported() {
        return this.mSupportBlueFilter;
    }

    public final boolean isScreenModeSupported() {
        return this.mSupportScreenMode || this.mSupportScreeenReadingMode;
    }

    public final boolean setColorFadeNightDim(boolean z) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mBlueLightFilterEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter", 0, -2) == 1;
        boolean z2 = Settings.System.getIntForUser(contentResolver, "blue_light_filter_night_dim", 0, -2) == 1;
        this.mNightDimModeEnabled = z2;
        if (this.mBlueLightFilterEnabled && z2) {
            Slog.i("SemMdnieManagerService", "setColorFadeNightDim");
            if (z) {
                return setNightDimOffMode(this.mNightModeIndex, this.mNightMode);
            }
            if (this.mDisplayState == 2) {
                return setNightMode(this.mNightMode, this.mNightModeIndex);
            }
        }
        return false;
    }

    public final boolean setColorVision(boolean z, int i, int i2) {
        if (i < 0 || i > 12 || i2 < 0 || i2 > 9) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                String str = Integer.toString(z ? 1 : 0) + " " + Integer.toString(i) + " " + Integer.toString(i2);
                if (sysfsWriteSting("/sys/class/mdnie/mdnie/color_lens", str) && new File("/sys/class/mdnie/mdnie1/color_lens").exists()) {
                    sysfsWriteSting("/sys/class/mdnie/mdnie1/color_lens", str);
                }
                Slog.i("SemMdnieManagerService", "setColorVision : " + z + " - " + i + " - " + i2);
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public final boolean setContentMode(int i) {
        boolean z = this.mSupportContentMode;
        if (!z || !z) {
            return false;
        }
        if (i != 0 && i != 1 && i != 4 && i != 6 && i != 20 && i != 8 && i != 9) {
            switch (i) {
                case 11:
                case 12:
                case 13:
                    if (!this.mSupportContentModeGame) {
                        return false;
                    }
                    break;
                case 14:
                case 15:
                    if (!this.mSupportContentModeVideoEnhance) {
                        return false;
                    }
                    break;
                default:
                    return false;
            }
        }
        synchronized (this.mLock) {
            try {
                if (this.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setContentMode : " + i);
                    this.mDisplayAiqeManager.setContentMode(i);
                } else if (sysfsWrite(i, "/sys/class/mdnie/mdnie/scenario")) {
                    if (new File("/sys/class/mdnie/mdnie1/scenario").exists()) {
                        sysfsWrite(i, "/sys/class/mdnie/mdnie1/scenario");
                    }
                    Slog.i("SemMdnieManagerService", "DDI setContentMode : " + i);
                }
                this.mContentMode = i;
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public final void setEadMode(int i, int[] iArr) {
        if (this.mEnvironmentAdaptiveDisplaySupported) {
            synchronized (this.mLock) {
                try {
                    if (this.mSupportAPmDNIe) {
                        this.mDisplayAiqeManager.setEnvironmentAdaptiveDisplayLevel(i);
                        Slog.i("SemMdnieManagerService", "AP setEadMode : index(" + i + ")");
                    } else if (new File("/sys/class/mdnie/mdnie/whiteRGB").exists()) {
                        sysfsWrite_AdaptiveArray("/sys/class/mdnie/mdnie/whiteRGB", iArr);
                        Slog.i("SemMdnieManagerService", "DDI setEadMode : arr(" + iArr[0] + "," + iArr[1] + "," + iArr[2] + ")");
                    }
                } finally {
                }
            }
        }
    }

    public final void setEadModeSub(int i, int[] iArr) {
        if (this.mEnvironmentAdaptiveDisplaySupported) {
            synchronized (this.mLock) {
                try {
                    if (this.mSupportAPmDNIe) {
                        Slog.i("SemMdnieManagerService", "AP setEadModeSub : index(" + i + ")");
                    } else if (new File("/sys/class/mdnie/mdnie1/whiteRGB").exists()) {
                        sysfsWrite_AdaptiveArray("/sys/class/mdnie/mdnie1/whiteRGB", iArr);
                        Slog.i("SemMdnieManagerService", "DDI setEadModeSub : arr(" + iArr[0] + "," + iArr[1] + "," + iArr[2] + ")");
                    }
                } finally {
                }
            }
        }
    }

    public final void setExtraDimMode(int i) {
        synchronized (this.mLock) {
            try {
                if (this.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setExtraDimMode : " + i);
                    this.mDisplayAiqeManager.setExtraDimMode(i);
                } else if (sysfsWrite(i, "sys/class/mdnie/mdnie/extra_dim")) {
                    if (new File("sys/class/mdnie/mdnie1/extra_dim").exists()) {
                        sysfsWrite(i, "sys/class/mdnie/mdnie1/extra_dim");
                    }
                    Slog.i("SemMdnieManagerService", "DDI setExtraDimMode : " + i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setHighBrightnessMode(int i, int i2, int i3) {
        synchronized (this.mLock) {
            try {
                if (this.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setHighBrightnessMode id(" + i + ") lux : " + i2 + ", index : " + i3);
                    this.mDisplayAiqeManager.setHighBrightnessMode(i3);
                } else {
                    if (i == 0) {
                        sysfsWrite(i2, "/sys/class/lcd/panel/lux");
                    } else if (i == 1) {
                        sysfsWrite(i2, "/sys/class/lcd/panel1/lux");
                    }
                    Slog.i("SemMdnieManagerService", "DDI setHighBrightnessMode id(" + i + ") lux : " + i2 + ", index : " + i3);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setHighDynamicRangeMode(boolean z) {
        synchronized (this.mLock) {
            try {
                if (this.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setHighDynamicRangeMode : " + z);
                    this.mDisplayAiqeManager.setHighDynamicRangeMode(z);
                    return true;
                }
                if (sysfsWrite(z ? 1 : 0, "sys/class/mdnie/mdnie/hdr")) {
                    if (new File("sys/class/mdnie/mdnie1/hdr").exists()) {
                        sysfsWrite(z ? 1 : 0, "sys/class/mdnie/mdnie1/hdr");
                    }
                    Slog.i("SemMdnieManagerService", "DDI setHighDynamicRangeMode : " + z);
                }
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean setLightNotificationMode(boolean z) {
        boolean sysfsWrite;
        if (!this.mSupportLightNotificationMode) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (new File("/sys/class/mdnie/mdnie1/light_notification").exists()) {
                    sysfsWrite(z ? 1 : 0, "/sys/class/mdnie/mdnie1/light_notification");
                }
                Slog.i("SemMdnieManagerService", "setLightNotificationMode : " + z);
                sysfsWrite = sysfsWrite(z ? 1 : 0, "/sys/class/mdnie/mdnie/light_notification");
            } catch (Throwable th) {
                throw th;
            }
        }
        return sysfsWrite;
    }

    public final boolean setNightDimOffMode(int i, boolean z) {
        String str;
        int i2;
        boolean z2 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "blue_light_filter_night_dim", 0, -2) == 1;
        this.mNightDimModeEnabled = z2;
        if (z2) {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toString(z ? 1 : 0));
            sb.append(" ");
            i2 = i + 102;
            str = ProcessList$$ExternalSyntheticOutline0.m(sb, i2);
        } else {
            str = Integer.toString(z ? 1 : 0) + " " + Integer.toString(i);
            i2 = i;
        }
        if (!this.mSupportBlueFilter || !this.mWorkBlueFilter || i < 0 || i > this.NIGHT_MODE_MAX_INDEX) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (this.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setNightMode : " + z + ", index : " + i2);
                    this.mDisplayAiqeManager.setBlueLightFilterMode(z, i2);
                } else if (sysfsWriteSting("/sys/class/mdnie/mdnie/night_mode", str)) {
                    if (new File("/sys/class/mdnie/mdnie1/night_mode").exists()) {
                        sysfsWriteSting("/sys/class/mdnie/mdnie1/night_mode", str);
                    }
                    Slog.i("SemMdnieManagerService", "DDI setNightDimOffMode : " + z + ", index : " + i + ", str : (" + str + ")");
                }
                this.mNightMode = z;
                this.mNightModeIndex = i;
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public final boolean setNightMode(boolean z, int i) {
        String str;
        int i2;
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mNightDimModeEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter_night_dim", 0, -2) == 1;
        boolean z2 = Settings.System.getIntForUser(contentResolver, "aod_show_state", 0, -2) == 1;
        this.mAlwaysOnDisplayEnabled = z2;
        if (!this.mNightDimModeEnabled) {
            str = Integer.toString(z ? 1 : 0) + " " + Integer.toString(i);
            i2 = i;
        } else if (z2 || this.mDisplayState == 1) {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toString(z ? 1 : 0));
            sb.append(" ");
            i2 = i + 102;
            str = ProcessList$$ExternalSyntheticOutline0.m(sb, i2);
        } else {
            StringBuilder sb2 = new StringBuilder();
            sb2.append(Integer.toString(z ? 1 : 0));
            sb2.append(" ");
            i2 = i + 204;
            str = ProcessList$$ExternalSyntheticOutline0.m(sb2, i2);
        }
        if (!this.mSupportBlueFilter || !this.mWorkBlueFilter || i < 0 || i > this.NIGHT_MODE_MAX_INDEX) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (this.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setNightMode : " + z + ", index : " + i2);
                    this.mDisplayAiqeManager.setBlueLightFilterMode(z, i2);
                } else if (sysfsWriteSting("/sys/class/mdnie/mdnie/night_mode", str)) {
                    if (new File("/sys/class/mdnie/mdnie1/night_mode").exists()) {
                        sysfsWriteSting("/sys/class/mdnie/mdnie1/night_mode", str);
                    }
                    Slog.i("SemMdnieManagerService", "DDI setNightMode : " + z + ", index : " + i + ", str : (" + str + ")");
                }
                this.mNightMode = z;
                this.mNightModeIndex = i;
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public final boolean setNightModeBlock(boolean z) {
        this.mNightModeBlock = true;
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "setNightModeBlock");
        Slog.i("SemMdnieManagerService", "setNightModeBlock : " + this.mWorkBlueFilter + " - " + z);
        if (!z) {
            disableNightMode();
        }
        this.mNightModeBlock = false;
        this.mWorkBlueFilter = z;
        return true;
    }

    public final boolean setNightModeStep(int i) {
        HermesService$3$$ExternalSyntheticOutline0.m(i, "setNightModeStep : ", "SemMdnieManagerService");
        this.mBlueFilterIndex = i;
        return true;
    }

    public final boolean setScreenMode(int i) {
        if ((!this.mSupportScreenMode && !this.mSupportScreeenReadingMode) || !checkScreenMode(i)) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (this.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setScreenMode : " + i);
                    this.mDisplayAiqeManager.setScreenMode(i);
                    if (new File("/sys/class/sensors/light_sensor/screen_mode").exists()) {
                        sysfsWrite(i, "/sys/class/sensors/light_sensor/screen_mode");
                    }
                } else if (sysfsWrite(i, "/sys/class/mdnie/mdnie/mode")) {
                    if (new File("/sys/class/mdnie/mdnie1/mode").exists()) {
                        sysfsWrite(i, "/sys/class/mdnie/mdnie1/mode");
                    }
                    Slog.i("SemMdnieManagerService", "DDI setScreenMode : " + i);
                }
                this.mScreenMode = i;
            } catch (Throwable th) {
                throw th;
            }
        }
        return true;
    }

    public final boolean setWhiteRGB(int i, int i2, int i3, int i4, int i5, int i6) {
        StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "setWhiteRGB(", ",", ",");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i3, i4, ",", ",", m);
        Slog.i("SemMdnieManagerService", ActivityManagerService$$ExternalSyntheticOutline0.m(i5, i6, ",", ")", m));
        if (!this.mEnvironmentAdaptiveDisplaySupported || !this.mEnvironmentAdaptiveDisplayEnabled) {
            return false;
        }
        synchronized (this.mLock) {
            try {
                if (this.mSupportAPmDNIe) {
                    Slog.i("SemMdnieManagerService", "AP setWhiteBalanceMode : Main RGB offset (" + i + "," + i2 + "," + i3 + ") , Sub RGB offset (" + i4 + "," + i5 + "," + i6 + ")");
                    this.mDisplayAiqeManager.setWhiteBalanceMode(i, i2, i3, i4, i5, i6);
                    return false;
                }
                if (new File("/sys/class/mdnie/mdnie/whiteRGB").exists()) {
                    sysfsWriteSting("/sys/class/mdnie/mdnie/whiteRGB", Integer.toString(i) + " " + Integer.toString(i2) + " " + Integer.toString(i3));
                }
                if (new File("/sys/class/mdnie/mdnie1/whiteRGB").exists()) {
                    sysfsWriteSting("/sys/class/mdnie/mdnie1/whiteRGB", Integer.toString(i4) + " " + Integer.toString(i5) + " " + Integer.toString(i6));
                }
                Slog.i("SemMdnieManagerService", "DDI setWhiteBalanceMode : Main RGB offset (" + i + "," + i2 + "," + i3 + ") , Sub RGB offset (" + i4 + "," + i5 + "," + i6 + ")");
                return true;
            } finally {
            }
        }
    }

    public final boolean setmDNIeAccessibilityMode(int i, boolean z) {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "setmDNIeAccessibilityMode");
        Slog.i("SemMdnieManagerService", "setmDNIeAccessibilityMode(" + i + ", " + z + ")");
        if (this.mCurtainModeIsRunning) {
            return false;
        }
        if (i == 10) {
            return sysfsWrite(z ? 1 : 0, "/sys/class/backlight/panel/weakness_hbm_comp");
        }
        if (!BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/accessibility")) {
            if (!z) {
                i = 0;
            }
            return sysfsWrite(i, "/sys/class/mdnie/mdnie/accessibility");
        }
        if (!sysfsWrite(z ? i : 0, "/sys/class/mdnie/mdnie/accessibility")) {
            return false;
        }
        if (!z) {
            i = 0;
        }
        return sysfsWrite(i, "/sys/class/mdnie/mdnie1/accessibility");
    }

    public final boolean setmDNIeColorBlind(boolean z, int[] iArr) {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "setmDNIeColorBlind");
        StringBuilder sb = new StringBuilder("setmDNIeColorBlind (");
        sb.append(z);
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, ")", "SemMdnieManagerService");
        if (this.mCurtainModeIsRunning) {
            return false;
        }
        if (!BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/accessibility")) {
            return sysfsWrite_CB(z ? 2 : 0, "/sys/class/mdnie/mdnie/accessibility", iArr);
        }
        if (sysfsWrite_CB(z ? 2 : 0, "/sys/class/mdnie/mdnie/accessibility", iArr)) {
            return sysfsWrite_CB(z ? 2 : 0, "/sys/class/mdnie/mdnie1/accessibility", iArr);
        }
        return false;
    }

    public final boolean setmDNIeEmergencyMode(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "setmDNIeEmergencyMode");
        StringBuilder sb = new StringBuilder("setmDNIeEmergencyMode (");
        sb.append(z);
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, ")", "SemMdnieManagerService");
        boolean sysfsWrite = sysfsWrite(z ? 4 : 0, "/sys/class/mdnie/mdnie/accessibility");
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/accessibility")) {
            return sysfsWrite & sysfsWrite(z ? 4 : 0, "/sys/class/mdnie/mdnie1/accessibility");
        }
        return sysfsWrite;
    }

    public final boolean setmDNIeNegative(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "setmDNIeNegative");
        StringBuilder sb = new StringBuilder("setmDNIeNegative (");
        sb.append(z);
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, ")", "SemMdnieManagerService");
        if (this.mCurtainModeIsRunning) {
            return false;
        }
        return BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/accessibility") ? sysfsWrite(z ? 1 : 0, "/sys/class/mdnie/mdnie/accessibility") && sysfsWrite(z ? 1 : 0, "/sys/class/mdnie/mdnie1/accessibility") : sysfsWrite(z ? 1 : 0, "/sys/class/mdnie/mdnie/accessibility");
    }

    public final boolean setmDNIeScreenCurtain(boolean z) {
        this.mContext.enforceCallingOrSelfPermission("com.samsung.android.permission.CHANGE_DISPLAY_COLOR", "setmDNIeScreenCurtain");
        StringBuilder sb = new StringBuilder("setmDNIeScreenCurtain (");
        sb.append(z);
        DeviceIdleController$$ExternalSyntheticOutline0.m(sb, ")", "SemMdnieManagerService");
        this.mCurtainModeIsRunning = z;
        boolean sysfsWrite = sysfsWrite(z ? 3 : 0, "/sys/class/mdnie/mdnie/accessibility");
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/accessibility")) {
            return sysfsWrite & sysfsWrite(z ? 3 : 0, "/sys/class/mdnie/mdnie1/accessibility");
        }
        return sysfsWrite;
    }

    public final void setting_is_changed() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6 = 0;
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "sec_display_temperature_red", 0, -2);
        int intForUser2 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "sec_display_temperature_green", 0, -2);
        int intForUser3 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "sec_display_temperature_blue", 0, -2);
        int intForUser4 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "sec_display_preset_index", 2, 0);
        this.mScreenModeSetting = Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_mode_setting", 4, -2);
        this.mEnvironmentAdaptiveDisplayEnabled = Settings.System.getIntForUser(this.mContext.getContentResolver(), "ead_enabled", 0, -2) == 1;
        this.mVividnessIndex = Settings.System.getIntForUser(this.mContext.getContentResolver(), "vividness_intensity", 0, 0);
        StringBuilder sb = new StringBuilder("setting_is_changed - Screen Mode : ");
        sb.append(this.mScreenModeSetting);
        sb.append(" , mMdnieWhiteRGBSupported : ");
        sb.append(this.mMdnieWhiteRGBSupported);
        sb.append(" , mEnvironmentAdaptiveDisplayEnabled : ");
        HeimdAllFsService$$ExternalSyntheticOutline0.m("SemMdnieManagerService", sb, this.mEnvironmentAdaptiveDisplayEnabled);
        if (this.mEnvironmentAdaptiveDisplayEnabled) {
            return;
        }
        boolean z = this.mMdnieWhiteRGBSupported;
        if (!z || this.mScreenModeSetting != 4) {
            if (!z || this.mScreenModeSetting == 4) {
                return;
            }
            if (this.mSupportAPmDNIe) {
                Slog.i("SemMdnieManagerService", "AP setWhiteBalanceMode : Main RGB offset (0,0,0) , Sub RGB offset (0,0,0)");
                this.mDisplayAiqeManager.setWhiteBalanceMode(0, 0, 0, 0, 0, 0);
                return;
            }
            if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie/whiteRGB")) {
                sysfsWriteSting("/sys/class/mdnie/mdnie/whiteRGB", "0 0 0");
            }
            if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/whiteRGB")) {
                sysfsWriteSting("/sys/class/mdnie/mdnie1/whiteRGB", "0 0 0");
            }
            Slog.i("SemMdnieManagerService", "DDI setWhiteBalanceMode : Main RGB offset (0,0,0) , Sub RGB offset (0,0,0)");
            return;
        }
        Slog.i("SemMdnieManagerService", ActivityManagerService$$ExternalSyntheticOutline0.m(intForUser2, intForUser3, "), B(", ")", ArrayUtils$$ExternalSyntheticOutline0.m(intForUser4, intForUser, "setting_is_changed - white balance(", "), R(", "), G(")));
        if (intForUser4 < 0 || intForUser4 >= 5) {
            i = 0;
            i2 = 0;
            i3 = 0;
            i4 = 0;
            i5 = 0;
        } else {
            int[] iArr = this.mPresetAdjustWhiteRGB[0][intForUser4];
            int i7 = iArr[0];
            int i8 = iArr[1];
            int i9 = intForUser2 + i8;
            int i10 = iArr[2];
            i4 = intForUser3 + i10;
            i3 = intForUser3 + i10;
            i5 = intForUser + i7;
            i2 = intForUser2 + i8;
            i = i7 + intForUser;
            i6 = i9;
        }
        if (i5 > 0 || i5 < -250 || i6 > 0 || i6 < -250 || i4 > 0 || i4 < -250 || i > 0 || i < -250 || i2 > 0 || i2 < -250 || i3 > 0 || i3 < -250) {
            return;
        }
        if (this.mSupportAPmDNIe) {
            StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(i5, i6, "AP setWhiteBalanceMode : Main RGB offset (", ",", ",");
            ServiceKeeper$$ExternalSyntheticOutline0.m(i4, i, ") , Sub RGB offset (", ",", m);
            Slog.i("SemMdnieManagerService", ActivityManagerService$$ExternalSyntheticOutline0.m(i2, i3, ",", ")", m));
            this.mDisplayAiqeManager.setWhiteBalanceMode(i5, i6, i4, i, i2, i3);
            return;
        }
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie/whiteRGB")) {
            sysfsWriteSting("/sys/class/mdnie/mdnie/whiteRGB", Integer.toString(i5) + " " + Integer.toString(i6) + " " + Integer.toString(i4));
        }
        if (BatteryService$$ExternalSyntheticOutline0.m45m("/sys/class/mdnie/mdnie1/whiteRGB")) {
            sysfsWriteSting("/sys/class/mdnie/mdnie1/whiteRGB", Integer.toString(i) + " " + Integer.toString(i2) + " " + Integer.toString(i3));
        }
        StringBuilder m2 = ArrayUtils$$ExternalSyntheticOutline0.m(i5, i6, "DDI setWhiteBalanceMode : Main RGB offset (", ",", ",");
        ServiceKeeper$$ExternalSyntheticOutline0.m(i4, i, ") , Sub RGB offset (", ",", m2);
        Slog.i("SemMdnieManagerService", ActivityManagerService$$ExternalSyntheticOutline0.m(i2, i3, ",", ")", m2));
    }

    public final void updateAlwaysOnDisplay(boolean z, int i) {
        synchronized (this.mLock) {
            if (z) {
                try {
                    AfterimageCompensationService afterimageCompensationService = this.afterimageCompensation;
                    if (afterimageCompensationService != null && afterimageCompensationService.AfcStateCondition && afterimageCompensationService.mAfcType >= 10 && z) {
                        afterimageCompensationService.AfcThreadAODCondition = z;
                        afterimageCompensationService.AodBrightness = i;
                        Thread thread = afterimageCompensationService.mAfcThread;
                        if (thread != null) {
                            synchronized (thread) {
                                afterimageCompensationService.mAfcThread.notify();
                            }
                        }
                    }
                } finally {
                }
            }
        }
    }
}
