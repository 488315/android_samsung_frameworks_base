package com.android.internal.widget.remotecompose.core.operations.utilities;

import android.media.MediaMetrics;

import java.util.Arrays;

/* loaded from: classes5.dex */
public class StringUtils {
    public static String floatToString(
            float value, int beforeDecimalPoint, int afterDecimalPoint, char pre, char post) {
        int integerPart = (int) value;
        float fractionalPart = value % 1.0f;
        String integerPartString = String.valueOf(integerPart);
        int iLen = integerPartString.length();
        if (iLen < beforeDecimalPoint) {
            int spacesToPad = beforeDecimalPoint - iLen;
            if (pre != 0) {
                char[] pad = new char[spacesToPad];
                Arrays.fill(pad, pre);
                integerPartString = new String(pad) + integerPartString;
            }
        } else if (iLen > beforeDecimalPoint) {
            integerPartString = integerPartString.substring(iLen - beforeDecimalPoint);
        }
        if (afterDecimalPoint == 0) {
            return integerPartString;
        }
        for (int i = 0; i < afterDecimalPoint; i++) {
            fractionalPart *= 10.0f;
        }
        int i2 = Math.round(fractionalPart);
        float fractionalPart2 = i2;
        for (int i3 = 0; i3 < afterDecimalPoint; i3++) {
            fractionalPart2 = (float) (fractionalPart2 * 0.1d);
        }
        String fact = Float.toString(fractionalPart2);
        String fact2 = fact.substring(2, Math.min(fact.length(), afterDecimalPoint + 2));
        int trim = fact2.length();
        for (int i4 = fact2.length() - 1; i4 >= 0 && fact2.charAt(i4) == '0'; i4--) {
            trim--;
        }
        int i5 = fact2.length();
        if (trim != i5) {
            fact2 = fact2.substring(0, trim);
        }
        int len = fact2.length();
        if (post != 0 && len < afterDecimalPoint) {
            char[] c = new char[afterDecimalPoint - len];
            Arrays.fill(c, post);
            fact2 = fact2 + new String(c);
        }
        return integerPartString + MediaMetrics.SEPARATOR + fact2;
    }
}
