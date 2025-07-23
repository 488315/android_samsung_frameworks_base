package android.hardware.radio.V1_0;

import android.security.keystore.KeyProperties;
import android.service.timezone.TimeZoneProviderService;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioCapabilityStatus {
    public static final int FAIL = 2;
    public static final int NONE = 0;
    public static final int SUCCESS = 1;

    public static final String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
        }
        if (i == 2) {
            return "FAIL";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(KeyProperties.DIGEST_NONE);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add(TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY);
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("FAIL");
            i2 |= 2;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
