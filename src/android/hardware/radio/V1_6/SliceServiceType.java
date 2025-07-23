package android.hardware.radio.V1_6;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SliceServiceType {
    public static final byte EMBB = 1;
    public static final byte MIOT = 3;
    public static final byte NONE = 0;
    public static final byte URLLC = 2;

    public static final String toString(byte b) {
        if (b == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (b == 1) {
            return "EMBB";
        }
        if (b == 2) {
            return "URLLC";
        }
        if (b == 3) {
            return "MIOT";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        if ((b & 1) == 1) {
            arrayList.add("EMBB");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("URLLC");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("MIOT");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
