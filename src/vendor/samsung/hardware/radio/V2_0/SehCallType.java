package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehCallType {
    public static final int VOICE = 0;
    public static final int VS_RX = 2;
    public static final int VS_TX = 1;
    public static final int VT = 3;

    public static final String toString(int i) {
        if (i == 0) {
            return "VOICE";
        }
        if (i == 1) {
            return "VS_TX";
        }
        if (i == 2) {
            return "VS_RX";
        }
        if (i == 3) {
            return "VT";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("VOICE");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("VS_TX");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("VS_RX");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("VT");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
