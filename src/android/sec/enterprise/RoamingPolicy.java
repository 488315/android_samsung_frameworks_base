package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class RoamingPolicy {
    private static String TAG = "RoamingPolicy";

    public boolean isRoamingPushEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isRoamingPushEnabled();
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isRoamingPushEnabled returning default value");
            return true;
        }
    }

    public boolean isRoamingDataEnabled() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isRoamingDataEnabled();
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isRoamingDataEnabled returning default value");
            return true;
        }
    }
}
