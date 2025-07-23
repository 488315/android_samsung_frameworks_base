package android.hardware.radio.V1_4;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SimLockMultiSimPolicy {
    public static final int NO_MULTISIM_POLICY = 0;
    public static final int ONE_VALID_SIM_MUST_BE_PRESENT = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return "NO_MULTISIM_POLICY";
        }
        if (i == 1) {
            return "ONE_VALID_SIM_MUST_BE_PRESENT";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("NO_MULTISIM_POLICY");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("ONE_VALID_SIM_MUST_BE_PRESENT");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
