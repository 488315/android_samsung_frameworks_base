package com.samsung.aasaservice;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.util.Slog;

/* loaded from: classes5.dex */
public class AASAServiceManagerImpl implements AASAServiceManager {
    private static final String CLASS_NAME_AASA_SERVICE = "com.samsung.aasaservice.AASAService";
    private static final int MAX_COUNT_TO_RETRY_BINDING = 5;
    private static final String PACKAGE_NAME_AASA_SERVICE = "com.samsung.aasaservice";
    private static final String TAG = "AASAServiceManager";
    private static IAASA aasaService;
    private static ServiceConnection aasaServiceConn;
    private AASAServiceManager.Callback callback;
    private final Context context;
    private static final Handler mainThreadHandler = new Handler(Looper.getMainLooper());
    private static final Object lock = new Object();
    private static int retryCnt = 0;

    public AASAServiceManagerImpl(Context context) {
        this.context = context;
    }

    @Override // com.samsung.aasaservice.AASAServiceManager
    public void initialize(AASAServiceManager.Callback callback) {
        this.callback = callback;
        mainThreadHandler.post(
                new Runnable() { // from class:
                                 // com.samsung.aasaservice.AASAServiceManagerImpl$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        AASAServiceManagerImpl.this.lambda$initialize$0();
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initialize$0() {
        initialize(this.context);
    }

    private void initialize(Context context) {
        Slog.i(TAG, "initialize");
        if (isBootCompleted()) {
            bindService();
        } else {
            bindAfterBootComplete(context);
        }
    }

    private boolean isBootCompleted() {
        boolean isBootCompleted = SystemProperties.get("sys.boot_completed").equals("1");
        Slog.i(TAG, "isBootCompleted: " + isBootCompleted);
        return isBootCompleted;
    }

    private void bindAfterBootComplete(Context context) {
        Slog.i(TAG, "bindAfterBootComplete");
        context.registerReceiver(
                new BroadcastReceiver() { // from class:
                                          // com.samsung.aasaservice.AASAServiceManagerImpl.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context2, Intent intent) {
                        Slog.i(AASAServiceManagerImpl.TAG, "receive: ACTION_LAZY_BOOT_COMPLETED");
                        AASAServiceManagerImpl.this.bindService();
                        context2.unregisterReceiver(this);
                    }
                },
                new IntentFilter(Intent.ACTION_LAZY_BOOT_COMPLETED),
                4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void bindService() {
        Slog.i(TAG, "bind to AASAService");
        synchronized (lock) {
            if (aasaService != null) {
                Slog.i(TAG, "already bound");
                mainThreadHandler.post(new AASAServiceManagerImpl$$ExternalSyntheticLambda3(this));
                return;
            }
            unbindService();
            ServiceConnection candidateServiceConn = createServiceConnection();
            if (this.context.bindServiceAsUser(
                    getAasaServiceIntent(), candidateServiceConn, 1, UserHandle.SYSTEM)) {
                Slog.i(TAG, "succeeded to request bind");
                synchronized (lock) {
                    aasaServiceConn = candidateServiceConn;
                    retryCnt = 0;
                }
            } else {
                Slog.w(TAG, "failed to request bind");
                this.context.unbindService(candidateServiceConn);
                retryBindService();
            }
            Slog.i(TAG, "[END] bind to AASAService");
        }
    }

    private Intent getAasaServiceIntent() {
        return new Intent().setClassName(PACKAGE_NAME_AASA_SERVICE, CLASS_NAME_AASA_SERVICE);
    }

    private ServiceConnection createServiceConnection() {
        return new AASAServiceConnection(
                new AASAServiceManager
                        .Callback() { // from class:
                                      // com.samsung.aasaservice.AASAServiceManagerImpl$$ExternalSyntheticLambda1
                    @Override // com.samsung.aasaservice.AASAServiceManager.Callback
                    public final void onReady() {
                        AASAServiceManagerImpl.this.lambda$createServiceConnection$1();
                    }
                },
                new IBinder
                        .DeathRecipient() { // from class:
                                            // com.samsung.aasaservice.AASAServiceManagerImpl$$ExternalSyntheticLambda2
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        AASAServiceManagerImpl.lambda$createServiceConnection$2();
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$createServiceConnection$1() {
        mainThreadHandler.post(new AASAServiceManagerImpl$$ExternalSyntheticLambda3(this));
    }

    static /* synthetic */ void lambda$createServiceConnection$2() {
        Slog.w(TAG, "binder gone away");
        synchronized (lock) {
            aasaService = null;
            aasaServiceConn = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyReady() {
        Slog.i(TAG, "notify service ready");
        if (this.callback == null) {
            Slog.w(TAG, "callback is null");
        } else {
            this.callback.onReady();
            this.callback = null;
        }
    }

    private void retryBindService() {
        retryCnt++;
        Slog.i(TAG, "retry to bind to AASAService / " + retryCnt);
        if (retryCnt <= 5) {
            mainThreadHandler.postDelayed(
                    new Runnable() { // from class:
                                     // com.samsung.aasaservice.AASAServiceManagerImpl$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            AASAServiceManagerImpl.this.bindService();
                        }
                    },
                    500L);
        }
    }

    @Override // com.samsung.aasaservice.AASAServiceManager
    public void deinitialize() {
        Slog.i(TAG, "deinitialize");
        unbindService();
    }

    private void unbindService() {
        synchronized (lock) {
            if (aasaServiceConn != null) {
                Slog.i(TAG, "unbind");
                this.context.unbindService(aasaServiceConn);
                aasaServiceConn = null;
            }
        }
    }

    @Override // com.samsung.aasaservice.AASAServiceManager
    public void notifyPolicyUpdateCompletion() {
        Slog.i(TAG, "notifyPolicyUpdateCompletion");
        synchronized (lock) {
            if (aasaService == null) {
                Slog.e(TAG, "not connected yet");
                return;
            }
            try {
                aasaService.onReceivePolicyUpdateCompletion();
            } catch (RemoteException e) {
                Slog.e(TAG, "Error calling IAASA#onReceivePolicyUpdateCompletion", e);
            }
        }
    }

    private static class AASAServiceConnection implements ServiceConnection {
        private final IBinder.DeathRecipient deathRecipient;
        private AASAServiceManager.Callback readyCallback;

        public AASAServiceConnection(
                AASAServiceManager.Callback readyCallback, IBinder.DeathRecipient deathRecipient) {
            this.readyCallback = readyCallback;
            this.deathRecipient = deathRecipient;
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName name, IBinder service) {
            Slog.i(AASAServiceManagerImpl.TAG, "onServiceConnected");
            addDeathHandler(service);
            synchronized (AASAServiceManagerImpl.lock) {
                AASAServiceManagerImpl.aasaService = IAASA.Stub.asInterface(service);
            }
            if (this.readyCallback != null) {
                this.readyCallback.onReady();
                this.readyCallback = null;
            }
        }

        private void addDeathHandler(IBinder service) {
            try {
                service.linkToDeath(this.deathRecipient, 0);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName name) {
            synchronized (AASAServiceManagerImpl.lock) {
                Slog.i(AASAServiceManagerImpl.TAG, "onServiceDisconnected");
                AASAServiceManagerImpl.aasaService = null;
                AASAServiceManagerImpl.aasaServiceConn = null;
            }
        }
    }
}
