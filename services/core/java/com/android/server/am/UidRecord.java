package com.android.server.am;

import android.app.ActivityManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.util.ArraySet;
import android.util.TimeUtils;

import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UidRecord {
    public static final int[] ORIG_ENUMS = {1, 2, 4, 8, 16, 32, Integer.MIN_VALUE};
    public static final int[] PROTO_ENUMS = {0, 1, 2, 3, 4, 5, 6};
    public long curProcStateSeq;
    public volatile boolean hasInternetPermission;
    public long lastNetworkUpdatedProcStateSeq;
    public boolean mCurAllowList;
    public boolean mEphemeral;
    public long mLastBackgroundTime;
    public long mLastIdleTimeIfStillIdle;
    public int mLastReportedChange;
    public boolean mProcAdjChanged;
    public long mRealLastIdleTime;
    public boolean mSetAllowList;
    public int mSetCapability;
    public boolean mSetIdle;
    public final int mUid;
    public boolean mUidIsFrozen;
    public volatile long procStateSeqWaitingForNetwork;
    public int mSetProcState = 20;
    public final ArraySet mProcRecords = new ArraySet();
    public boolean mFGSFilter = false;
    public final Object networkStateLock = new Object();
    public final UidObserverController.ChangeRecord pendingChange =
            new UidObserverController.ChangeRecord();
    public boolean mIdle = true;
    public int mCurProcState = 19;
    public boolean mForegroundServices = false;
    public int mCurCapability = 0;

    public UidRecord(int i) {
        this.mUid = i;
    }

    public final boolean areAllProcessesFrozen(ProcessRecord processRecord) {
        for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
            ProcessRecord processRecord2 = (ProcessRecord) this.mProcRecords.valueAt(size);
            ProcessCachedOptimizerRecord processCachedOptimizerRecord = processRecord2.mOptRecord;
            if (processRecord != processRecord2 && !processCachedOptimizerRecord.mFrozen) {
                return false;
            }
        }
        return true;
    }

    public final void forEachProcess(Consumer consumer) {
        for (int size = this.mProcRecords.size() - 1; size >= 0; size--) {
            consumer.accept((ProcessRecord) this.mProcRecords.valueAt(size));
        }
    }

    public final String toString() {
        boolean z;
        StringBuilder m = BootReceiver$$ExternalSyntheticOutline0.m(128, "UidRecord{");
        m.append(Integer.toHexString(System.identityHashCode(this)));
        m.append(' ');
        UserHandle.formatUid(m, this.mUid);
        m.append(' ');
        int i = this.mCurProcState;
        int i2 = ProcessList.PAGE_SIZE;
        m.append(ActivityManager.procStateToString(i));
        if (this.mEphemeral) {
            m.append(" ephemeral");
        }
        if (this.mForegroundServices) {
            m.append(" fgServices");
        }
        if (this.mCurAllowList) {
            m.append(" allowlist");
        }
        if (this.mLastBackgroundTime > 0) {
            m.append(" bg:");
            TimeUtils.formatDuration(SystemClock.elapsedRealtime() - this.mLastBackgroundTime, m);
        }
        if (this.mIdle) {
            m.append(" idle");
        }
        if (this.mLastReportedChange != 0) {
            m.append(" change:");
            boolean z2 = true;
            if ((this.mLastReportedChange & 1) != 0) {
                m.append("gone");
                z = true;
            } else {
                z = false;
            }
            if ((this.mLastReportedChange & 2) != 0) {
                if (z) {
                    m.append("|");
                }
                m.append("idle");
                z = true;
            }
            if ((this.mLastReportedChange & 4) != 0) {
                if (z) {
                    m.append("|");
                }
                m.append("active");
                z = true;
            }
            if ((this.mLastReportedChange & 8) != 0) {
                if (z) {
                    m.append("|");
                }
                m.append("cached");
            } else {
                z2 = z;
            }
            if ((this.mLastReportedChange & 16) != 0) {
                if (z2) {
                    m.append("|");
                }
                m.append("uncached");
            }
            if ((this.mLastReportedChange & Integer.MIN_VALUE) != 0) {
                if (z2) {
                    m.append("|");
                }
                m.append("procstate");
            }
            if ((this.mLastReportedChange & 64) != 0) {
                if (z2) {
                    m.append("|");
                }
                m.append("procadj");
            }
        }
        m.append(" procs:");
        m.append(0);
        m.append(" seq(");
        m.append(this.curProcStateSeq);
        m.append(",");
        m.append(this.lastNetworkUpdatedProcStateSeq);
        m.append(")}");
        m.append(" caps=");
        ActivityManager.printCapabilitiesSummary(m, this.mCurCapability);
        return m.toString();
    }
}
