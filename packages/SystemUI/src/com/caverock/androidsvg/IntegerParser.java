package com.caverock.androidsvg;

/* loaded from: classes3.dex */
public class IntegerParser {
    public final int pos;
    public final long value;

    public IntegerParser(long j, int i) {
        this.value = j;
        this.pos = i;
    }

    public static IntegerParser parseInt(int i, int i2, String str) {
        if (i >= i2) {
            return null;
        }
        long j = 0;
        int i3 = i;
        while (i3 < i2) {
            char cCharAt = str.charAt(i3);
            if (cCharAt < '0' || cCharAt > '9') {
                break;
            }
            j = (j * 10) + (cCharAt - '0');
            if (j > 2147483647L) {
                return null;
            }
            i3++;
        }
        if (i3 == i) {
            return null;
        }
        return new IntegerParser(j, i3);
    }
}
