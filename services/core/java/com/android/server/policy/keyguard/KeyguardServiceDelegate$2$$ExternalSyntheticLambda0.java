package com.android.server.policy.keyguard;

import android.app.ActivityTaskManager;
import android.os.RemoteException;

public final /* synthetic */ class KeyguardServiceDelegate$2$$ExternalSyntheticLambda0
        implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        try {
            ActivityTaskManager.getService().setLockScreenShown(true, false);
        } catch (RemoteException unused) {
        }
    }
}
