package vendor.samsung.hardware.thermal.V1_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehTempStatus {
    public static final int DISABLED = -777;
    public static final int NOT_EXIST = -999;
    public static final int NOT_READABLE = -888;

    public static final String toString(int i) {
        if (i == -999) {
            return "NOT_EXIST";
        }
        if (i == -888) {
            return "NOT_READABLE";
        }
        if (i == -777) {
            return "DISABLED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = -999;
        if ((i & (-999)) == -999) {
            arrayList.add("NOT_EXIST");
        } else {
            i2 = 0;
        }
        if ((i & NOT_READABLE) == -888) {
            arrayList.add("NOT_READABLE");
            i2 |= NOT_READABLE;
        }
        if ((i & DISABLED) == -777) {
            arrayList.add("DISABLED");
            i2 |= DISABLED;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
