package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CallPresentation {
    public static final int ALLOWED = 0;
    public static final int PAYPHONE = 3;
    public static final int RESTRICTED = 1;
    public static final int UNKNOWN = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "ALLOWED";
        }
        if (i == 1) {
            return "RESTRICTED";
        }
        if (i == 2) {
            return "UNKNOWN";
        }
        if (i == 3) {
            return "PAYPHONE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ALLOWED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("RESTRICTED");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("UNKNOWN");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("PAYPHONE");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
