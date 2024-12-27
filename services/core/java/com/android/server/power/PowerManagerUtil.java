package com.android.server.power;

import android.R;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Process;
import android.os.SystemClock;
import android.os.SystemProperties;

import com.android.internal.display.BrightnessSynchronizer;
import com.android.internal.util.RingBuffer;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.aod.AODConfig;

import com.samsung.android.displayquality.SemDisplayQualityFeature;
import com.samsung.android.feature.SemFloatingFeature;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class PowerManagerUtil {
    public static final int AUTO_BRIGHTNESS_TYPE;
    public static final int BRIGHTNESS_ANIMATION_MIN_LIMIT_HZ;
    public static final int HBM_LUX;
    public static final boolean SECURITY_FINGERPRINT_IN_DISPLAY;
    public static final boolean SEC_FEATURE_AOD_BRIGHTNESS_ANIM;
    public static final boolean SEC_FEATURE_AOD_DISABLE_CLOCK_TRANSITION;
    public static final boolean SEC_FEATURE_AOD_LOOK_CHARGING_UI;
    public static final boolean SEC_FEATURE_AOD_LOOK_CHARGING_UI_ON_SUB_DISPLAY;
    public static final boolean SEC_FEATURE_BATTERY_FULL_CAPACITY;
    public static final boolean SEC_FEATURE_BATTERY_LIFE_EXTENDER;
    public static final boolean SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE;
    public static final boolean SEC_FEATURE_DEX_DUAL_VIEW;
    public static final boolean SEC_FEATURE_DISPLAY_QUALITY;
    public static final boolean SEC_FEATURE_DUAL_DISPLAY;
    public static final boolean SEC_FEATURE_ENABLE_MTK_POWER_THROTTLING;
    public static final boolean SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING;
    public static final boolean SEC_FEATURE_FLIP_COVER_DISPLAY;
    public static final boolean SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY;
    public static final boolean SEC_FEATURE_FOLD_COVER_DISPLAY;
    public static final boolean SEC_FEATURE_FULLSCREEN_AOD;
    public static final boolean SEC_FEATURE_HQM_SEND_DPUC;
    public static final boolean SEC_FEATURE_HQM_SEND_LBHD_HIGHEST;
    public static final boolean SEC_FEATURE_LCD_SUPPORT_PASSIVE_MODE;
    public static final boolean SEC_FEATURE_PAPAYA_DQE;
    public static final boolean SEC_FEATURE_SCREEN_CURTAIN;
    public static final boolean SEC_FEATURE_SEAMLESS_AOD;
    public static final boolean SEC_FEATURE_SUPPORT_AOD;
    public static final boolean SEC_FEATURE_SUPPORT_AOD_LIVE_CLOCK;
    public static final boolean SEC_FEATURE_SUPPORT_HBM;
    public static final boolean SEC_FEATURE_SUPPORT_LEGACY_MISC_POWER_HAL;
    public static final boolean SEC_FEATURE_SUPPORT_LEGACY_PERFORMANCE_MODE;
    public static final boolean SEC_FEATURE_SUPPORT_WIRELESS_NIGHT_MODE;
    public static final boolean SEC_FEATURE_TABLET;
    public static final boolean SEC_FEATURE_USE_AFC;
    public static final boolean SEC_FEATURE_USE_GED_DOZE;
    public static final boolean SEC_FEATURE_USE_LIGHTS_HAL_EXTENSION;
    public static final boolean SEC_FEATURE_USE_PMS_LOG;
    public static final boolean SEC_FEATURE_USE_SFC;
    public static final boolean SEC_FEATURE_USE_WIRELESS_AFC;
    public static final boolean SEC_FEATURE_USE_WIRELESS_POWER_SHARING;
    public static final boolean SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED;
    public static final boolean SEC_FEATURE_WA_WAITING_AOD_WHEN_WAKINGUP_FROM_DOZE;
    public static final boolean SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PASS_THROUGH;
    public static final boolean SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI;
    public static final boolean USE_PERMISSIBLE_RATIO_FOR_LONGTERM_MODEL;
    public static final boolean USE_PERSONAL_AUTO_BRIGHTNESS_V3;
    public static final boolean USE_PERSONAL_AUTO_BRIGHTNESS_V4;
    public static boolean USE_SEC_LONG_TERM_MODEL;
    public static final ScreenOffProfiler sCurrentScreenOffProfiler;
    public static final ScreenOnProfiler sCurrentScreenOnProfiler;
    public static final RingBuffer sScreenOffProfilers;
    public static final RingBuffer sScreenOnProfilers;

    public final class ScreenOffProfiler {
        public int mCfAnimationDuration;
        public long mCfAnimationStartTime;
        public int mCfPrepareDuration;
        public long mCfPrepareStartTime;
        public int mDisplayDuration;
        public long mDisplayStartTime;
        public boolean mDone;
        public int mGoToSleepDiff;
        public int mGoToSleepDuration;
        public long mGoToSleepStartTime;
        public String mGoToSleepTimeStr;
        public boolean mNeedSkip;
        public int mOrder;
        public boolean mSaved;

        public final void clearAll() {
            this.mOrder = 0;
            this.mDone = false;
            this.mGoToSleepDiff = 0;
            this.mGoToSleepTimeStr = "";
            this.mNeedSkip = false;
            this.mGoToSleepStartTime = 0L;
            this.mGoToSleepDuration = 0;
            this.mCfPrepareStartTime = 0L;
            this.mCfPrepareDuration = 0;
            this.mCfAnimationStartTime = 0L;
            this.mCfAnimationDuration = 0;
            this.mDisplayStartTime = 0L;
            this.mDisplayDuration = 0;
            this.mSaved = false;
        }

        public final void noteDisplayEnd() {
            this.mDisplayDuration = (int) (SystemClock.uptimeMillis() - this.mDisplayStartTime);
            if (!this.mDone) {
                this.mDone = true;
                this.mGoToSleepDuration =
                        (int)
                                ((SystemClock.uptimeMillis() - this.mGoToSleepStartTime)
                                        + this.mGoToSleepDiff);
            }
            if (this.mNeedSkip) {
                return;
            }
            ScreenOffProfiler screenOffProfiler = PowerManagerUtil.sCurrentScreenOffProfiler;
            if (screenOffProfiler.mGoToSleepStartTime == 0 || screenOffProfiler.mSaved) {
                return;
            }
            screenOffProfiler.mSaved = true;
            Slog.d("PowerManagerUtil", screenOffProfiler.toString(false));
            RingBuffer ringBuffer = PowerManagerUtil.sScreenOffProfilers;
            ScreenOffProfiler screenOffProfiler2 = new ScreenOffProfiler();
            screenOffProfiler2.mNeedSkip = false;
            screenOffProfiler2.mOrder = screenOffProfiler.mOrder;
            screenOffProfiler2.mDone = screenOffProfiler.mDone;
            screenOffProfiler2.mGoToSleepDiff = screenOffProfiler.mGoToSleepDiff;
            screenOffProfiler2.mGoToSleepTimeStr = screenOffProfiler.mGoToSleepTimeStr;
            screenOffProfiler2.mNeedSkip = screenOffProfiler.mNeedSkip;
            screenOffProfiler2.mGoToSleepStartTime = screenOffProfiler.mGoToSleepStartTime;
            screenOffProfiler2.mGoToSleepDuration = screenOffProfiler.mGoToSleepDuration;
            screenOffProfiler2.mCfPrepareStartTime = screenOffProfiler.mCfPrepareStartTime;
            screenOffProfiler2.mCfPrepareDuration = screenOffProfiler.mCfPrepareDuration;
            screenOffProfiler2.mCfAnimationStartTime = screenOffProfiler.mCfAnimationStartTime;
            screenOffProfiler2.mCfAnimationDuration = screenOffProfiler.mCfAnimationDuration;
            screenOffProfiler2.mDisplayStartTime = screenOffProfiler.mDisplayStartTime;
            screenOffProfiler2.mDisplayDuration = screenOffProfiler.mDisplayDuration;
            screenOffProfiler2.mSaved = screenOffProfiler.mSaved;
            ringBuffer.append(screenOffProfiler2);
        }

        public final void setSkip() {
            this.mNeedSkip = true;
        }

        public final String toString(boolean z) {
            StringBuilder sb = new StringBuilder();
            if (z) {
                sb.append(
                        String.format(
                                "[OFF][%3d][%s][T:%4d]  [Caller:%2d] [Cfp:%3d] [Cfa:%2d]"
                                    + " [Panel:%3d]",
                                Integer.valueOf(this.mOrder),
                                this.mGoToSleepTimeStr,
                                Integer.valueOf(this.mGoToSleepDuration),
                                Integer.valueOf(this.mGoToSleepDiff),
                                Integer.valueOf(this.mCfPrepareDuration),
                                Integer.valueOf(this.mCfAnimationDuration),
                                Integer.valueOf(this.mDisplayDuration)));
            } else {
                sb.append(
                        String.format(
                                "[OFF][%3d][T:%4d]  [Caller:%2d] [Cfp:%3d] [Cfa:%2d] [Panel:%3d]",
                                Integer.valueOf(this.mOrder),
                                Integer.valueOf(this.mGoToSleepDuration),
                                Integer.valueOf(this.mGoToSleepDiff),
                                Integer.valueOf(this.mCfPrepareDuration),
                                Integer.valueOf(this.mCfAnimationDuration),
                                Integer.valueOf(this.mDisplayDuration)));
            }
            return sb.toString();
        }
    }

    public final class ScreenOnProfiler {
        public static boolean CHECK_FRAME = true;
        public static int mDisplayType = 1;
        public static int mFrameCheckCnt = 0;
        public static boolean mFramePass = false;
        public static long mFramePrevTime = -1;
        public static Handler mHandler;
        public static HandlerThread mHandlerThread;
        public static boolean sInitialized;
        public int mDisplayDuration;
        public long mDisplayStartTime;
        public final AnonymousClass1 mFrameCheckerRunnable =
                new Runnable() { // from class:
                                 // com.android.server.power.PowerManagerUtil.ScreenOnProfiler.1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ScreenOnProfiler.this.getClass();
                        long frameTimeFromDriver = ScreenOnProfiler.getFrameTimeFromDriver();
                        if (frameTimeFromDriver != ScreenOnProfiler.mFramePrevTime) {
                            ScreenOnProfiler screenOnProfiler = ScreenOnProfiler.this;
                            screenOnProfiler.mFrameEndTime = frameTimeFromDriver;
                            screenOnProfiler.noteFrameEnd();
                            return;
                        }
                        int i = ScreenOnProfiler.mFrameCheckCnt;
                        ScreenOnProfiler.mFrameCheckCnt = i + 1;
                        if (i < 3) {
                            ScreenOnProfiler.mHandler.postDelayed(this, 100L);
                            return;
                        }
                        ScreenOnProfiler.this.mFrameEndTime = SystemClock.uptimeMillis();
                        Slog.d("PowerManagerUtil", "Frame Timeout !!! ");
                        ScreenOnProfiler.this.noteFrameEnd();
                    }
                };
        public boolean mFrameDone;
        public int mFrameDuration;
        public long mFrameEndTime;
        public long mFrameStartTime;
        public int mListenerDuration;
        public long mListenerStartTime;
        public int mOrder;
        public boolean mSaved;
        public int mWakeUpDiff;
        public int mWakeUpDuration;
        public long mWakeUpEndTime;
        public long mWakeUpStartTime;
        public String mWakeUpTimeStr;
        public boolean mWmsDone;
        public int mWmsDuration;
        public long mWmsStartTime;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.power.PowerManagerUtil$ScreenOnProfiler$1] */
        public ScreenOnProfiler() {
            if (!sInitialized) {
                HandlerThread handlerThread = new HandlerThread("PmsFrameChecker");
                mHandlerThread = handlerThread;
                handlerThread.start();
                mHandler = new Handler(mHandlerThread.getLooper());
                sInitialized = true;
            }
            clearAll();
        }

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.power.PowerManagerUtil$ScreenOnProfiler$1] */
        public ScreenOnProfiler(ScreenOnProfiler screenOnProfiler) {
            this.mOrder = screenOnProfiler.mOrder;
            this.mWakeUpDiff = screenOnProfiler.mWakeUpDiff;
            this.mWakeUpTimeStr = screenOnProfiler.mWakeUpTimeStr;
            this.mWakeUpStartTime = screenOnProfiler.mWakeUpStartTime;
            this.mWakeUpEndTime = screenOnProfiler.mWakeUpEndTime;
            this.mWakeUpDuration = screenOnProfiler.mWakeUpDuration;
            this.mWmsStartTime = screenOnProfiler.mWmsStartTime;
            this.mWmsDuration = screenOnProfiler.mWmsDuration;
            this.mWmsDone = screenOnProfiler.mWmsDone;
            this.mDisplayStartTime = screenOnProfiler.mDisplayStartTime;
            this.mDisplayDuration = screenOnProfiler.mDisplayDuration;
            this.mListenerStartTime = screenOnProfiler.mListenerStartTime;
            this.mListenerDuration = screenOnProfiler.mListenerDuration;
            this.mFrameStartTime = screenOnProfiler.mFrameStartTime;
            this.mFrameEndTime = screenOnProfiler.mFrameEndTime;
            this.mFrameDuration = screenOnProfiler.mFrameDuration;
            this.mFrameDone = screenOnProfiler.mFrameDone;
            this.mSaved = screenOnProfiler.mSaved;
        }

        public static long getFrameTimeFromDriver() {
            String readFromFile;
            int i = mDisplayType;
            if (i == 1) {
                readFromFile = PowerManagerUtil.readFromFile("/sys/class/lcd/panel/display_on");
            } else if (i != 2) {
                BatteryService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Display Type err = "), mDisplayType, "PowerManagerUtil");
                readFromFile = "";
            } else {
                readFromFile = PowerManagerUtil.readFromFile("/sys/class/lcd/panel1/display_on");
            }
            if (readFromFile == null) {
                Slog.d(
                        "PowerManagerUtil",
                        "null : /sys/class/lcd/panel/display_on or"
                            + " /sys/class/lcd/panel1/display_on");
            } else {
                try {
                    return Long.parseLong(readFromFile);
                } catch (NumberFormatException unused) {
                    Slog.d(
                            "PowerManagerUtil",
                            "/sys/class/lcd/panel/display_on or /sys/class/lcd/panel1/display_on data is "
                                    .concat(readFromFile));
                }
            }
            return -1L;
        }

        public final void clearAll() {
            this.mOrder = 0;
            this.mWakeUpDiff = 0;
            this.mWakeUpTimeStr = "";
            this.mWakeUpStartTime = 0L;
            this.mWakeUpEndTime = 0L;
            this.mWakeUpDuration = 0;
            this.mWmsStartTime = 0L;
            this.mWmsDuration = 0;
            this.mWmsDone = false;
            this.mDisplayStartTime = 0L;
            this.mDisplayDuration = 0;
            this.mListenerStartTime = 0L;
            this.mListenerDuration = 0;
            this.mFrameStartTime = 0L;
            this.mFrameEndTime = 0L;
            this.mFrameDuration = 0;
            mFramePass = false;
            this.mFrameDone = false;
            this.mSaved = false;
        }

        public final void noteFrameEnd() {
            mHandler.removeCallbacks(this.mFrameCheckerRunnable);
            mFrameCheckCnt = 0;
            this.mFrameDone = true;
            this.mFrameDuration = (int) (this.mFrameEndTime - this.mFrameStartTime);
            if (this.mWmsDone || this.mWmsStartTime == 0) {
                noteWakeupEnd(true);
            }
        }

        public final void noteFrameStart(int i) {
            if (CHECK_FRAME && !mFramePass) {
                this.mWakeUpEndTime = SystemClock.uptimeMillis();
                mDisplayType = i;
                this.mFrameStartTime = SystemClock.uptimeMillis();
                mHandler.postDelayed(this.mFrameCheckerRunnable, 100L);
                return;
            }
            this.mFrameDone = true;
            this.mFrameDuration = -1;
            if (this.mWmsDone || this.mWmsStartTime == 0) {
                noteWakeupEnd(false);
            }
        }

        public final void noteWakeupEnd(boolean z) {
            if (z) {
                this.mWakeUpDuration =
                        ((int) ((this.mWakeUpEndTime - this.mWakeUpStartTime) + this.mWakeUpDiff))
                                + this.mFrameDuration;
            } else {
                long uptimeMillis = SystemClock.uptimeMillis();
                this.mWakeUpEndTime = uptimeMillis;
                this.mWakeUpDuration =
                        (int) ((uptimeMillis - this.mWakeUpStartTime) + this.mWakeUpDiff);
            }
            ScreenOnProfiler screenOnProfiler = PowerManagerUtil.sCurrentScreenOnProfiler;
            if (screenOnProfiler.mWakeUpStartTime == 0 || screenOnProfiler.mSaved) {
                return;
            }
            screenOnProfiler.mSaved = true;
            Slog.d("PowerManagerUtil", screenOnProfiler.toString(false));
            PowerManagerUtil.sScreenOnProfilers.append(new ScreenOnProfiler(screenOnProfiler));
        }

        public final String toString(boolean z) {
            StringBuilder sb = new StringBuilder();
            if (z) {
                sb.append(
                        String.format(
                                "[%3d][%s][T:%4d]  [Caller:%2d] [WMS:%3d] [DSL:%2d] [Panel:%3d]"
                                    + " [Frame:%3d]",
                                Integer.valueOf(this.mOrder),
                                this.mWakeUpTimeStr,
                                Integer.valueOf(this.mWakeUpDuration),
                                Integer.valueOf(this.mWakeUpDiff),
                                Integer.valueOf(this.mWmsDuration),
                                Integer.valueOf(this.mListenerDuration),
                                Integer.valueOf(this.mDisplayDuration),
                                Integer.valueOf(this.mFrameDuration)));
            } else {
                sb.append(
                        String.format(
                                "[%3d][T:%4d]  [Caller:%2d] [WMS:%3d] [DSL:%2d] [Panel:%3d]"
                                    + " [Frame:%3d]",
                                Integer.valueOf(this.mOrder),
                                Integer.valueOf(this.mWakeUpDuration),
                                Integer.valueOf(this.mWakeUpDiff),
                                Integer.valueOf(this.mWmsDuration),
                                Integer.valueOf(this.mListenerDuration),
                                Integer.valueOf(this.mDisplayDuration),
                                Integer.valueOf(this.mFrameDuration)));
            }
            return sb.toString();
        }
    }

    public final class StopwatchLogger {
        public long mStartTimeMillis;
    }

    static {
        String trim = SystemProperties.get("ro.product.vendor.device", "NONE").trim();
        boolean z = trim.startsWith("o1") || trim.startsWith("t2") || trim.startsWith("p3");
        boolean z2 = trim.startsWith("r0") || trim.startsWith("g0") || trim.startsWith("b0");
        boolean startsWith = trim.startsWith("a53x");
        trim.startsWith("a23xq");
        boolean z3 = trim.startsWith("dm1") || trim.startsWith("dm2") || trim.startsWith("dm3");
        boolean z4 = trim.startsWith("e1") || trim.startsWith("e2") || trim.startsWith("e3");
        boolean startsWith2 = trim.startsWith("gts10");
        boolean z5 = trim.startsWith("pa1") || trim.startsWith("pa2") || trim.startsWith("pa3");
        int parseInt = Integer.parseInt("5");
        AUTO_BRIGHTNESS_TYPE = parseInt;
        USE_PERSONAL_AUTO_BRIGHTNESS_V3 = parseInt == 4;
        USE_PERSONAL_AUTO_BRIGHTNESS_V4 = parseInt == 5;
        USE_PERMISSIBLE_RATIO_FOR_LONGTERM_MODEL = !z2;
        SEC_FEATURE_USE_PMS_LOG =
                ("Unknown".equalsIgnoreCase(SystemProperties.get("ro.boot.debug_level"))
                                || "0x4f4c"
                                        .equalsIgnoreCase(
                                                SystemProperties.get("ro.boot.debug_level")))
                        ? false
                        : true;
        SEC_FEATURE_SUPPORT_LEGACY_PERFORMANCE_MODE =
                SystemProperties.getInt("ro.product.first_api_level", 0) < 30;
        boolean contains =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                        .contains("aodversion");
        boolean contains2 =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                        .contains("aodversion");
        SEC_FEATURE_USE_GED_DOZE = contains2;
        SEC_FEATURE_SEAMLESS_AOD =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_WALLPAPER_STYLE")
                        .contains("INFINITY");
        SEC_FEATURE_FULLSCREEN_AOD =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_FULLSCREEN", -1)
                        == 1;
        SEC_FEATURE_WAKEUP_WHEN_PLUG_CHANGED = contains2;
        SEC_FEATURE_WA_WAITING_AOD_WHEN_WAKINGUP_FROM_DOZE = contains;
        SEC_FEATURE_AOD_LOOK_CHARGING_UI =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_CLOCKPACK_ITEM")
                        .contains("clockpackversion");
        SEC_FEATURE_AOD_LOOK_CHARGING_UI_ON_SUB_DISPLAY =
                AODConfig.SUPPORT_SUB_DISPLAY_COVER
                        && !AODConfig.SUPPORT_FRONT_SUB_DISPLAY_WATCHFACE;
        SEC_FEATURE_AOD_BRIGHTNESS_ANIM =
                SemFloatingFeature.getInstance()
                                .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_AOD_BRIGHTNESS_ANIMATION")
                        == 1;
        boolean z6 =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_AMOLED_DISPLAY");
        int parseInt2 = Integer.parseInt("3");
        BRIGHTNESS_ANIMATION_MIN_LIMIT_HZ = parseInt2 == 4 ? 120 : 60;
        SEC_FEATURE_LCD_SUPPORT_PASSIVE_MODE = z6 && parseInt2 < 4;
        SEC_FEATURE_USE_AFC =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_HV");
        SEC_FEATURE_USE_SFC =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PD_HV");
        SEC_FEATURE_USE_WIRELESS_AFC =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_HV");
        SEC_FEATURE_USE_WIRELESS_POWER_SHARING =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_TX");
        SEC_FEATURE_BATTERY_LIFE_EXTENDER =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_OPTION");
        SEC_FEATURE_BATTERY_FULL_CAPACITY =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_FORCE_CUTOFF");
        SEC_FEATURE_SUPPORT_WIRELESS_NIGHT_MODE =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_NIGHT_MODE");
        SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PASS_THROUGH =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PASS_THROUGH");
        SEC_FEATURE_DEX_DUAL_VIEW =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP");
        boolean z7 =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
        SEC_FEATURE_FOLD_COVER_DISPLAY = z7;
        boolean z8 =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FLIP");
        SEC_FEATURE_FLIP_COVER_DISPLAY = z8;
        SEC_FEATURE_FLIP_LARGE_COVER_DISPLAY =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN");
        boolean z9 = z7 || z8;
        SEC_FEATURE_DUAL_DISPLAY = z9;
        SEC_FEATURE_HQM_SEND_DPUC = z9 || z || z2 || z3 || z4 || z5;
        SEC_FEATURE_HQM_SEND_LBHD_HIGHEST = z4;
        SECURITY_FINGERPRINT_IN_DISPLAY = true;
        boolean contains3 =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                        .contains("activeclock");
        SEC_FEATURE_SUPPORT_AOD_LIVE_CLOCK = contains3;
        SEC_FEATURE_AOD_DISABLE_CLOCK_TRANSITION =
                (contains3
                                || SemFloatingFeature.getInstance()
                                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                                        .contains("clocktransition"))
                        ? false
                        : true;
        String str = SystemProperties.get("ro.build.characteristics");
        SEC_FEATURE_TABLET = str != null && str.contains("tablet");
        SEC_LIGHT_SENSOR_BLOCKING_PREVENTION_MULTI =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_TOUCH_EVENT_AUTOBRIGHTNESS");
        SEC_FEATURE_ENSURE_TRANSITION_TO_DOZING = startsWith;
        SEC_FEATURE_PAPAYA_DQE =
                Resources.getSystem().getBoolean(R.bool.config_enableIdleScreenBrightnessMode);
        SEC_FEATURE_DISPLAY_QUALITY = SemDisplayQualityFeature.ENABLED;
        String string =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_HOMEHUB");
        if (string != null) {
            string.isEmpty();
        }
        SEC_FEATURE_BATTERY_NOTIFY_SCREEN_STATE = Integer.parseInt("34") >= 33;
        SEC_FEATURE_SUPPORT_HBM =
                Resources.getSystem()
                                .getInteger(R.integer.config_burnInProtectionMinHorizontalOffset)
                        != -1;
        HBM_LUX =
                Resources.getSystem()
                        .getInteger(R.integer.config_burnInProtectionMinHorizontalOffset);
        SEC_FEATURE_SCREEN_CURTAIN =
                SemFloatingFeature.getInstance()
                        .getBoolean("SEC_FLOATING_FEATURE_SYSTEM_SUPPORT_SCREEN_CURTAIN");
        SEC_FEATURE_SUPPORT_AOD =
                SemFloatingFeature.getInstance()
                        .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "")
                        .contains("aodversion");
        SEC_FEATURE_SUPPORT_LEGACY_MISC_POWER_HAL =
                SystemProperties.getInt("ro.product.first_api_level", 0) < 31;
        SEC_FEATURE_USE_LIGHTS_HAL_EXTENSION =
                z9 && SystemProperties.getInt("ro.product.first_api_level", 0) < 33;
        SEC_FEATURE_ENABLE_MTK_POWER_THROTTLING = startsWith2;
        USE_SEC_LONG_TERM_MODEL = true;
        sScreenOffProfilers = new RingBuffer(ScreenOffProfiler.class, 100);
        ScreenOffProfiler screenOffProfiler = new ScreenOffProfiler();
        screenOffProfiler.mNeedSkip = false;
        screenOffProfiler.clearAll();
        sCurrentScreenOffProfiler = screenOffProfiler;
        sScreenOnProfilers = new RingBuffer(ScreenOnProfiler.class, 100);
        sCurrentScreenOnProfiler = new ScreenOnProfiler();
    }

    public static String brightnessToString(float f) {
        return String.format(
                Locale.US,
                "%d(%.2f)",
                Integer.valueOf(BrightnessSynchronizer.brightnessFloatToInt(f)),
                Float.valueOf(f));
    }

    public static String brightnessToString(float f, int i) {
        return String.format(Locale.US, "%d(%.3f)", Integer.valueOf(i), Float.valueOf(f));
    }

    public static String callerInfoToString(boolean z) {
        StringBuilder sb = new StringBuilder();
        sb.append(
                DualAppManagerService$$ExternalSyntheticOutline0.m(
                        Binder.getCallingUid(), Binder.getCallingPid(), " (uid: ", " pid: ", ")"));
        if (!z || Binder.getCallingPid() != Process.myPid()) {
            return sb.toString();
        }
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int i = 0;
        while (true) {
            if (i < stackTrace.length) {
                if (!stackTrace[i].getClassName().contains("dalvik.system.VMStack")
                        && !stackTrace[i].getClassName().contains("java.lang.Thread")
                        && !stackTrace[i]
                                .getClassName()
                                .contains("com.android.server.power.PowerManagerUtil")
                        && !stackTrace[i]
                                .getClassName()
                                .contains("com.android.server.power.PowerManagerService")
                        && !stackTrace[i].getClassName().contains("android.os.PowerManager")) {
                    sb.append(
                            " <- "
                                    + stackTrace[i].getMethodName()
                                    + "() in "
                                    + stackTrace[i].getClassName()
                                    + ":"
                                    + stackTrace[i].getLineNumber());
                    break;
                }
                i++;
            } else {
                break;
            }
        }
        return sb.toString();
    }

    public static boolean fileExist(String str) {
        boolean z;
        try {
            z = new File(str).exists();
        } catch (Exception e) {
            e.printStackTrace();
            z = false;
        }
        Slog.d("PowerManagerUtil", "fileExist " + str + ": " + z);
        return z;
    }

    public static void fileWriteString(String str, String str2) {
        Slog.d("PowerManagerUtil", "fileWriteString to " + str + ", " + str2);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(new File(str));
            try {
                fileOutputStream.write(str2.getBytes());
                fileOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentTimeAsString() {
        return new SimpleDateFormat("MM-dd HH:mm:ss.SSS")
                .format(new Date(System.currentTimeMillis()));
    }

    public static boolean isFakeAodAvailable(int i) {
        if (SEC_FEATURE_AOD_LOOK_CHARGING_UI) {
            return true;
        }
        return SEC_FEATURE_AOD_LOOK_CHARGING_UI_ON_SUB_DISPLAY && i == 1;
    }

    public static String readFromFile(String str) {
        String str2 = null;
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(new File(str), "r");
            try {
                str2 = randomAccessFile.readLine();
                Slog.d("PowerManagerUtil", "readFromFile " + str + ": " + str2);
                randomAccessFile.close();
            } finally {
            }
        } catch (IOException unused) {
        }
        return str2;
    }
}
