package android.hardware.radio.V1_4;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class EmergencyNumberSource {
    public static final int DEFAULT = 8;
    public static final int MODEM_CONFIG = 4;
    public static final int NETWORK_SIGNALING = 1;
    public static final int SIM = 2;

    public static final String toString(int i) {
        if (i == 1) {
            return "NETWORK_SIGNALING";
        }
        if (i == 2) {
            return "SIM";
        }
        if (i == 4) {
            return "MODEM_CONFIG";
        }
        if (i == 8) {
            return "DEFAULT";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("NETWORK_SIGNALING");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("SIM");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("MODEM_CONFIG");
            i2 |= 4;
        }
        if ((i & 8) == 8) {
            arrayList.add("DEFAULT");
            i2 |= 8;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
