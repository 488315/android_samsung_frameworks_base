package com.samsung.android.wifi;

import android.content.Context;
import android.net.wifi.SoftApConfiguration;
import android.text.TextUtils;
import android.util.Log;

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

    public static void setSSID(Context context, String valueSSID) {
        Log.i(TAG, "setSSID() - Setting: " + valueSSID);
        SemWifiApContentProviderHelper.insert(context, KEY_SSID, valueSSID);
    }

    public static String getSSID(Context context) {
        String valueSSID = SemWifiApContentProviderHelper.get(context, KEY_SSID);
        Log.i(TAG, "getSSID() - Getting: " + valueSSID);
        return valueSSID;
    }

    public static void setPassword(Context context, String valuePassword) {
        String spr = valuePassword;
        if (!TextUtils.isEmpty(valuePassword)) {
            spr = "xxxxxx";
        }
        Log.i(TAG, "setPassword() - Setting: " + spr);
        SemWifiApContentProviderHelper.insert(context, KEY_PASSWORD, valuePassword);
    }

    public static String getPassword(Context context) {
        String valuePassword = SemWifiApContentProviderHelper.get(context, KEY_PASSWORD);
        if (TextUtils.isEmpty(valuePassword)) {
            Log.i(TAG, "getPassword() - is null");
        } else {
            Log.i(TAG, "got Password() sucess");
        }
        return valuePassword;
    }

    private static void setSecurityType(Context context, String securityType) {
        Log.i(TAG, "setSecurityType() - Setting securityType:" + securityType);
        SemWifiApContentProviderHelper.insert(context, KEY_SECURITY_TYPE, securityType);
    }

    public static void setSecurityTypeIndex(Context context, int securityTypeIndex) {
        String securityType;
        Log.i(TAG, "setSecurityType() - Setting securityTypeIndex: " + securityTypeIndex);
        if (securityTypeIndex == 1) {
            securityType = WPA2_INDEX;
        } else if (securityTypeIndex == 3) {
            securityType = WPA3_INDEX;
        } else if (securityTypeIndex == 2) {
            securityType = WPA3_TRANSITION_INDEX;
        } else {
            securityType = KEY_OPEN_INDEX;
        }
        setSecurityType(context, securityType);
    }

    private static String getSecurityTypeAsString(Context context) {
        String SecurityTypeString = SemWifiApContentProviderHelper.get(context, KEY_SECURITY_TYPE);
        Log.i(TAG, "getSecurityTypeAsString() - Getting: " + SecurityTypeString);
        return SecurityTypeString;
    }

    public static int getSecurityType(Context context) {
        String securityType = getSecurityTypeAsString(context);
        if (securityType.equals(WPA2_INDEX)) {
            Log.i(TAG, "getSecurityType() - Getting: KeyMgmt.WPA2_PSK");
            return 1;
        }
        if (securityType.equals(WPA3_INDEX)) {
            Log.i(TAG, "getSecurityType() - Getting: KeyMgmt.SOFTAP_WPA3_SAE");
            return 3;
        }
        if (securityType.equals(WPA3_TRANSITION_INDEX)) {
            Log.i(TAG, "getSecurityType() - Getting: KeyMgmt.SOFTAP_WPA3_SAE_TRANSITION");
            return 2;
        }
        Log.i(TAG, "getSecurityType() - Getting: KeyMgmt.NONE");
        return 0;
    }

    public static void setCurrentApConfiguration(Context context) {
        Log.i(TAG, "setCurrentApConfiguration() - Start");
        SemWifiManager mSemWifiManager =
                (SemWifiManager) context.getSystemService(Context.SEM_WIFI_SERVICE);
        SoftApConfiguration softApConfiguration = mSemWifiManager.getSoftApConfiguration();
        setSSID(context, softApConfiguration.getSsid());
        setPassword(context, softApConfiguration.getPassphrase());
        setSecurityTypeIndex(context, softApConfiguration.getSecurityType());
    }

    public static void setApConfiguration(
            Context context, SoftApConfiguration softApConfiguration) {
        Log.i(TAG, "setApConfiguration(softApConfiguration) - Start");
        setSSID(context, softApConfiguration.getSsid());
        setPassword(context, softApConfiguration.getPassphrase());
        setSecurityTypeIndex(context, softApConfiguration.getSecurityType());
    }
}
