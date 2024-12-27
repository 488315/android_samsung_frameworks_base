package com.android.server.devicepolicy;

import android.os.RemoteException;
import android.os.ServiceManager;
import android.view.IWindowManager;

import com.android.server.utils.Slogf;

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
