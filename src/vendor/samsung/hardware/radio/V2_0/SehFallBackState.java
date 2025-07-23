package vendor.samsung.hardware.radio.V2_0;

import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehFallBackState {
    public static final int FALLBACK_TO_CS = 1;
    public static final int FALLBACK_TO_VOLTE = 2;
    public static final int FALLBACK_TO_VOWIFI = 3;

    public static final String toString(int i) {
        if (i == 1) {
            return "FALLBACK_TO_CS";
        }
        if (i == 2) {
            return "FALLBACK_TO_VOLTE";
        }
        if (i == 3) {
            return "FALLBACK_TO_VOWIFI";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("FALLBACK_TO_CS");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("FALLBACK_TO_VOLTE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("FALLBACK_TO_VOWIFI");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
