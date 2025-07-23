package android.hardware.radio.V1_6;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class EmcIndicator {
    public static final byte EMC_BOTH_NR_EUTRA_CONNECTED_TO_5GCN = 3;
    public static final byte EMC_EUTRA_CONNECTED_TO_5GCN = 2;
    public static final byte EMC_NOT_SUPPORTED = 0;
    public static final byte EMC_NR_CONNECTED_TO_5GCN = 1;

    public static final String toString(byte b) {
        if (b == 0) {
            return "EMC_NOT_SUPPORTED";
        }
        if (b == 1) {
            return "EMC_NR_CONNECTED_TO_5GCN";
        }
        if (b == 2) {
            return "EMC_EUTRA_CONNECTED_TO_5GCN";
        }
        if (b == 3) {
            return "EMC_BOTH_NR_EUTRA_CONNECTED_TO_5GCN";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        arrayList.add("EMC_NOT_SUPPORTED");
        if ((b & 1) == 1) {
            arrayList.add("EMC_NR_CONNECTED_TO_5GCN");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("EMC_EUTRA_CONNECTED_TO_5GCN");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("EMC_BOTH_NR_EUTRA_CONNECTED_TO_5GCN");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
