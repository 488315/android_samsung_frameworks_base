package android.telephony.mbms;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.mbms.IMbmsStreamingSessionCallback;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class InternalStreamingSessionCallback extends IMbmsStreamingSessionCallback.Stub {
    private final MbmsStreamingSessionCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalStreamingSessionCallback(MbmsStreamingSessionCallback mbmsStreamingSessionCallback, Executor executor) {
        this.mAppCallback = mbmsStreamingSessionCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onError(final int i, final String str) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalStreamingSessionCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    InternalStreamingSessionCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onStreamingServicesUpdated(final List<StreamingServiceInfo> list) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalStreamingSessionCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    InternalStreamingSessionCallback.this.mAppCallback.onStreamingServicesUpdated(list);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsStreamingSessionCallback
    public void onMiddlewareReady() throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalStreamingSessionCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    InternalStreamingSessionCallback.this.mAppCallback.onMiddlewareReady();
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
