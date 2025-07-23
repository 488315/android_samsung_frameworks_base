package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface EutranBands$$ {
    static String toString(int i) {
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
        if (i == 49) {
            return "BAND_49";
        }
        if (i == 50) {
            return "BAND_50";
        }
        if (i == 51) {
            return "BAND_51";
        }
        if (i == 52) {
            return "BAND_52";
        }
        if (i == 53) {
            return "BAND_53";
        }
        if (i == 71) {
            return "BAND_71";
        }
        if (i == 72) {
            return "BAND_72";
        }
        if (i == 73) {
            return "BAND_73";
        }
        if (i == 74) {
            return "BAND_74";
        }
        if (i == 85) {
            return "BAND_85";
        }
        if (i == 87) {
            return "BAND_87";
        }
        if (i == 88) {
            return "BAND_88";
        }
        return Integer.toString(i);
    }

    static String arrayToString(Object obj) {
        if (obj == null) {
            return PerfettoProtoLogImpl.NULL_STRING;
        }
        Class<?> cls = obj.getClass();
        if (!cls.isArray()) {
            throw new IllegalArgumentException("not an array: " + obj);
        }
        Class<?> componentType = cls.getComponentType();
        StringJoiner stringJoiner = new StringJoiner(", ", NavigationBarInflaterView.SIZE_MOD_START, NavigationBarInflaterView.SIZE_MOD_END);
        int i = 0;
        if (componentType.isArray()) {
            while (i < Array.getLength(obj)) {
                stringJoiner.add(arrayToString(Array.get(obj, i)));
                i++;
            }
        } else {
            if (cls != int[].class) {
                throw new IllegalArgumentException("wrong type: " + cls);
            }
            int[] iArr = (int[]) obj;
            int length = iArr.length;
            while (i < length) {
                stringJoiner.add(toString(iArr[i]));
                i++;
            }
        }
        return stringJoiner.toString();
    }
}
