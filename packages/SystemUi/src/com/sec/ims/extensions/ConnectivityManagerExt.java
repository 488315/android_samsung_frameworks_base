package com.sec.ims.extensions;

import android.net.ConnectivityManager;
import java.lang.reflect.Field;
import java.net.InetAddress;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class ConnectivityManagerExt {
    public static final String LOG_TAG = "ConnectivityManagerExt";
    public static final int TYPE_BLUETOOTH = 7;
    public static final int TYPE_ETHERNET = 9;
    public static final int TYPE_MOBILE = 0;
    public static final int TYPE_MOBILE_CBS = 12;
    public static final int TYPE_MOBILE_EMERGENCY = 15;
    public static final int TYPE_MOBILE_HIPRI = 5;
    public static final int TYPE_MOBILE_IMS = 11;
    public static final int TYPE_MOBILE_XCAP = 27;
    public static final int TYPE_NONE = -1;
    public static final int TYPE_WIFI = 1;
    public static final int TYPE_WIMAX = 6;
    public static final int TYPE_MOBILE_FOTA = getIntField("TYPE_MOBILE_FOTA", 10);
    public static final int TYPE_WIFI_P2P = getIntField("TYPE_WIFI_P2P", 13);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public enum ConnectivityTypeExt {
        TYPE_NONE(-1),
        TYPE_MOBILE(0),
        TYPE_WIFI(1),
        TYPE_MOBILE_IMS(11),
        TYPE_MOBILE_CBS(12),
        TYPE_MOBILE_XCAP(27),
        TYPE_MOBILE_EMERGENCY(15);

        private final int mValue;

        ConnectivityTypeExt(int i) {
            this.mValue = i;
        }

        public int toInt() {
            return this.mValue;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static ConnectivityTypeExt valueOf(int i) {
            ConnectivityTypeExt connectivityTypeExt = TYPE_NONE;
            for (ConnectivityTypeExt connectivityTypeExt2 : values()) {
                if (connectivityTypeExt2.mValue == i) {
                    return connectivityTypeExt2;
                }
            }
            return connectivityTypeExt;
        }
    }

    public static ConnectivityTypeExt getConnectivityEnumType(int i) {
        return ConnectivityTypeExt.valueOf(i);
    }

    public static final int getIntField(String str, int i) {
        try {
            Field field = ReflectionUtils.getField(ConnectivityManager.class, str);
            if (field != null) {
                return field.getInt(null);
            }
        } catch (IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return i;
    }

    public static boolean removeRouteToHostAddress(ConnectivityManager connectivityManager, int i, InetAddress inetAddress) {
        try {
            return ((Boolean) ReflectionUtils.invoke2(connectivityManager.getClass().getMethod("removeRouteToHostAddress", Integer.TYPE, InetAddress.class), connectivityManager, Integer.valueOf(i), inetAddress)).booleanValue();
        } catch (IllegalStateException | NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean requestRouteToHostAddress(ConnectivityManager connectivityManager, int i, InetAddress inetAddress) {
        try {
            return ((Boolean) ReflectionUtils.invoke2(connectivityManager.getClass().getMethod("requestRouteToHostAddress", Integer.TYPE, InetAddress.class), connectivityManager, Integer.valueOf(i), inetAddress)).booleanValue();
        } catch (IllegalStateException | NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }
}
