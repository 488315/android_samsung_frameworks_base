package com.android.systemui.screenshot.sep;

import android.content.Context;
import android.graphics.Insets;
import android.hardware.display.DisplayManager;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.Display;
import android.view.WindowInsets;
import android.view.WindowManager;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.settingslib.satellite.SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0;
import com.android.systemui.BasicRune$$ExternalSyntheticOutline0;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.desktopmode.SemDesktopModeState;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class ScreenshotUtils {
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
            }
            display = null;
            if (display != null) {
                Log.i("Screenshot", "getDisplay: subDisplay's rotation=" + display.getRotation());
                return display;
            }
        }
        DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
        Display display2 = displayManager.getDisplay(i);
        return display2 != null ? display2 : displayManager.getDisplay(0);
    }

    public static int getNavBarPosition(Context context, int i, boolean z) {
        Insets insets = ((WindowManager) context.getApplicationContext().getSystemService("window")).getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars());
        if (i == 0) {
            return 4;
        }
        int i2 = insets.left;
        if (i2 != 0 && i2 >= i) {
            return 1;
        }
        int i3 = insets.right;
        if (i3 != 0 && i3 >= i) {
            return 2;
        }
        int i4 = insets.top;
        if (i4 == 0) {
            return 4;
        }
        if (z) {
            if (Settings.Global.getInt(context.getContentResolver(), SettingsHelper.INDEX_NAVIGATION_BAR_GESTURE_WHILE_HIDDEN, 0) != 0) {
                return 4;
            }
        } else if (i4 < i) {
            return 4;
        }
        return 8;
    }

    public static String[] getScreenshotSaveInfo(Context context) {
        String string = Settings.System.getString(context.getContentResolver(), "screenshot_current_save_dir");
        MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("getScreenshotSaveInfoDB() saveInfo: ", string, "Screenshot");
        String str = "external_primary:DCIM/Screenshots";
        if (string == null) {
            string = "external_primary:DCIM/Screenshots";
        } else if (!string.contains(":")) {
            string = "external_primary:".concat(string);
        }
        if (MediaStore.getExternalVolumeNames(context).contains(string.split(":")[0].toLowerCase())) {
            str = string;
        } else {
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("setScreenshotSaveInfoDB() isSuccessfullySet: ", "Screenshot", Settings.System.putString(context.getContentResolver(), "screenshot_current_save_dir", "external_primary:DCIM/Screenshots"));
        }
        return str.split(":", 2);
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
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("isDesktopMode :: isDeXMode=", "Screenshot", z);
        return z;
    }

    public static boolean isExcludeSystemUI(Context context) {
        int semGetMyUserId = UserHandle.semGetMyUserId();
        boolean z = ((150 > semGetMyUserId || 160 < semGetMyUserId) ? Settings.System.getInt(context.getContentResolver(), "exclude_systemui_screenshots", 0) : Settings.System.getIntForUser(context.getContentResolver(), "exclude_systemui_screenshots", 0, 0)) == 1;
        SatelliteDialogUtils$requestIsEnabled$2$1$1$$ExternalSyntheticOutline0.m("isExcludeSystemUI : ", "Screenshot", z);
        return z;
    }

    public static boolean isSubDisplayCapture(int i) {
        return BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY", "WATCHFACE") && i == 1;
    }
}
