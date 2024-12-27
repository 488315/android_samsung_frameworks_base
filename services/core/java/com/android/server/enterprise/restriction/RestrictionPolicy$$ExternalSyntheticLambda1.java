package com.android.server.enterprise.restriction;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;

public final /* synthetic */ class RestrictionPolicy$$ExternalSyntheticLambda1 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        String[] strArr = RestrictionPolicy.excludedAdminList;
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window"))
                    .refreshScreenCaptureDisabled();
        } catch (RemoteException e) {
            Log.e("RestrictionPolicy", "Unable to notify WindowManager.", e);
        }
    }
}
