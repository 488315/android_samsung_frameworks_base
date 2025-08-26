package com.android.internal.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.Log;
import android.view.Display;

/* loaded from: classes5.dex */
public class RefreshRateSettingsUtils {
    public static final float DEFAULT_REFRESH_RATE = 60.0f;
    private static final String TAG = "RefreshRateSettingsUtils";

    public static float findHighestRefreshRateForDefaultDisplay(Context context) {
        Display display = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplay(0);
        float refreshRate = 60.0f;
        if (display == null) {
            Log.w(TAG, "No valid default display device");
            return 60.0f;
        }
        for (Display.Mode mode : display.getSupportedModes()) {
            if (mode.getRefreshRate() > refreshRate) {
                refreshRate = mode.getRefreshRate();
            }
        }
        return refreshRate;
    }

    public static float findHighestRefreshRateAmongAllDisplays(Context context) {
        Display[] displays = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplays(DisplayManager.DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED);
        float refreshRate = 60.0f;
        if (displays.length == 0) {
            Log.w(TAG, "No valid display devices");
            return 60.0f;
        }
        for (Display display : displays) {
            for (Display.Mode mode : display.getSupportedModes()) {
                if (mode.getRefreshRate() > refreshRate) {
                    refreshRate = mode.getRefreshRate();
                }
            }
        }
        return refreshRate;
    }

    public static float findHighestRefreshRateAmongAllBuiltInDisplays(Context context) {
        Display[] displays = ((DisplayManager) context.getSystemService(DisplayManager.class)).getDisplays(DisplayManager.DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED);
        float refreshRate = 60.0f;
        if (displays.length == 0) {
            Log.w(TAG, "No valid display devices");
            return 60.0f;
        }
        for (Display display : displays) {
            if (display.getType() == 1) {
                for (Display.Mode mode : display.getSupportedModes()) {
                    if (mode.getRefreshRate() > refreshRate) {
                        refreshRate = mode.getRefreshRate();
                    }
                }
            }
        }
        return refreshRate;
    }
}
