package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CdmaSubscriptionSource {
    public static final int NV = 1;
    public static final int RUIM_SIM = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "RUIM_SIM";
        }
        if (i == 1) {
            return "NV";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("RUIM_SIM");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("NV");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
