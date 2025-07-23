package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class AppState {
    public static final int DETECTED = 1;
    public static final int PIN = 2;
    public static final int PUK = 3;
    public static final int READY = 5;
    public static final int SUBSCRIPTION_PERSO = 4;
    public static final int UNKNOWN = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "DETECTED";
        }
        if (i == 2) {
            return "PIN";
        }
        if (i == 3) {
            return "PUK";
        }
        if (i == 4) {
            return "SUBSCRIPTION_PERSO";
        }
        if (i == 5) {
            return "READY";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("UNKNOWN");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("DETECTED");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("PIN");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("PUK");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("SUBSCRIPTION_PERSO");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("READY");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
