package android.service.remotelockscreenvalidation;

import android.Manifest;
import android.app.PendingIntent$$ExternalSyntheticLambda1;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.p002pm.PackageManager;
import android.content.p002pm.ServiceInfo;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Looper;
import android.p009os.RemoteException;
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
    private static final String TAG = RemoteLockscreenValidationClientImpl.class.getSimpleName();
    private final Context mContext;
    private final Handler mHandler;
    private boolean mIsConnected;
    private final boolean mIsServiceAvailable;
    private final Executor mLifecycleExecutor;
    private final Queue<Call> mRequestQueue;
    private IRemoteLockscreenValidationService mService;
    private ServiceInfo mServiceInfo;

    RemoteLockscreenValidationClientImpl(Context context, Executor bgExecutor, ComponentName serviceComponent) {
        Context applicationContext = context.getApplicationContext();
        this.mContext = applicationContext;
        this.mIsServiceAvailable = isServiceAvailable(applicationContext, serviceComponent);
        this.mHandler = new Handler(Looper.getMainLooper());
        this.mLifecycleExecutor = bgExecutor == null ? new PendingIntent$$ExternalSyntheticLambda1() : bgExecutor;
        this.mRequestQueue = new ArrayDeque();
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public boolean isServiceAvailable() {
        return this.mIsServiceAvailable;
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public void validateLockscreenGuess(final byte[] guess, final IRemoteLockscreenValidationCallback callback) {
        try {
            if (!isServiceAvailable()) {
                callback.onFailure("Service is not available");
                return;
            }
        } catch (RemoteException e) {
            Log.m97e(TAG, "Error while failing for service unavailable", e);
        }
        executeApiCall(new Call() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super();
            }

            @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call
            public void exec(IRemoteLockscreenValidationService service) throws RemoteException {
                service.validateLockscreenGuess(guess, callback);
            }

            @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl.Call
            void onError(String msg) {
                try {
                    callback.onFailure(msg);
                } catch (RemoteException e2) {
                    Log.m97e(RemoteLockscreenValidationClientImpl.TAG, "Error while failing validateLockscreenGuess", e2);
                }
            }
        });
    }

    @Override // android.service.remotelockscreenvalidation.RemoteLockscreenValidationClient
    public void disconnect() {
        this.mHandler.post(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.disconnectInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void disconnectInternal() {
        if (!this.mIsConnected) {
            Log.m102w(TAG, "already disconnected");
            return;
        }
        this.mIsConnected = false;
        this.mLifecycleExecutor.execute(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda1
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
        this.mHandler.post(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.connectInternal();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void connectInternal() {
        if (this.mServiceInfo == null) {
            Log.m102w(TAG, "RemoteLockscreenValidation service unavailable");
            return;
        }
        if (this.mIsConnected) {
            return;
        }
        this.mIsConnected = true;
        final Intent intent = new Intent(RemoteLockscreenValidationService.SERVICE_INTERFACE);
        intent.setComponent(this.mServiceInfo.getComponentName());
        final int flags = 33;
        this.mLifecycleExecutor.execute(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.lambda$connectInternal$1(intent, flags);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$connectInternal$1(Intent intent, int flags) {
        this.mContext.bindService(intent, this, flags);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: onConnectedInternal, reason: merged with bridge method [inline-methods] */
    public void lambda$onServiceConnected$3(IRemoteLockscreenValidationService service) {
        if (!this.mIsConnected) {
            Log.m102w(TAG, "onConnectInternal but connection closed");
            this.mService = null;
            return;
        }
        this.mService = service;
        Iterator it = new ArrayList(this.mRequestQueue).iterator();
        while (it.hasNext()) {
            Call call = (Call) it.next();
            performApiCallInternal(call, this.mService);
            this.mRequestQueue.remove(call);
        }
    }

    private boolean isServiceAvailable(Context context, ComponentName serviceComponent) {
        ServiceInfo serviceInfo = getServiceInfo(context, serviceComponent);
        this.mServiceInfo = serviceInfo;
        if (serviceInfo == null) {
            return false;
        }
        if (!Manifest.permission.BIND_REMOTE_LOCKSCREEN_VALIDATION_SERVICE.equals(serviceInfo.permission)) {
            Log.m102w(TAG, TextUtils.formatSimple("%s/%s does not require permission %s", this.mServiceInfo.packageName, this.mServiceInfo.name, Manifest.permission.BIND_REMOTE_LOCKSCREEN_VALIDATION_SERVICE));
            return false;
        }
        return true;
    }

    private ServiceInfo getServiceInfo(Context context, ComponentName serviceComponent) {
        try {
            return context.getPackageManager().getServiceInfo(serviceComponent, PackageManager.ComponentInfoFlags.m10of(128L));
        } catch (PackageManager.NameNotFoundException e) {
            Log.m102w(TAG, TextUtils.formatSimple("Cannot resolve service %s", serviceComponent.getClassName()));
            return null;
        }
    }

    private void executeApiCall(final Call call) {
        this.mHandler.post(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda4
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

    private void performApiCallInternal(Call apiCaller, IRemoteLockscreenValidationService service) {
        if (service == null) {
            apiCaller.onError("Service is null");
            return;
        }
        try {
            apiCaller.exec(service);
        } catch (RemoteException e) {
            Log.m103w(TAG, "executeInternal error", e);
            apiCaller.onError(e.getMessage());
            disconnect();
        }
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName name, IBinder binder) {
        final IRemoteLockscreenValidationService service = IRemoteLockscreenValidationService.Stub.asInterface(binder);
        this.mHandler.post(new Runnable() { // from class: android.service.remotelockscreenvalidation.RemoteLockscreenValidationClientImpl$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                RemoteLockscreenValidationClientImpl.this.lambda$onServiceConnected$3(service);
            }
        });
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName name) {
    }

    @Override // android.content.ServiceConnection
    public void onBindingDied(ComponentName name) {
        disconnect();
    }

    @Override // android.content.ServiceConnection
    public void onNullBinding(ComponentName name) {
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
