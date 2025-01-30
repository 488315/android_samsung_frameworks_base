package com.android.systemui.statusbar.policy;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
        while (i2 > 0) {
            int i3 = i2 - 1;
            if (!Character.isWhitespace(str.charAt(i3))) {
                break;
            }
            i2 = i3;
        }
        return str.substring(0, i2) + (char) 61184 + str.substring(i2, i) + "a\uef01" + str.substring(i + 1);
    }
}
