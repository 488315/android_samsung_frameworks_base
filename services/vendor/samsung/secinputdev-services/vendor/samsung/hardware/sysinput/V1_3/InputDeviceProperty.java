package vendor.samsung.hardware.sysinput.V1_3;

import java.util.ArrayList;

/* loaded from: classes.dex */
public final class InputDeviceProperty {
    public static final int AOD_ACTIVE_AREA = 6;
    public static final int AOD_ENABLE = 7;
    public static final int BLE_CHARGING = 12;
    public static final int CMD_LIST = 2;
    public static final int ENABLED = 17;
    public static final int EPEN_MEMO = 14;
    public static final int EPEN_POS = 8;
    public static final int EPEN_SAVING = 13;
    public static final int EPEN_WCHARGING = 16;
    public static final int FEATURE = 1;
    public static final int FOD_INFO = 4;
    public static final int FOD_POS = 5;
    public static final int HAND_EDGE = 15;
    public static final int HW_PARAM = 10;
    public static final int LP_DUMP = 11;
    public static final int PROX_OFF = 9;
    public static final int SCRUB_POS = 3;

    public static final String toString(int o) {
        if (o == 1) {
            return "FEATURE";
        }
        if (o == 2) {
            return "CMD_LIST";
        }
        if (o == 3) {
            return "SCRUB_POS";
        }
        if (o == 4) {
            return "FOD_INFO";
        }
        if (o == 5) {
            return "FOD_POS";
        }
        if (o == 6) {
            return "AOD_ACTIVE_AREA";
        }
        if (o == 7) {
            return "AOD_ENABLE";
        }
        if (o == 8) {
            return "EPEN_POS";
        }
        if (o == 9) {
            return "PROX_OFF";
        }
        if (o == 10) {
            return "HW_PARAM";
        }
        if (o == 11) {
            return "LP_DUMP";
        }
        if (o == 12) {
            return "BLE_CHARGING";
        }
        if (o == 13) {
            return "EPEN_SAVING";
        }
        if (o == 14) {
            return "EPEN_MEMO";
        }
        if (o == 15) {
            return "HAND_EDGE";
        }
        if (o == 16) {
            return "EPEN_WCHARGING";
        }
        if (o == 17) {
            return "ENABLED";
        }
        return "0x" + Integer.toHexString(o);
    }

    public static final String dumpBitfield(int o) {
        ArrayList<String> list = new ArrayList<>();
        int flipped = 0;
        if ((o & 1) == 1) {
            list.add("FEATURE");
            flipped = 0 | 1;
        }
        if ((o & 2) == 2) {
            list.add("CMD_LIST");
            flipped |= 2;
        }
        if ((o & 3) == 3) {
            list.add("SCRUB_POS");
            flipped |= 3;
        }
        if ((o & 4) == 4) {
            list.add("FOD_INFO");
            flipped |= 4;
        }
        if ((o & 5) == 5) {
            list.add("FOD_POS");
            flipped |= 5;
        }
        if ((o & 6) == 6) {
            list.add("AOD_ACTIVE_AREA");
            flipped |= 6;
        }
        if ((o & 7) == 7) {
            list.add("AOD_ENABLE");
            flipped |= 7;
        }
        if ((o & 8) == 8) {
            list.add("EPEN_POS");
            flipped |= 8;
        }
        if ((o & 9) == 9) {
            list.add("PROX_OFF");
            flipped |= 9;
        }
        if ((o & 10) == 10) {
            list.add("HW_PARAM");
            flipped |= 10;
        }
        if ((o & 11) == 11) {
            list.add("LP_DUMP");
            flipped |= 11;
        }
        if ((o & 12) == 12) {
            list.add("BLE_CHARGING");
            flipped |= 12;
        }
        if ((o & 13) == 13) {
            list.add("EPEN_SAVING");
            flipped |= 13;
        }
        if ((o & 14) == 14) {
            list.add("EPEN_MEMO");
            flipped |= 14;
        }
        if ((o & 15) == 15) {
            list.add("HAND_EDGE");
            flipped |= 15;
        }
        if ((o & 16) == 16) {
            list.add("EPEN_WCHARGING");
            flipped |= 16;
        }
        if ((o & 17) == 17) {
            list.add("ENABLED");
            flipped |= 17;
        }
        if (o != flipped) {
            list.add("0x" + Integer.toHexString((~flipped) & o));
        }
        return String.join(" | ", list);
    }
}
