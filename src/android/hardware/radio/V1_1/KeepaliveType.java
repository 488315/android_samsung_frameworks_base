package android.hardware.radio.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class KeepaliveType {
    public static final int NATT_IPV4 = 0;
    public static final int NATT_IPV6 = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return "NATT_IPV4";
        }
        if (i == 1) {
            return "NATT_IPV6";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("NATT_IPV4");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("NATT_IPV6");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
