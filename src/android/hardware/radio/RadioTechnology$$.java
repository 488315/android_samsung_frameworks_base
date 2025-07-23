package android.hardware.radio;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.telephony.DctConstants;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface RadioTechnology$$ {
    static String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "GPRS";
        }
        if (i == 2) {
            return "EDGE";
        }
        if (i == 3) {
            return "UMTS";
        }
        if (i == 4) {
            return "IS95A";
        }
        if (i == 5) {
            return "IS95B";
        }
        if (i == 6) {
            return "ONE_X_RTT";
        }
        if (i == 7) {
            return "EVDO_0";
        }
        if (i == 8) {
            return "EVDO_A";
        }
        if (i == 9) {
            return "HSDPA";
        }
        if (i == 10) {
            return "HSUPA";
        }
        if (i == 11) {
            return "HSPA";
        }
        if (i == 12) {
            return "EVDO_B";
        }
        if (i == 13) {
            return "EHRPD";
        }
        if (i == 14) {
            return DctConstants.RAT_NAME_LTE;
        }
        if (i == 15) {
            return "HSPAP";
        }
        if (i == 16) {
            return "GSM";
        }
        if (i == 17) {
            return "TD_SCDMA";
        }
        if (i == 18) {
            return "IWLAN";
        }
        if (i == 19) {
            return "LTE_CA";
        }
        if (i == 20) {
            return "NR";
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
