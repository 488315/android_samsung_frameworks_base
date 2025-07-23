package com.samsung.android.wifi;

import android.content.Context;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemWifiApAutoHotspotHelper {
    public static final String KEY_CAN_AUTO_HOTSPOT_BE_ENABLED = "key_can_auto_hotspot_be_enabled";
    private static String TAG = "SemWifiApAutoHotspotHelper";
    public static final String VALUE_FALSE = "0";
    public static final String VALUE_TRUE = "1";

    public static boolean getIfAutoHotspotCanBeEnabled(Context context) {
        setIfAutoHotspotCanBeEnabled(context, ((SemWifiManager) context.getSystemService(Context.SEM_WIFI_SERVICE)).canAutoHotspotBeEnabled());
        String str = SemWifiApContentProviderHelper.get(context, KEY_CAN_AUTO_HOTSPOT_BE_ENABLED);
        Log.i(TAG, "getIfAutoHotspotCanBeEnabled() - Getting: " + str);
        return str.equals("1");
    }

    private static void setIfAutoHotspotCanBeEnabled(Context context, boolean z) {
        Log.i(TAG, "setIfAutoHotspotCanBeEnabled() - " + z);
        if (z) {
            SemWifiApContentProviderHelper.insert(context, KEY_CAN_AUTO_HOTSPOT_BE_ENABLED, "1");
        } else {
            SemWifiApContentProviderHelper.insert(context, KEY_CAN_AUTO_HOTSPOT_BE_ENABLED, "0");
        }
    }
}
