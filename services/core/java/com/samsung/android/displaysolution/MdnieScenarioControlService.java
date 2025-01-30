package com.samsung.android.displaysolution;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IProcessObserver;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.media.SemMediaResourceHelper;
import com.samsung.android.view.SemWindowManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes2.dex */
public class MdnieScenarioControlService {
    public String[] ACL_CONTROL_GALLERY_APP_LIST;
    public String[] ACL_CONTROL_THIRD_PARTY_APP_LIST;
    public int ACTION_DETAIL_VIEW_STATE_IN_DEBOUNCE_MILLIS;
    public int ACTION_DETAIL_VIEW_STATE_OUT_DEBOUNCE_MILLIS;
    public int ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS;
    public int ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS;
    public int ACTION_NOTIFY_MULTIWINDOW_STATUS_DEBOUNCE_MILLIS;
    public int ACTION_SET_UI_MODE_DEBOUNCE_MILLIS;
    public int ACTION_VIDEO_APP_STATE_IN_DEBOUNCE_MILLIS;
    public int ACTION_VIDEO_APP_STATE_OUT_DEBOUNCE_MILLIS;
    public String[] ANDROID_APP_LAUNCHER;
    public int AUTO_CURRENT_LIMIT_VERSION;
    public String[] CAMERA_APP_LAUNCHER;
    public String[] CHROMEBROWSER_APP_LAUNCHER;
    public String[] DAY_OF_USE_SUPPORT_APP_LIST;
    public String[] DMB_APP_LAUNCHER;
    public int DOU_BRIGHTNESS_STANDARD_VALUE;
    public String[] EBOOK_APP_LAUNCHER;
    public String[] EMAIL_APP_LAUNCHER;
    public String[] EYE_COMFORT_1_05_APP_LIST;
    public String[] EYE_COMFORT_1_10_APP_LIST;
    public String[] EYE_COMFORT_1_15_APP_LIST;
    public String[] EYE_COMFORT_BLACKLIST_APP_LIST;
    public int FOREGROUND_RESCAN_DEBOUNCE_MILLIS;
    public String[] GALLERY_APP_LAUNCHER;
    public int IS_CAMERA_APP_DEBOUNCE_MILLIS;
    public int IS_GALLERY_FROM_CAMERA_DEBOUNCE_MILLIS;
    public String[] OVERHEAT_CONTROL_SUPPORT_APP_LIST;
    public String[] SBROWSER_APP_LAUNCHER;
    public String[] SNS_APP_LAUNCHER;
    public String[] SVIDEO_APP_LAUNCHER;
    public String[] SVIDEO_APP_OPTION_LAUNCHER;
    public String[] VIDEO_APP_LAUNCHER;
    public String[] mAclDimmingArray;
    public boolean mAclDimmingFunction;
    public ActivityManager mActivityManager;
    public int[] mBrowserBrightnessArray;
    public String[] mBrowserBrightnessStringArray;
    public final Context mContext;
    public DisplaySolutionDataBase mDBHelper;
    public SemDesktopModeManager mDesktopModeManager;
    public DisplayManager mDisplayManager;
    public SQLiteDatabase mDisplaySolutionDataBase;
    public boolean mEnvironmentAdaptiveDisplaySupported;
    public SemWindowManager.FoldStateListener mFoldStateListener;
    public boolean mGlareReductionSupported;
    public MSCSControlHandler mHandler;
    public HandlerThread mHandlerThread;
    public InputManager mInputManager;
    public KeyguardManager mKeyGuardManager;
    public SemMdnieManager mMdnieManager;
    public SemPersonaManager mPersonaManager;
    public boolean mScreenStateOn;
    public SemDisplaySolutionManager mSemDisplaySolutionManager;
    public SemMediaResourceHelper mSemMediaResourceHelper;
    public SemMultiWindowManager mSemMultiWindowManager;
    public SettingsObserver mSettingsObserver;
    public boolean mUseEyeComfortSolutionServiceConfig;
    public boolean mUseMdnieScenarioControlConfig;
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public final Object mLock = new Object();
    public final int MSG_FOREGROUND_APP = 0;
    public final int MSG_RESCAN_FOREGROUND_APP = 1;
    public final int MSG_SET_UI_MODE = 2;
    public final int MSG_SET_BROWSER_MODE = 3;
    public final int MSG_SET_GALLERY_MODE = 4;
    public final int MSG_SET_CAMERA_MODE = 5;
    public final int MSG_SET_VIDEO_MODE = 6;
    public final int MSG_SET_SAMSUNG_VIDEO_MODE = 7;
    public final int MSG_SET_SAMSUNG_VIDEO_OUT_MODE = 8;
    public final int MSG_SET_EMAIL_MODE = 9;
    public final int MSG_SET_EBOOK_MODE = 10;
    public final int MSG_SET_DMB_MODE = 11;
    public final int MSG_SET_SNS_MODE = 12;
    public final int MSG_SET_GALLERY_OUT_MODE = 13;
    public final int MSG_SET_VIDEO_OPTION_MODE = 14;
    public final int MSG_GET_SYSTEM_SERVICES = 15;
    public final int MSG_CHECK_MULTIWINDOW = 16;
    public final int MSG_BROWSER_BRIGHTNESS_DECREASE_ON = 17;
    public final int MSG_BROWSER_BRIGHTNESS_DECREASE_OFF = 18;
    public final int MSG_GET_ON_PIXEL_RATIO_VALUE = 19;
    public final int MSG_CONTROL_ANTI_GLARE_STATE = 20;
    public final int MSG_WRITE_DATABASE_VIDEO_ENHANCER_LIST = 21;
    public final int mNumberBrowserBrightnessFactor = 2;
    public int mPlatformBrightnessValue = 0;
    public int mAppLaunchStateInDatabase = 0;
    public int BROWSER_BRIGHTNESS_DECREASE_SUPPORT_VALUE = 0;
    public int ACTION_BROWSER_BRIGHTNESS_DECREASE_FIRST_MILLIS = 1200000;
    public int ACTION_BROWSER_BRIGHTNESS_DECREASE_MILLIS = 120000;
    public int GET_SYSTEM_SERVICES_MILLIS = 8000;
    public int BRIGHTNESS_DECREASE_STEP = 1;
    public int mBrowserBrightnessDefault = 0;
    public int mPolicyVersion = 0;
    public int mUserActivityStatus = 1;
    public int mQuickPanelState = 0;
    public int mPrevAclValue = 1;
    public int mCurAclValue = 1;
    public int mReduceBrightColorsLevel = 50;
    public int mScreenModeSetting = 4;
    public int mVividnessIndex = 0;
    public long mLastUserInputDuration = 0;
    public final String HIGH_BRIGHTNESS_MODE_PMS_ENTER = "high_brightness_mode_pms_enter";
    public final String ACTION_MOVIE_PLAYER_STATE_IN = "ACTION_MOVIE_PLAYER_STATE_IN";
    public final String ACTION_MOVIE_PLAYER_STATE_OUT = "ACTION_MOVIE_PLAYER_STATE_OUT";
    public final String ACTION_USER_ACTIVITY_SCREEN_DREAM = "com.samsung.server.PowerManagerService.action.USER_ACTIVITY";
    public final String ACTION_PANEL_EXPANDED = "com.samsung.systemui.statusbar.EXPANDED";
    public final String ACTION_PANEL_COLLAPSED = "com.samsung.systemui.statusbar.COLLAPSED";
    public String READING_SCENARIO_PATH = "/sys/class/mdnie/mdnie/scenario";
    public String READING_LUX_PATH = "/sys/class/lcd/panel/lux";
    public String READING_LUX_SUB_PATH = "/sys/class/lcd/panel1/lux";
    public String REDUCE_BRIGHT_COLORS_ACTIVATED_NODE = "sys/class/mdnie/mdnie/extra_dim";
    public String ADAPTIVE_CONTROL_PATH = "/sys/class/lcd/panel/adaptive_control";
    public String ON_PIXEL_RATIO_PATH = "/sys/class/sensors/light_sensor/copr_roix";
    public String GALLERY_APP_PACKAGENAME = "com.sec.android.gallery3d";
    public String SCENARIO_VALUE = null;
    public String LUX_VALUE = null;
    public String SUB_LUX_VALUE = null;
    public String FrontPackageName = null;
    public String BurnInPackageName = null;
    public String[] GALLERY_APP_MULTI_VIEW_LAUNCHER = new String[0];
    public String[] GALLERY_APP_OPTION_LAUNCHER = new String[0];
    public String[] GALLERY_APP_LAUNCHER_FROM_CAMERA = new String[0];
    public boolean mWorkingCondition = false;
    public boolean mEnableCondition = false;
    public boolean mVideoEndNotUI = false;
    public boolean isLockScreenOn = false;
    public boolean mMultiWindowOn = false;
    public boolean mWindowDivideOn = false;
    public boolean mHdrEffectEnabled = false;
    public boolean mPowerSavingModeEnabled = false;
    public boolean mHighBrightnessModeEnabled = false;
    public boolean mScreenCurtainEnabled = false;
    public boolean mNegativeColorEnabled = false;
    public boolean mColorBlindEnabled = false;
    public boolean mEBookScenarioIntented = false;
    public boolean mUIScenarioEnabled = false;
    public boolean mBrowserScenarioEnabled = false;
    public boolean mGalleryScenarioEnabled = false;
    public boolean mGalleryOptionScenarioEnabled = false;
    public boolean mCameraScenarioEnabled = false;
    public boolean mSVideoScenarioEnabled = false;
    public boolean mSVideoOptionScenarioEnabled = false;
    public boolean mVideoScenarioEnabled = false;
    public boolean mEmailScenarioEnabled = false;
    public boolean mEbookScenarioEnabled = false;
    public boolean mDMBScenarioEnabled = false;
    public boolean mSNSScenarioEnabled = false;
    public boolean mGalleryAppLauncher = false;
    public boolean mSVideoAppLauncher = false;
    public boolean mGameModeLauncher = false;
    public boolean mIsFromCamera = false;
    public boolean mGalleryAppState = false;
    public boolean mCameraAppState = false;
    public boolean mSamsungVideoAppState = false;
    public boolean mDayOfUseSupportAppState = false;
    public boolean mOverheatControlSupportAppState = false;
    public boolean mVideoEnd = false;
    public boolean mSettingCondition = false;
    public boolean mDexModeState = false;
    public boolean isHDMIConnected = false;
    public boolean mAclState = false;
    public boolean mVideoModeCheck = false;
    public boolean mHighDynamicRangeEnabled = false;
    public boolean mVideoEnhacnerEnabled = false;
    public boolean mBrowserBrightnessDecreaseEnabled = false;
    public boolean mBrowserAppLauncher = false;
    public boolean mGameAppLauncher = false;
    public boolean mAclOffEnabled = false;
    public boolean mEyeComfortScaleAppEnabled = false;
    public boolean mAutoBrightnessMode = false;
    public boolean mScreenOffTomeoutAbnormal = false;
    public boolean mMediaResourceUsed = false;
    public boolean mUseScaleFactorState = false;
    public boolean mIsFolded = false;
    public boolean mIsBrightnessAnimating = false;
    public boolean mAntiGlareEnable = false;
    public boolean mBlueLightFilterModeEnabled = false;
    public boolean mBlueLightFilterAdaptiveModeEnabled = false;
    public boolean mBlueLightFilterAntiGlareEnabled = false;
    public boolean mBlueLightFilterCustomAlwaysOn = false;
    public boolean mReduceBrightColorsActivatedEnabled = false;
    public IProcessObserver mProcessObserver = new IProcessObserver.Stub() { // from class: com.samsung.android.displaysolution.MdnieScenarioControlService.1
        public void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        public void onProcessDied(int i, int i2) {
        }

        public void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            String str;
            if (MdnieScenarioControlService.this.mHandler == null) {
                return;
            }
            MdnieScenarioControlService mdnieScenarioControlService = MdnieScenarioControlService.this;
            mdnieScenarioControlService.mActivityManager = (ActivityManager) mdnieScenarioControlService.mContext.getSystemService("activity");
            if (i != -1) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MdnieScenarioControlService.this.mHandler.removeMessages(1);
                MdnieScenarioControlService.this.mHandler.sendEmptyMessageAtTime(1, uptimeMillis + MdnieScenarioControlService.this.FOREGROUND_RESCAN_DEBOUNCE_MILLIS);
                return;
            }
            if (MdnieScenarioControlService.this.mActivityManager == null || MdnieScenarioControlService.this.mActivityManager.getRunningTasks(1) == null) {
                str = null;
            } else {
                List<ActivityManager.RunningTaskInfo> runningTasks = MdnieScenarioControlService.this.mActivityManager.getRunningTasks(1);
                if (runningTasks.size() <= 0) {
                    return;
                }
                String packageName = runningTasks.get(0).topActivity.getPackageName() != null ? runningTasks.get(0).topActivity.getPackageName() : null;
                r1 = runningTasks.get(0).topActivity.getClassName() != null ? runningTasks.get(0).topActivity.getClassName() : null;
                if (packageName != null && !packageName.contains("com.att.iqi")) {
                    Slog.d("MdnieScenarioControlService", " packageName : " + packageName + "    className : " + r1);
                }
                MdnieScenarioControlService.this.FrontPackageName = packageName;
                str = r1;
                r1 = packageName;
            }
            if (r1 == null || str == null) {
                return;
            }
            Message obtainMessage = MdnieScenarioControlService.this.mHandler.obtainMessage();
            obtainMessage.what = 0;
            obtainMessage.obj = r1 + str;
            obtainMessage.arg1 = i;
            obtainMessage.arg2 = i2;
            MdnieScenarioControlService.this.mHandler.sendMessage(obtainMessage);
        }
    };
    public SemDesktopModeManager.EventListener eventListener = new SemDesktopModeManager.EventListener() { // from class: com.samsung.android.displaysolution.MdnieScenarioControlService.2
        public void onDesktopDockConnectionChanged(boolean z) {
        }

        public void onDesktopModeChanged(boolean z) {
            if (z) {
                Slog.d("MdnieScenarioControlService", "Dex Mode Connected");
                MdnieScenarioControlService.this.mDexModeState = true;
            } else {
                Slog.d("MdnieScenarioControlService", "Dex Mode Disconnected");
                MdnieScenarioControlService.this.mDexModeState = false;
            }
        }
    };
    public DisplayManager.DisplayListener mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.samsung.android.displaysolution.MdnieScenarioControlService.4
        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayAdded(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayRemoved(int i) {
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public void onDisplayChanged(int i) {
            BrightnessInfo brightnessInfo;
            if (i != 0 || (brightnessInfo = MdnieScenarioControlService.this.mDisplayManager.getDisplay(i).getBrightnessInfo()) == null) {
                return;
            }
            MdnieScenarioControlService.this.mIsBrightnessAnimating = brightnessInfo.isAnimating;
            if (!MdnieScenarioControlService.this.mIsBrightnessAnimating && MdnieScenarioControlService.this.mPrevAclValue == 5 && MdnieScenarioControlService.this.mAutoBrightnessMode) {
                Slog.v("MdnieScenarioControlService", "BrightnessAnimating() ACL mPrevAclValue (" + MdnieScenarioControlService.this.mPrevAclValue + ") - mCurrentValue (4)");
                if (MdnieScenarioControlService.this.mSemDisplaySolutionManager != null) {
                    MdnieScenarioControlService.this.mSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(4);
                }
                MdnieScenarioControlService.this.mPrevAclValue = 4;
            }
        }
    };

    public void setAutoCurrentLimitOffMode(boolean z) {
    }

    public void setAutoCurrentLimitOffModeSNS(boolean z) {
    }

    public MdnieScenarioControlService(Context context) {
        this.DOU_BRIGHTNESS_STANDARD_VALUE = 0;
        this.AUTO_CURRENT_LIMIT_VERSION = 0;
        this.FOREGROUND_RESCAN_DEBOUNCE_MILLIS = 0;
        this.ACTION_DETAIL_VIEW_STATE_IN_DEBOUNCE_MILLIS = 0;
        this.ACTION_DETAIL_VIEW_STATE_OUT_DEBOUNCE_MILLIS = 0;
        this.ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS = 0;
        this.ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS = 0;
        this.ACTION_VIDEO_APP_STATE_IN_DEBOUNCE_MILLIS = 0;
        this.ACTION_VIDEO_APP_STATE_OUT_DEBOUNCE_MILLIS = 0;
        this.ACTION_SET_UI_MODE_DEBOUNCE_MILLIS = 0;
        this.ACTION_NOTIFY_MULTIWINDOW_STATUS_DEBOUNCE_MILLIS = 0;
        this.IS_GALLERY_FROM_CAMERA_DEBOUNCE_MILLIS = 0;
        this.IS_CAMERA_APP_DEBOUNCE_MILLIS = 0;
        this.ANDROID_APP_LAUNCHER = new String[0];
        this.SBROWSER_APP_LAUNCHER = new String[0];
        this.CHROMEBROWSER_APP_LAUNCHER = new String[0];
        this.GALLERY_APP_LAUNCHER = new String[0];
        this.CAMERA_APP_LAUNCHER = new String[0];
        this.SVIDEO_APP_LAUNCHER = new String[0];
        this.SVIDEO_APP_OPTION_LAUNCHER = new String[0];
        this.VIDEO_APP_LAUNCHER = new String[0];
        this.EMAIL_APP_LAUNCHER = new String[0];
        this.EBOOK_APP_LAUNCHER = new String[0];
        this.DMB_APP_LAUNCHER = new String[0];
        this.SNS_APP_LAUNCHER = new String[0];
        this.ACL_CONTROL_GALLERY_APP_LIST = new String[0];
        this.ACL_CONTROL_THIRD_PARTY_APP_LIST = new String[0];
        this.DAY_OF_USE_SUPPORT_APP_LIST = new String[0];
        this.OVERHEAT_CONTROL_SUPPORT_APP_LIST = new String[0];
        this.EYE_COMFORT_BLACKLIST_APP_LIST = new String[0];
        this.EYE_COMFORT_1_05_APP_LIST = new String[0];
        this.EYE_COMFORT_1_10_APP_LIST = new String[0];
        this.EYE_COMFORT_1_15_APP_LIST = new String[0];
        this.mDisplaySolutionDataBase = null;
        this.mDBHelper = null;
        this.mUseMdnieScenarioControlConfig = false;
        this.mUseEyeComfortSolutionServiceConfig = false;
        this.mScreenStateOn = false;
        this.mAclDimmingFunction = false;
        this.mEnvironmentAdaptiveDisplaySupported = false;
        this.mGlareReductionSupported = false;
        this.mContext = context;
        DisplaySolutionDataBase displaySolutionDataBase = new DisplaySolutionDataBase(context);
        this.mDBHelper = displaySolutionDataBase;
        this.mDisplaySolutionDataBase = displaySolutionDataBase.getWritableDatabase();
        HandlerThread handlerThread = new HandlerThread("MdnieScenarioControlServiceThread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new MSCSControlHandler(this.mHandlerThread.getLooper());
        this.mUseMdnieScenarioControlConfig = context.getResources().getBoolean(R.bool.config_keyguardUserSwitcher);
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0) > 0) {
            this.mUseEyeComfortSolutionServiceConfig = true;
        }
        this.ANDROID_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_deviceTabletopRotations);
        this.SBROWSER_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_healthConnectRestoreKnownSigners);
        this.CHROMEBROWSER_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_disabledUntilUsedPreinstalledImes);
        this.GALLERY_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_ephemeralResolverPackage);
        this.CAMERA_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_disabledDreamComponents);
        this.SVIDEO_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_highRefreshRateBlacklist);
        this.SVIDEO_APP_OPTION_LAUNCHER = context.getResources().getStringArray(R.array.config_integrityRuleProviderPackages);
        this.VIDEO_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_localPrivateDisplayPorts);
        this.EMAIL_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_display_no_service_when_sim_unready);
        this.EBOOK_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_displayWhiteBalanceStrongDisplayColorTemperatures);
        this.DMB_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_displayWhiteBalanceAmbientColorTemperatures);
        this.SNS_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_halfFoldedDeviceStates);
        this.ACL_CONTROL_GALLERY_APP_LIST = context.getResources().getStringArray(R.array.config_deviceStatesAvailableForAppRequests);
        this.ACL_CONTROL_THIRD_PARTY_APP_LIST = context.getResources().getStringArray(R.array.config_deviceSpecificSystemServices);
        this.DAY_OF_USE_SUPPORT_APP_LIST = context.getResources().getStringArray(R.array.config_displayCutoutPathArray);
        this.OVERHEAT_CONTROL_SUPPORT_APP_LIST = context.getResources().getStringArray(R.array.config_gnssParameters);
        this.EYE_COMFORT_BLACKLIST_APP_LIST = context.getResources().getStringArray(R.array.config_emergency_iso_country_codes);
        this.EYE_COMFORT_1_05_APP_LIST = context.getResources().getStringArray(R.array.config_doubleClickVibePattern);
        this.EYE_COMFORT_1_10_APP_LIST = context.getResources().getStringArray(R.array.config_dozeTapSensorPostureMapping);
        this.EYE_COMFORT_1_15_APP_LIST = context.getResources().getStringArray(R.array.config_dropboxLowPriorityTags);
        this.DOU_BRIGHTNESS_STANDARD_VALUE = context.getResources().getInteger(R.integer.config_defaultRefreshRate);
        this.AUTO_CURRENT_LIMIT_VERSION = context.getResources().getInteger(R.integer.config_audio_ring_vol_default);
        this.FOREGROUND_RESCAN_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_letterboxDefaultPositionForTabletopModeReachability);
        this.ACTION_DETAIL_VIEW_STATE_IN_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoBrightnessBrighteningLightDebounce);
        this.ACTION_DETAIL_VIEW_STATE_OUT_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoBrightnessDarkeningLightDebounce);
        this.ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoBrightnessInitialLightSensorRate);
        this.ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoBrightnessLightSensorRate);
        this.ACTION_VIDEO_APP_STATE_IN_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoGroupAtCount);
        this.ACTION_VIDEO_APP_STATE_OUT_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoPowerModeAnyMotionSensor);
        this.ACTION_SET_UI_MODE_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_sidefpsBpPowerPressWindow);
        this.ACTION_NOTIFY_MULTIWINDOW_STATUS_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoBrightnessShortTermModelTimeout);
        this.IS_GALLERY_FROM_CAMERA_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_lightSensorWarmupTime);
        this.IS_CAMERA_APP_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_lidOpenRotation);
        this.mHandler.removeMessages(21);
        this.mHandler.sendEmptyMessage(21);
        this.mBrowserBrightnessStringArray = context.getResources().getStringArray(R.array.config_restrictedImagesServices);
        this.mBrowserBrightnessArray = new int[2];
        int i = 0;
        while (true) {
            String[] strArr = this.mBrowserBrightnessStringArray;
            if (i >= strArr.length) {
                break;
            }
            this.mBrowserBrightnessArray[i] = Integer.valueOf(Integer.parseInt(strArr[i])).intValue();
            i++;
        }
        if (new File(this.ADAPTIVE_CONTROL_PATH).exists()) {
            String[] stringArray = this.mContext.getResources().getStringArray(R.array.config_loggable_dream_prefixes);
            this.mAclDimmingArray = stringArray;
            if (stringArray.length == 2) {
                this.mAclDimmingFunction = true;
            }
            Slog.i("MdnieScenarioControlService", "mAclDimmingFunction " + this.mAclDimmingFunction + " , array lenght : " + this.mAclDimmingArray.length + " , AclVersion : " + this.AUTO_CURRENT_LIMIT_VERSION);
        }
        this.mEnvironmentAdaptiveDisplaySupported = false;
        this.mGlareReductionSupported = false;
        Slog.i("MdnieScenarioControlService", "mEnvironmentAdaptiveDisplaySupported " + this.mEnvironmentAdaptiveDisplaySupported + " , mGlareReductionSupported : " + this.mGlareReductionSupported);
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
        this.mSemMultiWindowManager = new SemMultiWindowManager();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        long uptimeMillis = SystemClock.uptimeMillis();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power"), false, this.mSettingsObserver);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_mode_setting"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("vividness_intensity"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_auto_brightness_adj"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("lcd_curtain"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("color_blind"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_off_timeout"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_adaptive_mode"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_anti_glare"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_type"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("reduce_bright_colors_activated"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("reduce_bright_colors_level"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("high_brightness_mode_pms_enter"), false, this.mSettingsObserver, -1);
        registerDisplayStateListener();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("ACTION_MOVIE_PLAYER_STATE_IN");
        intentFilter.addAction("ACTION_MOVIE_PLAYER_STATE_OUT");
        intentFilter.addAction("com.samsung.server.PowerManagerService.action.USER_ACTIVITY");
        intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
        intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
        this.mContext.registerReceiverAsUser(new ScreenWatchingReceiver(), UserHandle.ALL, intentFilter, null, null);
        this.mHandler.removeMessages(15);
        this.mHandler.sendEmptyMessageAtTime(15, uptimeMillis + this.GET_SYSTEM_SERVICES_MILLIS);
        SemSystemProperties.set("sys.mdniecontrolservice.mscon", "false");
        if (this.mUseMdnieScenarioControlConfig) {
            SemSystemProperties.set("sys.mdniecontrolservice.mscon", "true");
        }
        this.mScreenStateOn = true;
        if ("500".equals(SemSystemProperties.get("persist.dm.passive.ambient_brightness", "")) || "1000".equals(SemSystemProperties.get("persist.dm.passive.ambient_brightness", ""))) {
            SemSystemProperties.set("persist.dm.passive.ambient_brightness", "1000,1500");
        }
        if ("172".equals(SemSystemProperties.get("persist.dm.passive.display_brightness", "")) || "255".equals(SemSystemProperties.get("persist.dm.passive.display_brightness", ""))) {
            SemSystemProperties.set("persist.dm.passive.display_brightness", "255,85");
        }
    }

    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("MdnieScenarioControlService", "action  :  " + action);
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Slog.i("MdnieScenarioControlService", "mActivityManager  :  " + MdnieScenarioControlService.this.mActivityManager + ", mMdnieManager  :  " + MdnieScenarioControlService.this.mMdnieManager + ", mSemDisplaySolutionManager  :  " + MdnieScenarioControlService.this.mSemDisplaySolutionManager + ", DesktopModeFeature  :  true");
                if (MdnieScenarioControlService.this.mActivityManager == null || MdnieScenarioControlService.this.mMdnieManager == null || MdnieScenarioControlService.this.mSemDisplaySolutionManager == null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    MdnieScenarioControlService.this.mHandler.removeMessages(15);
                    MdnieScenarioControlService.this.mHandler.sendEmptyMessageAtTime(15, uptimeMillis + MdnieScenarioControlService.this.GET_SYSTEM_SERVICES_MILLIS);
                    Slog.v("MdnieScenarioControlService", "Failure to register all of the services system.");
                }
                if (MdnieScenarioControlService.this.mActivityManager != null) {
                    MdnieScenarioControlService.this.setting_is_changed();
                }
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_WIDE_COLOR_GAMUT", false)) {
                    MdnieScenarioControlService.this.set_wcg_property();
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                if (MdnieScenarioControlService.this.mKeyGuardManager == null) {
                    MdnieScenarioControlService mdnieScenarioControlService = MdnieScenarioControlService.this;
                    mdnieScenarioControlService.mKeyGuardManager = (KeyguardManager) mdnieScenarioControlService.mContext.getSystemService("keyguard");
                    Slog.v("MdnieScenarioControlService", "mKeyGuardManager : " + MdnieScenarioControlService.this.mKeyGuardManager);
                }
                if (MdnieScenarioControlService.this.mKeyGuardManager != null && MdnieScenarioControlService.this.mKeyGuardManager.isKeyguardLocked()) {
                    MdnieScenarioControlService.this.isLockScreenOn = true;
                }
                Slog.d("MdnieScenarioControlService", "isLockScreenOn : " + MdnieScenarioControlService.this.isLockScreenOn);
                SystemClock.uptimeMillis();
                MdnieScenarioControlService.this.mScreenStateOn = true;
                if (MdnieScenarioControlService.this.isLockScreenOn) {
                    MdnieScenarioControlService.this.receive_screen_on_intent();
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                MdnieScenarioControlService.this.receive_screen_off_intent();
                return;
            }
            if ("android.intent.action.USER_PRESENT".equals(action)) {
                MdnieScenarioControlService.this.isLockScreenOn = false;
                MdnieScenarioControlService.this.mScreenStateOn = true;
                try {
                    if (MdnieScenarioControlService.this.mActivityManager != null) {
                        MdnieScenarioControlService.this.mProcessObserver.onForegroundActivitiesChanged(-1, 0, false);
                        return;
                    }
                    return;
                } catch (RemoteException unused) {
                    Slog.d("MdnieScenarioControlService", "failed to onForegroundActivitiesChanged");
                    return;
                }
            }
            if ("ACTION_MOVIE_PLAYER_STATE_IN".equals(action)) {
                MdnieScenarioControlService.this.mVideoModeCheck = true;
                MdnieScenarioControlService.this.mGalleryAppState = false;
                MdnieScenarioControlService.this.mCameraAppState = false;
                long uptimeMillis2 = SystemClock.uptimeMillis();
                MdnieScenarioControlService.this.mHandler.removeMessages(7);
                MdnieScenarioControlService.this.mHandler.sendEmptyMessageAtTime(7, uptimeMillis2 + MdnieScenarioControlService.this.ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS);
                return;
            }
            if ("ACTION_MOVIE_PLAYER_STATE_OUT".equals(action)) {
                MdnieScenarioControlService.this.mVideoModeCheck = false;
                MdnieScenarioControlService.this.mVideoEnd = true;
                long uptimeMillis3 = SystemClock.uptimeMillis();
                MdnieScenarioControlService.this.mHandler.removeMessages(8);
                MdnieScenarioControlService.this.mHandler.sendEmptyMessageAtTime(8, uptimeMillis3 + MdnieScenarioControlService.this.ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS);
                return;
            }
            if ("com.samsung.server.PowerManagerService.action.USER_ACTIVITY".equals(action)) {
                MdnieScenarioControlService.this.mUserActivityStatus = intent.getIntExtra("status", 1);
            } else {
                if ("com.samsung.systemui.statusbar.EXPANDED".equals(action)) {
                    MdnieScenarioControlService.this.mQuickPanelState = 1;
                    return;
                }
                if ("com.samsung.systemui.statusbar.COLLAPSED".equals(action)) {
                    MdnieScenarioControlService.this.mQuickPanelState = 0;
                    if (MdnieScenarioControlService.this.mAutoBrightnessMode) {
                        MdnieScenarioControlService.this.mHandler.removeMessages(19);
                        MdnieScenarioControlService.this.mHandler.sendEmptyMessage(19);
                    }
                }
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Uri BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI;
        public final Uri BLUE_LIGHT_FILTER_ANTI_GLARE_URI;
        public final Uri BLUE_LIGHT_FILTER_URI;
        public final Uri BRIGHTNESS_ADJ_URI;
        public final Uri BRIGHTNESS_MODE_URI;
        public final Uri BRIGHTNESS_URI;
        public final Uri REDUCE_BRIGHT_COLORS_ACTIVATED_URI;
        public final Uri REDUCE_BRIGHT_COLORS_LEVEL_URI;
        public final Uri SCREEN_MODE_SETTING_URI;
        public final Uri VIVIDNESS_INTENSITY_URI;
        public ContentResolver resolver;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.resolver = MdnieScenarioControlService.this.mContext.getContentResolver();
            this.BRIGHTNESS_URI = Settings.System.getUriFor("screen_brightness");
            this.BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
            this.BRIGHTNESS_ADJ_URI = Settings.System.getUriFor("screen_auto_brightness_adj");
            this.BLUE_LIGHT_FILTER_URI = Settings.System.getUriFor("blue_light_filter");
            this.BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI = Settings.System.getUriFor("blue_light_filter_adaptive_mode");
            this.BLUE_LIGHT_FILTER_ANTI_GLARE_URI = Settings.System.getUriFor("blue_light_filter_anti_glare");
            this.REDUCE_BRIGHT_COLORS_ACTIVATED_URI = Settings.Secure.getUriFor("reduce_bright_colors_activated");
            this.REDUCE_BRIGHT_COLORS_LEVEL_URI = Settings.Secure.getUriFor("reduce_bright_colors_level");
            this.SCREEN_MODE_SETTING_URI = Settings.System.getUriFor("screen_mode_setting");
            this.VIVIDNESS_INTENSITY_URI = Settings.System.getUriFor("vividness_intensity");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            MdnieScenarioControlService.this.setting_is_changed();
            if (MdnieScenarioControlService.this.AUTO_CURRENT_LIMIT_VERSION == 4) {
                if (this.BRIGHTNESS_MODE_URI.equals(uri) && Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2) == 0) {
                    MdnieScenarioControlService.this.mPrevAclValue = 3;
                    MdnieScenarioControlService.this.mAntiGlareEnable = false;
                }
                if (this.BRIGHTNESS_URI.equals(uri) && Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2) == 1 && !MdnieScenarioControlService.this.mHighBrightnessModeEnabled) {
                    MdnieScenarioControlService.this.anti_glare_state();
                    MdnieScenarioControlService.this.setAclModeSettings();
                }
                if (this.BLUE_LIGHT_FILTER_URI.equals(uri) || this.BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI.equals(uri) || this.BLUE_LIGHT_FILTER_ANTI_GLARE_URI.equals(uri)) {
                    if (Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2) == 1 && !MdnieScenarioControlService.this.mHighBrightnessModeEnabled) {
                        MdnieScenarioControlService.this.anti_glare_state();
                        MdnieScenarioControlService.this.setAclModeSettings();
                    } else {
                        MdnieScenarioControlService.this.mAntiGlareEnable = false;
                    }
                }
            }
            if (this.BRIGHTNESS_ADJ_URI.equals(uri) && MdnieScenarioControlService.this.mAutoBrightnessMode) {
                MdnieScenarioControlService.this.mHandler.removeMessages(19);
                MdnieScenarioControlService.this.mHandler.sendEmptyMessage(19);
            }
            if ((this.REDUCE_BRIGHT_COLORS_ACTIVATED_URI.equals(uri) || this.REDUCE_BRIGHT_COLORS_LEVEL_URI.equals(uri)) && SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) == 1) {
                Slog.d("MdnieScenarioControlService", "mReduceBrightColorsActivatedEnabled(" + MdnieScenarioControlService.this.mReduceBrightColorsActivatedEnabled + ") - level : " + MdnieScenarioControlService.this.mReduceBrightColorsLevel);
                if (MdnieScenarioControlService.this.mReduceBrightColorsActivatedEnabled) {
                    MdnieScenarioControlService.sysfsWrite(MdnieScenarioControlService.this.REDUCE_BRIGHT_COLORS_ACTIVATED_NODE, MdnieScenarioControlService.this.mReduceBrightColorsLevel);
                } else if (!MdnieScenarioControlService.this.mReduceBrightColorsActivatedEnabled) {
                    MdnieScenarioControlService.sysfsWrite(MdnieScenarioControlService.this.REDUCE_BRIGHT_COLORS_ACTIVATED_NODE, 0);
                }
            }
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_WIDE_COLOR_GAMUT", false)) {
                if (this.VIVIDNESS_INTENSITY_URI.equals(uri) || this.SCREEN_MODE_SETTING_URI.equals(uri)) {
                    MdnieScenarioControlService.this.set_wcg_property();
                }
            }
        }
    }

    public final void receive_screen_on_intent() {
        boolean z = false;
        this.mBrowserScenarioEnabled = false;
        this.mScreenStateOn = true;
        if (this.mSettingCondition && this.mWorkingCondition) {
            z = true;
        }
        this.mEnableCondition = z;
        String str = this.FrontPackageName;
        if (str != null) {
            setUIMode(str);
        }
    }

    public final void receive_screen_off_intent() {
        this.mScreenStateOn = false;
        this.mEnableCondition = false;
        boolean z = this.mBrowserBrightnessDecreaseEnabled;
        if (z) {
            this.mHandler.removeMessages(17);
            this.mHandler.removeMessages(18);
            this.mHandler.sendEmptyMessage(18);
        } else {
            if (z) {
                return;
            }
            this.mHandler.removeMessages(17);
        }
    }

    public final boolean getting_media_resource_info() {
        try {
            SemMediaResourceHelper createInstance = SemMediaResourceHelper.createInstance(2, false);
            this.mSemMediaResourceHelper = createInstance;
            ArrayList mediaResourceInfo = createInstance.getMediaResourceInfo(2);
            if (mediaResourceInfo != null) {
                if (mediaResourceInfo.size() > 0) {
                    return true;
                }
            }
            return false;
        } catch (IllegalStateException unused) {
            Slog.d("MdnieScenarioControlService", "failed to getMediaResourceInfo");
            this.mSemMediaResourceHelper.release();
            return false;
        }
    }

    public final int getting_platform_brightness_value() {
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness", 0, -2);
        this.mPlatformBrightnessValue = intForUser;
        return intForUser;
    }

    public final boolean getting_setting_value() {
        boolean z = getting_knox_mode_enabled();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (!z) {
            this.mHdrEffectEnabled = Settings.System.getIntForUser(contentResolver, "hdr_effect", 0, -2) == 1;
            Slog.d("MdnieScenarioControlService", "Use Current User");
        } else {
            this.mHdrEffectEnabled = Settings.System.getIntForUser(contentResolver, "hdr_effect", 0, 0) == 1;
            Slog.d("MdnieScenarioControlService", "Use Owner User");
        }
        return this.mHdrEffectEnabled;
    }

    public final boolean getting_knox_mode_enabled() {
        SemPersonaManager semPersonaManager = (SemPersonaManager) this.mContext.getSystemService("persona");
        this.mPersonaManager = semPersonaManager;
        return SemPersonaManager.isKnoxId(semPersonaManager.getFocusedUser());
    }

    public final boolean getting_autocurrentlimit_state() {
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            this.mAclState = semDisplaySolutionManager.getAutoCurrentLimitOffModeEnabled();
        }
        return this.mAclState;
    }

    public final void setting_is_changed() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        this.mPowerSavingModeEnabled = Settings.Global.getInt(contentResolver, "low_power", 0) == 1;
        this.mScreenCurtainEnabled = Settings.System.getIntForUser(contentResolver, "lcd_curtain", 0, -2) == 1;
        this.mColorBlindEnabled = Settings.System.getIntForUser(contentResolver, "color_blind", 0, -2) == 1;
        this.mHighBrightnessModeEnabled = Settings.System.getIntForUser(contentResolver, "high_brightness_mode_pms_enter", 0, -2) == 1;
        this.mAutoBrightnessMode = Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1;
        this.mScreenOffTomeoutAbnormal = Settings.System.getIntForUser(contentResolver, "screen_off_timeout", 0, -2) > 600000;
        this.mBlueLightFilterModeEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter", 0, -2) == 1;
        this.mBlueLightFilterAdaptiveModeEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter_adaptive_mode", 0, -2) == 1;
        this.mBlueLightFilterAntiGlareEnabled = Settings.System.getIntForUser(contentResolver, "blue_light_filter_anti_glare", 0, -2) == 1;
        this.mBlueLightFilterCustomAlwaysOn = Settings.System.getIntForUser(contentResolver, "blue_light_filter_type", 0, -2) == 0;
        this.mReduceBrightColorsActivatedEnabled = Settings.Secure.getIntForUser(contentResolver, "reduce_bright_colors_activated", 0, -2) == 1;
        this.mReduceBrightColorsLevel = Settings.Secure.getIntForUser(contentResolver, "reduce_bright_colors_level", 50, -2);
        this.mScreenModeSetting = Settings.System.getIntForUser(contentResolver, "screen_mode_setting", 4, -2);
        this.mVividnessIndex = Settings.System.getIntForUser(contentResolver, "vividness_intensity", 0, -2);
        boolean z = (this.mScreenCurtainEnabled || this.mColorBlindEnabled) ? false : true;
        this.mSettingCondition = z;
        boolean z2 = this.mScreenStateOn && z && this.mWorkingCondition;
        this.mEnableCondition = z2;
        if (z2) {
            try {
                this.mProcessObserver.onForegroundActivitiesChanged(-1, 0, false);
            } catch (RemoteException unused) {
                Slog.d("MdnieScenarioControlService", "failed to onForegroundActivitiesChanged");
            }
        }
    }

    public final void set_wcg_property() {
        Slog.d("MdnieScenarioControlService", "Screen Mode Version : " + SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) + " , Default : " + SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_DEFAULT_SCREEN_MODE", 4) + " , SCREEN MODE : " + this.mScreenModeSetting);
        int i = this.mScreenModeSetting;
        if (i == 4) {
            if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) == 0) {
                wcg_property_changed(1, 1);
            } else if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) == 1) {
                wcg_property_changed(0, 0);
            } else if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) >= 2) {
                wcg_property_changed(0, this.mVividnessIndex + 256);
            }
        } else if (i == 3) {
            wcg_property_changed(0, 0);
        }
        if (FactoryTest.isFactoryBinary()) {
            wcg_property_changed(1, 1);
        }
    }

    public final void wcg_property_changed(int i, int i2) {
        Slog.d("MdnieScenarioControlService", "PERSISTENT_PROPERTY_DISPLAY_COLOR(" + i + "), SurfaceFlinger(" + i2 + ")");
        SemSystemProperties.set("persist.sys.sf.native_mode", Integer.toString(i));
        IBinder service = ServiceManager.getService("SurfaceFlinger");
        if (service != null) {
            Parcel obtain = Parcel.obtain();
            obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
            obtain.writeInt(i2);
            try {
                try {
                    service.transact(1023, obtain, null, 0);
                } catch (RemoteException e) {
                    Slog.e("MdnieScenarioControlService", "Failed to set display color_2", e);
                }
            } finally {
                obtain.recycle();
            }
        }
    }

    public final void registerDisplayStateListener() {
        Slog.d("MdnieScenarioControlService", "registerDisplayStateListener");
        this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.samsung.android.displaysolution.MdnieScenarioControlService.3
            public void onTableModeChanged(boolean z) {
            }

            public void onFoldStateChanged(boolean z) {
                synchronized (MdnieScenarioControlService.this.mLock) {
                    MdnieScenarioControlService.this.mIsFolded = z;
                }
            }
        };
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
    }

    public final void monitorForegroundActivity(String str, int i, int i2) {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        boolean z9;
        boolean z10;
        boolean z11;
        this.mEyeComfortScaleAppEnabled = false;
        if (this.mSemMultiWindowManager.getMode() == 0) {
            this.mMultiWindowOn = false;
        } else {
            this.mMultiWindowOn = true;
        }
        int i3 = 0;
        while (true) {
            String[] strArr = this.ANDROID_APP_LAUNCHER;
            if (i3 >= strArr.length) {
                z = false;
                break;
            } else {
                if (str.contains(strArr[i3])) {
                    z = true;
                    break;
                }
                i3++;
            }
        }
        int i4 = 0;
        while (true) {
            String[] strArr2 = this.SBROWSER_APP_LAUNCHER;
            if (i4 >= strArr2.length) {
                z2 = false;
                break;
            } else {
                if (str.contains(strArr2[i4])) {
                    z2 = true;
                    break;
                }
                i4++;
            }
        }
        int i5 = 0;
        while (true) {
            String[] strArr3 = this.CHROMEBROWSER_APP_LAUNCHER;
            if (i5 >= strArr3.length) {
                break;
            }
            if (str.contains(strArr3[i5])) {
                z2 = true;
                break;
            }
            i5++;
        }
        int i6 = 0;
        while (true) {
            String[] strArr4 = this.GALLERY_APP_LAUNCHER;
            if (i6 >= strArr4.length) {
                z3 = false;
                break;
            } else {
                if (str.contains(strArr4[i6])) {
                    z3 = true;
                    break;
                }
                i6++;
            }
        }
        int i7 = 0;
        while (true) {
            String[] strArr5 = this.CAMERA_APP_LAUNCHER;
            if (i7 >= strArr5.length) {
                z4 = false;
                break;
            } else {
                if (str.contains(strArr5[i7])) {
                    z4 = true;
                    break;
                }
                i7++;
            }
        }
        int i8 = 0;
        while (true) {
            String[] strArr6 = this.SVIDEO_APP_LAUNCHER;
            if (i8 >= strArr6.length) {
                z5 = false;
                break;
            } else {
                if (str.contains(strArr6[i8])) {
                    z5 = true;
                    break;
                }
                i8++;
            }
        }
        int i9 = 0;
        while (true) {
            String[] strArr7 = this.SVIDEO_APP_OPTION_LAUNCHER;
            if (i9 >= strArr7.length) {
                z6 = false;
                break;
            } else {
                if (str.contains(strArr7[i9])) {
                    z6 = true;
                    break;
                }
                i9++;
            }
        }
        int i10 = 0;
        while (true) {
            String[] strArr8 = this.VIDEO_APP_LAUNCHER;
            if (i10 >= strArr8.length) {
                z7 = false;
                break;
            } else {
                if (str.contains(strArr8[i10])) {
                    z7 = true;
                    break;
                }
                i10++;
            }
        }
        int i11 = 0;
        while (true) {
            String[] strArr9 = this.EMAIL_APP_LAUNCHER;
            if (i11 >= strArr9.length) {
                z8 = false;
                break;
            } else {
                if (str.contains(strArr9[i11])) {
                    z8 = true;
                    break;
                }
                i11++;
            }
        }
        int i12 = 0;
        while (true) {
            String[] strArr10 = this.EBOOK_APP_LAUNCHER;
            if (i12 >= strArr10.length) {
                z9 = false;
                break;
            } else {
                if (str.contains(strArr10[i12])) {
                    z9 = true;
                    break;
                }
                i12++;
            }
        }
        int i13 = 0;
        while (true) {
            String[] strArr11 = this.DMB_APP_LAUNCHER;
            if (i13 >= strArr11.length) {
                z10 = false;
                break;
            } else {
                if (str.contains(strArr11[i13])) {
                    z10 = true;
                    break;
                }
                i13++;
            }
        }
        int i14 = 0;
        while (true) {
            String[] strArr12 = this.SNS_APP_LAUNCHER;
            if (i14 >= strArr12.length) {
                z11 = false;
                break;
            } else {
                if (str.contains(strArr12[i14])) {
                    z11 = true;
                    break;
                }
                i14++;
            }
        }
        int i15 = 0;
        while (true) {
            String[] strArr13 = this.EYE_COMFORT_BLACKLIST_APP_LIST;
            if (i15 >= strArr13.length) {
                break;
            }
            if (!str.contains(strArr13[i15])) {
                i15++;
            } else if (this.mUseEyeComfortSolutionServiceConfig) {
                this.mEyeComfortScaleAppEnabled = true;
                setEyeComfortScaleFactor(0.5f);
            }
        }
        int i16 = 0;
        while (true) {
            String[] strArr14 = this.EYE_COMFORT_1_05_APP_LIST;
            if (i16 >= strArr14.length) {
                break;
            }
            if (!str.contains(strArr14[i16])) {
                i16++;
            } else if (this.mUseEyeComfortSolutionServiceConfig) {
                this.mEyeComfortScaleAppEnabled = true;
                setEyeComfortScaleFactor(1.05f);
            }
        }
        int i17 = 0;
        while (true) {
            String[] strArr15 = this.EYE_COMFORT_1_10_APP_LIST;
            if (i17 >= strArr15.length) {
                break;
            }
            if (!str.contains(strArr15[i17])) {
                i17++;
            } else if (this.mUseEyeComfortSolutionServiceConfig) {
                this.mEyeComfortScaleAppEnabled = true;
                setEyeComfortScaleFactor(1.1f);
            }
        }
        int i18 = 0;
        while (true) {
            String[] strArr16 = this.EYE_COMFORT_1_15_APP_LIST;
            if (i18 >= strArr16.length) {
                break;
            }
            if (!str.contains(strArr16[i18])) {
                i18++;
            } else if (this.mUseEyeComfortSolutionServiceConfig) {
                this.mEyeComfortScaleAppEnabled = true;
                setEyeComfortScaleFactor(1.15f);
            }
        }
        if (!this.mEyeComfortScaleAppEnabled && this.mUseEyeComfortSolutionServiceConfig) {
            setEyeComfortScaleFactor(1.0f);
        }
        boolean z12 = this.mMultiWindowOn;
        if (!z12 && z2) {
            if (this.mBrowserScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mBrowserScenarioEnabled = true;
            this.mHandler.removeMessages(3);
            this.mHandler.sendEmptyMessage(3);
            return;
        }
        if (!z12 && z3) {
            if (this.mGalleryScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mGalleryScenarioEnabled = true;
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.removeMessages(4);
            this.mHandler.sendEmptyMessageAtTime(4, uptimeMillis + this.ACTION_DETAIL_VIEW_STATE_IN_DEBOUNCE_MILLIS);
            return;
        }
        if (!z12 && z4) {
            if (this.mCameraScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mCameraScenarioEnabled = true;
            long uptimeMillis2 = SystemClock.uptimeMillis();
            this.mHandler.removeMessages(5);
            this.mHandler.sendEmptyMessageAtTime(5, uptimeMillis2 + this.IS_CAMERA_APP_DEBOUNCE_MILLIS);
            return;
        }
        if (!z12 && z5) {
            if (this.mSVideoScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mSVideoScenarioEnabled = true;
            long uptimeMillis3 = SystemClock.uptimeMillis();
            Slog.d("MdnieScenarioControlService", "in video Real Multi Window State : " + this.mMultiWindowOn);
            if (this.mMultiWindowOn) {
                this.mHandler.removeMessages(8);
                this.mHandler.sendEmptyMessageAtTime(8, uptimeMillis3 + this.ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS);
                return;
            } else {
                this.mHandler.removeMessages(7);
                this.mHandler.sendEmptyMessageAtTime(7, uptimeMillis3 + this.ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS);
                return;
            }
        }
        if (!z12 && z6) {
            if (this.mSVideoOptionScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mSVideoOptionScenarioEnabled = true;
            this.mHandler.removeMessages(14);
            this.mHandler.sendEmptyMessage(14);
            return;
        }
        if (!z12 && z7) {
            if (this.mVideoScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mVideoScenarioEnabled = true;
            long uptimeMillis4 = SystemClock.uptimeMillis();
            this.mHandler.removeMessages(6);
            this.mHandler.sendEmptyMessageAtTime(6, uptimeMillis4 + this.ACTION_VIDEO_APP_STATE_IN_DEBOUNCE_MILLIS);
            return;
        }
        if (!z12 && z8) {
            if (this.mEmailScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mEmailScenarioEnabled = true;
            this.mHandler.removeMessages(9);
            this.mHandler.sendEmptyMessage(9);
            return;
        }
        if (!z12 && z9) {
            if (this.mEbookScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mEbookScenarioEnabled = true;
            this.mHandler.removeMessages(10);
            this.mHandler.sendEmptyMessage(10);
            return;
        }
        if (!z12 && z10) {
            if (this.mDMBScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mDMBScenarioEnabled = true;
            this.mHandler.removeMessages(11);
            this.mHandler.sendEmptyMessage(11);
            return;
        }
        if (!z12 && z11) {
            if (this.mSNSScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mSNSScenarioEnabled = true;
            this.mHandler.removeMessages(12);
            this.mHandler.sendEmptyMessage(12);
            return;
        }
        if (!z12 && z) {
            if (this.mUIScenarioEnabled) {
                return;
            }
            scenario_enable_reset();
            this.mUIScenarioEnabled = true;
            Slog.v("MdnieScenarioControlService", "setUIMode from UI function(2)");
            this.mHandler.removeMessages(2);
            this.mHandler.sendEmptyMessage(2);
            return;
        }
        long uptimeMillis5 = SystemClock.uptimeMillis();
        scenario_enable_reset();
        Slog.v("MdnieScenarioControlService", "setUIMode from UI function(3)");
        this.mHandler.removeMessages(2);
        this.mHandler.sendEmptyMessageAtTime(2, uptimeMillis5 + this.ACTION_SET_UI_MODE_DEBOUNCE_MILLIS);
    }

    public final class MSCSControlHandler extends Handler {
        public MSCSControlHandler(Looper looper) {
            super(looper, null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            switch (message.what) {
                case 0:
                    MdnieScenarioControlService.this.monitorForegroundActivity((String) message.obj, message.arg1, message.arg2);
                    break;
                case 1:
                    try {
                        MdnieScenarioControlService.this.mProcessObserver.onForegroundActivitiesChanged(-1, 0, false);
                        break;
                    } catch (RemoteException unused) {
                        return;
                    }
                case 2:
                    MdnieScenarioControlService mdnieScenarioControlService = MdnieScenarioControlService.this;
                    mdnieScenarioControlService.setUIMode(mdnieScenarioControlService.FrontPackageName);
                    break;
                case 3:
                    MdnieScenarioControlService mdnieScenarioControlService2 = MdnieScenarioControlService.this;
                    mdnieScenarioControlService2.setBrowserMode(mdnieScenarioControlService2.FrontPackageName);
                    break;
                case 4:
                    MdnieScenarioControlService mdnieScenarioControlService3 = MdnieScenarioControlService.this;
                    mdnieScenarioControlService3.setGalleryMode(mdnieScenarioControlService3.FrontPackageName);
                    break;
                case 5:
                    MdnieScenarioControlService mdnieScenarioControlService4 = MdnieScenarioControlService.this;
                    mdnieScenarioControlService4.setCameraMode(mdnieScenarioControlService4.FrontPackageName);
                    break;
                case 6:
                    MdnieScenarioControlService mdnieScenarioControlService5 = MdnieScenarioControlService.this;
                    mdnieScenarioControlService5.setVideoMode(mdnieScenarioControlService5.FrontPackageName);
                    break;
                case 7:
                    MdnieScenarioControlService mdnieScenarioControlService6 = MdnieScenarioControlService.this;
                    mdnieScenarioControlService6.setSVideoMode(true, mdnieScenarioControlService6.FrontPackageName);
                    break;
                case 8:
                    MdnieScenarioControlService mdnieScenarioControlService7 = MdnieScenarioControlService.this;
                    mdnieScenarioControlService7.setSVideoMode(false, mdnieScenarioControlService7.FrontPackageName);
                    break;
                case 9:
                    MdnieScenarioControlService.this.setEmailMode();
                    break;
                case 10:
                    MdnieScenarioControlService.this.setEbookMode();
                    break;
                case 11:
                    MdnieScenarioControlService mdnieScenarioControlService8 = MdnieScenarioControlService.this;
                    mdnieScenarioControlService8.setDMBMode(mdnieScenarioControlService8.FrontPackageName);
                    break;
                case 12:
                    MdnieScenarioControlService mdnieScenarioControlService9 = MdnieScenarioControlService.this;
                    mdnieScenarioControlService9.setSNSMode(mdnieScenarioControlService9.FrontPackageName);
                    break;
                case 14:
                    MdnieScenarioControlService.this.setSVideoOptionMode();
                    break;
                case 15:
                    MdnieScenarioControlService.this.getSystemServices();
                    break;
                case 17:
                    MdnieScenarioControlService.this.browser_brightness_decrease_mode(true);
                    break;
                case 18:
                    MdnieScenarioControlService.this.browser_brightness_decrease_mode(false);
                    break;
                case 19:
                    MdnieScenarioControlService.this.getCurrentOpr();
                    break;
                case 20:
                    MdnieScenarioControlService.this.anti_glare_state();
                    break;
                case 21:
                    try {
                        MdnieScenarioControlService.this.writeVideoEnhancerListInDataBase2();
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        MdnieScenarioControlService.this.writeVideoEnhancerListInDataBase();
                        return;
                    }
            }
        }
    }

    public final void setUIMode(String str) {
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        int i = 0;
        while (true) {
            String[] strArr = this.OVERHEAT_CONTROL_SUPPORT_APP_LIST;
            if (i >= strArr.length) {
                break;
            }
            if (strArr[i].contains(str)) {
                this.mOverheatControlSupportAppState = true;
                break;
            } else {
                this.mOverheatControlSupportAppState = false;
                i++;
            }
        }
        mdnie_reset();
        if (this.mGameModeLauncher) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled);
        if (this.mAclOffEnabled && !this.isLockScreenOn) {
            setAclModeScenario(false, 1);
        }
        if (this.mOverheatControlSupportAppState && !this.mHighBrightnessModeEnabled && !this.mMultiWindowOn) {
            setBrightnessScaleFactor(7);
        } else if (this.mUseScaleFactorState) {
            setBrightnessScaleFactor(0);
        }
        String str2 = this.SCENARIO_VALUE;
        if (str2 == null || str2.equals("0") || this.SCENARIO_VALUE.contains("UI_APP")) {
            return;
        }
        setMdnieScenarioMode(0);
        setAudioHDR(false);
        setVideoAppLaunch(false);
        setCameraAppLaunch(false);
        setDouAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mDayOfUseSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setUIMode from UI function");
    }

    public final void setBrowserMode(String str) {
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        int i = 0;
        while (true) {
            String[] strArr = this.DAY_OF_USE_SUPPORT_APP_LIST;
            if (i >= strArr.length) {
                break;
            }
            if (strArr[i].contains(str)) {
                setDouAppLaunch(true);
                this.mDayOfUseSupportAppState = true;
                break;
            } else {
                setDouAppLaunch(false);
                this.mDayOfUseSupportAppState = false;
                i++;
            }
        }
        mdnie_reset();
        String str2 = this.SCENARIO_VALUE;
        if (str2 == null || str2.equals("8") || this.SCENARIO_VALUE.contains("BROWSER_APP") || this.isLockScreenOn) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled);
        if (this.mAclOffEnabled) {
            setAclModeScenario(false, 1);
        }
        setMdnieScenarioMode(8);
        if (this.DOU_BRIGHTNESS_STANDARD_VALUE != 255 && this.mDayOfUseSupportAppState && !this.mAutoBrightnessMode && !this.mHighBrightnessModeEnabled) {
            setBrightnessScaleFactor(6);
        } else {
            setBrightnessScaleFactor(0);
        }
        setAudioHDR(false);
        setVideoAppLaunch(false);
        setCameraAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setBrowserMode from Browser function");
    }

    public final void setGalleryMode(String str) {
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        boolean z = false;
        while (true) {
            String[] strArr = this.ACL_CONTROL_GALLERY_APP_LIST;
            if (i >= strArr.length) {
                break;
            }
            if (strArr[i].contains(str)) {
                z = true;
            }
            i++;
        }
        mdnie_reset();
        String str2 = this.SCENARIO_VALUE;
        if (str2 == null || str2.equals("6") || this.SCENARIO_VALUE.contains("GALLERY_APP") || this.isLockScreenOn) {
            return;
        }
        if (z) {
            setAclModeScenario(true, 0);
        }
        setMdnieScenarioMode(6);
        if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false)) {
            setBrightnessScaleFactor(3);
        } else {
            setBrightnessScaleFactor(0);
        }
        setAudioHDR(false);
        setVideoAppLaunch(false);
        setCameraAppLaunch(false);
        setDouAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = true;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mDayOfUseSupportAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setGalleryMode from Gallery function");
    }

    public final void setCameraMode(String str) {
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        mdnie_reset();
        this.mIsFromCamera = true;
        if (!this.mVideoEnd) {
            String str2 = this.SCENARIO_VALUE;
            if (str2 == null || str2.equals("4") || this.SCENARIO_VALUE.contains("CAMERA_APP") || this.isLockScreenOn) {
                return;
            }
            Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled);
            if (this.mAclOffEnabled) {
                setAclModeScenario(false, 1);
            }
            setMdnieScenarioMode(4);
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false)) {
                setBrightnessScaleFactor(4);
            } else {
                setBrightnessScaleFactor(0);
            }
            setAudioHDR(false);
            setVideoAppLaunch(false);
            setCameraAppLaunch(true);
            setDouAppLaunch(false);
            this.mVideoEnhacnerEnabled = false;
            this.mGalleryAppState = false;
            this.mCameraAppState = true;
            this.mSamsungVideoAppState = false;
            this.mDayOfUseSupportAppState = false;
            Slog.v("MdnieScenarioControlService", "setCameraMode from Camera function");
            return;
        }
        this.mVideoEnd = false;
    }

    public final void setVideoMode(String str) {
        String str2;
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        while (true) {
            String[] strArr = this.DAY_OF_USE_SUPPORT_APP_LIST;
            if (i >= strArr.length) {
                break;
            }
            if (strArr[i].contains(str)) {
                setDouAppLaunch(true);
                this.mDayOfUseSupportAppState = true;
                break;
            } else {
                setDouAppLaunch(false);
                this.mDayOfUseSupportAppState = false;
                i++;
            }
        }
        boolean z = getting_setting_value();
        getting_knox_mode_enabled();
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        mdnie_reset();
        StringBuilder sb = new StringBuilder();
        sb.append("hdr_effect_enable : ");
        sb.append(z);
        sb.append(" , app_setting_value : ");
        sb.append(getAppSettingState(str) == 1);
        Slog.v("MdnieScenarioControlService", sb.toString());
        if (z && getAppSettingState(str) == 1) {
            boolean z2 = this.mMultiWindowOn;
            if (z2) {
                if (!z2 || (str2 = this.SCENARIO_VALUE) == null || str2.equals("1") || this.SCENARIO_VALUE.contains("VIDEO_APP") || this.isLockScreenOn) {
                    return;
                }
                Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
                if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
                    setAclModeScenario(false, 1);
                }
                setMdnieScenarioMode(1);
                if (this.DOU_BRIGHTNESS_STANDARD_VALUE != 255 && this.mDayOfUseSupportAppState && !this.mAutoBrightnessMode && !this.mHighBrightnessModeEnabled) {
                    setBrightnessScaleFactor(6);
                } else {
                    setBrightnessScaleFactor(0);
                }
                setAudioHDR(false);
                setVideoAppLaunch(true);
                setCameraAppLaunch(false);
                this.mVideoEnhacnerEnabled = false;
                this.mGalleryAppState = false;
                this.mCameraAppState = false;
                this.mSamsungVideoAppState = false;
                this.mOverheatControlSupportAppState = false;
                Slog.v("MdnieScenarioControlService", "setVideoMode from Video function");
                return;
            }
            String str3 = this.SCENARIO_VALUE;
            if (str3 == null || str3.equals("15") || this.SCENARIO_VALUE.contains("VIDEO_ENHANCER_THIRD") || this.isLockScreenOn) {
                return;
            }
            Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
            if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
                setAclModeScenario(false, 1);
            }
            setMdnieScenarioMode(15);
            Slog.v("MdnieScenarioControlService", "mDexModeState : " + this.mDexModeState);
            if (this.mDexModeState) {
                if (this.DOU_BRIGHTNESS_STANDARD_VALUE != 255 && this.mDayOfUseSupportAppState && !this.mAutoBrightnessMode && !this.mHighBrightnessModeEnabled) {
                    setBrightnessScaleFactor(6);
                } else {
                    setBrightnessScaleFactor(0);
                }
            } else {
                setBrightnessScaleFactor(2);
            }
            setAudioHDR(true);
            setVideoAppLaunch(true);
            setCameraAppLaunch(false);
            this.mVideoEnhacnerEnabled = true;
            this.mGalleryAppState = false;
            this.mCameraAppState = false;
            this.mSamsungVideoAppState = false;
            this.mOverheatControlSupportAppState = false;
            Slog.v("MdnieScenarioControlService", "setVideoMode from Video function(VIDEO_ENHANCER_THIRD)");
            return;
        }
        String str4 = this.SCENARIO_VALUE;
        if (str4 == null || str4.equals("1") || this.SCENARIO_VALUE.contains("VIDEO_APP") || this.isLockScreenOn) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
        if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
            setAclModeScenario(false, 1);
        }
        setMdnieScenarioMode(1);
        if (this.DOU_BRIGHTNESS_STANDARD_VALUE != 255 && this.mDayOfUseSupportAppState && !this.mAutoBrightnessMode && !this.mHighBrightnessModeEnabled) {
            setBrightnessScaleFactor(6);
        } else {
            setBrightnessScaleFactor(0);
        }
        setAudioHDR(false);
        setVideoAppLaunch(true);
        setCameraAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setVideoMode from Video function");
    }

    public final void setSVideoMode(boolean z, String str) {
        String str2;
        String str3;
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (z) {
            boolean z2 = getting_setting_value();
            getting_knox_mode_enabled();
            this.mAclOffEnabled = getting_autocurrentlimit_state();
            mdnie_reset();
            StringBuilder sb = new StringBuilder();
            sb.append("hdr_effect_enable : ");
            sb.append(z2);
            sb.append(" , app_setting_value : ");
            sb.append(getAppSettingState(str) == 1);
            sb.append(" , mVideoModeCheck : ");
            sb.append(this.mVideoModeCheck);
            Slog.v("MdnieScenarioControlService", sb.toString());
            if (z2 && getAppSettingState(str) == 1) {
                boolean z3 = this.mMultiWindowOn;
                if (!z3 && this.mVideoModeCheck) {
                    String str4 = this.SCENARIO_VALUE;
                    if (str4 == null || str4.equals("14") || this.SCENARIO_VALUE.contains("VIDEO_ENHANCER") || this.isLockScreenOn) {
                        return;
                    }
                    Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
                    if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
                        setAclModeScenario(false, 1);
                    }
                    setMdnieScenarioMode(14);
                    Slog.v("MdnieScenarioControlService", "mDexModeState : " + this.mDexModeState);
                    if (this.mDexModeState) {
                        setBrightnessScaleFactor(0);
                    } else {
                        setBrightnessScaleFactor(1);
                    }
                    setAudioHDR(true);
                    setVideoAppLaunch(true);
                    setCameraAppLaunch(false);
                    setDouAppLaunch(false);
                    this.mVideoEnhacnerEnabled = true;
                    this.mGalleryAppState = false;
                    this.mCameraAppState = false;
                    this.mSamsungVideoAppState = false;
                    this.mDayOfUseSupportAppState = false;
                    this.mOverheatControlSupportAppState = false;
                    Slog.v("MdnieScenarioControlService", "setVideoMode from Video function(VIDEO_ENHANCER)");
                    return;
                }
                if ((!z3 && this.mVideoModeCheck) || (str3 = this.SCENARIO_VALUE) == null || str3.equals("1") || this.SCENARIO_VALUE.contains("VIDEO_APP") || this.isLockScreenOn) {
                    return;
                }
                Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
                if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
                    setAclModeScenario(false, 1);
                }
                setMdnieScenarioMode(1);
                setBrightnessScaleFactor(0);
                setAudioHDR(false);
                setVideoAppLaunch(true);
                setCameraAppLaunch(false);
                setDouAppLaunch(false);
                this.mVideoEnhacnerEnabled = false;
                this.mGalleryAppState = false;
                this.mCameraAppState = false;
                this.mSamsungVideoAppState = true;
                this.mDayOfUseSupportAppState = false;
                this.mOverheatControlSupportAppState = false;
                Slog.v("MdnieScenarioControlService", "setVideoMode from SVideo function");
                return;
            }
            String str5 = this.SCENARIO_VALUE;
            if (str5 == null || str5.equals("1") || this.SCENARIO_VALUE.contains("VIDEO_APP") || this.isLockScreenOn) {
                return;
            }
            Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
            if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
                setAclModeScenario(false, 1);
            }
            setMdnieScenarioMode(1);
            setBrightnessScaleFactor(0);
            setAudioHDR(false);
            setVideoAppLaunch(true);
            setCameraAppLaunch(false);
            setDouAppLaunch(false);
            this.mVideoEnhacnerEnabled = false;
            this.mGalleryAppState = false;
            this.mCameraAppState = false;
            this.mSamsungVideoAppState = true;
            this.mDayOfUseSupportAppState = false;
            this.mOverheatControlSupportAppState = false;
            Slog.v("MdnieScenarioControlService", "setVideoMode from SVideo function");
            return;
        }
        mdnie_reset();
        if (this.GALLERY_APP_PACKAGENAME.equalsIgnoreCase(this.FrontPackageName) || (str2 = this.SCENARIO_VALUE) == null || str2.equals("0") || this.SCENARIO_VALUE.contains("UI_APP") || this.isLockScreenOn) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
        if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
            setAclModeScenario(false, 1);
        }
        setMdnieScenarioMode(0);
        setBrightnessScaleFactor(0);
        setAudioHDR(false);
        setVideoAppLaunch(false);
        setCameraAppLaunch(false);
        setDouAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mDayOfUseSupportAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setUIMode from SVideo function");
    }

    public final void setSVideoOptionMode() {
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        mdnie_reset();
        String str = this.SCENARIO_VALUE;
        if (str == null || str.equals("1") || this.SCENARIO_VALUE.contains("VIDEO_APP") || this.isLockScreenOn) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
        if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
            setAclModeScenario(false, 1);
        }
        setMdnieScenarioMode(1);
        setBrightnessScaleFactor(0);
        setAudioHDR(false);
        setVideoAppLaunch(true);
        setCameraAppLaunch(false);
        setDouAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = true;
        this.mDayOfUseSupportAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setVideoMode from SVideoOption function");
    }

    public final void setEbookMode() {
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        mdnie_reset();
        String str = this.SCENARIO_VALUE;
        if (str == null || str.equals("9") || this.SCENARIO_VALUE.contains("eBOOK_APP") || this.isLockScreenOn) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled);
        if (this.mAclOffEnabled) {
            setAclModeScenario(false, 1);
        }
        setMdnieScenarioMode(9);
        setBrightnessScaleFactor(0);
        setAudioHDR(false);
        setVideoAppLaunch(false);
        setCameraAppLaunch(false);
        setDouAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mDayOfUseSupportAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setEbookMode from Ebook function");
    }

    public final void setEmailMode() {
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        mdnie_reset();
        String str = this.SCENARIO_VALUE;
        if (str == null || str.equals("10") || this.SCENARIO_VALUE.contains("EMAIL_APP") || this.isLockScreenOn) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled);
        if (this.mAclOffEnabled) {
            setAclModeScenario(false, 1);
        }
        setMdnieScenarioMode(10);
        setBrightnessScaleFactor(0);
        setAudioHDR(false);
        setVideoAppLaunch(false);
        setCameraAppLaunch(false);
        setDouAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mDayOfUseSupportAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setEmailMode from Email function");
    }

    public final void setDMBMode(String str) {
        String str2;
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean z = getting_setting_value();
        getting_knox_mode_enabled();
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        mdnie_reset();
        StringBuilder sb = new StringBuilder();
        sb.append("hdr_effect_enable : ");
        sb.append(z);
        sb.append(" , app_setting_value : ");
        sb.append(getAppSettingState(str) == 1);
        Slog.v("MdnieScenarioControlService", sb.toString());
        if (z && getAppSettingState(str) == 1) {
            boolean z2 = this.mMultiWindowOn;
            if (z2) {
                if (!z2 || (str2 = this.SCENARIO_VALUE) == null || str2.equals("1") || this.SCENARIO_VALUE.contains("VIDEO_APP") || this.isLockScreenOn) {
                    return;
                }
                Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
                if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
                    setAclModeScenario(false, 1);
                }
                setMdnieScenarioMode(1);
                setBrightnessScaleFactor(0);
                setAudioHDR(false);
                setVideoAppLaunch(true);
                setCameraAppLaunch(false);
                setDouAppLaunch(false);
                this.mVideoEnhacnerEnabled = false;
                this.mGalleryAppState = false;
                this.mCameraAppState = false;
                this.mSamsungVideoAppState = false;
                this.mDayOfUseSupportAppState = false;
                this.mOverheatControlSupportAppState = false;
                Slog.v("MdnieScenarioControlService", "setVideoMode from DMB function");
                return;
            }
            String str3 = this.SCENARIO_VALUE;
            if (str3 == null || str3.equals("15") || this.SCENARIO_VALUE.contains("VIDEO_ENHANCER_THIRD") || this.isLockScreenOn) {
                return;
            }
            Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
            if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
                setAclModeScenario(false, 1);
            }
            setMdnieScenarioMode(15);
            Slog.v("MdnieScenarioControlService", "mDexModeState : " + this.mDexModeState);
            if (this.mDexModeState) {
                setBrightnessScaleFactor(0);
            } else {
                setBrightnessScaleFactor(2);
            }
            setAudioHDR(true);
            setVideoAppLaunch(true);
            setCameraAppLaunch(false);
            setDouAppLaunch(false);
            this.mVideoEnhacnerEnabled = true;
            this.mGalleryAppState = false;
            this.mCameraAppState = false;
            this.mSamsungVideoAppState = false;
            this.mDayOfUseSupportAppState = false;
            this.mOverheatControlSupportAppState = false;
            Slog.v("MdnieScenarioControlService", "setVideoMode from DMB function(VIDEO_ENHANCER_THIRD)");
            return;
        }
        String str4 = this.SCENARIO_VALUE;
        if (str4 == null || str4.equals("1") || this.SCENARIO_VALUE.contains("VIDEO_APP") || this.isLockScreenOn) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled + " mHighDynamicRangeEnabled : " + this.mHighDynamicRangeEnabled);
        if (this.mAclOffEnabled && !this.mHighDynamicRangeEnabled) {
            setAclModeScenario(false, 1);
        }
        setMdnieScenarioMode(1);
        setBrightnessScaleFactor(0);
        setAudioHDR(false);
        setVideoAppLaunch(true);
        setCameraAppLaunch(false);
        setDouAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mDayOfUseSupportAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setVideoMode from DMB function");
    }

    public final void setSNSMode(String str) {
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int i = 0;
        while (true) {
            String[] strArr = this.DAY_OF_USE_SUPPORT_APP_LIST;
            if (i >= strArr.length) {
                break;
            }
            if (strArr[i].contains(str)) {
                setDouAppLaunch(true);
                this.mDayOfUseSupportAppState = true;
                break;
            } else {
                setDouAppLaunch(false);
                this.mDayOfUseSupportAppState = false;
                i++;
            }
        }
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        mdnie_reset();
        String str2 = this.SCENARIO_VALUE;
        if (str2 == null || str2.equals("4") || this.SCENARIO_VALUE.contains("CAMERA_APP") || this.isLockScreenOn) {
            return;
        }
        Slog.v("MdnieScenarioControlService", "mAclOffEnabled : " + this.mAclOffEnabled);
        if (this.mAclOffEnabled) {
            setAclModeScenario(false, 1);
        }
        setMdnieScenarioMode(4);
        if (this.DOU_BRIGHTNESS_STANDARD_VALUE != 255 && this.mDayOfUseSupportAppState && !this.mAutoBrightnessMode && !this.mHighBrightnessModeEnabled) {
            setBrightnessScaleFactor(6);
        } else {
            setBrightnessScaleFactor(0);
        }
        setAudioHDR(false);
        setVideoAppLaunch(false);
        setCameraAppLaunch(false);
        this.mVideoEnhacnerEnabled = false;
        this.mGalleryAppState = false;
        this.mCameraAppState = false;
        this.mSamsungVideoAppState = false;
        this.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setSnsMode(Same Camera Mode) from Sns function");
    }

    public final void mdnie_reset() {
        this.mIsFromCamera = false;
        this.mUIScenarioEnabled = false;
        this.mGalleryAppLauncher = false;
        this.mSVideoAppLauncher = false;
        this.mDMBScenarioEnabled = false;
        this.mSNSScenarioEnabled = false;
        this.mEmailScenarioEnabled = false;
        this.mVideoScenarioEnabled = false;
        this.mEbookScenarioEnabled = false;
        this.mGalleryScenarioEnabled = false;
        this.mSVideoScenarioEnabled = false;
        this.mCameraScenarioEnabled = false;
        this.mBrowserScenarioEnabled = false;
        this.mGalleryOptionScenarioEnabled = false;
        this.mSVideoOptionScenarioEnabled = false;
    }

    public final void scenario_enable_reset() {
        this.mUIScenarioEnabled = false;
        this.mDMBScenarioEnabled = false;
        this.mSNSScenarioEnabled = false;
        this.mEmailScenarioEnabled = false;
        this.mVideoScenarioEnabled = false;
        this.mEbookScenarioEnabled = false;
        this.mGalleryScenarioEnabled = false;
        this.mSVideoScenarioEnabled = false;
        this.mCameraScenarioEnabled = false;
        this.mBrowserScenarioEnabled = false;
        this.mGalleryOptionScenarioEnabled = false;
        this.mSVideoOptionScenarioEnabled = false;
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:30:0x007f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:36:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r5v4, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final String getStringFromFile(String str) {
        ?? r0;
        FileInputStream fileInputStream;
        byte[] bArr = new byte[128];
        File file = new File(str);
        int i = 0;
        for (int i2 = 0; i2 < 128; i2++) {
            bArr[i2] = 0;
        }
        FileInputStream fileInputStream2 = null;
        if (!file.exists()) {
            return null;
        }
        try {
            if (str != null) {
                try {
                    fileInputStream = new FileInputStream(file);
                } catch (Exception e) {
                    e = e;
                    r0 = 0;
                    Slog.e("MdnieScenarioControlService", "Exception : " + e + " , in : " + fileInputStream2 + " , value : " + r0 + " , length : " + i);
                    e.printStackTrace();
                    if (fileInputStream2 != null) {
                        return r0;
                    }
                    try {
                        fileInputStream2.close();
                        return r0;
                    } catch (Exception unused) {
                        Slog.e("MdnieScenarioControlService", "File Close error");
                        return r0;
                    }
                }
            } else {
                fileInputStream = null;
            }
            if (fileInputStream != null) {
                try {
                    try {
                        int read = fileInputStream.read(bArr);
                        if (read > 0) {
                            try {
                                fileInputStream2 = new String(bArr, 0, read - 1, StandardCharsets.UTF_8);
                            } catch (Exception e2) {
                                e = e2;
                                i = read;
                                FileInputStream fileInputStream3 = fileInputStream2;
                                fileInputStream2 = fileInputStream;
                                e = e;
                                r0 = fileInputStream3;
                                Slog.e("MdnieScenarioControlService", "Exception : " + e + " , in : " + fileInputStream2 + " , value : " + r0 + " , length : " + i);
                                e.printStackTrace();
                                if (fileInputStream2 != null) {
                                }
                            }
                        }
                        fileInputStream.close();
                    } catch (Throwable th) {
                        fileInputStream2 = fileInputStream;
                        th = th;
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (Exception unused2) {
                                Slog.e("MdnieScenarioControlService", "File Close error");
                            }
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e = e3;
                }
            }
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (Exception unused3) {
                    Slog.e("MdnieScenarioControlService", "File Close error");
                }
            }
            return fileInputStream2;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public final void setAclModeScenario(boolean z, int i) {
        this.mCurAclValue = i;
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            boolean z2 = this.mAclDimmingFunction;
            if (!z2) {
                semDisplaySolutionManager.onAutoCurrentLimitStateChanged(z);
                return;
            }
            if (z2) {
                boolean z3 = this.mAutoBrightnessMode;
                if (!z3) {
                    if (z3) {
                        return;
                    }
                    if (i == 1) {
                        i = 3;
                    }
                    Slog.v("MdnieScenarioControlService", "setAclModeScenario() ACL mPrevAclValue (" + this.mPrevAclValue + ") - mCurrentValue (" + this.mCurAclValue + ")");
                    this.mSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(i);
                    this.mPrevAclValue = this.mCurAclValue;
                    return;
                }
                if (this.mAntiGlareEnable) {
                    if (i == 0) {
                        i = 2;
                    } else if (i == 1) {
                        i = 5;
                    }
                }
                Slog.v("MdnieScenarioControlService", "setAclModeScenario() ACL mPrevAclValue (" + this.mPrevAclValue + ") - mCurrentValue (" + this.mCurAclValue + ")");
                this.mSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(i);
                this.mPrevAclValue = this.mCurAclValue;
            }
        }
    }

    public final void setAclModeSettings() {
        if (this.mSemDisplaySolutionManager == null || !this.mAutoBrightnessMode) {
            return;
        }
        if (this.mAntiGlareEnable) {
            int i = this.mPrevAclValue;
            if (i == 0) {
                this.mCurAclValue = 2;
            } else if (i == 1 || i == 3) {
                this.mCurAclValue = 5;
            }
        } else {
            int i2 = this.mPrevAclValue;
            if (i2 == 2) {
                this.mCurAclValue = 0;
            } else if (i2 == 5 || i2 == 4) {
                this.mCurAclValue = 1;
            }
        }
        Slog.v("MdnieScenarioControlService", "setAclModeSettings() ACL mPrevAclValue (" + this.mPrevAclValue + ") - mCurrentValue (" + this.mCurAclValue + ")");
        this.mSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(this.mCurAclValue);
        this.mPrevAclValue = this.mCurAclValue;
    }

    public final void setMdnieScenarioMode(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        SemMdnieManager semMdnieManager = this.mMdnieManager;
        if (semMdnieManager != null) {
            semMdnieManager.setContentMode(i);
            Slog.v("MdnieScenarioControlService", "Calling MdnieManager API(setContentMode(" + i + "))");
            if (i == 8) {
                this.mBrowserAppLauncher = true;
                Slog.d("MdnieScenarioControlService", "Start Browser Brightness Decrease Timer");
                this.mHandler.removeMessages(17);
                this.mHandler.sendEmptyMessageAtTime(17, uptimeMillis + this.ACTION_BROWSER_BRIGHTNESS_DECREASE_FIRST_MILLIS);
                return;
            }
            this.mBrowserAppLauncher = false;
            boolean z = this.mBrowserBrightnessDecreaseEnabled;
            if (z) {
                this.mHandler.removeMessages(17);
                this.mHandler.removeMessages(18);
                this.mHandler.sendEmptyMessage(18);
            } else {
                if (z) {
                    return;
                }
                this.mHandler.removeMessages(17);
            }
        }
    }

    public final void setBrightnessScaleFactor(int i) {
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            if (i == 0) {
                this.mUseScaleFactorState = false;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_off");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 1 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_1");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 2 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_2");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 3 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_3");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 4 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_4");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 5 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_5");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 6 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                if (this.DOU_BRIGHTNESS_STANDARD_VALUE == 255) {
                    semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_off");
                    Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                    return;
                }
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_6");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i != 7 || this.isLockScreenOn) {
                return;
            }
            this.mUseScaleFactorState = true;
            semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_7");
            Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
        }
    }

    public final void setEyeComfortScaleFactor(float f) {
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setEyeComfortWeightingFactor(f);
        }
    }

    public final void setAudioHDR(boolean z) {
        AudioManagerHelper.semSetAudioHDR(z);
    }

    public final void setVideoAppLaunch(boolean z) {
        if (this.mSemDisplaySolutionManager == null) {
            this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        }
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setVideoModeEnable(z);
        }
    }

    public final void setCameraAppLaunch(boolean z) {
        if (this.mSemDisplaySolutionManager == null) {
            this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        }
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setCameraModeEnable(z);
        }
    }

    public final void setDouAppLaunch(boolean z) {
        if (this.mSemDisplaySolutionManager == null) {
            this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        }
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setDouAppModeEnable(z);
        }
    }

    public final void getSystemServices() {
        this.mActivityManager = (ActivityManager) this.mContext.getSystemService("activity");
        Slog.v("MdnieScenarioControlService", "mActivityManager : " + this.mActivityManager);
        this.mMdnieManager = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
        Slog.v("MdnieScenarioControlService", "mMdnieManager : " + this.mMdnieManager);
        this.mInputManager = (InputManager) this.mContext.getSystemService("input");
        Slog.v("MdnieScenarioControlService", "mInputManager : " + this.mInputManager);
        this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        Slog.v("MdnieScenarioControlService", "mSemDisplaySolutionManager : " + this.mSemDisplaySolutionManager);
        this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
        Slog.v("MdnieScenarioControlService", "mDisplayManager : " + this.mDisplayManager);
        if (this.mActivityManager == null || this.mMdnieManager == null || this.mInputManager == null || this.mSemDisplaySolutionManager == null || this.mDisplayManager == null) {
            long uptimeMillis = SystemClock.uptimeMillis();
            this.mHandler.removeMessages(15);
            this.mHandler.sendEmptyMessageAtTime(15, uptimeMillis + this.GET_SYSTEM_SERVICES_MILLIS);
            Slog.v("MdnieScenarioControlService", "Failure to register all of the services system.");
            return;
        }
        registerProcessObserver();
        this.mWorkingCondition = true;
        Slog.v("MdnieScenarioControlService", "Success to register all of the services system.");
    }

    public void browser_brightness_decrease_mode(boolean z) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (!this.mBrowserBrightnessDecreaseEnabled) {
            this.mMediaResourceUsed = getting_media_resource_info();
            this.mBrowserBrightnessDefault = getting_platform_brightness_value();
            InputManager inputManager = this.mInputManager;
            if (inputManager != null) {
                this.mLastUserInputDuration = inputManager.semGetMotionIdleTimeMillis();
            }
        }
        Slog.v("MdnieScenarioControlService", "browser_brightness_decrease_mode(), mBrowserBrightnessDecreaseEnabled : " + this.mBrowserBrightnessDecreaseEnabled + " , enabled : " + z + " , mScreenStateOn : " + this.mScreenStateOn + " , mBrowserAppLauncher : " + this.mBrowserAppLauncher + " , mScreenOffTomeoutAbnormal : " + this.mScreenOffTomeoutAbnormal + " , mUserActivityStatus : " + this.mUserActivityStatus + " , mMediaResourceUsed : " + this.mMediaResourceUsed + ", mLastUserInputDuration : " + this.mLastUserInputDuration + " , mBrowserBrightnessDefault : " + this.mBrowserBrightnessDefault + " , mBrowserBrightnessArray : " + this.mBrowserBrightnessArray[0]);
        if (z && this.mScreenStateOn && this.mBrowserAppLauncher && (this.mScreenOffTomeoutAbnormal || this.mUserActivityStatus == 3)) {
            boolean z2 = this.mMediaResourceUsed;
            if (z2 || this.mLastUserInputDuration <= 600000) {
                if (z2 || this.mLastUserInputDuration <= 600000) {
                    this.mHandler.removeMessages(17);
                    this.mHandler.sendEmptyMessageAtTime(17, uptimeMillis + this.ACTION_BROWSER_BRIGHTNESS_DECREASE_FIRST_MILLIS);
                    return;
                }
                return;
            }
            int i = this.mBrowserBrightnessDefault;
            int[] iArr = this.mBrowserBrightnessArray;
            if (i >= iArr[0]) {
                this.mBrowserBrightnessDecreaseEnabled = true;
                browser_brightness_decrease_function(iArr[1]);
                return;
            }
            return;
        }
        this.mBrowserBrightnessDecreaseEnabled = false;
        this.BRIGHTNESS_DECREASE_STEP = 1;
        Slog.v("MdnieScenarioControlService", "Disabled Browser Brightness Decrease Mode");
        this.mHandler.removeMessages(17);
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setMultipleScreenBrightness("browser_brightness_off");
        }
    }

    public final void browser_brightness_decrease_function(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        if (this.mSemDisplaySolutionManager != null) {
            for (int i2 = 1; i2 < i + 1; i2++) {
                if (this.BRIGHTNESS_DECREASE_STEP == i2) {
                    Slog.v("MdnieScenarioControlService", "Enabled Browser Brightness Decrease Mode " + i2 + " , MAX  : " + i);
                    SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
                    StringBuilder sb = new StringBuilder();
                    sb.append("browser_brightness_on_");
                    sb.append(i2);
                    semDisplaySolutionManager.setMultipleScreenBrightness(sb.toString());
                    if (i2 < i) {
                        this.mHandler.removeMessages(17);
                        this.mHandler.sendEmptyMessageAtTime(17, uptimeMillis + this.ACTION_BROWSER_BRIGHTNESS_DECREASE_MILLIS);
                        this.BRIGHTNESS_DECREASE_STEP = i2 + 1;
                        return;
                    }
                    this.BRIGHTNESS_DECREASE_STEP = 1;
                    return;
                }
            }
        }
    }

    public final void getCurrentOpr() {
        int i;
        if (new File(this.ON_PIXEL_RATIO_PATH).exists()) {
            i = 0;
            try {
                String stringFromFile = getStringFromFile(this.ON_PIXEL_RATIO_PATH);
                if (stringFromFile != null) {
                    String[] split = stringFromFile.trim().split(",");
                    try {
                        if (split.length == 12) {
                            int i2 = 0;
                            for (int i3 = 3; i3 < 6; i3++) {
                                i2 += Integer.parseInt(split[i3]);
                            }
                            if (i2 > 0) {
                                i = i2 / 3;
                            }
                        }
                    } catch (NumberFormatException e) {
                        Slog.e("MdnieScenarioControlService", "NumberFormatException : " + e);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } else {
            i = -1;
        }
        Slog.d("MdnieScenarioControlService", "currentOpr : " + String.valueOf(i) + ", mQuickPanelState : " + String.valueOf(this.mQuickPanelState));
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setOnPixelRatioValueForPMS(String.valueOf(i) + "," + String.valueOf(this.mQuickPanelState));
        }
    }

    public void isHighDynamicRangeModeState(boolean z) {
        this.mHighDynamicRangeEnabled = z;
        Slog.d("MdnieScenarioControlService", "isHighDynamicRangeModeState : " + this.mHighDynamicRangeEnabled);
        if (this.mHighDynamicRangeEnabled) {
            setAclModeScenario(true, 0);
        } else {
            setAclModeScenario(false, 1);
        }
    }

    public final void anti_glare_state() {
        try {
            this.LUX_VALUE = getStringFromFile(this.READING_LUX_PATH);
            String stringFromFile = getStringFromFile(this.READING_LUX_SUB_PATH);
            this.SUB_LUX_VALUE = stringFromFile;
            boolean z = this.mIsFolded;
            if (z) {
                if (z && stringFromFile != null) {
                    Slog.d("MdnieScenarioControlService", "anti_glare_state SUB_LUX_VALUE : " + this.SUB_LUX_VALUE);
                    if (Integer.parseInt(this.SUB_LUX_VALUE) >= 0 && 10 > Integer.parseInt(this.SUB_LUX_VALUE)) {
                        if (this.mBlueLightFilterModeEnabled && this.mGlareReductionSupported) {
                            boolean z2 = this.mBlueLightFilterAdaptiveModeEnabled;
                            if (z2) {
                                this.mAntiGlareEnable = true;
                            } else if (!z2) {
                                boolean z3 = this.mBlueLightFilterCustomAlwaysOn;
                                if (z3) {
                                    if (this.mBlueLightFilterAntiGlareEnabled) {
                                        this.mAntiGlareEnable = true;
                                    } else {
                                        this.mAntiGlareEnable = false;
                                    }
                                } else if (!z3) {
                                    if (this.mSemDisplaySolutionManager.isBlueLightFilterScheduledTime() && this.mBlueLightFilterAntiGlareEnabled) {
                                        this.mAntiGlareEnable = true;
                                    } else {
                                        this.mAntiGlareEnable = false;
                                    }
                                }
                            }
                        } else {
                            this.mAntiGlareEnable = false;
                        }
                    } else if (10 <= Integer.parseInt(this.SUB_LUX_VALUE)) {
                        this.mAntiGlareEnable = false;
                    }
                }
            } else if (this.LUX_VALUE != null) {
                Slog.d("MdnieScenarioControlService", "anti_glare_state LUX_VALUE : " + this.LUX_VALUE);
                if (Integer.parseInt(this.LUX_VALUE) >= 0 && 10 > Integer.parseInt(this.LUX_VALUE)) {
                    if (this.mBlueLightFilterModeEnabled && this.mGlareReductionSupported) {
                        boolean z4 = this.mBlueLightFilterAdaptiveModeEnabled;
                        if (z4) {
                            this.mAntiGlareEnable = true;
                        } else if (!z4) {
                            boolean z5 = this.mBlueLightFilterCustomAlwaysOn;
                            if (z5) {
                                if (this.mBlueLightFilterAntiGlareEnabled) {
                                    this.mAntiGlareEnable = true;
                                } else {
                                    this.mAntiGlareEnable = false;
                                }
                            } else if (!z5) {
                                if (this.mSemDisplaySolutionManager.isBlueLightFilterScheduledTime() && this.mBlueLightFilterAntiGlareEnabled) {
                                    this.mAntiGlareEnable = true;
                                } else {
                                    this.mAntiGlareEnable = false;
                                }
                            }
                        }
                    } else {
                        this.mAntiGlareEnable = false;
                    }
                } else if (10 <= Integer.parseInt(this.LUX_VALUE)) {
                    this.mAntiGlareEnable = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void registerProcessObserver() {
        try {
            ActivityManagerNative.getDefault().registerProcessObserver(this.mProcessObserver);
            if (this.mDesktopModeManager == null) {
                this.mDesktopModeManager = (SemDesktopModeManager) this.mContext.getSystemService("desktopmode");
                Slog.v("MdnieScenarioControlService", "mDesktopModeManager : " + this.mDesktopModeManager);
            }
            if (this.mDesktopModeManager != null) {
                SemDesktopModeManager.registerListener(this.eventListener);
            }
            DisplayManager displayManager = this.mDisplayManager;
            if (displayManager != null) {
                displayManager.registerDisplayListener(this.mDisplayListener, this.mHandler, 8L);
            }
            setting_is_changed();
        } catch (Exception unused) {
            Slog.d("MdnieScenarioControlService", "failed to registerProcessObserver");
        }
    }

    public int findVideoEnhancerSettingState(String str) {
        return getAppSettingState(str);
    }

    public void updateVideoEnhancerSettingState(String str, int i) {
        Slog.d("MdnieScenarioControlService", "Update Video Enhancer SubKey state. package : " + str + " , state : " + i);
        setAppSettingState(str, i);
    }

    public final void writeVideoEnhancerListInDataBase() {
        int i = 0;
        int i2 = 0;
        while (true) {
            String[] strArr = this.VIDEO_APP_LAUNCHER;
            if (i2 >= strArr.length) {
                break;
            }
            insertDataUsage(strArr[i2]);
            i2++;
        }
        int i3 = 0;
        while (true) {
            String[] strArr2 = this.SVIDEO_APP_LAUNCHER;
            if (i3 >= strArr2.length) {
                break;
            }
            insertDataUsage(strArr2[i3]);
            i3++;
        }
        while (true) {
            String[] strArr3 = this.DMB_APP_LAUNCHER;
            if (i >= strArr3.length) {
                return;
            }
            insertDataUsage(strArr3[i]);
            i++;
        }
    }

    public final void writeVideoEnhancerListInDataBase2() {
        try {
            ArrayList arrayList = new ArrayList();
            Collections.addAll(arrayList, this.VIDEO_APP_LAUNCHER);
            Collections.addAll(arrayList, this.SVIDEO_APP_LAUNCHER);
            Collections.addAll(arrayList, this.DMB_APP_LAUNCHER);
            HashMap appListRegistState = getAppListRegistState(arrayList);
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                if (appListRegistState.get(arrayList.get(i)) == null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("packagename", (String) arrayList.get(i));
                    contentValues.put("settingstate", (Integer) 1);
                    arrayList2.add(contentValues);
                }
            }
            this.mDBHelper.bulkInsert(this.mDisplaySolutionDataBase, arrayList2, "MSCS_APP_LIST");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX WARN: Finally extract failed */
    public final HashMap getAppListRegistState(List list) {
        HashMap hashMap = new HashMap();
        try {
            Cursor cursor = null;
            try {
                cursor = this.mDBHelper.query(this.mDisplaySolutionDataBase, new String[]{"packagename"}, "packagename IN('" + TextUtils.join("','", list) + "')", null, null, "MSCS_APP_LIST");
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        hashMap.put(cursor.getString(0), Boolean.TRUE);
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public final void insertDataUsage(String str) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("packagename", str);
        contentValues.put("settingstate", (Integer) 1);
        if (getAppListRegistState(str) == 0) {
            this.mDBHelper.insert(this.mDisplaySolutionDataBase, contentValues, "MSCS_APP_LIST");
        }
    }

    public final int getAppListRegistState(String str) {
        Cursor cursor = null;
        try {
            cursor = this.mDBHelper.query(this.mDisplaySolutionDataBase, null, "packagename = '" + str + "' ", null, null, "MSCS_APP_LIST");
            return cursor != null ? cursor.getCount() : 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public final void setAppSettingState(String str, int i) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("settingstate", Integer.valueOf(i));
        this.mDBHelper.update(this.mDisplaySolutionDataBase, contentValues, "packagename = '" + str + "' ", null, "MSCS_APP_LIST");
    }

    public final int getAppSettingState(String str) {
        Cursor cursor = null;
        try {
            cursor = this.mDBHelper.query(this.mDisplaySolutionDataBase, null, "packagename = '" + str + "' ", null, null, "MSCS_APP_LIST");
            if (cursor != null && cursor.moveToFirst()) {
                this.mAppLaunchStateInDatabase = cursor.getInt(cursor.getColumnIndex("settingstate"));
            }
            return this.mAppLaunchStateInDatabase;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
