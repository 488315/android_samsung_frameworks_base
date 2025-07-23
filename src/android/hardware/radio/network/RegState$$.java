package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface RegState$$ {
    static String toString(int i) {
        if (i == 0) {
            return "NOT_REG_MT_NOT_SEARCHING_OP";
        }
        if (i == 1) {
            return "REG_HOME";
        }
        if (i == 2) {
            return "NOT_REG_MT_SEARCHING_OP";
        }
        if (i == 3) {
            return "REG_DENIED";
        }
        if (i == 4) {
            return "UNKNOWN";
        }
        if (i == 5) {
            return "REG_ROAMING";
        }
        if (i == 10) {
            return "NOT_REG_MT_NOT_SEARCHING_OP_EM";
        }
        if (i == 12) {
            return "NOT_REG_MT_SEARCHING_OP_EM";
        }
        if (i == 13) {
            return "REG_DENIED_EM";
        }
        if (i == 14) {
            return "UNKNOWN_EM";
        }
        if (i == 20) {
            return "REG_EM";
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
