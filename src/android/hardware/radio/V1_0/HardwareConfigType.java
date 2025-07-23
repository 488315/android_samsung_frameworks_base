package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class HardwareConfigType {
    public static final int MODEM = 0;
    public static final int SIM = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return "MODEM";
        }
        if (i == 1) {
            return "SIM";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("MODEM");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("SIM");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
