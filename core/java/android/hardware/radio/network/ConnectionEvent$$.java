package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import java.lang.reflect.Array;
import java.util.StringJoiner;

public interface ConnectionEvent$$ {
    static String toString(int _aidl_v) {
        return _aidl_v == 0
                ? "CS_SIGNALLING_GSM"
                : _aidl_v == 1
                        ? "PS_SIGNALLING_GPRS"
                        : _aidl_v == 2
                                ? "CS_SIGNALLING_3G"
                                : _aidl_v == 3
                                        ? "PS_SIGNALLING_3G"
                                        : _aidl_v == 4
                                                ? "NAS_SIGNALLING_LTE"
                                                : _aidl_v == 5
                                                        ? "AS_SIGNALLING_LTE"
                                                        : _aidl_v == 6
                                                                ? "VOLTE_SIP"
                                                                : _aidl_v == 7
                                                                        ? "VOLTE_SIP_SOS"
                                                                        : _aidl_v == 8
                                                                                ? "VOLTE_RTP"
                                                                                : _aidl_v == 9
                                                                                        ? "VOLTE_RTP_SOS"
                                                                                        : _aidl_v
                                                                                                        == 10
                                                                                                ? "NAS_SIGNALLING_5G"
                                                                                                : _aidl_v
                                                                                                                == 11
                                                                                                        ? "AS_SIGNALLING_5G"
                                                                                                        : _aidl_v
                                                                                                                        == 12
                                                                                                                ? "VONR_SIP"
                                                                                                                : _aidl_v
                                                                                                                                == 13
                                                                                                                        ? "VONR_SIP_SOS"
                                                                                                                        : _aidl_v
                                                                                                                                        == 14
                                                                                                                                ? "VONR_RTP"
                                                                                                                                : _aidl_v
                                                                                                                                                == 15
                                                                                                                                        ? "VONR_RTP_SOS"
                                                                                                                                        : Integer
                                                                                                                                                .toString(
                                                                                                                                                        _aidl_v);
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
        StringJoiner _aidl_sj =
                new StringJoiner(
                        ", ",
                        NavigationBarInflaterView.SIZE_MOD_START,
                        NavigationBarInflaterView.SIZE_MOD_END);
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
