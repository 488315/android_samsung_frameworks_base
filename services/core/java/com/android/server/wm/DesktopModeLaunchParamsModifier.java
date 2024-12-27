package com.android.server.wm;

import android.R;
import android.content.Context;
import android.os.SystemProperties;
import com.android.server.wm.LaunchParamsController;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class DesktopModeLaunchParamsModifier implements LaunchParamsController.LaunchParamsModifier {
    public static final boolean ENFORCE_DEVICE_RESTRICTIONS;

    static {
        SystemProperties.getInt("persist.wm.debug.desktop_mode_initial_bounds_scale", 75);
        ENFORCE_DEVICE_RESTRICTIONS = SystemProperties.getBoolean("persist.wm.debug.desktop_mode_enforce_device_restrictions", true);
    }

    public static boolean enforceDeviceRestrictions() {
        return ENFORCE_DEVICE_RESTRICTIONS;
    }

    public static boolean isDesktopModeSupported(Context context) {
        return context.getResources().getBoolean(R.bool.config_leftRightSplitInPortrait);
    }
}
