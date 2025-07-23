package android.service.remotelockscreenvalidation;

import android.Manifest;
import android.app.PendingIntent$$ExternalSyntheticLambda0;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ServiceInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.service.remotelockscreenvalidation.IRemoteLockscreenValidationService;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.Executor;

/* loaded from: classes3.dex */
public class RemoteLockscreenValidationClientImpl implements RemoteLockscreenValidationClient, ServiceConnection {
    private static final String TAG = "RemoteLockscreenValidationClientImpl";
    private final Context mContext;
    private final Handler mHandler;
    private boolean mIsConnected;
    private final boolean mIsServiceAvailable;
    private final Executor mLifecycleExecutor;
    private final Queue<Call> mRequestQueue;
    private IRemoteLockscreenValidationService mService;
    private ServiceInfo mServiceInfo;

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
    }

    RemoteLockscreenValidationClientImpl(Context context, Executor executor, ComponentName componentName) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mIsServiceAvailable = isServiceAvailable(applicationContext, componentName);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mLifecycleExecutor = executor == null ? new PendingIntent$$ExternalSyntheticLambda0() : executor;
        this.mRequestQueue = new ArrayDeque();
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public boolean isServiceAvailable() {
        return this.mIsServiceAvailable;
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public void validateLockscreenGuess(final byte[] bArr, final IRemoteLockscreenValidationCallback iRemoteLockscreenValidationCallback) {
        try {
            if (!isServiceAvailable()) {
                iRemoteLockscreenValidationCallback.onFailure("Service is not available");
                return;
            }
        } catch (RemoteException e) {
            Log.e(TAG, "Error while failing for service unavailable", e);
        }
        executeApiCall(new Call(this) { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call
            public void exec(IRemoteLockscreenValidationService iRemoteLockscreenValidationService) throws RemoteException {
                iRemoteLockscreenValidationService.validateLockscreenGuess(bArr, iRemoteLockscreenValidationCallback);
            }

            @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call
            void onError(String str) {
                try {
                    iRemoteLockscreenValidationCallback.onFailure(str);
                } catch (RemoteException e2) {
                    Log.e(RemoteLockscreenValidationClientImpl.TAG, "Error while failing validateLockscreenGuess", e2);
                }
            }
        });
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public void disconnect() {
        this.mHandler.post(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.disconnectInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectInternal() {
        if (!this.mIsConnected) {
            Log.w(TAG, "already disconnected");
            return;
        }
        this.mIsConnected = false;
        this.mLifecycleExecutor.execute(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.lambda$disconnectInternal$0();
            }
        });
        this.mService = null;
        this.mRequestQueue.clear();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$disconnectInternal$0() {
        this.mContext.unbindService(this);
    }

    private void connect() {
        this.mHandler.post(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.connectInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectInternal() {
        if (this.mServiceInfo == null) {
            Log.w(TAG, "RemoteLockscreenValidation service unavailable");
            return;
        }
        if (this.mIsConnected) {
            return;
        }
        this.mIsConnected = true;
        final Intent intent = new Intent(RemoteLockscreenValidationService.SERVICE_INTERFACE);
        intent.setComponent(this.mServiceInfo.getComponentName());
        final int i = 33;
        this.mLifecycleExecutor.execute(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.lambda$connectInternal$1(intent, i);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectInternal$1(Intent intent, int i) {
        this.mContext.bindService(intent, this, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onConnectedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onServiceConnected$3(IRemoteLockscreenValidationService iRemoteLockscreenValidationService) {
        if (!this.mIsConnected) {
            Log.w(TAG, "onConnectInternal but connection closed");
            this.mService = null;
            return;
        }
        this.mService = iRemoteLockscreenValidationService;
        Iterator it = new ArrayList(this.mRequestQueue).iterator();
        while (it.hasNext()) {
            Call call = (Call) it.next();
            performApiCallInternal(call, this.mService);
            this.mRequestQueue.remove(call);
        }
    }

    private boolean isServiceAvailable(Context context, ComponentName componentName) {
        ServiceInfo serviceInfo = getServiceInfo(context, componentName);
        this.mServiceInfo = serviceInfo;
        if (serviceInfo == null) {
            return false;
        }
        if (Manifest.permission.BIND_REMOTE_LOCKSCREEN_VALIDATION_SERVICE.equals(serviceInfo.permission)) {
            return true;
        }
        Log.w(TAG, TextUtils.formatSimple("%s/%s does not require permission %s", this.mServiceInfo.packageName, this.mServiceInfo.name, Manifest.permission.BIND_REMOTE_LOCKSCREEN_VALIDATION_SERVICE));
        return false;
    }

    private ServiceInfo getServiceInfo(Context context, ComponentName componentName) {
        try {
            return context.getPackageManager().getServiceInfo(componentName, PackageManager.ComponentInfoFlags.of(128L));
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w(TAG, TextUtils.formatSimple("Cannot resolve service %s", componentName.getClassName()));
            return null;
        }
    }

    private void executeApiCall(final Call call) {
        this.mHandler.post(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.lambda$executeApiCall$2(call);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: executeInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$executeApiCall$2(Call call) {
        IRemoteLockscreenValidationService iRemoteLockscreenValidationService;
        if (this.mIsConnected && (iRemoteLockscreenValidationService = this.mService) != null) {
            performApiCallInternal(call, iRemoteLockscreenValidationService);
        } else {
            this.mRequestQueue.add(call);
            connect();
        }
    }

    private void performApiCallInternal(Call call, IRemoteLockscreenValidationService iRemoteLockscreenValidationService) {
        if (iRemoteLockscreenValidationService == null) {
            call.onError("Service is null");
            return;
        }
        try {
            call.exec(iRemoteLockscreenValidationService);
        } catch (RemoteException e) {
            Log.w(TAG, "executeInternal error", e);
            call.onError(e.getMessage());
            disconnect();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        final IRemoteLockscreenValidationService asInterface = IRemoteLockscreenValidationService.Stub.asInterface(iBinder);
        this.mHandler.post(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.lambda$onServiceConnected$3(asInterface);
            }
        });
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName componentName) {
        disconnect();
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName componentName) {
        disconnect();
    }

    /* JADX INFO: Access modifiers changed from: private */
    static abstract class Call {
        abstract void exec(IRemoteLockscreenValidationService iRemoteLockscreenValidationService) throws RemoteException;

        abstract void onError(String str);

        private Call() {
        }
    }
}
