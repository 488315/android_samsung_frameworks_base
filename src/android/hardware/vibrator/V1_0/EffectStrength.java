package android.hardware.vibrator.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class EffectStrength {
    public static final byte LIGHT = 0;
    public static final byte MEDIUM = 1;
    public static final byte STRONG = 2;

    public static final String toString(byte b) {
        if (b == 0) {
            return "LIGHT";
        }
        if (b == 1) {
            return "MEDIUM";
        }
        if (b == 2) {
            return "STRONG";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        arrayList.add("LIGHT");
        if ((b & 1) == 1) {
            arrayList.add("MEDIUM");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("STRONG");
            b2 = (byte) (b2 | 2);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
