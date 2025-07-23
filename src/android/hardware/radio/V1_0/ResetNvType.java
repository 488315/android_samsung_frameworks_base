package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class ResetNvType {
    public static final int ERASE = 1;
    public static final int FACTORY_RESET = 2;
    public static final int RELOAD = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "RELOAD";
        }
        if (i == 1) {
            return "ERASE";
        }
        if (i == 2) {
            return "FACTORY_RESET";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("RELOAD");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("ERASE");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("FACTORY_RESET");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
