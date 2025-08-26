package android.app.admin;

import android.annotation.SystemApi;
import android.app.Service;
import android.app.admin.IKeyguardClient;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import android.view.SurfaceControlViewHost;

@SystemApi
/* loaded from: classes.dex */
public class DevicePolicyKeyguardService extends Service {
    private static final String TAG = "DevicePolicyKeyguardService";
    private IKeyguardCallback mCallback;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final IKeyguardClient mClient = new AnonymousClass1();

    public SurfaceControlViewHost.SurfacePackage onCreateKeyguardSurface(IBinder iBinder) {
        return null;
    }

    /* renamed from: android.app.admin.DevicePolicyKeyguardService$1, reason: invalid class name */
    class AnonymousClass1 extends IKeyguardClient.Stub {
        AnonymousClass1() {
        }

        @Override // android.app.admin.IKeyguardClient
        public void onCreateKeyguardSurface(final IBinder iBinder, IKeyguardCallback iKeyguardCallback) {
            DevicePolicyKeyguardService.this.mCallback = iKeyguardCallback;
            DevicePolicyKeyguardService.this.mHandler.post(new Runnable() { // from class: android.app.admin.DevicePolicyKeyguardService$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.lambda$onCreateKeyguardSurface$0(iBinder);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onCreateKeyguardSurface$0(IBinder iBinder) {
            try {
                DevicePolicyKeyguardService.this.mCallback.onRemoteContentReady(DevicePolicyKeyguardService.this.onCreateKeyguardSurface(iBinder));
            } catch (RemoteException e) {
                Log.e(DevicePolicyKeyguardService.TAG, "Failed to return created SurfacePackage", e);
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

    public void dismiss() {
        IKeyguardCallback iKeyguardCallback = this.mCallback;
        if (iKeyguardCallback == null) {
            Log.w(TAG, "KeyguardCallback was unexpectedly null");
            return;
        }
        try {
            iKeyguardCallback.onDismiss();
        } catch (RemoteException e) {
            Log.e(TAG, "onDismiss failed", e);
        }
    }
}
