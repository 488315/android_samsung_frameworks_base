package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SliceStatus {
    public static final byte ALLOWED = 2;
    public static final byte CONFIGURED = 1;
    public static final byte DEFAULT_CONFIGURED = 5;
    public static final byte REJECTED_NOT_AVAILABLE_IN_PLMN = 3;
    public static final byte REJECTED_NOT_AVAILABLE_IN_REG_AREA = 4;
    public static final byte UNKNOWN = 0;

    public static final String toString(byte b) {
        if (b == 0) {
            return "UNKNOWN";
        }
        if (b == 1) {
            return "CONFIGURED";
        }
        if (b == 2) {
            return "ALLOWED";
        }
        if (b == 3) {
            return "REJECTED_NOT_AVAILABLE_IN_PLMN";
        }
        if (b == 4) {
            return "REJECTED_NOT_AVAILABLE_IN_REG_AREA";
        }
        if (b == 5) {
            return "DEFAULT_CONFIGURED";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNKNOWN");
        if ((b & 1) == 1) {
            arrayList.add("CONFIGURED");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("ALLOWED");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("REJECTED_NOT_AVAILABLE_IN_PLMN");
            b2 = (byte) (b2 | 3);
        }
        if ((b & 4) == 4) {
            arrayList.add("REJECTED_NOT_AVAILABLE_IN_REG_AREA");
            b2 = (byte) (b2 | 4);
        }
        if ((b & 5) == 5) {
            arrayList.add("DEFAULT_CONFIGURED");
            b2 = (byte) (b2 | 5);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
