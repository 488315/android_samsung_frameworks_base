package vendor.samsung.hardware.radio.V2_1;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehNrMode {
    public static final int DISABLE_NONE = 0;
    public static final int DISABLE_NSA = 2;
    public static final int DISABLE_SA = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return "DISABLE_NONE";
        }
        if (i == 1) {
            return "DISABLE_SA";
        }
        if (i == 2) {
            return "DISABLE_NSA";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("DISABLE_NONE");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("DISABLE_SA");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("DISABLE_NSA");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
