package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface ConnectionEvent$$ {
    static String toString(int i) {
        if (i == 0) {
            return "CS_SIGNALLING_GSM";
        }
        if (i == 1) {
            return "PS_SIGNALLING_GPRS";
        }
        if (i == 2) {
            return "CS_SIGNALLING_3G";
        }
        if (i == 3) {
            return "PS_SIGNALLING_3G";
        }
        if (i == 4) {
            return "NAS_SIGNALLING_LTE";
        }
        if (i == 5) {
            return "AS_SIGNALLING_LTE";
        }
        if (i == 6) {
            return "VOLTE_SIP";
        }
        if (i == 7) {
            return "VOLTE_SIP_SOS";
        }
        if (i == 8) {
            return "VOLTE_RTP";
        }
        if (i == 9) {
            return "VOLTE_RTP_SOS";
        }
        if (i == 10) {
            return "NAS_SIGNALLING_5G";
        }
        if (i == 11) {
            return "AS_SIGNALLING_5G";
        }
        if (i == 12) {
            return "VONR_SIP";
        }
        if (i == 13) {
            return "VONR_SIP_SOS";
        }
        if (i == 14) {
            return "VONR_RTP";
        }
        if (i == 15) {
            return "VONR_RTP_SOS";
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
