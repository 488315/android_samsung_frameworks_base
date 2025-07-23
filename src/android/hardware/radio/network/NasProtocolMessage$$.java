package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface NasProtocolMessage$$ {
    static String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "ATTACH_REQUEST";
        }
        if (i == 2) {
            return "IDENTITY_RESPONSE";
        }
        if (i == 3) {
            return "DETACH_REQUEST";
        }
        if (i == 4) {
            return "TRACKING_AREA_UPDATE_REQUEST";
        }
        if (i == 5) {
            return "LOCATION_UPDATE_REQUEST";
        }
        if (i == 6) {
            return "AUTHENTICATION_AND_CIPHERING_RESPONSE";
        }
        if (i == 7) {
            return "REGISTRATION_REQUEST";
        }
        if (i == 8) {
            return "DEREGISTRATION_REQUEST";
        }
        if (i == 9) {
            return "CM_REESTABLISHMENT_REQUEST";
        }
        if (i == 10) {
            return "CM_SERVICE_REQUEST";
        }
        if (i == 11) {
            return "IMSI_DETACH_INDICATION";
        }
        if (i == 12) {
            return "THREAT_IDENTIFIER_FALSE";
        }
        if (i == 13) {
            return "THREAT_IDENTIFIER_TRUE";
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
