package android.hardware.radio.V1_0;

import android.security.keystore.KeyProperties;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class PhoneRestrictedState {
    public static final int CS_ALL = 4;
    public static final int CS_EMERGENCY = 1;
    public static final int CS_NORMAL = 2;
    public static final int NONE = 0;
    public static final int PS_ALL = 16;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "CS_EMERGENCY";
        }
        if (i == 2) {
            return "CS_NORMAL";
        }
        if (i == 4) {
            return "CS_ALL";
        }
        if (i == 16) {
            return "PS_ALL";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("CS_EMERGENCY");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("CS_NORMAL");
            i2 |= 2;
        }
        if ((i & 4) == 4) {
            arrayList.add("CS_ALL");
            i2 |= 4;
        }
        if ((i & 16) == 16) {
            arrayList.add("PS_ALL");
            i2 |= 16;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
