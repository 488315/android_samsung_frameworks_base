package com.samsung.android.displaysolution;

import android.R;
import android.content.ContentResolver;
import android.content.Context;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.PowerManager;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;

import com.samsung.android.displaysolution.BigDataLoggingService.ScreenWatchingReceiver;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.view.SemWindowManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SemDisplaySolutionManagerService extends ISemDisplaySolutionManager.Stub {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String BURN_IN_APPLY_COUNT;
    public final String BURN_IN_APPLY_COUNT_SUB;
    public final int DOU_BRIGHTNESS_STANDARD_VALUE;
    public final int DOU_BRIGHTNESS_SUPPORT_VALUE;
    public final int DOU_VERSION;
    public final String IRC_MODE_NODE;
    public final String IRC_MODE_SUB_NODE;
    public final boolean SEC_FEATURE_EXTENDED_BRIGHTNESS;
    public final AdaptiveDisplaySolutionService adsService;
    public final EyeComfortSolutionService ecsService;
    public boolean mAdaptiveScreenModeEnabled;
    public final int mAfcType;
    public boolean mAppBrightnessScale;
    public boolean mAutoBrightnessModeEnabled;
    public boolean mAutoCurrentLimitOffEnable;
    public final String[] mBrightnessBacklightValueStringArray;
    public final float[] mBrightnessCameraAppArray;
    public float mBrightnessCompensation;
    public final float[] mBrightnessExtraBrightnessArray;
    public final float[] mBrightnessGalleryAppArray;
    public final String[] mBrightnessNitsValueStringArray;
    public final float[] mBrightnessOverHeatAppArray;
    public final float[] mBrightnessSamsungVideoAppArray;
    public final float[] mBrightnessVideoEnhancerArray;
    public boolean mBrowserAppBrightnessControl;
    public boolean mBurnInPrevention;
    public final String[] mBurnInScaleFactorStringArray;
    public final float[] mBurnInScaleFactorValueArray;
    public final String[] mCameraAppBrightnessStringArray;
    public boolean mCameraEnable;
    public boolean mColorBlind;
    public final Context mContext;
    public boolean mDouAppEnable;
    public boolean mExtraBrightness;
    public boolean mExtraBrightnessModeEnabled;
    public final String[] mExtraBrightnessStringArray;
    public final AnonymousClass1 mFoldStateListener;
    public final String[] mGalleryAppBrightnessStringArray;
    public boolean mGalleryEnable;
    public final float[] mGammaArray;
    public final String[] mGammaStringArray;
    public boolean mHighBrightnessModeEnabled;
    public float mHighDynamicRangeScaleFactorValue;
    public boolean mIsFolded;
    public float mLastScaleFactorValue;
    public final Object mLock;
    public boolean mMSCSEnable;
    public String mName;
    public String mOnPixelRatioValue;
    public final String[] mOverHeatAppBrightnessStringArray;
    public final PowerManager mPowerManager;
    public boolean mRealHDR;
    public final String[] mSamsungVideoAppBrightnessStringArray;
    public float mScaleFactor;
    public final String[] mScaleFactorStringArray;
    public final float[] mScaleFactorValueArray;
    public String mSettingValue;
    public String mTitle;
    public final boolean mUseEyeComfortSolutionServiceConfig;
    public boolean mVideoEnable;
    public final String[] mVideoEnhancerBrightnessStringArray;
    public final MdnieScenarioControlService mscsService;
    public String temp_APP_BRIGHTNESS_SCALE_ON;
    public String temp_BROWSER_APP_BRIGHTNESS_ON;
    public String temp_BURNIN_PREVENTION_ON;

    public final class SettingsObserver extends ContentObserver {
        public final Uri HIGH_BRIGHTNESS_MODE_PMS_ENTER_URI;
        public final Uri SCREEN_BRIGHTNESS_MODE_URI;
        public final Uri SCREEN_EXTRA_BRIGHTNESS_URI;
        public final Uri SCREEN_MODE_AUTOMATIC_SETTING_URI;

        public SettingsObserver(Handler handler) {
            super(handler);
            SemDisplaySolutionManagerService.this.mContext.getContentResolver();
            this.HIGH_BRIGHTNESS_MODE_PMS_ENTER_URI =
                    Settings.System.getUriFor("high_brightness_mode_pms_enter");
            this.SCREEN_MODE_AUTOMATIC_SETTING_URI =
                    Settings.System.getUriFor("screen_mode_automatic_setting");
            this.SCREEN_EXTRA_BRIGHTNESS_URI = Settings.Secure.getUriFor("screen_extra_brightness");
            this.SCREEN_BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            SemDisplaySolutionManagerService semDisplaySolutionManagerService =
                    SemDisplaySolutionManagerService.this;
            int i = SemDisplaySolutionManagerService.$r8$clinit;
            semDisplaySolutionManagerService.setting_is_changed();
            if ((this.SCREEN_EXTRA_BRIGHTNESS_URI.equals(uri)
                            || this.SCREEN_BRIGHTNESS_MODE_URI.equals(uri))
                    && SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_LCD_SUPPORT_EXTRA_BRIGHTNESS", false)) {
                StringBuilder sb = new StringBuilder("mAutoBrightnessModeEnabled : ");
                sb.append(SemDisplaySolutionManagerService.this.mAutoBrightnessModeEnabled);
                sb.append(" , mExtraBrightnessModeEnabled : ");
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "SemDisplaySolutionManagerService",
                        sb,
                        SemDisplaySolutionManagerService.this.mExtraBrightnessModeEnabled);
                SemDisplaySolutionManagerService semDisplaySolutionManagerService2 =
                        SemDisplaySolutionManagerService.this;
                if (semDisplaySolutionManagerService2.mAutoBrightnessModeEnabled
                        || !semDisplaySolutionManagerService2.mExtraBrightnessModeEnabled) {
                    semDisplaySolutionManagerService2.setMultipleScreenBrightness(
                            "extra_brightness_off");
                } else {
                    semDisplaySolutionManagerService2.setMultipleScreenBrightness(
                            "extra_brightness_on");
                }
            }
            if (this.SCREEN_MODE_AUTOMATIC_SETTING_URI.equals(uri)
                    || this.HIGH_BRIGHTNESS_MODE_PMS_ENTER_URI.equals(uri)) {
                StringBuilder sb2 = new StringBuilder("mHighBrightnessModeEnabled : ");
                sb2.append(SemDisplaySolutionManagerService.this.mHighBrightnessModeEnabled);
                sb2.append(" , mAdaptiveScreenModeEnabled : ");
                AnyMotionDetector$$ExternalSyntheticOutline0.m(
                        "SemDisplaySolutionManagerService",
                        sb2,
                        SemDisplaySolutionManagerService.this.mAdaptiveScreenModeEnabled);
                SemDisplaySolutionManagerService semDisplaySolutionManagerService3 =
                        SemDisplaySolutionManagerService.this;
                if (semDisplaySolutionManagerService3.mHighBrightnessModeEnabled
                        || semDisplaySolutionManagerService3.mAdaptiveScreenModeEnabled) {
                    SemDisplaySolutionManagerService.sysfsWrite(
                            0, semDisplaySolutionManagerService3.IRC_MODE_NODE);
                    if (new File(SemDisplaySolutionManagerService.this.IRC_MODE_SUB_NODE)
                            .exists()) {
                        SemDisplaySolutionManagerService.sysfsWrite(
                                0, SemDisplaySolutionManagerService.this.IRC_MODE_SUB_NODE);
                    }
                    Slog.d("SemDisplaySolutionManagerService", "IRC Mode : moderato_mode");
                    return;
                }
                SemDisplaySolutionManagerService.sysfsWrite(
                        1, semDisplaySolutionManagerService3.IRC_MODE_NODE);
                if (new File(SemDisplaySolutionManagerService.this.IRC_MODE_SUB_NODE).exists()) {
                    SemDisplaySolutionManagerService.sysfsWrite(
                            1, SemDisplaySolutionManagerService.this.IRC_MODE_SUB_NODE);
                }
                Slog.d("SemDisplaySolutionManagerService", "IRC Mode : flat_gamma_mode");
            }
        }
    }

    /* JADX WARN: Type inference failed for: r12v43, types: [com.samsung.android.displaysolution.SemDisplaySolutionManagerService$1] */
    public SemDisplaySolutionManagerService(Context context) {
        "eng".equals(Build.TYPE);
        this.mLock = new Object();
        this.mscsService = null;
        this.adsService = null;
        this.ecsService = null;
        this.mUseEyeComfortSolutionServiceConfig = false;
        this.mIsFolded = false;
        this.mMSCSEnable = false;
        this.mColorBlind = false;
        this.mRealHDR = false;
        this.mExtraBrightness = false;
        this.mAppBrightnessScale = false;
        this.mBrowserAppBrightnessControl = false;
        this.mBurnInPrevention = false;
        this.mAutoBrightnessModeEnabled = false;
        this.mAdaptiveScreenModeEnabled = false;
        this.mExtraBrightnessModeEnabled = false;
        this.mHighBrightnessModeEnabled = false;
        this.SEC_FEATURE_EXTENDED_BRIGHTNESS = false;
        this.DOU_BRIGHTNESS_SUPPORT_VALUE = 0;
        this.DOU_BRIGHTNESS_STANDARD_VALUE = 0;
        this.DOU_VERSION = 0;
        this.mAfcType = 0;
        this.mBrightnessCompensation = 1.0f;
        this.mLastScaleFactorValue = 1.0f;
        this.mHighDynamicRangeScaleFactorValue = 1.0f;
        this.IRC_MODE_NODE = "/sys/class/lcd/panel/irc_mode";
        this.IRC_MODE_SUB_NODE = "/sys/class/lcd/panel1/irc_mode";
        this.BURN_IN_APPLY_COUNT = "/efs/afc/apply_count";
        this.BURN_IN_APPLY_COUNT_SUB = "/efs/afc1/apply_count";
        SystemProperties.get("ro.build.scafe.version");
        this.mContext = context;
        boolean z = context.getResources().getBoolean(R.bool.config_noHomeScreen);
        boolean z2 = context.getResources().getBoolean(R.bool.config_allow3rdPartyAppOnInternal);
        boolean z3 = context.getResources().getBoolean(R.bool.config_brightWhenDozing);
        int integer =
                context.getResources()
                        .getInteger(R.integer.config_vibratorControlServiceDumpSizeLimit);
        this.mScaleFactorStringArray =
                context.getResources().getStringArray(R.array.config_displayUniqueIdArray);
        this.mBurnInScaleFactorStringArray =
                context.getResources()
                        .getStringArray(R.array.config_displayCutoutSideOverrideArray);
        this.mGammaStringArray =
                context.getResources().getStringArray(R.array.config_displayShapeArray);
        this.mExtraBrightnessStringArray =
                context.getResources()
                        .getStringArray(R.array.config_display_no_service_when_sim_unready);
        this.mGalleryAppBrightnessStringArray =
                context.getResources().getStringArray(R.array.config_emergency_iso_country_codes);
        this.mCameraAppBrightnessStringArray =
                context.getResources()
                        .getStringArray(R.array.config_disableApksUnlessMatchedSku_apk_list);
        this.mSamsungVideoAppBrightnessStringArray =
                context.getResources().getStringArray(R.array.config_gnssParameters);
        this.mOverHeatAppBrightnessStringArray =
                context.getResources()
                        .getStringArray(R.array.config_force_cellular_transport_capabilities);
        this.mVideoEnhancerBrightnessStringArray =
                context.getResources().getStringArray(R.array.config_keep_warming_services);
        this.mBrightnessBacklightValueStringArray =
                context.getResources()
                        .getStringArray(R.array.config_healthConnectMigrationKnownSigners);
        this.mBrightnessNitsValueStringArray =
                context.getResources()
                        .getStringArray(R.array.config_healthConnectRestoreKnownSigners);
        this.DOU_BRIGHTNESS_SUPPORT_VALUE =
                context.getResources().getInteger(R.integer.config_deskDockRotation);
        this.DOU_BRIGHTNESS_STANDARD_VALUE =
                context.getResources().getInteger(R.integer.config_deskDockKeepsScreenOn);
        this.DOU_VERSION =
                context.getResources()
                        .getInteger(R.integer.config_jobSchedulerInactivityIdleThreshold);
        this.mAfcType =
                context.getResources().getInteger(R.integer.config_MaxConcurrentDownloadsAllowed);
        if (SemFloatingFeature.getInstance()
                        .getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0)
                > 0) {
            this.mUseEyeComfortSolutionServiceConfig = true;
        }
        "DDI"
                .equals(
                        SemFloatingFeature.getInstance()
                                .getString("SEC_FLOATING_FEATURE_LCD_CONFIG_HW_MDNIE"));
        if (z) {
            this.mMSCSEnable = true;
            this.mscsService = new MdnieScenarioControlService(context);
        }
        if (z2) {
            this.adsService = new AdaptiveDisplaySolutionService(context);
        }
        if (this.mUseEyeComfortSolutionServiceConfig) {
            this.ecsService = new EyeComfortSolutionService(context);
        }
        if (z3) {
            BigDataLoggingService bigDataLoggingService = new BigDataLoggingService();
            "eng".equals(Build.TYPE);
            HandlerThread handlerThread = new HandlerThread("BigDataLoggingServiceThread");
            handlerThread.start();
            new BigDataLoggingService.ScrControlHandler(handlerThread.getLooper(), null);
            boolean z4 = context.getResources().getBoolean(R.bool.config_brightWhenDozing);
            SystemProperties.set("sys.bigdatalogging.bdlon", "false");
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            context.registerReceiver(
                    bigDataLoggingService.new ScreenWatchingReceiver(), intentFilter);
            if (z4) {
                SystemProperties.set("sys.bigdatalogging.bdlon", "true");
            }
        }
        if (integer > 255) {
            this.SEC_FEATURE_EXTENDED_BRIGHTNESS = true;
        }
        Slog.d("SemDisplaySolutionManagerService", "mUseMdnieScenarioControlServiceConfig : " + z);
        Slog.d(
                "SemDisplaySolutionManagerService",
                "mUseAdaptiveDisplaySolutionServiceConfig : " + z2);
        Slog.d(
                "SemDisplaySolutionManagerService",
                "mUseEyeComfortSolutionServiceConfig : "
                        + this.mUseEyeComfortSolutionServiceConfig);
        Slog.d("SemDisplaySolutionManagerService", "mUseBigDataLoggingServiceConfig : " + z);
        Slog.d(
                "SemDisplaySolutionManagerService",
                "mScreenBrightnessExtendedMaximumConfig : " + integer);
        this.mScaleFactorValueArray = new float[1];
        int i = 0;
        while (true) {
            String[] strArr = this.mScaleFactorStringArray;
            if (i >= strArr.length) {
                break;
            }
            this.mScaleFactorValueArray[i] = Float.parseFloat(strArr[i]);
            i++;
        }
        this.mBurnInScaleFactorValueArray = new float[10];
        int i2 = 0;
        while (true) {
            String[] strArr2 = this.mBurnInScaleFactorStringArray;
            if (i2 >= strArr2.length) {
                break;
            }
            this.mBurnInScaleFactorValueArray[i2] = Float.parseFloat(strArr2[i2]);
            i2++;
        }
        this.mBrightnessExtraBrightnessArray = new float[1];
        int i3 = 0;
        while (true) {
            String[] strArr3 = this.mExtraBrightnessStringArray;
            if (i3 >= strArr3.length) {
                break;
            }
            this.mBrightnessExtraBrightnessArray[i3] = Float.parseFloat(strArr3[i3]);
            i3++;
        }
        this.mBrightnessGalleryAppArray = new float[1];
        int i4 = 0;
        while (true) {
            String[] strArr4 = this.mGalleryAppBrightnessStringArray;
            if (i4 >= strArr4.length) {
                break;
            }
            this.mBrightnessGalleryAppArray[i4] = Float.parseFloat(strArr4[i4]);
            i4++;
        }
        this.mBrightnessCameraAppArray = new float[1];
        int i5 = 0;
        while (true) {
            String[] strArr5 = this.mCameraAppBrightnessStringArray;
            if (i5 >= strArr5.length) {
                break;
            }
            this.mBrightnessCameraAppArray[i5] = Float.parseFloat(strArr5[i5]);
            i5++;
        }
        this.mBrightnessSamsungVideoAppArray = new float[1];
        int i6 = 0;
        while (true) {
            String[] strArr6 = this.mSamsungVideoAppBrightnessStringArray;
            if (i6 >= strArr6.length) {
                break;
            }
            this.mBrightnessSamsungVideoAppArray[i6] = Float.parseFloat(strArr6[i6]);
            i6++;
        }
        this.mBrightnessOverHeatAppArray = new float[1];
        int i7 = 0;
        while (true) {
            String[] strArr7 = this.mOverHeatAppBrightnessStringArray;
            if (i7 >= strArr7.length) {
                break;
            }
            this.mBrightnessOverHeatAppArray[i7] = Float.parseFloat(strArr7[i7]);
            i7++;
        }
        this.mBrightnessVideoEnhancerArray = new float[2];
        int i8 = 0;
        while (true) {
            String[] strArr8 = this.mVideoEnhancerBrightnessStringArray;
            if (i8 >= strArr8.length) {
                break;
            }
            this.mBrightnessVideoEnhancerArray[i8] = Float.parseFloat(strArr8[i8]);
            i8++;
        }
        this.mGammaArray = new float[1];
        int i9 = 0;
        while (true) {
            String[] strArr9 = this.mGammaStringArray;
            if (i9 >= strArr9.length) {
                break;
            }
            this.mGammaArray[i9] = Float.parseFloat(strArr9[i9]);
            i9++;
        }
        SettingsObserver settingsObserver = new SettingsObserver(new Handler());
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mPowerManager = (PowerManager) this.mContext.getSystemService("power");
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("high_brightness_mode_pms_enter"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("screen_mode_automatic_setting"),
                false,
                settingsObserver,
                -1);
        contentResolver.registerContentObserver(
                Settings.Secure.getUriFor("screen_extra_brightness"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("screen_brightness_mode"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("blue_light_filter"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("blue_light_filter_night_dim"),
                false,
                settingsObserver,
                -1);
        Slog.d("SemDisplaySolutionManagerService", "registerDisplayStateListener");
        this.mFoldStateListener =
                new SemWindowManager.FoldStateListener() { // from class:
                    // com.samsung.android.displaysolution.SemDisplaySolutionManagerService.1
                    public final void onFoldStateChanged(boolean z5) {
                        synchronized (SemDisplaySolutionManagerService.this.mLock) {
                            try {
                                SemDisplaySolutionManagerService.this.mIsFolded = z5;
                                if (SemFloatingFeature.getInstance()
                                        .getBoolean(
                                                "SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN",
                                                false)) {
                                    SemDisplaySolutionManagerService
                                            semDisplaySolutionManagerService =
                                                    SemDisplaySolutionManagerService.this;
                                    if (semDisplaySolutionManagerService.mAutoBrightnessModeEnabled
                                            || !semDisplaySolutionManagerService
                                                    .mExtraBrightnessModeEnabled
                                            || semDisplaySolutionManagerService.mIsFolded) {
                                        semDisplaySolutionManagerService
                                                .setMultipleScreenBrightness(
                                                        "extra_brightness_off");
                                    } else {
                                        semDisplaySolutionManagerService
                                                .setMultipleScreenBrightness("extra_brightness_on");
                                    }
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    public final void onTableModeChanged(boolean z5) {}
                };
        SemWindowManager.getInstance()
                .registerFoldStateListener(this.mFoldStateListener, (Handler) null);
        setting_is_changed();
        if (SemFloatingFeature.getInstance()
                .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_EXTRA_BRIGHTNESS", false)) {
            StringBuilder sb = new StringBuilder("mAutoBrightnessModeEnabled : ");
            sb.append(this.mAutoBrightnessModeEnabled);
            sb.append(" , mExtraBrightnessModeEnabled : ");
            AnyMotionDetector$$ExternalSyntheticOutline0.m(
                    "SemDisplaySolutionManagerService", sb, this.mExtraBrightnessModeEnabled);
            if (this.mAutoBrightnessModeEnabled || !this.mExtraBrightnessModeEnabled) {
                setMultipleScreenBrightness("extra_brightness_off");
            } else {
                setMultipleScreenBrightness("extra_brightness_on");
            }
        }
        StringBuilder sb2 = new StringBuilder("mHighBrightnessModeEnabled : ");
        sb2.append(this.mHighBrightnessModeEnabled);
        sb2.append(" , mAdaptiveScreenModeEnabled : ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "SemDisplaySolutionManagerService", sb2, this.mAdaptiveScreenModeEnabled);
        if (this.mHighBrightnessModeEnabled || this.mAdaptiveScreenModeEnabled) {
            sysfsWrite(0, this.IRC_MODE_NODE);
            if (new File(this.IRC_MODE_SUB_NODE).exists()) {
                sysfsWrite(0, this.IRC_MODE_SUB_NODE);
            }
            Slog.d("SemDisplaySolutionManagerService", "IRC Mode : moderato_mode");
            return;
        }
        sysfsWrite(1, this.IRC_MODE_NODE);
        if (new File(this.IRC_MODE_SUB_NODE).exists()) {
            sysfsWrite(1, this.IRC_MODE_SUB_NODE);
        }
        Slog.d("SemDisplaySolutionManagerService", "IRC Mode : flat_gamma_mode");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0079 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0083 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v3 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v5, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getStringFromFile(java.lang.String r9) {
        /*
            r0 = 128(0x80, float:1.794E-43)
            byte[] r1 = new byte[r0]
            r2 = 0
            r3 = r2
        L6:
            if (r3 >= r0) goto Ld
            r1[r3] = r2
            int r3 = r3 + 1
            goto L6
        Ld:
            java.lang.String r0 = "File Close error"
            java.lang.String r3 = "SemDisplaySolutionManagerService"
            r4 = 0
            if (r9 == 0) goto L25
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L22
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L22
            r6.<init>(r9)     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L22
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L1f java.lang.Exception -> L22
            goto L26
        L1f:
            r9 = move-exception
            goto L81
        L22:
            r9 = move-exception
            r1 = r4
            goto L48
        L25:
            r5 = r4
        L26:
            if (r5 == 0) goto L8b
            int r9 = r5.read(r1)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L46
            if (r9 <= 0) goto L42
            java.lang.String r6 = new java.lang.String     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c
            int r7 = r9 + (-1)
            java.nio.charset.Charset r8 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c
            r6.<init>(r1, r2, r7, r8)     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c
            r4 = r6
            goto L42
        L39:
            r9 = move-exception
            r4 = r5
            goto L81
        L3c:
            r1 = move-exception
            r2 = r9
            r9 = r1
        L3f:
            r1 = r4
            r4 = r5
            goto L48
        L42:
            r5.close()     // Catch: java.lang.Throwable -> L39 java.lang.Exception -> L3c
            goto L8b
        L46:
            r9 = move-exception
            goto L3f
        L48:
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L1f
            r5.<init>()     // Catch: java.lang.Throwable -> L1f
            java.lang.String r6 = "Exception : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L1f
            r5.append(r9)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r6 = " , in : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L1f
            r5.append(r4)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r6 = " , value : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L1f
            r5.append(r1)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r6 = " , length : "
            r5.append(r6)     // Catch: java.lang.Throwable -> L1f
            r5.append(r2)     // Catch: java.lang.Throwable -> L1f
            java.lang.String r2 = r5.toString()     // Catch: java.lang.Throwable -> L1f
            android.util.Slog.e(r3, r2)     // Catch: java.lang.Throwable -> L1f
            r9.printStackTrace()     // Catch: java.lang.Throwable -> L1f
            if (r4 == 0) goto L95
            r4.close()     // Catch: java.lang.Exception -> L7d
            goto L95
        L7d:
            android.util.Slog.e(r3, r0)
            goto L95
        L81:
            if (r4 == 0) goto L8a
            r4.close()     // Catch: java.lang.Exception -> L87
            goto L8a
        L87:
            android.util.Slog.e(r3, r0)
        L8a:
            throw r9
        L8b:
            if (r5 == 0) goto L94
            r5.close()     // Catch: java.lang.Exception -> L91
            goto L94
        L91:
            android.util.Slog.e(r3, r0)
        L94:
            r1 = r4
        L95:
            return r1
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.displaysolution.SemDisplaySolutionManagerService.getStringFromFile(java.lang.String):java.lang.String");
    }

    public static boolean sysfsWrite(int i, String str) {
        File file = new File(str);
        if (file.exists()) {
            FileOutputStream fileOutputStream = null;
            try {
                try {
                    FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                    try {
                        fileOutputStream2.write(Integer.toString(i).getBytes());
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
        return false;
    }

    public final void controlScaleFactorValue(float f, String str) {
        String str2;
        Binder binder = new Binder();
        this.mScaleFactor = f;
        this.mTitle = "application";
        this.mName = str;
        if (new File(this.BURN_IN_APPLY_COUNT).exists()
                || new File(this.BURN_IN_APPLY_COUNT_SUB).exists()) {
            try {
                boolean z = this.mIsFolded;
                if (!z) {
                    str2 = getStringFromFile(this.BURN_IN_APPLY_COUNT);
                    Slog.d(
                            "SemDisplaySolutionManagerService",
                            "burn_in_brightness_compensation() raw : " + str2);
                } else if (z) {
                    String stringFromFile = getStringFromFile(this.BURN_IN_APPLY_COUNT_SUB);
                    Slog.d(
                            "SemDisplaySolutionManagerService",
                            "burn_in_brightness_compensation() sub raw : " + stringFromFile);
                    str2 = stringFromFile;
                } else {
                    str2 = null;
                }
                if (str2 != null) {
                    String[] split = str2.trim().split(" ");
                    try {
                        if (split.length == 2) {
                            int i = this.mAfcType;
                            if (i == 2) {
                                this.mBrightnessCompensation =
                                        (Float.parseFloat(split[0].trim()) * 0.01f) + 1.0f;
                            } else if (i >= 3 && i <= 7) {
                                this.mBrightnessCompensation =
                                        (Float.parseFloat(split[0].trim()) * 0.005f) + 1.0f;
                            } else if (i >= 8) {
                                this.mBrightnessCompensation =
                                        (Float.parseFloat(split[0].trim()) * 0.007f) + 1.0f;
                            }
                        } else {
                            this.mBrightnessCompensation = 1.0f;
                        }
                    } catch (NumberFormatException e) {
                        Slog.e("SemDisplaySolutionManagerService", "NumberFormatException : " + e);
                    }
                } else {
                    this.mBrightnessCompensation = 1.0f;
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if (this.mTitle.equals("application")
                && this.SEC_FEATURE_EXTENDED_BRIGHTNESS
                && this.mScaleFactor * this.mBrightnessCompensation
                        >= FullScreenMagnificationGestureHandler.MAX_SCALE) {
            Slog.d(
                    "SemDisplaySolutionManagerService",
                    "where : "
                            + this.mTitle
                            + " what : "
                            + this.mName
                            + " setScreenBrightnessScaleFactor("
                            + this.mScaleFactor
                            + ") , mBrightnessCompensation : "
                            + this.mBrightnessCompensation
                            + " , PMS value : "
                            + (this.mScaleFactor * this.mBrightnessCompensation));
            this.mPowerManager.setScreenBrightnessScaleFactor(
                    this.mScaleFactor * this.mBrightnessCompensation, binder);
        }
    }

    public final float getAlphaMaskLevel(float f, float f2, float f3) {
        Slog.d(
                "SemDisplaySolutionManagerService",
                "getAlphaMaskLevel() CurrentPlatformBrightnessValue : "
                        + f
                        + " , FingerPrintPlatformValue : "
                        + f2
                        + " , br_ctrl : "
                        + f3
                        + " , gamma : "
                        + this.mGammaArray[0]);
        float parseFloat =
                f >= FullScreenMagnificationGestureHandler.MAX_SCALE
                        ? Float.parseFloat(this.mBrightnessNitsValueStringArray[(int) f])
                        : -1.0f;
        float parseFloat2 =
                f2 >= FullScreenMagnificationGestureHandler.MAX_SCALE
                        ? Float.parseFloat(this.mBrightnessNitsValueStringArray[(int) f2])
                        : -1.0f;
        Slog.d(
                "SemDisplaySolutionManagerService",
                "calculateAlphaBlendingValue() currentNits : "
                        + parseFloat
                        + " , targetNits : "
                        + parseFloat2
                        + " , br_ctrl : "
                        + f3
                        + " , gamma : "
                        + this.mGammaArray[0]);
        return (float) (1.0d - Math.pow((parseFloat * f3) / parseFloat2, 1.0f / r7));
    }

    public final boolean getAutoCurrentLimitOffModeEnabled() {
        return this.mAutoCurrentLimitOffEnable;
    }

    public final int getBlfAdaptiveCurrentIndex() {
        synchronized (this.mLock) {
            try {
                EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
                if (eyeComfortSolutionService == null) {
                    return -1;
                }
                return eyeComfortSolutionService.mLastTotalBlueLightFilterIndex;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean getCameraModeEnable() {
        return this.mCameraEnable;
    }

    public final boolean getDouAppModeEnable() {
        return this.mDouAppEnable;
    }

    public final float getFingerPrintBacklightValue(int i) {
        int i2 = 0;
        while (true) {
            String[] strArr = this.mBrightnessNitsValueStringArray;
            if (i2 > strArr.length - 1) {
                return -1.0f;
            }
            if (i <= Integer.parseInt(strArr[i2])) {
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i,
                                "getFingerPrintBacklightValue() brightnessNits : ",
                                " , FingerPrintBacklightValue : ");
                m.append(Float.parseFloat(this.mBrightnessBacklightValueStringArray[i2]));
                m.append(" , mBrightnessNitsValueStringArray_Size : ");
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        m,
                        this.mBrightnessNitsValueStringArray.length,
                        "SemDisplaySolutionManagerService");
                return Float.parseFloat(this.mBrightnessBacklightValueStringArray[i2]);
            }
            i2++;
        }
    }

    public final boolean getGalleryModeEnable() {
        return this.mGalleryEnable;
    }

    public final String getOnPixelRatioValueForPMS() {
        return this.mOnPixelRatioValue;
    }

    public final int getVideoEnhancerSettingState(String str) {
        return this.mscsService.getAppSettingState(str);
    }

    public final boolean getVideoModeEnable() {
        return this.mVideoEnable;
    }

    public final boolean getting_auto_brightness_mode_enabled() {
        return Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "screen_brightness_mode", 0, -2)
                == 1;
    }

    public final float getting_platform_brightness_value() {
        return Settings.System.getIntForUser(
                this.mContext.getContentResolver(), "screen_brightness", 0, -2);
    }

    public final boolean isBlueLightFilterScheduledTime() {
        synchronized (this.mLock) {
            try {
                EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
                boolean z = false;
                if (eyeComfortSolutionService == null) {
                    return false;
                }
                Slog.d(
                        "EyeComfortSolutionService",
                        "mBlueLightFilterAdaptiveModeEnabled: "
                                + eyeComfortSolutionService.mBlueLightFilterAdaptiveModeEnabled
                                + " , mBlueLightFilterScheduledTime: "
                                + eyeComfortSolutionService.mBlueLightFilterScheduledTime);
                if (!eyeComfortSolutionService.mBlueLightFilterAdaptiveModeEnabled
                        && eyeComfortSolutionService.mBlueLightFilterScheduledTime) {
                    z = true;
                }
                return z;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean isMdnieScenarioControlServiceEnabled() {
        return this.mMSCSEnable;
    }

    public final void onAutoCurrentLimitOffMode(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    if (this.adsService != null) {
                        this.mscsService.getClass();
                    }
                } else if (this.adsService != null) {
                    this.mscsService.getClass();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onAutoCurrentLimitStateChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    AdaptiveDisplaySolutionService adaptiveDisplaySolutionService = this.adsService;
                    if (adaptiveDisplaySolutionService != null) {
                        adaptiveDisplaySolutionService.setAutoCurrentLimitState(z);
                    }
                } else {
                    AdaptiveDisplaySolutionService adaptiveDisplaySolutionService2 =
                            this.adsService;
                    if (adaptiveDisplaySolutionService2 != null) {
                        adaptiveDisplaySolutionService2.setAutoCurrentLimitState(z);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onAutoCurrentLimitStateChangedInt(int i) {
        synchronized (this.mLock) {
            AdaptiveDisplaySolutionService adaptiveDisplaySolutionService = this.adsService;
            if (adaptiveDisplaySolutionService != null) {
                ContentResolver contentResolver =
                        adaptiveDisplaySolutionService.mContext.getContentResolver();
                if (adaptiveDisplaySolutionService.AUTO_CURRENT_LIMIT_VERSION >= 3
                        && i == 1
                        && Settings.System.getIntForUser(
                                        contentResolver, "screen_brightness_mode", 0, -2)
                                == 0) {
                    i += 2;
                }
                StringBuilder m =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "setAutoCurrentLimitStateInt(", ") , mADSEnableCondition : ");
                m.append(adaptiveDisplaySolutionService.mADSEnableCondition);
                Slog.d("AdaptiveDisplaySolutionService", m.toString());
                adaptiveDisplaySolutionService.updateAdaptiveControlStateInt(i);
            }
        }
    }

    public final void onAutoCurrentLimitStateChangedWithBrightness(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    AdaptiveDisplaySolutionService adaptiveDisplaySolutionService = this.adsService;
                    if (adaptiveDisplaySolutionService != null) {
                        adaptiveDisplaySolutionService.setAutoCurrentLimitStateWithBrightness(z);
                    }
                } else {
                    AdaptiveDisplaySolutionService adaptiveDisplaySolutionService2 =
                            this.adsService;
                    if (adaptiveDisplaySolutionService2 != null) {
                        adaptiveDisplaySolutionService2.setAutoCurrentLimitStateWithBrightness(z);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onBurnInPreventionDisabled(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    if (this.adsService != null) {
                        this.mscsService.getClass();
                    }
                } else if (this.adsService != null) {
                    this.mscsService.getClass();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void onDetailVeiwStateChanged(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    AdaptiveDisplaySolutionService adaptiveDisplaySolutionService = this.adsService;
                    if (adaptiveDisplaySolutionService != null) {
                        adaptiveDisplaySolutionService.setGalleryDetailViewMode(z);
                    }
                } else {
                    AdaptiveDisplaySolutionService adaptiveDisplaySolutionService2 =
                            this.adsService;
                    if (adaptiveDisplaySolutionService2 != null) {
                        adaptiveDisplaySolutionService2.setGalleryDetailViewMode(z);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setAutoCurrentLimitOffModeEnabled(boolean z) {
        synchronized (this.mLock) {
            this.mAutoCurrentLimitOffEnable = z;
        }
    }

    public final void setBlfEnableTimeBySchedule(boolean z, int i) {
        synchronized (this.mLock) {
            try {
                EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
                if (eyeComfortSolutionService != null) {
                    eyeComfortSolutionService.setBlfEnableTimeBySchedule(z, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setCameraModeEnable(boolean z) {
        synchronized (this.mLock) {
            this.mCameraEnable = z;
        }
    }

    public final void setDouAppModeEnable(boolean z) {
        synchronized (this.mLock) {
            this.mDouAppEnable = z;
        }
    }

    public final void setEadIndexOffset(int i) {
        synchronized (this.mLock) {
            EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
            if (eyeComfortSolutionService != null) {
                eyeComfortSolutionService.getClass();
                DeviceIdleController$$ExternalSyntheticOutline0.m(
                        i, "setEadIndexOffset(", ")", "EyeComfortSolutionService");
                if (i < 0) {
                    i = 0;
                }
                eyeComfortSolutionService.mBlfIndexEadOffset = i;
                if (eyeComfortSolutionService.mBlueLightFilterModeEnabled
                        && eyeComfortSolutionService.mBlueLightFilterAdaptiveModeEnabled) {
                    eyeComfortSolutionService.setBlueLightFilterMode(
                            eyeComfortSolutionService.mLastTotalBlueLightFilterIndex, true);
                }
            }
        }
    }

    public final void setEyeComfortWeightingFactor(float f) {
        synchronized (this.mLock) {
            EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
            if (eyeComfortSolutionService != null) {
                eyeComfortSolutionService.app_weighting_factor = f;
            }
        }
    }

    public final void setGalleryModeEnable(boolean z) {
        synchronized (this.mLock) {
            this.mGalleryEnable = z;
        }
    }

    public final void setHighDynamicRangeMode(boolean z) {
        synchronized (this.mLock) {
            MdnieScenarioControlService mdnieScenarioControlService = this.mscsService;
            if (mdnieScenarioControlService != null) {
                mdnieScenarioControlService.mHighDynamicRangeEnabled = z;
                Slog.d(
                        "MdnieScenarioControlService",
                        "isHighDynamicRangeModeState : "
                                + mdnieScenarioControlService.mHighDynamicRangeEnabled);
                if (mdnieScenarioControlService.mHighDynamicRangeEnabled) {
                    mdnieScenarioControlService.setAclModeScenario(0, true);
                } else if (!mdnieScenarioControlService.mGalleryAppState) {
                    mdnieScenarioControlService.setAclModeScenario(1, false);
                }
            }
        }
    }

    public final void setIRCompensationMode(boolean z) {
        synchronized (this.mLock) {
            try {
                if (z) {
                    sysfsWrite(0, this.IRC_MODE_NODE);
                    Slog.d(
                            "SemDisplaySolutionManagerService",
                            "setIRCompensationMode(" + z + ") IRC Mode : moderato_mode");
                } else if (this.mAdaptiveScreenModeEnabled) {
                    sysfsWrite(0, this.IRC_MODE_NODE);
                    Slog.d(
                            "SemDisplaySolutionManagerService",
                            "setIRCompensationMode("
                                    + z
                                    + ") IRC Mode : moderato_mode , vivid mode :"
                                    + this.mAdaptiveScreenModeEnabled);
                } else {
                    sysfsWrite(1, this.IRC_MODE_NODE);
                    Slog.d(
                            "SemDisplaySolutionManagerService",
                            "setIRCompensationMode("
                                    + z
                                    + ") IRC Mode : flat_gamma_mode , vivid mode :"
                                    + this.mAdaptiveScreenModeEnabled);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setMdnieScenarioControlServiceEnable(boolean z) {
        synchronized (this.mLock) {
            this.mMSCSEnable = z;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x0201 A[Catch: all -> 0x0157, TryCatch #0 {all -> 0x0157, blocks: (B:5:0x006c, B:7:0x0093, B:9:0x0097, B:11:0x00a1, B:14:0x00ad, B:16:0x00b8, B:19:0x00c5, B:21:0x00cf, B:24:0x00db, B:26:0x00e5, B:28:0x00ef, B:30:0x00f9, B:32:0x0103, B:34:0x010d, B:36:0x0117, B:38:0x0121, B:41:0x012d, B:43:0x0137, B:46:0x0142, B:48:0x014c, B:51:0x01fd, B:53:0x0201, B:54:0x0221, B:56:0x0225, B:57:0x0241, B:59:0x0245, B:60:0x0265, B:64:0x026f, B:67:0x028a, B:74:0x02c8, B:76:0x02d0, B:77:0x02e4, B:70:0x0309, B:81:0x033e, B:83:0x0342, B:85:0x034c, B:86:0x037c, B:89:0x038a, B:91:0x03bc, B:93:0x03c0, B:95:0x03c5, B:97:0x03cb, B:99:0x03d8, B:100:0x0408, B:101:0x043c, B:103:0x0444, B:104:0x0474, B:105:0x049f, B:107:0x04a9, B:109:0x04ad, B:111:0x04d1, B:112:0x04f3, B:114:0x04fd, B:116:0x0501, B:118:0x0525, B:119:0x0547, B:122:0x0553, B:124:0x057f, B:125:0x05a9, B:128:0x05b5, B:130:0x05bc, B:131:0x061c, B:132:0x063e, B:134:0x0648, B:135:0x066a, B:139:0x0672, B:141:0x068d, B:146:0x06d0, B:147:0x015b, B:149:0x0165, B:150:0x016d, B:152:0x0177, B:153:0x017b, B:155:0x0185, B:156:0x018d, B:158:0x0197, B:159:0x019a, B:161:0x01a4, B:162:0x01a7, B:163:0x01ae, B:165:0x01b8, B:166:0x01bb, B:168:0x01c5, B:169:0x01c8, B:171:0x01d3, B:172:0x01d6, B:174:0x01e1, B:175:0x01e4, B:177:0x01ee, B:178:0x01f1, B:180:0x01fb, B:181:0x06f6), top: B:4:0x006c }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0221 A[Catch: all -> 0x0157, TryCatch #0 {all -> 0x0157, blocks: (B:5:0x006c, B:7:0x0093, B:9:0x0097, B:11:0x00a1, B:14:0x00ad, B:16:0x00b8, B:19:0x00c5, B:21:0x00cf, B:24:0x00db, B:26:0x00e5, B:28:0x00ef, B:30:0x00f9, B:32:0x0103, B:34:0x010d, B:36:0x0117, B:38:0x0121, B:41:0x012d, B:43:0x0137, B:46:0x0142, B:48:0x014c, B:51:0x01fd, B:53:0x0201, B:54:0x0221, B:56:0x0225, B:57:0x0241, B:59:0x0245, B:60:0x0265, B:64:0x026f, B:67:0x028a, B:74:0x02c8, B:76:0x02d0, B:77:0x02e4, B:70:0x0309, B:81:0x033e, B:83:0x0342, B:85:0x034c, B:86:0x037c, B:89:0x038a, B:91:0x03bc, B:93:0x03c0, B:95:0x03c5, B:97:0x03cb, B:99:0x03d8, B:100:0x0408, B:101:0x043c, B:103:0x0444, B:104:0x0474, B:105:0x049f, B:107:0x04a9, B:109:0x04ad, B:111:0x04d1, B:112:0x04f3, B:114:0x04fd, B:116:0x0501, B:118:0x0525, B:119:0x0547, B:122:0x0553, B:124:0x057f, B:125:0x05a9, B:128:0x05b5, B:130:0x05bc, B:131:0x061c, B:132:0x063e, B:134:0x0648, B:135:0x066a, B:139:0x0672, B:141:0x068d, B:146:0x06d0, B:147:0x015b, B:149:0x0165, B:150:0x016d, B:152:0x0177, B:153:0x017b, B:155:0x0185, B:156:0x018d, B:158:0x0197, B:159:0x019a, B:161:0x01a4, B:162:0x01a7, B:163:0x01ae, B:165:0x01b8, B:166:0x01bb, B:168:0x01c5, B:169:0x01c8, B:171:0x01d3, B:172:0x01d6, B:174:0x01e1, B:175:0x01e4, B:177:0x01ee, B:178:0x01f1, B:180:0x01fb, B:181:0x06f6), top: B:4:0x006c }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void setMultipleScreenBrightness(java.lang.String r31) {
        /*
            Method dump skipped, instructions count: 1786
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.samsung.android.displaysolution.SemDisplaySolutionManagerService.setMultipleScreenBrightness(java.lang.String):void");
    }

    public final void setMultipleScreenBrightnessValueForHDR(float f) {
        synchronized (this.mLock) {
            this.mHighDynamicRangeScaleFactorValue = f;
        }
    }

    public final void setOnPixelRatioValueForPMS(String str) {
        synchronized (this.mLock) {
            this.mOnPixelRatioValue = str;
        }
    }

    public final void setScreenBrightnessForPreview(int i) {
        synchronized (this.mLock) {
        }
    }

    public final void setSleepPatternBLF(String str, long j, long j2, float f) {
        synchronized (this.mLock) {
            try {
                EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
                if (eyeComfortSolutionService != null) {
                    eyeComfortSolutionService.updateRubinSleepPattern(str, j, j2, f);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setVideoEnhancerSettingState(String str, int i) {
        synchronized (this.mLock) {
            try {
                MdnieScenarioControlService mdnieScenarioControlService = this.mscsService;
                if (mdnieScenarioControlService != null) {
                    mdnieScenarioControlService.updateVideoEnhancerSettingState(i, str);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setVideoModeEnable(boolean z) {
        synchronized (this.mLock) {
            this.mVideoEnable = z;
        }
    }

    public final void setmDNIeModeState(String str) {
        synchronized (this.mLock) {
            try {
                EyeComfortSolutionService eyeComfortSolutionService = this.ecsService;
                if (eyeComfortSolutionService != null) {
                    eyeComfortSolutionService.setmDNIeModeState(str);
                }
            } finally {
            }
        }
        synchronized (this.mLock) {
            try {
                MdnieScenarioControlService mdnieScenarioControlService = this.mscsService;
                if (mdnieScenarioControlService != null) {
                    mdnieScenarioControlService.setmDNIeModeState(str);
                }
            } finally {
            }
        }
    }

    public final void setting_is_changed() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mHighBrightnessModeEnabled =
                Settings.System.getIntForUser(
                                contentResolver, "high_brightness_mode_pms_enter", 0, -2)
                        == 1;
        this.mAdaptiveScreenModeEnabled =
                Settings.System.getIntForUser(
                                contentResolver, "screen_mode_automatic_setting", 1, -2)
                        == 1;
        this.mExtraBrightnessModeEnabled =
                Settings.Secure.getIntForUser(contentResolver, "screen_extra_brightness", 0, -2)
                        == 1;
        this.mAutoBrightnessModeEnabled =
                Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2)
                        == 1;
        StringBuilder sb = new StringBuilder("mHighBrightnessModeEnabled : ");
        sb.append(this.mHighBrightnessModeEnabled);
        sb.append(" , mAdaptiveScreenModeEnabled : ");
        sb.append(this.mAdaptiveScreenModeEnabled);
        sb.append(" , mExtraBrightnessModeEnabled : ");
        sb.append(this.mExtraBrightnessModeEnabled);
        sb.append(" , mAutoBrightnessModeEnabled : ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "SemDisplaySolutionManagerService", sb, this.mAutoBrightnessModeEnabled);
    }

    public final void updateAutoBrightnessLux(int i, int i2) {
        synchronized (this.mLock) {
            try {
                MdnieScenarioControlService mdnieScenarioControlService = this.mscsService;
                if (mdnieScenarioControlService != null) {
                    mdnieScenarioControlService.setVisionBoosterMode(i, i2);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
