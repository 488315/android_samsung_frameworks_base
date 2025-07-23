package android.hardware.thermal.V2_0;

import android.security.keystore.KeyProperties;
import com.samsung.android.globalactions.presentation.strategies.WindowManagerFunctionStrategy;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class ThrottlingSeverity {
    public static final int CRITICAL = 4;
    public static final int EMERGENCY = 5;
    public static final int LIGHT = 1;
    public static final int MODERATE = 2;
    public static final int NONE = 0;
    public static final int SEVERE = 3;
    public static final int SHUTDOWN = 6;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "LIGHT";
        }
        if (i == 2) {
            return "MODERATE";
        }
        if (i == 3) {
            return "SEVERE";
        }
        if (i == 4) {
            return "CRITICAL";
        }
        if (i == 5) {
            return "EMERGENCY";
        }
        if (i == 6) {
            return WindowManagerFunctionStrategy.SHUTDOWN;
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("LIGHT");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("MODERATE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SEVERE");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CRITICAL");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("EMERGENCY");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add(WindowManagerFunctionStrategy.SHUTDOWN);
            i2 |= 6;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
