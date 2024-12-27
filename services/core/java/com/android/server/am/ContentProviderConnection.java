package com.android.server.am;

import android.os.Binder;
import android.os.SystemClock;
import android.util.Slog;
import android.util.TimeUtils;

import com.android.internal.app.procstats.AssociationState;
import com.android.internal.app.procstats.ProcessStats;
import com.android.server.BootReceiver$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContentProviderConnection extends Binder
        implements OomAdjusterModernImpl.Connection {
    public AssociationState.SourceState association;
    public final ProcessRecord client;
    public final String clientPackage;
    public boolean dead;
    public final int mExpectedUserId;
    public int mNumStableIncs;
    public int mNumUnstableIncs;
    public Object mProcStatsLock;
    public int mStableCount;
    public int mUnstableCount;
    public final ContentProviderRecord provider;
    public boolean waiting;
    public final Object mLock = new Object();
    public final long createTime = SystemClock.elapsedRealtime();

    public ContentProviderConnection(
            ContentProviderRecord contentProviderRecord,
            ProcessRecord processRecord,
            String str,
            int i) {
        this.provider = contentProviderRecord;
        this.client = processRecord;
        this.clientPackage = str;
        this.mExpectedUserId = i;
    }

    public final void adjustCounts(int i, int i2) {
        synchronized (this.mLock) {
            if (i > 0) {
                try {
                    this.mNumStableIncs += i;
                } catch (Throwable th) {
                    throw th;
                }
            }
            int i3 = this.mStableCount + i;
            if (i3 < 0) {
                throw new IllegalStateException("stableCount < 0: " + i3);
            }
            if (i2 > 0) {
                this.mNumUnstableIncs += i2;
            }
            int i4 = this.mUnstableCount + i2;
            if (i4 < 0) {
                throw new IllegalStateException("unstableCount < 0: " + i4);
            }
            if (i3 + i4 <= 0) {
                throw new IllegalStateException(
                        "ref counts can't go to zero here: stable=" + i3 + " unstable=" + i4);
            }
            this.mStableCount = i3;
            this.mUnstableCount = i4;
        }
    }

    @Override // com.android.server.am.OomAdjusterModernImpl.Connection
    public final void computeHostOomAdjLSP(
            OomAdjuster oomAdjuster,
            ProcessRecord processRecord,
            ProcessRecord processRecord2,
            long j,
            ProcessRecord processRecord3,
            boolean z,
            int i) {
        oomAdjuster.computeProviderHostOomAdjLSP(
                this,
                processRecord,
                processRecord2,
                j,
                processRecord3,
                z,
                false,
                false,
                i,
                1001,
                false,
                false);
    }

    public final int decrementCount(boolean z) {
        int i;
        synchronized (this.mLock) {
            try {
                if (z) {
                    this.mStableCount--;
                } else {
                    this.mUnstableCount--;
                }
                i = this.mStableCount + this.mUnstableCount;
            } catch (Throwable th) {
                throw th;
            }
        }
        return i;
    }

    public final int getStableCount() {
        return this.mStableCount;
    }

    public final int getUnstableCount() {
        return this.mUnstableCount;
    }

    public final void startAssociationIfNeeded() {
        if (this.association == null) {
            ContentProviderRecord contentProviderRecord = this.provider;
            if (contentProviderRecord.proc != null) {
                int i = contentProviderRecord.appInfo.uid;
                ProcessRecord processRecord = this.client;
                if (i == processRecord.uid
                        && contentProviderRecord.info.processName.equals(
                                processRecord.processName)) {
                    return;
                }
                ContentProviderRecord contentProviderRecord2 = this.provider;
                ProcessStats.ProcessStateHolder processStateHolder =
                        contentProviderRecord2.proc.mPkgList.get(
                                contentProviderRecord2.name.getPackageName());
                if (processStateHolder == null) {
                    Slog.wtf(
                            "ActivityManager",
                            "No package in referenced provider "
                                    + this.provider.name.toShortString()
                                    + ": proc="
                                    + this.provider.proc);
                    return;
                }
                if (processStateHolder.pkg == null) {
                    Slog.wtf(
                            "ActivityManager",
                            "Inactive holder in referenced provider "
                                    + this.provider.name.toShortString()
                                    + ": proc="
                                    + this.provider.proc);
                    return;
                }
                Object obj = this.provider.proc.mService.mProcessStats.mLock;
                this.mProcStatsLock = obj;
                synchronized (obj) {
                    AssociationState associationStateLocked =
                            processStateHolder.pkg.getAssociationStateLocked(
                                    processStateHolder.state, this.provider.name.getClassName());
                    ProcessRecord processRecord2 = this.client;
                    this.association =
                            associationStateLocked.startSource(
                                    processRecord2.uid,
                                    processRecord2.processName,
                                    this.clientPackage);
                }
            }
        }
    }

    public final void toClientString(StringBuilder sb) {
        sb.append(this.client.toShortString());
        synchronized (this.mLock) {
            sb.append(" s");
            sb.append(this.mStableCount);
            sb.append("/");
            sb.append(this.mNumStableIncs);
            sb.append(" u");
            sb.append(this.mUnstableCount);
            sb.append("/");
            sb.append(this.mNumUnstableIncs);
        }
        if (this.waiting) {
            sb.append(" WAITING");
        }
        if (this.dead) {
            sb.append(" DEAD");
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        sb.append(" ");
        TimeUtils.formatDuration(elapsedRealtime - this.createTime, sb);
    }

    public final String toString() {
        StringBuilder m =
                BootReceiver$$ExternalSyntheticOutline0.m(128, "ContentProviderConnection{");
        m.append(this.provider.toShortString());
        m.append("->");
        toClientString(m);
        m.append('}');
        return m.toString();
    }
}
