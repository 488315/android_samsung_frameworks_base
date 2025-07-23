package android.hardware.radio.V1_5;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class SignalMeasurementType {
    public static final int RSCP = 2;
    public static final int RSRP = 3;
    public static final int RSRQ = 4;
    public static final int RSSI = 1;
    public static final int RSSNR = 5;
    public static final int SSRSRP = 6;
    public static final int SSRSRQ = 7;
    public static final int SSSINR = 8;

    public static final String toString(int i) {
        if (i == 1) {
            return "RSSI";
        }
        if (i == 2) {
            return "RSCP";
        }
        if (i == 3) {
            return "RSRP";
        }
        if (i == 4) {
            return "RSRQ";
        }
        if (i == 5) {
            return "RSSNR";
        }
        if (i == 6) {
            return "SSRSRP";
        }
        if (i == 7) {
            return "SSRSRQ";
        }
        if (i == 8) {
            return "SSSINR";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("RSSI");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("RSCP");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("RSRP");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("RSRQ");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("RSSNR");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("SSRSRP");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("SSRSRQ");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("SSSINR");
            i2 |= 8;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
