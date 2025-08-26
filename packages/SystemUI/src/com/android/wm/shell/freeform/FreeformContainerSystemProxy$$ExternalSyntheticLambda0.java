package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.os.RemoteException;
import android.util.Log;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerSystemProxy$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ boolean f$0;

    @Override // java.lang.Runnable
    public final void run() {
        boolean z = this.f$0;
        ExecutorService executorService = FreeformContainerSystemProxy.mExecutor;
        try {
            ActivityManager.getService().setHasTopUi(z);
        } catch (RemoteException e) {
            Log.w("FreeformContainer", "[FreeformContainerSystemProxy] Failed to setHasTopUi: " + e);
            e.printStackTrace();
        }
    }
}
