package com.android.systemui.statusbar.policy;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class QSClockUtils {
    public static String getBasicClockFormat(String str) {
        int i = 0;
        boolean z = false;
        while (true) {
            if (i >= str.length()) {
                i = -1;
                break;
            }
            char charAt = str.charAt(i);
            if (charAt == '\'') {
                z = !z;
            }
            if (!z && charAt == 'a') {
                break;
            }
            i++;
        }
        if (i < 0) {
            return str;
        }
        int i2 = i;
        while (i2 > 0 && Character.isWhitespace(str.charAt(i2 - 1))) {
            i2--;
        }
        return str.substring(0, i2) + (char) 61184 + str.substring(i2, i) + "a\uef01" + str.substring(i + 1);
    }
}
