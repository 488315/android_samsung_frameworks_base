package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class PublicKeyType {
    public static final byte EPDG = 1;
    public static final byte WLAN = 2;

    public static final String toString(byte b) {
        if (b == 1) {
            return "EPDG";
        }
        if (b == 2) {
            return "WLAN";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        if ((b & 1) == 1) {
            arrayList.add("EPDG");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("WLAN");
            b2 = (byte) (b2 | 2);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
