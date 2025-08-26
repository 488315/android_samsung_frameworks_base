package android.content;

import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;

/* loaded from: classes.dex */
public class SyncContext {
    private static final long HEARTBEAT_SEND_INTERVAL_IN_MS = 1000;
    private long mLastHeartbeatSendTime = 0;
    private ISyncContext mSyncContext;

    public SyncContext(ISyncContext iSyncContext) {
        this.mSyncContext = iSyncContext;
    }

    public void setStatusText(String str) {
        updateHeartbeat();
    }

    private void updateHeartbeat() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        if (jElapsedRealtime < this.mLastHeartbeatSendTime + 1000) {
            return;
        }
        try {
            this.mLastHeartbeatSendTime = jElapsedRealtime;
            ISyncContext iSyncContext = this.mSyncContext;
            if (iSyncContext != null) {
                iSyncContext.sendHeartbeat();
            }
        } catch (RemoteException unused) {
        }
    }

    public void onFinished(SyncResult syncResult) {
        try {
            ISyncContext iSyncContext = this.mSyncContext;
            if (iSyncContext != null) {
                iSyncContext.onFinished(syncResult);
            }
        } catch (RemoteException unused) {
        }
    }

    public IBinder getSyncContextBinder() {
        ISyncContext iSyncContext = this.mSyncContext;
        if (iSyncContext == null) {
            return null;
        }
        return iSyncContext.asBinder();
    }
}
