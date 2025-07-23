package android.hardware.radio.sim;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface SimLockMultiSimPolicy$$ {
    static String toString(int i) {
        if (i == 0) {
            return "NO_MULTISIM_POLICY";
        }
        if (i == 1) {
            return "ONE_VALID_SIM_MUST_BE_PRESENT";
        }
        if (i == 2) {
            return "APPLY_TO_ALL_SLOTS";
        }
        if (i == 3) {
            return "APPLY_TO_ONLY_SLOT_1";
        }
        if (i == 4) {
            return "VALID_SIM_MUST_PRESENT_ON_SLOT_1";
        }
        if (i == 5) {
            return "ACTIVE_SERVICE_ON_SLOT_1_TO_UNBLOCK_OTHER_SLOTS";
        }
        if (i == 6) {
            return "ACTIVE_SERVICE_ON_ANY_SLOT_TO_UNBLOCK_OTHER_SLOTS";
        }
        if (i == 7) {
            return "ALL_SIMS_MUST_BE_VALID";
        }
        if (i == 8) {
            return "SLOT_POLICY_OTHER";
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
