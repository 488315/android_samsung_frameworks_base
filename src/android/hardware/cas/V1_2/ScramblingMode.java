package android.hardware.cas.V1_2;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class ScramblingMode {
    public static final int AES128 = 9;
    public static final int AES_ECB = 10;
    public static final int AES_SCTE52 = 11;
    public static final int DVB_CISSA_V1 = 6;
    public static final int DVB_CSA1 = 1;
    public static final int DVB_CSA2 = 2;
    public static final int DVB_CSA3_ENHANCE = 5;
    public static final int DVB_CSA3_MINIMAL = 4;
    public static final int DVB_CSA3_STANDARD = 3;
    public static final int DVB_IDSA = 7;
    public static final int MULTI2 = 8;
    public static final int RESERVED = 0;
    public static final int TDES_ECB = 12;
    public static final int TDES_SCTE52 = 13;

    public static final String toString(int i) {
        if (i == 0) {
            return "RESERVED";
        }
        if (i == 1) {
            return "DVB_CSA1";
        }
        if (i == 2) {
            return "DVB_CSA2";
        }
        if (i == 3) {
            return "DVB_CSA3_STANDARD";
        }
        if (i == 4) {
            return "DVB_CSA3_MINIMAL";
        }
        if (i == 5) {
            return "DVB_CSA3_ENHANCE";
        }
        if (i == 6) {
            return "DVB_CISSA_V1";
        }
        if (i == 7) {
            return "DVB_IDSA";
        }
        if (i == 8) {
            return "MULTI2";
        }
        if (i == 9) {
            return "AES128";
        }
        if (i == 10) {
            return "AES_ECB";
        }
        if (i == 11) {
            return "AES_SCTE52";
        }
        if (i == 12) {
            return "TDES_ECB";
        }
        if (i == 13) {
            return "TDES_SCTE52";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("RESERVED");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("DVB_CSA1");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("DVB_CSA2");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("DVB_CSA3_STANDARD");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("DVB_CSA3_MINIMAL");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("DVB_CSA3_ENHANCE");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("DVB_CISSA_V1");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("DVB_IDSA");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("MULTI2");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("AES128");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("AES_ECB");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("AES_SCTE52");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("TDES_ECB");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("TDES_SCTE52");
            i2 |= 13;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
