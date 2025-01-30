package com.sec.ims.extensions;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Message;
import android.util.Log;
import com.android.systemui.controls.management.ControlsListingControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.wifi.SemWifiManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class WiFiManagerExt {
    public static final int SEC_COMMAND_ID_DELAY_DISCONNECT_TRANSITION = getIntFromField("SEC_COMMAND_ID_DELAY_DISCONNECT_TRANSITION", 81);
    public static final String SEM_WIFI_SERVICE = "sem_wifi";
    private static final String TAG = "WiFiManagerExt";

    public static int callSECApi(WifiManager wifiManager, Message message) {
        try {
            return ((Integer) ReflectionUtils.invoke2(WifiManager.class.getMethod("callSECApi", Message.class), wifiManager, message)).intValue();
        } catch (IllegalStateException | NoSuchMethodException e) {
            Log.e(TAG, e.getClass().getSimpleName() + "!! " + e.getMessage());
            return -1;
        }
    }

    public static int getIntFromField(String str, int i) {
        try {
            return WifiManager.class.getDeclaredField(str).getInt(null);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException e) {
            Log.e(TAG, e.getClass().getSimpleName() + "!! " + e.getMessage());
            return i;
        }
    }

    public static void setImsCallEstablished(Context context, boolean z) {
        SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(SEM_WIFI_SERVICE);
        if (semWifiManager != null) {
            ControlsListingControllerImpl$$ExternalSyntheticOutline0.m117m("setImsCallEstablished: semWifiManager.setImsCallEstablished : ", z, TAG);
            try {
                ReflectionUtils.invoke2(SemWifiManager.class.getMethod("setImsCallEstablished", Boolean.TYPE), semWifiManager, Boolean.valueOf(z));
            } catch (IllegalStateException | NoSuchMethodException e) {
                Log.e(TAG, e.getClass().getSimpleName() + "!! " + e.getMessage());
            }
        }
    }

    public static void setMaxDtimInSuspendMode(Context context, boolean z) {
        SemWifiManager semWifiManager = (SemWifiManager) context.getSystemService(SEM_WIFI_SERVICE);
        if (semWifiManager != null) {
            try {
                ReflectionUtils.invoke2(SemWifiManager.class.getMethod("setMaxDtimInSuspendMode", Boolean.TYPE), semWifiManager, Boolean.valueOf(z));
            } catch (IllegalStateException | NoSuchMethodException e) {
                Log.e(TAG, e.getClass().getSimpleName() + "!! " + e.getMessage());
            }
        }
    }
}
