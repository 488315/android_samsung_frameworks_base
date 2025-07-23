package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class PbReceivedStatus {
    public static final byte PB_RECEIVED_ABORT = 3;
    public static final byte PB_RECEIVED_ERROR = 2;
    public static final byte PB_RECEIVED_FINAL = 4;
    public static final byte PB_RECEIVED_OK = 1;

    public static final String toString(byte b) {
        if (b == 1) {
            return "PB_RECEIVED_OK";
        }
        if (b == 2) {
            return "PB_RECEIVED_ERROR";
        }
        if (b == 3) {
            return "PB_RECEIVED_ABORT";
        }
        if (b == 4) {
            return "PB_RECEIVED_FINAL";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        if ((b & 1) == 1) {
            arrayList.add("PB_RECEIVED_OK");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("PB_RECEIVED_ERROR");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("PB_RECEIVED_ABORT");
            b2 = (byte) (b2 | 3);
        }
        if ((b & 4) == 4) {
            arrayList.add("PB_RECEIVED_FINAL");
            b2 = (byte) (b2 | 4);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
