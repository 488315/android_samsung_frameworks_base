package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SapStatus {
    public static final int CARD_INSERTED = 4;
    public static final int CARD_NOT_ACCESSIBLE = 2;
    public static final int CARD_REMOVED = 3;
    public static final int CARD_RESET = 1;
    public static final int RECOVERED = 5;
    public static final int UNKNOWN_ERROR = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNKNOWN_ERROR";
        }
        if (i == 1) {
            return "CARD_RESET";
        }
        if (i == 2) {
            return "CARD_NOT_ACCESSIBLE";
        }
        if (i == 3) {
            return "CARD_REMOVED";
        }
        if (i == 4) {
            return "CARD_INSERTED";
        }
        if (i == 5) {
            return "RECOVERED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNKNOWN_ERROR");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("CARD_RESET");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("CARD_NOT_ACCESSIBLE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("CARD_REMOVED");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CARD_INSERTED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("RECOVERED");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
