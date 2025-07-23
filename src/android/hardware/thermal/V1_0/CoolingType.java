package android.hardware.thermal.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CoolingType {
    public static final int FAN_RPM = 0;

    public static final String toString(int i) {
        if (i == 0) {
            return "FAN_RPM";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("FAN_RPM");
        if (i != 0) {
            arrayList.add("0x" + Integer.toHexString(i));
        }
        return String.join(" | ", arrayList);
    }
}
