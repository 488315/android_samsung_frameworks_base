package android.telephony.mbms;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.mbms.IStreamingServiceCallback;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class InternalStreamingServiceCallback extends IStreamingServiceCallback.Stub {
    private final StreamingServiceCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalStreamingServiceCallback(StreamingServiceCallback streamingServiceCallback, Executor executor) {
        this.mAppCallback = streamingServiceCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onError(final int i, final String str) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onStreamStateUpdated(final int i, final int i2) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onStreamStateUpdated(i, i2);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onMediaDescriptionUpdated() throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onMediaDescriptionUpdated();
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onBroadcastSignalStrengthUpdated(final int i) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.4
                @Override // java.lang.Runnable
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onBroadcastSignalStrengthUpdated(i);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IStreamingServiceCallback
    public void onStreamMethodUpdated(final int i) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalStreamingServiceCallback.5
                @Override // java.lang.Runnable
                public void run() {
                    InternalStreamingServiceCallback.this.mAppCallback.onStreamMethodUpdated(i);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public void stop() {
        this.mIsStopped = true;
    }
}
