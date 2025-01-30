package com.android.systemui;

import android.os.Build;
import android.os.SystemProperties;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class NotiRune extends Rune {
    public static final boolean NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
    public static final boolean NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_SHOW_CONTENT_WHEN_UNLOCKED").contains("support");
    public static final boolean NOTI_STATIC_SHELF_ALPHA_VI = !DeviceType.isTablet();
    public static final boolean NOTI_STATUSBAR_SIMPLE_DEFAULT_ON = "On".equals(SemCscFeature.getInstance().getString("CscFeature_SystemUI_ConfigDefStatusSimpleStatusBar", "On"));
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

    static {
        int i = Build.VERSION.SEM_PLATFORM_INT;
        boolean z = false;
        boolean z2 = i >= 140100;
        boolean z3 = i >= 140500;
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
        NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA = z5 && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false) && Rune.SYSUI_CHINA_FEATURE && Build.VERSION.SEM_FIRST_SDK_INT >= 34;
        if (z5 && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false) && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GENAI_SUPPORT_OFFLINE_LANGUAGEMODEL", false) && !"CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO", ""))) {
            z = true;
        }
        NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI = z;
        NOTI_STYLE_ICON_BACKGROUND_COLOR_THEME = z2;
        NOTI_STYLE_EMPTY_SHADE = !DeviceType.isTablet();
        NOTI_STYLE_TABLET_BG = QpRune.QUICK_TABLET_BG;
        NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW = DeviceType.isTablet();
    }
}
