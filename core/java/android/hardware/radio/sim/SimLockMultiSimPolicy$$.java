package android.hardware.radio.sim;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface SimLockMultiSimPolicy$$ {
    static String toString(int _aidl_v) {
        return _aidl_v == 0 ? "NO_MULTISIM_POLICY" : _aidl_v == 1 ? "ONE_VALID_SIM_MUST_BE_PRESENT" : _aidl_v == 2 ? "APPLY_TO_ALL_SLOTS" : _aidl_v == 3 ? "APPLY_TO_ONLY_SLOT_1" : _aidl_v == 4 ? "VALID_SIM_MUST_PRESENT_ON_SLOT_1" : _aidl_v == 5 ? "ACTIVE_SERVICE_ON_SLOT_1_TO_UNBLOCK_OTHER_SLOTS" : _aidl_v == 6 ? "ACTIVE_SERVICE_ON_ANY_SLOT_TO_UNBLOCK_OTHER_SLOTS" : _aidl_v == 7 ? "ALL_SIMS_MUST_BE_VALID" : _aidl_v == 8 ? "SLOT_POLICY_OTHER" : Integer.toString(_aidl_v);
    }

    static String arrayToString(Object _aidl_v) {
        if (_aidl_v == null) {
            return "null";
        }
        Class<?> _aidl_cls = _aidl_v.getClass();
        if (!_aidl_cls.isArray()) {
            throw new IllegalArgumentException("not an array: " + _aidl_v);
        }
        Class<?> comp = _aidl_cls.getComponentType();
        StringJoiner _aidl_sj = new StringJoiner(", ", NavigationBarInflaterView.SIZE_MOD_START, NavigationBarInflaterView.SIZE_MOD_END);
        if (comp.isArray()) {
            for (int _aidl_i = 0; _aidl_i < Array.getLength(_aidl_v); _aidl_i++) {
                _aidl_sj.add(arrayToString(Array.get(_aidl_v, _aidl_i)));
            }
        } else {
            if (_aidl_cls != int[].class) {
                throw new IllegalArgumentException("wrong type: " + _aidl_cls);
            }
            for (int e : (int[]) _aidl_v) {
                _aidl_sj.add(toString(e));
            }
        }
        return _aidl_sj.toString();
    }
}
