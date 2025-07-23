package android.hardware.vibrator.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class Effect_1_1 {
    public static final int CLICK = 0;
    public static final int DOUBLE_CLICK = 1;
    public static final int TICK = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "CLICK";
        }
        if (i == 1) {
            return "DOUBLE_CLICK";
        }
        if (i == 2) {
            return "TICK";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("CLICK");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("DOUBLE_CLICK");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("TICK");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
