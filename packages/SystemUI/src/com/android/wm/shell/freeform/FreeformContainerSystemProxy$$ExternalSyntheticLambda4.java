package com.android.wm.shell.freeform;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;
import java.util.concurrent.ExecutorService;

/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformContainerSystemProxy$$ExternalSyntheticLambda4 implements Runnable {
    public final /* synthetic */ FreeformContainerItem f$0;

    public /* synthetic */ FreeformContainerSystemProxy$$ExternalSyntheticLambda4(FreeformContainerItem freeformContainerItem) {
        this.f$0 = freeformContainerItem;
    }

    @Override // java.lang.Runnable
    public final void run() {
        FreeformContainerItem freeformContainerItem = this.f$0;
        ExecutorService executorService = FreeformContainerSystemProxy.mExecutor;
        try {
            ActivityTaskManager.getService().removeTaskWithFlags(freeformContainerItem.getTaskId(), 16);
        } catch (RemoteException e) {
            Log.w("FreeformContainer", "[FreeformContainerSystemProxy] Failed to removeTask: " + e);
            e.printStackTrace();
        }
    }
}
