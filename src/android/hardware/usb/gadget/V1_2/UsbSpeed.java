package android.hardware.usb.gadget.V1_2;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class UsbSpeed {
    public static final int FULLSPEED = 1;
    public static final int HIGHSPEED = 2;
    public static final int LOWSPEED = 0;
    public static final int RESERVED_SPEED = 64;
    public static final int SUPERSPEED = 3;
    public static final int SUPERSPEED_10Gb = 4;
    public static final int SUPERSPEED_20Gb = 5;
    public static final int UNKNOWN = -1;
    public static final int USB4_GEN2_10Gb = 6;
    public static final int USB4_GEN2_20Gb = 7;
    public static final int USB4_GEN3_20Gb = 8;
    public static final int USB4_GEN3_40Gb = 9;

    public static final String toString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return "LOWSPEED";
        }
        if (i == 1) {
            return "FULLSPEED";
        }
        if (i == 2) {
            return "HIGHSPEED";
        }
        if (i == 3) {
            return "SUPERSPEED";
        }
        if (i == 4) {
            return "SUPERSPEED_10Gb";
        }
        if (i == 5) {
            return "SUPERSPEED_20Gb";
        }
        if (i == 6) {
            return "USB4_GEN2_10Gb";
        }
        if (i == 7) {
            return "USB4_GEN2_20Gb";
        }
        if (i == 8) {
            return "USB4_GEN3_20Gb";
        }
        if (i == 9) {
            return "USB4_GEN3_40Gb";
        }
        if (i == 64) {
            return "RESERVED_SPEED";
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
        arrayList.add("LOWSPEED");
        if ((i & 1) == 1) {
            arrayList.add("FULLSPEED");
            i2 |= 1;
        }
        if ((i & 2) == 2) {
            arrayList.add("HIGHSPEED");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("SUPERSPEED");
            i2 |= 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("SUPERSPEED_10Gb");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("SUPERSPEED_20Gb");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("USB4_GEN2_10Gb");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("USB4_GEN2_20Gb");
            i2 |= 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("USB4_GEN3_20Gb");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("USB4_GEN3_40Gb");
            i2 |= 9;
        }
        if ((i & 64) == 64) {
            arrayList.add("RESERVED_SPEED");
            i2 |= 64;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
