package android.hardware.contexthub.V1_2;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class Setting {
    public static final byte AIRPLANE_MODE = 2;
    public static final byte LOCATION = 0;
    public static final byte MICROPHONE = 3;
    public static final byte WIFI_AVAILABLE = 1;

    public static final String toString(byte b) {
        if (b == 0) {
            return "LOCATION";
        }
        if (b == 1) {
            return "WIFI_AVAILABLE";
        }
        if (b == 2) {
            return "AIRPLANE_MODE";
        }
        if (b == 3) {
            return "MICROPHONE";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        arrayList.add("LOCATION");
        if ((b & 1) == 1) {
            arrayList.add("WIFI_AVAILABLE");
            b2 = (byte) 1;
        } else {
            b2 = 0;
        }
        if ((b & 2) == 2) {
            arrayList.add("AIRPLANE_MODE");
            b2 = (byte) (b2 | 2);
        }
        if ((b & 3) == 3) {
            arrayList.add("MICROPHONE");
            b2 = (byte) (b2 | 3);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
