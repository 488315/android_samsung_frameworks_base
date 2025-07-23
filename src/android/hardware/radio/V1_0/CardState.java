package android.hardware.radio.V1_0;

import android.service.timezone.TimeZoneProviderService;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CardState {
    public static final int ABSENT = 0;
    public static final int ERROR = 2;
    public static final int PRESENT = 1;
    public static final int RESTRICTED = 3;

    public static final String toString(int i) {
        if (i == 0) {
            return "ABSENT";
        }
        if (i == 1) {
            return "PRESENT";
        }
        if (i == 2) {
            return TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY;
        }
        if (i == 3) {
            return "RESTRICTED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ABSENT");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("PRESENT");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add(TimeZoneProviderService.TEST_COMMAND_RESULT_ERROR_KEY);
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("RESTRICTED");
            i2 = 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
