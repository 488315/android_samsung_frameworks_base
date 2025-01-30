package com.sec.ims.extensions;

import android.content.Context;
import android.os.SemSystemProperties;
import android.telephony.TelephonyManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class TelephonyManagerExt {
    public static final String ACTION_PRECISE_DATA_CONNECTION_STATE_CHANGED = (String) ReflectionUtils.getValueOf("ACTION_PRECISE_DATA_CONNECTION_STATE_CHANGED", (Class<?>) TelephonyManager.class);
    public static final int NETWORK_CLASS_2_G = 1;
    public static final int NETWORK_CLASS_3_G = 2;
    public static final int NETWORK_CLASS_4_G = 3;
    public static final int NETWORK_CLASS_5_G = 4;
    public static final int NETWORK_CLASS_UNKNOWN = -1;
    public static final int NETWORK_TYPE_DC = 30;
    public static final int NETWORK_TYPE_GSM = 16;
    public static final int NETWORK_TYPE_IWLAN = 18;
    public static final int NETWORK_TYPE_LTE_CA = 19;
    public static final int NETWORK_TYPE_TDLTE = 31;
    public static final int NETWORK_TYPE_TD_SCDMA = 17;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum NetworkTypeExt {
        NETWORK_TYPE_UNKNOWN(0),
        NETWORK_TYPE_GPRS(1),
        NETWORK_TYPE_EDGE(2),
        NETWORK_TYPE_UMTS(3),
        NETWORK_TYPE_CDMA(4),
        NETWORK_TYPE_EVDO_0(5),
        NETWORK_TYPE_EVDO_A(6),
        NETWORK_TYPE_1xRTT(7),
        NETWORK_TYPE_HSDPA(8),
        NETWORK_TYPE_HSUPA(9),
        NETWORK_TYPE_HSPA(10),
        NETWORK_TYPE_IDEN(11),
        NETWORK_TYPE_EVDO_B(12),
        NETWORK_TYPE_LTE(13),
        NETWORK_TYPE_EHRPD(14),
        NETWORK_TYPE_HSPAP(15),
        NETWORK_TYPE_GSM(16),
        NETWORK_TYPE_TD_SCDMA(17),
        NETWORK_TYPE_IWLAN(18),
        NETWORK_TYPE_DC(30),
        NETWORK_TYPE_TDLTE(31);

        private final int mValue;

        NetworkTypeExt(int i) {
            this.mValue = i;
        }

        public int toInt() {
            return this.mValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static NetworkTypeExt valueOf(int i) {
            NetworkTypeExt networkTypeExt = NETWORK_TYPE_UNKNOWN;
            for (NetworkTypeExt networkTypeExt2 : values()) {
                if (networkTypeExt2.mValue == i) {
                    return networkTypeExt2;
                }
            }
            return networkTypeExt;
        }
    }

    public static byte[] getCurrentUATI(Context context) {
        try {
            Method declaredMethod = Class.forName(TelephonyManager.class.getName()).getDeclaredMethod("getITelephony", new Class[0]);
            declaredMethod.setAccessible(true);
            Object invoke = declaredMethod.invoke(context.getSystemService("phone"), new Object[0]);
            if (invoke != null) {
                return (byte[]) invoke.getClass().getMethod("getCurrentUATI", new Class[0]).invoke(invoke, new Object[0]);
            }
            return null;
        } catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getNetworkClass(int i) {
        switch (i) {
            case 1:
            case 2:
            case 4:
            case 7:
            case 11:
            case 16:
                return 1;
            case 3:
            case 5:
            case 6:
            case 8:
            case 9:
            case 10:
            case 12:
            case 14:
            case 15:
            case 17:
                return 2;
            case 13:
            case 18:
            case 19:
                return 3;
            case 20:
                return 4;
            default:
                return -1;
        }
    }

    public static NetworkTypeExt getNetworkEnumType(int i) {
        return NetworkTypeExt.valueOf(i);
    }

    public static String getNetworkTypeName(int i) {
        try {
            return (String) ReflectionUtils.invoke2(TelephonyManager.class.getMethod("getNetworkTypeName", Integer.TYPE), null, Integer.valueOf(i));
        } catch (IllegalStateException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] getPsismsc(TelephonyManager telephonyManager, int i) {
        try {
            return (byte[]) ReflectionUtils.invoke2(TelephonyManager.class.getMethod("getPsismsc", Integer.TYPE), telephonyManager, Integer.valueOf(i));
        } catch (IllegalStateException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getSubscriberId(TelephonyManager telephonyManager, int i) {
        try {
            return (String) ReflectionUtils.invoke2(TelephonyManager.class.getMethod("getSubscriberId", Integer.TYPE), telephonyManager, Integer.valueOf(i));
        } catch (IllegalStateException | NoSuchMethodException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isRoaming() {
        return "true".equalsIgnoreCase(SemSystemProperties.get("gsm.operator.isroaming"));
    }
}
