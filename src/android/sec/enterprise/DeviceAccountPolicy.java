package android.sec.enterprise;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.util.Log;

/* loaded from: classes3.dex */
public class DeviceAccountPolicy {
    private static String TAG = "DeviceAccountPolicy";

    public boolean isAccountRemovalAllowed(String str, String str2, boolean z) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.isAccountRemovalAllowed(str, str2, z);
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-isAccountRemovalAllowed returning default value");
            return true;
        }
    }
}
