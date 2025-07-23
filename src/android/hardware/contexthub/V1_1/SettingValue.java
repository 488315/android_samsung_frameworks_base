package android.hardware.contexthub.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SettingValue {
    public static final byte DISABLED = 0;
    public static final byte ENABLED = 1;

    public static final String toString(byte b) {
        if (b == 0) {
            return "DISABLED";
        }
        if (b == 1) {
            return "ENABLED";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        arrayList.add("DISABLED");
        if ((b & 1) == 1) {
            arrayList.add("ENABLED");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
