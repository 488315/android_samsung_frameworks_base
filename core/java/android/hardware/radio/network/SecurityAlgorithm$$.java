package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface SecurityAlgorithm$$ {
    static String toString(int _aidl_v) {
        return _aidl_v == 0 ? "A50" : _aidl_v == 1 ? "A51" : _aidl_v == 2 ? "A52" : _aidl_v == 3 ? "A53" : _aidl_v == 4 ? "A54" : _aidl_v == 14 ? "GEA0" : _aidl_v == 15 ? "GEA1" : _aidl_v == 16 ? "GEA2" : _aidl_v == 17 ? "GEA3" : _aidl_v == 18 ? "GEA4" : _aidl_v == 19 ? "GEA5" : _aidl_v == 29 ? "UEA0" : _aidl_v == 30 ? "UEA1" : _aidl_v == 31 ? "UEA2" : _aidl_v == 41 ? "EEA0" : _aidl_v == 42 ? "EEA1" : _aidl_v == 43 ? "EEA2" : _aidl_v == 44 ? "EEA3" : _aidl_v == 55 ? "NEA0" : _aidl_v == 56 ? "NEA1" : _aidl_v == 57 ? "NEA2" : _aidl_v == 58 ? "NEA3" : _aidl_v == 66 ? "SIP_NO_IPSEC_CONFIG" : _aidl_v == 67 ? "IMS_NULL" : _aidl_v == 68 ? "SIP_NULL" : _aidl_v == 69 ? "AES_GCM" : _aidl_v == 70 ? "AES_GMAC" : _aidl_v == 71 ? "AES_CBC" : _aidl_v == 72 ? "DES_EDE3_CBC" : _aidl_v == 73 ? "AES_EDE3_CBC" : _aidl_v == 74 ? "HMAC_SHA1_96" : _aidl_v == 75 ? "HMAC_MD5_96" : _aidl_v == 85 ? "RTP" : _aidl_v == 86 ? "SRTP_NULL" : _aidl_v == 87 ? "SRTP_AES_COUNTER" : _aidl_v == 88 ? "SRTP_AES_F8" : _aidl_v == 89 ? "SRTP_HMAC_SHA1" : _aidl_v == 99 ? "ENCR_AES_GCM_16" : _aidl_v == 100 ? "ENCR_AES_CBC" : _aidl_v == 101 ? "AUTH_HMAC_SHA2_256_128" : _aidl_v == 113 ? "UNKNOWN" : _aidl_v == 114 ? "OTHER" : _aidl_v == 124 ? "ORYX" : Integer.toString(_aidl_v);
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
