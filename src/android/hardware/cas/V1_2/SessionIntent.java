package android.hardware.cas.V1_2;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SessionIntent {
    public static final int LIVE = 0;
    public static final int PLAYBACK = 1;
    public static final int RECORD = 2;
    public static final int TIMESHIFT = 3;

    public static final String toString(int i) {
        if (i == 0) {
            return "LIVE";
        }
        if (i == 1) {
            return "PLAYBACK";
        }
        if (i == 2) {
            return "RECORD";
        }
        if (i == 3) {
            return "TIMESHIFT";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("LIVE");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("PLAYBACK");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("RECORD");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("TIMESHIFT");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
