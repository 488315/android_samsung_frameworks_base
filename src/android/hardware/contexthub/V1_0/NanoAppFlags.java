package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class NanoAppFlags {
    public static final int ENCRYPTED = 2;
    public static final int SIGNED = 1;

    public static final String toString(int i) {
        if (i == 1) {
            return "SIGNED";
        }
        if (i == 2) {
            return "ENCRYPTED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("SIGNED");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("ENCRYPTED");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
