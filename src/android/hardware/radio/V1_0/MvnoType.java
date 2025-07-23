package android.hardware.radio.V1_0;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class MvnoType {
    public static final int GID = 2;
    public static final int IMSI = 1;
    public static final int NONE = 0;
    public static final int SPN = 3;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "IMSI";
        }
        if (i == 2) {
            return "GID";
        }
        if (i == 3) {
            return "SPN";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("IMSI");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("GID");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SPN");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
