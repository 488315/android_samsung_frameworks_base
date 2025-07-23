package android.hardware.radio.voice;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface AudioQuality$$ {
    static String toString(int i) {
        if (i == 0) {
            return "UNSPECIFIED";
        }
        if (i == 1) {
            return "AMR";
        }
        if (i == 2) {
            return "AMR_WB";
        }
        if (i == 3) {
            return "GSM_EFR";
        }
        if (i == 4) {
            return "GSM_FR";
        }
        if (i == 5) {
            return "GSM_HR";
        }
        if (i == 6) {
            return "EVRC";
        }
        if (i == 7) {
            return "EVRC_B";
        }
        if (i == 8) {
            return "EVRC_WB";
        }
        if (i == 9) {
            return "EVRC_NW";
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
