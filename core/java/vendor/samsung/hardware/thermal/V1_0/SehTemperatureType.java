package vendor.samsung.hardware.thermal.V1_0;

import java.util.ArrayList;

public final class SehTemperatureType {
    public static final int BATTERY = 2;
    public static final int BCL_CURRENT = 7;
    public static final int BCL_PERCENTAGE = 8;
    public static final int BCL_VOLTAGE = 6;
    public static final int CPU = 0;
    public static final int GPU = 1;
    public static final int NPU = 9;
    public static final int POWER_AMPLIFIER = 5;
    public static final int SEH_AMBIENT = 54;
    public static final int SEH_CHARGER = 51;
    public static final int SEH_FLASH = 53;
    public static final int SEH_MODEM = 50;
    public static final int SEH_WIFI = 52;
    public static final int SKIN = 3;
    public static final int UNKNOWN = -1;
    public static final int USB_PORT = 4;

    public static final String toString(int o) {
        if (o == -1) {
            return "UNKNOWN";
        }
        if (o == 0) {
            return "CPU";
        }
        if (o == 1) {
            return "GPU";
        }
        if (o == 2) {
            return "BATTERY";
        }
        if (o == 3) {
            return "SKIN";
        }
        if (o == 4) {
            return "USB_PORT";
        }
        if (o == 5) {
            return "POWER_AMPLIFIER";
        }
        if (o == 6) {
            return "BCL_VOLTAGE";
        }
        if (o == 7) {
            return "BCL_CURRENT";
        }
        if (o == 8) {
            return "BCL_PERCENTAGE";
        }
        if (o == 9) {
            return "NPU";
        }
        if (o == 50) {
            return "SEH_MODEM";
        }
        if (o == 51) {
            return "SEH_CHARGER";
        }
        if (o == 52) {
            return "SEH_WIFI";
        }
        if (o == 53) {
            return "SEH_FLASH";
        }
        if (o == 54) {
            return "SEH_AMBIENT";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & (-1)) == -1) {
            list.add("UNKNOWN");
            flipped = 0 | (-1);
        }
        list.add("CPU");
        if ((o & 1) == 1) {
            list.add("GPU");
            flipped |= 1;
        }
        if ((o & 2) == 2) {
            list.add("BATTERY");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("SKIN");
            flipped |= 3;
        }
        if ((o & 4) == 4) {
            list.add("USB_PORT");
            flipped |= 4;
        }
        if ((o & 5) == 5) {
            list.add("POWER_AMPLIFIER");
            flipped |= 5;
        }
        if ((o & 6) == 6) {
            list.add("BCL_VOLTAGE");
            flipped |= 6;
        }
        if ((o & 7) == 7) {
            list.add("BCL_CURRENT");
            flipped |= 7;
        }
        if ((o & 8) == 8) {
            list.add("BCL_PERCENTAGE");
            flipped |= 8;
        }
        if ((o & 9) == 9) {
            list.add("NPU");
            flipped |= 9;
        }
        if ((o & 50) == 50) {
            list.add("SEH_MODEM");
            flipped |= 50;
        }
        if ((o & 51) == 51) {
            list.add("SEH_CHARGER");
            flipped |= 51;
        }
        if ((o & 52) == 52) {
            list.add("SEH_WIFI");
            flipped |= 52;
        }
        if ((o & 53) == 53) {
            list.add("SEH_FLASH");
            flipped |= 53;
        }
        if ((o & 54) == 54) {
            list.add("SEH_AMBIENT");
            flipped |= 54;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
