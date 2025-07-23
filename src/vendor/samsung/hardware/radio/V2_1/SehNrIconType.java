package vendor.samsung.hardware.radio.V2_1;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes6.dex */
public final class SehNrIconType {
    public static final int BASIC = 1;
    public static final int INVALID = -1;
    public static final int NONE = 0;
    public static final int UWB = 2;

    public static final String toString(int i) {
        if (i == -1) {
            return "INVALID";
        }
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "BASIC";
        }
        if (i == 2) {
            return "UWB";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = -1;
        if (i == -1) {
            arrayList.add("INVALID");
        } else {
            i2 = 0;
        }
        arrayList.add(KeyProperties.DIGEST_NONE);
        if ((i & 1) == 1) {
            arrayList.add("BASIC");
            i2 |= 1;
        }
        if ((i & 2) == 2) {
            arrayList.add("UWB");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
