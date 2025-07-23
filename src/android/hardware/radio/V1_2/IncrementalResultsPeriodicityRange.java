package android.hardware.radio.V1_2;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class IncrementalResultsPeriodicityRange {
    public static final int MAX = 10;
    public static final int MIN = 1;

    public static final String toString(int i) {
        if (i == 1) {
            return "MIN";
        }
        if (i == 10) {
            return "MAX";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("MIN");
        } else {
            i2 = 0;
        }
        if ((i & 10) == 10) {
            arrayList.add("MAX");
            i2 |= 10;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
