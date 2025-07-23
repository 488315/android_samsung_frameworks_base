package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CallForwardInfoStatus {
    public static final int DISABLE = 0;
    public static final int ENABLE = 1;
    public static final int ERASURE = 4;
    public static final int INTERROGATE = 2;
    public static final int REGISTRATION = 3;

    public static final String toString(int i) {
        if (i == 0) {
            return "DISABLE";
        }
        if (i == 1) {
            return "ENABLE";
        }
        if (i == 2) {
            return "INTERROGATE";
        }
        if (i == 3) {
            return "REGISTRATION";
        }
        if (i == 4) {
            return "ERASURE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("DISABLE");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("ENABLE");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("INTERROGATE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("REGISTRATION");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("ERASURE");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
