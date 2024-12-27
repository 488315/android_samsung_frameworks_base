package com.samsung.android.biometrics.app.setting;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.hardware.display.DisplayManagerGlobal;
import android.os.Build;
import android.os.Debug;
import android.os.Message;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Display;
import android.view.DisplayInfo;
import android.view.HapticFeedbackConstants;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowMetrics;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.TextView;

import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.vibrator.VibRune;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

public abstract class Utils {
    public static final boolean IS_DEBUG_LEVEL_MID_OR_HIGH;
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static String mDeviceType = null;

    public abstract class Config {
        public static final boolean FACE_FEATURE_POSE_ENROLL;
        public static final String FEATURE_CONFIG_CONTROL_AUTO_BRIGHTNESS;
        public static boolean FEATURE_FACE_HAL;
        public static final boolean FEATURE_JDM_HAL;
        public static final boolean FEATURE_SUPPORT_AOD;
        public static final boolean FEATURE_SUPPORT_AOD_TRANSITION_ANIMATION;
        public static final boolean FEATURE_SUPPORT_BP_IN_COVER_SCREEN;
        public static final boolean FEATURE_SUPPORT_DESKTOP_MODE;
        public static final boolean FEATURE_SUPPORT_DISABLED_MENU_K05;
        public static final boolean FEATURE_SUPPORT_DISPLAY_SEAMLESS_MODE;
        public static final boolean FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS;
        public static final boolean FEATURE_SUPPORT_DUAL_DISPLAY;
        public static final boolean FEATURE_SUPPORT_SPEN;
        public static final boolean FEATURE_SUPPORT_TASKBAR;
        public static final boolean FP_FEATURE_ENROLL_FRAME_MOVING_UX;
        public static final boolean FP_FEATURE_HW_LIGHT_SOURCE;
        public static final boolean FP_FEATURE_LOCAL_HBM;
        public static final boolean FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE;
        public static final boolean FP_FEATURE_SENSOR_IS_OPTICAL;
        public static final boolean FP_FEATURE_SENSOR_IS_REAR;
        public static final boolean FP_FEATURE_SENSOR_IS_SIDE;
        public static final boolean FP_FEATURE_SENSOR_IS_ULTRASONIC;
        public static final boolean FP_FEATURE_TSP_BLOCK;
        public static final String SENSOR_BOTTOM_MARGIN_BOUNDARY_RECENT_HOME_KEY;

        static {
            boolean contains =
                    SemFloatingFeature.getInstance()
                            .getString(
                                    "SEC_FLOATING_FEATURE_COMMON_CONFIG_DEVICE_MANUFACTURING_TYPE")
                            .contains("jdm");
            FEATURE_JDM_HAL = contains;
            FEATURE_SUPPORT_DESKTOP_MODE =
                    SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_KNOX_DESKTOP");
            boolean z =
                    SemFloatingFeature.getInstance()
                                    .getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION")
                            > 0;
            FEATURE_SUPPORT_SPEN = z;
            FEATURE_SUPPORT_AOD =
                    SemFloatingFeature.getInstance()
                            .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM")
                            .contains("aodversion");
            String string =
                    SemFloatingFeature.getInstance()
                            .getString("SEC_FLOATING_FEATURE_BIOAUTH_CONFIG_FINGERPRINT_FEATURES");
            FP_FEATURE_SENSOR_IS_IN_DISPLAY_TYPE = true;
            FP_FEATURE_SENSOR_IS_ULTRASONIC = true;
            FP_FEATURE_SENSOR_IS_OPTICAL = string.contains("optical");
            FP_FEATURE_SENSOR_IS_SIDE = string.contains("powerkey") || string.contains("side");
            FP_FEATURE_SENSOR_IS_REAR = string.contains("rear");
            FP_FEATURE_HW_LIGHT_SOURCE =
                    SemFloatingFeature.getInstance()
                                    .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_SELFMASK_VERSION")
                            >= 1;
            FP_FEATURE_TSP_BLOCK = z;
            FP_FEATURE_ENROLL_FRAME_MOVING_UX = true;
            FP_FEATURE_LOCAL_HBM =
                    SemFloatingFeature.getInstance()
                                    .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_LOCAL_HBM")
                            == 1;
            FACE_FEATURE_POSE_ENROLL = !contains;
            FEATURE_SUPPORT_DISABLED_MENU_K05 =
                    SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_DISABLED_MENU_K05");
            SENSOR_BOTTOM_MARGIN_BOUNDARY_RECENT_HOME_KEY = "15";
            FEATURE_SUPPORT_DUAL_DISPLAY =
                    SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
            FEATURE_SUPPORT_TASKBAR =
                    SemFloatingFeature.getInstance()
                            .getBoolean("SEC_FLOATING_FEATURE_LAUNCHER_SUPPORT_TASKBAR");
            FEATURE_CONFIG_CONTROL_AUTO_BRIGHTNESS =
                    SemFloatingFeature.getInstance()
                            .getString("SEC_FLOATING_FEATURE_LCD_CONFIG_CONTROL_AUTO_BRIGHTNESS");
            FEATURE_SUPPORT_DISPLAY_SEAMLESS_MODE =
                    SemFloatingFeature.getInstance()
                                    .getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_HFR_MODE")
                            == 2;
            FEATURE_SUPPORT_BP_IN_COVER_SCREEN =
                    SemFloatingFeature.getInstance()
                            .getBoolean(
                                    "SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN");
            FEATURE_SUPPORT_AOD_TRANSITION_ANIMATION =
                    SemFloatingFeature.getInstance()
                            .getString("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_AOD_ITEM", "")
                            .contains("clocktransition");
            FEATURE_SUPPORT_DRM_PROPERTY_FOR_BRIGHTNESS =
                    SystemProperties.getInt("vendor.display.enable_brightness_drm_prop", 0) != 0;
        }
    }

    static {
        IS_DEBUG_LEVEL_MID_OR_HIGH =
                SystemProperties.get("ro.boot.debug_level", "").equalsIgnoreCase("0x494d")
                        || SystemProperties.get("ro.boot.debug_level", "")
                                .equalsIgnoreCase("0x4948");
    }

    public static int dipToPixel(Context context, double d) {
        context.getDisplay().getRealMetrics(new DisplayMetrics());
        return Math.round((float) (d * r0.density));
    }

    public static int getDisplayHeight(Context context) {
        Display display = context.getDisplay();
        Point point = new Point();
        try {
            display.getRealSize(point);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return point.y;
    }

    public static DisplayMetrics getDisplayMetrics(Context context) {
        Display display;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        if (context != null && (display = context.getDisplay()) != null) {
            display.getRealMetrics(displayMetrics);
        }
        return displayMetrics;
    }

    public static Point getDisplaySize(Context context) {
        DisplayManager displayManager =
                (DisplayManager) context.getSystemService(DisplayManager.class);
        DisplayInfo displayInfo = new DisplayInfo();
        Display display = displayManager.getDisplay(0);
        if (display != null) {
            display.getDisplayInfo(displayInfo);
        }
        int i = displayInfo.rotation;
        return (i == 0 || i == 2)
                ? new Point(displayInfo.logicalWidth, displayInfo.logicalHeight)
                : new Point(displayInfo.logicalHeight, displayInfo.logicalWidth);
    }

    public static int getDisplayWidthInPortraitMode(Context context) {
        if (isScreenLandscape(context)) {
            return getDisplayHeight(context);
        }
        Display display = context.getDisplay();
        Point point = new Point();
        try {
            display.getRealSize(point);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return point.x;
    }

    public static boolean getFolderOpenState(Context context, boolean z) {
        Configuration configuration = context.getResources().getConfiguration();
        int i = configuration == null ? -1 : configuration.hardKeyboardHidden;
        Log.d("BSS_Utils", "getFolderOpenState : " + i);
        return z || i == 1;
    }

    public static int getInDisplayFingerPositionStringId(Rect rect, float f, float f2) {
        if (rect == null) {
            return 0;
        }
        return new int[] {
                    R.string.fingerprint_tts_indisplay_position_down_right,
                    R.string.fingerprint_tts_indisplay_position_down,
                    R.string.fingerprint_tts_indisplay_position_down_left,
                    R.string.fingerprint_tts_indisplay_position_right,
                    0,
                    R.string.fingerprint_tts_indisplay_position_left,
                    R.string.fingerprint_tts_indisplay_position_up_right,
                    R.string.fingerprint_tts_indisplay_position_up,
                    R.string.fingerprint_tts_indisplay_position_up_left
                }
                [
                ((f2 >= ((float) rect.top) ? f2 > ((float) rect.bottom) ? 2 : 1 : 0) * 3)
                        + (f < ((float) rect.left) ? 0 : f > ((float) rect.right) ? 2 : 1)];
    }

    public static int getIntDb(Context context, String str, boolean z, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            i =
                    z
                            ? Settings.Secure.getIntForUser(contentResolver, str, i, -2)
                            : Settings.System.getIntForUser(contentResolver, str, i, -2);
            return i;
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("getIntDb: "), "BSS_Utils");
            return i;
        }
    }

    public static String getLogFormat(Message message) {
        StringBuilder sb = new StringBuilder("handleMessage = ");
        sb.append(message.what);
        sb.append(", ");
        sb.append(message.arg1);
        sb.append(", ");
        sb.append(message.arg2);
        if (DEBUG) {
            sb.append(", { when=");
            TimeUtils.formatDuration(message.getWhen() - SystemClock.uptimeMillis(), sb);
            if (message.obj instanceof String) {
                sb.append(", ");
                sb.append((String) message.obj);
            }
            sb.append(" }");
        }
        return sb.toString();
    }

    public static long getLongDb(Context context, String str, long j) {
        try {
            return Settings.System.getLongForUser(context.getContentResolver(), str, j, -2);
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("getLongDb: "), "BSS_Utils");
            return j;
        }
    }

    public static Point getMaximumWindowSize(Context context) {
        Point point = new Point();
        WindowMetrics maximumWindowMetrics =
                ((WindowManager) context.getSystemService(WindowManager.class))
                        .getMaximumWindowMetrics();
        point.x = maximumWindowMetrics.getBounds().width();
        point.y = maximumWindowMetrics.getBounds().height();
        return point;
    }

    public static int getNavigationBarHeight(Context context) {
        int identifier;
        if (context == null) {
            Log.e("BSS_Utils", "getNavigationBar : Context is null");
            return 0;
        }
        if ((context.getResources().getConfiguration().screenLayout & 15) >= 3
                || (identifier =
                                context.getResources()
                                        .getIdentifier("navigation_bar_height", "dimen", "android"))
                        <= 0) {
            return 0;
        }
        return context.getResources().getDimensionPixelSize(identifier);
    }

    public static int getRatioHeight(Context context, float f) {
        return (int) (getDisplayHeight(context) * f);
    }

    public static int getRotation(Context context) {
        Display realDisplay =
                DisplayManagerGlobal.getInstance().getRealDisplay(context.getDisplayId());
        if (realDisplay != null) {
            return realDisplay.getRotation();
        }
        Log.w("BSS_Utils", "NO DISPLAY");
        return 0;
    }

    public static int getStatusBarHeight(Context context) {
        if (context == null) {
            Log.e("BSS_Utils", "getStatusBarHeight : Context is null");
        } else {
            int identifier =
                    context.getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (identifier > 0) {
                return context.getResources().getDimensionPixelSize(identifier);
            }
        }
        return 0;
    }

    public static boolean isColorThemeEnabled(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "wallpapertheme_state", 0) == 1
                || !TextUtils.isEmpty(
                        Settings.System.getString(
                                context.getContentResolver(), "current_sec_active_themepackage"));
    }

    public static boolean isDesktopMode(Context context) {
        SemDesktopModeManager semDesktopModeManager =
                (SemDesktopModeManager) context.getSystemService(SemDesktopModeManager.class);
        return semDesktopModeManager != null
                && semDesktopModeManager.getDesktopModeState().getEnabled() == 4;
    }

    public static boolean isDesktopStandaloneMode(Context context) {
        SemDesktopModeManager semDesktopModeManager =
                (SemDesktopModeManager) context.getSystemService("desktopmode");
        return semDesktopModeManager != null
                && semDesktopModeManager.getDesktopModeState().getDisplayType() == 101;
    }

    public static boolean isForegroundTask(String str) {
        try {
            List<ActivityManager.RunningTaskInfo> tasks =
                    ActivityTaskManager.getInstance().getTasks(Integer.MAX_VALUE);
            if (tasks == null || tasks.isEmpty()) {
                return false;
            }
            for (ActivityManager.RunningTaskInfo runningTaskInfo : tasks) {
                ComponentName componentName = runningTaskInfo.topActivity;
                if (componentName != null) {
                    Log.d(
                            "BSS_Utils",
                            componentName.getPackageName()
                                    + ", isVisible = "
                                    + runningTaskInfo.isVisible()
                                    + ", isFocused = "
                                    + runningTaskInfo.isFocused);
                    if (str.contentEquals(componentName.getPackageName())
                            && runningTaskInfo.isVisible()) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("isForegroundTask: "), "BSS_Utils");
            return false;
        }
    }

    public static boolean isNightThemeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(), "display_night_theme", 0) != 0;
    }

    public static boolean isScreenLandscape(Context context) {
        return (getRotation(context) == 1) | (getRotation(context) == 3);
    }

    public static boolean isTablet() {
        String str = mDeviceType;
        if (str != null && str.length() > 0) {
            return mDeviceType.contains("tablet");
        }
        String str2 = SystemProperties.get("ro.build.characteristics");
        mDeviceType = str2;
        return str2 != null && str2.contains("tablet");
    }

    public static boolean isTalkBackEnabled(Context context) {
        AccessibilityManager accessibilityManager =
                (AccessibilityManager) context.getSystemService("accessibility");
        return accessibilityManager != null && accessibilityManager.semIsScreenReaderEnabled();
    }

    public static boolean isTpaMode(Context context) {
        return (Build.IS_USER || getIntDb(context, "biometric_tpa_mode", true, 0) == 0)
                ? false
                : true;
    }

    public static void notifyAccessibilityContentChanged(
            AccessibilityManager accessibilityManager, ViewGroup viewGroup) {
        if (accessibilityManager.isEnabled()) {
            AccessibilityEvent obtain = AccessibilityEvent.obtain();
            obtain.setEventType(2048);
            obtain.setContentChangeTypes(1);
            viewGroup.sendAccessibilityEventUnchecked(obtain);
            viewGroup.notifySubtreeAccessibilityStateChanged(viewGroup, viewGroup, 1);
        }
    }

    public static void playVibration(Context context, int i) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Vibrator.class);
        if (vibrator == null) {
            Log.w("BSS_Utils", "getSystemService(Vibrator.class) is failed.");
            return;
        }
        if (VibRune.SUPPORT_HAPTIC_FEEDBACK_ON_DC_MOTOR
                && vibrator.semGetSupportedVibrationType() == 1) {
            i = 100;
        }
        vibrator.vibrate(
                VibrationEffect.semCreateWaveform(
                        HapticFeedbackConstants.semGetVibrationIndex(i),
                        -1,
                        VibrationEffect.SemMagnitudeType.TYPE_TOUCH));
    }

    public static void putIntDb(Context context, String str, boolean z, int i) {
        ContentResolver contentResolver = context.getContentResolver();
        try {
            if (z) {
                Settings.Secure.putIntForUser(contentResolver, str, i, -2);
            } else {
                Settings.System.putIntForUser(contentResolver, str, i, -2);
            }
        } catch (Exception e) {
            FocusableWindow$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("putIntDb: "), "BSS_Utils");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v10, types: [byte[]] */
    /* JADX WARN: Type inference failed for: r5v4 */
    public static byte[] readFile(File file) {
        Exception e;
        byte[] bArr;
        byte[] bArr2;
        FileInputStream fileInputStream;
        Throwable th;
        if (file == 0 || !file.exists()) {
            return null;
        }
        try {
            try {
                fileInputStream = new FileInputStream(file);
                try {
                    file = new byte[(int) file.length()];
                } catch (Throwable th2) {
                    th = th2;
                    file = 0;
                }
                try {
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        fileInputStream.close();
                    } catch (Throwable th4) {
                        th.addSuppressed(th4);
                    }
                    throw th;
                }
            } catch (Exception e2) {
                e = e2;
                bArr = null;
                e.printStackTrace();
                bArr2 = bArr;
                return bArr2;
            }
        } catch (Exception e3) {
            e = e3;
            bArr = file;
            e.printStackTrace();
            bArr2 = bArr;
            return bArr2;
        }
        if (fileInputStream.read(file) < 0) {
            fileInputStream.close();
            return null;
        }
        fileInputStream.close();
        bArr2 = file;
        return bArr2;
    }

    public static void setMaxTextScaleSize(TextView textView, int i) {
        if (textView == null) {
            return;
        }
        float f = textView.getResources().getConfiguration().fontScale;
        textView.setTextSize(
                0, textView.getResources().getDimensionPixelSize(i) * (f > 1.3f ? 1.3f / f : 1.0f));
    }
}
