package android.telephony.mbms;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.mbms.IDownloadProgressListener;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class InternalDownloadProgressListener extends IDownloadProgressListener.Stub {
    private final DownloadProgressListener mAppListener;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalDownloadProgressListener(DownloadProgressListener downloadProgressListener, Executor executor) {
        this.mAppListener = downloadProgressListener;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IDownloadProgressListener
    public void onProgressUpdated(final DownloadRequest downloadRequest, final FileInfo fileInfo, final int i, final int i2, final int i3, final int i4) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalDownloadProgressListener.1
                @Override // java.lang.Runnable
                public void run() {
                    InternalDownloadProgressListener.this.mAppListener.onProgressUpdated(downloadRequest, fileInfo, i, i2, i3, i4);
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
