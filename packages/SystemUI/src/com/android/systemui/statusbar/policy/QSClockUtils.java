package com.android.systemui.statusbar.policy;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class QSClockUtils {
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
