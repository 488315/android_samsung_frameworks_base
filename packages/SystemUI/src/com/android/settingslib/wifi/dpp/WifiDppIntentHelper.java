package com.android.settingslib.wifi.dpp;

import android.text.TextUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
