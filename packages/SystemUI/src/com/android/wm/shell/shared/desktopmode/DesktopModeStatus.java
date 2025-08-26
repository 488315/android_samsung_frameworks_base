package com.android.wm.shell.shared.desktopmode;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.os.SystemProperties;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* loaded from: classes3.dex */
public class DesktopModeStatus {
    public static final boolean ENFORCE_DEVICE_RESTRICTIONS;
    static final String ENFORCE_DEVICE_RESTRICTIONS_PROPERTY = "persist.wm.debug.desktop_mode_enforce_device_restrictions";

    static {
        SystemProperties.getBoolean("persist.wm.debug.desktop_veiled_resizing", true);
        SystemProperties.getBoolean("persist.wm.debug.desktop_change_display", false);
        SystemProperties.getBoolean("persist.wm.debug.desktop_use_window_shadows", true);
        SystemProperties.getBoolean("persist.wm.debug.desktop_use_window_shadows_focused_window", false);
        SystemProperties.getBoolean("persist.wm.debug.desktop_use_rounded_corners", true);
        ENFORCE_DEVICE_RESTRICTIONS = SystemProperties.getBoolean(ENFORCE_DEVICE_RESTRICTIONS_PROPERTY, true);
        SystemProperties.getBoolean("persist.wm.debug.use_app_to_web_build_time_generic_links", true);
        SystemProperties.getBoolean("persist.wm.debug.desktop_mode_density_enabled", false);
        SystemProperties.getInt("persist.wm.debug.desktop_mode_density", IKnoxCustomManager.Stub.TRANSACTION_registerSystemUiCallback);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:19:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean canEnterDesktopMode(Context context) throws Resources.NotFoundException {
        if (enforceDeviceRestrictions()) {
            boolean z = DesktopExperienceFlags.ENABLE_PROJECTED_DISPLAY_DESKTOP_MODE.isTrue() ? context.getResources().getBoolean(R.bool.config_mobile_data_capable) : context.getResources().getBoolean(R.bool.config_mobile_data_capable) && context.getResources().getBoolean(R.bool.config_cbrs_supported);
            boolean z2 = context.getResources().getBoolean(R.bool.config_mms_content_disposition_support);
            if (z || z2) {
                if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODE.isTrue()) {
                    if (!DesktopModeFlags.isDesktopModeForcedEnabled() || (enforceDeviceRestrictions() && ((!context.getResources().getBoolean(R.bool.config_mobile_data_capable) || !context.getResources().getBoolean(R.bool.config_cbrs_supported)) && !context.getResources().getBoolean(R.bool.config_mms_content_disposition_support)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean enforceDeviceRestrictions() {
        return ENFORCE_DEVICE_RESTRICTIONS;
    }
}
