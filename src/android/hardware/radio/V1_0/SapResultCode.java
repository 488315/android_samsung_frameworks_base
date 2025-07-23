package android.hardware.radio.V1_0;

import android.service.timezone.TimeZoneProviderService;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SapResultCode {
    public static final int CARD_ALREADY_POWERED_OFF = 3;
    public static final int CARD_ALREADY_POWERED_ON = 5;
    public static final int CARD_NOT_ACCESSSIBLE = 2;
    public static final int CARD_REMOVED = 4;
    public static final int DATA_NOT_AVAILABLE = 6;
    public static final int GENERIC_FAILURE = 1;
    public static final int NOT_SUPPORTED = 7;
    public static final int SUCCESS = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY;
        }
        if (i == 1) {
            return "GENERIC_FAILURE";
        }
        if (i == 2) {
            return "CARD_NOT_ACCESSSIBLE";
        }
        if (i == 3) {
            return "CARD_ALREADY_POWERED_OFF";
        }
        if (i == 4) {
            return "CARD_REMOVED";
        }
        if (i == 5) {
            return "CARD_ALREADY_POWERED_ON";
        }
        if (i == 6) {
            return "DATA_NOT_AVAILABLE";
        }
        if (i == 7) {
            return "NOT_SUPPORTED";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(TimeZoneProviderService.TEST_COMMAND_RESULT_SUCCESS_KEY);
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("GENERIC_FAILURE");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("CARD_NOT_ACCESSSIBLE");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("CARD_ALREADY_POWERED_OFF");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("CARD_REMOVED");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("CARD_ALREADY_POWERED_ON");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("DATA_NOT_AVAILABLE");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("NOT_SUPPORTED");
            i2 = 7;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
