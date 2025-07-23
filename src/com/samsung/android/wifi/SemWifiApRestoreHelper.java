package com.samsung.android.wifi;

import android.content.Context;
import android.net.wifi.SoftApConfiguration;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

/* loaded from: classes6.dex */
public class SemWifiApRestoreHelper {
    public static final String KEY_OPEN_INDEX = "open_index";
    public static final String KEY_PASSWORD = "shared_password";
    public static final String KEY_SECURITY_TYPE = "security_type";
    public static final String KEY_SSID = "shared_ssid";
    public static final String KEY_WPA_INDEX = "wpa_index";
    private static String TAG = "SemWifiApRestoreHelper";
    public static final String WPA2_INDEX = "wpa2_index";
    public static final String WPA3_INDEX = "wpa3_index";
    public static final String WPA3_TRANSITION_INDEX = "wpa3_transition_index";

    public static void setSSID(Context context, String str) {
        Log.i(TAG, "setSSID() - Setting: " + str);
        SemWifiApContentProviderHelper.insert(context, KEY_SSID, str);
        setRestoreNetworkPasswordDialogToBeShowOnce(context, true);
    }

    public static String getSSID(Context context) {
        String str = SemWifiApContentProviderHelper.get(context, KEY_SSID);
        Log.i(TAG, "getSSID() - Getting: " + str);
        return str;
    }

    public static void setPassword(Context context, String str) {
        String str2 = !TextUtils.isEmpty(str) ? "xxxxxx" : str;
        Log.i(TAG, "setPassword() - Setting: " + str2);
        SemWifiApContentProviderHelper.insert(context, KEY_PASSWORD, str);
    }

    public static String getPassword(Context context) {
        String str = SemWifiApContentProviderHelper.get(context, KEY_PASSWORD);
        if (TextUtils.isEmpty(str)) {
            Log.i(TAG, "getPassword() - is null");
            return str;
        }
        Log.i(TAG, "got Password() sucess");
        return str;
    }

    private static void setSecurityType(Context context, String str) {
        Log.i(TAG, "setSecurityType() - Setting securityType:" + str);
        SemWifiApContentProviderHelper.insert(context, KEY_SECURITY_TYPE, str);
    }

    public static void setSecurityTypeIndex(Context context, int i) {
        String str;
        Log.i(TAG, "setSecurityType() - Setting securityTypeIndex: " + i);
        if (i == 1) {
            str = WPA2_INDEX;
        } else if (i == 3) {
            str = WPA3_INDEX;
        } else if (i == 2) {
            str = WPA3_TRANSITION_INDEX;
        } else {
            str = KEY_OPEN_INDEX;
        }
        setSecurityType(context, str);
    }

    private static String getSecurityTypeAsString(Context context) {
        String str = SemWifiApContentProviderHelper.get(context, KEY_SECURITY_TYPE);
        Log.i(TAG, "getSecurityTypeAsString() - Getting: " + str);
        return str;
    }

    public static int getSecurityType(Context context) {
        String securityTypeAsString = getSecurityTypeAsString(context);
        if (securityTypeAsString.equals(WPA2_INDEX)) {
            Log.i(TAG, "getSecurityType() - Getting: KeyMgmt.WPA2_PSK");
            return 1;
        }
        if (securityTypeAsString.equals(WPA3_INDEX)) {
            Log.i(TAG, "getSecurityType() - Getting: KeyMgmt.SOFTAP_WPA3_SAE");
            return 3;
        }
        if (securityTypeAsString.equals(WPA3_TRANSITION_INDEX)) {
            Log.i(TAG, "getSecurityType() - Getting: KeyMgmt.SOFTAP_WPA3_SAE_TRANSITION");
            return 2;
        }
        Log.i(TAG, "getSecurityType() - Getting: KeyMgmt.NONE");
        return 0;
    }

    public static void setCurrentApConfiguration(Context context) {
        Log.i(TAG, "setCurrentApConfiguration() - Start");
        SoftApConfiguration softApConfiguration = ((SemWifiManager) context.getSystemService(Context.SEM_WIFI_SERVICE)).getSoftApConfiguration();
        setSSID(context, softApConfiguration.getSsid());
        setPassword(context, softApConfiguration.getPassphrase());
        setSecurityTypeIndex(context, softApConfiguration.getSecurityType());
    }

    public static void setApConfiguration(Context context, SoftApConfiguration softApConfiguration) {
        Log.i(TAG, "setApConfiguration(softApConfiguration) - Start");
        setSSID(context, softApConfiguration.getSsid());
        setPassword(context, softApConfiguration.getPassphrase());
        setSecurityTypeIndex(context, softApConfiguration.getSecurityType());
    }

    public static boolean isRestoreSsidSameAsSoftApSsid(Context context) {
        SoftApConfiguration softApConfiguration = ((SemWifiManager) context.getSystemService(Context.SEM_WIFI_SERVICE)).getSoftApConfiguration();
        String ssid = getSSID(context);
        String ssid2 = softApConfiguration.getSsid();
        if (ssid.isEmpty() || ssid.equals(ssid2)) {
            return true;
        }
        Log.i(TAG, "SSID in Content Provider is different from current SSID. contentProviderSSID: " + ssid + ", networkName: " + ssid2);
        return false;
    }

    public static boolean isRestoreNetworkPasswordDialogToBeShowOnce(Context context) {
        boolean z = Settings.Secure.getInt(context.getContentResolver(), "wifi_ap_is_restore_network_password_dialog_to_be_shown_once", 0) == 1;
        if (!z) {
            Log.i(TAG, "Show restore dialog Flag not set. [Not required to show dialog]");
            return false;
        }
        if (isRestoreSsidSameAsSoftApSsid(context)) {
            Log.i(TAG, "SSID in Content Provider is same as current SSID. [Not required to show dialog]");
            if (z) {
                setRestoreNetworkPasswordDialogToBeShowOnce(context, false);
            }
            return false;
        }
        Log.i(TAG, "Showing restoring dialog might be required. [Further Need to check EDM policy & AH connection]");
        return true;
    }

    public static void setRestoreNetworkPasswordDialogToBeShowOnce(Context context, boolean z) {
        Log.i(TAG, "setRestoreNetworkPasswordDialogRequiredToShow() - Setting: " + z);
        if (z) {
            Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_is_restore_network_password_dialog_to_be_shown_once", 1);
        } else {
            Settings.Secure.putInt(context.getContentResolver(), "wifi_ap_is_restore_network_password_dialog_to_be_shown_once", 0);
        }
    }
}
