package android.hardware.radio.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class KeepaliveStatusCode {
    public static final int ACTIVE = 0;
    public static final int INACTIVE = 1;
    public static final int PENDING = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "ACTIVE";
        }
        if (i == 1) {
            return "INACTIVE";
        }
        if (i == 2) {
            return "PENDING";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ACTIVE");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("INACTIVE");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("PENDING");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
