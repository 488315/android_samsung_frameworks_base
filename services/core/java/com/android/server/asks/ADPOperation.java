package com.android.server.asks;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class ADPOperation {
    public static int getConvertedNumber(Pattern pattern, String str) {
        StringBuffer stringBuffer = new StringBuffer();
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            for (int i = 1; i <= matcher.groupCount(); i++) {
                stringBuffer.append(matcher.group(i));
            }
        }
        return Integer.valueOf(stringBuffer.toString()).intValue();
    }

    public static boolean isGreaterOrEqual(int i, String str, String str2) {
        return isGreaterOrEqual(
                Pattern.compile(
                        i != 0
                                ? i != 5
                                        ? i != 2
                                                ? i != 3 ? "" : "^(\\d?\\d{4})\\d{2}(\\d{3})\\z"
                                                : "^(\\d?\\d{6})\\d{3}\\z"
                                        : "^(\\d?\\d{9})\\z"
                                : "^(\\d?\\d{4})\\d{5}\\z"),
                str,
                str2);
    }

    public static boolean isGreaterOrEqual(Pattern pattern, String str, String str2) {
        try {
            return getConvertedNumber(pattern, str) >= getConvertedNumber(pattern, str2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return false;
        }
    }
}
