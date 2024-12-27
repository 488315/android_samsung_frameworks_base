package com.android.server;

import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.IUpdateLock;
import android.os.TokenWatcher;
import android.os.UserHandle;

import com.android.internal.util.DumpUtils;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;

import java.io.FileDescriptor;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class UpdateLockService extends IUpdateLock.Stub {
    public final Context mContext;
    public final LockWatcher mLocks = new LockWatcher(new Handler());

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LockWatcher extends TokenWatcher {
        public LockWatcher(Handler handler) {
            super(handler, "UpdateLocks");
        }

        @Override // android.os.TokenWatcher
        public final void acquired() {
            UpdateLockService.this.sendLockChangedBroadcast(false);
        }

        @Override // android.os.TokenWatcher
        public final void released() {
            UpdateLockService.this.sendLockChangedBroadcast(true);
        }
    }

    public UpdateLockService(Context context) {
        this.mContext = context;
        sendLockChangedBroadcast(true);
    }

    public final void acquireUpdateLock(IBinder iBinder, String str) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.UPDATE_LOCK", "acquireUpdateLock");
        LockWatcher lockWatcher = this.mLocks;
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("{tag=", str, " uid=");
        m.append(Binder.getCallingUid());
        m.append(" pid=");
        m.append(Binder.getCallingPid());
        m.append('}');
        lockWatcher.acquire(iBinder, m.toString());
    }

    public final void dump(
            FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (DumpUtils.checkDumpPermission(this.mContext, "UpdateLockService", printWriter)) {
            this.mLocks.dump(printWriter);
        }
    }

    public final void releaseUpdateLock(IBinder iBinder) {
        this.mContext.enforceCallingOrSelfPermission(
                "android.permission.UPDATE_LOCK", "releaseUpdateLock");
        this.mLocks.release(iBinder);
    }

    public final void sendLockChangedBroadcast(boolean z) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mContext.sendStickyBroadcastAsUser(
                    new Intent("android.os.UpdateLock.UPDATE_LOCK_CHANGED")
                            .putExtra("nowisconvenient", z)
                            .putExtra("timestamp", System.currentTimeMillis())
                            .addFlags(67108864),
                    UserHandle.ALL);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }
}
