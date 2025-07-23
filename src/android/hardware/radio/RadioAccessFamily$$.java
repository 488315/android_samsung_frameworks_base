package android.hardware.radio;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.telephony.DctConstants;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface RadioAccessFamily$$ {
    static String toString(int i) {
        if (i == 1) {
            return "UNKNOWN";
        }
        if (i == 2) {
            return "GPRS";
        }
        if (i == 4) {
            return "EDGE";
        }
        if (i == 8) {
            return "UMTS";
        }
        if (i == 16) {
            return "IS95A";
        }
        if (i == 32) {
            return "IS95B";
        }
        if (i == 64) {
            return "ONE_X_RTT";
        }
        if (i == 128) {
            return "EVDO_0";
        }
        if (i == 256) {
            return "EVDO_A";
        }
        if (i == 512) {
            return "HSDPA";
        }
        if (i == 1024) {
            return "HSUPA";
        }
        if (i == 2048) {
            return "HSPA";
        }
        if (i == 4096) {
            return "EVDO_B";
        }
        if (i == 8192) {
            return "EHRPD";
        }
        if (i == 16384) {
            return DctConstants.RAT_NAME_LTE;
        }
        if (i == 32768) {
            return "HSPAP";
        }
        if (i == 65536) {
            return "GSM";
        }
        if (i == 131072) {
            return "TD_SCDMA";
        }
        if (i == 262144) {
            return "IWLAN";
        }
        if (i == 524288) {
            return "LTE_CA";
        }
        if (i == 1048576) {
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
