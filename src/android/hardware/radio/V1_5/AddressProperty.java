package android.hardware.radio.V1_5;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class AddressProperty {
    public static final int DEPRECATED = 32;
    public static final int NONE = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 32) {
            return "DEPRECATED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 32;
        if ((i & 32) == 32) {
            arrayList.add("DEPRECATED");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
