package android.hardware.gnss.V2_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class ElapsedRealtimeFlags {
    public static final short HAS_TIMESTAMP_NS = 1;
    public static final short HAS_TIME_UNCERTAINTY_NS = 2;

    public static final String toString(short s) {
        if (s == 1) {
            return "HAS_TIMESTAMP_NS";
        }
        if (s == 2) {
            return "HAS_TIME_UNCERTAINTY_NS";
        }
        return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
    }

    public static final String dumpBitfield(short s) {
        short s2;
        ArrayList arrayList = new ArrayList();
        if ((s & 1) == 1) {
            arrayList.add("HAS_TIMESTAMP_NS");
            s2 = (short) 1;
        } else {
            s2 = 0;
        }
        if ((s & 2) == 2) {
            arrayList.add("HAS_TIME_UNCERTAINTY_NS");
            s2 = (short) (s2 | 2);
        }
        if (s != s2) {
            arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
        }
        return String.join(" | ", arrayList);
    }
}
