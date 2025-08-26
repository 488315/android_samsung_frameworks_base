package android.telephony.mbms;

import android.os.Binder;
import android.os.RemoteException;
import android.telephony.mbms.IDownloadStatusListener;
import java.util.concurrent.Executor;

/* loaded from: classes4.dex */
public class InternalDownloadStatusListener extends IDownloadStatusListener.Stub {
    private final DownloadStatusListener mAppListener;
    private final Executor mExecutor;
    private volatile boolean mIsStopped = false;

    public InternalDownloadStatusListener(DownloadStatusListener downloadStatusListener, Executor executor) {
        this.mAppListener = downloadStatusListener;
        this.mExecutor = executor;
    }

    @Override // android.telephony.mbms.IDownloadStatusListener
    public void onStatusUpdated(final DownloadRequest downloadRequest, final FileInfo fileInfo, final int i) throws RemoteException {
        if (this.mIsStopped) {
            return;
        }
        long jClearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mExecutor.execute(new Runnable() { // from class: android.telephony.mbms.InternalDownloadStatusListener.1
                @Override // java.lang.Runnable
                public void run() {
                    InternalDownloadStatusListener.this.mAppListener.onStatusUpdated(downloadRequest, fileInfo, i);
                }
            });
        } finally {
            Binder.restoreCallingIdentity(jClearCallingIdentity);
        }
    }

    public void stop() {
        this.mIsStopped = true;
    }
}
