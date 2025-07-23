package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SscMode {
    public static final byte MODE_1 = 1;
    public static final byte MODE_2 = 2;
    public static final byte MODE_3 = 3;

    public static final String toString(byte b) {
        if (b == 1) {
            return "MODE_1";
        }
        if (b == 2) {
            return "MODE_2";
        }
        if (b == 3) {
            return "MODE_3";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        if ((b & 1) == 1) {
            arrayList.add("MODE_1");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("MODE_2");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("MODE_3");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
