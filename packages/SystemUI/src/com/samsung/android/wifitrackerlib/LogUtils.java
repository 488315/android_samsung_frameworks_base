package com.samsung.android.wifitrackerlib;

import android.os.Debug;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class LogUtils {
    public final Pattern bssidPattern;
    public final boolean isProductDev;

    public LogUtils() {
        this.isProductDev = Debug.semIsProductDev();
        this.bssidPattern = Pattern.compile("([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})");
    }

    public final String getPrintableLog(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Matcher matcher = this.bssidPattern.matcher(str);
        int i = 0;
        while (matcher.find(i)) {
            try {
                int start = matcher.start();
                int end = matcher.end();
                sb.append((CharSequence) str, i, start + 9);
                sb.append("**");
                sb.append((CharSequence) str, start + 11, start + 12);
                sb.append("**");
                sb.append((CharSequence) str, start + 14, end);
                i = matcher.end();
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
        }
        sb.append(str.substring(i));
        return sb.toString();
    }

    public LogUtils(boolean z) {
        this.isProductDev = z;
        this.bssidPattern = Pattern.compile("([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})");
    }
}
