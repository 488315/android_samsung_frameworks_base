package android.hardware.radio.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class ScanStatus {
    public static final int COMPLETE = 2;
    public static final int PARTIAL = 1;

    public static final String toString(int i) {
        if (i == 1) {
            return "PARTIAL";
        }
        if (i == 2) {
            return "COMPLETE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("PARTIAL");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("COMPLETE");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
