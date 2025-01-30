package android.util;

import android.content.Context;
import android.content.res.Resources;
import android.provider.DeviceConfig;

/* loaded from: classes4.dex */
public class SafetyProtectionUtils {
    private static final String SAFETY_PROTECTION_RESOURCES_ENABLED = "safety_protection_enabled";

    public static boolean shouldShowSafetyProtectionResources(Context context) {
        try {
            if (DeviceConfig.getBoolean("privacy", SAFETY_PROTECTION_RESOURCES_ENABLED, false) && context.getResources().getBoolean(Resources.getSystem().getIdentifier("config_safetyProtectionEnabled", "bool", "android")) && context.getDrawable(17301685) != null) {
                return !context.getString(17039425).isEmpty();
            }
            return false;
        } catch (Resources.NotFoundException e) {
            return false;
        }
    }
}
