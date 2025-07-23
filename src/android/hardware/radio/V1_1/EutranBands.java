package android.hardware.radio.V1_1;

import java.util.ArrayList;

/* loaded from: classes2.dex */
public final class EutranBands {
    public static final int BAND_1 = 1;
    public static final int BAND_10 = 10;
    public static final int BAND_11 = 11;
    public static final int BAND_12 = 12;
    public static final int BAND_13 = 13;
    public static final int BAND_14 = 14;
    public static final int BAND_17 = 17;
    public static final int BAND_18 = 18;
    public static final int BAND_19 = 19;
    public static final int BAND_2 = 2;
    public static final int BAND_20 = 20;
    public static final int BAND_21 = 21;
    public static final int BAND_22 = 22;
    public static final int BAND_23 = 23;
    public static final int BAND_24 = 24;
    public static final int BAND_25 = 25;
    public static final int BAND_26 = 26;
    public static final int BAND_27 = 27;
    public static final int BAND_28 = 28;
    public static final int BAND_3 = 3;
    public static final int BAND_30 = 30;
    public static final int BAND_31 = 31;
    public static final int BAND_33 = 33;
    public static final int BAND_34 = 34;
    public static final int BAND_35 = 35;
    public static final int BAND_36 = 36;
    public static final int BAND_37 = 37;
    public static final int BAND_38 = 38;
    public static final int BAND_39 = 39;
    public static final int BAND_4 = 4;
    public static final int BAND_40 = 40;
    public static final int BAND_41 = 41;
    public static final int BAND_42 = 42;
    public static final int BAND_43 = 43;
    public static final int BAND_44 = 44;
    public static final int BAND_45 = 45;
    public static final int BAND_46 = 46;
    public static final int BAND_47 = 47;
    public static final int BAND_48 = 48;
    public static final int BAND_5 = 5;
    public static final int BAND_6 = 6;
    public static final int BAND_65 = 65;
    public static final int BAND_66 = 66;
    public static final int BAND_68 = 68;
    public static final int BAND_7 = 7;
    public static final int BAND_70 = 70;
    public static final int BAND_8 = 8;
    public static final int BAND_9 = 9;

    public static final String toString(int i) {
        if (i == 1) {
            return "BAND_1";
        }
        if (i == 2) {
            return "BAND_2";
        }
        if (i == 3) {
            return "BAND_3";
        }
        if (i == 4) {
            return "BAND_4";
        }
        if (i == 5) {
            return "BAND_5";
        }
        if (i == 6) {
            return "BAND_6";
        }
        if (i == 7) {
            return "BAND_7";
        }
        if (i == 8) {
            return "BAND_8";
        }
        if (i == 9) {
            return "BAND_9";
        }
        if (i == 10) {
            return "BAND_10";
        }
        if (i == 11) {
            return "BAND_11";
        }
        if (i == 12) {
            return "BAND_12";
        }
        if (i == 13) {
            return "BAND_13";
        }
        if (i == 14) {
            return "BAND_14";
        }
        if (i == 17) {
            return "BAND_17";
        }
        if (i == 18) {
            return "BAND_18";
        }
        if (i == 19) {
            return "BAND_19";
        }
        if (i == 20) {
            return "BAND_20";
        }
        if (i == 21) {
            return "BAND_21";
        }
        if (i == 22) {
            return "BAND_22";
        }
        if (i == 23) {
            return "BAND_23";
        }
        if (i == 24) {
            return "BAND_24";
        }
        if (i == 25) {
            return "BAND_25";
        }
        if (i == 26) {
            return "BAND_26";
        }
        if (i == 27) {
            return "BAND_27";
        }
        if (i == 28) {
            return "BAND_28";
        }
        if (i == 30) {
            return "BAND_30";
        }
        if (i == 31) {
            return "BAND_31";
        }
        if (i == 33) {
            return "BAND_33";
        }
        if (i == 34) {
            return "BAND_34";
        }
        if (i == 35) {
            return "BAND_35";
        }
        if (i == 36) {
            return "BAND_36";
        }
        if (i == 37) {
            return "BAND_37";
        }
        if (i == 38) {
            return "BAND_38";
        }
        if (i == 39) {
            return "BAND_39";
        }
        if (i == 40) {
            return "BAND_40";
        }
        if (i == 41) {
            return "BAND_41";
        }
        if (i == 42) {
            return "BAND_42";
        }
        if (i == 43) {
            return "BAND_43";
        }
        if (i == 44) {
            return "BAND_44";
        }
        if (i == 45) {
            return "BAND_45";
        }
        if (i == 46) {
            return "BAND_46";
        }
        if (i == 47) {
            return "BAND_47";
        }
        if (i == 48) {
            return "BAND_48";
        }
        if (i == 65) {
            return "BAND_65";
        }
        if (i == 66) {
            return "BAND_66";
        }
        if (i == 68) {
            return "BAND_68";
        }
        if (i == 70) {
            return "BAND_70";
        }
        return "0x" + Integer.toHexString(i);
    }

    public static final String dumpBitfield(int i) {
        ArrayList arrayList = new ArrayList();
        int i2 = 1;
        if ((i & 1) == 1) {
            arrayList.add("BAND_1");
        } else {
            i2 = 0;
        }
        if ((i & 2) == 2) {
            arrayList.add("BAND_2");
            i2 |= 2;
        }
        if ((i & 3) == 3) {
            arrayList.add("BAND_3");
            i2 = 3;
        }
        if ((i & 4) == 4) {
            arrayList.add("BAND_4");
            i2 |= 4;
        }
        if ((i & 5) == 5) {
            arrayList.add("BAND_5");
            i2 |= 5;
        }
        if ((i & 6) == 6) {
            arrayList.add("BAND_6");
            i2 |= 6;
        }
        if ((i & 7) == 7) {
            arrayList.add("BAND_7");
            i2 = 7;
        }
        if ((i & 8) == 8) {
            arrayList.add("BAND_8");
            i2 |= 8;
        }
        if ((i & 9) == 9) {
            arrayList.add("BAND_9");
            i2 |= 9;
        }
        if ((i & 10) == 10) {
            arrayList.add("BAND_10");
            i2 |= 10;
        }
        if ((i & 11) == 11) {
            arrayList.add("BAND_11");
            i2 |= 11;
        }
        if ((i & 12) == 12) {
            arrayList.add("BAND_12");
            i2 |= 12;
        }
        if ((i & 13) == 13) {
            arrayList.add("BAND_13");
            i2 |= 13;
        }
        if ((i & 14) == 14) {
            arrayList.add("BAND_14");
            i2 |= 14;
        }
        if ((i & 17) == 17) {
            arrayList.add("BAND_17");
            i2 |= 17;
        }
        if ((i & 18) == 18) {
            arrayList.add("BAND_18");
            i2 |= 18;
        }
        if ((i & 19) == 19) {
            arrayList.add("BAND_19");
            i2 |= 19;
        }
        if ((i & 20) == 20) {
            arrayList.add("BAND_20");
            i2 |= 20;
        }
        if ((i & 21) == 21) {
            arrayList.add("BAND_21");
            i2 |= 21;
        }
        if ((i & 22) == 22) {
            arrayList.add("BAND_22");
            i2 |= 22;
        }
        if ((i & 23) == 23) {
            arrayList.add("BAND_23");
            i2 |= 23;
        }
        if ((i & 24) == 24) {
            arrayList.add("BAND_24");
            i2 |= 24;
        }
        if ((i & 25) == 25) {
            arrayList.add("BAND_25");
            i2 |= 25;
        }
        if ((i & 26) == 26) {
            arrayList.add("BAND_26");
            i2 |= 26;
        }
        if ((i & 27) == 27) {
            arrayList.add("BAND_27");
            i2 |= 27;
        }
        if ((i & 28) == 28) {
            arrayList.add("BAND_28");
            i2 |= 28;
        }
        if ((i & 30) == 30) {
            arrayList.add("BAND_30");
            i2 |= 30;
        }
        if ((i & 31) == 31) {
            arrayList.add("BAND_31");
            i2 = 31;
        }
        if ((i & 33) == 33) {
            arrayList.add("BAND_33");
            i2 |= 33;
        }
        if ((i & 34) == 34) {
            arrayList.add("BAND_34");
            i2 |= 34;
        }
        if ((i & 35) == 35) {
            arrayList.add("BAND_35");
            i2 |= 35;
        }
        if ((i & 36) == 36) {
            arrayList.add("BAND_36");
            i2 |= 36;
        }
        if ((i & 37) == 37) {
            arrayList.add("BAND_37");
            i2 |= 37;
        }
        if ((i & 38) == 38) {
            arrayList.add("BAND_38");
            i2 |= 38;
        }
        if ((i & 39) == 39) {
            arrayList.add("BAND_39");
            i2 |= 39;
        }
        if ((i & 40) == 40) {
            arrayList.add("BAND_40");
            i2 |= 40;
        }
        if ((i & 41) == 41) {
            arrayList.add("BAND_41");
            i2 |= 41;
        }
        if ((i & 42) == 42) {
            arrayList.add("BAND_42");
            i2 |= 42;
        }
        if ((i & 43) == 43) {
            arrayList.add("BAND_43");
            i2 |= 43;
        }
        if ((i & 44) == 44) {
            arrayList.add("BAND_44");
            i2 |= 44;
        }
        if ((i & 45) == 45) {
            arrayList.add("BAND_45");
            i2 |= 45;
        }
        if ((i & 46) == 46) {
            arrayList.add("BAND_46");
            i2 |= 46;
        }
        if ((i & 47) == 47) {
            arrayList.add("BAND_47");
            i2 |= 47;
        }
        if ((i & 48) == 48) {
            arrayList.add("BAND_48");
            i2 |= 48;
        }
        if ((i & 65) == 65) {
            arrayList.add("BAND_65");
            i2 |= 65;
        }
        if ((i & 66) == 66) {
            arrayList.add("BAND_66");
            i2 |= 66;
        }
        if ((i & 68) == 68) {
            arrayList.add("BAND_68");
            i2 |= 68;
        }
        if ((i & 70) == 70) {
            arrayList.add("BAND_70");
            i2 |= 70;
        }
        if (i != i2) {
            arrayList.add("0x" + Integer.toHexString(i & (~i2)));
        }
        return String.join(" | ", arrayList);
    }
}
