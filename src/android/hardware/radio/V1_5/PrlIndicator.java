package android.hardware.radio.V1_5;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class PrlIndicator {
    public static final int IN_PRL = 1;
    public static final int NOT_IN_PRL = 0;
    public static final int NOT_REGISTERED = -1;

    public static final String toString(int i) {
        if (i == -1) {
            return "NOT_REGISTERED";
        }
        if (i == 0) {
            return "NOT_IN_PRL";
        }
        if (i == 1) {
            return "IN_PRL";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = -1;
        if (i == -1) {
            arrayList.add("NOT_REGISTERED");
        } else {
            i2 = 0;
        }
        arrayList.add("NOT_IN_PRL");
        if ((i & 1) == 1) {
            arrayList.add("IN_PRL");
            i2 |= 1;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
