package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SrvccState {
    public static final int HANDOVER_CANCELED = 3;
    public static final int HANDOVER_COMPLETED = 1;
    public static final int HANDOVER_FAILED = 2;
    public static final int HANDOVER_STARTED = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "HANDOVER_STARTED";
        }
        if (i == 1) {
            return "HANDOVER_COMPLETED";
        }
        if (i == 2) {
            return "HANDOVER_FAILED";
        }
        if (i == 3) {
            return "HANDOVER_CANCELED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("HANDOVER_STARTED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("HANDOVER_COMPLETED");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("HANDOVER_FAILED");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("HANDOVER_CANCELED");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
