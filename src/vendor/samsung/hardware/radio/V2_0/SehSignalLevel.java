package vendor.samsung.hardware.radio.V2_0;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehSignalLevel {
    public static final int EXCELLLENT = 5;
    public static final int GOOD = 3;
    public static final int GREAT = 4;
    public static final int MODERATE = 2;
    public static final int NONE = 0;
    public static final int POOR = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "POOR";
        }
        if (i == 2) {
            return "MODERATE";
        }
        if (i == 3) {
            return "GOOD";
        }
        if (i == 4) {
            return "GREAT";
        }
        if (i == 5) {
            return "EXCELLLENT";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("POOR");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("MODERATE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("GOOD");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("GREAT");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("EXCELLLENT");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
