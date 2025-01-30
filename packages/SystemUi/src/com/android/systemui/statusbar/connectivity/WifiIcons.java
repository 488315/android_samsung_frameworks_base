package com.android.systemui.statusbar.connectivity;

import com.android.settingslib.AccessibilityContentDescriptions;
import com.android.settingslib.SignalIcon$IconGroup;
import com.android.systemui.R;
import com.android.systemui.statusbar.pipeline.wifi.ui.util.SamsungWifiIcons;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WifiIcons {
    public static final int[][] QS_WIFI_SIGNAL_STRENGTH;
    public static final SignalIcon$IconGroup UNMERGED_WIFI;

    static {
        int[][] iArr = {new int[]{R.drawable.ic_no_internet_wifi_signal_0, R.drawable.ic_no_internet_wifi_signal_1, R.drawable.ic_no_internet_wifi_signal_2, R.drawable.ic_no_internet_wifi_signal_3, R.drawable.ic_no_internet_wifi_signal_4}, new int[]{android.R.drawable.ic_voice_search_api_holo_light, android.R.drawable.ic_voice_search_api_material, android.R.drawable.ic_volume, android.R.drawable.ic_volume_bluetooth_ad2p, android.R.drawable.ic_volume_bluetooth_in_call}};
        QS_WIFI_SIGNAL_STRENGTH = iArr;
        int length = iArr[0].length;
        UNMERGED_WIFI = new SignalIcon$IconGroup("Wi-Fi Icons", SamsungWifiIcons.WIFI_SIGNAL_STRENGTH_SAMSUNG, iArr, AccessibilityContentDescriptions.WIFI_CONNECTION_STRENGTH, android.R.drawable.ic_voice_search_api_holo_light, android.R.drawable.ic_voice_search_api_holo_light, android.R.drawable.ic_voice_search_api_holo_light, android.R.drawable.ic_voice_search_api_holo_light, R.string.accessibility_no_wifi, SamsungWifiIcons.WIFI_ACTIVITY);
    }
}
