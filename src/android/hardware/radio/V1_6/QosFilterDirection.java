package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class QosFilterDirection {
    public static final byte BIDIRECTIONAL = 2;
    public static final byte DOWNLINK = 0;
    public static final byte UPLINK = 1;

    public static final String toString(byte b) {
        if (b == 0) {
            return "DOWNLINK";
        }
        if (b == 1) {
            return "UPLINK";
        }
        if (b == 2) {
            return "BIDIRECTIONAL";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        arrayList.add("DOWNLINK");
        if ((b & 1) == 1) {
            arrayList.add("UPLINK");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("BIDIRECTIONAL");
            b2 = (byte) (b2 | 2);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
