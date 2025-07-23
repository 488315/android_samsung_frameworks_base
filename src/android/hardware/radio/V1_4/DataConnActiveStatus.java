package android.hardware.radio.V1_4;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class DataConnActiveStatus {
    public static final int ACTIVE = 2;
    public static final int DORMANT = 1;
    public static final int INACTIVE = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "INACTIVE";
        }
        if (i == 1) {
            return "DORMANT";
        }
        if (i == 2) {
            return "ACTIVE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("INACTIVE");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("DORMANT");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("ACTIVE");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
