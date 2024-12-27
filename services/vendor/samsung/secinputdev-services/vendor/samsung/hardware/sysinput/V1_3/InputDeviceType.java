package vendor.samsung.hardware.sysinput.V1_3;

import java.util.ArrayList;

public final class InputDeviceType {
    public static final int HALL = 10;
    public static final int HALL_DIGITAL = 11;
    public static final int KEY = 30;
    public static final int KEYBOARD = 40;
    public static final int RESERVED = 100;
    public static final int SPEN = 20;
    public static final int TAAS = 50;
    public static final int TSP_1 = 1;
    public static final int TSP_2 = 2;
    public static final int TSP_3 = 3;
    public static final int TSP_MAX = 4;

    public static final String toString(int o) {
        if (o == 1) {
            return "TSP_1";
        }
        if (o == 2) {
            return "TSP_2";
        }
        if (o == 3) {
            return "TSP_3";
        }
        if (o == 4) {
            return "TSP_MAX";
        }
        if (o == 10) {
            return "HALL";
        }
        if (o == 11) {
            return "HALL_DIGITAL";
        }
        if (o == 20) {
            return "SPEN";
        }
        if (o == 30) {
            return "KEY";
        }
        if (o == 40) {
            return "KEYBOARD";
        }
        if (o == 50) {
            return "TAAS";
        }
        if (o == 100) {
            return "RESERVED";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & 1) == 1) {
            list.add("TSP_1");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("TSP_2");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("TSP_3");
            flipped |= 3;
        }
        if ((o & 4) == 4) {
            list.add("TSP_MAX");
            flipped |= 4;
        }
        if ((o & 10) == 10) {
            list.add("HALL");
            flipped |= 10;
        }
        if ((o & 11) == 11) {
            list.add("HALL_DIGITAL");
            flipped |= 11;
        }
        if ((o & 20) == 20) {
            list.add("SPEN");
            flipped |= 20;
        }
        if ((o & 30) == 30) {
            list.add("KEY");
            flipped |= 30;
        }
        if ((o & 40) == 40) {
            list.add("KEYBOARD");
            flipped |= 40;
        }
        if ((o & 50) == 50) {
            list.add("TAAS");
            flipped |= 50;
        }
        if ((o & 100) == 100) {
            list.add("RESERVED");
            flipped |= 100;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
