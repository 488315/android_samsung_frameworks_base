package android.hardware.contexthub.V1_0;

import android.service.timezone.TimeZoneProviderService;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class TransactionResult {
    public static final int FAILURE = 1;
    public static final int SUCCESS = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
        }
        if (i == 1) {
            return "FAILURE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("FAILURE");
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
