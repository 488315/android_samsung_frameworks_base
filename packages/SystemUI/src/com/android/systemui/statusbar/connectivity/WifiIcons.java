package com.android.systemui.statusbar.connectivity;

import android.R;
import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.SamsungWifiIcons;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class WifiIcons {
    public static final int[][] QS_WIFI_SIGNAL_STRENGTH;
    public static final SignalIcon$IconGroup UNMERGED_WIFI;
    public static final int[] WIFI_FULL_ICONS;
    public static final int[] WIFI_NO_INTERNET_ICONS;

    static {
        int[] iArr = {R.drawable.list_selector_background_disabled, R.drawable.list_selector_background_disabled_light, R.drawable.list_selector_background_focus, R.drawable.list_selector_background_focused, R.drawable.list_selector_background_focused_light};
        WIFI_FULL_ICONS = iArr;
        int[] iArr2 = {com.android.systemui.R.drawable.ic_no_internet_wifi_signal_0, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_1, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_2, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_3, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_4};
        WIFI_NO_INTERNET_ICONS = iArr2;
        int[][] iArr3 = {iArr2, iArr};
        QS_WIFI_SIGNAL_STRENGTH = iArr3;
        int length = iArr3[0].length;
        UNMERGED_WIFI = new SignalIcon$IconGroup("Wi-Fi Icons", SamsungWifiIcons.WIFI_SIGNAL_STRENGTH_SAMSUNG, iArr3, AccessibilityContentDescriptions.WIFI_CONNECTION_STRENGTH, R.drawable.list_selector_background_disabled, R.drawable.list_selector_background_disabled, R.drawable.list_selector_background_disabled, R.drawable.list_selector_background_disabled, com.android.systemui.R.string.accessibility_no_wifi, SamsungWifiIcons.WIFI_ACTIVITY);
    }
}
