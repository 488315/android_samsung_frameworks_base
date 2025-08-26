package com.android.systemui;

import android.os.Build;
import android.os.FactoryTest;
import android.os.SystemProperties;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes.dex */
public class NotiRune extends Rune {
    public static final boolean NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW;
    public static final boolean NOTI_LOCKSCREEN_ALWAYS_HIDE_SENSITIVE = BasicRune$$ExternalSyntheticOutline0.m("SEC_FLOATING_FEATURE_SYSTEMUI_CONFIG_SHOW_CONTENT_WHEN_UNLOCKED", "support");
    public static final boolean NOTI_ONGOING_GEMINI_DEMO;
    public static final boolean NOTI_STYLE_APP_LOCK;
    public static final boolean NOTI_STYLE_ICON_BACKGROUND_COLOR_THEME;
    public static final boolean NOTI_STYLE_POP_OVER_COLLAPSE;
    public static final boolean NOTI_STYLE_POP_OVER_DISMISS_CLIP_VIEW;
    public static final boolean NOTI_STYLE_POP_OVER_SHELF;
    public static final boolean NOTI_STYLE_POP_OVER_TOP_PADDING;
    public static final boolean NOTI_STYLE_POP_OVER_TRANSLATION_X;
    public static final boolean NOTI_SUBSCREEN_ALL;
    public static final boolean NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT;
    public static final boolean NOTI_SUBSCREEN_CLEAR_COVER;
    public static final boolean NOTI_SUBSCREEN_GHOST_NOTIFICATION;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION_COMMON;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION_FIFTH;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION_SECOND;
    public static final boolean NOTI_SUBSCREEN_NOTIFICATION_SEVENTH;
    public static final boolean NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT;
    public static final boolean NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY;
    public static final boolean NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI;
    public static final boolean NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA;

    static {
        int i = Build.VERSION.SEM_PLATFORM_INT;
        boolean z = i >= 140100;
        boolean z2 = i >= 140500;
        NOTI_ONGOING_GEMINI_DEMO = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_SUPPORT_AI_AGENT", false);
        String string = ("user".equals(Build.TYPE) || (SystemProperties.getInt("persist.debug.subdisplay_test_mode", 0) & 1) == 0) ? SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LOCKSCREEN_CONFIG_SUBDISPLAY_POLICY") : "";
        boolean zContains = string.contains("COVER");
        NOTI_SUBSCREEN_NOTIFICATION = zContains;
        boolean z3 = zContains && string.contains("WATCHFACE");
        NOTI_SUBSCREEN_NOTIFICATION_SECOND = z3;
        boolean z4 = z3 && string.contains("LARGESCREEN");
        NOTI_SUBSCREEN_NOTIFICATION_FIFTH = z4;
        boolean z5 = z4 && string.contains("FULLSCREEN");
        NOTI_SUBSCREEN_NOTIFICATION_SEVENTH = z5;
        boolean z6 = z3 || z4 || z5;
        NOTI_SUBSCREEN_NOTIFICATION_COMMON = z6;
        NOTI_SUBSCREEN_PENDING_CALL_FULLSCRREN_INTENT = zContains;
        boolean zContains2 = string.contains("VIRTUAL_DISPLAY");
        NOTI_SUBSCREEN_CLEAR_COVER = zContains2;
        NOTI_SUBSCREEN_ALL = zContains || z6 || zContains2;
        NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY = z2;
        NOTI_SUBSCREEN_CHILD_TO_RECEIVE_PARENT_ALERT = zContains;
        NOTI_SUBSCREEN_GHOST_NOTIFICATION = z4;
        NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI_FOR_CHINA = z4 && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false) && Rune.SYSUI_CHINA_FEATURE && Build.VERSION.SEM_FIRST_SDK_INT >= 34;
        NOTI_SUBSCREEN_SUPPORT_SMART_REPLY_AI = z4 && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_COMMON_DISABLE_NATIVE_AI", false) && SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_GENAI_SUPPORT_OFFLINE_LANGUAGEMODEL", false) && !"CN".equalsIgnoreCase(SemCscFeature.getInstance().getString("CountryISO", ""));
        NOTI_STYLE_ICON_BACKGROUND_COLOR_THEME = z;
        NOTI_STYLE_APP_LOCK = Rune.SYSUI_APPLOCK;
        boolean z7 = QpRune.QUICK_PANEL_CODE_FOR_POP_OVER;
        NOTI_STYLE_POP_OVER_DISMISS_CLIP_VIEW = z7;
        NOTI_STYLE_POP_OVER_TOP_PADDING = z7;
        NOTI_STYLE_POP_OVER_COLLAPSE = z7;
        NOTI_STYLE_POP_OVER_SHELF = z7;
        NOTI_STYLE_POP_OVER_TRANSLATION_X = z7;
        NOTI_AOSP_DISABLE_EMPTY_SHADE_VIEW = FactoryTest.isFactoryBinary();
        SystemProperties.getBoolean("persist.sys.fflag.override.settings_enable_sec_notification_summarize_gauss", false);
    }
}
