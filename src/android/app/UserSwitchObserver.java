package android.app;

import android.app.IUserSwitchObserver;
import android.os.IRemoteCallback;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class UserSwitchObserver extends IUserSwitchObserver.Stub {
    @Override // android.app.IUserSwitchObserver
    public void onForegroundProfileSwitch(int i) throws RemoteException {
    }

    @Override // android.app.IUserSwitchObserver
    public void onLockedBootComplete(int i) throws RemoteException {
    }

    @Override // android.app.IUserSwitchObserver
    public void onUserSwitchComplete(int i) throws RemoteException {
    }

    @Override // android.app.IUserSwitchObserver
    public void onBeforeUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        if (iRemoteCallback != null) {
            iRemoteCallback.sendResult(null);
        }
    }

    public void onUserSwitching(int i, IRemoteCallback iRemoteCallback) throws RemoteException {
        if (iRemoteCallback != null) {
            iRemoteCallback.sendResult(null);
        }
    }
}
