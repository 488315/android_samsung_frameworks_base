package com.android.server.am;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.Log;
import android.util.TimeUtils;

import java.io.PrintWriter;

public abstract class PersistentConnection {
    public final PersistentConnection$$ExternalSyntheticLambda0 mBindForBackoffRunnable;
    public boolean mBound;
    public final ComponentName mComponentName;
    public final Context mContext;
    public final Handler mHandler;
    public boolean mIsConnected;
    public long mLastConnectedTime;
    public long mNextBackoffMs;
    public int mNumBindingDied;
    public int mNumConnected;
    public int mNumDisconnected;
    public final double mRebindBackoffIncrease;
    public final long mRebindBackoffMs;
    public final long mRebindMaxBackoffMs;
    public boolean mRebindScheduled;
    public long mReconnectTime;
    public final long mResetBackoffDelay;
    public Object mService;
    public boolean mShouldBeBound;
    public final PersistentConnection$$ExternalSyntheticLambda0 mStableCheck;
    public final String mTag;
    public final int mUserId;
    public final Object mLock = new Object();
    public final AnonymousClass1 mServiceConnection =
            new ServiceConnection() { // from class: com.android.server.am.PersistentConnection.1
                @Override // android.content.ServiceConnection
                public final void onBindingDied(ComponentName componentName) {
                    synchronized (PersistentConnection.this.mLock) {
                        try {
                            PersistentConnection persistentConnection = PersistentConnection.this;
                            if (!persistentConnection.mBound) {
                                Log.w(
                                        persistentConnection.mTag,
                                        "Binding died: "
                                                + PersistentConnection.this.mComponentName
                                                        .flattenToShortString()
                                                + " u"
                                                + PersistentConnection.this.mUserId
                                                + " but not bound, ignore.");
                                return;
                            }
                            Log.w(
                                    persistentConnection.mTag,
                                    "Binding died: "
                                            + PersistentConnection.this.mComponentName
                                                    .flattenToShortString()
                                            + " u"
                                            + PersistentConnection.this.mUserId);
                            PersistentConnection persistentConnection2 = PersistentConnection.this;
                            persistentConnection2.mNumBindingDied =
                                    persistentConnection2.mNumBindingDied + 1;
                            persistentConnection2.scheduleRebindLocked();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    synchronized (PersistentConnection.this.mLock) {
                        try {
                            PersistentConnection persistentConnection = PersistentConnection.this;
                            if (!persistentConnection.mBound) {
                                Log.w(
                                        persistentConnection.mTag,
                                        "Connected: "
                                                + PersistentConnection.this.mComponentName
                                                        .flattenToShortString()
                                                + " u"
                                                + PersistentConnection.this.mUserId
                                                + " but not bound, ignore.");
                                return;
                            }
                            Log.i(
                                    persistentConnection.mTag,
                                    "Connected: "
                                            + PersistentConnection.this.mComponentName
                                                    .flattenToShortString()
                                            + " u"
                                            + PersistentConnection.this.mUserId);
                            PersistentConnection persistentConnection2 = PersistentConnection.this;
                            persistentConnection2.mNumConnected =
                                    persistentConnection2.mNumConnected + 1;
                            persistentConnection2.mIsConnected = true;
                            persistentConnection2.mLastConnectedTime =
                                    persistentConnection2.injectUptimeMillis();
                            PersistentConnection persistentConnection3 = PersistentConnection.this;
                            persistentConnection3.mService =
                                    persistentConnection3.asInterface(iBinder);
                            PersistentConnection persistentConnection4 = PersistentConnection.this;
                            persistentConnection4.injectRemoveCallbacks(
                                    persistentConnection4.mStableCheck);
                            persistentConnection4.injectPostAtTime(
                                    persistentConnection4.mStableCheck,
                                    persistentConnection4.injectUptimeMillis()
                                            + persistentConnection4.mResetBackoffDelay);
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    synchronized (PersistentConnection.this.mLock) {
                        Log.i(
                                PersistentConnection.this.mTag,
                                "Disconnected: "
                                        + PersistentConnection.this.mComponentName
                                                .flattenToShortString()
                                        + " u"
                                        + PersistentConnection.this.mUserId);
                        PersistentConnection persistentConnection = PersistentConnection.this;
                        persistentConnection.mNumDisconnected =
                                persistentConnection.mNumDisconnected + 1;
                        persistentConnection.mIsConnected = false;
                        persistentConnection.injectRemoveCallbacks(
                                persistentConnection.mStableCheck);
                    }
                }
            };

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.am.PersistentConnection$1] */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.android.server.am.PersistentConnection$$ExternalSyntheticLambda0] */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.am.PersistentConnection$$ExternalSyntheticLambda0] */
    public PersistentConnection(
            String str,
            Context context,
            Handler handler,
            int i,
            ComponentName componentName,
            long j,
            double d,
            long j2,
            long j3) {
        final int i2 = 0;
        this.mBindForBackoffRunnable =
                new Runnable(
                        this) { // from class:
                                // com.android.server.am.PersistentConnection$$ExternalSyntheticLambda0
                    public final /* synthetic */ PersistentConnection f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i3 = i2;
                        PersistentConnection persistentConnection = this.f$0;
                        switch (i3) {
                            case 0:
                                synchronized (persistentConnection.mLock) {
                                    try {
                                        if (persistentConnection.mShouldBeBound) {
                                            persistentConnection.bindInnerLocked(false);
                                            return;
                                        }
                                        return;
                                    } finally {
                                    }
                                }
                            default:
                                synchronized (persistentConnection.mLock) {
                                    try {
                                        long injectUptimeMillis =
                                                (persistentConnection.mLastConnectedTime
                                                                + persistentConnection
                                                                        .mResetBackoffDelay)
                                                        - persistentConnection.injectUptimeMillis();
                                        if (persistentConnection.mBound
                                                && persistentConnection.mIsConnected
                                                && injectUptimeMillis <= 0) {
                                            persistentConnection.resetBackoffLocked();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        final int i3 = 1;
        this.mStableCheck =
                new Runnable(
                        this) { // from class:
                                // com.android.server.am.PersistentConnection$$ExternalSyntheticLambda0
                    public final /* synthetic */ PersistentConnection f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        int i32 = i3;
                        PersistentConnection persistentConnection = this.f$0;
                        switch (i32) {
                            case 0:
                                synchronized (persistentConnection.mLock) {
                                    try {
                                        if (persistentConnection.mShouldBeBound) {
                                            persistentConnection.bindInnerLocked(false);
                                            return;
                                        }
                                        return;
                                    } finally {
                                    }
                                }
                            default:
                                synchronized (persistentConnection.mLock) {
                                    try {
                                        long injectUptimeMillis =
                                                (persistentConnection.mLastConnectedTime
                                                                + persistentConnection
                                                                        .mResetBackoffDelay)
                                                        - persistentConnection.injectUptimeMillis();
                                        if (persistentConnection.mBound
                                                && persistentConnection.mIsConnected
                                                && injectUptimeMillis <= 0) {
                                            persistentConnection.resetBackoffLocked();
                                        }
                                    } finally {
                                    }
                                }
                                return;
                        }
                    }
                };
        this.mTag = str;
        this.mContext = context;
        this.mHandler = handler;
        this.mUserId = i;
        this.mComponentName = componentName;
        long j4 = j * 1000;
        this.mRebindBackoffMs = j4;
        this.mRebindBackoffIncrease = d;
        this.mRebindMaxBackoffMs = j2 * 1000;
        this.mResetBackoffDelay = j3 * 1000;
        this.mNextBackoffMs = j4;
    }

    public abstract Object asInterface(IBinder iBinder);

    public final void bindInnerLocked(boolean z) {
        injectRemoveCallbacks(this.mBindForBackoffRunnable);
        this.mRebindScheduled = false;
        if (this.mBound) {
            return;
        }
        this.mBound = true;
        injectRemoveCallbacks(this.mStableCheck);
        if (z) {
            resetBackoffLocked();
        }
        Intent component = new Intent().setComponent(this.mComponentName);
        Context context = this.mContext;
        int bindFlags = getBindFlags() | 1;
        int i = this.mUserId;
        if (context.bindServiceAsUser(
                component, this.mServiceConnection, bindFlags, this.mHandler, UserHandle.of(i))) {
            return;
        }
        Log.e(this.mTag, "Binding: " + component.getComponent() + " u" + i + " failed.");
    }

    public final void dump(String str, PrintWriter printWriter) {
        synchronized (this.mLock) {
            try {
                printWriter.print(str);
                printWriter.print(this.mComponentName.flattenToShortString());
                printWriter.print(" u");
                printWriter.print(this.mUserId);
                printWriter.print(this.mBound ? " [bound]" : " [not bound]");
                printWriter.print(this.mIsConnected ? " [connected]" : " [not connected]");
                if (this.mRebindScheduled) {
                    printWriter.print(" reconnect in ");
                    TimeUtils.formatDuration(
                            this.mReconnectTime - injectUptimeMillis(), printWriter);
                }
                printWriter.println();
                printWriter.print(str);
                printWriter.print("  Next backoff(sec): ");
                printWriter.print(this.mNextBackoffMs / 1000);
                printWriter.println();
                printWriter.print(str);
                printWriter.print("  Connected: ");
                printWriter.print(this.mNumConnected);
                printWriter.print("  Disconnected: ");
                printWriter.print(this.mNumDisconnected);
                printWriter.print("  Died: ");
                printWriter.print(this.mNumBindingDied);
                if (this.mIsConnected) {
                    printWriter.print("  Duration: ");
                    TimeUtils.formatDuration(
                            injectUptimeMillis() - this.mLastConnectedTime, printWriter);
                }
                printWriter.println();
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public abstract int getBindFlags();

    public Runnable getBindForBackoffRunnableForTest() {
        return this.mBindForBackoffRunnable;
    }

    public final long getNextBackoffMs() {
        long j;
        synchronized (this.mLock) {
            j = this.mNextBackoffMs;
        }
        return j;
    }

    public long getNextBackoffMsForTest() {
        return this.mNextBackoffMs;
    }

    public final int getNumBindingDied() {
        int i;
        synchronized (this.mLock) {
            i = this.mNumBindingDied;
        }
        return i;
    }

    public final int getNumDisconnected() {
        int i;
        synchronized (this.mLock) {
            i = this.mNumDisconnected;
        }
        return i;
    }

    public long getReconnectTimeForTest() {
        return this.mReconnectTime;
    }

    public ServiceConnection getServiceConnectionForTest() {
        return this.mServiceConnection;
    }

    public Runnable getStableCheckRunnableForTest() {
        return this.mStableCheck;
    }

    public void injectPostAtTime(Runnable runnable, long j) {
        this.mHandler.postAtTime(runnable, j);
    }

    public void injectRemoveCallbacks(Runnable runnable) {
        this.mHandler.removeCallbacks(runnable);
    }

    public long injectUptimeMillis() {
        return SystemClock.uptimeMillis();
    }

    public final void resetBackoffLocked() {
        long j = this.mNextBackoffMs;
        long j2 = this.mRebindBackoffMs;
        if (j != j2) {
            this.mNextBackoffMs = j2;
            Log.i(this.mTag, "Backoff reset to " + this.mNextBackoffMs);
        }
    }

    public final void scheduleRebindLocked() {
        unbindLocked();
        if (this.mRebindScheduled) {
            return;
        }
        Log.i(
                this.mTag,
                AudioConfig$$ExternalSyntheticOutline0.m(
                        new StringBuilder("Scheduling to reconnect in "),
                        this.mNextBackoffMs,
                        " ms (uptime)"));
        long injectUptimeMillis = injectUptimeMillis() + this.mNextBackoffMs;
        this.mReconnectTime = injectUptimeMillis;
        injectPostAtTime(this.mBindForBackoffRunnable, injectUptimeMillis);
        this.mNextBackoffMs =
                Math.min(
                        this.mRebindMaxBackoffMs,
                        (long) (this.mNextBackoffMs * this.mRebindBackoffIncrease));
        this.mRebindScheduled = true;
    }

    public boolean shouldBeBoundForTest() {
        return this.mShouldBeBound;
    }

    public final void unbind() {
        synchronized (this.mLock) {
            this.mShouldBeBound = false;
            unbindLocked();
            injectRemoveCallbacks(this.mStableCheck);
        }
    }

    public final void unbindLocked() {
        injectRemoveCallbacks(this.mBindForBackoffRunnable);
        this.mRebindScheduled = false;
        if (this.mBound) {
            Log.i(
                    this.mTag,
                    "Stopping: "
                            + this.mComponentName.flattenToShortString()
                            + " u"
                            + this.mUserId);
            this.mBound = false;
            this.mContext.unbindService(this.mServiceConnection);
            this.mIsConnected = false;
            injectRemoveCallbacks(this.mStableCheck);
        }
    }
}
