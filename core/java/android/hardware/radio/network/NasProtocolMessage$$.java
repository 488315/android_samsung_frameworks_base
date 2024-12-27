package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;

import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface NasProtocolMessage$$ {
    static String toString(int _aidl_v) {
        return _aidl_v == 0
                ? "UNKNOWN"
                : _aidl_v == 1
                        ? "ATTACH_REQUEST"
                        : _aidl_v == 2
                                ? "IDENTITY_RESPONSE"
                                : _aidl_v == 3
                                        ? "DETACH_REQUEST"
                                        : _aidl_v == 4
                                                ? "TRACKING_AREA_UPDATE_REQUEST"
                                                : _aidl_v == 5
                                                        ? "LOCATION_UPDATE_REQUEST"
                                                        : _aidl_v == 6
                                                                ? "AUTHENTICATION_AND_CIPHERING_RESPONSE"
                                                                : _aidl_v == 7
                                                                        ? "REGISTRATION_REQUEST"
                                                                        : _aidl_v == 8
                                                                                ? "DEREGISTRATION_REQUEST"
                                                                                : _aidl_v == 9
                                                                                        ? "CM_REESTABLISHMENT_REQUEST"
                                                                                        : _aidl_v
                                                                                                        == 10
                                                                                                ? "CM_SERVICE_REQUEST"
                                                                                                : _aidl_v
                                                                                                                == 11
                                                                                                        ? "IMSI_DETACH_INDICATION"
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
