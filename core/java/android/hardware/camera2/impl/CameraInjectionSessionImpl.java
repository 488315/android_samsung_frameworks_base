package android.hardware.camera2.impl;

import android.hardware.camera2.CameraInjectionSession;
import android.hardware.camera2.ICameraInjectionCallback;
import android.hardware.camera2.ICameraInjectionSession;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class CameraInjectionSessionImpl extends CameraInjectionSession implements IBinder.DeathRecipient {
    private static final String TAG = "CameraInjectionSessionImpl";
    private final Executor mExecutor;
    private ICameraInjectionSession mInjectionSession;
    private final CameraInjectionSession.InjectionStatusCallback mInjectionStatusCallback;
    private final CameraInjectionCallback mCallback = new CameraInjectionCallback();
    private final Object mInterfaceLock = new Object();

    public CameraInjectionSessionImpl(CameraInjectionSession.InjectionStatusCallback callback, Executor executor) {
        this.mInjectionStatusCallback = callback;
        this.mExecutor = executor;
    }

    @Override // android.hardware.camera2.CameraInjectionSession, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mInterfaceLock) {
            try {
                if (this.mInjectionSession != null) {
                    this.mInjectionSession.stopInjection();
                    this.mInjectionSession.asBinder().unlinkToDeath(this, 0);
                    this.mInjectionSession = null;
                }
            } catch (RemoteException e) {
            }
        }
    }

    protected void finalize() throws Throwable {
        try {
            close();
        } finally {
            super.finalize();
        }
    }

    @Override // android.os.IBinder.DeathRecipient
    public void binderDied() {
        synchronized (this.mInterfaceLock) {
            Log.w(TAG, "CameraInjectionSessionImpl died unexpectedly");
            if (this.mInjectionSession == null) {
                return;
            }
            Runnable r = new Runnable() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    CameraInjectionSessionImpl.this.mInjectionStatusCallback.onInjectionError(1);
                }
            };
            long ident = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(r);
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }
    }

    public CameraInjectionCallback getCallback() {
        return this.mCallback;
    }

    public void setRemoteInjectionSession(ICameraInjectionSession injectionSession) {
        synchronized (this.mInterfaceLock) {
            if (injectionSession == null) {
                Log.e(TAG, "The camera injection session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            this.mInjectionSession = injectionSession;
            IBinder remoteSessionBinder = injectionSession.asBinder();
            if (remoteSessionBinder == null) {
                Log.e(TAG, "The camera injection session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            long ident = Binder.clearCallingIdentity();
            try {
                remoteSessionBinder.linkToDeath(this, 0);
                this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl.2
                    @Override // java.lang.Runnable
                    public void run() {
                        CameraInjectionSessionImpl.this.mInjectionStatusCallback.onInjectionSucceeded(CameraInjectionSessionImpl.this);
                    }
                });
            } catch (RemoteException e) {
                scheduleNotifyError(0);
            } finally {
                Binder.restoreCallingIdentity(ident);
            }
        }
    }

    public void onInjectionError(int errorCode) {
        Log.v(TAG, String.format("Injection session error received, code %d", Integer.valueOf(errorCode)));
        synchronized (this.mInterfaceLock) {
            if (this.mInjectionSession == null) {
                return;
            }
            switch (errorCode) {
                case 0:
                    scheduleNotifyError(0);
                    break;
                case 1:
                    scheduleNotifyError(1);
                    break;
                case 2:
                    scheduleNotifyError(2);
                    break;
                default:
                    Log.e(TAG, "Unknown error from injection session: " + errorCode);
                    scheduleNotifyError(1);
                    break;
            }
        }
    }

    private void scheduleNotifyError(int errorCode) {
        long ident = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((CameraInjectionSessionImpl) obj).notifyError(((Integer) obj2).intValue());
                }
            }, this, Integer.valueOf(errorCode)).recycleOnUse());
        } finally {
            Binder.restoreCallingIdentity(ident);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int errorCode) {
        if (this.mInjectionSession != null) {
            this.mInjectionStatusCallback.onInjectionError(errorCode);
        }
    }

    public class CameraInjectionCallback extends ICameraInjectionCallback.Stub {
        public CameraInjectionCallback() {
        }

        @Override // android.hardware.camera2.ICameraInjectionCallback.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.hardware.camera2.ICameraInjectionCallback
        public void onInjectionError(int errorCode) {
            CameraInjectionSessionImpl.this.onInjectionError(errorCode);
        }
    }
}
