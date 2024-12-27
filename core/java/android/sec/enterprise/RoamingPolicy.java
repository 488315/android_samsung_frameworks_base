package android.sec.enterprise;

import android.sec.enterprise.content.SecContentProviderURI;
import android.util.Log;

public class RoamingPolicy {
    private static String TAG = SecContentProviderURI.ROAMINGPOLICY;

    public boolean isRoamingPushEnabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isRoamingPushEnabled();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isRoamingPushEnabled returning default value");
            return true;
        }
    }

    public boolean isRoamingDataEnabled() {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                return lService.isRoamingDataEnabled();
            }
            return true;
        } catch (Exception e) {
            Log.d(TAG, "PXY-isRoamingDataEnabled returning default value");
            return true;
        }
    }
}
