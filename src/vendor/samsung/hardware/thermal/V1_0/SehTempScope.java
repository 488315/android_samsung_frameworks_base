package vendor.samsung.hardware.thermal.V1_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehTempScope {
    public static final int VALID_MAX = 900;
    public static final int VALID_MIN = -300;

    public static final String toString(int i) {
        if (i == -300) {
            return "VALID_MIN";
        }
        if (i == 900) {
            return "VALID_MAX";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = i & VALID_MIN;
        int i3 = VALID_MIN;
        if (i2 == -300) {
            arrayList.add("VALID_MIN");
        } else {
            i3 = 0;
        }
        if ((i & 900) == 900) {
            arrayList.add("VALID_MAX");
            i3 |= 900;
        }
        if (i != i3) {
            arrayList.add("0x" + Integer.toHexString(i & (~i3)));
        }
        return String.join(" | ", arrayList);
    }
}
