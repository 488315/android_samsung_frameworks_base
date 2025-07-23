package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RegState {
    public static final int NOT_REG_MT_NOT_SEARCHING_OP = 0;
    public static final int NOT_REG_MT_NOT_SEARCHING_OP_EM = 10;
    public static final int NOT_REG_MT_SEARCHING_OP = 2;
    public static final int NOT_REG_MT_SEARCHING_OP_EM = 12;
    public static final int REG_DENIED = 3;
    public static final int REG_DENIED_EM = 13;
    public static final int REG_HOME = 1;
    public static final int REG_ROAMING = 5;
    public static final int UNKNOWN = 4;
    public static final int UNKNOWN_EM = 14;

    public static final String toString(int i) {
        if (i == 0) {
            return "NOT_REG_MT_NOT_SEARCHING_OP";
        }
        if (i == 1) {
            return "REG_HOME";
        }
        if (i == 2) {
            return "NOT_REG_MT_SEARCHING_OP";
        }
        if (i == 3) {
            return "REG_DENIED";
        }
        if (i == 4) {
            return "UNKNOWN";
        }
        if (i == 5) {
            return "REG_ROAMING";
        }
        if (i == 10) {
            return "NOT_REG_MT_NOT_SEARCHING_OP_EM";
        }
        if (i == 12) {
            return "NOT_REG_MT_SEARCHING_OP_EM";
        }
        if (i == 13) {
            return "REG_DENIED_EM";
        }
        if (i == 14) {
            return "UNKNOWN_EM";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("NOT_REG_MT_NOT_SEARCHING_OP");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("REG_HOME");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("NOT_REG_MT_SEARCHING_OP");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("REG_DENIED");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("UNKNOWN");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("REG_ROAMING");
            i2 |= 5;
        }
        if ((i & 10) == 10) {
            arrayList.add("NOT_REG_MT_NOT_SEARCHING_OP_EM");
            i2 |= 10;
        }
        if ((i & 12) == 12) {
            arrayList.add("NOT_REG_MT_SEARCHING_OP_EM");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("REG_DENIED_EM");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("UNKNOWN_EM");
            i2 |= 14;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
