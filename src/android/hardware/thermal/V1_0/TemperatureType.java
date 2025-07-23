package android.hardware.thermal.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class TemperatureType {
    public static final int BATTERY = 2;
    public static final int CPU = 0;
    public static final int GPU = 1;
    public static final int SKIN = 3;
    public static final int UNKNOWN = -1;

    public static final String toString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return "CPU";
        }
        if (i == 1) {
            return "GPU";
        }
        if (i == 2) {
            return "BATTERY";
        }
        if (i == 3) {
            return "SKIN";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = -1;
        if (i == -1) {
            arrayList.add("UNKNOWN");
        } else {
            i2 = 0;
        }
        arrayList.add("CPU");
        if ((i & 1) == 1) {
            arrayList.add("GPU");
            i2 |= 1;
        }
        if ((i & 2) == 2) {
            arrayList.add("BATTERY");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SKIN");
            i2 |= 3;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
