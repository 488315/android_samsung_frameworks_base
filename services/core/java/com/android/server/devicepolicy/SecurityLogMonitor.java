package com.android.server.devicepolicy;

import android.app.admin.IAuditLogEventsCallback;
import android.app.admin.SecurityLog;
import android.app.admin.flags.Flags;
import android.os.Handler;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;
import android.util.Slog;
import android.util.SparseArray;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.utils.Slogf;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SecurityLogMonitor implements Runnable {
    public static final long BROADCAST_RETRY_INTERVAL_MS;
    static final int BUFFER_ENTRIES_NOTIFICATION_LEVEL = 1024;
    public static final long FORCE_FETCH_THROTTLE_NS;
    public static final long MAX_AUDIT_LOG_EVENT_AGE_NS;
    public static final long OVERLAP_NS;
    public static final long POLLING_INTERVAL_MS;
    public static final long RATE_LIMIT_INTERVAL_MS;
    public boolean mAuditLogEnabled;
    public int mEnabledUser;
    public final Handler mHandler;
    public long mLastForceNanos;
    public boolean mLegacyLogEnabled;
    public final DevicePolicyManagerService mService;
    public final Lock mLock = new ReentrantLock();
    public Thread mMonitorThread = null;
    public ArrayList mPendingLogs = new ArrayList();
    public boolean mAllowedToRetrieve = false;
    public boolean mCriticalLevelLogged = false;
    public final ArrayList mLastEvents = new ArrayList();
    public long mLastEventNanos = -1;
    public long mNextAllowedRetrievalTimeMillis = -1;
    public boolean mPaused = false;
    public final Semaphore mForceSemaphore = new Semaphore(0);
    public final SparseArray mAuditLogCallbacks = new SparseArray();
    public final ArrayDeque mAuditLogEventBuffer = new ArrayDeque();
    public long mId = 0;

    static {
        TimeUnit timeUnit = TimeUnit.HOURS;
        RATE_LIMIT_INTERVAL_MS = timeUnit.toMillis(2L);
        TimeUnit timeUnit2 = TimeUnit.MINUTES;
        BROADCAST_RETRY_INTERVAL_MS = timeUnit2.toMillis(30L);
        POLLING_INTERVAL_MS = timeUnit2.toMillis(1L);
        TimeUnit timeUnit3 = TimeUnit.SECONDS;
        OVERLAP_NS = timeUnit3.toNanos(3L);
        FORCE_FETCH_THROTTLE_NS = timeUnit3.toNanos(10L);
        MAX_AUDIT_LOG_EVENT_AGE_NS = timeUnit.toNanos(8L);
    }

    public SecurityLogMonitor(DevicePolicyManagerService devicePolicyManagerService, Handler handler) {
        this.mLastForceNanos = 0L;
        this.mService = devicePolicyManagerService;
        this.mLastForceNanos = System.nanoTime();
        this.mHandler = handler;
    }

    public final void getNextBatch(ArrayList arrayList) {
        if (this.mLastEventNanos < 0) {
            SecurityLog.readEvents(arrayList);
        } else {
            SecurityLog.readEventsSince(this.mLastEvents.isEmpty() ? this.mLastEventNanos : Math.max(0L, this.mLastEventNanos - OVERLAP_NS), arrayList);
        }
        int i = 0;
        while (true) {
            if (i >= arrayList.size() - 1) {
                break;
            }
            long timeNanos = ((SecurityLog.SecurityEvent) arrayList.get(i)).getTimeNanos();
            i++;
            if (timeNanos > ((SecurityLog.SecurityEvent) arrayList.get(i)).getTimeNanos()) {
                arrayList.sort(new SecurityLogMonitor$$ExternalSyntheticLambda1());
                break;
            }
        }
        SecurityLog.redactEvents(arrayList, this.mEnabledUser);
    }

    public final void mergeBatchLocked(ArrayList arrayList) {
        ArrayList arrayList2 = new ArrayList();
        int i = 0;
        int i2 = 0;
        while (i < this.mLastEvents.size() && i2 < arrayList.size()) {
            SecurityLog.SecurityEvent securityEvent = (SecurityLog.SecurityEvent) arrayList.get(i2);
            long timeNanos = securityEvent.getTimeNanos();
            if (timeNanos > this.mLastEventNanos) {
                break;
            }
            SecurityLog.SecurityEvent securityEvent2 = (SecurityLog.SecurityEvent) this.mLastEvents.get(i);
            long timeNanos2 = securityEvent2.getTimeNanos();
            if (timeNanos2 > timeNanos) {
                arrayList2.add(securityEvent);
            } else if (timeNanos2 < timeNanos) {
                i++;
            } else {
                if (!securityEvent2.eventEquals(securityEvent)) {
                    arrayList2.add(securityEvent);
                }
                i++;
            }
            i2++;
        }
        arrayList2.addAll(arrayList.subList(i2, arrayList.size()));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            ((SecurityLog.SecurityEvent) it.next()).setId(this.mId);
            long j = this.mId;
            if (j == Long.MAX_VALUE) {
                Slog.i("SecurityLogMonitor", "Reached maximum id value; wrapping around.");
                this.mId = 0L;
            } else {
                this.mId = j + 1;
            }
        }
        if (!Flags.securityLogV2Enabled() || this.mLegacyLogEnabled) {
            this.mPendingLogs.addAll(arrayList2);
            if (SecurityLog.isLoggingEnabled() && this.mPendingLogs.size() >= 9216 && !this.mCriticalLevelLogged) {
                this.mCriticalLevelLogged = true;
                SecurityLog.writeEvent(210015, new Object[0]);
            }
            if (this.mPendingLogs.size() > 10240) {
                this.mPendingLogs = new ArrayList(this.mPendingLogs.subList(r2.size() - 5120, this.mPendingLogs.size()));
                this.mCriticalLevelLogged = false;
                Slog.i("SecurityLogMonitor", "Pending logs buffer full. Discarding old logs.");
            }
        }
        if (Flags.securityLogV2Enabled() && this.mAuditLogEnabled && !this.mPaused) {
            if (!arrayList2.isEmpty()) {
                for (int i3 = 0; i3 < this.mAuditLogCallbacks.size(); i3++) {
                    this.mHandler.post(new SecurityLogMonitor$$ExternalSyntheticLambda0(this, this.mAuditLogCallbacks.keyAt(i3), (IAuditLogEventsCallback) this.mAuditLogCallbacks.valueAt(i3), arrayList2));
                }
            }
            this.mAuditLogEventBuffer.addAll(arrayList2);
            long nanos = TimeUnit.MILLISECONDS.toNanos(System.currentTimeMillis());
            Iterator it2 = this.mAuditLogEventBuffer.iterator();
            while (it2.hasNext()) {
                SecurityLog.SecurityEvent securityEvent3 = (SecurityLog.SecurityEvent) it2.next();
                if (this.mAuditLogEventBuffer.size() <= 10000 && nanos - securityEvent3.getTimeNanos() <= MAX_AUDIT_LOG_EVENT_AGE_NS) {
                    return;
                } else {
                    it2.remove();
                }
            }
        }
    }

    public final void notifyDeviceOwnerOrProfileOwnerIfNeeded(boolean z) {
        ((ReentrantLock) this.mLock).lockInterruptibly();
        try {
            if (this.mPaused) {
                ((ReentrantLock) this.mLock).unlock();
                return;
            }
            int size = this.mPendingLogs.size();
            boolean z2 = (size >= 1024 || (z && size > 0)) && !this.mAllowedToRetrieve;
            if (size > 0 && SystemClock.elapsedRealtime() >= this.mNextAllowedRetrievalTimeMillis) {
                z2 = true;
            }
            if (z2) {
                this.mAllowedToRetrieve = true;
                this.mNextAllowedRetrievalTimeMillis = SystemClock.elapsedRealtime() + BROADCAST_RETRY_INTERVAL_MS;
            }
            ((ReentrantLock) this.mLock).unlock();
            if (z2) {
                Slog.i("SecurityLogMonitor", "notify DO or PO");
                this.mService.sendDeviceOwnerOrProfileOwnerCommand(this.mEnabledUser, "android.app.action.SECURITY_LOGS_AVAILABLE", null);
            }
        } catch (Throwable th) {
            ((ReentrantLock) this.mLock).unlock();
            throw th;
        }
    }

    public final void resetLegacyBufferLocked() {
        this.mPendingLogs = new ArrayList();
        this.mCriticalLevelLogged = false;
        this.mAllowedToRetrieve = false;
        this.mNextAllowedRetrievalTimeMillis = -1L;
        Slog.i("SecurityLogMonitor", "Legacy buffer reset.");
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean tryAcquire;
        Process.setThreadPriority(10);
        ArrayList arrayList = new ArrayList();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                tryAcquire = this.mForceSemaphore.tryAcquire(POLLING_INTERVAL_MS, TimeUnit.MILLISECONDS);
                getNextBatch(arrayList);
                ((ReentrantLock) this.mLock).lockInterruptibly();
                try {
                    mergeBatchLocked(arrayList);
                    ((ReentrantLock) this.mLock).unlock();
                    saveLastEvents(arrayList);
                    arrayList.clear();
                } catch (Throwable th) {
                    ((ReentrantLock) this.mLock).unlock();
                    throw th;
                }
            } catch (IOException e) {
                Log.e("SecurityLogMonitor", "Failed to read security log", e);
            } catch (InterruptedException e2) {
                Log.i("SecurityLogMonitor", "Thread interrupted, exiting.", e2);
            }
            if (Flags.securityLogV2Enabled() && !this.mLegacyLogEnabled) {
            }
            notifyDeviceOwnerOrProfileOwnerIfNeeded(tryAcquire);
        }
        this.mLastEvents.clear();
        long j = this.mLastEventNanos;
        if (j != -1) {
            this.mLastEventNanos = j + 1;
        }
        Slog.i("SecurityLogMonitor", "MonitorThread exit.");
    }

    public final void saveLastEvents(ArrayList arrayList) {
        this.mLastEvents.clear();
        if (arrayList.isEmpty()) {
            return;
        }
        this.mLastEventNanos = ((SecurityLog.SecurityEvent) arrayList.get(arrayList.size() - 1)).getTimeNanos();
        int size = arrayList.size() - 2;
        while (size >= 0 && this.mLastEventNanos - ((SecurityLog.SecurityEvent) arrayList.get(size)).getTimeNanos() < OVERLAP_NS) {
            size--;
        }
        this.mLastEvents.addAll(arrayList.subList(size + 1, arrayList.size()));
    }

    public final void setAuditLogEventsCallback(int i, IAuditLogEventsCallback iAuditLogEventsCallback) {
        ((ReentrantLock) this.mLock).lock();
        try {
            if (iAuditLogEventsCallback == null) {
                this.mAuditLogCallbacks.remove(i);
                Slogf.i("SecurityLogMonitor", "Cleared audit log callback for UID %d", Integer.valueOf(i));
                return;
            }
            this.mHandler.post(new SecurityLogMonitor$$ExternalSyntheticLambda0(this, i, iAuditLogEventsCallback, new ArrayList(this.mAuditLogEventBuffer)));
            this.mAuditLogCallbacks.append(i, iAuditLogEventsCallback);
            ((ReentrantLock) this.mLock).unlock();
            Slogf.i("SecurityLogMonitor", "Set audit log callback for UID %d", Integer.valueOf(i));
        } finally {
            ((ReentrantLock) this.mLock).unlock();
        }
    }

    public final void start(int i) {
        HermesService$3$$ExternalSyntheticOutline0.m(i, "Starting security logging for user ", "SecurityLogMonitor");
        this.mEnabledUser = i;
        ((ReentrantLock) this.mLock).lock();
        try {
            if (this.mMonitorThread == null) {
                resetLegacyBufferLocked();
                startMonitorThreadLocked();
            } else {
                Slog.i("SecurityLogMonitor", "Security log monitor thread is already running");
            }
            ((ReentrantLock) this.mLock).unlock();
        } catch (Throwable th) {
            ((ReentrantLock) this.mLock).unlock();
            throw th;
        }
    }

    public final void startMonitorThreadLocked() {
        this.mId = 0L;
        this.mPaused = false;
        Thread thread = new Thread(this);
        this.mMonitorThread = thread;
        thread.start();
        SecurityLog.writeEvent(210011, new Object[0]);
        Slog.i("SecurityLogMonitor", "Security log monitor thread started");
    }

    public final void stop() {
        Slog.i("SecurityLogMonitor", "Stopping security logging.");
        ((ReentrantLock) this.mLock).lock();
        try {
            if (this.mMonitorThread != null) {
                stopMonitorThreadLocked();
                resetLegacyBufferLocked();
            }
        } finally {
            ((ReentrantLock) this.mLock).unlock();
        }
    }

    public final void stopMonitorThreadLocked() {
        this.mMonitorThread.interrupt();
        try {
            this.mMonitorThread.join(TimeUnit.SECONDS.toMillis(5L));
        } catch (InterruptedException e) {
            Log.e("SecurityLogMonitor", "Interrupted while waiting for thread to stop", e);
        }
        this.mMonitorThread = null;
        SecurityLog.writeEvent(210012, new Object[0]);
    }
}
