package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CdmaSmsWriteArgsStatus {
    public static final int REC_READ = 1;
    public static final int REC_UNREAD = 0;
    public static final int STO_SENT = 3;
    public static final int STO_UNSENT = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "REC_UNREAD";
        }
        if (i == 1) {
            return "REC_READ";
        }
        if (i == 2) {
            return "STO_UNSENT";
        }
        if (i == 3) {
            return "STO_SENT";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("REC_UNREAD");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("REC_READ");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("STO_UNSENT");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("STO_SENT");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
