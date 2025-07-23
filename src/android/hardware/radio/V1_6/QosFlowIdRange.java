package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class QosFlowIdRange {
    public static final byte MAX = 63;
    public static final byte MIN = 1;

    public static final String toString(byte b) {
        if (b == 1) {
            return "MIN";
        }
        if (b == 63) {
            return "MAX";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        if ((b & 1) == 1) {
            arrayList.add("MIN");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 63) == 63) {
            arrayList.add("MAX");
            b2 = (byte) (b2 | 63);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
