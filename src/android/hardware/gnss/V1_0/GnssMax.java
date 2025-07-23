package android.hardware.gnss.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class GnssMax {
    public static final int SVS_COUNT = 64;

    public static final String toString(int i) {
        if (i == 64) {
            return "SVS_COUNT";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 64;
        if ((i & 64) == 64) {
            arrayList.add("SVS_COUNT");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
