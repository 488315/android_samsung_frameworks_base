package com.android.systemui.keyguard;

import android.app.ActivityTaskManager;
import android.os.RemoteException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 implements Runnable {
    public boolean aodShowing;
    public boolean showing;

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        try {
            ActivityTaskManager.getService().setLockScreenShown(this.showing, this.aodShowing);
            Log.m141i("KeyguardViewMediator", "setLockScreenShown " + this.showing + " " + this.aodShowing);
            z = true;
        } catch (RemoteException unused) {
            Log.m140e("KeyguardViewMediator", "setLockScreenShown is failed");
            z = false;
        }
        KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 1, z, this.showing, this.aodShowing, 0, 0, 48);
    }
}
