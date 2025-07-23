package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class HardwareConfigState {
    public static final int DISABLED = 2;
    public static final int ENABLED = 0;
    public static final int STANDBY = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return "ENABLED";
        }
        if (i == 1) {
            return "STANDBY";
        }
        if (i == 2) {
            return "DISABLED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ENABLED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("STANDBY");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("DISABLED");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
