package com.android.settingslib.wifi.dpp;

import android.text.TextUtils;

/* loaded from: classes.dex */
public class WifiDppIntentHelper {
    public static String removeFirstAndLastDoubleQuotes(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        int length = str.length();
        int i = length - 1;
        int i2 = str.charAt(0) == '\"' ? 1 : 0;
        if (str.charAt(i) == '\"') {
            i = length - 2;
        }
        return str.substring(i2, i + 1);
    }
}
