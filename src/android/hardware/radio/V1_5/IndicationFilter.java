package android.hardware.radio.V1_5;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class IndicationFilter {
    public static final int ALL = -1;
    public static final int BARRING_INFO = 64;
    public static final int DATA_CALL_DORMANCY_CHANGED = 4;
    public static final int FULL_NETWORK_STATE = 2;
    public static final int LINK_CAPACITY_ESTIMATE = 8;
    public static final int NONE = 0;
    public static final int PHYSICAL_CHANNEL_CONFIG = 16;
    public static final int REGISTRATION_FAILURE = 32;
    public static final int SIGNAL_STRENGTH = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == -1) {
            return "ALL";
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
        if (i == 8) {
            return "LINK_CAPACITY_ESTIMATE";
        }
        if (i == 16) {
            return "PHYSICAL_CHANNEL_CONFIG";
        }
        if (i == 32) {
            return "REGISTRATION_FAILURE";
        }
        if (i == 64) {
            return "BARRING_INFO";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = -1;
        if (i == -1) {
            arrayList.add("ALL");
        } else {
            i2 = 0;
        }
        if ((i & 1) == 1) {
            arrayList.add("SIGNAL_STRENGTH");
            i2 |= 1;
        }
        if ((i & 2) == 2) {
            arrayList.add("FULL_NETWORK_STATE");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("DATA_CALL_DORMANCY_CHANGED");
            i2 |= 4;
        }
        if ((i & 8) == 8) {
            arrayList.add("LINK_CAPACITY_ESTIMATE");
            i2 |= 8;
        }
        if ((i & 16) == 16) {
            arrayList.add("PHYSICAL_CHANNEL_CONFIG");
            i2 |= 16;
        }
        if ((i & 32) == 32) {
            arrayList.add("REGISTRATION_FAILURE");
            i2 |= 32;
        }
        if ((i & 64) == 64) {
            arrayList.add("BARRING_INFO");
            i2 |= 64;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
