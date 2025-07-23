package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface SecurityAlgorithm$$ {
    static String toString(int i) {
        if (i == 0) {
            return "A50";
        }
        if (i == 1) {
            return "A51";
        }
        if (i == 2) {
            return "A52";
        }
        if (i == 3) {
            return "A53";
        }
        if (i == 4) {
            return "A54";
        }
        if (i == 14) {
            return "GEA0";
        }
        if (i == 15) {
            return "GEA1";
        }
        if (i == 16) {
            return "GEA2";
        }
        if (i == 17) {
            return "GEA3";
        }
        if (i == 18) {
            return "GEA4";
        }
        if (i == 19) {
            return "GEA5";
        }
        if (i == 29) {
            return "UEA0";
        }
        if (i == 30) {
            return "UEA1";
        }
        if (i == 31) {
            return "UEA2";
        }
        if (i == 41) {
            return "EEA0";
        }
        if (i == 42) {
            return "EEA1";
        }
        if (i == 43) {
            return "EEA2";
        }
        if (i == 44) {
            return "EEA3";
        }
        if (i == 55) {
            return "NEA0";
        }
        if (i == 56) {
            return "NEA1";
        }
        if (i == 57) {
            return "NEA2";
        }
        if (i == 58) {
            return "NEA3";
        }
        if (i == 66) {
            return "SIP_NO_IPSEC_CONFIG";
        }
        if (i == 67) {
            return "IMS_NULL";
        }
        if (i == 68) {
            return "SIP_NULL";
        }
        if (i == 69) {
            return "AES_GCM";
        }
        if (i == 70) {
            return "AES_GMAC";
        }
        if (i == 71) {
            return "AES_CBC";
        }
        if (i == 72) {
            return "DES_EDE3_CBC";
        }
        if (i == 73) {
            return "AES_EDE3_CBC";
        }
        if (i == 74) {
            return "HMAC_SHA1_96";
        }
        if (i == 75) {
            return "HMAC_MD5_96";
        }
        if (i == 85) {
            return "RTP";
        }
        if (i == 86) {
            return "SRTP_NULL";
        }
        if (i == 87) {
            return "SRTP_AES_COUNTER";
        }
        if (i == 88) {
            return "SRTP_AES_F8";
        }
        if (i == 89) {
            return "SRTP_HMAC_SHA1";
        }
        if (i == 99) {
            return "ENCR_AES_GCM_16";
        }
        if (i == 100) {
            return "ENCR_AES_CBC";
        }
        if (i == 101) {
            return "AUTH_HMAC_SHA2_256_128";
        }
        if (i == 113) {
            return "UNKNOWN";
        }
        if (i == 114) {
            return "OTHER";
        }
        if (i == 124) {
            return "ORYX";
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
