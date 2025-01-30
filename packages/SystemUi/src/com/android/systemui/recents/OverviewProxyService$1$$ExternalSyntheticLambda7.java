package com.android.systemui.recents;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class OverviewProxyService$1$$ExternalSyntheticLambda7 implements Runnable {
    @Override // java.lang.Runnable
    public final void run() {
        try {
            ActivityTaskManager.getService().stopSystemLockTaskMode();
        } catch (RemoteException unused) {
            Log.e("OverviewProxyService", "Failed to stop screen pinning");
        }
    }
}
