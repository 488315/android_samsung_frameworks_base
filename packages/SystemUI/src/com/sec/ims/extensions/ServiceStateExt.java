package com.sec.ims.extensions;

import android.telephony.ServiceState;
import android.util.Log;

/* loaded from: classes4.dex */
public class ServiceStateExt {
    public static final int LTE_IMS_VOICE_AVAIL_NOT_SUPPORT = 3;
    public static final int LTE_IMS_VOICE_AVAIL_SUPPORT = 2;
    public static final int LTE_IMS_VOICE_AVAIL_UNKNOWN = 1;
    private static final String TAG = "ServiceStateExt";
    public static final int SNAPSHOT_STATUS_DEACTIVATED = getIntField("SNAPSHOT_STATUS_DEACTIVATED", 0);
    public static final int SNAPSHOT_STATUS_ACTIVATED = getIntField("SNAPSHOT_STATUS_ACTIVATED", 1);

    public static final int getIntField(String str, int i) {
        try {
            return ServiceState.class.getDeclaredField(str).getInt(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            Log.e(TAG, e.getClass().getSimpleName() + "!! " + e.getMessage());
            return i;
        }
    }
}
