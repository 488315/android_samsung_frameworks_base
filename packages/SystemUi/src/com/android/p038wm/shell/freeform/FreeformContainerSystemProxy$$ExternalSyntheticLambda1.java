package com.android.p038wm.shell.freeform;

import android.app.ActivityManager;
import android.os.RemoteException;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class FreeformContainerSystemProxy$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ boolean f$0;

    @Override // java.lang.Runnable
    public final void run() {
        try {
            ActivityManager.getService().setHasTopUi(this.f$0);
        } catch (RemoteException e) {
            Log.w("FreeformContainer", "[FreeformContainerSystemProxy] Failed to setHasTopUi: " + e);
            e.printStackTrace();
        }
    }
}
