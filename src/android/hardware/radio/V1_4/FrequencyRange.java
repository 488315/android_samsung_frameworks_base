package android.hardware.radio.V1_4;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class FrequencyRange {
    public static final int HIGH = 3;
    public static final int LOW = 1;
    public static final int MID = 2;
    public static final int MMWAVE = 4;

    public static final String toString(int i) {
        if (i == 1) {
            return "LOW";
        }
        if (i == 2) {
            return "MID";
        }
        if (i == 3) {
            return "HIGH";
        }
        if (i == 4) {
            return "MMWAVE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("LOW");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("MID");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("HIGH");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("MMWAVE");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
