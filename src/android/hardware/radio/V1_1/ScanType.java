package android.hardware.radio.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class ScanType {
    public static final int ONE_SHOT = 0;
    public static final int PERIODIC = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return "ONE_SHOT";
        }
        if (i == 1) {
            return "PERIODIC";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ONE_SHOT");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("PERIODIC");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
