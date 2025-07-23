package android.hardware.radio.V1_0;

import android.service.timezone.TimeZoneProviderService;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CdmaSmsErrorClass {
    public static final int ERROR = 1;
    public static final int NO_ERROR = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "NO_ERROR";
        }
        if (i == 1) {
            return TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("NO_ERROR");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add(TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY);
        } else {
            i2 = 0;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
