package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface UtranBands$$ {
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
        if (i == 25) {
            return "BAND_25";
        }
        if (i == 26) {
            return "BAND_26";
        }
        if (i == 101) {
            return "BAND_A";
        }
        if (i == 102) {
            return "BAND_B";
        }
        if (i == 103) {
            return "BAND_C";
        }
        if (i == 104) {
            return "BAND_D";
        }
        if (i == 105) {
            return "BAND_E";
        }
        if (i == 106) {
            return "BAND_F";
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
