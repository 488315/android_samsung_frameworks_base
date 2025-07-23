package com.android.wm.shell.freeform;

import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import java.util.concurrent.ExecutorService;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerSystemProxy$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ Context f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ FreeformContainerSystemProxy$$ExternalSyntheticLambda2(Context context, int i) {
        this.f$0 = context;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        Context context = this.f$0;
        int i = this.f$1;
        ExecutorService executorService = FreeformContainerSystemProxy.mExecutor;
        try {
            ActivityTaskManager.getService().moveTaskToFront(ActivityThread.currentActivityThread().getApplicationThread(), context.getPackageName(), i, 0, (Bundle) null);
        } catch (RemoteException e) {
            Log.w("FreeformContainer", "[FreeformContainerSystemProxy] Failed to moveTaskToFront: " + e);
            throw e.rethrowFromSystemServer();
        }
    }
}
