package com.samsung.android.wifi;

import android.content.Context;
import android.util.Log;

public class SemWifiApAutoHotspotHelper {
    public static final String KEY_CAN_AUTO_HOTSPOT_BE_ENABLED = "key_can_auto_hotspot_be_enabled";
    private static String TAG = SemWifiApAutoHotspotHelper.class.getSimpleName();
    public static final String VALUE_FALSE = "0";
    public static final String VALUE_TRUE = "1";

    public static boolean getIfAutoHotspotCanBeEnabled(Context context) {
        SemWifiManager mSemWifiManager =
                (SemWifiManager) context.getSystemService(Context.SEM_WIFI_SERVICE);
        boolean canAutoHotspotBeEnabled = mSemWifiManager.canAutoHotspotBeEnabled();
        setIfAutoHotspotCanBeEnabled(context, canAutoHotspotBeEnabled);
        String valueIfAutoHotspotCanBeEnabled =
                SemWifiApContentProviderHelper.get(context, KEY_CAN_AUTO_HOTSPOT_BE_ENABLED);
        Log.i(TAG, "getIfAutoHotspotCanBeEnabled() - Getting: " + valueIfAutoHotspotCanBeEnabled);
        if (valueIfAutoHotspotCanBeEnabled.equals("1")) {
            return true;
        }
        return false;
    }

    private static void setIfAutoHotspotCanBeEnabled(
            Context context, boolean ifAutoHotspotCanBeEnabled) {
        Log.i(TAG, "setIfAutoHotspotCanBeEnabled() - " + ifAutoHotspotCanBeEnabled);
        if (ifAutoHotspotCanBeEnabled) {
            SemWifiApContentProviderHelper.insert(context, KEY_CAN_AUTO_HOTSPOT_BE_ENABLED, "1");
        } else {
            SemWifiApContentProviderHelper.insert(context, KEY_CAN_AUTO_HOTSPOT_BE_ENABLED, "0");
        }
    }
}
