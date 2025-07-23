package com.samsung.android.knox.custom;

import android.sec.enterprise.EnterpriseDeviceManager;
import android.sec.enterprise.IEDMProxy;
import android.util.Log;

/* loaded from: classes6.dex */
public class CustomSystemManagerProxy {
    private static final String TAG = "CustomSystemManagerProxy";
    private static CustomSystemManagerProxy mProxy;

    public static CustomSystemManagerProxy getInstance() {
        if (mProxy == null) {
            mProxy = new CustomSystemManagerProxy();
        }
        return mProxy;
    }

    public boolean getExtendedCallInfoState() {
        try {
            IEDMProxy service = EnterpriseDeviceManager.EDMProxyServiceHelper.getService();
            if (service != null) {
                return service.getExtendedCallInfoState();
            }
            return true;
        } catch (Exception unused) {
            Log.d(TAG, "PXY-getExtendedCallInfoState returning default value");
            return true;
        }
    }
}
