package android.hardware.radio.V1_0;

import android.security.keystore.KeyProperties;
import com.android.internal.telephony.DctConstants;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CellInfoType {
    public static final int CDMA = 2;
    public static final int GSM = 1;
    public static final int LTE = 3;
    public static final int NONE = 0;
    public static final int TD_SCDMA = 5;
    public static final int WCDMA = 4;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "GSM";
        }
        if (i == 2) {
            return "CDMA";
        }
        if (i == 3) {
            return DctConstants.RAT_NAME_LTE;
        }
        if (i == 4) {
            return "WCDMA";
        }
        if (i == 5) {
            return "TD_SCDMA";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("GSM");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("CDMA");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add(DctConstants.RAT_NAME_LTE);
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("WCDMA");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("TD_SCDMA");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
