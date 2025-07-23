package android.hardware.radio.V1_6;

import android.telephony.ims.SipDelegateImsConfiguration;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class QosProtocol {
    public static final byte AH = 51;
    public static final byte ESP = 50;
    public static final byte TCP = 6;
    public static final byte UDP = 17;
    public static final byte UNSPECIFIED = -1;

    public static final String toString(byte b) {
        if (b == -1) {
            return "UNSPECIFIED";
        }
        if (b == 6) {
            return SipDelegateImsConfiguration.SIP_TRANSPORT_TCP;
        }
        if (b == 17) {
            return SipDelegateImsConfiguration.SIP_TRANSPORT_UDP;
        }
        if (b == 50) {
            return "ESP";
        }
        if (b == 51) {
            return "AH";
        }
        return "0x" + Integer.toHexString(Byte.toUnsignedInt(b));
    }

    public static final String dumpBitfield(byte b) {
        byte b2;
        ArrayList arrayList = new ArrayList();
        if (b == -1) {
            arrayList.add("UNSPECIFIED");
            b2 = (byte) (-1);
        } else {
            b2 = 0;
        }
        if ((b & 6) == 6) {
            arrayList.add(SipDelegateImsConfiguration.SIP_TRANSPORT_TCP);
            b2 = (byte) (b2 | 6);
        }
        if ((b & 17) == 17) {
            arrayList.add(SipDelegateImsConfiguration.SIP_TRANSPORT_UDP);
            b2 = (byte) (b2 | 17);
        }
        if ((b & 50) == 50) {
            arrayList.add("ESP");
            b2 = (byte) (b2 | 50);
        }
        if ((b & 51) == 51) {
            arrayList.add("AH");
            b2 = (byte) (b2 | 51);
        }
        if (b != b2) {
            arrayList.add("0x" + Integer.toHexString(Byte.toUnsignedInt((byte) (b & (~b2)))));
        }
        return String.join(" | ", arrayList);
    }
}
