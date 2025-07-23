package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface NgranBands$$ {
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
        if (i == 5) {
            return "BAND_5";
        }
        if (i == 7) {
            return "BAND_7";
        }
        if (i == 8) {
            return "BAND_8";
        }
        if (i == 12) {
            return "BAND_12";
        }
        if (i == 14) {
            return "BAND_14";
        }
        if (i == 18) {
            return "BAND_18";
        }
        if (i == 20) {
            return "BAND_20";
        }
        if (i == 25) {
            return "BAND_25";
        }
        if (i == 26) {
            return "BAND_26";
        }
        if (i == 28) {
            return "BAND_28";
        }
        if (i == 29) {
            return "BAND_29";
        }
        if (i == 30) {
            return "BAND_30";
        }
        if (i == 34) {
            return "BAND_34";
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
        if (i == 46) {
            return "BAND_46";
        }
        if (i == 48) {
            return "BAND_48";
        }
        if (i == 50) {
            return "BAND_50";
        }
        if (i == 51) {
            return "BAND_51";
        }
        if (i == 53) {
            return "BAND_53";
        }
        if (i == 65) {
            return "BAND_65";
        }
        if (i == 66) {
            return "BAND_66";
        }
        if (i == 70) {
            return "BAND_70";
        }
        if (i == 71) {
            return "BAND_71";
        }
        if (i == 74) {
            return "BAND_74";
        }
        if (i == 75) {
            return "BAND_75";
        }
        if (i == 76) {
            return "BAND_76";
        }
        if (i == 77) {
            return "BAND_77";
        }
        if (i == 78) {
            return "BAND_78";
        }
        if (i == 79) {
            return "BAND_79";
        }
        if (i == 80) {
            return "BAND_80";
        }
        if (i == 81) {
            return "BAND_81";
        }
        if (i == 82) {
            return "BAND_82";
        }
        if (i == 83) {
            return "BAND_83";
        }
        if (i == 84) {
            return "BAND_84";
        }
        if (i == 86) {
            return "BAND_86";
        }
        if (i == 89) {
            return "BAND_89";
        }
        if (i == 90) {
            return "BAND_90";
        }
        if (i == 91) {
            return "BAND_91";
        }
        if (i == 92) {
            return "BAND_92";
        }
        if (i == 93) {
            return "BAND_93";
        }
        if (i == 94) {
            return "BAND_94";
        }
        if (i == 95) {
            return "BAND_95";
        }
        if (i == 96) {
            return "BAND_96";
        }
        if (i == 257) {
            return "BAND_257";
        }
        if (i == 258) {
            return "BAND_258";
        }
        if (i == 260) {
            return "BAND_260";
        }
        if (i == 261) {
            return "BAND_261";
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
