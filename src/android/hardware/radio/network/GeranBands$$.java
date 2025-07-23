package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface GeranBands$$ {
    static String toString(int i) {
        if (i == 1) {
            return "BAND_T380";
        }
        if (i == 2) {
            return "BAND_T410";
        }
        if (i == 3) {
            return "BAND_450";
        }
        if (i == 4) {
            return "BAND_480";
        }
        if (i == 5) {
            return "BAND_710";
        }
        if (i == 6) {
            return "BAND_750";
        }
        if (i == 7) {
            return "BAND_T810";
        }
        if (i == 8) {
            return "BAND_850";
        }
        if (i == 9) {
            return "BAND_P900";
        }
        if (i == 10) {
            return "BAND_E900";
        }
        if (i == 11) {
            return "BAND_R900";
        }
        if (i == 12) {
            return "BAND_DCS1800";
        }
        if (i == 13) {
            return "BAND_PCS1900";
        }
        if (i == 14) {
            return "BAND_ER900";
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
