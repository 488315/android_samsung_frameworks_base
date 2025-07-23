package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioIndicationType {
    public static final int UNSOLICITED = 0;
    public static final int UNSOLICITED_ACK_EXP = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNSOLICITED";
        }
        if (i == 1) {
            return "UNSOLICITED_ACK_EXP";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNSOLICITED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("UNSOLICITED_ACK_EXP");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
