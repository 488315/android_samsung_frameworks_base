package com.android.systemui;

import android.os.Build;
import android.os.FactoryTest;
import android.os.SystemProperties;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotiRune extends Rune {
    public static final boolean NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
    public static final boolean NOTI_INSIGNIFICANT;
    public static final boolean NOTI_ONGOING_GEMINI_DEMO;
    public static final boolean NOTI_STYLE_APP_LOCK;
    public static final boolean NOTI_STYLE_EMPTY_SHADE;
    public static final boolean NOTI_STYLE_ICON_BACKGROUND_COLOR_THEME;
    public static final boolean NOTI_STYLE_TABLET_BG;
    public static final boolean NOTI_SUBSCREEN_ALL;
    public static final boolean NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT;
    public static final boolean NOTI_SUBSCREEN_CLEAR_COVER;
    public static final boolean NOTI_SUBSCREEN_GHOST_NOTIFICATION;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION_COMMON;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION_FIFTH;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION_SECOND;
    public static final boolean NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT;
    public static final boolean NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY;
    public static final boolean NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI;
    public static final boolean NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA;
    public static final boolean NOTI_SUPPORT_NOTIFICATION_SUMMARIZE_FOR_CHINA;
    public static final boolean NOTI_SUPPORT_NOTIFICATION_SUMMARIZE_GAUSS;
    public static final boolean NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE = BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_SHOW_CONTENT_WHEN_UNLOCKED", "support");
    public static final boolean NOTI_STATIC_SHELF_ALPHA_VI = !DeviceType.isTablet();

    static {
        boolean z = true;
        "On".equals(SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefStatusSimpleStatusBar", "On"));
        int i = Build.VERSION.SEM_PLATFORM_INT;
        boolean z2 = i >= 140100;
        boolean z3 = i >= 140500;
        NOTI_ONGOING_GEMINI_DEMO = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_AI_AGENT", false);
        String string = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
        boolean contains = string.contains("COVER");
        NOTI_SUBSCREEN_NOTIFICATION = contains;
        boolean z4 = contains && string.contains("WATCHFACE");
        NOTI_SUBSCREEN_NOTIFICATION_SECOND = z4;
        boolean z5 = z4 && string.contains("LARGESCREEN");
        NOTI_SUBSCREEN_NOTIFICATION_FIFTH = z5;
        boolean z6 = z4 || z5;
        NOTI_SUBSCREEN_NOTIFICATION_COMMON = z6;
        NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT = contains;
        boolean contains2 = string.contains("VIRTUAL_DISPLAY");
        NOTI_SUBSCREEN_CLEAR_COVER = contains2;
        NOTI_SUBSCREEN_ALL = contains || z6 || contains2;
        NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY = z3;
        NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT = contains;
        NOTI_SUBSCREEN_GHOST_NOTIFICATION = z5;
        boolean z7 = Rune.SYSUI_CHINA_FEATURE;
        NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA = z5 && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false) && z7 && Build.VERSION.SEM_FIRST_SDK_INT >= 34;
        NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI = z5 && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false) && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GENAI_SUPPORT_OFFLINE_LANGUAGEMODEL", false) && !"CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO", ""));
        NOTI_STYLE_ICON_BACKGROUND_COLOR_THEME = z2;
        NOTI_STYLE_EMPTY_SHADE = !DeviceType.isTablet();
        NOTI_STYLE_TABLET_BG = DeviceType.isTablet();
        NOTI_STYLE_APP_LOCK = Rune.SYSUI_APPLOCK;
        NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW = DeviceType.isTablet() || FactoryTest.isFactoryBinary();
        NOTI_SUPPORT_NOTIFICATION_SUMMARIZE_GAUSS = SystemProperties.getBoolean("persist.sys.fflag.override.settings_enable_sec_notification_summarize_gauss", false);
        NOTI_SUPPORT_NOTIFICATION_SUMMARIZE_FOR_CHINA = z7;
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_COMMON_CONFIG_AI_VERSION") < 20251 && !SystemProperties.getBoolean("persist.sys.fflag.override.settings_enable_sec_notification_highlight", false)) {
            z = false;
        }
        NOTI_INSIGNIFICANT = z;
    }
}
