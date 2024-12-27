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
        DisplayManager dm = (DisplayManager) context.getSystemService(DisplayManager.class);
        Display display = dm.getDisplay(0);
        if (display == null) {
            Log.w(TAG, "No valid default display device");
            return 60.0f;
        }
        float maxRefreshRate = 60.0f;
        for (Display.Mode mode : display.getSupportedModes()) {
            if (mode.getRefreshRate() > maxRefreshRate) {
                maxRefreshRate = mode.getRefreshRate();
            }
        }
        return maxRefreshRate;
    }

    public static float findHighestRefreshRateAmongAllDisplays(Context context) {
        DisplayManager dm = (DisplayManager) context.getSystemService(DisplayManager.class);
        Display[] displays = dm.getDisplays(DisplayManager.DISPLAY_CATEGORY_ALL_INCLUDING_DISABLED);
        if (displays.length == 0) {
            Log.w(TAG, "No valid display devices");
            return 60.0f;
        }
        float maxRefreshRate = 60.0f;
        for (Display display : displays) {
            for (Display.Mode mode : display.getSupportedModes()) {
                if (mode.getRefreshRate() > maxRefreshRate) {
                    maxRefreshRate = mode.getRefreshRate();
                }
            }
        }
        return maxRefreshRate;
    }
}
