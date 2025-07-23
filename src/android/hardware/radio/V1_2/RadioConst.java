package android.hardware.radio.V1_2;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class RadioConst {
    public static final int CARD_MAX_APPS = 8;
    public static final int CDMA_ALPHA_INFO_BUFFER_LENGTH = 64;
    public static final int CDMA_MAX_NUMBER_OF_INFO_RECS = 10;
    public static final int CDMA_NUMBER_INFO_BUFFER_LENGTH = 81;
    public static final int MAX_CLIENT_ID_LENGTH = 2;
    public static final int MAX_DEBUG_SOCKET_NAME_LENGTH = 12;
    public static final int MAX_QEMU_PIPE_NAME_LENGTH = 11;
    public static final int MAX_RILDS = 3;
    public static final int MAX_SOCKET_NAME_LENGTH = 6;
    public static final int MAX_UUID_LENGTH = 64;
    public static final int NUM_SERVICE_CLASSES = 7;
    public static final int NUM_TX_POWER_LEVELS = 5;
    public static final int RADIO_ACCESS_SPECIFIER_MAX_SIZE = 8;
    public static final int SS_INFO_MAX = 4;

    public static final String toString(int i) {
        if (i == 64) {
            return "CDMA_ALPHA_INFO_BUFFER_LENGTH";
        }
        if (i == 81) {
            return "CDMA_NUMBER_INFO_BUFFER_LENGTH";
        }
        if (i == 3) {
            return "MAX_RILDS";
        }
        if (i == 6) {
            return "MAX_SOCKET_NAME_LENGTH";
        }
        if (i == 2) {
            return "MAX_CLIENT_ID_LENGTH";
        }
        if (i == 12) {
            return "MAX_DEBUG_SOCKET_NAME_LENGTH";
        }
        if (i == 11) {
            return "MAX_QEMU_PIPE_NAME_LENGTH";
        }
        if (i == 64) {
            return "MAX_UUID_LENGTH";
        }
        if (i == 8) {
            return "CARD_MAX_APPS";
        }
        if (i == 10) {
            return "CDMA_MAX_NUMBER_OF_INFO_RECS";
        }
        if (i == 4) {
            return "SS_INFO_MAX";
        }
        if (i == 7) {
            return "NUM_SERVICE_CLASSES";
        }
        if (i == 5) {
            return "NUM_TX_POWER_LEVELS";
        }
        if (i == 8) {
            return "RADIO_ACCESS_SPECIFIER_MAX_SIZE";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        int i2;
        ArrayList arrayList = new ArrayList();
        int i3 = i & 64;
        if (i3 == 64) {
            arrayList.add("CDMA_ALPHA_INFO_BUFFER_LENGTH");
            i2 = 64;
        } else {
            i2 = 0;
        }
        if ((i & 81) == 81) {
            arrayList.add("CDMA_NUMBER_INFO_BUFFER_LENGTH");
            i2 = 81;
        }
        if ((i & 3) == 3) {
            arrayList.add("MAX_RILDS");
            i2 |= 3;
        }
        if ((i & 6) == 6) {
            arrayList.add("MAX_SOCKET_NAME_LENGTH");
            i2 |= 6;
        }
        if ((i & 2) == 2) {
            arrayList.add("MAX_CLIENT_ID_LENGTH");
            i2 |= 2;
        }
        if ((i & 12) == 12) {
            arrayList.add("MAX_DEBUG_SOCKET_NAME_LENGTH");
            i2 |= 12;
        }
        if ((i & 11) == 11) {
            arrayList.add("MAX_QEMU_PIPE_NAME_LENGTH");
            i2 |= 11;
        }
        if (i3 == 64) {
            arrayList.add("MAX_UUID_LENGTH");
            i2 |= 64;
        }
        int i4 = i & 8;
        if (i4 == 8) {
            arrayList.add("CARD_MAX_APPS");
            i2 |= 8;
        }
        if ((i & 10) == 10) {
            arrayList.add("CDMA_MAX_NUMBER_OF_INFO_RECS");
            i2 |= 10;
        }
        if ((i & 4) == 4) {
            arrayList.add("SS_INFO_MAX");
            i2 |= 4;
        }
        if ((i & 7) == 7) {
            arrayList.add("NUM_SERVICE_CLASSES");
            i2 |= 7;
        }
        if ((i & 5) == 5) {
            arrayList.add("NUM_TX_POWER_LEVELS");
            i2 |= 5;
        }
        if (i4 == 8) {
            arrayList.add("RADIO_ACCESS_SPECIFIER_MAX_SIZE");
            i2 |= 8;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
