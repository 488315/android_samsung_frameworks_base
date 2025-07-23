package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CallState {
    public static final int ACTIVE = 0;
    public static final int ALERTING = 3;
    public static final int DIALING = 2;
    public static final int HOLDING = 1;
    public static final int INCOMING = 4;
    public static final int WAITING = 5;

    public static final String toString(int i) {
        if (i == 0) {
            return "ACTIVE";
        }
        if (i == 1) {
            return "HOLDING";
        }
        if (i == 2) {
            return "DIALING";
        }
        if (i == 3) {
            return "ALERTING";
        }
        if (i == 4) {
            return "INCOMING";
        }
        if (i == 5) {
            return "WAITING";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ACTIVE");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("HOLDING");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("DIALING");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("ALERTING");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("INCOMING");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("WAITING");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
