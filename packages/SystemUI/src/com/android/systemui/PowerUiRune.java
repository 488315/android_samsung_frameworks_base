package com.android.systemui;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;
import java.io.File;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PowerUiRune extends Rune {
    public static final boolean ADAPTIVE_PROTECTION_NOTIFICATION;
    public static final boolean AUDIO_DISABLE_HEADSET_CHARGING_SOUND;
    public static final boolean AUDIO_SUPPORT_SITUATION_EXTENSION;
    public static final boolean BATTERY_CHARGING_ESTIMATE_TIME;
    public static final boolean BATTERY_PROTECTION;
    public static final boolean BATTERY_PROTECTION_NOTIFICATION;
    public static final boolean BATTERY_PROTECTION_TIPS_NOTIFICATION;
    public static final boolean BATTERY_SWELLING_NOTICE;
    public static final boolean CHARGING_VI_NOW_BAR;
    public static final boolean CHN_SMART_MANAGER;
    public static final boolean COVER_DISPLAY_LARGE_SCREEN;
    public static final boolean FULL_BATTERY_CHECK;
    public static final boolean GPU_BLUR_SUPPORTED;
    public static final boolean HV_CHARGER_ENABLE_POPUP;
    public static final boolean INCOMPATIBLE_CHARGER_CHECK;
    public static final boolean INIT_LTC_TIME_CHANGED;
    public static final boolean IS_LDU_OR_UNPACK_BINARY;
    public static final boolean KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION;
    public static final boolean LOW_BATTTERY_SOUND_THEME;
    public static final boolean POLICY_CHARGING_NOTIFICATION;
    public static final boolean PROTECT_BATTERY_CUTOFF;
    public static final boolean SPECIFIC_POWER_REQUEST_BY_CHN;
    public static final boolean SPECIFIC_POWER_REQUEST_BY_VZW;
    public static final boolean SYSTEM_SOUND_THEME;
    public static final boolean TIPS_NOTIFICATION;
    public static final boolean TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE;
    public static final boolean TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA;
    public static final boolean WINDOW_BLUR_SUPPORTED;
    public static final boolean WIRELESS_CHARGING;

    static {
        String string = SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigQuickSettingPopup", "");
        CHN_SMART_MANAGER = "com.samsung.android.sm_cn".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME", "com.samsung.android.sm"));
        BATTERY_CHARGING_ESTIMATE_TIME = new File("/sys/class/power_supply/battery/time_to_full_now").exists();
        boolean z = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_LONGLIFE_FORCE_CUTOFF");
        PROTECT_BATTERY_CUTOFF = z;
        boolean exists = new File("/sys/class/sec/led/led_pattern").exists();
        KEEP_DIMMING_AT_BATTERY_HEALTH_INTERRUPTION = DeviceType.isTablet() || !exists;
        FULL_BATTERY_CHECK = !exists || "VZW".equals(string) || "ATT".equals(string) || "SPR".equals(string) || "TMB".equals(string);
        BATTERY_SWELLING_NOTICE = !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_LOW_TEMP_SLOW_CHARGED_POPUP");
        INCOMPATIBLE_CHARGER_CHECK = "VZW".equals(string);
        SPECIFIC_POWER_REQUEST_BY_VZW = "VZW".equals(string);
        SPECIFIC_POWER_REQUEST_BY_CHN = "China".equalsIgnoreCase(SystemProperties.get("ro.csc.country_code"));
        if (!"DCM".equals(string) && !"KDI".equals(string) && !"SBM".equals(string)) {
            "XJP".equals(string);
        }
        SYSTEM_SOUND_THEME = TextUtils.equals("sep_basic", SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_SEP_CATEGORY"));
        HV_CHARGER_ENABLE_POPUP = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_PD_HV");
        AUDIO_SUPPORT_SITUATION_EXTENSION = "TRUE".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_AUDIO_SUPPORT_SITUATION_EXTENSION"));
        WIRELESS_CHARGING = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_SUPPORT_WIRELESS_HV");
        IS_LDU_OR_UNPACK_BINARY = DeviceType.isLDUSKU() || SemCscFeature.getInstance().getBoolean("CscFeature_Common_EnableLiveDemo") || SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_UNPACK");
        int i = Build.VERSION.SEM_PLATFORM_INT;
        LOW_BATTTERY_SOUND_THEME = i >= 120100;
        String string2 = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY");
        TIPS_NOTIFICATION = string2.contains("WATCHFACE");
        WINDOW_BLUR_SUPPORTED = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GRAPHICS_SUPPORT_3D_SURFACE_TRANSITION_FLAG");
        GPU_BLUR_SUPPORTED = "1".equalsIgnoreCase(SystemProperties.get("ro.surface_flinger.protected_contents")) || "true".equalsIgnoreCase(SystemProperties.get("ro.surface_flinger.protected_contents"));
        AUDIO_DISABLE_HEADSET_CHARGING_SOUND = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_AUDIO_DISABLE_HEADSET_CHARGING_SOUND");
        TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_TA = z;
        INIT_LTC_TIME_CHANGED = z;
        COVER_DISPLAY_LARGE_SCREEN = string2.contains("LARGESCREEN");
        boolean z2 = i >= 150100 && z && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_BATTERY_DISABLE_ECO_BATTERY");
        BATTERY_PROTECTION = z2;
        POLICY_CHARGING_NOTIFICATION = i >= 150000;
        BATTERY_PROTECTION_NOTIFICATION = z2;
        TURN_ON_PROTECT_BATTERY_BY_LONG_TERM_CHARGE = z2;
        ADAPTIVE_PROTECTION_NOTIFICATION = z2;
        BATTERY_PROTECTION_TIPS_NOTIFICATION = z2;
        CHARGING_VI_NOW_BAR = i >= 160000;
    }
}
