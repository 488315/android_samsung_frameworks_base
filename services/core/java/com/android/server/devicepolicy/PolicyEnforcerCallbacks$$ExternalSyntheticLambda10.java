package com.android.server.devicepolicy;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.IWindowManager;

import com.android.server.utils.Slogf;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class PolicyEnforcerCallbacks$$ExternalSyntheticLambda10
        implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        try {
            IWindowManager.Stub.asInterface(ServiceManager.getService("window"))
                    .refreshScreenCaptureDisabled();
        } catch (RemoteException e) {
            Slogf.w("PolicyEnforcerCallbacks", "Unable to notify WindowManager.", e);
        }
    }
}
