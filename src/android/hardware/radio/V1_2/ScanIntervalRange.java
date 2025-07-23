package android.hardware.radio.V1_2;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class ScanIntervalRange {
    public static final int MAX = 300;
    public static final int MIN = 5;

    public static final String toString(int i) {
        if (i == 5) {
            return "MIN";
        }
        if (i == 300) {
            return "MAX";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 5;
        if ((i & 5) == 5) {
            arrayList.add("MIN");
        } else {
            i2 = 0;
        }
        if ((i & 300) == 300) {
            arrayList.add("MAX");
            i2 |= 300;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
