package vendor.samsung.hardware.radio.satellite;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.samsung.android.globalactions.presentation.strategies.WindowManagerFunctionStrategy;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public interface SehSatPowerType$$ {
    static String toString(int i) {
        if (i == 0) {
            return "OFF";
        }
        if (i == 1) {
            return "ON";
        }
        if (i == 4) {
            return WindowManagerFunctionStrategy.SHUTDOWN;
        }
        if (i == 6) {
            return "RESET";
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
