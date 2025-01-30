package android.app.admin;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.RemoteException;
import android.util.Log;
import android.view.SurfaceControlViewHost;

@SystemApi
/* loaded from: classes.dex */
public class DevicePolicyKeyguardService extends Service {
  private static final String TAG = "DevicePolicyKeyguardService";
  private IKeyguardCallback mCallback;
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  private final IKeyguardClient mClient = new IKeyguardClientStubC03821();

  /* renamed from: android.app.admin.DevicePolicyKeyguardService$1 */
  class IKeyguardClientStubC03821 extends IKeyguardClient.Stub {
    IKeyguardClientStubC03821() {}

    @Override // android.app.admin.IKeyguardClient
    public void onCreateKeyguardSurface(final IBinder hostInputToken, IKeyguardCallback callback) {
      DevicePolicyKeyguardService.this.mCallback = callback;
      DevicePolicyKeyguardService.this.mHandler.post(
          new Runnable() { // from class:
                           // android.app.admin.DevicePolicyKeyguardService$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
              DevicePolicyKeyguardService.IKeyguardClientStubC03821.this
                  .lambda$onCreateKeyguardSurface$0(hostInputToken);
            }
          });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreateKeyguardSurface$0(IBinder hostInputToken) {
      SurfaceControlViewHost.SurfacePackage surfacePackage =
          DevicePolicyKeyguardService.this.onCreateKeyguardSurface(hostInputToken);
      try {
        DevicePolicyKeyguardService.this.mCallback.onRemoteContentReady(surfacePackage);
      } catch (RemoteException e) {
        Log.m97e(DevicePolicyKeyguardService.TAG, "Failed to return created SurfacePackage", e);
      }
    }
  }

  @Override // android.app.Service
  public void onDestroy() {
    this.mHandler.removeCallbacksAndMessages(null);
  }

  @Override // android.app.Service
  public final IBinder onBind(Intent intent) {
    return this.mClient.asBinder();
  }

  public SurfaceControlViewHost.SurfacePackage onCreateKeyguardSurface(IBinder hostInputToken) {
    return null;
  }

  public void dismiss() {
    IKeyguardCallback iKeyguardCallback = this.mCallback;
    if (iKeyguardCallback == null) {
      Log.m102w(TAG, "KeyguardCallback was unexpectedly null");
      return;
    }
    try {
      iKeyguardCallback.onDismiss();
    } catch (RemoteException e) {
      Log.m97e(TAG, "onDismiss failed", e);
    }
  }
}
