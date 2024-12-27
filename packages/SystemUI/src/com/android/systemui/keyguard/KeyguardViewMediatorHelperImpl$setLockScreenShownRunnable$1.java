package com.android.systemui.keyguard;

import android.app.ActivityTaskManager;
import android.os.RemoteException;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardViewMediatorHelperImpl$setLockScreenShownRunnable$1 implements Runnable {
    public boolean aodShowing;
    public boolean showing;

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        try {
            ActivityTaskManager.getService().setLockScreenShown(this.showing, this.aodShowing);
            Log.i("KeyguardViewMediator", "setLockScreenShown " + this.showing + " " + this.aodShowing);
            z = true;
        } catch (RemoteException unused) {
            Log.e("KeyguardViewMediator", "setLockScreenShown is failed");
            z = false;
        }
        KeyguardDumpLog.state$default(KeyguardDumpLog.INSTANCE, 1, z, this.showing, this.aodShowing, 0, 0, 48);
    }
}
