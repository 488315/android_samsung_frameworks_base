package com.android.server.am;

import android.os.Process;
import android.os.StrictMode;
import android.os.SystemClock;
import android.os.Trace;
import android.os.UserHandle;
import android.util.EventLog;
import android.util.Slog;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;
import com.android.server.am.ProcessList;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class PhantomProcessRecord {
    public long mCurrentCputime;
    public long mLastCputime;
    public final Object mLock;
    public final Consumer mOnKillListener;
    public final int mPid;
    public final FileDescriptor mPidFd;
    public final int mPpid;
    public final String mProcessName;
    public final ActivityManagerService mService;
    public String mStringName;
    public final int mUid;
    public int mUpdateSeq;
    public boolean mZombie;
    public static final long[] LONG_OUT = new long[1];
    public static final int[] LONG_FORMAT = {8202};
    public final AnonymousClass1 mProcKillTimer = new Runnable() { // from class: com.android.server.am.PhantomProcessRecord.1
        @Override // java.lang.Runnable
        public final void run() {
            synchronized (PhantomProcessRecord.this.mLock) {
                Slog.w("ActivityManager", "Process " + toString() + " is still alive after " + PhantomProcessRecord.this.mService.mConstants.mProcessKillTimeoutMs + "ms");
                PhantomProcessRecord phantomProcessRecord = PhantomProcessRecord.this;
                phantomProcessRecord.mZombie = true;
                phantomProcessRecord.onProcDied(false);
            }
        }
    };
    public boolean mKilled = false;
    public int mAdj = -1000;
    public final long mKnownSince = SystemClock.elapsedRealtime();
    public final ProcessList.KillHandler mKillHandler = ProcessList.sKillHandler;

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.am.PhantomProcessRecord$1] */
    public PhantomProcessRecord(String str, int i, int i2, int i3, ActivityManagerService activityManagerService, PhantomProcessList$$ExternalSyntheticLambda0 phantomProcessList$$ExternalSyntheticLambda0) {
        this.mProcessName = str;
        this.mUid = i;
        this.mPid = i2;
        this.mPpid = i3;
        this.mService = activityManagerService;
        this.mLock = activityManagerService.mPhantomProcessList.mLock;
        this.mOnKillListener = phantomProcessList$$ExternalSyntheticLambda0;
        if (!Process.supportsPidFd()) {
            this.mPidFd = null;
            return;
        }
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            try {
                FileDescriptor openPidFd = Process.openPidFd(i2, 0);
                this.mPidFd = openPidFd;
                if (openPidFd != null) {
                } else {
                    throw new IllegalStateException();
                }
            } catch (IOException e) {
                Slog.w("ActivityManager", "Unable to open process " + i2 + ", it might be gone");
                IllegalStateException illegalStateException = new IllegalStateException();
                illegalStateException.initCause(e);
                throw illegalStateException;
            }
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }

    public final void killLocked(String str, boolean z) {
        if (this.mKilled) {
            return;
        }
        Trace.traceBegin(64L, "kill");
        if (z || this.mUid == this.mService.mCurOomAdjUid) {
            this.mService.reportUidInfoMessageLocked(this.mUid, "Killing " + toString() + ": " + str);
        }
        if (this.mPid > 0) {
            Integer valueOf = Integer.valueOf(UserHandle.getUserId(this.mUid));
            Integer valueOf2 = Integer.valueOf(this.mPid);
            String str2 = this.mProcessName;
            Integer valueOf3 = Integer.valueOf(this.mAdj);
            long[] rss = Process.getRss(this.mPid);
            EventLog.writeEvent(30023, valueOf, valueOf2, str2, valueOf3, str, Long.valueOf((rss == null || rss.length <= 0) ? 0L : rss[0]));
            if (Process.supportsPidFd()) {
                this.mKillHandler.postDelayed(this.mProcKillTimer, this, this.mService.mConstants.mProcessKillTimeoutMs);
            } else {
                onProcDied(false);
            }
            Process.killProcessQuiet(this.mPid);
            ProcessList.killProcessGroup(this.mUid, this.mPid);
        }
        this.mKilled = true;
        Trace.traceEnd(64L);
    }

    public final void onProcDied(boolean z) {
        if (z) {
            Slog.i("ActivityManager", "Process " + toString() + " died");
        }
        this.mKillHandler.removeCallbacks(this.mProcKillTimer, this);
        Consumer consumer = this.mOnKillListener;
        if (consumer != null) {
            consumer.accept(this);
        }
    }

    public final String toString() {
        String str = this.mStringName;
        if (str != null) {
            return str;
        }
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "PhantomProcessRecord {");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        m.append(this.mPid);
        m.append(':');
        m.append(this.mPpid);
        m.append(':');
        m.append(this.mProcessName);
        m.append('/');
        int i = this.mUid;
        if (i < 10000) {
            m.append(i);
        } else {
            m.append('u');
            m.append(UserHandle.getUserId(i));
            int appId = UserHandle.getAppId(i);
            if (appId >= 10000) {
                m.append('a');
                m.append(appId - 10000);
            } else {
                m.append('s');
                m.append(appId);
            }
            if (appId >= 99000 && appId <= 99999) {
                m.append('i');
                m.append(appId - 99000);
            }
        }
        m.append('}');
        String sb = m.toString();
        this.mStringName = sb;
        return sb;
    }
}
