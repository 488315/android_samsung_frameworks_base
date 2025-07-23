package android.hardware.thermal.V2_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class TemperatureType {
    public static final int BATTERY = 2;
    public static final int BCL_CURRENT = 7;
    public static final int BCL_PERCENTAGE = 8;
    public static final int BCL_VOLTAGE = 6;
    public static final int CPU = 0;
    public static final int GPU = 1;
    public static final int NPU = 9;
    public static final int POWER_AMPLIFIER = 5;
    public static final int SKIN = 3;
    public static final int UNKNOWN = -1;
    public static final int USB_PORT = 4;

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
        if (i == 4) {
            return "USB_PORT";
        }
        if (i == 5) {
            return "POWER_AMPLIFIER";
        }
        if (i == 6) {
            return "BCL_VOLTAGE";
        }
        if (i == 7) {
            return "BCL_CURRENT";
        }
        if (i == 8) {
            return "BCL_PERCENTAGE";
        }
        if (i == 9) {
            return "NPU";
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
        if ((i & 4) == 4) {
            arrayList.add("USB_PORT");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("POWER_AMPLIFIER");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("BCL_VOLTAGE");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("BCL_CURRENT");
            i2 |= 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("BCL_PERCENTAGE");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("NPU");
            i2 |= 9;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
