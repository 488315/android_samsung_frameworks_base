package android.hardware.contexthub.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class HostEndPoint {
    public static final short BROADCAST = -1;
    public static final short UNSPECIFIED = -2;

    public static final String toString(short s) {
        if (s == -1) {
            return "BROADCAST";
        }
        if (s == -2) {
            return "UNSPECIFIED";
        }
        return "0x" + Integer.toHexString(Short.toUnsignedInt(s));
    }

    public static final String dumpBitfield(short s) {
        short s2;
        ArrayList arrayList = new ArrayList();
        if (s == -1) {
            arrayList.add("BROADCAST");
            s2 = (short) (-1);
        } else {
            s2 = 0;
        }
        if ((s & (-2)) == -2) {
            arrayList.add("UNSPECIFIED");
            s2 = (short) (s2 | (-2));
        }
        if (s != s2) {
            arrayList.add("0x" + Integer.toHexString(Short.toUnsignedInt((short) (s & (~s2)))));
        }
        return String.join(" | ", arrayList);
    }
}
