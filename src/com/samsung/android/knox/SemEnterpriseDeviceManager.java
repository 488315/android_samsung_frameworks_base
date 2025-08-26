package com.samsung.android.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;

/* loaded from: classes6.dex */
public class SemEnterpriseDeviceManager {
    private static String TAG = "SemEnterpriseDeviceManager";
    private static volatile SemEnterpriseDeviceManager mSemEnterpriseDeviceManager;
    private final Context mContext;

    private SemEnterpriseDeviceManager(Context context) {
        this.mContext = context;
    }

    private static SemEnterpriseDeviceManager createInstance(Context context) {
        return new SemEnterpriseDeviceManager(context);
    }

    public static synchronized SemEnterpriseDeviceManager getInstance(Context context) {
        if (mSemEnterpriseDeviceManager == null && context != null) {
            mSemEnterpriseDeviceManager = createInstance(context);
        }
        return mSemEnterpriseDeviceManager;
    }

    public Bundle getApplicationRestrictions(String str) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                if (str == null) {
                    str = new String();
                }
                return service.getApplicationRestrictions(str, 0);
            }
            return new Bundle();
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

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

    public void setKeyedAppStatesReport(String str, Bundle bundle, int i) {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                service.setKeyedAppStatesReport(this.mContext.getPackageName(), safePackageName(str), safeBundle(bundle), i);
            }
        } catch (RemoteException e) {
            throw e.rethrowFromSystemServer();
        }
    }

    private String safePackageName(String str) {
        return str != null ? str : "";
    }

    private Bundle safeBundle(Bundle bundle) {
        return bundle != null ? bundle : new Bundle();
    }
}
