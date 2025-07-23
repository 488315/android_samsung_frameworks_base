package com.android.net.module.util;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;

/* loaded from: classes6.dex */
public class ConnectivitySettingsUtils {
    public static final String PRIVATE_DNS_DEFAULT_MODE = "private_dns_default_mode";
    public static final String PRIVATE_DNS_MODE = "private_dns_mode";
    public static final int PRIVATE_DNS_MODE_OFF = 1;
    public static final String PRIVATE_DNS_MODE_OFF_STRING = "off";
    public static final int PRIVATE_DNS_MODE_OPPORTUNISTIC = 2;
    public static final String PRIVATE_DNS_MODE_OPPORTUNISTIC_STRING = "opportunistic";
    public static final int PRIVATE_DNS_MODE_PROVIDER_HOSTNAME = 3;
    public static final String PRIVATE_DNS_MODE_PROVIDER_HOSTNAME_STRING = "hostname";
    public static final String PRIVATE_DNS_SPECIFIER = "private_dns_specifier";

    public static String getPrivateDnsModeAsString(int i) {
        if (i == 1) {
            return "off";
        }
        if (i == 2) {
            return PRIVATE_DNS_MODE_OPPORTUNISTIC_STRING;
        }
        if (i == 3) {
            return PRIVATE_DNS_MODE_PROVIDER_HOSTNAME_STRING;
        }
        throw new IllegalArgumentException("Invalid private dns mode: " + i);
    }

    private static int getPrivateDnsModeAsInt(String str) {
        if (TextUtils.isEmpty(str)) {
            return 2;
        }
        str.hashCode();
        if (str.equals(PRIVATE_DNS_MODE_PROVIDER_HOSTNAME_STRING)) {
            return 3;
        }
        return !str.equals("off") ? 2 : 1;
    }

    public static int getPrivateDnsMode(Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        String string = Settings.Global.getString(contentResolver, "private_dns_mode");
        if (TextUtils.isEmpty(string)) {
            string = Settings.Global.getString(contentResolver, "private_dns_default_mode");
        }
        return getPrivateDnsModeAsInt(string);
    }

    public static void setPrivateDnsMode(Context context, int i) {
        if (i != 1 && i != 2 && i != 3) {
            throw new IllegalArgumentException("Invalid private dns mode: " + i);
        }
        Settings.Global.putString(context.getContentResolver(), "private_dns_mode", getPrivateDnsModeAsString(i));
    }

    public static String getPrivateDnsHostname(Context context) {
        return Settings.Global.getString(context.getContentResolver(), "private_dns_specifier");
    }

    public static void setPrivateDnsHostname(Context context, String str) {
        Settings.Global.putString(context.getContentResolver(), "private_dns_specifier", str);
    }
}
