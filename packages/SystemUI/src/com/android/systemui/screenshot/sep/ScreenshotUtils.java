package com.android.systemui.screenshot.sep;

import android.R;
import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Insets;
import android.hardware.display.DisplayManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune$$ExternalSyntheticOutline0;
import com.android.systemui.aod.AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.List;

/* loaded from: classes2.dex */
public class ScreenshotUtils {
    /* JADX WARN: Removed duplicated region for block: B:18:0x0033  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Display getDisplay(int i, Context context) {
        Display display;
        if (BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "LARGESCREEN") && i == 1) {
            Display[] displays = ((DisplayManager) context.getSystemService("display")).getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            if (displays != null) {
                int length = displays.length;
                for (int i2 = 0; i2 < length; i2++) {
                    display = displays[i2];
                    if (display != null && display.getDisplayId() == 1) {
                        break;
                    }
                }
                display = null;
                if (display != null) {
                    Log.i("Screenshot", "getDisplay: subDisplay's rotation=" + display.getRotation());
                    return display;
                }
            } else {
                display = null;
                if (display != null) {
                }
            }
        }
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        Display display2 = displayManager.getDisplay(i);
        return display2 != null ? display2 : displayManager.getDisplay(0);
    }

    public static int getNavBarPosition(Context context, int i, boolean z) {
        Insets insetsIgnoringVisibility = ((WindowManager) context.getApplicationContext().getSystemService("window")).getCurrentWindowMetrics().getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars());
        if (i != 0) {
            int i2 = insetsIgnoringVisibility.left;
            if (i2 != 0 && i2 >= i) {
                return 1;
            }
            int i3 = insetsIgnoringVisibility.right;
            if (i3 != 0 && i3 >= i) {
                return 2;
            }
            int i4 = insetsIgnoringVisibility.top;
            if (i4 != 0) {
                if (z) {
                    return Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) != 0 ? 4 : 8;
                }
                if (i4 >= i) {
                    return 8;
                }
            }
        }
        return 4;
    }

    public static String[] getScreenshotSaveInfo(Context context) {
        String stringForUser = Settings.System.getStringForUser(context.getContentResolver(), "screenshot_current_save_dir", 0);
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getScreenshotSaveInfoDB() saveInfo: ", stringForUser, "Screenshot");
        String str = "external_primary:DCIM/Screenshots";
        if (stringForUser == null) {
            stringForUser = "external_primary:DCIM/Screenshots";
        }
        if (MediaStore.getExternalVolumeNames(context).contains(stringForUser.split(":")[0].toLowerCase())) {
            str = stringForUser;
        } else {
            EmergencyButtonController$$ExternalSyntheticOutline0.m("setScreenshotSaveInfoDB() isSuccess: ", "Screenshot", Settings.System.putStringForUser(context.getContentResolver(), "screenshot_current_save_dir", "external_primary:DCIM/Screenshots", 0));
        }
        return str.split(":", 2);
    }

    public static String getTopMostApplicationPackage(Context context) {
        List<ActivityManager.RunningTaskInfo> runningTasks;
        if (context == null || (runningTasks = ((ActivityManager) context.getSystemService("activity")).getRunningTasks(2)) == null || runningTasks.size() <= 0) {
            return null;
        }
        for (int i = 0; i < runningTasks.size(); i++) {
            ActivityManager.RunningTaskInfo runningTaskInfo = runningTasks.get(i);
            if (runningTaskInfo.getDisplayId() == context.getDisplayId()) {
                return runningTaskInfo.topActivity.getPackageName();
            }
        }
        return null;
    }

    public static boolean isDesktopMode(Context context) {
        boolean z = false;
        if (context == null) {
            Log.d("Screenshot", "isDesktopMode :: context is null!");
            return false;
        }
        SemDesktopModeManager semDesktopModeManager = (SemDesktopModeManager) context.getSystemService("desktopmode");
        if (semDesktopModeManager == null) {
            Log.d("Screenshot", "isDesktopMode :: desktopModeManager is null!");
            return false;
        }
        SemDesktopModeState desktopModeState = semDesktopModeManager.getDesktopModeState();
        if (desktopModeState != null && desktopModeState.enabled == 4) {
            z = true;
        }
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isDesktopMode :: isDeXMode=", "Screenshot", z);
        return z;
    }

    public static boolean isExcludeSystemUI(Context context) {
        boolean z = Settings.System.getIntForUser(context.getContentResolver(), "exclude_systemui_screenshots", 0, 0) == 1;
        AODAmbientWallpaperHelper$initAODAmbientWallpaperHelper$1$$ExternalSyntheticOutline0.m("isExcludeSystemUI : ", "Screenshot", z);
        return z;
    }

    public static boolean isSubDisplayCapture(int i) {
        return BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "WATCHFACE") && i == 1;
    }

    public static void showToast(int i, Context context) {
        if (context != null) {
            Toast.makeText(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.Light), i, 0).show();
        }
    }
}
