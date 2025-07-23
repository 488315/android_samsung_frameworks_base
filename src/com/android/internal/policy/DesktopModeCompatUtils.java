package com.android.internal.policy;

import android.content.pm.ActivityInfo;
import android.window.DesktopModeFlags;

/* loaded from: classes5.dex */
public final class DesktopModeCompatUtils {
    public static boolean shouldExcludeCaptionFromAppBounds(ActivityInfo activityInfo, boolean z, boolean z2) {
        if (DesktopModeFlags.EXCLUDE_CAPTION_FROM_APP_BOUNDS.isTrue() && isAnyForceConsumptionFlagsEnabled() && !isConfigurationDecoupled(activityInfo, z2)) {
            return !z || activityInfo.isChangeEnabled(ActivityInfo.OVERRIDE_EXCLUDE_CAPTION_INSETS_FROM_APP_BOUNDS);
        }
        return false;
    }

    private static boolean isConfigurationDecoupled(ActivityInfo activityInfo, boolean z) {
        return activityInfo.isChangeEnabled(ActivityInfo.INSETS_DECOUPLED_CONFIGURATION_ENFORCED) && !z;
    }

    private static boolean isAnyForceConsumptionFlagsEnabled() {
        return DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION_ALWAYS.isTrue() || DesktopModeFlags.ENABLE_CAPTION_COMPAT_INSET_FORCE_CONSUMPTION.isTrue();
    }
}
