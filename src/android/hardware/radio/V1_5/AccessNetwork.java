package android.hardware.radio.V1_5;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class AccessNetwork {
    public static final int CDMA2000 = 4;
    public static final int EUTRAN = 3;
    public static final int GERAN = 1;
    public static final int IWLAN = 5;
    public static final int NGRAN = 6;
    public static final int UNKNOWN = 0;
    public static final int UTRAN = 2;

    public static final String toString(int i) {
        if (i == 1) {
            return "GERAN";
        }
        if (i == 2) {
            return "UTRAN";
        }
        if (i == 3) {
            return "EUTRAN";
        }
        if (i == 4) {
            return "CDMA2000";
        }
        if (i == 5) {
            return "IWLAN";
        }
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 6) {
            return "NGRAN";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("GERAN");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("UTRAN");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("EUTRAN");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CDMA2000");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("IWLAN");
            i2 |= 5;
        }
        arrayList.add("UNKNOWN");
        if ((i & 6) == 6) {
            arrayList.add("NGRAN");
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
