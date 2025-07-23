package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface RadioBandMode$$ {
    static String toString(int i) {
        if (i == 0) {
            return "BAND_MODE_UNSPECIFIED";
        }
        if (i == 1) {
            return "BAND_MODE_EURO";
        }
        if (i == 2) {
            return "BAND_MODE_USA";
        }
        if (i == 3) {
            return "BAND_MODE_JPN";
        }
        if (i == 4) {
            return "BAND_MODE_AUS";
        }
        if (i == 5) {
            return "BAND_MODE_AUS_2";
        }
        if (i == 6) {
            return "BAND_MODE_CELL_800";
        }
        if (i == 7) {
            return "BAND_MODE_PCS";
        }
        if (i == 8) {
            return "BAND_MODE_JTACS";
        }
        if (i == 9) {
            return "BAND_MODE_KOREA_PCS";
        }
        if (i == 10) {
            return "BAND_MODE_5_450M";
        }
        if (i == 11) {
            return "BAND_MODE_IMT2000";
        }
        if (i == 12) {
            return "BAND_MODE_7_700M_2";
        }
        if (i == 13) {
            return "BAND_MODE_8_1800M";
        }
        if (i == 14) {
            return "BAND_MODE_9_900M";
        }
        if (i == 15) {
            return "BAND_MODE_10_800M_2";
        }
        if (i == 16) {
            return "BAND_MODE_EURO_PAMR_400M";
        }
        if (i == 17) {
            return "BAND_MODE_AWS";
        }
        if (i == 18) {
            return "BAND_MODE_USA_2500M";
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
