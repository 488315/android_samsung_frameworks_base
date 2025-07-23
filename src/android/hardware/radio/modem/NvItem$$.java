package android.hardware.radio.modem;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface NvItem$$ {
    static String toString(int i) {
        if (i == 0) {
            return "INVALID";
        }
        if (i == 1) {
            return "CDMA_MEID";
        }
        if (i == 2) {
            return "CDMA_MIN";
        }
        if (i == 3) {
            return "CDMA_MDN";
        }
        if (i == 4) {
            return "CDMA_ACCOLC";
        }
        if (i == 11) {
            return "DEVICE_MSL";
        }
        if (i == 12) {
            return "RTN_RECONDITIONED_STATUS";
        }
        if (i == 13) {
            return "RTN_ACTIVATION_DATE";
        }
        if (i == 14) {
            return "RTN_LIFE_TIMER";
        }
        if (i == 15) {
            return "RTN_LIFE_CALLS";
        }
        if (i == 16) {
            return "RTN_LIFE_DATA_TX";
        }
        if (i == 17) {
            return "RTN_LIFE_DATA_RX";
        }
        if (i == 18) {
            return "OMADM_HFA_LEVEL";
        }
        if (i == 31) {
            return "MIP_PROFILE_NAI";
        }
        if (i == 32) {
            return "MIP_PROFILE_HOME_ADDRESS";
        }
        if (i == 33) {
            return "MIP_PROFILE_AAA_AUTH";
        }
        if (i == 34) {
            return "MIP_PROFILE_HA_AUTH";
        }
        if (i == 35) {
            return "MIP_PROFILE_PRI_HA_ADDR";
        }
        if (i == 36) {
            return "MIP_PROFILE_SEC_HA_ADDR";
        }
        if (i == 37) {
            return "MIP_PROFILE_REV_TUN_PREF";
        }
        if (i == 38) {
            return "MIP_PROFILE_HA_SPI";
        }
        if (i == 39) {
            return "MIP_PROFILE_AAA_SPI";
        }
        if (i == 40) {
            return "MIP_PROFILE_MN_HA_SS";
        }
        if (i == 41) {
            return "MIP_PROFILE_MN_AAA_SS";
        }
        if (i == 51) {
            return "CDMA_PRL_VERSION";
        }
        if (i == 52) {
            return "CDMA_BC10";
        }
        if (i == 53) {
            return "CDMA_BC14";
        }
        if (i == 54) {
            return "CDMA_SO68";
        }
        if (i == 55) {
            return "CDMA_SO73_COP0";
        }
        if (i == 56) {
            return "CDMA_SO73_COP1TO7";
        }
        if (i == 57) {
            return "CDMA_1X_ADVANCED_ENABLED";
        }
        if (i == 58) {
            return "CDMA_EHRPD_ENABLED";
        }
        if (i == 59) {
            return "CDMA_EHRPD_FORCED";
        }
        if (i == 71) {
            return "LTE_BAND_ENABLE_25";
        }
        if (i == 72) {
            return "LTE_BAND_ENABLE_26";
        }
        if (i == 73) {
            return "LTE_BAND_ENABLE_41";
        }
        if (i == 74) {
            return "LTE_SCAN_PRIORITY_25";
        }
        if (i == 75) {
            return "LTE_SCAN_PRIORITY_26";
        }
        if (i == 76) {
            return "LTE_SCAN_PRIORITY_41";
        }
        if (i == 77) {
            return "LTE_HIDDEN_BAND_PRIORITY_25";
        }
        if (i == 78) {
            return "LTE_HIDDEN_BAND_PRIORITY_26";
        }
        if (i == 79) {
            return "LTE_HIDDEN_BAND_PRIORITY_41";
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
