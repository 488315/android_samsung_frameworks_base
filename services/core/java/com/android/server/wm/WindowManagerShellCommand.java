package com.android.server.wm;

import android.R;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Binder;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.os.ShellCommand;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.util.Slog;
import android.util.TypedValue;
import android.view.CrossWindowBlurListeners;
import android.view.IWindow;

import com.android.internal.os.ByteTransferPipe;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.IoThread;
import com.android.server.UiModeManagerService$13$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.policy.DisplayFoldController;
import com.android.server.policy.PhoneWindowManager;

import com.samsung.android.rune.CoreRune;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class WindowManagerShellCommand extends ShellCommand {
    public final AppCompatConfiguration mAppCompatConfiguration;
    public final WindowManagerService mInterface;
    public final WindowManagerService mInternal;

    public WindowManagerShellCommand(WindowManagerService windowManagerService) {
        this.mInterface = windowManagerService;
        this.mInternal = windowManagerService;
        this.mAppCompatConfiguration = windowManagerService.mAppCompatConfiguration;
    }

    public final int getDisplayId(String str) {
        if (!"-d".equals(str)) {
            str = getNextOption();
        }
        if (str != null && "-d".equals(str)) {
            try {
                return Integer.parseInt(getNextArgRequired());
            } catch (NumberFormatException e) {
                this.getErrPrintWriter().println("Error: bad number " + e);
            } catch (IllegalArgumentException e2) {
                this.getErrPrintWriter().println("Error: " + e2);
            }
        }
        return 0;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0239, code lost:

       if (r11.equals("true") == false) goto L143;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int onCommand(java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 1134
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowManagerShellCommand.onCommand(java.lang.String):int");
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Window manager (window) commands:");
        outPrintWriter.println("  help");
        outPrintWriter.println("      Print this help text.");
        outPrintWriter.println("  size [reset|WxH|WdpxHdp] [-d DISPLAY_ID]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Return or override display size.",
                "    width and height in pixels unless suffixed with 'dp'.",
                "  density [reset|DENSITY] [-d DISPLAY_ID] [-u UNIQUE_ID]",
                "    Return or override display density.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  folded-area [reset|LEFT,TOP,RIGHT,BOTTOM]",
                "    Return or override folded area.",
                "  scaling [off|auto] [-d DISPLAY_ID]",
                "    Set display scaling mode.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "  dismiss-keyguard",
                "    Dismiss the keyguard, prompting user for auth if necessary.",
                "  disable-blur [true|1|false|0]",
                "  user-rotation [-d DISPLAY_ID] [free|lock] [rotation]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Print or set user rotation mode and user rotation.",
                "  dump-visible-window-views",
                "    Dumps the encoded view hierarchies of visible windows",
                "  fixed-to-user-rotation [-d DISPLAY_ID] [enabled|disabled|default");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      |enabled_if_no_auto_rotation]",
                "    Print or set rotating display for app requested orientation.",
                "  set-ignore-orientation-request [-d DISPLAY_ID] [true|1|false|0]",
                "  get-ignore-orientation-request [-d DISPLAY_ID] ");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    If app requested orientation should be ignored.",
                "  set-sandbox-display-apis [true|1|false|0]",
                "    Sets override of Display APIs getRealSize / getRealMetrics to reflect ",
                "    DisplayArea of the activity, or the window bounds if in letterbox or");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    Size Compat Mode.",
                "  set-letterbox-style",
                "    Sets letterbox style using the following options:",
                "      --aspectRatio aspectRatio");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        Aspect ratio of letterbox for fixed orientation. If aspectRatio <= 1.0",
                "        both it and R.dimen.config_fixedOrientationLetterboxAspectRatio will",
                "        be ignored and framework implementation will determine aspect ratio.",
                "      --minAspectRatioForUnresizable aspectRatio");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        Default min aspect ratio for unresizable apps which is used when an",
                "        app is eligible for the size compat mode.  If aspectRatio <= 1.0",
                "        both it and R.dimen.config_fixedOrientationLetterboxAspectRatio will",
                "        be ignored and framework implementation will determine aspect ratio.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --cornerRadius radius",
                "        Corners radius (in pixels) for activities in the letterbox mode.",
                "        If radius < 0, both R.integer.config_letterboxActivityCornersRadius",
                "        and it will be ignored and corners of the activity won't be rounded.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --backgroundType [reset|solid_color|app_color_background",
                "          |app_color_background_floating|wallpaper]",
                "        Type of background used in the letterbox mode.",
                "      --backgroundColor color");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        Color of letterbox which is be used when letterbox background type",
                "        is 'solid-color'. Use (set)get-letterbox-style to check and control",
                "        letterbox background type. See Color#parseColor for allowed color",
                "        formats (#RRGGBB and some colors by name, e.g. magenta or olive).");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --backgroundColorResource resource_name",
                "        Color resource name of letterbox background which is used when",
                "        background type is 'solid-color'. Use (set)get-letterbox-style to",
                "        check and control background type. Parameter is a color resource");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        name, for example, @android:color/system_accent2_50.",
                "      --wallpaperBlurRadius radius",
                "        Blur radius for 'wallpaper' letterbox background. If radius <= 0",
                "        both it and R.dimen.config_letterboxBackgroundWallpaperBlurRadius");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        are ignored and 0 is used.",
                "      --wallpaperDarkScrimAlpha alpha",
                "        Alpha of a black translucent scrim shown over 'wallpaper'",
                "        letterbox background. If alpha < 0 or >= 1 both it and");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        R.dimen.config_letterboxBackgroundWallaperDarkScrimAlpha are ignored",
                "        and 0.0 (transparent) is used instead.",
                "      --horizontalPositionMultiplier multiplier",
                "        Horizontal position of app window center. If multiplier < 0 or > 1,");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        both it and R.dimen.config_letterboxHorizontalPositionMultiplier",
                "        are ignored and central position (0.5) is used.",
                "      --verticalPositionMultiplier multiplier",
                "        Vertical position of app window center. If multiplier < 0 or > 1,");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        both it and R.dimen.config_letterboxVerticalPositionMultiplier",
                "        are ignored and central position (0.5) is used.",
                "      --isHorizontalReachabilityEnabled [true|1|false|0]",
                "        Whether horizontal reachability repositioning is allowed for ");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        letterboxed fullscreen apps in landscape device orientation.",
                "      --isVerticalReachabilityEnabled [true|1|false|0]",
                "        Whether vertical reachability repositioning is allowed for ",
                "        letterboxed fullscreen apps in portrait device orientation.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --defaultPositionForHorizontalReachability [left|center|right]",
                "        Default position of app window when horizontal reachability is.",
                "        enabled.",
                "      --defaultPositionForVerticalReachability [top|center|bottom]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        Default position of app window when vertical reachability is.",
                "        enabled.",
                "      --persistentPositionForHorizontalReachability [left|center|right]",
                "        Persistent position of app window when horizontal reachability is.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        enabled.",
                "      --persistentPositionForVerticalReachability [top|center|bottom]",
                "        Persistent position of app window when vertical reachability is.",
                "        enabled.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      --isEducationEnabled [true|1|false|0]",
                "        Whether education is allowed for letterboxed fullscreen apps.",
                "      --isSplitScreenAspectRatioForUnresizableAppsEnabled [true|1|false|0]",
                "        Whether using split screen aspect ratio as a default aspect ratio for");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        unresizable apps.",
                "      --isTranslucentLetterboxingEnabled [true|1|false|0]",
                "        Whether letterboxing for translucent activities is enabled.",
                "      --isUserAppAspectRatioSettingsEnabled [true|1|false|0]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        Whether user aspect ratio settings are enabled.",
                "      --isUserAppAspectRatioFullscreenEnabled [true|1|false|0]",
                "        Whether user aspect ratio fullscreen option is enabled.",
                "      --isCameraCompatRefreshEnabled [true|1|false|0]");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        Whether camera compatibility refresh is enabled.",
                "      --isCameraCompatRefreshCycleThroughStopEnabled [true|1|false|0]",
                "        Whether activity \"refresh\" in camera compatibility treatment should",
                "        happen using the \"stopped -> resumed\" cycle rather than");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "        \"paused -> resumed\" cycle.",
                "  reset-letterbox-style [aspectRatio|cornerRadius|backgroundType",
                "      |backgroundColor|wallpaperBlurRadius|wallpaperDarkScrimAlpha",
                "      |horizontalPositionMultiplier|verticalPositionMultiplier");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      |isHorizontalReachabilityEnabled|isVerticalReachabilityEnabled",
                "      |isEducationEnabled|defaultPositionMultiplierForHorizontalReachability",
                "      |isTranslucentLetterboxingEnabled|isUserAppAspectRatioSettingsEnabled",
                "      |persistentPositionMultiplierForHorizontalReachability");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "      |persistentPositionMultiplierForVerticalReachability",
                "      |defaultPositionMultiplierForVerticalReachability]",
                "    Resets overrides to default values for specified properties separated",
                "    by space, e.g. 'reset-letterbox-style aspectRatio cornerRadius'.");
        BatteryService$$ExternalSyntheticOutline0.m(
                outPrintWriter,
                "    If no arguments provided, all values will be reset.",
                "  get-letterbox-style",
                "    Prints letterbox style configuration.");
        outPrintWriter.println("  set-multi-window-config");
        outPrintWriter.println(
                "    Sets options to determine if activity should be shown in multi window:");
        outPrintWriter.println("      --supportsNonResizable [configValue]");
        outPrintWriter.println(
                "        Whether the device supports non-resizable activity in multi window.");
        outPrintWriter.println(
                "        -1: The device doesn't support non-resizable in multi window.");
        outPrintWriter.println(
                "         0: The device supports non-resizable in multi window only if");
        outPrintWriter.println("            this is a large screen device.");
        outPrintWriter.println(
                "         1: The device always supports non-resizable in multi window.");
        outPrintWriter.println("      --respectsActivityMinWidthHeight [configValue]");
        outPrintWriter.println(
                "        Whether the device checks the activity min width/height to determine ");
        outPrintWriter.println("        if it can be shown in multi window.");
        outPrintWriter.println(
                "        -1: The device ignores the activity min width/height when determining");
        outPrintWriter.println("            if it can be shown in multi window.");
        outPrintWriter.println(
                "         0: If this is a small screen, the device compares the activity min");
        outPrintWriter.println(
                "            width/height with the min multi window modes dimensions");
        outPrintWriter.println(
                "            the device supports to determine if the activity can be shown in");
        outPrintWriter.println("            multi window.");
        outPrintWriter.println(
                "         1: The device always compare the activity min width/height with the");
        outPrintWriter.println(
                "            min multi window dimensions the device supports to determine if");
        outPrintWriter.println("            the activity can be shown in multi window.");
        outPrintWriter.println("  get-multi-window-config");
        outPrintWriter.println("    Prints values of the multi window config options.");
        outPrintWriter.println("  reset-multi-window-config");
        outPrintWriter.println(
                "    Resets overrides to default values of the multi window config options.");
        outPrintWriter.println("  reset [-d DISPLAY_ID]");
        outPrintWriter.println("    Reset all override settings.");
        if (!Build.IS_USER) {
            BatteryService$$ExternalSyntheticOutline0.m(
                    outPrintWriter,
                    "  tracing (start | stop)",
                    "    Start or stop window tracing.",
                    "  logging (start | stop | enable | disable | enable-text | disable-text)",
                    "    Logging settings.");
        }
        outPrintWriter.println("  size-density [reset|WxH|WdpxHdp] [reset|DENSITY]");
        outPrintWriter.println("    Return or override display size & density at once.");
    }

    public final int parseDimension(int i, String str) {
        int i2;
        if (str.endsWith("px")) {
            return Integer.parseInt(str.substring(0, str.length() - 2));
        }
        if (!str.endsWith("dp")) {
            return Integer.parseInt(str);
        }
        try {
            i2 = this.mInterface.getBaseDisplayDensity(i);
        } catch (RemoteException unused) {
            i2 = 160;
        }
        return (Integer.parseInt(str.substring(0, str.length() - 2)) * i2) / 160;
    }

    public final int printDisplayUserRotation(int i, PrintWriter printWriter) {
        int displayUserRotation = this.mInternal.getDisplayUserRotation(i);
        if (displayUserRotation < 0) {
            getErrPrintWriter().println("Error: check logcat for more details.");
            return -1;
        }
        if (!this.mInternal.isDisplayRotationFrozen(i)) {
            printWriter.println("free");
            return 0;
        }
        printWriter.print("lock ");
        printWriter.println(displayUserRotation);
        return 0;
    }

    public final int printFixedToUserRotation(int i, PrintWriter printWriter) {
        int i2;
        WindowManagerService windowManagerService = this.mInternal;
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent = windowManagerService.mRoot.getDisplayContent(i);
                if (displayContent == null) {
                    Slog.w(
                            "WindowManager",
                            "Trying to get fixed to user rotation for a missing display.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                    i2 = -1;
                } else {
                    i2 = displayContent.mDisplayRotation.mFixedToUserRotation;
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        if (i2 == 0) {
            printWriter.println("default");
            return 0;
        }
        if (i2 == 1) {
            printWriter.println("disabled");
            return 0;
        }
        if (i2 == 2) {
            printWriter.println("enabled");
            return 0;
        }
        if (i2 != 3) {
            getErrPrintWriter().println("Error: check logcat for more details.");
            return -1;
        }
        printWriter.println("enabled_if_no_auto_rotation");
        return 0;
    }

    public final void printInitialDisplayDensity(int i, PrintWriter printWriter) {
        try {
            int initialDisplayDensity =
                    CoreRune.FW_DYNAMIC_RESOLUTION_CONTROL
                            ? DisplayMetrics.DENSITY_DEVICE_STABLE
                            : this.mInterface.getInitialDisplayDensity(i);
            int baseDisplayDensity = this.mInterface.getBaseDisplayDensity(i);
            printWriter.println("Physical density: " + initialDisplayDensity);
            if (initialDisplayDensity != baseDisplayDensity) {
                printWriter.println("Override density: " + baseDisplayDensity);
            }
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e, printWriter);
        }
    }

    public final void printInitialDisplaySize(int i, PrintWriter printWriter) {
        Point point = new Point();
        Point point2 = new Point();
        try {
            this.mInterface.getInitialDisplaySize(i, point);
            this.mInterface.getBaseDisplaySize(i, point2);
            printWriter.println("Physical size: " + point.x + "x" + point.y);
            if (point.equals(point2)) {
                return;
            }
            printWriter.println("Override size: " + point2.x + "x" + point2.y);
        } catch (RemoteException e) {
            UiModeManagerService$13$$ExternalSyntheticOutline0.m(
                    "Remote exception: ", e, printWriter);
        }
    }

    public final void resetLetterboxStyle() {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                AppCompatConfiguration appCompatConfiguration = this.mAppCompatConfiguration;
                appCompatConfiguration.mFixedOrientationLetterboxAspectRatio =
                        appCompatConfiguration
                                .mContext
                                .getResources()
                                .getFloat(R.dimen.config_pictureInPictureMinAspectRatio);
                AppCompatConfiguration appCompatConfiguration2 = this.mAppCompatConfiguration;
                appCompatConfiguration2.mDefaultMinAspectRatioForUnresizableApps =
                        appCompatConfiguration2
                                .mContext
                                .getResources()
                                .getFloat(R.dimen.config_screenBrightnessDozeFloat);
                AppCompatConfiguration appCompatConfiguration3 = this.mAppCompatConfiguration;
                appCompatConfiguration3.mLetterboxActivityCornersRadius =
                        appCompatConfiguration3
                                .mContext
                                .getResources()
                                .getInteger(R.integer.config_minNumVisibleRecentTasks_lowRam);
                AppCompatConfiguration appCompatConfiguration4 = this.mAppCompatConfiguration;
                appCompatConfiguration4.mLetterboxBackgroundTypeOverride = -1;
                appCompatConfiguration4.mLetterboxBackgroundColorOverride = null;
                appCompatConfiguration4.mLetterboxBackgroundColorResourceIdOverride = null;
                appCompatConfiguration4.mLetterboxBackgroundWallpaperBlurRadiusPx =
                        appCompatConfiguration4
                                .mContext
                                .getResources()
                                .getDimensionPixelSize(
                                        R.dimen.config_rotaryEncoderAxisScrollTickInterval);
                AppCompatConfiguration appCompatConfiguration5 = this.mAppCompatConfiguration;
                appCompatConfiguration5.mLetterboxBackgroundWallpaperDarkScrimAlpha =
                        appCompatConfiguration5
                                .mContext
                                .getResources()
                                .getFloat(R.dimen.config_resActivitySnapshotScale);
                AppCompatConfiguration appCompatConfiguration6 = this.mAppCompatConfiguration;
                appCompatConfiguration6.mLetterboxHorizontalPositionMultiplier =
                        appCompatConfiguration6
                                .mContext
                                .getResources()
                                .getFloat(R.dimen.config_screenBrightnessMinimumDimAmountFloat);
                AppCompatConfiguration appCompatConfiguration7 = this.mAppCompatConfiguration;
                appCompatConfiguration7.mLetterboxVerticalPositionMultiplier =
                        appCompatConfiguration7
                                .mContext
                                .getResources()
                                .getFloat(R.dimen.config_screen_magnification_scaling_threshold);
                AppCompatConfiguration appCompatConfiguration8 = this.mAppCompatConfiguration;
                appCompatConfiguration8.mIsHorizontalReachabilityEnabled =
                        appCompatConfiguration8
                                .mContext
                                .getResources()
                                .getBoolean(R.bool.config_magnification_always_on_enabled);
                AppCompatConfiguration appCompatConfiguration9 = this.mAppCompatConfiguration;
                appCompatConfiguration9.mIsVerticalReachabilityEnabled =
                        appCompatConfiguration9
                                .mContext
                                .getResources()
                                .getBoolean(R.bool.config_maskMainBuiltInDisplayCutout);
                AppCompatConfiguration appCompatConfiguration10 = this.mAppCompatConfiguration;
                appCompatConfiguration10.mIsAutomaticReachabilityInBookModeEnabled =
                        appCompatConfiguration10
                                .mContext
                                .getResources()
                                .getBoolean(R.bool.config_lockUiMode);
                AppCompatConfiguration appCompatConfiguration11 = this.mAppCompatConfiguration;
                appCompatConfiguration11.mDefaultPositionForHorizontalReachability =
                        AppCompatConfiguration
                                .readLetterboxHorizontalReachabilityPositionFromConfig(
                                        appCompatConfiguration11.mContext, false);
                AppCompatConfiguration appCompatConfiguration12 = this.mAppCompatConfiguration;
                appCompatConfiguration12.mDefaultPositionForVerticalReachability =
                        AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(
                                appCompatConfiguration12.mContext, false);
                AppCompatConfiguration appCompatConfiguration13 = this.mAppCompatConfiguration;
                int readLetterboxHorizontalReachabilityPositionFromConfig =
                        AppCompatConfiguration
                                .readLetterboxHorizontalReachabilityPositionFromConfig(
                                        appCompatConfiguration13.mContext, false);
                AppCompatConfigurationPersister appCompatConfigurationPersister =
                        appCompatConfiguration13.mAppCompatConfigurationPersister;
                appCompatConfigurationPersister.setLetterboxPositionForHorizontalReachability(
                        readLetterboxHorizontalReachabilityPositionFromConfig, false);
                appCompatConfigurationPersister.setLetterboxPositionForHorizontalReachability(
                        AppCompatConfiguration
                                .readLetterboxHorizontalReachabilityPositionFromConfig(
                                        appCompatConfiguration13.mContext, true),
                        true);
                AppCompatConfiguration appCompatConfiguration14 = this.mAppCompatConfiguration;
                int readLetterboxVerticalReachabilityPositionFromConfig =
                        AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(
                                appCompatConfiguration14.mContext, false);
                AppCompatConfigurationPersister appCompatConfigurationPersister2 =
                        appCompatConfiguration14.mAppCompatConfigurationPersister;
                appCompatConfigurationPersister2.setLetterboxPositionForVerticalReachability(
                        readLetterboxVerticalReachabilityPositionFromConfig, false);
                appCompatConfigurationPersister2.setLetterboxPositionForVerticalReachability(
                        AppCompatConfiguration.readLetterboxVerticalReachabilityPositionFromConfig(
                                appCompatConfiguration14.mContext, true),
                        true);
                AppCompatConfiguration appCompatConfiguration15 = this.mAppCompatConfiguration;
                appCompatConfiguration15.mIsEducationEnabled =
                        appCompatConfiguration15
                                .mContext
                                .getResources()
                                .getBoolean(R.bool.config_lowPowerStandbyEnabledByDefault);
                AppCompatConfiguration appCompatConfiguration16 = this.mAppCompatConfiguration;
                appCompatConfiguration16.mIsSplitScreenAspectRatioForUnresizableAppsEnabled =
                        appCompatConfiguration16
                                .mContext
                                .getResources()
                                .getBoolean(R.bool.config_mainBuiltInDisplayIsRound);
                AppCompatConfiguration appCompatConfiguration17 = this.mAppCompatConfiguration;
                appCompatConfiguration17.mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox =
                        appCompatConfiguration17
                                .mContext
                                .getResources()
                                .getBoolean(R.bool.config_lockscreenWeatherEnabledByDefault);
                AppCompatConfiguration appCompatConfiguration18 = this.mAppCompatConfiguration;
                appCompatConfiguration18.mTranslucentLetterboxingOverrideEnabled = false;
                appCompatConfiguration18.mUserAppAspectRatioSettingsOverrideEnabled = false;
                appCompatConfiguration18.mUserAppAspectRatioFullscreenOverrideEnabled = false;
                appCompatConfiguration18.mIsCameraCompatTreatmentRefreshEnabled = true;
                appCompatConfiguration18.mIsCameraCompatRefreshCycleThroughStopEnabled = true;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final int runDisplayDensity(PrintWriter printWriter) {
        int displayIdByUniqueId;
        String nextArg = getNextArg();
        String nextOption = getNextOption();
        String nextArg2 = getNextArg();
        int i = -1;
        if (!"-d".equals(nextOption) || nextArg2 == null) {
            if ("-u".equals(nextOption) && nextArg2 != null) {
                displayIdByUniqueId = this.mInterface.getDisplayIdByUniqueId(nextArg2);
                if (displayIdByUniqueId == -1) {
                    getErrPrintWriter().println("Error: the uniqueId is invalid ");
                    return -1;
                }
            }
            displayIdByUniqueId = 0;
        } else {
            try {
                displayIdByUniqueId = Integer.parseInt(nextArg2);
            } catch (NumberFormatException e) {
                getErrPrintWriter().println("Error: bad number " + e);
            }
        }
        if (nextArg == null) {
            printInitialDisplayDensity(displayIdByUniqueId, printWriter);
            return 0;
        }
        if ("-d".equals(nextArg)) {
            printInitialDisplayDensity(displayIdByUniqueId, printWriter);
            return 0;
        }
        if (!"reset".equals(nextArg)) {
            try {
                int parseInt = Integer.parseInt(nextArg);
                if (parseInt < 72) {
                    getErrPrintWriter().println("Error: density must be >= 72");
                    return -1;
                }
                i = parseInt;
            } catch (NumberFormatException e2) {
                getErrPrintWriter().println("Error: bad number " + e2);
                return -1;
            }
        }
        if (i > 0) {
            this.mInterface.setForcedDisplayDensityForUser(displayIdByUniqueId, i, -2);
        } else {
            this.mInterface.clearForcedDisplayDensityForUser(displayIdByUniqueId, -2);
        }
        return 0;
    }

    /* JADX WARN: Finally extract failed */
    public final int runDisplayFoldedArea(PrintWriter printWriter) {
        Rect rect;
        String nextArg = getNextArg();
        Rect rect2 = new Rect();
        if (nextArg != null) {
            if ("reset".equals(nextArg)) {
                rect2.setEmpty();
            } else {
                Matcher matcher =
                        Pattern.compile("(-?\\d+),(-?\\d+),(-?\\d+),(-?\\d+)").matcher(nextArg);
                if (!matcher.matches()) {
                    getErrPrintWriter().println("Error: area should be LEFT,TOP,RIGHT,BOTTOM");
                    return -1;
                }
                rect2.set(
                        Integer.parseInt(matcher.group(1)),
                        Integer.parseInt(matcher.group(2)),
                        Integer.parseInt(matcher.group(3)),
                        Integer.parseInt(matcher.group(4)));
            }
            this.mInternal.setOverrideFoldedArea(rect2);
            return 0;
        }
        WindowManagerService windowManagerService = this.mInternal;
        windowManagerService.getClass();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    DisplayFoldController displayFoldController =
                            ((PhoneWindowManager) windowManagerService.mPolicy)
                                    .mDisplayFoldController;
                    rect =
                            displayFoldController != null
                                    ? !displayFoldController.mOverrideFoldedArea.isEmpty()
                                            ? displayFoldController.mOverrideFoldedArea
                                            : displayFoldController.mFoldedArea
                                    : new Rect();
                } finally {
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            Binder.restoreCallingIdentity(clearCallingIdentity);
            if (rect.isEmpty()) {
                printWriter.println("Folded area: none");
            } else {
                StringBuilder sb = new StringBuilder("Folded area: ");
                sb.append(rect.left);
                sb.append(",");
                sb.append(rect.top);
                sb.append(",");
                sb.append(rect.right);
                sb.append(",");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                        sb, rect.bottom, printWriter);
            }
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final int runDisplaySize(PrintWriter printWriter) {
        int parseDimension;
        String nextArg = getNextArg();
        int displayId = getDisplayId(nextArg);
        if (nextArg == null) {
            printInitialDisplaySize(displayId, printWriter);
            return 0;
        }
        if ("-d".equals(nextArg)) {
            printInitialDisplaySize(displayId, printWriter);
            return 0;
        }
        int i = -1;
        if ("reset".equals(nextArg)) {
            parseDimension = -1;
        } else {
            int indexOf = nextArg.indexOf(120);
            if (indexOf <= 0 || indexOf >= nextArg.length() - 1) {
                getErrPrintWriter().println("Error: bad size ".concat(nextArg));
                return -1;
            }
            String substring = nextArg.substring(0, indexOf);
            String substring2 = nextArg.substring(indexOf + 1);
            try {
                int parseDimension2 = parseDimension(displayId, substring);
                parseDimension = parseDimension(displayId, substring2);
                i = parseDimension2;
            } catch (NumberFormatException e) {
                getErrPrintWriter().println("Error: bad number " + e);
                return -1;
            }
        }
        if (i < 0 || parseDimension < 0) {
            this.mInterface.clearForcedDisplaySize(displayId);
        } else {
            this.mInterface.setForcedDisplaySize(displayId, i, parseDimension);
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0093 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0055  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runDisplaySizeDensity(java.io.PrintWriter r11) {
        /*
            r10 = this;
            java.lang.String r0 = r10.getNextArg()
            int r1 = r10.getDisplayId(r0)
            java.lang.String r2 = "Error: bad number "
            java.lang.String r3 = "reset"
            r4 = -1
            r5 = 0
            if (r0 != 0) goto L15
            r10.printInitialDisplaySize(r1, r11)
            goto L20
        L15:
            java.lang.String r6 = "-d"
            boolean r6 = r6.equals(r0)
            if (r6 == 0) goto L23
            r10.printInitialDisplaySize(r1, r11)
        L20:
            r6 = r5
        L21:
            r7 = r6
            goto L4f
        L23:
            boolean r6 = r3.equals(r0)
            if (r6 == 0) goto L2b
            r6 = r4
            goto L21
        L2b:
            r6 = 120(0x78, float:1.68E-43)
            int r6 = r0.indexOf(r6)
            if (r6 <= 0) goto Le0
            int r7 = r0.length()
            int r7 = r7 + (-1)
            if (r6 < r7) goto L3d
            goto Le0
        L3d:
            java.lang.String r7 = r0.substring(r5, r6)
            int r6 = r6 + 1
            java.lang.String r6 = r0.substring(r6)
            int r7 = r10.parseDimension(r1, r7)     // Catch: java.lang.NumberFormatException -> Lcb
            int r6 = r10.parseDimension(r1, r6)     // Catch: java.lang.NumberFormatException -> Lcb
        L4f:
            java.lang.String r8 = r10.getNextArg()
            if (r8 != 0) goto L60
            boolean r9 = r3.equals(r0)
            if (r9 != 0) goto L60
            r10.printInitialDisplayDensity(r1, r11)
            r11 = r5
            goto L91
        L60:
            if (r8 == 0) goto L90
            boolean r11 = r3.equals(r8)
            if (r11 == 0) goto L69
            goto L90
        L69:
            int r11 = java.lang.Integer.parseInt(r8)     // Catch: java.lang.NumberFormatException -> L7b
            r2 = 72
            if (r11 >= r2) goto L91
            java.io.PrintWriter r10 = r10.getErrPrintWriter()
            java.lang.String r11 = "Error: density must be >= 72"
            r10.println(r11)
            return r4
        L7b:
            r11 = move-exception
            java.io.PrintWriter r10 = r10.getErrPrintWriter()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r2)
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            r10.println(r11)
            return r4
        L90:
            r11 = r4
        L91:
            if (r0 == 0) goto Lca
            if (r8 != 0) goto L9c
            boolean r0 = r3.equals(r0)
            if (r0 != 0) goto L9c
            goto Lca
        L9c:
            if (r7 < 0) goto Lc5
            if (r6 < 0) goto Lc5
            if (r11 <= 0) goto Lc5
            com.samsung.android.view.MultiResolutionChangeRequestInfo$Builder r0 = new com.samsung.android.view.MultiResolutionChangeRequestInfo$Builder     // Catch: android.os.RemoteException -> Lca
            r0.<init>(r5)     // Catch: android.os.RemoteException -> Lca
            com.samsung.android.view.MultiResolutionChangeRequestInfo$Builder r0 = r0.setWidth(r7)     // Catch: android.os.RemoteException -> Lca
            com.samsung.android.view.MultiResolutionChangeRequestInfo$Builder r0 = r0.setHeight(r6)     // Catch: android.os.RemoteException -> Lca
            com.samsung.android.view.MultiResolutionChangeRequestInfo$Builder r11 = r0.setDensity(r11)     // Catch: android.os.RemoteException -> Lca
            com.samsung.android.view.MultiResolutionChangeRequestInfo$Builder r11 = r11.setSaveToSettings(r5)     // Catch: android.os.RemoteException -> Lca
            com.samsung.android.view.MultiResolutionChangeRequestInfo$Builder r11 = r11.setForcedHideCutout(r4)     // Catch: android.os.RemoteException -> Lca
            com.samsung.android.view.MultiResolutionChangeRequestInfo r11 = r11.build()     // Catch: android.os.RemoteException -> Lca
            com.android.server.wm.WindowManagerService r10 = r10.mInterface     // Catch: android.os.RemoteException -> Lca
            r10.setForcedDisplaySizeDensityWithInfo(r11)     // Catch: android.os.RemoteException -> Lca
            goto Lca
        Lc5:
            com.android.server.wm.WindowManagerService r10 = r10.mInterface     // Catch: android.os.RemoteException -> Lca
            r10.clearForcedDisplaySizeDensity(r1)     // Catch: android.os.RemoteException -> Lca
        Lca:
            return r5
        Lcb:
            r11 = move-exception
            java.io.PrintWriter r10 = r10.getErrPrintWriter()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r2)
            r0.append(r11)
            java.lang.String r11 = r0.toString()
            r10.println(r11)
            return r4
        Le0:
            java.io.PrintWriter r10 = r10.getErrPrintWriter()
            java.lang.String r11 = "Error: bad size "
            java.lang.String r11 = r11.concat(r0)
            r10.println(r11)
            return r4
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowManagerShellCommand.runDisplaySizeDensity(java.io.PrintWriter):int");
    }

    public final int runDisplayUserRotation(PrintWriter printWriter) {
        int i;
        int parseInt;
        String nextArg = getNextArg();
        if (nextArg == null) {
            return printDisplayUserRotation(0, printWriter);
        }
        if ("-d".equals(nextArg)) {
            i = Integer.parseInt(getNextArgRequired());
            nextArg = getNextArg();
        } else {
            i = 0;
        }
        if (nextArg == null) {
            return printDisplayUserRotation(i, printWriter);
        }
        if ("free".equals(nextArg)) {
            this.mInternal.thawDisplayRotation(i, "WindowManagerShellCommand#free");
            return 0;
        }
        if (!"lock".equals(nextArg)) {
            getErrPrintWriter().println("Error: argument needs to be either -d, free or lock.");
            return -1;
        }
        String nextArg2 = getNextArg();
        if (nextArg2 != null) {
            try {
                parseInt = Integer.parseInt(nextArg2);
            } catch (IllegalArgumentException e) {
                getErrPrintWriter().println("Error: " + e.getMessage());
                return -1;
            }
        } else {
            parseInt = -1;
        }
        this.mInternal.freezeDisplayRotation(i, parseInt, "WindowManagerShellCommand#lock");
        return 0;
    }

    public final void runDumpVisibleWindowViews(PrintWriter printWriter) {
        if (!this.mInternal.checkCallingPermission$1(
                "android.permission.DUMP", "runDumpVisibleWindowViews()", true)) {
            throw new SecurityException("Requires DUMP permission");
        }
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(getRawOutputStream());
            try {
                final ArrayList arrayList = new ArrayList();
                WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
                WindowManagerService.boostPriorityForLockedSection();
                synchronized (windowManagerGlobalLock) {
                    try {
                        WindowManagerService windowManagerService = this.mInternal;
                        RecentTasks recentTasks = windowManagerService.mAtmService.mRecentTasks;
                        final int i = recentTasks != null ? recentTasks.mRecentsUid : -1;
                        windowManagerService.mRoot.forAllWindows(
                                new Consumer() { // from class:
                                    // com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda0
                                    @Override // java.util.function.Consumer
                                    public final void accept(Object obj) {
                                        ByteTransferPipe byteTransferPipe;
                                        final WindowManagerShellCommand windowManagerShellCommand =
                                                WindowManagerShellCommand.this;
                                        int i2 = i;
                                        ArrayList arrayList2 = arrayList;
                                        WindowState windowState = (WindowState) obj;
                                        windowManagerShellCommand.getClass();
                                        boolean z = windowState.mSession.mUid == i2;
                                        if (windowState.isVisible() || z) {
                                            ByteTransferPipe byteTransferPipe2 = null;
                                            try {
                                                byteTransferPipe = new ByteTransferPipe();
                                            } catch (RemoteException | IOException unused) {
                                            }
                                            try {
                                                final ParcelFileDescriptor writeFd =
                                                        byteTransferPipe.getWriteFd();
                                                final IWindow iWindow = windowState.mClient;
                                                if (iWindow instanceof IWindow.Stub) {
                                                    IoThread.getExecutor()
                                                            .execute(
                                                                    new Runnable() { // from class:
                                                                        // com.android.server.wm.WindowManagerShellCommand$$ExternalSyntheticLambda12
                                                                        @Override // java.lang.Runnable
                                                                        public final void run() {
                                                                            WindowManagerShellCommand
                                                                                    windowManagerShellCommand2 =
                                                                                            WindowManagerShellCommand
                                                                                                    .this;
                                                                            IWindow iWindow2 =
                                                                                    iWindow;
                                                                            ParcelFileDescriptor
                                                                                    parcelFileDescriptor =
                                                                                            writeFd;
                                                                            WindowManagerGlobalLock
                                                                                    windowManagerGlobalLock2 =
                                                                                            windowManagerShellCommand2
                                                                                                    .mInternal
                                                                                                    .mGlobalLock;
                                                                            WindowManagerService
                                                                                    .boostPriorityForLockedSection();
                                                                            synchronized (
                                                                                    windowManagerGlobalLock2) {
                                                                                try {
                                                                                    try {
                                                                                        iWindow2
                                                                                                .executeCommand(
                                                                                                        "DUMP_ENCODED",
                                                                                                        (String)
                                                                                                                null,
                                                                                                        parcelFileDescriptor);
                                                                                    } catch (
                                                                                            Exception
                                                                                                    e) {
                                                                                        e
                                                                                                .printStackTrace();
                                                                                    }
                                                                                } catch (
                                                                                        Throwable
                                                                                                th) {
                                                                                    WindowManagerService
                                                                                            .resetPriorityAfterLockedSection();
                                                                                    throw th;
                                                                                }
                                                                            }
                                                                            WindowManagerService
                                                                                    .resetPriorityAfterLockedSection();
                                                                        }
                                                                    });
                                                } else {
                                                    iWindow.executeCommand(
                                                            "DUMP_ENCODED", (String) null, writeFd);
                                                }
                                                arrayList2.add(
                                                        Pair.create(
                                                                windowState.getName(),
                                                                byteTransferPipe));
                                            } catch (RemoteException | IOException unused2) {
                                                byteTransferPipe2 = byteTransferPipe;
                                                if (byteTransferPipe2 != null) {
                                                    byteTransferPipe2.kill();
                                                }
                                            }
                                        }
                                    }
                                },
                                false);
                    } catch (Throwable th) {
                        WindowManagerService.resetPriorityAfterLockedSection();
                        throw th;
                    }
                }
                WindowManagerService.resetPriorityAfterLockedSection();
                Iterator it = arrayList.iterator();
                while (it.hasNext()) {
                    Pair pair = (Pair) it.next();
                    try {
                        byte[] bArr = ((ByteTransferPipe) pair.second).get();
                        zipOutputStream.putNextEntry(new ZipEntry((String) pair.first));
                        zipOutputStream.write(bArr);
                    } catch (IOException unused) {
                    }
                }
                zipOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            printWriter.println("Error fetching dump " + e.getMessage());
        }
    }

    public final void runGetIgnoreOrientationRequest(PrintWriter printWriter) {
        boolean z = false;
        int parseInt = "-d".equals(getNextArg()) ? Integer.parseInt(getNextArgRequired()) : 0;
        WindowManagerService windowManagerService = this.mInternal;
        WindowManagerGlobalLock windowManagerGlobalLock = windowManagerService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                DisplayContent displayContent =
                        windowManagerService.mRoot.getDisplayContent(parseInt);
                if (displayContent == null) {
                    Slog.w(
                            "WindowManager",
                            "Trying to getIgnoreOrientationRequest() for a missing display.");
                    WindowManagerService.resetPriorityAfterLockedSection();
                } else {
                    z = displayContent.getIgnoreOrientationRequest();
                    WindowManagerService.resetPriorityAfterLockedSection();
                }
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        printWriter.println("ignoreOrientationRequest " + z + " for displayId=" + parseInt);
    }

    public final void runGetLetterboxStyle(PrintWriter printWriter) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                printWriter.println(
                        "Corner radius: "
                                + this.mAppCompatConfiguration.mLetterboxActivityCornersRadius);
                printWriter.println(
                        "Horizontal position multiplier: "
                                + this.mAppCompatConfiguration
                                        .mLetterboxHorizontalPositionMultiplier);
                printWriter.println(
                        "Vertical position multiplier: "
                                + this.mAppCompatConfiguration
                                        .mLetterboxVerticalPositionMultiplier);
                printWriter.println(
                        "Horizontal position multiplier (book mode): "
                                + this.mAppCompatConfiguration
                                        .mLetterboxBookModePositionMultiplier);
                printWriter.println(
                        "Vertical position multiplier (tabletop mode): "
                                + this.mAppCompatConfiguration
                                        .mLetterboxTabletopModePositionMultiplier);
                printWriter.println(
                        "Horizontal position multiplier for reachability: "
                                + this.mAppCompatConfiguration
                                        .getHorizontalMultiplierForReachability(false));
                printWriter.println(
                        "Vertical position multiplier for reachability: "
                                + this.mAppCompatConfiguration.getVerticalMultiplierForReachability(
                                        false));
                printWriter.println(
                        "Aspect ratio: "
                                + this.mAppCompatConfiguration
                                        .mFixedOrientationLetterboxAspectRatio);
                printWriter.println(
                        "Default min aspect ratio for unresizable apps: "
                                + this.mAppCompatConfiguration
                                        .mDefaultMinAspectRatioForUnresizableApps);
                printWriter.println(
                        "Is horizontal reachability enabled: "
                                + this.mAppCompatConfiguration.mIsHorizontalReachabilityEnabled);
                printWriter.println(
                        "Is vertical reachability enabled: "
                                + this.mAppCompatConfiguration.mIsVerticalReachabilityEnabled);
                printWriter.println(
                        "Is automatic reachability in book mode enabled: "
                                + this.mAppCompatConfiguration
                                        .mIsAutomaticReachabilityInBookModeEnabled);
                printWriter.println(
                        "Default position for horizontal reachability: "
                                .concat(
                                        AppCompatConfiguration
                                                .letterboxHorizontalReachabilityPositionToString(
                                                        this.mAppCompatConfiguration
                                                                .mDefaultPositionForHorizontalReachability)));
                printWriter.println(
                        "Default position for vertical reachability: "
                                .concat(
                                        AppCompatConfiguration
                                                .letterboxVerticalReachabilityPositionToString(
                                                        this.mAppCompatConfiguration
                                                                .mDefaultPositionForVerticalReachability)));
                printWriter.println(
                        "Current position for horizontal reachability:"
                                .concat(
                                        AppCompatConfiguration
                                                .letterboxHorizontalReachabilityPositionToString(
                                                        this.mAppCompatConfiguration
                                                                .mAppCompatConfigurationPersister
                                                                .mLetterboxPositionForHorizontalReachability)));
                printWriter.println(
                        "Current position for vertical reachability:"
                                .concat(
                                        AppCompatConfiguration
                                                .letterboxVerticalReachabilityPositionToString(
                                                        this.mAppCompatConfiguration
                                                                .mAppCompatConfigurationPersister
                                                                .mLetterboxPositionForVerticalReachability)));
                printWriter.println(
                        "Is education enabled: "
                                + this.mAppCompatConfiguration.mIsEducationEnabled);
                printWriter.println(
                        "Is using split screen aspect ratio as aspect ratio for unresizable apps: "
                                + this.mAppCompatConfiguration
                                        .mIsSplitScreenAspectRatioForUnresizableAppsEnabled);
                printWriter.println(
                        "Is using display aspect ratio as aspect ratio for all letterboxed apps: "
                                + this.mAppCompatConfiguration
                                        .mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox);
                printWriter.println(
                        "    Is activity \"refresh\" in camera compatibility treatment enabled: "
                                + this.mAppCompatConfiguration
                                        .mIsCameraCompatTreatmentRefreshEnabled);
                printWriter.println(
                        "    Refresh using \"stopped -> resumed\" cycle: "
                                + this.mAppCompatConfiguration
                                        .mIsCameraCompatRefreshCycleThroughStopEnabled);
                printWriter.println(
                        "Background type: "
                                + AppCompatConfiguration.letterboxBackgroundTypeToString(
                                        this.mAppCompatConfiguration.getLetterboxBackgroundType()));
                printWriter.println(
                        "    Background color: "
                                + Integer.toHexString(
                                        this.mAppCompatConfiguration
                                                .getLetterboxBackgroundColor()
                                                .toArgb()));
                printWriter.println(
                        "    Wallpaper blur radius: "
                                + this.mAppCompatConfiguration
                                        .mLetterboxBackgroundWallpaperBlurRadiusPx);
                printWriter.println(
                        "    Wallpaper dark scrim alpha: "
                                + this.mAppCompatConfiguration
                                        .mLetterboxBackgroundWallpaperDarkScrimAlpha);
                printWriter.println(
                        "Is letterboxing for translucent activities enabled: "
                                + this.mAppCompatConfiguration.isTranslucentLetterboxingEnabled());
                printWriter.println(
                        "Is the user aspect ratio settings enabled: "
                                + this.mAppCompatConfiguration
                                        .isUserAppAspectRatioSettingsEnabled());
                printWriter.println(
                        "Is the fullscreen option in user aspect ratio settings enabled: "
                                + this.mAppCompatConfiguration
                                        .isUserAppAspectRatioFullscreenEnabled());
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void runGetMultiWindowConfig(PrintWriter printWriter) {
        WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mAtmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                printWriter.println(
                        "Supports non-resizable in multi window: "
                                + this.mInternal.mAtmService.mSupportsNonResizableMultiWindow);
                printWriter.println(
                        "Respects activity min width/height in multi window: "
                                + this.mInternal
                                        .mAtmService
                                        .mRespectsActivityMinWidthHeightMultiWindow);
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void runReset(PrintWriter printWriter) {
        int displayId = getDisplayId(getNextArg());
        this.mInterface.clearForcedDisplaySize(displayId);
        this.mInterface.clearForcedDisplayDensityForUser(displayId, -2);
        this.mInternal.setOverrideFoldedArea(new Rect());
        this.mInterface.setForcedDisplayScalingMode(displayId, 0);
        this.mInternal.thawDisplayRotation(displayId, "WindowManagerShellCommand#runReset");
        this.mInterface.setFixedToUserRotation(displayId, 0);
        this.mInterface.setIgnoreOrientationRequest(displayId, false);
        if (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY) {
            this.mInternal.setNullableIgnoreOrientationRequest(displayId, null);
        }
        resetLetterboxStyle();
        this.mInternal.setSandboxDisplayApis(displayId, true);
        runResetMultiWindowConfig();
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                new StringBuilder("Reset all settings for displayId="), displayId, printWriter);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:10:0x0022. Please report as an issue. */
    public final int runResetLetterboxStyle() {
        char c;
        if (peekNextArg() == null) {
            resetLetterboxStyle();
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            while (peekNextArg() != null) {
                try {
                    String nextArg = getNextArg();
                    switch (nextArg.hashCode()) {
                        case -2064669968:
                            if (nextArg.equals("wallpaperDarkScrimAlpha")) {
                                c = 6;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1229148346:
                            if (nextArg.equals(
                                    "IsDisplayAspectRatioEnabledForFixedOrientationLetterbox")) {
                                c = 17;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1135892646:
                            if (nextArg.equals("defaultPositionForHorizontalReachability")) {
                                c = 11;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1124529213:
                            if (nextArg.equals("isEducationEnabled")) {
                                c = 15;
                                break;
                            }
                            c = 65535;
                            break;
                        case -1034497596:
                            if (nextArg.equals("persistentPositionForHorizontalReachability")) {
                                c = '\r';
                                break;
                            }
                            c = 65535;
                            break;
                        case -567226646:
                            if (nextArg.equals("minAspectRatioForUnresizable")) {
                                c = 1;
                                break;
                            }
                            c = 65535;
                            break;
                        case -563782898:
                            if (nextArg.equals("horizontalPositionMultiplier")) {
                                c = 7;
                                break;
                            }
                            c = 65535;
                            break;
                        case -559641828:
                            if (nextArg.equals("isHorizontalReachabilityEnabled")) {
                                c = '\t';
                                break;
                            }
                            c = 65535;
                            break;
                        case -39374981:
                            if (nextArg.equals("wallpaperBlurRadius")) {
                                c = 5;
                                break;
                            }
                            c = 65535;
                            break;
                        case 208616300:
                            if (nextArg.equals("defaultPositionForVerticalReachability")) {
                                c = '\f';
                                break;
                            }
                            c = 65535;
                            break;
                        case 583595847:
                            if (nextArg.equals("cornerRadius")) {
                                c = 2;
                                break;
                            }
                            c = 65535;
                            break;
                        case 691402838:
                            if (nextArg.equals("persistentPositionForVerticalReachability")) {
                                c = 14;
                                break;
                            }
                            c = 65535;
                            break;
                        case 814923786:
                            if (nextArg.equals("isVerticalReachabilityEnabled")) {
                                c = '\n';
                                break;
                            }
                            c = 65535;
                            break;
                        case 883700309:
                            if (nextArg.equals("isTranslucentLetterboxingEnabled")) {
                                c = 18;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1092174483:
                            if (nextArg.equals("aspectRatio")) {
                                c = 0;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1109312992:
                            if (nextArg.equals("verticalPositionMultiplier")) {
                                c = '\b';
                                break;
                            }
                            c = 65535;
                            break;
                        case 1287124693:
                            if (nextArg.equals("backgroundColor")) {
                                c = 4;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1396867991:
                            if (nextArg.equals("isCameraCompatRefreshEnabled")) {
                                c = 21;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1427509640:
                            if (nextArg.equals("backgroundType")) {
                                c = 3;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1869151343:
                            if (nextArg.equals(
                                    "isSplitScreenAspectRatioForUnresizableAppsEnabled")) {
                                c = 16;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1870284982:
                            if (nextArg.equals("isCameraCompatRefreshCycleThroughStopEnabled")) {
                                c = 22;
                                break;
                            }
                            c = 65535;
                            break;
                        case 1892753783:
                            if (nextArg.equals("isUserAppAspectRatioSettingsEnabled")) {
                                c = 19;
                                break;
                            }
                            c = 65535;
                            break;
                        case 2102105247:
                            if (nextArg.equals("isUserAppAspectRatioFullscreenEnabled")) {
                                c = 20;
                                break;
                            }
                            c = 65535;
                            break;
                        default:
                            c = 65535;
                            break;
                    }
                    switch (c) {
                        case 0:
                            AppCompatConfiguration appCompatConfiguration =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration.mFixedOrientationLetterboxAspectRatio =
                                    appCompatConfiguration
                                            .mContext
                                            .getResources()
                                            .getFloat(
                                                    R.dimen.config_pictureInPictureMinAspectRatio);
                            break;
                        case 1:
                            AppCompatConfiguration appCompatConfiguration2 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration2.mDefaultMinAspectRatioForUnresizableApps =
                                    appCompatConfiguration2
                                            .mContext
                                            .getResources()
                                            .getFloat(R.dimen.config_screenBrightnessDozeFloat);
                            break;
                        case 2:
                            AppCompatConfiguration appCompatConfiguration3 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration3.mLetterboxActivityCornersRadius =
                                    appCompatConfiguration3
                                            .mContext
                                            .getResources()
                                            .getInteger(
                                                    R.integer
                                                            .config_minNumVisibleRecentTasks_lowRam);
                            break;
                        case 3:
                            this.mAppCompatConfiguration.mLetterboxBackgroundTypeOverride = -1;
                            break;
                        case 4:
                            AppCompatConfiguration appCompatConfiguration4 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration4.mLetterboxBackgroundColorOverride = null;
                            appCompatConfiguration4.mLetterboxBackgroundColorResourceIdOverride =
                                    null;
                            break;
                        case 5:
                            AppCompatConfiguration appCompatConfiguration5 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration5.mLetterboxBackgroundWallpaperBlurRadiusPx =
                                    appCompatConfiguration5
                                            .mContext
                                            .getResources()
                                            .getDimensionPixelSize(
                                                    R.dimen
                                                            .config_rotaryEncoderAxisScrollTickInterval);
                            break;
                        case 6:
                            AppCompatConfiguration appCompatConfiguration6 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration6.mLetterboxBackgroundWallpaperDarkScrimAlpha =
                                    appCompatConfiguration6
                                            .mContext
                                            .getResources()
                                            .getFloat(R.dimen.config_resActivitySnapshotScale);
                            break;
                        case 7:
                            AppCompatConfiguration appCompatConfiguration7 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration7.mLetterboxHorizontalPositionMultiplier =
                                    appCompatConfiguration7
                                            .mContext
                                            .getResources()
                                            .getFloat(
                                                    R.dimen
                                                            .config_screenBrightnessMinimumDimAmountFloat);
                            break;
                        case '\b':
                            AppCompatConfiguration appCompatConfiguration8 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration8.mLetterboxVerticalPositionMultiplier =
                                    appCompatConfiguration8
                                            .mContext
                                            .getResources()
                                            .getFloat(
                                                    R.dimen
                                                            .config_screen_magnification_scaling_threshold);
                            break;
                        case '\t':
                            AppCompatConfiguration appCompatConfiguration9 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration9.mIsHorizontalReachabilityEnabled =
                                    appCompatConfiguration9
                                            .mContext
                                            .getResources()
                                            .getBoolean(
                                                    R.bool.config_magnification_always_on_enabled);
                            break;
                        case '\n':
                            AppCompatConfiguration appCompatConfiguration10 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration10.mIsVerticalReachabilityEnabled =
                                    appCompatConfiguration10
                                            .mContext
                                            .getResources()
                                            .getBoolean(R.bool.config_maskMainBuiltInDisplayCutout);
                            break;
                        case 11:
                            AppCompatConfiguration appCompatConfiguration11 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration11.mDefaultPositionForHorizontalReachability =
                                    AppCompatConfiguration
                                            .readLetterboxHorizontalReachabilityPositionFromConfig(
                                                    appCompatConfiguration11.mContext, false);
                            break;
                        case '\f':
                            AppCompatConfiguration appCompatConfiguration12 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration12.mDefaultPositionForVerticalReachability =
                                    AppCompatConfiguration
                                            .readLetterboxVerticalReachabilityPositionFromConfig(
                                                    appCompatConfiguration12.mContext, false);
                            break;
                        case '\r':
                            AppCompatConfiguration appCompatConfiguration13 =
                                    this.mAppCompatConfiguration;
                            int readLetterboxHorizontalReachabilityPositionFromConfig =
                                    AppCompatConfiguration
                                            .readLetterboxHorizontalReachabilityPositionFromConfig(
                                                    appCompatConfiguration13.mContext, false);
                            AppCompatConfigurationPersister appCompatConfigurationPersister =
                                    appCompatConfiguration13.mAppCompatConfigurationPersister;
                            appCompatConfigurationPersister
                                    .setLetterboxPositionForHorizontalReachability(
                                            readLetterboxHorizontalReachabilityPositionFromConfig,
                                            false);
                            appCompatConfigurationPersister
                                    .setLetterboxPositionForHorizontalReachability(
                                            AppCompatConfiguration
                                                    .readLetterboxHorizontalReachabilityPositionFromConfig(
                                                            appCompatConfiguration13.mContext,
                                                            true),
                                            true);
                            break;
                        case 14:
                            AppCompatConfiguration appCompatConfiguration14 =
                                    this.mAppCompatConfiguration;
                            int readLetterboxVerticalReachabilityPositionFromConfig =
                                    AppCompatConfiguration
                                            .readLetterboxVerticalReachabilityPositionFromConfig(
                                                    appCompatConfiguration14.mContext, false);
                            AppCompatConfigurationPersister appCompatConfigurationPersister2 =
                                    appCompatConfiguration14.mAppCompatConfigurationPersister;
                            appCompatConfigurationPersister2
                                    .setLetterboxPositionForVerticalReachability(
                                            readLetterboxVerticalReachabilityPositionFromConfig,
                                            false);
                            appCompatConfigurationPersister2
                                    .setLetterboxPositionForVerticalReachability(
                                            AppCompatConfiguration
                                                    .readLetterboxVerticalReachabilityPositionFromConfig(
                                                            appCompatConfiguration14.mContext,
                                                            true),
                                            true);
                            break;
                        case 15:
                            AppCompatConfiguration appCompatConfiguration15 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration15.mIsEducationEnabled =
                                    appCompatConfiguration15
                                            .mContext
                                            .getResources()
                                            .getBoolean(
                                                    R.bool.config_lowPowerStandbyEnabledByDefault);
                            break;
                        case 16:
                            AppCompatConfiguration appCompatConfiguration16 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration16
                                            .mIsSplitScreenAspectRatioForUnresizableAppsEnabled =
                                    appCompatConfiguration16
                                            .mContext
                                            .getResources()
                                            .getBoolean(R.bool.config_mainBuiltInDisplayIsRound);
                            break;
                        case 17:
                            AppCompatConfiguration appCompatConfiguration17 =
                                    this.mAppCompatConfiguration;
                            appCompatConfiguration17
                                            .mIsDisplayAspectRatioEnabledForFixedOrientationLetterbox =
                                    appCompatConfiguration17
                                            .mContext
                                            .getResources()
                                            .getBoolean(
                                                    R.bool
                                                            .config_lockscreenWeatherEnabledByDefault);
                            break;
                        case 18:
                            this.mAppCompatConfiguration.mTranslucentLetterboxingOverrideEnabled =
                                    false;
                            break;
                        case 19:
                            this.mAppCompatConfiguration
                                            .mUserAppAspectRatioSettingsOverrideEnabled =
                                    false;
                            break;
                        case 20:
                            this.mAppCompatConfiguration
                                            .mUserAppAspectRatioFullscreenOverrideEnabled =
                                    false;
                            break;
                        case 21:
                            this.mAppCompatConfiguration.mIsCameraCompatTreatmentRefreshEnabled =
                                    true;
                            break;
                        case 22:
                            this.mAppCompatConfiguration
                                            .mIsCameraCompatRefreshCycleThroughStopEnabled =
                                    true;
                            break;
                        default:
                            getErrPrintWriter()
                                    .println(
                                            "Error: Unrecognized letterbox style option: "
                                                    + nextArg);
                            WindowManagerService.resetPriorityAfterLockedSection();
                            return -1;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
            return 0;
        }
    }

    public final void runResetMultiWindowConfig() {
        int integer =
                this.mInternal
                        .mContext
                        .getResources()
                        .getInteger(R.integer.device_idle_location_accuracy);
        int integer2 =
                this.mInternal
                        .mContext
                        .getResources()
                        .getInteger(R.integer.config_soundEffectVolumeDb);
        WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mAtmService.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                ActivityTaskManagerService activityTaskManagerService = this.mInternal.mAtmService;
                activityTaskManagerService.mSupportsNonResizableMultiWindow = integer;
                activityTaskManagerService.mRespectsActivityMinWidthHeightMultiWindow = integer2;
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void runSetAppCompatConfigurationPreset() {
        try {
            int parseInt = Integer.parseInt(getNextArgRequired());
            WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    updateAppCompatConfigurationPreset(parseInt);
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } catch (NumberFormatException e) {
            getErrPrintWriter().println("Error: Preset format " + e);
        } catch (IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: Preset should be provided as an argument " + e2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetBlurDisabled(PrintWriter printWriter) {
        char c;
        int i = 1;
        String nextArg = getNextArg();
        if (nextArg == null) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Blur supported on device: "),
                            CrossWindowBlurListeners.CROSS_WINDOW_BLUR_SUPPORTED,
                            printWriter,
                            "Blur enabled: "),
                    this.mInternal.mBlurController.mBlurEnabled,
                    printWriter);
            return 0;
        }
        switch (nextArg.hashCode()) {
            case 48:
                if (nextArg.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (nextArg.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3569038:
                if (nextArg.equals("true")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (nextArg.equals("false")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 3:
                i = 0;
                break;
            case 1:
            case 2:
                break;
            default:
                getErrPrintWriter()
                        .println("Error: expected true, 1, false, 0, but got ".concat(nextArg));
                return -1;
        }
        Settings.Global.putInt(
                this.mInternal.mContext.getContentResolver(), "disable_window_blurs", i);
        return 0;
    }

    public final void runSetBooleanFlag(Consumer consumer) {
        boolean z = true;
        String nextArg = getNextArg();
        if (nextArg == null) {
            getErrPrintWriter().println("Error: expected true, 1, false, 0, but got empty input.");
            return;
        }
        switch (nextArg) {
            case "0":
            case "false":
                z = false;
                break;
            case "1":
            case "true":
                break;
            default:
                getErrPrintWriter()
                        .println("Error: expected true, 1, false, 0, but got ".concat(nextArg));
                return;
        }
        WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
        WindowManagerService.boostPriorityForLockedSection();
        synchronized (windowManagerGlobalLock) {
            try {
                consumer.accept(Boolean.valueOf(z));
            } catch (Throwable th) {
                WindowManagerService.resetPriorityAfterLockedSection();
                throw th;
            }
        }
        WindowManagerService.resetPriorityAfterLockedSection();
    }

    public final void runSetFixedOrientationLetterboxAspectRatio() {
        try {
            float parseFloat = Float.parseFloat(getNextArgRequired());
            WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAppCompatConfiguration.mFixedOrientationLetterboxAspectRatio = parseFloat;
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } catch (NumberFormatException e) {
            getErrPrintWriter().println("Error: bad aspect ratio format " + e);
        } catch (IllegalArgumentException e2) {
            getErrPrintWriter()
                    .println("Error: aspect ratio should be provided as an argument " + e2);
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runSetIgnoreOrientationRequest() {
        int i;
        char c;
        boolean z = true;
        String nextArgRequired = getNextArgRequired();
        if ("-d".equals(nextArgRequired)) {
            i = Integer.parseInt(getNextArgRequired());
            nextArgRequired = getNextArgRequired();
        } else {
            i = 0;
        }
        nextArgRequired.getClass();
        switch (nextArgRequired.hashCode()) {
            case 48:
                if (nextArgRequired.equals("0")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 49:
                if (nextArgRequired.equals("1")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 3569038:
                if (nextArgRequired.equals("true")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 97196323:
                if (nextArgRequired.equals("false")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 108404047:
                if (nextArgRequired.equals("reset")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 3:
                z = false;
            case 1:
            case 2:
                this.mInterface.setIgnoreOrientationRequest(i, z);
                return 0;
            case 4:
                if (CoreRune.MT_APP_COMPAT_ORIENTATION_POLICY) {
                    this.mInternal.setNullableIgnoreOrientationRequest(i, null);
                    return 0;
                }
            default:
                getErrPrintWriter()
                        .println(
                                "Error: expecting true, 1, false, 0, but we get "
                                        .concat(nextArgRequired));
                return -1;
        }
    }

    public final void runSetLetterboxActivityCornersRadius() {
        try {
            int parseInt = Integer.parseInt(getNextArgRequired());
            WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAppCompatConfiguration.mLetterboxActivityCornersRadius = parseInt;
                    if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
                        updateAppCompatConfigurationPreset(0);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } catch (NumberFormatException e) {
            getErrPrintWriter().println("Error: bad corners radius format " + e);
        } catch (IllegalArgumentException e2) {
            getErrPrintWriter()
                    .println("Error: corners radius should be provided as an argument " + e2);
        }
    }

    public final void runSetLetterboxBackgroundColor() {
        try {
            Color valueOf = Color.valueOf(Color.parseColor(getNextArgRequired()));
            WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAppCompatConfiguration.mLetterboxBackgroundColorOverride = valueOf;
                    if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
                        updateAppCompatConfigurationPreset(0);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } catch (IllegalArgumentException e) {
            getErrPrintWriter()
                    .println(
                            "Error: color in #RRGGBB format should be provided as an argument "
                                    + e);
        }
    }

    public final void runSetLetterboxBackgroundType() {
        char c;
        try {
            String nextArgRequired = getNextArgRequired();
            int i = 2;
            switch (nextArgRequired.hashCode()) {
                case -1700528003:
                    if (nextArgRequired.equals("app_color_background_floating")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -231186968:
                    if (nextArgRequired.equals("app_color_background")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1216433359:
                    if (nextArgRequired.equals("solid_color")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1474694658:
                    if (nextArgRequired.equals("wallpaper")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                i = 0;
            } else if (c == 1) {
                i = 1;
            } else if (c != 2) {
                if (c != 3) {
                    getErrPrintWriter()
                            .println(
                                    "Error: 'solid_color', 'app_color_background' or 'wallpaper'"
                                        + " should be provided as an argument");
                    return;
                }
                i = 3;
            }
            WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAppCompatConfiguration.mLetterboxBackgroundTypeOverride = i;
                    if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
                        updateAppCompatConfigurationPreset(0);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } catch (IllegalArgumentException e) {
            getErrPrintWriter()
                    .println(
                            "Error: 'solid_color', 'app_color_background' or 'wallpaper' should be"
                                + " provided as an argument"
                                    + e);
        }
    }

    public final void runSetLetterboxBackgroundWallpaperBlurRadius() {
        try {
            int parseInt = Integer.parseInt(getNextArgRequired());
            WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAppCompatConfiguration.mLetterboxBackgroundWallpaperBlurRadiusPx =
                            (int)
                                    TypedValue.applyDimension(
                                            1,
                                            parseInt,
                                            this.mInternal
                                                    .mContext
                                                    .getResources()
                                                    .getDisplayMetrics());
                    if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
                        updateAppCompatConfigurationPreset(0);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } catch (NumberFormatException e) {
            getErrPrintWriter().println("Error: blur radius format " + e);
        } catch (IllegalArgumentException e2) {
            getErrPrintWriter()
                    .println("Error: blur radius should be provided as an argument " + e2);
        }
    }

    public final void runSetLetterboxBackgroundWallpaperDarkScrimAlpha() {
        try {
            float parseFloat = Float.parseFloat(getNextArgRequired());
            WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    this.mAppCompatConfiguration.mLetterboxBackgroundWallpaperDarkScrimAlpha =
                            parseFloat;
                    if (CoreRune.MT_APP_COMPAT_CONFIGURATION) {
                        updateAppCompatConfigurationPreset(0);
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } catch (NumberFormatException e) {
            getErrPrintWriter().println("Error: bad alpha format " + e);
        } catch (IllegalArgumentException e2) {
            getErrPrintWriter().println("Error: alpha should be provided as an argument " + e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runSetLetterboxDefaultPositionForHorizontalReachability() {
        /*
            r6 = this;
            java.lang.String r0 = r6.getNextArgRequired()     // Catch: java.lang.IllegalArgumentException -> L26
            int r1 = r0.hashCode()     // Catch: java.lang.IllegalArgumentException -> L26
            r2 = -1364013995(0xffffffffaeb2cc55, float:-8.1307995E-11)
            r3 = 1
            r4 = 0
            r5 = 2
            if (r1 == r2) goto L32
            r2 = 3317767(0x32a007, float:4.649182E-39)
            if (r1 == r2) goto L28
            r2 = 108511772(0x677c21c, float:4.6598146E-35)
            if (r1 == r2) goto L1b
            goto L3c
        L1b:
            java.lang.String r1 = "right"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r5
            goto L3d
        L26:
            r0 = move-exception
            goto L67
        L28:
            java.lang.String r1 = "left"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r4
            goto L3d
        L32:
            java.lang.String r1 = "center"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r3
            goto L3d
        L3c:
            r0 = -1
        L3d:
            if (r0 == 0) goto L4f
            if (r0 == r3) goto L50
            if (r0 == r5) goto L4d
            java.io.PrintWriter r0 = r6.getErrPrintWriter()     // Catch: java.lang.IllegalArgumentException -> L26
            java.lang.String r1 = "Error: 'left', 'center' or 'right' are expected as an argument"
            r0.println(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            return
        L4d:
            r3 = r5
            goto L50
        L4f:
            r3 = r4
        L50:
            com.android.server.wm.WindowManagerService r0 = r6.mInternal
            com.android.server.wm.WindowManagerGlobalLock r0 = r0.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r0)
            com.android.server.wm.AppCompatConfiguration r6 = r6.mAppCompatConfiguration     // Catch: java.lang.Throwable -> L61
            r6.mDefaultPositionForHorizontalReachability = r3     // Catch: java.lang.Throwable -> L61
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L61
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return
        L61:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L61
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r6
        L67:
            java.io.PrintWriter r6 = r6.getErrPrintWriter()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Error: 'left', 'center' or 'right' are expected as an argument"
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r6.println(r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowManagerShellCommand.runSetLetterboxDefaultPositionForHorizontalReachability():void");
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runSetLetterboxDefaultPositionForVerticalReachability() {
        /*
            r6 = this;
            java.lang.String r0 = r6.getNextArgRequired()     // Catch: java.lang.IllegalArgumentException -> L26
            int r1 = r0.hashCode()     // Catch: java.lang.IllegalArgumentException -> L26
            r2 = -1383228885(0xffffffffad8d9a2b, float:-1.6098308E-11)
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == r2) goto L32
            r2 = -1364013995(0xffffffffaeb2cc55, float:-8.1307995E-11)
            if (r1 == r2) goto L28
            r2 = 115029(0x1c155, float:1.6119E-40)
            if (r1 == r2) goto L1b
            goto L3c
        L1b:
            java.lang.String r1 = "top"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r5
            goto L3d
        L26:
            r0 = move-exception
            goto L67
        L28:
            java.lang.String r1 = "center"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r4
            goto L3d
        L32:
            java.lang.String r1 = "bottom"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r3
            goto L3d
        L3c:
            r0 = -1
        L3d:
            if (r0 == 0) goto L4f
            if (r0 == r4) goto L4d
            if (r0 == r3) goto L50
            java.io.PrintWriter r0 = r6.getErrPrintWriter()     // Catch: java.lang.IllegalArgumentException -> L26
            java.lang.String r1 = "Error: 'top', 'center' or 'bottom' are expected as an argument"
            r0.println(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            return
        L4d:
            r3 = r4
            goto L50
        L4f:
            r3 = r5
        L50:
            com.android.server.wm.WindowManagerService r0 = r6.mInternal
            com.android.server.wm.WindowManagerGlobalLock r0 = r0.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r0)
            com.android.server.wm.AppCompatConfiguration r6 = r6.mAppCompatConfiguration     // Catch: java.lang.Throwable -> L61
            r6.mDefaultPositionForVerticalReachability = r3     // Catch: java.lang.Throwable -> L61
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L61
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return
        L61:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L61
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r6
        L67:
            java.io.PrintWriter r6 = r6.getErrPrintWriter()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Error: 'top', 'center' or 'bottom' are expected as an argument"
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r6.println(r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowManagerShellCommand.runSetLetterboxDefaultPositionForVerticalReachability():void");
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0319  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0335 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x032c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int runSetLetterboxStyle() {
        /*
            Method dump skipped, instructions count: 1196
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowManagerShellCommand.runSetLetterboxStyle():int");
    }

    public final void runSetLetterboxVerticalPositionMultiplier() {
        try {
            float parseFloat = Float.parseFloat(getNextArgRequired());
            WindowManagerGlobalLock windowManagerGlobalLock = this.mInternal.mGlobalLock;
            WindowManagerService.boostPriorityForLockedSection();
            synchronized (windowManagerGlobalLock) {
                try {
                    try {
                        AppCompatConfiguration appCompatConfiguration =
                                this.mAppCompatConfiguration;
                        appCompatConfiguration.getClass();
                        AppCompatConfiguration.assertValidMultiplier(
                                parseFloat, "mLetterboxVerticalPositionMultiplier");
                        appCompatConfiguration.mLetterboxVerticalPositionMultiplier = parseFloat;
                    } catch (IllegalArgumentException e) {
                        getErrPrintWriter().println("Error: invalid multiplier value " + e);
                        WindowManagerService.resetPriorityAfterLockedSection();
                        return;
                    }
                } catch (Throwable th) {
                    WindowManagerService.resetPriorityAfterLockedSection();
                    throw th;
                }
            }
            WindowManagerService.resetPriorityAfterLockedSection();
        } catch (NumberFormatException e2) {
            getErrPrintWriter().println("Error: bad multiplier format " + e2);
        } catch (IllegalArgumentException e3) {
            getErrPrintWriter()
                    .println("Error: multiplier should be provided as an argument " + e3);
        }
    }

    public final int runSetMultiWindowConfig() {
        if (peekNextArg() == null) {
            getErrPrintWriter().println("Error: No arguments provided.");
        }
        int i = 0;
        while (true) {
            int i2 = -1;
            if (peekNextArg() == null) {
                return i == 0 ? 0 : -1;
            }
            String nextArg = getNextArg();
            nextArg.getClass();
            if (nextArg.equals("--supportsNonResizable")) {
                String nextArg2 = getNextArg();
                if (nextArg2.equals("-1") || nextArg2.equals("0") || nextArg2.equals("1")) {
                    int parseInt = Integer.parseInt(nextArg2);
                    WindowManagerGlobalLock windowManagerGlobalLock =
                            this.mInternal.mAtmService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock) {
                        try {
                            this.mInternal.mAtmService.mSupportsNonResizableMultiWindow = parseInt;
                        } finally {
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    i2 = 0;
                } else {
                    getErrPrintWriter()
                            .println(
                                    "Error: a config value of [-1, 0, 1] must be provided as an"
                                        + " argument for supportsNonResizableMultiWindow");
                }
            } else {
                if (!nextArg.equals("--respectsActivityMinWidthHeight")) {
                    getErrPrintWriter()
                            .println("Error: Unrecognized multi window option: ".concat(nextArg));
                    return -1;
                }
                String nextArg3 = getNextArg();
                if (nextArg3.equals("-1") || nextArg3.equals("0") || nextArg3.equals("1")) {
                    int parseInt2 = Integer.parseInt(nextArg3);
                    WindowManagerGlobalLock windowManagerGlobalLock2 =
                            this.mInternal.mAtmService.mGlobalLock;
                    WindowManagerService.boostPriorityForLockedSection();
                    synchronized (windowManagerGlobalLock2) {
                        try {
                            this.mInternal.mAtmService.mRespectsActivityMinWidthHeightMultiWindow =
                                    parseInt2;
                        } finally {
                        }
                    }
                    WindowManagerService.resetPriorityAfterLockedSection();
                    i2 = 0;
                } else {
                    getErrPrintWriter()
                            .println(
                                    "Error: a config value of [-1, 0, 1] must be provided as an"
                                        + " argument for"
                                        + " respectsActivityMinWidthHeightMultiWindow");
                }
            }
            i += i2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0058 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x004f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void runSetPersistentLetterboxPositionForVerticalReachability() {
        /*
            r6 = this;
            java.lang.String r0 = r6.getNextArgRequired()     // Catch: java.lang.IllegalArgumentException -> L26
            int r1 = r0.hashCode()     // Catch: java.lang.IllegalArgumentException -> L26
            r2 = -1383228885(0xffffffffad8d9a2b, float:-1.6098308E-11)
            r3 = 2
            r4 = 1
            r5 = 0
            if (r1 == r2) goto L32
            r2 = -1364013995(0xffffffffaeb2cc55, float:-8.1307995E-11)
            if (r1 == r2) goto L28
            r2 = 115029(0x1c155, float:1.6119E-40)
            if (r1 == r2) goto L1b
            goto L3c
        L1b:
            java.lang.String r1 = "top"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r5
            goto L3d
        L26:
            r0 = move-exception
            goto L6a
        L28:
            java.lang.String r1 = "center"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r4
            goto L3d
        L32:
            java.lang.String r1 = "bottom"
            boolean r0 = r0.equals(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            if (r0 == 0) goto L3c
            r0 = r3
            goto L3d
        L3c:
            r0 = -1
        L3d:
            if (r0 == 0) goto L4f
            if (r0 == r4) goto L4d
            if (r0 == r3) goto L50
            java.io.PrintWriter r0 = r6.getErrPrintWriter()     // Catch: java.lang.IllegalArgumentException -> L26
            java.lang.String r1 = "Error: 'top', 'center' or 'bottom' are expected as an argument"
            r0.println(r1)     // Catch: java.lang.IllegalArgumentException -> L26
            return
        L4d:
            r3 = r4
            goto L50
        L4f:
            r3 = r5
        L50:
            com.android.server.wm.WindowManagerService r0 = r6.mInternal
            com.android.server.wm.WindowManagerGlobalLock r0 = r0.mGlobalLock
            com.android.server.wm.WindowManagerService.boostPriorityForLockedSection()
            monitor-enter(r0)
            com.android.server.wm.AppCompatConfiguration r6 = r6.mAppCompatConfiguration     // Catch: java.lang.Throwable -> L64
            com.android.server.wm.AppCompatConfigurationPersister r6 = r6.mAppCompatConfigurationPersister     // Catch: java.lang.Throwable -> L64
            r6.setLetterboxPositionForVerticalReachability(r3, r5)     // Catch: java.lang.Throwable -> L64
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L64
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            return
        L64:
            r6 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L64
            com.android.server.wm.WindowManagerService.resetPriorityAfterLockedSection()
            throw r6
        L6a:
            java.io.PrintWriter r6 = r6.getErrPrintWriter()
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "Error: 'top', 'center' or 'bottom' are expected as an argument"
            r1.<init>(r2)
            r1.append(r0)
            java.lang.String r0 = r1.toString()
            r6.println(r0)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.WindowManagerShellCommand.runSetPersistentLetterboxPositionForVerticalReachability():void");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final int runWmShellCommand(PrintWriter printWriter) {
        char c;
        String nextArg = getNextArg();
        int hashCode = nextArg.hashCode();
        if (hashCode != -1067396926) {
            if (hashCode == 3198785) {
                nextArg.equals("help");
            }
        } else if (nextArg.equals("tracing")) {
            String nextArg2 = getNextArg();
            nextArg2.getClass();
            switch (nextArg2.hashCode()) {
                case -390772652:
                    if (nextArg2.equals("save-for-bugreport")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 3540994:
                    if (nextArg2.equals("stop")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 109757538:
                    if (nextArg2.equals("start")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    this.mInternal.mTransitionTracer.saveForBugreport(printWriter);
                    break;
                case 1:
                    this.mInternal.mTransitionTracer.stopTrace(printWriter);
                    break;
                case 2:
                    this.mInternal.mTransitionTracer.startTrace(printWriter);
                    break;
                default:
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            getErrPrintWriter(),
                            "Error: expected 'start' or 'stop', but got '",
                            nextArg2,
                            "'");
                    return -1;
            }
            return 0;
        }
        BatteryService$$ExternalSyntheticOutline0.m(
                printWriter,
                "Window Manager Shell commands:",
                "  help",
                "    Print this help text.",
                "  tracing <start/stop>");
        printWriter.println("    Start/stop shell transition tracing.");
        return 0;
    }

    public final void updateAppCompatConfigurationPreset(int i) {
        this.mInternal.mAtmService.updateAppCompatConfigurationPreset(i);
    }
}
