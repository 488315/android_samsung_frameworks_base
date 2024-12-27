package android.sec.enterprise;

import android.os.Bundle;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public class ApplicationRestrictionsManager {
    private static String TAG = "ApplicationRestrictionsManager";

    public Bundle getApplicationRestrictions(String packageName, int userId) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                String pn = packageName != null ? packageName : new String();
                return lService.getApplicationRestrictions(pn, userId);
            }
            return new Bundle();
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }
}
