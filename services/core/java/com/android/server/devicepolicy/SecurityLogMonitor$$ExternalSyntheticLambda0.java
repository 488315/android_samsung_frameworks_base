package com.android.server.devicepolicy;

import android.app.admin.IAuditLogEventsCallback;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Slog;

import com.android.server.utils.Slogf;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class SecurityLogMonitor$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ SecurityLogMonitor f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ IAuditLogEventsCallback f$2;
    public final /* synthetic */ List f$3;

    public /* synthetic */ SecurityLogMonitor$$ExternalSyntheticLambda0(
            SecurityLogMonitor securityLogMonitor,
            int i,
            IAuditLogEventsCallback iAuditLogEventsCallback,
            List list) {
        this.f$0 = securityLogMonitor;
        this.f$1 = i;
        this.f$2 = iAuditLogEventsCallback;
        this.f$3 = list;
    }

    @Override // java.lang.Runnable
    public final void run() {
        SecurityLogMonitor securityLogMonitor = this.f$0;
        int i = this.f$1;
        IAuditLogEventsCallback iAuditLogEventsCallback = this.f$2;
        List list = this.f$3;
        securityLogMonitor.getClass();
        try {
            list.size();
            iAuditLogEventsCallback.onNewAuditLogEvents(list);
        } catch (RemoteException e) {
            Slogf.e(
                    "SecurityLogMonitor",
                    e,
                    "Failed to invoke audit log callback for UID %d",
                    Integer.valueOf(i));
            IBinder asBinder = iAuditLogEventsCallback.asBinder();
            if (asBinder.isBinderAlive()) {
                Slog.i("SecurityLogMonitor", "Callback binder is still alive, not removing.");
                return;
            }
            ((ReentrantLock) securityLogMonitor.mLock).lock();
            try {
                int indexOfKey = securityLogMonitor.mAuditLogCallbacks.indexOfKey(i);
                if (indexOfKey < 0) {
                    Slogf.i(
                            "SecurityLogMonitor",
                            "Callback not registered for UID %d, nothing to remove",
                            Integer.valueOf(i));
                } else if (((IAuditLogEventsCallback)
                                securityLogMonitor.mAuditLogCallbacks.valueAt(indexOfKey))
                        .asBinder()
                        .equals(asBinder)) {
                    Slogf.i(
                            "SecurityLogMonitor",
                            "Removing callback for UID %d",
                            Integer.valueOf(i));
                    securityLogMonitor.mAuditLogCallbacks.removeAt(indexOfKey);
                } else {
                    Slogf.i(
                            "SecurityLogMonitor",
                            "Callback is already replaced for UID %d, not removing",
                            Integer.valueOf(i));
                }
                ((ReentrantLock) securityLogMonitor.mLock).unlock();
            } catch (Throwable th) {
                ((ReentrantLock) securityLogMonitor.mLock).unlock();
                throw th;
            }
        }
    }
}
