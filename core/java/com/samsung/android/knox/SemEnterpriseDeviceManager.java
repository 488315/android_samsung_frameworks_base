package com.samsung.android.knox;

import android.content.Context;
import android.p009os.Bundle;
import android.p009os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;

/* loaded from: classes5.dex */
public class SemEnterpriseDeviceManager {
    private static String TAG = "SemEnterpriseDeviceManager";
    private static SemEnterpriseDeviceManager mSemEnterpriseDeviceManager = null;

    private SemEnterpriseDeviceManager() {
    }

    public static synchronized SemEnterpriseDeviceManager getInstance(Context context) {
        SemEnterpriseDeviceManager semEnterpriseDeviceManager;
        synchronized (SemEnterpriseDeviceManager.class) {
            if (mSemEnterpriseDeviceManager == null) {
                mSemEnterpriseDeviceManager = new SemEnterpriseDeviceManager();
            }
            semEnterpriseDeviceManager = mSemEnterpriseDeviceManager;
        }
        return semEnterpriseDeviceManager;
    }

    public Bundle getApplicationRestrictions(String packageName) {
        try {
            IEDMProxy lService = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (lService != null) {
                String pn = packageName != null ? packageName : new String();
                return lService.getApplicationRestrictions(pn, 0);
            }
            return new Bundle();
        } catch (RemoteException re) {
            throw re.rethrowFromSystemServer();
        }
    }
}
