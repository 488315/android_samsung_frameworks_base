package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SsTeleserviceType {
    public static final int ALL_DATA_TELESERVICES = 3;
    public static final int ALL_TELESERVICES_EXCEPT_SMS = 5;
    public static final int ALL_TELESEVICES = 1;
    public static final int ALL_TELE_AND_BEARER_SERVICES = 0;
    public static final int SMS_SERVICES = 4;
    public static final int TELEPHONY = 2;

    public static final String toString(int i) {
        if (i == 0) {
            return "ALL_TELE_AND_BEARER_SERVICES";
        }
        if (i == 1) {
            return "ALL_TELESEVICES";
        }
        if (i == 2) {
            return "TELEPHONY";
        }
        if (i == 3) {
            return "ALL_DATA_TELESERVICES";
        }
        if (i == 4) {
            return "SMS_SERVICES";
        }
        if (i == 5) {
            return "ALL_TELESERVICES_EXCEPT_SMS";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("ALL_TELE_AND_BEARER_SERVICES");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("ALL_TELESEVICES");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("TELEPHONY");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("ALL_DATA_TELESERVICES");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("SMS_SERVICES");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("ALL_TELESERVICES_EXCEPT_SMS");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
