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

    public CameraInjectionSessionImpl(CameraInjectionSession.InjectionStatusCallback injectionStatusCallback, Executor executor) {
        this.mInjectionStatusCallback = injectionStatusCallback;
        this.mExecutor = executor;
    }

    @Override // android.hardware.camera2.CameraInjectionSession, java.lang.AutoCloseable
    public void close() {
        synchronized (this.mInterfaceLock) {
            try {
                ICameraInjectionSession iCameraInjectionSession = this.mInjectionSession;
                if (iCameraInjectionSession != null) {
                    iCameraInjectionSession.stopInjection();
                    this.mInjectionSession.asBinder().unlinkToDeath(this, 0);
                    this.mInjectionSession = null;
                }
            } catch (RemoteException unused) {
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
            Runnable runnable = new Runnable() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl.1
                @Override // java.lang.Runnable
                public void run() {
                    CameraInjectionSessionImpl.this.mInjectionStatusCallback.onInjectionError(1);
                }
            };
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                this.mExecutor.execute(runnable);
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public CameraInjectionCallback getCallback() {
        return this.mCallback;
    }

    public void setRemoteInjectionSession(ICameraInjectionSession iCameraInjectionSession) {
        synchronized (this.mInterfaceLock) {
            if (iCameraInjectionSession == null) {
                Log.e(TAG, "The camera injection session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            this.mInjectionSession = iCameraInjectionSession;
            IBinder asBinder = iCameraInjectionSession.asBinder();
            if (asBinder == null) {
                Log.e(TAG, "The camera injection session has encountered a serious error");
                scheduleNotifyError(0);
                return;
            }
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                try {
                    asBinder.linkToDeath(this, 0);
                    this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl.2
                        @Override // java.lang.Runnable
                        public void run() {
                            CameraInjectionSessionImpl.this.mInjectionStatusCallback.onInjectionSucceeded(CameraInjectionSessionImpl.this);
                        }
                    });
                } catch (RemoteException unused) {
                    scheduleNotifyError(0);
                }
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        }
    }

    public void onInjectionError(int i) {
        Log.v(TAG, String.format("Injection session error received, code %d", Integer.valueOf(i)));
        synchronized (this.mInterfaceLock) {
            if (this.mInjectionSession == null) {
                return;
            }
            if (i == 0) {
                scheduleNotifyError(0);
            } else if (i == 1) {
                scheduleNotifyError(1);
            } else if (i == 2) {
                scheduleNotifyError(2);
            } else {
                Log.e(TAG, "Unknown error from injection session: " + i);
                scheduleNotifyError(1);
            }
        }
    }

    private void scheduleNotifyError(int i) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(PooledLambda.obtainRunnable(new BiConsumer() { // from class: android.hardware.camera2.impl.CameraInjectionSessionImpl$$ExternalSyntheticLambda0
                @Override // java.util.function.BiConsumer
                public final void accept(Object obj, Object obj2) {
                    ((CameraInjectionSessionImpl) obj).notifyError(((Integer) obj2).intValue());
                }
            }, this, Integer.valueOf(i)).recycleOnUse());
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyError(int i) {
        if (this.mInjectionSession != null) {
            this.mInjectionStatusCallback.onInjectionError(i);
        }
    }

    public class CameraInjectionCallback extends ICameraInjectionCallback.Stub {
        @Override // android.hardware.camera2.ICameraInjectionCallback.Stub, android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public CameraInjectionCallback() {
        }

        @Override // android.hardware.camera2.ICameraInjectionCallback
        public void onInjectionError(int i) {
            CameraInjectionSessionImpl.this.onInjectionError(i);
        }
    }
}
