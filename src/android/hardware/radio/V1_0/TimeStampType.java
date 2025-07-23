package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class TimeStampType {
    public static final int ANTENNA = 1;
    public static final int JAVA_RIL = 4;
    public static final int MODEM = 2;
    public static final int OEM_RIL = 3;
    public static final int UNKNOWN = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "ANTENNA";
        }
        if (i == 2) {
            return "MODEM";
        }
        if (i == 3) {
            return "OEM_RIL";
        }
        if (i == 4) {
            return "JAVA_RIL";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("ANTENNA");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("MODEM");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("OEM_RIL");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("JAVA_RIL");
            i2 |= 4;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
