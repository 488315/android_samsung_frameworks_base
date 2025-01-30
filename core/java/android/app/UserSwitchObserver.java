package android.app;

import android.p009os.IRemoteCallback;
import android.p009os.RemoteException;

/* loaded from: classes.dex */
public class UserSwitchObserver extends IUserSwitchObserver.Stub {
  @Override // android.app.IUserSwitchObserver
  public void onBeforeUserSwitching(int newUserId) throws RemoteException {}

  public void onUserSwitching(int newUserId, IRemoteCallback reply) throws RemoteException {
    if (reply != null) {
      reply.sendResult(null);
    }
  }

  @Override // android.app.IUserSwitchObserver
  public void onUserSwitchComplete(int newUserId) throws RemoteException {}

  @Override // android.app.IUserSwitchObserver
  public void onForegroundProfileSwitch(int newProfileId) throws RemoteException {}

  @Override // android.app.IUserSwitchObserver
  public void onLockedBootComplete(int newUserId) throws RemoteException {}
}
