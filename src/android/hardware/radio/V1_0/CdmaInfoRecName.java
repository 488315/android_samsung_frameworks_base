package android.hardware.radio.V1_0;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class CdmaInfoRecName {
    public static final int CALLED_PARTY_NUMBER = 1;
    public static final int CALLING_PARTY_NUMBER = 2;
    public static final int CONNECTED_NUMBER = 3;
    public static final int DISPLAY = 0;
    public static final int EXTENDED_DISPLAY = 7;
    public static final int LINE_CONTROL = 6;
    public static final int REDIRECTING_NUMBER = 5;
    public static final int SIGNAL = 4;
    public static final int T53_AUDIO_CONTROL = 10;
    public static final int T53_CLIR = 8;
    public static final int T53_RELEASE = 9;

    public static final String toString(int i) {
        if (i == 0) {
            return "DISPLAY";
        }
        if (i == 1) {
            return "CALLED_PARTY_NUMBER";
        }
        if (i == 2) {
            return "CALLING_PARTY_NUMBER";
        }
        if (i == 3) {
            return "CONNECTED_NUMBER";
        }
        if (i == 4) {
            return "SIGNAL";
        }
        if (i == 5) {
            return "REDIRECTING_NUMBER";
        }
        if (i == 6) {
            return "LINE_CONTROL";
        }
        if (i == 7) {
            return "EXTENDED_DISPLAY";
        }
        if (i == 8) {
            return "T53_CLIR";
        }
        if (i == 9) {
            return "T53_RELEASE";
        }
        if (i == 10) {
            return "T53_AUDIO_CONTROL";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("DISPLAY");
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("CALLED_PARTY_NUMBER");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("CALLING_PARTY_NUMBER");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("CONNECTED_NUMBER");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("SIGNAL");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("REDIRECTING_NUMBER");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("LINE_CONTROL");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("EXTENDED_DISPLAY");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("T53_CLIR");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("T53_RELEASE");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("T53_AUDIO_CONTROL");
            i2 |= 10;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
