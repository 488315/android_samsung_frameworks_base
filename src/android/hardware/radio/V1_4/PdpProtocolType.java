package android.hardware.radio.V1_4;

import android.telephony.CarrierConfigManager;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class PdpProtocolType {
    public static final int IP = 0;
    public static final int IPV4V6 = 2;
    public static final int IPV6 = 1;
    public static final int NON_IP = 4;
    public static final int PPP = 3;
    public static final int UNKNOWN = -1;
    public static final int UNSTRUCTURED = 5;

    public static final String toString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return CarrierConfigManager.Apn.PROTOCOL_IPV4;
        }
        if (i == 1) {
            return "IPV6";
        }
        if (i == 2) {
            return CarrierConfigManager.Apn.PROTOCOL_IPV4V6;
        }
        if (i == 3) {
            return "PPP";
        }
        if (i == 4) {
            return "NON_IP";
        }
        if (i == 5) {
            return "UNSTRUCTURED";
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
        arrayList.add(CarrierConfigManager.Apn.PROTOCOL_IPV4);
        if ((i & 1) == 1) {
            arrayList.add("IPV6");
            i2 |= 1;
        }
        if ((i & 2) == 2) {
            arrayList.add(CarrierConfigManager.Apn.PROTOCOL_IPV4V6);
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("PPP");
            i2 |= 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("NON_IP");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("UNSTRUCTURED");
            i2 |= 5;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
