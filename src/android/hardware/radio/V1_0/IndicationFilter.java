package android.hardware.radio.V1_0;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class IndicationFilter {
    public static final int ALL = 7;
    public static final int DATA_CALL_DORMANCY_CHANGED = 4;
    public static final int FULL_NETWORK_STATE = 2;
    public static final int NONE = 0;
    public static final int SIGNAL_STRENGTH = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "SIGNAL_STRENGTH";
        }
        if (i == 2) {
            return "FULL_NETWORK_STATE";
        }
        if (i == 4) {
            return "DATA_CALL_DORMANCY_CHANGED";
        }
        if (i == 7) {
            return "ALL";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("SIGNAL_STRENGTH");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("FULL_NETWORK_STATE");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("DATA_CALL_DORMANCY_CHANGED");
            i2 |= 4;
        }
        if ((i & 7) == 7) {
            arrayList.add("ALL");
            i2 = 7;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
