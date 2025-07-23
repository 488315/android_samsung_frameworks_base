package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehEnableStatus {
    public static final int NR_NETWORK_TYPE_EXCLUDED = 0;
    public static final int NR_NETWORK_TYPE_INCLUDED = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return "NR_NETWORK_TYPE_EXCLUDED";
        }
        if (i == 1) {
            return "NR_NETWORK_TYPE_INCLUDED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("NR_NETWORK_TYPE_EXCLUDED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("NR_NETWORK_TYPE_INCLUDED");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
