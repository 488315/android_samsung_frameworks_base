package android.telephony.mbms;

import android.os.Binder;
import android.telephony.mbms.IMbmsGroupCallSessionCallback;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class InternalGroupCallSessionCallback extends IMbmsGroupCallSessionCallback.Stub {
    private final MbmsGroupCallSessionCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalGroupCallSessionCallback(MbmsGroupCallSessionCallback mbmsGroupCallSessionCallback, Executor executor) {
        this.mAppCallback = mbmsGroupCallSessionCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
    public void onError(final int i, final String str) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalGroupCallSessionCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    InternalGroupCallSessionCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
    public void onAvailableSaisUpdated(final List list, final List list2) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalGroupCallSessionCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    InternalGroupCallSessionCallback.this.mAppCallback.onAvailableSaisUpdated(list, list2);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
    public void onServiceInterfaceAvailable(final String str, final int i) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalGroupCallSessionCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    InternalGroupCallSessionCallback.this.mAppCallback.onServiceInterfaceAvailable(str, i);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsGroupCallSessionCallback
    public void onMiddlewareReady() {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalGroupCallSessionCallback.4
                @Override // java.lang.Runnable
                public void run() {
                    InternalGroupCallSessionCallback.this.mAppCallback.onMiddlewareReady();
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
