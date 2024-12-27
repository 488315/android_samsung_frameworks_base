package vendor.samsung.hardware.radio.satellite;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public interface SehSatRegState$$ {
    static String toString(int _aidl_v) {
        return _aidl_v == 0 ? "NOT_REG_MT_NOT_SEARCHING_OP" : _aidl_v == 1 ? "REG_HOME" : _aidl_v == 2 ? "NOT_REG_MT_SEARCHING_OP" : _aidl_v == 3 ? "REG_DENIED" : _aidl_v == 4 ? "UNKNOWN" : _aidl_v == 5 ? "REG_ROAMING" : Integer.toString(_aidl_v);
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
