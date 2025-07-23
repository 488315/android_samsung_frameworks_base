package android.sec.enterprise;

import android.os.Bundle;
import android.os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;

/* loaded from: classes3.dex */
public class ApplicationRestrictionsManager {
    private static String TAG = "ApplicationRestrictionsManager";

    public Bundle getApplicationRestrictions(String str, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                if (str == null) {
                    str = new String();
                }
                return service.getApplicationRestrictions(str, i);
            }
            return new Bundle();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }
}
