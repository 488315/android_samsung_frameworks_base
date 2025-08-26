package com.android.internal.widget;

/* loaded from: classes6.dex */
public class ScrollBarUtils {
    public static int getThumbLength(int i, int i2, int i3, int i4) {
        int i5 = i2 * 2;
        int iRound = Math.round((i * i3) / i4);
        return iRound < i5 ? i5 : iRound;
    }

    public static int getThumbOffset(int i, int i2, int i3, int i4, int i5) {
        int i6 = i - i2;
        int iRound = Math.round((i6 * i5) / (i4 - i3));
        return iRound > i6 ? i6 : iRound;
    }
}
