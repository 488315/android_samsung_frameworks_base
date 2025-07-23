package android.hardware.radio.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface DataThrottlingAction$$ {
    static String toString(byte b) {
        if (b == 0) {
            return "NO_DATA_THROTTLING";
        }
        if (b == 1) {
            return "THROTTLE_SECONDARY_CARRIER";
        }
        if (b == 2) {
            return "THROTTLE_ANCHOR_CARRIER";
        }
        if (b == 3) {
            return "HOLD";
        }
        return Byte.toString(b);
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
            if (cls != byte[].class) {
                throw new IllegalArgumentException("wrong type: " + cls);
            }
            byte[] bArr = (byte[]) obj;
            int length = bArr.length;
            while (i < length) {
                stringJoiner.add(toString(bArr[i]));
                i++;
            }
        }
        return stringJoiner.toString();
    }
}
