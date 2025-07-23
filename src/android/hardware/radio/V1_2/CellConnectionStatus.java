package android.hardware.radio.V1_2;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CellConnectionStatus {
    public static final int NONE = 0;
    public static final int PRIMARY_SERVING = 1;
    public static final int SECONDARY_SERVING = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "PRIMARY_SERVING";
        }
        if (i == 2) {
            return "SECONDARY_SERVING";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("PRIMARY_SERVING");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("SECONDARY_SERVING");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
