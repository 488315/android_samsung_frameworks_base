package com.android.server.wm;

import android.R;
import android.content.Context;
import android.os.SystemProperties;

public abstract class DesktopModeLaunchParamsModifier
        implements LaunchParamsController.LaunchParamsModifier {
    public static final boolean ENFORCE_DEVICE_RESTRICTIONS;

    static {
        SystemProperties.getInt("persist.wm.debug.desktop_mode_initial_bounds_scale", 75);
        ENFORCE_DEVICE_RESTRICTIONS =
                SystemProperties.getBoolean(
                        "persist.wm.debug.desktop_mode_enforce_device_restrictions", true);
    }

    public static boolean enforceDeviceRestrictions() {
        return ENFORCE_DEVICE_RESTRICTIONS;
    }

    public static boolean isDesktopModeSupported(Context context) {
        return context.getResources().getBoolean(R.bool.config_leftRightSplitInPortrait);
    }
}
