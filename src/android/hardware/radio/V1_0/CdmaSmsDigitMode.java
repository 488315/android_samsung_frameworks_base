package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CdmaSmsDigitMode {
    public static final int EIGHT_BIT = 1;
    public static final int FOUR_BIT = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "FOUR_BIT";
        }
        if (i == 1) {
            return "EIGHT_BIT";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("FOUR_BIT");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("EIGHT_BIT");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
