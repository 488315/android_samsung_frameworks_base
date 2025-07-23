package android.telephony.mbms;

import android.os.Binder;
import android.telephony.mbms.IMbmsDownloadSessionCallback;
import java.util.List;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class InternalDownloadSessionCallback extends IMbmsDownloadSessionCallback.Stub {
    private final MbmsDownloadSessionCallback mAppCallback;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalDownloadSessionCallback(MbmsDownloadSessionCallback mbmsDownloadSessionCallback, Executor executor) {
        this.mAppCallback = mbmsDownloadSessionCallback;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
    public void onError(final int i, final String str) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalDownloadSessionCallback.1
                @Override // java.lang.Runnable
                public void run() {
                    InternalDownloadSessionCallback.this.mAppCallback.onError(i, str);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
    public void onFileServicesUpdated(final List<FileServiceInfo> list) {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalDownloadSessionCallback.2
                @Override // java.lang.Runnable
                public void run() {
                    InternalDownloadSessionCallback.this.mAppCallback.onFileServicesUpdated(list);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    @Override // android.telephony.mbms.IMbmsDownloadSessionCallback
    public void onMiddlewareReady() {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalDownloadSessionCallback.3
                @Override // java.lang.Runnable
                public void run() {
                    InternalDownloadSessionCallback.this.mAppCallback.onMiddlewareReady();
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
