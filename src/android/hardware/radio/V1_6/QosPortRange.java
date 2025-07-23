package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class QosPortRange {
    public static final short MAX = -1;
    public static final short MIN = 20;

    public static final String toString(short s) {
        if (s == 20) {
            return "MIN";
        }
        if (s == -1) {
            return "MAX";
        }
        return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
    }

    public static final String dumpBitfield(short s) {
        short s2;
        ArrayList arrayList = new ArrayList();
        if ((s & 20) == 20) {
            arrayList.add("MIN");
            s2 = (short) 20;
        } else {
            s2 = 0;
        }
        if (s == -1) {
            arrayList.add("MAX");
            s2 = (short) (-1);
        }
        if (s != s2) {
            arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
        }
        return String.join(" | ", arrayList);
    }
}
