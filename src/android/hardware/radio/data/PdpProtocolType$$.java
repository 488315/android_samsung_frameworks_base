package android.hardware.radio.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.telephony.CarrierConfigManager;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface PdpProtocolType$$ {
    static String toString(int i) {
        if (i == -1) {
            return "UNKNOWN";
        }
        if (i == 0) {
            return CarrierConfigManager.Apn.PROTOCOL_IPV4;
        }
        if (i == 1) {
            return "IPV6";
        }
        if (i == 2) {
            return CarrierConfigManager.Apn.PROTOCOL_IPV4V6;
        }
        if (i == 3) {
            return "PPP";
        }
        if (i == 4) {
            return "NON_IP";
        }
        if (i == 5) {
            return "UNSTRUCTURED";
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
