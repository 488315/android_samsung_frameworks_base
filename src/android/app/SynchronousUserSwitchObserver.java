package android.app;

import android.os.IRemoteCallback;
import android.os.RemoteException;

/* loaded from: classes.dex */
public abstract class SynchronousUserSwitchObserver extends UserSwitchObserver {
    public abstract void onUserSwitching(int i) throws RemoteException;

    @Override // android.app.UserSwitchObserver, android.app.IUserSwitchObserver
    public final void onUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        try {
            onUserSwitching(i);
        } finally {
            if (iRemoteCallback != null) {
                iRemoteCallback.sendResult(null);
            }
        }
    }
}
