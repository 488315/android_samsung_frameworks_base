package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CdmaRedirectingReason {
    public static final int CALLED_DTE_OUT_OF_ORDER = 9;
    public static final int CALL_FORWARDING_BUSY = 1;
    public static final int CALL_FORWARDING_BY_THE_CALLED_DTE = 10;
    public static final int CALL_FORWARDING_NO_REPLY = 2;
    public static final int CALL_FORWARDING_UNCONDITIONAL = 15;
    public static final int RESERVED = 16;
    public static final int UNKNOWN = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "CALL_FORWARDING_BUSY";
        }
        if (i == 2) {
            return "CALL_FORWARDING_NO_REPLY";
        }
        if (i == 9) {
            return "CALLED_DTE_OUT_OF_ORDER";
        }
        if (i == 10) {
            return "CALL_FORWARDING_BY_THE_CALLED_DTE";
        }
        if (i == 15) {
            return "CALL_FORWARDING_UNCONDITIONAL";
        }
        if (i == 16) {
            return "RESERVED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("CALL_FORWARDING_BUSY");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("CALL_FORWARDING_NO_REPLY");
            i2 |= 2;
        }
        if ((i & 9) == 9) {
            arrayList.add("CALLED_DTE_OUT_OF_ORDER");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("CALL_FORWARDING_BY_THE_CALLED_DTE");
            i2 |= 10;
        }
        if ((i & 15) == 15) {
            arrayList.add("CALL_FORWARDING_UNCONDITIONAL");
            i2 = 15;
        }
        if ((i & 16) == 16) {
            arrayList.add("RESERVED");
            i2 |= 16;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
