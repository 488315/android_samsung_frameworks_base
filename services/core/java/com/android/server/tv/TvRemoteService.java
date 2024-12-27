package com.android.server.tv;

import android.content.Context;
import android.content.IntentFilter;
import android.os.UserHandle;
import android.util.Slog;
import com.android.server.SystemService;
import com.android.server.VcnManagementService$$ExternalSyntheticOutline0;
import com.android.server.Watchdog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TvRemoteService extends SystemService implements Watchdog.Monitor {
    public final Object mLock;
    public final TvRemoteProviderWatcher mWatcher;

    public TvRemoteService(Context context) {
        super(context);
        Object obj = new Object();
        this.mLock = obj;
        this.mWatcher = new TvRemoteProviderWatcher(context, obj);
        Watchdog.getInstance().addMonitor(this);
    }

    @Override // com.android.server.Watchdog.Monitor
    public final void monitor() {
        synchronized (this.mLock) {
        }
    }

    @Override // com.android.server.SystemService
    public final void onBootPhase(int i) {
        if (i == 600) {
            boolean z = TvRemoteProviderWatcher.DEBUG;
            TvRemoteProviderWatcher tvRemoteProviderWatcher = this.mWatcher;
            if (z) {
                tvRemoteProviderWatcher.getClass();
                Slog.d("TvRemoteProviderWatcher", "start()");
            }
            if (tvRemoteProviderWatcher.mRunning) {
                return;
            }
            tvRemoteProviderWatcher.mRunning = true;
            IntentFilter m = VcnManagementService$$ExternalSyntheticOutline0.m("android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_REMOVED", "android.intent.action.PACKAGE_CHANGED", "android.intent.action.PACKAGE_REPLACED", "android.intent.action.PACKAGE_RESTARTED");
            m.addDataScheme("package");
            tvRemoteProviderWatcher.mContext.registerReceiverAsUser(tvRemoteProviderWatcher.mScanPackagesReceiver, new UserHandle(tvRemoteProviderWatcher.mUserId), m, null, tvRemoteProviderWatcher.mHandler);
            tvRemoteProviderWatcher.mHandler.post(tvRemoteProviderWatcher.mScanPackagesRunnable);
        }
    }

    @Override // com.android.server.SystemService
    public final void onStart() {
    }
}
