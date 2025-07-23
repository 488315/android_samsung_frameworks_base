package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehHandoverState {
    public static final int INTER_HANDOVER_FAILED = 1;
    public static final int INTER_HANDOVER_STARTED = 0;
    public static final int INTER_HANDOVER_SUCCESS = 2;
    public static final int INTRA_HANDOVER_FAILED = 4;
    public static final int INTRA_HANDOVER_STARTED = 3;
    public static final int INTRA_HANDOVER_SUCCESS = 5;
    public static final int MEASUREMENT_REPORT_DELIVERED = 6;
    public static final int UNKNOWN = -1;

    public static final String toString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return "INTER_HANDOVER_STARTED";
        }
        if (i == 1) {
            return "INTER_HANDOVER_FAILED";
        }
        if (i == 2) {
            return "INTER_HANDOVER_SUCCESS";
        }
        if (i == 3) {
            return "INTRA_HANDOVER_STARTED";
        }
        if (i == 4) {
            return "INTRA_HANDOVER_FAILED";
        }
        if (i == 5) {
            return "INTRA_HANDOVER_SUCCESS";
        }
        if (i == 6) {
            return "MEASUREMENT_REPORT_DELIVERED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = -1;
        if (i == -1) {
            arrayList.add("UNKNOWN");
        } else {
            i2 = 0;
        }
        arrayList.add("INTER_HANDOVER_STARTED");
        if ((i & 1) == 1) {
            arrayList.add("INTER_HANDOVER_FAILED");
            i2 |= 1;
        }
        if ((i & 2) == 2) {
            arrayList.add("INTER_HANDOVER_SUCCESS");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("INTRA_HANDOVER_STARTED");
            i2 |= 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("INTRA_HANDOVER_FAILED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("INTRA_HANDOVER_SUCCESS");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("MEASUREMENT_REPORT_DELIVERED");
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
