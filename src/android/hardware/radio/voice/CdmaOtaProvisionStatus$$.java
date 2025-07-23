package android.hardware.radio.voice;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface CdmaOtaProvisionStatus$$ {
    static String toString(int i) {
        if (i == 0) {
            return "SPL_UNLOCKED";
        }
        if (i == 1) {
            return "SPC_RETRIES_EXCEEDED";
        }
        if (i == 2) {
            return "A_KEY_EXCHANGED";
        }
        if (i == 3) {
            return "SSD_UPDATED";
        }
        if (i == 4) {
            return "NAM_DOWNLOADED";
        }
        if (i == 5) {
            return "MDN_DOWNLOADED";
        }
        if (i == 6) {
            return "IMSI_DOWNLOADED";
        }
        if (i == 7) {
            return "PRL_DOWNLOADED";
        }
        if (i == 8) {
            return "COMMITTED";
        }
        if (i == 9) {
            return "OTAPA_STARTED";
        }
        if (i == 10) {
            return "OTAPA_STOPPED";
        }
        if (i == 11) {
            return "OTAPA_ABORTED";
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
