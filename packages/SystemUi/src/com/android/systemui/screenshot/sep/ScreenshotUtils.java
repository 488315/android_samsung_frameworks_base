package com.android.systemui.screenshot.sep;

import android.R;
import android.content.Context;
import android.graphics.Insets;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ScreenshotUtils {
    public static final String VALUE_SUB_DISPLAY_POLICY;

    static {
        VALUE_SUB_DISPLAY_POLICY = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
    }

    public static Display getDisplay(int i, Context context) {
        Display display;
        if (VALUE_SUB_DISPLAY_POLICY.contains("LARGESCREEN") && i == 1) {
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
        Insets insets = ((WindowManager) context.getApplicationContext().getSystemService("window")).getCurrentWindowMetrics().getWindowInsets().getInsets(WindowInsets.Type.navigationBars() | WindowInsets.Type.displayCutout() | WindowInsets.Type.statusBars());
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
            if (Settings.Global.getInt(context.getContentResolver(), "navigation_bar_gesture_while_hidden", 0) != 0) {
                return 4;
            }
        } else if (i4 < i) {
            return 4;
        }
        return 8;
    }

    public static String[] getScreenshotSaveInfo(Context context) {
        String str;
        try {
            str = Settings.System.getString(context.getContentResolver(), "screenshot_current_save_dir");
        } catch (Exception e) {
            Log.e("Screenshot", "getScreenshotSaveInfoDB() Settings.System", e);
            str = null;
        }
        String str2 = "external_primary:DCIM/Screenshots";
        if (str == null) {
            str = "external_primary:DCIM/Screenshots";
        } else if (!str.contains(":")) {
            str = "external_primary:".concat(str);
        }
        if (MediaStore.getExternalVolumeNames(context).contains(str.split(":")[0].toLowerCase())) {
            str2 = str;
        } else {
            try {
                Settings.System.putString(context.getContentResolver(), "screenshot_current_save_dir", "external_primary:DCIM/Screenshots");
            } catch (Exception e2) {
                Log.e("Screenshot", "setScreenshotSaveInfoDB() Settings.System", e2);
            }
        }
        return str2.split(":", 2);
    }

    public static boolean isExcludeSystemUI(Context context) {
        int semGetMyUserId = UserHandle.semGetMyUserId();
        boolean z = ((150 > semGetMyUserId || 160 < semGetMyUserId) ? Settings.System.getInt(context.getContentResolver(), "exclude_systemui_screenshots", 0) : Settings.System.getIntForUser(context.getContentResolver(), "exclude_systemui_screenshots", 0, 0)) == 1;
        ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("isExcludeSystemUI : ", z, "Screenshot");
        return z;
    }

    public static void showToast(int i, Context context) {
        if (context != null) {
            Toast.makeText(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.Light), i, 0).show();
        }
    }

    public static void showToast(Context context, int i) {
        if (context != null) {
            Toast.makeText(new ContextThemeWrapper(context, R.style.Theme.DeviceDefault.Light), context.getString(com.android.systemui.R.string.cant_screenshot_in_ps, context.getString(i)), 0).show();
        }
    }
}
