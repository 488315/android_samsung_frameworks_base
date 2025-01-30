package com.samsung.android.desktopsystemui.sharedlib.system;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewConfiguration;
import com.android.internal.policy.ScreenDecorationsUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.StringJoiner;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QuickStepContract {
    public static final String KEY_EXTRA_INPUT_MONITOR = "extra_input_monitor";
    public static final String KEY_EXTRA_SHELL_ONE_HANDED = "extra_shell_one_handed";
    public static final String KEY_EXTRA_SHELL_PIP = "extra_shell_pip";
    public static final String KEY_EXTRA_SHELL_SHELL_TRANSITIONS = "extra_shell_shell_transitions";
    public static final String KEY_EXTRA_SHELL_SPLIT_SCREEN = "extra_shell_split_screen";
    public static final String KEY_EXTRA_SHELL_STARTING_WINDOW = "extra_shell_starting_window";
    public static final String KEY_EXTRA_SUPPORTS_WINDOW_CORNERS = "extra_supports_window_corners";
    public static final String KEY_EXTRA_SYSUI_PROXY = "extra_sysui_proxy";
    public static final String KEY_EXTRA_WINDOW_CORNER_RADIUS = "extra_window_corner_radius";
    public static final String NAV_BAR_MODE_2BUTTON_OVERLAY = "com.android.internal.systemui.navbar.twobutton";
    public static final String NAV_BAR_MODE_3BUTTON_OVERLAY = "com.android.internal.systemui.navbar.threebutton";
    public static final String NAV_BAR_MODE_GESTURAL_OVERLAY = "com.android.internal.systemui.navbar.gestural";
    public static final int NAV_BAR_MODE_SAMSUNG_GESTURAL = 3;
    public static final float QUICKSTEP_TOUCH_SLOP_RATIO = 3.0f;
    public static final int SYSUI_STATE_A11Y_BUTTON_CLICKABLE = 16;
    public static final int SYSUI_STATE_A11Y_BUTTON_LONG_CLICKABLE = 32;
    public static final int SYSUI_STATE_ALLOW_GESTURE_IGNORING_BAR_VISIBILITY = 131072;
    public static final int SYSUI_STATE_ASSIST_GESTURE_CONSTRAINED = 8192;
    public static final int SYSUI_STATE_BACK_DISABLED = 4194304;
    public static final int SYSUI_STATE_BOUNCER_SHOWING = 8;
    public static final int SYSUI_STATE_BUBBLES_EXPANDED = 16384;
    public static final int SYSUI_STATE_BUBBLES_MANAGE_MENU_EXPANDED = 8388608;
    public static final int SYSUI_STATE_DEVICE_DOZING = 2097152;
    public static final int SYSUI_STATE_GAME_TOOLS_SHOWING = 33554432;
    public static final int SYSUI_STATE_GLOBAL_ACTIONS_SHOWING = 32768;
    public static final int SYSUI_STATE_HOME_DISABLED = 256;
    public static final int SYSUI_STATE_IME_SHOWING = 262144;
    public static final int SYSUI_STATE_IME_SWITCHER_SHOWING = 1048576;
    public static final int SYSUI_STATE_KNOX_HARD_KEY_INTENT = 536870912;
    public static final int SYSUI_STATE_MAGNIFICATION_OVERLAP = 524288;
    public static final int SYSUI_STATE_NAV_BAR_HIDDEN = 2;
    public static final int SYSUI_STATE_NAV_BAR_VIS_GONE = 268435456;
    public static final int SYSUI_STATE_NOTIFICATION_PANEL_EXPANDED = 4;
    public static final int SYSUI_STATE_ONE_HANDED_ACTIVE = 65536;
    public static final int SYSUI_STATE_OVERVIEW_DISABLED = 128;
    public static final int SYSUI_STATE_QUICK_SETTINGS_EXPANDED = 2048;
    public static final int SYSUI_STATE_REQUESTED_HOME_KEY = 134217728;
    public static final int SYSUI_STATE_REQUESTED_RECENT_KEY = 67108864;
    public static final int SYSUI_STATE_SCREEN_PINNING = 1;
    public static final int SYSUI_STATE_SEARCH_DISABLED = 1024;
    public static final int SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING = 64;
    public static final int SYSUI_STATE_STATUS_BAR_KEYGUARD_SHOWING_OCCLUDED = 512;
    public static final int SYSUI_STATE_TRACING_ENABLED = 4096;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    @Retention(RetentionPolicy.SOURCE)
    public @interface SystemUiStateFlags {
    }

    private static int convertDpToPixel(float f) {
        return (int) (f * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int getQuickScrubTouchSlopPx() {
        return convertDpToPixel(24.0f);
    }

    public static int getQuickStepDragSlopPx() {
        return convertDpToPixel(10.0f);
    }

    public static final float getQuickStepTouchSlopPx(Context context) {
        return ViewConfiguration.get(context).getScaledTouchSlop() * 3.0f;
    }

    public static String getSystemUiStateString(int i) {
        StringJoiner stringJoiner = new StringJoiner("|");
        stringJoiner.add((i & 1) != 0 ? "screen_pinned" : "");
        stringJoiner.add((i & 128) != 0 ? "overview_disabled" : "");
        stringJoiner.add((i & 256) != 0 ? "home_disabled" : "");
        stringJoiner.add((i & 1024) != 0 ? "search_disabled" : "");
        stringJoiner.add((i & 2) != 0 ? "navbar_hidden" : "");
        stringJoiner.add((i & 4) != 0 ? "notif_visible" : "");
        stringJoiner.add((i & 2048) != 0 ? "qs_visible" : "");
        stringJoiner.add((i & 64) != 0 ? "keygrd_visible" : "");
        stringJoiner.add((i & 512) != 0 ? "keygrd_occluded" : "");
        stringJoiner.add((i & 8) != 0 ? "bouncer_visible" : "");
        stringJoiner.add((32768 & i) != 0 ? "global_actions" : "");
        stringJoiner.add((i & 16) != 0 ? "a11y_click" : "");
        stringJoiner.add((i & 32) != 0 ? "a11y_long_click" : "");
        stringJoiner.add((i & 4096) != 0 ? "tracing" : "");
        stringJoiner.add((i & 8192) != 0 ? "asst_gesture_constrain" : "");
        stringJoiner.add((i & 16384) != 0 ? "bubbles_expanded" : "");
        stringJoiner.add((65536 & i) != 0 ? "one_handed_active" : "");
        stringJoiner.add((131072 & i) != 0 ? "allow_gesture" : "");
        stringJoiner.add((262144 & i) != 0 ? "ime_visible" : "");
        stringJoiner.add((524288 & i) != 0 ? "magnification_overlap" : "");
        stringJoiner.add((1048576 & i) != 0 ? "ime_switcher_showing" : "");
        stringJoiner.add((2097152 & i) != 0 ? "device_dozing" : "");
        stringJoiner.add((4194304 & i) != 0 ? "back_disabled" : "");
        stringJoiner.add((8388608 & i) != 0 ? "bubbles_mange_menu_expanded" : "");
        stringJoiner.add((33554432 & i) != 0 ? "game_tools_showing" : "");
        stringJoiner.add((67108864 & i) != 0 ? "requested_recent_key" : "");
        stringJoiner.add((134217728 & i) != 0 ? "requested_home_key" : "");
        stringJoiner.add((268435456 & i) != 0 ? "navbar_gone" : "");
        stringJoiner.add((i & SYSUI_STATE_KNOX_HARD_KEY_INTENT) != 0 ? "knox_hard_key_intent" : "");
        return stringJoiner.toString();
    }

    public static float getWindowCornerRadius(Context context) {
        return ScreenDecorationsUtils.getWindowCornerRadius(context);
    }

    public static boolean isAssistantGestureDisabled(int i) {
        if ((131072 & i) != 0) {
            i &= -3;
        }
        if ((i & 3083) != 0) {
            return true;
        }
        return (i & 4) != 0 && (i & 64) == 0;
    }

    public static boolean isBackGestureDisabled(int i) {
        if ((i & 8) != 0 || (32768 & i) != 0) {
            return false;
        }
        if ((131072 & i) != 0) {
            i &= -3;
        }
        return (i & 268435458) != 0;
    }

    public static boolean isGesturalMode(int i) {
        return i == 2 || i == 3;
    }

    public static boolean isLegacyMode(int i) {
        return i == 0;
    }

    public static boolean isSwipeUpMode(int i) {
        return i == 1;
    }

    public static boolean supportsRoundedCornersOnWindows(Resources resources) {
        return ScreenDecorationsUtils.supportsRoundedCornersOnWindows(resources);
    }

    public static int getQuickStepTouchSlopPx() {
        return convertDpToPixel(24.0f);
    }
}
