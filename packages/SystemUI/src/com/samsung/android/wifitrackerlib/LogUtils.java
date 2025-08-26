package com.samsung.android.wifitrackerlib;

import android.os.Debug;
import android.text.TextUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        int iEnd = 0;
        while (matcher.find(iEnd)) {
            try {
                int iStart = matcher.start();
                int iEnd2 = matcher.end();
                sb.append((CharSequence) str, iEnd, iStart + 9);
                sb.append("**");
                sb.append((CharSequence) str, iStart + 11, iStart + 12);
                sb.append("**");
                sb.append((CharSequence) str, iStart + 14, iEnd2);
                iEnd = matcher.end();
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
        }
        sb.append(str.substring(iEnd));
        return sb.toString();
    }

    public LogUtils(boolean z) {
        this.isProductDev = z;
        this.bssidPattern = Pattern.compile("([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})");
    }
}
