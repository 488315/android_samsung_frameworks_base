package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class DataThrottlingAction {
    public static final byte HOLD = 3;
    public static final byte NO_DATA_THROTTLING = 0;
    public static final byte THROTTLE_ANCHOR_CARRIER = 2;
    public static final byte THROTTLE_SECONDARY_CARRIER = 1;

    public static final String toString(byte b) {
        if (b == 0) {
            return "NO_DATA_THROTTLING";
        }
        if (b == 1) {
            return "THROTTLE_SECONDARY_CARRIER";
        }
        if (b == 2) {
            return "THROTTLE_ANCHOR_CARRIER";
        }
        if (b == 3) {
            return "HOLD";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        arrayList.add("NO_DATA_THROTTLING");
        if ((b & 1) == 1) {
            arrayList.add("THROTTLE_SECONDARY_CARRIER");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("THROTTLE_ANCHOR_CARRIER");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("HOLD");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
