package android.hardware.radio.data;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface ApnTypes$$ {
    static String toString(int i) {
        if (i == 0) {
            return KeyProperties.DIGEST_NONE;
        }
        if (i == 1) {
            return "DEFAULT";
        }
        if (i == 2) {
            return "MMS";
        }
        if (i == 4) {
            return "SUPL";
        }
        if (i == 8) {
            return "DUN";
        }
        if (i == 16) {
            return "HIPRI";
        }
        if (i == 32) {
            return "FOTA";
        }
        if (i == 64) {
            return "IMS";
        }
        if (i == 128) {
            return "CBS";
        }
        if (i == 256) {
            return "IA";
        }
        if (i == 512) {
            return "EMERGENCY";
        }
        if (i == 1024) {
            return "MCX";
        }
        if (i == 2048) {
            return "XCAP";
        }
        if (i == 4096) {
            return "VSIM";
        }
        if (i == 8192) {
            return "BIP";
        }
        if (i == 16384) {
            return "ENTERPRISE";
        }
        if (i == 32768) {
            return "RCS";
        }
        if (i == 65536) {
            return "OEM_PAID";
        }
        if (i == 131072) {
            return "OEM_PRIVATE";
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
