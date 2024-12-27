package com.android.systemui.statusbar.connectivity;

import android.R;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.settingslib.flags.Flags;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.SamsungWifiIcons;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class WifiIcons {
    public static final int[][] QS_WIFI_SIGNAL_STRENGTH;
    public static final SignalIcon$IconGroup UNMERGED_WIFI;
    public static final int[] WIFI_FULL_ICONS;
    public static final int[] WIFI_NO_INTERNET_ICONS;

    static {
        Flags.newStatusBarIcons();
        int[] iArr = {R.drawable.jog_tab_bar_right_sound_off, R.drawable.jog_tab_bar_right_sound_on, R.drawable.jog_tab_left_answer, R.drawable.jog_tab_left_confirm_gray, R.drawable.jog_tab_left_confirm_green};
        WIFI_FULL_ICONS = iArr;
        Flags.newStatusBarIcons();
        int[] iArr2 = {com.android.systemui.R.drawable.ic_no_internet_wifi_signal_0, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_1, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_2, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_3, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_4};
        WIFI_NO_INTERNET_ICONS = iArr2;
        int[][] iArr3 = {iArr2, iArr};
        QS_WIFI_SIGNAL_STRENGTH = iArr3;
        int length = iArr3[0].length;
        UNMERGED_WIFI = new SignalIcon$IconGroup("Wi-Fi Icons", SamsungWifiIcons.WIFI_SIGNAL_STRENGTH_SAMSUNG, iArr3, AccessibilityContentDescriptions.WIFI_CONNECTION_STRENGTH, R.drawable.jog_tab_bar_right_sound_off, R.drawable.jog_tab_bar_right_sound_off, R.drawable.jog_tab_bar_right_sound_off, R.drawable.jog_tab_bar_right_sound_off, com.android.systemui.R.string.accessibility_no_wifi, SamsungWifiIcons.WIFI_ACTIVITY);
    }
}
