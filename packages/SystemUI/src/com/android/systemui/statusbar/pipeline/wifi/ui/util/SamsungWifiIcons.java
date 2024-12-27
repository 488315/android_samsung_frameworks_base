package com.android.systemui.statusbar.pipeline.wifi.ui.util;

import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.connectivity.WifiIcons;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class SamsungWifiIcons {
    public static final int[] WIFI_ACTIVITY;
    public static final SignalIcon$IconGroup WIFI_ICON_5;
    public static final SignalIcon$IconGroup WIFI_ICON_6G;
    public static final SignalIcon$IconGroup WIFI_ICON_6GE;
    public static final SignalIcon$IconGroup WIFI_ICON_7G;
    public static final SignalIcon$IconGroup WIFI_ICON_WIFI_CALLING;
    public static final Companion Companion = new Companion(null);
    public static final int[][] WIFI_SIGNAL_STRENGTH_SAMSUNG = {new int[]{R.drawable.stat_sys_wifi_captive_0, R.drawable.stat_sys_wifi_captive_1, R.drawable.stat_sys_wifi_captive_2, R.drawable.stat_sys_wifi_captive_3, R.drawable.stat_sys_wifi_captive_4}, new int[]{R.drawable.stat_sys_wifi_signal_0, R.drawable.stat_sys_wifi_signal_1, R.drawable.stat_sys_wifi_signal_2, R.drawable.stat_sys_wifi_signal_3, R.drawable.stat_sys_wifi_signal_4}};

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        int[] iArr = {R.drawable.stat_sys_wifi_signal_no_inout, R.drawable.stat_sys_wifi_signal_in, R.drawable.stat_sys_wifi_signal_out, R.drawable.stat_sys_wifi_signal_inout};
        WIFI_ACTIVITY = iArr;
        int[][] iArr2 = {new int[]{R.drawable.stat_sys_wifi_captive_0, R.drawable.stat_sys_wifi_captive_1, R.drawable.stat_sys_wifi_captive_2, R.drawable.stat_sys_wifi_captive_3, R.drawable.stat_sys_wifi_captive_4}, new int[]{R.drawable.stat_sys_wifi5_signal_0, R.drawable.stat_sys_wifi5_signal_1, R.drawable.stat_sys_wifi5_signal_2, R.drawable.stat_sys_wifi5_signal_3, R.drawable.stat_sys_wifi5_signal_4}};
        int[][] iArr3 = WifiIcons.QS_WIFI_SIGNAL_STRENGTH;
        int[] iArr4 = AccessibilityContentDescriptions.WIFI_CONNECTION_STRENGTH;
        WIFI_ICON_5 = new SignalIcon$IconGroup("Wi-Fi 5 icons", iArr2, iArr3, iArr4, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, R.string.accessibility_no_wifi, iArr);
        WIFI_ICON_6G = new SignalIcon$IconGroup("Wi-Fi 6G icons", new int[][]{new int[]{R.drawable.stat_sys_wifi_captive_0, R.drawable.stat_sys_wifi_captive_1, R.drawable.stat_sys_wifi_captive_2, R.drawable.stat_sys_wifi_captive_3, R.drawable.stat_sys_wifi_captive_4}, new int[]{R.drawable.stat_sys_wifi6_signal_0, R.drawable.stat_sys_wifi6_signal_1, R.drawable.stat_sys_wifi6_signal_2, R.drawable.stat_sys_wifi6_signal_3, R.drawable.stat_sys_wifi6_signal_4}}, iArr3, iArr4, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, R.string.accessibility_no_wifi, iArr);
        WIFI_ICON_6GE = new SignalIcon$IconGroup("Wi-Fi 6Ge icons", new int[][]{new int[]{R.drawable.stat_sys_wifi_captive_0, R.drawable.stat_sys_wifi_captive_1, R.drawable.stat_sys_wifi_captive_2, R.drawable.stat_sys_wifi_captive_3, R.drawable.stat_sys_wifi_captive_4}, new int[]{R.drawable.stat_sys_wifi6e_signal_0, R.drawable.stat_sys_wifi6e_signal_1, R.drawable.stat_sys_wifi6e_signal_2, R.drawable.stat_sys_wifi6e_signal_3, R.drawable.stat_sys_wifi6e_signal_4}}, iArr3, iArr4, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, R.string.accessibility_no_wifi, iArr);
        WIFI_ICON_7G = new SignalIcon$IconGroup("Wi-Fi 7G icons", new int[][]{new int[]{R.drawable.stat_sys_wifi_captive_0, R.drawable.stat_sys_wifi_captive_1, R.drawable.stat_sys_wifi_captive_2, R.drawable.stat_sys_wifi_captive_3, R.drawable.stat_sys_wifi_captive_4}, new int[]{R.drawable.stat_sys_wifi7_signal_0, R.drawable.stat_sys_wifi7_signal_1, R.drawable.stat_sys_wifi7_signal_2, R.drawable.stat_sys_wifi7_signal_3, R.drawable.stat_sys_wifi7_signal_4}}, iArr3, iArr4, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, R.string.accessibility_no_wifi, iArr);
        WIFI_ICON_WIFI_CALLING = new SignalIcon$IconGroup("Wi-Fi calling Wi-Fi icons", new int[][]{new int[]{R.drawable.stat_sys_wifi_signal_wifi_calling_0, R.drawable.stat_sys_wifi_signal_wifi_calling_1, R.drawable.stat_sys_wifi_signal_wifi_calling_2, R.drawable.stat_sys_wifi_signal_wifi_calling_3, R.drawable.stat_sys_wifi_signal_wifi_calling_4}, new int[]{R.drawable.stat_sys_wifi_signal_wifi_calling_0, R.drawable.stat_sys_wifi_signal_wifi_calling_1, R.drawable.stat_sys_wifi_signal_wifi_calling_2, R.drawable.stat_sys_wifi_signal_wifi_calling_3, R.drawable.stat_sys_wifi_signal_wifi_calling_4}}, iArr3, iArr4, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, android.R.drawable.jog_tab_bar_right_sound_off, R.string.accessibility_no_wifi, iArr);
    }
}
