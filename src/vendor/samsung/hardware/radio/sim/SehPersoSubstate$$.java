package vendor.samsung.hardware.radio.sim;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes6.dex */
public interface SehPersoSubstate$$ {
    static String toString(int i) {
        if (i == 0) {
            return "UNKNOWN";
        }
        if (i == 1) {
            return "IN_PROGRESS";
        }
        if (i == 2) {
            return "READY";
        }
        if (i == 3) {
            return "SIM_NETWORK";
        }
        if (i == 4) {
            return "SIM_NETWORK_SUBSET";
        }
        if (i == 5) {
            return "SIM_CORPORATE";
        }
        if (i == 6) {
            return "SIM_SERVICE_PROVIDER";
        }
        if (i == 7) {
            return "SIM_SIM";
        }
        if (i == 8) {
            return "SIM_NETWORK_PUK";
        }
        if (i == 9) {
            return "SIM_NETWORK_SUBSET_PUK";
        }
        if (i == 10) {
            return "SIM_CORPORATE_PUK";
        }
        if (i == 11) {
            return "SIM_SERVICE_PROVIDER_PUK";
        }
        if (i == 12) {
            return "SIM_SIM_PUK";
        }
        if (i == 13) {
            return "RUIM_NETWORK1";
        }
        if (i == 14) {
            return "RUIM_NETWORK2";
        }
        if (i == 15) {
            return "RUIM_HRPD";
        }
        if (i == 16) {
            return "RUIM_CORPORATE";
        }
        if (i == 17) {
            return "RUIM_SERVICE_PROVIDER";
        }
        if (i == 18) {
            return "RUIM_RUIM";
        }
        if (i == 19) {
            return "RUIM_NETWORK1_PUK";
        }
        if (i == 20) {
            return "RUIM_NETWORK2_PUK";
        }
        if (i == 21) {
            return "RUIM_HRPD_PUK";
        }
        if (i == 22) {
            return "RUIM_CORPORATE_PUK";
        }
        if (i == 23) {
            return "RUIM_SERVICE_PROVIDER_PUK";
        }
        if (i == 24) {
            return "RUIM_RUIM_PUK";
        }
        if (i == 25) {
            return "SIM_SPN";
        }
        if (i == 26) {
            return "SIM_SPN_PUK";
        }
        if (i == 27) {
            return "SIM_SP_EHPLMN";
        }
        if (i == 28) {
            return "SIM_SP_EHPLMN_PUK";
        }
        if (i == 29) {
            return "SIM_ICCID";
        }
        if (i == 30) {
            return "SIM_ICCID_PUK";
        }
        if (i == 31) {
            return "SIM_IMPI";
        }
        if (i == 32) {
            return "SIM_IMPI_PUK";
        }
        if (i == 33) {
            return "SIM_NS_SP";
        }
        if (i == 34) {
            return "SIM_NS_SP_PUK";
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
